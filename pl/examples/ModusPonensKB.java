package pl.examples;

import pl.core.*;

public class ModusPonensKB extends KB {
	public Symbol q;
	public ModusPonensKB() {
		super();
		Symbol p = intern("P");
		q = intern("Q");
		add(p);
		add(new Implication(p, q));
	}
	
	public static void main(String[] argv) {
		new ModusPonensKB().dump();
	}

}
