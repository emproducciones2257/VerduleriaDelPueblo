package com.emproducciones.verduleriadelpueblo.repository

import com.emproducciones.verduleriadelpueblo.MainApplication
import com.emproducciones.verduleriadelpueblo.modelo.histVtas
import com.emproducciones.verduleriadelpueblo.modelo.producto
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class repositorio {

    private lateinit var Db: AppDatabase

    fun setProdu(produ: producto):Maybe<Long>{

        Db = AppDatabase.getInstance(MainApplication.applicationContext())

        return Db.daoProducto().insertProducto(produ).
        subscribeOn(Schedulers.io()).
        observeOn(AndroidSchedulers.mainThread())
    }

    fun updateProdu(produ: producto):Completable{

        Db = AppDatabase.getInstance(MainApplication.applicationContext())

        return Db.daoProducto().updateProdu(produ).
        subscribeOn(Schedulers.io()).
        observeOn(AndroidSchedulers.mainThread())
    }

    fun getProdu (): Flowable<List<producto>> {
        Db = AppDatabase.getInstance(MainApplication.applicationContext())

        return Db.daoProducto().
        getProductos().
        subscribeOn(Schedulers.io()).
        observeOn(AndroidSchedulers.mainThread())
    }

    fun setVta(vta: histVtas):Maybe<Long>{

        Db = AppDatabase.getInstance(MainApplication.applicationContext())

        return Db.daoProducto().insertVta(vta).
        subscribeOn(Schedulers.io()).
        observeOn(AndroidSchedulers.mainThread())
    }

    fun getVtas (): Flowable<List<histVtas>> {
        Db = AppDatabase.getInstance(MainApplication.applicationContext())

        return Db.daoProducto().
        getTodasVtas().
        subscribeOn(Schedulers.io()).
        observeOn(AndroidSchedulers.mainThread())
    }
}