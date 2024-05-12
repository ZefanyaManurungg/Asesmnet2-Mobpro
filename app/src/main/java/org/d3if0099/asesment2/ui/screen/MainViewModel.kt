package org.d3if0099.asesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if0099.asesment2.database.PenghuniDao
import org.d3if0099.asesment2.model.Penghuni

class MainViewModel(dao: PenghuniDao) : ViewModel() {

    val data: StateFlow<List<Penghuni>> = dao.getMahasiswa().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}