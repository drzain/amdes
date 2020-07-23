package com.sip.amdesmobile;

public class DataArmada {

    String idmobil,nopol,namadriver,terpakai;

    public DataArmada(){

    }

    public DataArmada(String idmobil, String nopol, String namadriver, String terpakai){
        this.idmobil = idmobil;
        this.nopol = nopol;
        this.namadriver = namadriver;
        this.terpakai = terpakai;
    }

    public String getIdmobil() {
        return idmobil;
    }

    public void setIdmobil(String idmobil) {
        this.idmobil = idmobil;
    }

    public String getNopol() {
        return nopol;
    }

    public void setNopol(String nopol) {
        this.nopol = nopol;
    }

    public String getNamadriver() {
        return namadriver;
    }

    public void setNamadriver(String namadriver) {
        this.namadriver = namadriver;
    }

    public String getTerpakai() {
        return terpakai;
    }

    public void setTerpakai(String terpakai) {
        this.terpakai = terpakai;
    }
}
