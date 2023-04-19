package jp.co.MarutouCompack.commonClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.Locale;

import jp.co.MarutouCompack.Printer.BaseActivity;
import jp.co.MarutouCompack.Printer.BasePrintActivity;
import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.baseClass.PrintBaseImage;
import jp.co.MarutouCompack.baseClass.PrintBaseImage.Align;
import jp.co.MarutouCompack.baseClass.PrintBaseImage.TextSize;
import jp.co.MarutouCompack.baseClass.PrintBaseImage2;
import jp.co.MarutouCompack.baseClass.PrintImageList;
import jp.co.MarutouCompack.baseClass.PrintKensinInterface;
import jp.co.MarutouCompack.commonClass.PrinterDat.CnpCusDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.CusData;
import jp.co.MarutouCompack.commonClass.PrinterDat.GasfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.GstpDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.GtpcDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.HybfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KensinData;
import jp.co.MarutouCompack.commonClass.PrinterDat.KnebDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.Ko2fDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KokfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KotfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KouserDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.Sy2fDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysOption;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.WarifDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.CounterUseKinDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KensinInfoBaseDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KinInfoDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UserData;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * @author 株式会社マルトウコンパック
 */
public class PrintKensinImage extends PrintBaseImage2 implements PrintKensinInterface {

    // --------------------------------------------------
    // 定数
    // --------------------------------------------------
    /** クラス識別用タグ */
    private static final String TAG = PrintKensinImage.class.getSimpleName();
    /** 請求関連印字位置 */
    protected static final int[] START_POSITION_SEIKYU = new int[] {START_TAG + RECT_MARGIN, 325};
    /** ガス料金表(A式)矩形印字位置 */
    protected static final int[] START_POSITION_RYOKIN_TABLE_A = new int[]{START_TAG + RECT_MARGIN, 385};
    /** ガス料金表(O式)矩形印字位置 */
    protected static final int[] START_POSITION_RYOKIN_TABLE_O = new int[]{START_TAG + RECT_MARGIN, 410};
    /** ガス料金使用量範囲印字位置 */
    protected static final int STRING_TAG_LIMIT = 120;
    /** ガス料金式印字位置 */
    protected static final int[] GASRKTBL_POS = { 25, 220, 285, 365 };

    /**
     * コンストラクタ<br />
     * ポート名及び設定情報を設定
     * 
     * @param ctx       [in] {@link Context}    呼び出し元コンテキスト
     */
    public PrintKensinImage(Context ctx) throws MException{
        super(ctx);
        SharedPreferences wkPref = ctx.getSharedPreferences("Printer", Context.MODE_PRIVATE);
        setPortName(wkPref.getString("PrinterMAC", ""));
        // この段階で接続
        connect();
    }

    @Override
    protected void printExecute(int nPrintMode) throws MException {
        // 最後のデータを送信
        sendImageData();
        // 切断
        disconnect();
    }

    @Override
    public void printExecute() throws MException {
        // 画像モードで印刷を実行する
        printExecute(PRINT_MODE_IMAGE);
    }


    /**
     * ヘッダー部分の印刷データ作成
     *
     * @param isNyukin    [in] boolean 入金有無
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    public void createPrintData(UserData userData, boolean isNyukin, boolean isHikae) throws MException {
        PrintImageList printImageList = new PrintImageList(mContext);
        int nXpos = 20;
        int nYpos = 10;
        String strTitle;
        if( userData.isNyukinMode() ){
            printImageList.add("領　収　書", nXpos, nYpos, TextSize.LARGE, Align.CENTER);
        }
        else {
            SysfDat sysfDat = userData.getSysfDat();
            GtpcDat gtpc = sysfDat.getGtpcDat();
            if (isNyukin) {
                if(isHikae) {
                    if (sysfDat.getVisibleGas() == 1 && !TextUtils.isEmpty(OtherUtil.cutStringSpace(gtpc.m_strTitle_3))) {
                        strTitle = gtpc.m_strTitle_3;
                    } else {
                        strTitle = "検針伝票 (兼　領収書)(控)";
                    }
                }
                else {
                    if (sysfDat.getVisibleGas() == 1 && !TextUtils.isEmpty(OtherUtil.cutStringSpace(gtpc.m_strTitle_1))) {
                        strTitle = gtpc.m_strTitle_1;
                    } else {
                        strTitle = "検針伝票 (兼　領収書)";
                    }
                }
            }
            else {
                if(isHikae){
                    if (sysfDat.getVisibleGas() == 1 && !TextUtils.isEmpty(OtherUtil.cutStringSpace(gtpc.m_strTitle_2))) {
                        strTitle = gtpc.m_strTitle_2;
                    } else {
                        strTitle = "検 針 伝 票(控)";
                    }
                }
                else {
                    if (sysfDat.getVisibleGas() == 1 && !TextUtils.isEmpty(OtherUtil.cutStringSpace(gtpc.m_strTitle_0))) {
                        strTitle = gtpc.m_strTitle_0;
                    } else {
                        strTitle = "検　針　伝　票";
                    }
                }
            }
            nYpos = printImageList.add(strTitle, nXpos, nYpos, TextSize.LARGE, Align.CENTER)[1];
            nYpos = printImageList.add("毎度ありがとうございます。", nXpos, nYpos, TextSize.SMALL, Align.CENTER)[1];
            printImageList.add("今月の検針は次の通りです。", nXpos, nYpos, TextSize.SMALL, Align.CENTER);
        }
        this.addPrintData(printImageList.getBitmap());
        printImageList.clear();

        checkPageModeLength();
    }

    /**
     * 顧客情報の印刷データを作成する。
     * 
     * @param cusData [in] {@link CusData}    顧客データ
     */
    public void createCusInfo(CusData cusData) throws MException {
        PrintImageList printImageList = new PrintImageList(mContext);

        String strLine;
        int nYpos = 10;
        int nStartheight;

        // 検針日印字
        strLine = (BaseActivity.mWebData.getUserData().isNyukinMode() ? "発行日　　" : "検針日　　" ) + cusData.getDate();
        nYpos = printImageList.add(strLine, STRING_TAG_KENSINDATE, nYpos, TextSize.SMALL)[1];

        nYpos += 10;
        nStartheight = nYpos - printImageList.RECT_PADDING;
        nYpos += printImageList.RECT_PADDING;

        // 顧客コード
        strLine = "コード：" + cusData.getKcode();
        nYpos = printImageList.add(strLine, STRING_TAG_NOW, nYpos, TextSize.SMALL)[1];

        // 顧客名
        if (OtherUtil.getClearString(cusData.getName0()).length() > 0 && OtherUtil.getClearString(cusData.getName1()).length() > 0) {
            nYpos = printImageList.add(cusData.getName0(), STRING_TAG_DEFAULT, nYpos, TextSize.NORMAL)[1];
            printImageList.add(cusData.getName1(), STRING_TAG_DEFAULT, nYpos, TextSize.NORMAL);
            nYpos = printImageList.add(cusData.getKname(), STRING_TAG_YOBI, nYpos, TextSize.NORMAL)[1];
        }
        else if (OtherUtil.getClearString(cusData.getName0()).length() > 0) {
            printImageList.add(cusData.getName0(), STRING_TAG_DEFAULT, nYpos, TextSize.NORMAL);
            nYpos = printImageList.add(cusData.getKname(), STRING_TAG_YOBI, nYpos, TextSize.NORMAL)[1];
        }
        else {
            printImageList.add(cusData.getName1(), STRING_TAG_DEFAULT, nYpos, TextSize.NORMAL);
            nYpos = printImageList.add(cusData.getKname(), STRING_TAG_YOBI, nYpos, TextSize.NORMAL)[1];
        }
        // 顧客住所
        nYpos = printImageList.add(cusData.getAdd0(), STRING_TAG_NOW, nYpos, TextSize.XSMALL)[1];
        Log.i(TAG, "y_pos: " + nYpos);
        if (!cusData.getAdd1().equals("")) { // あれば住所その２
            nYpos = printImageList.add(cusData.getAdd1(), STRING_TAG_NOW, nYpos, TextSize.XSMALL)[1];
        }
        // 矩形印字
        printImageList.addRectBold(nStartheight, nYpos);

        this.addPrintData(printImageList.getBitmap());
        printImageList.clear();
        checkPageModeLength();
    }


    /**
     * 検針情報の印刷データを作成する。
     *
     * @param kensinData  [in] {@link KensinData}   検針情報
     * @throws MException 印刷データ作成時にエラーがあった場合に発生
     */
    @Override
    public void createKensinInfo(UserData userData, KensinData kensinData) throws MException {
        PrintImageList printImageList = new PrintImageList(mContext);

        if (userData.getKokfDat().getKenSumi() == 1 && !userData.isNyukinMode() && kensinData.isPrintKensin()) {
            createKensinInfoBase(userData, BasePrintActivity.mWebData.getAKensinDat().getKSIB(), printImageList);
        }

        KotfDat kotfDat = kensinData.getKotfDat();
        if(kotfDat != null && kotfDat.getKen_sumi() == 1 && !userData.isNyukinMode() && kensinData.isPrintToyu()){
            createToyuKensinInfoBase(kensinData, printImageList);
        }

        createKinInfo(BasePrintActivity.mWebData.getAKensinDat().getKI(), printImageList);
        if(!userData.isNyukinMode()) {
            // 内税を印字する
            if (userData.getSy2fDat().getSysOption()[SysOption.NOT_PRINT_UTIZEI.getIdx()] == 0) { // 内税コメントの抑制フラグ
                createUTaxComment(BasePrintActivity.mWebData.getAKensinDat().getUTC(), printImageList);
            }
            if (userData.getKokfDat().getKenSumi() == 1 || (userData.getKokfDat().getKotfDat() != null && userData.getKokfDat().getKotfDat().getKen_sumi() == 1)) {
                createSeikyuComment(BasePrintActivity.mWebData.getKensinData().getNyukin(), printImageList);        //kensinData.getNyukin()
            }

            if (userData.getKokfDat().getKenSumi() == 1 && !userData.isNyukinMode() && kensinData.isPrintKensin()) {
                createGasryokinSiki(printImageList, BasePrintActivity.mWebData.getKensinData(), printImageList.getHeightPrintLine());

                if (kensinData.isPrintHiwariComment()) {
                    createHiwariComment(printImageList, kensinData, printImageList.getHeightPrintLine());
                }
            }
        }

        this.addPrintData(printImageList.getBitmap());
        printImageList.clear();
        checkPageModeLength();
    }


