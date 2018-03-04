package pl;

import pl.cnf.Literal;
import pl.core.KB;
import pl.core.Model;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.examples.WumpusWorldKB;

import java.util.ArrayList;
import java.util.List;

/**
 uses existing file WumpusWorldKB.java

 */
public class Part1 {

    public static void main(String[] args) {
        WumpusWorldKB kb1 = new WumpusWorldKB();
        Symbol p12 = kb1.p12;

        Literal l = new Literal(p12);
        System.out.println(Entails(kb1,p12));
    }


    public static boolean Entails(KB kb, Sentence alpha) {
        List<Symbol> symbols = new ArrayList<>(kb.symbols());

        return CheckAll(kb, alpha, symbols, new WumpusModel());
    }


    public static boolean CheckAll(KB kb, Sentence alpha, List<Symbol> symbols, Model model) {

        if (symbols.isEmpty()) {
            if (model.satisfies(kb)) {
                return model.satisfies(alpha);
            } else {
                return true;
            }
        }
        Symbol p = symbols.get(0);
        List<Symbol> rest = symbols.subList(1, symbols.size()-1);

        WumpusModel newModel = new WumpusModel();

        return CheckAll(kb, alpha, rest, newModel.union(p,true))
                && CheckAll(kb, alpha, rest, newModel.union(p,false));

    }
}
