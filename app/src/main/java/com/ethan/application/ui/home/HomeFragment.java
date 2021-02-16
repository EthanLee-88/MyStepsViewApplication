package com.ethan.application.ui.home;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ethan.application.R;
import com.ethan.application.ui.view.MyStepView;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private MyStepView myStepView;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.text_home);
        myStepView = root.findViewById(R.id.my_step_view);
        homeViewModel.getText().observe(getViewLifecycleOwner(), (String s) -> {
            textView.setText(s);
        });
        initRes();
        return root;
    }

    private void initRes() {
        myStepView.setOnClickListener((View view) -> { //点击事件，模拟步数更新，使用属性动画
            setAnimator();
        });
        homeViewModel.getSteps().observe(getViewLifecycleOwner(), (Integer step) -> {
            Log.d(TAG , "updateSteps=" + step);
            myStepView.setSteps(step);  //更新步数
        });
    }

    private void setAnimator() {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 3900);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener((ValueAnimator animation) -> {
            float steps = (float) animation.getAnimatedValue();
            Log.d(TAG , "getSteps=" + steps);
            homeViewModel.setSteps((int) steps);
        });
        valueAnimator.start();
    }
}