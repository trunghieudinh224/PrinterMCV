
package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;
import jp.co.MarutouCompack.commonClass.PrinterDat.GtpcDat;

/**
 * システム情報データ.
 */
public class SysfDat{

    /** 処理日付(日) */
    @SerializedName("mDate")
    private byte mDate;
    /** システム年 */
    @SerializedName("mSysYear")
    private short mSysYear;
    /** システム月 */
    @SerializedName("mSysMonth")
    private byte mSysMonth;
    /** 消費税率 */
    @SerializedName("mConsumTax")
    private short mConsumTax;
    /** 消費税:端数処理(加算) */
    @SerializedName("mFracAddTax")
    private long mFracAddTax;
    /** 消費税:端数処理(乗算) */
    @SerializedName("mFracMulTax")
    private long mFracMulTax;
    /** 入力有無:保安点検 */
    @SerializedName("mCheckHoan")
    private boolean mCheckHoan;
    /** 入力有無:入金 */
    @SerializedName("mIfMoney")
    private boolean mIfMoney;
    /** 差益還元:有無 */
    @SerializedName("mIfReduce")
    private boolean mIfReduce;
    /** 伝票出力フラグ:ユーザー名(店舗名) */
    @SerializedName("mIfChitUser")
    private boolean mIfChitUser;
    /** 伝票出力フラグ:警報機リース */
    @SerializedName("mIfAlarm")
    private boolean mIfAlarm;
    /** 伝票出力フラグ:分割金 */
    @SerializedName("mIfDiv")
    private boolean mIfDiv;
    /** 伝票出力フラグ:その他売上 */
    @SerializedName("mIfProceeds")
    private boolean mIfProceeds;
    /** 伝票出力フラグ:前月請求額 */
    @SerializedName("mIfDemand")
    private boolean mIfDemand;
    /** 伝票出力フラグ:入金・調整 */
    @SerializedName("mIfAdjust")
    private boolean mIfAdjust;
    /** 伝票出力フラグ:灯油 */
    @SerializedName("mIfLampoil")
    private boolean mIfLampoil;
    /** 産気率 */
    @SerializedName("mSanki")
    private short mSanki;
    /** 使用率チェック:使用率 */
    @SerializedName("mSrChkm")
    private short[] mSrChkm;
    /** 使用率チェック:倍率 */
    @SerializedName("mSrChkr")
    private short[] mSrChkr;
    /** 売上用端数処理:加算 */
    @SerializedName("mFracAddKin")
    private long mFracAddKin;
    /** 売上用端数処理:乗算 */
    @SerializedName("mFracMulKin")
    private long mFracMulKin;
    /** オプションフラグ */
    @SerializedName("mHtOption")
    private byte[] mHtOption;
    /** 灯油品目コード */
    @SerializedName("mHinCd9")
    private short mHinCd9;
    /** リース計上機能有無 */
    @SerializedName("mLesUmu")
    private byte mLesUmu;
    /** 管ガス:最低検針日数 */
    @SerializedName("mKgasDays0")
    private short mKgasDays0;
    /** 管ガス:最大検針日数 */
    @SerializedName("mKgasDays1")
    private short mKgasDays1;
    /** 管ガス:閉開栓時日数 */
    @SerializedName("mKgasDays2")
    private short mKgasDays2;
    /** 商品消費税の使用依頼 */
    @SerializedName("mShoTaxcom")
    private byte mShoTaxcom;
    /** コンパックＲより追加　取引区分、商品コード判別用 */
    @SerializedName("mSnvalue")
    private short mSnvalue;
    /** 消費税変更日付 */
    @SerializedName("mTax_yy")
    private short mTax_yy;
    @SerializedName("mTax_mm")
    private byte mTax_mm;
    @SerializedName("mTax_dd")
    private byte mTax_dd;
    /** 消費税変更旧税率 */
    @SerializedName("mTaxr_old")
    private short mTaxr_old;
    @SerializedName("mTaxr_new")
    private short mTaxr_new;
    /** 値引きシステムフラグ */
    @SerializedName("mKnebFlg")
    private byte mKnebFlg;
    /** ガス料金透明化対応フラグ */
    @SerializedName("mVisibleGas")
    private byte mVisibleGas;
    /** ガス料金透明化設備料金対応フラグ */
    @SerializedName("mVisibleFacility")
    private byte mVisibleFacility;
    /** ガス料金透明化設定データ */
    @SerializedName("mGtpcDat")
    private GtpcDat mGtpcDat;
    /** 簡ガス日常点検有無 */
    @SerializedName("mTenkenKgas")
    private byte mTenkenKgas;
    /** 日常点検判定項目△有無 */
    @SerializedName("mTenkenDelta")
    private byte mTenkenDelta;
    /** 灯油検針フラグ */
    @SerializedName("m_isToyukensinFlg")
    private boolean m_isToyukensinFlg = false;
    /** ltasモードフラグ */
    @SerializedName("m_isLtas")
    private boolean m_isLtas = false;
    /** インボイスコメント */
    @SerializedName("m_strInvoiceComment")
    private String m_strInvoiceComment;

//    public mShofDatKangen


