
package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

import java.util.GregorianCalendar;

import jp.co.MarutouCompack.baseClass.OtherUtil;

/**
 * 印刷顧客データクラス.
 */
@SuppressWarnings("unused")
public class CusData {
    /** 検針日 */
    @SerializedName("m_strDate")
    private String m_strDate;
    /** 顧客コード */
    @SerializedName("m_strKcode")
    private String m_strKcode;
    /** 顧客名1 */
    @SerializedName("m_strName0")
    private String m_strName0;
    /** 顧客名2 */
    @SerializedName("m_strName1")
    private String m_strName1;
    /** 敬称 */
    @SerializedName("m_strKname")
    private String m_strKname;
    /** 住所１ */
    @SerializedName("m_strAdd0")
    private String m_strAdd0;
    /** 住所２ */
    @SerializedName("m_strAdd1")
    private String m_strAdd1;

    /**
     * コンストラクタ.
     */
    public CusData(String m_strDate, String m_strKcode, String m_strName0, String m_strName1, String m_strKname, String m_strAdd0, String m_strAdd1) {
        this.m_strDate = m_strDate;
        this.m_strKcode = m_strKcode;
        this.m_strName0 = m_strName0;
        this.m_strName1 = m_strName1;
        this.m_strKname = m_strKname;
        this.m_strAdd0 = m_strAdd0;
        this.m_strAdd1 = m_strAdd1;
    }

    /**
     * 検針日を取得する。
     * 
     * @return String   検針日
     */
    public String getDate() {
        return m_strDate;
    }

    /**
     * 検針日をセットする。
     * 
     * @param strDate   [in] String 検針日
     */
    public void setDate(String strDate) {
        m_strDate = strDate;
    }

    /**
     * 顧客コードを取得する。
     * 
     * @return String   顧客コード
     */
    public String getKcode() {
        return m_strKcode;
    }

    /**
     * 顧客コードをセットする。
     * 
     * @param strKcode  [in] String 顧客コード
     */
    public void setKcode(String strKcode) {
        m_strKcode = strKcode;
    }

    /**
     * 伝票用氏名1の取得.
     *
     * @return String   伝票用氏名1
     */
    public String getName0() {
        return m_strName0;
    }

    /**
     * 伝票用氏名2の取得.
     *
     * @return String   伝票用氏名2
     */
    public String getName1() {
        return m_strName1;
    }

    /**
     * 伝票用紙名1,2の設定.
     *
     * @param strName0  [in] String 伝票用紙名1
     * @param strName1  [in] string 伝票用紙名2
     */
    public void setName(String strName0, String strName1) {
        m_strName0 = strName0;
        m_strName1 = strName1;
    }

    /**
     * 敬称の取得.
     *
     * @return  String  敬称
     */
    public String getKname() {
        return m_strKname;
    }

    /**
     * 敬称の設定.
     *
     * @param kname [in] String 敬称
     */
    public void setKname(String kname) {
        m_strKname = kname;
    }

    /**
     * 住所1の取得.
     *
     * @return String   住所1
     */
    public String getAdd0() {
        return m_strAdd0;
    }

    /**
     * 住所1の設定.
     *
     * @param add0  [in] String 住所1
     */
    public void setAdd0(String add0) {
        m_strAdd0 = add0;
    }

    /**
     * 住所2の取得.
     *
     * @return  String  住所2
     */
    public String getAdd1() {
        return m_strAdd1;
    }

    /**
     * 住所2の設定.
     *
     * @param add1  [in] String 住所2
     */
    public void setAdd1(String add1) {
        m_strAdd1 = add1;
    }
}
