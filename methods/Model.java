package methods;
import java.util.HashMap;
import java.util.Map;

import proposition.ComplexSentence;
import proposition.Sentence;
import proposition.SimpleSentence;

/**
 * Model data structure to aide the truth-table enumeration algorithm in AIMA 4th ed, page 231. 
 * This implementation is infered based on the given psuedocode in figure 7.10, specifically focusing
 * on how the model is used and what it knows.
 * 
 * @author Zoie Tad-y 
 */
public class Model {
    // map of truth value assignment
    private Map<String, Boolean> assignments = new HashMap<String, Boolean>();

    // default constructor
    public Model() {
    }

    /**
     * implementation of PL-TRUE
     * @param symbol sentence to evaluate for truth value
     * @return truth value of symbol
     */
    public boolean isTrue(String symbol) {
        return Boolean.TRUE.equals(assignments.get(symbol));
    }

    /**
     * implementation of PL-TRUE
     * @param s sentence to evaluate for truth value
     * @return truth value of setence
     */
    public boolean isTrue(Sentence s) {
        return Boolean.TRUE.equals(evaluate(s));
    }

    /**
     * implementation of  model u {P = truth value}) as per the figure 7.10 of page 221 in AIMA 4th ed
     * 
     * @param symbol symbol to unite with mode
     * @param b truth value assignment of symbol added
     * @return updated model
     */
    public Model union(String symbol, boolean b) {
        // create new mode object
        Model passModel = new Model();

        // copy existing assignments
        passModel.assignments.putAll(this.assignments);
        // assign value to given symbol and add to table of assignments
        passModel.assignments.put(symbol, b);

        // return updated model
        return passModel;
    }

    /**
     * method for evaluating for truth value of a sentence
     * @param s sentence to be evaluated
     * @return truth value
     */
    public boolean evaluate(Sentence s) {
        // if the sentence is a simple sentence (contains a single symbol)
        if (s instanceof SimpleSentence) {
            SimpleSentence ss = (SimpleSentence) s;
            // simply get value assignment
            return assignments.get(ss.getSymbol());
        }

        // if the sentence is a complex sentence
        if (s instanceof ComplexSentence) {
            // evaluate it in context of connectives
            return evaluate((ComplexSentence) s);
        }

        // otherwise it is just false
        return false;
    }

    /**
     * evalue truth value of complex sentence (i.e. sentences with connectives)
     * 
     * @param s complex sentence to evaluate
     * @return
     */
    public boolean evaluate(ComplexSentence s) {
        // decompose complex sentence to simpler sentences
        Boolean firstValue = evaluate(s.getSimplerSentence(0));
        Boolean secondValue = evaluate(s.getSimplerSentence(1));

        // get connective of sentence
        String connective = s.getConnective();

        // if (connective.equals("&")) {
        //     return Boolean.FALSE.equals(firstValue) || Boolean.FALSE.equals(secondValue) ? Boolean.FALSE : Boolean.TRUE;
        // } else if (connective.equals("=>")) {
        //     return Boolean.FALSE.equals(firstValue) || Boolean.TRUE.equals(secondValue) ? Boolean.TRUE : Boolean.FALSE;
        // }

        // evaluate in context of different logical connectives or operators
        if (connective.equals("&")) {
            // F as long as one of the values is false, otherwise T
			return Boolean.FALSE.equals(firstValue) || Boolean.FALSE.equals(secondValue) ? Boolean.FALSE :  Boolean.TRUE;
		} else if (connective.equals("||")) {
            // T as long as one of the values is true, otherwise F
			return Boolean.TRUE.equals(firstValue) || Boolean.TRUE.equals(secondValue) ? Boolean.TRUE : Boolean.FALSE;
		} else if (connective.equals("=>")) {
            // T as long as the first value is true or the second value is false, otherwise (T=>F) F
			return Boolean.FALSE.equals(firstValue) || Boolean.TRUE.equals(secondValue) ? Boolean.TRUE : Boolean.FALSE;
		} else if (connective.equals("<==>")) {
            // T as long as the both values are the same
			return firstValue.equals(secondValue);
		}

        // otherwise false
        return false;
    }
}
