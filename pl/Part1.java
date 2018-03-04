package pl;

import pl.cnf.Literal;
import pl.core.*;
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
        Negation n  = new Negation(p12);

        System.out.println("Does the KB entail the proposition \"There is a pit in 1,2\": "+Entails(kb1,p12));
    }


    public static boolean Entails(KB kb, Sentence alpha) {
        List<Symbol> symbols = new ArrayList<>(kb.symbols());

        return CheckAll(kb, alpha, symbols, new GenModel());
    }


    public static boolean CheckAll(KB kb, Sentence alpha, List<Symbol> symbols, Model model) {

        if (symbols.isEmpty()) {
            System.out.println(model.satisfies(kb));
            if (model.satisfies(kb)) {
                System.out.println("DONE------------------------------------------------------");
                return model.satisfies(alpha);
            } else {
                return true;
            }
        }
        Symbol p = symbols.get(0);
        List<Symbol> rest = rest(symbols,1);
        System.out.println("REST: " +rest);


        return CheckAll(kb, alpha, rest, model.union(p,true))
                && CheckAll(kb, alpha, rest, model.union(p,false));

    }

    /**
     *
     * helper function to return sublists.
     * Necessary for some cases of lists
     */
    public static List rest(List l,int start){
        if (l.size() ==0 || l.size() ==1){
            System.out.println("empty");
            l.remove(0);
            return new ArrayList();
        }


        return l.subList(start,l.size());
    }
}
