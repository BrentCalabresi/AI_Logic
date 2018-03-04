
package pl;



import pl.cnf.CNFConverter;
import pl.cnf.Literal;
import pl.core.*;
import pl.cnf.Clause;

import java.util.*;

public class Part2 {

    public static void main(String[] args) {

    }

    public static boolean DPLL_Satisfiable(KB kb) {
        HashSet<Clause> clauses = new HashSet<>();

        for (Sentence s : kb.sentences()) {
            for (Clause c : CNFConverter.convert(s)) {
                clauses.add(c);
            }
        }

        return DPLL(clauses, kb.symbols(), new GenModel());

    }

    public static boolean DPLL(Set<Clause> clauses, Collection<Symbol> symbols, Model model) {

        boolean allTrue = true;
        for (Clause c : clauses) {
            Boolean satisfied = c.isSatisfiedBy(model);
            if (satisfied == null) {
                allTrue = false;
                break;
            }
            if (!satisfied) {
                return false;
            }

            Object pureSymArr = getPureSymbol(clauses, model);

        }
        if (allTrue) return true;



    }

    public static Object[] getPureSymbol(Set<Clause> clauses, Model model) {

        HashSet<Clause> trimmedClauses = new HashSet<>();
        for (Clause c : clauses) {
            Boolean satisfied = c.isSatisfiedBy(model);
            if (satisfied == null) continue;
            trimmedClauses.add(c);
        }

        LinkedHashMap<Symbol, Literal.Polarity> possiblePureSymbols = new LinkedHashMap<>();
        HashSet<Symbol> definitelyNotPureSymbols = new HashSet<>();



        for (Clause c : trimmedClauses) {
            for (Literal l : c) {
                Symbol currContent = l.getContent();
                Literal.Polarity currPolarity = l.getPolarity();

                if (!definitelyNotPureSymbols.contains(currContent) && !possiblePureSymbols.containsKey(currContent)) {
                    possiblePureSymbols.put(currContent, currPolarity);

                    continue;
                }

                if (possiblePureSymbols.containsKey(currContent) && possiblePureSymbols.get(currPolarity) != currPolarity) {
                    possiblePureSymbols.remove(currContent);
                    definitelyNotPureSymbols.add(currContent);

                }
            }
        }

        if (possiblePureSymbols.size() == 0) return null;
        Object[] retrArr = new Object[2];
        retrArr[0] = possiblePureSymbols.keySet().iterator().next();
        retrArr[1] = possiblePureSymbols.get(retrArr[0]);

        return retrArr;

    }
}

