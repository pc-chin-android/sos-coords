package com.pcchin.soscoords.contactlist;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@SuppressWarnings("ALL")
@Entity
public class ContactListEntity {
    @NonNull
    @PrimaryKey
    public String ContactId;

    public String ContactNumList;

    public ContactListEntity() {}

}
