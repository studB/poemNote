package com.studb.poemNote.utils;

import java.util.HashMap;
import java.util.Map;

public class OperationResult {
    
    private Map<String, Object> result;

    public OperationResult() {
        this.result = new HashMap<String, Object>();
    };

    public void addElmt(String key, Object obj){
        result.put(key, obj);
    }

    public Map<String, Object> getResult() {
        return result;
    }
    
}
