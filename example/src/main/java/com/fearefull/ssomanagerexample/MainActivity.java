package com.fearefull.ssomanagerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fearefull.ssomanager.SSOManager;

public class MainActivity extends AppCompatActivity {

    Button clickMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickMe = findViewById(R.id.click_me);

        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SSOManager.showToast(getApplicationContext(), "hey");
            }
        });
    }

}
