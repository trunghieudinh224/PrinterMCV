package jp.co.MarutouCompack.baseClass;

import android.content.Context;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;

import jp.co.MarutouCompack.commonClass.PrinterDat.HanfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.TaxDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UTaxCommentDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UserData;

public class PrintBaseImage2 extends PrintBase {

    /** 矩形マージン */
    protected static final int RECT_MARGIN = 1;
    /** デフォルト印字位置(25) */
    protected static final int STRING_TAG_DEFAULT = 25;
    /** 検針日印字位置(30) */
    protected static final int STRING_TAG_KENSINDATE = 30;
    /** 印字位置(35) */
    protected static final int STRING_TAG_PREVIEW = 35;
    /** 印字位置(40) */
    protected static final int STRING_TAG_NOW = 25;
    /** 印字位置(490) */
    protected static final int STRING_TAG_YOBI = 495;
    /** 消費税印字位置(20) */
    protected static final int STRING_TAG_TAX = 20;
    /** 明細関連印字位置 */
    protected int[] START_POSITION_HAN_NORMAL = new int[] {START_TAG + RECT_MARGIN, 90, 310, 415};
    /** 明細関連印字位置(単価有り) */
    protected int[] START_POSITION_HAN_TANKA = new int[] {START_TAG + RECT_MARGIN, 90, 220, 320, 440};

    /**
     * コンストラクタ.
     *
     * @param ctx       [in] {@link Context}    呼び出し元コンテキスト
     */
    public PrintBaseImage2(Context ctx) {
        super(ctx);
    }

    /**
     * 矩形内の印字位置取得<br />
     * 引数で与えた印字位置一覧から矩形用マージンを減算した値の配列を取得
     *
     * @param positions [in] int[] 印字位置一覧
     * @return int[] 矩形マージンを減算した印字位置一覧
     */
    protected int[] getRectStartX(int[] positions) {
        int[] ret = new int[positions.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = positions[i] - RECT_MARGIN;
        }
        return ret;
    }


    /**
     * コメント印字
     *
     * @param commentData [in] CommentData    コメントデータ
     * @throws MException 印刷データ作成時にエラーがあった場合発生
     */
    public void createComment(String[] commentData) throws MException {
        if (commentData.length == 0) return;
        PrintImageList t_lst = new PrintImageList(mContext);
        int t_ypos = 10;
        int t_startheight = t_ypos - t_lst.RECT_PADDING;

        for (int i = 0; i < commentData.length; i++) {
            if (commentData[i].length() == 0) continue;
            t_lst.add(commentData[i], 20, t_ypos, PrintBaseImage.TextSize.XSMALL);
            t_ypos = t_lst.getHeightPrintLine();
        }
        if (t_ypos != t_startheight) {
            t_lst.addRect(t_startheight, t_ypos);
        }
        addPrintData(t_lst.getBitmap());
        t_lst.clear();
        checkPageModeLength();
    }

