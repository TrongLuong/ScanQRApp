package com.example.scanqrapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Vibrator;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.scanqrapp.DataSQLite.DataStudent;
import com.example.scanqrapp.MainActivity;
import com.example.scanqrapp.Object.Student;
import com.example.scanqrapp.R;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanQRActivity extends AppCompatActivity  {

    private Button btnSave, btnScanQR, btnClose;
    private TextView txtMSSV, txtHoTen, txtGT, txtTT, txtNS,txtDiaChi;
    private ImageView imgQR;
    DataStudent dataStudent;


    //ánh xạ
    private void initWidget() {
        btnScanQR = findViewById(R.id.btnscanqrSC);
        txtMSSV = findViewById(R.id.txtMSSVSC);
        txtHoTen = findViewById(R.id.txtHoTenSC);
        txtGT = findViewById(R.id.txtGTSC);
        txtTT = findViewById(R.id.txtTTSC);
        txtNS = findViewById(R.id.txtNamSinhSC);
        txtDiaChi = findViewById(R.id.txtDiaChiSC);
        imgQR = findViewById(R.id.imgQRSC);
        btnSave = findViewById(R.id.btnSaveSC);
        btnClose = findViewById(R.id.btnCloseSC);


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
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itQLSV:
                Intent iqlsv = new Intent(this, ManagermentActivity.class);
                startActivity(iqlsv);
                break;
            case R.id.itThemSV:
                Intent ithem = new Intent(this, AddStudenActivity.class);
                startActivity(ithem);
                break;
            case R.id.itQuetSV:
                Intent iquet = new Intent(this, ScanQRActivity.class);
                startActivity(iquet);
                break;
            case R.id.itTimSV:
                Intent iTim = new Intent(this, SearchActivity.class);
                startActivity(iTim);
                break;
            case R.id.itTC:
                Intent iTc = new Intent(this, MainActivity.class);
                startActivity(iTc);
                break;
        }
        return super.onOptionsItemSelected(item);
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
                    int mssv = Integer.parseInt(obj.getString("mssv"));
                    String hoten = obj.getString("hoten");
                    String gt = obj.getString("gt");
                    String tt = obj.getString("tt");
                    //lay student ra load len textview
                    Student s = dataStudent.getStudentByMSSV((mssv));
                    txtMSSV.setText(mssv+"");
                    txtHoTen.setText(hoten);
                    txtTT.setText(tt);
                    txtGT.setText(gt);
                    txtDiaChi.setText(s.getDiaChi());
                    txtNS.setText(s.getNamSinh()+"");


                    //chinh rung khi lay kq thanh cong
                    Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(400);
                    //khoi tao lai ma QR da quet
                    JSONObject js = new JSONObject();
                    try {
                        js.put("mssv", mssv);
                        js.put("hoten", hoten);
                        js.put("gt", gt);
                        js.put("tt", tt);
                        Picasso.get().load("https://chart.googleapis.com/chart?cht=qr&chl=" + js + "&chs=200x200&chld=L|0").into(imgQR);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


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
