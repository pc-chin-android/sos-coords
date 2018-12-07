package com.pcchin.soscoords.contactlist;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ContactListEntity {
    @PrimaryKey
    public String ContactId;

    public String ContactNumList;

    public ContactListEntity() {}

}
