package com.wisteria.projectwalk;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * To be removed, this basic Activity is just to show the backend is working...
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DataHandler handle = new DataHandler(MainActivity.this);

        final EditText countryName = (EditText) findViewById(R.id.countryName);
        final EditText yearValue = (EditText) findViewById(R.id.yearValue);
        final TextView textView = (TextView) findViewById(R.id.textView);

        Button data = (Button) findViewById(R.id.loadData);
        data.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String country = countryName.getText().toString();
                int year = Integer.parseInt(yearValue.getText().toString());
                textView.setText(Html.fromHtml(handle.printData(country, year)));

            }

        });

    }
}
