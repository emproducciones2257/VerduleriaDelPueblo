package com.emproducciones.verduleriadelpueblo.impresion

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.emproducciones.verduleriadelpueblo.MainApplication

class bluetooth {

    private val bAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    fun verificarBT (){

        if (bAdapter == null) {
            Toast.makeText(MainApplication.applicationContext(),"Aca hay nooo blue carajo",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(MainApplication.applicationContext(),"Aca tenemps blue carajo",Toast.LENGTH_SHORT).show()
        }
    }


}