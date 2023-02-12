package com.jobjournal.JobJournal.shared.models.validation;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class FullFilterValidationObject {
    @NotNull(message = "Text cannot be null.")
    @Size(max = 6000, message = "Maximum of 6000 characters.")
    @Pattern(regexp = "^[^&<>`]*$", message = "Text cannot contain the following characters: ^<>`&")
    private String _text;
    private List<String> _filter_tags;

    public FullFilterValidationObject(String _text, List<String> _filter_tags) {
        this._text = _text;
        this._filter_tags = _filter_tags;
    }

    public String get_text() {
        return _text;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public List<String> get_filter_tags() {
        return _filter_tags;
    }

    public void set_filter_tags(List<String> _filter_tags) {
        this._filter_tags = _filter_tags;
    }
}
