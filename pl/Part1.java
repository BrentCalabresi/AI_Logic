package pl;

import pl.core.KB;
import pl.core.Negation;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.examples.*;

import java.util.ArrayList;
import java.util.LinkedList;
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

        LiarsTruthTellersKB liars = new LiarsTruthTellersKB();
        LiarsTruthTellersKB liarsB = new LiarsTruthTellersKB(2);
        Symbol amy = liars.Amy;
        Symbol bob = liars.Bob;
        Symbol cal = liars.Cal;

        System.out.println("----- Part 1 -----");
        System.out.println("MODUS PONENS:");
        System.out.println("Does the KB entail the proposition \"If P && Q, then P implies Q\": "+Entails(MPKB, q));
        System.out.println("\nWUMPUS WORLD:");
        System.out.println("Does the KB entail the proposition \"There is a pit in 1,2\": "+Entails(kb1, p12));
        System.out.println("\nHORN CLAUSES:");
        System.out.println("Does the KB entail the proposition \"mythical?\": "+Entails(unicorns,mythical));
        System.out.println("Does the KB entail the proposition \"magical?\": "+Entails(unicorns,magical));
        System.out.println("Does the KB entail the proposition \"horned?\": "+Entails(unicorns,horned));
        System.out.println("\nLIARS AND TRUTH-TELLERS (a):");
        System.out.println("Does the KB entail the proposition \"Amy?\": "+Entails(liars,amy));
        System.out.println("Does the KB entail the proposition \"Bob?\": "+Entails(liars,bob));
        System.out.println("Does the KB entail the proposition \"Cal?\": "+Entails(liars,cal));
        amy = liarsB.Amy;
        bob = liarsB.Bob;
        cal = liarsB.Cal;
        System.out.println("\nLIARS AND TRUTH-TELLERS (b):");
        System.out.println("Does the KB entail the proposition \"Amy?\": " + Entails(liarsB, amy));
        System.out.println("Does the KB entail the proposition \"Bob?\": " + Entails(liarsB, bob));
        System.out.println("Does the KB entail the proposition \"Cal?\": " + Entails(liarsB, cal));

        System.out.println("\nMORE LIARS:");
        MoreLiarsKB mlkb = new MoreLiarsKB();
        LinkedList<Symbol> liarsList = new LinkedList<>();
        LinkedList<Symbol> possibleTruthTellers = new LinkedList<>();
        for (Symbol l : mlkb.getSymbols()) {
            MoreLiarsKB newmlkb = new MoreLiarsKB();
            if (Entails(newmlkb, new Negation(l))) liarsList.add(l);
            else possibleTruthTellers.add(l);
        }

        LinkedList<Symbol> truthTellers = new LinkedList<>();
        LinkedList<Symbol> undecidable = new LinkedList<>();
        for (Symbol s : possibleTruthTellers) {
            MoreLiarsKB newmlkb = new MoreLiarsKB();
            if (Entails(newmlkb, s)) truthTellers.add(s);
            else undecidable.add(s);

        }

        System.out.println("The KB entails the negation of: " + liarsList + " (ie, these are liars)");
        System.out.println("The KB entails the following: " + truthTellers + " (ie, these are truth-tellers)");
        System.out.println("\n\n");
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
               // System.out.println("model satisfies kb, returning model satisfies apha: "+model.satisfies(alpha));
                return model.satisfies(alpha);
            } else {
                //System.out.println("no more cases... return true");
                return true;
            }
        }
        Symbol p = symbols.get(0);
        //List<Symbol> rest = rest(symbols,1);
        List<Symbol> rest = symbols.subList(1,symbols.size());
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
    @Deprecated
    public static List rest(List l,int start){
        if (l.size() ==0 || l.size() ==1){
            //System.out.println("empty");
            l.remove(0);
            return new ArrayList();
        }

        return l.subList(start,l.size());
    }
}
