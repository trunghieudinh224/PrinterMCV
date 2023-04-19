package jp.co.MarutouCompack.commonClass;

import android.content.Context;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.baseClass.PrintBaseCharactor;
import jp.co.MarutouCompack.baseClass.PrintKensinListInterface;
import jp.co.MarutouCompack.commonClass.PrinterDat.ShukeiKensinData;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * @author 株式会社マルトウコンパック
 */
public class PrintKensinListCharactor extends PrintBaseCharactor implements PrintKensinListInterface {

    private static final String TAG = PrintKensinListCharactor.class.getSimpleName();

    /**
     * コンストラクタ<br />
     * ポート名及び設定情報を設定
     *
     * @param context   [in] {@link Context}    呼び出し元コンテキスト
     */
    public PrintKensinListCharactor(Context context) throws MException {
        super(context);
        // この段階で接続する
        connect();
    }

    @Override
    protected void printExecute(int nPrintMode) throws MException {
        // 最後のデータを送信
        sendData();
        // 切断
        disconnect();
    }

    /**
     * 請求書情報の印字。
     *
     * @param strLine [in] String 印字文字列
     * @param nIdx  [in] int    印字位置
     */
    private void printInfo(String strLine, int nIdx) {
        byte[] bPositions = new byte[] {
                2, 12, 25, 35
        };
        printHPInfo(bPositions, strLine, nIdx);
    }

    private void printCusInfo(String strMsg, int nNo){
        byte[] bPositions = new byte[]{
                2, 15
        };
        printHPInfo(bPositions, strMsg, nNo);
    }

    private void printFotterInfo(String strMsg, int nNo){
        byte[] bPositions = new byte[]{
                2, 35
        };
        printHPInfo(bPositions, strMsg, nNo);
    }

    @Override
    public void createPrintData(Map<String, List<ShukeiKensinData>> mapKensinData, boolean isPrintToyu, String tantname) {
        try {
            print("検　針　日　報", CHARSIZE.LARGE, LAYOUT.CENTER);
            printFeedDot((byte) 10);
            printNormal("印刷日 " + OtherUtil.format("yyyy年 MM月 dd日 HH:mm:ss", new java.util.Date()));

            // 担当
            printNormal("担当 " + tantname);

            Set<String> setKeys = mapKensinData.keySet();
            printRectangle(0, RECT_WIDTH, mHeight, mHeight, STYLE.BOLD);
            printFeedDot((byte)5);
            int nCnt = 0;
            int nToyuCnt = 0;

            ShukeiKensinData footerKensinData = new ShukeiKensinData();
            for (String strKey : setKeys) {
                // 日付
                printNormal("検針日 " + strKey);
                printRectangle(0, RECT_WIDTH, mHeight, mHeight, STYLE.NORMAL);
                List<ShukeiKensinData> lstKensinData = mapKensinData.get(strKey);
                for (ShukeiKensinData kensinData : Objects.requireNonNull(lstKensinData)) {
                    int nStartHeight = printFeedDot((byte)10)[0];
                    printCusInfo(kensinData.getKcode(), 0);
                    printCusInfo(kensinData.getName(), 1);
                    if(kensinData.isKensin()) {
                        nCnt++;
                        printInfo("指針", 0);
                        printInfo(OtherUtil.printformat(kensinData.getSs(), "######0.0", (byte) 1) + "  ", 1);
                        printInfo("使用量", 2);
                        printInfo(OtherUtil.printformat(kensinData.getSr(), "######0.0", (byte) 1) + "m3", 3);
                        printInfo("ガス料金", 0);
                        printInfo(OtherUtil.printformat("#,###,##0", kensinData.getKin()) + "円", 1);
                        printInfo("消費税額", 2);
                        printInfo(OtherUtil.printformat("#,###,##0", kensinData.getTax()) + "円", 3);
                        if (kensinData.getKng() != 0) {
                            printInfo("還元額", 0);
                            printInfo(OtherUtil.printformat("#,###,##0", kensinData.getKng()) + "円", 1);
                            printLF(1);
                        }
                    }
                    if(isPrintToyu && kensinData.isToyu()){
                        nToyuCnt++;
                        printInfo("灯油", 0);
                        printLF(1);
                        printInfo("指針", 0);
                        printInfo(OtherUtil.printformat(kensinData.getToyuSs(), "######0.0", (byte)1) + "  ", 1);
                        printInfo("使用量", 2);
                        printInfo(OtherUtil.printformat(kensinData.getToyuSr(), "######0.0", (byte)1) + "Ｌ", 3);
                        printInfo("灯油料金", 0);
                        printInfo(OtherUtil.printformat("#,###,##0", kensinData.getToyuKin()) + "円", 1);
                        printInfo("消費税額", 2);
                        printInfo(OtherUtil.printformat("#,###,##0", kensinData.getToyuTax()) + "円", 3);
                    }
                    int nEndHeight = printFeedDot((byte)5)[0];
                    printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.NORMAL);
                    checkPageModeLength();
                    footerKensinData.addData(kensinData);
                }
                int nYpos = printFeedDot((byte)5)[0];
                printRectangle(0, RECT_WIDTH, nYpos, nYpos, STYLE.BOLD);
                printFeedDot((byte)5);
            }
            printFotterInfo("検針件数", 0);
            printFotterInfo(OtherUtil.printformat("#,###,##0", nCnt) + "件", 1);
            printFotterInfo("ガス使用量", 0);
            printFotterInfo(OtherUtil.printformat(footerKensinData.getSr(), "######0.0", (byte) 1) + "m3", 1);
            printFotterInfo("ガス料金", 0);
            printFotterInfo(OtherUtil.printformat("#,###,##0", footerKensinData.getKin()) + "円", 1);
            printFotterInfo("消費税額", 0);
            printFotterInfo(OtherUtil.printformat("#,###,##0", footerKensinData.getTax()) + "円", 1);
            printFotterInfo("還元額", 0);
            printFotterInfo(OtherUtil.printformat("#,###,##0", footerKensinData.getKng()) + "円", 1);
            if(isPrintToyu && nToyuCnt != 0) {
                printFotterInfo("灯油件数", 0);
                printFotterInfo(OtherUtil.printformat("#,###,##0", nToyuCnt) + "件", 1);
                printFotterInfo("灯油使用量", 0);
                printFotterInfo(OtherUtil.printformat(footerKensinData.getToyuSr(), "######0.0", (byte) 1) + "m3", 1);
                printFotterInfo("灯油料金", 0);
                printFotterInfo(OtherUtil.printformat("#,###,##0", footerKensinData.getToyuKin()) + "円", 1);
                printFotterInfo("消費税額", 0);
                printFotterInfo(OtherUtil.printformat("#,###,##0", footerKensinData.getToyuTax()) + "円", 1);
            }
        }
        catch (Exception mex){
            MLog.ERROR(mContext, TAG, mex);
        }
    }


    @Override
    public void printExecute() throws MException {
        printExecute(PRINT_MODE_IMAGE);
    }
}
