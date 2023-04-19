
package jp.co.MarutouCompack.commonClass.PrinterDat;

/**
 * 販売店データクラス.
 */
public class PrintStatus{

    /** 保安点検印刷フラグ */
    public boolean m_isPrintHoan;
    /** 入金印刷フラグ */
    public boolean m_isPrintNyukin;
    /** 領収印刷フラグ */
    public long m_lReceipt;
    /** 残高 */
    public long m_lZandaka;
    /** 検針印刷フラグ */
    public boolean m_isPrintKensin;
    /** 灯油印刷フラグ */
    public boolean m_isPrintToyu;

    /**
     * コンストラクタ.
     *
     * @param isPrintHoan   [in] boolean    保安点検印刷フラグ
     * @param isPrintNyukin [in] boolean    入金印刷フラグ
     * @param lReceipt      [in] long       領収額
     * @param lZandaka      [in] long       残高
     * @param isPrintKensin [in] boolean    検針印刷フラグ
     * @param isPrintToyu   [in] boolean    灯油印刷フラグ
     */
    public PrintStatus(boolean isPrintHoan, boolean isPrintNyukin, long lReceipt, long lZandaka, boolean isPrintKensin, boolean isPrintToyu){
        m_isPrintHoan = isPrintHoan;
        m_isPrintNyukin = isPrintNyukin;
        m_lReceipt = lReceipt;
        m_lZandaka = lZandaka;
        m_isPrintKensin = isPrintKensin;
        m_isPrintToyu = isPrintToyu;
    }

    public boolean isPrintHoan() {
        return m_isPrintHoan;
    }

    public void setIsPrintHoan(boolean m_isPrintHoan) {
        this.m_isPrintHoan = m_isPrintHoan;
    }

    public boolean isPrintNyukin() {
        return m_isPrintNyukin;
    }

    public void setIsPrintNyukin(boolean m_isPrintNyukin) {
        this.m_isPrintNyukin = m_isPrintNyukin;
    }

    public long getReceipt() {
        return m_lReceipt;
    }

    public void setReceipt(long m_lReceipt) {
        this.m_lReceipt = m_lReceipt;
    }

    public long getZandaka() {
        return m_lZandaka;
    }

    public void setZandaka(long m_lZandaka) {
        this.m_lZandaka = m_lZandaka;
    }

    public boolean isPrintKensin() {
        return m_isPrintKensin;
    }

    public void setIsPrintKensin(boolean m_isPrintKensin) {
        this.m_isPrintKensin = m_isPrintKensin;
    }

    public boolean isM_isPrintToyu() {
        return m_isPrintToyu;
    }

    public void setM_isPrintToyu(boolean m_isPrintToyu) {
        this.m_isPrintToyu = m_isPrintToyu;
    }
}
