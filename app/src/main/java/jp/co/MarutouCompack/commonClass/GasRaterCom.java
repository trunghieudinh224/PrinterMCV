
package jp.co.MarutouCompack.commonClass;

import android.content.Context;


import jp.co.MarutouCompack.commonClass.PrinterDat.KokfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.Sy2fDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysOption;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * ガス料金等計算処理クラス.
 */
public class GasRaterCom {

    /** ログ出力用タグ */
    private static final String TAG = GasRaterCom.class.getSimpleName();
    /** 端数処理：加算 */
    private static final int[] HASADD = new int[]{ 0, 50000, 0, 99000, 20000, 0, 49000, 5000, 0, 9000, 2000, 0, 4000, 500, 0, 900, 0 };
    /** 端数処理：乗算 */
    private static final int[] HASMUL = new int[]{ 10, 100000, 100000, 100000, 50000, 50000, 50000, 10000, 10000, 10000, 5000, 5000, 5000, 1000, 1000, 1000, 10 };


    /**
     * 振替依頼中を考慮した前月残高計算
     *
     * @param ctx       [in] {@link Context}    呼び出し元コンテキスト
     * @param sysfDat   [in] {@link SysfDat}    システムデータ
     * @param kokfDat   [in] {@link KokfDat}    顧客データ
     * @param sy2fDat   [in] {@link Sy2fDat}    システム2データ
     * @return  int 前月残高
     */
    public static int readPrebalance(Context ctx, SysfDat sysfDat, KokfDat kokfDat, Sy2fDat sy2fDat) {
        int wkKingaku;
        // 振替依頼中の前月残高の抑制フラグ
        int wkFuriDemand = 0;

        if (sy2fDat.getSysOption()[SysOption.PRINT_ZENZAN_IRAI.getIdx()] == 1) {
            // 振替依頼中は、前月残高を抑制フラグの有効中
            if (kokfDat.getBankCode() != 0 && kokfDat.getFriKin() != 0 && (kokfDat.getFristat() == 2 || kokfDat.getFristat() == 3) && sysfDat.ifDemand()) {
                // 振替依頼中は前月残高は印字しない
                wkFuriDemand = 1;
                if (kokfDat.getFriKin() != kokfDat.getPreBalance()) {
                    // 振替依頼中の金額<>前月残高では、前月残高の抑制は不可
                    wkFuriDemand = 0;
                }
            }
        }
        if ((sysfDat.ifDemand() && kokfDat.getPreBalance() != 0) && wkFuriDemand == 0) {
            wkKingaku = kokfDat.getPreBalance();
        }
        else {
            wkKingaku = 0;
        }
        MLog.INFO(ctx, TAG, "前残:" + wkKingaku);
        return wkKingaku;
    }

    /**
     * 振替依頼中の前残非表示有無の取得.
     *
     * @param sysfDat   [in] {@link SysfDat}    システムデータ
     * @param sy2fDat   [in] {@link Sy2fDat}    システム2データ
     * @param kokfDat   [in] {@link KokfDat}    顧客データ
     * @return  boolean true:前残印字しない, false:前残印字する
     */
    public static boolean isFuriDemand(SysfDat sysfDat, Sy2fDat sy2fDat, KokfDat kokfDat){
        boolean isFuriDemand = false;

        if (sy2fDat.getSysOption()[SysOption.PRINT_ZENZAN_IRAI.getIdx()] == 1) {
            // 振替依頼中は、前月残高を抑制フラグの有効中
            if (kokfDat.getBankCode() != 0 && kokfDat.getFriKin() != 0 && (kokfDat.getFristat() == 2 || kokfDat.getFristat() == 3) && sysfDat.ifDemand()) {
                // 振替依頼中は前月残高は印字しない
                // ただし、振替依頼中の金額<>前月残高では、前月残高の抑制は不可
                isFuriDemand = kokfDat.getFriKin() == kokfDat.getPreBalance();
            }
        }
        return isFuriDemand;
    }

