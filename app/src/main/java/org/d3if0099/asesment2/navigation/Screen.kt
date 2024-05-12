package org.d3if0099.asesment2.navigation

import org.d3if0099.asesment2.ui.screen.KEY_ID_PENGHUNI

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_PENGHUNI}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}