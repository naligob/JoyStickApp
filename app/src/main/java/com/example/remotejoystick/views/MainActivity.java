package com.example.remotejoystick.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.remotejoystick.R;
import com.example.remotejoystick.databinding.ActivityMainBinding;
import com.example.remotejoystick.model.AppModel;
import com.example.remotejoystick.viewmodel.AppViewModel;

public class MainActivity extends AppCompatActivity {
    private AppViewModel mAppViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppViewModel = new AppViewModel();
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setViewModel(mAppViewModel);
        activityMainBinding.executePendingBindings();

        JoyStick joyStick = findViewById(R.id.JS);
        joyStick.setListener(mAppViewModel);

        SeekBar seekBar1 = findViewById(R.id.rudderSB);
        seekBar1.setOnSeekBarChangeListener(mAppViewModel);

        SeekBar seekBar2 = findViewById(R.id.throttleSB);
        seekBar2.setOnSeekBarChangeListener(mAppViewModel);
    }

    @BindingAdapter({"toastMessage"})
    public static void showToast(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}