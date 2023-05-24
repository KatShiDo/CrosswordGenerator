package com.example.crosswordgenerator.cwbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class WordReadyToSave {
    private String word;
    private boolean isHorizontal;
    private int posX, posY;
}
