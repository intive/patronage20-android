package com.intive.patronage.smarthome.feature.home.view

import android.content.res.Configuration
import android.os.Bundle
import android.os.SystemClock
import android.util.DisplayMetrics
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.ToastListener
import com.intive.patronage.smarthome.common.coordinateToPercentX
import com.intive.patronage.smarthome.common.coordinateToPercentY
import com.intive.patronage.smarthome.databinding.HomeFragmentBinding
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.dashboard.model.MapPosition
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import com.intive.patronage.smarthome.feature.home.viewmodel.HomeSharedViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf


class HomeFragment : Fragment(), ToastListener {

    private val homeSensorListAdapter: HomeSensorListAdapter by inject {
        parametersOf(::onItemClick)
    }
    private val homeSharedViewModel: HomeSharedViewModel by sharedViewModel()
    private val draggedSensor = DraggedSensor()
    private lateinit var image: HomeLayoutView
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: HomeFragmentBinding
    private lateinit var homeOverlay: ViewOverlay
    private lateinit var gestureDetector: GestureDetector
    private var sensorToPost: DashboardSensor? = null
    private var dragAction: Boolean = false
    private var draggedView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        binding.lifecycleOwner = this
        binding.homeViewModelDataBind = homeSharedViewModel
        setupRecyclerView(binding)

        homeSharedViewModel.toastListener = this
        image = binding.home
        getSensors()
        initGestureDetector()
        setTouchListeners()
        homeOverlay = binding.homeLayout.overlay
        return binding.root
    }

    private fun initGestureDetector() {
        gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onLongPress(e: MotionEvent) {
                    super.onLongPress(e)
                    if (!dragAction) {
                        val sensor = image.findSensor(e.x, e.y)
                        sensor?.let {
                            homeSharedViewModel.deleteSensor(it.id.toInt())
                        }
                    }
                }
            })
    }

    private fun onDragStart(x: Float, y: Float) {
        draggedSensor.setPosition(
            x + (resources.getDimension(R.dimen.home_sensor_margin) * 2) + recyclerView.x,
            y + (resources.getDimension(R.dimen.home_sensor_margin) * 2) + recyclerView.y
        )
        draggedSensor.initialize(resources, sensorToPost, image.height)
    }

    private fun onDragEnd(x: Float, y: Float) {
        homeOverlay.remove(draggedSensor)
        draggedView?.alpha = 1f
        draggedView = null
        recyclerView.suppressLayout(false)
        dragAction = false
        if (x >= 0 && y >= 0 && x >= image.x && x <= image.x + image.width && y >= image.y && y <= image.y + image.height) {
            handlePost(sensorToPost, x - image.x, y - image.y)
        }
    }

    private fun getSensors() {
        homeSharedViewModel.items.observe(this, Observer {
            image.setData(it)
        })
    }

    private fun handlePost(sensor: DashboardSensor?, x: Float, y: Float) {
        if (image.checkForSensors(x, y)) {
            sensor?.let {
                homeSharedViewModel.postSensor(
                    it.id.toInt(),
                    HomeSensor(
                        it.id.toInt(),
                        it.type,
                        MapPosition(
                            coordinateToPercentX(x, image.width),
                            coordinateToPercentY(y, image.height)
                        )
                    )
                )
            }
        } else {
            showToast(R.string.sensor_add_failure)
        }
    }

    override fun showToast(message: Int) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView(binding: HomeFragmentBinding) {
        recyclerView = binding.sensorRecyclerViewD
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getRealMetrics(displayMetrics)
        val orientation = resources.configuration.orientation
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                LinearLayoutManager(activity)
            } else {
                GridLayoutManager(
                    activity,
                    (displayMetrics.widthPixels / (resources.getDimension(R.dimen.home_sensor_size) + resources.getDimension(
                        R.dimen.home_sensor_margin
                    ) * 2)).toInt()
                )
            }
            adapter = homeSensorListAdapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

    }

    private fun onItemClick(sensor: DashboardSensor, x: Float, y: Float, draggedView: View) {
        this.draggedView = draggedView
        draggedView.alpha = 0.3f
        sensorToPost = sensor
        recyclerView.suppressLayout(true)
        homeOverlay.add(draggedSensor)
        dragAction = true
        val e = MotionEvent.obtain(
            SystemClock.uptimeMillis(),
            SystemClock.uptimeMillis(),
            MotionEvent.ACTION_DOWN,
            x, y, 0
        )
        binding.homeLayout.dispatchTouchEvent(e)
    }

    private fun setTouchListeners() {
        binding.homeLayout.setOnTouchListener { view, motionEvent ->
            if ((motionEvent.action == MotionEvent.ACTION_DOWN) and dragAction) {
                onDragStart(motionEvent.x, motionEvent.y)
            }
            if ((motionEvent.action == MotionEvent.ACTION_MOVE) and dragAction) {
                view.parent.requestDisallowInterceptTouchEvent(true)
                draggedSensor.setPosition(motionEvent.x, motionEvent.y)
            } else if ((motionEvent.action == MotionEvent.ACTION_UP) and dragAction) {
                view.parent.requestDisallowInterceptTouchEvent(false)
                onDragEnd(motionEvent.x, motionEvent.y)
            }
            true
        }
        image.setOnTouchListener { v, motionEvent ->
            gestureDetector.onTouchEvent(motionEvent)
        }
    }

}
