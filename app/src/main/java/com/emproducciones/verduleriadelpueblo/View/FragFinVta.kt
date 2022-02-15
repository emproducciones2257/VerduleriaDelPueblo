package com.emproducciones.verduleriadelpueblo.View

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.*
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection
import com.emproducciones.verduleriadelpueblo.MainApplication
import com.emproducciones.verduleriadelpueblo.R
import com.emproducciones.verduleriadelpueblo.View.Dialog.alertDialogImpl
import com.emproducciones.verduleriadelpueblo.View.RecyclerView.RecyclerAdapterFinVta
import com.emproducciones.verduleriadelpueblo.ViewModel.viewModel
import com.emproducciones.verduleriadelpueblo.clicRegistroProducto
import com.emproducciones.verduleriadelpueblo.impresion.impresion
import com.emproducciones.verduleriadelpueblo.modelo.histVtas
import com.emproducciones.verduleriadelpueblo.modelo.producto
import com.emproducciones.verduleriadelpueblo.modelo.vtaProd
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_frag_fin_vta.*
import kotlinx.android.synthetic.main.fragment_frag_fin_vta.view.*
import java.util.*
import kotlin.collections.ArrayList


class fragFinVta() : Fragment(), clicRegistroProducto {

    private lateinit var tete : RecyclerView
    private val args : fragFinVtaArgs by navArgs()
    private var precio = 0.0
    private lateinit var valor : ArrayList<vtaProd>
    private var gestionImpresion = impresion()
    private var blue2 = com.emproducciones.verduleriadelpueblo.impresion.blue2()
    private var impresoraElegida:String?=""
    private var bluetoothConnection: BluetoothConnection? = null
    private var composite = CompositeDisposable()
    private val vMod = viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vistita = inflater.inflate(R.layout.fragment_frag_fin_vta, container, false)

        vistita.recViewFinVta.layoutManager = LinearLayoutManager(MainApplication.applicationContext())

        vistita.recViewFinVta.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        leerImpresora()
        return vistita
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (bluetoothConnection==null) encenderBT(blue2.bAdapter, blue2.REQUEST_CODE_ENABLE_BT)

        tete = recViewFinVta
        valor = args.listaProductosVenta.toCollection(ArrayList())
        precio = args.totalDineroVta.toDouble()
        configRecy(valor)
        btnImprimir.text= "TOTAL: $ ${String.format("%.2f",precio)}"
        btnImprimir.setOnClickListener{ imprimirComprobante()}
    }

    private fun imprimirComprobante(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Verifica permisos para Android 6.0+
            val permissionCheck = ContextCompat.checkSelfPermission(
                MainApplication.applicationContext(), Manifest.permission.BLUETOOTH
            )
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.BLUETOOTH),
                    225
                )
            } else {
                animation_view_imp.visibility=View.VISIBLE
                gestionImpresion.conectarBluetooh(btnconectar,bluetoothConnection)
                var vta = histVtas(Calendar.getInstance().time,precio)
                composite.add(vMod.setVta(vta).
                        subscribe({gestionImpresion.imprimirTicket(valor)
                        findNavController().navigate(R.id.fragmentEleccionIniciar)
                            },{}))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        tete = recViewFinVta
    }

    override fun onPause() {
        super.onPause()
        gestionImpresion.closeBT()
    }

    fun configRecy(valo: ArrayList<vtaProd>) {
        val reciclador = RecyclerAdapterFinVta(valo, this)
        tete.adapter = reciclador;
    }

    override fun clicRegProducto(prod: producto) {
    }

    override fun aDUpdate(prod: producto) {
    }

    override fun mantenerLista(ele: vtaProd) {
        alertDialogImpl(requireContext(), this).detalleProduVta(ele)
    }

    override fun updateLista(prod: producto) {
    }

    fun encenderBT(bAdapter: BluetoothAdapter, REQUEST_CODE_ENABLE_BT: Int) {

        if (bAdapter.isEnabled){
            Toast.makeText(MainApplication.applicationContext(), "Alredy On", Toast.LENGTH_SHORT).show()
            discoverarBT(impresoraElegida)
        }
        else {
            var intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
        }
    }

    private fun discoverarBT(impresoraABuscar: String?) {
        if (blue2.bAdapter.isEnabled) {
            val devices = blue2.bAdapter.bondedDevices
            for (device in devices) {
                if(device.name.equals(impresoraABuscar)){
                    bluetoothConnection = BluetoothConnection(device).connect()
                    break
                }
            }
        } else {
            Toast.makeText(
                MainApplication.applicationContext(),
                "Turn on Bluetoot firts",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            blue2.REQUEST_CODE_ENABLE_BT ->
                if (resultCode == Activity.RESULT_OK) {
                    discoverarBT(impresoraElegida)
                }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun leerImpresora() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        impresoraElegida = sharedPref.getString("impresora", "")
    }
}