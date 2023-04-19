package jp.co.MarutouCompack.commonClass;

import android.content.Context;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.baseClass.PrintBaseImage;
import jp.co.MarutouCompack.baseClass.PrintBaseImage2;
import jp.co.MarutouCompack.baseClass.PrintImageList;
import jp.co.MarutouCompack.baseClass.PrintUriageListInterface;
import jp.co.MarutouCompack.commonClass.PrinterDat.ShukeiKensinData;
import jp.co.MarutouCompack.commonClass.PrinterDat.ShukeiUriageData;
import jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UriageItemDat;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * 売上日報(文字列)印刷データ生成クラス.
 */
public class PrintUriageListImage extends PrintBaseImage2 implements PrintUriageListInterface {

    /** クラス識別用タグ */
    private static final String TAG = PrintUriageListImage.class.getSimpleName();

    /**
     * コンストラクタ<br />
     * ポート名及び設定情報を設定
     *
     * @param context   [in] {@link Context}    呼び出し元コンテキスト
     */
    public PrintUriageListImage(Context context) throws MException{
        super(context);
        MLog.INFO(context, TAG, "売上日報(画像)印刷処理[開始]");
        connect();
    }

    @Override
    protected void printExecute(int wkPrintMode) throws MException {
        sendImageData();
        disconnect();
        MLog.INFO(mContext, TAG, "売上日報(画像)印刷処理[終了]");
    }

    @Override
    public void createPrintData(Map<String, List<UriageItemDat>> mapUriageData, String tantname)  {
        try {
            PrintImageList printImageList = new PrintImageList(mContext);
            int nYpos = printImageList.add("売　上　日　報", 0, 10, PrintBaseImage.TextSize.LARGE, PrintBaseImage.Align.CENTER, PrintBaseImage.STROKE_WIDTH_BOLD)[1];

            nYpos = printImageList.add("印刷日 " + OtherUtil.format("yyyy年 MM月 dd日 HH:mm:ss", new java.util.Date()), 10, nYpos, PrintBaseImage.TextSize.XSMALL)[1];

            // 担当
            nYpos = printImageList.add("担当 " + tantname, 25, nYpos, PrintBaseImage.TextSize.XSMALL)[1];

            Set<String> setKeys = mapUriageData.keySet();
            printImageList.addRectBold(nYpos, nYpos);
            addPrintData(printImageList.getBitmap());
            printImageList = new PrintImageList(mContext);
            for (String strKey : setKeys) {
                // 日付
                nYpos = printImageList.RECT_PADDING * 2;
                nYpos = printImageList.add("売上日 " + strKey, 10, nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                List<UriageItemDat> lstUriageItemDat = mapUriageData.get(strKey);
                String previousCusCode = "";
                String strPrint;
                int nTax = 0;
                for (UriageItemDat uriageItemDat : Objects.requireNonNull(lstUriageItemDat)) {
                    if (!previousCusCode.equals(uriageItemDat.getCusCode().trim())) {
                        if(nTax != 0){
                            // 消費税有り
                            printImageList.add("消費税", 40, nYpos, PrintBaseImage.TextSize.XSMALL);
                            printImageList.add(OtherUtil.printformat("#,###,##0", nTax/1000), START_POSITION_HAN_TANKA[4], nYpos, PrintBaseImage.TextSize.XSMALL);
                        }
                        addPrintData(printImageList.getBitmap());
                        printImageList = new PrintImageList(mContext);
                        nYpos = 0;
                        checkPageModeLength();

                        nTax = 0;
                        printImageList.addRect(nYpos++, nYpos);
                        nYpos += printImageList.RECT_PADDING;
                        printImageList.add(uriageItemDat.getCusCode().trim(), 20, nYpos, PrintBaseImage.TextSize.XSMALL);
                        nYpos = printImageList.add(uriageItemDat.getName().trim(), 250, nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                        previousCusCode = uriageItemDat.getCusCode().trim();
                    }

                    // 品目
                    strPrint = OtherUtil.getClearString(uriageItemDat.getHmname());
                    if (strPrint.getBytes(Charset.forName("Shift_JIS")).length > 20) {
                        printImageList.add(strPrint, 40, nYpos + 5, PrintBaseImage.TextSize.HMSMALL);
                    } else {
                        printImageList.add(strPrint, 40, nYpos, PrintBaseImage.TextSize.XSMALL);
                    }
                    // 数量
                    if (uriageItemDat.getSuryo() != 0) {
                        strPrint = OtherUtil.printformat(uriageItemDat.getSuryo(), "###0.00", (byte) 2);
                    } else {
                        strPrint = " ";
                    }
                    printImageList.add(strPrint, START_POSITION_HAN_TANKA[2], nYpos, PrintBaseImage.TextSize.XSMALL);
                    if (uriageItemDat.getTank() != 0) {
                        // 単価印字有り
                        if (uriageItemDat.getTank() % 100 == 0) {
                            strPrint = OtherUtil.printformat(uriageItemDat.getTank(), "###,##0", (byte) 2);
                        } else if (uriageItemDat.getTank() % 10 == 0) {
                            strPrint = OtherUtil.printformat(uriageItemDat.getTank(), "##,##0.0", (byte) 2);
                        } else {
                            strPrint = OtherUtil.printformat(uriageItemDat.getTank(), "#,##0.00", (byte) 2);
                        }
                        printImageList.add(strPrint, START_POSITION_HAN_TANKA[3], nYpos, PrintBaseImage.TextSize.XSMALL);
                    }
                    // 金額
                    nTax += uriageItemDat.getTax();
                    printImageList.add(OtherUtil.printformat("#,###,##0", uriageItemDat.getKin()), START_POSITION_HAN_TANKA[4], nYpos, PrintBaseImage.TextSize.XSMALL);
                    addPrintData(printImageList.getBitmap());
                    printImageList = new PrintImageList(mContext);
                    nYpos = 0;
                    checkPageModeLength();
                }
                if(nTax != 0){
                    // 消費税有り
                    printImageList.add("消費税", 40, nYpos, PrintBaseImage.TextSize.XSMALL);
                    printImageList.add(OtherUtil.printformat("#,###,##0", nTax/1000), START_POSITION_HAN_TANKA[4], nYpos, PrintBaseImage.TextSize.XSMALL);
                }
                addPrintData(printImageList.getBitmap());
                printImageList = new PrintImageList(mContext);
                nYpos = 0;
                checkPageModeLength();
            }
            nYpos++;
            printImageList.addRectBold(nYpos, nYpos);
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
}