    /**
     * 検針情報印刷データの生成.
     *
     * @param mKSIB             [in] {@link KensinInfoBaseDat}     検針印刷データ
     * @param printImageList    [in] {@link PrintImageList} 印刷データ格納先
     * @throws MException 印刷データ生成時にエラーがあった場合に発生
     */
    private void createKensinInfoBase(UserData userData, KensinInfoBaseDat mKSIB, PrintImageList printImageList) throws MException {
        String strLine;
        int nYpos = 10;
        int nStartheight = nYpos - printImageList.RECT_PADDING;

        // 今回検針
        printImageList.add("今回指針", STRING_TAG_NOW, nYpos, TextSize.NORMAL);
        strLine = mKSIB.getSisin(); //KSIB.sSisin()
        nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT)[1];

        if(mKSIB.isChgMeter()){    //KSIB.bIsChgMeter
            // メーター取替有
            strLine = "取付指針";   //KSIB.sToritsukjJiZenkaiSiSin
        }
        else {
            // 前回指針
            strLine = "前回指針";   //KSIB.sToritsukjJiZenkaiSiSin
        }
        strLine += mKSIB.getToritsukjJiZenkaiSiSin();
        printImageList.add(strLine, STRING_TAG_NOW, nYpos, TextSize.NORMAL);
        strLine = mKSIB.getSisinPrev();//KSIB.sSisinPrev
        nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT)[1];

        if(mKSIB.isChgMeter()){    //KSIB.bIsChgMeter
            // メーター取替有は中間使用量を印字
            printImageList.add("中間使用量", STRING_TAG_NOW, nYpos, TextSize.NORMAL);
            strLine = mKSIB.getChukanSur(); //KSIB.sChukanSur
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT)[1];
        }
        printImageList.add("使用量", STRING_TAG_NOW, nYpos, TextSize.NORMAL, PrintBaseImage.STROKE_WIDTH_BOLD);
        strLine = mKSIB.getNowUse();    //KSIB.sNowUse
        nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT, PrintBaseImage.STROKE_WIDTH_BOLD)[1];

        // 矩形印字
        nYpos += printImageList.RECT_PADDING;
//        printImageList.addRectMulti(getRectStartX(START_POSITION_SEIKYU), nStartheight, nYpos);
        printImageList.addRect(START_POSITION_SEIKYU[0], nStartheight, nYpos);
        nStartheight = nYpos;

        if(mKSIB.isPrnZensr()) {       //KSIB.bIsPrnZensr
            nYpos += printImageList.RECT_PADDING;
            nYpos += printImageList.RECT_PADDING;
            nYpos += printImageList.RECT_PADDING;
            printImageList.add("前回使用量", STRING_TAG_PREVIEW, nYpos, TextSize.SMALL);
            strLine = mKSIB.getPreUse();    //KSIB.sPreUse
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.SMALL, Align.RIGHT)[1];
        }
        if(mKSIB.isPrintZenYearKenSr()){   //KSIB.bIsPrintZenYearKenSr
            if(!mKSIB.isPrnZensr()){     //KSIB.bIsPrnZensr
                nYpos += printImageList.RECT_PADDING;
                nYpos += printImageList.RECT_PADDING;
                nYpos += printImageList.RECT_PADDING;
            }
            nYpos = createZenYearkenSr(printImageList, mKSIB, nYpos);  //KSIB.bIsPrintZenYearKenSr, //KSIB.sZenYearKenSr
        }

        if(mKSIB.isChgMeter()){    //KSIB.bIsChgMeter
            // メーター交換有りは取外指針と前回指針を印字


            strLine = "取外指針";
            //KSIB.sTorihazuSiSinDate
            strLine += mKSIB.getTorihazuSiSinDate();
            printImageList.add(strLine, STRING_TAG_NOW, nYpos, TextSize.NORMAL);
            //KSIB.sTorihazuSiSin
            strLine = mKSIB.getChgZsisin();
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT)[1];

            strLine = "前回指針";
            //KSIB.sZenkaiSiSinDate
            strLine += mKSIB.getZenkaiSiSinDate();
            printImageList.add(strLine, STRING_TAG_NOW, nYpos, TextSize.NORMAL);
            //KSIB.sChgZsisin
            strLine = mKSIB.getChgZsisin();
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT)[1];
        }

        if(nYpos != nStartheight ) {
            // 矩形印字
            nYpos += printImageList.RECT_PADDING;
//            printImageList.addRectMulti(getRectStartX(START_POSITION_SEIKYU), nStartheight, nYpos);
            printImageList.addRect(START_POSITION_SEIKYU[0], nStartheight, nYpos);
            nStartheight = nYpos;
        }
        nYpos += printImageList.RECT_PADDING;
        nYpos += printImageList.RECT_PADDING;
        nYpos += printImageList.RECT_PADDING;

        printImageList.add("ガス料金", STRING_TAG_NOW, nYpos, TextSize.NORMAL, PrintBaseImage.STROKE_WIDTH_BOLD);

        //通常料金
        strLine = mKSIB.getGasPay();    //KSIB.sGasPay
        nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT, PrintBaseImage.STROKE_WIDTH_BOLD)[1];

//        Ko2fDat ko2fDat = kensinData.getKo2fDat();
//        HybfDat hybfDat = kensinData.getHybfDat();
        if(mKSIB.isPrnGasBaseKin()){   //KSIB.bIsPrnGasBaseKin
            int nStartHeight = nYpos;
            nYpos += printImageList.RECT_PADDING;
            nYpos += printImageList.RECT_PADDING;
            printImageList.add("　基本料金", STRING_TAG_NOW, nYpos, TextSize.SMALL);
            //KSIB.sKihonRyookin
            strLine = mKSIB.getKihonRyookin();
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.SMALL, Align.RIGHT)[1];


            //HieuNote (chỗ này chưa làm tới)
            //KSIB.bIsHybrid && KSIB.nGashyb
            //KSIB.sRyookin
            if(mKSIB.isHybrid() && mKSIB.getGashyb() > 0) {
                printImageList.add("　通常従量料金", STRING_TAG_NOW, nYpos, TextSize.SMALL);
            }else{
                printImageList.add("　従量料金", STRING_TAG_NOW, nYpos, TextSize.SMALL);
            }
            strLine = mKSIB.getRyookin();
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.SMALL, Align.RIGHT)[1];

            //HieuNote (chỗ này chưa làm tới)
            //KSIB.bIsHybrid && KSIB.nGashyb
//            if(mKSIB.isHybrid() && mKSIB.getGashyb() > 0){
//                String str;
//                double nGasTotal;
//                for ( int j = 0 ; j < Ko2fDat.kHyb_MAX ; j++ ) {
//                    if (hybfDat.mCusef[j] == 1 && ko2fDat.mFee[j] != 0) {
//                        //カウンタ名称
//                        str = "　" + kensinData.getCounterName()[j];
//                        printImageList.add(str, STRING_TAG_NOW, nYpos, TextSize.SMALL);
//
//                        nGasTotal = ko2fDat.mFee[j];
//                        strLine = OtherUtil.KingakuFormat((long)nGasTotal) + "円";
//                        nYpos = printImageList.add(strLine, 0, nYpos, TextSize.SMALL, Align.RIGHT)[1];
//                    }
//                }
//            }

//            printImageList.addRect(STRING_TAG_NOW, nStartHeight, RECT_WIDTH - (STRING_TAG_NOW - START_TAG), nYpos - nStartHeight, PrintBaseImage.RECT_STROKE_NORMAL);
//            printImageList.addRect(START_TAG, nYpos, RECT_WIDTH - (STRING_TAG_NOW - START_TAG), 0, PrintBaseImage.RECT_STROKE_NORMAL);
        }

        if(mKSIB.isHybrid()) {     //KSIB.bIsHybrid
            //カウンター使用料 (nType is unnecessary, let it be zero)

            // KSIB.counterUseKinDat
            nYpos = printCounterUseKin(printImageList, nYpos, mKSIB.getCounterUseKinDat(), false, 0);
        }

        // 消費税有り
        //KSIB.gasfDat
        if(mKSIB.getGasfDat() != null) {
            if (mKSIB.getGasfDat().getTaxDiv() == 3 && mKSIB.getGasTax() != 0) {
                nYpos += printImageList.RECT_PADDING * 2;
                printImageList.add("ガス消費税", STRING_TAG_NOW, nYpos, TextSize.NORMAL, PrintBaseImage.STROKE_WIDTH_BOLD);
                strLine = OtherUtil.KingakuFormat(mKSIB.getGasTax()) + "円";        //KSIB.nGasTax
                nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT, PrintBaseImage.STROKE_WIDTH_BOLD)[1];
            }
        }

        //HieuNote (chưa đối ứng)
        //KSIB.nKnebFlg
        if(mKSIB.getKnebFlg() == 1) {
            // 漢の値引き有り
            //KSIB.listKnebiDat
            for (KnebDat knebDat : userData.getLstKnebDat()) {
                if (knebDat.getCode() > 0 &&  // 割引コード設定有
                        knebDat.getUmu() == 1 &&  // 割引フラグ有
                        knebDat.getRes() == 1 &&  // 割引実績有
                        knebDat.getKin() != 0) {  // 割引金額有
                    nYpos += printImageList.RECT_PADDING * 2;
//                    WarifDat warifDat = InputDat.getWarifDat(mContext, knebDat.m_nCode);
                    //Hieunote
                    WarifDat warifDat = new WarifDat();
                    printImageList.add(warifDat.m_strHinName, STRING_TAG_NOW, nYpos, TextSize.NORMAL, PrintBaseImage.STROKE_WIDTH_BOLD);
                    strLine = OtherUtil.KingakuFormat(knebDat.getKin() + knebDat.getTax()) + "円";
                    nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT, PrintBaseImage.STROKE_WIDTH_BOLD)[1];
                }
            }
        }

        // 還元額有り
        //KSIB.bIfReduce
        //KSIB.nReduce
        if (mKSIB.ifReduce() && mKSIB.getReduce() != 0) {
            // 差益還元額名称取得
            nYpos += printImageList.RECT_PADDING * 2;
            //KSIB.sKangcontname
            printImageList.add(mKSIB.getKangcontname(),
                    STRING_TAG_NOW,
                    nYpos,
                    TextSize.NORMAL,
                    PrintBaseImage.STROKE_WIDTH_BOLD);
            //KSIB.nReduce
            strLine = OtherUtil.KingakuFormat(mKSIB.getReduce()) + "円";
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT, PrintBaseImage.STROKE_WIDTH_BOLD)[1];
        }
