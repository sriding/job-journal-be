package com.jobjournal.JobJournal.shared.datastructures;

import java.util.HashMap;

public class ResponsePayloadHashMap {
    HashMap<String, Object> responsePayloadHashMap;

    public ResponsePayloadHashMap() {
        this.responsePayloadHashMap = new HashMap<String, Object>();
    }

    public <T> ResponsePayloadHashMap(Boolean _success, String _message, T _class) {
        this.responsePayloadHashMap = new HashMap<String, Object>() {
            {
                put("_success", _success);
                put("_message", _message);
                put("_payload", _class);
            }
        };
    }

    public void set_success(boolean _value) {
        this.responsePayloadHashMap.put("_success", _value);
    }

    public void set_message(String _message) {
        this.responsePayloadHashMap.put("_message", _message);
    }

    public <T> void set_payload(T _class) {
        this.responsePayloadHashMap.put("_payload", _class);
    }

    public HashMap<String, Object> getResponsePayloadHashMap() {
        return responsePayloadHashMap;
    }
}
