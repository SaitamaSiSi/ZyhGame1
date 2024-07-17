package com.zyh.ZyhG1.ui.AndroidStudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zyh.ZyhG1.R;

public class Fragment1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        Button msgButton = view.findViewById(R.id.f1_btn_msg);
        msgButton.setOnClickListener((v) -> {
            Toast.makeText(this.getContext(), "You clicked Button 1", Toast.LENGTH_SHORT).show();
        });
        Button dialogButton = view.findViewById(R.id.f1_btn_dialog);
        dialogButton.setOnClickListener((v) -> {
            Activity activity = this.getActivity();
            if (activity != null) {
                Intent intent = new Intent(activity, DialogActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
