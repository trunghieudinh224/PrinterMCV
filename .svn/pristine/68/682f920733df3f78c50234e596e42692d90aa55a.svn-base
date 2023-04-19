package jp.co.MarutouCompack.commonClass;

import android.content.Context;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.baseClass.PrintBaseCharactor;
import jp.co.MarutouCompack.baseClass.PrintBaseImage;
import jp.co.MarutouCompack.baseClass.PrintUriageListInterface;
import jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UriageItemDat;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * 売上日報(文字列)印刷データ生成クラス.
 */
public class PrintUriageListCharactor extends PrintBaseCharactor implements PrintUriageListInterface {
    /** クラス識別用タグ */
    private static final String TAG = PrintUriageListCharactor.class.getSimpleName();

    /**
     * コンストラクタ<br />
     * ポート名及び設定情報を設定
     *
     * @param context   [in] {@link Context}    呼び出し元コンテキスト
     */
    public PrintUriageListCharactor(Context context) throws MException {
        super(context);
        // この段階で接続する
        MLog.INFO(context, TAG, "売上日報(文字列)印刷処理[開始]");
        connect();
    }

    @Override
    protected void printExecute(int wkPrintMode) throws MException {
        // 最後のデータを送信
        sendData();
        // 切断
        disconnect();
        MLog.INFO(mContext, TAG, "売上日報(文字列)印刷処理[終了]");
    }

    /**
     * 顧客情報印字.
     *
     * @param strMsg    [in] String 印字文字列
     * @param nNo       [in] int    印字位置
     */
    private void printCusInfo(String strMsg, int nNo){
        byte[] bPositions = new byte[]{
                2, 15
        };
        printHPInfo(bPositions, strMsg, nNo);
    }

    @Override
    public void createPrintData(Map<String, List<UriageItemDat>> mapUriageData, String tantname)  {
        print("売　上　日　報", CHARSIZE.LARGE, LAYOUT.CENTER);
        printFeedDot((byte) 10);
        printNormal("印刷日 " + OtherUtil.format("yyyy年 MM月 dd日 HH:mm:ss", new java.util.Date()));

        // 担当
        printNormal("担当 " + tantname);

        Set<String> setKeys = mapUriageData.keySet();
        printRectangle(0, RECT_WIDTH, mHeight, mHeight, STYLE.BOLD);
        printFeedDot((byte)5);
        for (String strKey : setKeys) {
            // 日付
            printNormal("売上日 " + strKey);
            List<UriageItemDat> lstUriageItemDat = mapUriageData.get(strKey);
            String previousCusCode = "";
            int nTax = 0;
            for (UriageItemDat uriageItemDat : Objects.requireNonNull(lstUriageItemDat)) {
                int nStartHeight = printFeedDot((byte)10)[0];
                if (!previousCusCode.equals(uriageItemDat.getCusCode().trim())) {
                    if(nTax != 0){
                        // 日付
                        printHPInfo(MEISAI_POSITION_TANKA, " ", 0);
                        // 品目名
                        printHPInfo(MEISAI_POSITION_TANKA, "消費税", 1);
                        // 数量
                        printHPInfo(MEISAI_POSITION_TANKA, " ", 2);
                        // 単価
                        printHPInfo(MEISAI_POSITION_TANKA, " ", 3);
                        // 金額
                        printHPInfo(MEISAI_POSITION_TANKA, " " + OtherUtil.printformat("#,###,##0", nTax/1000), 4);
                    }

                    nTax = 0;
                    printCusInfo(uriageItemDat.getCusCode().trim(), 0);
                    printCusInfo(uriageItemDat.getName().trim(), 1);
                    previousCusCode = uriageItemDat.getCusCode().trim();
                }
                // 日付
                printFeedDot((byte)5);
                printHPInfo(MEISAI_POSITION_TANKA, " ", 0);
                // 品目
                String strHmname = OtherUtil.getClearString(uriageItemDat.getHmname());
                if(strHmname.getBytes(Charset.forName("Shift_JIS")).length > 10){
                    strHmname = OtherUtil.cutString(strHmname, 10);
                }
                printHPInfo(MEISAI_POSITION_TANKA, OtherUtil.getClearString(strHmname), 1);
                // 数量
                if (uriageItemDat.getSuryo() != 0) {
                    printHPInfo(MEISAI_POSITION_TANKA, OtherUtil.printformat(uriageItemDat.getSuryo(), "###0.00", (byte) 2), 2);
                }
                else {
                    printHPInfo(MEISAI_POSITION_TANKA, " ", 2);
                }

                if(uriageItemDat.getTank() != 0){
                    // 単価印字有り
                    if(uriageItemDat.getTank() % 100 == 0){
                        printHPInfo(MEISAI_POSITION_TANKA, OtherUtil.printformat(uriageItemDat.getTank(), "#,##0", (byte) 2), 3);
                    }
                    else if(uriageItemDat.getTank() % 10 == 0){
                        printHPInfo(MEISAI_POSITION_TANKA, OtherUtil.printformat(uriageItemDat.getTank(), "#,##0.0", (byte) 2), 3);
                    }
                    else {
                        printHPInfo(MEISAI_POSITION_TANKA, OtherUtil.printformat(uriageItemDat.getTank(), "#,##0.00", (byte) 2), 3);
                    }
                }
                else {
                    printHPInfo(MEISAI_POSITION_TANKA, " ", 3);
                }
                // 金額
                printHPInfo(MEISAI_POSITION_TANKA, " " + OtherUtil.printformat("#,###,##0", uriageItemDat.getKin()), 4);

                nTax += uriageItemDat.getTax();



                // 消費税
                if(nTax != 0){
                    // 日付
                    printHPInfo(MEISAI_POSITION_TANKA, " ", 0);
                    // 品目名
                    printHPInfo(MEISAI_POSITION_TANKA, "消費税", 1);
                    // 数量
                    printHPInfo(MEISAI_POSITION_TANKA, " ", 2);
                    // 単価
                    printHPInfo(MEISAI_POSITION_TANKA, " ", 3);
                    // 金額
                    printHPInfo(MEISAI_POSITION_TANKA, " " + OtherUtil.printformat("#,###,##0", nTax/1000), 4);
                }
                int nEndHeight = printFeedDot((byte)5)[0];
                printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.NORMAL);
                checkPageModeLength();
            }
            int nYpos = printFeedDot((byte) 5)[0];
            printRectangle(0, RECT_WIDTH, nYpos, nYpos, STYLE.BOLD);
            printFeedDot((byte) 5);
        }
    }

    @Override
    public void printExecute() throws MException {
        printExecute(PRINT_MODE_IMAGE);
    }
}
