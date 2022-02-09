package com.hustunique.morii.edit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

public class EditModel implements EditContract.IModel{

    private String path;

    private AppCompatActivity appCompatActivityUse;

    private final EditContract.IListener iListener;
    //注册权限请求
    private ActivityResultLauncher<String> ActivityLauncherPermission = new ActivityResultLauncher<String>() {
        @Override
        public void launch(String input, @Nullable ActivityOptionsCompat options) {

        }

        @Override
        public void unregister() {

        }

        @NonNull
        @Override
        public ActivityResultContract<String, ?> getContract() {
            return null;
        }
    };
    // avoid the appearance of a null reference !!!
    private ActivityResultLauncher<Intent> ActivityResultLauncher = new ActivityResultLauncher<Intent>() {
        @Override
        public void launch(Intent input, @Nullable ActivityOptionsCompat options) {

        }

        @Override
        public void unregister() {

        }

        @NonNull
        @Override
        public ActivityResultContract<Intent, ?> getContract() {
            return null;
        }
    };

    EditModel(EditContract.IListener iListener){
        this.iListener = iListener;
    }


    @Override
    public void setAppCompatActivityUse(Context context){
        this.appCompatActivityUse =(AppCompatActivity) context;
        ActivityLauncherPermission =  appCompatActivityUse.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), result ->{
                    if (result.booleanValue() == true ){
                        ChoosePhoto();
                    }else{
                        Toast.makeText(appCompatActivityUse,"获取相册权限失败",Toast.LENGTH_SHORT).show();
                    }
                });
        ActivityResultLauncher = appCompatActivityUse.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        handleImageOnKitKat(data);
                    }
                });
    }

    @Override
    public void getPicture() {
        ActivityLauncherPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void ChoosePhoto() {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            ActivityResultLauncher.launch(intent);
    }


    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(appCompatActivityUse, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    @SuppressLint("Range")
    public String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = appCompatActivityUse.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            iListener.setIt(imagePath);
        } else {
            Toast.makeText(appCompatActivityUse, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

}
