package com.daniel.data_mapping;

public class Salary {

    private final float starting_salary;

    Salary(String[] words){
        this.starting_salary = cleanUpString(words[1]);
    }

    public float getStarting_salary() {
        return starting_salary;
    }

    // turn currency formatted values to raw numbers (ex: 10,000 -> 10000)
    public float cleanUpString(String word){
        return Float.parseFloat(word
                                .substring(2, word.length() - 2)
                                .replaceAll(",", ""));
    }
}
