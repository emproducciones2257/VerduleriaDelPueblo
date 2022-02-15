package com.emproducciones.verduleriadelpueblo.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emproducciones.verduleriadelpueblo.*
import com.emproducciones.verduleriadelpueblo.View.RecyclerView.RecyclerAdapter
import com.emproducciones.verduleriadelpueblo.modelo.producto
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_producto.*
import kotlinx.android.synthetic.main.fragment_producto.view.*

class fragmentProducto(
    private val dato: Flowable<List<producto>>,
    private val eleccion: String,
    private val fofito : clicRegistroProducto
    ): Fragment() {

    private var listado = ArrayList<producto>()
    private lateinit var tete : RecyclerView
    private var composite = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val vistita = inflater.inflate(R.layout.fragment_producto, container, false)

        vistita.recliadorProductos.layoutManager = LinearLayoutManager(MainApplication.applicationContext())

        vistita.recliadorProductos.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        return vistita
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

            cargaDtos()
    }

    private fun configRecicler(dato: ArrayList<producto>){
        val reciclador = RecyclerAdapter(dato, fofito)
        tete.adapter = reciclador;
    }

    fun cargaDtos (){
        composite.add(dato.subscribe { dato ->
            listado.addAll(dato);
            limpiarLista()
        })
    }

    private fun limpiarLista(){
        var contador = 1
        composite.add(Observable.fromIterable(listado).
            filter { pro -> pro.categoria == eleccion }.
            distinct { prod -> prod.idProducto }.
            toSortedList { p1, p2 -> comparador(p1.nombre.toUpperCase(), p2.nombre.toUpperCase()) }.
            subscribe { ite ->
                if (listado.size == 0) {
                    contador++
                    if (contador == listado.size)
                        configRecicler(listado)
                } else configRecicler(ArrayList(ite))
            })
        }

    private fun comparador(n1: String, n2: String): Int {
        return when {
            n1>n2 -> 1
            n1<n2 -> -1
            else -> 0
        }
    }

    override fun onStart() {
        super.onStart()
        tete = recliadorProductos
    }

    override fun onStop() {
        composite.clear()
        super.onStop()
    }
}