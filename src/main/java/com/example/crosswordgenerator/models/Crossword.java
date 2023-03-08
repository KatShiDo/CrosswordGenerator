package com.example.crosswordgenerator.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Table(name = "crosswords")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Crossword {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "words", columnDefinition = "text")
    private String words;

    @Column(name = "user")
    private String user;
}
