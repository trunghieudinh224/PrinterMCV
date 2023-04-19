
package jp.co.MarutouCompack.commonClass.PrinterDat;


import com.google.gson.annotations.SerializedName;

/**
 * 販売明細データクラス.
 */
public class HmefDat {
    /** 使用有無 */
    @SerializedName("mUsef")
    private boolean mUsef;
    /** 明細種別 0:締後、1:締前、9:ﾊﾝﾃﾞｨ、2：残高明細 */
    @SerializedName("mHmeKind")
    private byte mHmeKind;
    /** 顧客コード */
    @SerializedName("mCusRec")
    private int mCusRec;
    /** 前明細レコード */
    @SerializedName("mPreHrec")
    private int mPreHrec;
    /** 次明細レコード 0で最終レコード */
    @SerializedName("mNxtHrec")
    private int mNxtHrec;
    /** 伝票日付(年) */
    @SerializedName("mDeny")
    private short mDeny;
    /** 伝票日付(月) */
    @SerializedName("mDenm")
    private byte mDenm;
    /** 伝票日付(日) */
    @SerializedName("mDend")
    private byte mDend;
    /** 取引種別 0:商品、1:入金、2:支払、 3:調整　 */
    @SerializedName("mDenKind")
    private byte mDenKind;
    /** 品目No >=100 */
    @SerializedName("mHmCode")
    private short mHmCode;
    /** 品目名称 (漢字12文字) */
    @SerializedName("mHmName")
    private String mHmName = "";
    /** 品番No 0 ～ 99 */
    @SerializedName("mHbCode")
    private short mHbCode;
    /** 品番名称 (半角24文字) */
    @SerializedName("mHbName")
    private String mHbName = "";
    /** 数量 */
    @SerializedName("mSuryo")
    private int mSuryo;
    /** 単価 */
    @SerializedName("mTanka")
    private int mTanka;
    /** 金額 */
    @SerializedName("mKin")
    private int mKin;
    /** 消費税区分 */
    @SerializedName("mTaxKu")
    private byte mTaxKu;
    /** 消費税率 */
    @SerializedName("mTaxR")
    private short mTaxR;
    /** 消費税額 */
    @SerializedName("mTax")
    private int mTax;
    /** リース明細かどうか */
    @SerializedName("mLeasKind")
    private byte mLeasKind;
    /** 符号 */
    @SerializedName("mSign")
    private byte mSign;
    /** 品番印字有無 */
    @SerializedName("mHbnmPrn")
    private byte mHbnmPrn;
    /** 軽減税率区分 */
    @SerializedName("mKeigenKubun")
    private byte mKeigenKubun;
    /** 自明細レコード番号 */
    @SerializedName("m_nRec")
    private int m_nRec = 0;
    /** リース情報レコード番号 */
    @SerializedName("m_nLeasRec")
    private int m_nLeasRec;

    // --------------------
    // 以下ltas仕様
    // --------------------
    /** 入金割当レコード */
    @SerializedName("mLnkRec")
    private short mLnkRec = 0;
    /** 入金割当フラグ */
    @SerializedName("mLnkFlg")
    private short mLnkFlg = 0;
    /** 入金割当残高(検針送信時) */
    @SerializedName("mLnkZan")
    private int mLnkZan = 0;
    /** 入金割当残高(HT) */
    @SerializedName("mLnkHtzan")
    private int mLnkHtzan = 0;
    /** 伝票レコード(上位4桁) */
    @SerializedName("mDencntH")
    private int mDencntH = 0;
    /** 伝票レコード(下位4桁) */
    @SerializedName("mDencntL")
    private int mDencntL = 0;
    /** 伝票行レコード */
    @SerializedName("mMeicnt")
    private short mMeicnt = 0;
    /**  */
    @SerializedName("mShofDat")
    private ShofDat mShofDat;

    /**
     * コンストラクタ<br />
     * 初期化を実施。
     */
    public HmefDat() {
    }


    public HmefDat(boolean mUsef, byte mHmeKind, int mCusRec, int mPreHrec, int mNxtHrec, short mDeny, byte mDenm, byte mDend,
                   byte mDenKind, short mHmCode, String mHmName, short mHbCode, String mHbName, int mSuryo, int mTanka, int mKin,
                   byte mTaxKu, short mTaxR, int mTax, byte mLeasKind, byte mSign, byte mHbnmPrn, byte mKeigenKubun, int m_nRec,
                   int m_nLeasRec, short mLnkRec, short mLnkFlg, int mLnkZan, int mLnkHtzan, int mDencntH, int mDencntL,
                   short mMeicnt, ShofDat mShofDat) {
        this.mUsef = mUsef;
        this.mHmeKind = mHmeKind;
        this.mCusRec = mCusRec;
        this.mPreHrec = mPreHrec;
        this.mNxtHrec = mNxtHrec;
        this.mDeny = mDeny;
        this.mDenm = mDenm;
        this.mDend = mDend;
        this.mDenKind = mDenKind;
        this.mHmCode = mHmCode;
        this.mHmName = mHmName;
        this.mHbCode = mHbCode;
        this.mHbName = mHbName;
        this.mSuryo = mSuryo;
        this.mTanka = mTanka;
        this.mKin = mKin;
        this.mTaxKu = mTaxKu;
        this.mTaxR = mTaxR;
        this.mTax = mTax;
        this.mLeasKind = mLeasKind;
        this.mSign = mSign;
        this.mHbnmPrn = mHbnmPrn;
        this.mKeigenKubun = mKeigenKubun;
        this.m_nRec = m_nRec;
        this.m_nLeasRec = m_nLeasRec;
        this.mLnkRec = mLnkRec;
        this.mLnkFlg = mLnkFlg;
        this.mLnkZan = mLnkZan;
        this.mLnkHtzan = mLnkHtzan;
        this.mDencntH = mDencntH;
        this.mDencntL = mDencntL;
        this.mMeicnt = mMeicnt;
        this.mShofDat = mShofDat;
    }

