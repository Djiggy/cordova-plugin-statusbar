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

import android.util.DisplayMetrics;


public class StatusbarTransparent extends CordovaPlugin {

    @Override
    protected void pluginInitialize() {
        if(VERSION.SDK_INT > VERSION_CODES.KITKAT) {
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
        if(action.equalsIgnoreCase("enable")) {
			if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
				cordova.getActivity().runOnUiThread( new Runnable() {
					public void run() {
						cordova.getActivity().getWindow().addFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS);
					}
				});
				callback.success();
			} else {
				callback.error("not supported");
			}
			return true;
		} else if(action.equalsIgnoreCase("disable")) {
			if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
				cordova.getActivity().runOnUiThread( new Runnable() {
					public void run() {
						cordova.getActivity().getWindow().clearFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS);
					}
				});
				callback.success();
			} else {
				callback.error("not supported");
			}
			return true;
		} else if ("getStatusbarHeight".equalsIgnoreCase(action)) {
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

        DisplayMetrics metrics = cordova.getActivity().getResources().getDisplayMetrics();

        callback.success(Float.toString((float)statusBarHeight / metrics.density));
    }

    private boolean isSupported(CallbackContext callback) {
        if(VERSION.SDK_INT > VERSION_CODES.KITKAT) {
            callback.success("true");
            return true;
        } else {
            callback.success("false");
            return false;
        }
    }
}
