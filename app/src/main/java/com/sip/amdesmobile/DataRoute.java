package com.sip.amdesmobile;

public class DataRoute {

    private String tanggal,rate,namakonsumen,nomorid,salesman,wilayah,area;

    public DataRoute(){

    }

    public DataRoute(String nomorid, String namakonsumen, String rate, String tanggal, String salesman, String wilayah, String area){
        this.tanggal = tanggal;
        this.rate = rate;
        this.namakonsumen = namakonsumen;
        this.nomorid = nomorid;
        this.salesman = salesman;
        this.wilayah = wilayah;
        this.area = area;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getNamakonsumen() {
        return namakonsumen;
    }

    public void setNamakonsumen(String namakonsumen) {
        this.namakonsumen = namakonsumen;
    }

    public String getNomorid() {
        return nomorid;
    }

    public void setNomorid(String nomorid) {
        this.nomorid = nomorid;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getWilayah() {
        return wilayah;
    }

    public void setWilayah(String wilayah) {
        this.wilayah = wilayah;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
