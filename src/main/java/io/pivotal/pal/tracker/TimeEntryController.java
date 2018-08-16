package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;
    private CounterService counter;
    private final GaugeService gaugeService;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, CounterService counter, GaugeService gaugeService) {
        this.timeEntryRepository = timeEntryRepository;
        this.counter = counter;
        this.gaugeService = gaugeService;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntryToCreate);
        counter.increment("TimeEntry.Created");
        gaugeService.submit("time.entries count", timeEntryRepository.list().size());
        return new ResponseEntity(createdTimeEntry, HttpStatus.CREATED);

    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry = timeEntryRepository.find(id);

        if(timeEntry == null) {
            counter.increment("TimeEntry.Read");
            return new ResponseEntity<>(timeEntry, HttpStatus.NOT_FOUND);
        }
        else
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> list = timeEntryRepository.list();
        counter.increment("Time Entry-Listed");
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PutMapping("/time-entries/{l}")
    public ResponseEntity update(@PathVariable long l, @RequestBody TimeEntry expected) {

        TimeEntry update = timeEntryRepository.update(l, expected);

        if(update == null) {
            return new ResponseEntity(update, HttpStatus.NOT_FOUND);
        } else {
            counter.increment("Timeentry.updated");
            return new ResponseEntity(update, HttpStatus.OK);
        }
    }

    @DeleteMapping("/time-entries/{l}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long l) {
        timeEntryRepository.delete(l);
        counter.increment("Timeentry.deleted");
        gaugeService.submit("Time entries.count", timeEntryRepository.list().size());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
