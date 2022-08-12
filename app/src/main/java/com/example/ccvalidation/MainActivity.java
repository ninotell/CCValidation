package com.example.ccvalidation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private boolean valid = false;
    EditText CCNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button checkButton = findViewById(R.id.checkButton);
        CCNumber = (EditText) findViewById(R.id.CCNumber);
        CCNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkCCNumber(s.toString());
                if (s.toString().length() == 16) {
                    updateTextView();
                }
            }
        });
    }


    public void checkCCNumber(String number) {
        ImageView provider = (ImageView) findViewById(R.id.provider);
        TextView card_number = (TextView) findViewById(R.id.number);
        int sum = 0;
        int position = 0;
        int first = Integer.parseInt(number.substring(0, 1));
        if (first == 5) {
            provider.setImageResource(R.drawable.master);
        } else if (first == 4) {
            provider.setImageResource(R.drawable.visa);
        }

        for (int i = number.length() - 1; i >= 0; i--) {
            position += 1;
            int digit = Integer.parseInt(Character.toString(number.charAt(i)));
            if (position % 2 == 0) {
                digit *= 2;
            }
            if (digit > 9) {
                digit -= 9;
            }
            sum += digit;
        }

        if (sum % 10 == 0) {
            valid = true;
            if (number.length() == 16) {
                card_number.setText(number.substring(0, 4) + " " + number.substring(4, 8) + " " + number.substring(8, 12) + " " + number.substring(12, 16));
            }
        } else {
            valid = false;
        }
    }

    ;

    private void updateTextView() {
        TextView valid = findViewById(R.id.textViewCheck);
        if (this.valid) {
            valid.setTextColor(Color.GREEN);
            valid.setText("VALIDA");
        } else {
            valid.setTextColor(Color.RED);
            valid.setText("INVALIDA");
        }

    }

//    public void check(View view) {
//        this.checkCCNumber();
//        Log.d("PRUEBA", String.valueOf(valid));
//        updateTextView();
//    }
}