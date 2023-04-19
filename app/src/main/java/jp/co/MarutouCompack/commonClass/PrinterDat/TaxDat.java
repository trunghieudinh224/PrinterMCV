package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

/**
 * 消費税データクラス.
 */
public class TaxDat {
    /** ガス内税 */
    @SerializedName("mGUchiZei")
    public long mGUchiZei = 0;
    /** ガス外内税 */
    @SerializedName("mUchiZei")
    public long mUchiZei = 0;

    public TaxDat(long mGUchiZei, long mUchiZei) {
        this.mGUchiZei = mGUchiZei;
        this.mUchiZei = mUchiZei;
    }

    public long getmGUchiZei() {
        return mGUchiZei;
    }

    public void setmGUchiZei(long mGUchiZei) {
        this.mGUchiZei = mGUchiZei;
    }

    public long getmUchiZei() {
        return mUchiZei;
    }

    public void setmUchiZei(long mUchiZei) {
        this.mUchiZei = mUchiZei;
    }
}
