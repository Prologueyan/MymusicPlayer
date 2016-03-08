package com.example.acer.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.acer.musicplayer.R;

import java.util.List;

/**
 * Created by acer on 2016/2/28.
 */
public class MyAdpter extends BaseAdapter implements ListAdapter {

    private LayoutInflater mInflater;
    private List<Bean> mDatas;
    private Context mContext;

    public MyAdpter(Context context, List<Bean> datas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();

    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, R.layout.item, position);
        Bean bean = mDatas.get(position);
        TextView songname = holder.getView(R.id.songname);
        songname.setText(bean.getSongName());
        TextView artist = holder.getView(R.id.artist);
        artist.setText(bean.getArtist());
        return holder.getmConvertView();
    }
}
