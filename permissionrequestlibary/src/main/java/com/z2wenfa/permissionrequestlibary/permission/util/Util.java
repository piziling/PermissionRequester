package com.z2wenfa.permissionrequestlibary.permission.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.util.SparseArray;

import com.z2wenfa.permissionrequestlibary.permission.Permissions;
import com.z2wenfa.permissionrequestlibary.permission.bean.PermissionEntity;
import com.z2wenfa.permissionrequestlibary.permission.broadcast.PermissionRequestResultBroadCast;

import java.util.ArrayList;

import static com.z2wenfa.permissionrequestlibary.permission.Permissions.getPermissionMap;

/**
 * Created by z2wenfa on 2016/10/24.
 */

public class Util {
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


    @TargetApi(value = Build.VERSION_CODES.M)
    public static ArrayList<String> findDeniedStrListByStrArr(Activity activity, String... permission) {
        ArrayList<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }



    public static String getPermissionStrByCode(int permissionCode) {
        switch (permissionCode) {
            case Permissions.CALENDAR:
                return Manifest.permission.READ_CALENDAR;
            case Permissions.CAMERA:
                return Manifest.permission.CAMERA;
            case Permissions.CONTACTS:
                return Manifest.permission.READ_CONTACTS;
            case Permissions.LOCATION:
                return Manifest.permission.ACCESS_FINE_LOCATION;
            case Permissions.PHONE:
                return Manifest.permission.CALL_PHONE;
            case Permissions.MICROPHONE:
                return Manifest.permission.RECORD_AUDIO;
            case Permissions.SENSORS:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    return Manifest.permission.BODY_SENSORS;
                }
            case Permissions.SMS:
                return Manifest.permission.READ_SMS;
            case Permissions.STORAGE:
                return Manifest.permission.READ_EXTERNAL_STORAGE;
        }
        return Manifest.permission.CAMERA;
    }


    public static String[] getStrArrByEntityList(ArrayList<PermissionEntity> entityList) {
        String[] strArr = new String[entityList.size()];
        for (int i = 0; i < entityList.size(); i++) {
            strArr[i] = entityList.get(i).getPermissionManifestStr();
        }
        return strArr;
    }



    public static PermissionEntity getEntityByStr(String manifestPermissionStr) {
        SparseArray<PermissionEntity> permissionEntitySparseArray = getPermissionMap();
        for (int i = 0; i < permissionEntitySparseArray.size(); i++) {
            PermissionEntity permissionEntity = permissionEntitySparseArray.valueAt(i);
            if (permissionEntity.getPermissionManifestStr().equals(manifestPermissionStr)) {
                return permissionEntity;
            }
        }
        throw new RuntimeException("Please input correct permission Manifest String!");
    }


    public static ArrayList<PermissionEntity> getEntityListByStrArr(String[] manifestPermissionStrArr) {
        ArrayList<PermissionEntity> permissionEntityArrayList = new ArrayList<>();
        for (int i = 0; i < manifestPermissionStrArr.length; i++) {
            permissionEntityArrayList.add(getEntityByStr(manifestPermissionStrArr[i]));
        }
        return permissionEntityArrayList;
    }


    public static PermissionEntity getEntityByCode(int permissionCode) {
        SparseArray<PermissionEntity> permissionEntitySparseArray = getPermissionMap();
        return permissionEntitySparseArray.get(permissionCode, permissionEntitySparseArray.get(Permissions.CAMERA));
    }


    public static ArrayList<PermissionEntity> getEntityListByCodeArr(int[] permissionCodeArr) {
        ArrayList<PermissionEntity> permissionEntityArrayList = new ArrayList<>();
        for (int i = 0; i < permissionCodeArr.length; i++) {
            permissionEntityArrayList.add(getEntityByCode(permissionCodeArr[i]));
        }
        return permissionEntityArrayList;
    }



    @TargetApi(Build.VERSION_CODES.M)
    public static ArrayList<PermissionEntity> getDeniedEntityListByEntityList(Activity activity, ArrayList<PermissionEntity> permissionEntityArrayList) {
        ArrayList<PermissionEntity> deniedEntityList = new ArrayList<>();
        for (int i = 0; i < permissionEntityArrayList.size(); i++) {
            PermissionEntity checkPermissionEntity = permissionEntityArrayList.get(i);
            if (activity.checkSelfPermission(checkPermissionEntity.getPermissionManifestStr()) != PackageManager.PERMISSION_GRANTED) {
                deniedEntityList.add(checkPermissionEntity);
            }
        }
        return deniedEntityList;
    }


    @TargetApi(Build.VERSION_CODES.M)
    public static ArrayList<PermissionEntity> getGrantedEntityListByEntityList(Activity activity, ArrayList<PermissionEntity> permissionEntityArrayList) {
        ArrayList<PermissionEntity> deniedEntityList = new ArrayList<>();
        for (int i = 0; i < permissionEntityArrayList.size(); i++) {
            PermissionEntity checkPermissionEntity = permissionEntityArrayList.get(i);
            if (activity.checkSelfPermission(checkPermissionEntity.getPermissionManifestStr()) == PackageManager.PERMISSION_GRANTED) {
                deniedEntityList.add(checkPermissionEntity);
            }
        }
        return deniedEntityList;
    }


    @TargetApi(Build.VERSION_CODES.M)
    public static ArrayList<PermissionEntity> getShouldShowEntityListByEntityList(Activity activity, ArrayList<PermissionEntity> permissionEntityArrayList) {
        ArrayList<PermissionEntity> shouldShowEntityList = new ArrayList<>();
        for (int i = 0; i < permissionEntityArrayList.size(); i++) {
            PermissionEntity checkPermissionEntity = permissionEntityArrayList.get(i);
            if (activity.shouldShowRequestPermissionRationale(permissionEntityArrayList.get(i).getPermissionManifestStr())) {
                shouldShowEntityList.add(checkPermissionEntity);
            }
        }
        return shouldShowEntityList;
    }


    public static void sendRequestResult(Activity activity, int requestCode, ArrayList<PermissionEntity> grantedPermissions, ArrayList<PermissionEntity> deniedPermissions, ArrayList<PermissionEntity> shouldShowPermissionEntity) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(activity);
        Intent intent = new Intent(PermissionRequestResultBroadCast.PERMISSIONREQUESTRESULT_ACTION);
        intent.putParcelableArrayListExtra(PermissionRequestResultBroadCast.DENID_PERMISSION_KEY, deniedPermissions);
        intent.putParcelableArrayListExtra(PermissionRequestResultBroadCast.GRANTED_PERMISSION_KEY, grantedPermissions);
        intent.putParcelableArrayListExtra(PermissionRequestResultBroadCast.SHOULDSHOWPERMISSION_KEY, shouldShowPermissionEntity);
        intent.putExtra(PermissionRequestResultBroadCast.PERMISSION_REQUESTCODE_KEY, requestCode);
        localBroadcastManager.sendBroadcast(intent);
    }
}
