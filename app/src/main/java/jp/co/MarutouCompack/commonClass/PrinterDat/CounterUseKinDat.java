package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

public class CounterUseKinDat {
    @SerializedName("nUseKin")
    public int nUseKin;
    @SerializedName("nUseSncode")
    public int nUseSncode;
    @SerializedName("sKin")
    public String sKin;

    public CounterUseKinDat(int nUseKin, int nUseSncode, String sKin) {
        this.nUseKin = nUseKin;
        this.nUseSncode = nUseSncode;
        this.sKin = sKin;
    }

    public int getUseKin() {
        return nUseKin;
    }

    public void setUseKin(int nUseKin) {
        this.nUseKin = nUseKin;
    }

    public int getUseSncode() {
        return nUseSncode;
    }

    public void setUseSncode(int nUseSncode) {
        this.nUseSncode = nUseSncode;
    }

    public String getKin() {
        return sKin;
    }

    public void setKin(String sKin) {
        this.sKin = sKin;
    }
}
