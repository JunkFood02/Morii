package com.hustunique.musica.design;

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

import com.hustunique.musica.R;
import com.hustunique.musica.RoundImage;

import java.util.List;

public class WhiteNoiseAdapter extends RecyclerView.Adapter<com.hustunique.musica.design.WhiteNoiseAdapter.ViewHolder> {
    private final Context context;

    private final List<Integer> list;
    //类型待定


    private View inflater;

    //构造方法，传入数据
    public WhiteNoiseAdapter(Context context, IMixDesign.IPresenter presenter) {
        this.context = context;
        this.list = presenter.getList();

    }

    @NonNull
    @Override
    public com.hustunique.musica.design.WhiteNoiseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.musicdesign_item, parent, false);
        return new com.hustunique.musica.design.WhiteNoiseAdapter.ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull com.hustunique.musica.design.WhiteNoiseAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //将数据和控件绑定
        //
        holder.Select = false;
        if (position % 7 + 1 == 1) holder.imageView.setImageResource(R.drawable.x1);
        if (position % 7 + 1 == 2) holder.imageView.setImageResource(R.drawable.x2);
        //Do not setBackgroundResource !
        if (position % 7 + 1 == 3) holder.imageView.setImageResource(R.drawable.x3);
        //you cannot make it!
        if (position % 7 + 1 == 4) holder.imageView.setImageResource(R.drawable.x4);
        if (position % 7 + 1 == 5) holder.imageView.setImageResource(R.drawable.x5);
        if (position % 7 + 1 == 6) holder.imageView.setImageResource(R.drawable.x6);
        if (position % 7 + 1 == 7) holder.imageView.setImageResource(R.drawable.x7);
        holder.textView.setText("白噪声"+String.valueOf(position+1));
        Log.d("RECYCLER2", String.valueOf(position));
        holder.itemView.setOnClickListener(v -> {
//            Toast.makeText(context,"QWQ",Toast.LENGTH_LONG);
            if (!holder.Select) holder.textView.setTextColor(Color.parseColor("#FF0000"));
            else holder.textView.setTextColor(Color.parseColor("#FF888888"));
            holder.Select=!holder.Select;
        });
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return list.size();
    }

    //内部类，绑定控件
    class ViewHolder extends RecyclerView.ViewHolder {
        RoundImage imageView;
        TextView textView;
        boolean Select = false;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.RoundIcon);
            textView = itemView.findViewById(R.id.IconName);
        }
    }
}
