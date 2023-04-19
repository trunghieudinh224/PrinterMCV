package jp.co.MarutouCompack.commonClass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.baseClass.PrintBase;
import jp.co.MarutouCompack.baseClass.PrintBaseImage;
import jp.co.MarutouCompack.baseClass.PrintImageList;
import jp.co.MarutouCompack.baseClass.PrintShukeiInterface;
import jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.ShukeiRowItem;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UserData;
import jp.co.MarutouCompack.commonClass.PrinterDat.ShukeiDat;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * 集計表印刷データ生成クラス
 */
public class PrintShukeiImage extends PrintBase implements PrintShukeiInterface {
    /** ログ出力用タグ */
    private static final String TAG = PrintShukeiImage.class.getSimpleName();
    /** 集計データ */
    private List<ShukeiRowItem> m_lstShukeiRowItem;

    /**
     * コンストラクタ<br />
     * ポート名及び設定情報を設定
     * 
     * @param activity  [in] {@link Activity}   呼び出し元アクティビティ
     */
    public PrintShukeiImage(Activity activity) {
        super(activity);
        SharedPreferences wkPref = activity.getSharedPreferences("Printer", Context.MODE_PRIVATE);
        setPortName(wkPref.getString("PrinterMAC", ""));
    }

    public void setShukeiData(ShukeiDat mData) {
        List<ShukeiRowItem> lstShukeiRowItem = new ArrayList<>();
        lstShukeiRowItem.add(new ShukeiRowItem("検針件数", mData.getKensu() + "件"));
        lstShukeiRowItem.add(new ShukeiRowItem("ガス使用量", OtherUtil.Format((int)mData.getGsiyou(), (byte)1 ) + "㎥"));
        lstShukeiRowItem.add(new ShukeiRowItem("ガス料金", OtherUtil.KingakuFormat(mData.getGryokin()) + "円"));
        lstShukeiRowItem.add(new ShukeiRowItem("消費税", OtherUtil.KingakuFormat(mData.getShohi()) + "円"));
        lstShukeiRowItem.add(new ShukeiRowItem("還元額", OtherUtil.KingakuFormat(mData.getKang()) + "円"));
        lstShukeiRowItem.add(new ShukeiRowItem("合計", OtherUtil.KingakuFormat(mData.getTotal()) + "円"));
        if(mData.isToyukensinFlg()) {
            // 灯油
            lstShukeiRowItem.add(new ShukeiRowItem("灯油件数", OtherUtil.KingakuFormat(mData.getToyuCnt()) + "件"));
            lstShukeiRowItem.add(new ShukeiRowItem("灯油使用量", OtherUtil.Format(mData.getToyuUse(), (byte)1) + "Ｌ"));
            lstShukeiRowItem.add(new ShukeiRowItem("灯油料金", OtherUtil.KingakuFormat(mData.getToyuKin()) + "円"));
            lstShukeiRowItem.add(new ShukeiRowItem("消費税 ", OtherUtil.KingakuFormat(mData.getToyuTax()) + "円"));
            lstShukeiRowItem.add(new ShukeiRowItem("合計", OtherUtil.KingakuFormat(mData.getToyuTotal()) + "円"));
        }
        lstShukeiRowItem.add(new ShukeiRowItem("入金件数", mData.getNyuCnt() + "件"));
        lstShukeiRowItem.add(new ShukeiRowItem("入金額", OtherUtil.KingakuFormat(mData.getNyukin()) + "円"));
        lstShukeiRowItem.add(new ShukeiRowItem("調整額", OtherUtil.KingakuFormat(mData.getChosei()) + "円"));
        lstShukeiRowItem.add(new ShukeiRowItem("売上件数", mData.getUricnt() + "件"));
        lstShukeiRowItem.add(new ShukeiRowItem("数量", OtherUtil.Format(mData.getUrisur(), (byte)2) + "　"));
        lstShukeiRowItem.add(new ShukeiRowItem("売上 額(税抜き)", OtherUtil.KingakuFormat(mData.getUrikin()) + "円"));
        lstShukeiRowItem.add(new ShukeiRowItem("消費税額", OtherUtil.KingakuFormat(mData.getUritax()) + "円"));
        lstShukeiRowItem.add(new ShukeiRowItem("合計金額", OtherUtil.KingakuFormat(mData.getUrikin() + mData.getUritax()) + "円"));
        m_lstShukeiRowItem = lstShukeiRowItem;
    }

    /**
     * 印刷データの生成.
     *
     * @throws MException   印刷データ生成時にエラーがあった場合に発生
     */
    public void createPrintData(String date, String tantname) throws MException {
        MLog.INFO(mContext, TAG, "集計表印刷データ生成[開始]");
        String strLine;

        PrintImageList printImageList = new PrintImageList(mContext);

        strLine = "全集計日";
        printImageList.add(strLine, 0, 10, PrintBaseImage.TextSize.LARGE, PrintBaseImage.Align.CENTER, PrintBaseImage.STROKE_WIDTH_BOLD);

        // 日付
        strLine = "全集計日" + "(" + date + ")";
        printImageList.add(strLine, 25, printImageList.getHeightPrintLine() + 15, PrintBaseImage.TextSize.NORMAL);

        // 担当
        printImageList.add("担当 " + tantname, 25, printImageList.getHeightPrintLine());

        for(ShukeiRowItem shukeiRowItem : m_lstShukeiRowItem){
            printImageList.add(shukeiRowItem.getName(), 25, printImageList.getHeightPrintLine());
            printImageList.add("[ " + String.format("%22s", shukeiRowItem.getValue()) + " ]", 0, printImageList.getHeightPrintLine(), PrintBaseImage.TextSize.NORMAL, PrintBaseImage.Align.CENTER);
        }

        addPrintData(printImageList.getBitmap());
        printImageList.clear();
        MLog.INFO(mContext, TAG, "集計表印刷データ生成[終了]");
    }

    @Override
    protected void createHmInfoHeader(String strTitle, PrintImageList printImageList, boolean isTanka) {
    }

    @Override
    protected int createHmInfo(HmefDat[] lstHmefDat, PrintImageList printImageList, SysfDat sysfDat, Map<Integer, HmefDat> mapHmefDat, boolean isTanka) {
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
