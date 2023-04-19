package jp.co.MarutouCompack.commonClass;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;
import java.util.Locale;

import jp.co.MarutouCompack.Printer.BaseActivity;
import jp.co.MarutouCompack.Printer.BasePrintActivity;
import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.baseClass.PrintBaseCharactor;
import jp.co.MarutouCompack.baseClass.PrintImageList;
import jp.co.MarutouCompack.baseClass.PrintKensinInterface;
import jp.co.MarutouCompack.commonClass.PrinterDat.CnpCusDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.CounterUseKinDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.CusData;
import jp.co.MarutouCompack.commonClass.PrinterDat.GasfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.GstpDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.GtpcDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.HybfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KensinData;
import jp.co.MarutouCompack.commonClass.PrinterDat.KensinInfoBaseDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KinInfoDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KnebDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.Ko2fDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KokfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KotfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KouserDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.Sy2fDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysOption;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UserData;
import jp.co.MarutouCompack.commonClass.PrinterDat.WarifDat;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * 検針伝票印刷処理.
 */
public class PrintKensinCharactor extends PrintBaseCharactor implements PrintKensinInterface {

    /** ログ出力用タグ */
    private static final String TAG = PrintKensinCharactor.class.getSimpleName();
    /** 料金式印字位置 */
    private static final int[] RECT_RYOKIN_SIKI = new int[]{0, 385, RECT_WIDTH};

    /**
     * コンストラクタ<br />
     * ポート名及び設定情報を設定
     * 
     * @param ctx       [in] {@link Context}    呼び出し元コンテキスト
     */
    public PrintKensinCharactor(Context ctx) throws MException{
        super(ctx);
        // この段階で接続
        MLog.INFO(ctx, TAG, "印刷開始[印刷モード:文字列]");
        connect();
    }

    @Override
    public void printExecute() throws MException {
        // 最終データの出力
        sendData();
        // 切断
        disconnect();
    }

    /**
     * 保安情報の印字。
     * 
     * @param strLine   [in] String 印字文字列
     * @param nIdx      [in] int    印字位置
     */
    private void printHoanInfo(String strLine, int nIdx) {
        byte[] t_position = new byte[] {
                2, 18, 26, 42,
        };
        printHPInfo(t_position, strLine, nIdx);
    }

    /**
     * ヘッダー部分の印刷データ作成
     *
     * @param isNyukin    [in] boolean 入金フラグ
     */
    public void createPrintData(UserData userData, boolean isNyukin, boolean isHikae) {
        String strTitle;
        
        if( userData.isNyukinMode() ){
            print( "領　収　書", CHARSIZE.LARGE, LAYOUT.CENTER );
            printFeedDot((byte)5);
        }
        else {
            SysfDat sysfDat = userData.getSysfDat();
            GtpcDat gtpcDat = sysfDat.getGtpcDat();
            if(isNyukin){
                if(isHikae){
                    if (sysfDat.getVisibleGas() == 1 && !TextUtils.isEmpty(OtherUtil.cutStringSpace(gtpcDat.m_strTitle_3))) {
                        strTitle = gtpcDat.m_strTitle_3;
                    } else {
                        strTitle = "検針伝票 (兼　領収書)(控)";
                    }
                }
                else {
                    if (sysfDat.getVisibleGas() == 1 && !TextUtils.isEmpty(OtherUtil.cutStringSpace(gtpcDat.m_strTitle_1))) {
                        strTitle = gtpcDat.m_strTitle_1;
                    } else {
                        strTitle = "検針伝票 (兼　領収書)";
                    }
                }
            }
            else{
                if(isHikae){
                    if (sysfDat.getVisibleGas() == 1 && !TextUtils.isEmpty(OtherUtil.cutStringSpace(gtpcDat.m_strTitle_2))) {
                        strTitle = gtpcDat.m_strTitle_2;
                    } else {
                        strTitle = "検 針 伝 票(控)";
                    }
                }
                else {
                    if (sysfDat.getVisibleGas() == 1 && !TextUtils.isEmpty(OtherUtil.cutStringSpace(gtpcDat.m_strTitle_0))) {
                        strTitle = gtpcDat.m_strTitle_0;
                    } else {
                        strTitle = "検　針　伝　票";
                    }
                }
            }
            print( strTitle, CHARSIZE.LARGE, LAYOUT.CENTER );
            printFeedDot((byte)5);
            print( "毎度ありがとうございます。", CHARSIZE.SMALL, LAYOUT.CENTER );
            print( "今月の検針は次の通りです。", CHARSIZE.SMALL, LAYOUT.CENTER );
            printFeedDot((byte)20);
        }
    }

