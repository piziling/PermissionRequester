package com.z2wenfa.permissionrequestlibary.permission;

import com.z2wenfa.permissionrequestlibary.permission.bean.PermissionEntity;

import java.util.ArrayList;

public interface PermissionRequestResultListener{

    void onGetGrantedPermission(int requestCode,ArrayList<PermissionEntity>grantedPermissions,boolean getAllRequestPermissions);

    void onGetDeniedPermission(int requestCode,ArrayList<PermissionEntity>deniedPermissions);

    void onShouldShowRequestPermissionRationale(int requestCode,ArrayList<PermissionEntity>shouldShowRequestPermission);

}