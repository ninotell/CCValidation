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

import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {
    private boolean valid = false;
    private static final LinkedHashMap<String, String> bins = new LinkedHashMap<String, String>() {{
        put("589562", "NARANJA");
        put("589657", "CABAL");
        put("6042", "CABAL");
        put("6043", "CABAL");
        put("34", "AMERICAN EXPRESS");
        put("35", "AMERICAN EXPRESS");
        put("36", "AMERICAN EXPRESS");
        put("37", "AMERICAN EXPRESS");
        put("4", "VISA");
        put("5", "MASTERCARD");
    }};
    EditText CCNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                if (s == null && s.toString() == "") {
                    return;
                }
                luhnAlgorithm(s.toString());
                if (s.toString().length() >= 15) {
                    updateTextView();
                }
                String number = s.toString();
                if (valid && number.length() >= 15){
                    TextView card_number = (TextView) findViewById(R.id.number);
                    switch (number.length()){
                        case 15:
                            if (getCardNameFromCardNumber(number) != "AMERICAN EXPRESS"){
                                break;
                            }
                            card_number.setText(number.substring(0, 4) + " " + number.substring(4, 8) + " " + number.substring(8, 12) + " " + number.substring(12, 15));
                            break;
                        case 16:
                            card_number.setText(number.substring(0, 4) + " " + number.substring(4, 8) + " " + number.substring(8, 12) + " " + number.substring(12, 16));
                            break;
                    }
                }

                ImageView provider = (ImageView) findViewById(R.id.provider);
                switch (getCardNameFromCardNumber(s.toString())){
                    case "VISA":
                        provider.setImageResource(R.drawable.visa);
                        break;
                    case "MASTERCARD":
                        provider.setImageResource(R.drawable.master);
                        break;
                    case "NARANJA":
                        provider.setImageResource(R.drawable.naranja);
                        break;
                    case "AMERICAN EXPRESS":
                        provider.setImageResource(R.drawable.amex);
                        break;
                    case "CABAL":
                        provider.setImageResource(R.drawable.cabal);
                        break;
                    default:
                        provider.setImageResource(0);
                        break;
                };
            }
        });
    }


    public void luhnAlgorithm(String number) {
        int sum = 0;
        int position = 0;
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
        } else {
            valid = false;
        }
    }

    private String getCardNameFromCardNumber(String nroTarjeta) {
        if (nroTarjeta == null) {
            return "";
        }
        for (String bin : bins.keySet()) {
            if (nroTarjeta.startsWith(bin)) {
                return bins.get(bin);
            }
        }
        return "";
    }

    private void updateTextView() {
        TextView tarjetaValida = findViewById(R.id.textViewCheck);
        if (valid) {
            tarjetaValida.setTextColor(Color.GREEN);
            tarjetaValida.setText("VALIDA");
        } else {
            tarjetaValida.setTextColor(Color.RED);
            tarjetaValida.setText("INVALIDA");
        }

    }
}