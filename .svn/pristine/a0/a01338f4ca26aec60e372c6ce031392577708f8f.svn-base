
package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import jp.co.MarutouCompack.commonClass.PrinterDat.CnpCusDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.HybfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.Sy2fCnpDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.Sy2fCnpTempDat;

/**
 * 販売店データクラス.
 */
public class KensinData {
    /** 今回指針 */
    @SerializedName("m_Sisin")
    private int m_Sisin;
    /** 前回検針月 */
    @SerializedName("m_KensinPrevMonth")
    private int m_KensinPrevMonth;
    /** 前回検針日 */
    @SerializedName("m_KensinPrevDay")
    private int m_KensinPrevDay;
    /** 前回指針 */
    @SerializedName("m_SisinPrev")
    private int m_SisinPrev;
    /** 今回使用量 */
    @SerializedName("m_NowUse")
    private int m_NowUse;
    /** 前回使用量 */
    @SerializedName("m_PreUse")
    private int m_PreUse;
    /** ガス売上 */
    @SerializedName("m_GasPay")
    private int m_GasPay;
    /** ガス消費税 */
    @SerializedName("m_GasTax")
    private int m_GasTax;
    /** 調整費 */
    @SerializedName("m_Reduce")
    private int m_Reduce;
    /** メーター取替有無 */
    @SerializedName("m_bChgMeter")
    private boolean m_bChgMeter;
    /** メーター取替月 */
    @SerializedName("m_ChgMonth")
    private int m_ChgMonth;
    /** メーター取替日 */
    @SerializedName("m_ChgDay")
    private int m_ChgDay;
    /** メーター取替前回指針 */
    @SerializedName("m_ChgZsisin")
    private int m_ChgZsisin;
    /** メーター取り外し指針 */
    @SerializedName("m_ChgSisin")
    private int m_ChgSisin;
    /** 中間使用量 */
    @SerializedName("m_ChukanSur")
    private int m_ChukanSur;
    /** 前月残高 */
    @SerializedName("m_PreReceipt")
    private long m_PreReceipt;
    /** 前残印字抑制フラグ */
    @SerializedName("m_isFuriDemand")
    private boolean m_isFuriDemand;
    /** 本日売上 */
    @SerializedName("m_HmDay")
    private int m_HmDay;
    /** 当月売上 */
    @SerializedName("m_HmMonth")
    private int m_HmMonth;
    /** 今回請求額 */
    @SerializedName("m_Receipt")
    private long m_Receipt;
    /** 預かり金 */
    @SerializedName("m_Azukarikin")
    private int m_Azukarikin;
    /** 入金 */
    @SerializedName("m_Nyukin")
    private int m_Nyukin;
    /** 調整 */
    @SerializedName("m_Chosei")
    private int m_Chosei;
    /** 残高 */
    @SerializedName("m_Zandaka")
    private long m_Zandaka = 0;
    /** CNポイント使用フラグ */
    @SerializedName("mCnp")
    private boolean mCnp;
    /** CNポイントデータ */
    @SerializedName("mCnpCusDat")
    private CnpCusDat mCnpCusDat;
    /** CNポイント本会員用コメント */
    @SerializedName("mCnpMemberCmt")
    private Sy2fCnpDat mCnpMemberCmt;
    /** CNポイント仮会員用コメント */
    @SerializedName("mCnpTempCmt")
    private Sy2fCnpTempDat mCnpTempCmt;
    /** 前回使用量印字フラグ */
    @SerializedName("mPrnZensr")
    private boolean mPrnZensr;
    /** ガス基本料金印字フラグ */
    @SerializedName("mPrnGasBaseKin")
    private boolean mPrnGasBaseKin = false;
    /** ガス基本料金 */
    @SerializedName("mGasBaseKin")
    private long mGasBaseKin = 0L;
    /** ガス契約単価 */
    @SerializedName("mGasAddKin")
    private long mGasAddKin = 0L;
    /** 前年同月使用量 */
    @SerializedName("m_nZenYearKenSr")
    private int m_nZenYearKenSr = 0;
    /** ガス料金総額 */
    @SerializedName("m_nGasTotalKin")
    private long m_nGasTotalKin = 0L;
    /** ガス料金総額(税抜き) */
    @SerializedName("m_nGasTotalKinWithoutTax")
    private long m_nGasTotalKinWithoutTax = 0L;
    /** 設備料金 */
    @SerializedName("m_nFacilityKin")
    private long m_nFacilityKin = 0L;
    /** ガス料金総額印字フラグ */
    @SerializedName("m_bPrintGasRyokinTotal")
    private boolean m_bPrintGasRyokinTotal = false;
    /** ガス料金式印字フラグ */
    @SerializedName("m_bPrintGasRyokinSiki")
    private boolean m_bPrintGasRyokinSiki = false;
    /** 設備料金印字フラグ */
    @SerializedName("m_bPrintGasFacilityKin")
    private boolean m_bPrintGasFacilityKin = false;
    /** 日割りコメント印字フラグ */
    @SerializedName("m_bPrintHiwariComment")
    private boolean m_bPrintHiwariComment = false;
    /** 前年同月使用量印字フラグ */
    @SerializedName("m_bPrintZenYearKenSr")
    private boolean m_bPrintZenYearKenSr = false;
    /** 日割りコメント１ */
    @SerializedName("m_strHiwariComment_0")
    private String m_strHiwariComment_0 = null;
    /** 日割りコメント２ */
    @SerializedName("m_strHiwariComment_1")
    private String m_strHiwariComment_1 = null;
    /** ガス料金データ */
    @SerializedName("m_GasfDat")
    private GasfDat m_GasfDat;
    /** ガス料金表開始インデックス */
    @SerializedName("m_nStartIdx")
    private int m_nStartIdx = 0;
    /** 一律料金フラグ */
    @SerializedName("m_bSingleStep")
    private boolean m_bSingleStep = false;
    /** 伝票印字パターン */
    @SerializedName("m_nPrintGasRyokinSikiPtn")
    private int m_nPrintGasRyokinSikiPtn = 0;
    /** ハイブリッド料金データ */
    @SerializedName("mHybfDat")
    private HybfDat mHybfDat = null;
    /** ハイブリッドカウンタ―名称 */
    @SerializedName("mCounterName")
    private String[] mCounterName = null;
    /** ハイブリッドフラグ */
    @SerializedName("m_isHybrid")
    private boolean m_isHybrid;
    /** 顧客ハイブリッドデータ */
    @SerializedName("mKo2fDat")
    private Ko2fDat mKo2fDat = null;
    /** ガス料金のみ */
    @SerializedName("m_nOnlyGas")
    private int m_nOnlyGas;
    /** ガス料金透明化フラグ */
    @SerializedName("m_isVisibleGas")
    private boolean m_isVisibleGas;
    /** 通常使用量 */
    @SerializedName("m_nNorSr")
    private int m_nNorSr = 0;
    /** ハイブリッドガス使用量 */
    @SerializedName("m_nHybGasUse")
    private int[] m_nHybGasUse = null;
    /** 顧客灯油データ */
    @SerializedName("mKotfDat")
    private KotfDat mKotfDat;
    /** 灯油料金分割印字 */
    @SerializedName("m_isToyuKinSep")
    private boolean m_isToyuKinSep;
    /** 検針のみ印刷 */
    @SerializedName("m_isPrintKensin")
    private boolean m_isPrintKensin;
    /** 灯油のみ印刷 */
    @SerializedName("m_isPrintToyu")
    private boolean m_isPrintToyu;
    /** 灯油単価 */
    @SerializedName("m_nLoilUnit")
    private int m_nLoilUnit;
    /** 前月ご請求額タイトル */
    @SerializedName("m_strZanTitle")
    private String m_strZanTitle = "前月御請求額";
    /** 当月入金額 */
    @SerializedName("m_nTReceipt")
    private int m_nTReceipt = 0;
    /** 当月調整額 */
    @SerializedName("m_nTAdjust")
    private int m_nTAdjust = 0;
    @SerializedName("m_strIrai")
    private String m_strIrai;