//        printImageList.addRectMultiBold(getRectStartX(START_POSITION_SEIKYU), nStartheight, nYpos);
        printImageList.addRectBold(START_POSITION_SEIKYU[0], nStartheight, nYpos);

        //KSIB.bIsPrintGasRyokinTotal
        if(mKSIB.isPrintGasRyokinTotal()){
            createGasryokinTotal(printImageList, mKSIB, nYpos);        ////KSIB.sGasTotalKin
        }
        checkPageModeLength();
    }

    /**
     * 灯油検針印刷データの生成.
     *
     * @param kensinData        [in] {@link KensinData}     検針印刷データ
     * @param printImageList    [in] {@link PrintImageList} 印刷情報格納データ
     * @throws MException   印刷データ生成時にエラーがあった場合に発生
     */
    private void createToyuKensinInfoBase(KensinData kensinData, PrintImageList printImageList) throws MException {
        String strLine;
        int nYpos = printImageList.getHeightPrintLine() + 30;
        int nStartheight = nYpos - printImageList.RECT_PADDING;
        KotfDat kotfDat = kensinData.getKotfDat();

        // 今回検針
        printImageList.add("今回指針", STRING_TAG_NOW, nYpos, TextSize.NORMAL);
        strLine = OtherUtil.Format(kotfDat.getNow_meter(), (byte) 1) + "  ";
        nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT)[1];

        boolean isMtChg = kotfDat.getBetw_meter() > 0;
        if(isMtChg){
            // メーター取替有
            strLine = "取付指針";
            if(kotfDat.getMtchg_m() != 0 && kotfDat.getMtchg_d() != 0){
                strLine += "(" + OtherUtil.DateFormat(kotfDat.getMtchg_m(), kotfDat.getMtchg_d(), true) + ")";
            }
        }
        else {
            // 前回指針
            strLine = "前回指針";
            if (kotfDat.getPuse_month() != 0 && kotfDat.getPuse_day() != 0) {
                strLine += "(" + OtherUtil.DateFormat(kotfDat.getPuse_month(), kotfDat.getPuse_day(), true) + ")";
            }
        }
        printImageList.add(strLine, STRING_TAG_NOW, nYpos, TextSize.NORMAL);
        strLine = OtherUtil.Format(kotfDat.getPre_meter(), (byte) 1) + "  ";
        nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT)[1];

        if(isMtChg){
            // メーター取替有は中間使用量を印字
            printImageList.add("中間使用量", STRING_TAG_NOW, nYpos, TextSize.NORMAL);
            strLine = OtherUtil.Format(kotfDat.getBetw_meter(), (byte) 1) + "  ";
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT)[1];
        }
        printImageList.add("使用量", STRING_TAG_NOW, nYpos, TextSize.NORMAL, PrintBaseImage.STROKE_WIDTH_BOLD);
        strLine = OtherUtil.Format(kotfDat.getLoil_use(), (byte) 1) + " L";
        nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT, PrintBaseImage.STROKE_WIDTH_BOLD)[1];

        // 矩形印字
        nYpos += printImageList.RECT_PADDING;
//        printImageList.addRectMulti(getRectStartX(START_POSITION_SEIKYU), nStartheight, nYpos);
        printImageList.addRect(START_POSITION_SEIKYU[0], nStartheight, nYpos);
        nStartheight = nYpos;

        if(kotfDat.getPuse_month() != 0) {
            nYpos += printImageList.RECT_PADDING;
            nYpos += printImageList.RECT_PADDING;
            nYpos += printImageList.RECT_PADDING;
            printImageList.add("前回使用量", STRING_TAG_PREVIEW, nYpos, TextSize.SMALL);
            strLine = OtherUtil.Format(kotfDat.getPre_use(), (byte) 1) + " L";
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.SMALL, Align.RIGHT)[1];
        }

        if(isMtChg){
            // メーター交換有りは取外指針と前回指針を印字
            strLine = "取外指針";
            if(kotfDat.getMtchg_m() != 0 && kotfDat.getMtchg_d() != 0){
                strLine += "(" + OtherUtil.DateFormat(kotfDat.getMtchg_m(), kotfDat.getMtchg_d(), true) + ")";
            }
            printImageList.add(strLine, STRING_TAG_NOW, nYpos, TextSize.NORMAL);
            strLine = OtherUtil.Format(kotfDat.getMtchg_oldss(), (byte) 1) + "  ";
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT)[1];

            strLine = "前回指針";
            if (kotfDat.getPuse_month() != 0 && kotfDat.getPuse_day() != 0) {
                strLine += "(" + OtherUtil.DateFormat(kotfDat.getPuse_month(), kotfDat.getPuse_day(), true) + ")";
            }
            printImageList.add(strLine, STRING_TAG_NOW, nYpos, TextSize.NORMAL);
            strLine = OtherUtil.Format(kotfDat.getMtchg_zknss(), (byte) 1) + "  ";
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT)[1];
        }

        if(nYpos != nStartheight ) {
            // 矩形印字
            nYpos += printImageList.RECT_PADDING;
//            printImageList.addRectMulti(getRectStartX(START_POSITION_SEIKYU), nStartheight, nYpos);
            printImageList.addRect(START_POSITION_SEIKYU[0], nStartheight, nYpos);
            nStartheight = nYpos;
        }
        nYpos += printImageList.RECT_PADDING;
        nYpos += printImageList.RECT_PADDING;
        nYpos += printImageList.RECT_PADDING;

        printImageList.add("灯油メーター料金", STRING_TAG_NOW, nYpos, TextSize.NORMAL, PrintBaseImage.STROKE_WIDTH_BOLD);

        //通常料金
        strLine = OtherUtil.KingakuFormat(kotfDat.getFee()) + "円";
        nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT, PrintBaseImage.STROKE_WIDTH_BOLD)[1];

        if(kensinData.isToyuKinSep()){
            int nStartHeight = nYpos;
            nYpos += printImageList.RECT_PADDING;
            nYpos += printImageList.RECT_PADDING;
            printImageList.add("　基本料金", STRING_TAG_NOW, nYpos, TextSize.XSMALL);
            strLine = OtherUtil.KingakuFormat(kotfDat.getLoil_base() / 100) + "円";
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.XSMALL, Align.RIGHT)[1];

            printImageList.add("　従量料金(単価" + OtherUtil.KingakuFormat(kensinData.getLoilUnit()) + "円)", STRING_TAG_NOW, nYpos, TextSize.XSMALL);
            strLine = OtherUtil.KingakuFormat(kotfDat.getFee() - (kotfDat.getLoil_base() / 100)) + "円";
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.XSMALL, Align.RIGHT)[1];

