package com.masefal_0046.tigaunifess.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.masefal_0046.tigaunifess.database.TigaUniFessDb

class ViewModelFactory (
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = TigaUniFessDb.getInstance(context).pesanDao

    }
}