package com.intive.patronage.smarthome.feature.home.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.ToastListener
import com.intive.patronage.smarthome.common.percentToCoordinateX
import com.intive.patronage.smarthome.common.percentToCoordinateY
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.dashboard.model.MapPosition
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import com.intive.patronage.smarthome.feature.home.viewmodel.HomeSharedViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : Fragment(), ToastListener {

    private val homeSharedViewModel: HomeSharedViewModel by sharedViewModel()
    lateinit var image: HomeLayoutView
    lateinit var gestureDetector: GestureDetector
    private var postDisposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeSharedViewModel.toastListener = this
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        image = view.findViewById(R.id.home)
        getSensors()
        initGestureDetector()
        image.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
        observePostSensor()
        return view
    }

    private fun initGestureDetector() {
        gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onLongPress(e: MotionEvent) {
                    super.onLongPress(e)
                    val x = e.x
                    val y = e.y
                    val sensorDialog = SensorDialog()
                    homeSharedViewModel.setSensorPosition(x, y, image.width, image.height)
                    sensorDialog.show(fragmentManager!!, "SensorList")
                }
            })
    }

    private fun getSensors() {
        homeSharedViewModel.items.observe(this, Observer {
            if (it != null) {
                image.setData(it)
            }
        })
    }

    override fun showToast(message: Int) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun observePostSensor(){
        postDisposable = homeSharedViewModel.postSensorPublishSubject.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                handlePost(it)
            },{})
    }

    fun handlePost(sensor: DashboardSensor){
        val x = homeSharedViewModel.getSensorXPosition()
        val y = homeSharedViewModel.getSensorYPosition()
        if (image.checkForSensors(
                percentToCoordinateX(x, image.width),
                percentToCoordinateY(y, image.height)
            )
        ) {
            homeSharedViewModel.postSensor(
                sensor.id.toInt(),
                HomeSensor(sensor.id.toInt(), sensor.type, MapPosition(x, y))
            )
        } else {
            Toast.makeText(
                this.context,
                getString(R.string.sensor_add_failure),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        postDisposable?.dispose()
    }
}
