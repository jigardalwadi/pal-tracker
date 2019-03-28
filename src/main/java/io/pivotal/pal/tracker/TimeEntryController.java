package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository ter;
    private DistributionSummary timeEntrySummary;
    private Counter actionCounter;


    public TimeEntryController(
            TimeEntryRepository ter,
            MeterRegistry meterRegistry
    ) {
        this.ter = ter;

        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }


    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry te = ter.create(timeEntryToCreate);

        actionCounter.increment();
        timeEntrySummary.record(ter.list().size());

        return ResponseEntity.status(HttpStatus.CREATED).body(te);
    }



    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry te = ter.find(id);
        if(te != null ){
            actionCounter.increment();
            return ResponseEntity.status(HttpStatus.OK).body(te);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(te);
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> li = ter.list();
        actionCounter.increment();
        return ResponseEntity.status(HttpStatus.OK).body(li);
    }



    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected) {
        TimeEntry te = ter.update(id,expected);
        if(te == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(te);

        }
        actionCounter.increment();
        return ResponseEntity.ok(te);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable  Long id) {
        ter.delete(id);

        TimeEntry tm = new TimeEntry();
        actionCounter.increment();
        timeEntrySummary.record(ter.list().size());
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(tm);


    }
}
