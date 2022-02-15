package com.emproducciones.verduleriadelpueblo.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class histVtas(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo var fecha: Date?,
    @ColumnInfo var precio: Double=0.0
)