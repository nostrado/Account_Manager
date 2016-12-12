package com.example.mar1s.account_manager.models;

import io.realm.RealmObject;

/**
 * Created by mar1s on 2016-12-12.
 */

public class User extends RealmObject {
    private String pattern;
    private String password;

    public void setPattern(String _pattern) {
        this.pattern = _pattern;
    }

    public void setPassword(String _password) {
        this.password = _password;
    }

    public String getPattern() {
        return pattern;
    }

    public String getPassword() {
        return password;
    }
}
