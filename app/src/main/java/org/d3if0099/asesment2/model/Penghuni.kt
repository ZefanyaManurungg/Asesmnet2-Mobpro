package org.d3if0099.asesment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "penghuni")
data class Penghuni(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val nokamar: String,
    val lantai: String,
    val gedung: String
)