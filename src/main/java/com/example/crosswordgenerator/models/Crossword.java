package com.example.crosswordgenerator.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Объект, в который будут отображаться строки из таблицы crosswords
 * */
@Entity
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Crossword {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private int solved, width, height;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<WordEntity> words;

    @ElementCollection
    private List<String> questions;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalDateTime creationDate;

    @PrePersist
    private void init() {
        solved = 0;
        log.info("Crossword persisted");
        creationDate = LocalDateTime.now();
    }
}
