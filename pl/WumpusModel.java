package pl;

import pl.core.KB;
import pl.core.Model;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.cnf.ArraySet;

import java.util.ArrayList;
import java.util.HashMap;

public class WumpusModel implements Model{
    private HashMap<Symbol,Boolean> assertions = new HashMap<Symbol, Boolean>();//sentences about the world
    KB kb;

    //TODO
    public WumpusModel(KB kb){//possible world of wumpus world
        System.out.println("this constructor is unfinished");
//        this.assertions =
//        this.symbols = (ArraySet<Symbol>)kb.symbols();
//        this.kb = kb;
    }

    public WumpusModel(WumpusModel w) {
        this.assertions = w.getAssertions();
        kb = w.getKb();
    }

    public WumpusModel(){}

    @Override
    public void set(Symbol sym, boolean value) {

        assertions.remove(sym);
        assertions.put(sym,value);

    }

    public void addSymbol(Symbol s, boolean b){
        if (assertions.containsValue(s)){
            System.out.println("Object is already in model");
        }
        else{
            assertions.put(s,b);
            //System.out.println(assertions.size());
        }

    }

    public HashMap<Symbol, Boolean> getAssertions(){
        return this.assertions;
    }

    @Override
    public boolean get(Symbol sym) {
        return assertions.get(sym);
    }

    public KB getKb() {
        return this.kb;
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
        return true;
    }

    @Override
    public boolean satisfies(Sentence sentence) {
        return sentence.isSatisfiedBy(this);
    }

    @Override
    public void dump() {
        for (Symbol s: assertions.keySet()){
            System.out.print("(Symbol: "+s+" boolean: "+assertions.get(s)+")  ");
        }
        System.out.println();
    }

    //TODO
    public WumpusModel duplicate(){
        WumpusModel newModel = new WumpusModel();
        newModel.assertions = new HashMap<>(this.assertions);

        return newModel;
    }

    //TODO
    public Model union(Symbol symbol, boolean b) {
        WumpusModel m = new WumpusModel(this);//this.duplicate();
        m.addSymbol(symbol,b);

        //m.dump();
        return m;
    }

}
