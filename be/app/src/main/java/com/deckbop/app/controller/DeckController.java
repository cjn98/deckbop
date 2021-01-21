package com.deckbop.app.controller;

import com.deckbop.app.controller.response.DeckGetResponse;
import com.deckbop.app.controller.request.DeckRequest;
import com.deckbop.app.dao.DeckDAO;
import com.deckbop.app.service.LoggingService;
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

    @Autowired
    LoggingService loggingService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createDeck(@RequestBody DeckRequest deckDto){
        deckDAO.createDeck(deckDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateDeck(@PathVariable long id, @RequestBody DeckRequest request){
        try {
            Optional<DeckGetResponse> deck = deckDAO.getDeck(id);
            if (deck.isPresent()) {
                return deckDAO.updateDeck(request, id);
            } else {
                return new ResponseEntity<>("Invalid Deck ID",HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            loggingService.error("SQL error while updating deck.");
        }
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DeckGetResponse> getDeck(@PathVariable long id) {
        Optional<DeckGetResponse> response = deckDAO.getDeck(id);
        return response.map(deckGetResponse -> new ResponseEntity<>(deckGetResponse, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDeck(@PathVariable long id){
        deckDAO.deleteDeck(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
