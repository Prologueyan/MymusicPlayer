package com.example.acer.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by acer on 2016/2/28.
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private int  mPosition ;
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent, int layoutid, int position) {
        this.mPosition = position;
        this.mConvertView = LayoutInflater.from(context).inflate(layoutid, parent, false);
        this.mViews = new SparseArray<View>();
        mConvertView.setTag(this);
    }


    public static ViewHolder get(Context context, View convertview,
                                 ViewGroup parent, int layoutid, int position) {
        if (convertview == null) {
            return new ViewHolder(context, parent, layoutid, position);
        } else {
            ViewHolder holder = (ViewHolder) convertview.getTag();
            holder.mPosition = position;
            return holder;
        }

    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getmConvertView() {
        return mConvertView;
    }
}
