package me.mattlineback.ouichef;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

/**
 * Created by matt on 11/6/17.
 */

public class OrderListHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "OrderListHolder.class";
    public TextView listItem;
    public ImageView deleteIcon;

    private List<ListItem> listObject;
    public OrderListHolder(final View view, final List<ListItem> listObject){
        super(view);
        listItem = view.findViewById(R.id.list_item_view);
        deleteIcon = view.findViewById(R.id.list_item_delete);
        this.listObject = listObject;


        deleteIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(view.getContext(), "Delete Icon has been clicked", Toast.LENGTH_LONG).show();
                String listItemString = listObject.get(getAdapterPosition()).getListItem();
                Log.d(TAG, "Item = " + listItemString);
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("orderItems");
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
