package com.example.me.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int quantity = 0;
    private Toast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);
    }

    public void submitOrder(View view) {
        CheckBox chocolateCheck = findViewById(R.id.Chocolate);
        CheckBox creamChecked = findViewById(R.id.Cream);
        String chocolateText = ifCases(chocolateCheck.isChecked());
        String creamText = ifCases(creamChecked.isChecked());
        int price = calculatePrice(creamChecked.isChecked(), chocolateCheck.isChecked());
        String clientName = clientName();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ah.abdulslam@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Send Order " + clientName);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price, creamText, chocolateText, clientName));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean HasCream, boolean hasChocolate) {
        int basePrice = 5;
        if (HasCream) {
            basePrice = basePrice + 1;
        }
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    private String createOrderSummary(int price, String creamText, String chocolateText, String clientName) {
        String priceMessage = getString(R.string.Order_Summary_Name)+" " + clientName;
        priceMessage += "\n" + getString(R.string.Order_Summary_cream)+" " + creamText;
        priceMessage += "\n" + getString(R.string.Order_Summary_chocolate)+" " + chocolateText;
        priceMessage += "\n" + getString(R.string.Order_Summary_quantity);
        priceMessage += quantity;
        priceMessage += "\n" + getString(R.string.Order_Summary_total);
        priceMessage += price;
        priceMessage += "\n" + getString(R.string.Order_Summary_thank);
        return priceMessage;
    }

    public void increment(View view) {
        if (quantity == 10) {

            myToast.setText(getString(R.string.Toast2));
            myToast.show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {
        quantity = quantity - 1;
        if (quantity < 1) {
            quantity = 1;
            myToast.setText(getString(R.string.Toast1));
            myToast.show();
        }
        display(quantity);
    }

    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private String clientName() {
        EditText clientName = findViewById(R.id.clientName);
        String clientName1 = clientName.getText().toString();
        return clientName1;
    }

    public String ifCases(boolean hasTopping) {
        String toppingText = getString(R.string.NoThanks);
        if (hasTopping) {
            toppingText = getString(R.string.YesPlease);
        }
        return toppingText;
    }
}