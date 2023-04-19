
package jp.co.MarutouCompack.baseClass;

import android.content.Context;
import android.content.SharedPreferences;

import com.sanei.libs.printersdk.PrinterSDK;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;

import jp.co.MarutouCompack.commonClass.PrinterDat.HanfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KensinData;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.TaxDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UTaxCommentDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UserData;

@SuppressWarnings("unused")
public class PrintBaseCharactor extends PrintBase {

    // --------------------------------------------------
    // 定数
    // --------------------------------------------------
    /** ログ出力用タグ */
    private static final String TAG = PrintBaseCharactor.class.getSimpleName();
    /** 改行量(標準) */
    protected static final int LINE_HEIGHT = 34;
    /** 改行量(大) */
    protected static final int LINE_HEIGHT_BIG = 42;
    /** 開始位置 */
    protected static final byte START_POSITION = 20;
    /** 矩形横幅 */
    protected static final int RECT_WIDTH = 555;
    /** 明細印字位置 */
    protected int[] START_X_HM_NORMAL = new int[] {
            0, 75, 320, 420, 555
    };
    /** 明細印字位置(含単価) */
    protected int[] START_X_HM_TANKA = new int[] {
            0, 75, 194, 302, 420, 555
    };
    /** 明細タイトル印字位置 */
    protected static byte[] MEISAI_POSITION_NORMAL = new byte[] {
            1, 7, 28, 34
    };
    /** 明細タイトル印字位置(含単価) */
    protected static byte[] MEISAI_POSITION_TANKA = new byte[]{
            1, 7, 18, 27, 36
    };

    // --------------------------------------------------
    // enum定数
    // --------------------------------------------------
    /** 印字位置 */
    protected enum LAYOUT {
        /** 中央寄せ */
        CENTER,
        /** 右寄せ */
        RIGHT,
        /** 左寄せ */
        LEFT
    }

    /** 文字サイズ */
    protected enum CHARSIZE {
        /** 小 */
        SMALL,
        /** 大 */
        LARGE,
    }

    /** 太さ */
    protected enum STYLE {
        /** 通常 */
        NORMAL,
        /** 太い */
        BOLD,
    }

    /**
     * コンストラクタ
     *
     * @param ctx           [in] {@link Context}    呼び出し元コンテキスト
     */
    public PrintBaseCharactor(Context ctx) {
        super(ctx);
        SharedPreferences wkPref = ctx.getSharedPreferences("Printer", Context.MODE_PRIVATE);
        setPortName(wkPref.getString("PrinterMAC", ""));
        // ShiftJISモードに設定
        addCommand(PrintCommand.commandSetKanjiMode(true));
        addCommand(PrintCommand.commandIndention((byte) LINE_HEIGHT));

    }

    @Override
    protected void printExecute(int wkPrintMode) throws MException {
        // ページモードにして印刷エリアを設定する
        this.setPageArea();
        // ページモードの印字とスタンダードモードへの復帰
        addCommand(PrintCommand.commandSwichStdMode());
        // 文字列モードで印刷を実行する
        super.printExecute(PRINT_MODE_CHAR);
    }

    /**
     * モバイルプリンタに対してデータを送信する.<br>
     * 送信する前にデータの先頭にページモードへの切り替えと印字エリアの設定コマンドを書き込む.
     */
    protected void sendData(){
        if(mHeight == 0){
            // 印字長が0の場合は出力しない
            return;
        }
        // ページモードにして印刷エリアを設定する
        this.setPageArea();
        // ページモードの印字とスタンダードモードへの復帰
        addCommand(PrintCommand.commandSwichStdMode());
        // データを送信する
        sendCharactorData();

        // すべてのデータを受信したか確認するためステータスを取得
        PrinterSDK.getStatus();
    }

    /**
     * 1500ラインを超える印字エリアは印刷不可な為、1500ラインを超えた場合は<br>
     * 一旦データを送信する
     */
    protected void checkPageModeLength(){
        if(mHeight > 1500){
            sendData();
            mHeight = 0;
        }
    }

