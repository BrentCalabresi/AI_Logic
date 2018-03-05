package pl.examples;

import pl.core.Biconditional;
import pl.core.Disjunction;
import pl.core.KB;
import pl.core.Negation;
import pl.core.Symbol;

public class WumpusWorldKB extends KB {
	public Symbol p12;

	public WumpusWorldKB() {
		super();
		Symbol p11 = intern("P1,1");
		p12 = intern("P1,2");
		Symbol p21 = intern("P2,1");
		Symbol p22 = intern("P2,2");
		Symbol p31 = intern("P3,1");
		Symbol b11 = intern("B1,1");
		Symbol b21 = intern("B2,1");

		add(new Negation(p11));
		add(new Biconditional(b11, new Disjunction(p12, p21)));
		add(new Biconditional(b21, new Disjunction(p11, new Disjunction(p22, p31))));
		add(new Negation(b11));
		add(b21);
	}


	public static void main(String[] argv) {
		new WumpusWorldKB().dump();
	}

}
