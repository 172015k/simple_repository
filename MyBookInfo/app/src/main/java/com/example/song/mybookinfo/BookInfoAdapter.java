package com.example.song.mybookinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BookInfoAdapter extends BaseAdapter{

    Context context = null;
    List<Row> data = null;
    LayoutInflater layoutInflater = null;

    public BookInfoAdapter(Context context, List<Row> data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder{
        TextView tvTitle = null;
        TextView tvAuthor = null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemLayout = null;
        ViewHolder viewHolder = null;
        if(itemLayout==null) {
            itemLayout = layoutInflater.inflate(R.layout.activity_row, null);
            TextView tvTitle = itemLayout.findViewById(R.id.tvTitle);
            TextView tvAuthor = itemLayout.findViewById(R.id.tvAuthor);
            itemLayout.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder)itemLayout.getTag();
        }

        viewHolder.tvTitle.setText(data.get(i).getBookTitle());
        viewHolder.tvAuthor.setText(data.get(i).getBookAuthor());


        return itemLayout;
    }
}
