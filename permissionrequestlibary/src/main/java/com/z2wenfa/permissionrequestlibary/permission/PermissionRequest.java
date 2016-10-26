package com.z2wenfa.permissionrequestlibary.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

import com.z2wenfa.permissionrequestlibary.R;
import com.z2wenfa.permissionrequestlibary.permission.bean.PermissionEntity;
import com.z2wenfa.permissionrequestlibary.permission.broadcast.PermissionRequestResultBroadCast;
import com.z2wenfa.permissionrequestlibary.permission.util.Util;

import java.util.ArrayList;

import static com.z2wenfa.permissionrequestlibary.permission.broadcast.PermissionRequestResultBroadCast.PERMISSIONREQUESTRESULT_ACTION;

/**
 * 权限请求工具类
 * Created by z2wenfa on 2016/10/21.
 */
public class PermissionRequest {

    private static PermissionRequestResultBroadCast permissionRequestResultBroadCast;

    private static PermissionRequestResultBroadCast getPermissionRequestResultBroadCast() {

        return permissionRequestResultBroadCast;
    }

    /**
     * 注册权限请求结果监听 (需在请求权限前注册)
     *
     * @param activity
     * @param requestResultListener
     * @param tag
     */
    public static void registerBroadCast(Activity activity, PermissionRequestResultListener requestResultListener, Object tag) {
        if (permissionRequestResultBroadCast == null) {
            permissionRequestResultBroadCast = new PermissionRequestResultBroadCast();
            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(activity);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(PERMISSIONREQUESTRESULT_ACTION);
            broadcastManager.registerReceiver(permissionRequestResultBroadCast, intentFilter);
        }
        permissionRequestResultBroadCast.addRequestResultListener(tag, requestResultListener);
    }

    /**
     * 反注册权限请求结果监听器
     *
     * @param tag
     */
    public static void unRegisterBroadCast(Object tag) {
        getPermissionRequestResultBroadCast().removeRequestResultListener(tag);
    }

    /**
     * 一般的请求权限
     *
     * @param activity
     * @param requestCode
     * @param permissions
     */
    public static void requestPermission(Activity activity, int requestCode, int... permissions) {
        ArrayList<PermissionEntity> requestEntityList = Util.getEntityListByCodeArr(permissions);
        ArrayList<PermissionEntity> deniedEntityList = Util.getDeniedEntityListByEntityList(activity, requestEntityList);
        ArrayList<PermissionEntity> grantedEntityList = Util.getGrantedEntityListByEntityList(activity, requestEntityList);
        ArrayList<PermissionEntity> shouldShowEntityList = Util.getShouldShowEntityListByEntityList(activity, deniedEntityList);


        if (deniedEntityList.size() > 0 && Util.isOverMarshmallow()) {
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(PermissionRequestActivity.PERMISSIONREQUEST_KEY, deniedEntityList);
            intent.putExtra(PermissionRequestActivity.PERMISSIONREQUESTCODE_KEY, requestCode);
            intent.setClass(activity, PermissionRequestActivity.class);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            Util.sendRequestResult(activity, requestCode, null, null, shouldShowEntityList);
        } else {
            Util.sendRequestResult(activity, requestCode, grantedEntityList, deniedEntityList, shouldShowEntityList);
        }
    }


    /**
     * 在OnCreate方法中请求权限
     *
     * @param activity
     * @param requestCode
     * @param permissions
     */
    public static void requestPermissionInOnCreateMethod(final Activity activity, final int requestCode, final int... permissions) {
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          PermissionRequest.requestPermission(activity, requestCode, permissions);
                                      }
                                  }
                , 100);
    }


}
