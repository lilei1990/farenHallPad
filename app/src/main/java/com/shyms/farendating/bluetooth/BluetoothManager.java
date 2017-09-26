package com.shyms.farendating.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import com.shyms.farendating.bluetooth.quuppatagdemo.CRC8;
import com.shyms.farendating.utils.LogcatUtil;

import java.util.Arrays;

import me.hokas.utils.PreferencesUtil;

/**
 * 蓝牙管理
 * Created by Hokas on 2015/6/30.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BluetoothManager {

    /**
     * For logging
     */
    /**
     * Name of the stored preferences  存储首选项的名称
     */
    public static final String PREFS_NAME = "MyPrefsFile";
    /**
     * Display names of the Adventizing Modes 显示的名字Adventizing模式
     */
    private final String[] advModes = new String[]{"ADVERTISE_MODE_LOW_POWER", "ADVERTISE_MODE_BALANCED", "ADVERTISE_MODE_LOW_LATENCY"};
    /**
     * Display names of the various TX power settings  显示不同的发射功率设置的名称
     */
    private final String[] advTxPowers = new String[]{"ADVERTISE_TX_POWER_ULTRA_LOW", "ADVERTISE_TX_POWER_LOW", "ADVERTISE_TX_POWER_MEDIUM", "ADVERTISE_TX_POWER_HIGH"};

    /**
     * reference to the #BluetoothLeAdvertiser  # BluetoothLeAdvertiser的引用
     */
    private BluetoothLeAdvertiser bluetoothLeAdvertiser;
    /** reference to the custom UI view that renders the pulsing Q  引用自定义UI视图,它显示脉冲Q */
