package io.pivotal.pal.tracker;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeEntryController {

	private TimeEntryRepository timeEntryRepository;
	private final CounterService counter;
	private final GaugeService gauge;

	public TimeEntryController(TimeEntryRepository timeEntryRepository,
			CounterService counter, GaugeService gauge) {
		this.timeEntryRepository = timeEntryRepository;
		this.counter = counter;
		this.gauge = gauge;
	}

	@RequestMapping(value = "/time-entries", method = RequestMethod.POST)
	public ResponseEntity<TimeEntry> create(
			@RequestBody TimeEntry timeEntryToCreate) {
		TimeEntry createdTimeEntry = timeEntryRepository
				.create(timeEntryToCreate);
		counter.increment("TimeEntry.created");
		gauge.submit("timeEntries.count", timeEntryRepository.list().size());
		return new ResponseEntity<>(createdTimeEntry, CREATED);

	}

	@RequestMapping(value = "/time-entries/{itemid}", method = RequestMethod.GET)
	public ResponseEntity<TimeEntry> read(@PathVariable("itemid") long l) {
		TimeEntry readTimeEntry = timeEntryRepository.find(l);
		if (!StringUtils.isEmpty(readTimeEntry)) {
			counter.increment("TimeEntry.read");
			return new ResponseEntity<>(readTimeEntry, OK);
		} else
			return new ResponseEntity<>(readTimeEntry, NOT_FOUND);

	}

	@RequestMapping(value = "/time-entries", method = RequestMethod.GET)
	public ResponseEntity<List<TimeEntry>> list() {
		List<TimeEntry> listTimeEntry = timeEntryRepository.list();
		counter.increment("TimeEntry.listed");
		if (!StringUtils.isEmpty(listTimeEntry))
			return new ResponseEntity<>(listTimeEntry, OK);
		else
			return new ResponseEntity<>(listTimeEntry, NOT_FOUND);
	}

	@RequestMapping(value = "/time-entries/{itemid}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable("itemid") long l,
			@RequestBody TimeEntry expected) {
		TimeEntry updatedTimeEntry = timeEntryRepository.update(l, expected);
		if (!StringUtils.isEmpty(updatedTimeEntry)) {
			counter.increment("TimeEntry.updated");
			return new ResponseEntity<>(updatedTimeEntry, OK);

		} else
			return new ResponseEntity<>(updatedTimeEntry, NOT_FOUND);
	}

	@RequestMapping(value = "/time-entries/{itemid}", method = RequestMethod.DELETE)
	public ResponseEntity<TimeEntry> delete(@PathVariable("itemid") long l) {
		timeEntryRepository.delete(l);
		counter.increment("TimeEntry.deleted");
		gauge.submit("timeEntries.count", timeEntryRepository.list().size());
		return new ResponseEntity<>(NO_CONTENT);
	}

}
