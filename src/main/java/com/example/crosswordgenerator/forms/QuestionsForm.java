package com.example.crosswordgenerator.forms;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class QuestionsForm {
    private List<String> questions;
    private String title;
}