//    private PulsingQView pulsingView;
    /**
     * flag indicating whether Direction Packet advertising has been started  标志指示方向是否包广告已经启动
     */
    private boolean dfPacketAdvRunning;
    /**
     * flag indicating whether Data Packet advertising has been started 滞后指示数据包广告是否已经开始了
     */
    private boolean dataPacketAdvRunning;
    /**
     * incrementing counter for Data Packets 数据包的递增计数器
     */
    private byte dataPacketCounter = 0;
    /**
     * Handler to time closing of Data Packet advs when user releases button 处理程序,当用户释放按钮关闭advs数据包
     */
    private Handler dataPacketTimer = new Handler();
    /**
     * Object for synchronization Object for synchronization
     */
    private Object dataPacketLock = new Object();
    /**
     * flag to indicate if button is held down 国旗表明如果按钮举行
     */
    private boolean button1Down = false;
    private Context context;

    public BluetoothManager(Context context) {
        this.context = context;
    }

    /**
     * Toggles the Direction Finding Packet broadcast on/off.
     * 切换测向数据包广播开/关。
     */
    public void toggleDFPacketAdv() {
        if (!dfPacketAdvRunning) {
            // first check that the tag id is set 首先检查标记id被设置
            SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
//            String tagID = settings.getString("tagID", null);
            String tagID = PreferencesUtil.getBluetoothMac().toLowerCase();
            if (tagID == null) {
                return;
            }
//            int mode = settings.getInt("advMode", AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY);
//            int tx = settings.getInt("advTxPower", AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);
            int mode = 2;
            int tx = 3;
            byte[] bytes = createBytesWithQuuppaDFPacket(tagID, mode, tx);
            if (bytes == null) {
                Toast.makeText(context, "Failed to create Address Payload!", Toast.LENGTH_LONG).show();
                return;
            }
            dfPacketAdvRunning = startAdvertising(mode, tx, bytes, dfPacketAdvCallback);
            return;
        } else {
            stopAdvertising(dfPacketAdvCallback);
            dfPacketAdvRunning = false;
//            pulsingView.setIsPulsing(false);
            return;
        }
    }

    /**
     * Starts Data Packet advertising.  数据包广告开始。
     */
    private void startDataPacketAdv() {
        synchronized (dataPacketLock) {
            if (dataPacketAdvRunning) {
                stopDataPacketAdv();
            }

            SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
            // first check that the tag id is set
//            String tagID = settings.getString("tagID", null);PreferencesUtil.getBluetoothMac();
            String tagID = PreferencesUtil.getBluetoothMac().toLowerCase();
            if (tagID == null) {
                return;
            }
//            int mode = settings.getInt("advMode", AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY);
//            int tx = settings.getInt("advTxPower", AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);
            int mode = 2;
            int tx = 3;
            dataPacketCounter++;
            if (dataPacketCounter > 15)
                dataPacketCounter = 0;
            byte[] bytes = createBytesWithQuuppaDataPacket(button1Down, tagID, dataPacketCounter);
            if (bytes == null) {
                return;
            }
            dataPacketAdvRunning = startAdvertising(mode, tx, bytes, dataPacketAdvCallback);
        }
    }


    /**
     * Stops Data Packet advertising. 停止数据包广告。
     */
    public void stopDataPacketAdv() {
        synchronized (dataPacketLock) {
            if (dataPacketAdvRunning) {
                stopAdvertising(dataPacketAdvCallback);
                dataPacketAdvRunning = false;
                return;
            }
        }
    }

    /**
     * Stops Advertising for given callback instance.
     * 停止广告给回调实例。
     *
     * @param callback AdvertiseCallback instance to be stopped. AdvertiseCallback实例停止。
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void stopAdvertising(AdvertiseCallback callback) {
        if (bluetoothLeAdvertiser == null)
            return;
        bluetoothLeAdvertiser.stopAdvertising(callback);
    }

    /**
     * Starts Advertising using given callback instance.
     * 广告使用给定的回调实例开始。
     *
     * @param mode     One of the values in AdvertiseSettings. 广告设置的值之一。
     * @param txPower  One of the values in AdvertiseSettings. 广告设置的值之一。
     * @param bytes    Bytes to be advertized.     字节做广告
     * @param callback Callback that gives ID to the advertising instance. 回调,给广告实例ID
     * @return true, if requesting for Advertiser was successfull, false otherwise. Please also check the callback status to see if advertiser is really running.   如果请求广告是成功的,否则假。也请检查回调状态广告是否真正运行。
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean startAdvertising(int mode, int txPower, byte[] bytes, AdvertiseCallback callback) {
        try {
            android.bluetooth.BluetoothManager btManager = (android.bluetooth.BluetoothManager) context.getSystemService(context.BLUETOOTH_SERVICE);
            BluetoothAdapter btAdapter = btManager.getAdapter();
            if (btAdapter.isEnabled()) {
                AdvertiseSettings advertiseSettings = new AdvertiseSettings.Builder()
                        .setAdvertiseMode(mode)
                        .setTxPowerLevel(txPower)
                        .setConnectable(true)
                        .build();

                AdvertiseData advertisementData = new AdvertiseData.Builder()
                        .setIncludeTxPowerLevel(false)
                        .addManufacturerData(0x00C7, bytes)
                        .build();

                bluetoothLeAdvertiser = btAdapter.getBluetoothLeAdvertiser();
                bluetoothLeAdvertiser.startAdvertising(advertiseSettings, advertisementData, callback);
            } else {
                final String message = "Start Bluetooth failed. Maybe turn it on?";
                LogcatUtil.d("AdvCallback", message);
                return false;
            }
            return true;
        } catch (Exception e) {
            final String message = "Start Bluetooth failed. " + e.getMessage();
            LogcatUtil.d("AdvCallback", message);
            return false;
        }
    }


    /**
     * Callback used for Direction Finding Packet advertisements.
     * 回调用于测向包广告
     */
    private final AdvertiseCallback dfPacketAdvCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings advertiseSettings) {
            final String message = "DF Broadcast started with " + advModes[advertiseSettings.getMode()] + ", " + advTxPowers[advertiseSettings.getTxPowerLevel()];
            LogcatUtil.d("AdvCallback", message);

//            pulsingView.setIsPulsing(true);
        }

        @Override
        public void onStartFailure(int i) {
            final String message = "Start broadcast failed error code: " + i;
            LogcatUtil.e("AdvCallback", message);

//            pulsingView.setIsPulsing(false);
        }
    };

    /**
     * Callback used for Data Packet advertisements.
     * 回调用于数据包广告
     */
    private final AdvertiseCallback dataPacketAdvCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings advertiseSettings) {
        }

        @Override
        public void onStartFailure(int i) {
            final String message = "Start Status broadcast failed error code: " + i;
            LogcatUtil.e("AdvCallback", message);

        }
    };


    /**
     * Creates a byte array with the given tag ID
     * 创建一个带有特定标记字节数组ID
     */
    private byte[] createQuuppaAddress(String tagID) {
        byte[] bytes = new byte[6];
        bytes[0] = (byte) Integer.parseInt(tagID.substring(0, 2), 16);
        bytes[1] = (byte) Integer.parseInt(tagID.substring(2, 4), 16);
        bytes[2] = (byte) Integer.parseInt(tagID.substring(4, 6), 16);
        bytes[3] = (byte) Integer.parseInt(tagID.substring(6, 8), 16);
        bytes[4] = (byte) Integer.parseInt(tagID.substring(8, 10), 16);
        bytes[5] = (byte) Integer.parseInt(tagID.substring(10, 12), 16);
        return bytes;
    }

    /**
     * Constructs a byte array using the Data Packet Specification.
     * 构造一个字节数组使用数据包的规范。
     * Please see the 'Specification of Bocoom Tag Emulation using Bluetooth Wireless Technology' -document for more details.
     * 请参阅“规范Bocoom标签模拟使用蓝牙无线技术的文档了解更多细节
     *
     * @param button1 boolean to raise one bit in the packet    包布尔提高一点
     * @param tagID   Tag ID to be injected into the packet   标签ID可以注入到数据包
     * @param counter counter value to be injected into the packet  计数器值注入包
     * @return constructed byte array   构造的字节数组
     */
    private byte[] createBytesWithQuuppaDataPacket(boolean button1, String tagID, byte counter) {
        // Please see the 'Specification of Bocoom Tag Emulation using Bluetooth Wireless Technology' -document for details how to create header
        //请参阅“规范Bocoom标签模拟使用蓝牙无线技术的文档详情如何创建标题
        byte header = counter;
        header |= 0x1 << 4;  // Bocoom Tag ID Type: 0x1 = "Generated by the SW developer (no guarantees for being globally unique)"
        //Bocoom标签ID类型:0 x1 = "生成的软件开发人员(无担保作为全球唯一)

        byte[] bytes = new byte[]{
                (byte) 0xF0, // Bocoom Packet ID: Quuppa Data Packet Bocoom包ID:Quuppa数据包
                (byte) header, // Payload header 有效载荷头
                (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, // Bocoom Address payload, will be replaced shortly...Bocoom地址有效载荷,不久将取代……
                // Payload, 16 octets 有效负载,16个八位字节
                (byte) 0xFF, // first byte: Developer Specific Data 第一个字节:开发人员的特定数据
                (byte) 0x1, (byte) 0x0, // Developer Specific ID 开发特定的ID
                (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0
        };

        // inject Bocoom Address into byte array 注入Bocoom地址字节数组
        byte[] qAddress = createQuuppaAddress(tagID);
        System.arraycopy(qAddress, 0, bytes, 2, 6);

        // inject button flags to array  注入按钮标志数组
        if (button1)
            bytes[11] |= 0x1;  // raise button 1 bit 提高按钮1位

        return bytes;
    }


    /**
     * Constructs a byte array using the Direction Finding Packet Specification.
     * 构造一个字节数组使用测向包规范。
     * Please see the 'Specification of Bocoom Tag Emulation using Bluetooth Wireless Technology' -document for more details.
     * 请参阅“规范Bocoom标签模拟使用蓝牙无线技术的文档了解更多细节
     *
     * @param tagID   Tag ID to be injected into the packet   标签ID可以注入到数据包
     * @param mode    One of the values of AdvertiseSettings.ADVERTISE_MODE_*. The value is injected in the DF Packet as indication of transmit rate.   AdvertiseSettings.ADVERTISE_MODE_ *的值之一。价值注入DF包的传输速率。
     * @param txPower One of the values of AdvertiseSettings.ADVERTISE_TX_*. The value is injected in the DF Packet as indication of transmit power.    AdvertiseSettings.ADVERTISE_TX_ *的值之一。的值注入DF数据包传输能量的指示。
     * @return constructed byte array 构造的字节数组
     */
    private byte[] createBytesWithQuuppaDFPacket(String tagID, int mode, int txPower) {
        // Please see the 'Specification of Bocoom Tag Emulation using Bluetooth Wireless Technology' -document for details
        byte header = (byte) (1 << 4); // 0x1 = Bocoom DF packet
        // bake in the TX power...
        if (txPower == AdvertiseSettings.ADVERTISE_TX_POWER_ULTRA_LOW)
            header |= (0 << 2); // NOP in fact...
        else if (txPower == AdvertiseSettings.ADVERTISE_TX_POWER_LOW)
            header |= (1 << 2);
        else if (txPower == AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
            header |= (2 << 2);
        else if (txPower == AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
            header |= (3 << 2);
        // bake in the TX rate...
        if (mode == AdvertiseSettings.ADVERTISE_MODE_LOW_POWER)  // about 1hz
            header |= 0; // NOP in fact...
        else if (mode == AdvertiseSettings.ADVERTISE_MODE_BALANCED)  // about 4hz
            header |= 1;
        else if (mode == AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY) // about 8Hz
            header |= 2;

        byte[] bytes = new byte[]{
                (byte) 0x01, // Bocoom Packet ID
                (byte) 0x21, // Device Type (0x21, android)
                (byte) header, // Payload header
                (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, // Bocoom Address payload, will be replaced shortly...
                (byte) 0xb4, // checksum, calculated later
                (byte) 0x67, (byte) 0xF7, (byte) 0xDB, (byte) 0x34, (byte) 0xC4, (byte) 0x03, (byte) 0x8E, (byte) 0x5C, (byte) 0x0B, (byte) 0xAA, (byte) 0x97, (byte) 0x30, (byte) 0x56, (byte) 0xE6 // DF field, 14 octets
        };

        // inject Bocoom Address into byte array
        byte[] qAddress = createQuuppaAddress(tagID);
        System.arraycopy(qAddress, 0, bytes, 3, 6);

        // calculate CRC and inject
        try {
            bytes[9] = CRC8.simpleCRC(Arrays.copyOfRange(bytes, 1, 9));
        } catch (Exception e) {
//            LogcatUtil.d(TAG, "CRC failed");
            return null;
        }
        return bytes;
    }

}
