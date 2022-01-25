package com.hustunique.morii.music;

import static com.hustunique.morii.util.MyApplication.musicTabList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import morii.R;

public class MusicSelectAdapter extends RecyclerView.Adapter<MusicSelectAdapter.ViewHolder> {
    private final Context context;

    //类型待定


    private View inflater;

    //构造方法，传入数据
    public MusicSelectAdapter(Context context, MusicSelectContract.IPresenter presenter) {
        this.context = context;
    }

    @NonNull
    @Override
    public MusicSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.musictab_item, parent, false);
        return new ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicSelectAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //将数据和控件绑定
        //

        holder.imageView.setBackgroundResource(musicTabList.get(position).getImageResId());
        Log.d("RECYCLER", String.valueOf(position));
        holder.itemView.setOnClickListener(v -> {
        });
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return musicTabList.size();
    }

    //内部类，绑定控件
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.EmotionPhoto);
        }
    }
}
