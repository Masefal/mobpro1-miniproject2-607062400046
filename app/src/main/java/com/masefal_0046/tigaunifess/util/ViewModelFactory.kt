package com.masefal_0046.tigaunifess.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.masefal_0046.tigaunifess.database.FessRepository
import com.masefal_0046.tigaunifess.database.TigaUniFessDb
import com.masefal_0046.tigaunifess.ui.screen.DetailViewModel
import com.masefal_0046.tigaunifess.ui.screen.MainViewModel

class ViewModelFactory (
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = TigaUniFessDb.getInstance(context)
        val settingDataStore = SettingDataStore(context)
        val repo = FessRepository(
            db.pesanDao,
            db.kategoriDao,
            settingDataStore
        )
        return when {
            modelClass == MainViewModel::class.java -> {
                MainViewModel(repo) as T
            }
            modelClass == DetailViewModel::class.java -> {
                DetailViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }

    }
}