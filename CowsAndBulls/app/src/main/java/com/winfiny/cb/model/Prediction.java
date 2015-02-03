package com.winfiny.cb.model;

public class Prediction {

    int bulls;
    int cows;
    String word;
    public int getBulls() {
        return bulls;
    }
    public void setBulls(int bulls) {
        this.bulls = bulls;
    }
    public int getCows() {
        return cows;
    }
    public void setCows(int cows) {
        this.cows = cows;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }

    public Prediction(int bulls, int cows, String word) {
        super();
        this.bulls = bulls;
        this.cows = cows;
        this.word = word;
    }

}