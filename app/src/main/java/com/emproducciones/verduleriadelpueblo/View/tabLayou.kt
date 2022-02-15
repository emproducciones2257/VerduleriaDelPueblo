package com.emproducciones.verduleriadelpueblo.View

import androidx.viewpager2.widget.ViewPager2
import com.emproducciones.verduleriadelpueblo.modelo.constantes
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class tabLayou(private var tabLayou: TabLayout,
               private var viewPager:ViewPager2) {

    fun tabLayo ():TabLayoutMediator{
        return TabLayoutMediator(tabLayou,viewPager
        ) { tab, position ->
            when (position) {

                0 -> {
                    tab.text = constantes.constantes.frutas
                }
                1 -> {
                    tab.text = constantes.constantes.verduras
                }
                2 -> {
                    tab.text = constantes.constantes.varios
                }
            }
        }
    }
}