    public KensinData(int m_Sisin, int m_KensinPrevMonth, int m_KensinPrevDay, int m_SisinPrev, int m_NowUse, int m_PreUse, int m_GasPay, int m_GasTax, int m_Reduce, boolean m_bChgMeter, int m_ChgMonth, int m_ChgDay, int m_ChgZsisin, int m_ChgSisin, int m_ChukanSur, long m_PreReceipt, boolean m_isFuriDemand, int m_HmDay, int m_HmMonth, long m_Receipt, int m_Azukarikin, int m_Nyukin, int m_Chosei, long m_Zandaka, boolean mCnp, CnpCusDat mCnpCusDat, Sy2fCnpDat mCnpMemberCmt, Sy2fCnpTempDat mCnpTempCmt, boolean mPrnZensr, boolean mPrnGasBaseKin, long mGasBaseKin, long mGasAddKin, int m_nZenYearKenSr, long m_nGasTotalKin, long m_nGasTotalKinWithoutTax, long m_nFacilityKin, boolean m_bPrintGasRyokinTotal, boolean m_bPrintGasRyokinSiki, boolean m_bPrintGasFacilityKin, boolean m_bPrintHiwariComment, boolean m_bPrintZenYearKenSr, String m_strHiwariComment_0, String m_strHiwariComment_1, GasfDat m_GasfDat, int m_nStartIdx, boolean m_bSingleStep, int m_nPrintGasRyokinSikiPtn, HybfDat mHybfDat, String[] mCounterName, boolean m_isHybrid, Ko2fDat mKo2fDat, int m_nOnlyGas, boolean m_isVisibleGas, int m_nNorSr, int[] m_nHybGasUse, KotfDat mKotfDat, boolean m_isToyuKinSep, boolean m_isPrintKensin, boolean m_isPrintToyu, int m_nLoilUnit, String m_strZanTitle, int m_nTReceipt, int m_nTAdjust, String m_strIrai) {
        this.m_Sisin = m_Sisin;
        this.m_KensinPrevMonth = m_KensinPrevMonth;
        this.m_KensinPrevDay = m_KensinPrevDay;
        this.m_SisinPrev = m_SisinPrev;
        this.m_NowUse = m_NowUse;
        this.m_PreUse = m_PreUse;
        this.m_GasPay = m_GasPay;
        this.m_GasTax = m_GasTax;
        this.m_Reduce = m_Reduce;
        this.m_bChgMeter = m_bChgMeter;
        this.m_ChgMonth = m_ChgMonth;
        this.m_ChgDay = m_ChgDay;
        this.m_ChgZsisin = m_ChgZsisin;
        this.m_ChgSisin = m_ChgSisin;
        this.m_ChukanSur = m_ChukanSur;
        this.m_PreReceipt = m_PreReceipt;
        this.m_isFuriDemand = m_isFuriDemand;
        this.m_HmDay = m_HmDay;
        this.m_HmMonth = m_HmMonth;
        this.m_Receipt = m_Receipt;
        this.m_Azukarikin = m_Azukarikin;
        this.m_Nyukin = m_Nyukin;
        this.m_Chosei = m_Chosei;
        this.m_Zandaka = m_Zandaka;
        this.mCnp = mCnp;
        this.mCnpCusDat = mCnpCusDat;
        this.mCnpMemberCmt = mCnpMemberCmt;
        this.mCnpTempCmt = mCnpTempCmt;
        this.mPrnZensr = mPrnZensr;
        this.mPrnGasBaseKin = mPrnGasBaseKin;
        this.mGasBaseKin = mGasBaseKin;
        this.mGasAddKin = mGasAddKin;
        this.m_nZenYearKenSr = m_nZenYearKenSr;
        this.m_nGasTotalKin = m_nGasTotalKin;
        this.m_nGasTotalKinWithoutTax = m_nGasTotalKinWithoutTax;
        this.m_nFacilityKin = m_nFacilityKin;
        this.m_bPrintGasRyokinTotal = m_bPrintGasRyokinTotal;
        this.m_bPrintGasRyokinSiki = m_bPrintGasRyokinSiki;
        this.m_bPrintGasFacilityKin = m_bPrintGasFacilityKin;
        this.m_bPrintHiwariComment = m_bPrintHiwariComment;
        this.m_bPrintZenYearKenSr = m_bPrintZenYearKenSr;
        this.m_strHiwariComment_0 = m_strHiwariComment_0;
        this.m_strHiwariComment_1 = m_strHiwariComment_1;
        this.m_GasfDat = m_GasfDat;
        this.m_nStartIdx = m_nStartIdx;
        this.m_bSingleStep = m_bSingleStep;
        this.m_nPrintGasRyokinSikiPtn = m_nPrintGasRyokinSikiPtn;
        this.mHybfDat = mHybfDat;
        this.mCounterName = mCounterName;
        this.m_isHybrid = m_isHybrid;
        this.mKo2fDat = mKo2fDat;
        this.m_nOnlyGas = m_nOnlyGas;
        this.m_isVisibleGas = m_isVisibleGas;
        this.m_nNorSr = m_nNorSr;
        this.m_nHybGasUse = m_nHybGasUse;
        this.mKotfDat = mKotfDat;
        this.m_isToyuKinSep = m_isToyuKinSep;
        this.m_isPrintKensin = m_isPrintKensin;
        this.m_isPrintToyu = m_isPrintToyu;
        this.m_nLoilUnit = m_nLoilUnit;
        this.m_strZanTitle = m_strZanTitle;
        this.m_nTReceipt = m_nTReceipt;
        this.m_nTAdjust = m_nTAdjust;
        this.m_strIrai = m_strIrai;
    }

