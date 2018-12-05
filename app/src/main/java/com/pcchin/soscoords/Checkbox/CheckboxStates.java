package com.pcchin.soscoords.Checkbox;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

// Initialize checkbox status in database (Entity)
@Entity
public class CheckboxStates {
    @NonNull
    @PrimaryKey
    public int CheckboxId;

    @ColumnInfo (name="Checked")
    public Boolean CheckboxChecked = true;
}