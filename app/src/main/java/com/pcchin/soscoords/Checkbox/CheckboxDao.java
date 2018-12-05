package com.pcchin.soscoords.Checkbox;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

// Read/update checkbox status in database (DAO)
@Dao
public interface CheckboxDao {
    @Query("SELECT * FROM CheckboxStates WHERE CheckboxId = :CheckboxId")
    boolean getCheckboxState(int CheckboxId);

    @Query("UPDATE CheckboxStates SET Checked = :CheckboxChecked WHERE CheckboxId = :CheckboxId")
    void updateCheckboxState(int CheckboxId, boolean CheckboxChecked);

    @Insert
    void addCheckboxState(boolean CheckboxChecked);

    @Query("SELECT COUNT(*) FROM CheckboxStates")
    int checkDatabaseLen();
}


