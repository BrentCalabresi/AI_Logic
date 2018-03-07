package pl;

import com.sun.org.apache.xpath.internal.operations.Mod;
import pl.cnf.CNFConverter;
import pl.cnf.Literal;
import pl.core.*;
import pl.cnf.Clause;
import pl.examples.*;

import java.util.*;

public class Part2 {



    public static void main(String[] args) {
        ModusPonensKB mkb = new ModusPonensKB();
        WumpusWorldKB wkb = new WumpusWorldKB();
        HornClauseKB hkb = new HornClauseKB();
        MoreLiarsKB mlkb = new MoreLiarsKB();

        System.out.println("----- Part 2 -----");
        boolean modusSat = DPLL_Satisfiable(mkb);
        boolean wumpusSat = DPLL_Satisfiable(wkb);
        boolean hornSat = DPLL_Satisfiable(hkb);
        boolean liarsSat = DPLL_Satisfiable(new LiarsTruthTellersKB());
        boolean moreSat = DPLL_Satisfiable(mlkb);

        System.out.println("MODUS PONENS:");
        System.out.print("Is modus ponens satisfiable? ");
        printBool(modusSat);
        System.out.print("Is modus ponens with ~Q added satisfiable? ");
        mkb.addNotQ();
        printBool(DPLL_Satisfiable(mkb));
        System.out.println("Since modus ponens is satisfiable without ~Q and not satisfiable with it, Q must be true\n");

        System.out.println("WUMPUS WORLD:");
        System.out.print("Is the given wumpus world KB satisfiable? ");
        printBool(wumpusSat);
        System.out.print("Is the given wumpus world with P1,2 added satisfiable? ");
        wkb.addP12();
        boolean modusSat2 = DPLL_Satisfiable(wkb);
        printBool(modusSat2);
        System.out.println("Since wumpus is satisfiable without P1,2 and not satisfiable with it, P1,2 must be false\n");

        System.out.println("HORN CLAUSES:");
        System.out.print("Is the horn clause problem satisfiable? ");
        printBool(hornSat);

        System.out.print("\nIs the horn clause problem with mythical added satisfiable? ");
        hkb.addSymbol(hkb.mythical, true);
        printBool(DPLL_Satisfiable(hkb));
        System.out.print("Is the horn clause problem with ~mythical added satisfiable? ");
        HornClauseKB hkb2 = new HornClauseKB();
        hkb2.addSymbol(hkb2.mythical, false);
        printBool(DPLL_Satisfiable(hkb2));
        System.out.println("Since it's satisfiable for both mythical and ~mythical, we can't prove whether the unicorn is mythical\n");

        System.out.print("Is the horn clause problem with ~magical added satisfiable? ");
        HornClauseKB hkb3 = new HornClauseKB();
        hkb3.addSymbol(hkb3.magical, false);
        printBool(DPLL_Satisfiable(hkb3));
        System.out.println("Since the horn clause problem isn't satisfiable with ~magical added, but is without it, we conclude that the unicorn is magical\n");

        System.out.print("Is the horn clause problem with ~horned added satisfiable? ");
        HornClauseKB hkb4 = new HornClauseKB();
        hkb4.addSymbol(hkb4.horned, false);
        printBool(DPLL_Satisfiable(hkb4));
        System.out.println("Since the horn clause problem isn't satisfiable with ~horned added, but is without it, we conclude that the unicorn is horned\n");



        System.out.println("LIARS & TRUTH-TELLERS (a):");
        System.out.print("Is the liars and truth-tellers problem satisfiable? ");
        printBool(liarsSat);
        System.out.print("Is the liars and truth-tellers problem with Amy added satisfiable? ");
        LiarsTruthTellersKB lkb2 = new LiarsTruthTellersKB();
        lkb2.addSymbol(lkb2.Amy, true);
        printBool(DPLL_Satisfiable(lkb2));
        System.out.println("Since liars & truth-tellers isn't satisfiable with Amy, but is without it, we conclude Amy is a liar\n");

        System.out.print("Is the liars and truth-tellers problem with Bob added satisfiable? ");
        LiarsTruthTellersKB lkb3 = new LiarsTruthTellersKB();
        lkb3.addSymbol(lkb3.Bob, true);
        printBool(DPLL_Satisfiable(lkb3));
        System.out.println("Since liars & truth-tellers isn't satisfiable with Bob, but is without it, we conclude Bob is a liar\n");

        System.out.print("Is the liars and truth-tellers problem with ~Cal added satisfiable? ");
        LiarsTruthTellersKB lkb4 = new LiarsTruthTellersKB();
        lkb4.addSymbol(lkb4.Cal, false);
        printBool(DPLL_Satisfiable(lkb4));
        System.out.println("Since liars & truth-tellers isn't satisfiable with ~Cal, but is without it, we conclude Cal is a truth-teller\n");


        System.out.println("LIARS & TRUTH-TELLERS (b):");
        System.out.print("Is this liars & truth-tellers problem satisfiable? ");
        LiarsTruthTellersKB lkbb = new LiarsTruthTellersKB(2);
        printBool(DPLL_Satisfiable(lkbb));
        System.out.print("Is this liars & truth-tellers problem with Amy added satisfiable? ");
        LiarsTruthTellersKB lkbb2 = new LiarsTruthTellersKB();
        lkbb2.addSymbol(lkbb2.Amy, true);
        printBool(DPLL_Satisfiable(lkbb2));
        System.out.println("Since liars & truth-tellers isn't satisfiable with Amy, but is without it, we conclude Amy is a liar\n");

        System.out.print("Is the liars and truth-tellers problem with Bob added satisfiable? ");
        LiarsTruthTellersKB lkbb3 = new LiarsTruthTellersKB();
        lkbb3.addSymbol(lkbb3.Bob, true);
        printBool(DPLL_Satisfiable(lkbb3));
        System.out.println("Since liars & truth-tellers isn't satisfiable with Bob, but is without it, we conclude Bob is a liar\n");

        System.out.print("Is the liars and truth-tellers problem with ~Cal added satisfiable? ");
        LiarsTruthTellersKB lkbb4 = new LiarsTruthTellersKB();
        lkbb4.addSymbol(lkbb4.Cal, false);
        printBool(DPLL_Satisfiable(lkbb4));
        System.out.println("Since liars & truth-tellers isn't satisfiable with ~Cal, but is without it, we conclude Cal is a truth-teller\n");

        System.out.println("MORE LIARS:");
        System.out.print("Is more liars satisfiable? ");

        printBool(moreSat);

        System.out.println("Who's a liar and who's a truth-teller? (this can take a minute...)");
        // lol this code is trash but it works so ¯\_(ツ)_/¯
        LinkedList<Symbol> liars = new LinkedList<>();
        LinkedList<Symbol> possibleTruthTellers = new LinkedList<>();
        for (Symbol l : mlkb.getSymbols()) {
            MoreLiarsKB newmlkb = new MoreLiarsKB();
            newmlkb.addSymbol(l, true);
            if (!DPLL_Satisfiable(newmlkb)) liars.add(l);
            else possibleTruthTellers.add(l);
        }

        LinkedList<Symbol> truthTellers = new LinkedList<>();
        LinkedList<Symbol> undecidable = new LinkedList<>();
        for (Symbol s : possibleTruthTellers) {
            MoreLiarsKB newmlkb = new MoreLiarsKB();
            newmlkb.addSymbol(s, false);
            if (!DPLL_Satisfiable(newmlkb)) truthTellers.add(s);
            else undecidable.add(s);

        }

        System.out.println("liars: " + liars);
        System.out.println("truth-tellers: " + truthTellers);
        System.out.println("We know this because we added, for example, Amy to the model and found that it was unsatisfiable, " +
                "meaning that Amy must be a liar (same for the other liars). \nTruth-tellers was done similarly, but with ~Jay and ~Kay.");



    }

