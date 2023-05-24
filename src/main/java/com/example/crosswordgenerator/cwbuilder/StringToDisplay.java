package com.example.crosswordgenerator.cwbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Класс, который позволяет различить номер слова и содерживое слова, чтобы применить к ним различные классы css
 * */
public class StringToDisplay {
    private String toDisplay;
    private boolean isWordNumber;

    public StringToDisplay(String toDisplay, boolean isWordNumber) {
        this.toDisplay = toDisplay;
        this.isWordNumber = isWordNumber;
    }

    public String getToDisplay() {
        return toDisplay;
    }

    public boolean getIsWordNumber() {
        return isWordNumber;
    }
}
