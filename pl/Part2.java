package pl;

import pl.cnf.CNFConverter;
import pl.core.KB;
import pl.cnf.Clause;
import pl.core.Model;
import pl.core.Sentence;
import pl.core.SymbolTable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part2 {

    public static void main(String[] args) {

    }

    public static void DPLL_Satisfiable(KB kb) {
        HashSet<Clause> clauses = new HashSet<>();

        for (Sentence s : kb.sentences()) {
            for (Clause c : CNFConverter.convert(s)) {
                clauses.add(c);
            }
        }

        return DPLL(clauses, kb.symbols(), new WumpusModel());

    }

    public static boolean DPLL(Set<Clause> clauses, Collection<Clause> symbols, Model model) {

    }
}
