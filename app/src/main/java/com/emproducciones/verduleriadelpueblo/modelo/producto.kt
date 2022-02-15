package com.emproducciones.verduleriadelpueblo.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class producto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo var idProducto: Int=0,
    @ColumnInfo var nombre: String="",
    @ColumnInfo var imagen: String="",
    @ColumnInfo var precio: Double=0.0,
    @ColumnInfo var categoria: String="",
    @ColumnInfo var uniVenta: String="",
    @ColumnInfo var cantidadPromocion: Byte=0,
    @ColumnInfo var precioPromocion: Double=1.0)

