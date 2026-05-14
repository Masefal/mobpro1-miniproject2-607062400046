package com.masefal_0046.tigaunifess.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.masefal_0046.tigaunifess.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)


}