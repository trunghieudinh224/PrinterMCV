package jp.co.MarutouCompack.commonClass;

import android.content.Context;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.baseClass.PrintBaseCharactor;
import jp.co.MarutouCompack.baseClass.PrintImageList;
import jp.co.MarutouCompack.baseClass.PrintUriageInterface;
import jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KokfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysOption;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UserData;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * 納品書印刷データ生成クラス(文字列)
 */
public class PrintUriageCharactor extends PrintBaseCharactor implements PrintUriageInterface {
    /** クラス識別用タグ */
    private static final String TAG = PrintUriageCharactor.class.getSimpleName();

    /**
     * コンストラクタ.
     *
     * @param ctx       [in] {@link Context}    呼び出し元コンテキス
     * @throws MException   エラーがあった場合に発生
     */
    public PrintUriageCharactor(Context ctx) throws MException{
        super(ctx);
        // この段階で接続しておく
        MLog.INFO(ctx, TAG, "納品書(文字列)印刷処理[開始]");
        connect();
    }

    @Override
    public void printExecute() throws MException {
        // 最終データの出力
        sendData();
        // 切断
        disconnect();
        MLog.INFO(mContext, TAG, "納品書(文字列)印刷処理[終了]");
    }

    @Override
    public void createHeader(boolean isHikae, boolean isGenuri, String date) {
        String strTitle = "納　品　書";
        if(isGenuri){
            strTitle += " 兼 領 収 書";
        }
        if(isHikae){
            strTitle += "（控）";
        }
        print( strTitle, CHARSIZE.LARGE, LAYOUT.CENTER );
        printFeedDot((byte)5);

        printNormal(date);
    }

