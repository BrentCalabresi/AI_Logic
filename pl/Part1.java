package pl;

import pl.core.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 uses existing file WumpusWorldKB.java

 */
public class Part1 {

    public static void main(String[] args) {

    }

    boolean enumerateTruthTable(KB kb, Sentence alpha){

        if (kb.symbols().isEmpty()){
            if ()
        }

        return false;
    }


    public boolean Entails(KB kb, Sentence alpha) {
        List<Symbol> symbols = (List)kb.symbols();

        return CheckAll(kb, alpha, symbols, new WumpusModel());
    }

    public boolean CheckAll(KB kb, Sentence alpha, List<Symbol> symbols, Model model) {

        if (symbols.isEmpty()) {
            if (model.satisfies(kb)) {
                return model.satisfies(alpha);
            } else {
                return true;
            }
        }

        Symbol p = symbols.get(0);
        List<Symbol> rest = Util.rest(symbols);

        return CheckAll(kb, alpha, rest, model.union(p, true))
                && CheckAll(kb, alpha, rest, model.union(p, false));
    }


}
