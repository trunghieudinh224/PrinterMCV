
package jp.co.MarutouCompack.commonClass.PrinterDat;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * ハイブリッド料金データクラス.
 */
@SuppressWarnings("unused")
public class HybfDat {
    /** 使用フラグ */
    private byte mUsef;
    /** 料金表示方式 */
    private byte mDispType;
    /** 基本料金名称コード */
    private short mBasenm;
    /** 通常料金名称コード */
    private short mUsenm;
    /** カウンタコード */
    private short[] mCntcd;
    /** カウンタ使用有無 */
    private byte[] mCusef;
    /** カウンタ端数処理(加算) */
    private long[] mCfracAdd;
    /** カウンタ端数処理(乗算) */
    private long[] mCfracMulti;
    /** カウンタ適用期間(開始) */
    private byte[] mMsrt;
    /** カウンタ適用期間(終了) */
    private byte[] mMend;
    /** 上限値 */
    private long[][] mGasLimit;
    /** 加算値 */
    private long[][] mGasAdd;
    /** 基本料金 */
    private long[][] mGasBase;
    /** カウンタ値引: SNCODE */
    private long mChoSncode;
    /** カウンタ値引: 消費税区分 */
    private short mChoTaxku;
    /** カウンタ値引: 消費税率 */
    private short mChoTaxr;
    /** カウンタ値引: 消費税端数処理(加算) */
    private long mChoAdd;
    /** カウンタ値引: 消費税端数処理(乗算) */
    private long mChoMulti;
    /** カウンタ使用料: SNCODE */
    private long mUseSncode;
    /** カウンタ使用料: 金額 */
    private long mUseKin;
    /** カウンタ使用料: 消費税 */
    private long mUseTax;
    /** カウンタ使用料: 消費税区分 */
    private short mUseTaxku;

    /**
     * コンストラクタ.
     */
    public HybfDat() {
    }

    public HybfDat(byte mUsef, byte mDispType, short mBasenm, short mUsenm, short[] mCntcd, byte[] mCusef, long[] mCfracAdd, long[] mCfracMulti, byte[] mMsrt, byte[] mMend, long[][] mGasLimit, long[][] mGasAdd, long[][] mGasBase, long mChoSncode, short mChoTaxku, short mChoTaxr, long mChoAdd, long mChoMulti, long mUseSncode, long mUseKin, long mUseTax, short mUseTaxku) {
        this.mUsef = mUsef;
        this.mDispType = mDispType;
        this.mBasenm = mBasenm;
        this.mUsenm = mUsenm;
        this.mCntcd = mCntcd;
        this.mCusef = mCusef;
        this.mCfracAdd = mCfracAdd;
        this.mCfracMulti = mCfracMulti;
        this.mMsrt = mMsrt;
        this.mMend = mMend;
        this.mGasLimit = mGasLimit;
        this.mGasAdd = mGasAdd;
        this.mGasBase = mGasBase;
        this.mChoSncode = mChoSncode;
        this.mChoTaxku = mChoTaxku;
        this.mChoTaxr = mChoTaxr;
        this.mChoAdd = mChoAdd;
        this.mChoMulti = mChoMulti;
        this.mUseSncode = mUseSncode;
        this.mUseKin = mUseKin;
        this.mUseTax = mUseTax;
        this.mUseTaxku = mUseTaxku;
    }

    public byte getUsef() {
        return mUsef;
    }

    public void setUsef(byte mUsef) {
        this.mUsef = mUsef;
    }

    public byte getmDispType() {
        return mDispType;
    }

    public void setDispType(byte mDispType) {
        this.mDispType = mDispType;
    }

    public short getBasenm() {
        return mBasenm;
    }

    public void setBasenm(short mBasenm) {
        this.mBasenm = mBasenm;
    }

    public short getUsenm() {
        return mUsenm;
    }

    public void setUsenm(short mUsenm) {
        this.mUsenm = mUsenm;
    }

    public short[] getCntcd() {
        return mCntcd;
    }

    public void setCntcd(short[] mCntcd) {
        this.mCntcd = mCntcd;
    }

    public byte[] getCusef() {
        return mCusef;
    }

    public void setCusef(byte[] mCusef) {
        this.mCusef = mCusef;
    }

    public long[] getCfracAdd() {
        return mCfracAdd;
    }

    public void setCfracAdd(long[] mCfracAdd) {
        this.mCfracAdd = mCfracAdd;
    }

    public long[] getCfracMulti() {
        return mCfracMulti;
    }

    public void setCfracMulti(long[] mCfracMulti) {
        this.mCfracMulti = mCfracMulti;
    }

    public byte[] getMsrt() {
        return mMsrt;
    }

    public void setMsrt(byte[] mMsrt) {
        this.mMsrt = mMsrt;
    }

    public byte[] getMend() {
        return mMend;
    }

    public void setMend(byte[] mMend) {
        this.mMend = mMend;
    }

    public long[][] getGasLimit() {
        return mGasLimit;
    }

    public void setGasLimit(long[][] mGasLimit) {
        this.mGasLimit = mGasLimit;
    }

    public long[][] getGasAdd() {
        return mGasAdd;
    }

    public void setGasAdd(long[][] mGasAdd) {
        this.mGasAdd = mGasAdd;
    }

    public long[][] getGasBase() {
        return mGasBase;
    }

    public void setGasBase(long[][] mGasBase) {
        this.mGasBase = mGasBase;
    }

    public long getChoSncode() {
        return mChoSncode;
    }

    public void setChoSncode(long mChoSncode) {
        this.mChoSncode = mChoSncode;
    }

    public short getChoTaxku() {
        return mChoTaxku;
    }

    public void setChoTaxku(short mChoTaxku) {
        this.mChoTaxku = mChoTaxku;
    }

    public short getChoTaxr() {
        return mChoTaxr;
    }

    public void setChoTaxr(short mChoTaxr) {
        this.mChoTaxr = mChoTaxr;
    }

    public long getChoAdd() {
        return mChoAdd;
    }

    public void setChoAdd(long mChoAdd) {
        this.mChoAdd = mChoAdd;
    }

    public long getChoMulti() {
        return mChoMulti;
    }

    public void setChoMulti(long mChoMulti) {
        this.mChoMulti = mChoMulti;
    }

    public long getUseSncode() {
        return mUseSncode;
    }

    public void setUseSncode(long mUseSncode) {
        this.mUseSncode = mUseSncode;
    }

    public long getUseKin() {
        return mUseKin;
    }

    public void setUseKin(long mUseKin) {
        this.mUseKin = mUseKin;
    }

    public long getUseTax() {
        return mUseTax;
    }

    public void setUseTax(long mUseTax) {
        this.mUseTax = mUseTax;
    }

    public short getUseTaxku() {
        return mUseTaxku;
    }

    public void setUseTaxku(short mUseTaxku) {
        this.mUseTaxku = mUseTaxku;
    }
}
