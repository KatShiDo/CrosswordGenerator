package com.example.crosswordgenerator.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Объект, в который будут отображаться строки из таблицы crosswords
 * */
@Entity
@Data
@Table(name = "crosswords")
@AllArgsConstructor
@NoArgsConstructor
public class Crossword {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Lob
    private byte[] content;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private User user;

    private LocalDateTime creationDate;

    @PrePersist
    private void init() {
        creationDate = LocalDateTime.now();
    }
}
