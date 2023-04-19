package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

import jp.co.MarutouCompack.commonClass.PrinterDat.Sy2fFunouComment;


public class Sy2fDat {
    /** オプション3 */
    @SerializedName("mSysOption")
    private byte[] mSysOption;
    /** 調整額コード */
    @SerializedName("mSysfHmcd13")
    private short mSysfHmcd13;
    /** 次回予定日印字フラグ */
    @SerializedName("mJifuriNext")
    private byte mJifuriNext;
    /** 軽減税率対応 */
    @SerializedName("mSyskeigen")
    private byte mSyskeigen;
    /** ポイントシステムver */
    @SerializedName("mPntVer")
    private byte mPntVer;
    /** 振替不能コメント印字フラグ */
    @SerializedName("mFunouPrint")
    private byte mFunouPrint;
    /** ポイント名称 */
    @SerializedName("pntDatName")
    private String pntDatName;
    /** 振替不能コメント */
    @SerializedName("mSy2fFunouComment")
    private Sy2fFunouComment mSy2fFunouComment;
    /** 宮野式ポイントフラグ */
    @SerializedName("mMiyanoFlg")
    private byte mMiyanoFlg;

    public Sy2fDat(byte[] mSysOption, short mSysfHmcd13, byte mJifuriNext, byte mSyskeigen, byte mPntVer, byte mFunouPrint, String pntDatName,
                   Sy2fFunouComment mSy2fFunouComment, byte mMiyanoFlg) {
        this.mSysOption = mSysOption;
        this.mSysfHmcd13 = mSysfHmcd13;
        this.mJifuriNext = mJifuriNext;
        this.mSyskeigen = mSyskeigen;
        this.mPntVer = mPntVer;
        this.mFunouPrint = mFunouPrint;
        this.pntDatName = pntDatName;
        this.mSy2fFunouComment = mSy2fFunouComment;
        this.mMiyanoFlg = mMiyanoFlg;
    }

    public byte[] getSysOption() {
        return mSysOption;
    }

    public void setSysOption(byte[] mSysOption) {
        this.mSysOption = mSysOption;
    }

    public short getSysfHmcd13() {
        return mSysfHmcd13;
    }

    public void setSysfHmcd13(short mSysfHmcd13) {
        this.mSysfHmcd13 = mSysfHmcd13;
    }

    public byte getJifuriNext() {
        return mJifuriNext;
    }

    public void setJifuriNext(byte mJifuriNext) {
        this.mJifuriNext = mJifuriNext;
    }

    public byte getSyskeigen() {
        return mSyskeigen;
    }

    public void setSyskeigen(byte mSyskeigen) {
        this.mSyskeigen = mSyskeigen;
    }

    public byte getPntVer() {
        return mPntVer;
    }

    public void setPntVer(byte mPntVer) {
        this.mPntVer = mPntVer;
    }

    public byte getFunouPrint() {
        return mFunouPrint;
    }

    public void setFunouPrint(byte mFunouPrint) {
        this.mFunouPrint = mFunouPrint;
    }

    public String getPntDatName() {
        return pntDatName;
    }

    public void setPntDatName(String pntDatName) {
        this.pntDatName = pntDatName;
    }

    public Sy2fFunouComment getSy2fFunouComment() {
        return mSy2fFunouComment;
    }

    public void setSy2fFunouComment(Sy2fFunouComment mSy2fFunouComment) {
        this.mSy2fFunouComment = mSy2fFunouComment;
    }

    public byte getMiyanoFlg() {
        return mMiyanoFlg;
    }

    public void setMiyanoFlg(byte mMiyanoFlg) {
        this.mMiyanoFlg = mMiyanoFlg;
    }
}