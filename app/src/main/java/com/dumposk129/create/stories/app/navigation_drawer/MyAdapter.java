package com.dumposk129.create.stories.app.navigation_drawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dumposk129.create.stories.app.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();
    private ClickListener mClickListener;

    // Constructor
    public MyAdapter(Context context, List<Information> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Show Layout ListItem from Navigation Drawer.
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    // Set Data from position.
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current = data.get(position);
        holder.title.setText(current.title);
    }

    // SetClickListener
    public void setClickListener(ClickListener clickListener){
        this.mClickListener = clickListener;
    }

    // Get Data From Value
    @Override
    public int getItemCount() {
        return data.size();
    }

    // Set Click Listener
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.navListItem);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null){
                mClickListener.itemClicked(v, getPosition());
            }
        }
    }

    public interface ClickListener{
        public void itemClicked(View view, int position);
    }
}