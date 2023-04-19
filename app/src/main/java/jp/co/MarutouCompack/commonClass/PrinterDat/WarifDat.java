package jp.co.MarutouCompack.commonClass.PrinterDat;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * 値引きデータクラス.
 */
@SuppressWarnings("unused")
public class WarifDat {
    
    // --------------------------------------------------
    // 変数
    // --------------------------------------------------
    /** 品目：使用有無 */
    public byte m_nHinUserf;
    /** 品目：品目or取引No */
    public short m_nHinHinno;
    /** 品目：名称 */
    public String m_strHinName;
    /** 品目：符号 */
    public byte m_nHinSign;
    /** 品目：種別 */
    public byte m_nHinKind;
    /** 商品：使用有無 */
    public byte m_nShoUserf;
    /** 商品：品目No */
    public short m_nShoHinno;
    /** 商品：品番No */
    public short m_nShoShono;
    /** 商品：名称 */
    public String m_strShoHinban;
    /** 商品：単位 */
    public String m_strShoUnit;
    /** 商品：消費税区分 */
    public byte m_nShoTaxku;
    /** 商品：消費税率 */
    public short m_nShoTaxr;
    /** 商品：単価 */
    public int m_nShoTanka;
    /** 消費税端数処理：加算 */
    public int m_nShoFracaddTax;
    /** 消費税端数処理：乗算 */
    public int m_nShoFracmulTax;
    /** 消費税更新：年 */
    public short m_sShoTax_yy;
    /** 消費税更新：月 */
    public byte m_bShoTax_mm;
    /** 消費税更新：日 */
    public byte m_bShoTax_dd;
    /** 旧消費税率 */
    public short m_sShoTaxr_old;
    /** 新消費税率 */
    public short m_sShoTaxr_new;

    /**
     * コンストラクタ
     */
    public WarifDat(){
    }

    public WarifDat(byte m_nHinUserf, short m_nHinHinno, String m_strHinName, byte m_nHinSign, byte m_nHinKind, byte m_nShoUserf, short m_nShoHinno, short m_nShoShono, String m_strShoHinban, String m_strShoUnit, byte m_nShoTaxku, short m_nShoTaxr, int m_nShoTanka, int m_nShoFracaddTax, int m_nShoFracmulTax, short m_sShoTax_yy, byte m_bShoTax_mm, byte m_bShoTax_dd, short m_sShoTaxr_old, short m_sShoTaxr_new) {
        this.m_nHinUserf = m_nHinUserf;
        this.m_nHinHinno = m_nHinHinno;
        this.m_strHinName = m_strHinName;
        this.m_nHinSign = m_nHinSign;
        this.m_nHinKind = m_nHinKind;
        this.m_nShoUserf = m_nShoUserf;
        this.m_nShoHinno = m_nShoHinno;
        this.m_nShoShono = m_nShoShono;
        this.m_strShoHinban = m_strShoHinban;
        this.m_strShoUnit = m_strShoUnit;
        this.m_nShoTaxku = m_nShoTaxku;
        this.m_nShoTaxr = m_nShoTaxr;
        this.m_nShoTanka = m_nShoTanka;
        this.m_nShoFracaddTax = m_nShoFracaddTax;
        this.m_nShoFracmulTax = m_nShoFracmulTax;
        this.m_sShoTax_yy = m_sShoTax_yy;
        this.m_bShoTax_mm = m_bShoTax_mm;
        this.m_bShoTax_dd = m_bShoTax_dd;
        this.m_sShoTaxr_old = m_sShoTaxr_old;
        this.m_sShoTaxr_new = m_sShoTaxr_new;
    }
}
