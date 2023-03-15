package com.example.crosswordgenerator.services;

import com.example.crosswordgenerator.models.Crossword;
import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.repositories.ICrosswordRepository;
import com.example.crosswordgenerator.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrosswordService {

    private final ICrosswordRepository crosswordRepository;
    private final IUserRepository userRepository;

    public List<Crossword> getAll() {
        return crosswordRepository.findAll();
    }

    public void save(Principal principal, Crossword crossword) {
        crossword.setUser(getUserByPrincipal(principal));
        log.info("Saving new Crossword. Title: {}", crossword.getTitle());
        crosswordRepository.save(crossword);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) {
            return new User();
        }
        return userRepository.findByNickname(principal.getName());
    }

    public void delete(Long id) {
        crosswordRepository.deleteById(id);
    }

    public Crossword getById(Long id) {
        return crosswordRepository.findById(id).orElse(null);
    }
}
