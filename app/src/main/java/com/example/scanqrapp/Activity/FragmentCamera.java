package com.example.scanqrapp.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.scanqrapp.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class FragmentCamera extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


       return inflater.inflate(R.layout.fragment_camera, container, false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String code = result.getContents();
            if (code== null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(result.getContents());

                    String mssv = obj.getString("mssv");
                    String hoten = obj.getString("hoten");
                    String gt = obj.getString("gt");
                    String tt = obj.getString("tt");
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("mssv", mssv); // Truyền một String
                    startActivity(intent);
                    intent.putExtras(bundle);







                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
