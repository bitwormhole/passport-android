package com.bitwormhole.passport.supports.db;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class RootDatabaseMigrationFactory {

    public static Migration[] getMigrations() {
        Migration v1v2 = for_v1_v2();
        return new Migration[]{
                v1v2
        };
    }

    private static Migration for_v1_v2() {
        return new Migration(1, 2) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {

                // database.execSQL("DROP TABLE UserEntity");
                // database.execSQL("DROP TABLE UserSpaceEntity");
            }
        };
    }

}
