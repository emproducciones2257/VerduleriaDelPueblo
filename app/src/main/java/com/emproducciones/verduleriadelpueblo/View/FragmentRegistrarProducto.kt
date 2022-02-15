package com.emproducciones.verduleriadelpueblo.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.emproducciones.verduleriadelpueblo.MainApplication
import com.emproducciones.verduleriadelpueblo.R
import com.emproducciones.verduleriadelpueblo.View.Dialog.alertDialogImpl
import com.emproducciones.verduleriadelpueblo.ViewModel.ViewPageAdapter
import com.emproducciones.verduleriadelpueblo.ViewModel.viewModel
import com.emproducciones.verduleriadelpueblo.clicRegistroProducto
import com.emproducciones.verduleriadelpueblo.modelo.producto
import com.emproducciones.verduleriadelpueblo.modelo.vtaProd
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_registrar_producto.*

class fragmentRegistrarProducto : Fragment(), clicRegistroProducto {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayou: TabLayout
    private lateinit var ada : ViewPageAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private val vMod = viewModel()
    private var composite = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_registrar_producto, container, false)

        tabLayou = v.findViewById(R.id.tab_layout)
        viewPager = v.findViewById(R.id.pager)

        viewPager.setPageTransformer(ZoomOutPageTransformer())

        ada = ViewPageAdapter(requireActivity(),this)
        viewPager.adapter=ada

        tabLayoutMediator = tabLayou(tabLayou,viewPager).tabLayo()

        tabLayoutMediator.attach()

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnNewFlotante.setOnClickListener{alertDialogImpl(requireContext(),this).alDiagRegistrar()}
    }

    override fun clicRegProducto(prod: producto) {
        composite.add(vMod.setProdu(prod).
                        subscribe({Toast.makeText(
                                    MainApplication.applicationContext(),
                                    "Producto Registrado Correctamente",
                                    Toast.LENGTH_SHORT).show()},
                                    {Toast.makeText(
                                    MainApplication.applicationContext(),
                                    "Error al registrar el producto",Toast.LENGTH_SHORT).show()}))
                        }

    override fun aDUpdate(prod: producto) {
        alertDialogImpl(requireContext(),this).alDiagUpdate(prod,ada)
    }

    override fun mantenerLista(ele: vtaProd) {
    }

    override fun updateLista(prod: producto) {
    }
    override fun onPause() {
        composite.clear()
        super.onPause()
    }
}