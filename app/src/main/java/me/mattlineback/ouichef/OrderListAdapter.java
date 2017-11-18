package me.mattlineback.ouichef;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by matt on 11/6/17.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListHolder> {

    private List<ListItem> item;
    protected Context context;

    public OrderListAdapter(Context context, List<ListItem> item){
        this.item = item;
        this.context = context;
    }
    @Override
    public OrderListHolder onCreateViewHolder(ViewGroup parent, int viewType){
        OrderListHolder viewHolder;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        viewHolder = new OrderListHolder(layoutView, item);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(OrderListHolder holder, int position){
        editView(holder.listItem);
        holder.listItem.setText(item.get(position).getListItem());
    }

    @Override
    public int getItemCount() {
        return this.item.size();
    }

    private void editView(TextView tv){
        tv.setTextColor(Color.WHITE);
        tv.setAllCaps(true);
        tv.setTextSize(20);
    }
}
