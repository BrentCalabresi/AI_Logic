package pl.examples;

import pl.core.*;

public class MoreLiarsKB extends KB {
    public Symbol Amy;
    public Symbol Bob;
    public Symbol Cal;
    public Symbol Dee;
    public Symbol Eli;
    public Symbol Fay;
    public Symbol Gil;
    public Symbol Hal;
    public Symbol Ida;
    public Symbol Jay;
    public Symbol Kay;
    public Symbol Lee;

    public MoreLiarsKB(){
        super();
        Amy = intern("Amy");
        Bob = intern("Bob");
        Cal = intern("Cal");
        Dee = intern("Dee");
        Eli = intern("Eli");
        Fay = intern("Fay");
        Gil = intern("Gil");
        Hal = intern("Hal");
        Ida = intern("Ida");
        Jay = intern("Jay");
        Kay = intern("Kay");
        Lee = intern("Lee");


        add(new Biconditional(Amy, new Conjunction(Hal,Ida)));
        add(new Biconditional(Bob, new Conjunction(Amy,Lee)));
        add(new Biconditional(Cal, new Conjunction(Bob,Gil)));
        add(new Biconditional(Dee, new Conjunction(Eli,Lee)));
        add(new Biconditional(Eli, new Conjunction(Cal,Hal)));
        add(new Biconditional(Fay, new Conjunction(Dee,Ida)));
        add(new Biconditional(Gil, new Conjunction(new Negation(Eli), new Negation(Jay))));
        add(new Biconditional(Hal, new Conjunction(new Negation(Fay), new Negation(Kay))));
        add(new Biconditional(Ida, new Conjunction(new Negation(Gil), new Negation(Kay))));
        add(new Biconditional(Jay, new Conjunction(new Negation(Amy), new Negation(Cal))));
        add(new Biconditional(Kay, new Conjunction(new Negation(Dee), new Negation(Fay))));
        add(new Biconditional(Lee, new Conjunction(new Negation(Bob), new Negation(Jay))));
    }
}