    public boolean isUsef() {
        return mUsef;
    }

    public void setUsef(boolean mUsef) {
        this.mUsef = mUsef;
    }

    public byte getHmeKind() {
        return mHmeKind;
    }

    public void setHmeKind(byte mHmeKind) {
        this.mHmeKind = mHmeKind;
    }

    public int getCusRec() {
        return mCusRec;
    }

    public void setCusRec(int mCusRec) {
        this.mCusRec = mCusRec;
    }

    public int getPreHrec() {
        return mPreHrec;
    }

    public void setPreHrec(int mPreHrec) {
        this.mPreHrec = mPreHrec;
    }

    public int getNxtHrec() {
        return mNxtHrec;
    }

    public void setNxtHrec(int mNxtHrec) {
        this.mNxtHrec = mNxtHrec;
    }

    public short getDeny() {
        return mDeny;
    }

    public void setDeny(short mDeny) {
        this.mDeny = mDeny;
    }

    public byte getDenm() {
        return mDenm;
    }

    public void setDenm(byte mDenm) {
        this.mDenm = mDenm;
    }

    public byte getDend() {
        return mDend;
    }

    public void setDend(byte mDend) {
        this.mDend = mDend;
    }

    public byte getDenKind() {
        return mDenKind;
    }

    public void setDenKind(byte mDenKind) {
        this.mDenKind = mDenKind;
    }

    public short getHmCode() {
        return mHmCode;
    }

    public void setHmCode(short mHmCode) {
        this.mHmCode = mHmCode;
    }

    public String getHmName() {
        return mHmName;
    }

    public void setHmName(String mHmName) {
        this.mHmName = mHmName;
    }

    public short getHbCode() {
        return mHbCode;
    }

    public void setHbCode(short mHbCode) {
        this.mHbCode = mHbCode;
    }

    public String getHbName() {
        return mHbName;
    }

    public void setHbName(String mHbName) {
        this.mHbName = mHbName;
    }

    public int getSuryo() {
        return mSuryo;
    }

    public void setSuryo(int mSuryo) {
        this.mSuryo = mSuryo;
    }

    public int getTanka() {
        return mTanka;
    }

    public void setTanka(int mTanka) {
        this.mTanka = mTanka;
    }

    public int getKin() {
        return mKin;
    }

    public void setKin(int mKin) {
        this.mKin = mKin;
    }

    public byte getTaxKu() {
        return mTaxKu;
    }

    public void setTaxKu(byte mTaxKu) {
        this.mTaxKu = mTaxKu;
    }

    public short getTaxR() {
        return mTaxR;
    }

    public void setTaxR(short mTaxR) {
        this.mTaxR = mTaxR;
    }

    public int getTax() {
        return mTax;
    }

    public void setTax(int mTax) {
        this.mTax = mTax;
    }

    public byte getLeasKind() {
        return mLeasKind;
    }

    public void setLeasKind(byte mLeasKind) {
        this.mLeasKind = mLeasKind;
    }

    public byte getSign() {
        return mSign;
    }

    public void setSign(byte mSign) {
        this.mSign = mSign;
    }

    public byte getHbnmPrn() {
        return mHbnmPrn;
    }

    public void setHbnmPrn(byte mHbnmPrn) {
        this.mHbnmPrn = mHbnmPrn;
    }

    public byte getKeigenKubun() {
        return mKeigenKubun;
    }

    public void setKeigenKubun(byte mKeigenKubun) {
        this.mKeigenKubun = mKeigenKubun;
    }

    public int getRec() {
        return m_nRec;
    }

    public void setRec(int m_nRec) {
        this.m_nRec = m_nRec;
    }

    public int getLeasRec() {
        return m_nLeasRec;
    }

    public void setLeasRec(int m_nLeasRec) {
        this.m_nLeasRec = m_nLeasRec;
    }

    public short getLnkRec() {
        return mLnkRec;
    }

    public void setLnkRec(short mLnkRec) {
        this.mLnkRec = mLnkRec;
    }

    public short getLnkFlg() {
        return mLnkFlg;
    }

    public void setLnkFlg(short mLnkFlg) {
        this.mLnkFlg = mLnkFlg;
    }

    public int getLnkZan() {
        return mLnkZan;
    }

    public void setLnkZan(int mLnkZan) {
        this.mLnkZan = mLnkZan;
    }

    public int getLnkHtzan() {
        return mLnkHtzan;
    }

    public void setLnkHtzan(int mLnkHtzan) {
        this.mLnkHtzan = mLnkHtzan;
    }

    public int getDencntH() {
        return mDencntH;
    }

    public void setDencntH(int mDencntH) {
        this.mDencntH = mDencntH;
    }

    public int getDencntL() {
        return mDencntL;
    }

    public void setDencntL(int mDencntL) {
        this.mDencntL = mDencntL;
    }

    public short getMeicnt() {
        return mMeicnt;
    }

    public void setMeicnt(short mMeicnt) {
        this.mMeicnt = mMeicnt;
    }

    public ShofDat getShofDat() {
        return mShofDat;
    }

    public void setShofDat(ShofDat mShofDat) {
        this.mShofDat = mShofDat;
    }
}
