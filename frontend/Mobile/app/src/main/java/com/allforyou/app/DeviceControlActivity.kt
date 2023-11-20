package com.allforyou.app

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.allforyou.app.retrofit.ApiRespond
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.UUID
import java.util.UUID.*

private val TAG = "gattClienCallback"
private val SERVICE_UUID = UUID.fromString("0000FFE0-0000-1000-8000-00805F9B34FB")
private val CHARACT_UUID = UUID.fromString("0000FFE1-0000-1000-8000-00805F9B34FB")
private lateinit var apiService: ApiService
class DeviceControlActivity(private val context: Context?, private var bluetoothGatt: BluetoothGatt?) {
    private var device : BluetoothDevice? = null
    private val gattCallback : BluetoothGattCallback = object : BluetoothGattCallback(){
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    Log.i(TAG, "Connected to GATT server.")
                    Log.i(TAG, "Attempting to start service discovery: " +
                            bluetoothGatt?.discoverServices())
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    Log.i(TAG, "Disconnected from GATT server.")
                    disconnectGattServer()
                }
            }

        }
        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)

            when (status) {
                BluetoothGatt.GATT_SUCCESS -> {
                    Log.i(TAG, "Connected to GATT_SUCCESS.")
                    broadcastUpdate("장치에 연결됐습니다. "+ device?.name)

                    val service = gatt?.getService(SERVICE_UUID)
                    val characteristic = service?.getCharacteristic(CHARACT_UUID)

                    if(characteristic != null) {
                        gatt.setCharacteristicNotification(characteristic, true)

                        val descriptor = characteristic.getDescriptor(fromString("00002902-0000-1000-8000-00805f9b34fb"))
                        descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                        gatt.writeDescriptor(descriptor)
                    } else {
                        Log.w(TAG, "Characteristic not found!")
                    }
                }

                else -> {
                    Log.w(TAG, "Device service discovery failed, status: $status")
                    broadcastUpdate("Fail Connect "+device?.name)
                }
            }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic) {
            super.onCharacteristicChanged(gatt, characteristic)

            if (CHARACT_UUID == characteristic.uuid) {
                val receivedData = characteristic.value
                val receivedString = String(receivedData).strip()
                broadcastUpdate(receivedString)

//                broadcastUpdate("Received data: " + receivedData.toString())
            }
        }

        private fun broadcastUpdate(str: String) {
            val mHandler : Handler = object : Handler(Looper.getMainLooper()){
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)

                    Log.d("인식된 RFID값 : ",str)
                    RemittanceRequestManager.rfid=str


//                    RFID인식완료
                    apiService = RetrofitClient.getClient()


//                  Log.d("인식","인식했어요요요요요요")

                    Log.d("api 호출","rfid인식했으니ㅣㄲㄲㄲㄲㄲㄲㄲㄲ"+str)

//                펫계좌 정보를 불러옵니다
                        GlobalScope.launch(Dispatchers.Main) {
                            try {
                                Log.d("api 호출","rfid값으로 강아지 정보를 요청합니다 "+str+"!")

                                str.trim()
                                Log.d("api 호출","rfid값으로 강아지 정보를 요청합니다 "+str+"!")

                                // ApiService 인터페이스를 통해 호출
                                val response: Response<ApiRespond<PetInfoResponse>> =
                                    apiService.getPetInfo(
                                        AccessTokenManager.getBearer(),
//                                        rfidCode(str)
                                        rfidCode("FA06F181")

                                    )

                                if (response.isSuccessful) {
                                    val apiResponse = response.body()
                                    Log.d("강아지 정보를 불러옵니당", apiResponse.toString())

                                    when (apiResponse?.code) {
                                        200 -> {

                                            val info:PetInfoResponse=apiResponse.data
                                            Log.d("강아지 정보 : ", info.toString())


                                            val intent = Intent(context, PayIdentificationActivity::class.java)
                                            intent.putExtra("petInfo", info)
                                            context!!.startActivity(intent)
//                                            startActivity(intent)
                                        }
                                        // Handle other response codes if needed
                                        else -> {
//                                            Toast.makeText(
//                                                this@DeviceControlActivity,
//                                                "등록된 반려동물 계좌가 없습니댜",
//                                                Toast.LENGTH_SHORT
//                                            ).show()
                                            Log.d("강아지 정보 : ", "등록된 반려동물 계좌가 없습니다")



                                        }
                                    }
                                } else {
                                }
                            } catch (e: Exception) {
                                // Handle exception
                                Log.d("계좌 조회 결과", "실패 했습니다")
                                e.printStackTrace()
                            }
                        }

Log.d("ddffffffffffff","dddddddddddddddddddddddddddddddddddddddd")

//                        val intent =
//                            Intent(
//                                context,
//                                MainActivity::class.java
//                            )
//                    context!!.startActivity(intent)






                    Toast.makeText(context,"정상적으로 인식되었습니다",Toast.LENGTH_SHORT).show()
                }
            }
            mHandler.obtainMessage().sendToTarget()
        }
        private fun disconnectGattServer() {
            Log.d(TAG, "Closing Gatt connection")
            // disconnect and close the gatt
            if (bluetoothGatt != null) {
                bluetoothGatt?.disconnect()
                bluetoothGatt?.close()
                bluetoothGatt = null
            }
        }
    }

    fun connectGatt(device:BluetoothDevice):BluetoothGatt?{
        this.device = device

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bluetoothGatt = device.connectGatt(context, false, gattCallback,
                BluetoothDevice.TRANSPORT_LE)
        }
        else {
            bluetoothGatt = device.connectGatt(context, false, gattCallback)
        }
        return bluetoothGatt
    }
}