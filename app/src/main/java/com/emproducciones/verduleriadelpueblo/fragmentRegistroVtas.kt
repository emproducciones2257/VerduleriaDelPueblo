package com.emproducciones.verduleriadelpueblo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*

class fragmentRegistroVtas : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro_vtas, container, false)
    }
}