package me.coffee.example.cleanableedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText1 = findViewById(R.id.et1);
        EditText editText2 = findViewById(R.id.et2);

        editText1.setText("abc");
        editText2.setText("123");
    }
}
