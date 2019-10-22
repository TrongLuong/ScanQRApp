package com.example.scanqrapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scanqrapp.DataSQLite.DataStudent;
import com.example.scanqrapp.MainActivity;
import com.example.scanqrapp.Object.Student;
import com.example.scanqrapp.R;

public class SearchActivity extends AppCompatActivity {
    private EditText edtSearch;
    private Button btnSearh;
    private TextView txtMSSV, txtHoTen,txtGT,txtNS,txtTT,txtDiaChi;
    DataStudent dataStudent;

    private void initWidget() {
        edtSearch = findViewById(R.id.edtSearchSE);
        txtMSSV = findViewById(R.id.txtMSSVSE);
        txtHoTen = findViewById(R.id.txtHoTenSE);
        txtNS = findViewById(R.id.txtNamSinhSE);
        txtGT = findViewById(R.id.txtGioiTinhSE);
        txtDiaChi = findViewById(R.id.txtDiaChiSE);
        txtTT = findViewById(R.id.txtTrangThaiSE);
        btnSearh = findViewById(R.id.btnSearchSE);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initWidget();
        dataStudent = new DataStudent(this);

        btnSearh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ht = edtSearch.getText().toString();

                Student student =  dataStudent.getStudentByHoTen(ht);
                if(student != null){
                    txtMSSV.setText("MSSV: " +student.getMssv());
                    txtHoTen.setText("Ho ten: " +student.getHoTen());
                    txtNS.setText("Nam sinh: " +student.getNamSinh()+"");
                    if(student.getGioiTinh()==1)
                    {
                        txtGT.setText("GT: Nam");
                    }else {
                        txtGT.setText("GT: Nữ");
                    }

                    txtDiaChi.setText("Dia chi " +student.getDiaChi());
                    txtTT.setText("So ngay di hoc: " + student.getSoNgayDiHoc() +"ngay");
                }
                else {
                    Toast.makeText(SearchActivity.this,"Khong tim thay!", Toast.LENGTH_SHORT).show();
                }





            }
        });


    }
}
