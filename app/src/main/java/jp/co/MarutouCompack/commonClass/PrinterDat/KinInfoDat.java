package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

public class KinInfoDat {
    @SerializedName("bNyukinOnly")
    private boolean bNyukinOnly;
    @SerializedName("bIfDemand")
    private boolean bIfDemand;
    @SerializedName("sZanTitle")
    private String sZanTitle;
    @SerializedName("nPreReceipt")
    private int nPreReceipt;
    @SerializedName("bIfProceeds")
    private boolean bIfProceeds;
    @SerializedName("nHmDay")
    private int nHmDay;
    @SerializedName("nHmMonth")
    private int nHmMonth;
    @SerializedName("nTReceipt")
    private int nTReceipt;
    @SerializedName("nTAdjust")
    private int nTAdjust;
    @SerializedName("nReceipt")
    private int nReceipt;
    @SerializedName("bIsFuriDemand")
    private boolean bIsFuriDemand;
    @SerializedName("sIraimsg")
    private String sIraimsg;
    @SerializedName("sChoseiTitle")
    private String sChoseiTitle;
    @SerializedName("nChosei")
    private int nChosei;
    @SerializedName("nNyukin")
    private int nNyukin;
    @SerializedName("nAzukarikin")
    private int nAzukarikin;
    @SerializedName("nKZandaka")
    private int nKZandaka;
    @SerializedName("nLZandaka")
    private int nLZandaka;

    public KinInfoDat(boolean bNyukinOnly, boolean bIfDemand, String sZanTitle, int nPreReceipt, boolean bIfProceeds, int nHmDay, int nHmMonth, int nTReceipt, int nTAdjust, int nReceipt, boolean bIsFuriDemand, String sIraimsg, String sChoseiTitle, int nChosei, int nNyukin, int nAzukarikin, int nKZandaka, int nLZandaka) {
        this.bNyukinOnly = bNyukinOnly;
        this.bIfDemand = bIfDemand;
        this.sZanTitle = sZanTitle;
        this.nPreReceipt = nPreReceipt;
        this.bIfProceeds = bIfProceeds;
        this.nHmDay = nHmDay;
        this.nHmMonth = nHmMonth;
        this.nTReceipt = nTReceipt;
        this.nTAdjust = nTAdjust;
        this.nReceipt = nReceipt;
        this.bIsFuriDemand = bIsFuriDemand;
        this.sIraimsg = sIraimsg;
        this.sChoseiTitle = sChoseiTitle;
        this.nChosei = nChosei;
        this.nNyukin = nNyukin;
        this.nAzukarikin = nAzukarikin;
        this.nKZandaka = nKZandaka;
        this.nLZandaka = nLZandaka;
    }

    public boolean isNyukinOnly() {
        return bNyukinOnly;
    }

    public void setNyukinOnly(boolean bNyukinOnly) {
        this.bNyukinOnly = bNyukinOnly;
    }

    public boolean ifDemand() {
        return bIfDemand;
    }

    public void setIfDemand(boolean bIfDemand) {
        this.bIfDemand = bIfDemand;
    }

    public String getsZanTitle() {
        return sZanTitle;
    }

    public void setsZanTitle(String sZanTitle) {
        this.sZanTitle = sZanTitle;
    }

    public int getPreReceipt() {
        return nPreReceipt;
    }

    public void setPreReceipt(int nPreReceipt) {
        this.nPreReceipt = nPreReceipt;
    }

    public boolean ifProceeds() {
        return bIfProceeds;
    }

    public void setIfProceeds(boolean bIfProceeds) {
        this.bIfProceeds = bIfProceeds;
    }

    public int getHmDay() {
        return nHmDay;
    }

    public void setHmDay(int nHmDay) {
        this.nHmDay = nHmDay;
    }

    public int getHmMonth() {
        return nHmMonth;
    }

    public void setHmMonth(int nHmMonth) {
        this.nHmMonth = nHmMonth;
    }

    public int getTReceipt() {
        return nTReceipt;
    }

    public void setTReceipt(int nTReceipt) {
        this.nTReceipt = nTReceipt;
    }

    public int getTAdjust() {
        return nTAdjust;
    }

    public void setTAdjust(int nTAdjust) {
        this.nTAdjust = nTAdjust;
    }

    public int getReceipt() {
        return nReceipt;
    }

    public void setReceipt(int nReceipt) {
        this.nReceipt = nReceipt;
    }

    public boolean isFuriDemand() {
        return bIsFuriDemand;
    }

    public void setIsFuriDemand(boolean bIsFuriDemand) {
        this.bIsFuriDemand = bIsFuriDemand;
    }

    public String getIraimsg() {
        return sIraimsg;
    }

    public void setIraimsg(String sIraimsg) {
        this.sIraimsg = sIraimsg;
    }

    public String getsChoseiTitle() {
        return sChoseiTitle;
    }

    public void setsChoseiTitle(String sChoseiTitle) {
        this.sChoseiTitle = sChoseiTitle;
    }

    public int getChosei() {
        return nChosei;
    }

    public void setChosei(int nChosei) {
        this.nChosei = nChosei;
    }

    public int getNyukin() {
        return nNyukin;
    }

    public void setNyukin(int nNyukin) {
        this.nNyukin = nNyukin;
    }

    public int getAzukarikin() {
        return nAzukarikin;
    }

    public void setAzukarikin(int nAzukarikin) {
        this.nAzukarikin = nAzukarikin;
    }

    public int getKZandaka() {
        return nKZandaka;
    }

    public void setKZandaka(int nKZandaka) {
        this.nKZandaka = nKZandaka;
    }

    public int getLZandaka() {
        return nLZandaka;
    }

    public void setLZandaka(int nLZandaka) {
        this.nLZandaka = nLZandaka;
    }
}
