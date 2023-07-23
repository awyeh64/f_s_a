
// Week 1 Assignment
// Andrew Y
// Reference List:
// Calculating elapsed time - https://www.baeldung.com/java-measure-elapsed-time



package io.collective;

import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.Instant;



public class SimpleAgedCache {
    HashMap<Object, Object> keyValues = new HashMap<Object, Object>();
    HashMap<Object, Integer> keyRetention = new HashMap<Object, Integer>();
    Instant start;
    Instant curr;
    Clock clock;
    Integer flag = 0;

    public SimpleAgedCache(Clock clock) {
        this.flag = 1;
        this.clock = clock;
        this.start = clock.instant();
    }

    public void update(){
        this.curr = this.clock.instant();
        long elapsed = Duration.between(this.start, this.curr).toMillis();
        for (Object i : this.keyRetention.keySet()){
            Integer upd = (int) (this.keyRetention.get(i) - elapsed);

            if ( upd <= 0){
                this.keyValues.remove(i);
                this.keyRetention.remove(i);
            }

        }
    }

    public SimpleAgedCache() {
        this.flag = 0;
    }

    public void put(Object key, Object value, int retentionInMillis) {
        this.keyValues.put(key, value);
        this.keyRetention.put(key, retentionInMillis);
    }

    public boolean isEmpty() {
        if (this.keyValues.size() == 0){
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        if (flag == 1){
            this.update();
        }
        return this.keyValues.size();
    }

    public Object get(Object key) {
        if (flag == 1){
            this.update();
        }
        return this.keyValues.get(key);
    }
}