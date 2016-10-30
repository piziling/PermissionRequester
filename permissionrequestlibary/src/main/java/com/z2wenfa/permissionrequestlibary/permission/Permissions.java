package com.z2wenfa.permissionrequestlibary.permission;

import android.Manifest;
import android.os.Build;
import android.util.SparseArray;

import com.z2wenfa.permissionrequestlibary.permission.bean.PermissionEntity;

public class Permissions {
    public static final int CALENDAR = 1;
    public static final int CAMERA = 2;
    public static final int CONTACTS = 3;
    public static final int LOCATION = 4;
    public static final int MICROPHONE = 5;
    public static final int PHONE = 6;
    public static final int SENSORS = 7;
    public static final int SMS = 8;
    public static final int STORAGE = 9;


    private static SparseArray<PermissionEntity> permissionMap;

    public static SparseArray<PermissionEntity> getPermissionMap() {
        if (permissionMap == null) {
            permissionMap = new SparseArray<>();
            permissionMap.put(CALENDAR, new PermissionEntity(CALENDAR, Manifest.permission.READ_CALENDAR, "日历"));
            permissionMap.put(CAMERA, new PermissionEntity(CAMERA, Manifest.permission.CAMERA, "照相机"));
            permissionMap.put(CONTACTS, new PermissionEntity(CONTACTS, Manifest.permission.READ_CONTACTS, "联系人"));
            permissionMap.put(LOCATION, new PermissionEntity(LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, "定位"));
            permissionMap.put(PHONE, new PermissionEntity(PHONE, Manifest.permission.CALL_PHONE, "拨号"));
            permissionMap.put(MICROPHONE, new PermissionEntity(MICROPHONE, Manifest.permission.RECORD_AUDIO, "麦克风"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                permissionMap.put(SENSORS, new PermissionEntity(SENSORS, Manifest.permission.BODY_SENSORS, "传感器"));
            }
            permissionMap.put(SMS, new PermissionEntity(SMS, Manifest.permission.READ_SMS, "短信"));
            permissionMap.put(STORAGE, new PermissionEntity(STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, "SD卡"));
        }
        return permissionMap;
    }






}


