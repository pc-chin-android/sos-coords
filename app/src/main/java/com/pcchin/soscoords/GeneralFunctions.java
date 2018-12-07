package com.pcchin.soscoords;

import android.arch.persistence.room.TypeConverter;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class GeneralFunctions {
    // ****** Converters ****** //

    @TypeConverter
    ArrayList<String> string_json_arraylist(String original) {
        return null;
    }

    @TypeConverter
    String arraylist_json_string(ArrayList<String> original) {
        return null;
    }

    // ****** GENERAL CONTACT GETTERS ****** //

    // Get all names of all contacts in list form (sorted)
    static ArrayList<ArrayList<String>> getContactNames(@NonNull Context context) {
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        ArrayList<ArrayList<String>> response = new ArrayList<>();
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur.moveToNext()) {
                ArrayList<String> temp = new ArrayList<>();
                // Add <id, name> to array
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                temp.add(id);
                temp.add(name);
                response.add(temp);
            }
            // Sort array by name
            Collections.sort(response, new NameComparator ());
        }
        if(cur!=null){
            cur.close();
        }
        return response;
    }

    // Get phone numbers of contacts in dictionary
    static HashMap getContactNumbers(@NonNull Context context) {
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        HashMap <String, ArrayList<String>> response = new HashMap<>();
        ArrayList<String> temp = new ArrayList<>();

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    if (pCur != null) {
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            temp.add(phoneNo);
                        }
                        pCur.close();
                    }
                }

                response.put(id, temp);
                temp.clear();
            }
        }
        if(cur!=null){
            cur.close();
        }
        return response;
    }
}
