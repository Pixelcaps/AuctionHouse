package com.example.auctionhouse.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auctionhouse.R;
import com.example.auctionhouse.model.Item;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ItemAdapter extends FirestoreRecyclerAdapter<Item, ItemAdapter.ItemHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ItemAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemHolder holder, int position, @NonNull Item model) {
        holder.textViewName.setText(model.getName());
//        holder.textViewExpirationDate.setText(model.getExpirationDate());
//        holder.textViewStartPrice.setText(model.getStartPrice());
//        holder.textViewCurrentBid.setText(model.getCurrentBid());
        holder.textViewSeller.setText(model.getSeller());
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,
                parent,false);
        return new ItemHolder(v);
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewExpirationDate;
        TextView textViewStartPrice;
        TextView textViewCurrentBid;
        TextView textViewSeller;

        public ItemHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.item_name);
//            textViewExpirationDate = itemView.findViewById(R.id.item_expiration);
//            textViewStartPrice = itemView.findViewById(R.id.item_start_price);
//            textViewCurrentBid = itemView.findViewById(R.id.item_current_bid);
            textViewSeller = itemView.findViewById(R.id.item_seller);
        }

    }


}
