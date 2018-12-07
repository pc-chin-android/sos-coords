package com.pcchin.soscoords.contactlist;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class ContactListEntity {
    @PrimaryKey
    public int ContactId;
    public ArrayList<String> ContactNumList;

}
