package com.example.crosswordgenerator;

import com.example.crosswordgenerator.cwbuilder.PlacedWord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlacedWordTests {

    @Test
    @DisplayName("Test checking collision between overlapping words")
    public void testCheckCollisionBetweenOverlappingWords() {
        PlacedWord placedWord = new PlacedWord(10, 0, 0, 0, true);
        assertTrue(placedWord.checkCollision(10, 0, 0, true));
    }

    @Test
    @DisplayName("Test checking collision between non overlapping words")
    public void testCheckCollisionBetweenNonOverlappingWords() {
        PlacedWord placedWord = new PlacedWord(10, 20, 20, 0, true);
        assertFalse(placedWord.checkCollision(10, 0, 0, false));
    }

    @Test
    @DisplayName("Test whether one word is equal to another")
    public void testIsOneWordEqualToAnother() {
        PlacedWord placedWord = new PlacedWord(10, 20, 20, 0, true);
        PlacedWord otherPlacedWord = new PlacedWord(10, 20, 20, 0, true);
        assertEquals(placedWord, otherPlacedWord);
    }

    @Test
    @DisplayName("Test whether one word is equal to another with non equal words")
    public void testIsOneWordNonEqualToAnother() {
        PlacedWord placedWord = new PlacedWord(10, 20, 20, 0, true);
        PlacedWord otherPlacedWord = new PlacedWord(10, 20, 20, 1, true);
        assertNotEquals(placedWord, otherPlacedWord);
    }

    @Test
    @DisplayName("Test toString() method of PlacedWord class")
    public void testToString() {
        PlacedWord placedWord = new PlacedWord(10, 20, 20, 0, true);
        assertEquals(placedWord.toString(), "word 0 (20, 20)");
    }
}
