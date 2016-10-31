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


public class PermissionRequest {

    private static PermissionRequestResultBroadCast permissionRequestResultBroadCast;

    private static PermissionRequestResultBroadCast getPermissionRequestResultBroadCast() {

        return permissionRequestResultBroadCast;
    }


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


    public static void unRegisterBroadCast(Object tag) {
        getPermissionRequestResultBroadCast().removeRequestResultListener(tag);
    }


    public static void requestPermission(Activity activity, int requestCode, int... permissions) {
        ArrayList<PermissionEntity> requestEntityList = Util.getEntityListByCodeArr(permissions);


        if (Util.isOverMarshmallow()) {

            ArrayList<PermissionEntity> deniedEntityList = Util.getDeniedEntityListByEntityList(activity, requestEntityList);
            ArrayList<PermissionEntity> grantedEntityList = Util.getGrantedEntityListByEntityList(activity, requestEntityList);
            ArrayList<PermissionEntity> shouldShowEntityList = Util.getShouldShowEntityListByEntityList(activity, deniedEntityList);

            if (deniedEntityList.size() > 0) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(PermissionRequestActivity.PERMISSIONREQUEST_KEY, deniedEntityList);
                intent.putExtra(PermissionRequestActivity.PERMISSIONREQUESTCODE_KEY, requestCode);
                intent.setClass(activity, PermissionRequestActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                Util.sendRequestResult(activity, requestCode, null, null, shouldShowEntityList);
            } else {
                Util.sendRequestResult(activity, requestCode, requestEntityList, null, null);
            }

        } else {
            Util.sendRequestResult(activity, requestCode, requestEntityList, null, null);
        }
    }



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