    public int getSisin() {
        return m_Sisin;
    }

    public void setSisin(int m_Sisin) {
        this.m_Sisin = m_Sisin;
    }

    public int getKensinPrevMonth() {
        return m_KensinPrevMonth;
    }

    public void setKensinPrevMonth(int m_KensinPrevMonth) {
        this.m_KensinPrevMonth = m_KensinPrevMonth;
    }

    public int getKensinPrevDay() {
        return m_KensinPrevDay;
    }

    public void setKensinPrevDay(int m_KensinPrevDay) {
        this.m_KensinPrevDay = m_KensinPrevDay;
    }

    public int getSisinPrev() {
        return m_SisinPrev;
    }

    public void setSisinPrev(int m_SisinPrev) {
        this.m_SisinPrev = m_SisinPrev;
    }

    public int getNowUse() {
        return m_NowUse;
    }

    public void setNowUse(int m_NowUse) {
        this.m_NowUse = m_NowUse;
    }

    public int getPreUse() {
        return m_PreUse;
    }

    public void setPreUse(int m_PreUse) {
        this.m_PreUse = m_PreUse;
    }

    public int getGasPay() {
        return m_GasPay;
    }

    public void setGasPay(int m_GasPay) {
        this.m_GasPay = m_GasPay;
    }

