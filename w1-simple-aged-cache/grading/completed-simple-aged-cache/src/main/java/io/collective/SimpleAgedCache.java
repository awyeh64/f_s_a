package io.collective;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SimpleAgedCache {

    Clock my_clock = null;
    List<List<Object>> cache = new ArrayList<>();

    public SimpleAgedCache(Clock clock) {
        my_clock = clock;
    }

    public SimpleAgedCache() {
    }

    public void ExpirableEntry() {
        for (int i = 0; i < cache.size(); i++) {

            List<Object> item = cache.get(i);
            Instant expiration_date = (Instant) item.get(2);
            
            Instant now = Instant.now(my_clock);//my_clock);
            
            int comparisonResult = now.compareTo(expiration_date);
            if (comparisonResult > 0) {
                cache.remove(i);
            }
        }
    }

    public void put(Object key, Object value, int retentionInMillis) {

        List<Object> item = new ArrayList<>();
        
        item.add(key);
        item.add(value);
        if (my_clock != null) {
            Instant now = Instant.now(my_clock);
            Duration duration = Duration.ofMillis(retentionInMillis);
            item.add(now.plus(duration));
        } else {
            item.add(retentionInMillis);
        }
        
        cache.add(item);
    }

    public boolean isEmpty() {
        //this.ExpirableEntry();
        return cache.size() == 0;
    }

    public int size() {
        if (my_clock != null){
            this.ExpirableEntry();
        }
        return cache.size();
    }

    public Object get(Object key) {
        if (my_clock != null){
            this.ExpirableEntry();
        }
        for (int i = 0; i < cache.size(); i++) {
            List<Object> item = cache.get(i);
            if (item.get(0) == key) {
                return item.get(1);
            }
        }
        return null;
    }
}