    public static void printBool(boolean sat) {
        if (sat) System.out.println("Yes");
        else System.out.println("No");
    }

    public static boolean DPLL_Satisfiable(KB kb) {
        HashSet<Clause> clauses = new HashSet<>();

        for (Sentence s : kb.sentences()) {
            for (Clause c : CNFConverter.convert(s)) {
                clauses.add(c);
            }
        }
        //System.out.println(clauses);


        return DPLL(clauses, kb.symbols(), new GenModel());

    }

    public static boolean DPLL(Set<Clause> clauses, Collection<Symbol> symbols, GenModel model) {


//        System.out.println("\n\n-- NEW RECURSION");
//        System.out.println("Clauses: " + clauses);
//        System.out.println("-- Model contents: ");
//        model.dump();
//        System.out.println("-- END MODEL CONTENTS");
        boolean allTrue = true;
        for (Clause c : clauses) {
            //System.out.println("-- checking clause: " + c);
            Boolean satisfied = c.isSatisfiedBy(model);
            if (satisfied == null) {
                allTrue = false;
                break;
            }
            if (!satisfied) {
                //System.out.println("-- wasn't satisfied by " + c);
                return false;
            }


        }
        if (allTrue) return true;

        //System.out.println("-- FINISHED INITIAL CHECK\n");


        Object[] pureSymArr = getPureSymbol(clauses, model);
        if (pureSymArr != null) {
//            System.out.print("-- Found pure symbol: ");
//            System.out.print(pureSymArr[0] + ", ");
//            System.out.println(pureSymArr[1]);
            DPLL_HeuristicRun(clauses, symbols, model, (Symbol)pureSymArr[0], (Literal.Polarity)pureSymArr[1]);
        }


        Literal unitLiteral = getUnitClause(clauses, model);
        if (unitLiteral != null) {
            DPLL_HeuristicRun(clauses, symbols, model, unitLiteral.getContent(), unitLiteral.getPolarity());
        }


        Symbol firstSym = symbols.iterator().next();
        Collection<Symbol> rest = cloneCollection(symbols);
        rest.remove(firstSym);

        //System.out.println("Setting symbol " + firstSym + " with a main recursion jump");


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



        //System.out.println("-- possiblePureSymbols: " + possiblePureSymbols);

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

