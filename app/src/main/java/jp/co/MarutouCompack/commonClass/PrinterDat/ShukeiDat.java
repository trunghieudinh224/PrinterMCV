package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

public class ShukeiDat {
    @SerializedName("shukeiDate")
    private String shukeiDate;
    @SerializedName("nKensu")
    private int nKensu;
    @SerializedName("nGsiyou")
    private int nGsiyou;
    @SerializedName("nGryokin")
    private int nGryokin;
    @SerializedName("nShohi")
    private int nShohi;
    @SerializedName("nKang")
    private int nKang;
    @SerializedName("nTotal")
    private int nTotal;
    @SerializedName("nToyuCnt")
    private int nToyuCnt;
    @SerializedName("nToyuUse")
    private int nToyuUse;
    @SerializedName("nToyuKin")
    private int nToyuKin;
    @SerializedName("nToyuTax")
    private int nToyuTax;
    @SerializedName("nToyuTotal")
    private int nToyuTotal;
    @SerializedName("nNyuCnt")
    private int nNyuCnt;
    @SerializedName("nNyukin")
    private int nNyukin;
    @SerializedName("nChosei")
    private int nChosei;
    @SerializedName("nUricnt")
    private int nUricnt;
    @SerializedName("nUrisur")
    private int nUrisur;
    @SerializedName("nUrikin")
    private int nUrikin;
    @SerializedName("nUritax")
    private int nUritax;
    @SerializedName("m_isToyukensinFlg")
    private boolean bIsToyukensinFlg;

    public ShukeiDat(String shukeiDate, int nKensu, int nGsiyou, int nGryokin, int nShohi, int nKang, int nTotal,
                     int nToyuCnt, int nToyuKin, int nToyuUse, int nToyuTax, int nToyuTotal, int nNyuCnt,
                     int nNyukin, int nChosei, int nUricnt, int nUrisur, int nUrikin, int nUritax, boolean bIsToyukensinFlg) {
        this.shukeiDate = shukeiDate;
        this.nKensu = nKensu;
        this.nGsiyou = nGsiyou;
        this.nGryokin = nGryokin;
        this.nShohi = nShohi;
        this.nKang = nKang;
        this.nTotal = nTotal;
        this.nToyuCnt = nToyuCnt;
        this.nToyuKin = nToyuKin;
        this.nToyuUse = nToyuUse;
        this.nToyuTax = nToyuTax;
        this.nToyuTotal = nToyuTotal;
        this.nNyuCnt = nNyuCnt;
        this.nNyukin = nNyukin;
        this.nChosei = nChosei;
        this.nUricnt = nUricnt;
        this.nUrisur = nUrisur;
        this.nUrikin = nUrikin;
        this.nUritax = nUritax;
        this.bIsToyukensinFlg = bIsToyukensinFlg;
    }

    public String getShukeiDate() {
        return shukeiDate;
    }

    public void setShukeiDate(String shukeiDate) {
        this.shukeiDate = shukeiDate;
    }

    public int getKensu() {
        return nKensu;
    }

    public void setKensu(int nKensu) {
        this.nKensu = nKensu;
    }

    public int getGsiyou() {
        return nGsiyou;
    }

    public void setGsiyou(int nGsiyou) {
        this.nGsiyou = nGsiyou;
    }

    public int getGryokin() {
        return nGryokin;
    }

    public void setGryokin(int nGryokin) {
        this.nGryokin = nGryokin;
    }

    public int getShohi() {
        return nShohi;
    }

    public void setShohi(int nShohi) {
        this.nShohi = nShohi;
    }

    public int getKang() {
        return nKang;
    }

    public void setKang(int nKang) {
        this.nKang = nKang;
    }

    public int getTotal() {
        return nTotal;
    }

    public void setTotal(int nTotal) {
        this.nTotal = nTotal;
    }

    public int getToyuCnt() {
        return nToyuCnt;
    }

    public void setToyuCnt(int nToyuCnt) {
        this.nToyuCnt = nToyuCnt;
    }

    public int getToyuUse() {
        return nToyuUse;
    }

    public void setToyuUse(int nToyuUse) {
        this.nToyuUse = nToyuUse;
    }

    public int getToyuKin() {
        return nToyuKin;
    }

    public void setToyuKin(int nToyuKin) {
        this.nToyuKin = nToyuKin;
    }

    public int getToyuTax() {
        return nToyuTax;
    }

    public void setToyuTax(int nToyuTax) {
        this.nToyuTax = nToyuTax;
    }

    public int getToyuTotal() {
        return nToyuTotal;
    }

    public void setToyuTotal(int nToyuTotal) {
        this.nToyuTotal = nToyuTotal;
    }

    public int getNyuCnt() {
        return nNyuCnt;
    }

    public void setNyuCnt(int nNyuCnt) {
        this.nNyuCnt = nNyuCnt;
    }

    public int getNyukin() {
        return nNyukin;
    }

    public void setNyukin(int nNyukin) {
        this.nNyukin = nNyukin;
    }

    public int getChosei() {
        return nChosei;
    }

    public void setChosei(int nChosei) {
        this.nChosei = nChosei;
    }

    public int getUricnt() {
        return nUricnt;
    }

    public void setUricnt(int nUricnt) {
        this.nUricnt = nUricnt;
    }

    public int getUrisur() {
        return nUrisur;
    }

    public void setUrisur(int nUrisur) {
        this.nUrisur = nUrisur;
    }

    public int getUrikin() {
        return nUrikin;
    }

    public void setUrikin(int nUrikin) {
        this.nUrikin = nUrikin;
    }

    public int getUritax() {
        return nUritax;
    }

    public void setUritax(int nUritax) {
        this.nUritax = nUritax;
    }

    public boolean isToyukensinFlg() {
        return bIsToyukensinFlg;
    }

    public void setIsToyukensinFlg(boolean bIsToyukensinFlg) {
        this.bIsToyukensinFlg = bIsToyukensinFlg;
    }
}
