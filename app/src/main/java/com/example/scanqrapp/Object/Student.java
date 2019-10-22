package com.example.scanqrapp.Object;

import java.io.Serializable;

public class Student implements Serializable {
    private int mssv;
    private String hoTen;
    private int namSinh;
    private int gioiTinh;
    private String diaChi;
    private int soNgayDiHoc;

    public Student(int mssv) {
        this.mssv = mssv;
    }

    public Student(int mssv, String hoTen, int namSinh, int gioiTinh, String diaChi, int soNgayDiHoc) {
        this.mssv = mssv;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.soNgayDiHoc = soNgayDiHoc;
    }

    public Student(){

    }


    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }

    public int getMssv() {
        return mssv;
    }

    public void setMssv(int mssv) {
        this.mssv = mssv;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getSoNgayDiHoc() {
        return soNgayDiHoc;
    }

    public void setSoNgayDiHoc(int soNgayDiHoc) {
        this.soNgayDiHoc = soNgayDiHoc;
    }
}
