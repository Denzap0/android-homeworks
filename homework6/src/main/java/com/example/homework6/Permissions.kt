package com.example.homework6

import android.app.Activity
import androidx.core.app.ActivityCompat.requestPermissions
import java.security.Permission

class Permissions {

    companion object{
        fun askPermission(permissionsArray : Array<String>, callingActivity : Activity){
            requestPermissions(callingActivity, permissionsArray, 1000)
        }
    }
}