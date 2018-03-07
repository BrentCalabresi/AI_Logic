package pl.examples;

import pl.core.*;

public class LiarsTruthTellersKB extends KB{
    public Symbol Amy;
    public Symbol Bob;
    public Symbol Cal;

    public LiarsTruthTellersKB(int part) {
        super();
        Amy = intern("Amy");
        Bob = intern("Bob");
        Cal = intern("Cal");

        if(part ==1){
            add(new Biconditional(Amy, new Conjunction(Amy,Cal)));
            add(new Biconditional(Bob, new Negation(Cal)));
            add(new Biconditional(Cal, new Disjunction(Bob, new Negation(Amy))));
        }
        else{
            add(new Biconditional(Amy,new Negation(Cal)));
            add(new Biconditional(Bob,new Conjunction(Amy,Cal)));
            add(new Biconditional(Cal,Bob));
        }

    }

    public LiarsTruthTellersKB() {
        this(1);
    }
}

