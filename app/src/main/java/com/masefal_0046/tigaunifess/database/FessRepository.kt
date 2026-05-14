package com.masefal_0046.tigaunifess.database

import com.masefal_0046.tigaunifess.model.Kategori
import com.masefal_0046.tigaunifess.model.Pesan
import kotlinx.coroutines.flow.Flow

class FessRepository(private val db: TigaUniFessDb) {
    val allPesan: Flow<List<Pesan>> = db.pesanDao.getAllPesan()
    val trashPesan: Flow<List<Pesan>> = db.pesanDao.getTrashPesan()

    suspend fun insertPesan(pesan: Pesan) = db.pesanDao.insert(pesan)
    suspend fun updatePesan(pesan: Pesan) = db.pesanDao.update(pesan)
    suspend fun moveToTrash(id: Int) = db.pesanDao.moveToTrash(id)
    suspend fun restoreFromTrash(id: Int) = db.pesanDao.restoreFromTrash(id)
    suspend fun deletePermanen(pesan: Pesan) = db.pesanDao.deletePermanen(pesan)

    val allKategori: Flow<List<Kategori>> = db.kategoriDao.getAllKategori()

    suspend fun insertKategori(kategori: Kategori) = db.kategoriDao.insert(kategori)
}