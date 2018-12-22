package com.pcchin.soscoords.contactlist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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
