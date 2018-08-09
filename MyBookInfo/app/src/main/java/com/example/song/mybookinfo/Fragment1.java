package com.example.song.mybookinfo;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;


public class Fragment1 extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.activity_fragment1,container,false);
        Bundle bundle = getArguments();
        MainActivity context = (MainActivity)bundle.getSerializable("context");
        String sql = "select title,author from bookTB";
        Cursor c = context.db.rawQuery(sql,null);
        int count = c.getCount();
        List<Row> data = new ArrayList<Row>();
        Row book = null;


        if(count>0){
            while(c.moveToNext()){
                book = new Row(c.getString(0),c.getString(1));
                data.add(book);
            }
        }else{
            data = null;
        }

        BookInfoAdapter adapter = new BookInfoAdapter(rootView.getContext(),data);
        ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return rootView;
    }

}
