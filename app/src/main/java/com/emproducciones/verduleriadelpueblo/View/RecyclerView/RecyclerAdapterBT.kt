package com.emproducciones.verduleriadelpueblo.View.RecyclerView

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emproducciones.verduleriadelpueblo.R
import com.emproducciones.verduleriadelpueblo.impresion.evtnBlutu
import kotlinx.android.synthetic.main.bt_lista.view.*

class RecyclerAdapterBT(private var bluet:ArrayList<BluetoothDevice>,
                        private var click:evtnBlutu): //private var click:clicRegistroProducto
    RecyclerView.Adapter<RecyclerAdapterBT.ProduHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterBT.ProduHolder {
        return ProduHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.bt_lista, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerAdapterBT.ProduHolder, position: Int) {
        holder.bind(bluet[position])
    }

    override fun getItemCount(): Int {
        return if(bluet.isNotEmpty()) bluet.size
        else 0
    }

    inner class ProduHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(item: BluetoothDevice) {
            itemView.setOnClickListener{click.clickItemBT(item)}
            itemView.txtNombreBT.text = item.name
        }
    }
}






