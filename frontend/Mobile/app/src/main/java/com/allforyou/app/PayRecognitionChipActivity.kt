package com.allforyou.app

import android.Manifest
import android.annotation.TargetApi
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityChipAlertBinding
import com.allforyou.app.databinding.ActivityPayMethodBinding
import com.allforyou.app.databinding.ActivityPayRecognitionChipBinding
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allforyou.app.retrofit.ApiRespond
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.UUID


private val TAG = "gattClienCallback"
private val SERVICE_UUID = UUID.fromString("0000FFE0-0000-1000-8000-00805F9B34FB")
private val CHARACT_UUID = UUID.fromString("0000FFE1-0000-1000-8000-00805F9B34FB")

class PayRecognitionChipActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayRecognitionChipBinding
    private val REQUEST_ENABLE_BT = 1
    private val REQUEST_ALL_PERMISSION = 2
    private lateinit var apiService: ApiService
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.BLUETOOTH_ADMIN,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH_ADVERTISE,
    )
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var scanning: Boolean = false
    private var devicesArr = ArrayList<BluetoothDevice>()
    private val SCAN_PERIOD = 1000
    private val handler = Handler()
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    // BLE Gatt
    private var bleGatt: BluetoothGatt? = null
    private var mContext: Context? = null

    private val mLeScanCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : ScanCallback() {
        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.d("scanCallback", "BLE Scan Failed : " + errorCode)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
            results?.let {
                // results is not null
                for (result in it) {
                    if (!devicesArr.contains(result.device) && result.device.name != null) devicesArr.add(
                        result.device
                    )
                }

            }
        }

        //        스캔 결과값 반환
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            result?.let {
                // result is not null
                if (!devicesArr.contains(it.device) && it.device.name != null) devicesArr.add(it.device)
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayRecognitionChipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiService = RetrofitClient.getClient()
//        binding.writeChipId.setOnClickListener {
//
//            // pet정보 가져오는 api연결
//            val intent = Intent(this, PayIdentificationActivity::class.java)
//            startActivity(intent)
//        }





        mContext = this
        val bleOnOffBtn: ToggleButton = findViewById(R.id.ble_on_off_btn)
        val scanBtn: Button = findViewById(R.id.scanBtn)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        viewManager = LinearLayoutManager(this)

        recyclerViewAdapter = RecyclerViewAdapter(devicesArr)
        recyclerViewAdapter.mListener = object : RecyclerViewAdapter.OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                scanDevice(false) // scan 중지
                val device = devicesArr.get(position)
                bleGatt = DeviceControlActivity(mContext, bleGatt).connectGatt(device)
            }
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = viewManager
            adapter = recyclerViewAdapter
        }

        if (bluetoothAdapter != null) {
            if (bluetoothAdapter?.isEnabled == false) {
                bleOnOffBtn.isChecked = true
                scanBtn.isVisible = false
            } else {
                bleOnOffBtn.isChecked = false
                scanBtn.isVisible = true
            }
        }

        bleOnOffBtn.setOnCheckedChangeListener { _, isChecked ->
            bluetoothOnOff()
            scanBtn.visibility = if (scanBtn.visibility == View.VISIBLE) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
            if (scanBtn.visibility == View.INVISIBLE) {
                scanDevice(false)
                devicesArr.clear()
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }

        scanBtn.setOnClickListener { v: View? -> // Scan Button Onclick
            if (!hasPermissions(this, PERMISSIONS)) {
                requestPermissions(PERMISSIONS, REQUEST_ALL_PERMISSION)
            }
            scanDevice(true)


            }
        }



    //    디바이스 스캔--
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun scanDevice(state: Boolean) = if (state) {
        handler.postDelayed({
            Log.d("dd","인식함222222")
            scanning = false
            bluetoothAdapter?.bluetoothLeScanner?.stopScan(mLeScanCallback)
        }, SCAN_PERIOD.toLong())

        Log.d("dd","인식함 3333333")
        scanning = true
        devicesArr.clear()
        bluetoothAdapter?.bluetoothLeScanner?.startScan(mLeScanCallback)
    } else {
        Log.d("dd","인식함 111111")
        scanning = false
        bluetoothAdapter?.bluetoothLeScanner?.stopScan(mLeScanCallback)
    }

    //    블루투스 권한 확인--
    private fun hasPermissions(context: Context?, permissions: Array<String>): Boolean {
        Log.d("로그 확인용", "hasPermission실행")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }


    // 권한 허용 요청--
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_ALL_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "BLUETOOTH 연결 권한이 허용되었습니다", Toast.LENGTH_SHORT).show()
                } else {
                    requestPermissions(permissions, REQUEST_ALL_PERMISSION)
                    Toast.makeText(this, "BLUETOOTH 연결 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun bluetoothOnOff() {
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Log.d("bluetoothAdapter", "Device doesn't support Bluetooth")
        } else {
            if (bluetoothAdapter?.isEnabled == false) { // 블루투스 꺼져 있으면 블루투스 활성화
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            } else { // 블루투스 켜져있으면 블루투스 비활성화
                bluetoothAdapter?.disable()
            }
        }
    }

    class RecyclerViewAdapter(private val myDataset: ArrayList<BluetoothDevice>) :
        RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

        var mListener: OnItemClickListener? = null

        interface OnItemClickListener {
            fun onClick(view: View, position: Int)
        }


        class MyViewHolder(val linearView: LinearLayout) : RecyclerView.ViewHolder(linearView)

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerViewAdapter.MyViewHolder {
            // create a new view
            val linearView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false) as LinearLayout

            return MyViewHolder(linearView)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val itemName: TextView = holder.linearView.findViewById(R.id.item_name)
            val itemAddress: TextView = holder.linearView.findViewById(R.id.item_address)
            itemName.text = myDataset[position].name
            itemAddress.text = myDataset[position].address
            if (mListener != null) {
                holder?.itemView?.setOnClickListener { v ->
                    mListener?.onClick(v, position)
                }
            }
        }

        override fun getItemCount() = myDataset.size
    }



}