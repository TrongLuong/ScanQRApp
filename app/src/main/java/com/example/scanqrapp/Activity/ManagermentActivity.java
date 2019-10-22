package com.example.scanqrapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scanqrapp.DataSQLite.DataStudent;
import com.example.scanqrapp.MainActivity;
import com.example.scanqrapp.Object.Student;
import com.example.scanqrapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ManagermentActivity extends AppCompatActivity implements View.OnClickListener {
    DataStudent dataStudent = new DataStudent(this);
    CustomAdapterLiv customAdapter;
    private List<Student> students;
    private ListView livStudent;
    private CheckBox cbDeleItem;
    private Button btnDelete, btnAdd, btnCapNhat;
    int position;
    Dialog dialog;

    private void init() {
        btnDelete = findViewById(R.id.btndeleteMA);
        livStudent = findViewById(R.id.livStudentMA);
        btnAdd = findViewById(R.id.btnAddMA);
        btnCapNhat = findViewById(R.id.btnCapNhatMA);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        init();
        students = dataStudent.getAllStudent();
        setCustomAdapter();
        btnDelete.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnCapNhat.setOnClickListener(this);
        livStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                return false;
            }
        });
        registerForContextMenu(livStudent);
    }

    @Override
    protected void onResume() {

        students.clear();
        students.addAll(dataStudent.getAllStudent());
        setCustomAdapter();
        super.onResume();
    }

    public void dsSudent() {
        dataStudent.addStudent(new Student(16038781, "Lương Nguyễn Đức Trọng", 1998, 1, "Tây Ninh", 1));
        dataStudent.addStudent(new Student(16038782, "Nguyễn Tú Trinh", 1998, 0, "Gò Vấp", 1));
        dataStudent.addStudent(new Student(16038721, "Lê Minh Long", 1998, 1, "Bình Dương", 1));
        dataStudent.addStudent(new Student(16028925, "Trương Duy Linh", 1998, 0, "Đà Lạt", 1));
        dataStudent.addStudent(new Student(16025883, "Dương Quí Phi", 1998, 0, "TPHCM", 1));
        dataStudent.addStudent(new Student(16087925, "Trần Hồng Nhung", 1998, 0, "Cà Mau", 1));
        dataStudent.addStudent(new Student(16069752, "Lê Thanh Hằng", 1998, 0, "Tây Nguyên", 1));
        dataStudent.addStudent(new Student(16075249, "Nguyễn Đức Long", 1998, 1, "Huế", 1));
        dataStudent.addStudent(new Student(16038662, "Lương Minh Trang", 1998, 0, "Hà Nội", 1));
        dataStudent.addStudent(new Student(16925333, "Hà Anh", 1998, 0, "Bình Phước", 1));
        dataStudent.addStudent(new Student(16925330, "Hà Anh Tuấn", 1998, 1, "Đồng Nai", 1));
        dataStudent.addStudent(new Student(16925338, "Kiều Vũ Đại", 1998, 1, "Vũ Đại", 1));

    }

    public void deleteStudent() {
        for (int i = livStudent.getChildCount() - 1; i >= 0; i--) {
            View v = livStudent.getChildAt(i);
            cbDeleItem = v.findViewById(R.id.cbXoaCustom);
            if (cbDeleItem.isChecked()) {
                int mssv = students.get(i).getMssv();
                Student st = new Student(mssv);
                dataStudent.deleteStudent(st);
                students.remove(i);
                Toast.makeText(ManagermentActivity.this, "Xóa Thành Công!", Toast.LENGTH_SHORT).show();
            }
            customAdapter.notifyDataSetChanged();
        }
    }

    public void setCustomAdapter() {
        if (customAdapter == null) {
            customAdapter = new CustomAdapterLiv(this, R.layout.custom_adapter_liv, students);
            livStudent.setAdapter(customAdapter);
        } else {
            customAdapter.notifyDataSetChanged();
            livStudent.setSelection(customAdapter.getCount() - 1);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCapNhatMA:
                dsSudent();
                students.clear();
                students.addAll(dataStudent.getAllStudent());
                setCustomAdapter();
                break;
            case R.id.btndeleteMA:
                deleteStudent();
                students.clear();
                students.addAll(dataStudent.getAllStudent());
                setCustomAdapter();
                break;
            case R.id.btnAddMA:
                Intent intentAD = new Intent(this, AddStudenActivity.class);
                startActivity(intentAD);
                break;


            default:
        }
    }

    //menu context
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        menu.setHeaderTitle("Chọn tác vụ!");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.itXemCT:
                xemChTiet();
                break;
            case R.id.itMyQR:
                xemMaQR();
                break;

        }
        return super.onContextItemSelected(item);
    }

    //xem ct - sua
    public void xemChTiet() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_chitiet);
        dialog.show();
        //khóa dialog, khi click ngoài k bị mất
        dialog.setCancelable(false);
        final TextView MSSV = dialog.findViewById(R.id.txtMSSVMA);
        final EditText editTextHoTen = dialog.findViewById(R.id.edtHoTenMA);
        final EditText editTextNamSinh = dialog.findViewById(R.id.edtNamSinhMA);
        final EditText editTextNgayHoc = dialog.findViewById(R.id.edtSoNgayHocMA);
        final EditText editTextDiaChi = dialog.findViewById(R.id.edtDiaChiMA);

        final RadioButton radioButtonNam = dialog.findViewById(R.id.radioNamMA);
        final RadioButton radioButtonNu = dialog.findViewById(R.id.radioNuMA);
        final RadioGroup radioGroupGT = dialog.findViewById(R.id.radioGTMA);

        final Student st = dataStudent.getStudentByMSSV(students.get(position).getMssv());
        MSSV.setText(st.getMssv() + "");

        editTextHoTen.setText(st.getHoTen());
        editTextNamSinh.setText(st.getNamSinh() + "");
        editTextDiaChi.setText(st.getDiaChi());
        editTextNgayHoc.setText(st.getSoNgayDiHoc() + "");

        if (st.getGioiTinh() == 1) {
            radioButtonNam.setChecked(true);
        }
        if (st.getGioiTinh() == 0) {
            radioButtonNu.setChecked(true);
        }
        Button buttonClose = dialog.findViewById(R.id.btnCloseMA);
        final Button buttonSave = dialog.findViewById(R.id.btnSaveMA);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                students.clear();
                students.addAll(dataStudent.getAllStudent());
                setCustomAdapter();

            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSave.setText("SAVE");
                editTextHoTen.setEnabled(true);
                editTextDiaChi.setEnabled(true);
                editTextNamSinh.setEnabled(true);
                editTextNgayHoc.setEnabled(true);
                radioButtonNam.setEnabled(true);
                radioButtonNu.setEnabled(true);
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Student student = dataStudent.getStudentByMSSV(Integer.parseInt(MSSV.getText().toString()));
                        student.setHoTen(editTextHoTen.getText().toString());
                        student.setDiaChi(editTextDiaChi.getText().toString());
                        student.setNamSinh(Integer.parseInt(editTextNamSinh.getText().toString()));
                        student.setSoNgayDiHoc(Integer.parseInt(editTextNgayHoc.getText().toString()));

                        if (radioGroupGT.getCheckedRadioButtonId() == R.id.radioNamMA) {
                            student.setGioiTinh(1);
                        } else {
                            student.setGioiTinh(0);
                        }
                        dataStudent.Update(student);

                        Toast.makeText(ManagermentActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();


                    }
                });

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

    public void xemMaQR() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_qr);
        dialog.show();
        //khóa dialog, khi click ngoài k bị mất
        dialog.setCancelable(false);

        TextView textViewTT = dialog.findViewById(R.id.txtDialogQrMA);
        Button buttonClose = dialog.findViewById(R.id.btnCloseDialogQRMA);
        ImageView imgLogoQR = dialog.findViewById(R.id.imgDialogQRMA);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        final Student student = dataStudent.getStudentByMSSV(students.get(position).getMssv());
        textViewTT.setText(student.getMssv() + " - "+ student.getHoTen());

        int mssv = student.getMssv();
        String ht = student.getHoTen();
        String gt;
        if (student.getGioiTinh() == 1) {
            gt = "Nam";
        } else {
            gt = "Nữ";
        }
        String tt ="Có";

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
