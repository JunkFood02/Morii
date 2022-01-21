package com.hustunique.musica.home;

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

import com.hustunique.musica.R;
import com.hustunique.musica.content.ExactActivity;

import java.util.Calendar;
import java.util.List;


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
    public MusicDiaryAdapter(Context context, List<MusicDiaryItem> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.cardview_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //将数据和控件绑定
        //
        holder.PhotoTitle.setImageResource(R.drawable.orange);
        MusicDiaryItem musicDiaryItem = list.get(position);
        Calendar calendar = musicDiaryItem.getCalendar();
        String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        String day = String.valueOf(calendar.get(Calendar.DATE));
        String hour = String.valueOf(calendar.get(Calendar.HOUR));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));
        if(calendar.get(Calendar.HOUR)<10) hour = "0" + hour;
        if(calendar.get(Calendar.MINUTE)<10) minute = "0" + minute;
        if(calendar.get(Calendar.SECOND)<10) second = "0" + second;
        String Weekday = "日一二三四五六";
        int week = calendar.get(Calendar.DAY_OF_WEEK);
//        holder.TextDate.setText(String.valueOf(week));
        holder.TextDate.setText(month+"月"+day+"日"+" 周"+Weekday.charAt(week-1));
        holder.TextTitle.setText(" "+ musicDiaryItem.getTitle());
        holder.TextTime.setText("Time : "+hour+":"+minute+":"+second+" ");

//        holder.PhotoTitle.setImageBitmap(musicDiaryItem.getBackgroundColor());



        Log.d("RECYCLER",String.valueOf(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExactActivity.class);
                intent.putExtra("WID", musicDiaryItem.getItemID());
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
