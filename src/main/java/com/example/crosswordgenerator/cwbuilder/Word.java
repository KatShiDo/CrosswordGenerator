package com.example.crosswordgenerator.cwbuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс, описывающий слово
 * */
class Word{
    private int uniqueChars, wi, length;
    private List<CharLink> chars = new LinkedList<>();

    public Word(){
        uniqueChars = 0;
    }

    public Word(CharLink firstLink, int wi, int length){
        this();
        chars.add(firstLink);
        this.wi = wi;
        this.length = length;
        uniqueChars++;
    }

    public int getWi() {
        return wi;
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean equals(Object obj) {
        Word other = (Word)obj;
        return other.wi == wi;
    }

    public void addCharLink(CharLink cl){
        if (!chars.contains(cl)){
            uniqueChars++;
        }
        chars.add(cl);
    }

    public List<CharLink> getAll(){
        return chars;
    }

    public int getUniqueChars(){
        return uniqueChars;
    }
}