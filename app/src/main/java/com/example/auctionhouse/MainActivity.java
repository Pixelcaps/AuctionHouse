package com.example.auctionhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.auctionhouse.R;

import com.example.auctionhouse.adapter.ItemAdapter;
import com.example.auctionhouse.model.Item;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference auctionItemsRef = db.collection("items");

    private ItemAdapter adapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddItem = findViewById(R.id.button_add_item);
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddItemActivity.class));
            }
        });
        
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        Query query = auctionItemsRef.orderBy("name", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Item> options = new FirestoreRecyclerOptions.Builder<Item>()
                .setQuery(query, Item.class)
                .build();

        adapter = new ItemAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnBidButtonClickListener(new ItemAdapter.OnBidButtonClickListener() {
            @Override
            public void onBidButtonClick(DocumentSnapshot documentSnapshot, int position) {
                Item item = documentSnapshot.toObject(Item.class);
                String id = documentSnapshot.getId();
                DocumentReference bidItem = auctionItemsRef.document(id);
                if (!(System.currentTimeMillis() >= Long.parseLong(item.getExpirationDate()))) {
                    int bidValue = item.getCurrentBid();

                    if (bidValue == 0) {
                        bidValue = item.getStartPrice();
                    } else {
                        bidValue += 10;
                    }
                    String bidder = currentUser.getEmail();
                    bidItem.update("currentBid", bidValue);
                    bidItem.update("highestBidder", bidder);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
