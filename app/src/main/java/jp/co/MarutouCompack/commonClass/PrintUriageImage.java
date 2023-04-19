package jp.co.MarutouCompack.commonClass;

import android.content.Context;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.baseClass.PrintBaseImage;
import jp.co.MarutouCompack.baseClass.PrintBaseImage2;
import jp.co.MarutouCompack.baseClass.PrintImageList;
import jp.co.MarutouCompack.baseClass.PrintUriageInterface;
import jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysOption;
import jp.co.MarutouCompack.commonClass.PrinterDat.KokfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UserData;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * 納品書印刷データ生成クラス(文字列)
 */
public class PrintUriageImage extends PrintBaseImage2 implements PrintUriageInterface {

    /** クラス識別用タグ */
    private static final String TAG = PrintUriageImage.class.getSimpleName();
    /** 印字位置 */
    protected static final int[] START_POSITION_SEIKYU = new int[] {START_TAG + RECT_MARGIN, 325};

    /**
     * コンストラクタ
     *
     * @param ctx      [in] {@link Context}    呼び出し元コンテキスト
     */
    public PrintUriageImage(Context ctx) {
        super(ctx);
        MLog.INFO(ctx, TAG, "納品書(画像)印刷処理[開始]");
    }

    @Override
    public void printExecute() throws MException {
        sendImageData();
        disconnect();
        MLog.INFO(mContext, TAG, "納品書(画像)印刷処理[終了]");
    }

