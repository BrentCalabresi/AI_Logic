package pl.examples;

import pl.core.*;

public class HornClauseKB extends KB{
        public Symbol mythical;
        public Symbol magical;
        public Symbol horned;
        public Symbol immortal;
        public Symbol mammal;

        public HornClauseKB() {
            super();
            mythical = intern("Mythical");
            magical = intern("Magical");
            horned = intern("Horned");
            immortal = intern("Immortal");
            mammal = intern("Mammal");


            add(new Implication(mythical, immortal));
            add(new Implication(new Negation(mythical),mammal));
            add(new Implication(horned,magical));
            add(new Implication(new Disjunction(immortal,mammal),horned));
        }



}