    /**
     * 振替依頼中を考慮した前月残高計算
     *
     * @param ctx       [in] {@link Context}    呼び出し元コンテキスト
     * @param sysfDat   [in] {@link SysfDat}    システムデータ
     * @param kokfDat   [in] {@link KokfDat}    顧客データ
     * @param sy2fDat   [in] {@link Sy2fDat}    システム2データ
     * @return  int 前月残高
     */
    public static int calcPrebalance(Context ctx, SysfDat sysfDat, KokfDat kokfDat, Sy2fDat sy2fDat) {
        int wkKingaku;
        // 振替依頼中の前月残高の抑制フラグ
        int wkFuriDemand = 0;

        if (sy2fDat.getSysOption()[SysOption.PRINT_ZENZAN_IRAI.getIdx()] == 1) {
            // 振替依頼中は、前月残高を抑制フラグの有効中
            if (kokfDat.getBankCode() != 0 && kokfDat.getFriKin() != 0 && (kokfDat.getFristat() == 2 || kokfDat.getFristat() == 3) && sysfDat.ifDemand()) {
                // 振替依頼中は前月残高は印字しない
                wkFuriDemand = 1;
                if (kokfDat.getFriKin() != kokfDat.getPreBalance()) {
                    // 振替依頼中の金額<>前月残高では、前月残高の抑制は不可
                    wkFuriDemand = 0;
                }
            }
        }
        if (!((sysfDat.ifDemand() && kokfDat.getPreBalance() != 0) && !isFuriDemand(sysfDat, sy2fDat, kokfDat))) {
            wkKingaku = kokfDat.getPreBalance();
        }
        else {
            wkKingaku = 0;
        }
        MLog.INFO(ctx, TAG, "前残:" + wkKingaku);
        return wkKingaku;
    }


    /**
     * 売掛残高の計算
     *
     * @param ctx           [in]    Context 呼び出し元コンテキスト
     * @param sysfDat       [in]    SysfDat システムデータ
     * @param kokfDat       [in]    KokfDat 顧客データ
     * @param sy2fDat       [in]    Sy2fDat システム2データ
     * @param isIrai        [in] boolean    依頼中前残計上フラグ
     * @return  int 売掛残高
     */
    public static int calcSeikyu(Context ctx,
                                 SysfDat sysfDat,
                                 KokfDat kokfDat,
                                 Sy2fDat sy2fDat,
                                 boolean isIrai) {
        int wkUrizan; // 売掛残高
        wkUrizan = kokfDat.getProcTisyuu() + kokfDat.getTaxTisyuu(); // 遅収料金
        if (sysfDat.ifDemand()) {
            MLog.INFO(ctx, TAG, "前月残高あり");
            wkUrizan += isIrai ? readPrebalance(ctx, sysfDat, kokfDat, sy2fDat) : kokfDat.getPreBalance(); // 前月残高
            MLog.INFO(ctx, TAG, "    -> " + wkUrizan);
        }
        if (sysfDat.ifAdjust()) {
            MLog.INFO(ctx, TAG, "入金調整あり");
            wkUrizan += kokfDat.getTAdjust() - kokfDat.getTReceipt(); // 入金調整額
            MLog.INFO(ctx, TAG, "    -> " + wkUrizan);
        }
        if (sysfDat.ifAlarm()) {
            MLog.INFO(ctx, TAG, "リース加算あり");
            wkUrizan += kokfDat.getProcLease() + kokfDat.getTaxLease(); // リース 加算
            MLog.INFO(ctx, TAG, "    -> " + wkUrizan);
        }
        if (sysfDat.ifDiv()) {
            MLog.INFO(ctx, TAG, "分割金あり");
            wkUrizan += kokfDat.getProcDiv() + kokfDat.getTaxDiv(); // 分割金 加算
            MLog.INFO(ctx, TAG, "    -> " + wkUrizan);
        }
        if (sysfDat.ismIfLampoil()) {
            MLog.INFO(ctx, TAG, "灯油あり");
            wkUrizan += kokfDat.getProcLoil() + kokfDat.getTaxLoil(); // 灯油　加算
            MLog.INFO(ctx, TAG, "    -> " + wkUrizan);
        }
        if (sysfDat.ifProceeds()) {
            MLog.INFO(ctx, TAG, "その他、ガス残高、遅収料金あり");
            wkUrizan += kokfDat.getProcEtc() + kokfDat.getTaxEtc() + // その他 加算
                    kokfDat.getProcGas() + kokfDat.getTaxGas() - // ガス残高
                    (kokfDat.getProcTisyuu() + kokfDat.getTaxTisyuu()); // 遅収料金
            MLog.INFO(ctx, TAG, "    -> " + wkUrizan);
        }
        MLog.INFO(ctx, TAG, "請求金額:" + wkUrizan);
        return wkUrizan;
    }


