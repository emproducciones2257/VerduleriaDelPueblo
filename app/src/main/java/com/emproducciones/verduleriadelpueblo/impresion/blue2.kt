package com.emproducciones.verduleriadelpueblo.impresion

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.emproducciones.verduleriadelpueblo.MainApplication


class blue2 : AppCompatActivity(){

    var bAdapter = BluetoothAdapter.getDefaultAdapter()
    val REQUEST_CODE_ENABLE_BT = 1
    val REQUEST_CODE_DISCOBERABLE_ENABLE_BT = 2

    @RequiresApi(Build.VERSION_CODES.M)

    fun inicial(){
        //check if bluetotoot is available or not
        if(bAdapter==null){
            Toast.makeText(
                MainApplication.applicationContext(),
                "Bluetooth not available",
                Toast.LENGTH_SHORT
            ).show()

        }else Toast.makeText(
            MainApplication.applicationContext(),
            "Bluetooth is available",
            Toast.LENGTH_SHORT
        ).show()

        // Quick permission check
       /* var permissionCheck = checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION")
        permissionCheck += checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION")
        if (permissionCheck != 0) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1001) //Any number
        }*/

        // Create a BroadcastReceiver for ACTION_FOUND.
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action: String? = intent.action
                when(action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        println("ENCONTREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE")
                        // Discovery has found a device. Get the BluetoothDevice
                        // object and its info from the Intent.
                        val device: BluetoothDevice? =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        val deviceName = device?.name
                        val deviceHardwareAddress = device?.address // MAC address
                        println(deviceName)
                    }
                }
            }
        }
       // val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        //registerReceiver(receiver, filter)
        //turn on blueto

    }

        //turn off bluetooo
        fun apagarBT() {
            if (!bAdapter.isEnabled)
                Toast.makeText(this, "Alredy Off", Toast.LENGTH_SHORT).show()
            else {
                bAdapter.disable()
                Toast.makeText(this, "Bluetoot Apagau", Toast.LENGTH_SHORT).show()

            }
        }

        //discoverable the bluet
        fun discoverarBT() {
            if (!bAdapter.isDiscovering) {
                Toast.makeText(this, "Making Your device discoverable", Toast.LENGTH_SHORT).show()
                var intet = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent, REQUEST_CODE_DISCOBERABLE_ENABLE_BT)
            }
        }

        fun pairedBT() {
            if (bAdapter.isEnabled) {
                println("paired BT")

                //get liste paired devices
                val devices = bAdapter.bondedDevices
                for (device in devices) {
                    val deviceName = device.name
                    val deviceAddre = device
                    println("paired BT: " + deviceName)
                }
            } else {
                Toast.makeText(this, "Turn on Bluetoot firts", Toast.LENGTH_SHORT).show()
            }
        }

        fun aBuscar() {
            if (bAdapter.isDiscovering) {
                Toast.makeText(this, "Ya esta buscando dispositivos", Toast.LENGTH_SHORT).show()
            } else {
                bAdapter.startDiscovery()
                Toast.makeText(this, "Buscando dispo", Toast.LENGTH_SHORT).show()

            }
        }

    fun encenderBT() {

        if (!bAdapter.isEnabled()) {
            val alertBuilder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(MainApplication.applicationContext())
            alertBuilder.setCancelable(true)
            alertBuilder.setMessage("Do you want to enable bluetooth")
            alertBuilder.setNeutralButton("OK",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    bAdapter.enable()
                })
            val alert: android.app.AlertDialog? = alertBuilder.create()
            alert?.show()
        }
        /*if (bAdapter.isEnabled)
            Toast.makeText(this, "Alredy On", Toast.LENGTH_SHORT).show()
        else {
            var intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            REQUEST_CODE_ENABLE_BT ->
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Se prendio che", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "no se prendio che", Toast.LENGTH_SHORT).show()
                }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}

