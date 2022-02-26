package com.hustunique.morii.edit

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.*
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.hustunique.morii.edit.EditContract.IListener
import com.hustunique.morii.edit.EditContract.IModel

class EditModel internal constructor(private val iListener: IListener) : IModel {
    private lateinit var appCompatActivityUse: AppCompatActivity

    //注册权限请求
    private var ActivityLauncherPermission: ActivityResultLauncher<String?> =
        object : ActivityResultLauncher<String?>() {
            override fun launch(input: String?, options: ActivityOptionsCompat?) {}
            override fun unregister() {}
            override fun getContract(): ActivityResultContract<String?, *> {
                TODO("Not yet implemented")
            }

        }

    // avoid the appearance of a null reference !!!
    private var ActivityResultLauncher: ActivityResultLauncher<Intent?> =
        object : ActivityResultLauncher<Intent?>() {
            override fun launch(input: Intent?, options: ActivityOptionsCompat?) {}
            override fun unregister() {}
            override fun getContract(): ActivityResultContract<Intent?, *> {
                TODO("Not yet implemented")
            }

        }

    override fun setAppCompatActivityUse(context: Context) {
        appCompatActivityUse = context as AppCompatActivity
        ActivityLauncherPermission = appCompatActivityUse.registerForActivityResult(
            RequestPermission()
        ) { result: Boolean ->
            if (result) {
                ChoosePhoto()
            } else {
                Toast.makeText(appCompatActivityUse, "获取相册权限失败", Toast.LENGTH_SHORT).show()
            }
        }
        ActivityResultLauncher = appCompatActivityUse.registerForActivityResult(
            StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                handleImageOnKitKat(data)
            }
        }
    }

    override val picture: Unit
        get() {
            ActivityLauncherPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    fun ChoosePhoto() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        ActivityResultLauncher.launch(intent)
    }

    @TargetApi(19)
    private fun handleImageOnKitKat(data: Intent?) {
        var imagePath: String? = null
        val uri = data!!.data
        Log.d("TAG", "handleImageOnKitKat: uri is $uri")
        if (DocumentsContract.isDocumentUri(appCompatActivityUse, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri!!.authority) {
                val id = docId.split(":").toTypedArray()[1] // 解析出数字格式的id
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.downloads.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(docId)
                )
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.path
        }
        displayImage(imagePath) // 根据图片路径显示图片
    }

    @SuppressLint("Range")
    fun getImagePath(uri: Uri?, selection: String?): String? {
        var path: String? = null
        // 通过Uri和selection来获取真实的图片路径
        val cursor = appCompatActivityUse.contentResolver.query(
            uri!!, null, selection, null, null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

    private fun displayImage(imagePath: String?) {
        if (imagePath != null) {
            iListener.setIt(imagePath)
        } else {
            Toast.makeText(appCompatActivityUse, "failed to get image", Toast.LENGTH_SHORT).show()
        }
    }
}