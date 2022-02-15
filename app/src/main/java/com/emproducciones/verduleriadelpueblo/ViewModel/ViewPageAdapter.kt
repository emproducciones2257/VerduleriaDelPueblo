package com.emproducciones.verduleriadelpueblo.ViewModel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.emproducciones.verduleriadelpueblo.View.fragmentProducto
import com.emproducciones.verduleriadelpueblo.clicRegistroProducto
import com.emproducciones.verduleriadelpueblo.modelo.constantes

class ViewPageAdapter(fa: FragmentActivity,
                        private val fofito: clicRegistroProducto):
                        FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val viewModel = viewModel()

        val datos = viewModel.getProdu()

        return when(position){
            0-> {fragmentProducto(datos, constantes.constantes.frutas, fofito)}
            1-> {fragmentProducto(datos, constantes.constantes.verduras, fofito)}
            2-> {fragmentProducto(datos, constantes.constantes.varios, fofito)}
            else -> fragmentProducto(datos, constantes.constantes.frutas, fofito)
        }
    }
}