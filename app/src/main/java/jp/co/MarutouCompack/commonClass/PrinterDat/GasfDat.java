
package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * ガス料金データクラス.
 */

public class GasfDat {
    /** 計算方法 */
    @SerializedName("mSum")
    private byte mSum;
    /** 端数処理1：加算 */
    @SerializedName("mFrac1Add")
    private int mFrac1Add;
    /** 端数処理1:乗算 */
    @SerializedName("mFrac1Mult")
    private int mFrac1Mult;
    /** 増減率 */
    @SerializedName("mRiseFall")
    private short mRiseFall;
    /** 端数処理2:加算 */
    @SerializedName("mFrac2Add")
    private int mFrac2Add;
    /** 端数処理2:乗算 */
    @SerializedName("mFrac2Mult")
    private int mFrac2Mult;
    /** ガス料金ステップデータ */
    @SerializedName("m_lstGstpDat")
    private List<GstpDat> m_lstGstpDat;
    /** 消費税区分 */
    @SerializedName("mTaxDiv")
    private short mTaxDiv;
    /** 消費税端数処理：加算 */
    @SerializedName("mTaxAdd")
    private int mTaxAdd;
    /** 消費税端数処理：乗算 */
    @SerializedName("mTaxMult")
    private int mTaxMult;
    /** 調整単価 */
    @SerializedName("mChoTanka")
    private int mChoTanka;
    /** ガス料金拡張データ */
    @SerializedName("mGextDat")
    private GextDat mGextDat;
    /** 明細行数 */
    @SerializedName("mLine")
    private short mLine;

    public GasfDat(byte mSum, int mFrac1Add, int mFrac1Mult, short mRiseFall, int mFrac2Add, int mFrac2Mult, List<GstpDat> m_lstGstpDat, short mTaxDiv, int mTaxAdd,
                   int mTaxMult, int mChoTanka, GextDat mGextDat, short mLine) {
        this.mSum = mSum;
        this.mFrac1Add = mFrac1Add;
        this.mFrac1Mult = mFrac1Mult;
        this.mRiseFall = mRiseFall;
        this.mFrac2Add = mFrac2Add;
        this.mFrac2Mult = mFrac2Mult;
        this.m_lstGstpDat = m_lstGstpDat;
        this.mTaxDiv = mTaxDiv;
        this.mTaxAdd = mTaxAdd;
        this.mTaxMult = mTaxMult;
        this.mChoTanka = mChoTanka;
        this.mGextDat = mGextDat;
        this.mLine = mLine;
    }

    public byte getSum() {
        return mSum;
    }

    public void setSum(byte mSum) {
        this.mSum = mSum;
    }

    public int getFrac1Add() {
        return mFrac1Add;
    }

    public void setFrac1Add(int mFrac1Add) {
        this.mFrac1Add = mFrac1Add;
    }

    public int getFrac1Mult() {
        return mFrac1Mult;
    }

    public void setFrac1Mult(int mFrac1Mult) {
        this.mFrac1Mult = mFrac1Mult;
    }

    public short getRiseFall() {
        return mRiseFall;
    }

    public void setRiseFall(short mRiseFall) {
        this.mRiseFall = mRiseFall;
    }

    public int getFrac2Add() {
        return mFrac2Add;
    }

    public void setFrac2Add(int mFrac2Add) {
        this.mFrac2Add = mFrac2Add;
    }

    public int getFrac2Mult() {
        return mFrac2Mult;
    }

    public void setFrac2Mult(int mFrac2Mult) {
        this.mFrac2Mult = mFrac2Mult;
    }

    public List<GstpDat> getLstGstpDat() {
        return m_lstGstpDat;
    }

    public void setLstGstpDat(List<GstpDat> m_lstGstpDat) {
        this.m_lstGstpDat = m_lstGstpDat;
    }

    public short getTaxDiv() {
        return mTaxDiv;
    }

    public void setTaxDiv(short mTaxDiv) {
        this.mTaxDiv = mTaxDiv;
    }

    public int getTaxAdd() {
        return mTaxAdd;
    }

    public void setTaxAdd(int mTaxAdd) {
        this.mTaxAdd = mTaxAdd;
    }

    public int getTaxMult() {
        return mTaxMult;
    }

    public void setTaxMult(int mTaxMult) {
        this.mTaxMult = mTaxMult;
    }

    public int getChoTanka() {
        return mChoTanka;
    }

    public void setChoTanka(int mChoTanka) {
        this.mChoTanka = mChoTanka;
    }

    public GextDat getGextDat() {
        return mGextDat;
    }

    public void setGextDat(GextDat mGextDat) {
        this.mGextDat = mGextDat;
    }

    public short getLine() {
        return mLine;
    }

    public void setLine(short mLine) {
        this.mLine = mLine;
    }
}