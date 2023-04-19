package jp.co.MarutouCompack.commonClass;

import android.content.Context;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.baseClass.PrintBase;
import jp.co.MarutouCompack.baseClass.PrintBaseImage;
import jp.co.MarutouCompack.baseClass.PrintImageList;
import jp.co.MarutouCompack.baseClass.PrintKensinListInterface;
import jp.co.MarutouCompack.commonClass.PrinterDat.ShukeiKensinData;
import jp.co.MarutouCompack.commonClass.PrinterDat.UserData;
import jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * @author 株式会社マルトウコンパック
 */
public class PrintKensinListImage extends PrintBase implements PrintKensinListInterface {

    private static final String TAG = PrintKensinListImage.class.getSimpleName();

    /**
     * コンストラクタ<br />
     * ポート名及び設定情報を設定
     *
     * @param context   [in] {@link Context}    呼び出し元コンテキスト
     */
    public PrintKensinListImage(Context context) throws MException{
        super(context);
        connect();
    }

    @Override
    protected void printExecute(int nPrintMode) throws MException {
        sendImageData();
        disconnect();
    }


    @Override
    public void createPrintData(Map<String, List<ShukeiKensinData>> mapKensinData,
                                boolean isPrintToyu,
                                String tantname) {
        try {
            PrintImageList printImageList = new PrintImageList(mContext);
            int nYpos = printImageList.add("検　針　日　報", 0, 10, PrintBaseImage.TextSize.LARGE, PrintBaseImage.Align.CENTER, PrintBaseImage.STROKE_WIDTH_BOLD)[1];

            nYpos = printImageList.add("印刷日 " + OtherUtil.format("yyyy年 MM月 dd日 HH:mm:ss", new java.util.Date()), 10, nYpos, PrintBaseImage.TextSize.XSMALL)[1];

            // 担当
            nYpos = printImageList.add("担当 " + tantname, 25, nYpos, PrintBaseImage.TextSize.XSMALL)[1];

            Set<String> setKeys = mapKensinData.keySet();
            printImageList.addRectBold(nYpos, nYpos);
            addPrintData(printImageList.getBitmap());
            printImageList = new PrintImageList(mContext);
            int nCnt = 0;
            int nToyuCnt = 0;
            ShukeiKensinData footerKensinData = new ShukeiKensinData();
            for (String strKey : setKeys) {
                // 日付
                nYpos = printImageList.RECT_PADDING * 2;
                printImageList.add("検針日 " + strKey, 10, nYpos, PrintBaseImage.TextSize.XSMALL);
                List<ShukeiKensinData> lstKensinData = mapKensinData.get(strKey);
                addPrintData(printImageList.getBitmap());
                nYpos = 0;
                printImageList = new PrintImageList(mContext);
                for (ShukeiKensinData kensinData : Objects.requireNonNull(lstKensinData)) {
                    printImageList.addRect(nYpos++, nYpos);
                    nYpos += printImageList.RECT_PADDING;
                    printImageList.add(kensinData.getKcode().trim(), 25, nYpos, PrintBaseImage.TextSize.XSMALL);
                    nYpos = printImageList.add(kensinData.getName().trim(), 270, nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                    if(kensinData.isKensin()) {
                        nCnt++;
                        printImageList.add("指針", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
                        printImageList.add(OtherUtil.printformat(kensinData.getSs(), "######0.0", (byte) 1) + "m3", 150, nYpos, PrintBaseImage.TextSize.XSMALL);
                        printImageList.add("使用量", 300, nYpos, PrintBaseImage.TextSize.XSMALL);
                        nYpos = printImageList.add(OtherUtil.printformat(kensinData.getSr(), "######0.0", (byte) 1) + "m3", 425, nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                        printImageList.add("ガス料金", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
                        printImageList.add(OtherUtil.printformat("#,###,##0", kensinData.getKin()) + "円", 150, nYpos, PrintBaseImage.TextSize.XSMALL);
                        printImageList.add("消費税額", 300, nYpos, PrintBaseImage.TextSize.XSMALL);
                        nYpos = printImageList.add(OtherUtil.printformat("#,###,##0", kensinData.getTax()) + "円", 425, nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                    }
                    if (kensinData.getKng() != 0) {
                        printImageList.add("還元額", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
                        nYpos = printImageList.add(OtherUtil.printformat("#,###,##0", kensinData.getKng()) + "円", 150, nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                    }
                    if(isPrintToyu && kensinData.isToyu()){
                        nToyuCnt++;
                        nYpos = printImageList.add("灯油", 25, nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                        printImageList.add("指針", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
                        printImageList.add(OtherUtil.printformat(kensinData.getToyuSs(), "######0.0", (byte) 1) + "m3", 150, nYpos, PrintBaseImage.TextSize.XSMALL);
                        printImageList.add("使用量", 300, nYpos, PrintBaseImage.TextSize.XSMALL);
                        nYpos = printImageList.add(OtherUtil.printformat(kensinData.getToyuSr(), "######0.0", (byte) 1) + "m3", 425, nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                        printImageList.add("ガス料金", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
                        printImageList.add(OtherUtil.printformat("#,###,##0", kensinData.getToyuKin()) + "円", 150, nYpos, PrintBaseImage.TextSize.XSMALL);
                        printImageList.add("消費税額", 300, nYpos, PrintBaseImage.TextSize.XSMALL);
                        printImageList.add(OtherUtil.printformat("#,###,##0", kensinData.getToyuTax()) + "円", 425, nYpos, PrintBaseImage.TextSize.XSMALL);
                    }
                    footerKensinData.addData(kensinData);
                    addPrintData(printImageList.getBitmap());
                    nYpos = 0;
                    checkPageModeLength();
                    printImageList = new PrintImageList(mContext);
                }
                printImageList.addRectBold(nYpos, nYpos);
            }
            nYpos += printImageList.RECT_PADDING * 2;
            printImageList.add("検針件数", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
            nYpos = printImageList.add(OtherUtil.format("#,##0", nCnt) + "件", 0, nYpos, PrintBaseImage.TextSize.XSMALL, PrintBaseImage.Align.RIGHT)[1];
            printImageList.add("ガス使用量", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
            nYpos = printImageList.add(OtherUtil.format("######0.0", footerKensinData.getSr(), (byte) 1) + "m3", 0, nYpos, PrintBaseImage.TextSize.XSMALL, PrintBaseImage.Align.RIGHT)[1];
            printImageList.add("ガス料金", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
            nYpos = printImageList.add(OtherUtil.format("#,###,##0", footerKensinData.getKin()) + "円", 0, nYpos, PrintBaseImage.TextSize.XSMALL, PrintBaseImage.Align.RIGHT)[1];
            printImageList.add("消費税額", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
            nYpos = printImageList.add(OtherUtil.format("#,###,##0", footerKensinData.getTax()) + "円", 0, nYpos, PrintBaseImage.TextSize.XSMALL, PrintBaseImage.Align.RIGHT)[1];
            printImageList.add("還元額", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
            nYpos = printImageList.add(OtherUtil.format("#,###,##0", footerKensinData.getKng()) + "円", 0, nYpos, PrintBaseImage.TextSize.XSMALL, PrintBaseImage.Align.RIGHT)[1];
            if(isPrintToyu && nToyuCnt != 0){
                printImageList.add("灯油件数", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
                nYpos = printImageList.add(OtherUtil.format("#,##0", nToyuCnt) + "件", 0, nYpos, PrintBaseImage.TextSize.XSMALL, PrintBaseImage.Align.RIGHT)[1];
                printImageList.add("灯油使用量", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
                nYpos = printImageList.add(OtherUtil.format("######0.0", footerKensinData.getToyuSr(), (byte) 1) + "m3", 0, nYpos, PrintBaseImage.TextSize.XSMALL, PrintBaseImage.Align.RIGHT)[1];
                printImageList.add("灯油料金", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
                nYpos = printImageList.add(OtherUtil.format("#,###,##0", footerKensinData.getToyuKin()) + "円", 0, nYpos, PrintBaseImage.TextSize.XSMALL, PrintBaseImage.Align.RIGHT)[1];
                printImageList.add("消費税額", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
                printImageList.add(OtherUtil.format("#,###,##0", footerKensinData.getToyuTax()) + "円", 0, nYpos, PrintBaseImage.TextSize.XSMALL, PrintBaseImage.Align.RIGHT);
            }
            addPrintData(printImageList.getBitmap());
        }
        catch (MException mex){
            MLog.ERROR(mContext, TAG, mex);
        }
    }

    @Override
    public void printExecute() throws MException {
        super.printExecute(PRINT_MODE_IMAGE);
    }

    @Override
    protected void createHmInfoHeader(String strTitle, PrintImageList printImageList, boolean isTanka) {
    }

    @Override
    protected int createHmInfo(HmefDat[] lstHmefDat, PrintImageList printImageList, jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat sysfDat, Map<Integer, HmefDat> mapHmefDat, boolean isTanka) {
        return 0;
    }

    @Override
    protected void createHmInfoTax(PrintImageList printImageList,
                                   Map<Integer, HmefDat> mapHmefDat,
                                   UserData userData,
                                   int nTax,
                                   boolean isTanka) {

    }


    @Override
    protected void createHmInfoFooter(PrintImageList printImageList, long lKin) {
    }
}
