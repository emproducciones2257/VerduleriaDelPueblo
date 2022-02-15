package com.emproducciones.verduleriadelpueblo.modelo

import android.os.Parcel
import android.os.Parcelable

class vtaProd() : Parcelable{
    var produ = producto()
    var cantidad = 0.0
    var total = 0.0

    constructor(parcel: Parcel) : this() {
        cantidad = parcel.readDouble()
        total = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(cantidad)
        parcel.writeDouble(total)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<vtaProd> {
        override fun createFromParcel(parcel: Parcel): vtaProd {
            return vtaProd(parcel)
        }

        override fun newArray(size: Int): Array<vtaProd?> {
            return arrayOfNulls(size)
        }
    }
}