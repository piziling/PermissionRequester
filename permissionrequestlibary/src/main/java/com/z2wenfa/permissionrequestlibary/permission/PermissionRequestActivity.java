package com.z2wenfa.permissionrequestlibary.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.z2wenfa.permissionrequestlibary.R;
import com.z2wenfa.permissionrequestlibary.permission.bean.PermissionEntity;
import com.z2wenfa.permissionrequestlibary.permission.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by z2wenfa on 2016/10/21.
 */

public class PermissionRequestActivity extends Activity {
    public static final String PERMISSIONREQUEST_KEY = "permissionrequest_key";
    public static final String PERMISSIONREQUESTCODE_KEY = "permissionrequestcode_key";

    private ArrayList<PermissionEntity> requestEntityList; //请求对象集合
    private int requestCode; //请求码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestEntityList = getIntent().getParcelableArrayListExtra(PERMISSIONREQUEST_KEY);
        requestCode = getIntent().getIntExtra(PERMISSIONREQUESTCODE_KEY, 100);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            _requestPermissions(requestCode, Util.getStrArrByEntityList(requestEntityList));
        } else {
            sendRequestResultAndFinish(this, requestCode, requestEntityList, null, null);
        }
    }


    /**
     * 请求未获得的权限权限
     *
     * @param requestCode
     * @param permissions
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void _requestPermissions(int requestCode, String... permissions) {
        List<String> deniedPermissions = Util.findDeniedStrListByStrArr(this, permissions);
        String[] deniedPermissionArr = deniedPermissions.toArray(new String[deniedPermissions.size()]);
        PermissionRequestActivity.this.requestPermissions(deniedPermissionArr, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        execRequestResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 请求结果处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void execRequestResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<PermissionEntity> deniedPermissions = new ArrayList<>();
        ArrayList<PermissionEntity> grantedPermissions = new ArrayList<>();
        ArrayList<PermissionEntity> shouldShowPermissions = new ArrayList<>();


        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(Util.getEntityByStr(permissions[i]));
            } else {
                grantedPermissions.add(Util.getEntityByStr(permissions[i]));
            }
        }

        shouldShowPermissions.addAll(Util.getShouldShowEntityListByEntityList(this, deniedPermissions));

        sendRequestResultAndFinish(this, requestCode, grantedPermissions, deniedPermissions, shouldShowPermissions);
    }

    /**
     * 发送处理的结果并关闭界面
     *
     * @param requestCode
     * @param grantedPermissions
     * @param deniedPermissions
     * @param shouldShowPermissionEntity
     */
    private void sendRequestResultAndFinish(Activity activity, int requestCode, ArrayList<PermissionEntity> grantedPermissions, ArrayList<PermissionEntity> deniedPermissions, ArrayList<PermissionEntity> shouldShowPermissionEntity) {
        Util.sendRequestResult(activity, requestCode, grantedPermissions, deniedPermissions, shouldShowPermissionEntity);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
