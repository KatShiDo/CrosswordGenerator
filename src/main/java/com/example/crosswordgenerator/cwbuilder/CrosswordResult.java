package com.example.crosswordgenerator.cwbuilder;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CrosswordResult {
    private List<String> omittedWords;
    private List<WordReadyToSave> crossword;
    private int width, height;

    public CrosswordResult(List<String> omittedWords, List<WordReadyToSave> crossword, int width, int height) {
        this.omittedWords = omittedWords;
        this.crossword = crossword;
        this.width = width;
        this.height = height;
    }


    public CrosswordResult(){
        omittedWords = null;
        crossword = null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<WordReadyToSave> getCrossword() {
        return crossword;
    }

    public CrosswordCell[][] getCrosswordAsArray(){
        if(crossword == null)
            return null;
        int offsetX=1, offsetY=1;
        if(width < 20 || height < 20){
            int old_width = width, old_height = height;
            if(width < 20) {
                width = 20;
                offsetX += (20 - old_width) / 2;
            }
            if(height < 20) {
                height = 20;
                offsetY += (20 - old_height) / 2;
            }
        }
        CrosswordCell[][] inArrayCw = new CrosswordCell[width][height];
        for(int i=0;i < width;i++)
            for(int j = 0;j < height;j++)
                inArrayCw[i][j] = new CrosswordCell(" ");
        int wordindex = 1;
        for(WordReadyToSave cwWord: crossword){
            boolean isHorizontal = cwWord.isHorizontal();
            int x = cwWord.getPosX(), y = cwWord.getPosY();
            String word = cwWord.getWord();
            inArrayCw[offsetX + x - (isHorizontal?1:0)][offsetY + y - (!isHorizontal?1:0)] = new CrosswordCell(String.valueOf(wordindex));
            for(int i=0;i<word.length();i++){
                if(!inArrayCw[offsetX + x][offsetY + y].getIsWord())
                    inArrayCw[offsetX + x][offsetY + y] = new CrosswordCell(String.valueOf(word.charAt(i)), wordindex - 1, i, true);
                else{
                    inArrayCw[offsetX + x][offsetY + y].setWord2(wordindex - 1);
                    inArrayCw[offsetX + x][offsetY + y].setCharPos2(i);
                }
                if(isHorizontal)
                    x++;
                else
                    y++;
            }
            wordindex++;
        }
        return inArrayCw;
    }

    public List<CrosswordCell> getCrosswordAsList(){
        List<CrosswordCell> cwInList = new LinkedList<>();
        CrosswordCell[][] inArrayCw = getCrosswordAsArray();
        if(inArrayCw == null)
            return null;
        for(int i=0;i < height;i++)
            for (int k = 0; k < width; k++)
                cwInList.add(inArrayCw[k][i]);
        return cwInList;
    }

    public List<String> getOmittedWords() {
        return omittedWords;
    }

    /**
     * 0 - успешно создан
     * 1 - создан, но некоторые слова были пропущены, т.к. в них нет общих с другими совами букв
     * 2 - не создан, так как в словах нет общих букв
     * 3 - не создан, так как кроссворд с такими словами не существует
     *     (слова слишком длинные и не получается расставить их так, чтобы они не переекались)
     * */
    public int getStatus(){
        if(crossword != null && omittedWords != null){
            if(omittedWords.size() == 0)
                return 0;
            else return 1;
        }
        else if(omittedWords != null)
            return 2;
        else return 3;
    }

    public List<String> getUsedWords() {
        return crossword.stream().map(WordReadyToSave::getWord).toList();
    }

    public void setOmittedWords(List<String> omittedWords) {
        this.omittedWords = omittedWords;
    }

    public void setCrossword(List<WordReadyToSave> crossword) {
        this.crossword = crossword;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