    /**
     * 文字を印字する。
     * 
     * @param strLine       [in] String             印字文字列
     * @param enumCharSize  [in] {@link CHARSIZE}   文字サイズ
     * @param enumLayout    [in] {@link LAYOUT}     文字のレイアウト
     * @param doLF          [in] boolean            改行フラグ
     * @return int[] 開始終了高さ
     */
    protected int[] print(String strLine, CHARSIZE enumCharSize, LAYOUT enumLayout, boolean doLF) {
        int[] ret = new int[2];
        ret[0] = mHeight;
        switch (enumLayout) {
            case CENTER: // 文字を中央寄せにする
                addCommand(PrintCommand.commandSetAlign(PrintCommand.ALIGN_CENTER));
                break;
            case RIGHT:
                addCommand(PrintCommand.commandSetAlign(PrintCommand.ALIGN_RIGHT));
                break;
            case LEFT:
                addCommand(PrintCommand.commandSetAlign(PrintCommand.ALIGN_LEFT));
                break;
            default:
                break;
        }
        switch (enumCharSize) {
            case SMALL:
                addCommand(PrintCommand.commandSetFontMode(false));
                addCommand(PrintCommand.commandSetKanjiSize(false)); // 文字サイズ拡大解除
                if (doLF) mHeight += (byte) (LINE_HEIGHT);
                break;
            case LARGE:
                addCommand(PrintCommand.commandSetFontMode(true));
                addCommand(PrintCommand.commandSetKanjiSize(true)); // 文字サイズを4倍（縦横２倍）にする
                if (doLF) mHeight += (byte) (LINE_HEIGHT_BIG);
                break;
        }
        if (doLF)
            addData(strLine);
        else
            addDataNoLF(strLine);

        ret[1] = mHeight;
        return ret;
    }

    /**
     * 印字濃度の設定
     *
     * @param nDencity [in] int    印字濃度
     */
    protected void setDensity(int nDencity) {
        addCommand(PrintCommand.commandSetPrintDensity(nDencity));
        addCommand(PrintCommand.commandSaveFlash());
    }

    /**
     * 文字列の印字
     *
     * @param strLine       [in] String             印字文字列
     * @param enumCharSize  [in] {@link CHARSIZE}   印字サイズ
     * @param enumLayout    [in] {@link LAYOUT}     印字位置
     * @return int[] 印字後の伝票高さ
     */
    protected int[] print(String strLine, CHARSIZE enumCharSize, LAYOUT enumLayout) {
        return print(strLine, enumCharSize, enumLayout, true);
    }

    /**
     * 通常印字
     *
     * @param strLine         [in] String 印字文字列
     * @return int[] 印字後の伝票高さ
     */
    protected int[] printNormal(String strLine) {
        addCommand(PrintCommand.commandSetRelativePosition(START_POSITION, (byte) 0));
        return print(strLine, CHARSIZE.SMALL, LAYOUT.LEFT, true);
    }

    /**
     * 通常印字。
     *
     * @param strLine       [in] String             印字文字列
     * @param bHigh         [in] byte               相対位置(上位)
     * @param bLow          [in] byte               相対位置(下位)
     * @param enumCharsize  [in] {@link CHARSIZE}   文字サイズ
     */
    protected int[] printNormal(String strLine, byte bHigh, byte bLow, CHARSIZE enumCharsize) {
        addCommand(PrintCommand.commandSetRelativePosition(bLow, bHigh));
        return print(strLine, enumCharsize, LAYOUT.LEFT, true);
    }

    /**
     * 高さ調整。
     *
     */
    protected void adjustHeight_13() {
        mHeight += 13;
    }

