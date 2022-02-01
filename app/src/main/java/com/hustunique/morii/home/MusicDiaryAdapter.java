package com.hustunique.morii.home;

import static com.hustunique.morii.util.MyApplication.context;
import static com.hustunique.morii.util.MyApplication.musicTabList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.hustunique.morii.content.ContentActivity;
import com.hustunique.morii.util.DatabaseUtil;

import java.io.File;
import java.util.List;
import java.util.Locale;

import morii.R;


/*
① 创建一个继承RecyclerView.Adapter<VH>的Adapter类
② 创建一个继承RecyclerView.ViewHolder的静态内部类
③ 在Adapter中实现3个方法：
   onCreateViewHolder()
   onBindViewHolder()
   getItemCount()
*/
public class MusicDiaryAdapter extends RecyclerView.Adapter<MusicDiaryAdapter.MyViewHolder> {

    private final Activity activity;
    private static final String TAG = "MusicDiaryAdapter";

    private final List<MusicDiaryItem> list;
    //类型待定


    //构造方法，传入数据
    public MusicDiaryAdapter(Activity context, List<MusicDiaryItem> list) {
        this.activity = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        View inflater = LayoutInflater.from(activity).inflate(R.layout.cardview_item, parent, false);
        return new MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //将数据和控件绑定
        //
        MusicDiaryItem musicDiaryItem = list.get(position);
        String imagePath = musicDiaryItem.getImagePath();
        if (null != imagePath) {
            Glide.with(holder.itemView).load(imagePath)
                    .into(holder.PhotoTitle);
            Log.d(TAG, "load image success");
        } else {
            Glide.with(holder.itemView)
                    .load(musicTabList.get(musicDiaryItem.getMusicTabId()).getImageResId())
                    .into(holder.PhotoTitle);
            Log.d(TAG, "onBindViewHolder: " + musicTabList.get(musicDiaryItem.getMusicTabId()).getImageResId());
        }
        holder.TextTitle.setText(musicDiaryItem.getTitle());
        holder.TextDate.setText("# " + musicDiaryItem.getDate());
        Log.d("RECYCLER", String.valueOf(position));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ContentActivity.class);
            intent.putExtra("diary", musicDiaryItem);
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) activity,
                            Pair.create(holder.PhotoTitle, "photo"));
            activity.startActivity(intent, options.toBundle());
        });
        holder.itemView.setOnLongClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity);
            builder.setTitle("确定删除这个音乐日记吗？")
                    .setMessage("这个操作不可被撤销。")
                    .setNegativeButton("取消", (dialog, which) -> {
                    })
                    .setPositiveButton("确认", (dialog, which) -> {
                        DatabaseUtil.deleteDiary(musicDiaryItem.getItemID());
                        Log.d(TAG, "deletePosition: " + holder.getLayoutPosition());
                        notifyItemRemoved(holder.getLayoutPosition());
                        list.remove(holder.getLayoutPosition());
                    })
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //内部类，绑定控件
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TextTitle;
        TextView TextDate;
        ShapeableImageView PhotoTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            TextTitle = itemView.findViewById(R.id.TextTitle);
            TextDate = itemView.findViewById(R.id.TextDate);
            PhotoTitle = itemView.findViewById(R.id.PhotoTitle);

        }
    }
}
