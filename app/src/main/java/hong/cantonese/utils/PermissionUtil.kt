package hong.cantonese.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * Created by Xiaohong on 2018/6/15.
 * desc: permission utils
 */
object PermissionUtil {


    fun requestPermission(context: Activity, permissions: Array<String>, requestCode: Int): Boolean {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            val unPermissioned = arrayListOf<String>()
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    unPermissioned.add(permission)
                }
            }
            if (!unPermissioned.isEmpty()) {
                ActivityCompat.requestPermissions(context, unPermissioned.toTypedArray(), requestCode)
                return false
            }
            return true
        }
        return true
    }

    fun shouldShowExplain(context: Activity, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                return false
            }
        }
        return true
    }


    fun forwardSetting(activity: Activity, requestCode: Int) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivityForResult(intent, requestCode)
    }

}