package com.example.crosswordgenerator.cwbuilder;

import lombok.Data;

public class CrosswordCell {
    private String value;
    private int word, charPos, word2, charPos2;
    private boolean isWord;

    public CrosswordCell(String value){
        word = charPos = 0;
        word2 = charPos2 = -1;
        this.value = value;
        isWord = false;
    }

    public CrosswordCell(String value, int word, int charPos, boolean isWord) {
        this.value = value;
        this.word = word;
        this.charPos = charPos;
        this.isWord = isWord;
        word2 = charPos2 = -1;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getWord() {
        return word;
    }

    public void setWord(int word) {
        this.word = word;
    }

    public int getCharPos() {
        return charPos;
    }

    public void setCharPos(int charPos) {
        this.charPos = charPos;
    }

    public int getWord2() {
        return word2;
    }

    public void setWord2(int word2) {
        this.word2 = word2;
    }

    public int getCharPos2() {
        return charPos2;
    }

    public void setCharPos2(int charPos2) {
        this.charPos2 = charPos2;
    }

    public boolean getIsWord() {
        return isWord;
    }

    public void setIsWord(boolean word) {
        isWord = word;
    }
}
