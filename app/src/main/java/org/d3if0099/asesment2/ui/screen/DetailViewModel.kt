package org.d3if0099.asesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0099.asesment2.database.PenghuniDao
import org.d3if0099.asesment2.model.Penghuni


class DetailViewModel(private val dao: PenghuniDao) : ViewModel() {

    fun insert(nama: String, nokamar: String, lantai: String, gedung: String) {
        val mahasiswa = Penghuni(
            nama = nama,
            nokamar = nokamar,
            lantai = lantai,
            gedung = gedung
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(mahasiswa)
        }
    }

    suspend fun getMahasiswa(id: Long): Penghuni? {
        return dao.getMahasiswaById(id)
    }

    fun update(id: Long, nama: String, nokamar: String, lantai: String, gedung: String) {
        val mahasiswa = Penghuni(
            id = id,
            nama = nama,
            nokamar = nokamar,
            lantai = lantai,
            gedung = gedung
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(mahasiswa)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }


}