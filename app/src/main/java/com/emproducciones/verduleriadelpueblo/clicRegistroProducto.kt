package com.emproducciones.verduleriadelpueblo

import com.emproducciones.verduleriadelpueblo.modelo.producto
import com.emproducciones.verduleriadelpueblo.modelo.vtaProd

interface clicRegistroProducto {

    fun clicRegProducto(prod:producto)
    fun aDUpdate(prod: producto)
    fun mantenerLista(ele: vtaProd)
    fun updateLista(prod: producto)
}