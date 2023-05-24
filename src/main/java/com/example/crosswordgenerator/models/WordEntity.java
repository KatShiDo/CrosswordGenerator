package com.example.crosswordgenerator.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="word")
@Data
@NoArgsConstructor
public class WordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String word;
    private int posX, posY;
    private boolean isHorizontal;
}
