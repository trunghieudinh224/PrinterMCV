
package jp.co.MarutouCompack.commonClass.PrinterDat;


import com.google.gson.annotations.SerializedName;

/**
 * システム2情報(CNポイント)データクラス.
 */
public class Sy2fCnpTempDat {
    /** CN仮会員コメント1 */
    @SerializedName("mCnpTempComment_0")
    private String mCnpTempComment_0 = "";
    /** CN仮会員コメント2 */
    @SerializedName("mCnpTempComment_1")
    private String mCnpTempComment_1 = "";
    /** CNコメント１ */
    @SerializedName("mCnpComment_0")
    private String mCnpComment_0 = "";
    /** CNコメント２ */
    @SerializedName("mCnpComment_1")
    private String mCnpComment_1 = "";
    /** CNコメント３ */
    @SerializedName("mCnpComment_2")
    private String mCnpComment_2 = "";

    /**
     * コンストラクタ<br />
     * 各メンバ変数の初期化を実施。
     */
    public Sy2fCnpTempDat() {
    }

    public Sy2fCnpTempDat(String mCnpTempComment_0, String mCnpTempComment_1, String mCnpComment_0, String mCnpComment_1, String mCnpComment_2) {
        this.mCnpTempComment_0 = mCnpTempComment_0;
        this.mCnpTempComment_1 = mCnpTempComment_1;
        this.mCnpComment_0 = mCnpComment_0;
        this.mCnpComment_1 = mCnpComment_1;
        this.mCnpComment_2 = mCnpComment_2;
    }

    public String getCnpTempComment_0() {
        return mCnpTempComment_0;
    }

    public void setCnpTempComment_0(String mCnpTempComment_0) {
        this.mCnpTempComment_0 = mCnpTempComment_0;
    }

    public String getCnpTempComment_1() {
        return mCnpTempComment_1;
    }

    public void setCnpTempComment_1(String mCnpTempComment_1) {
        this.mCnpTempComment_1 = mCnpTempComment_1;
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