    /**
     * 顧客情報の印刷データを作成する。
     *
     * @param cusData [in] {@link CusData}  顧客データ
     * @throws MException 印字中にエラーがあった場合に発生
     */
    public void createCusInfo(CusData cusData) throws MException {
        int nStartHeight;
        int nEndHeight;
        printNormal( (BaseActivity.mWebData.getUserData().isNyukinMode() ? "発行日　　" : "検針日　　") + cusData.getDate());
        printFeedDot((byte)5);
        // 顧客コード
        nStartHeight = printFeedDot((byte)5)[0];
        printNormal("コード：　" + cusData.getKcode());
        // 顧客名　＋　呼称
        if (OtherUtil.getClearString(cusData.getName0()).length() > 0 && OtherUtil.getClearString(cusData.getName1()).length() > 0) {
            printNormal(addSpaceToString(cusData.getName0()));
            printNormal(addSpaceToString(cusData.getName1()) + cusData.getKname());
        }
        else if (OtherUtil.getClearString(cusData.getName0()).length() > 0) {
            printNormal(addSpaceToString(cusData.getName0()) + cusData.getKname());
        }
        else {
            printNormal(addSpaceToString(cusData.getName1()) + cusData.getKname());
        }
        // 住所その１
        printNormal("　　" + cusData.getAdd0());
        if (!cusData.getAdd1().equals("")) { // あれば住所その２
            printNormal("　　" + cusData.getAdd1());
        }
        nEndHeight = printFeedDot((byte) 10)[1];
        // 矩形設定
        printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.BOLD);
        printFeedDot((byte)10);
        checkPageModeLength();
    }

    /**
     * 検針情報の印刷データを作成する。
     * 
     * @param kensinData  [in] KensinData 検針データ
     * @throws MException 印字中にエラーがあった場合に発生
     */
    @Override
    public void createKensinInfo(UserData userData, KensinData kensinData) throws MException {
        if (userData.getKokfDat().getKenSumi() == 1 && !userData.isNyukinMode() && kensinData.isPrintKensin()) {
            createKensinInfoBase(userData, BasePrintActivity.mWebData.getAKensinDat().getKSIB()); // 13.01.16
        }
        KotfDat kotfDat = kensinData.getKotfDat();
        if(kotfDat != null && kotfDat.getKen_sumi() == 1 && !userData.isNyukinMode() && kensinData.isPrintToyu()){
            createToyuKensinInfoBase(kensinData);
        }

        createKinInfo(BasePrintActivity.mWebData.getAKensinDat().getKI()); // 13.01.16

        if(!userData.isNyukinMode()) {
            // 内税を印字する
            if (userData.getSy2fDat().getSysOption()[SysOption.NOT_PRINT_UTIZEI.getIdx()] == 0) { // 内税コメントの抑制フラグ
                createUTaxComment(BasePrintActivity.mWebData.getAKensinDat().getUTC());
            }

            if (userData.getKokfDat().getKenSumi() == 1 || (kotfDat != null && kotfDat.getKen_sumi() == 1)) {
                createSeikyuComment(BasePrintActivity.mWebData.getKensinData().getNyukin()); // 13.01.16
            }

            if (userData.getKokfDat().getKenSumi() == 1 && !userData.isNyukinMode() && kensinData.isPrintKensin()) {
                createGasryokinSiki(null, kensinData, 0);

                if (kensinData.isPrintHiwariComment()) {
                    createHiwariComment(null, kensinData, 0);
                }
            }
        }
        checkPageModeLength();
    }


    /**
     * 請求コメントの印字
     *
     */
    private void createSeikyuComment(int nyukin) {
        // 入金額が0円の場合
        printFeedDot((byte)10);
        if (nyukin == 0) {
            printNormal("　上記の通りご請求致します。");
        }
        checkPageModeLength();
    }

    /**
     * 検針情報の印字
     *
     * @throws MException 印字中にエラーがあった場合に発生
     */
    private void createKensinInfoBase(UserData userData, KensinInfoBaseDat mKSIB) throws MException {
        int nStartheight = printFeedDot((byte)5)[0];
        int nEndheight;
        // -----------------------------------------------
        // ガス指針情報
        printSeikyuInfo("今回指針", 0);
        printSeikyuInfo(mKSIB.getSisin(), 1);

        String strLine = "前回指針";
        if(mKSIB.isChgMeter()){
            // メーター取替有
            strLine = "取付指針";
        }
        strLine += mKSIB.getToritsukjJiZenkaiSiSin();
        printSeikyuInfo(strLine, 0);
        printSeikyuInfo(mKSIB.getSisinPrev(), 1);

        if(mKSIB.isChgMeter()){
            // メーター取替有は中間使用量を印字
            printSeikyuInfo("中間使用量", 0);
            printSeikyuInfo(mKSIB.getChukanSur(), 1);
        }

        printSeikyuInfo("使用量", 0);
        printSeikyuInfo(mKSIB.getNowUse(), 1);
        nEndheight = printFeedDot((byte)10)[1];
        printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.NORMAL);
        nEndheight = nStartheight;

        if(mKSIB.isPrnZensr()) {
            nStartheight = printFeedDot((byte)5)[0];
            printSeikyuInfo("前回使用量", 0);
            nEndheight = printSeikyuInfo(mKSIB.getPreUse(), 1)[1];
        }
        if(mKSIB.isPrintZenYearKenSr()){
            // 前年同月使用量
            nEndheight = createZenYearkenSr(null, mKSIB, nStartheight);
        }

        if(mKSIB.isChgMeter()){
            // メーター取替有は前回取外し指針と前回指針を印字
            nStartheight = printFeedDot((byte)5)[0];
            strLine = "取外指針";
            strLine += mKSIB.getTorihazuSiSinDate();
            printSeikyuInfo(strLine, 0);
            printSeikyuInfo(mKSIB.getChgZsisin(), 1);
            
            strLine = "前回指針";
            strLine += mKSIB.getZenkaiSiSinDate();
            printSeikyuInfo(strLine, 0);
            nEndheight = printSeikyuInfo(mKSIB.getChgZsisin(), 1)[1];
        }
        if(nStartheight != nEndheight) {
            nEndheight = printFeedDot((byte) 10)[1];
            printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.NORMAL);
        }

        // -----------------------------------------------
        // ガス料金情報
        nStartheight = printFeedDot((byte)5)[0];
        printSeikyuInfo("ガス料金", 0);
        nEndheight = printSeikyuInfo(mKSIB.getGasPay(), 1)[1];

        if(mKSIB.isPrnGasBaseKin()){
            // ガス基本料金・従量料金を分けて印字
            int nStartHeight = printFeedDot((byte)5)[0];
            printSeikyuInfo("　基本料金", 0);
            printSeikyuInfo(mKSIB.getKihonRyookin(), 1);

            if(mKSIB.isHybrid() && mKSIB.getGashyb() > 0) {
                printSeikyuInfo("　通常従量料金", 0);
            }else{
                printSeikyuInfo("　従量料金", 0);
            }
            printSeikyuInfo(mKSIB.getRyookin(), 1);

//            if(mKSIB.isHybrid() && mKSIB.getGashyb() > 0){
//                String str;
//                for ( int j = 0 ; j < Ko2fDat.getHyb_MAX() ; j++ ) {
//                    if (hybfDat.mCusef[j] == 1 && ko2fDat.mFee[j] != 0) {
//                        //カウンタ名称
//                        str = "　" + kensinData.getCounterName(j);
//                        printSeikyuInfo(str, 0);
//
//                        printSeikyuInfo(OtherUtil.printformat("#,###,##0", ko2fDat.mFee[j]) + "円", 1);
//                    }
//                }
//            }
//
            nEndheight = printFeedDot((byte)5)[1];
//            printRectangle(30, RECT_WIDTH - 30, nStartHeight, nEndheight, STYLE.NORMAL);
            printRectangle(0, RECT_WIDTH, nEndheight, nEndheight, STYLE.NORMAL);
        }

        if(mKSIB.isHybrid()) {
            //カウンター使用料
            if (mKSIB.getCounterUseKinDat().getUseKin() > 0 && mKSIB.getCounterUseKinDat().getUseSncode() > 0) {
                printFeedDot((byte)5);
                printSeikyuInfo("カウンタ使用料", 0);
                printSeikyuInfo(mKSIB.getCounterUseKinDat().getKin(), 1);
            }
        }

        // 消費税有り
        GasfDat gasfDat = userData.getGasfDat();
        if(gasfDat != null) {
            if (mKSIB.getGasfDat().getTaxDiv() == 3 && mKSIB.getGasTax() != 0) {
                printFeedDot((byte)5);
                printSeikyuInfo("ガス消費税", 0);
                printSeikyuInfo(OtherUtil.KingakuFormat(mKSIB.getGasTax()) + "円", 1);
                nEndheight = printFeedDot((byte)5)[1];
            }
        }

        if(mKSIB.getKnebFlg() == 1) {
            // 漢の値引き有り
            for (KnebDat knebDat : userData.getLstKnebDat()) {
                if (knebDat.getCode() > 0 &&  // 割引コード設定有
                        knebDat.getUmu() == 1 &&  // 割引フラグ有
                        knebDat.getRes() == 1 &&  // 割引実績有
                        knebDat.getKin() != 0) {  // 割引金額有
//                    WarifDat warifDat = InputDat.getWarifDat(mContext, knebDat.m_nCode);
                    //Hieunote
                    WarifDat warifDat = new WarifDat();
                    printSeikyuInfo(warifDat.m_strHinName.trim(), 0);
                    printSeikyuInfo( OtherUtil.KingakuFormat(knebDat.getKin() + knebDat.getTax()) + "円", 1);
                    nEndheight = printFeedDot((byte)5)[1];
                }
            }
        }

        // 還元額有り
        if (mKSIB.ifReduce() && mKSIB.getReduce() != 0) {
            printSeikyuInfo(mKSIB.getKangcontname(), 0);
            printSeikyuInfo(OtherUtil.KingakuFormat(mKSIB.getReduce()) + "円", 1);
            nEndheight = printFeedDot((byte)5)[1];
        }
        printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.BOLD);

        if(mKSIB.isPrintGasRyokinTotal()){
            createGasryokinTotal(null, mKSIB, 0);
        }
        checkPageModeLength();
    }

    /**
     * 灯油検針情報の印字
     *
     * @param kensinData    [in] {@link KensinData} 検針データ
     */
    private void createToyuKensinInfoBase(KensinData kensinData) {

        KotfDat kotfDat = kensinData.getKotfDat();
        int nStartheight = printFeedDot((byte)5)[0];
        int nEndheight;
        // -----------------------------------------------
        // 灯油指針情報
        printSeikyuInfo("今回指針", 0);
        printSeikyuInfo(OtherUtil.printformat(kotfDat.getNow_meter(), "######0.0", (byte) 1) + "  ", 1);

        String strItemName = "前回指針";
        boolean isMtChg = kotfDat.getBetw_meter() > 0;
        if(isMtChg){
            // メーター取替有
            strItemName = "取付指針";
            if(kotfDat.getMtchg_m() != 0 && kotfDat.getMtchg_d() != 0){
                strItemName += "(" + OtherUtil.DateFormat(kotfDat.getMtchg_m(), kotfDat.getMtchg_d(), true) + ")";
            }
        }
        else{
            if (kotfDat.getPuse_month() != 0 && kotfDat.getPuse_day() != 0) {
                strItemName += "(" + OtherUtil.DateFormat(kotfDat.getPuse_month(), kotfDat.getPuse_day(), true) + ")";
            }
        }
        printSeikyuInfo(strItemName, 0);
        printSeikyuInfo(OtherUtil.printformat(kotfDat.getPre_meter(), "######0.0", (byte) 1) + "  ", 1);

        if(isMtChg){
            // メーター取替有は中間使用量を印字
            printSeikyuInfo("中間使用量", 0);
            printSeikyuInfo(OtherUtil.printformat(kotfDat.getBetw_meter(), "######0.0", (byte) 1) + "  ", 1);
        }

        printSeikyuInfo("使用量", 0);
        printSeikyuInfo(OtherUtil.printformat(kotfDat.getLoil_use(), "###,##0.0", (byte) 1) + "  L", 1);
        nEndheight = printFeedDot((byte)10)[1];
        printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.NORMAL);
        nEndheight = nStartheight;

        if(kotfDat.getPuse_month() != 0) {
            nStartheight = printFeedDot((byte)5)[0];
            printSeikyuInfo("前回使用量", 0);
            nEndheight = printSeikyuInfo(OtherUtil.printformat(kotfDat.getPre_use(), "###,##0.0", (byte) 1) + "  L", 1)[1];
        }

        if(isMtChg){
            // メーター取替有は前回取外し指針と前回指針を印字
            nStartheight = printFeedDot((byte)5)[0];
            strItemName = "取外指針";
            if (kotfDat.getMtchg_m() != 0 && kotfDat.getMtchg_d() != 0) {
                strItemName += "(" + OtherUtil.DateFormat(kotfDat.getMtchg_m(), kotfDat.getMtchg_d(), true) + ")";
            }
            printSeikyuInfo(strItemName, 0);
            printSeikyuInfo(OtherUtil.printformat(kotfDat.getMtchg_oldss(), "######0.0", (byte) 1) + "  ", 1);

            strItemName = "前回指針";
            if (kotfDat.getPuse_month() != 0 && kotfDat.getPuse_day() != 0) {
                strItemName += "(" + OtherUtil.DateFormat(kotfDat.getPuse_month(), kotfDat.getPuse_day(), true) + ")";
            }
            printSeikyuInfo(strItemName, 0);
            nEndheight = printSeikyuInfo(OtherUtil.printformat(kotfDat.getMtchg_zknss(), "######0.0", (byte) 1) + "  ", 1)[1];
        }
        if(nStartheight != nEndheight) {
            nEndheight = printFeedDot((byte) 10)[1];
            printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.NORMAL);
        }

        // -----------------------------------------------
        // 灯油料金情報
        nStartheight = printFeedDot((byte)5)[0];
        printSeikyuInfo("灯油メーター料金", 0);
        nEndheight = printSeikyuInfo(OtherUtil.printformat("#,###,##0", kotfDat.getFee()) + "円", 1)[1];

        if(kensinData.isToyuKinSep()){
            // 灯油基本料金・従量料金を分けて印字
            int nStartHeight = printFeedDot((byte)5)[0];
            printSeikyuInfo("　基本料金", 0);
            printSeikyuInfo(OtherUtil.printformat("#,###,##0", kotfDat.getLoil_base() / 100) + "円", 1);

            printSeikyuInfo("　従量料金(単価" + OtherUtil.printformat("#,###", kensinData.getLoilUnit())+ "円)", 0);
            printSeikyuInfo(OtherUtil.printformat("#,###,##0", kotfDat.getFee() - (kotfDat.getLoil_base() / 100)) + "円", 1);

            nEndheight = printFeedDot((byte)5)[1];
            printRectangle(30, RECT_WIDTH - 30, nStartHeight, nEndheight, STYLE.NORMAL);
            printRectangle(0, RECT_WIDTH, nEndheight, nEndheight, STYLE.NORMAL);
        }

        // 消費税有り
        if(kotfDat.getCon_tax() != 0) {
            printFeedDot((byte)5);
            printSeikyuInfo("灯油消費税", 0);
            printSeikyuInfo(OtherUtil.printformat("#,###,##0", kotfDat.getCon_tax()) + "円", 1);
            nEndheight = printFeedDot((byte)5)[1];
        }

        printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.BOLD);

        checkPageModeLength();
    }

    /**
     * 金額関係印刷データの生成.
     *
     * @param mKI           [in] {@link KinInfoDat} 検針印刷データ
     * @throws MException   印刷データ生成時にエラーがあった場合に発生
     */
    private void createKinInfo(KinInfoDat mKI) throws MException {
        int nStartheight = printFeedDot((byte)5)[0];
        int nEndheight = nStartheight;
        boolean isPrint = false;
        
        if(!mKI.isNyukinOnly()){
            // 前月残高
            if(mKI.ifDemand() && mKI.getPreReceipt() != 0){
                printSeikyuInfo(mKI.getsZanTitle(), 0);
                nEndheight = printSeikyuInfo(OtherUtil.KingakuFormat(mKI.getPreReceipt()) + "円", 1)[1];
            }
            
            if (mKI.ifProceeds()) { // その他売上
                if (mKI.getHmDay() != 0) {
                    printSeikyuInfo("本日お買い上げ額", 0);
                    nEndheight = printSeikyuInfo(OtherUtil.KingakuFormat(mKI.getHmDay()) + "円", 1)[1];
                }
                if (mKI.getHmMonth() != 0) {
                    printSeikyuInfo("当月お買い上げ額", 0);
                    nEndheight = printSeikyuInfo(OtherUtil.KingakuFormat(mKI.getHmMonth()) + "円", 1)[1];
                }
            }
    
            // 当月入金額
            if (mKI.getTReceipt() != 0) {
                printSeikyuInfo("当月入金額", 0);
                nEndheight = printSeikyuInfo(OtherUtil.KingakuFormat(mKI.getTReceipt()) + "円", 1)[1];
            }
            // 当月調整額
            if(mKI.getTAdjust() != 0){
                printSeikyuInfo("当月調整額", 0);
                nEndheight = printSeikyuInfo(OtherUtil.KingakuFormat(mKI.getTAdjust()) + "円", 1)[1];
            }

            if(nStartheight != nEndheight){
                nEndheight = printFeedDot((byte)10)[1];
                printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.BOLD);
                nStartheight = printFeedDot((byte)5)[0];
            }

            // 今回請求額
            if(mKI.isFuriDemand()){
                printSeikyuInfo("今回請求予定額", 0);
                printSeikyuInfo(OtherUtil.KingakuFormat(mKI.getReceipt()) + "円", 1);
                nEndheight = printFeedDot((byte)10)[1];
            }
            else {
                printSeikyuInfoLarge("今回請求額", 0);
                printSeikyuInfoLarge(OtherUtil.KingakuFormat(mKI.getReceipt()) + "円", 1);
                adjustHeight_13();
                nEndheight = printFeedDot((byte)10)[1];
            }
            printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.BOLD);
            nStartheight = printFeedDot((byte)5)[0];

            if(!mKI.getIraimsg().isEmpty()){
                printNormal(mKI.getIraimsg());
                nEndheight = printFeedDot((byte)10)[1];
                printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.BOLD);
            }
            // -----------------------------------------------
            // 当日入金・調整額
            if (mKI.getChosei() != 0) {
                isPrint = true;
                printSeikyuInfo(mKI.getsChoseiTitle(), 0);
                nEndheight = printSeikyuInfo(OtherUtil.KingakuFormat(mKI.getChosei()) + "円", 1)[1];
            }
        }
        String strLine;
        if (mKI.getNyukin() != 0) {
            isPrint = true;
            if (mKI.getAzukarikin() == mKI.getNyukin()) { // 13.02.12
                strLine = "本日入金額";
            }
            else {
                strLine = "本日お預かり金額";
            }
            printSeikyuInfo(strLine, 0);
            nEndheight = printSeikyuInfo(mKI.getAzukarikin() + "円", 1)[1];
        }
        int nOtsuri = mKI.getAzukarikin() - mKI.getNyukin(); // 13.02.12
        if (nOtsuri > 0) {
            printSeikyuInfo("おつり", 0);
            nEndheight = printSeikyuInfo(OtherUtil.printformat("#,###,##0", nOtsuri) + "円", 1)[1];
        }

        if (nStartheight != nEndheight) {
            nEndheight = printFeedDot((byte)10)[1];
            printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.BOLD);
        }
        // -----------------------------------------------
        // 差引残高
        if (mKI.getKZandaka() != 0 && isPrint ) {
            nStartheight = printFeedDot((byte)5)[0];
            printSeikyuInfoLarge("差引残高", 0);
            printSeikyuInfoLarge(OtherUtil.KingakuFormat(mKI.getLZandaka()) + "円", 1);
            adjustHeight_13(); // 全角改行の高さ調整  // 13.03.18
            nEndheight = printFeedDot((byte) 10)[1];
            printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.BOLD);
        }
        checkPageModeLength();
    }

    @Override
    public void createHmInfo(UserData userData) throws MException {
        SysfDat sysfDat = userData.getSysfDat();
        if(sysfDat.isLtas()){
//            createHmInfoLocalLtas(userData);
        }
        else {
            createHmInfoLocal(userData);
        }
    }

    /**
     * 保安情報の印刷データを作成する。
     * 
     * @param strHoan    [in] String 保安情報
     */
    public void createHoanInfo(boolean isNyukinMode, String strHoan) {
        if(isNyukinMode){
            return;
        }

        int nStartheight = printFeedDot((byte)5)[0];
        int nEndheight;

        print("* * * 保　安　点　検 * * *", CHARSIZE.SMALL, LAYOUT.CENTER);
        for (int i = 0; i < HOAN_ITEMS.length; i++) {
            if (i % 2 == 0) {
                // 奇数
                printHoanInfo(HOAN_ITEMS[i], 0);
                printHoanInfo("[" + strHoan.charAt(i) + "]", 1);
            }
            else {
                // 偶数
                printHoanInfo(HOAN_ITEMS[i], 2);
                printHoanInfo("[" + strHoan.charAt(i) + "]", 3);
            }
        }
        nEndheight = printFeedDot((byte)10)[1];
        printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.NORMAL);

        printFeedDot((byte)10);
        printNormal("基準に適合しない場合には速やかに措置を");
        printNormal("講じる必要があります。");
        printLF(1);
        checkPageModeLength();
    }

    @Override
    public void createBank(UserData userData) {
        KokfDat kokfDat = userData.getKokfDat();
        Sy2fDat sy2fDat = userData.getSy2fDat();
        KouserDat kouserDat = userData.getKouserDat();
        int nStartHeight = printFeedDot((byte)5)[0];
        int nEndHeight = nStartHeight;

        // 前回引き落とし結果
        if(sy2fDat.getSysOption()[SysOption.PRINT_JIFURI.getIdx()] != 0 && kokfDat.getTransFee() != 0 && kokfDat.getTransFee() < 50000L) {
            printNormal("下記の金額をご指定の預金口座より");
            printNormal("自動振替させていただきました。");
            printSeikyuInfo("前回引落日", 0);
            printSeikyuInfo(String.format(Locale.JAPANESE, "   %02d月%02d日", kokfDat.getTransMonth(), kokfDat.getTransDate()), 1);
            printSeikyuInfo("前回引落額", 0);
            nEndHeight = printSeikyuInfo(OtherUtil.printformat("#,###,##0", kokfDat.getTransFee()) + "円", 1)[1];
        }
        // 依頼中
        if (sy2fDat.getSysOption()[SysOption.PRINT_JIFURI.getIdx()] != 0 && (kouserDat.getIraiStat() == 1 || kouserDat.getIraiStat() == 2 || kouserDat.getIraiStat() == 3)) {
            printSeikyuInfo("引落処理中", 0);
            printSeikyuInfo("", 1);
            printSeikyuInfo(String.format(Locale.JAPANESE, "%02d月%02d日", kouserDat.getIraiMonth(), kouserDat.getIraiDay()), 0);
            nEndHeight = printSeikyuInfo(OtherUtil.printformat("#,###,##0", kouserDat.getIraiKin()) + "円", 1)[1];
        }

        // 次回予定日
        if (sy2fDat.getJifuriNext() == 1 && kouserDat.getNextTransYear() != 0 && kouserDat.getNextTransMonth() != 0 && kouserDat.getNextTransDay() != 0) {
            printSeikyuInfo("次回引落予定日", 0);
            nEndHeight = printSeikyuInfo(String.format(Locale.JAPANESE, "   %02d月%02d日", kouserDat.getNextTransMonth(), kouserDat.getNextTransDay()), 1)[1];
        }

        if(nStartHeight != nEndHeight){
            printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.NORMAL);
            printLF(1);
        }
        checkPageModeLength();
    }

    @Override
    public void createPoint(UserData userData) {
        Sy2fDat sy2fDat = userData.getSy2fDat();
        KokfDat kokfDat = userData.getKokfDat();
        int nStartHeight = printFeedDot((byte)5)[0];
        int nEndHeight;

        if(sy2fDat.getPntVer() > 0 && kokfDat.getPoint() > 0){
            if(sy2fDat.getPntDatName() == null || sy2fDat.getPntDatName().trim().isEmpty()){
                // ポイント名が空の場合は印字しない
                return;
            }
            printSeikyuInfo(OtherUtil.nullToString(sy2fDat.getPntDatName()), 0);
            printSeikyuInfo(OtherUtil.printformat("#,###,##0", kokfDat.getPoint()) + " P", 1);
            nEndHeight = printFeedDot((byte)5)[1];
            printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.NORMAL);
            printLF(1);
            checkPageModeLength();
        }
    }

    /**
     * 料金表(A式)の印刷データ生成.
     *
     * @param kensinData    [in] {@link KensinData} 検針印刷データ
     * @return  int 印刷後の高さ
     */
    protected int printGasryokinA(KensinData kensinData) {
        long nPrnGasKin = kensinData.getGasBaseKin() + kensinData.getFacilityKin();
        long nNextBaseKin;
        double nWorkKin;
        GasfDat gasfDat = kensinData.getGasfDat();
        List<GstpDat> lstGstpDat = gasfDat.getLstGstpDat();
        Ko2fDat ko2fDat = kensinData.getKo2fDat();

        int nStartIdx = kensinData.getStartIdx();
        long nGasTotalKin = (int)(kensinData.getGasPay() - kensinData.getGasBaseKin() / 1000 - kensinData.getFacilityKin() / 1000);

        GstpDat gstpDat;
        if(gasfDat.getSum() != 3){
            // 契約単価は料金式を持たない
            gstpDat = lstGstpDat.get(nStartIdx);
        }
        else {
            gstpDat = new GstpDat();
            gstpDat.setUplimit(999999);
            gstpDat.setAdd((int)kensinData.getGasAddKin() * 10);
            gstpDat.setBase((int)kensinData.getGasBaseKin());
        }

        if(kensinData.isHybrid() && ko2fDat.getGashyb() != 0) {
            printNormal("通常使用分従量料金");
        }else{
            printNormal("従量料金");
        }

        long nAddKin = gstpDat.getAdd();
        if(gasfDat.getSum() == 2 || nAddKin > 0){
            nAddKin += gasfDat.getChoTanka();
        }
        nAddKin = OtherUtil.hasCom(nAddKin, gasfDat.getFrac1Add(), gasfDat.getFrac1Mult(), 10000.);
        nAddKin += (nAddKin * gasfDat.getRiseFall() / 1000L);

        // add new start
        long nSur = kensinData.getNowUse();
        if(kensinData.isHybrid() && ko2fDat.getGashyb() > 0){
            // ハイブリッドの場合は通常カウンタ使用量を設定
            nSur = kensinData.getNorSr();
            nGasTotalKin = (ko2fDat.getNorKin() - kensinData.getGasBaseKin() / 1000 - kensinData.getFacilityKin() / 1000);
        }

        long nGasStepKin = (long)(nAddKin * gstpDat.getUplimit() * 0.00001 + 0.0001);
        if(kensinData.isSingleStep() || nSur <= gstpDat.getUplimit()) {
            nGasStepKin = nGasTotalKin;
        }
        else {
            if(nAddKin == 0){
                // 単価無し
                nGasStepKin = 0;
                if(gasfDat.getSum() == 1 && nStartIdx < gasfDat.getLine()){
                    // 通常料金式では、次のステップの基本料金迄の金額を計算
                    nWorkKin = Integer.toUnsignedLong(lstGstpDat.get(nStartIdx + 1).getBase());
                    nWorkKin = OtherUtil.hasCom(nWorkKin, gasfDat.getFrac1Add(), gasfDat.getFrac1Mult(), 10000.);
                    nWorkKin *= 1000 + gasfDat.getRiseFall();
                    nNextBaseKin = (long)(OtherUtil.hasCom(nWorkKin, gasfDat.getFrac2Add(), gasfDat.getFrac2Mult(), 10000000.) / 10000000.);
                    // ステップ間の金額 = 次ステップの基本料金 - 印字した前までの金額
                    nGasStepKin = nNextBaseKin - nPrnGasKin / 1000;
                }
            }
        }
        nGasTotalKin -= nGasStepKin;
        nPrnGasKin += nGasStepKin * 1000;

        int nYpos = printGasRyokinStep_A(1, gstpDat.getUplimit(), nAddKin, nGasStepKin);

        if(!kensinData.isSingleStep() && nSur > gstpDat.getUplimit()) {
            nStartIdx++;
            for (int i = nStartIdx; i < lstGstpDat.size(); i++) {
                GstpDat prevGstp = gstpDat;
                gstpDat = lstGstpDat.get(i);

                // ステップの単価を印字(増減率を考慮)
                nAddKin = gstpDat.getAdd();
                if(gasfDat.getSum() == 2 || nAddKin > 0){
                    nAddKin += gasfDat.getChoTanka();
                }
                nAddKin = OtherUtil.hasCom(nAddKin, gasfDat.getFrac1Add(), gasfDat.getFrac1Mult(), 10000.);
                nAddKin += nAddKin * gasfDat.getRiseFall() / 1000L;

                if (nSur < gstpDat.getUplimit()) {
                    nGasStepKin = nGasTotalKin;
                } else {
                    if(nAddKin == 0){
                        // 単価無し
                        nGasStepKin = 0;
                        if(gasfDat.getSum() == 1 && i < gasfDat.getLine()){
                            // 通常料金式では、次のステップの基本料金迄の金額を計算
                            nWorkKin = Integer.toUnsignedLong(lstGstpDat.get(i + 1).getBase());
                            nWorkKin = OtherUtil.hasCom(nWorkKin, gasfDat.getFrac1Add(), gasfDat.getFrac1Mult(), 10000.);
                            nWorkKin *= 1000 + gasfDat.getRiseFall();
                            nNextBaseKin = (long)(OtherUtil.hasCom(nWorkKin, gasfDat.getFrac2Add(), gasfDat.getFrac2Mult(), 10000000.) / 10000000.);
                            // ステップ間の金額 = 次ステップの基本料金 - 印字した前までの金額
                            nGasStepKin = nNextBaseKin - nPrnGasKin / 1000;
                        }
                    }
                    else {
                        nGasStepKin = (long) (nAddKin * (gstpDat.getUplimit() - prevGstp.getUplimit()) * 0.00001 + 0.0001);
                    }
                    nGasTotalKin -= nGasStepKin;
                }
                nPrnGasKin += nGasStepKin * 1000;
                // print
                nYpos = printGasRyokinStep_A(prevGstp.getUplimit() + 1, gstpDat.getUplimit(), nAddKin, nGasStepKin);

                if (nSur <= gstpDat.getUplimit()) {
                    break;
                }
            }
        }

        //HieuNote chưa làm tới
//        try {
//            nYpos = printGasryokin_Hybrid(nYpos, kensinData, 0);
//        } catch (MException e) {
//            e.printStackTrace();
//        }

        return nYpos;
    }

    /**
     * 料金表(O式)印字データ生成.
     *
     * @param kensinData    [in] {@link KensinData} 検針印刷データ
     * @return  int 印刷後の高さ
     */
    protected int printGasryokinO(KensinData kensinData) {
        GasfDat gasfDat = kensinData.getGasfDat();
        Ko2fDat ko2fDat = kensinData.getKo2fDat();
        List<GstpDat> lstGstpDat = gasfDat.getLstGstpDat();
        int nStartIdx = kensinData.getStartIdx();
        GstpDat gstpDat;
        if(gasfDat.getSum() != 3){
            // 契約単価はガス料金を持たない
            gstpDat = lstGstpDat.get(nStartIdx);
        }
        else {
            gstpDat = new GstpDat();
            gstpDat.setUplimit(999999);
            gstpDat.setBase((int)kensinData.getGasBaseKin());
            gstpDat.setAdd((int)kensinData.getGasAddKin() * 10);
        }

        if(kensinData.isHybrid() && ko2fDat.getGashyb() != 0) {
            printNormal("通常使用分従量料金");
        }else{
            printNormal("従量料金");
        }

        StringBuilder strStep = new StringBuilder();
        strStep.append("　　　　");
        printRyokinSikiO(strStep.toString(), 0);

        strStep = new StringBuilder();
        if(kensinData.isSingleStep()){
            strStep.append("一律");
        }
        else {
            strStep.append(OtherUtil.printformat(1, "####0.0", (byte) 1))
                    .append("m3 ～ ")
                    .append(OtherUtil.printformat(gstpDat.getUplimit(), "####0.0", (byte) 1))
                    .append("m3");
        }
        printRyokinSikiO(strStep.toString(), 1);

        long nAddKin = gstpDat.getAdd();
        if(gasfDat.getSum() == 2 || nAddKin > 0){
            nAddKin += gasfDat.getChoTanka();
        }
        nAddKin = OtherUtil.hasCom(nAddKin, gasfDat.getFrac1Add(), gasfDat.getFrac1Mult(), 10000.);
        if (gasfDat.getRiseFall() != 0) {
            nAddKin += (nAddKin * gasfDat.getRiseFall() / 1000L);
        }

        // add new start
        long nSur = kensinData.getNowUse();
        if(kensinData.isHybrid() && ko2fDat.getGashyb() > 0){
            // ハイブリッドの場合は通常カウンタ使用量を設定
            nSur = kensinData.getNorSr();
        }

        strStep = new StringBuilder();
        strStep.append(OtherUtil.printformat(nAddKin, "#,##0.00", (byte) 4)).append("円/m3");
        int nYpos = printRyokinSikiO(strStep.toString(), 2)[1];

        if(!kensinData.isSingleStep() && nSur > gstpDat.getUplimit()) {
            nStartIdx++;
            for (int i = nStartIdx; i < lstGstpDat.size(); i++) {
                GstpDat prevGstpDat = gstpDat;
                gstpDat = lstGstpDat.get(i);

                nAddKin = gstpDat.getAdd();
                if(gasfDat.getSum() == 2 || nAddKin > 0){
                    nAddKin += gasfDat.getChoTanka();
                }
                nAddKin = OtherUtil.hasCom(nAddKin, gasfDat.getFrac1Add(), gasfDat.getFrac1Mult(), 10000.);
                if (gasfDat.getRiseFall() != 0) {
                    nAddKin += (nAddKin * gasfDat.getRiseFall() / 1000L);
                }

                nYpos = printGasRyokinStep_O(prevGstpDat.getUplimit() + 1, gstpDat.getUplimit(), nAddKin);

                if (nSur <= gstpDat.getUplimit()) {
                    break;
                }
            }
        }

        // Hyc printing
        try {
            nYpos = printGasryokin_Hybrid(nYpos, kensinData, 1);
        } catch (MException e) {
            e.printStackTrace();
        }

        return nYpos;

    }

    @Override
    public void createCnComment(KensinData kensinData) {
        if(kensinData.isCnp()) {
            int nStartYpos = printFeedDot((byte) 5)[0];
            int nEndYpos;
            printFeedDot((byte)5);
            CnpCusDat cnpCusDat = kensinData.getCnpCusDat();

            if (cnpCusDat.getCnpMembers() > 0) {
                // 本会員は会員ID印字
                printSeikyuInfo("CN会員ID", 0);
                printSeikyuInfo(OtherUtil.nullToString(cnpCusDat.getCnpMembersId()), 1);
            }
            printSeikyuInfo("前月獲得ポイント", 0);
            printSeikyuInfo(OtherUtil.format("#,###,##0", cnpCusDat.getCnpZpoint()), 1);
            if (cnpCusDat.getCnpMembers() > 0) {
                // 本会員
                printSeikyuInfo("利用可能ポイント", 0);
            } else if (cnpCusDat.getCnpTemp() > 0) {
                // 仮会員
                printSeikyuInfo("お試しポイント", 0);
            }
            printSeikyuInfo(OtherUtil.format("#,###,##0", cnpCusDat.getCnpPoint()), 1);
            if (cnpCusDat.getCnpMembers() == 0 && cnpCusDat.getCnpTemp() > 0) {
                // 仮会員はコメントを追加
                for(String strCmt : kensinData.getCnpCmt()){
                    printSeikyuInfo(strCmt, 0);
                }
            }

            List<String> lstCmt = kensinData.getCnpCmt();
            for (String strCmt : lstCmt) {
                printNormal(strCmt);
            }
            nEndYpos = printFeedDot((byte) 5)[1];
            printRectangle(0, RECT_WIDTH, nStartYpos, nEndYpos, STYLE.NORMAL);
            printLF(1);
        }
    }

    @Override
    public void createMiyaPoint(UserData userData) {
        Sy2fDat sy2fDat = userData.getSy2fDat();
        if(sy2fDat.getMiyanoFlg() == 0 ||
                (sy2fDat.getSysOption()[SysOption.PRINT_MIYANO_GET.getIdx()] == 0 &&
                        sy2fDat.getSysOption()[SysOption.PRINT_MIYANO_USE.getIdx()] == 0 &&
                        sy2fDat.getSysOption()[SysOption.PRINT_MIYANO_RUI.getIdx()] == 0)){
            // 宮野式ポイント未使用の場合
            // もしくは印字フラグが全てOFFの場合
            // は印字しない。
            return;
        }
        KouserDat kouserDat = userData.getKouserDat();

        int nStartYpos = printFeedDot((byte)5)[0];
        int nEndYpos;
        if(sy2fDat.getSysOption()[SysOption.PRINT_MIYANO_GET.getIdx()] == 1){
            // 獲得ポイント
            printSeikyuInfo("獲得ポイント", 0);
            printSeikyuInfo(OtherUtil.format("#,###,##0", kouserDat.getMiyanoGetpnt()) + "P", 1);
        }
        if(sy2fDat.getSysOption()[SysOption.PRINT_MIYANO_USE.getIdx()] == 1){
            // 使用ポイント
            printSeikyuInfo("使用ポイント", 0);
            printSeikyuInfo(OtherUtil.format("#,###,##0", kouserDat.getMiyanoUsepnt()) + "P", 1);
        }
        if(sy2fDat.getSysOption()[SysOption.PRINT_MIYANO_RUI.getIdx()] == 1){
            // 累計ポイント
            printSeikyuInfo("現在ポイント", 0);
            printSeikyuInfo(OtherUtil.format("#,###,##0", kouserDat.getMiyanoZanpnt()) + "P", 1);
        }
        nEndYpos = printFeedDot((byte)10)[1];
        printRectangle(0, RECT_WIDTH, nStartYpos, nEndYpos, STYLE.NORMAL);
        printLF(1);
        checkPageModeLength();
    }


    public void createGasryokinSiki(PrintImageList printImageList, KensinData kensinData, int nYpos) {
        GasfDat gasfDat = kensinData.getGasfDat();
        if(gasfDat != null && kensinData.isVisibleGas()) {
            if (kensinData.isPrintGasRyokinSiki()) {
                if (gasfDat.getSum() != 4) {
                    int nStartHeight = printFeedDot((byte) 5)[0];
                    String strTitle = "ガス料金内訳";
                    if (gasfDat.getTaxDiv() == 2) {
                        strTitle += "（消費税込み）";
                    } else if (gasfDat.getTaxDiv() == 3) {
                        strTitle += "（消費税抜き）";
                    }
                    int nEndHeight = printNormal(strTitle)[1];
                    printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.NORMAL);
                    printFeedDot((byte) 5);
                    printSeikyuInfo("基本料金", 0);
                    nEndHeight = printSeikyuInfo(OtherUtil.printformat("##,###,##0", kensinData.getGasBaseKin() / 1000) + "円  ", 1)[1];
                    printRectangles(RECT_RYOKIN_SIKI, nStartHeight, nEndHeight);
                    if (kensinData.isPrintGasFacilityKin()) {
                        printFeedDot((byte) 5);
                        printSeikyuInfo("設備料金", 0);
                        printSeikyuInfo(OtherUtil.printformat("##,###,##0", kensinData.getFacilityKin() / 1000) + "円  ", 1);
                        nEndHeight = printFeedDot((byte) 5)[1];
                        printRectangles(RECT_RYOKIN_SIKI, nStartHeight, nEndHeight);
                    }
                    printFeedDot((byte) 5);

                    // ガス料金式印字
                    switch (kensinData.getPrintGasRyokinSikiPtn()) {
                        case 0: // 秋元式
                            nEndHeight = printGasryokinA(kensinData);
                            break;
                        case 1: // 大口式
                            nEndHeight = printGasryokinO(kensinData);
                            break;
                    }
                    printRectangles(RECT_RYOKIN_SIKI, nStartHeight, nEndHeight);

                    if (kensinData.getPrintGasRyokinSikiPtn() == 0) {
                        printFeedDot((byte) 5);
                        printSeikyuInfo("合計", 0);
                        nEndHeight = printSeikyuInfo(OtherUtil.printformat("##,###,##0", kensinData.getGasTotalKinWithoutTax()) + "円  ", 1)[1];
                    }
                    printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.BOLD);
                    printFeedDot((byte) 10);
                }
            }

            //HieuNote
