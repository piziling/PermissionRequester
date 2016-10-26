package com.z2wenfa.permissionrequesterdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.z2wenfa.permissionrequestlibary.permission.PermissionRequest;
import com.z2wenfa.permissionrequestlibary.permission.PermissionRequestResultListener;
import com.z2wenfa.permissionrequestlibary.permission.bean.PermissionEntity;

import java.util.ArrayList;

/**
 * Created by z2wenfa on 2016/10/26.
 */

public class BaseActivity extends AppCompatActivity implements PermissionRequestResultListener {

    private int tag = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 注册权限请求结果监听器
         */
        PermissionRequest.registerBroadCast(this, this, tag);
    }

    @Override
    public void onGetGrantedPermission(int requestCode, ArrayList<PermissionEntity> grantedPermissions, boolean getAllRequestPermissions) {
        showMsg("请求" + grantedPermissions.toString() + "权限成功。" + "请求的权限是否全部获得:" + getAllRequestPermissions);
        Log.i("test3", "权限请求成功:" + grantedPermissions.toString() + ":" + getAllRequestPermissions);
    }

    @Override
    public void onGetDeniedPermission(int requestCode, ArrayList<PermissionEntity> deniedPermissions) {
        showMsg("请求" + deniedPermissions.toString() + "权限失败,请到设置→应用管理→权限中授予权限。");
        Log.i("test3", "权限请求失败:" + deniedPermissions.toString());
    }

    @Override
    public void onShouldShowRequestPermissionRationale(int requestCode, ArrayList<PermissionEntity> deniedPermissions) {
        showMsg("程序需要获得" + deniedPermissions.toString() + "权限已正常使用功能。");
        Log.i("test3", "未获得:" + deniedPermissions.toString());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 反注册监听器 释放内存
         */
        PermissionRequest.unRegisterBroadCast(tag);
    }


    private void showMsg(String s) {
        show(s, Toast.LENGTH_SHORT);
    }

    private static Toast mToast;

    public void show(CharSequence text, int duration) {
        text = TextUtils.isEmpty(text == null ? "" : text.toString()) ? "请检查您的网络！"
                : text;
        if (mToast == null) {
            mToast = Toast.makeText(this.getApplicationContext(), text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }


}