    @Override
    public void createCusInfo(UserData userData, String strSname_0, String strSname_1) throws MException {
        int nStartHeigh;
        int nEndHeight;
        nStartHeigh = printFeedDot((byte)5)[0];

        KokfDat kokfDat = userData.getKokfDat();
        printNormal("コード：　" + kokfDat.getCusCode());
        if (OtherUtil.getClearString(strSname_0).length() > 0 && OtherUtil.getClearString(strSname_1).length() > 0) {
            printNormal(addSpaceToString(strSname_0));
            printNormal(addSpaceToString(strSname_1) + kokfDat.getKName());
        }
        else if (OtherUtil.getClearString(strSname_0).length() > 0) {
            printNormal(addSpaceToString(strSname_0) + kokfDat.getKName());
        }
        else {
            printNormal(addSpaceToString(strSname_1) + kokfDat.getKName());
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
    public void createMeisaiInfo(UserData userData, List<HmefDat> lstHmefDat) throws MException {
//        createMeisaiInfoLocal(lstHmefDat);
        SysfDat sysfDat = userData.getSysfDat();
        boolean isTanka = userData.getSy2fDat().getSysOption()[SysOption.PRINT_TANKA.getIdx()] == 1;
        // 販売データ
        boolean isUriage = isUriage(new HmefDat[0], new HmefDat[0], lstHmefDat.toArray(new HmefDat[0]), sysfDat);
        if (isUriage) { // 販売実績がある場合のみ印刷する
            Map<Integer, HmefDat> mapHmefDat = new LinkedHashMap<>();
            calcKeigen(mapHmefDat, lstHmefDat.toArray(new HmefDat[0]), userData);
            createHmInfoHeader("", null, isTanka);

            int nTax = 0;
            long lKin = 0L;
            for(HmefDat hmefDat : lstHmefDat){
                if(hmefDat.isUsef() && hmefDat.getHmCode() >= sysfDat.getSnvalue()){
                    lKin += hmefDat.getKin();
                    nTax += hmefDat.getTax();
                }
            }
            if (!lstHmefDat.isEmpty()) {
                createHmInfo(lstHmefDat.toArray(new HmefDat[0]), null, sysfDat, mapHmefDat, isTanka);
            }
            // 消費税
            createHmInfoTax(null, mapHmefDat, userData, nTax, isTanka);
            checkPageModeLength();

            printLF(1);

            createHmInfoFooter(null, lKin + nTax);
        }

        checkPageModeLength();
    }

    @Override
    protected void createHmInfoFooter(PrintImageList printImageList, long lKin) {
        int nStartHeight = printFeedDot((byte)5)[0];
        int nEndHeight;

        printSeikyuInfo("本日売上金額", 0);
        nEndHeight = printSeikyuInfo(OtherUtil.printformat("#,###,##0", lKin) + "円", 1)[1];
        printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.BOLD);
        printLF(1);

        printNormal("上記の通り納品致しました。有難うございました。");
        printLF(1);
        checkPageModeLength();
    }


    @Override
    public void createRyoshu(UserData userData, int nChokin, int nNyukin, int nRecept, long lSeikyu) throws MException{
        int nStartHeight = printFeedDot((byte)5)[0];
        int nEndHeight;
        // 今回請求額
        printSeikyuInfoLarge("今回請求額", 0);
        nEndHeight = printSeikyuInfoLarge(OtherUtil.printformat("#,###,##0", lSeikyu) + "円", 1)[1];
        adjustHeight_13();

        // -----------------------------------------------
        // 当日入金・調整額
        if (nChokin != 0) {
            printSeikyuInfo(getChoTitle(userData), 0);
            nEndHeight = printSeikyuInfo(OtherUtil.printformat("#,###,##0", nChokin) + "円", 1)[1];
        }
        String t_str;
        if (nNyukin != 0) {
            if (nRecept == nNyukin) { // 13.02.12
                t_str = "本日入金額";
            }
            else {
                t_str = "本日お預かり金額";
            }
            printSeikyuInfo(t_str, 0);
            nEndHeight = printSeikyuInfo(OtherUtil.printformat("#,###,##0", nRecept) + "円", 1)[1];
        }
        long lOtsuri = nRecept - (lSeikyu + nChokin); // 13.02.12
        if (lOtsuri > 0) {
            printSeikyuInfo("おつり", 0);
            nEndHeight = printSeikyuInfo(OtherUtil.printformat("#,###,##0", lOtsuri) + "円", 1)[1];
        }

        if (nStartHeight != nEndHeight) {
            nEndHeight = printFeedDot((byte)10)[1];
            printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.BOLD);
        }
        // -----------------------------------------------
        // 差引残高
        long lZandaka = lSeikyu + nChokin - nNyukin;
        if (lZandaka != 0 ) {
            nStartHeight = printFeedDot((byte)5)[0];
            printSeikyuInfoLarge("差引残高", 0);
            printSeikyuInfoLarge(OtherUtil.printformat("##,###,##0", lZandaka) + "円", 1);
            adjustHeight_13(); // 全角改行の高さ調整  // 13.03.18
            nEndHeight = printFeedDot((byte) 10)[1];
            printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.BOLD);
        }
        checkPageModeLength();

        printLF(1);

        final int STARTX = 400;
        final int RECTWIDTH = 165;

        int nStart = printFeedDot((byte)5 )[0];
        int nEnd = printNormal("　　 領 収 印", (byte) 1, (byte) 120, CHARSIZE.SMALL)[1]; // 位置合わせが難しいため最終的に空白で調整
        printRectangle(STARTX, RECTWIDTH, nStart, nEnd, STYLE.BOLD);

        printNormal("領収金額", (byte) 0, (byte) 100, CHARSIZE.LARGE);
        String t_strnyukin = OtherUtil.KingakuFormat(nNyukin) + "円";
        printNormal(t_strnyukin, (byte) 0, (byte) 100, CHARSIZE.LARGE);
        nEnd = printLF(2)[1];
        printRectangle(STARTX, RECTWIDTH, nStart, nEnd, STYLE.BOLD);
        printNormal("上記の通り領収致しました。有難うございました。");
        adjustHeight_13(); // 全角改行の高さ調整
        printLF(1);
        checkPageModeLength();
    }
}
