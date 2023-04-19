package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

public class ANyukinDat {
    @SerializedName("mUTC")
    private UTaxCommentDat mUTC;
    @SerializedName("sChoseiTitle")
    private String sChoseiTitle;

    public ANyukinDat(UTaxCommentDat mUTC, String sChoseiTitle) {
        this.mUTC = mUTC;
        this.sChoseiTitle = sChoseiTitle;
    }

    public UTaxCommentDat getUTC() {
        return mUTC;
    }

    public void setUTC(UTaxCommentDat mUTC) {
        this.mUTC = mUTC;
    }

    public String getChoseiTitle() {
        return sChoseiTitle;
    }

    public void setChoseiTitle(String sChoseiTitle) {
        this.sChoseiTitle = sChoseiTitle;
    }
}
