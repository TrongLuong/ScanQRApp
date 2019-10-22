package com.example.scanqrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.scanqrapp.Activity.AddStudenActivity;
import com.example.scanqrapp.Activity.ManagermentActivity;
import com.example.scanqrapp.Activity.ScanQRActivity;
import com.example.scanqrapp.Activity.SearchActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnScan, btnSearch, btnManagerment, btnAdd;

    private void  anhXa(){
        btnAdd = findViewById(R.id.addStudentMain);
        btnManagerment = findViewById(R.id.managerStudenMain);
        btnSearch = findViewById(R.id.searchStudentMain);
        btnScan = findViewById(R.id.scanqrStudentMain);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        btnManagerment.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addStudentMain:
                Intent intentADD = new Intent(this,AddStudenActivity.class);
                startActivity(intentADD);
                break;
            case R.id.searchStudentMain:
                Intent intentSE = new Intent(this, SearchActivity.class);
                startActivity(intentSE);
                break;
            case R.id.managerStudenMain:
                Intent intentMA = new Intent(this, ManagermentActivity.class);
                startActivity(intentMA);
                break;
            case R.id.scanqrStudentMain:
                Intent intentSC = new Intent(this, ScanQRActivity.class);
                startActivity(intentSC);
                break;
            default:
        }
    }
}
