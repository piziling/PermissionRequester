package com.z2wenfa.permissionrequesterdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.z2wenfa.permissionrequestlibary.permission.PermissionRequest;
import com.z2wenfa.permissionrequestlibary.permission.PermissionRequestResultListener;
import com.z2wenfa.permissionrequestlibary.permission.Permissions;
import com.z2wenfa.permissionrequestlibary.permission.bean.PermissionEntity;

import java.util.ArrayList;

/**
 * Created by z2wenfa on 2016/10/26.
 */

public class TestFragment extends Fragment implements PermissionRequestResultListener {

    private Button btn2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn2 = (Button) view.findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionRequest.requestPermission(TestFragment.this.getActivity(), 2015,  Permissions.SENSORS);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PermissionRequest.registerBroadCast(this.getActivity(), this, 1);
    }

    @Override
    public void onGetGrantedPermission(int requestCode, ArrayList<PermissionEntity> grantedPermissions, boolean getAllRequestPermissions) {
        showMsg("请求" + grantedPermissions.toString() + "权限成功。" + "请求的权限是否全部获得:" + getAllRequestPermissions);
        Log.i("test3", "权限请求成功:" + grantedPermissions.toString() + ":" + getAllRequestPermissions);
    }

    @Override
    public void onGetDeniedPermission(int requestCode, ArrayList<PermissionEntity> deniedPermissions) {
        showMsg("请求" + deniedPermissions.toString() + "权限失败,请到设置中的应用管理授予权限。");
        Log.i("test3", "权限请求失败:" + deniedPermissions.toString());
    }

    @Override
    public void onShouldShowRequestPermissionRationale(int requestCode, ArrayList<PermissionEntity> deniedPermissions) {
        showMsg("程序需要获得" + deniedPermissions.toString() + "权限已正常使用功能。");
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
            mToast = Toast.makeText(this.getActivity().getApplicationContext(), text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        PermissionRequest.unRegisterBroadCast(2);
    }
}