    /**
     * 検針関係の消費税率の取得（検針・還元額）.
     *
     * @param kokf      [in] {@link KokfDat}    顧客データ
     * @param sysf      [in] {@link SysfDat}   システムデータ
     * @param tax_yy    [in] short              消費税変更日付(年)
     * @param tax_mm    [in] byte               消費税変更日付(月)
     * @param tax_dd    [in] byte               消費税変更日付(日)
     * @param taxr      [in] short              基本の消費税率
     * @param taxr_old  [in] short              旧税率
     * @param taxr_new [in] short               新税率
     * @return int   消費税率
     */
    public static int getKenTaxr(KokfDat kokf,
                                 SysfDat sysf,
                                 short tax_yy, byte tax_mm, byte tax_dd, short taxr, short taxr_old, short taxr_new) {
        int res_taxr = taxr;
        int wk_yy;
        byte wk_mm;
        byte wk_dd;
        long tax_ymd;//消費税更新日
        long tax_next;//消費税更新の翌月
        long srt_ymd;//初期日付
        long old_ymd;//前回検針日
        long ken_ymd;//今回検針日

        if (tax_yy !=0 && tax_mm !=0 && tax_dd !=0 ) {

            tax_next = (long)tax_yy*10000L + (long)(tax_mm +1)*100L + (long)tax_dd;
            tax_ymd = (long)tax_yy*10000L + (long)tax_mm*100L + (long)tax_dd;
            //
            if (kokf.getKMonth() ==0) {
                wk_yy = sysf.getSysYear();
                wk_mm = sysf.getSysMonth();
                wk_dd  = sysf.getDate();
            } else {
                wk_mm = kokf.getKMonth();
                wk_dd  = kokf.getKDate();
                if(sysf.getSysMonth() == wk_mm){
                    wk_yy = sysf.getSysYear();
                }
                else if(sysf.getSysMonth() == 1){
                    wk_yy = sysf.getSysYear() - 1;
                }
                else{
                    wk_yy = sysf.getSysYear();
                }
            }
            ken_ymd = (long)wk_yy*10000L + (long)wk_mm*100L + (long)wk_dd;
            //初回日付
            if (kokf.getKaiYear() !=0 && kokf.getKaiMonth() !=0 && kokf.getKaiDate() !=0 ) {
                srt_ymd = (long)kokf.getKaiYear()*10000L + (long)kokf.getKaiMonth()*100L + (long)kokf.getKaiDate();
            } else {
                srt_ymd =0;
            }
            //前回検針日
            if (kokf.getPuseYear() !=0 && kokf.getPuseMonth() !=0 && kokf.getPuseDate() !=0 ) {
                old_ymd = (long)kokf.getPuseYear()*10000L + (long)kokf.getPuseMonth()*100L + (long)kokf.getPuseDate();
            } else {
                old_ymd =0;
            }
            //消費税更新日付あり
            //　①今回検針日が、  システムの消費変更日付の翌月以降・・・・新税率
            //　②メータ初期日が、システムの消費変更日付以降・・・・・・・新税率
            //　③前回検針日が、  システムの消費変更日付以降・・・・・・・新税率
            //　④今回検針日が、  システムの消費変更日付以前・・・・・・・旧税率
            //　⑤前回検針日が、  システムの消費変更日付以前・・・・・・・旧税率
            //  ⑥メータ初期日、  システムの消費変更日付以前・・・・・・・旧税率
            //　　上記以外は、新税率とする。
            if (ken_ymd >= tax_next){
                res_taxr=taxr_new;
            }
            else if (srt_ymd > 0 && srt_ymd >= tax_ymd) {
                res_taxr=taxr_new;
            }
            else if (old_ymd > 0 && old_ymd >= tax_ymd) {
                res_taxr=taxr_new;
            }
            else if (ken_ymd > 0 && ken_ymd <  tax_ymd) {
                res_taxr=taxr_old;
            }
            else if (old_ymd > 0) {
                res_taxr=taxr_old;
            }
            else if (srt_ymd > 0) {
                res_taxr=taxr_old;
            }
            else {
                res_taxr=taxr_new;
            }
        }
        return res_taxr;
    }
}
