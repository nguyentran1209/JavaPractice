package org.example;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.example.BaseQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import picocli.CommandLine;

import java.io.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

import static java.nio.file.Files.createFile;
import static java.nio.file.Path.of;

@Command(
        name= "exercise",
        description = "Java Exercise"

)

public class main implements Runnable {
    @Option(names = "-csv", description = "1. Create a new CSV file")
    private boolean createCsvFile;


    @Option(names = "-json", description = "2. Create a Json file")
    private boolean createJsonFile;

    @Option(names = "-both", description = "Both options")
    private boolean bothOption;

    public static void main(String[] args)  {
        new CommandLine(new main()).execute(args);
    }

    @Override
    public void run(){
        if(createCsvFile){
            try {
                processingCSVFileToGetBaseQuestionAsANewFile();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvException e) {
                e.printStackTrace();
            }
        }
        if(createJsonFile){
            processingBaseQuestionToJSon();
        }
        if(bothOption){
            try {
                processingCSVFileToGetBaseQuestionAsANewFile();
                processingBaseQuestionToJSon();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvException e) {
                e.printStackTrace();
            }
        }

    }

    public static void processingCSVFileToGetBaseQuestionAsANewFile() throws IOException,CsvException{
        String fileName = "Data/DataCollection.csv";
        List<String[]> r;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            r = reader.readAll();
        }

        List<String[]> listQuestion = new ArrayList<String[]>();
        HashMap<String, Boolean> temp = new HashMap<>();
        for (String[] arrays : r) {
            if(!temp.containsKey(arrays[0]) && !arrays[0].contains("smalltalk")){
                temp.put(arrays[0],true);
                String[] arrStr ={};
                arrStr = Arrays.copyOfRange( arrays, 0, 1);
                listQuestion.add(arrStr);
            }
        }

        String outputCSVFile ="Data/OutPutFile.csv";
        writeDataAtOnce(outputCSVFile,listQuestion);

    }

    public static void processingBaseQuestionToJSon(){
        String fileOutput = "D:\\OutPutFile.csv";
        List<String[]> o =  new ArrayList<String[]>();
        try (CSVReader reader = new CSVReader(new FileReader(fileOutput))) {
            o = reader.readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        ArrayList<BaseQuestion> baseQuestionsList = new ArrayList<BaseQuestion>();
        int count =1;
        for(int i=1; i<o.size(); i++){
            BaseQuestion question = new BaseQuestion(String.valueOf(count),String.valueOf(count), o.get(i)[0]);
            baseQuestionsList.add(question);
            count++;
        }

        String jsonText = new Gson().toJson(baseQuestionsList);
        System.out.println(jsonText);

        try {
            FileWriter file = new FileWriter("Data/output.json");
            file.write(jsonText);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDataAtOnce(String filePath, List<String[]> data)
    {
        File file = new File(filePath);
        try {
            FileWriter outPutFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outPutFile);
            writer.writeAll(data);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}

