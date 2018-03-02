package pl;

import pl.core.KB;
import pl.core.Model;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.util.ArraySet;

public class WumpusModel implements Model{
    ArraySet<Symbol> symbols = new ArraySet<>();
    Boolean[] booleans = new Boolean[symbols.size()];//indexes correspond to those in symbols set
    KB kb;

    public WumpusModel(KB kb){//possible world of wumpus world
        symbols = (ArraySet<Symbol>)kb.symbols();
        this.kb = kb;
    }
    public WumpusModel(){}

    @Override
    public void set(Symbol sym, boolean value) {

        booleans[symbols.indexOf(sym)] = value;

    }

    @Override
    public boolean get(Symbol sym) {
        return booleans[symbols.indexOf(sym)];
    }


    /**
     * for each sentence in kb, does the model produce a true value
     * for that sentence?
     */
    @Override
    public boolean satisfies(KB kb) {
        for (Sentence s : kb.sentences()){
            if (!s.isSatisfiedBy(this))
                return false;
        }
        return false;
    }

    @Override
    public boolean satisfies(Sentence sentence) {
        return sentence.isSatisfiedBy(this);
    }

    @Override
    public void dump() {
        System.out.println("Symbols: ");
        for (Symbol s: symbols)
            System.out.println(s+ " "+booleans[symbols.indexOf(s)]);
    }
}
