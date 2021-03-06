package com.daniel.data_mapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class DataLoading {

    final String ROOT_DIR = System.getProperty("user.dir") + "/";
    final String RES_DIR = ROOT_DIR + "res/";
    final String FILE_ONE_NAME = "major_salary.csv";
    final String FILE_TWO_NAME = "unemployment.csv";

    public DataLoading() {
        try {
            setup_dataSetOne();
            setup_dataSetTwo();
            dataCleanUp();
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Get and store the data from first csv file.
    void setup_dataSetOne() throws IOException {
        Files.readAllLines(Path.of(RES_DIR + FILE_ONE_NAME)).stream().skip(1).forEach(line -> {
            String[] words = split_csv_line_to_arr(line);
            Storage.MAP_SALARY.put(words[0], new Salary(words));
        });
    }

    // Get and store the data from second csv file.
    void setup_dataSetTwo() throws IOException {
        Files.readAllLines(Path.of(RES_DIR + FILE_TWO_NAME)).stream().skip(1).forEach(line -> {
            String[] words = split_csv_line_to_arr(line);
            Storage.MAP_UNEMPLOYMENT.put(words[0], new Unemployment(words));
        });
    }

    // clean up data
    void dataCleanUp(){
        var map_1 = new HashMap<>(Storage.MAP_SALARY);
        var map_2 = new HashMap<>(Storage.MAP_UNEMPLOYMENT);
        Storage.MAP_SALARY.clear();
        Storage.MAP_UNEMPLOYMENT.clear();
        map_1.forEach((k, v) -> {
            map_2.forEach((k2, v2) -> {
                if (keyMatches(k, k2)) {
                    Storage.MAP_SALARY.put(k, v);
                    Storage.MAP_UNEMPLOYMENT.put(k, v2);
                }
            });
        });
    }

    // data cleanup matches
    boolean keyMatches(String a, String b){
        return a.equals(b) // below, special cases
            || a.equals("Education")        && b.equals("General Education")
            || a.equals("English")          && b.equals("English Language")
            || a.equals("Graphic Design")   && b.equals("Commercial Art & Graphic Design")
            || a.equals("Math")             && b.equals("Mathematics")
            || a.equals("Nutrition")        && b.equals("Nutrition Sciences")
            || a.equals("Religion")         && b.equals("Theology and Religion");
    }

    // to get data from csv file that contains comma.
    public String[] split_csv_line_to_arr(String line){
        return line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    }

}
