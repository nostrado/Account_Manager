package com.example.mar1s.account_manager;

import android.content.Context;

import com.example.mar1s.account_manager.models.Account;
import com.example.mar1s.account_manager.models.User;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by mar1s on 2016-12-14.
 */

public class DAO {
    private Realm realm;
    private static DAO obj;

    private DAO() {    }

    public static DAO sharedInstance() {
        if(obj == null) {
            obj = new DAO();
        }
        return obj;
    }

    public void initDAO(Context context) {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void colseDAO() {
        realm.close();
    }

    public void enrollUser(final String _pattern, final String _password) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.createObject(User.class);
                user.setPattern(_pattern);
                user.setPassword(_password);
            }
        });
    }

    public void enrollAccount(final String _domain, final String _id, final String _pw, final String _name) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Account account = realm.createObject(Account.class);
                account.setDomain(_domain);
                account.setUserID(_id);
                account.setUserPW(_pw);
                account.setUserName(_name);
            }
        });
    }

    public User getUser() {
        User user = realm.where(User.class).findFirst();
        return user;
    }

    public void updateUserPassword(final String password) {
        final User user = realm.where(User.class).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                user.setPassword(password);
            }
        });
    }

    public RealmResults<Account> getAccountList(String domain) {
        RealmResults<Account> results = realm.where(Account.class).equalTo("domain", domain).findAll();
        return  results;
    }

    public void deleteAlldata() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    public void deleteAllAccount() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Account.class);
            }
        });
    }
}
