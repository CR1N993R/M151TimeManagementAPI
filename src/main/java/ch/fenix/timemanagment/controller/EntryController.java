package ch.fenix.timemanagment.controller;

import ch.fenix.timemanagment.models.Entry;
import ch.fenix.timemanagment.services.EntryService;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static ch.fenix.timemanagment.configuration.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/entries")
@RequiredArgsConstructor
public class EntryController {
    private final EntryService entryService;

    @GetMapping
    public List<Entry> getEntries(@RequestHeader String authorization) {
        String username = JWT.decode(authorization.replace(TOKEN_PREFIX, "")).getSubject();
        return entryService.getEntriesByUsername(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entry createEntry(@RequestBody ComEntry comEntry, @RequestHeader String authorization) {
        String username = JWT.decode(authorization.replace(TOKEN_PREFIX, "")).getSubject();
        return entryService.createEntry(comEntry.checkIn, comEntry.checkOut, username, comEntry.categoryId);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteEntry(@RequestHeader String authorization, @PathVariable long id) {
        String username = JWT.decode(authorization.replace(TOKEN_PREFIX, "")).getSubject();
        if (entryService.deleteEntryByUsername(id, username) == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Entry> editEntry(@PathVariable long id, @RequestHeader String authorization, @RequestBody ComEntry comEntry) {
        String username = JWT.decode(authorization.replace(TOKEN_PREFIX, "")).getSubject();
        Entry entry = entryService.editEntry(id, comEntry.categoryId, username, comEntry.checkIn, comEntry.checkOut);
        if (entry == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(entry, HttpStatus.OK);
    }

    @Data
    static class ComEntry {
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime checkIn;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime checkOut;
        long categoryId;
    }
}
