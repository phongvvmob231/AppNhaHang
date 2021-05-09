package com.example.appnhahang.moder;

public class Menu {
    private String maMon;
    private String tenMon;
    private String giaTien;
    private String hinhAhMon;
    String tentk,tenban;


    public Menu() {

    }

    public Menu(String maMon, String tenMon, String giaTien, String hinhAhMon, String tentk, String tenban) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.giaTien = giaTien;
        this.hinhAhMon = hinhAhMon;
        this.tentk = tentk;
        this.tenban = tenban;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public String getHinhAhMon() {
        return hinhAhMon;
    }

    public void setHinhAhMon(String hinhAhMon) {
        this.hinhAhMon = hinhAhMon;
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
}
