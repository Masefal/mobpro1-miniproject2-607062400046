package com.masefal_0046.tigaunifess.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
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
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TigaUniFessDb::class.java,
                    "tigaunifess"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}