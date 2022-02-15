package com.emproducciones.verduleriadelpueblo.View.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emproducciones.verduleriadelpueblo.R
import com.emproducciones.verduleriadelpueblo.clicRegistroProducto
import com.emproducciones.verduleriadelpueblo.modelo.constantes
import com.emproducciones.verduleriadelpueblo.modelo.producto
import kotlinx.android.synthetic.main.productos_lista.view.*


class RecyclerAdapter(private var pro:ArrayList<producto>,
                       private var prductoClick:clicRegistroProducto):
                        RecyclerView.Adapter<RecyclerAdapter.ProduoHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProduoHolder {
        return ProduoHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.productos_lista, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProduoHolder, position: Int) {
       holder.bind(pro[position])
    }

    override fun getItemCount(): Int {
        return pro.size
    }

    inner class ProduoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private fun escribir(item:producto, texto:String){

            (item.cantidadPromocion.toString() + " " + texto + "  Por $ " +
                    item.precioPromocion.toString()).also { itemView.txtCan.text = it }
        }

        fun bind(item: producto) = with(itemView) {

            itemView.setOnClickListener { (prductoClick.aDUpdate(item)) }
            itemView.txtNombre.text = item.nombre
            itemView.txtPrecio.text = "$ "+item.precio.toString()

            if (item.cantidadPromocion!=0.toByte()){
                    if (item.uniVenta==constantes.constantes.kilo){
                        escribir(item,constantes.constantes.kiloPlu)
                    }else{
                        escribir(item,constantes.constantes.uniPlu)
                    }

            }else itemView.txtCan.text="Sin Promo"
        }
    }
}

