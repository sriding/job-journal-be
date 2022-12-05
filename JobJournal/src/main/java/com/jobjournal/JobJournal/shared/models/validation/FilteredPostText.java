package com.jobjournal.JobJournal.shared.models.validation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class FilteredPostText {

    @NotNull(message = "Text cannot be null.")
    @Size(max = 6000, message = "Maximum of 6000 characters.")
    @Pattern(regexp = "^[^&<>`]*$", message = "Text cannot contain the following characters: ^<>`&")
    private String _text;

    public FilteredPostText() {
    }

    public FilteredPostText(String _text) {
        this._text = _text;
    }

    public String get_text() {
        return _text;
    }

    public void set_text(String _text) {
        this._text = _text;
    }
}
