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
 * 实现了PermissionRequestResultListener 请求结果监听器的类
 * Created by z2wenfa on 2016/10/26.
 */
public class BaseActivity extends AppCompatActivity implements PermissionRequestResultListener {

    private int tag = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionRequest.registerBroadCast(this, this, tag);//注册请求结果监听
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PermissionRequest.unRegisterBroadCast(tag);//反注册请求结果的监听
    }

    /**
     *请求权限被授予时调用
     * @param requestCode 请求码
     * @param grantedPermissions   被授予的权限
     * @param getAllRequestPermissions  这次请求的权限是否全部都获得了
     */
    @Override
    public void onGetGrantedPermission(int requestCode, ArrayList<PermissionEntity> grantedPermissions, boolean getAllRequestPermissions) {
        showMsg("请求" + grantedPermissions.toString() + "权限成功。" + "请求的权限是否全部获得:" + getAllRequestPermissions);
        Log.i("test3", "权限请求成功:" + grantedPermissions.toString() + ":" + getAllRequestPermissions);
    }

    /**
     *请求权限被拒绝是调用
     * @param requestCode 请求码
     * @param deniedPermissions 被拒绝的权限
     */
    @Override
    public void onGetDeniedPermission(int requestCode, ArrayList<PermissionEntity> deniedPermissions) {
        showMsg("请求" + deniedPermissions.toString() + "权限失败,请到设置→应用管理→权限中授予权限。");
        Log.i("test3", "权限请求失败:" + deniedPermissions.toString());
    }

    /**
     *需要告诉用户为什么申请时调用
     * @param requestCode 请求码
     * @param deniedPermissions 需要告知用户的权限
     */
    @Override
    public void onShouldShowRequestPermissionRationale(int requestCode, ArrayList<PermissionEntity> deniedPermissions) {
        showMsg("程序需要获得" + deniedPermissions.toString() + "权限以正常使用功能。");
        Log.i("test3", "未获得:" + deniedPermissions.toString());
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
