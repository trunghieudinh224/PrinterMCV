package jp.co.MarutouCompack.commonClass;

import android.content.Context;

import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.baseClass.PrintBaseImage;
import jp.co.MarutouCompack.baseClass.PrintBaseImage2;
import jp.co.MarutouCompack.baseClass.PrintImageList;
import jp.co.MarutouCompack.baseClass.PrintShukinInterface;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysOption;
import jp.co.MarutouCompack.commonClass.PrinterDat.UserData;
import jp.co.MarutouCompack.commonClass.PrinterDat.WebData;
import jp.co.MarutouCompack.commonClass.PrinterDat.KokfDat;

/**
 * 領収証印字データ生成クラス(画像)
 */
public class PrintShukinImage extends PrintBaseImage2 implements PrintShukinInterface {

    /** ログ出力用タグ */
    @SuppressWarnings("unused")
    private static final String TAG = PrintShukeiImage.class.getSimpleName();

    protected static final int[] START_POSITION_SEIKYU = new int[] {START_TAG + RECT_MARGIN, 325};

    /**
     * コンストラクタ
     *
     * @param ctx      [in] {@link Context}    呼び出し元コンテキスト
     */
    public PrintShukinImage(Context ctx) {
        super(ctx);
    }

    @Override
    public void printExecute() throws MException {
        sendImageData();
        disconnect();
    }

    @Override
    public void createHeaderData(boolean isHikae, String kensinDate){
        PrintImageList lstPrintImage = new PrintImageList(mContext);
        int nXpos = 20;
        int nYpos = 10;
        
        String strTitle = "領　収　書";
        if(isHikae){
            strTitle += " (控)";
        }
        nYpos = lstPrintImage.add(strTitle, nXpos, nYpos, PrintBaseImage.TextSize.LARGE, PrintBaseImage.Align.CENTER)[1];
        nYpos += 10;
        String strDate = "発行日　　" + kensinDate;
        lstPrintImage.add(strDate, STRING_TAG_KENSINDATE, nYpos, PrintBaseImage.TextSize.SMALL);

        addPrintData(lstPrintImage.getBitmap());
    }

    @Override
    public void createCusInfo(UserData userData) throws MException {
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
        if (OtherUtil.getClearString(kokfDat.getSName0()).length() > 0 && OtherUtil.getClearString(kokfDat.getSName1()).length() > 0) {
            nYpos = printImageList.add(kokfDat.getSName0(), STRING_TAG_DEFAULT, nYpos, PrintBaseImage.TextSize.NORMAL)[1];
            printImageList.add(kokfDat.getSName1(), STRING_TAG_DEFAULT, nYpos, PrintBaseImage.TextSize.NORMAL);
            nYpos = printImageList.add(kokfDat.getKName(), STRING_TAG_YOBI, nYpos, PrintBaseImage.TextSize.NORMAL)[1];
        }
        else if (OtherUtil.getClearString(kokfDat.getSName0()).length() > 0) {
            printImageList.add(kokfDat.getSName0(), STRING_TAG_DEFAULT, nYpos, PrintBaseImage.TextSize.NORMAL);
            nYpos = printImageList.add(kokfDat.getKName(), STRING_TAG_YOBI, nYpos, PrintBaseImage.TextSize.NORMAL)[1];
        }
        else {
            printImageList.add(kokfDat.getSName1(), STRING_TAG_DEFAULT, nYpos, PrintBaseImage.TextSize.NORMAL);
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

        addPrintData(printImageList.getBitmap());
        printImageList.clear();
        checkPageModeLength();
    }


    @Override
    public void createRyosyuInfo(WebData mWebdata) throws MException {
        PrintImageList printImageList = new PrintImageList(mContext);
        int nYpos = 10;
        int nStartheight = nYpos - printImageList.RECT_PADDING;
        if(!mWebdata.getUserData().isNyukinOnly()) {
            if (mWebdata.getKensinData().getReceipt() != 0) {
                printImageList.add("今回請求額", STRING_TAG_DEFAULT, nYpos);
                nYpos = printImageList.add(OtherUtil.KingakuFormat(mWebdata.getKensinData().getReceipt()) + "円", 0, nYpos, PrintBaseImage.TextSize.NORMAL, PrintBaseImage.Align.RIGHT)[1];
            }
            if (mWebdata.getKensinData().getChosei() != 0) {
                printImageList.add(mWebdata.getANyukinDat().getChoseiTitle(), STRING_TAG_DEFAULT, nYpos);
                nYpos = printImageList.add(OtherUtil.KingakuFormat(mWebdata.getKensinData().getChosei()) + "円", 0, nYpos, PrintBaseImage.TextSize.NORMAL, PrintBaseImage.Align.RIGHT)[1];
            }
        }

        // 入金
        boolean isJust = mWebdata.getKensinData().getAzukarikin() == mWebdata.getKensinData().getNyukin();
        printImageList.add(isJust ? "ご入金" : "お預り額", STRING_TAG_DEFAULT, nYpos);
        nYpos = printImageList.add(OtherUtil.KingakuFormat(mWebdata.getKensinData().getAzukarikin()) + "円", 0, nYpos, PrintBaseImage.TextSize.NORMAL, PrintBaseImage.Align.RIGHT)[1];

        if(mWebdata.getKensinData().getAzukarikin() > mWebdata.getKensinData().getNyukin()){
            // おつりあり
            printImageList.add("おつり", STRING_TAG_DEFAULT, nYpos);
            nYpos = printImageList.add(OtherUtil.KingakuFormat(mWebdata.getKensinData().getAzukarikin() - mWebdata.getKensinData().getNyukin()) + "円", 0, nYpos, PrintBaseImage.TextSize.NORMAL, PrintBaseImage.Align.RIGHT)[1];
        }
//        printImageList.addRect(printImageList.getHeightPrintLine(), nYpos);
        printImageList.addRect(START_POSITION_SEIKYU[0], nStartheight, nYpos);

        if(!mWebdata.getUserData().isNyukinOnly()) {
            long lZandaka = mWebdata.getKensinData().getReceipt() + mWebdata.getKensinData().getChosei() - mWebdata.getKensinData().getNyukin();
            if (isJust || lZandaka != 0) {
                // 差引残高
                int nStartHeight = printImageList.getHeightPrintLine();
                nYpos += printImageList.RECT_PADDING * 2;
                printImageList.add("差引残高", STRING_TAG_DEFAULT, nYpos);
                nYpos = printImageList.add(OtherUtil.KingakuFormat(lZandaka) + "円", 0, nYpos, PrintBaseImage.TextSize.NORMAL, PrintBaseImage.Align.RIGHT)[1];
                printImageList.addRectBold(nStartHeight, nYpos);
            }
        }
        if (!mWebdata.getUserData().isNyukinOnly() && mWebdata.getUserData().getSy2fDat().getSysOption()[SysOption.NOT_PRINT_UTIZEI.getIdx()] == 0) { // 内税コメントの抑制フラグ
            createUTaxComment(mWebdata.getANyukinDat().getUTC(), printImageList);
        }
        addPrintData(printImageList.getBitmap());
        printImageList.clear();
        checkPageModeLength();
    }


    @Override
    public void createHmInfo(jp.co.MarutouCompack.commonClass.PrinterDat.UserData wkUserData) throws MException {
        createHmInfoShukin(wkUserData);
    }
}