//            // ガス料金コメントの印字
//            printGasryokinComment(gasfDat.mGextDat);
            checkPageModeLength();
        }
    }


    protected int[] printRyokinSikiA(String strPrint, int nPos){
        return printHPInfo(new byte[]{0, 24, 34}, strPrint, nPos);
    }

    protected int[] printRyokinSikiO(String strPrint, int nPos){
        return printHPInfo(new byte[]{2, 10, 33}, strPrint, nPos);
    }

    protected int[] printHybComment(String strPrint, int nPos){
        return printHPInfo(new byte[]{0, 16, 26, 36}, strPrint, nPos);
    }
    protected int[] printHybCommentKin(String strPrint, int nPos){
        return printHPInfo(new byte[]{0, 14, 24, 36}, strPrint, nPos);
    }

    /**
     * ハイブリッドガス料金式の印字(秋元式)
     *
     * @param kensinData        [in] {@link KensinData} 印字データ
     * @return int 印字後の伝票高さ
     */
    protected int printGasryokin_Hybrid(int nYpos, KensinData kensinData, int nType) throws MException {

//        Ko2fDat ko2fDat = kensinData.getKo2fDat();
//        if(kensinData.isHybrid() && ko2fDat.mGashyb > 0) {
//            HybfDat hybf = kensinData.getHybfDat(); //ﾊｲﾌﾞﾘｯﾄﾞﾒｰﾀｰ料金表
//            long nAddKin;
//            double nTotalKin;
//            int nStep;
//            long nGasTotal;
//            boolean bSingleStep;
//            String str;
//
//            for (int j = 0; j < Ko2fDat.kHyb_MAX; j++) {
//                if (hybf.mCusef[j] == 1 && ko2fDat.mFee[j] != 0) {
//                    nStep = 0;
//                    //カウンタ名称
//                    str = kensinData.getCounterName(j);
//                    printNormal(str);
//
//                    nGasTotal = ko2fDat.mFee[j];
//                    nAddKin = hybf.mGasAdd[j][nStep] * 10;
//                    long nSur = kensinData.getHybCntSr(j);
//                    if (nSur <= hybf.mGasLimit[j][nStep]) {
//                        // 最終ステップのガス料金は残りの金額
//                        nTotalKin = nGasTotal;
//                        bSingleStep = true;
//                    } else {
//                        nTotalKin = nAddKin * hybf.mGasLimit[j][nStep];
//                        nTotalKin = nTotalKin * 0.00001 + 0.0001;
//                        nGasTotal -= (long) nTotalKin;
//                        bSingleStep = false;
//                    }
//
//                    // ステップの下限値～上限値を印字
//                    if(nType == 0) {
//                        // A式
//                        nYpos = printGasRyokinStep_A(1, hybf.mGasLimit[j][nStep], nAddKin, (long)nTotalKin);
//                    }
//                    else {
//                        // O式
//                        nYpos = printGasRyokinStep_O(1, hybf.mGasLimit[j][nStep], nAddKin);
//                    }
//
//                    if (!bSingleStep) {
//                        nStep++;
//                        for (; nStep < 10; nStep++) {
//                            if (hybf.mGasLimit[j][nStep] == 0L) {
//                                // ステップ上限が0の場合
//                                // 料金表の印字を終了
//                                break;
//                            }
//                            // ガス料金の従量料金
//                            long nStepSur = hybf.mGasLimit[j][nStep] - hybf.mGasLimit[j][nStep - 1];
//                            nAddKin = hybf.mGasAdd[j][nStep] * 10;
//                            if (nSur <= hybf.mGasLimit[j][nStep]) {
//                                // 最終ステップのガス料金は残りの金額
//                                nTotalKin = nGasTotal;
//                            } else {
//                                nTotalKin = nAddKin * nStepSur;
//                                nTotalKin = nTotalKin * 0.00001 + 0.0001;
//                                nGasTotal -= (long) nTotalKin;
//                            }
//
//                            // ステップの下限値～上限値を印字
//                            if (nType == 0) {
//                                // A式
//                                nYpos = printGasRyokinStep_A(hybf.mGasLimit[j][nStep - 1] + 1, hybf.mGasLimit[j][nStep], nAddKin, (long)nTotalKin);
//                            }
//                            else {
//                                // O式
//                                nYpos = printGasRyokinStep_O(hybf.mGasLimit[j][nStep - 1] + 1, hybf.mGasLimit[j][nStep], nAddKin);
//                            }
//
//                            if (nSur <= hybf.mGasLimit[j][nStep]) {
//                                // ガス使用量がステップ上限より小さい場合
//                                // 料金式の印字を終了
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//
//            //カウンター使用料
//            nYpos = printCounterUseKin(nYpos, ko2fDat, hybf);
//        }
//        checkPageModeLength();

        return nYpos;
    }

    /**
     * カウンタ使用料
     *
     * @return  int 印字後の伝票高さ
     */
    private int printCounterUseKin(int nYpos, CounterUseKinDat mCUKD, boolean isRect, int nType) {
        int nEndPos = nYpos;

        if (mCUKD.getUseKin() > 0 && mCUKD.getUseSncode() > 0) {

            printFeedDot((byte)5);

            printSeikyuInfo("カウンタ使用料", 0);
            nEndPos = printSeikyuInfo(mCUKD.getKin(), 1)[1];

            printRectangles(RECT_RYOKIN_SIKI, nYpos, nEndPos);
        }

        checkPageModeLength();
        return nEndPos;
    }

    @Override
    public void createGasryokinTotal(PrintImageList printImageList, KensinInfoBaseDat mKSIB, int nYpos) {
        int nStartHeight = printFeedDot((byte)5)[0];
        printSeikyuInfo("ガス料金総額", 0);
        printSeikyuInfo(mKSIB.getGasTotalKin(), 1);
        int nEndHeight = printFeedDot((byte)5)[1];
        printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.BOLD);
        checkPageModeLength();
    }

    @Override
    public void createHiwariComment(PrintImageList printImageList, KensinData kensinData, int nYpos) {
        int nStartHeight = printFeedDot((byte)5)[0];
        int nEndHeight = nStartHeight;
        if(!TextUtils.isEmpty(OtherUtil.getClearString(kensinData.getHiwariComment_0()))){
            nEndHeight = printNormal(kensinData.getHiwariComment_0())[1];
        }
        if(!TextUtils.isEmpty(OtherUtil.getClearString(kensinData.getHiwariComment_1()))){
            nEndHeight = printNormal(kensinData.getHiwariComment_1())[1];
        }
        // 矩形設定
        if(nStartHeight != nEndHeight){
            nEndHeight = printFeedDot((byte)10)[1];
            printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.NORMAL);
        }
        printLF(1);
        checkPageModeLength();
    }

    @Override
    public int createZenYearkenSr(PrintImageList printImageList, KensinInfoBaseDat mKSIB, int nYpos) {
        if(!mKSIB.isPrintZenYearKenSr()){   //KSIB.bIsPrintZenYearKenSr
            return nYpos;
        }
        printSeikyuInfo("前年同月使用量", 0);
        nYpos = printSeikyuInfo(mKSIB.getZenYearKenSr(), 1)[1];
        checkPageModeLength();
        return nYpos;
    }

    /**
     * 料金式(A式)の印刷データ生成.
     *
     * @param dLowVal   [in] double 下限値
     * @param dUpVal    [in] double 上限値
     * @param lAddKin   [in] long   加算値
     * @param lTotalKin [in] long   合計金額
     * @return  int 印刷後の高さ
     */
    protected int printGasRyokinStep_A(double dLowVal, double dUpVal, long lAddKin, long lTotalKin){
        // ステップの下限値～上限値を印字
        String strStep = OtherUtil.printformat((int) dLowVal, "####0.0", (byte) 1) +
                "→" +
                OtherUtil.printformat((int) dUpVal, "####0.0", (byte) 1) +
                "m3 単価";
        printRyokinSikiA(strStep, 0);

        printRyokinSikiA(OtherUtil.format("#,##0.00", lAddKin, (byte) 4) + "円", 1);

        return printRyokinSikiA(OtherUtil.printformat("#,###,##0", lTotalKin) + "円", 2)[1];
    }

    /**
     * 料金式(O式)の印刷データ生成.
     *
     * @param dLowVal   [in] double 下限値
     * @param dUpVal    [in] double 上限値
     * @param lAddKin   [in] long   加算値
     * @return  int 印刷後の高さ
     */
    protected int printGasRyokinStep_O(double dLowVal, double dUpVal, long lAddKin){
        // ステップの下限値～上限値を印字
        StringBuilder strStep = new StringBuilder();
        strStep.append("　　　　");
        printRyokinSikiO(strStep.toString(), 0);
        strStep = new StringBuilder();

        strStep.append(OtherUtil.printformat((int)dLowVal, "####0.0", (byte)1)) // 下限値
                .append("m3 ～ ")
                .append(OtherUtil.printformat((int)dUpVal, "####0.0", (byte)1))       // 上限値
                .append("m3");
        // ステップの単価を印字(増減率を考慮)
        printRyokinSikiO(strStep.toString(), 1);

        strStep = new StringBuilder();
        strStep.append(OtherUtil.printformat(lAddKin, "#,##0.00", (byte) 4)).append("円/m3");

        return printRyokinSikiO(strStep.toString(), 2)[1];
    }

    @Override
    public void createHybComment(KensinData kensinData) {
        String str;
        long lGasKin;
        long lHybKin;

        Ko2fDat ko2fDat = kensinData.getKo2fDat();

        if(ko2fDat == null || ko2fDat.getGashyb() == 0){
            return;
        }

        int nStartheight = printFeedDot((byte)5)[0];
        int nEndheight;

        printFeedDot((byte) 5);

        lGasKin = kensinData.getOnlyGas();

        //2010.06.08 ハイブリッド価格
        //           ガス料金（ハイブリッド使用量単位）
        //         ＋原料調整費（税込み）
        //         ＋消費税
        //         ＋カウンター料
        lHybKin = ko2fDat.getChoKin() + ko2fDat.getChoTax();

        if (lHybKin < 0) {
            str = "　通常料金の場合 ";
            str += OtherUtil.KingakuFormat(lGasKin) + "円です。";
            printNormal(str);

            str = "　　今回のガスご使用料金は、";
            printNormal(str);

            str = "　当社標準価格より " ;
            str +=  OtherUtil.KingakuFormat((lHybKin * -1)) + "円お得です。" ;
            nEndheight = printNormal(str)[1];

            //罫線
            // 矩形設定
            if(nStartheight != nEndheight){
                nEndheight = printFeedDot((byte)10)[1];
                printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.NORMAL);
            }
            printLF(1);
        }

        checkPageModeLength();
    }

    @Override
    public void createHybTblPrint(KensinData kensinData) {

        String strLine;
        int nIdx;

        Ko2fDat ko2fDat = kensinData.getKo2fDat();
        HybfDat hybfDat = kensinData.getHybfDat();

        if(ko2fDat == null || ko2fDat.getGashyb() == 0){
            return;
        }

        int nStartheight = printFeedDot((byte)5)[0];
        int nEndheight;

        strLine =  "ご使用量の内訳" ;
        printHybComment(strLine, 0);

        strLine =  "今回指針" ;
        printHybComment(strLine, 1);

        strLine =  "前回指針" ;
        printHybComment(strLine, 2);

        strLine =  "今回使用量";
        nEndheight = printHybComment(strLine, 3)[1];

        //----------------------------------------------------------------
        //罫線
        // 横線
        printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.NORMAL);
        printFeedDot((byte)5);

        //----------------------------------------------------------------
        // ご使用量合計
        strLine =  "ご使用量合計";
        printHybCommentKin(strLine, 0);

        //----------------------------------------------------------------
        // 今回指針
        strLine = OtherUtil.printformat(kensinData.getSisin(), "#,##0.0", (byte) 1) + "m3";
        printHybCommentKin(strLine, 1);

        //----------------------------------------------------------------
        // 前回指針
        strLine = OtherUtil.printformat(kensinData.getSisinPrev(), "#,##0.0", (byte) 1)+"m3";
        printHybCommentKin(strLine, 2);

        //----------------------------------------------------------------
        // 使用量
        strLine = OtherUtil.printformat(kensinData.getNowUse(), "#,##0.0", (byte) 1)+"m3";
        printHybCommentKin(strLine, 3);

        //----------------------------------------------------------------
        // 通常使用
        strLine = "通常使用量";
        printHybCommentKin(strLine, 0);
        printHybCommentKin(" ", 1);
        printHybCommentKin(" ", 2);

        //----------------------------------------------------------------
        //通常使用量は、指針部分印字しない　2016.01.21
        //----------------------------------------------------------------

        //----------------------------------------------------------------
        // 使用量
        strLine =  OtherUtil.printformat( ko2fDat.getNorKin(), "#,##0.0", (byte) 1)+"m3";
        printHybCommentKin(strLine, 3);

        //----------------------------------------------------------------
        // ハイブリッドのカウンター
        for ( nIdx= 0 ; nIdx < Ko2fDat.getHyb_MAX() ; nIdx++ ) {
            if (hybfDat.getCusef()[nIdx] == 1 && ko2fDat.getFee()[nIdx] != 0) {
                //カウンタ名称
                strLine = kensinData.getCounterName()[nIdx];
                printHybCommentKin(strLine, 0);

                //----------------------------------------------------------------
                // 今回指針
                strLine =  OtherUtil.printformat(ko2fDat.getNowMeter()[nIdx], "#,##0.0", (byte)1)+"m3";
                printHybCommentKin(strLine, 1);

                //----------------------------------------------------------------
                // 前回指針
                strLine =  OtherUtil.printformat(ko2fDat.getPreMeter()[nIdx], "#,##0.0", (byte)1)+"m3";
                printHybCommentKin(strLine, 2);

                //----------------------------------------------------------------
                // 使用量
                strLine =  OtherUtil.printformat(kensinData.getHybGasUse()[nIdx], "#,##0.0", (byte)1)+"m3";
                nEndheight = printHybCommentKin(strLine, 3)[1];
            }

        }

        if(nStartheight != nEndheight){
            nEndheight = printFeedDot((byte)10)[1];
            printRectangle(0, RECT_WIDTH, nStartheight, nEndheight, STYLE.NORMAL);
        }
        printLF(1);
        checkPageModeLength();
    }

    @Override
    public void createFunouComment(UserData userData) {
        Sy2fDat sy2fDat = userData.getSy2fDat();
        if(sy2fDat.getFunouPrint() == 0){
            return;
        }
        KouserDat kouserDat = userData.getKouserDat();
        if( kouserDat.getIraiStat() != 6 ){
            return;
        }
        int nStartHeight = printFeedDot((byte)5)[0];
        int nEndHeight = nStartHeight;

        String strFunouComment0 = OtherUtil.getClearString(sy2fDat.getSy2fFunouComment().getFunouComment0());
        String strFunouComment1 = OtherUtil.getClearString(sy2fDat.getSy2fFunouComment().getFunouComment1());
        if(!strFunouComment0.isEmpty()){
            nEndHeight = printNormal(strFunouComment0)[1];
        }
        if(!strFunouComment1.isEmpty()){
            nEndHeight = printNormal(strFunouComment1)[1];
        }
        // 矩形設定
        if(nStartHeight != nEndHeight){
            nEndHeight = printFeedDot((byte)10)[1];
            printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.NORMAL);
            printLF(1);
        }
        checkPageModeLength();
    }
}
