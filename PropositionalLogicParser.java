import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PropositionalLogicParser {

    private File file;
    private Map<String, SimpleSentence> symbols;
    private ArrayList<Sentence> sentences; 
    private Sentence query;

    public PropositionalLogicParser(String filePath) {
        this.file = new File(filePath);
        this.symbols = new HashMap<String, SimpleSentence>();
        this.sentences = new ArrayList<Sentence>();

        try {
            this.ReadSentenceFromFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void ReadSentenceFromFile() throws FileNotFoundException {

        try (Scanner reader = new Scanner(file)) {
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


    private void ParseSentences(Scanner reader){
        String tell, sentences, ask, query;

        tell = reader.nextLine();

        sentences = reader.nextLine();
        this.CollectSymbols(sentences);
        this.sentences = this.CreateListOfSentences(sentences);

        ask = reader.nextLine();

        query = reader.nextLine();
        this.query = this.CreateQuery(query);
        
    }

    private Sentence CreateQuery(String query){
        Sentence newQuery = this.symbols.get(query);
        if(newQuery == null){
            newQuery = CreateSentence(query);
            if(newQuery == null){
                query = query.replaceAll("\\s+","");
                newQuery = new SimpleSentence(query);
            }
        }
        return newQuery;
    }

    private void CollectSymbols(String sentences){
        sentences = sentences.replaceAll("\\s+","");

        sentences = sentences.replaceAll("=>",";");
        sentences = sentences.replaceAll("&",";");

        String[] symbolsArray = sentences.split(";");

        for (String symbol : symbolsArray) {
            this.symbols.put(symbol, new SimpleSentence(symbol));
        }
    }

    private ArrayList<Sentence> CreateListOfSentences(String sentences){
        sentences = sentences.replaceAll("\\s+","");
        String[] sentencesArray = sentences.split(";");

        ArrayList<Sentence> listOfSentences = new ArrayList<Sentence>();

        for (String sentenceString : sentencesArray) {
            listOfSentences.add(CreateSentence(sentenceString));
        }

        return listOfSentences;
    }

    private Sentence CreateSentence(String sentence){
        if(sentence.contains("=>")){
            ComplexSentence newComplexSentence;
            String[] premiseAndConclusion = sentence.split("=>");
            if(premiseAndConclusion[0].contains("&")){
                String[] premises = premiseAndConclusion[0].split("&");

                newComplexSentence = new ComplexSentence(this.symbols.get(premises[0]), "&", this.symbols.get(premises[1]));
                
                ComplexSentence anotherComplexSentence = new ComplexSentence(newComplexSentence, "=>", this.symbols.get(premiseAndConclusion[1]));
                return anotherComplexSentence;
            }else{
                newComplexSentence = new ComplexSentence(this.symbols.get(premiseAndConclusion[0]), "=>", this.symbols.get(premiseAndConclusion[1]));
                return newComplexSentence;
            }
        } else {
            return this.symbols.get(sentence);
        }
    }

}
