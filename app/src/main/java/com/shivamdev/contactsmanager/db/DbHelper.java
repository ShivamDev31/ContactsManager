package com.shivamdev.contactsmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shivamdev.contactsmanager.common.constants.DbConstants;
import com.shivamdev.contactsmanager.common.constants.DbConstants.ContactsTable;


/**
 * Created by shivam on 10/12/16.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String CREATE_CONTACTS_TABLE = ""
            + "CREATE TABLE "
            + DbConstants.CONTACTS_TABLE
            + "("
            + ContactsTable.ID
            + " INTEGER NOT NULL PRIMARY KEY,"
            + ContactsTable.CONTACT_ID
            + " TEXT NOT NULL,"
            + ContactsTable.FIRST_NAME
            + " TEXT NOT NULL,"
            + ContactsTable.LAST_NAME
            + " TEXT NOT NULL,"
            + ContactsTable.PHONE_NUMBER
            + " TEXT,"
            + ContactsTable.EMAIL
            + " TEXT,"
            + ContactsTable.PROFILE_URL
            + " TEXT,"
            + ContactsTable.IS_FAVOURITE
            + " INTEGER NOT NULL DEFAULT "
            + Db.BOOLEAN_FALSE + ","
            + ContactsTable.CREATED_AT
            + " TEXT,"
            + ContactsTable.UPDATED_AT
            + " TEXT"
            + ")";

    static final String GET_ALL_CONTACTS =
            "SELECT * FROM " + DbConstants.CONTACTS_TABLE;

    static final String GET_ALL_FAVOURITE_CONTACTS =
            "SELECT * FROM " + DbConstants.CONTACTS_TABLE
                    + " WHERE " + ContactsTable.IS_FAVOURITE + " = " + Db.BOOLEAN_TRUE;

    static final String GET_CONTACT_BY_CONTACT_ID =
            "SELECT * FROM "
                    + DbConstants.CONTACTS_TABLE
                    + " WHERE "
                    + ContactsTable.CONTACT_ID + "=?";

    public DbHelper(Context context) {
        super(context, DbConstants.DB_NAME, null, DbConstants.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
