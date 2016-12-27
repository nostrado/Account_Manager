package com.example.mar1s.account_manager.models;

import io.realm.RealmObject;

/**
 * Created by mar1s on 2016-12-27.
 */

public class Account extends RealmObject {
    private String domain;
    private String userID;
    private String userName;
    private String userPW;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPW() {
        return userPW;
    }

    public void setUserPW(String userPW) {
        this.userPW = userPW;
    }
}
