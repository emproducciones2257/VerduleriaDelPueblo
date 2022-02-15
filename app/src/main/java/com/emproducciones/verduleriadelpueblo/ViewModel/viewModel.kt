package com.emproducciones.verduleriadelpueblo.ViewModel

import androidx.lifecycle.ViewModel
import com.emproducciones.verduleriadelpueblo.modelo.histVtas
import com.emproducciones.verduleriadelpueblo.modelo.producto
import com.emproducciones.verduleriadelpueblo.repository.repositorio
import io.reactivex.*

class viewModel: ViewModel() {

   private lateinit var repo : repositorio

   fun getProdu():Flowable<List<producto>>{
      repo = repositorio()
      return repo.getProdu()
   }

   fun setProdu(produ : producto):Maybe<Long>{
      repo = repositorio()
      return repo.setProdu(produ)
   }

   fun updateProdu(produ : producto):Completable{
      repo = repositorio()
      return repo.updateProdu(produ)
   }

   fun setVta(vta : histVtas):Maybe<Long>{
      repo = repositorio()
      return repo.setVta(vta)
   }

   fun getVtas():Flowable<List<histVtas>>{
      repo = repositorio()
      return repo.getVtas()
   }
}