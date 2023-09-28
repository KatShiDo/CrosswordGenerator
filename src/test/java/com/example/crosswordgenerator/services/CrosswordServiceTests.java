package com.example.crosswordgenerator.services;

import com.example.crosswordgenerator.models.Crossword;
import com.example.crosswordgenerator.repositories.ICrosswordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CrosswordServiceTests {

    @Mock
    private ICrosswordRepository crosswordRepository;

    @InjectMocks
    private CrosswordService crosswordService;

    @Test
    public void CrosswordService_SaveCrossword_ReturnsCrossword() {
        Crossword cw = new Crossword();
        when(crosswordRepository.save(Mockito.any(Crossword.class))).thenReturn(cw);
        cw = crosswordService.save(cw);
        Assertions.assertNotNull(cw);
    }

    @Test
    public void CrosswordService_SaveCrossword_NullArgument_ThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            crosswordService.save(null);
        }, "NullPointerException was expected");
    }

    @Test
    public void CrosswordService_GetAllCrosswords_ReturnsIterable() {
        Iterable<Crossword> crosswords = Mockito.mock(Iterable.class);
        when(crosswordRepository.findAll()).thenReturn(crosswords);
        Iterable<Crossword> crosswordsReturn = crosswordService.getAll();
        Assertions.assertNotNull(crosswordsReturn);
    }

    @Test
    public void CrosswordService_GetById_ReturnsCrossword() {
        Crossword crossword = new Crossword();
        when(crosswordRepository.findById(1L)).thenReturn(Optional.of(crossword));
        crossword = crosswordService.getById(1L);
        Assertions.assertNotNull(crossword);
    }

    @Test
    public void CrosswordService_GetById_NullId_ReturnsNull() {
        Crossword crossword = crosswordService.getById(null);
        Assertions.assertNull(crossword);
    }

    @Test
    public void CrosswordService_IncreaseSolvedCount_ReturnsTrue() {
        Crossword crossword = new Crossword();
        when(crosswordRepository.findById(1L)).thenReturn(Optional.of(crossword));
        when(crosswordRepository.save(Mockito.any(Crossword.class))).thenReturn(crossword);

        boolean result = crosswordService.increaseSolvedCount(1L);
        Assertions.assertTrue(result);
    }

    @Test
    public void CrosswordService_IncreaseSolvedCount_NullId_ReturnsFalse() {
        boolean result = crosswordService.increaseSolvedCount(1L);
        Assertions.assertFalse(result);
    }

    @Test
    public void CrosswordService_DeleteCrossword_ReturnsTrue() {
        Crossword crossword = new Crossword();
        when(crosswordRepository.findById(1L)).thenReturn(Optional.of(crossword));
        boolean result = crosswordService.delete(1L);
        Assertions.assertTrue(result);
    }

    @Test
    public void CrosswordService_DeleteCrossword_NullId_ReturnsFalse() {
        boolean result = crosswordService.delete(null);
        Assertions.assertFalse(result);
    }
}
