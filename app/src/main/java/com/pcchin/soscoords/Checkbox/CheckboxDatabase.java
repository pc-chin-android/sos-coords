package com.pcchin.soscoords.Checkbox;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {CheckboxStates.class}, version = 1, exportSchema = false)
public abstract class CheckboxDatabase extends RoomDatabase {
    public abstract CheckboxDao checkboxDao();
}
