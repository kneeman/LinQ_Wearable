package com.sai.linq.wearable.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.wear.widget.WearableLinearLayoutManager
import com.sai.linq.wearable.adapters.AlertAdapter
import com.sai.linq.wearable.databinding.ActivityAlertsBinding
import com.sai.linq.wearable.models.AlertItemTableServiceEntity


class AlertsActivity : Activity() {

    private lateinit var binding: ActivityAlertsBinding
    private lateinit var alertsAdapter: AlertAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAlertsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alertsAdapter = AlertAdapter(createStaticAlertsList())
        binding.alertsRecyclerView.apply {
            adapter = alertsAdapter
            layoutManager = CustomWearableLinearLayoutManager(this@AlertsActivity, CustomScrollingLayoutCallback())
        }
        LinearSnapHelper().attachToRecyclerView(binding.alertsRecyclerView)
        binding.title.setOnClickListener { startActivity(
            Intent(this,LinqSettingsActivity::class.java)
        ) }
    }

    private fun createStaticAlertsList() = listOf(AlertItemTableServiceEntity().apply {
        buildingID = "bldId1"
        floorID = "floorId1"
        roomID = "roomId1"
        deviceType = "DeviceType1"
        localTime = "2022-06-22 13:01:30"
    }, AlertItemTableServiceEntity().apply {
        buildingID = "bldId2"
        floorID = "floorId2"
        roomID = "roomId2"
        deviceType = "DeviceType2"
        localTime = "2022-06-22 13:02:30"
    }, AlertItemTableServiceEntity().apply {
        buildingID = "bldId3"
        floorID = "floorId3"
        roomID = "roomId3"
        deviceType = "DeviceType3"
        localTime = "2022-06-22 13:03:30"
    }, AlertItemTableServiceEntity().apply {
        buildingID = "bldId4"
        floorID = "floorId4"
        roomID = "roomId4"
        deviceType = "DeviceType4"
        localTime = "2022-06-22 13:04:30"
    })
}

/** How much should we scale the icon at most.  */
private const val MAX_ICON_PROGRESS = 0.65f

class CustomScrollingLayoutCallback : WearableLinearLayoutManager.LayoutCallback() {

    private var progressToCenter: Float = 0f

    override fun onLayoutFinished(child: View, parent: RecyclerView) {
        child.apply {
            // Figure out % progress from top to bottom
            val centerOffset = height.toFloat() / 2.0f / parent.height.toFloat()
            val yRelativeToCenterOffset = y / parent.height + centerOffset

            // Normalize for center
            progressToCenter = Math.abs(0.5f - yRelativeToCenterOffset)
            // Adjust to the maximum scale
            progressToCenter = Math.min(progressToCenter, MAX_ICON_PROGRESS)

            scaleX = 1 - progressToCenter
            scaleY = 1 - progressToCenter
        }
    }
}

class CustomWearableLinearLayoutManager(context: Context, layoutCallback: WearableLinearLayoutManager.LayoutCallback):
    WearableLinearLayoutManager(context, layoutCallback) {
    override fun getPaddingTop(): Int {
        return (height / 2) - 100
    }

    override fun getPaddingBottom(): Int {
        return paddingTop
    }
}