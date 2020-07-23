package com.sip.amdesmobile;

public class DataDetailArmada {

    String idnumber, kota;

    public DataDetailArmada(){

    }

    public DataDetailArmada(String idnumber, String kota){
        this.idnumber = idnumber;
        this.kota = kota;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }
}
