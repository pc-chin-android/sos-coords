package com.pcchin.soscoords.contactlist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;

@Dao
public interface ContactListDao {
    @Query("SELECT ContactNumList FROM ContactListEntity WHERE ContactId LIKE :ContactId LIMIT 1")
    String getContactNum(String ContactId); // String contains JSON Array

    @Query("SELECT * FROM ContactListEntity")
    ArrayList<ContactListEntity> getAllContacts();

    @Query("SELECT ContactId FROM ContactListEntity")
    ArrayList<String> getAllContactsId();

    @Query("SELECT ContactNumList FROM ContactListEntity")
    ArrayList<String> getAllContactsNum(); // String contains JSON Array

    @Insert
    void insertContact(ContactListEntity contact);

    @Query("DELETE FROM ContactListEntity")
    void nukeAllContacts();
}
