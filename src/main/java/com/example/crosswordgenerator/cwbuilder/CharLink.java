package com.example.crosswordgenerator.cwbuilder;

/**
 * Класс, описывающий символ, через который можно связать
 * */
class CharLink{
    private int position, prevalence, posX, posY, wi;
    private char character;
    private boolean isInHorizontalWord;

    public CharLink(CharLink other){
        character = other.character;
        position = other.position;
        prevalence = other.prevalence;
        posX = other.posX;
        posY = other.posY;
        wi = other.wi;
    }

    public CharLink(char character, int position, int prevalence, int wi) {
        this.character = character;
        this.position = position;
        this.prevalence = prevalence;
        this.wi = wi;
        posX = posY = 0;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWi() {
        return wi;
    }

    public char getCharacter(){
        return character;
    }

    public int getPosition(){
        return position;
    }


    public String toString(){
        return " [" + character + " pos: " + position + " (" + posX  + ", " + posY + ")]";
    }

    public boolean isInHorizontalWord() {
        return isInHorizontalWord;
    }

    public void setInHorizontalWord(boolean inHorizontalWord) {
        isInHorizontalWord = inHorizontalWord;
    }

    @Override
    public boolean equals(Object obj) {
        CharLink other = (CharLink) obj;
        return wi == other.wi && position == other.position;
    }

    public int getPrevalence() {
        return prevalence;
    }
}