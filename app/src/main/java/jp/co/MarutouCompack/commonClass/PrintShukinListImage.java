package jp.co.MarutouCompack.commonClass;

import android.content.Context;

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
import jp.co.MarutouCompack.baseClass.PrintShukinListInterface;
import jp.co.MarutouCompack.commonClass.PrinterDat.ShukeiKensinData;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * 集金日報印刷データ生成クラス(画像)
 */
public class PrintShukinListImage extends PrintBaseImage2 implements PrintShukinListInterface {

    /** ログ出力用タグ */
    private static final String TAG = PrintShukinListImage.class.getSimpleName();

    /**
     * コンストラクタ<br />
     * ポート名及び設定情報を設定
     *
     * @param context   [in] {@link Context}    呼び出し元コンテキスト
     */
    public PrintShukinListImage(Context context) throws MException{
        super(context);
        connect();
    }

    @Override
    protected void printExecute(int nPrintMode) throws MException {
        sendImageData();
        disconnect();
    }

    @Override
    public void createPrintData(Map<String, List<ShukeiKensinData>> mapKensinData, String tantname)  {
        try {
            PrintImageList printImageList = new PrintImageList(mContext);
            int nYpos = printImageList.add("集　金　日　報", 0, 10, PrintBaseImage.TextSize.LARGE, PrintBaseImage.Align.CENTER, PrintBaseImage.STROKE_WIDTH_BOLD)[1];

            nYpos = printImageList.add("印刷日 " + OtherUtil.format("yyyy年 MM月 dd日 HH:mm:ss", new java.util.Date()), 10, nYpos, PrintBaseImage.TextSize.XSMALL)[1];

            // 担当
            nYpos = printImageList.add("担当 " + tantname, 25, nYpos, PrintBaseImage.TextSize.XSMALL)[1];

            Set<String> setKeys = mapKensinData.keySet();
            printImageList.addRectBold(nYpos, nYpos);
            addPrintData(printImageList.getBitmap());
            printImageList = new PrintImageList(mContext);
            int nCnt = 0;
            ShukeiKensinData footerKensinData = new ShukeiKensinData();
            for (String strKey : setKeys) {
                boolean isFirst = true;
                // 日付
                List<ShukeiKensinData> lstKensinData = mapKensinData.get(strKey);
                nYpos = 0;
                printImageList = new PrintImageList(mContext);
                for (ShukeiKensinData kensinData : Objects.requireNonNull(lstKensinData)) {
                    if(kensinData.getNyu() == 0 && kensinData.getCho() == 0){
                        continue;
                    }
                    if(isFirst){
                        isFirst = false;
                        nYpos = printImageList.RECT_PADDING * 2;
                        nYpos = printImageList.add("集金日 " + strKey, 10, nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                    }
                    nCnt++;
                    printImageList.addRect(nYpos++, nYpos);
                    nYpos += printImageList.RECT_PADDING;
                    printImageList.add(kensinData.getKcode().trim(), 25, nYpos, PrintBaseImage.TextSize.XSMALL);
                    nYpos = printImageList.add(kensinData.getName().trim(), 270, nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                    printImageList.add("入金額", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
                    printImageList.add(OtherUtil.printformat("#,###,##0", kensinData.getNyu()) + "円", 150, nYpos, PrintBaseImage.TextSize.XSMALL);
                    printImageList.add("調整額", 300, nYpos, PrintBaseImage.TextSize.XSMALL);
                    printImageList.add(OtherUtil.printformat("#,###,##0", kensinData.getCho()) + "円", 425, nYpos, PrintBaseImage.TextSize.XSMALL);
                    footerKensinData.addData(kensinData);
                    addPrintData(printImageList.getBitmap());
                    nYpos = 0;
                    checkPageModeLength();
                    printImageList = new PrintImageList(mContext);
                }
                if(!isFirst) {
                    printImageList.addRectBold(nYpos, nYpos);
                }
            }
            nYpos += printImageList.RECT_PADDING * 2;
            printImageList.add("集金件数", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
            nYpos = printImageList.add(OtherUtil.format("#,##0", nCnt) + "件", 0, nYpos, PrintBaseImage.TextSize.XSMALL, PrintBaseImage.Align.RIGHT)[1];
            printImageList.add("入金額", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
            nYpos = printImageList.add(OtherUtil.printformat("#,###,##0", footerKensinData.getNyu()) + "円", 0, nYpos, PrintBaseImage.TextSize.XSMALL, PrintBaseImage.Align.RIGHT)[1];
            printImageList.add("調整額", 25, nYpos, PrintBaseImage.TextSize.XSMALL);
            printImageList.add(OtherUtil.printformat("#,###,##0", footerKensinData.getCho()) + "円", 0, nYpos, PrintBaseImage.TextSize.XSMALL, PrintBaseImage.Align.RIGHT);
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