//            printImageList.addRect(STRING_TAG_NOW, nStartHeight, RECT_WIDTH - (STRING_TAG_NOW - START_TAG), nYpos - nStartHeight, PrintBaseImage.RECT_STROKE_NORMAL);
//            printImageList.addRect(START_TAG, nYpos, RECT_WIDTH - (STRING_TAG_NOW - START_TAG), 0, PrintBaseImage.RECT_STROKE_NORMAL);
        }

        // 消費税有り
        if(kotfDat.getCon_tax() != 0) {
            nYpos += printImageList.RECT_PADDING * 2;
            printImageList.add("灯油消費税", STRING_TAG_NOW, nYpos, TextSize.NORMAL, PrintBaseImage.STROKE_WIDTH_BOLD);
            strLine = OtherUtil.KingakuFormat(kotfDat.getCon_tax()) + "円";
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT, PrintBaseImage.STROKE_WIDTH_BOLD)[1];
        }

        printImageList.addRectBold(START_POSITION_SEIKYU[0], nStartheight, nYpos);
        checkPageModeLength();
    }


    /**
     * 金額情報印刷データの生成.
     *
     * @param mKI        [in] {@link KinInfoDat}     検針印刷データ
     * @param printImageList    [in] {@link PrintImageList} 印刷情報格納データ
     * @throws MException   印刷情報生成時にエラーがあった場合に発生
     */
    private void createKinInfo(KinInfoDat mKI, PrintImageList printImageList) throws MException {
        boolean isPrint = false;
        int nYpos = printImageList.getHeightPrintLine() + 5;
        int nStartheight = nYpos;
        String strLine;

        if(!mKI.isNyukinOnly()){      //mKI.bNyukinOnly()
            // 入金のみの場合は前残等印字しない
            // 前月残高

            //mKI.bIfDemand
            //mKI.nPreReceipt
            if (mKI.ifDemand() && mKI.getPreReceipt() != 0) {
                nYpos += printImageList.RECT_PADDING * 2;
                printImageList.add(mKI.getsZanTitle(), STRING_TAG_PREVIEW, nYpos, TextSize.SMALL);
                //mKI.sZanTitle
                strLine = OtherUtil.KingakuFormat(mKI.getPreReceipt()) + "円";
                nYpos = printImageList.add(strLine, 0, nYpos, TextSize.SMALL, Align.RIGHT)[1];
            }

            // その他売上
            //mKI.bIfProceeds
            if (mKI.ifProceeds()) {
                //mKI.nHmDay
                if (mKI.getHmDay() != 0) {
                    if(nStartheight == nYpos ){
                        nYpos += printImageList.RECT_PADDING * 2;
                    }
                    printImageList.add("本日お買い上げ額", STRING_TAG_PREVIEW, nYpos, TextSize.SMALL);
                    strLine = OtherUtil.KingakuFormat(mKI.getHmDay()) + "円";
                    nYpos = printImageList.add(strLine, 0, nYpos, TextSize.SMALL, Align.RIGHT)[1];
                }
                //mKI.nHmMonth
                if (mKI.getHmMonth() != 0) {
                    if(nStartheight == nYpos ){
                        nYpos += printImageList.RECT_PADDING * 2;
                    }
                    printImageList.add("当月お買い上げ額", STRING_TAG_PREVIEW, nYpos, TextSize.SMALL);
                    strLine = OtherUtil.KingakuFormat(mKI.getHmMonth()) + "円";
                    nYpos = printImageList.add(strLine, 0, nYpos, TextSize.SMALL, Align.RIGHT)[1];
                }
            }
            // 当月入金額
            //mKI.nTReceipt
            if (mKI.getTReceipt() != 0) {
                if(nStartheight == nYpos ){
                    nYpos += printImageList.RECT_PADDING * 2;
                }
                printImageList.add("当月入金額", STRING_TAG_PREVIEW, nYpos, TextSize.SMALL);
                strLine = OtherUtil.KingakuFormat(mKI.getTReceipt()) + "円";
                nYpos = printImageList.add(strLine, 0, nYpos, TextSize.SMALL, Align.RIGHT)[1];
            }
            // s 13.07.11
            // 当月調整額
            //mKI.nTAdjust
            if (mKI.getTAdjust() != 0) {
                if(nStartheight == nYpos ){
                    nYpos += printImageList.RECT_PADDING * 2;
                }
                printImageList.add("当月調整額", STRING_TAG_PREVIEW, nYpos, TextSize.SMALL);
                strLine = OtherUtil.KingakuFormat(mKI.getTAdjust()) + "円";
                nYpos = printImageList.add(strLine, 0, nYpos, TextSize.SMALL, Align.RIGHT)[1];
            }
            // e 13.07.11
            if (nYpos != nStartheight) { // 13.07.11
//                printImageList.addRectMultiBold(getRectStartX(START_POSITION_SEIKYU), nStartheight, nYpos);
                printImageList.addRectBold(START_POSITION_SEIKYU[0], nStartheight, nYpos);
                nStartheight = nYpos;
            }

            nYpos += printImageList.RECT_PADDING * 4;
            // 今回請求額
            // 今回請求額用矩形生成
            //mKI.nReceipt
            strLine = OtherUtil.KingakuFormat(mKI.getReceipt()) + "円";
            TextSize enumTextSize = TextSize.LARGE;
            //mKI.bIsFuriDemand
            if(mKI.getReceipt() >= 100000 || mKI.isFuriDemand()){
                enumTextSize = TextSize.NORMAL;
            }
            printImageList.add(strLine, 0, nYpos, enumTextSize, Align.RIGHT, PrintBaseImage.STROKE_WIDTH_BOLD);

            String strSeiTitle = "今回請求";
            if(mKI.isFuriDemand()){  //kensinData.isFuriDemand()
                strSeiTitle += "予定";
            }
            strSeiTitle += "額";
            nYpos = printImageList.add(strSeiTitle, STRING_TAG_DEFAULT, nYpos, enumTextSize, PrintBaseImage.STROKE_WIDTH_BOLD)[1];

            if (nStartheight != nYpos) { // 印字行ありの場合のみ枠印字
                printImageList.addRectBold(START_POSITION_SEIKYU[0], nStartheight, nYpos);
            }

            //mKI.sIraimsg
            if(!mKI.getIraimsg().isEmpty()){
                nStartheight = nYpos;
                nYpos += printImageList.RECT_PADDING * 2;
                nYpos = printImageList.addXsmall(mKI.getIraimsg(), STRING_TAG_DEFAULT, nYpos)[1];
                printImageList.addRectBold(nStartheight, nYpos);
                nStartheight = nYpos;
            }

            // 調整額
            //mKI.nChosei
            if (mKI.getChosei() != 0) {
//                if(nStartheight == nYpos ){
//                    nYpos += printImageList.RECT_PADDING * 2;
//                }
                nYpos += printImageList.RECT_PADDING * 2;
                // 調整額有り
                isPrint = true;
                printImageList.add(mKI.getsChoseiTitle(), STRING_TAG_PREVIEW, nYpos);
                strLine = OtherUtil.KingakuFormat(mKI.getChosei()) + "円";
                nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT)[1];
            }
        }

        // 本日入金額
        //mKI.nNyukin
        if (mKI.getNyukin() != 0) {
            if(nStartheight == nYpos ){
                nYpos += printImageList.RECT_PADDING * 2;
            }
            isPrint = true;
            //mKI.nAzukarikin
            if (mKI.getAzukarikin() == mKI.getNyukin()) { // 13.02.12
                strLine = "本日入金額";
            }
            else {
                strLine = "本日お預かり金額";
            }
            printImageList.add(strLine, STRING_TAG_PREVIEW, nYpos);
            strLine = OtherUtil.KingakuFormat(mKI.getAzukarikin()) + "円";
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT)[1];
        }

        // おつり
        int t_otsuri = mKI.getAzukarikin() - mKI.getNyukin(); // 13.02.12
        if (t_otsuri > 0) {
            if(nStartheight == nYpos ){
                nYpos += printImageList.RECT_PADDING * 2;
            }
            strLine = "おつり";
            printImageList.add(strLine, STRING_TAG_PREVIEW, nYpos);
            strLine = OtherUtil.KingakuFormat(t_otsuri) + "円"; // 13.02.12
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT)[1];
        }
        if (nStartheight != nYpos) { // 印字行ありの場合のみ枠印字
            printImageList.addRectBold(START_POSITION_SEIKYU[0], nStartheight, nYpos);
        }
        // 差引残高

        //mKI.nKZandaka
        //mKI.nAzukarikin
        if (mKI.getKZandaka() != 0 && isPrint) {
            if (nStartheight != nYpos) { // 印字行ありの場合のみ枠印字
                nStartheight = nYpos;
                nYpos += printImageList.RECT_PADDING * 2;
            }
            printImageList.add("差引残高", STRING_TAG_NOW, nYpos, TextSize.NORMAL, PrintBaseImage.STROKE_WIDTH_BOLD);
            //mKI.nLZandaka
            strLine = OtherUtil.KingakuFormat(mKI.getLZandaka()) + "円";
            nYpos = printImageList.add(strLine, 0, nYpos, TextSize.NORMAL, Align.RIGHT, PrintBaseImage.STROKE_WIDTH_BOLD)[1];
//            printImageList.addRectMultiBold(getRectStartX(START_POSITION_SEIKYU), nStartheight, nYpos);
            printImageList.addRectBold(START_POSITION_SEIKYU[0], nStartheight, nYpos);
        }
    }


    /**
     * 請求コメント印刷データの生成.
     *
     * @param nyukin        [in] {@link int}     検針印刷データ
     * @param printImageList    [in] {@link PrintImageList} 印刷情報格納データ
     */
    private void createSeikyuComment(int nyukin, PrintImageList printImageList) {
        int y_pos = printImageList.getHeightPrintLine() + 10;
        // 入金額が0円の場合
        if (nyukin == 0) {
            printImageList.add("上記の通りご請求致します。", STRING_TAG_PREVIEW, y_pos, TextSize.SMALL);
        }
    }


    //Hieu
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
     * @param strHoan    [in] String 保安点検情報
     */
    public void createHoanInfo(boolean isNyukinMode, String strHoan) throws MException {
        if(isNyukinMode){
            return;
        }
        int nYpos = 10;
        int nStartheight;

        PrintImageList printImageList = new PrintImageList(mContext);

        nYpos = printImageList.add("* * * 保　安　点　検 * * *", 0, nYpos, TextSize.NORMAL, Align.CENTER, PrintBaseImage.STROKE_WIDTH_BOLD)[1];
        nYpos += 10;
        nStartheight = nYpos - printImageList.RECT_PADDING;
        for (int i = 0; i < HOAN_ITEMS.length; i++) {
            if (i % 2 == 0) {
                // 奇数
                printImageList.add(HOAN_ITEMS[i], 20, nYpos, TextSize.XSMALL);
                printImageList.add("[" + strHoan.charAt(i) + "]", 230, nYpos, TextSize.XSMALL);
            }
            else {
                // 偶数
                printImageList.add(HOAN_ITEMS[i], 285, nYpos, TextSize.XSMALL);
                nYpos = printImageList.add("[" + strHoan.charAt(i) + "]", 510, nYpos, TextSize.XSMALL)[1];
            }

        }
        nYpos += printImageList.RECT_PADDING;
        printImageList.addRect(nStartheight, nYpos);
        nYpos += 10;
        nYpos = printImageList.add("基準に適合しない場合には速やかに措置を", START_TAG, nYpos, TextSize.SMALL)[1];
        printImageList.add("講じる必要があります。", START_TAG, nYpos, TextSize.SMALL);

        addPrintData(printImageList.getBitmap());
        printImageList.clear();
        checkPageModeLength();
    }


    /**
     * 銀行自動振替
     */
    @Override
    public void createBank(UserData userData) throws MException{
        PrintImageList printImageList = new PrintImageList(mContext);
        int nYpos = printImageList.RECT_PADDING;
        int nStartHeight = nYpos;
        nYpos += printImageList.RECT_PADDING * 2;
        KokfDat kokfDat = userData.getKokfDat();
        KouserDat kouserDat = userData.getKouserDat();
        Sy2fDat sy2fDat = userData.getSy2fDat();
        String wkStr;

        // 前回引き落とし結果
        if(sy2fDat.getSysOption()[SysOption.PRINT_JIFURI.getIdx()] != 0 && kokfDat.getTransFee() != 0 && kokfDat.getTransFee() < 50000L) {
            nYpos = printImageList.add("下記の金額をご指定の預金口座より", START_TAG, nYpos, TextSize.SMALL)[1];
            nYpos = printImageList.add("自動振替させていただきました。", START_TAG, nYpos, TextSize.SMALL)[1];
            nYpos += printImageList.RECT_PADDING;
            printImageList.add("前回引落日", STRING_TAG_DEFAULT, nYpos, TextSize.SMALL);
            nYpos = printImageList.add(String.format(Locale.JAPANESE, "%02d月%02d日", kokfDat.getTransMonth(), kokfDat.getTransDate()), 0, nYpos, TextSize.SMALL, Align.RIGHT)[1];
            printImageList.add("前回引落額", STRING_TAG_DEFAULT, nYpos, TextSize.SMALL);
            wkStr = OtherUtil.printformat("#,###,##0", kokfDat.getTransFee()) + "円";
            nYpos = printImageList.add(wkStr, 0, nYpos, TextSize.SMALL, Align.RIGHT)[1];
        }

        // 依頼中
        if (sy2fDat.getSysOption()[SysOption.PRINT_JIFURI.getIdx()] != 0 && (kouserDat.getIraiStat() == 1 || kouserDat.getIraiStat() == 2 || kouserDat.getIraiStat() == 3)) {
            nYpos += printImageList.RECT_PADDING;
            nYpos = printImageList.add("引落処理中", STRING_TAG_DEFAULT, nYpos, TextSize.SMALL)[1];
            printImageList.add(String.format(Locale.JAPANESE, "%02d月 %02d日", kouserDat.getIraiMonth(), kouserDat.getIraiDay()), STRING_TAG_DEFAULT, nYpos, TextSize.SMALL);
            nYpos = printImageList.add(OtherUtil.printformat("#,###,##0", kouserDat.getIraiKin()) + "円", 0, nYpos, TextSize.SMALL, Align.RIGHT)[1];
        }

        // 次回予定日
        if (sy2fDat.getJifuriNext() == 1 && kouserDat.getNextTransYear() != 0 && kouserDat.getNextTransMonth() != 0 && kouserDat.getNextTransDay() != 0) {
            printImageList.add("次回引落予定日", STRING_TAG_DEFAULT, nYpos, TextSize.SMALL);
            nYpos = printImageList.add(String.format(Locale.JAPANESE, "%02d月%02d日", kouserDat.getNextTransMonth(), kouserDat.getNextTransDay()), 0, nYpos, TextSize.SMALL, Align.RIGHT)[1];
        }

        if(nStartHeight != nYpos - printImageList.RECT_PADDING * 2) {
            printImageList.addRectBold(nStartHeight, nYpos);
            addPrintData(printImageList.getBitmap());
            checkPageModeLength();
        }
    }

    @Override
    public void createPoint(UserData userData) throws MException {
        MLog.DEBUG(mContext, TAG, "標準ポイント印字処理[開始]");
        Sy2fDat sy2fDat = userData.getSy2fDat();
        KokfDat kokfDat = userData.getKokfDat();

        MLog.DEBUG(mContext, TAG, "ポイントVer:" + sy2fDat.getPntVer());
        MLog.DEBUG(mContext, TAG, "ポイント=" + kokfDat.getPoint());
        if(sy2fDat.getPntVer() > 0 && kokfDat.getPoint() > 0){
            if(sy2fDat.getPntDatName() == null || sy2fDat.getPntDatName().trim().isEmpty()){
                // ポイント名が空の場合は印字しない
                MLog.DEBUG(mContext, TAG, "標準ポイント印字処理[終了][ポイント名称無し]");
                return;
            }
            PrintImageList printDat = new PrintImageList(mContext);
            int nYpos = printDat.RECT_PADDING;
            int nStartPos = nYpos;
            nYpos += printDat.RECT_PADDING;
            printDat.add(OtherUtil.nullToString(sy2fDat.getPntDatName()), STRING_TAG_DEFAULT, nYpos, TextSize.SMALL);
            nYpos = printDat.add(OtherUtil.printformat("#,###,##0", kokfDat.getPoint()) + " P", 0, nYpos, TextSize.SMALL, Align.RIGHT )[1];
            printDat.addRect(nStartPos, nYpos);

            addPrintData(printDat.getBitmap());
            checkPageModeLength();
        }
        MLog.DEBUG(mContext, TAG, "標準ポイント印字処理[終了]");
    }


    /**
     * 秋元式ガス料金式印字
     *
     * @param printImageList    [in] {@link PrintImageList} 印字データ
     * @param nYpos             [in] int                    伝票印字開始高さ
     * @param kensinData        [in] {@link KensinData}     検針データ
     * @return int 伝票印字後の高さ
     */
    protected int printGasryokinA(PrintImageList printImageList, int nYpos, KensinData kensinData){
        long nPrnGasKin = kensinData.getGasBaseKin() + kensinData.getFacilityKin();
        long nNextBaseKin;
        double nWorkKin;
        GasfDat gasfDat = kensinData.getGasfDat();
        int nStartIdx = kensinData.getStartIdx();
        List<GstpDat> lstGstpDat = gasfDat.getLstGstpDat();
        Ko2fDat ko2f = kensinData.getKo2fDat();
        StringBuilder strStep = new StringBuilder();
        GstpDat gstpDat;

        if(gasfDat.getSum() != 3){
            gstpDat = lstGstpDat.get(nStartIdx);
        }
        else {
            gstpDat = new GstpDat();
            gstpDat.setUplimit(999999);
            gstpDat.setAdd((int)kensinData.getGasAddKin() * 10);
            gstpDat.setBase((int)kensinData.getGasBaseKin());
        }
        long nGasTotalKin = (int)(kensinData.getGasPay() - kensinData.getGasBaseKin() / 1000 - kensinData.getFacilityKin() / 1000);
        nYpos += printImageList.RECT_PADDING * 2;

        if(kensinData.isHybrid() && ko2f.getGashyb() != 0) {
            strStep.append("通常使用分従量料金");
        }else{
            strStep.append("従量料金");
        }
        nYpos = printImageList.add(strStep.toString(), STRING_TAG_DEFAULT, nYpos, TextSize.XSMALL, PrintBaseImage.STROKE_WIDTH_NORMAL)[1];

        long nAddKin = gstpDat.getAdd();
        if(gasfDat.getSum() == 2 || nAddKin > 0){
            nAddKin += gasfDat.getChoTanka();
        }
        nAddKin = OtherUtil.hasCom(nAddKin, gasfDat.getFrac1Add(), gasfDat.getFrac1Mult(), 10000.);
        nAddKin += (nAddKin * gasfDat.getRiseFall() / 1000L);

        // add new s
        long nSur = kensinData.getNowUse();
        if(kensinData.isHybrid() && ko2f.getGashyb() > 0){
            // ハイブリッドの場合は通常カウンタ使用量を設定
            nSur = kensinData.getNorSr();
            nGasTotalKin = (ko2f.getNorKin() - kensinData.getGasBaseKin() / 1000 - kensinData.getFacilityKin() / 1000);
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

        nYpos = printGasRyokinStep_A(printImageList, nYpos, 1, gstpDat.getUplimit(), nAddKin, nGasStepKin);

        if(!kensinData.isSingleStep() && nSur > gstpDat.getUplimit()) {
            nStartIdx++;
            for (int i = nStartIdx; i < lstGstpDat.size(); i++) {
                GstpDat prevGstpDat = gstpDat;
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
                        nGasStepKin = (long) (nAddKin * (gstpDat.getUplimit() - prevGstpDat.getUplimit()) * 0.00001 + 0.0001);
                    }
                    nGasTotalKin -= nGasStepKin;
                }
                nPrnGasKin += nGasStepKin * 1000;

                nYpos = printGasRyokinStep_A(printImageList, nYpos, prevGstpDat.getUplimit() + 1, gstpDat.getUplimit(), nAddKin, nGasStepKin);

                if (nSur <= gstpDat.getUplimit()) {
                    break;
                }
            }
        }

        //HieuNote chưa làm tới
