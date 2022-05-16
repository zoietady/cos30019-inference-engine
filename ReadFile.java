import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.LimitExceededException;

import java.util.HashMap;

public class ReadFile {

    private SimpleSentence ss1;
    private SimpleSentence ss2;
    private SimpleSentence ss3;
    private SimpleSentence ss4;
    private SimpleSentence ss5;
    private ComplexSentence cs1;
    private ComplexSentence cs2;
    private ComplexSentence cs3;
    private ComplexSentence cs4;
    private ComplexSentence cs5;
    private ComplexSentence cs6;

    private String[] s2;
    private String[] s3;

    private String con1;
    private String con2;
    private String[] string2;

    String[] temp;

    private String[] splitString;
    private ArrayList<Sentence> listOfSS1;
    private ArrayList<Sentence> listOfSS2;
    private ArrayList<Sentence> listOfSS3;
    private ArrayList<Sentence> tempList;


    private ArrayList<Sentence> listComplex1;
    private SimpleSentence ask;

    // creates empty knowledge base
    public ReadFile() {
        // readInput(r);
    }

    public ArrayList<Sentence> GetSentences() {
        return listComplex1;
    }

    public SimpleSentence GetAsk() {
        return ask;
    }

    public void readInput(BufferedReader r) {
        try {
            r.readLine();
            String tellString = r.readLine();
            tellString = tellString.replaceAll("\\s", "");
            System.out.println(tellString);
            String[] strings = tellString.split(";");
            listComplex1 = new ArrayList<Sentence>();
            for (String s : strings) {
                if (!s.contains("=>") && !s.contains("&")) {
                    listOfSS1 = new ArrayList<Sentence>();
                    ss1 = new SimpleSentence(s);
                    listComplex1.add(ss1);
                }
                if (s.contains("=>")) {
                    listOfSS1 = new ArrayList<Sentence>();
                    s2 = s.split("=>");
                    ss2 = new SimpleSentence(s2[1]);
                    con1 = "=>";
                    if (!s2[0].contains("&")) {
                        ss1 = new SimpleSentence(s2[0]);
                        listOfSS1.add(ss1);
                        listOfSS1.add(ss2);
                    }
                    cs1 = new ComplexSentence(listOfSS1, "=>");
                    listComplex1.add(cs1);
                    if (s2[0].contains("&")) {
                        listOfSS2 = new ArrayList<Sentence>();
                        tempList = new ArrayList<Sentence>();
                        s2[0].replaceAll("\\s", "");
                        s3 = s2[0].split("&");
                        ss3 = new SimpleSentence(s3[0]);
                        ss4 = new SimpleSentence(s3[1]);
                        listOfSS2.add(ss3);
                        listOfSS2.add(ss4);
                        con2 = "&";
                        // if (s3.length > 2) {
                        //     listOfSS3 = new ArrayList<Sentence>();
                        //     cs4 = new ComplexSentence(listOfSS2, con2);
                        //     listOfSS3.add(cs4);
                        //     ss5 = new SimpleSentence(s3[2]);
                        //     listOfSS3.add(ss5);
                        //     cs5 = new ComplexSentence(listOfSS3, con2);
                        //     listComplex1.add(cs5);
                        // }
                        cs3 = new ComplexSentence(listOfSS2, "&");
                        tempList.add(cs3);
                        tempList.add(ss2);
                        cs2 = new ComplexSentence(tempList, "=>");
                        listComplex1.add(cs2);
                    }
                }
                cleanList();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(2);
        }

    }

    public void readInputQuestion(BufferedReader r) {
        try {
            r.readLine();
            String tellString = r.readLine();
            tellString = tellString.replaceAll("\\s", "");
            ask = new SimpleSentence(tellString);

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(2);
        }

    }

    private void cleanList(){
        for(Sentence s : listComplex1){
            if(s.getSymbols().isEmpty()){
                listComplex1.remove(s);
            }
        }
    }


}