    public SysfDat(byte mDate, short mSysYear, byte mSysMonth, short mConsumTax, long mFracAddTax, long mFracMulTax, boolean mCheckHoan,
                   boolean mIfMoney, boolean mIfReduce, boolean mIfChitUser, boolean mIfAlarm, boolean mIfDiv, boolean mIfProceeds,
                   boolean mIfDemand, boolean mIfAdjust, boolean mIfLampoil, short mSanki, short[] mSrChkm, short[] mSrChkr, long mFracAddKin,
                   long mFracMulKin, byte[] mHtOption, short mHinCd9, byte mLesUmu, short mKgasDays0, short mKgasDays1, short mKgasDays2,
                   byte mShoTaxcom, short mSnvalue, short mTax_yy, byte mTax_mm, byte mTax_dd, short mTaxr_old, short mTaxr_new, byte mKnebFlg,
                   byte mVisibleGas, byte mVisibleFacility, GtpcDat mGtpcDat, byte mTenkenKgas, byte mTenkenDelta, boolean m_isToyukensinFlg,
                   boolean m_isLtas, String m_strInvoiceComment) {
        this.mDate = mDate;
        this.mSysYear = mSysYear;
        this.mSysMonth = mSysMonth;
        this.mConsumTax = mConsumTax;
        this.mFracAddTax = mFracAddTax;
        this.mFracMulTax = mFracMulTax;
        this.mCheckHoan = mCheckHoan;
        this.mIfMoney = mIfMoney;
        this.mIfReduce = mIfReduce;
        this.mIfChitUser = mIfChitUser;
        this.mIfAlarm = mIfAlarm;
        this.mIfDiv = mIfDiv;
        this.mIfProceeds = mIfProceeds;
        this.mIfDemand = mIfDemand;
        this.mIfAdjust = mIfAdjust;
        this.mIfLampoil = mIfLampoil;
        this.mSanki = mSanki;
        this.mSrChkm = mSrChkm;
        this.mSrChkr = mSrChkr;
        this.mFracAddKin = mFracAddKin;
        this.mFracMulKin = mFracMulKin;
        this.mHtOption = mHtOption;
        this.mHinCd9 = mHinCd9;
        this.mLesUmu = mLesUmu;
        this.mKgasDays0 = mKgasDays0;
        this.mKgasDays1 = mKgasDays1;
        this.mKgasDays2 = mKgasDays2;
        this.mShoTaxcom = mShoTaxcom;
        this.mSnvalue = mSnvalue;
        this.mTax_yy = mTax_yy;
        this.mTax_mm = mTax_mm;
        this.mTax_dd = mTax_dd;
        this.mTaxr_old = mTaxr_old;
        this.mTaxr_new = mTaxr_new;
        this.mKnebFlg = mKnebFlg;
        this.mVisibleGas = mVisibleGas;
        this.mVisibleFacility = mVisibleFacility;
        this.mGtpcDat = mGtpcDat;
        this.mTenkenKgas = mTenkenKgas;
        this.mTenkenDelta = mTenkenDelta;
        this.m_isToyukensinFlg = m_isToyukensinFlg;
        this.m_isLtas = m_isLtas;
        this.m_strInvoiceComment = m_strInvoiceComment;
    }

