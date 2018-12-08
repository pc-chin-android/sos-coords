package com.pcchin.soscoords.contactlist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities= {ContactListEntity.class}, version=1, exportSchema=false)
public abstract class ContactListDatabase extends RoomDatabase {
    public abstract ContactListDao daoAccess();
}
