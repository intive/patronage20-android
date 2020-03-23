package com.intive.patronage.smarthome.feature.hvac

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.HVACRoom
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HvacViewModel(private val dashboardService: DashboardService) : ViewModel() {


    var disposable: Disposable? = null
    var hvacList :List<HVACRoom> = listOf()



    /* init {
         disposable = dashboardService.getHVAC().toObservable()
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe ({if (it!= null){ setList(it) }
             else{Log.d("testowanie model", "ERROR")} },
                 {Log.d("Error", "error")})
     }*/

     fun loadHvac() {

         disposable = dashboardService.getHVAC()
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe ({if (it!= null){ setList(it)
                 Log.d("testowanie loadHvac", hvacList.size.toString())}
             else{Log.d("testowanie model", "ERROR")}
                 },
                 {Log.d("Error", "error")})
     }

    fun setList(list:List<HVACRoom>){
        hvacList = list
    }
    fun getList() : List<HVACRoom> {
        loadHvac()
        Log.d("testowanie getList", hvacList.size.toString())
        return hvacList}
}