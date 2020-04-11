package com.oneonefivedigitalcreations.easyhrv

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

import com.oneonefivedigitalcreations.easyhrv.bluetoothle.BluetoothLeService
import com.oneonefivedigitalcreations.easyhrv.bluetoothle.BluetoothLeService.LocalBinder
import com.oneonefivedigitalcreations.easyhrv.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mBluetoothLeService: BluetoothLeService? = null

    private val kPermissionRequestLocation = 99

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, service: IBinder) {
//            mBluetoothLeService = (service as LocalBinder).service
//            mBluetoothLeService!!.initialize()
//            requestLocationPermissions()
//            mBluetoothLeService!!.scanForPeripherals()
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mBluetoothLeService = null
        }
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            kPermissionRequestLocation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        val gattServiceIntent = Intent(applicationContext, BluetoothLeService::class.java)
        bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)

        // TODO: Add this back when we have a connection modal
//        binding.connectToPeripheral.setOnClickListener {
//            mBluetoothLeService!!.connectToPeripheral("08:7C:BE:CD:66:CE")
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == kPermissionRequestLocation &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mBluetoothLeService!!.scanForPeripherals()
        }
    }
}