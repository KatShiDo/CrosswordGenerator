package com.example.crosswordgenerator.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String originalFileName;

    private Long size;

    private String contentType;

    @Lob
    private byte[] bytes;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private User user;
}
