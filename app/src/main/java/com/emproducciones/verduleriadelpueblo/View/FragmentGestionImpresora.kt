package com.emproducciones.verduleriadelpueblo.View

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.*
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emproducciones.verduleriadelpueblo.MainApplication
import com.emproducciones.verduleriadelpueblo.R
import com.emproducciones.verduleriadelpueblo.View.RecyclerView.RecyclerAdapterBT
import com.emproducciones.verduleriadelpueblo.impresion.evtnBlutu
import kotlinx.android.synthetic.main.fragment_gestion_impresora.*
import kotlinx.android.synthetic.main.fragment_gestion_impresora.view.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class FragmentGestionImpresora : Fragment(), evtnBlutu {

    private lateinit var bAdapter : BluetoothAdapter
    private val REQUEST_CODE_ENABLE_BT = 1
    private val REQUEST_CODE_DISCOBERABLE_ENABLE_BT = 2
    private var listaBT = ArrayList<BluetoothDevice>()
    private lateinit var tete : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Quick permission check
        var permissionCheck = checkSelfPermission(MainApplication.applicationContext(),"Manifest.permission.ACCESS_FINE_LOCATION")
        permissionCheck += checkSelfPermission(MainApplication.applicationContext(),"Manifest.permission.ACCESS_COARSE_LOCATION")
        if (permissionCheck != 0) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1001) //Any number
        }

        // Create a BroadcastReceiver for ACTION_FOUND.
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action: String? = intent.action
                when(action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        // Discovery has found a device. Get the BluetoothDevice
                        // object and its info from the Intent.
                        val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        device?.name?.let { listaBT.add(device) }
                        cargaDtos(listaBT)
                    }
                }
            }
        }

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        requireActivity().registerReceiver(receiver, filter)

        bAdapter = BluetoothAdapter.getDefaultAdapter()
        var vista = inflater.inflate(R.layout.fragment_gestion_impresora, container, false)

        vista.recyBTLista.layoutManager = LinearLayoutManager(MainApplication.applicationContext())

        vista.recyBTLista.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        return vista
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //chequearTengoBT()
        encenderBT()
        leerImpresora(txtImpGuardada)
        btnBuscarBT.setOnClickListener{
            discoverarBT()
            buscarBTNuevos()
        }
    }

    private fun cargaDtos(dato : ArrayList<BluetoothDevice>) {
        val reciclador = RecyclerAdapterBT(dato,this)
        tete.adapter = reciclador;
    }

    private fun chequearTengoBT() {
        //if (bAdapter == null)
            //txtNombres.text = "Bluetooth not available"
         //else txtNombres.text = "Bluetooth is available"
    }

    private fun encenderBT() {
        if (bAdapter.isEnabled)
            Toast.makeText(MainApplication.applicationContext(), "Alredy On", Toast.LENGTH_SHORT).show()
        else {
            var intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
        }
    }

    private fun discoverarBT () {
        if (bAdapter.isEnabled) {
            val devices = bAdapter.bondedDevices
            for (device in devices) {
                listaBT.add(device)
            }
            cargaDtos(listaBT)
        } else {
            Toast.makeText(MainApplication.applicationContext(), "Turn on Bluetoot firts", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buscarBTNuevos()  {
        if (bAdapter.isDiscovering) {
            Toast.makeText(MainApplication.applicationContext(), "Ya esta buscando dispositivos", Toast.LENGTH_SHORT).show()
        } else {
            bAdapter.startDiscovery()
            Toast.makeText(MainApplication.applicationContext(), "Buscando dispo", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        tete = recyBTLista
    }

    override fun clickItemBT(blutu: BluetoothDevice) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("impresora",blutu.name)
            apply()
        }
        leerImpresora(txtImpGuardada)
        ConnectThread(blutu).run()
    }

    fun leerImpresora(texto:TextView) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)

        if (sharedPref != null) {
             texto.text="Impresora Predefinida: "+sharedPref.getString("impresora","")
        }else texto.text="Impresora Predefinida: NO SE SELECCIONO IMPRESORA"
    }

    private inner class ConnectThread(device: BluetoothDevice) : Thread() {

        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
        }

        public override fun run() {
            // Cancel discovery because it otherwise slows down the connection.
            bAdapter?.cancelDiscovery()

            mmSocket?.use { socket ->
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                socket.connect()

                // The connection attempt succeeded. Perform work associated with
                // the connection in a separate thread.
                //manageMyConnectedSocket(socket)
            }
        }

        // Closes the client socket and causes the thread to finish.
        fun cancel() {
            try {
                mmSocket?.close()
            } catch (e: IOException) {
                Log.e(ContentValues.TAG, "Could not close the client socket", e)
            }
        }
    }
}