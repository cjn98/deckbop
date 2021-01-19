package com.deckbop.app.controller;

import com.deckbop.app.controller.dto.DeckGetResponse;
import com.deckbop.app.controller.dto.DeckPostRequest;
import com.deckbop.app.dao.DeckDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/deck")
public class DeckController {
    
    @Autowired
    DeckDAO deckDAO;
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createDeck(@RequestBody DeckPostRequest deckDto){
        deckDAO.createDeck(deckDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DeckGetResponse> getDeck(@PathVariable long id) {
        Optional<DeckGetResponse> response = deckDAO.getDeck(id);
        return response.map(deckGetResponse -> new ResponseEntity<>(deckGetResponse, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}