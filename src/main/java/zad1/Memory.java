package zad1;

import java.util.HashMap;

public class Memory {


    private HashMap<String, Integer> memory;

    public Memory() {
        this.memory = new HashMap<>();
    }

    public boolean containsKey(String key) {
        return memory.containsKey(key);
    }

    public int getValue(String key) {
        return memory.get(key);
    }

    public void putValue(String key, Integer value) {
        memory.put(key, value);
    }

}
