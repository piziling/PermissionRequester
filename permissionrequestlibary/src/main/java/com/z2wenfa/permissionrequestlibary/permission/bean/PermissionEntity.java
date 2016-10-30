package com.z2wenfa.permissionrequestlibary.permission.bean;

import android.os.Parcel;
import android.os.Parcelable;


public class PermissionEntity implements Parcelable {
    private int permissionCode;
    private String permissionManifestStr;
    private String permissionName;



    public PermissionEntity() {

    }

    public PermissionEntity(int permissionCode, String permissionManifestStr, String permissionName) {
        this.permissionCode = permissionCode;
        this.permissionManifestStr = permissionManifestStr;
        this.permissionName = permissionName;
    }

    protected PermissionEntity(Parcel in) {
        permissionCode = in.readInt();
        permissionManifestStr = in.readString();
        permissionName = in.readString();
    }

    public static final Creator<PermissionEntity> CREATOR = new Creator<PermissionEntity>() {
        @Override
        public PermissionEntity createFromParcel(Parcel in) {
            return new PermissionEntity(in);
        }

        @Override
        public PermissionEntity[] newArray(int size) {
            return new PermissionEntity[size];
        }
    };

    public int getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(int permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionManifestStr() {
        return permissionManifestStr;
    }

    public void setPermissionManifestStr(String permissionManifestStr) {
        this.permissionManifestStr = permissionManifestStr;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(permissionCode);
        dest.writeString(permissionManifestStr);
        dest.writeString(permissionName);
    }

    @Override
    public String toString() {
        return permissionManifestStr;
    }
}
