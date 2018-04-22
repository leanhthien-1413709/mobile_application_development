package com.example.adam.currency_converter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {


    private Spinner spinner_1, spinner_2;
    private String selectedItem_1, selectedItem_2;
    private EditText edit_1, edit_2;
    private Button btnConvert, btnReverse, btnClear;

    //All available country rate
    private String listName[] = {"USD", "VND", "EUR", "GBP"};
    private Double rate;

    //For local convert
    private Double rateList_2[][] = {   {1., 22753., 0.81, 0.71},
                                        {0.000044, 1., 0.000036, 0.000031},
                                        {1.23, 28041.72, 1., 0.88},
                                        {1.40, 31864.48, 1.14, 1.}  };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listenerEvent();
    }

    //Function initials listener for button
    public void listenerEvent() {

        edit_1 = findViewById(R.id.edit_1);
        edit_2 = findViewById(R.id.edit_2);
        btnConvert = findViewById(R.id.btnConvert);
        btnReverse = findViewById(R.id.btnReverse);
        btnClear = findViewById(R.id.btnClear);

        edit_2.setEnabled(false);

        btnConvert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                convertEvent();
            }

        });

        btnReverse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                reverseEvent();
            }

        });

        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clearEvent();
            }

        });

    }

    //Function converts input from source to target currency
    public void convertEvent() {

        spinner_1 = findViewById(R.id.spinner1);
        spinner_2 = findViewById(R.id.spinner2);
        edit_1 = findViewById(R.id.edit_1);
        edit_2 = findViewById(R.id.edit_2);

        //Get input's amount and check value
        final String input = edit_1.getText().toString();

        if (TextUtils.isEmpty(input)) {

            Toast.makeText(MainActivity.this, "Input can not be empty!",
                    Toast.LENGTH_LONG).show();
            return;
        }

        //Get Currency type
        //Input country currency

        selectedItem_1 = spinner_1.getSelectedItem().toString();
        selectedItem_2 = spinner_2.getSelectedItem().toString();
        int inputPos = 0, outputPos = 0;

        for (int i = 0; i < 7; i++) {
            if (selectedItem_1.contains(listName[i])) {
                inputPos = i;
                break;
            }
        }

        for (int i = 0; i < 7; i++) {
            if (selectedItem_2.contains(listName[i])) {
                outputPos = i;
                break;
            }
        }

        //Set rate currency
        rate = rateList_2[inputPos][outputPos];

        //Convert
        final String result = Double.toString((Double.parseDouble(input) * rate));
        edit_2.setText(result);

    }

    //Function reverse input and out Currency type to convert
    public void reverseEvent() {

        spinner_1 = findViewById(R.id.spinner1);
        spinner_2 = findViewById(R.id.spinner2);
        selectedItem_1 = spinner_1.getSelectedItem().toString();
        selectedItem_2 = spinner_2.getSelectedItem().toString();

        spinner_1.setSelection(((ArrayAdapter)spinner_1.getAdapter()).getPosition(selectedItem_2));
        spinner_2.setSelection(((ArrayAdapter)spinner_2.getAdapter()).getPosition(selectedItem_1));

        clearEvent();

    }

    //Function clear text in Input and Output
    public void clearEvent() {

        edit_1 = findViewById(R.id.edit_1);
        edit_2 = findViewById(R.id.edit_2);

        edit_1.setText("");
        edit_2.setText("");
    }




}
