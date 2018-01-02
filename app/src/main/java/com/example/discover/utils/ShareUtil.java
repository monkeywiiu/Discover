package com.example.discover.utils;

import android.content.Context;
import android.content.Intent;

import com.example.discover.R;

/**
 * Created by monkeyWiiu on 2017/12/29.
 */

public class ShareUtil {

    public static void share(Context context, String shareText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.action_share)));
    }
}
