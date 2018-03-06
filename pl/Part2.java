package pl;

import pl.cnf.CNFConverter;
import pl.cnf.Literal;
import pl.core.*;
import pl.cnf.Clause;
import pl.examples.ModusPonensKB;
import pl.examples.WumpusWorldKB;

import java.util.*;

public class Part2 {



    public static void main(String[] args) {

        boolean sat = DPLL_Satisfiable(new ModusPonensKB());

        if (sat) System.out.println("It's satisfiable");
        else System.out.println("It's not satisfiable");
    }

    public static boolean DPLL_Satisfiable(KB kb) {
        HashSet<Clause> clauses = new HashSet<>();

        for (Sentence s : kb.sentences()) {
            for (Clause c : CNFConverter.convert(s)) {
                clauses.add(c);
            }
        }
        System.out.println(clauses);


        return DPLL(clauses, kb.symbols(), new GenModel());

    }

    public static boolean DPLL(Set<Clause> clauses, Collection<Symbol> symbols, GenModel model) {


        System.out.println("\n\n-- NEW RECURSION");
        System.out.println("Clauses: " + clauses);
        System.out.println("-- Model contents: ");
        model.dump();
        System.out.println("-- END MODEL CONTENTS");
        boolean allTrue = true;
        for (Clause c : clauses) {
            System.out.println("-- checking clause: " + c);
            Boolean satisfied = c.isSatisfiedBy(model);
            if (satisfied == null) {
                allTrue = false;
                break;
            }
            if (!satisfied) {
                System.out.println("-- wasn't satisfied by " + c);
                return false;
            }


        }
        if (allTrue) return true;

        System.out.println("-- FINISHED INITIAL CHECK\n");


        Object[] pureSymArr = getPureSymbol(clauses, model);
        if (pureSymArr != null) {
            System.out.print("-- Found pure symbol: ");
            System.out.print(pureSymArr[0] + ", ");
            System.out.println(pureSymArr[1]);
            DPLL_HeuristicRun(clauses, symbols, model, (Symbol)pureSymArr[0], (Literal.Polarity)pureSymArr[1]);
        }


        Literal unitLiteral = getUnitClause(clauses, model);
        if (unitLiteral != null) {
            DPLL_HeuristicRun(clauses, symbols, model, unitLiteral.getContent(), unitLiteral.getPolarity());
        }


        Symbol firstSym = symbols.iterator().next();
        Collection<Symbol> rest = cloneCollection(symbols);
        rest.remove(firstSym);

        System.out.println("Setting symbol " + firstSym + " with a main recursion jump");


        return DPLL(clauses, rest, model.union(firstSym, true)) || DPLL(clauses, rest, model.union(firstSym, false));

    }

    public static boolean DPLL_HeuristicRun(Set<Clause> clauses, Collection<Symbol> symbols, GenModel model, Symbol removeSym, Literal.Polarity symPol) {
        Collection<Symbol> newSymbols = cloneCollection(symbols);
        newSymbols.remove(removeSym);

        boolean symbolSign;
        if (symPol == Literal.Polarity.POSITIVE) symbolSign = true;
        else symbolSign = false;

        return DPLL(clauses, newSymbols, model.union(removeSym, symbolSign));
    }

    public static HashSet<Clause> getTrimmedClauses(Set<Clause> clauses, Model model) {
        HashSet<Clause> trimmedClauses = new HashSet<>();
        for (Clause c : clauses) {
            Boolean satisfied = c.isSatisfiedBy(model);
            if (satisfied == null) trimmedClauses.add(c);
        }
        return trimmedClauses;
    }

    public static Object[] getPureSymbol(Set<Clause> clauses, Model model) {

        // create set of clauses with undefined truth values
        HashSet<Clause> trimmedClauses = getTrimmedClauses(clauses, model);

        // two sets - one to store symbols we've seen so far that could be literals,
        // another to store ones that definitely aren't once we see a contradiction
        LinkedHashMap<Symbol, Literal.Polarity> possiblePureSymbols = new LinkedHashMap<>();
        HashSet<Symbol> definitelyNotPureSymbols = new HashSet<>();


        // store a list of all pure symbols in possiblePureSymbols
        for (Clause c : trimmedClauses) {
            for (Literal l : c) {
                Symbol currContent = l.getContent(); // get the symbol this literal is talking about
                Literal.Polarity currPolarity = l.getPolarity(); // get the polarity of the symbol in this clause

                // if we haven't seen the symbol before, add it to possiblyPureSymbols
                if (!definitelyNotPureSymbols.contains(currContent) && !possiblePureSymbols.containsKey(currContent)) {
                    possiblePureSymbols.put(currContent, currPolarity);

                    continue;
                }

                // if the polarity for the symbol stored in possiblyPureSymbols doesn't match the polarity we
                // see, remove it from possiblyPureSymbols and add it to definitelyNotePureSymbols
                if (possiblePureSymbols.containsKey(currContent) && possiblePureSymbols.get(currContent) != currPolarity) {
                    possiblePureSymbols.remove(currContent);
                    definitelyNotPureSymbols.add(currContent);

                }
            }
        }

        // if symbol is already defined in model, we don't need to check it as a
        // possible pure symbol, so remove it
        Iterator<Symbol> iter = possiblePureSymbols.keySet().iterator();
        while (iter.hasNext()) {
            Symbol s = iter.next();
            if (model.get(s) != null) iter.remove();
        }

        // if there aren't any pure symbols, return null
        if (possiblePureSymbols.size() == 0) return null;



        System.out.println("-- possiblePureSymbols: " + possiblePureSymbols);

        // return an array of the first pure symbol and that symbol's polarity
        Object[] retrArr = new Object[2];
        retrArr[0] = possiblePureSymbols.keySet().iterator().next();
        retrArr[1] = possiblePureSymbols.get(retrArr[0]);

        return retrArr;

    }

    public static Literal getUnitClause(Set<Clause> clauses, GenModel model) {
        HashSet<Clause> trimmedClauses = getTrimmedClauses(clauses, model);

        boolean foundNull = false;
        Literal unitLiteral = null;
        // search through each clause
        for (Clause c : trimmedClauses) {
            for (Literal l : c) {

                // if there's a symbol w/ an undefined truth value & we haven't already found one,
                // store it and continue checking for more. if we find another undefined value
                // in this clause, say never mind and go to next clause
                if (model.get(l.getContent()) == null) {
                    if (!foundNull) {
                        foundNull = true;
                        unitLiteral = l;
                    } else {
                        unitLiteral = null;
                        foundNull = false;
                        break;
                    }
                }

            }
            if (foundNull) break;

        }
        return unitLiteral;
    }

    public static HashMap<String,Symbol> cloneHashMap(HashMap<String,Symbol> hm) {
        HashMap<String ,Symbol> newHm = new HashMap<>();
        for (String s : hm.keySet()) {
            newHm.put(s, hm.get(s));
        }
        return newHm;
    }

    public static LinkedList<Symbol> cloneCollection(Collection<Symbol> c) {
        LinkedList<Symbol> newL = new LinkedList<>();
        for (Symbol s : c) newL.add(s);
        return newL;
    }

}

