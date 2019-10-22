package com.example.scanqrapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.scanqrapp.DataSQLite.DataStudent;
import com.example.scanqrapp.MainActivity;
import com.example.scanqrapp.Object.Student;
import com.example.scanqrapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class AddStudenActivity extends AppCompatActivity {
    private ImageView imgLogoQR;
    private EditText edtMSSV, edtHoTen, edtNS, edtDiaChi;
    RadioGroup radioGroupGT, radioGroupTT;
    private Button btnSave, btnClose;
    DataStudent dataStudent;

    private void initWidget() {
        imgLogoQR = findViewById(R.id.imglogoAD);
        edtMSSV = findViewById(R.id.edtMSSVAD);
        radioGroupGT = findViewById(R.id.radioGTAD);

        edtNS = findViewById(R.id.edtNamSinhAD);
        edtHoTen = findViewById(R.id.edtHoTenAD);
        edtDiaChi = findViewById(R.id.edtDiaChiAD);
        radioGroupTT = findViewById(R.id.radioTTAD);
        btnSave = findViewById(R.id.btnSaveAD);
        btnClose = findViewById(R.id.btnCloseAD);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_studen);
        initWidget();
        dataStudent = new DataStudent(this);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = createStudent();
                if (student != null) {
                    dataStudent.addStudent(student);
                    Toast.makeText(AddStudenActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    createQR();
                } else {
                    Toast.makeText(AddStudenActivity.this, "Kiểm tra dữ liệu trống", Toast.LENGTH_SHORT).show();
                }
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

    private Student createStudent() {
        Student students;
        String mssv = (edtMSSV.getText().toString().trim());
        String ht = edtHoTen.getText().toString().trim();
        int gt;
        if (radioGroupGT.getCheckedRadioButtonId() == R.id.radioNuAD) {
            gt = 0;
        } else {
            gt = 1;
        }
        String ns = edtNS.getText().toString().trim();
        String dc = edtDiaChi.getText().toString().trim();
        int tt = 0;
        if (radioGroupTT.getCheckedRadioButtonId() == R.id.radioCoAD) {
            tt = 1;
        }
        if (mssv.length() == 0 || ht.length() == 0 || ns.length() == 0 || dc.length() == 0) {
            return null;
        } else
            students = new Student(Integer.parseInt(mssv), ht, Integer.parseInt(ns), gt, dc, tt);
        return students;
    }


    public void createQR() {
        int mssv = Integer.parseInt(edtMSSV.getText().toString().trim());
        String ht = edtHoTen.getText().toString().trim();
        String gt;
        if (radioGroupGT.getCheckedRadioButtonId() == R.id.radioNuAD) {
            gt = "Nữ";
        } else {
            gt = "Nam";
        }
      String tt= "";
        if (radioGroupTT.getCheckedRadioButtonId() == R.id.radioCoAD) {
            tt = "Có";
        }
        JSONObject js = new JSONObject();
        try {
            js.put("mssv", mssv);
            js.put("hoten", ht);
            js.put("gt", gt);
            js.put("tt", tt);
            Picasso.get().load("https://chart.googleapis.com/chart?cht=qr&chl=" + js + "&chs=200x200&chld=L|0").into(imgLogoQR);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
