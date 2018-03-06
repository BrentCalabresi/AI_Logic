package pl;

import pl.core.KB;
import pl.core.Model;
import pl.core.Sentence;
import pl.core.Symbol;

import java.util.HashMap;

public class GenModel implements Model{
    private HashMap<Symbol,Boolean> assertions = new HashMap<Symbol, Boolean>();//sentences about the world
    KB kb;

    //TODO
    public GenModel(KB kb){
        System.out.println("this constructor is unfinished");
//        this.assertions =
//        this.symbols = (ArraySet<Symbol>)kb.symbols();
//        this.kb = kb;
    }

    public GenModel(GenModel w) {
        for (Symbol s : w.getAssertions().keySet())
            this.assertions.put(s,w.getAssertions().get(s));



        kb = w.getKb();
    }

    public GenModel(){}

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
    public Boolean get(Symbol sym) {
        if (assertions.containsKey(sym)) {
            return assertions.get(sym);
        }
        else return null;
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
            //System.out.println(s);
//            System.out.println(this.get((Symbol) s));
            //this.dump();
            //System.out.println(s);
            if (!s.isSatisfiedBy(this)){
                //System.out.println("Model: ");
                //this.dump();
                //System.out.println("not satisfying "+s);
                return false;
            }

        }
        return true;
    }

    @Override
    public boolean satisfies(Sentence sentence) {
        return sentence.isSatisfiedBy(this);
    }

    @Override
    public void dump() {
        //System.out.println("Hashtable of assertions");
        for (Symbol s: assertions.keySet()){
            System.out.print("(Sentence: "+s+" is "+assertions.get(s)+")  ");
        }
        System.out.println();
    }

    @Deprecated
    public GenModel duplicate(){
        GenModel newModel = new GenModel();
        newModel.assertions = new HashMap<>(this.assertions);

        return newModel;
    }

    public GenModel union(Symbol symbol, boolean b) {
        GenModel m = new GenModel(this);//this.duplicate();
        m.addSymbol(symbol,b);
        System.out.println("adding: "+symbol);
        //m.dump();
        return m;
    }

}
