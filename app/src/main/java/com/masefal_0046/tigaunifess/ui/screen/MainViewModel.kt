package com.masefal_0046.tigaunifess.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masefal_0046.tigaunifess.database.KategoriDao
import com.masefal_0046.tigaunifess.database.PesanDao
import com.masefal_0046.tigaunifess.model.Pesan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(pesanDao: PesanDao, kategoriDao: KategoriDao) : ViewModel() {
    val data: StateFlow<List<Pesan>> = pesanDao.getAllPesan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue =emptyList()
    )

    fun hapusPesan(id: Int) {
        viewModelScope.launch {
            
        }
    }
}