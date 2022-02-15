package com.emproducciones.verduleriadelpueblo.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.emproducciones.verduleriadelpueblo.R
import com.emproducciones.verduleriadelpueblo.View.Dialog.alertDialogImpl
import com.emproducciones.verduleriadelpueblo.ViewModel.ViewPageAdapter
import com.emproducciones.verduleriadelpueblo.clicRegistroProducto
import com.emproducciones.verduleriadelpueblo.modelo.producto
import com.emproducciones.verduleriadelpueblo.modelo.vtaProd
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.vta_fragment.*
import kotlin.collections.ArrayList

class vtaFragment() : Fragment(), clicRegistroProducto {

    private lateinit var viewPagerVta: ViewPager2
    private lateinit var tabLayouVta: TabLayout
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private var productosVta = ArrayList<vtaProd>()
    private lateinit var ada : ViewPageAdapter
    private var total = 0.0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.vta_fragment, container, false)

        tabLayouVta = v.findViewById(R.id.tab_layout_vtas)
        viewPagerVta = v.findViewById(R.id.pagerVtas)

        viewPagerVta.setPageTransformer(ZoomOutPageTransformer())

        ada = ViewPageAdapter(requireActivity(), this)
        viewPagerVta.adapter=ada
        tabLayoutMediator = tabLayou(tabLayouVta, viewPagerVta).tabLayo()

        tabLayoutMediator.attach()

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnCerrarVta.setOnClickListener{
            val act = vtaFragmentDirections.actionVtaFragmentToFragFinVta(
                productosVta.toTypedArray(),
                total.toFloat()
            );
                findNavController().navigate(act)}
    }

    override fun clicRegProducto(prod: producto) {}

    override fun aDUpdate(prod: producto) {
        alertDialogImpl(requireContext(), this).cantidadVtaProd(prod)
    }

    override fun mantenerLista(ele: vtaProd) {
        var estado = false

        if (productosVta.size==0){

            productosVta.add(ele)
            estado= true
        }
        else{
            for (item in productosVta){
                if (item.produ.idProducto==ele.produ.idProducto){
                    item.cantidad=ele.cantidad
                    item.total=ele.total
                    estado=true
                    }
                }
            }
        if (!estado) productosVta.add(ele)
        sumarTotal()
    }

    override fun updateLista(prod: producto) {
        for ( toto in productosVta){
            if (toto.produ.idProducto==prod.idProducto){
                productosVta.remove(toto)
                sumarTotal()
                break
            }
        }
    }

    private fun sumarTotal(){
        var totalTemp = 0.0
        for (titi in productosVta){
            totalTemp += titi.total
        }
        total=totalTemp
        btnCerrarVta.text= "Cerrar Venta - $${String.format("%.2f", total)}"
    }

    override fun onStart() {
        super.onStart()
        if (productosVta.isNotEmpty()) {
            sumarTotal()
        }
    }
}