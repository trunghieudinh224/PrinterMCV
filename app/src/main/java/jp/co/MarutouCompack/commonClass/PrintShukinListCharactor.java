package jp.co.MarutouCompack.commonClass;

import android.content.Context;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.baseClass.PrintBaseCharactor;
import jp.co.MarutouCompack.baseClass.PrintShukinListInterface;
import jp.co.MarutouCompack.commonClass.PrinterDat.ShukeiKensinData;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * 集金日報印刷データ生成クラス(文字列)
 */
public class PrintShukinListCharactor extends PrintBaseCharactor implements PrintShukinListInterface {

    /** ログ出力用タグ */
    private static final String TAG = PrintShukinListCharactor.class.getSimpleName();

    /**
     * コンストラクタ<br />
     * ポート名及び設定情報を設定
     *
     * @param context   [in] {@link Context}    呼び出し元コンテキスト
     */
    public PrintShukinListCharactor(Context context) throws MException {
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
        byte[] t_position = new byte[] {
                2, 12, 25, 35
        };
        printHPInfo(t_position, strLine, nIdx);
    }

    /**
     * 顧客情報印字.
     *
     * @param strMsg    [in] String 印刷文字列
     * @param nNo       [in] int    印字位置
     */
    private void printCusInfo(String strMsg, int nNo){
        byte[] bPositions = new byte[]{
                2, 15
        };
        printHPInfo(bPositions, strMsg, nNo);
    }

    /**
     * フッター印字.
     *
     * @param strMsg    [in] String 印刷文字列
     * @param nNo       [in] int    印字位置
     */
    private void printFotterInfo(String strMsg, int nNo){
        byte[] bPositions = new byte[]{
                2, 35
        };
        printHPInfo(bPositions, strMsg, nNo);
    }

    @Override
    public void createPrintData(Map<String, List<ShukeiKensinData>> mapKensinData, String tantname)  {
        print("集　金　日　報", CHARSIZE.LARGE, LAYOUT.CENTER);
        printFeedDot((byte) 10);
        printNormal("印刷日 " + OtherUtil.format("yyyy年 MM月 dd日 HH:mm:ss", new java.util.Date()));

        // 担当
        printNormal("担当 " + tantname);

        Set<String> setKeys = mapKensinData.keySet();
        printRectangle(0, RECT_WIDTH, mHeight, mHeight, STYLE.BOLD);
        printFeedDot((byte)5);
        int nCnt = 0;

        ShukeiKensinData footerKensinData = new ShukeiKensinData();
        for (String strKey : setKeys) {
            // 日付
            boolean isFirst = true;
            List<ShukeiKensinData> lstKensinData = mapKensinData.get(strKey);
            for (ShukeiKensinData kensinData : Objects.requireNonNull(lstKensinData)) {
                if(kensinData.getNyu() == 0 && kensinData.getCho() == 0){
                    continue;
                }
                if(isFirst){
                    isFirst = false;
                    printNormal("集金日 " + strKey);
                    printRectangle(0, RECT_WIDTH, mHeight, mHeight, STYLE.NORMAL);
                }
                nCnt++;
                int nStartHeight = printFeedDot((byte)10)[0];
                printCusInfo(kensinData.getKcode(), 0);
                printCusInfo(kensinData.getName(), 1);
                printInfo("入金額", 0);
                printInfo(OtherUtil.printformat("#,###,##0", kensinData.getNyu()) + "円", 1);
                printInfo("調整額", 2);
                printInfo(OtherUtil.printformat("#,###,##0", kensinData.getCho()) + "円", 3);
                int nEndHeight = printFeedDot((byte)5)[0];
                printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.NORMAL);
                checkPageModeLength();
                footerKensinData.addData(kensinData);
            }
            if(!isFirst) {
                int nYpos = printFeedDot((byte) 5)[0];
                printRectangle(0, RECT_WIDTH, nYpos, nYpos, STYLE.BOLD);
                printFeedDot((byte) 5);
            }
        }
        printFotterInfo("集金件数", 0);
        printFotterInfo(OtherUtil.printformat("#,###,##0", nCnt) + "件", 1);
        printFotterInfo("入金額", 0);
        printFotterInfo(OtherUtil.printformat("#,###,##0", footerKensinData.getNyu()) + "円", 1);
        printFotterInfo("調整額", 0);
        printFotterInfo(OtherUtil.printformat("#,###,##0", footerKensinData.getCho()) + "円", 1);
    }

    @Override
    public void printExecute() throws MException {
        printExecute(PRINT_MODE_IMAGE);
    }
}
