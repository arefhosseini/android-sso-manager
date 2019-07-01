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

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button checkStatus, logout, loginByPhoneNumber, loginByUsername, requestCode,
            signUpByUsername, signUpByEmail;
    private TextView userStatus;
    private SSOManager ssoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkStatus = findViewById(R.id.check_status);
        loginByPhoneNumber = findViewById(R.id.login_by_phone_number);
        loginByUsername = findViewById(R.id.login_by_username);
        logout = findViewById(R.id.logout);
        requestCode = findViewById(R.id.request_code);
        signUpByUsername = findViewById(R.id.signup_by_username);
        signUpByEmail = findViewById(R.id.signup_by_email);
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

        requestCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ssoManager.requestCode(AppConstants.REQUEST_CODE_URL,
                        "09215206388",
                        new SSOCallback() {
                            @Override
                            public void onFailure(Exception error, String response, int statusCode) {
                                showErrorMessage(error, statusCode);
                            }
                            @Override
                            public void onResponse(JSONObject response, int statusCode) {
                                ssoManager.showToast(response.toString());
                            }
                        });
            }
        });

        signUpByUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ssoManager.signupByUsername(AppConstants.SIGNUP_BY_USERNAME_URL,
                        "arefhosseini", "arefaref", "aref", "hosseini",
                        new SSOCallback() {
                            @Override
                            public void onFailure(Exception error, String response, int statusCode) {
                                showErrorMessage(error, statusCode);
                            }
                            @Override
                            public void onResponse(JSONObject response, int statusCode) {
                                ssoManager.showToast(response.toString());
                            }
                        });
            }
        });

        signUpByEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ssoManager.signupByEmail(AppConstants.SIGNUP_BY_EMAIL_URL,
                        "arefhosseiniwad@yahoo.com", "arefaref", "aref", "hosseini",
                        new SSOCallback() {
                            @Override
                            public void onFailure(Exception error, String response, int statusCode) {
                                showErrorMessage(error, statusCode);
                            }
                            @Override
                            public void onResponse(JSONObject response, int statusCode) {
                                ssoManager.showToast(response.toString());
                            }
                        });
            }
        });

        loginByPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ssoManager.loginByPhoneNumber(
                        AppConstants.LOGIN_BY_PHONE_NUMBER_URL,
                        "09215206388",
                        "3154",
                        new SSOCallback() {
                            @Override
                            public void onFailure(Exception error, String response, int statusCode) {
                                showErrorMessage(error, statusCode);
                            }
                            @Override
                            public void onResponse(JSONObject response, int statusCode) {
                                ssoManager.showToast(response.toString());
                                checkUserStatus();
                            }
                        });
            }
        });

        loginByUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ssoManager.loginByUsername(
                        AppConstants.LOGIN_BY_USERNAME_URL,
                        "fearefull",
                        "aref1441375",
                        new SSOCallback() {
                            @Override
                            public void onFailure(Exception error, String response, int statusCode) {
                                showErrorMessage(error, statusCode);
                            }
                            @Override
                            public void onResponse(JSONObject response, int statusCode) {
                                ssoManager.showToast(response.toString());
                                checkUserStatus();
                            }
                        });
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
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

    private void showErrorMessage(Exception error, int statusCode) {
        if (error.getMessage() != null)
            Log.d("TAG", error.getMessage());
        ssoManager.showToast(String.valueOf(statusCode));
    }
}
