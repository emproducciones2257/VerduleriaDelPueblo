package com.emproducciones.verduleriadelpueblo.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.navigation.fragment.findNavController
import com.emproducciones.verduleriadelpueblo.R
import kotlinx.android.synthetic.main.fragment_inicio.*

class Inicio : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnInicio.setOnClickListener {
            findNavController().navigate(R.id.fragmentEleccionIniciar)
        }
    }
}