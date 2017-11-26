package me.mattlineback.ouichef;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by mattlineback on 11/6/2017.
 */

public class EightySixBoardAdapter extends RecyclerView.Adapter<EightySixBoardAdapter.EightySixBoardHolder>{

    private List<ListItem> item;
    private Context context;

    EightySixBoardAdapter(Context context, List<ListItem> item){
        this.item = item;
        this.context = context;
    }
    @Override
    public EightySixBoardHolder onCreateViewHolder(ViewGroup parent, int viewType){
        EightySixBoardHolder viewHolder;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        viewHolder = new EightySixBoardHolder(layoutView, item);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(EightySixBoardHolder holder, int position){
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

    class EightySixBoardHolder extends ViewHolder {
        private static final String TAG = "EightySixBoardHolder.class";
        TextView listItem;
        ImageView deleteIcon;

        EightySixBoardHolder(final View view, final List<ListItem> listObject){
            super(view);
            listItem = view.findViewById(R.id.list_item_view);
            deleteIcon = view.findViewById(R.id.list_item_delete);

            deleteIcon.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Toast.makeText(view.getContext(), "Delete Icon has been clicked", Toast.LENGTH_LONG).show();
                    String listItemString = listObject.get(getAdapterPosition()).getListItem();
                    Log.d(TAG, "Item = " + listItemString);
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("eightSixItems");
                    Query query = dbRef.orderByChild("listItem").equalTo(listItemString);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                                Log.d(TAG, "item to remove on data change" + orderSnapshot.getRef());
                                orderSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, "onCancelled ", databaseError.toException());

                        }
                    });
                }
            });
        }
    }
}
