package com.khushal.RBAC_demo.service;

import com.khushal.RBAC_demo.entity.JournalEntry;
import com.khushal.RBAC_demo.entity.User;
import com.khushal.RBAC_demo.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class JournalService {

    @Autowired
    private JournalRepository journalRepository; // Repository for accessing journal entries in the database
    @Autowired
    private UserService userService; // Service for handling user-related operations

    // Saves a new journal entry and associates it with the specified user
    @Transactional  // Ensures atomicity: either both operations (journal save and user update) succeed, or neither
    public void saveEntry(String username, JournalEntry journalEntry) {
        try {
            journalEntry.setDate(LocalDateTime.now()); // Sets the current date and time for the journal entry
            JournalEntry saved = journalRepository.save(journalEntry); // Saves the journal entry to the repository

            User user = userService.findByUserName(username); // Retrieves the user by username
            user.getEntries().add(saved); // Adds the journal entry to the user's list of entries
            userService.saveUser(user); // Saves the updated user
        } catch (Exception e) {
            System.out.println(e.toString()); // Logs any exception
            throw e; // Propagates the exception
        }
    }

    // Retrieves all journal entries
    public List<JournalEntry> getAllEntries() {
        return journalRepository.findAll(); // Fetches all journal entries from the repository
    }

    // Retrieves a journal entry by its ID
    public Optional<JournalEntry> getById(ObjectId id) {
        return journalRepository.findById(id); // Fetches a journal entry by its ID
    }

    // Deletes a journal entry by its ID and removes it from the associated user's entries
    @Transactional
    public void deleteEntry(ObjectId id, String username) {
        try {
            User user = userService.findByUserName(username); // Retrieves the user by username
            boolean removed = user.getEntries().removeIf(x -> x.getId().equals(id)); // Removes the entry from the user's list if the ID matches

            if(removed) {
                userService.saveUser(user); // Saves the updated user
                journalRepository.deleteById(id); // Deletes the journal entry from the repository
            }
        } catch (Exception e) {
            System.out.println(e.toString()); // Logs any exception
            throw new RuntimeException("An error occurred while deleting journalEntry", e); // Throws a runtime exception
        }
    }

    // Updates a journal entry if it exists and updates non-null fields
    public JournalEntry updateEntry(ObjectId id, JournalEntry myEntry) {
        JournalEntry old = journalRepository.findById(id).orElse(null); // Retrieves the existing journal entry by ID

        if (old != null) {
            // Updates fields if new values are provided, else keeps the old values
            old.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().isEmpty() ? myEntry.getTitle() : old.getTitle());
            old.setContent(myEntry.getContent() != null && !myEntry.getContent().isEmpty() ? myEntry.getContent() : old.getContent());
            old.setDate(LocalDateTime.now()); // Sets the current date and time
            journalRepository.save(old); // Saves the updated journal entry
        }
        return old; // Returns the updated journal entry
    }
}
