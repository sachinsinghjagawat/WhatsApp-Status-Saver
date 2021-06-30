package com.example.whatsappstatussaver

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.annotations.Nullable


class MainActivity : AppCompatActivity() {

    val WAITING_TIME:Int= 3000;
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        actionBar?.hide();
        if (!checkPermission()){
            showPermissionDialog()
        }else{
            Handler().postDelayed(Runnable { /* Create an Intent that will start the Menu-Activity. */
                intent = Intent (this, MainActivity2::class.java)
                startActivity(intent);
                this@MainActivity.finish()
            }, WAITING_TIME.toLong())
        }

    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1234 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Handler().postDelayed(Runnable { /* Create an Intent that will start the Menu-Activity. */
                        intent = Intent (this, MainActivity2::class.java)
                        startActivity(intent);
                        this@MainActivity.finish()
                    }, WAITING_TIME.toLong())
                } else {
                    var sn= Snackbar.make(findViewById(android.R.id.content), "Premission Denied!! Click here to give permission", Snackbar.LENGTH_INDEFINITE)
                    sn.setAction("Click Here", View.OnClickListener {
                        intent= Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        var uri= Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri)
                        startActivity(intent);

                    })
                    sn.show();
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
    private fun showPermissionDialog() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(
                    String.format(
                        "package:%s", *arrayOf<Any>(
                            applicationContext.packageName
                        )
                    )
                )
                startActivityForResult(intent, 2000)
            } catch (e: Exception) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivityForResult(intent, 2000)
            }
        } else ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
            333
        )
    }

    private fun checkPermission(): Boolean {
        return if (SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val write = ContextCompat.checkSelfPermission(
                applicationContext,
                WRITE_EXTERNAL_STORAGE
            )
            val read = ContextCompat.checkSelfPermission(
                applicationContext,
                READ_EXTERNAL_STORAGE
            )
            write == PackageManager.PERMISSION_GRANTED &&
                    read == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2000) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                } else {
                }
            }
        }
    }
}