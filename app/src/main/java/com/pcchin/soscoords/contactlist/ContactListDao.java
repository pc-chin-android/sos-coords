package com.pcchin.soscoords.contactlist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ContactListDao {
    @Query("SELECT ContactId FROM ContactListEntity")
    List<String> getAllContactsId();

    @Query("SELECT ContactNumList FROM ContactListEntity")
    List<String> getAllContactsNum(); // String contains Array

    @Insert
    void insertContact(ContactListEntity contact);

    @Query("DELETE FROM ContactListEntity")
    void nukeAllContacts();
}
