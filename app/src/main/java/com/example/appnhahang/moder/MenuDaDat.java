package com.example.appnhahang.moder;

public class MenuDaDat {
    String tentk,tenban;
    double tongtien;
    String time;
    String trangThai;

    public MenuDaDat() {
    }

    public String getTentk() {
        return tentk;
    }

    public void setTentk(String tentk) {
        this.tentk = tentk;
    }

    public String getTenban() {
        return tenban;
    }

    public void setTenban(String tenban) {
        this.tenban = tenban;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public MenuDaDat(String tentk, String tenban, double tongtien, String time, String trangThai) {
        this.tentk = tentk;
        this.tenban = tenban;
        this.tongtien = tongtien;
        this.time = time;
        this.trangThai = trangThai;
    }
}
