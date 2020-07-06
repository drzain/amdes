package com.sip.amdesmobile;

public class DataTask {

    private String numberid, namakonsumen, rate, tanggal;

    public DataTask(){

    }

    public DataTask(String numberid, String namakonsumen, String rate, String tanggal){
        this.numberid = numberid;
        this.namakonsumen = namakonsumen;
        this.rate = rate;
        this.tanggal = tanggal;
    }

    public String getNumberid() {
        return numberid;
    }

    public void setNumberid(String numberid) {
        this.numberid = numberid;
    }

    public String getNamakonsumen() {
        return namakonsumen;
    }

    public void setNamakonsumen(String namakonsumen) {
        this.namakonsumen = namakonsumen;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
