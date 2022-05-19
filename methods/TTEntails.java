package methods;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// must be kept to automatically recomplie model object in vscode
import methods.Model;
import knowledgeBase.KnowledgeBase;
import proposition.Sentence;

/**
 * Implementation of the TT entails Algorithm (TT) - as defined in AIMA 4th ed, pages 220-231. 
 * 
 * @author Zoie Tad-y 
 */

 /**
 * a model-checking approach that is a
 * direct implementation of the definition of entailment: enumerate the models, and check that
 * a is true in every model in which kb is true. Models are assignments of true or false to
 * every proposition symbol. (pp 220 AIMA 4th ed)
 * 
 *
 * performs a recursive enumeration of a finite space of assignments to symbols (pp 220-2021 AIMA 4th ed)
 */
public class TTEntails {

    // default contructor for tt entailment
    public TTEntails() {

    }

    // counter for the number of models in whcih a is true where kb is true
    private int numberOfModels;

    /**
     * check if a query is true or not in the context of the knowledgebase
     * 
     * direct adaptation of the truth-table enumeration algorithm as described
     * in figure 7.10 in page 221 of AIMA 4th ed.
     * 
     * general flow of logic
     * recursively assign truth value to a symbol wth respect to a model
     * if the kb is true, also check if alpha is true in the model.
     * 
     * @param kb - knowledgebase
     * @param alpha - alpha or query to check if true in the context of the kb
     * @return truth value of alpha
     */
    public boolean isEntailed(KnowledgeBase kb, Sentence alpha) {
        // a list of the proposition symbols in KB and a
        Set<String> setSymbols = new HashSet<String>(alpha.getSymbols());
        setSymbols.addAll(kb.getSymbols());
        // convert set to symbol, for iterative and splicing use
        List<String> symbols = new ArrayList<String>(setSymbols);
        // initialise counter
        numberOfModels = 0;
        // recurively check all models
        return ttCheckAll(kb, alpha, symbols, new Model());
    }

    /**
     * checks truth value of all models
     * 
     * direct adaptation of the truth-table enumeration algorithm as described
     * in figure 7.10 in page 221 of AIMA 4th ed.
     * 
     * @param kb knowledgbase in conext
     * @param alpha alpha or query in context of kb
     * @param symbols symbols in kb and a, or remaining symbols yet to be assigned a value
     * @param model model
     * @return truth value of model
     */
    public boolean ttCheckAll(KnowledgeBase kb, Sentence alpha, List<String> symbols, Model model) {
        // if there are no symbols left to assign values to
        if (symbols.isEmpty()) {
            // check if kb is true
            if (model.isTrue(kb.asSingleSentence())) {
                // if so
                // increment model counter
                incrementModel();
                // truth value of alpha
                return model.isTrue(alpha);
            } else {
                //  when KB is false, always return true
                return true;
            }
        }

        // first(symbols)
        String p = symbols.get(0);
        // rest(symbols)
        List<String> rest = symbols.subList(1, symbols.size());

        // return (TT-CHECK-ALL(kb,a,rest, model u {P = true}) and TT-CHECK-ALL(kb,a,rest, model u {P = false }))
        return ttCheckAll(kb, alpha, rest, model.union(p, true)) && ttCheckAll(kb, alpha, rest, model.union(p, false));
    }

    // getter for the number of models where KB is ture and alpha is true
    public int getNumberOfModels() {
        return this.numberOfModels;
    }

    // increment method for the number of models
    private void incrementModel() {
        this.numberOfModels++;
    }
}
