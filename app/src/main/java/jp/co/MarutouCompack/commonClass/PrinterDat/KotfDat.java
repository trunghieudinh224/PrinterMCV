
package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;
/**
 * 顧客灯油データクラス.
 */
public class KotfDat {
    /** 検針対象区分(0:対象, 1:非検針) */
    @SerializedName("m_bNo_kensin")
    private int m_bNo_kensin = 0;
    /** 灯油検針済み区分(0:未, 1:済) */
    @SerializedName("m_bKen_sumi")
    private int m_bKen_sumi = 0;
    /** 前回指針 */
    @SerializedName("m_nPre_meter")
    private int m_nPre_meter;
    /** 中間使用量 */
    @SerializedName("m_nBetw_meter")
    private int m_nBetw_meter;
    /** 前回使用量 */
    @SerializedName("m_nPre_use")
    private int m_nPre_use;
    /** 今回指針 */
    @SerializedName("m_nNow_meter")
    private int m_nNow_meter;
    /** 今回使用量 */
    @SerializedName("m_nLoil_use")
    private int m_nLoil_use;
    /** 灯油料金 */
    @SerializedName("m_nFee")
    private int m_nFee;
    /** 灯油消費税額 */
    @SerializedName("m_nCon_tax")
    private int m_nCon_tax;
    /** 灯油基本料金 */
    @SerializedName("m_nLoil_base")
    private int m_nLoil_base;
    /** メーター取替：月 */
    @SerializedName("m_bMtchg_m")
    private byte m_bMtchg_m;
    /** メーター取替：日 */
    @SerializedName("m_bMtchg_d")
    private byte m_bMtchg_d;
    /** 前回検針：月 */
    @SerializedName("m_bPuse_month")
    private byte m_bPuse_month;
    /** 前回検針：日 */
    @SerializedName("m_bPuse_day")
    private byte m_bPuse_day;
    /** メーター取替：旧指針 */
    @SerializedName("m_nMtchg_oldss")
    private int m_nMtchg_oldss;
    /** メーター取替：取替前検針時指針 */
    @SerializedName("m_nMtchg_zknss")
    private int m_nMtchg_zknss;

    public KotfDat(int m_bNo_kensin, int m_bKen_sumi, int m_nPre_meter, int m_nBetw_meter, int m_nPre_use, int m_nNow_meter, int m_nLoil_use,
                   int m_nFee, int m_nCon_tax, int m_nLoil_base, byte m_bMtchg_m, byte m_bMtchg_d, byte m_bPuse_month, byte m_bPuse_day,
                   int m_nMtchg_oldss, int m_nMtchg_zknss) {
        this.m_bNo_kensin = m_bNo_kensin;
        this.m_bKen_sumi = m_bKen_sumi;
        this.m_nPre_meter = m_nPre_meter;
        this.m_nBetw_meter = m_nBetw_meter;
        this.m_nPre_use = m_nPre_use;
        this.m_nNow_meter = m_nNow_meter;
        this.m_nLoil_use = m_nLoil_use;
        this.m_nFee = m_nFee;
        this.m_nCon_tax = m_nCon_tax;
        this.m_nLoil_base = m_nLoil_base;
        this.m_bMtchg_m = m_bMtchg_m;
        this.m_bMtchg_d = m_bMtchg_d;
        this.m_bPuse_month = m_bPuse_month;
        this.m_bPuse_day = m_bPuse_day;
        this.m_nMtchg_oldss = m_nMtchg_oldss;
        this.m_nMtchg_zknss = m_nMtchg_zknss;
    }

    public int getNo_kensin() {
        return m_bNo_kensin;
    }

    public void setNo_kensin(int m_bNo_kensin) {
        this.m_bNo_kensin = m_bNo_kensin;
    }

    public int getKen_sumi() {
        return m_bKen_sumi;
    }

    public void setKen_sumi(int m_bKen_sumi) {
        this.m_bKen_sumi = m_bKen_sumi;
    }

    public int getPre_meter() {
        return m_nPre_meter;
    }

    public void setPre_meter(int m_nPre_meter) {
        this.m_nPre_meter = m_nPre_meter;
    }

    public int getBetw_meter() {
        return m_nBetw_meter;
    }

    public void setBetw_meter(int m_nBetw_meter) {
        this.m_nBetw_meter = m_nBetw_meter;
    }

    public int getPre_use() {
        return m_nPre_use;
    }

    public void setPre_use(int m_nPre_use) {
        this.m_nPre_use = m_nPre_use;
    }

    public int getNow_meter() {
        return m_nNow_meter;
    }

    public void setNow_meter(int m_nNow_meter) {
        this.m_nNow_meter = m_nNow_meter;
    }

    public int getLoil_use() {
        return m_nLoil_use;
    }

    public void setLoil_use(int m_nLoil_use) {
        this.m_nLoil_use = m_nLoil_use;
    }

    public int getFee() {
        return m_nFee;
    }

    public void setFee(int m_nFee) {
        this.m_nFee = m_nFee;
    }

    public int getCon_tax() {
        return m_nCon_tax;
    }

    public void setCon_tax(int m_nCon_tax) {
        this.m_nCon_tax = m_nCon_tax;
    }

    public int getLoil_base() {
        return m_nLoil_base;
    }

    public void setLoil_base(int m_nLoil_base) {
        this.m_nLoil_base = m_nLoil_base;
    }

    public byte getMtchg_m() {
        return m_bMtchg_m;
    }

    public void setMtchg_m(byte m_bMtchg_m) {
        this.m_bMtchg_m = m_bMtchg_m;
    }

    public byte getMtchg_d() {
        return m_bMtchg_d;
    }

    public void setMtchg_d(byte m_bMtchg_d) {
        this.m_bMtchg_d = m_bMtchg_d;
    }

    public byte getPuse_month() {
        return m_bPuse_month;
    }

    public void setPuse_month(byte m_bPuse_month) {
        this.m_bPuse_month = m_bPuse_month;
    }

    public byte getPuse_day() {
        return m_bPuse_day;
    }

    public void setPuse_day(byte m_bPuse_day) {
        this.m_bPuse_day = m_bPuse_day;
    }

    public int getMtchg_oldss() {
        return m_nMtchg_oldss;
    }

    public void setMtchg_oldss(int m_nMtchg_oldss) {
        this.m_nMtchg_oldss = m_nMtchg_oldss;
    }

    public int getMtchg_zknss() {
        return m_nMtchg_zknss;
    }

    public void setMtchg_zknss(int m_nMtchg_zknss) {
        this.m_nMtchg_zknss = m_nMtchg_zknss;
    }
}
