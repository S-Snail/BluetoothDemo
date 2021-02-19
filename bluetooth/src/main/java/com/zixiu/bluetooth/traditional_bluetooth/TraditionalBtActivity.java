package com.zixiu.bluetooth.traditional_bluetooth;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.zixiu.bluetooth.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * 经典蓝牙：
 * 开发流程：
 * 1、开启蓝牙
 * 2、扫描蓝牙
 * 3、配对蓝牙
 * 4、连接蓝牙
 * 5、通信
 */

public class TraditionalBtActivity extends AppCompatActivity {

    //权限相关
    private final static int CODE_REQUEST_PERMISSION = 1000;
    private final static int CODE_REQUST_OPEN_GPS = 1001;
    private String permissions[] = {Manifest.permission.ACCESS_FINE_LOCATION};
    private List<String> denyPermissionList = new ArrayList<>();

    //蓝牙相关
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traditional_bt);

        checkPermissions();

    }

    private void checkPermissions() {
        for (String permission : permissions) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(this, permission);
            if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permission);
            } else {
                denyPermissionList.add(permission);
            }
        }
        if (!denyPermissionList.isEmpty()) {
            String[] permissions = denyPermissionList.toArray(new String[denyPermissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, CODE_REQUEST_PERMISSION);
        }
    }

    private boolean checkGpsIsOpen() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null)
            return false;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void onPermissionGranted(String permission) {
        switch (permission) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                //打开GPS
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGpsIsOpen()) {
                    new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setPositiveButton("打开GPS", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(TraditionalBtActivity.this, "确定", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivityForResult(intent, CODE_REQUST_OPEN_GPS);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            })
                            .create()
                            .show();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODE_REQUEST_PERMISSION:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            onPermissionGranted(permissions[i]);
                        }
                    }
                }
                break;
            case CODE_REQUST_OPEN_GPS:
                if (checkGpsIsOpen()) {
                    Toast.makeText(this, "GPS已开启", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}