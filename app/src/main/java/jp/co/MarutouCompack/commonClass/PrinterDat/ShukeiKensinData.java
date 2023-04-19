package jp.co.MarutouCompack.commonClass.PrinterDat;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ShukeiKensinData {
    /** 顧客コード */
    @SerializedName("m_strKcode")
    private String m_strKcode;
    /** 顧客名 */
    @SerializedName("m_strName")
    private String m_strName;
    /** 今回指針 */
    @SerializedName("m_nSs")
    private int m_nSs;
    /** 今回使用量 */
    @SerializedName("m_nSr")
    private int m_nSr;
    /** ガス料金 */
    @SerializedName("m_nKin")
    private long m_nKin;
    /** 消費税 */
    @SerializedName("m_nTax")
    private long m_nTax;
    /** 還元額 */
    @SerializedName("m_nKng")
    private long m_nKng;
    /** 灯油指針 */
    @SerializedName("m_nToyuSs")
    private int m_nToyuSs;
    /** 灯油使用量 */
    @SerializedName("m_nToyuSr")
    private int m_nToyuSr;
    /** 灯油金額 */
    @SerializedName("m_lToyuKin")
    private long m_lToyuKin;
    /** 灯油消費税 */
    @SerializedName("m_lToyuTax")
    private long m_lToyuTax;
    /** 灯油検針有無 */
    @SerializedName("m_isToyu")
    private boolean m_isToyu;
    /** ガス検針有無 */
    @SerializedName("m_isKensin")
    private boolean m_isKensin;
    /** 入金額 */
    @SerializedName("m_lNyu")
    private long m_lNyu;
    /** 調整額 */
    @SerializedName("m_lCho")
    private long m_lCho;

    /**
     * コンストラクタ.
     */
    public ShukeiKensinData(){
    }

    public ShukeiKensinData(String m_strKcode, String m_strName, int m_nSs, int m_nSr, long m_nKin, long m_nTax,
                            long m_nKng, int m_nToyuSs, int m_nToyuSr, long m_lToyuKin, long m_lToyuTax, boolean m_isToyu,
                            boolean m_isKensin, long m_lNyu, long m_lCho) {
        this.m_strKcode = m_strKcode;
        this.m_strName = m_strName;
        this.m_nSs = m_nSs;
        this.m_nSr = m_nSr;
        this.m_nKin = m_nKin;
        this.m_nTax = m_nTax;
        this.m_nKng = m_nKng;
        this.m_nToyuSs = m_nToyuSs;
        this.m_nToyuSr = m_nToyuSr;
        this.m_lToyuKin = m_lToyuKin;
        this.m_lToyuTax = m_lToyuTax;
        this.m_isToyu = m_isToyu;
        this.m_isKensin = m_isKensin;
        this.m_lNyu = m_lNyu;
        this.m_lCho = m_lCho;
    }

    /**
     * 顧客コードの取得
     *
     * @return  String  顧客コード
     */
    public String getKcode(){
        return m_strKcode;
    }

    /**
     * 顧客コードの設定
     *
     * @param strKcode  [in] String 顧客コード
     */
    public void setKcode(String strKcode){
        m_strKcode = strKcode;
    }

    /**
     * 顧客名の取得
     *
     * @return  String  顧客コード
     */
    public String getName(){
        return m_strName;
    }

    /**
     * 顧客コードの設定
     *
     * @param strName   [in] String 顧客コード
     */
    public void setName(String strName){
        m_strName = strName;
    }

    /**
     * 今回指針の取得
     *
     * @return  int 今回指針
     */
    public int getSs(){
        return m_nSs;
    }

    /**
     * 今回指針の設定
     *
     * @param nSs   [in] int    今回指針
     */
    public void setSs(int nSs){
        m_nSs = nSs;
    }

    /**
     * 今回使用量の取得
     *
     * @return  int 今回使用量
     */
    public int getSr(){
        return m_nSr;
    }

    /**
     * 今回使用量の設定
     *
     * @param nSr   [in] int    今回使用量
     */
    public void setSr(int nSr){
        m_nSr = nSr;
    }

    /**
     * 今回使用量を加算
     *
     * @param nSr   [in] int    今回使用量
     */
    public void addSr(int nSr){
        m_nSr += nSr;
    }

    /**
     * 金額の取得
     *
     * @return  long    金額
     */
    public long getKin(){
        return m_nKin;
    }

    /**
     * 金額の設定
     *
     * @param nKin  [in] long   金額
     */
    public void setKin(long nKin){
        m_nKin = nKin;
    }

    /**
     * 金額の加算
     *
     * @param nKin  [in] long   金額
     */
    public void addKin(long nKin){
        m_nKin += nKin;
    }

    /**
     * 消費税金額の取得
     *
     * @return  long    消費税金額
     */
    public long getTax(){
        return m_nTax;
    }

    /**
     * 消費税金額の設定
     *
     * @param nTax  [in] long   消費税金額
     */
    public void setTax(long nTax){
        m_nTax = nTax;
    }

    /**
     * 消費税金額の加算
     *
     * @param nTax  [in] long   消費税金額
     */
    public void addTax(long nTax){
        m_nTax += nTax;
    }

    /**
     * 還元額の取得
     *
     * @return  long    還元額
     */
    public long getKng(){
        return m_nKng;
    }

    /**
     * 還元額の設定
     *
     * @param nKng  [in] long   還元額
     */
    public void setKng(long nKng){
        m_nKng = nKng;
    }

    /**
     * 還元額の加算
     *
     * @param nKng  [in] long   還元額
     */
    public void addKng(long nKng){
        m_nKng += nKng;
    }

    /**
     * 灯油指針の取得.
     *
     * @return  int 灯油指針
     */
    public int getToyuSs(){
        return m_nToyuSs;
    }

    /**
     * 灯油指針の設定.
     *
     * @param nToyuSs   [in] int    灯油指針
     */
    public void setToyuSs(int nToyuSs){
        m_nToyuSs = nToyuSs;
    }

    /**
     * 灯油使用量の取得.
     *
     * @return  int 灯油使用量
     */
    public int getToyuSr(){
        return m_nToyuSr;
    }

    /**
     * 灯油使用量の加算.
     *
     * @param nToyuSr   [in] int    灯油使用量
     */
    public void addToyuSr(int nToyuSr){
        m_nToyuSr += nToyuSr;
    }

    /**
     * 灯油使用量の設定.
     *
     * @param nToyuSr   [in] int    灯油使用量
     */
    public void setToyuSr(int nToyuSr){
        m_nToyuSr = nToyuSr;
    }

    /**
     * 灯油金額の取得.
     *
     * @return  long    灯油金額
     */
    public long getToyuKin(){
        return m_lToyuKin;
    }

    /**
     * 灯油金額の設定.
     *
     * @param lToyuKin  [in] long   灯油金額
     */
    public void setToyuKin(long lToyuKin){
        m_lToyuKin = lToyuKin;
    }

    /**
     * 灯油金額の加算.
     *
     * @param lToyuKin  [in] long   灯油金額
     */
    public void addToyuKin(long lToyuKin){
        m_lToyuKin += lToyuKin;
    }

    /**
     * 灯油消費税の取得.
     *
     * @return  long    灯油消費税
     */
    public long getToyuTax(){
        return m_lToyuTax;
    }

    /**
     * 灯油消費税の設定.
     *
     * @param lToyuTax  [in] long   灯油消費税
     */
    public void setToyuTax(long lToyuTax){
        m_lToyuTax = lToyuTax;
    }

    /**
     * 灯油消費税の加算.
     *
     * @param lToyuTax  [in] long   灯油消費税
     */
    public void addToyuTax(long lToyuTax){
        m_lToyuTax += lToyuTax;
    }

    /**
     * 灯油検針有無の取得.
     *
     * @return  boolean true: 灯油検針有り, false: 灯油検針無し
     */
    public boolean isToyu(){
        return m_isToyu;
    }

    /**
     * 灯油検針有無の設定.
     *
     * @param isToyu    [in] boolean    true: 灯油検針有り, false: 灯油検針無し
     */
    public void setToyu(boolean isToyu){
        m_isToyu = isToyu;
    }

    /**
     * ガス検針有無の取得.
     *
     * @return  boolean true: ガス検針有り/ false: ガス検針無し
     */
    public boolean isKensin(){
        return m_isKensin;
    }

    /**
     * ガス検針有無の設定.
     *
     * @param isKensin  [in] boolean    true:ガス検針有り/ false: ガス検針無し
     */
    public void setKensin(boolean isKensin){
        m_isKensin = isKensin;
    }

    /**
     * 入金額の取得.
     *
     * @return  long    入金額
     */
    public long getNyu(){
        return m_lNyu;
    }

    /**
     * 入金額の設定.
     *
     * @param lNyu  [in] long   入金額
     */
    public void setNyu(long lNyu){
        m_lNyu = lNyu;
    }

    /**
     * 入金額の加算.
     *
     * @param lNyu  [in] long   入金額
     */
    public void addNyu(long lNyu){
        m_lNyu += lNyu;
    }

    /**
     * 調整額の取得.
     *
     * @return  long    調整額
     */
    public long getCho(){
        return m_lCho;
    }

    /**
     * 調整額の設定.
     *
     * @param lCho  [in] long   調整額
     */
    public void setCho(long lCho){
        m_lCho = lCho;
    }

    /**
     * 調整額の加算.
     *
     * @param lCho  [in] long   調整額
     */
    public void addCho(long lCho){
        m_lCho += lCho;
    }

    /**
     * 集計用データ加算
     *
     * @param kensinData    [in] ShukeiKensindata   集計用データ
     */
    public void addData(ShukeiKensinData kensinData){
        addSr(kensinData.getSr());
        addKin(kensinData.getKin());
        addTax(kensinData.getTax());
        addKng(kensinData.getKng());
        addToyuSr(kensinData.getToyuSr());
        addToyuKin(kensinData.getToyuKin());
        addToyuTax(kensinData.getToyuTax());
        addNyu(kensinData.getNyu());
        addCho(kensinData.getCho());
    }
}
