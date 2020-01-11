package com.example.auctionhouse.adapter;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auctionhouse.R;
import com.example.auctionhouse.model.Item;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import org.w3c.dom.Text;

public class ItemAdapter extends FirestoreRecyclerAdapter<Item, ItemAdapter.ItemHolder> {

    private OnBidButtonClickListener listener;

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

        String date = convertDate(model.getExpirationDate(),"dd/MM/yyyy hh:mm:ss");

        if (!checkIfTimeExpired(model.getExpirationDate())) {
            holder.textViewExpirationDate.setText(date);
        } else {
            holder.textViewExpirationDate.setText(R.string.expired);
        }


        holder.textViewName.setText(model.getName());
        holder.textViewStartPrice.setText(String.valueOf(model.getStartPrice()));
        holder.textViewCurrentBid.setText(String.valueOf(model.getCurrentBid()));
        holder.textViewSeller.setText(String.valueOf(model.getSeller()));
        holder.textViewHighestBidder.setText(model.getHighestBidder());
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,
                parent,false);
        return new ItemHolder(v);
    }

    public static String convertDate(String dateInMilliseconds,String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewExpirationDate;
        TextView textViewStartPrice;
        TextView textViewCurrentBid;
        TextView textViewSeller;
        TextView textViewHighestBidder;
        ImageButton btnBidOnItem;

        public ItemHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.item_name);
            textViewExpirationDate = itemView.findViewById(R.id.item_expiration);
            textViewStartPrice = itemView.findViewById(R.id.item_start_price);
            textViewCurrentBid = itemView.findViewById(R.id.item_current_bid);
            textViewSeller = itemView.findViewById(R.id.item_seller);
            textViewHighestBidder = itemView.findViewById(R.id.item_bidder);
            btnBidOnItem = itemView.findViewById(R.id.button_bid);

            btnBidOnItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onBidButtonClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnBidButtonClickListener {
        void onBidButtonClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnBidButtonClickListener(OnBidButtonClickListener listener) {
        this.listener = listener;
    }

    public boolean checkIfTimeExpired(String expirationDate) {
        long temp = Long.parseLong(expirationDate);
        long currentTimeInMillis = System.currentTimeMillis();
        if (currentTimeInMillis >= temp) return true;
        return false;
    }


}
