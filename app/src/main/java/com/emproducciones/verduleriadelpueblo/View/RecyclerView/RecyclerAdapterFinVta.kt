package com.emproducciones.verduleriadelpueblo.View.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emproducciones.verduleriadelpueblo.R
import com.emproducciones.verduleriadelpueblo.clicRegistroProducto
import com.emproducciones.verduleriadelpueblo.modelo.constantes
import com.emproducciones.verduleriadelpueblo.modelo.vtaProd
import kotlinx.android.synthetic.main.list_fin_vta.view.*


class RecyclerAdapterFinVta(private var vtas:ArrayList<vtaProd>,
                            private var click:clicRegistroProducto):
                        RecyclerView.Adapter<RecyclerAdapterFinVta.ProduoHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProduoHolder {
        return ProduoHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_fin_vta, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProduoHolder, position: Int) {
       holder.bind(vtas[position])
    }

    override fun getItemCount(): Int {
        return vtas.size
    }

    inner class ProduoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: vtaProd) = with(itemView) {
            itemView.setOnClickListener{click.mantenerLista(item)}
            itemView.txtNombre.text = item.produ.nombre
            itemView.txtPrecio.text = "$ ${String.format("%.2f",item.total)}"
            if (item.produ.uniVenta==constantes.constantes.kilo) {
                itemView.txtCan.text = item.cantidad.toString() + " " + constantes.constantes.kiloPlu
            }else{
                itemView.txtCan.text = item.cantidad.toString() + " " + constantes.constantes.uniPlu
            }
        }
    }
}

