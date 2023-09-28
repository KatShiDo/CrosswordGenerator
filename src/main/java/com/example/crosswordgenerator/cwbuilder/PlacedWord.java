package com.example.crosswordgenerator.cwbuilder;

/**
 * Класс, описывающий слово, помещённое в кроссоврд
 * */
public class PlacedWord{
    private int length, posX, posY, wi;
    private boolean isHorizontal;

    public PlacedWord(int length, int wi, boolean isHorizontal){
        this.length = length;
        this.wi = wi;
        this.isHorizontal = isHorizontal;
        posX = posY = 0;
    }


    public PlacedWord(int length, int posX, int posY, int wi, boolean isHorizontal) {
        this.length = length;
        this.posX = posX;
        this.posY = posY;
        this.wi = wi;
        this.isHorizontal = isHorizontal;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getWi() {
        return wi;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getLength() {
        return length;
    }

    /**
     * Проверяет, будет ли новое слово пересекаться с другими словами в кроссворде
     * */
    public boolean checkCollision(int otherLength, int otherX, int otherY, boolean otherHorizontal){
        if(isHorizontal ^ otherHorizontal){
            int xCol, xLeft, xRight, yRow, yTop, yBottom;
            if(isHorizontal){
                xLeft = posX - 2;
                xRight = posX + length;
                xCol = otherX;
                yRow = posY;
                yTop = otherY - 2;
                yBottom = otherY + otherLength;
            }else{
                xLeft = otherX - 2;
                xRight = otherX + otherLength;
                xCol = posX;
                yRow = otherY;
                yTop = posY - 2;
                yBottom = posY + length;
            }
            return (xLeft <= xCol && xCol <= xRight) && (yTop <= yRow && yRow <= yBottom);
        }else{
            if(isHorizontal){
                return posY == otherY && (posX + length - 1 >= otherX || otherX + otherLength - 1 >= posX);
            }else{
                return posX == otherX && (posY + length - 1 >= otherY || otherY + otherLength - 1 >= posY);
            }
        }
    }

    public String toString(){
        return "word " + wi + " (" + posX + ", " + posY + ")";
    }

    @Override
    public boolean equals(Object obj) {
        PlacedWord other = (PlacedWord) obj;
        return wi == other.wi;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }
}