package com.pcchin.soscoords.contactlist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@SuppressWarnings("ALL")
@Entity
public class ContactListEntity {
    @NonNull
    @PrimaryKey
    private String ContactId;

    private String ContactNumList;

    public ContactListEntity() {}

    public String getContactId() {return ContactId;}

    public String getContactNumList() {return ContactNumList;}

    public void setContactId(String ContactId) {this.ContactId = ContactId;}

    public void setContactNumList(String ContactNumList) {this.ContactNumList = ContactNumList;}

}