    public int getGasTax() {
        return m_GasTax;
    }

    public void setGasTax(int m_GasTax) {
        this.m_GasTax = m_GasTax;
    }

    public int getReduce() {
        return m_Reduce;
    }

    public void setReduce(int m_Reduce) {
        this.m_Reduce = m_Reduce;
    }

    public boolean isChgMeter() {
        return m_bChgMeter;
    }

    public void setChgMeter(boolean m_bChgMeter) {
        this.m_bChgMeter = m_bChgMeter;
    }

    public int getChgMonth() {
        return m_ChgMonth;
    }

    public void setM_ChgMonth(int m_ChgMonth) {
        this.m_ChgMonth = m_ChgMonth;
    }

    public int getChgDay() {
        return m_ChgDay;
    }

    public void setChgDay(int m_ChgDay) {
        this.m_ChgDay = m_ChgDay;
    }

    public int getChgZsisin() {
        return m_ChgZsisin;
    }

    public void setChgZsisin(int m_ChgZsisin) {
        this.m_ChgZsisin = m_ChgZsisin;
    }

    public int getChgSisin() {
        return m_ChgSisin;
    }

    public void setChgSisin(int m_ChgSisin) {
        this.m_ChgSisin = m_ChgSisin;
    }

    public int getChukanSur() {
        return m_ChukanSur;
    }

    public void setChukanSur(int m_ChukanSur) {
        this.m_ChukanSur = m_ChukanSur;
    }

