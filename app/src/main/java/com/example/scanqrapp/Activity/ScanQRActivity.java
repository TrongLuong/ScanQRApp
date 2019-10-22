package com.example.scanqrapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Vibrator;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.scanqrapp.DataSQLite.DataStudent;
import com.example.scanqrapp.Object.Student;
import com.example.scanqrapp.R;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanQRActivity extends AppCompatActivity  {

    private Button btnSave, btnScanQR;
    private TextView txtMSSV, txtHoTen, txtGT, txtTT;
    DataStudent dataStudent;


    //ánh xạ
    private void initWidget() {
        btnScanQR = findViewById(R.id.btnscanqrSC);
        txtMSSV = findViewById(R.id.txtMSSVSC);
        txtHoTen = findViewById(R.id.txtHoTenSC);
        txtGT = findViewById(R.id.txtGTSC);
        txtTT = findViewById(R.id.txtTTSC);
        btnSave = findViewById(R.id.btnSaveSC);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        initWidget();
        dataStudent = new DataStudent(this);
        final IntentIntegrator integrator = new IntentIntegrator(this);
        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                integrator.setPrompt("Quét mã QR sinh viên");
                integrator.initiateScan();
                integrator.setOrientationLocked(false);


                btnSave.setEnabled(true);
                btnScanQR.setEnabled(false);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = dataStudent.getStudentByMSSV(Integer.parseInt(txtMSSV.getText().toString()));
                int sndh = student.getSoNgayDiHoc();
                if (txtTT.getText().toString().equals("Có")) {
                    student.setSoNgayDiHoc(sndh + 1);
                    dataStudent.UpdateSoNgayHoc(student);

                }
                Toast.makeText(ScanQRActivity.this,"Có đi học hôm nay!", Toast.LENGTH_SHORT).show();
                btnSave.setEnabled(false);
                btnScanQR.setEnabled(true);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            if (result.getContents() == null) {
                Toast.makeText(this, "Không tìm thấy kết quả", Toast.LENGTH_LONG).show();
                btnScanQR.setEnabled(true);
                btnSave.setEnabled(false);

            } else {
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                    String mssv = obj.getString("mssv");
                    String hoten = obj.getString("hoten");
                    String gt = obj.getString("gt");
                    String tt = obj.getString("tt");


                    txtMSSV.setText(mssv);
                    txtHoTen.setText(hoten);
                    txtTT.setText(tt);
                    txtGT.setText(gt);
                    //chinh rung khi lay kq thanh cong
                    Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(400);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
