package com.hustunique.musica.AapterMain;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.hustunique.musica.Exact.ExactActivity;
import com.hustunique.musica.R;
import java.util.List;


/*
① 创建一个继承RecyclerView.Adapter<VH>的Adapter类
② 创建一个继承RecyclerView.ViewHolder的静态内部类
③ 在Adapter中实现3个方法：
   onCreateViewHolder()
   onBindViewHolder()
   getItemCount()
*/
public class Adapter01 extends RecyclerView.Adapter<Adapter01.MyViewHolder> implements IAdapter01.IView{

    private Context context;



    private List<Integer> list;
    //类型待定



    private IAdapter01.IPresenter presenter;
    private View inflater;
    //构造方法，传入数据
    public Adapter01(Context context, List<Integer> list){
        this.context = context;
        this.list = list;
        presenter = new Presenter01(this);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.adapter01,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //将数据和控件绑定
        //
        presenter.getUI(holder,position);
        Log.d("RECYCLER",String.valueOf(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExactActivity.class);
                context.startActivity(intent);
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
        TextView TextTitle;
        TextView TextTime;
        TextView TextDate;
        ImageView PhotoTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            TextTitle = (TextView) itemView.findViewById(R.id.TextTitle);
            TextDate = (TextView) itemView.findViewById(R.id.TextDate);
            PhotoTitle = (ImageView) itemView.findViewById(R.id.PhotoTitle);
            TextTime = (TextView) itemView.findViewById(R.id.TextTime);
        }
    }
}
