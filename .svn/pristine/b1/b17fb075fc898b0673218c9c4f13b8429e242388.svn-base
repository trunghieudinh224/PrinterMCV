
package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

/**
 * ガス料金拡張データクラス.
 */
public class GextDat {
    /** ガス基本料金 */
    @SerializedName("m_nBasekin")
    public long m_nBasekin = 0;
    /** ガス設備料 */
    @SerializedName("m_nFacilitykin")
    public long m_nFacilitykin = 0;
    /** ガス料金式印字フラグ */
    @SerializedName("m_nPrintGasryokinSiki")
    public byte m_nPrintGasryokinSiki = 0;

    public GextDat(long m_nBasekin, long m_nFacilitykin, byte m_nPrintGasryokinSiki) {
        this.m_nBasekin = m_nBasekin;
        this.m_nFacilitykin = m_nFacilitykin;
        this.m_nPrintGasryokinSiki = m_nPrintGasryokinSiki;
    }

    public long getBasekin() {
        return m_nBasekin;
    }

    public void setBasekin(long m_nBasekin) {
        this.m_nBasekin = m_nBasekin;
    }

    public long getFacilitykin() {
        return m_nFacilitykin;
    }

    public void setFacilitykin(long m_nFacilitykin) {
        this.m_nFacilitykin = m_nFacilitykin;
    }

    public byte getPrintGasryokinSiki() {
        return m_nPrintGasryokinSiki;
    }

    public void setPrintGasryokinSiki(byte m_nPrintGasryokinSiki) {
        this.m_nPrintGasryokinSiki = m_nPrintGasryokinSiki;
    }
}