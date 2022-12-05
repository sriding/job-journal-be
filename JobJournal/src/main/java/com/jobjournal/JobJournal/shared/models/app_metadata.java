package com.jobjournal.JobJournal.shared.models;

public class app_metadata {
    private Boolean _deactivated;

    public app_metadata(Boolean activation) {
        this._deactivated = activation;
    }

    public Boolean get_deactivated() {
        return _deactivated;
    }

    public void set_deactivated(Boolean _deactivated) {
        this._deactivated = _deactivated;
    }
}