    public byte getDate() {
        return mDate;
    }

    public void setDate(byte mDate) {
        this.mDate = mDate;
    }

    public short getSysYear() {
        return mSysYear;
    }

    public void setSysYear(short mSysYear) {
        this.mSysYear = mSysYear;
    }

    public byte getSysMonth() {
        return mSysMonth;
    }

    public void setSysMonth(byte mSysMonth) {
        this.mSysMonth = mSysMonth;
    }

    public short getConsumTax() {
        return mConsumTax;
    }

    public void setConsumTax(short mConsumTax) {
        this.mConsumTax = mConsumTax;
    }

    public long getFracAddTax() {
        return mFracAddTax;
    }

    public void setFracAddTax(long mFracAddTax) {
        this.mFracAddTax = mFracAddTax;
    }

    public long getFracMulTax() {
        return mFracMulTax;
    }

    public void setFracMulTax(long mFracMulTax) {
        this.mFracMulTax = mFracMulTax;
    }

    public boolean isCheckHoan() {
        return mCheckHoan;
    }

    public void setCheckHoan(boolean mCheckHoan) {
        this.mCheckHoan = mCheckHoan;
    }

    public boolean ifMoney() {
        return mIfMoney;
    }

    public void setIfMoney(boolean mIfMoney) {
        this.mIfMoney = mIfMoney;
    }

    public boolean ifReduce() {
        return mIfReduce;
    }

    public void setIfReduce(boolean mIfReduce) {
        this.mIfReduce = mIfReduce;
    }

    public boolean ifChitUser() {
        return mIfChitUser;
    }

    public void setIfChitUser(boolean mIfChitUser) {
        this.mIfChitUser = mIfChitUser;
    }

    public boolean ifAlarm() {
        return mIfAlarm;
    }

    public void setIfAlarm(boolean mIfAlarm) {
        this.mIfAlarm = mIfAlarm;
    }

    public boolean ifDiv() {
        return mIfDiv;
    }

    public void setIfDiv(boolean mIfDiv) {
        this.mIfDiv = mIfDiv;
    }

    public boolean ifProceeds() {
        return mIfProceeds;
    }

    public void setIfProceeds(boolean mIfProceeds) {
        this.mIfProceeds = mIfProceeds;
    }

    public boolean ifDemand() {
        return mIfDemand;
    }

    public void setIfDemand(boolean mIfDemand) {
        this.mIfDemand = mIfDemand;
    }

    public boolean ifAdjust() {
        return mIfAdjust;
    }

    public void setIfAdjust(boolean mIfAdjust) {
        this.mIfAdjust = mIfAdjust;
    }

    public boolean ismIfLampoil() {
        return mIfLampoil;
    }

    public void setIfLampoil(boolean mIfLampoil) {
        this.mIfLampoil = mIfLampoil;
    }

    public short getSanki() {
        return mSanki;
    }

    public void setSanki(short mSanki) {
        this.mSanki = mSanki;
    }

    public short[] getSrChkm() {
        return mSrChkm;
    }

    public void setSrChkm(short[] mSrChkm) {
        this.mSrChkm = mSrChkm;
    }

    public short[] getSrChkr() {
        return mSrChkr;
    }

    public void setSrChkr(short[] mSrChkr) {
        this.mSrChkr = mSrChkr;
    }

    public long getFracAddKin() {
        return mFracAddKin;
    }

    public void setFracAddKin(long mFracAddKin) {
        this.mFracAddKin = mFracAddKin;
    }

    public long getFracMulKin() {
        return mFracMulKin;
    }

    public void setFracMulKin(long mFracMulKin) {
        this.mFracMulKin = mFracMulKin;
    }

    public byte[] getHtOption() {
        return mHtOption;
    }

