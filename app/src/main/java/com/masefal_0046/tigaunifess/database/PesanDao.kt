package com.masefal_0046.tigaunifess.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.masefal_0046.tigaunifess.model.Pesan
import kotlinx.coroutines.flow.Flow

@Dao
interface PesanDao {
    @Insert
    suspend fun insert(pesan: Pesan)

    @Update
    suspend fun update(pesan: Pesan)

    @Query("SELECT * FROM pesan WHERE isDelete = 0 ORDER BY tanggal DESC")
    fun getAllPesan(): Flow<List<Pesan>>

    @Query("SELECT * FROM pesan WHERE isDelete = 1 ORDER BY tanggal DESC")
    fun getTrashPesan(): Flow<List<Pesan>>

    @Query("UPDATE pesan SET isDelete = 1 WHERE id = :id")
    suspend fun moveToTrash(id: Int)

    @Query("UPDATE pesan SET isDelete = 0 WHERE id = :id")
    suspend fun restoreFromTrash(id: Int)

    @Delete
    suspend fun deletePermanen(pesan: Pesan)

}