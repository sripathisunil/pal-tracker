package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    public Map<Long, TimeEntry> myInMemoryDB = new HashMap<>();
    public long key = 0;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        key = key+1;
        TimeEntry timeEntry1 = new TimeEntry(key, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        myInMemoryDB.put(new Long(key), timeEntry1);
        return timeEntry1;
    }

    @Override
    public TimeEntry find(long id) {
        return myInMemoryDB.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        final ArrayList<TimeEntry> timeEntries = new ArrayList<>(myInMemoryDB.values());
        return timeEntries;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        timeEntry.setId(id);
        myInMemoryDB.replace(id, timeEntry);
        return myInMemoryDB.get(id);
    }

    @Override
    public void delete(long id) {
        myInMemoryDB.remove(id);

    }
}
