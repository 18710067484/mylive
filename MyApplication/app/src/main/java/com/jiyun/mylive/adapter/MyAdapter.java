package com.jiyun.mylive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiyun.mylive.R;

import java.util.List;

/**
 * Created by lenovo on 2017/9/29.
 */

public class MyAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public MyAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Hodelr hodelr;
        if (view == null) {
            hodelr = new Hodelr();
            view = LayoutInflater.from(context).inflate(R.layout.item_listview, null);
            hodelr.tv = view.findViewById(R.id.item_content);
            view.setTag(hodelr);
        } else {
            hodelr = (Hodelr) view.getTag();
        }
        hodelr.tv.setText(list.get(i));
        return view;
    }

    static class Hodelr {
        TextView tv;
    }
}
