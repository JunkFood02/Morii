package com.hustunique.morii.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.hustunique.morii.content.ContentActivity;

import java.io.File;
import java.util.Calendar;
import java.util.List;

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

    private Context context;


    private List<MusicDiaryItem> list;
    //类型待定


    private View inflater;

    //构造方法，传入数据
    public MusicDiaryAdapter(Context context, List<MusicDiaryItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.cardview_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //将数据和控件绑定
        //
        MusicDiaryItem musicDiaryItem = list.get(position);
        String imagePath = musicDiaryItem.getImagePath();
        if (null != imagePath)
            Glide.with(context).load(new File(imagePath)).into(holder.PhotoTitle);
        else holder.PhotoTitle.setImageResource(R.drawable.orange);
        holder.TextDate.setText(musicDiaryItem.getDate());
        Log.d("RECYCLER", String.valueOf(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra("diary", musicDiaryItem);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) context,
                                Pair.create(holder.PhotoTitle, "photo"));
                context.startActivity(intent,
                        options.toBundle());

            }
        });
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return list.size();
//        return 5;
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TextTitle;
        TextView TextTime;
        TextView TextDate;
        ShapeableImageView PhotoTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            TextTitle = (TextView) itemView.findViewById(R.id.TextTitle);
            TextDate = (TextView) itemView.findViewById(R.id.TextDate);
            PhotoTitle = itemView.findViewById(R.id.PhotoTitle);
            TextTime = (TextView) itemView.findViewById(R.id.TextTime);
        }
    }
}
