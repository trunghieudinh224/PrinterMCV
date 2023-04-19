package jp.co.MarutouCompack.commonClass.PrinterDat;


import com.google.gson.annotations.SerializedName;

/**
 * 値引きデータクラス.
 */
@SuppressWarnings("unused")
public class KnebDat {

    /** コード */
    @SerializedName("m_nCode")
    private byte m_nCode;
    /** 有無 */
    @SerializedName("m_nUmu")
    private byte m_nUmu;
    /** 種別 */
    @SerializedName("m_nKind")
    private byte m_nKind;
    /** 値引き金額 */
    @SerializedName("m_nSkkin")
    private int m_nSkkin;
    /** 値引率 */
    @SerializedName("m_nSkper")
    private int m_nSkper;
    /** 商品コード */
    @SerializedName("m_nSncode")
    private short m_nSncode;
    /** 使用量下限 */
    @SerializedName("m_nLimit_s")
    private int m_nLimit_s;
    /** 使用量上限 */
    @SerializedName("m_nLimit_e")
    private int m_nLimit_e;
    /** 結果 */
    @SerializedName("m_nRes")
    private byte m_nRes;
    /** 金額 */
    @SerializedName("m_nKin")
    private int m_nKin;
    /** 消費税 */
    @SerializedName("m_nTax")
    private int m_nTax;
    /** 使用量下限 */
    @SerializedName("m_nLowlimit")
    private byte m_nLowlimit;

    /**
     * コンストラクタ
     */
    public KnebDat(){
    }

    public KnebDat(byte m_nCode, byte m_nUmu, byte m_nKind, int m_nSkkin, int m_nSkper, short m_nSncode, int m_nLimit_s, int m_nLimit_e, byte m_nRes, int m_nKin, int m_nTax, byte m_nLowlimit) {
        this.m_nCode = m_nCode;
        this.m_nUmu = m_nUmu;
        this.m_nKind = m_nKind;
        this.m_nSkkin = m_nSkkin;
        this.m_nSkper = m_nSkper;
        this.m_nSncode = m_nSncode;
        this.m_nLimit_s = m_nLimit_s;
        this.m_nLimit_e = m_nLimit_e;
        this.m_nRes = m_nRes;
        this.m_nKin = m_nKin;
        this.m_nTax = m_nTax;
        this.m_nLowlimit = m_nLowlimit;
    }

    public byte getCode() {
        return m_nCode;
    }

    public void setCode(byte m_nCode) {
        this.m_nCode = m_nCode;
    }

    public byte getUmu() {
        return m_nUmu;
    }

    public void setUmu(byte m_nUmu) {
        this.m_nUmu = m_nUmu;
    }

    public byte getKind() {
        return m_nKind;
    }

    public void setKind(byte m_nKind) {
        this.m_nKind = m_nKind;
    }

    public int getSkkin() {
        return m_nSkkin;
    }

    public void setSkkin(int m_nSkkin) {
        this.m_nSkkin = m_nSkkin;
    }

    public int getSkper() {
        return m_nSkper;
    }

    public void setSkper(int m_nSkper) {
        this.m_nSkper = m_nSkper;
    }

    public short getSncode() {
        return m_nSncode;
    }

    public void setSncode(short m_nSncode) {
        this.m_nSncode = m_nSncode;
    }

    public int getLimit_s() {
        return m_nLimit_s;
    }

    public void setLimit_s(int m_nLimit_s) {
        this.m_nLimit_s = m_nLimit_s;
    }

    public int getLimit_e() {
        return m_nLimit_e;
    }

    public void setLimit_e(int m_nLimit_e) {
        this.m_nLimit_e = m_nLimit_e;
    }

    public byte getRes() {
        return m_nRes;
    }

    public void setRes(byte m_nRes) {
        this.m_nRes = m_nRes;
    }

    public int getKin() {
        return m_nKin;
    }

    public void setKin(int m_nKin) {
        this.m_nKin = m_nKin;
    }

    public int getTax() {
        return m_nTax;
    }

    public void setTax(int m_nTax) {
        this.m_nTax = m_nTax;
    }

    public byte getLowlimit() {
        return m_nLowlimit;
    }

    public void setLowlimit(byte m_nLowlimit) {
        this.m_nLowlimit = m_nLowlimit;
    }
}
