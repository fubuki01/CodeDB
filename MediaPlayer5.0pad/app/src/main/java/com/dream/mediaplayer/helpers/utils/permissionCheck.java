package com.dream.mediaplayer.helpers.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import static android.content.ContentValues.TAG;


/**
 * Created by Wyd on 2017/6/28.
 */

public class permissionCheck {
    private PermissionsChecker mPermissionsChecker;
    public static boolean flag = false;
    public static boolean flagStop = true;

    private static final int PERMISSIONS_REQUEST_CODE = 201702; // 请求码
    private Activity activityCheck;
    // 所需的全部权限,依据应用使用权限
    static final String[] PERMISSIONS = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public permissionCheck(Activity activity) {
        this.activityCheck = activity;
        mPermissionsChecker = new PermissionsChecker(activityCheck);
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            flag = true;
            startPermissionsActivity();
        }

    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(activityCheck,
                PERMISSIONS_REQUEST_CODE, PERMISSIONS);

    }


    public void onActivityResultTest(Activity activity, int requestCode, int resultCode) {
        if (requestCode == PERMISSIONS_REQUEST_CODE
                && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            activity.finish();
        }else {
            flagStop = false;
            Log.e("wyd", "onActivityResultTest: "+flag);
        }
    }

}
