package com.oneonefivedigitalcreations.easyhrv.bluetoothle

import android.app.Activity
import android.app.Service
import android.bluetooth.*
import android.bluetooth.BluetoothProfile.ServiceListener
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 */
class BluetoothLeService : Service() {

    private var mBluetoothManager: BluetoothManager? = null
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mBluetoothGatt: BluetoothGatt? = null
    private val mHandler: Handler = Handler()
    private val mPeripherals: HashMap<String, ScanResult> = HashMap()
    private var mIsScanningContinuously = false
    private val mHeartRateCharacteristics: ArrayList<UUID> = ArrayList()

    private val kHeartRateService: UUID =
        UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb")

    init {
        mHeartRateCharacteristics.add(UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb"))
    }

    fun connectToPeripheral(address: String?): Boolean {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.")
            return false
        }
        val device = mBluetoothAdapter!!.getRemoteDevice(address)
        if (device == null) {
            Log.w(TAG, "Device not found. Unable to Connect to Peripheral.")
            return false
        }
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback)
        Log.d(TAG, "Trying to create a new connection.")
        return true
    }

    fun disconnectFromPeripheral() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized")
            return
        }
        mBluetoothGatt!!.disconnect()
        closeConnection()
    }

    private fun closeConnection() {
        if (mBluetoothGatt == null) {
            return
        }
        mBluetoothGatt!!.close()
        mBluetoothGatt = null
    }

    fun readCharacteristic(
        gatt: BluetoothGatt,
        serviceUuid: UUID?,
        characteristicUuid: UUID?
    ) {
        val characteristic = gatt
            .getService(serviceUuid)
            .getCharacteristic(characteristicUuid)
        gatt.readCharacteristic(characteristic)
    }

    @ExperimentalUnsignedTypes
    private val mGattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            super.onServicesDiscovered(gatt, status)
            if (status != BluetoothGatt.GATT_SUCCESS) {
                return
            }
            enableService(gatt, kHeartRateService, mHeartRateCharacteristics.removeAt(0))
        }

        override fun onConnectionStateChange(
            gatt: BluetoothGatt,
            status: Int,
            newState: Int
        ) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mBluetoothGatt!!.discoverServices()
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {

            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic
        ) {
            super.onCharacteristicChanged(gatt, characteristic)

            Log.i(TAG, characteristic.value[1].toString())
        }

    }

    private fun activateHighPriorityTransmission(gatt: BluetoothGatt) {
//        if (BLEUtils.doesRequireConnectionPriorityChange(Build.MANUFACTURER)) {
//            gatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH)
//        }
    }

    private fun deactivateHighPriorityTransmission(gatt: BluetoothGatt) {
//        if (BLEUtils.doesRequireConnectionPriorityChange(Build.MANUFACTURER)) {
//            gatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_BALANCED)
//        }
    }

    private val isBluetoothDisabled: Boolean
        private get() = mBluetoothAdapter!!.state == BluetoothAdapter.STATE_OFF

    fun scanForPeripherals() {
        mIsScanningContinuously = true
        val bluetoothLeScanner = mBluetoothAdapter!!.bluetoothLeScanner
        mHandler.postDelayed(
            { stopScanLeDevice() },
            SCAN_PERIOD
        )
        if (!isBluetoothDisabled) {
            bluetoothLeScanner.startScan(mLeScanCallback)
        }
    }

    val isLocationServiceEnabled: Boolean
        get() =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // This is new method provided in API 28
                val manager =
                    this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                manager.isLocationEnabled
            } else {
                // This is Deprecated in API 28
                val mode = Settings.Secure.getInt(
                    applicationContext.contentResolver,
                    Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_OFF)
                mode != Settings.Secure.LOCATION_MODE_OFF
            }

    fun requestLocationServiceActivation(applicationActivity: Activity) {
        val callGPSSettingIntent =
            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        applicationActivity.startActivity(callGPSSettingIntent)
    }

    private fun stopScanLeDevice() {
        val bluetoothLeScanner = mBluetoothAdapter!!.bluetoothLeScanner
        if (!mIsScanningContinuously) {
            return
        }

        // If after 10 Seconds nothing is found do another scan
        if (mPeripherals.isEmpty()) {
            scanForPeripherals()
            return
        }

        // Don't stop scanning if bluetooth is disabled
        // to mimic behaviour of iOS
        if (bluetoothLeScanner != null) {
            mIsScanningContinuously = false
            bluetoothLeScanner.stopScan(mLeScanCallback)
        }
    }

    private val mLeScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(
            callbackType: Int,
            device: ScanResult
        ) {
            super.onScanResult(callbackType, device)
            val deviceName: String = device.device.name ?: ""
            if(deviceName.contains("CorSense", true)) {
                mPeripherals[device.device.name] = device
                Log.i(TAG, device.device.name)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e("Scan Failed", "Error Code: $errorCode")
        }

    }

    fun clearPeripherals() {
        mPeripherals.clear()
    }

    /*
     * Boilerplate Methods Required for binding the Bluetooth Service
     * to a class.
     * */
    private val mBinder: IBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        val service: BluetoothLeService
            get() = this@BluetoothLeService
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    override fun onUnbind(intent: Intent): Boolean {
        closeConnection()
        return super.onUnbind(intent)
    }

    fun initialize(): Boolean {
        if (mBluetoothManager == null) {
            mBluetoothManager =
                getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.")
                return false
            }
        }
        mBluetoothAdapter = mBluetoothManager!!.adapter
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.")
            return false
        }
        mBluetoothAdapter!!.getProfileProxy(
            applicationContext, mBluetoothServiceListener, BluetoothProfile.HEADSET
        )
        return true
    }

    private var mBluetoothServiceListener: ServiceListener = object : ServiceListener {
        override fun onServiceConnected(
            profile: Int,
            proxy: BluetoothProfile
        ) {}

        override fun onServiceDisconnected(profile: Int) {
            closeConnection()
            Log.e(TAG, "USER TURNED OFF BLUETOOTH")
        }
    }

    companion object {
        // Constants for Bluetooth
        private val TAG = BluetoothLeService::class.java.simpleName
        private const val SCAN_PERIOD: Long = 4000

        private val CLIENT_CHARACTERISTIC_CONFIG =
            UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")

        private fun enableService(
            gatt: BluetoothGatt,
            serviceUuid: UUID,
            characteristicUuid: UUID
        ): BluetoothGattCharacteristic {

            val service: BluetoothGattService = gatt.getService(serviceUuid)

            val characteristic: BluetoothGattCharacteristic =
                service.getCharacteristic(characteristicUuid)

            gatt.setCharacteristicNotification(characteristic, true)

            val descriptor = characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG)
            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            gatt.writeDescriptor(descriptor)

            return characteristic
        }
    }
}