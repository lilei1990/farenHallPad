package com.shyms.farendating.bluetooth;

import android.bluetooth.BluetoothAdapter;

/**
 * 蓝牙工具
 * Created by hokas on 2015/7/28.
 */
public class BluetoothUtils {

    public static String getBuletoothMac() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isEnabled()) {
                return bluetoothAdapter.getAddress();
            }
        }
        return "";
    }
    public static boolean isOpenBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.isEnabled();
    }
}
