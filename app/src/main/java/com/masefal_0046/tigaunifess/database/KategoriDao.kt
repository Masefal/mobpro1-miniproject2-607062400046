package com.masefal_0046.tigaunifess.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.masefal_0046.tigaunifess.model.Kategori
import kotlinx.coroutines.flow.Flow

@Dao
interface KategoriDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(kategori: Kategori)

    @Query("SELECT * FROM kategori ORDER BY nama ASC")
    fun getAllKategori(): Flow<List<Kategori>>

    @Delete
    suspend fun delete(kategori: Kategori)
}