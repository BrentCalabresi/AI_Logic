package pl.examples;

import pl.core.*;

public class LiarsTruthTellersKB extends KB{
    public Symbol Amy;
    public Symbol Bob;
    public Symbol Cal;
    //public Symbol A;
    //public Symbol B;
    //public Symbol C;

    public LiarsTruthTellersKB() {
        super();
        Amy = intern("Amy");
        Bob = intern("Bob");
        Cal = intern("Cal");

        //A = intern("A");
        //B = intern("B");
        //C = intern("C");

        //add(new Biconditional(Amy, new Conjunction(Amy,Cal)));
        //add(new Biconditional(Bob, new Negation(Cal)));
        //add(new Biconditional(Cal, new Disjunction(Bob, new Negation(Amy))));
        //add(new Biconditional(new Negation(Amy), new Disjunction(new Negation(Amy), new Negation(Cal))));
        //add(new Biconditional(new Negation(Bob), Cal));
        //add(new Biconditional(new Negation(Cal), new Conjunction(new Negation(Bob), Amy)));


        add(new Disjunction((new Implication(Amy, new Conjunction(Amy,Cal))), new Implication(new Negation(Amy), new Disjunction(Amy, new Negation(Amy)))));
        add(new Disjunction(new Implication(Bob, new Negation(Cal)), new Implication(new Negation(Bob), Cal)));
        add(new Disjunction(new Implication(Cal, new Disjunction(Bob, new Negation(Amy))), new Implication(new Negation(Cal), new Conjunction(new Negation(Bob), Amy))));

        //add(A);
        //add(B);
        //add(C);
    }
}

