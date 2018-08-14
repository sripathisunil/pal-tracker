package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {

        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity(createdTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry = timeEntryRepository.find(id);

        if(timeEntry == null)
            return new ResponseEntity<>(timeEntry, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> list = timeEntryRepository.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PutMapping("/time-entries/{l}")
    public ResponseEntity update(@PathVariable long l, @RequestBody TimeEntry expected) {

        TimeEntry update = timeEntryRepository.update(l, expected);

        if(update == null) {
            return new ResponseEntity(update, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(update, HttpStatus.OK);
        }
    }

    @DeleteMapping("/time-entries/{l}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long l) {
        timeEntryRepository.delete(l);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
