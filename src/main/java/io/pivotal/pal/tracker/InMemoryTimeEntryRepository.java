package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long, TimeEntry> sharedData = new HashMap<Long, TimeEntry>();
    private long key = 0L;


    @Override
    public TimeEntry find(Long id) {
        return sharedData.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(sharedData.values());
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry updatedTimeEntry = new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        sharedData.put(id, updatedTimeEntry);
        return updatedTimeEntry;
    }

    @Override
    public void delete(long id) {
        sharedData.remove(id);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        key = key + 1L;
        TimeEntry newTimeEntry = new TimeEntry(key, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        sharedData.put(key, newTimeEntry);
        return newTimeEntry;
    }


}
