
package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import jp.co.MarutouCompack.commonClass.PrinterDat.Ko2fDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.ShofDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.Sy2fDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KokfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.HanfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.GasfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KnebDat;

/**
 * 販売店データクラス.
 */
public class UserData {
    /** システム設定情報 */
    @SerializedName("mSysfDat")
    private SysfDat mSysfDat = null;
    /** システムデータクラス */
    @SerializedName("mSy2fDat")
    private Sy2fDat mSy2fDat = null;
    /** 検針日 */
    @SerializedName("mKensinDate")
    private String mKensinDate;
    /** 顧客データ */
    @SerializedName("mKokfDat")
    private KokfDat mKokfDat = null;
    /** 顧客ハイブリッドデータ */
    @SerializedName("mKo2fDat")
    private Ko2fDat mKo2fDat = null;
    /** ガス料金データ */
    @SerializedName("mGasfDat")
    private GasfDat mGasfDat = null;
    /** 販売明細データ */
    @SerializedName("mHmefList")
    private List<HmefDat> mHmefList;
    /** 店舗データ */
    @SerializedName("mHanfDat")
    private HanfDat mHanfDat = null;
    /** 入金モードフラグ */
    @SerializedName("mNyukinMode")
    private boolean mNyukinMode;
    /** 領収書印字時に残高明細を印字しない */
    @SerializedName("mNyukinOnly")
    private boolean mNyukinOnly;
    /** 顧客特別仕様 */
    @SerializedName("mKouserDat")
    private KouserDat mKouserDat;
    /** 顧客値引きデータ */
    @SerializedName("m_lstKnebDat")
    private List<KnebDat> m_lstKnebDat;
    /** 検針時リースの販売明細一覧 */
    @SerializedName("m_lstLeasHmefDat")
    private List<HmefDat> m_lstLeasHmefDat;
    /** 締後 */
    @SerializedName("getHmef0")
    private List<HmefDat> getHmef0;
    /** 締前 */
    @SerializedName("getHmef1")
    private List<HmefDat> getHmef1;
    /** ハンディ */
    @SerializedName("getHmef2")
    private List<HmefDat> getHmef2;
    /**  */
    @SerializedName("mShofDatKangen")
    private ShofDat mShofDatKangen;

    public UserData(SysfDat mSysfDat, Sy2fDat mSy2fDat, String mKensinDate, KokfDat mKokfDat, Ko2fDat mKo2fDat, GasfDat mGasfDat,
                    List<HmefDat> mHmefList, HanfDat mHanfDat, boolean mNyukinMode, boolean mNyukinOnly,
                    KouserDat mKouserDat, List<KnebDat> m_lstKnebDat, List<HmefDat> m_lstLeasHmefDat,
                    List<HmefDat> getHmef0, List<HmefDat> getHmef1, List<HmefDat> getHmef2, ShofDat mShofDatKangen) {
        this.mSysfDat = mSysfDat;
        this.mSy2fDat = mSy2fDat;
        this.mKensinDate = mKensinDate;
        this.mKokfDat = mKokfDat;
        this.mKo2fDat = mKo2fDat;
        this.mGasfDat = mGasfDat;
        this.mHmefList = mHmefList;
        this.mHanfDat = mHanfDat;
        this.mNyukinMode = mNyukinMode;
        this.mNyukinOnly = mNyukinOnly;
        this.mKouserDat = mKouserDat;
        this.m_lstKnebDat = m_lstKnebDat;
        this.m_lstLeasHmefDat = m_lstLeasHmefDat;
        this.getHmef0 = getHmef0;
        this.getHmef1 = getHmef1;
        this.getHmef2 = getHmef2;
        this.mShofDatKangen = mShofDatKangen;
    }

    public SysfDat getSysfDat() {
        return mSysfDat;
    }

    public void setSysfDat(SysfDat mSysfDat) {
        this.mSysfDat = mSysfDat;
    }

    public Sy2fDat getSy2fDat() {
        return mSy2fDat;
    }

    public void setSy2fDat(Sy2fDat mSy2fDat) {
        this.mSy2fDat = mSy2fDat;
    }

    public String getKensinDate() {
        return mKensinDate;
    }

    public void setKensinDate(String mKensinDate) {
        this.mKensinDate = mKensinDate;
    }

    public KokfDat getKokfDat() {
        return mKokfDat;
    }

    public void setKokfDat(KokfDat mKokfDat) {
        this.mKokfDat = mKokfDat;
    }

    public Ko2fDat getKo2fDat() {
        return mKo2fDat;
    }

    public void setKo2fDat(Ko2fDat mKo2fDat) {
        this.mKo2fDat = mKo2fDat;
    }

    public GasfDat getGasfDat() {
        return mGasfDat;
    }

    public void setGasfDat(GasfDat mGasfDat) {
        this.mGasfDat = mGasfDat;
    }

    public List<HmefDat> getHmefList() {
        return mHmefList;
    }

    public void setHmefList(List<HmefDat> mHmefList) {
        this.mHmefList = mHmefList;
    }

    public HanfDat getHanfDat() {
        return mHanfDat;
    }

    public void setHanfDat(HanfDat mHanfDat) {
        this.mHanfDat = mHanfDat;
    }

    public boolean isNyukinMode() {
        return mNyukinMode;
    }

    public void setNyukinMode(boolean mNyukinMode) {
        this.mNyukinMode = mNyukinMode;
    }

    public boolean isNyukinOnly() {
        return mNyukinOnly;
    }

    public void setNyukinOnly(boolean mNyukinOnly) {
        this.mNyukinOnly = mNyukinOnly;
    }

    public KouserDat getKouserDat() {
        return mKouserDat;
    }

    public void setKouserDat(KouserDat mKouserDat) {
        this.mKouserDat = mKouserDat;
    }

    public List<KnebDat> getLstKnebDat() {
        return m_lstKnebDat;
    }

    public void setLstKnebDat(List<KnebDat> m_lstKnebDat) {
        this.m_lstKnebDat = m_lstKnebDat;
    }

    public List<HmefDat> getLstLeasHmefDat() {
        return m_lstLeasHmefDat;
    }

    public void setLstLeasHmefDat(List<HmefDat> m_lstLeasHmefDat) {
        this.m_lstLeasHmefDat = m_lstLeasHmefDat;
    }

    public List<HmefDat> getGetHmef0() {
        return getHmef0;
    }

    public void setGetHmef0(List<HmefDat> getHmef0) {
        this.getHmef0 = getHmef0;
    }

    public List<HmefDat> getGetHmef1() {
        return getHmef1;
    }

    public void setGetHmef1(List<HmefDat> getHmef1) {
        this.getHmef1 = getHmef1;
    }

    public List<HmefDat> getGetHmef2() {
        return getHmef2;
    }

    public void setGetHmef2(List<HmefDat> getHmef2) {
        this.getHmef2 = getHmef2;
    }

    public ShofDat getShofDatKangen() {
        return mShofDatKangen;
    }

    public void setShofDatKangen(ShofDat mShofDatKangen) {
        this.mShofDatKangen = mShofDatKangen;
    }
}
