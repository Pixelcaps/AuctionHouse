package com.example.auctionhouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.auctionhouse.model.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextExpirationDate;
    private EditText editTextDate;
    private EditText editTextTime;
    private FirebaseAuth mAuth;
    private int mYear, mMonth, mDay, mHour, mMinute;

    Button btnDatePicker, btnTimePicker;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        editTextName = findViewById(R.id.edit_text_name);
        editTextPrice = findViewById(R.id.edit_text_price);
        editTextDate = findViewById(R.id.edit_text_date);
        editTextTime = findViewById(R.id.edit_text_time);

        btnDatePicker = findViewById(R.id.button_date);
        btnTimePicker = findViewById(R.id.button_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Item");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_new_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                try {
                    addItem();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addItem() throws ParseException {
        String name = editTextName.getText().toString();
        int startPrice = Integer.parseInt(editTextPrice.getText().toString());
        String expirationDate = editTextDate.getText() + " " + editTextTime.getText();
        Date date = new Date(System.currentTimeMillis());
        try {
            date = sdf.parse(expirationDate);
        } catch(ParseException e){
            e.printStackTrace();
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String seller = currentUser.getEmail();

        CollectionReference auctionItemsRef = FirebaseFirestore.getInstance().collection("items");
        auctionItemsRef.add(new Item(name, Long.toString(date.getTime()), startPrice, 0, seller, ""));
        Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            editTextTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}
