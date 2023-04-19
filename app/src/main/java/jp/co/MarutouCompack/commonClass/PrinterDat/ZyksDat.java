
package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

/**
 * 前年同月使用量データクラス.
 */
public class ZyksDat {
    /** 使用量 */
    @SerializedName("m_nSr")
    public int m_nSr = 0;
    /** 伝票日付(年) */
    @SerializedName("m_nDenymd_year")
    public short m_nDenymd_year = (short)0;

    public ZyksDat(int m_nSr, short m_nDenymd_year) {
        this.m_nSr = m_nSr;
        this.m_nDenymd_year = m_nDenymd_year;
    }

    public int getM_nSr() {
        return m_nSr;
    }

    public void setM_nSr(int m_nSr) {
        this.m_nSr = m_nSr;
    }

    public short getM_nDenymd_year() {
        return m_nDenymd_year;
    }

    public void setM_nDenymd_year(short m_nDenymd_year) {
        this.m_nDenymd_year = m_nDenymd_year;
    }
}