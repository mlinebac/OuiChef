package me.mattlineback.ouichef;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by matt on 11/6/17.
 */

public class ListItemRecyclerViewAdapter extends RecyclerView.Adapter<OrderListRecyclerViewHolder> {

    private List<ListItem> item;
    protected Context context;

    public ListItemRecyclerViewAdapter(Context context, List<ListItem> item){
        this.item = item;
        this.context = context;
    }
    @Override
    public OrderListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        OrderListRecyclerViewHolder viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        viewHolder = new OrderListRecyclerViewHolder(layoutView, item);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(OrderListRecyclerViewHolder holder, int position){
        holder.listItem.setText(item.get(position).getListItem());
    }

    @Override
    public int getItemCount() {
        return this.item.size();
    }
}
