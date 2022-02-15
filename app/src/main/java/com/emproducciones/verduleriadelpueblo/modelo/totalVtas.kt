package com.emproducciones.verduleriadelpueblo.modelo

import java.util.*

class totalVtas {

    private val idVenta: Int
    private val total: Double
    private val fecha: Date

    constructor(idVenta: Int, total: Double, fecha: Date) {
        this.idVenta = idVenta
        this.total = total
        this.fecha = fecha
    }
}