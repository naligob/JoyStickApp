package com.example.remotejoystick.viewmodel;
// 10.100.102.6
import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.remotejoystick.BR;
import com.example.remotejoystick.R;
import com.example.remotejoystick.model.AppModel;
import com.example.remotejoystick.model.Utils;
import com.example.remotejoystick.views.JoyStick;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppViewModel extends BaseObservable implements JoyStick.JoystickListener, SeekBar.OnSeekBarChangeListener {
    private AppModel mAppModel;

    @Bindable
    private String toastMessage = null;
    private String successMessage = "Connected successfully";
    private String errorMessage = "IP or Port is not valid please try again";
    private String loginMessage = "Connecting...";

    public AppViewModel() {
        mAppModel = new AppModel();
    }

    private void setToastMessage(String newToast) {
        this.toastMessage = newToast;
//        Log.d("TAG", "setToastMessage: " + newToast);
        notifyPropertyChanged(BR.toastMessage);
    }

    public String getToastMessage(){
        return this.toastMessage;
    }

    public void afterIPTextChanged(CharSequence s) {
        mAppModel.setIP(s.toString());
    }

    public void afterPortTextChanged(CharSequence s) {mAppModel.setPort((Integer.parseInt(s.toString())));}

    public void onLoginClicked() {
//        setToastMessage(loginMessage);
//            SystemClock.sleep(1500);
        if (Utils.valid_IP_Port(mAppModel.getIP(),mAppModel.getPort())) {
//            Log.d("AppModel", "onLoginClicked: IP: " + mAppModel.getIP()
//                + " valid: " + mAppModel.getPort());
            mAppModel.connect();
//            Log.d("AppModel", "onLoginClicked: after connection");

        } else {
//            setToastMessage(errorMessage);
            Log.d("AppModel", "onLoginClicked:valid_IP_Port: IP or Port not valid");
        }
    }

    @Override
    public void onJoystickMoved(float xAxisAileron, float yAxisElevator) {
        mAppModel.sendJoyStickUpdate(String.valueOf(xAxisAileron),String.valueOf(yAxisElevator));
//        Log.d("ViewModel", "X percent: " + xAxisAileron + " Y percent: " + yAxisElevator);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.rudderSB:
                mAppModel.sendRud(seekBarNormalization(progress));
                break;
            case R.id.throttleSB:
                mAppModel.sendThr(throttleBarNormalization(progress));
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
    private String seekBarNormalization(int value) {
        if(value < 100){
            return String.valueOf(-(1-(double)value/100));
        }
        return String.valueOf((double)(value-100)/100);
    }
    private String throttleBarNormalization(int value) {
        return String.valueOf((double)value / 100);
    }
}
