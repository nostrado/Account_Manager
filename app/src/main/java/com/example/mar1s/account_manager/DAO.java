package com.example.mar1s.account_manager;

import android.content.Context;

import com.example.mar1s.account_manager.models.User;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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

        byte[] key = new byte[64];
        new SecureRandom().nextBytes(key);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().encryptionKey(key).build();
        Realm.deleteRealm(realmConfiguration);
        realm = Realm.getInstance(realmConfiguration);
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

    public User getUser() {
        User user = realm.where(User.class).findFirst();
        return user;
    }

    public void deleteAlldata() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }
}
