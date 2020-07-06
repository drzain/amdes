package com.sip.amdesmobile;

public class DataMobil {

    private String idmobil, platmobil;

    public DataMobil(){

    }

    public DataMobil(String idmobil, String platmobil){
        this.idmobil = idmobil;
        this.platmobil = platmobil;
    }

    public String getIdmobil() {
        return idmobil;
    }

    public void setIdmobil(String idmobil) {
        this.idmobil = idmobil;
    }

    public String getPlatmobil() {
        return platmobil;
    }

    public void setPlatmobil(String platmobil) {
        this.platmobil = platmobil;
    }
}
