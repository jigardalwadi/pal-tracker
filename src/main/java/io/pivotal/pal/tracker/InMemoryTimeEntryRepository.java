package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long,TimeEntry> map = new HashMap<>();

    public InMemoryTimeEntryRepository() {
    }

    long id = 1L;
    @Override
    public TimeEntry create(TimeEntry tm) {
        tm.setId(id);
        map.put(id,tm);
        id++;
        return map.get(id-1);
    }

    @Override
    public TimeEntry find(Long id) {
        return map.get(id);
    }

    @Override
    public boolean find_MissingEntry(Long id) {
        if(map.get(id) == null){
            return false;
        }return true;

    }


    @Override
    public List<TimeEntry> list() {
        List<TimeEntry> li = new ArrayList<>();
        for(Map.Entry<Long, TimeEntry> entry : map.entrySet()){
            li.add(entry.getValue());
        }
        return li;
    }



    @Override
    public TimeEntry update(Long id, TimeEntry tm) {

        if(map.get(id) ==null){
            return null;
        }

        tm.setId(id);
        map.put(id,tm);
        return tm;
    }




    @Override
    public void delete(Long id) {
        map.remove(id);

    }

    @Override
    public boolean deleteKeepsTrackOfLatestIdProperly(Long id) {
        map.remove(id);
        if(map.get(id) == null){
            return true;
        }return false;
    }

}
