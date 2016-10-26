package com.z2wenfa.permissionrequesterdemo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.z2wenfa.permissionrequestlibary.permission.PermissionRequest;
import com.z2wenfa.permissionrequestlibary.permission.Permissions;

public class MainActivity extends BaseActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //在OnCreate方法中请求权限
        PermissionRequest.requestPermissionInOnCreateMethod(this, 2015, Permissions.CAMERA, Permissions.LOCATION);
    }


    private void initView() {
        button = (Button) findViewById(R.id.btn1);
        //通过点击事件请求权限
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionRequest.requestPermission(MainActivity.this, 2015, Permissions.CONTACTS, Permissions.SMS);
            }
        });

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new TestFragment());
        fragmentTransaction.commit();
    }


}
