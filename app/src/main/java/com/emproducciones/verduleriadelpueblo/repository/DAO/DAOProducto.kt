package com.emproducciones.verduleriadelpueblo.repository.DAO

import androidx.room.*
import com.emproducciones.verduleriadelpueblo.modelo.histVtas
import com.emproducciones.verduleriadelpueblo.modelo.producto
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface DAOProducto {

    @Query("SELECT * FROM producto ORDER BY nombre")
    fun getProductos(): Flowable<List<producto>>

    @Insert
    fun insertProducto(prod: producto): Maybe<Long>

    @Update(entity = producto::class)
    fun updateProdu(vararg prod: producto):Completable

    @Insert
    fun insertVta(vta: histVtas): Maybe<Long>

    @Query("SELECT * FROM histVtas")
    fun getTodasVtas(): Flowable<List<histVtas>>

}