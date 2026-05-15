package com.masefal_0046.tigaunifess

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.masefal_0046.tigaunifess.navigation.SetupNavGraph
import com.masefal_0046.tigaunifess.ui.theme.TigaUniFessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TigaUniFessTheme {
                SetupNavGraph(rememberNavController())
            }
        }
    }
}