    @Override
    protected int createHmInfo(HmefDat[] lstHmefDat, PrintImageList printImageList, SysfDat sysfDat, Map<Integer, HmefDat> mapHmefDat, boolean isTanka) {
        if (lstHmefDat == null || lstHmefDat.length == 0) {
            return 0;
        }
        int nTax = 0;
        int nYpos = printImageList.getHeightPrintLine();
        int nStartHeight;
        String strPrint;
        int nIdx;
        int[] nPositions = isTanka ? START_POSITION_HAN_TANKA : START_POSITION_HAN_NORMAL;
        for (HmefDat hmefDat : lstHmefDat) {
            if(!hmefDat.isUsef() || hmefDat.getHmCode() < sysfDat.getSnvalue() ){
                continue;
            }
            nStartHeight = nYpos;
            nYpos += printImageList.RECT_PADDING;
            nIdx = 0;
            // 日付
            if(hmefDat.getDenm() != 0) {
                strPrint = (hmefDat.getDenm() < 10 ? " " + hmefDat.getDenm() : hmefDat.getDenm()) + "/"
                        + (hmefDat.getDend() < 10 ? " " + hmefDat.getDend() : hmefDat.getDend());
                printImageList.add(strPrint, nPositions[nIdx], nYpos, PrintBaseImage.TextSize.XSMALL);
            }
            nIdx++;
            // 品目
            strPrint = OtherUtil.getClearString(hmefDat.getHmName());
            if(strPrint.getBytes(Charset.forName("Shift_JIS")).length > 20){
                printImageList.add(strPrint, nPositions[nIdx++], nYpos + 5, PrintBaseImage.TextSize.HMSMALL);
            }
            else {
                printImageList.add(strPrint, nPositions[nIdx++], nYpos, PrintBaseImage.TextSize.XSMALL);
            }
            // 数量
            if (hmefDat.getSuryo() != 0) {
                strPrint = OtherUtil.printformat(hmefDat.getSuryo(), "###0.00", (byte) 2);
            }
            else {
                strPrint = " ";
            }
            printImageList.add(strPrint, nPositions[nIdx++], nYpos, PrintBaseImage.TextSize.XSMALL);
            if(isTanka && hmefDat.getTanka() != 0){
                // 単価印字有り
                if(hmefDat.getTanka() % 100 == 0){
                    strPrint = OtherUtil.printformat(hmefDat.getTanka(), "###,##0", (byte) 2);
                }
                else if(hmefDat.getTanka() % 10 == 0){
                    strPrint = OtherUtil.printformat(hmefDat.getTanka(), "##,##0.0", (byte) 2);
                }
                else {
                    strPrint = OtherUtil.printformat(hmefDat.getTanka(), "#,##0.00", (byte) 2);
                }
                printImageList.add(strPrint, nPositions[nIdx++], nYpos, PrintBaseImage.TextSize.XSMALL);
            }
            // 金額
            int nKin = hmefDat.getKin();
            if(!mapHmefDat.isEmpty()){
                // 軽減税率対応は税込み
                nKin += hmefDat.getTax();
            }
            strPrint = OtherUtil.printformat("#,###,##0", nKin);
            HmefDat hmefDatKeigen = mapHmefDat.get(hmefDat.getKeigenKubun() * 1000 + hmefDat.getTaxR());
            if(hmefDatKeigen != null && hmefDatKeigen.getKeigenKubun() != 0){
                strPrint += String.format(Locale.JAPAN, "*%d", hmefDatKeigen.getCusRec());
            }
            nTax += hmefDat.getTax();
            nYpos = printImageList.add(strPrint, nPositions[nIdx], nYpos, PrintBaseImage.TextSize.XSMALL)[1];

            // リース割賦の残回数を印字する。// 2008.07.28
            if ( hmefDat.getHbnmPrn() == 1 ) {
                strPrint = hmefDat.getHbName();
                if (!strPrint.contains("(  ")) continue; // 残回数 '(  ' というパターンがなければ以下を除外する。
                strPrint = strPrint.replace( "(  ", "(" );
                strPrint = strPrint.replace( "/ ", "/" );

                //伝票日付
                //品目名
                nYpos = printImageList.add(strPrint, nPositions[1], nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                //数量
                // 単価
                //金額
            }

            printImageList.addRectMulti(getRectStartX(nPositions), nStartHeight, nYpos);
        }
        return nTax;
    }

    @Override
    public void createHeader(boolean isHikae, boolean isGenuri, String date) throws MException {
        PrintImageList lstPrintImage = new PrintImageList(mContext);
        int nXpos = 20;
        int nYpos = 10;
        
        String strTitle = "納　品　書";
        if(isGenuri){
            strTitle += " 兼 領 収 書";
        }
        if(isHikae){
            strTitle += " (控)";
        }
        nYpos = lstPrintImage.add(strTitle, nXpos, nYpos, PrintBaseImage.TextSize.LARGE, PrintBaseImage.Align.CENTER)[1];
        nYpos += 10;
        lstPrintImage.add(date, STRING_TAG_KENSINDATE, nYpos, PrintBaseImage.TextSize.SMALL);

        addPrintData(lstPrintImage.getBitmap());

        checkPageModeLength();
    }

    @Override
    public void createCusInfo(UserData userData, String strSname_0, String strSname_1) throws MException {
        PrintImageList printImageList = new PrintImageList(mContext);

        String wkStr;
        int nYpos = 10;
        int nStartheight;

        nStartheight = nYpos - printImageList.RECT_PADDING;
        nYpos += printImageList.RECT_PADDING;

        KokfDat kokfDat = userData.getKokfDat();
        // 顧客コード
        wkStr = "コード：" + kokfDat.getCusCode();
        nYpos = printImageList.add(wkStr, STRING_TAG_NOW, nYpos, PrintBaseImage.TextSize.SMALL)[1];

        // 顧客名
        if (OtherUtil.getClearString(strSname_0).length() > 0 && OtherUtil.getClearString(strSname_1).length() > 0) {
            nYpos = printImageList.add(strSname_0, STRING_TAG_DEFAULT, nYpos, PrintBaseImage.TextSize.NORMAL)[1];
            printImageList.add(strSname_1, STRING_TAG_DEFAULT, nYpos, PrintBaseImage.TextSize.NORMAL);
            nYpos = printImageList.add(kokfDat.getKName(), STRING_TAG_YOBI, nYpos, PrintBaseImage.TextSize.NORMAL)[1];
        }
        else if (OtherUtil.getClearString(strSname_0).length() > 0) {
            printImageList.add(strSname_0, STRING_TAG_DEFAULT, nYpos, PrintBaseImage.TextSize.NORMAL);
            nYpos = printImageList.add(kokfDat.getKName(), STRING_TAG_YOBI, nYpos, PrintBaseImage.TextSize.NORMAL)[1];
        }
        else {
            printImageList.add(strSname_1, STRING_TAG_DEFAULT, nYpos, PrintBaseImage.TextSize.NORMAL);
            nYpos = printImageList.add(kokfDat.getKName(), STRING_TAG_YOBI, nYpos, PrintBaseImage.TextSize.NORMAL)[1];
        }
        // 顧客住所
        String strAdd1 = OtherUtil.getClearString(kokfDat.getAdd0().substring(0, 20));
        String strAdd2 = OtherUtil.getClearString(kokfDat.getAdd1().substring(20));
        nYpos = printImageList.add(strAdd1, STRING_TAG_NOW, nYpos, PrintBaseImage.TextSize.XSMALL)[1];
        if (!strAdd2.equals("")) { // あれば住所その２
            nYpos = printImageList.add(strAdd2, STRING_TAG_NOW, nYpos, PrintBaseImage.TextSize.XSMALL)[1];
        }
        // 矩形印字
        printImageList.addRectBold(nStartheight, nYpos);

        this.addPrintData(printImageList.getBitmap());
        printImageList.clear();
        checkPageModeLength();
    }

    @Override
    public void createMeisaiInfo(UserData userData, List<HmefDat> lstHmefDat) throws MException {
        // 販売データ
        SysfDat sysfDat = userData.getSysfDat();
        boolean isUriage = isUriage(new HmefDat[0], new HmefDat[0], lstHmefDat.toArray(new HmefDat[0]), sysfDat);
        if (isUriage) { // 販売実績がある場合のみ印刷する
            boolean isTanka = userData.getSy2fDat().getSysOption()[SysOption.PRINT_TANKA.getIdx()] == 1;
            // 軽減税率データ
            Map<Integer, HmefDat> mapHmefDat = new LinkedHashMap<>();
            calcKeigen(mapHmefDat, lstHmefDat.toArray(new HmefDat[0]), userData);
            PrintImageList printImageList = new PrintImageList(mContext);
            createHmInfoHeader("", printImageList, isTanka);
            long lKin = 0L;
            int nTax = 0;
            for(HmefDat hmefDat : lstHmefDat){
                if(hmefDat.isUsef() && hmefDat.getHmCode() >= sysfDat.getSnvalue()){
                    lKin += hmefDat.getKin();
                    nTax += hmefDat.getTax();
                }
            }
            createHmInfo(lstHmefDat.toArray(new HmefDat[0]), printImageList, sysfDat, mapHmefDat, isTanka);
            createHmInfoTax(printImageList, mapHmefDat, userData, nTax, isTanka);
            createHmInfoFooter(printImageList, lKin + nTax);
            addPrintData(printImageList.getBitmap());
            printImageList.clear();
        }
        checkPageModeLength();
    }

    @Override
    protected void createHmInfoFooter(PrintImageList printImageList, long lKin){
        int nYpos = printImageList.getHeightPrintLine() + 20;
        int nStartHeight = nYpos - printImageList.RECT_PADDING;

        nYpos += printImageList.RECT_PADDING * 2;
        printImageList.add("本日売上金額", STRING_TAG_NOW, nYpos, PrintBaseImage.TextSize.NORMAL, PrintBaseImage.STROKE_WIDTH_BOLD);

        //通常料金
        nYpos = printImageList.add(OtherUtil.KingakuFormat(lKin) + "円", 0, nYpos, PrintBaseImage.TextSize.NORMAL, PrintBaseImage.Align.RIGHT, PrintBaseImage.STROKE_WIDTH_BOLD)[1];

        printImageList.addRectBold(nStartHeight, nYpos);

        nYpos += 20;
        printImageList.add("上記の通り納品致しました。有難うございました。", START_TAG, nYpos, PrintBaseImage.TextSize.XSMALL);
    }

    @Override
    public void createRyoshu(UserData userData, int nChokin, int nNyukin, int nRecept, long lSeikyu) throws MException{
        PrintImageList printImageList = new PrintImageList(mContext);
        int nYpos = printImageList.getHeightPrintLine() + 5;
        int nStartHeight = nYpos;
        nYpos += printImageList.RECT_PADDING * 2;
        String strPrint;

        strPrint = OtherUtil.KingakuFormat(lSeikyu) + "円";
        PrintBaseImage.TextSize enumTextSize = PrintBaseImage.TextSize.LARGE;
        if(lSeikyu >= 100000){
            enumTextSize = PrintBaseImage.TextSize.NORMAL;
        }
        printImageList.add(strPrint, 0, nYpos, enumTextSize, PrintBaseImage.Align.RIGHT, PrintBaseImage.STROKE_WIDTH_BOLD);
        nYpos = printImageList.add("今回請求額", STRING_TAG_DEFAULT, nYpos, PrintBaseImage.TextSize.LARGE, PrintBaseImage.STROKE_WIDTH_BOLD)[1];

        // 調整額
        if (nChokin != 0) {
            if(nStartHeight == nYpos ){
                nYpos += printImageList.RECT_PADDING * 2;
            }
            // 調整額有り
            printImageList.add(getChoTitle(userData), STRING_TAG_PREVIEW, nYpos);
            strPrint = OtherUtil.KingakuFormat(nChokin) + "円";
            nYpos = printImageList.add(strPrint, 0, nYpos, PrintBaseImage.TextSize.NORMAL, PrintBaseImage.Align.RIGHT)[1];
        }

        // 本日入金額
        if (nNyukin != 0) {
            if(nStartHeight == nYpos ){
                nYpos += printImageList.RECT_PADDING * 2;
            }
            if (nRecept == nNyukin) { // 13.02.12
                strPrint = "本日入金額";
            }
            else {
                strPrint = "本日お預かり金額";
            }
            printImageList.add(strPrint, STRING_TAG_PREVIEW, nYpos);
            strPrint = OtherUtil.KingakuFormat(nRecept) + "円";
            nYpos = printImageList.add(strPrint, 0, nYpos, PrintBaseImage.TextSize.NORMAL, PrintBaseImage.Align.RIGHT)[1];
        }

        // おつり
        long lOtsuri = nRecept - (lSeikyu + nChokin);
        if (lOtsuri > 0) {
            if(nStartHeight == nYpos ){
                nYpos += printImageList.RECT_PADDING * 2;
            }
            strPrint = "おつり";
            printImageList.add(strPrint, STRING_TAG_PREVIEW, nYpos);
            strPrint = OtherUtil.KingakuFormat(lOtsuri) + "円"; // 13.02.12
            nYpos = printImageList.add(strPrint, 0, nYpos, PrintBaseImage.TextSize.NORMAL, PrintBaseImage.Align.RIGHT)[1];
        }
        if (nStartHeight != nYpos) { // 印字行ありの場合のみ枠印字
            printImageList.addRectBold(START_POSITION_SEIKYU[0], nStartHeight, nYpos);
        }
        // 差引残高
        long lZandaka = lSeikyu + nChokin - nNyukin;
        if (lZandaka != 0) {
            if (nStartHeight != nYpos) { // 印字行ありの場合のみ枠印字
                nStartHeight = nYpos;
                nYpos += printImageList.RECT_PADDING * 2;
            }
            printImageList.add("差引残高", STRING_TAG_NOW, nYpos, PrintBaseImage.TextSize.NORMAL, PrintBaseImage.STROKE_WIDTH_BOLD);
            strPrint = OtherUtil.KingakuFormat(lZandaka) + "円";
            nYpos = printImageList.add(strPrint, 0, nYpos, PrintBaseImage.TextSize.NORMAL, PrintBaseImage.Align.RIGHT, PrintBaseImage.STROKE_WIDTH_BOLD)[1];
            printImageList.addRectMultiBold(getRectStartX(START_POSITION_SEIKYU), nStartHeight, nYpos);
        }

        nYpos += 25;
        nYpos += printImageList.RECT_PADDING * 2;
        nStartHeight = nYpos - printImageList.RECT_PADDING;
        strPrint = "領 収 印";
        nYpos = printImageList.add(strPrint, 429, nYpos, PrintBaseImage.TextSize.SMALL)[1];
        printImageList.addRect(412, nStartHeight, 150, nYpos - nStartHeight, PrintBaseImage.RECT_STROKE_BOLD);
        nStartHeight += nYpos - nStartHeight;
        printImageList.addRect(412, nStartHeight, 150, 150, PrintBaseImage.RECT_STROKE_BOLD);

        strPrint = "領収金額";
        int x_pos = 350 - (int) PrintBaseImage.getValuePosition(mContext, strPrint, PrintBaseImage.TextSize.LARGE, PrintBaseImage.STROKE_WIDTH_BOLD);
        nYpos = printImageList.add(strPrint, x_pos, nYpos, PrintBaseImage.TextSize.LARGE, PrintBaseImage.STROKE_WIDTH_BOLD)[1];

        strPrint = OtherUtil.KingakuFormat(nNyukin) + "円";
        x_pos = 350 - (int) PrintBaseImage.getValuePosition(mContext, strPrint, PrintBaseImage.TextSize.LARGE, PrintBaseImage.STROKE_WIDTH_BOLD);
        nYpos = printImageList.add(strPrint, x_pos, nYpos, PrintBaseImage.TextSize.LARGE, PrintBaseImage.STROKE_WIDTH_BOLD)[1];
        nYpos += 50;
        printImageList.add("上記の通り領収致しました。有難うございました。", START_TAG, nYpos, PrintBaseImage.TextSize.XSMALL);

        addPrintData(printImageList.getBitmap());
        printImageList.clear();
        checkPageModeLength();
    }

    @Override
    public void createComment(String[] commentData) throws MException {
        if (commentData.length == 0) return;
        PrintImageList t_lst = new PrintImageList(mContext);
        int t_ypos = 10;
        int t_startheight = t_ypos - t_lst.RECT_PADDING;

        for (int i = 0; i < commentData.length; i++) {
            if (commentData[i].length() == 0) continue;
            t_lst.add(commentData[i], 20, t_ypos, PrintBaseImage.TextSize.XSMALL);
            t_ypos = t_lst.getHeightPrintLine();
        }
        if (t_ypos != t_startheight) {
            t_lst.addRect(t_startheight, t_ypos);
        }
        addPrintData(t_lst.getBitmap());
        t_lst.clear();
        checkPageModeLength();
    }
}
