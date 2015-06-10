package com.dumposk129.create.stories.app.create_stories;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by DumpOSK129.
 */
public class GridViewAdapter extends ArrayAdapter{
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecyclerView.ViewHolder holder = null;
        if (row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           // holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        }else {
            holder = (RecyclerView.ViewHolder) row.getTag();
        }

       /* ImageItem item = data.get(position);
        holder.image.setImageBitmap(item.getImage());*/

        return row;
    }

    static class ViewHolder{
        ImageView image;
    }
}
