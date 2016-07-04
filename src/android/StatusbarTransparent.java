package net.ekuwang.cordova.plugin.statusbar;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.WindowManager.LayoutParams;
import android.graphics.Color;
import android.view.Window;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

public class StatusbarTransparent extends CordovaPlugin {

    @Override
    protected void pluginInitialize() {
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            cordova.getActivity().runOnUiThread( new Runnable() {
                public void run() {
                    Window window = cordova.getActivity().getWindow();
                    window.addFlags(LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(Color.argb(0, 0, 0, 0));
                }
            });
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callback) throws JSONException {
        // grab the correct methods
        if ("getStatusbarHeight".equalsIgnoreCase(action)) {
            getStatusBarHeight(callback);
            return true;
        } else if ("isSupported".equalsIgnoreCase(action)) {
            isSupported(callback);
            return true;
        } else {
            callback.error("Unknown Action: " + action);
            return false;
        }
    }

    private void getStatusBarHeight(CallbackContext callback) {
        Rect rectangle = new Rect();
        Window window = cordova.getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;

        callback.success(statusBarHeight);
    }

    private boolean isSupported(CallbackContext callback) {
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            callback.success(true);
            return true;
        } else {
            callback.success(false);
            return false;
        }
    }
}
