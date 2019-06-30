package com.example.ssomanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fearefull.ssomanager.SSOCallback;
import com.fearefull.ssomanager.SSOManager;
import com.fearefull.ssomanager.SSOUser;
import com.fearefull.ssomanager.SSOUserStatus;

public class MainActivity extends AppCompatActivity {

    private Button checkStatus, logoutMe, loginMe;
    private TextView userStatus;
    private SSOManager ssoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkStatus = findViewById(R.id.check_status);
        loginMe = findViewById(R.id.login_me);
        logoutMe = findViewById(R.id.logout_me);
        userStatus = findViewById(R.id.user_status);
        ssoManager = SSOManager.getInstance();
        
        checkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ssoManager.checkLogin() == SSOUserStatus.USER_OK)
                    ssoManager.showToast(ssoManager.getUser().getToken().getAccessToken());
                else
                    ssoManager.showToast("not logged in");
            }
        });

        loginMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ssoManager.loginByPhoneNumber(
                        "http://www.mocky.io/v2/597c41390f0000d002f4dbd1",
                        "09215206388",
                        "0000",
                        new SSOCallback() {
                            @Override
                            public void onFailure(Exception e, int statusCode) {
                                if (e.getMessage() != null)
                                    Log.d("TAG", e.getMessage());
                                ssoManager.showToast(String.valueOf(statusCode));
                            }

                            @Override
                            public void onResponse(String response, int statusCode) {
                                ssoManager.showToast(response);
                                checkUserStatus();
                            }
                        });
            }
        });

        logoutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ssoManager.logout();
                ssoManager.showToast("User logged out");
                checkUserStatus();
            }
        });
        
        checkUserStatus();
    }

    @Override
    protected void onDestroy() {
        ssoManager.destroy();
        super.onDestroy();
    }
    
    private void checkUserStatus() {
        if (ssoManager.checkLogin() == SSOUserStatus.USER_OK) {
            SSOUser ssoUser = ssoManager.getUser();
            if (ssoUser.hasUsername())
                userStatus.setText(new StringBuilder("User " + ssoUser.getUsername() + " logged in"));
            else if (ssoUser.hasEmail())
                userStatus.setText(new StringBuilder("User " + ssoUser.getEmail() + " logged in"));
            else if (ssoUser.hasPhoneNumber())
                userStatus.setText(new StringBuilder("User " + ssoUser.getPhoneNumber() + " logged in"));
        }
        else
            userStatus.setText(R.string.no_user);

    }
}
