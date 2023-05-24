package com.example.crosswordgenerator.tojson;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SolutionResult {
    private List<List<Integer>> mistakes;
    private int wordsSolved;
    private double accuracy;
}
