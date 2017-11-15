package me.mattlineback.ouichef;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by mattlineback on 11/6/2017.
 */

public class PrepListAdapter extends  RecyclerView.Adapter<PrepListHolder>{

    private List<ListItem> item;
    protected Context context;

    public PrepListAdapter(Context context, List<ListItem> item){
        this.item = item;
        this.context = context;
    }
    @Override
    public PrepListHolder onCreateViewHolder(ViewGroup parent, int viewType){
        PrepListHolder viewHolder;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        viewHolder = new PrepListHolder(layoutView, item);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(PrepListHolder holder, int position){
        holder.listItem.setText(item.get(position).getListItem());
    }

    @Override
    public int getItemCount() {
        return this.item.size();
    }
}

