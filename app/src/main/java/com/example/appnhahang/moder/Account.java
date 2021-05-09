package com.example.appnhahang.moder;

public class Account {
    String tentk,pass,name,namsinh,sdt,maql;
    public Account() {

    }

    public Account(String tentk, String pass, String name, String namsinh, String sdt, String maql) {
        this.tentk = tentk;
        this.pass = pass;
        this.name = name;
        this.namsinh = namsinh;
        this.sdt = sdt;
        this.maql = maql;

    }

    public String getTentk() {
        return tentk;
    }

    public void setTentk(String tentk) {
        this.tentk = tentk;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(String namsinh) {
        this.namsinh = namsinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMaql() {
        return maql;
    }

    public void setMaql(String maql) {
        this.maql = maql;
    }


}