//        nYpos = printGasryokin_Hybrid(printImageList, nYpos, kensinData, 0);

        return nYpos;
    }


    /**
     * 大口式ガス料金式印字
     *
     * @param printImageList    [in] {@link PrintImageList} 印字データ
     * @param nYpos             [in] int                    伝票印字開始高さ
     * @param kensinData        [in] {@link KensinData      検針データ
     * @return int 伝票印字後の高さ
     */
    protected int printGasryokinO(PrintImageList printImageList, int nYpos, KensinData kensinData){
        GasfDat gasfDat = kensinData.getGasfDat();
        Ko2fDat ko2fDat = kensinData.getKo2fDat();
        StringBuilder strStep = new StringBuilder();

        int nStartIdx = kensinData.getStartIdx();
        List<GstpDat> lstGstpDat = gasfDat.getLstGstpDat();
        GstpDat gstpDat;
        if(gasfDat.getSum() != 3){
            gstpDat = lstGstpDat.get(kensinData.getStartIdx());
        }
        else {
            gstpDat = new GstpDat();
            gstpDat.setUplimit(999999);
            gstpDat.setBase((int)kensinData.getGasBaseKin());
            gstpDat.setAdd((int)kensinData.getGasAddKin() * 10);
        }
        nYpos += printImageList.RECT_PADDING * 2;

        if(kensinData.isHybrid() && ko2fDat.getGashyb() != 0) {
            strStep.append("通常使用分従量料金");
        }else{
            strStep.append("従量料金");
        }
        nYpos = printImageList.add(strStep.toString(), STRING_TAG_DEFAULT, nYpos, TextSize.XSMALL, PrintBaseImage.STROKE_WIDTH_NORMAL)[1];

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
        printImageList.add(strStep.toString(), STRING_TAG_LIMIT, nYpos, TextSize.XSMALL, PrintBaseImage.STROKE_WIDTH_NORMAL);
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
        strStep.append(OtherUtil.format("#,##0.00", nAddKin, (byte) 4)).append("円");
        nYpos = printImageList.add(strStep.toString(), 0, nYpos, TextSize.XSMALL, Align.RIGHT, PrintBaseImage.STROKE_WIDTH_NORMAL)[1];

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
                nYpos = printGasRyokinStep_O(printImageList, nYpos, prevGstpDat.getUplimit() + 1, gstpDat.getUplimit(), nAddKin);

                if (nSur <= gstpDat.getUplimit()) {
                    break;
                }
            }
        }

        //HieuNote chưa làm tới