    /**
     * 店舗情報の印刷データを作成する。
     *
     * @param hanfDat           [in] {@link HanfDat}    店舗データ
     * @param strTantname       [in] String             担当者名
     * @param sysfDat           [in] {@link SysfDat}    システムデータ
     * @param isTnumberPrint    [in] boolean            Tナンバー・インボイスコメント印字有無(true:印字する, false:印字しない)
     */
    public void createUserInfo(jp.co.MarutouCompack.commonClass.PrinterDat.HanfDat hanfDat,
                               String strTantname,
                               jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat sysfDat,
                               boolean isTnumberPrint) throws MException {
        PrintImageList t_lst = new PrintImageList(mContext);
        int t_ypos = 10;
        int t_startheight = t_ypos;
        String wkStr;

        t_ypos += (t_lst.RECT_PADDING * 2);
        wkStr = hanfDat.getName();
        if(wkStr.getBytes(Charset.forName("Shift_JIS")).length > 32){
            // 販売店名が32byte以上の場合は文字サイズを小さくする
            t_ypos = t_lst.addSmall(wkStr, STRING_TAG_NOW, t_ypos)[1];
        }
        else {
            t_ypos = t_lst.add(wkStr, STRING_TAG_NOW, t_ypos)[1];
        }

        if (!OtherUtil.getClearString(hanfDat.getAdd1()).equals("")) {
            t_ypos = t_lst.add(hanfDat.getAdd1(), STRING_TAG_PREVIEW, t_ypos, PrintBaseImage.TextSize.SMALL)[1];
        }
        if (!OtherUtil.getClearString(hanfDat.getAdd2()).equals("")) {
            t_ypos = t_lst.add(hanfDat.getAdd2(), STRING_TAG_PREVIEW, t_ypos, PrintBaseImage.TextSize.SMALL)[1];
        }
        t_lst.addRectBold(t_startheight, t_ypos);

        t_startheight = t_ypos;
        t_ypos += (t_lst.RECT_PADDING * 2);

        t_lst.add("TEL " + hanfDat.getTel(), STRING_TAG_PREVIEW, t_ypos, PrintBaseImage.TextSize.SMALL);
        t_ypos = t_lst.add("(担当)", 340, t_ypos, PrintBaseImage.TextSize.XSMALL)[1];
        t_lst.add("FAX " + hanfDat.getFax(), STRING_TAG_PREVIEW, t_ypos, PrintBaseImage.TextSize.SMALL);
        t_ypos = t_lst.add(strTantname, 340, t_ypos, PrintBaseImage.TextSize.XSMALL)[1];
        t_lst.addRectBold(t_startheight, t_ypos);

        String strBkinfo;
        t_startheight = t_ypos;
        t_ypos += t_lst.RECT_PADDING * 2;
        if(OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkname_0()).trim()) > 0){
            // 銀行１名称有り
            strBkinfo = OtherUtil.getClearString(hanfDat.getBkname_0()).trim();
            if(OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkshiten_0()).trim()) > 0){
                // 銀行１支店名有り
                strBkinfo += "/" + OtherUtil.getClearString(hanfDat.getBkshiten_0()).trim();
            }
            t_ypos = t_lst.add(strBkinfo, STRING_TAG_PREVIEW, t_ypos, PrintBaseImage.TextSize.SMALL)[1];
            strBkinfo = "";
            if(OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkkubun_0()).trim()) > 0){
                // 銀行１区分有り
                strBkinfo = OtherUtil.getClearString(hanfDat.getBkkubun_0()).trim();
            }
            if(OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkban_0()).trim()) > 0){
                // 銀行１口座番号有り
                if(strBkinfo.length() != 0){
                    // 区分有りの場合
                    strBkinfo += "/";
                }
                strBkinfo += OtherUtil.getClearString(hanfDat.getBkban_0()).trim();
            }
            if(strBkinfo.length() != 0){
                t_ypos = t_lst.add(strBkinfo, STRING_TAG_PREVIEW, t_ypos, PrintBaseImage.TextSize.SMALL)[1];
            }
        }
        if(OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkname_1()).trim()) > 0){
            // 銀行２名称有り
            strBkinfo = OtherUtil.getClearString(hanfDat.getBkname_1()).trim();
            if(OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkshiten_1()).trim()) > 0){
                // 銀行２支店名有り
                strBkinfo += "/" + OtherUtil.getClearString(hanfDat.getBkshiten_1()).trim();
            }
            t_ypos = t_lst.add(strBkinfo, STRING_TAG_PREVIEW, t_ypos, PrintBaseImage.TextSize.SMALL)[1];
            strBkinfo = "";
            if(OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkkubun_1()).trim()) > 0){
                // 銀行２区分有り
                strBkinfo = OtherUtil.getClearString(hanfDat.getBkkubun_1()).trim();
            }
            if(OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkban_1()).trim()) > 0){
                // 銀行２口座番号有り
                if(strBkinfo.length() != 0){
                    // 区分有りの場合
                    strBkinfo += "/";
                }
                strBkinfo += OtherUtil.getClearString(hanfDat.getBkban_1()).trim();
            }
            if(strBkinfo.length() != 0){
                t_ypos = t_lst.add(strBkinfo, STRING_TAG_PREVIEW, t_ypos, PrintBaseImage.TextSize.SMALL)[1];
            }
        }
        if(t_startheight != t_ypos - (t_lst.RECT_PADDING * 2)) {
            // 銀行情報を印字した場合は枠線を追加
            t_lst.addRectBold(t_startheight, t_ypos);
        }
        // インボイス対応(Tナンバー・インボイスコメント印字)
        String strTnumber = OtherUtil.getClearString(hanfDat.getTnumber()).trim();
        if(isTnumberPrint && !strTnumber.isEmpty()) {
            // Tナンバー・インボイスコメント印字有り
            // Tナンバー設定有り
            // ⇒ 印字する
            t_startheight = t_ypos;
            t_ypos += t_lst.RECT_PADDING * 2;
            t_ypos = t_lst.add("登録番号：T" + strTnumber, STRING_TAG_PREVIEW, t_ypos, PrintBaseImage.TextSize.SMALL)[1];

            String strInvoiceComment = OtherUtil.getClearString(sysfDat.getInvoiceComment()).trim();
            t_ypos = t_lst.add(strInvoiceComment, STRING_TAG_PREVIEW, t_ypos, PrintBaseImage.TextSize.XXSMALL)[1];
//            if(!strInvoiceComment.isEmpty()) {
//                for(String strCmt : OtherUtil.cutStringList(strInvoiceComment, 40)){
//                    t_ypos = t_lst.add(strCmt, STRING_TAG_PREVIEW, t_ypos, PrintBaseImage.TextSize.XXSMALL)[1];
//                }
//            }
            if (t_startheight != t_ypos - (t_lst.RECT_PADDING * 2)) {
                // 銀行情報を印字した場合は枠線を追加
                t_lst.addRectBold(t_startheight, t_ypos);
            }
        }
        t_lst.add(" ", STRING_TAG_NOW, t_ypos, PrintBaseImage.TextSize.XSMALL); // 最終項目の見切れ対策 13.05.15
        addPrintData(t_lst.getBitmap());
        t_lst.clear();
        checkPageModeLength();
    }

    /**
     * 販売明細ヘッダーの印字.
     *
     * @param strTitle          [in] String                     タイトル
     * @param printImageList    [in] {@link PrintImageList}     印刷データ格納先インスタンス
     * @param isTanka           [in] boolean                    単価印字フラグ(true: 印字有り, false: 印字無し)
     */
    protected void createHmInfoHeader(String strTitle, PrintImageList printImageList, boolean isTanka) {
        int t_ypos = printImageList.getHeightPrintLine() + 10;
        if(strTitle != null && !strTitle.isEmpty()) {
            t_ypos = printImageList.add(strTitle, START_TAG, t_ypos, PrintBaseImage.TextSize.XSMALL)[1];
            t_ypos += 5;
        }
        int t_startheight = t_ypos - printImageList.RECT_PADDING;
        int t_idx = 0;
        int[] nPositions = isTanka ? START_POSITION_HAN_TANKA : START_POSITION_HAN_NORMAL;
        printImageList.add(" 月日", nPositions[t_idx++], t_ypos, PrintBaseImage.TextSize.XSMALL);
        String strHmTitle = isTanka ? "   品　目" : "　   品　目";
        printImageList.add(strHmTitle, nPositions[t_idx++], t_ypos, PrintBaseImage.TextSize.XSMALL);
        String strSurTilte = isTanka ? " 数 量" : " 数　量";
        printImageList.add(strSurTilte, nPositions[t_idx++], t_ypos, PrintBaseImage.TextSize.XSMALL);
        if(isTanka){
            printImageList.add("  単　価", nPositions[t_idx++], t_ypos, PrintBaseImage.TextSize.XSMALL);
        }
        t_ypos = printImageList.add("　 金　額", nPositions[t_idx], t_ypos, PrintBaseImage.TextSize.XSMALL)[1];
        printImageList.addRectMulti(getRectStartX(nPositions), t_startheight, t_ypos);
    }

    protected int createHmInfo(jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[] lstHmefDat,
                               PrintImageList printImageList, jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat sysfDat,
                               Map<Integer, jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat> mapHmefDat,
                               boolean isTanka){
        if (lstHmefDat == null || lstHmefDat.length == 0) {
            return 0;
        }
        int nTax = 0;
        int nYpos = printImageList.getHeightPrintLine();
        int nStartHeight;
        String strPrint;
        int nIdx;
        int[] nPositions = isTanka ? START_POSITION_HAN_TANKA : START_POSITION_HAN_NORMAL;
        for (jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat hmefDat : lstHmefDat) {
            if(!hmefDat.isUsef() || hmefDat.getHmCode() < sysfDat.getSnvalue() ){
                continue;
            }
            nStartHeight = nYpos;
            nYpos += printImageList.RECT_PADDING;
            nIdx = 0;
            // 日付
            if(hmefDat.getDenm() != 0) {
                strPrint = (hmefDat.getDenm() < 10 ? " " + hmefDat.getDenm() : hmefDat.getDenm()) + "/"
                        + (hmefDat.getDend() < 10 ? " " + hmefDat.getDend() : hmefDat.getDend());
                printImageList.add(strPrint, nPositions[nIdx], nYpos, PrintBaseImage.TextSize.XSMALL);
            }
            nIdx++;
            // 品目
            strPrint = OtherUtil.getClearString(hmefDat.getHmName());
            if(strPrint.getBytes(Charset.forName("Shift_JIS")).length > 20){
                printImageList.add(strPrint, nPositions[nIdx++], nYpos + 5, PrintBaseImage.TextSize.HMSMALL);
            }
            else {
                printImageList.add(strPrint, nPositions[nIdx++], nYpos, PrintBaseImage.TextSize.XSMALL);
            }
            // 数量
            if (hmefDat.getSuryo() != 0) {
                strPrint = OtherUtil.printformat(hmefDat.getSuryo(), "###0.00", (byte) 2);
            }
            else {
                strPrint = " ";
            }
            printImageList.add(strPrint, nPositions[nIdx++], nYpos, PrintBaseImage.TextSize.XSMALL);
            if(isTanka && hmefDat.getTanka() != 0){
                // 単価印字有り
                if(hmefDat.getTanka() % 100 == 0){
                    strPrint = OtherUtil.printformat(hmefDat.getTanka(), "###,##0", (byte) 2);
                }
                else if(hmefDat.getTanka() % 10 == 0){
                    strPrint = OtherUtil.printformat(hmefDat.getTanka(), "##,##0.0", (byte) 2);
                }
                else {
                    strPrint = OtherUtil.printformat(hmefDat.getTanka(), "#,##0.00", (byte) 2);
                }
                printImageList.add(strPrint, nPositions[nIdx++], nYpos, PrintBaseImage.TextSize.XSMALL);
            }
            // 金額
            int nKin = hmefDat.getKin();
            if(!mapHmefDat.isEmpty()){
                // 軽減税率対応は税込み
                nKin += hmefDat.getTax();
            }
            strPrint = OtherUtil.printformat("#,###,##0", nKin);
            jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat hmefDatKeigen = mapHmefDat.get(hmefDat.getKeigenKubun() * 1000 + hmefDat.getTaxR());
            if(hmefDatKeigen != null && hmefDatKeigen.getKeigenKubun() != 0){
                strPrint += String.format(Locale.JAPAN, "*%d", hmefDatKeigen.getCusRec());
            }
            nTax += hmefDat.getTax();
            nYpos = printImageList.add(strPrint, nPositions[nIdx], nYpos, PrintBaseImage.TextSize.XSMALL)[1];

            // リース割賦の残回数を印字する。// 2008.07.28
            if ( hmefDat.getHbnmPrn() == 1 ) {
                strPrint = hmefDat.getHbName();
                if (!strPrint.contains("(  ")) continue; // 残回数 '(  ' というパターンがなければ以下を除外する。
                strPrint = strPrint.replace( "(  ", "(" );
                strPrint = strPrint.replace( "/ ", "/" );

                //伝票日付
                //品目名
                nYpos = printImageList.add(strPrint, nPositions[1], nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                //数量
                // 単価
                //金額
            }

            printImageList.addRectMulti(getRectStartX(nPositions), nStartHeight, nYpos);
        }
        return nTax;
    }

    //Hieu
    /**
     * 消費税明細の印字データ生成.
     * <br>軽減税率対応(Sy2fDat.msyskeigen == 1)の場合は軽減税率区分、税率毎の消費税を印字.
     * <br>非対応の場合は明細の合計消費税額を印字.
     *
     * @param printImageList    [in] {@link PrintImageList}         印刷データ
     * @param mapHmefDat        [in] {@code Map<Integer, HmefDat>}  軽減税率区分、税率毎の消費税金額
     * @param nTax              [in] int                            消費税金額
     * @param isTanka           [in] boolean                        単価印字フラグ
     */
    protected void createHmInfoTax(PrintImageList printImageList,
                                   Map<Integer, jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat> mapHmefDat,
                                   jp.co.MarutouCompack.commonClass.PrinterDat.UserData userData,
                                   int nTax, boolean isTanka){
        // 消費税
        int[] nPositions = isTanka ? START_POSITION_HAN_TANKA : START_POSITION_HAN_NORMAL;
        if(userData.getSy2fDat().getSyskeigen() == 0) {
            if (nTax != 0) {
                int nYpos = printImageList.getHeightPrintLine();
                int nStartHeight = nYpos;
                nYpos += printImageList.RECT_PADDING;
                printImageList.add("消費税", nPositions[1], nYpos, PrintBaseImage.TextSize.XSMALL);
                nYpos = printImageList.add(OtherUtil.printformat("#,###,##0", nTax), nPositions[isTanka ? 4 : 3], nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                printImageList.addRectMulti(getRectStartX(nPositions), nStartHeight, nYpos);
            }
        }
        else if(mapHmefDat != null ){
            for(jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat hmefDat : mapHmefDat.values()){
                int nYpos = printImageList.getHeightPrintLine();
                int nStartHeight = nYpos;
                nYpos += printImageList.RECT_PADDING;
                printImageList.add(getHmefTaxKeigenTotal(hmefDat), nPositions[1], nYpos, PrintBaseImage.TextSize.XSMALL);
                nYpos = printImageList.add(OtherUtil.printformat( "#,###,##0", hmefDat.getKin()) + ")", nPositions[isTanka ? 4 : 3], nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                printImageList.add(getHmefTaxkeigenTax(hmefDat), nPositions[1], nYpos, PrintBaseImage.TextSize.XSMALL);
                nYpos = printImageList.add(OtherUtil.printformat("#,###,##0", hmefDat.getTax()) + ")", nPositions[isTanka ? 4 : 3], nYpos, PrintBaseImage.TextSize.XSMALL)[1];
                printImageList.addRect(nStartHeight, nYpos);
            }
        }
    }


    /**
     * 内税コメントの生成.
     *
     * @param mUTC  [in] {@link UTaxCommentDat}         印刷用データ
     * @param t_lst         [in/out] {@link PrintImageList} 実際の印刷データ
     */
    protected void createUTaxComment(UTaxCommentDat mUTC, PrintImageList t_lst){
        //内税の印字
        int t_ypos = t_lst.getHeightPrintLine();
        int t_startheight = t_ypos;
        String wkStr;


        //mUTC.nGUchiZei
        //mUTC.nUchiZei
        if ( mUTC.getGUchiZei() != 0 || mUTC.getUchiZei() != 0 ) {
            //wkyPos = yPos;
            if ( mUTC.getGUchiZei() != 0 ) {
                t_ypos += t_lst.RECT_PADDING * 2;
                if ( mUTC.getUchiZei() == 0 ) {
                    wkStr = "ガス売上には";
                    wkStr += OtherUtil.KingakuFormat(mUTC.getGUchiZei());
                    wkStr += "円の消費税が含まれます。";
                }else {
                    wkStr = "ガス売上には";
                    wkStr += OtherUtil.KingakuFormat(mUTC.getGUchiZei());
                    wkStr += "円、";
                }
                t_ypos = t_lst.add(wkStr, STRING_TAG_TAX, t_ypos, PrintBaseImage.TextSize.XSMALL)[1];
            }
            if ( mUTC.getUchiZei() != 0 ) {
                if(t_startheight == t_ypos ){
                    t_ypos += t_lst.RECT_PADDING * 2;
                }
                if ( mUTC.getGUchiZei() == 0 ) {
                    wkStr = "他売上には";
                }else {
                    wkStr = "売上には";
                }
                wkStr += OtherUtil.KingakuFormat(mUTC.getUchiZei());
                wkStr += "円の消費税が含まれます。";
                t_ypos = t_lst.add(wkStr, STRING_TAG_TAX, t_ypos, PrintBaseImage.TextSize.XSMALL)[1];
            }
            t_lst.addRectBold(START_TAG, t_startheight, t_ypos);
        }
    }

    /**
     * 領収印印字.
     *
     * @param strInpReceipt   [in] String 領収金額
     */
    public void createRyoshu(String strInpReceipt) throws MException {
        int t_ypos = 25;
        int t_startheight;
        String wkStr;
        PrintImageList t_lst = new PrintImageList(mContext);
        t_startheight = t_ypos - t_lst.RECT_PADDING;
        wkStr = "領 収 印";
        t_ypos = t_lst.add(wkStr, 429, t_ypos, PrintBaseImage.TextSize.SMALL)[1];
        t_lst.addRect(412, t_startheight, 150, t_ypos - t_startheight, PrintBaseImage.RECT_STROKE_BOLD);
        t_startheight += t_ypos - t_startheight;
        t_lst.addRect(412, t_startheight, 150, 150, PrintBaseImage.RECT_STROKE_BOLD);

        wkStr = "領収金額";
        int x_pos = 350 - (int) PrintBaseImage.getValuePosition(mContext, wkStr, PrintBaseImage.TextSize.LARGE, PrintBaseImage.STROKE_WIDTH_BOLD);
        t_ypos = t_lst.add(wkStr, x_pos, t_ypos, PrintBaseImage.TextSize.LARGE, PrintBaseImage.STROKE_WIDTH_BOLD)[1];

        wkStr = strInpReceipt + "円";
        x_pos = 350 - (int) PrintBaseImage.getValuePosition(mContext, wkStr, PrintBaseImage.TextSize.LARGE, PrintBaseImage.STROKE_WIDTH_BOLD);
        t_ypos = t_lst.add(wkStr, x_pos, t_ypos, PrintBaseImage.TextSize.LARGE, PrintBaseImage.STROKE_WIDTH_BOLD)[1];
        t_ypos += 50;
        t_lst.add("上記の通り領収致しました。有難うございました。", START_TAG, t_ypos, PrintBaseImage.TextSize.XSMALL);

        addPrintData(t_lst.getBitmap());
        t_lst.clear();
        checkPageModeLength();
    }

    @Override
    protected void createHmInfoFooter(PrintImageList printImageList, long lKin) {
    }
}
