package com.masefal_0046.tigaunifess.navigation

const val KEY_ID_TELUFESS = "idTigaUniFess"
sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")

}