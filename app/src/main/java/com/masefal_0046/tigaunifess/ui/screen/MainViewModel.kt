package com.masefal_0046.tigaunifess.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masefal_0046.tigaunifess.database.FessRepository
import com.masefal_0046.tigaunifess.model.Pesan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val fessRepository: FessRepository) : ViewModel() {
    val data: StateFlow<List<Pesan>> = fessRepository.allPesan.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue =emptyList()
    )

    suspend fun getPesanById(id: Long): Pesan? {
        return fessRepository.allPesan.first().find { it.id == id }
    }

    fun deletePesan(pesan: Pesan) {
        viewModelScope.launch {
            fessRepository.moveToTrash(pesan.id)
        }
    }
}