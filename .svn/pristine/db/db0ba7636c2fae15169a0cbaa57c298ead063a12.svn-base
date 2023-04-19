
package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat;

/**
 * 販売店データクラス.
 */
public class PrintGenuriInfo {

    /** 伝票用氏名1 */
    @SerializedName("m_strSname_0")
    private String m_strSname_0;
    /** 伝票用氏名2 */
    @SerializedName("m_strSname_1")
    private String m_strSname_1;
    /** 現金売りフラグ */
    @SerializedName("m_isGenuri")
    private boolean m_isGenuri;
    /** 調整額 */
    @SerializedName("m_nChokin")
    private int m_nChokin;
    /** 入金額 */
    @SerializedName("m_nNyukin")
    private int m_nNyukin;
    /** 預り金額 */
    @SerializedName("m_nReceipt")
    private int m_nReceipt;
    /** 今回売上明細一覧 */
    @SerializedName("m_lstHmefDat")
    private List<HmefDat> m_lstHmefDat;

    /**
     * コンストラクタ.
     *
     * @param strSname_0    [in] String                 伝票用氏名1
     * @param strSname_1    [in] String                 伝票用氏名2
     * @param isGenuri      [in] boolean                現金売りフラグ(true: 現金売り, false: 掛売)
     * @param nChokin       [in] int                    調整額
     * @param nNyukin       [in] int                    入金額
     * @param nReceipt      [in] int                    領収金額
     * @param lstHmefDat    [in] {@code List<HmefDat>}  売上明細
     */
    public PrintGenuriInfo(String strSname_0, String strSname_1, boolean isGenuri, int nChokin, int nNyukin, int nReceipt, List<HmefDat> lstHmefDat){
        m_strSname_0 = strSname_0;
        m_strSname_1 = strSname_1;
        m_isGenuri = isGenuri;
        m_nChokin = nChokin;
        m_nNyukin = nNyukin;
        m_nReceipt = nReceipt;
        m_lstHmefDat = lstHmefDat;
    }

    public String getSname_0() {
        return m_strSname_0;
    }

    public void setSname_0(String m_strSname_0) {
        this.m_strSname_0 = m_strSname_0;
    }

    public String getSname_1() {
        return m_strSname_1;
    }

    public void setSname_1(String m_strSname_1) {
        this.m_strSname_1 = m_strSname_1;
    }

    public boolean isGenuri() {
        return m_isGenuri;
    }

    public void setIsGenuri(boolean m_isGenuri) {
        this.m_isGenuri = m_isGenuri;
    }

    public int getChokin() {
        return m_nChokin;
    }

    public void setChokin(int m_nChokin) {
        this.m_nChokin = m_nChokin;
    }

    public int getNyukin() {
        return m_nNyukin;
    }

    public void setNyukin(int m_nNyukin) {
        this.m_nNyukin = m_nNyukin;
    }

    public int getReceipt() {
        return m_nReceipt;
    }

    public void setReceipt(int m_nReceipt) {
        this.m_nReceipt = m_nReceipt;
    }

    public List<HmefDat> getLstHmefDat() {
        return m_lstHmefDat;
    }

    public void setLstHmefDat(List<HmefDat> m_lstHmefDat) {
        this.m_lstHmefDat = m_lstHmefDat;
    }
}
