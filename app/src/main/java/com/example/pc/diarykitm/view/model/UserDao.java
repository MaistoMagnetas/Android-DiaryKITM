package com.example.pc.diarykitm.view.model;

import android.database.Cursor;

import java.util.List;

/**
 * Created by PC on 4/7/2018.
 */

public interface UserDao {
    //Add user tp DB
    boolean addUser(User user);
    //Get users for login informaition
    Cursor getUserData();
}
