package com.jobjournal.JobJournal.shared.datastructures;

import java.util.HashMap;

public class ResponsePayloadHashMap {
    HashMap<String, Object> responsePayloadHashMap;

    public <T> ResponsePayloadHashMap(Boolean _success, String _message, T _class) {
        this.responsePayloadHashMap = new HashMap<String, Object>() {
            {
                put("_success", _success);
                put("_message", _message);
                put("_payload", _class);
            }
        };
    }

    public HashMap<String, Object> getResponsePayloadHashMap() {
        return responsePayloadHashMap;
    }
}
