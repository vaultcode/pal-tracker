package io.pivotal.pal.tracker;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeEntryController {

	public TimeEntryController(TimeEntryRepository timeEntryRepository) {
		this.timeEntryRepository = timeEntryRepository;
	}
	
	
	private TimeEntryRepository timeEntryRepository;


	@RequestMapping(value="/time-entries", method = RequestMethod.POST)
	public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
		TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntryToCreate);
		return new ResponseEntity<TimeEntry>(createdTimeEntry, HttpStatus.CREATED);
		
	}

	@RequestMapping(value="/time-entries/{itemid}", method = RequestMethod.GET)
	public ResponseEntity<TimeEntry> read(@PathVariable("itemid") long l) {
		TimeEntry readTimeEntry = timeEntryRepository.find(l);
		if(!StringUtils.isEmpty(readTimeEntry))
		return new ResponseEntity<TimeEntry>(readTimeEntry, HttpStatus.OK);
		else
		return new ResponseEntity<TimeEntry>(readTimeEntry, HttpStatus.NOT_FOUND);
			
	}

	@RequestMapping(value="/time-entries", method = RequestMethod.GET)
	public ResponseEntity<List<TimeEntry>> list() {
		List<TimeEntry> listTimeEntry = timeEntryRepository.list();
		if(!StringUtils.isEmpty(listTimeEntry))
			return new ResponseEntity<List<TimeEntry>>(listTimeEntry, HttpStatus.OK);
			else
			return new ResponseEntity<List<TimeEntry>>(listTimeEntry, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value="/time-entries/{itemid}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable("itemid") long l, @RequestBody TimeEntry expected) {
		TimeEntry updatedTimeEntry = timeEntryRepository.update(l, expected);
		if(!StringUtils.isEmpty(updatedTimeEntry))
			return new ResponseEntity<TimeEntry>(updatedTimeEntry, HttpStatus.OK);
		else
			return new ResponseEntity<TimeEntry>(updatedTimeEntry, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value="/time-entries/{itemid}", method = RequestMethod.DELETE)
	public ResponseEntity<TimeEntry> delete(@PathVariable("itemid") long l) {
		timeEntryRepository.delete(l);
		return new ResponseEntity<TimeEntry>(HttpStatus.NO_CONTENT);
	}

}
