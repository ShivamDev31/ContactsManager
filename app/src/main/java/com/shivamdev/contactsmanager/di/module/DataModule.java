package com.shivamdev.contactsmanager.di.module;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import com.shivamdev.contactsmanager.db.DbHelper;
import com.shivamdev.contactsmanager.db.LocalDataStore;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

/**
 * Created by shivam on 3/2/17.
 */

@Module
public class DataModule {

    @Provides
    @Singleton
    SQLiteOpenHelper provideSqliteOpenHelper(Application application) {
        return new DbHelper(application);
    }

    @Provides
    @Singleton
    SqlBrite provideSqlBrite() {
        return new SqlBrite.Builder().build();
    }

    @Provides
    @Singleton
    BriteDatabase provideBriteDatabase(SQLiteOpenHelper helper, SqlBrite sqlBrite) {
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
        db.setLoggingEnabled(true);
        return db;
    }

    @Provides
    @Singleton
    LocalDataStore provideLocalDataStore(BriteDatabase db) {
        return new LocalDataStore(db);
    }
}