    public void setHtOption(byte[] mHtOption) {
        this.mHtOption = mHtOption;
    }

    public short getHinCd9() {
        return mHinCd9;
    }

    public void setHinCd9(short mHinCd9) {
        this.mHinCd9 = mHinCd9;
    }

    public byte getLesUmu() {
        return mLesUmu;
    }

    public void setLesUmu(byte mLesUmu) {
        this.mLesUmu = mLesUmu;
    }

    public short getKgasDays0() {
        return mKgasDays0;
    }

    public void setKgasDays0(short mKgasDays0) {
        this.mKgasDays0 = mKgasDays0;
    }

    public short getKgasDays1() {
        return mKgasDays1;
    }

    public void setKgasDays1(short mKgasDays1) {
        this.mKgasDays1 = mKgasDays1;
    }

    public short getKgasDays2() {
        return mKgasDays2;
    }

    public void setKgasDays2(short mKgasDays2) {
        this.mKgasDays2 = mKgasDays2;
    }

    public byte getShoTaxcom() {
        return mShoTaxcom;
    }

    public void setShoTaxcom(byte mShoTaxcom) {
        this.mShoTaxcom = mShoTaxcom;
    }

    public short getSnvalue() {
        return mSnvalue;
    }

    public void setSnvalue(short mSnvalue) {
        this.mSnvalue = mSnvalue;
    }

    public short getTax_yy() {
        return mTax_yy;
    }

    public void setTax_yy(short mTax_yy) {
        this.mTax_yy = mTax_yy;
    }

    public byte getTax_mm() {
        return mTax_mm;
    }

    public void setTax_mm(byte mTax_mm) {
        this.mTax_mm = mTax_mm;
    }

    public byte getTax_dd() {
        return mTax_dd;
    }

    public void setTax_dd(byte mTax_dd) {
        this.mTax_dd = mTax_dd;
    }

    public short getTaxr_old() {
        return mTaxr_old;
    }

    public void setTaxr_old(short mTaxr_old) {
        this.mTaxr_old = mTaxr_old;
    }

    public short getTaxr_new() {
        return mTaxr_new;
    }

    public void setTaxr_new(short mTaxr_new) {
        this.mTaxr_new = mTaxr_new;
    }

    public byte getKnebFlg() {
        return mKnebFlg;
    }

    public void setKnebFlg(byte mKnebFlg) {
        this.mKnebFlg = mKnebFlg;
    }

    public byte getVisibleGas() {
        return mVisibleGas;
    }

    public void setVisibleGas(byte mVisibleGas) {
        this.mVisibleGas = mVisibleGas;
    }

    public byte getVisibleFacility() {
        return mVisibleFacility;
    }

    public void setVisibleFacility(byte mVisibleFacility) {
        this.mVisibleFacility = mVisibleFacility;
    }

    public GtpcDat getGtpcDat() {
        return mGtpcDat;
    }

    public void setGtpcDat(GtpcDat mGtpcDat) {
        this.mGtpcDat = mGtpcDat;
    }

    public byte getTenkenKgas() {
        return mTenkenKgas;
    }

    public void setTenkenKgas(byte mTenkenKgas) {
        this.mTenkenKgas = mTenkenKgas;
    }

    public byte getTenkenDelta() {
        return mTenkenDelta;
    }

    public void setTenkenDelta(byte mTenkenDelta) {
        this.mTenkenDelta = mTenkenDelta;
    }

    public boolean isToyukensinFlg() {
        return m_isToyukensinFlg;
    }

    public void setToyukensinFlg(boolean m_isToyukensinFlg) {
        this.m_isToyukensinFlg = m_isToyukensinFlg;
    }

    public boolean isLtas() {
        return m_isLtas;
    }

    public void setIsLtas(boolean m_isLtas) {
        this.m_isLtas = m_isLtas;
    }

    public String getInvoiceComment() {
        return m_strInvoiceComment;
    }

    public void setInvoiceComment(String m_strInvoiceComment) {
        this.m_strInvoiceComment = m_strInvoiceComment;
    }
}
