
package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

/**
 * 取引区分・品目データクラス
 */
public class BusfDat {
    /** 使用有無 */
    @SerializedName("mUsef")
    public boolean mUsef;
    /** 品目No(取引区分No) */
    @SerializedName("mHinno")
    public short mHinno;
    /** 名称 */
    @SerializedName("mName")
    public String mName;

    public BusfDat(boolean mUsef, short mHinno, String mName) {
        this.mUsef = mUsef;
        this.mHinno = mHinno;
        this.mName = mName;
    }

    public boolean isUsef() {
        return mUsef;
    }

    public void setUsef(boolean mUsef) {
        this.mUsef = mUsef;
    }

    public short getHinno() {
        return mHinno;
    }

    public void setHinno(short mHinno) {
        this.mHinno = mHinno;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}