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

        add(new Biconditional(Amy, new Conjunction(Amy,Cal)));
        add(new Biconditional(Bob, new Negation(Cal)));
        add(new Biconditional(Cal, new Disjunction(Bob, new Negation(Amy))));

//        add(new Biconditional(Amy,new Negation(Cal)));
//        add(new Biconditional(Bob,new Conjunction(Amy,Cal)));
//        add(new Biconditional(Cal,Bob));
    }
}