    public long getPreReceipt() {
        return m_PreReceipt;
    }

    public void setPreReceipt(long m_PreReceipt) {
        this.m_PreReceipt = m_PreReceipt;
    }

    public boolean isFuriDemand() {
        return m_isFuriDemand;
    }

    public void isFuriDemand(boolean m_isFuriDemand) {
        this.m_isFuriDemand = m_isFuriDemand;
    }

    public int getHmDay() {
        return m_HmDay;
    }

    public void setHmDay(int m_HmDay) {
        this.m_HmDay = m_HmDay;
    }

    public int getHmMonth() {
        return m_HmMonth;
    }

    public void setHmMonth(int m_HmMonth) {
        this.m_HmMonth = m_HmMonth;
    }

    public long getReceipt() {
        return m_Receipt;
    }

    public void setReceipt(long m_Receipt) {
        this.m_Receipt = m_Receipt;
    }

    public int getAzukarikin() {
        return m_Azukarikin;
    }

    public void setAzukarikin(int m_Azukarikin) {
        this.m_Azukarikin = m_Azukarikin;
    }

    public int getNyukin() {
        return m_Nyukin;
    }

    public void setNyukin(int m_Nyukin) {
        this.m_Nyukin = m_Nyukin;
    }

    public int getChosei() {
        return m_Chosei;
    }

    public void setChosei(int m_Chosei) {
        this.m_Chosei = m_Chosei;
    }

    public long getZandaka() {
        return m_Zandaka;
    }

    public void setZandaka(long m_Zandaka) {
        this.m_Zandaka = m_Zandaka;
    }

    public boolean isCnp() {
        return mCnp;
    }

    public void setCnp(boolean mCnp) {
        this.mCnp = mCnp;
    }

    public CnpCusDat getCnpCusDat() {
        return mCnpCusDat;
    }

    public void setCnpCusDat(CnpCusDat mCnpCusDat) {
        this.mCnpCusDat = mCnpCusDat;
    }

    public Sy2fCnpDat getCnpMemberCmt() {
        return mCnpMemberCmt;
    }

    public void setCnpMemberCmt(Sy2fCnpDat mCnpMemberCmt) {
        this.mCnpMemberCmt = mCnpMemberCmt;
    }

    /**
     * CNポイント用コメントの取得
     *
     * @return  List<String>    CNポイント用コメント
     */
    public List<String> getCnpCmt(){
        List<String> lstCnpCmt = new ArrayList<>();
        if(mCnp){
            if(mCnpCusDat.getCnpMembers() > 0){
                // 本会員用コメント
                if(mCnpMemberCmt.mCnpComment_0.trim().length() != 0){
                    lstCnpCmt.add(mCnpMemberCmt.mCnpComment_0);
                }
                if(mCnpMemberCmt.mCnpComment_1.trim().length() != 0){
                    lstCnpCmt.add(mCnpMemberCmt.mCnpComment_1);
                }
                if(mCnpMemberCmt.mCnpComment_2.trim().length() != 0){
                    lstCnpCmt.add(mCnpMemberCmt.mCnpComment_2);
                }
            }
            else if(mCnpCusDat.getCnpTemp() > 0){
                // 仮会員用コメント
                if(mCnpTempCmt.getCnpComment_0().trim().length() != 0){
                    lstCnpCmt.add(mCnpTempCmt.getCnpComment_0());
                }
                if(mCnpTempCmt.getCnpComment_1().trim().length() != 0){
                    lstCnpCmt.add(mCnpTempCmt.getCnpComment_1());
                }
                if(mCnpTempCmt.getCnpComment_2().trim().length() != 0){
                    lstCnpCmt.add(mCnpTempCmt.getCnpComment_2());
                }
            }
        }
        return lstCnpCmt;
    }

    public List<String> getCnpTempCmt() {
        List<String> lstCmt = new ArrayList<>();
        if(mCnp && mCnpCusDat.getCnpTemp() > 0){
            if(!mCnpTempCmt.getCnpComment_0().trim().isEmpty()){
                lstCmt.add(mCnpTempCmt.getCnpComment_0());
            }
            if(!mCnpTempCmt.getCnpComment_1().trim().isEmpty()){
                lstCmt.add(mCnpTempCmt.getCnpComment_1());
            }
        }
        return lstCmt;
    }

