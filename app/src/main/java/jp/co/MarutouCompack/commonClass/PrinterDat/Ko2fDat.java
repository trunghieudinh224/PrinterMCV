
package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

/**
 * 顧客ハイブリッドデータクラス.
 */
public class Ko2fDat {
    /** ハイブリッドカウンタ数 */
    @SerializedName("kHyb_MAX")
    private static final int kHyb_MAX = 4;
    /** ハイブリッド料金区分 */
    @SerializedName("mGashyb")
    private short mGashyb;
    /** カウント値引消費税 */
    @SerializedName("mChoTax")
    private long mChoTax;
    /** カウンタ使用料 */
    @SerializedName("mUseKin")
    private long mUseKin;
    /** カウンタ使用料消費税 */
    @SerializedName("mUseTax")
    private long mUseTax;
    /** カウンター使用料:税区分 */
    @SerializedName("mUseTaxku")
    private short mUseTaxku;
    /** カウンター使用料:税率 */
    @SerializedName("mUseTaxr")
    private short mUseTaxr;
    /** カウント値引 */
    @SerializedName("mChoKin")
    private long mChoKin;
    /** 今回使用量 */
    @SerializedName("mGasUse")
    private long[] mGasUse;
    /** 通常使用量料金 */
    @SerializedName("mNorKin")
    private long mNorKin;
    /** ガス料金 */
    @SerializedName("mFee")
    private long[] mFee;
    /** 前回指針 */
    @SerializedName("mPreMeter")
    private long[] mPreMeter;
    /** 今回指針 */
    @SerializedName("mNowMeter")
    private long[] mNowMeter;

    public Ko2fDat(short mGashyb, long mChoTax, long mUseKin, long mUseTax, short mUseTaxku, short mUseTaxr, long mChoKin,
                   long[] mGasUse, long mNorKin, long[] mFee, long[] mPreMeter, long[] mNowMeter) {
        this.mGashyb = mGashyb;
        this.mChoTax = mChoTax;
        this.mUseKin = mUseKin;
        this.mUseTax = mUseTax;
        this.mUseTaxku = mUseTaxku;
        this.mUseTaxr = mUseTaxr;
        this.mChoKin = mChoKin;
        this.mGasUse = mGasUse;
        this.mNorKin = mNorKin;
        this.mFee = mFee;
        this.mPreMeter = mPreMeter;
        this.mNowMeter = mNowMeter;
    }

    public static int getHyb_MAX() {
        return kHyb_MAX;
    }

    public short getGashyb() {
        return mGashyb;
    }

    public void setGashyb(short mGashyb) {
        this.mGashyb = mGashyb;
    }

    public long getChoTax() {
        return mChoTax;
    }

    public void setChoTax(long mChoTax) {
        this.mChoTax = mChoTax;
    }

    public long getUseKin() {
        return mUseKin;
    }

    public void setUseKin(long mUseKin) {
        this.mUseKin = mUseKin;
    }

    public long getUseTax() {
        return mUseTax;
    }

    public void setUseTax(long mUseTax) {
        this.mUseTax = mUseTax;
    }

    public short geUseTaxku() {
        return mUseTaxku;
    }

    public void setUseTaxku(short mUseTaxku) {
        this.mUseTaxku = mUseTaxku;
    }

    public short getUseTaxr() {
        return mUseTaxr;
    }

    public void setUseTaxr(short mUseTaxr) {
        this.mUseTaxr = mUseTaxr;
    }

    public long getChoKin() {
        return mChoKin;
    }

    public void setChoKin(long mChoKin) {
        this.mChoKin = mChoKin;
    }

    public long[] getGasUse() {
        return mGasUse;
    }

    public void setGasUse(long[] mGasUse) {
        this.mGasUse = mGasUse;
    }

    public long getNorKin() {
        return mNorKin;
    }

    public void setNorKin(long mNorKin) {
        this.mNorKin = mNorKin;
    }

    public long[] getFee() {
        return mFee;
    }

    public void setFee(long[] mFee) {
        this.mFee = mFee;
    }

    public long[] getPreMeter() {
        return mPreMeter;
    }

    public void setPreMeter(long[] mPreMeter) {
        this.mPreMeter = mPreMeter;
    }

    public long[] getNowMeter() {
        return mNowMeter;
    }

    public void setNowMeter(long[] mNowMeter) {
        this.mNowMeter = mNowMeter;
    }
}