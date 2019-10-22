package com.example.scanqrapp.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.scanqrapp.Object.Student;
import com.example.scanqrapp.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterLiv extends ArrayAdapter<Student> {
    private Context context;
    private int resource;
    private ArrayList<Student> arrStudent;
    //private final String TAG = getClass().getSimpleName();

    public CustomAdapterLiv(@NonNull Context context, int resource, @NonNull List<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrStudent = (ArrayList<Student>) objects;
    }

    //su dung class nay dung de khoi tao view 1 lan
//    public class ViewHoder {
//        TextView textViewMSSV;
//        TextView textViewHoTen;
//        ImageView imageViewGioiTinh;
//        TextView textViewNgayHoc;
//
//
//    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        ViewHoder viewHoder;
//        //nếu chưa tạo View
//        if (convertView == null) {
//            viewHoder = new ViewHoder();
//            convertView = LayoutInflater.from(context).inflate(R.layout.custom_adapter_liv, parent, false);
//            viewHoder.textViewMSSV = convertView.findViewById(R.id.txtMSSVCustom);
//            viewHoder.textViewHoTen = convertView.findViewById(R.id.txtHoTenCustom);
//            viewHoder.textViewNgayHoc = convertView.findViewById(R.id.txtNgayHocCustom);
//            viewHoder.imageViewGioiTinh = convertView.findViewById(R.id.imageCustom);
//            convertView.setTag(viewHoder);
//        } else {
//            //neu da khoi tao view roi, thi se lay ra ma khong can khoi tao lai nua
//            viewHoder = (ViewHoder) convertView.getTag();
//        }
//        Student student = arrStudent.get(position);
//
//        viewHoder.textViewMSSV.setText(student.getMssv()+"");
//        viewHoder.textViewHoTen.setText(student.getHoTen());
//        viewHoder.textViewNgayHoc.setText(student.getSoNgayDiHoc()+" Ngày");
//        if(student.getGioiTinh()==0){
//            viewHoder.imageViewGioiTinh.setImageResource(R.drawable.nu);
//        }
//        else {
//            viewHoder.imageViewGioiTinh.setImageResource(R.drawable.nam);
//        }
//
//
//        return convertView;
//    }
@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    convertView = LayoutInflater.from(context).inflate(R.layout.custom_adapter_liv, parent, false);
    ImageView imageIcon = convertView.findViewById(R.id.imageCustom);
    TextView textViewMSSV = convertView.findViewById(R.id.txtMSSVCustom);
    TextView textViewTen = convertView.findViewById(R.id.txtHoTenCustom);
    TextView textViewNgayHoc = convertView.findViewById(R.id.txtNgayHocCustom);
    Student st = arrStudent.get(position);
    if(st.getGioiTinh()==1){
        imageIcon.setImageResource(R.drawable.nam);
    }else {
        imageIcon.setImageResource(R.drawable.nu);
    }
    textViewMSSV.setText(st.getMssv()+ " ");
    textViewTen.setText(st.getHoTen());
    textViewNgayHoc.setText(st.getSoNgayDiHoc()+" ngày");
    return convertView;
}

}
