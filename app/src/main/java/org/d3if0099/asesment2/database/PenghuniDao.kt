package org.d3if0099.asesment2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if0099.asesment2.model.Penghuni


@Dao
interface PenghuniDao {
    @Insert
    suspend fun insert(penghuni: Penghuni)

    @Update
    suspend fun update(penghuni: Penghuni)

    @Query("SELECT * FROM penghuni ORDER BY gedung, lantai, nokamar, nama ASC")
    fun getMahasiswa(): Flow<List<Penghuni>>

    @Query("SELECT * FROM penghuni WHERE id = :id")
    suspend fun getMahasiswaById(id: Long): Penghuni?

    @Query("DELETE FROM penghuni WHERE id = :id")
    suspend fun deleteById(id: Long)
}