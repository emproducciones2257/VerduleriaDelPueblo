package com.emproducciones.verduleriadelpueblo.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.emproducciones.verduleriadelpueblo.R
import kotlinx.android.synthetic.main.fragment_eleccion_iniciar.*

class fragmentEleccionIniciar : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eleccion_iniciar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnVta.setOnClickListener{(findNavController().navigate(R.id.vtaFragment))}

        vtnProd.setOnClickListener{(findNavController().navigate(R.id.fragmentRegistrarProducto))}

        vtnReg.setOnClickListener{(findNavController().navigate(R.id.fragmentRegistroVtas))}

        btnGestionBT.setOnClickListener{(findNavController().navigate(R.id.fragmentGestionImpresora))}
    }
}
