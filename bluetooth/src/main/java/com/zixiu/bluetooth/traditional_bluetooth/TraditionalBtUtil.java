package com.zixiu.bluetooth.traditional_bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/**
 * Author: Snail
 * Time:  2021/2/19 2:51 PM
 * FileName:  TraditionalBtUtil
 * 简介：
 */
public class TraditionalBtUtil {

    private static BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    /**
     * <p>1、扫描方法</p>
     * 通过接收广播，获取扫描到的设备
     * return tue 扫描成功
     */
    public static boolean scanBlue() {
        if (!isBluetoothEnable()) {
            return false;
        }
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        return mBluetoothAdapter.startDiscovery();
    }

    /**
     * <p>2、取消扫描蓝牙</p>
     *
     * @return true 取消成功
     */
    public static boolean cancelScanBlue() {
        if (isSupportBlue()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        return true;
    }

    /**
     * <p>3、通过广播的方式，接收扫描结果</p>
     * 注册广播
     *
     * @param context
     * @param scanReceiver
     */
    public static void registerBroadcastReceiver(Activity context, BroadcastReceiver scanReceiver) {
        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        IntentFilter filter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(scanReceiver, filter1);
        context.registerReceiver(scanReceiver, filter2);
        context.registerReceiver(scanReceiver, filter3);
    }




    private static boolean isBluetoothEnable() {
        return isSupportBlue() && mBluetoothAdapter.isEnabled();
    }

    private static boolean isSupportBlue() {
        return mBluetoothAdapter != null;
    }

}
