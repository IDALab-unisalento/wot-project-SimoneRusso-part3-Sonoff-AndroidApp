package it.unisalento.sonoff.restService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import it.unisalento.sonoff.R;

@SuppressLint({"HardwareIds", "UseSwitchCompatOrMaterialCode"})
public class RestService {
    String address = "http://192.168.1.100:8082";
    String clientId;

    public RestService(Context context) {
        AndroidNetworking.initialize(context);
        clientId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    public void getStatus(ToggleButton toggleButton){
        AndroidNetworking.get(address+"/getStatus/"+clientId)
                .setPriority(Priority.LOW)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("Rest (getStatus()):", "stato corrente " + response);
                        toggleButton.setChecked(response.equals("ON"));
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Rest (getStatus()):", anError.toString());
                        Log.e("Rest (getStatus()):", anError.getErrorBody());
                    }
                });
    }
    public void getStatus(TextView textView){
        AndroidNetworking.get(address+"/getStatus/"+clientId)
                .setPriority(Priority.LOW)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("Rest (getStatus()):", "stato corrente " + response);
                        textView.setVisibility(View.VISIBLE);

                        if(response.equals("ON")) {
                            textView.setText(R.string.access_ok);
                            textView.setTextColor(Color.parseColor("#417A00"));
                        }
                        else{
                            textView.setText(R.string.access_deny);
                            textView.setTextColor(Color.RED);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Rest (getStatus()):", anError.toString());
                        textView.setText(R.string.access_ok);
                        textView.setTextColor(Color.parseColor("#417A00"));
                        textView.setVisibility(View.VISIBLE);

                    }
                });
    }


    public void changeStatusON(CompoundButton toggleButton, TextView textView) {
        AndroidNetworking.get(address+"/changeStatusON/"+clientId)
                .setPriority(Priority.LOW)
                .build()
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Rest (changeStatus()):", "status changed" + response);
                        textView.setText("");
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Rest (changeStatus()):", anError.toString());
                        toggleButton.setChecked(false);
                    }
                });
    }

    public void changeStatusOFF(CompoundButton toggleButton, TextView textView) {
        AndroidNetworking.get(address+"/changeStatusOFF/"+clientId)
                .setPriority(Priority.LOW)
                .build()
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Rest (changeStatus()):", "status changed " + response);
                        textView.setText("");
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Rest (changeStatus()):", anError.toString());
                        toggleButton.setChecked(true);
                    }
                });
    }
}
