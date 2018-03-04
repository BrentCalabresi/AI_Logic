package pl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import pl.cnf.CNFConverter;
import pl.cnf.Literal;
import pl.core.*;
import pl.cnf.Clause;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

        }
        if (allTrue) return true;



    }

    public static Literal getPureSymbol(Set<Clause> clauses, Collection<Symbol> symbols, Model model) {

        HashSet<Clause> trimmedClauses = new HashSet<>();
        for (Clause c : clauses) {
            Boolean satisfied = c.isSatisfiedBy(model);
            if (satisfied == null) continue;
            trimmedClauses.add(c);
        }

        HashSet<Symbol> possiblePureSymbols = new HashSet<>();
        HashSet<Symbol> definitelyNotPureSymbols = new HashSet<>();

        for (Clause c : trimmedClauses) {
            for (Literal l : c) {
                if (!definitelyNotPureSymbols.contains(l.getContent())) {
                    possiblePureSymbols.add(l.getContent());
                    continue;
                }
            }
        }
    }
}