//        nYpos = printGasryokin_Hybrid(printImageList, nYpos, kensinData, 1);

        return nYpos;
    }


    @Override
    public void createCnComment(KensinData kensinData) {
        if(kensinData.isCnp()) {
            PrintImageList printDat = new PrintImageList(mContext);
            int yPos = printDat.RECT_PADDING;
            int yStartPos = yPos;
            yPos += printDat.RECT_PADDING;
            CnpCusDat cnpCusDat = kensinData.getCnpCusDat();

            if (cnpCusDat.getCnpMembers() > 0) {
                // 本会員はID印字
                printDat.add("CN会員ID", STRING_TAG_DEFAULT, yPos, TextSize.SMALL);
                yPos = printDat.add(cnpCusDat.getCnpMembersId(), 0, yPos, TextSize.SMALL, Align.RIGHT)[1];
            }

            printDat.add("前月獲得ポイント", STRING_TAG_DEFAULT, yPos, TextSize.SMALL);
            yPos = printDat.add(OtherUtil.format("#,###,##0", cnpCusDat.getCnpZpoint()) + "Pt", 0, yPos, TextSize.SMALL, Align.RIGHT)[1];
            if (cnpCusDat.getCnpMembers() > 0) {
                // 本会員
                printDat.add("利用可能ポイント", STRING_TAG_DEFAULT, yPos, TextSize.SMALL);
            } else if (cnpCusDat.getCnpTemp() > 0) {
                // 仮会員
                printDat.add("お試しポイント", STRING_TAG_DEFAULT, yPos, TextSize.SMALL);
            }
            yPos = printDat.add(OtherUtil.format("#,###,##0", cnpCusDat.getCnpZpoint()) + "Pt", 0, yPos, TextSize.SMALL, Align.RIGHT)[1];
            if (cnpCusDat.getCnpMembers() == 0 && cnpCusDat.getCnpTemp() > 0) {
                // 仮会員はコメントを追加
                for(String strTempCmt : kensinData.getCnpTempCmt()){
                    yPos = printDat.add(strTempCmt, STRING_TAG_DEFAULT, yPos, TextSize.SMALL)[1];
                }
            }

            List<String> lstCmt = kensinData.getCnpCmt();
            for (String strCmt : lstCmt) {
                yPos = printDat.add(strCmt, STRING_TAG_DEFAULT, yPos, TextSize.XSMALL)[1];
            }
            yPos += printDat.RECT_PADDING;
            printDat.addRect(yStartPos, yPos);

            addPrintData(printDat.getBitmap());
        }
    }


    @Override
    public void createMiyaPoint(UserData userData) throws MException {
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

        PrintImageList printDat = new  PrintImageList(mContext);
        int yPos = printDat.RECT_PADDING;
        int yStartPos = yPos;
        yPos += printDat.RECT_PADDING * 2;
        if(sy2fDat.getSysOption()[SysOption.PRINT_MIYANO_GET.getIdx()] == 1){
            // 獲得ポイント
            printDat.add("獲得ポイント", STRING_TAG_DEFAULT, yPos, TextSize.SMALL);
            yPos = printDat.add(OtherUtil.format("#,###,##0", kouserDat.getMiyanoGetpnt()) + "P", 0, yPos, TextSize.SMALL, Align.RIGHT)[1];
        }
        if(sy2fDat.getSysOption()[SysOption.PRINT_MIYANO_USE.getIdx()] == 1){
            // 使用ポイント
            printDat.add("使用ポイント", STRING_TAG_DEFAULT, yPos, TextSize.SMALL);
            yPos = printDat.add(OtherUtil.format("#,###,##0", kouserDat.getMiyanoUsepnt()) + "P", 0, yPos, TextSize.SMALL, Align.RIGHT)[1];
        }
        if(sy2fDat.getSysOption()[SysOption.PRINT_MIYANO_RUI.getIdx()] == 1){
            // 累計ポイント
            printDat.add("現在ポイント", STRING_TAG_DEFAULT, yPos, TextSize.SMALL);
            yPos = printDat.add(OtherUtil.format("#,###,##0", kouserDat.getMiyanoZanpnt()) + "P", 0, yPos, TextSize.SMALL, Align.RIGHT)[1];
        }

        if(yPos != yStartPos + printDat.RECT_PADDING * 2){
            printDat.addRect(yStartPos, yPos);
            addPrintData(printDat.getBitmap());
            checkPageModeLength();
        }
    }

    //Hieu
    public void createGasryokinSiki(PrintImageList printImageList, KensinData kensinData, int nYpos) {
        GasfDat gasfDat = kensinData.getGasfDat();
        //mGS.gasfDat
        //mGS.bIsVisibleGas
        if(gasfDat != null && kensinData.isVisibleGas()) {
            //mGS.bIsPrintGasRyokinSiki
            if (kensinData.isPrintGasRyokinSiki()) {
                if (gasfDat.getSum() != 4) {

                    // set draw format
                    // 初期値：秋元式
                    int[] START_POSITION_RYOKIN_TABLE_X = START_POSITION_RYOKIN_TABLE_A;
                    //mGS.nPrintGasRyokinSikiPtn
                    if (kensinData.getPrintGasRyokinSikiPtn() == 1) {
                        // 大口式
                        START_POSITION_RYOKIN_TABLE_X = START_POSITION_RYOKIN_TABLE_O;
                    }

                    // 手入力以外
                    nYpos += printImageList.RECT_PADDING * 2;
                    // 矩形印字開始高さ
                    int nStartPos = nYpos;
                    int nStartPosBold = nYpos;
                    nYpos += printImageList.RECT_PADDING * 2;
                    String strTitle = "ガス料金内訳";
                    if (gasfDat.getTaxDiv() == 2) {
                        strTitle += "（消費税込み）";
                    } else if (gasfDat.getTaxDiv() == 3) {
                        strTitle += "（消費税抜き）";
                    }
                    nYpos = printImageList.add(strTitle, STRING_TAG_DEFAULT, nYpos, TextSize.SMALL, PrintBaseImage.STROKE_WIDTH_NORMAL)[1];
                    printImageList.addRectBold(START_TAG, nStartPos, nYpos);
                    nStartPos = nYpos;
                    nYpos += printImageList.RECT_PADDING * 2;
                    printImageList.add("基本料金", STRING_TAG_DEFAULT, nYpos, TextSize.XSMALL, PrintBaseImage.STROKE_WIDTH_NORMAL);
                    nYpos = printImageList.add(
                            OtherUtil.KingakuFormat(kensinData.getGasBaseKin() / 1000) + "円",   //mGS.nGasBaseKin
                            0,
                            nYpos,
                            TextSize.XSMALL,
                            Align.RIGHT,
                            PrintBaseImage.STROKE_WIDTH_NORMAL
                    )[1];
                    printImageList.addRectMulti(getRectStartX(START_POSITION_RYOKIN_TABLE_X), nStartPos, nYpos);
                    // 基本料金矩形印字
                    if (kensinData.isPrintGasFacilityKin()) {      //mGS.bPrintGasFacilityKin
                        // 設備料金印字
                        nStartPos = nYpos;
                        nYpos += printImageList.RECT_PADDING * 2;
                        printImageList.add("設備料金", STRING_TAG_DEFAULT, nYpos, TextSize.XSMALL, PrintBaseImage.STROKE_WIDTH_NORMAL);
                        nYpos = printImageList.add(
                                OtherUtil.KingakuFormat(kensinData.getFacilityKin() / 1000) + "円",  //mGS.nFacilityKin
                                0,
                                nYpos,
                                TextSize.XSMALL,
                                Align.RIGHT,
                                PrintBaseImage.STROKE_WIDTH_NORMAL
                        )[1];
                        // 設備料金矩形印字
                        printImageList.addRectMulti(getRectStartX(START_POSITION_RYOKIN_TABLE_X), nStartPos, nYpos);
                    }
                    // ガス料金式印字
                    nStartPos = nYpos;

                    switch (kensinData.getPrintGasRyokinSikiPtn()) {        ////mGS.nPrintGasRyokinSikiPtn có rồi
                        case 0: // 秋元式
                            nYpos = printGasryokinA(printImageList, nYpos, kensinData);
                            break;
                        case 1: // 大口式
                            nYpos = printGasryokinO(printImageList, nYpos, kensinData);
                            break;
                    }

                    printImageList.addRectMulti(getRectStartX(START_POSITION_RYOKIN_TABLE_X), nStartPos, nYpos);

                    if (kensinData.getPrintGasRyokinSikiPtn() == 0) {   ////mGS.nPrintGasRyokinSikiPtn có rồi
                        nStartPos = nYpos;
                        nYpos += printImageList.RECT_PADDING * 2;
                        printImageList.add("合計", STRING_TAG_DEFAULT, nYpos, TextSize.XSMALL, PrintBaseImage.STROKE_WIDTH_NORMAL);
                        nYpos = printImageList.add(
                                OtherUtil.KingakuFormat(kensinData.getGasTotalKinWithoutTax()) + "円",   //mGS.nGasTotalKinWithoutTax
                                0,
                                nYpos,
                                TextSize.XSMALL,
                                Align.RIGHT,
                                PrintBaseImage.STROKE_WIDTH_NORMAL
                        )[1];
                        printImageList.addRectMulti(getRectStartX(START_POSITION_RYOKIN_TABLE_X), nStartPos, nYpos);
                    }
                    printImageList.addRectBold(nStartPosBold, nYpos);
                }
            }


            //HieuNote
//            // ガス料金コメントの印字
//            printGasryokinComment(printImageList, gasfDat.mGextDat, nYpos);
        }
    }


    //Hieu
    @Override
    public void createGasryokinTotal(PrintImageList printImageList, KensinInfoBaseDat mKSIB, int nYpos) {
        int nStartPos = nYpos;
        nYpos += printImageList.RECT_PADDING * 3;
        printImageList.add("ガス料金総額", STRING_TAG_NOW, nYpos, TextSize.NORMAL, PrintBaseImage.STROKE_WIDTH_BOLD);
        nYpos = printImageList.add(     //KSIB.sGasTotalKin
                mKSIB.getGasTotalKin(),
                0,
                nYpos,
                TextSize.NORMAL,
                Align.RIGHT,
                PrintBaseImage.STROKE_WIDTH_BOLD)[1];
        nYpos += printImageList.RECT_PADDING;
//        printImageList.addRectMultiBold(getRectStartX(START_POSITION_SEIKYU), nStartPos, nYpos);
        printImageList.addRectBold(START_POSITION_SEIKYU[0], nStartPos, nYpos);
    }


    @Override
    public void createHiwariComment(PrintImageList printImageList, KensinData kensinData, int nYpos) {
        nYpos += printImageList.RECT_PADDING * 2;
        int nStartPos = nYpos;
        nYpos += printImageList.RECT_PADDING * 2;

        if(OtherUtil.getClearString(kensinData.getHiwariComment_0()).length() != 0){
            nYpos = printImageList.add(kensinData.getHiwariComment_0(), 20, nYpos, TextSize.XSMALL)[1];
        }
        if (OtherUtil.getClearString(kensinData.getHiwariComment_1()).length() != 0){
            nYpos = printImageList.add(kensinData.getHiwariComment_1(), 20, nYpos, TextSize.XSMALL)[1];
        }
        nYpos += printImageList.RECT_PADDING;
        printImageList.addRect(nStartPos, nYpos);
    }

    //Hieu
    @Override
    public int createZenYearkenSr(PrintImageList printImageList, KensinInfoBaseDat mKSIB, int nYpos) {
        if(!mKSIB.isPrintZenYearKenSr()){   //KSIB.bIsPrintZenYearKenSr
            return nYpos;
        }
        printImageList.add("前年同月使用量", STRING_TAG_PREVIEW, nYpos, TextSize.SMALL);       //KSIB.sZenYearKenSr
        nYpos = printImageList.add(
                mKSIB.getZenYearKenSr(),
                0,
                nYpos,
                TextSize.SMALL,
                Align.RIGHT)[1];
        return nYpos;
    }

    //Hieu
    /**
     * ハイブリッドガス料金式の印字(秋元式)
     *
     * @param printImageList    [in] {@link PrintImageList} 帳票データ
     * @param nYpos             [in] int                    印字開始高さ
     * @param kensinData        [in] {@link KensinData}     印字データ
     * @return int 印字後の伝票高さ
     */
    protected int printGasryokin_Hybrid(PrintImageList printImageList, int nYpos, KensinData kensinData, int nType){

//        Ko2fDat ko2fDat = kensinData.getKo2fDat();
//        if(kensinData.isHybrid() && ko2fDat.getGashyb() > 0) {
//            HybfDat hybf = kensinData.getHybfDat(); //ﾊｲﾌﾞﾘｯﾄﾞﾒｰﾀｰ料金表
//            double nAddKin;
//            double nTotalKin;
//            int nStep;
//            long nGasTotal;
//            boolean bSingleStep;
//            String str;
//
//            for (int j = 0; j < Ko2fDat.getHyb_MAX(); j++) {
//                if (hybf.mCusef[j] == 1 && ko2fDat.getFee()[j] != 0) {
//                    nStep = 0;
//                    //カウンタ名称
//                    str = kensinData.getCounterName()[j];
//                    printImageList.add(str, GASRKTBL_POS[0], nYpos, TextSize.XSMALL, PrintBaseImage.STROKE_WIDTH_NORMAL);
//
//                    nGasTotal = ko2fDat.getFee()[j];
//                    nAddKin = hybf.mGasAdd[j][nStep] * 10;
//                    long nSur = kensinData.getHybGasUse()[j];
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
//                    nYpos += 30;
//
//                    // ステップの下限値～上限値を印字
//                    if(nType == 0) {
//                        // A式
//                        nYpos = printGasRyokinStep_A(printImageList, nYpos, 1, hybf.mGasLimit[j][nStep], nAddKin, nTotalKin);
//                    }
//                    else {
//                        // O式
//                        nYpos = printGasRyokinStep_O(printImageList, nYpos, 1, hybf.mGasLimit[j][nStep], nAddKin);
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
//                                nYpos = printGasRyokinStep_A(printImageList, nYpos, hybf.mGasLimit[j][nStep - 1] + 1, hybf.mGasLimit[j][nStep], nAddKin, nTotalKin);
//                            }
//                            else {
//                                // O式
//                                nYpos = printGasRyokinStep_O(printImageList, nYpos, hybf.mGasLimit[j][nStep - 1] + 1, hybf.mGasLimit[j][nStep], nAddKin);
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
//            nYpos = printCounterUseKin(printImageList, nYpos, ko2fDat, hybf, true, nType);
//        }
        return nYpos;
    }


    /**
     * カウンタ使用料
     *
     * @param printImageList    [in] {@link PrintImageList} 帳票データ
     * @param nYpos             [in] int                    印字開始高さ
     * @param mCUKD             [in] {@link CounterUseKinDat}        ハイブリッド料金表データ
     * @param isRect            [in] boolean                矩形印字有無
     * @return  int 印字後の伝票高さ
     */
    private int printCounterUseKin(PrintImageList printImageList, int nYpos, CounterUseKinDat mCUKD, boolean isRect, int nType){
        int nStartPos = 0;

        //KSIB.CounterUseKinDat.nUseKin
        //KSIB.CounterUseKinDat.nUseSncode
        if (mCUKD.getUseKin() > 0 && mCUKD.getUseSncode() > 0) {
            nYpos += 10;
            int nPos = STRING_TAG_NOW;
            float nStyle = PrintBaseImage.STROKE_WIDTH_BOLD;
            TextSize enumTextSize = TextSize.SMALL;
            if(isRect) {
                enumTextSize = TextSize.XSMALL;
                nPos = STRING_TAG_DEFAULT;
                nStyle = PrintBaseImage.STROKE_WIDTH_NORMAL;
                nStartPos = nYpos;
                nYpos += printImageList.RECT_PADDING * 2;
            }
            printImageList.add("カウンタ使用料", nPos, nYpos, enumTextSize, nStyle);

            //KSIB.CounterUseKinDat.sKin
            nYpos = printImageList.add(
                    mCUKD.getKin(),
                    0,
                    nYpos,
                    enumTextSize,
                    Align.RIGHT,
                    nStyle
            )[1];

            if(isRect) {
                if (nType == 0) {
                    // A式
                    printImageList.addRectMulti(getRectStartX(START_POSITION_RYOKIN_TABLE_A), nStartPos, nYpos);
                }
                else {
                    // O式
                    printImageList.addRectMulti(getRectStartX(START_POSITION_RYOKIN_TABLE_O), nStartPos, nYpos);
                }

            }
        }

        return nYpos;
    }

    /**
     * ガス料金式(A式)印刷データの生成.
     *
     * @param printImageList    [in] {@link PrintBaseImage} 印刷データ
     * @param nYpos             [in] int                    印刷開始位置
     * @param dLowLimit         [in] double                 下限値
     * @param dUpLimit          [in] double                 上限値
     * @param dAddKin           [in] double                 加算値
     * @param dTotalKin         [in] double                 ステップ金額
     * @return int  印刷後の高さ
     */
    protected int printGasRyokinStep_A(PrintImageList printImageList, int nYpos, double dLowLimit, double dUpLimit, double dAddKin, double dTotalKin){
        // ステップの下限値～上限値を印字
        String strStep = OtherUtil.printformat((int) dLowLimit, "####0.0", (byte) 1) +   // 下限値
                "→" +
                OtherUtil.printformat((int) dUpLimit, "####0.0", (byte) 1);     // 上限値
        printImageList.add(strStep, GASRKTBL_POS[0], nYpos, TextSize.XSMALL, PrintBaseImage.STROKE_WIDTH_NORMAL);
        nYpos += 6;
        printImageList.add("m3 単価", GASRKTBL_POS[1], nYpos, TextSize.XXSMALL);
        nYpos -= 6;
        printImageList.add(OtherUtil.format("#,##0.00", dAddKin, (byte) 4), GASRKTBL_POS[2], nYpos, TextSize.XSMALL, PrintBaseImage.STROKE_WIDTH_NORMAL);
        nYpos += 6;
        printImageList.add("円", GASRKTBL_POS[3], nYpos, TextSize.XXSMALL, PrintBaseImage.STROKE_WIDTH_NORMAL);
        nYpos -= 6;
        nYpos = printImageList.add(OtherUtil.KingakuFormat((long)dTotalKin) + "円", 0, nYpos, TextSize.XSMALL, Align.RIGHT, PrintBaseImage.STROKE_WIDTH_NORMAL)[1];

        return nYpos;
    }

    /**
     * ガス料金式(O式)印刷データの生成.
     *
     * @param printImageList    [in] {@link PrintImageList} 印刷格納データ
     * @param nYpos             [in] int                    印刷開始高さ
     * @param dLowLimit         [in] double                 下限値
     * @param dUpLimit          [in] double                 上限値
     * @param dAddKin           [in] double                 加算値
     * @return  int 印刷後の高さ
     */
    protected int printGasRyokinStep_O(PrintImageList printImageList, int nYpos, double dLowLimit, double dUpLimit, double dAddKin){
        // ステップの下限値～上限値を印字
        String strStep = OtherUtil.printformat((int) dLowLimit, "####0.0", (byte) 1) +    // 下限値
                "m3 ～ " +
                OtherUtil.printformat((int) dUpLimit, "####0.0", (byte) 1) + // 上限値
                "m3";
        printImageList.add(strStep, STRING_TAG_LIMIT, nYpos, TextSize.XSMALL, PrintBaseImage.STROKE_WIDTH_NORMAL);
        nYpos = printImageList.add(OtherUtil.format("#,##0.00", dAddKin, (byte) 4) + "円", 0, nYpos, TextSize.XSMALL, Align.RIGHT, PrintBaseImage.STROKE_WIDTH_NORMAL)[1];

        return nYpos;
    }


    @Override
    public void createHybComment(KensinData kensinData) throws MException {
        String str;
        int nYpos;
        long lGaskin;
        long lHybkin;

        Ko2fDat ko2fDat = kensinData.getKo2fDat();

        PrintImageList printImageList = new PrintImageList(mContext);
        nYpos = printImageList.RECT_PADDING;
        int nStartheight = nYpos;

        if(!kensinData.isHybrid() || ko2fDat.getGashyb() == 0){
            return; // 0
        }

        lGaskin = kensinData.getOnlyGas();

        //2010.06.08 ハイブリッド価格
        //           ガス料金（ハイブリッド使用量単位）
        //         ＋原料調整費（税込み）
        //         ＋消費税
        //         ＋カウンター料
        lHybkin = ko2fDat.getChoKin() + ko2fDat.getChoTax();

        if (lHybkin < 0) {
            nYpos += 5;
            str = "　通常料金の場合 ";
            str += OtherUtil.KingakuFormat(lGaskin) + "円です。";

            printImageList.add(str, 20, nYpos, TextSize.XSMALL);
            nYpos = printImageList.getHeightPrintLine();

            str = "　　今回のガスご使用料金は、";

            printImageList.add(str, 20, nYpos, TextSize.XSMALL);
            nYpos = printImageList.getHeightPrintLine();

            str = "　当社標準価格より " ;
            str +=  OtherUtil.KingakuFormat((lHybkin * -1)) + "円お得です。" ;
            printImageList.add(str, 20, nYpos, TextSize.XSMALL);
            nYpos = printImageList.getHeightPrintLine();


            //罫線
            nYpos += 5;

            printImageList.addRectBold(nStartheight, nYpos);
            addPrintData(printImageList.getBitmap());
            printImageList.clear();
            checkPageModeLength();
        }
    }


    //Hieu
    @Override
    public void createHybTblPrint(KensinData kensinData) throws MException {
        String strLine;
        int nIdx;
        int[] lstXpos = { 25, 205, 325, 440, 600 };
        int nXpos;

        Ko2fDat ko2fDat = kensinData.getKo2fDat();
        HybfDat hybfDat = kensinData.getHybfDat();

        PrintImageList printImageList = new PrintImageList(mContext);
        int nYpos = 10;
        int t_startheight = nYpos - printImageList.RECT_PADDING;

        if(!kensinData.isHybrid() || ko2fDat.getGashyb() == 0){
            return;
        }

        nXpos = lstXpos[0];
        strLine =  "ご使用量の内訳" ;
        printImageList.add(strLine, nXpos, nYpos, TextSize.XSMALL);

        nXpos = lstXpos[1];
        strLine =  "今回指針" ;
        printImageList.add(strLine, nXpos, nYpos, TextSize.XSMALL);

        nXpos = lstXpos[2];
        strLine =  "前回指針" ;
        printImageList.add(strLine, nXpos, nYpos, TextSize.XSMALL);

        nXpos = lstXpos[3];
        strLine =  "今回使用量";
        printImageList.add(strLine, nXpos, nYpos, TextSize.XSMALL);
        nYpos = printImageList.getHeightPrintLine();

        //----------------------------------------------------------------
        //罫線
        // 横線
        printImageList.addRect(t_startheight, nYpos);

        //----------------------------------------------------------------
        // ご使用量合計
        nYpos += 35;
        nXpos = lstXpos[0];
        strLine =  "ご使用量合計";
        printImageList.add(strLine, nXpos, nYpos, TextSize.XSMALL);

        //----------------------------------------------------------------
        // 今回指針
        nXpos = lstXpos[1]-30;
        strLine = OtherUtil.printformat(kensinData.getSisin(), "#,##0.0", (byte) 1);
        printImageList.add(strLine + "m3", nXpos, nYpos, TextSize.XSMALL, PrintBaseImage.STROKE_WIDTH_NORMAL);

        //----------------------------------------------------------------
        // 前回指針
        nXpos = lstXpos[2]-30;
        printImageList.add(OtherUtil.printformat(kensinData.getSisinPrev(), "#,##0.0", (byte) 1)+"m3", nXpos, nYpos, TextSize.XSMALL, PrintBaseImage.STROKE_WIDTH_NORMAL);

        //----------------------------------------------------------------
        // 使用量
        nXpos = lstXpos[3]-10;
        printImageList.add(OtherUtil.printformat(kensinData.getNowUse(), "#,##0.0", (byte) 1)+"m3", nXpos, nYpos, TextSize.XSMALL, PrintBaseImage.STROKE_WIDTH_NORMAL);

        //----------------------------------------------------------------
        // 通常使用
        nYpos += 35;
        nXpos = lstXpos[0];
        strLine = "通常使用量";
        printImageList.add(strLine, nXpos, nYpos, TextSize.XSMALL);

        //----------------------------------------------------------------
        //通常使用量は、指針部分印字しない　2016.01.21
        //----------------------------------------------------------------

        //----------------------------------------------------------------
        // 使用量
        nXpos = lstXpos[3]-10;
        strLine =  OtherUtil.printformat( ko2fDat.getNorKin(), "#,##0.0", (byte) 1)+"m3";
        printImageList.add(strLine, nXpos, nYpos, TextSize.XSMALL);

        //----------------------------------------------------------------
        // ハイブリッドのカウンター
        for ( nIdx= 0 ; nIdx < Ko2fDat.getHyb_MAX() ; nIdx++ ) {
            if (hybfDat.getCusef()[nIdx] == 1 && ko2fDat.getFee()[nIdx] != 0) {
                nYpos += 35;

                //カウンタ名称
                nXpos = lstXpos[0];
                strLine = kensinData.getCounterName()[nIdx];
                printImageList.add(strLine, nXpos, nYpos, TextSize.XSMALL);

                //----------------------------------------------------------------
                // 今回指針
                nXpos = lstXpos[1]-30;
                strLine =  OtherUtil.printformat(ko2fDat.getNowMeter()[nIdx], "#,##0.0", (byte)1)+"m3";
                printImageList.add(strLine, nXpos, nYpos, TextSize.XSMALL);

                //----------------------------------------------------------------
                // 前回指針
                nXpos = lstXpos[2]-30;
                strLine =  OtherUtil.printformat(ko2fDat.getPreMeter()[nIdx], "#,##0.0", (byte)1)+"m3";
                printImageList.add(strLine, nXpos, nYpos, TextSize.XSMALL);


                //----------------------------------------------------------------
                // 使用量
                nXpos = lstXpos[3]-10;
                strLine =  OtherUtil.printformat(kensinData.getHybGasUse()[nIdx], "#,##0.0", (byte)1)+"m3";
                printImageList.add(strLine, nXpos, nYpos, TextSize.XSMALL);
            }

        }

        nYpos = printImageList.getHeightPrintLine();
        if (nYpos != t_startheight) {
            printImageList.addRectBold(t_startheight, nYpos);
            addPrintData(printImageList.getBitmap());
            checkPageModeLength();
        }
        printImageList.clear();
    }

    @Override
    public void createFunouComment(UserData userData) throws MException{
        Sy2fDat sy2fDat = userData.getSy2fDat();
        if(sy2fDat.getFunouPrint() == 0){
            return;
        }
        KouserDat kouserDat = userData.getKouserDat();
        if( kouserDat.getIraiStat() != 6 ){
            return;
        }
        PrintImageList printImageList = new PrintImageList(mContext);
        int nYpos = printImageList.RECT_PADDING;
        int nStartHeight = nYpos;
        nYpos += printImageList.RECT_PADDING * 2;

        String strFunouComment0 = OtherUtil.getClearString(sy2fDat.getSy2fFunouComment().getFunouComment0());
        String strFunouComment1 = OtherUtil.getClearString(sy2fDat.getSy2fFunouComment().getFunouComment1());
        if(!strFunouComment0.isEmpty()){
            nYpos = printImageList.add(strFunouComment0, 20, nYpos, TextSize.XSMALL)[1];
        }
        if(!strFunouComment1.isEmpty()){
            nYpos = printImageList.add(strFunouComment1, 20, nYpos, TextSize.XSMALL)[1];
        }

        if(nYpos != nStartHeight){
            printImageList.addRect(nStartHeight, nYpos);
            addPrintData(printImageList.getBitmap());
            checkPageModeLength();
        }
    }
}
