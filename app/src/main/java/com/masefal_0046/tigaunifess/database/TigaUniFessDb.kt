package com.masefal_0046.tigaunifess.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.masefal_0046.tigaunifess.model.Kategori
import com.masefal_0046.tigaunifess.model.Pesan

@Database(
    entities = [Kategori::class, Pesan::class],
    version = 1,
    exportSchema = false
)

abstract class TigaUniFessDb : RoomDatabase() {
    abstract val kategoriDao: KategoriDao
    abstract val pesanDao: PesanDao

    companion object {
        @Volatile
        private var INSTANCE: TigaUniFessDb? = null

        fun getInstance(context: Context): TigaUniFessDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TigaUniFessDb::class.java,
                        "tigaunifess.db"
                    ).fallbackToDestructiveMigration(false)
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                // Query SQL untuk isi data awal otomatis
                                db.execSQL("INSERT OR IGNORE INTO kategori (id, nama) VALUES (1, 'Akademik'), (2, 'Organisasi'), (3, 'Kosan'), (4, 'Cinta')")
                            }
                        }).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}