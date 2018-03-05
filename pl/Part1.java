package pl;

import pl.cnf.Literal;
import pl.core.*;
import pl.examples.HornClauseKB;
import pl.examples.ModusPonensKB;
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
        Negation notPit = new Negation(kb1.p12);

        ModusPonensKB MPKB = new ModusPonensKB();
        Symbol q = MPKB.q;
        Negation nq = new Negation(q);

        HornClauseKB unicorns = new HornClauseKB();
        Symbol mythical = unicorns.mythical;
        Symbol magical = unicorns.magical;
        Symbol horned = unicorns.horned;
        Negation notMythical = new Negation(unicorns.mythical);
        Negation notMagical = new Negation(unicorns.magical);
        Negation notHorned = new Negation(unicorns.horned);


        System.out.println("Does the KB entail the proposition \"There is a pit in 1,2\": "+Entails(kb1, p12));
        System.out.println("Does the KB entail the proposition \"If P && Q, then P implies Q\": "+Entails(MPKB, q));
        System.out.println("Does the KB entail the proposition \"mythical?\": "+Entails(unicorns,mythical));
        System.out.println("Does the KB entail the proposition \"magical?\": "+Entails(unicorns,magical));
        System.out.println("Does the KB entail the proposition \"horned?\": "+Entails(unicorns,horned));

    }


    public static boolean Entails(KB kb, Sentence alpha) {
        List<Symbol> symbols = new ArrayList<>(kb.symbols());

        return CheckAll(kb, alpha, symbols, new GenModel());
    }


    public static boolean CheckAll(KB kb, Sentence alpha, List<Symbol> symbols, GenModel model) {
        //model.dump();
        if (symbols.isEmpty()) {
            //System.out.println("does the current model satisfy the knowledge base?: "+model.satisfies(kb));
            //model.dump();
            if (model.satisfies(kb)) {
                //System.out.println("model satisfies kb, returning model satisfies apha: "+model.satisfies(alpha));
                return model.satisfies(alpha);
            } else {
                //System.out.println("no more cases... return true");
                return true;
            }
        }
        Symbol p = symbols.get(0);
        List<Symbol> rest = rest(symbols,1);
        //System.out.println("REST: " +rest);


        model.set(p,true);
        boolean t = CheckAll(kb,alpha,rest,model);
        model.set(p,false);
        boolean f = CheckAll(kb,alpha,rest,model);

        return t && f;

//        return CheckAll(kb, alpha, rest, model.union(p,true))
//                && CheckAll(kb, alpha, rest, model.union(p,false));

    }

    /**
     *
     * helper function to return sublists.
     * Necessary for some cases of lists
     */
    public static List rest(List l,int start){
        if (l.size() ==0 || l.size() ==1){
            //System.out.println("empty");
            l.remove(0);
            return new ArrayList();
        }


        return l.subList(start,l.size());
    }
}
