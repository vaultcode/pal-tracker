package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
	
    private static HashMap<Long, TimeEntry> sharedData = new HashMap<Long, TimeEntry>();
    private Long key= 0L;


	@Override
	public TimeEntry find(Long id) {
		return sharedData.get(id);
	}

	@Override
	public List<TimeEntry> list() {
		return  new ArrayList<TimeEntry>(sharedData.values());
	}

	@Override
	public TimeEntry update(long id, TimeEntry timeEntry) {
		timeEntry.setId(id);
		sharedData.put(id, timeEntry);
		return timeEntry;
	}

	@Override
	public void delete(long id) {
		sharedData.clear();
		
	}

	@Override
	public TimeEntry create(TimeEntry timeEntry) {
		key = key + 1L;
		timeEntry.setId(key);
		sharedData.put(key, new TimeEntry(timeEntry.getId(), timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours()));
		return timeEntry;
	}

	

	

}
