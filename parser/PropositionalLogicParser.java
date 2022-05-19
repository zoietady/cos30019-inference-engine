package parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import proposition.ComplexSentence;
import proposition.Sentence;
import proposition.SimpleSentence;


/**
 * Implementation of a simple (limited) propositional logic parser that reads from a text file that assumes the structure and format as specified in the assignment brief
 * 
 * @author Zoie Tad-y 
 */
public class PropositionalLogicParser {

    // file to read from
    private File file;
    // mapp of string to symbols
    private Map<String, SimpleSentence> symbols;
    // list of sentences
    private ArrayList<Sentence> sentences; 
    // query sentence
    private Sentence query;

    /**
     * constructor of PropositionalLogicParser
     * 
     * @param filePath filepath of file to parse
     */
    public PropositionalLogicParser(String filePath) {
        // initialise file and containers
        this.file = new File(filePath);
        this.symbols = new HashMap<String, SimpleSentence>();
        this.sentences = new ArrayList<Sentence>();

        // try to read sentences from file
        try {
            this.ReadSentenceFromFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // read from file
    private void ReadSentenceFromFile() throws FileNotFoundException {

        try (Scanner reader = new Scanner(file)) {
            // parser using the reader
            ParseSentences(reader);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }   

    public ArrayList<Sentence> getSentences() {
        return sentences;
    }

    

    public Sentence getQuery() {
        return query;
    }


    /**
     * line parser of the file
     * 
     * @param reader reader of the file being read
     */
    private void ParseSentences(Scanner reader){
        String tell, sentences, ask, query;

        // read tell
        tell = reader.nextLine();

        // read the line of sentences
        sentences = reader.nextLine();
        // collect the symbols to initialise the map of string to sentences
        this.CollectSymbols(sentences);
        // create the list of sentences
        this.sentences = this.CreateListOfSentences(sentences);

        // read ask
        ask = reader.nextLine();

        // read the query
        query = reader.nextLine();
        // create the query
        this.query = this.CreateQuery(query);
        
    }

    /**
     * create the sentence query, given a string query
     * @param query query string to create a sentece from
     * @return sentence query
     */
    private Sentence CreateQuery(String query){
        // make sure there are no white spaces
        query = query.replaceAll("\\s+","");

        // get sentence from map given query string
        Sentence newQuery = this.symbols.get(query);
        // if it is not in the map of sentences
        if(newQuery == null){
            // create a sentence out of this query
            newQuery = CreateSentence(query);
            // if a sentence cannot be created from the query string
            if(newQuery == null){
                //  just create a simple sentence out of it
                newQuery = new SimpleSentence(query);
            }
        }
        // return sentence query
        return newQuery;
    }

    /**
     * collect all the symbols in the string
     * @param sentences sentence string to collect symbols from
     */
    private void CollectSymbols(String sentences){
        sentences = sentences.replaceAll("\\s+","");

        // convert connectives to ; as separators
        sentences = sentences.replaceAll("=>",";");
        sentences = sentences.replaceAll("&",";");

        // split the string by the ; separators
        String[] symbolsArray = sentences.split(";");

        // for each string symbol in the array, create a simple sentence out of it
        // and add it to the symbol table
        for (String symbol : symbolsArray) {
            this.symbols.put(symbol, new SimpleSentence(symbol));
        }
    }

    /**
     * given a single string of sentences separated by ;
     * @param sentences
     * @return
     */
    private ArrayList<Sentence> CreateListOfSentences(String sentences){
        // ensure there are no white spaces
        sentences = sentences.replaceAll("\\s+","");
        // split by ;
        String[] sentencesArray = sentences.split(";");

        // initialise container of sentences
        ArrayList<Sentence> listOfSentences = new ArrayList<Sentence>();

        // for each sentence string, create a new sentence
        for (String sentenceString : sentencesArray) {
            listOfSentences.add(CreateSentence(sentenceString));
        }

        // return list of sentences
        return listOfSentences;
    }

    /**
     * create a sentence given a sentence string
     * 
     * @param sentence sentence string
     * @return Sentence object made from sentence string
     */
    private Sentence CreateSentence(String sentence){
        // if it is a statement of implication
        if(sentence.contains("=>") || sentence.contains("&")){
            ComplexSentence newComplexSentence;
            // split it by the implication connective
            String[] premiseAndConclusion = sentence.split("=>");
            // if it is a conjunctive connective 
            if(premiseAndConclusion[0].contains("&")){
                // split by connection
                String[] premises = premiseAndConclusion[0].split("&");

                // create a new complex sentence from the conjuctive premise
                newComplexSentence = new ComplexSentence(this.symbols.get(premises[0]), "&", this.symbols.get(premises[1]));
                
                // create another complex sentence composed of the conjuctive premise and the conclusion of the imlication
                ComplexSentence anotherComplexSentence = new ComplexSentence(newComplexSentence, "=>", this.symbols.get(premiseAndConclusion[1]));
                return anotherComplexSentence;
            }else{
                // else create a sentece using the symbols with implication as a connective
                newComplexSentence = new ComplexSentence(this.symbols.get(premiseAndConclusion[0]), "=>", this.symbols.get(premiseAndConclusion[1]));
                return newComplexSentence;
            }
        } else {
            // else it is just a sole symbol
            return this.symbols.get(sentence);
        }
    }

}
