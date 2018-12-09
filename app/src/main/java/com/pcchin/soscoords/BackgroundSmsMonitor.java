package com.pcchin.soscoords;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.pcchin.soscoords.contactlist.ContactListDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackgroundSmsMonitor extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BackgroundSmsMonitor(String name) {
        super(name);
    }

    @Override
        protected void onHandleIntent(Intent workIntent) {
            // TODO: SEE BELOW
            // Add event notifier for new sms
            // Check checkbox status
            // Check sender matches number in list
            // Check SMS content matches code
            // Send reply (Reply 0 to exit, 1 for gps coordinates, 2 for ringing phone)
            // Wait for 1st reply
            // If reply is either 1 or two, do corresponding action
            // Update database when MainEvent calls for
            // When booted, go to step 2
        }

        @NonNull
        private List<String> updateNumberList() {
            // Access database
            ContactListDatabase contactListDatabase = Room.databaseBuilder(getApplicationContext(), ContactListDatabase.class, "current_contact_list").build();
            List<String> updateNumberString = contactListDatabase.daoAccess().getAllContactsNum();
            contactListDatabase.close();

            // Convert String back to array
            List<String> responseArray = new ArrayList<>();
            //Merge all arrays into one
            for (int i=0; i < updateNumberString.size(); i++) {
                // Convert current phone number stored in contacts (String to Array)
                List<String> responseArrayCurrent = new ArrayList<>(Arrays.asList(updateNumberString.get(i).split("; ")));
                // Append all values in current array to final array
                responseArray.addAll(responseArrayCurrent);
            }
            return responseArray;
        }
}