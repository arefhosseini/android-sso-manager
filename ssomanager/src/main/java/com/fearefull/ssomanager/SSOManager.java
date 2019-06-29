package com.fearefull.ssomanager;

import android.content.Context;
import android.widget.Toast;

public class SSOManager {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
