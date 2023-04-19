
package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

/**
 * 顧客料金透明化データクラス.
 */
public class KtpcDat  {
    /** 基本料金 */
    @SerializedName("m_nBasekin")
    public int m_nBasekin = 0;
    /** 従量料金 */
    @SerializedName("m_nAddkin")
    public int m_nAddkin = 0;
    /** 設備料金 */
    @SerializedName("m_nFacilitykin")
    public int m_nFacilitykin = 0;

    public KtpcDat(int m_nBasekin, int m_nAddkin, int m_nFacilitykin) {
        this.m_nBasekin = m_nBasekin;
        this.m_nAddkin = m_nAddkin;
        this.m_nFacilitykin = m_nFacilitykin;
    }

    public int getM_nBasekin() {
        return m_nBasekin;
    }

    public void setM_nBasekin(int m_nBasekin) {
        this.m_nBasekin = m_nBasekin;
    }

    public int getM_nAddkin() {
        return m_nAddkin;
    }

    public void setM_nAddkin(int m_nAddkin) {
        this.m_nAddkin = m_nAddkin;
    }

    public int getM_nFacilitykin() {
        return m_nFacilitykin;
    }

    public void setM_nFacilitykin(int m_nFacilitykin) {
        this.m_nFacilitykin = m_nFacilitykin;
    }
}