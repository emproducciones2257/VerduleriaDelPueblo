package com.emproducciones.verduleriadelpueblo.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.emproducciones.verduleriadelpueblo.modelo.Converters
import com.emproducciones.verduleriadelpueblo.modelo.histVtas
import com.emproducciones.verduleriadelpueblo.modelo.producto
import com.emproducciones.verduleriadelpueblo.repository.DAO.DAOProducto

@Database(entities = [producto::class, histVtas::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoProducto(): DAOProducto

    companion object{

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "database-name")
                    .build()
            }

            return INSTANCE as AppDatabase
        }
    }
}

