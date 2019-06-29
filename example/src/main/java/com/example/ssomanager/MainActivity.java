package com.example.ssomanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fearefull.ssomanager.SSOManager;

public class MainActivity extends AppCompatActivity {

    Button checkStatus;
    Button loginMe;
    Button logoutMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkStatus = findViewById(R.id.check_status);
        loginMe = findViewById(R.id.login_me);
        logoutMe = findViewById(R.id.logout_me);

        checkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SSOManager.getInstance().checkLogin())
                    SSOManager.getInstance().showToast(SSOManager.getInstance().getToken());
                else
                    SSOManager.getInstance().showToast("not logged in");
            }
        });

        loginMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SSOManager.getInstance().login("09215206388");
            }
        });

        logoutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SSOManager.getInstance().logout();
            }
        });
    }

}
