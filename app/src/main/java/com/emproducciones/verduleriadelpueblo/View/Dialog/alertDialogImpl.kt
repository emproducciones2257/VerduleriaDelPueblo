package com.emproducciones.verduleriadelpueblo.View.Dialog

import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.emproducciones.verduleriadelpueblo.MainApplication
import com.emproducciones.verduleriadelpueblo.R
import com.emproducciones.verduleriadelpueblo.ViewModel.ViewPageAdapter
import com.emproducciones.verduleriadelpueblo.ViewModel.viewModel
import com.emproducciones.verduleriadelpueblo.clicRegistroProducto
import com.emproducciones.verduleriadelpueblo.modelo.constantes
import com.emproducciones.verduleriadelpueblo.modelo.producto
import com.emproducciones.verduleriadelpueblo.modelo.vtaProd
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.dialog_prod_vta.view.*
import kotlinx.android.synthetic.main.dialog_productos.view.*
import kotlinx.android.synthetic.main.dialog_vtas.view.*
import kotlinx.android.synthetic.main.dialog_vtas.view.btnPromo
import kotlinx.android.synthetic.main.dialog_vtas.view.txtTitulo
import kotlinx.android.synthetic.main.dialog_vtas.view.txtTotal

class alertDialogImpl(var ctx:Context,
                      var listPro:clicRegistroProducto?) {

    private lateinit var mDialogView: View
    private lateinit var builder: AlertDialog.Builder
    private var layoutCargaUpdate = R.layout.dialog_productos
    private var layoutCantVta = R.layout.dialog_vtas
    private var layoutDetalleProduVta = R.layout.dialog_prod_vta
    private var cantidad = 0.0
    private var precio = 0.0
    private val compo = CompositeDisposable()
    private val vMod = viewModel()

    fun alDiagRegistrar() {

        confIniAlertDiag(layoutCargaUpdate)

        builder.setPositiveButton("Guardar") { _, _ ->

            val productoNuevo = juntarDtosAlertDiag(producto())

            if (productoNuevo.nombre != "") {
                listPro?.clicRegProducto(productoNuevo)
            }
        }

        builder.setNegativeButton("Cancelar") { _, _ -> }

        builder.show()
    }

    fun alDiagUpdate(produ: producto, ada: ViewPageAdapter) {
        confIniAlertDiag(layoutCargaUpdate)

        mDialogView.txtTitulo.text = ("Actualizar: " + '\n' + produ.nombre).toUpperCase()

        mDialogView.txtNombreProducto.setText(produ.nombre)
        mDialogView.txtPrecioProdNuevo.setText(produ.precio.toString())

        when (produ.categoria) {
            constantes.constantes.frutas -> mDialogView.rdBtnFruta.isChecked =
                true
            constantes.constantes.verduras -> mDialogView.rdBtnVerd.isChecked =
                true
            else -> mDialogView.rdBtnVar.isChecked = true
        }

        when (produ.uniVenta) {
            constantes.constantes.kilo -> mDialogView.rdBtnKilo.isChecked =
                true
            else -> mDialogView.rdBtnUni.isChecked = true
        }

        if (produ.cantidadPromocion != 0.toByte()) {
            mDialogView.edtCantidadPromo.setText(produ.cantidadPromocion.toString())
            mDialogView.edtPrecioPromo.setText(produ.precioPromocion.toString())
        }

        builder.setPositiveButton("Actualizar") { _, _ ->

            val productoNuevo = juntarDtosAlertDiag(produ)

            if (productoNuevo.nombre != "") {

                compo.add(vMod.updateProdu(productoNuevo).subscribe({
                    ada.notifyItemRemoved(0)
                    compo.clear()
                },
                    {
                        Toast.makeText(
                            MainApplication.applicationContext(),
                            "Error Update",
                            Toast.LENGTH_SHORT
                        )
                    }
                ))
            }
        }
        builder.setNegativeButton("Cancelar") { _, _ -> }

        builder.show()
    }

    fun detalleProduVta(pro: vtaProd) {

        confIniAlertDiag(layoutDetalleProduVta)

        mDialogView.txtTitulo.text = pro.produ.nombre
        mDialogView.txtValorPrecio.text = "$ " + pro.produ.precio

        mDialogView.txtCantidaDtPro.text = "Cantidad " + pro.cantidad + " kg"
        mDialogView.txtTotalDepPro.text = "$ " + pro.total

        nombreBtnPromo(pro.produ)

        builder.setPositiveButton("Aceptar") { _, _ -> }

        builder.show()
    }

    private fun juntarDtosAlertDiag(p: producto): producto {
        var pp = p

        if (verificarCamposVacios()) {

            lateinit var radCate: RadioButton
            lateinit var radTipoVta: RadioButton

            val selectedOptionCate: Int = mDialogView.radGroupCategorias!!.checkedRadioButtonId
            val selectedOptionTipo: Int = mDialogView.radButtUniVenta!!.checkedRadioButtonId

            radCate = mDialogView.findViewById(selectedOptionCate)
            radTipoVta = mDialogView.findViewById(selectedOptionTipo)

            pp.nombre = mDialogView.txtNombreProducto.text.toString()
            pp.precio = mDialogView.txtPrecioProdNuevo.text.toString().toDouble()
            pp.categoria = radCate.text.toString()
            pp.uniVenta = radTipoVta.text.toString()
            pp.imagen = ""

            if (mDialogView.edtCantidadPromo.text.toString() != "") {
                pp.cantidadPromocion = mDialogView.edtCantidadPromo.text.toString().toByte()
                pp.precioPromocion = mDialogView.edtPrecioPromo.text.toString().toDouble()
            } else {
                pp.cantidadPromocion = 0
                pp.precioPromocion = 0.0
            }
        } else {
            Toast.makeText(
                MainApplication.applicationContext(),
                "Verificar Campos Vacios", Toast.LENGTH_SHORT
            ).show()
            pp = producto()
        }
        return pp
    }

    private fun verificarCamposVacios(): Boolean {
        var estado = true

        if (mDialogView.txtNombreProducto.text.toString() == "") estado = false
        if (mDialogView.txtPrecioProdNuevo.text.toString() == "") estado = false

        if (!mDialogView.rdBtnFruta.isChecked &&
            !mDialogView.rdBtnVerd.isChecked &&
            !mDialogView.rdBtnVar.isChecked
        ) estado = false

        if (!mDialogView.rdBtnKilo.isChecked &&
            !mDialogView.rdBtnUni.isChecked
        ) estado = false

        return estado
    }

    fun cantidadVtaProd (prod: producto) {

        val tata = vtaProd()
        tata.produ=prod

        confIniAlertDiag(layoutCantVta)

        mDialogView.txtTitulo.text=prod.nombre

        if (prod.uniVenta==constantes.constantes.uni) configDialogUnidad()

        if (prod.cantidadPromocion!=0.toByte()) configBtnPromo(prod)

        mDialogView.btnMedio.setOnClickListener {( evtnBtn(0.5, prod))
                                                    mDialogView.edtKg.setText(0.5.toString())}
        mDialogView.btnUno.setOnClickListener { (evtnBtn(1.0, prod))
                                                    mDialogView.edtKg.setText(1.0.toString())}
        mDialogView.btnDos.setOnClickListener { (evtnBtn(2.0, prod))
                                                    mDialogView.edtKg.setText(2.0.toString())}

        mDialogView.edtKg.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (start!=0)evtnBtn(s.toString().toDouble(),prod)
            }
        })

        builder.setPositiveButton("Agregar"){ _, _ ->

            if (cantidad!=0.0 || precio != 0.0){
                tata.cantidad=cantidad
                tata.total=precio
                listPro?.mantenerLista(tata)
                cantidad=0.0
                precio=0.0
            }
        }

        builder.setNeutralButton("Quitar"){ _, _ ->
            listPro?.updateLista(prod)
        }

        builder.setNegativeButton("Cancelar") { _, _ ->}

        builder.show()
    }

    private fun configBtnPromo(prod: producto) {

        if (prod.cantidadPromocion==1.toByte()) mDialogView.btnUno.isEnabled=false
        if (prod.cantidadPromocion==2.toByte()) mDialogView.btnDos.isEnabled=false

        mDialogView.btnPromo.setOnClickListener{
            cantidad = prod.cantidadPromocion.toDouble()
            precio = prod.precioPromocion
            (mDialogView.edtKg.setText(cantidad.toString()));
            mDialogView.txtTotal.text= "Precio $ $precio"
        }

        nombreBtnPromo(prod)
    }

    private fun configDialogUnidad() {
        mDialogView.btnMedio.isEnabled=false
        mDialogView.btnUno.text="1 U."
        mDialogView.btnDos.text="2 U."
    }

    private fun evtnBtn (peso:Double, prod:producto){
        cantidad=peso

        if ((prod.cantidadPromocion!=0.toByte())&& (cantidad > prod.cantidadPromocion)){

            precio=prod.precioPromocion/prod.cantidadPromocion
            precio *= cantidad
        }else{
            precio = cantidad*prod.precio
        }
        mDialogView.txtTotal.text= "Precio $${String.format("%.2f",precio)}"
    }

    private fun nombreBtnPromo(produ: producto) {
        var tipoVta = constantes.constantes.kiloPlu
        if (produ.uniVenta==constantes.constantes.uni) tipoVta= constantes.constantes.uniPlu

        mDialogView.btnPromo.text= produ.cantidadPromocion.toString() + " " + tipoVta + " a $ " + produ.precioPromocion
    }

    private fun confIniAlertDiag(layot:Int){
        mDialogView = LayoutInflater.from(ctx).
        inflate(layot,null)
        builder = AlertDialog.Builder(ctx).
        setView(mDialogView)
    }
}