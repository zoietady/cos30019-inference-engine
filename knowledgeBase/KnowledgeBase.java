package knowledgeBase;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import proposition.ComplexSentence;
import proposition.Sentence;

/**
 * Implementation of a knowledge base data structure based on the description of the knowledge base in pages 209-210 of AIMA 4th ed.
 * 
 * i.e. 
 * ...a set of sentences (Section 7.1, page 209, AIMA 4th ed)
 * ...There must be a way to add new sentences to the knowledge base and a way to query what is known (Section 7.1, page 209, AIMA 4th ed)
 * 
 * @author Zoie Tad-y 
 */
public class KnowledgeBase {
    // set of sentences
    private ArrayList<Sentence> sentences;
    // summary of symbols
    // aids different methods of inference
    private Set<String> symbols;

    /**
     * contructor for kb
     * 
     * @param sentences sentences in the kb
     */
    public KnowledgeBase(ArrayList<Sentence> sentences) {
        this.sentences = sentences;
        this.symbols = gatherSymbols();
    }

    /**
     * equivalent to the Tell operation described in Section 7.1, page 209, AIMA 4th ed
     * 
     * way of adding new sentences
     * @param sentence sentence to add
     * @return true if succesffully added, false otherwise
     */
    public boolean addSentence(Sentence sentence){
        // add sentence to list of sentences
        boolean result = this.sentences.add(sentence);
        // update summarly of symbols
        this.symbols.addAll(sentence.getSymbols());
        // return result of insertion
        return result;
    }

    /**
     * equivalent to the Tell operation for multiple sentences described in Section 7.1, page 209, AIMA 4th ed
     * 
     * way to add add multiple sentences 
     * @param sentences sentence to add
     * @return true if succesffully added, false otherwise
     */
    public boolean addSentences(ArrayList<Sentence> sentences){
        boolean result = this.sentences.addAll(sentences);
        this.symbols = gatherSymbols();
        return result;
    }

    /**
     * method for gathering symbols from the set of sentences
     * @return
     */
    public Set<String> gatherSymbols(){
        // initialise set of symbols
        Set<String> symbols =  new HashSet<String>();

        // for each sentence
        for (Sentence sentence : this.sentences) {
            // get the symbols in it to the set of symbols
            symbols.addAll(sentence.getSymbols());
        }
        // return updated set of symbols
        return symbols;
    }

    /**
     * prints all the symbols in the KB
     * 
     * was used for debugging
     */
    public void listSymbols(){
        System.out.println("Propositional Symbols");
        for (String symbol : symbols) {
            System.out.println(symbol);
        }
    }

    /**
     * prints all the symbols in the sentence
     * 
     * was used for debugging
     */
    public void listSentences(){
        System.out.println("Sentences");
        for (Sentence sentence : sentences) {
            System.out.println(sentence.toString());
        }
    }

    /**
     * getter for symbols
     * @return symbols in kb
     */ 
    public Set<String> getSymbols(){
        return this.symbols;
    }

    /**
     * getter for sentences
     * @return sentences in kb
     */ 
    public ArrayList<Sentence> getSentences(){
        return this.sentences;
    }


    /**
     * used for truth table enumertion algorithm 
     * constructs the knowledgbase as a single sentence, connected using "&".
     * 
     * e.g. kb = {a, b&c, e=>a} is converted to sentence = ((a & (b&c)) & (e=>a))
     * @return single sentence of kb
     */
    public Sentence asSingleSentence() {
        Sentence kbInSingleSentence;
        Queue<Sentence> sentencesQueue = new LinkedList<Sentence>(sentences);
        Sentence s;
        // foreach sentence in set of sentences in kb
        kbInSingleSentence = sentencesQueue.poll();
        while(!sentencesQueue.isEmpty()){
            s = sentencesQueue.poll();
            // create a new sentence out of it
            kbInSingleSentence = new ComplexSentence(s, "&", kbInSingleSentence);

        }
        // return single sentence of kb
        return kbInSingleSentence;
    }
}
