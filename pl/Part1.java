package pl;

import pl.core.KB;
import pl.core.Negation;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.examples.*;

import java.util.ArrayList;
import java.util.List;


public class Part1 {

    public Part1(){}

    public void run() {
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

        LiarsTruthTellersKB liarsA = new LiarsTruthTellersKB(1);
        LiarsTruthTellersKB liarsB = new LiarsTruthTellersKB(2);
        Symbol amy = liarsA.Amy;
        Symbol bob = liarsA.Bob;
        Symbol cal = liarsA.Cal;

        System.out.println("----- Part 1 -----");
        System.out.println("Does the KB entail the proposition \"There is a pit in 1,2\": " + Entails(kb1, p12));
        System.out.println("Does the KB entail the proposition \"If P && Q, then P implies Q\": " + Entails(MPKB, q));
        System.out.println("Does the KB entail the proposition \"mythical?\": " + Entails(unicorns, mythical));
        System.out.println("Does the KB entail the proposition \"magical?\": " + Entails(unicorns, magical));
        System.out.println("Does the KB entail the proposition \"horned?\": " + Entails(unicorns, horned));
        System.out.println("Liars and Truth tellers part A");
        System.out.println("Does the KB entail the proposition \"Amy?\": " + Entails(liarsA, amy));
        System.out.println("Does the KB entail the proposition \"Bob?\": " + Entails(liarsA, bob));
        System.out.println("Does the KB entail the proposition \"Cal?\": " + Entails(liarsA, cal));
        System.out.println("Part B");
        amy = liarsB.Amy;
        bob = liarsB.Bob;
        cal = liarsB.Cal;
        System.out.println("Does the KB entail the proposition \"Amy?\": " + Entails(liarsB, amy));
        System.out.println("Does the KB entail the proposition \"Bob?\": " + Entails(liarsB, bob));
        System.out.println("Does the KB entail the proposition \"Cal?\": " + Entails(liarsB, cal));
        System.out.println("\n"+"More liars and truth tellers");
        MoreLiarsKB moreLiarsKB = new MoreLiarsKB();
         Symbol Amy = moreLiarsKB.Amy;
         Symbol Bob = moreLiarsKB.Bob;
         Symbol Cal = moreLiarsKB.Cal;
         Symbol Dee = moreLiarsKB.Dee;
         Symbol Eli = moreLiarsKB.Eli;
         Symbol Fay = moreLiarsKB.Fay;
         Symbol Gil = moreLiarsKB.Gil;
         Symbol Hal = moreLiarsKB.Hal;
         Symbol Ida = moreLiarsKB.Ida;
         Symbol Jay = moreLiarsKB.Jay;
         Symbol Kay = moreLiarsKB.Kay;
         Symbol Lee = moreLiarsKB.Lee;

        System.out.println("Who's telling the truth?:");
        System.out.println("Amy: "+Entails(moreLiarsKB,Amy));
        System.out.println("Bob: "+Entails(moreLiarsKB,Bob));
        System.out.println("Cal: "+Entails(moreLiarsKB,Cal));
        System.out.println("Dee: "+Entails(moreLiarsKB,Dee));
        System.out.println("Eli: "+Entails(moreLiarsKB,Eli));
        System.out.println("Fay: "+Entails(moreLiarsKB,Fay));
        System.out.println("Gil: "+Entails(moreLiarsKB,Gil));
        System.out.println("Hal: "+Entails(moreLiarsKB,Hal));
        System.out.println("Ida: "+Entails(moreLiarsKB,Ida));
        System.out.println("Jay: "+Entails(moreLiarsKB,Jay));
        System.out.println("Kay: "+Entails(moreLiarsKB,Kay));
        System.out.println("Lee: "+Entails(moreLiarsKB,Lee));
        
    }
    public static boolean Entails(KB kb, Sentence alpha) {
        List<Symbol> symbols = new ArrayList<>(kb.symbols());

        return CheckAll(kb, alpha, symbols, new GenModel());
    }


    public static boolean CheckAll(KB kb, Sentence alpha, List<Symbol> symbols, GenModel model) {
        if (symbols.isEmpty()) {
            if (model.satisfies(kb)) {
                return model.satisfies(alpha);
            } else {
                return true;
            }
        }
        Symbol p = symbols.get(0);
        List<Symbol> rest = symbols.subList(1,symbols.size());

        model.set(p,true);
        boolean t = CheckAll(kb,alpha,rest,model);
        model.set(p,false);
        boolean f = CheckAll(kb,alpha,rest,model);

        return t && f;

    }

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
