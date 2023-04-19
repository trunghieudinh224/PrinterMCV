
package jp.co.MarutouCompack.commonClass.PrinterDat;


import com.google.gson.annotations.SerializedName;

/**
 * システム2情報(CNポイント)データクラス.
 */
public class Sy2fCnpDat {
    /** CNコメント１ */
    @SerializedName("mCnpComment_0")
    public String mCnpComment_0 = "";
    /** CNコメント２ */
    @SerializedName("mCnpComment_1")
    public String mCnpComment_1 = "";
    /** CNコメント３ */
    @SerializedName("mCnpComment_2")
    public String mCnpComment_2 = "";

    /**
     * コンストラクタ<br />
     * 各メンバ変数の初期化を実施。
     */
    public Sy2fCnpDat() {
    }

    public Sy2fCnpDat(String mCnpComment_0, String mCnpComment_1, String mCnpComment_2) {
        this.mCnpComment_0 = mCnpComment_0;
        this.mCnpComment_1 = mCnpComment_1;
        this.mCnpComment_2 = mCnpComment_2;
    }

    public String getCnpComment_0() {
        return mCnpComment_0;
    }

    public void setCnpComment_0(String mCnpComment_0) {
        this.mCnpComment_0 = mCnpComment_0;
    }

    public String getCnpComment_1() {
        return mCnpComment_1;
    }

    public void setCnpComment_1(String mCnpComment_1) {
        this.mCnpComment_1 = mCnpComment_1;
    }

    public String getCnpComment_2() {
        return mCnpComment_2;
    }

    public void setCnpComment_2(String mCnpComment_2) {
        this.mCnpComment_2 = mCnpComment_2;
    }
}
