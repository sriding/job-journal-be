package com.jobjournal.JobJournal.shared.models.auth;

public class Auth0UserInfoResponse {
    private String sub;
    private String given_name;
    private String family_name;
    private String nickname;
    private String name;
    private String picture;
    private String locale;
    private String updated_at;
    private String email;
    private boolean email_verified;

    public Auth0UserInfoResponse(String sub, String given_name, String family_name, String nickname, String name,
            String picture, String locale, String updated_at, String email, boolean email_verified) {
        this.sub = sub;
        this.given_name = given_name;
        this.family_name = family_name;
        this.nickname = nickname;
        this.name = name;
        this.picture = picture;
        this.locale = locale;
        this.updated_at = updated_at;
        this.email = email;
        this.email_verified = email_verified;
    }

    public Auth0UserInfoResponse() {
    };

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }
}