    public void setCnpTempCmt(Sy2fCnpTempDat mCnpTempCmt) {
        this.mCnpTempCmt = mCnpTempCmt;
    }

    public boolean isPrnZensr() {
        return mPrnZensr;
    }

    public void setPrnZensr(boolean mPrnZensr) {
        this.mPrnZensr = mPrnZensr;
    }

    public boolean isPrnGasBaseKin() {
        return mPrnGasBaseKin;
    }

    public void setPrnGasBaseKin(boolean mPrnGasBaseKin) {
        this.mPrnGasBaseKin = mPrnGasBaseKin;
    }

    public long getGasBaseKin() {
        return mGasBaseKin;
    }

    public void setGasBaseKin(long mGasBaseKin) {
        this.mGasBaseKin = mGasBaseKin;
    }

    public long getGasAddKin() {
        return mGasAddKin;
    }

    public void setGasAddKin(long mGasAddKin) {
        this.mGasAddKin = mGasAddKin;
    }

    public int getZenYearKenSr() {
        return m_nZenYearKenSr;
    }

    public void setZenYearKenSr(int m_nZenYearKenSr) {
        this.m_nZenYearKenSr = m_nZenYearKenSr;
    }

    public long getGasTotalKin() {
        return m_nGasTotalKin;
    }

    public void setGasTotalKin(long m_nGasTotalKin) {
        this.m_nGasTotalKin = m_nGasTotalKin;
    }

    public long getGasTotalKinWithoutTax() {
        return m_nGasTotalKinWithoutTax;
    }

    public void setGasTotalKinWithoutTax(long m_nGasTotalKinWithoutTax) {
        this.m_nGasTotalKinWithoutTax = m_nGasTotalKinWithoutTax;
    }

    public long getFacilityKin() {
        return m_nFacilityKin;
    }

    public void setFacilityKin(long m_nFacilityKin) {
        this.m_nFacilityKin = m_nFacilityKin;
    }

    public boolean isPrintGasRyokinTotal() {
        return m_bPrintGasRyokinTotal;
    }

    public void setPrintGasRyokinTotal(boolean m_bPrintGasRyokinTotal) {
        this.m_bPrintGasRyokinTotal = m_bPrintGasRyokinTotal;
    }

    public boolean isPrintGasRyokinSiki() {
        return m_bPrintGasRyokinSiki;
    }

    public void setPrintGasRyokinSiki(boolean m_bPrintGasRyokinSiki) {
        this.m_bPrintGasRyokinSiki = m_bPrintGasRyokinSiki;
    }

    public boolean isPrintGasFacilityKin() {
        return m_bPrintGasFacilityKin;
    }

    public void setPrintGasFacilityKin(boolean m_bPrintGasFacilityKin) {
        this.m_bPrintGasFacilityKin = m_bPrintGasFacilityKin;
    }

    public boolean isPrintHiwariComment() {
        return m_bPrintHiwariComment;
    }

    public void setPrintHiwariComment(boolean m_bPrintHiwariComment) {
        this.m_bPrintHiwariComment = m_bPrintHiwariComment;
    }

    public boolean isPrintZenYearKenSr() {
        return m_bPrintZenYearKenSr;
    }

    public void setPrintZenYearKenSr(boolean m_bPrintZenYearKenSr) {
        this.m_bPrintZenYearKenSr = m_bPrintZenYearKenSr;
    }

    public String getHiwariComment_0() {
        return m_strHiwariComment_0;
    }

    public void setHiwariComment_0(String m_strHiwariComment_0) {
        this.m_strHiwariComment_0 = m_strHiwariComment_0;
    }

    public String getHiwariComment_1() {
        return m_strHiwariComment_1;
    }

    public void setHiwariComment_1(String m_strHiwariComment_1) {
        this.m_strHiwariComment_1 = m_strHiwariComment_1;
    }

    public GasfDat getGasfDat() {
        return m_GasfDat;
    }

