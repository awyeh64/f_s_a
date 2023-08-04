package io.collective;

import java.time.Clock;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

public class SimpleAgedCache {

    public static class ExpirableEntry {
        Object key;
        Object val;
        int retention;

        public ExpirableEntry(Object key, Object val, int retention) {
            this.key = key;
            this.val = val;
            this.retention = retention;
        }
    }
    public void checkExpired(){
        Entries.removeIf(entry -> entry.retention < (clock.millis() - baseClock.millis()));
    }

    Comparator<ExpirableEntry> retentionComparator = new Comparator<ExpirableEntry>() {
        @Override
        public int compare(ExpirableEntry e1, ExpirableEntry e2) {
            return e1.retention - e2.retention;
        }
    };

    PriorityQueue<ExpirableEntry> Entries = new PriorityQueue<>(retentionComparator);

    Clock clock;
    Clock baseClock = Clock.systemUTC();
    public SimpleAgedCache(Clock clock) {
        this.clock = clock;
    }

    public SimpleAgedCache() {
        this.clock = baseClock;
    }

    public void put(Object key, Object value, int retentionInMillis) {
        ExpirableEntry entry = new ExpirableEntry(key, value, retentionInMillis);
        Entries.add(entry);
    }

    public boolean isEmpty() {
        return Entries.size() == 0;
    }

    public int size() {
        this.checkExpired();
        return Entries.size();
    }

    public Object get(Object key) {
        this.checkExpired();
        for(ExpirableEntry entry : Entries){
            if (entry.key == key) {
                return entry.val;
            }
        }
    return null;
    }
}
