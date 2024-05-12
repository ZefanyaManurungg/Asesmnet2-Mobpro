package org.d3if0099.asesment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if0099.asesment2.model.Penghuni

@Database(entities = [Penghuni::class], version = 1, exportSchema = false)
abstract class PenghuniDb: RoomDatabase() {
    abstract val dao: PenghuniDao

    companion object{
        @Volatile
        private var INSTANCE: PenghuniDb? = null

        fun getInstance(context: Context): PenghuniDb{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PenghuniDb::class.java,
                        "penghuni.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}