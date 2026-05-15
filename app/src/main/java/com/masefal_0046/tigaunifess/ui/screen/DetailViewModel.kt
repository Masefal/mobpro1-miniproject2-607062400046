package com.masefal_0046.tigaunifess.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masefal_0046.tigaunifess.database.FessRepository
import com.masefal_0046.tigaunifess.model.Kategori
import com.masefal_0046.tigaunifess.model.Pesan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val fessRepository: FessRepository) : ViewModel() {
    fun insert(konten: String, pengirim: String, idKategori: Long) {
        val pesan = Pesan(
            konten = konten,
            pengirim = if (pengirim.isBlank()) "Anonymous" else pengirim,
            idKategori = idKategori
        )

        viewModelScope.launch(Dispatchers.IO) {
            fessRepository.insertPesan(pesan)
        }
    }

    suspend fun getPesan(id: Long): Pesan? {
        return withContext(Dispatchers.IO) {
            fessRepository.allPesan.first().find { it.id == id }
        }
    }

    fun update(id: Long, konten: String, pengirim: String, idKategori: Long) {
        val pesan = Pesan(
            id = id,
            konten = konten,
            pengirim = if (pengirim.isBlank()) "Anonymous" else pengirim,
            idKategori = idKategori,
            isDelete = false
        )

        viewModelScope.launch(Dispatchers.IO) {
            fessRepository.updatePesan(pesan)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO)  {
            fessRepository.moveToTrash(id)
        }
    }

    fun seedKategori() {
        viewModelScope.launch(Dispatchers.IO) {
            fessRepository.insertKategori(Kategori(id = 1L, nama = "Akademik"))
            fessRepository.insertKategori(Kategori(id = 2L, nama = "Organisasi"))
            fessRepository.insertKategori(Kategori(id = 3L, nama = "Kosan"))
            fessRepository.insertKategori(Kategori(id = 4L, nama = "Cinta"))
        }
    }
}