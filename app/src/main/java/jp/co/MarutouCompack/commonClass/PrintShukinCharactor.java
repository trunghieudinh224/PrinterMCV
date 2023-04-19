package jp.co.MarutouCompack.commonClass;

import android.content.Context;

import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.baseClass.PrintBaseCharactor;
import jp.co.MarutouCompack.baseClass.PrintShukinInterface;
import jp.co.MarutouCompack.commonClass.PrinterDat.KokfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysOption;
import jp.co.MarutouCompack.commonClass.PrinterDat.UserData;
import jp.co.MarutouCompack.commonClass.PrinterDat.WebData;

/**
 * 領収証印字データ生成クラス(文字列)
 */
public class PrintShukinCharactor extends PrintBaseCharactor implements PrintShukinInterface {

    /** ログ出力タグ */
    @SuppressWarnings("unused")
    private static final String TAG = PrintShukinCharactor.class.getSimpleName();

    /**
     * コンストラクタ.
     *
     * @param ctx       [in] {@link Context}    呼び出し元コンテキス
     * @throws MException   エラーがあった場合に発生
     */
    public PrintShukinCharactor(Context ctx) throws MException{
        super(ctx);
        // この段階で接続しておく
        connect();
    }

    @Override
    public void printExecute() throws MException {
        // 最終データの出力
        sendData();
        // 切断
        disconnect();
    }

    @Override
    public void createHeaderData(boolean isHikae, String kensinDate){
        String strTitle = "領　収　書";
        if(isHikae){
            strTitle += "（控）";
        }
        print( strTitle, CHARSIZE.LARGE, LAYOUT.CENTER );
        printFeedDot((byte)5);

        String strDate = "発行日　　" + kensinDate;
        printNormal(strDate);
    }

    @Override
    public void createCusInfo(UserData userData) throws MException {
        int nStartHeigh;
        int nEndHeight;
        printFeedDot((byte)10);
        nStartHeigh = printFeedDot((byte)5)[0];

        KokfDat kokfDat = userData.getKokfDat();
        printNormal("コード：　" + kokfDat.getCusCode());
        if (OtherUtil.getClearString(kokfDat.getSName0()).length() > 0 && OtherUtil.getClearString(kokfDat.getSName1()).length() > 0) {
            printNormal(addSpaceToString(kokfDat.getSName0()));
            printNormal(addSpaceToString(kokfDat.getSName1()) + kokfDat.getKName());
        }
        else if (OtherUtil.getClearString(kokfDat.getSName0()).length() > 0) {
            printNormal(addSpaceToString(kokfDat.getSName0()) + kokfDat.getKName());
        }
        else {
            printNormal(addSpaceToString(kokfDat.getSName1()) + kokfDat.getKName());
        }

        // 顧客住所
        String strAdd1 = OtherUtil.getClearString(kokfDat.getAdd0().substring(0, 20));
        String strAdd2 = OtherUtil.getClearString(kokfDat.getAdd1().substring(20));
        // 住所その１
        printNormal("　　" + strAdd1);
        if (!strAdd2.equals("")) { // あれば住所その２
            printNormal("　　" + strAdd2);
        }
        nEndHeight = printFeedDot((byte) 10)[1];
        // 矩形設定
        printRectangle(0, RECT_WIDTH, nStartHeigh, nEndHeight, STYLE.BOLD);
        printFeedDot((byte)10);
        checkPageModeLength();
    }

    @Override
    public void createRyosyuInfo(WebData mWebdata) throws MException {
        printFeedDot((byte)5);
        if(!mWebdata.getUserData().isNyukinOnly()) {
            if (mWebdata.getKensinData().getReceipt() != 0) {
                printSeikyuInfo("今回請求額", 0);
                printSeikyuInfo(OtherUtil.printformat("#,###,##0", mWebdata.getKensinData().getReceipt()) + "円", 1);
            }
            if (mWebdata.getKensinData().getChosei() != 0) {
                printSeikyuInfo(mWebdata.getANyukinDat().getChoseiTitle(), 0);
                printSeikyuInfo(OtherUtil.printformat("#,###,##0", mWebdata.getKensinData().getChosei()) + "円", 1);
            }
        }

        // 入金
        boolean isJust = mWebdata.getKensinData().getAzukarikin() == mWebdata.getKensinData().getReceipt() + mWebdata.getKensinData().getChosei();
        printSeikyuInfo(isJust ? "ご入金" : "お預り額", 0);
        printSeikyuInfo(OtherUtil.printformat("#,###,##0", mWebdata.getKensinData().getAzukarikin()) + "円", 1);

        if(mWebdata.getKensinData().getAzukarikin() > mWebdata.getKensinData().getNyukin()){
            // おつりあり
            printSeikyuInfo("おつり", 0);
            printSeikyuInfo(OtherUtil.printformat("#,###,##0", mWebdata.getKensinData().getAzukarikin() - mWebdata.getKensinData().getNyukin()) + "円", 1);
        }

        if(!mWebdata.getUserData().isNyukinOnly()) {
            long lZandaka = mWebdata.getKensinData().getReceipt() + mWebdata.getKensinData().getChosei() - mWebdata.getKensinData().getNyukin();
            if (isJust || lZandaka != 0) {
                // 差引残高
                int nStartHeight = printFeedDot((byte) 5)[0];
                printSeikyuInfo("差引残高", 0);
                int nEndHeight = printSeikyuInfo(OtherUtil.printformat("#,###,##0", lZandaka) + "円", 1)[1];
                printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.BOLD);
            }
        }
        checkPageModeLength();

        if (!mWebdata.getUserData().isNyukinOnly() && mWebdata.getUserData().getSy2fDat().getSysOption()[SysOption.NOT_PRINT_UTIZEI.getIdx()] == 0) { // 内税コメントの抑制フラグ
            createUTaxComment(mWebdata.getANyukinDat().getUTC());
        }
    }

    @Override
    public void createHmInfo(UserData wkUserData) throws MException {
        createHmInfoShukin(wkUserData);
    }
}
