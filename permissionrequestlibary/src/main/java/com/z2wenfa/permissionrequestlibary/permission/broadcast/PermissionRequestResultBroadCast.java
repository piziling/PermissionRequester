package com.z2wenfa.permissionrequestlibary.permission.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.z2wenfa.permissionrequestlibary.permission.PermissionRequestResultListener;
import com.z2wenfa.permissionrequestlibary.permission.bean.PermissionEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by z2wenfa on 2016/10/25.
 */

public class PermissionRequestResultBroadCast extends BroadcastReceiver {
    public static final String PERMISSIONREQUESTRESULT_ACTION = "PERMISSIONrEQUESTrESULT_ACTION";
    public static final String GRANTED_PERMISSION_KEY = "GRANTED_PERMISSION_KEY";
    public static final String DENID_PERMISSION_KEY = "DENID_PERMISSION_KEY";
    public static final String PERMISSION_REQUESTCODE_KEY = "PERMISSION_REQUESTCODE_KEY";
    public static final String SHOULDSHOWPERMISSION_KEY = "SHOULDSHOWPERMISSION_KEY";


    private Map<Object, PermissionRequestResultListener> map = new HashMap<>();

    public void addRequestResultListener(Object tag, PermissionRequestResultListener permissionRequestResultListener) {
        map.put(tag, permissionRequestResultListener);
    }

    public void removeRequestResultListener(Object tag) {
        map.remove(tag);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (PERMISSIONREQUESTRESULT_ACTION.equals(action)) {
            ArrayList<PermissionEntity> grantedPermissions = intent.getParcelableArrayListExtra(GRANTED_PERMISSION_KEY);
            ArrayList<PermissionEntity> deniedPermissions = intent.getParcelableArrayListExtra(DENID_PERMISSION_KEY);
            ArrayList<PermissionEntity> shouldShowPermissions = intent.getParcelableArrayListExtra(SHOULDSHOWPERMISSION_KEY);
            int requestCode = intent.getIntExtra(PERMISSION_REQUESTCODE_KEY, -1);

            for (Map.Entry<Object, PermissionRequestResultListener> entrySet : map.entrySet()) {
                PermissionRequestResultListener permissionRequestResultListener = entrySet.getValue();
                if (grantedPermissions != null && grantedPermissions.size() > 0) {
                    boolean getAllRequestPermissions = (deniedPermissions == null || deniedPermissions.size() == 0);
                    permissionRequestResultListener.onGetGrantedPermission(requestCode, grantedPermissions, getAllRequestPermissions);
                }

                if (deniedPermissions != null && deniedPermissions.size() > 0) {
                    permissionRequestResultListener.onGetDeniedPermission(requestCode, deniedPermissions);
                }

                if (shouldShowPermissions != null && shouldShowPermissions.size() > 0) {
                    permissionRequestResultListener.onShouldShowRequestPermissionRationale(requestCode, shouldShowPermissions);
                }
            }
        }

    }


}
