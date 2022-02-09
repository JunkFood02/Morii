package com.hustunique.morii.design;

import static com.hustunique.morii.util.MyApplication.soundItemList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import morii.R;

import com.google.android.material.imageview.ShapeableImageView;

public class WhiteNoiseAdapter extends RecyclerView.Adapter<WhiteNoiseAdapter.ViewHolder> {
    private final Context context;
    //类型待定


    //构造方法，传入数据
    public WhiteNoiseAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public WhiteNoiseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        View inflater = LayoutInflater.from(context).inflate(R.layout.musicdesign_item, parent, false);
        return new WhiteNoiseAdapter.ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull WhiteNoiseAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //将数据和控件绑定
        //
        holder.Select = false;
        holder.imageView.setImageResource(soundItemList.get(position).getIconResId());

        holder.textView.setText(soundItemList.get(position).getSoundName());
        Log.d("RECYCLER2", String.valueOf(position));
        //holder.itemView.setOnTouchListener(new Drag(soundItemList.get(position % 7).getIconResId()));
        holder.itemView.setOnTouchListener(new StartDrag(position));
        holder.itemView.setOnClickListener(v -> {
            if (!holder.Select) holder.textView.setTextColor(Color.parseColor("#FF0000"));
            else holder.textView.setTextColor(Color.parseColor("#FF888888"));
            holder.Select = !holder.Select;
        });
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return soundItemList.size();
    }

    //内部类，绑定控件
    class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imageView;
        TextView textView;
        boolean Select = false;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.RoundIcon);
            textView = itemView.findViewById(R.id.IconName);
        }
    }
}
