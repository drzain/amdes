package com.sip.amdesmobile;

public class DataPdi {

    private String tanggal,rate,namakonsumen,nomorid,salesman,wilayah,area,nokaid,acc;

    public DataPdi(){

    }

    public DataPdi(String nomorid, String namakonsumen, String rate, String tanggal, String salesman, String wilayah, String area, String nokaid, String acc){
        this.tanggal = tanggal;
        this.rate = rate;
        this.namakonsumen = namakonsumen;
        this.nomorid = nomorid;
        this.salesman = salesman;
        this.wilayah = wilayah;
        this.area = area;
        this.nokaid = nokaid;
        this.acc = acc;
    }

    public String getNokaid() {
        return nokaid;
    }

    public void setNokaid(String nokaid) {
        this.nokaid = nokaid;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
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