    /**
     * 指定byte数だけ半角スペースで埋める。
     * 
     * @param strLine   [in] String 対象文字列
     * @param nLength   [in] byte   半角スペースで埋める文字数
     * @return String 指定byte数分半角スペースで埋めた文字列
     */
    private String fillSpace(String strLine, int nLength) {
        StringBuilder ret = new StringBuilder();
        if (strLine.getBytes().length % 2 != 0) {
            strLine += " ";
        }
        int t_idx = 0;
        try {
            while (ret.toString().getBytes("SJIS").length < nLength) {
                if (t_idx < strLine.length()) {
                    ret.append(strLine.charAt(t_idx++));
                }
                else {
                    ret.append(" ");
                }
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    /**
     * 水平位置の指定位置に文字列を印字する
     *
     * @param bPositions    [in] byte[] 印字位置
     * @param strLine       [in] String 印字文字列
     * @param nIdx          [in] int    印字位置
     * @return int[] 印字後の伝票高さ
     */
    protected int[] printHPInfo(byte[] bPositions, String strLine, int nIdx) {
        return printHPInfo(bPositions, strLine, nIdx, CHARSIZE.SMALL);
    }

    /**
     * 水平位置の指定位置に文字列を印字する
     *
     * @param bPositions    [in] byte[] 印字位置
     * @param strLine       [in] String 印字文字列
     * @param nIdx          [in] int    印字位置
     * @return int[] 印字後の伝票高さ
     */
    protected int[] printHPInfoLarge(byte[] bPositions, String strLine, int nIdx) {
        return printHPInfo(bPositions, strLine, nIdx, CHARSIZE.LARGE);
    }

    /**
     * 水平位置の指定位置に文字列を印字する
     *
     * @param bPositions    [in] byte[]             印字位置
     * @param strLine       [in] String             印字文字列
     * @param nIdx          [in] int                印字位置
     * @param enumCharSize  [in] {@link CHARSIZE}   文字サイズ
     * @return int[] 印字後の伝票高さ
     */
    protected int[] printHPInfo(byte[] bPositions, String strLine, int nIdx, CHARSIZE enumCharSize) {
        // 最初の位置のみ水平移動を使用
        if (nIdx == 0) {
            addCommand(PrintCommand.commandSetRelativePosition((byte) (bPositions[nIdx] * 8), (byte) 0));
        }
        // ２番目以降は空白埋めで対応
        if (nIdx + 1 < bPositions.length) {
            strLine = fillSpace(strLine, bPositions[nIdx + 1] - bPositions[nIdx]);
        }

        if (nIdx != bPositions.length - 1) {
            return print(strLine, enumCharSize, LAYOUT.LEFT, false);
        }
        else {
            return print(strLine, enumCharSize, LAYOUT.LEFT, true);
        }
    }

    /**
     * 指定ドット数改行する。
     * 
     * @param nLines [in] int 行数
     */
    private int[] printLFBase(int nLines) {
        int[] ret = new int[2];
        ret[0] = mHeight;
        mHeight += nLines * LINE_HEIGHT;
        addCommand(PrintCommand.commandFeedLine((byte) nLines));
        ret[1] = mHeight;
        return ret;
    }

    /**
     * 指定行数改行する。
     * 
     * @param nLines  [in] int    行数
     * @return int[] 開始終了高さ
     */
    protected int[] printLF(int nLines) {
        return printLFBase(nLines);
    }

    /**
     * 指定行数改行する。
     * 
     * @param nDots [in] byte   ドット数
     * @return int[]    開始終了高さ
     */
    protected int[] printFeedDot(byte nDots) {
        int[] ret = new int[2];
        ret[0] = mHeight;
        mHeight += nDots;
        addCommand(PrintCommand.commandFeedDot(nDots));
        ret[1] = mHeight;
        return ret;
    }

    /**
     * 複数の矩形印字
     *
     * @param nStartX      [in] int[]  開始位置(必ず２点以上を指示すること)
     * @param nStartHeight [in] int    印字開始高さ
     * @param nEndHeight   [in] int    印字終了高さ
     */
    protected void printRectangles(int[] nStartX, int nStartHeight, int nEndHeight) {
        for (int i = 0; i < nStartX.length - 1; i++) {
            printRectangle(nStartX[i], nStartX[i + 1] - nStartX[i] + 2, nStartHeight, nEndHeight, STYLE.NORMAL);
        }
    }

    /**
     * 矩形の印字
     *
     * @param nStartX       [in] int            印字開始横位置
     * @param nWidth        [in] int            横幅
     * @param nStartHeight  [in] int            印字開始高さ
     * @param nEndHeight    [in] int            印字終了高さ
     * @param enumStyle     [in] {@link STYLE}  線の太さ
     */
    protected void printRectangle(int nStartX, int nWidth, int nStartHeight, int nEndHeight, STYLE enumStyle) {
        if (enumStyle == STYLE.BOLD) {
            nStartHeight -= 2;
            nEndHeight += 2;
        }
        byte t_linethin = (enumStyle == STYLE.NORMAL ? (byte) 2 : (byte) 5);
        addCommand(createRectangle(nStartX, nStartHeight, nWidth, getRectangleHeight(nStartHeight, nEndHeight), t_linethin));
    }

    /**
     * 矩形印字時の高さの取得
     *
     * @param nStartHeight [in] int    印字開始高さ
     * @param nEndHeight   [in] int    印字終了高さ
     * @return int 終了-開始
     */
    private int getRectangleHeight(int nStartHeight, int nEndHeight) {
        return nEndHeight - nStartHeight;
    }


    /**
     * 明細情報の印字。
     *
     * @param strLine   [in] String 印字文字列
     * @param nIdx      [in] int    印字位置
     * @return int[] 印字後の伝票高さ
     */
    protected int[] printMeisaiInfo(String strLine, int nIdx) {
        byte[] t_position = new byte[] {
                1, 7, 28, 34
        };
        return printHPInfo(t_position, strLine, nIdx);
    }

    /**
     * 販売店情報の印字。
     *
     * @param strLine   [in] String 印字文字列
     * @param nIdx      [in] int    印字位置
     */
    protected void printHanInfo(String strLine, int nIdx) {
        byte[] t_position = new byte[] {
                2, 28,
        };
        printHPInfo(t_position, strLine, nIdx);
    }

    /**
     * 請求書情報の印字。
     *
     * @param strLine   [in] String 印字文字列
     * @param nIdx      [in] int    印字位置
     * @return int[] 印字後の伝票高さ
     */
    protected int[] printSeikyuInfo(String strLine, int nIdx) {
        byte[] t_position = new byte[] {
                2, 34
        };
        return printHPInfo(t_position, strLine, nIdx);
    }

    /**
     * 請求書情報の印字。
     *
     * @param strLine   [in] String 印字文字列
     * @param nIdx      [in] int    印字位置
     * @return int[] 印字後の伝票高さ
     */
    protected int[] printSeikyuInfoLarge(String strLine, int nIdx) {
        byte[] t_position = new byte[] {
                2, 15
        };
        return printHPInfoLarge(t_position, strLine, nIdx);
    }

    /**
     * 内税コメントの生成.
     *
     * @param mUTC  [in] {@link UTaxCommentDat}         印刷用データ
     */
    protected void createUTaxComment(UTaxCommentDat mUTC) {
        //内税の印字
        int nStartHeight = mHeight;
        int nEndHight = nStartHeight;
        String strComment;


        if ( mUTC.getGUchiZei() != 0 || mUTC.getUchiZei() != 0 ) {
            if ( mUTC.getGUchiZei() != 0 ) {
                if ( mUTC.getUchiZei() == 0 ) {
                    strComment = "ガス売上には";
                    strComment += OtherUtil.KingakuFormat(mUTC.getGUchiZei());
                    strComment += "円の消費税が含まれます。";
                }else {
                    strComment = "ガス売上には";
                    strComment += OtherUtil.KingakuFormat(mUTC.getGUchiZei());
                    strComment += "円、";
                }
                nEndHight = printNormal(strComment)[1];
            }
            if ( mUTC.getUchiZei() != 0 ) {
                if ( mUTC.getGUchiZei() == 0 ) {
                    strComment = "他売上には";
                }else {
                    strComment = "売上には";
                }
                strComment += OtherUtil.KingakuFormat(mUTC.getUchiZei());
                strComment += "円の消費税が含まれます。";
                nEndHight = printNormal(strComment)[1];
            }

            if(nStartHeight != nEndHight) {
                nEndHight = printFeedDot((byte) 10)[1];
                printRectangle(0, RECT_WIDTH, nStartHeight, nEndHight, STYLE.BOLD);
            }
        }
        checkPageModeLength();
    }

    /**
     * 領収印印字.
     *
     * @param strInpReceipt   [in] String 入金額
     */
    public void createRyoshu(String strInpReceipt) {
        printLF(1);

        final int STARTX = 400;
        final int RECTWIDTH = 165;

        int nStart = printFeedDot((byte)5 )[0];
        int nEnd = printNormal("　　 領 収 印", (byte) 1, (byte) 120, CHARSIZE.SMALL)[1]; // 位置合わせが難しいため最終的に空白で調整
//        int nEnd = printFeedDot((byte)5)[1];
        printRectangle(STARTX, RECTWIDTH, nStart, nEnd, STYLE.BOLD);

        printNormal("領収金額", (byte) 0, (byte) 100, CHARSIZE.LARGE);
        String t_strnyukin = strInpReceipt + "円";
        printNormal(t_strnyukin, (byte) 0, (byte) 100, CHARSIZE.LARGE);
        nEnd = printLF(2)[1];
        printRectangle(STARTX, RECTWIDTH, nStart, nEnd, STYLE.BOLD);
        printNormal("上記の通り領収致しました。有難うございました。");
        adjustHeight_13(); // 全角改行の高さ調整
        printLF(1);
        checkPageModeLength();
    }

    /**
     * 販売明細のヘッダーを作成
     *
     * @param strHeaderTitle    [in] String     タイトル
     * @param isTanka           [in] boolean    単価印字有無(true: 単価印字有り, false: 単価印字無し)
     */
    protected void createHmInfoHeader(String strHeaderTitle, PrintImageList printImageList, boolean isTanka) {
        printFeedDot((byte)5);
        if(!OtherUtil.getClearString(strHeaderTitle).isEmpty()) {
            printNormal(strHeaderTitle);
        }
        byte[] bPositions = isTanka ? MEISAI_POSITION_TANKA : MEISAI_POSITION_NORMAL;

        int nStartHeight = printFeedDot((byte)5)[0];
        int nIdx = 0;
        printHPInfo(bPositions, " 月日", nIdx++);
        printHPInfo(bPositions, "　品　目", nIdx++);
        printHPInfo(bPositions, "数　量", nIdx++);
        if(isTanka){
            printHPInfo(bPositions, "単　価", nIdx++);
        }
        int nEndHeight = printHPInfo(bPositions, "　金　額", nIdx)[1];
        printRectangles(isTanka ? START_X_HM_TANKA : START_X_HM_NORMAL, nStartHeight, nEndHeight);
        checkPageModeLength();
    }

    @Override
    protected int createHmInfo(HmefDat[] lstHmefDat, PrintImageList printImageList, SysfDat sysfDat, Map<Integer, HmefDat> mapHmefDat, boolean isTanka) {
        if (lstHmefDat == null) return 0;
        int nTax = 0;
        int nStartHeight;
        int nEndHeight;
        byte[] bPositions = isTanka ? MEISAI_POSITION_TANKA : MEISAI_POSITION_NORMAL;

        for (HmefDat hmefDat : lstHmefDat) {
            if (hmefDat == null || !hmefDat.isUsef() || hmefDat.getHmCode() < sysfDat.getSnvalue()) {
                continue; // 13.01.30
            }
            int nIdx = 0;
            // 日付
            nStartHeight = printFeedDot((byte)5)[0];
            if(hmefDat.getDenm() != 0) {
                printHPInfo(bPositions, (hmefDat.getDenm() < 10 ? " " + hmefDat.getDenm() : hmefDat.getDenm()) + "/"
                        + (hmefDat.getDend() < 10 ? " " + hmefDat.getDend() : hmefDat.getDend()), nIdx++);
            }
            else {
                printHPInfo(bPositions, " ", nIdx++);
            }
            // 品目
            String strHmname = hmefDat.getHmName();
            int nHmLength = isTanka ? 10 : 20;
            if(strHmname.getBytes(Charset.forName("Shift_JIS")).length > nHmLength){
                strHmname = OtherUtil.cutString(strHmname, nHmLength);
            }
            printHPInfo(bPositions, OtherUtil.getClearString(strHmname), nIdx++);
            // 数量
            if (hmefDat.getSuryo() != 0) {
                printHPInfo(bPositions, OtherUtil.printformat(hmefDat.getSuryo(), "###0.00", (byte) 2), nIdx++);
            }
            else {
                printHPInfo(bPositions, " ", nIdx++);
            }

            if(isTanka && hmefDat.getTanka() != 0){
                // 単価印字有り
                if(hmefDat.getTanka() % 100 == 0){
                    printHPInfo(bPositions, OtherUtil.printformat(hmefDat.getTanka(), "#,##0", (byte) 2), nIdx++);
                }
                else if(hmefDat.getTanka() % 10 == 0){
                    printHPInfo(bPositions, OtherUtil.printformat(hmefDat.getTanka(), "#,##0.0", (byte) 2), nIdx++);
                }
                else {
                    printHPInfo(bPositions, OtherUtil.printformat(hmefDat.getTanka(), "#,##0.00", (byte) 2), nIdx++);
                }
            }

            // 金額
            int nKin = hmefDat.getKin();
            if(!mapHmefDat.isEmpty()){
                // 軽減税率対応は税込み
                nKin += hmefDat.getTax();
            }
            String strKin = " " + OtherUtil.printformat("#,###,##0", nKin);
            HmefDat hmefDatKeigen = mapHmefDat.get(hmefDat.getKeigenKubun() * 1000 + hmefDat.getTaxR());
            if(hmefDatKeigen != null && hmefDatKeigen.getKeigenKubun() != 0){
                strKin += String.format(Locale.JAPAN, "*%d", hmefDatKeigen.getCusRec());
            }
            nEndHeight = printHPInfo(bPositions, strKin, nIdx)[1]; // 13.03.13

            nTax += hmefDat.getTax();

            // リース割賦の残回数を印字する。// 2008.07.28
            if ( hmefDat.getHbnmPrn() == 1 ) {
                printFeedDot((byte)3);

                nIdx = 0;
                strHmname = hmefDat.getHbName();
                if (!strHmname.contains("(  ")){
                    continue; // 残回数 '(  ' というパターンがなければ以下を除外する。
                }
                strHmname = strHmname.replace( "(  ", "(" );
                strHmname = strHmname.replace( "/ ", "/" );

                //伝票日付
                printHPInfo(bPositions, " ", nIdx++);
                //品目名
                if(strHmname.getBytes(Charset.forName("Shift_JIS")).length > 20){
                    strHmname = OtherUtil.cutString(strHmname, 20);
                }
                printHPInfo(bPositions, OtherUtil.getClearString(strHmname), nIdx++);
                //数量
                printHPInfo(bPositions, " ", nIdx++);
                if(isTanka){
                    // 単価
                    printHPInfo(bPositions, " ", nIdx++);
                }
                //金額
                nEndHeight = printHPInfo(bPositions, " ", nIdx)[1];
            }

            printRectangles(isTanka ? START_X_HM_TANKA : START_X_HM_NORMAL, nStartHeight - 1, nEndHeight);
        }
        return  nTax;
    }



    /**
     * 店舗情報の印刷データを作成する。
     *
     * @param hanfDat           [in] {@link HanfDat}   店舗データ
     * @param strTantname       [in] String 担当者名
     * @param sysfDat           [in] {@link SysfDat}    システムデータ
     * @param isTnumberPrint    [in] boolean            Tナンバー・インボイスコメント印字有無(true:印字する, false:印字しない)
     */
    public void createUserInfo(HanfDat hanfDat, String strTantname, SysfDat sysfDat, boolean isTnumberPrint) throws MException {
        int nStartHeight = printFeedDot((byte)5)[0];
        int nEndHeight;

        printNormal(hanfDat.getName());

        if (!OtherUtil.getClearString(hanfDat.getAdd1()).equals("")) {
            printNormal(hanfDat.getAdd1());
        }
        if (!OtherUtil.getClearString(hanfDat.getAdd2()).equals("")) {
            printNormal(hanfDat.getAdd2());
        }

        nEndHeight = printFeedDot((byte)10)[1];
        printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.BOLD);

        nStartHeight = printFeedDot((byte) 10)[0];
        printHanInfo("Tel " + hanfDat.getTel(), 0);
        printHanInfo("(担当)", 1);
        printHanInfo("Fax " + hanfDat.getFax(), 0);
        printHanInfo(strTantname, 1);
        nEndHeight = printFeedDot((byte)10)[1];
        printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.BOLD);

        nStartHeight = printFeedDot((byte)10)[0];
        nEndHeight = nStartHeight;
        String strBkinfo;
        if (OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkname_0()).trim()) > 0) {
            // 銀行１名称有り
            strBkinfo = OtherUtil.getClearString(hanfDat.getBkname_0()).trim();
            if (OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkshiten_0()).trim()) > 0) {
                // 銀行１支店名有り
                strBkinfo += "/" + OtherUtil.getClearString(hanfDat.getBkshiten_0()).trim();
            }
            nEndHeight = printNormal(strBkinfo)[1];
            strBkinfo = "";
            if (OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkkubun_0()).trim()) > 0) {
                // 銀行１区分有り
                strBkinfo = OtherUtil.getClearString(hanfDat.getBkkubun_0()).trim();
            }
            if (OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkban_0()).trim()) > 0) {
                // 銀行１口座番号有り
                if (strBkinfo.length() != 0) {
                    // 区分有りの場合
                    strBkinfo += "/";
                }
                strBkinfo += OtherUtil.getClearString(hanfDat.getBkban_0()).trim();
            }
            if (strBkinfo.length() != 0) {
                nEndHeight = printNormal(strBkinfo)[1];
            }
        }
        if (OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkname_1()).trim()) > 0) {
            // 銀行２名称有り
            strBkinfo = OtherUtil.getClearString(hanfDat.getBkname_1()).trim();
            if (OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkshiten_1()).trim()) > 0) {
                // 銀行２支店名有り
                strBkinfo += "/" + OtherUtil.getClearString(hanfDat.getBkshiten_1()).trim();
            }
            nEndHeight = printNormal(strBkinfo)[1];
            strBkinfo = "";
            if (OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkkubun_1()).trim()) > 0) {
                // 銀行２区分有り
                strBkinfo = OtherUtil.getClearString(hanfDat.getBkkubun_1()).trim();
            }
            if (OtherUtil.getBytesLen(OtherUtil.getClearString(hanfDat.getBkban_1()).trim()) > 0) {
                // 銀行２口座番号有り
                if (strBkinfo.length() != 0) {
                    // 区分有りの場合
                    strBkinfo += "/";
                }
                strBkinfo += OtherUtil.getClearString(hanfDat.getBkban_1()).trim();
            }
            if (strBkinfo.length() != 0) {
                nEndHeight = printNormal(strBkinfo)[1];
            }
        }
        if (nStartHeight != nEndHeight) {
            // 銀行情報を印字した場合は枠線を追加
            nEndHeight = printFeedDot((byte)10)[1];
            printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.BOLD);
        }
        // インボイス対応(Tナンバー・インボイスコメント印字)
        String strTnumber = OtherUtil.getClearString(hanfDat.getTnumber()).trim();
        if(isTnumberPrint && !strTnumber.isEmpty()) {
            // Tナンバー・インボイスコメント印字有り
            // Tナンバー設定有り
            // ⇒ 印字する
            nStartHeight = printFeedDot((byte)10)[0];
            printNormal("登録番号：T" + strTnumber);

            String strInvoiceComment = OtherUtil.getClearString(sysfDat.getInvoiceComment()).trim();
            if(!strInvoiceComment.isEmpty()) {
                for(String strCmt : OtherUtil.cutStringList(strInvoiceComment, 40)){
                    printNormal(strCmt);
                }
            }
            nEndHeight = printFeedDot((byte)10)[1];
            printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.BOLD);
        }

        checkPageModeLength();
    }


    /**
     * 消費税明細の印字データ生成.
     * <br>軽減税率対応(Sy2fDat.msyskeigen == 1)の場合は軽減税率区分、税率毎の消費税を印字.
     * <br>非対応の場合は明細の合計消費税額を印字.
     *
     * @param mapHmefDat    [in] {@code Map<Integer, HmefDat>}  軽減税率区分、税率毎の消費税金額
     * @param nTax          [in] int                            消費税金額
     * @param isTanka       [in] boolean                        単価印字フラグ
     */
    protected void createHmInfoTax(PrintImageList printImageList,
                                   Map<Integer, HmefDat> mapHmefDat,
                                   UserData userData,
                                   int nTax,
                                   boolean isTanka){
        byte[] bPositions = isTanka ? MEISAI_POSITION_TANKA : MEISAI_POSITION_NORMAL;
        int nIdx = 0;
        if(userData.getSy2fDat().getSyskeigen() == 0) {
            if (nTax != 0) {
                // 日付
                int nStartHeight = printFeedDot((byte) 5)[0];
                printHPInfo(bPositions, " ", nIdx++);
                // 品目
                printHPInfo(bPositions, "消費税", nIdx++);
                // 数量
                printHPInfo(bPositions, " ", nIdx++);
                if(isTanka){
                    printHPInfo(bPositions, " ", nIdx++);
                }
                // 金額
                String t_strkin = " ";
                int nEndHeight = printHPInfo(bPositions, " " + OtherUtil.printformat("#,###,##0", nTax), nIdx)[1]; // 13.03.13
                printRectangles(isTanka ? START_X_HM_TANKA : START_X_HM_NORMAL, nStartHeight - 1, nEndHeight);
                printLF(1);
            }
        }
        else if(mapHmefDat != null){
            // 軽減税率
            for(HmefDat hmefDat : mapHmefDat.values()){
                nIdx = 0;
                int nStartHeight = printFeedDot((byte)5)[0];
                printHPInfo(bPositions, " ", nIdx++);
                printHPInfo(bPositions, getHmefTaxKeigenTotal(hmefDat), nIdx++);
                printHPInfo(bPositions, " ", nIdx++);
                if(isTanka){
                    printHPInfo(bPositions, " ", nIdx++);
                }
                printHPInfo(bPositions, OtherUtil.printformat( "#,###,##0", hmefDat.getKin()) + ")", nIdx);

                nIdx = 0;
                printMeisaiInfo(" ", nIdx++);
                printMeisaiInfo(getHmefTaxkeigenTax(hmefDat), nIdx++);
                printMeisaiInfo(" ", nIdx++);
                if(isTanka){
                    printHPInfo(bPositions, " ", nIdx++);
                }
                int nEndHeight = printMeisaiInfo(OtherUtil.printformat("#,###,##0", hmefDat.getTax()) + ")", nIdx)[1];
                printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.NORMAL);
            }
            printLF(1);
        }
    }


    /**
     * コメント印字
     *
     * @param commentData [in] CommentData    コメントデータ
     * @throws MException 印刷データ作成時にエラーがあった場合発生
     */
    public void createComment(String[] commentData) throws MException {
        if (commentData.length == 0) return;

        int nStartHeight = printFeedDot((byte)5)[0];
        int nEndHeight = nStartHeight;
        for (int i = 0; i < commentData.length; i++) {
            if (commentData[i].length() == 0) continue;
            nEndHeight = printNormal(commentData[i])[1];
        }
        // 矩形設定
        if(nStartHeight != nEndHeight){
            nEndHeight = printFeedDot((byte)10)[1];
            printRectangle(0, RECT_WIDTH, nStartHeight, nEndHeight, STYLE.NORMAL);
        }
        printLF(1);
        checkPageModeLength();
    }

    @Override
    protected void createHmInfoFooter(PrintImageList printImageList, long lKin) {
    }
}
