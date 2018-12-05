package com.pcchin.soscoords;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

// Store contact id and numbers needed (Entity)
@Entity
public class ActiveContactList {
    @NonNull
    @PrimaryKey
    public int contactId;
    // TODO: Complete Room database
}
