package com.masefal_0046.tigaunifess.database

import com.masefal_0046.tigaunifess.model.Kategori
import com.masefal_0046.tigaunifess.model.Pesan
import com.masefal_0046.tigaunifess.util.SettingDataStore
import kotlinx.coroutines.flow.Flow

class FessRepository(
    private val pesanDao: PesanDao,
    private val kategoriDao: KategoriDao,
    private val dataStore: SettingDataStore
) {
    val allPesan: Flow<List<Pesan>> = pesanDao.getAllPesan()
    val trashPesan: Flow<List<Pesan>> = pesanDao.getTrashPesan()

    suspend fun insertPesan(pesan: Pesan) = pesanDao.insert(pesan)
    suspend fun updatePesan(pesan: Pesan) = pesanDao.update(pesan)
    suspend fun moveToTrash(id: Int) = pesanDao.moveToTrash(id)
    suspend fun restoreFromTrash(id: Int) = pesanDao.restoreFromTrash(id)
    suspend fun deletePermanen(pesan: Pesan) = pesanDao.deletePermanen(pesan)

    val allKategori: Flow<List<Kategori>> = kategoriDao.getAllKategori()
    suspend fun insertKategori(kategori: Kategori) = kategoriDao.insert(kategori)

    val layoutFlow: Flow<Boolean> = dataStore.layoutFlow
    suspend fun saveLayout(isList: Boolean) = dataStore.saveLayout(isList)
}