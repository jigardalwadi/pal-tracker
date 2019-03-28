package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {
    public TimeEntry create(TimeEntry tm);

    public TimeEntry find(Long id);

    public boolean find_MissingEntry(Long id);


    public List<TimeEntry> list();

    public TimeEntry update(Long id,TimeEntry tm);



    public void delete(Long id);

    public boolean deleteKeepsTrackOfLatestIdProperly(Long id);
}
