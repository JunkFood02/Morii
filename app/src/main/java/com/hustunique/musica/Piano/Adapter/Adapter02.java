package com.hustunique.musica.Piano.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.hustunique.musica.R;

import java.util.List;

public class Adapter02 extends RecyclerView.Adapter<Adapter02.MyViewHolder>implements IAdapter02.IView{
    private Context context;

    private List<Integer> list;
    //类型待定



    private IAdapter02.IPresenter presenter;
    private View inflater;
    //构造方法，传入数据
    public Adapter02(Context context, List<Integer> list){
        this.context = context;
        this.list = list;
        presenter = new Presenter02(this);
    }

    @Override
    public Adapter02.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.adapter02,parent,false);
        Adapter02.MyViewHolder myViewHolder = new Adapter02.MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter02.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //将数据和控件绑定
        //
        presenter.getUI(holder,position);
        Log.d("RECYCLER",String.valueOf(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, ExactActivity.class);
//                context.startActivity(intent);
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
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.EmotionPhoto);
        }
    }
}
