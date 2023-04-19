package com.example.crosswordgenerator.cwbuilder;

import java.util.List;

public class CrosswordResult {
    private List<String> omittedWords;
    private String[][] crossword;

    public CrosswordResult(List<String> omittedWords, String[][] crossword) {
        this.omittedWords = omittedWords;
        this.crossword = crossword;
    }


    public CrosswordResult(){
        omittedWords = null;
        crossword = null;
    }

    public String[][] getCrossword() {
        return crossword;
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
}