    public void setGasfDat(GasfDat m_GasfDat) {
        this.m_GasfDat = m_GasfDat;
    }

    public int getStartIdx() {
        return m_nStartIdx;
    }

    public void setStartIdx(int m_nStartIdx) {
        this.m_nStartIdx = m_nStartIdx;
    }

    public boolean isSingleStep() {
        return m_bSingleStep;
    }

    public void setSingleStep(boolean m_bSingleStep) {
        this.m_bSingleStep = m_bSingleStep;
    }

    public int getPrintGasRyokinSikiPtn() {
        return m_nPrintGasRyokinSikiPtn;
    }

    public void setPrintGasRyokinSikiPtn(int m_nPrintGasRyokinSikiPtn) {
        this.m_nPrintGasRyokinSikiPtn = m_nPrintGasRyokinSikiPtn;
    }

    public HybfDat getHybfDat() {
        return mHybfDat;
    }

    public void setHybfDat(HybfDat mHybfDat) {
        this.mHybfDat = mHybfDat;
    }

    public String[] getCounterName() {
        return mCounterName;
    }

    public void setCounterName(String[] mCounterName) {
        this.mCounterName = mCounterName;
    }

    public boolean isHybrid() {
        return m_isHybrid;
    }

    public void setHybrid(boolean m_isHybrid) {
        this.m_isHybrid = m_isHybrid;
    }

    public Ko2fDat getKo2fDat() {
        return mKo2fDat;
    }

    public void setKo2fDat(Ko2fDat mKo2fDat) {
        this.mKo2fDat = mKo2fDat;
    }

    public int getOnlyGas() {
        return m_nOnlyGas;
    }

    public void setOnlyGas(int m_nOnlyGas) {
        this.m_nOnlyGas = m_nOnlyGas;
    }

    public boolean isVisibleGas() {
        return m_isVisibleGas;
    }

    public void setVisibleGas(boolean m_isVisibleGas) {
        this.m_isVisibleGas = m_isVisibleGas;
    }

    public int getNorSr() {
        return m_nNorSr;
    }

    public void setNorSr(int m_nNorSr) {
        this.m_nNorSr = m_nNorSr;
    }

    public int[] getHybGasUse() {
        return m_nHybGasUse;
    }

    public void setHybGasUse(int[] m_nHybGasUse) {
        this.m_nHybGasUse = m_nHybGasUse;
    }

    public KotfDat getKotfDat() {
        return mKotfDat;
    }

    public void setKotfDat(KotfDat mKotfDat) {
        this.mKotfDat = mKotfDat;
    }

    public boolean isToyuKinSep() {
        return m_isToyuKinSep;
    }

    public void setToyuKinSep(boolean m_isToyuKinSep) {
        this.m_isToyuKinSep = m_isToyuKinSep;
    }

    public boolean isPrintKensin() {
        return m_isPrintKensin;
    }

    public void setPrintKensin(boolean m_isPrintKensin) {
        this.m_isPrintKensin = m_isPrintKensin;
    }

    public boolean isPrintToyu() {
        return m_isPrintToyu;
    }

    public void setPrintToyu(boolean m_isPrintToyu) {
        this.m_isPrintToyu = m_isPrintToyu;
    }

    public int getLoilUnit() {
        return m_nLoilUnit;
    }

    public void setLoilUnit(int m_nLoilUnit) {
        this.m_nLoilUnit = m_nLoilUnit;
    }

    public String getZanTitle() {
        return m_strZanTitle;
    }

    public void setZanTitle(String m_strZanTitle) {
        this.m_strZanTitle = m_strZanTitle;
    }

    public int getTReceipt() {
        return m_nTReceipt;
    }

    public void setTReceipt(int m_nTReceipt) {
        this.m_nTReceipt = m_nTReceipt;
    }

    public int getTAdjust() {
        return m_nTAdjust;
    }

    public void setTAdjust(int m_nTAdjust) {
        this.m_nTAdjust = m_nTAdjust;
    }

    public String getIrai() {
        return m_strIrai;
    }

    public void setIrai(String m_strIrai) {
        this.m_strIrai = m_strIrai;
    }
}