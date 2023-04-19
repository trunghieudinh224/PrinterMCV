
package jp.co.MarutouCompack.baseClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.saneielec.libs.PrinterSDK;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import jp.co.MarutouCompack.Printer.BasePrintActivity;
import jp.co.MarutouCompack.Printer.R;
import jp.co.MarutouCompack.commonClass.GasRaterCom;
import jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysOption;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UserData;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * <p>タイトル: MGS</p>
 * <p>説明:</p>
 * <p>著作権: Copyright (c) 2011 Marutou Compack Co.,Ltd</p>
 * <p>会社名: Marutou Compack Co.,Ltd</p>
 * 
 * @author S.iimura
 */
@SuppressWarnings("unused")
public abstract class PrintBase {

    // For Debug
    // ****************************************************************
    // 定数
    // ****************************************************************
    /** プリンタのタイプ： SM1-21 */
    protected static final int PRINTER_TYPE_SM121 = 0;
    /** プリンタのタイプ： BLM-80 */
    protected static final int PRINTER_TYPE_BLM80 = 1;
    /** プリンタのタイプ： SM2-24 */
    protected static final int PRINTER_TYPE_SM224 = 2;

    /** 正常 */
    protected static final int STATUS_NORMAL = 0x00;
    /** 用紙切れ、または、カバーオープン */
    protected static final int STATUS_PAPER_END = 0x01;
    /** プリンタヘッドの温度異常 */
    protected static final int STATUS_TEMP_ERROR = 0x02;
    /** ローバッテリー */
    protected static final int STATUS_LOW_BATTERY = 0x04;
    /** ログ出力用タグ */
    private static final String TAG = PrintBase.class.getSimpleName();
    /** 圧縮構造のビットイメージ指定（非圧縮モード） */
    private static final byte[] prn_bmp_c10 = {
            0x1b, 0x2a, 0x10
    };
    /** 印字および紙送り */
    private static final byte[] prn_feed_24dot = {
            0x1b, 0x4a, 24
    };
    /** 印字および紙送り */
    private static final byte[] prn_feed_4lines = {
            0x1b, 0x64, 4
    };
    /** 終了コード */
    private static final byte[] prn_demo_last = {
            0x1b, '@'
    };
    /** 応答コマンド */
    private static final byte[] res = {
            0x1b, 0x76
    };
    /** 濃度を120%にする */
    private static final byte[] prn_120 = {
            0x1b, 0x59, 0x04
    };
    /** 開始横位置: 20 */
    protected static final int START_TAG = 15;
    /** 矩形横幅: 536 */
    protected static final int RECT_WIDTH = 546;
    /** 印刷画像データ名 */
    private static final String TEMP_FILE_NAME = "receipt.png";
    /** 印刷モード：画像 */
    public static final int PRINT_MODE_IMAGE = 0x01;
    /** 印刷モード：文字列 */
    public static final int PRINT_MODE_CHAR = 0x02;

    // --------------------------------------------------
    // 変数
    // --------------------------------------------------
    /** 印刷データ(画像) */
    private final ArrayList<Bitmap> mPrintDataBitmap;
    /** 印刷データ(キャラクタ) */
    private final ArrayList<Byte> mPrintData;
    /** 画像データ　 */
    private Bitmap mBitmap = null;
    /** 伝票の高さ */
    protected int mHeight = 0;
    /** プリンタポート */
    private String mPortName = null;
    /** プリンタポート設定 */
    private String mPortSetting = null;
    /** コンテキスト */
    protected Context mContext;

    /**
     * コンストラクタ
     *
     * @param ctx       [in] {@link Context}    呼び出し元コンテキスト
     */
    public PrintBase(Context ctx) {
        mContext = ctx;
        mPrintDataBitmap = new ArrayList<>();
        mPrintData = new ArrayList<>();
    }

    /**
     * ポート名を取得する
     * 
     * @return String ポート名
     */
    protected String getPortName() {
        return mPortName;
    }

    /**
     * ポート名を設定する
     * 
     * @param wkPortName    [in] String    設定するポート名
     */
    protected void setPortName(String wkPortName) {
        mPortName = wkPortName;
    }

    /**
     * ポート設定を取得する
     * 
     * @return String   ポート設定
     */
    protected String getPortSetting() {
        return mPortSetting;
    }

    /**
     * ポート設定を設定する
     * 
     * @param wkPortSetting [in] String 設定するポート設定
     */
    protected void setPortSetting(String wkPortSetting) {
        mPortSetting = wkPortSetting;
    }

    /**
     * ビットマップデータを登録する。
     * 
     * @param wkBitmap  [in] {@link Bitmap} 印刷する画像データ
     */
    protected void addPrintData(Bitmap wkBitmap) {
        if (wkBitmap != null) {
            mPrintDataBitmap.add(wkBitmap);
        }
    }

    /**
     * 印刷する文字列を格納する
     * 
     * @param wkStr [in] String 印刷する文字列
     */
    protected void addData(String wkStr) {
        addData(wkStr, true);
    }

    /**
     * 印刷する文字列を格納する。<br />
     * 改行量が0の場合は単純な改行(LF)を実施して、数値が設定されている場合は設定された数値を紙送りを実施する
     * 
     * @param wkStr [in] String 印刷する文字列
     */
    protected void addDataNoLF(String wkStr) {
        addData(wkStr, false);
    }

    /**
     * 印刷する文字列を格納する。<br />
     * 改行量が0の場合は単純な改行(LF)を実施して、数値が設定されている場合は設定された数値を紙送りを実施する<br />
     * 改行しないことを選択することも可能.
     *
     *  @param wkStr    [in] String     印刷する文字列
     * @param doLF      [in] boolean    改行フラグ(true: 改行する, false: 改行しない)
     */
    protected void addData(String wkStr, boolean doLF) {
        if (wkStr != null) {
            byte[] wkBytes;
            try {
                wkBytes = wkStr.getBytes("Shift_JIS");
                for (byte wkByte : wkBytes) {
                    mPrintData.add(wkByte);
                }
                if (doLF) { // 改行するかどうか？
                    addCommand(PrintCommand.commandLf());
                }
            }
            catch (UnsupportedEncodingException e) {
                MLog.WARN(mContext, TAG, e);
            }
        }
    }

    /**
     * コマンドを格納する
     * 
     * @param bCommands   [in] byte[] コマンド
     */
    protected void addCommand(byte[] bCommands) {
        if (bCommands == null) {
            return;
        }
        for (byte bCommand : bCommands) {
            mPrintData.add(bCommand);
        }
    }

    /**
     * コマンドを、コマンドリストの先頭に格納.
     * それまで格納していたコマンドを後ろにずらす.
     *
     * @param bCommans  [in] byte[] コマンド
     */
    protected void addCommandHead(byte[] bCommans){
        if(bCommans == null){
            return;
        }
        int nIdx = 0;
        for(byte bCommand : bCommans){
            mPrintData.add(nIdx++, bCommand);
        }
    }

    /**
     * コマンドを格納する。<br />
     * 単一byteのとき。
     * 
     * @param wkByte    [in] byte   コマンド
     */
    protected void addCommand(byte wkByte) {
        mPrintData.add(wkByte);
    }

    /**
     * 印刷領域を設定する
     * 
     * @param x         [in] int    印刷開始位置(左上)
     * @param y         [in] int    印刷開始位置(左上)
     * @param width     [in] int    印刷横幅
     * @param height    [in] int    印刷高さ
     */
    protected void setPrintArea(int x, int y, int width, int height) {
        byte[] wkBytes = PrintCommand.commandSetPrintArea(getLowLevel(x), getHighLevel(x), getLowLevel(y), getHighLevel(y), getLowLevel(width),
                getHighLevel(width), getLowLevel(height), getHighLevel(height));
        addCommand(wkBytes);
    }

    /**
     * 矩形を表示する
     * 
     * @param x         [in] int    横位置
     * @param y         [in] int    縦位置
     * @param width     [in] int    横幅
     * @param height    [in] int    高さ
     * @param weight    [in] int    線の太さ
     */
    protected byte[] createRectangle(int x, int y, int width, int height, byte weight) {
        return PrintCommand.commandSetLineBox(
                getLowLevel(x),
                getHighLevel(x),
                getLowLevel(y),
                getHighLevel(y),
                getLowLevel(width),
                getHighLevel(width),
                getLowLevel(height),
                getHighLevel(height),
                (byte) 0x01,
                weight);
    }

    /**
     * LowLevel側の数値を取得する<br />
     * 引数で与えられた数値を255で除算し、その余りを返す
     * 
     * @param wkNum [in] int    数値
     * @return byte 255で除算した余り
     */
    private byte getLowLevel(int wkNum) {
        int wkLowLevel = 0;
        if (wkNum > 0) {
            wkLowLevel = wkNum % 255;
        }
        return (byte)wkLowLevel;
    }

    /**
     * HighLevel側の数値を取得する<br />
     * 引数で与えられた数値を255で除算した結果を返す
     * 
     * @param wkNum [in] int    数値
     * @return byte 255で除算した結果
     */
    private byte getHighLevel(int wkNum) {
        int wkHighLevel = 0;
        if (wkNum > 0) {
            wkHighLevel = wkNum / 255;
        }
        return (byte)wkHighLevel;
    }

    /**
     * 印刷データをバイト配列に変換して返す
     * 
     * @return byte[] 実際の印刷データ
     */
    public byte[] getPrintExecData() {
        byte[] wkByteData = new byte[mPrintData.size()];
        for (int i = 0; i < wkByteData.length; i++) {
            wkByteData[i] = mPrintData.get(i);
        }
        return wkByteData;
    }

    /**
     * BTプリンタ接続開始.
     *
     * @throws MException   エラー時に発生
     */
    protected void connect() throws MException{
        int wkPrintSt; // プリンタステータス用
        int wkPrintType; // プリンタタイプ用

        // // プリンターに接続されていない時のみ再接続
        wkPrintSt = PrinterSDK.getStatus();
        if (wkPrintSt == -1) {
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_PRINT_FAILED), null); // 13.02.04
        }

        // プリンタ情報取得
        wkPrintType = PrinterSDK.getPrinterType();
        if (wkPrintType != PRINTER_TYPE_BLM80) {
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_OTHER_CONNECT), null);
        }

        // プリンタ初期化
        if (!PrinterSDK.initPrint()) {
            // 初期化失敗
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_SET_INFO), null);
        }

        // プリンタの状態を取得
        wkPrintSt = PrinterSDK.getStatus();
        if ((wkPrintSt & 0x01) != 0) {
            // 紙なし
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_COVER_OPEN), null);
        }
        if ((wkPrintSt & 0x02) != 0) {
            // 温度エラー
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_TEMP_ERROR), null);
        }
        if ((wkPrintSt & 0x04) != 0) {
            // ローバッテリー
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_LOW_BATTERY), null);
        }

        // 濃度を120%にする
        PrinterSDK.sendData(prn_120, prn_120.length);
    }

    /**
     * BTプリンタから切断.
     *
     * @throws MException   エラー時に発生
     */
    protected void disconnect() throws MException{
        // 紙送り=4行
        if (!PrinterSDK.sendData(prn_feed_4lines, prn_feed_4lines.length)) {
            // 入出力ストリーム、及びbluetoothを閉じる
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_SET_DATA), null);
        }

        // 印字コード終了コード送信
        if (!PrinterSDK.sendData(prn_demo_last, prn_demo_last.length)) {
            // 入出力ストリーム、及びbluetoothを閉じる
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_SET_DATA), null);
        }

        // ステータス取得命令送信
        PrinterSDK.getStatus();
        SharedPreferences wkSharedPreferences = mContext.getSharedPreferences("Printer", Context.MODE_PRIVATE);
        if (wkSharedPreferences.getBoolean(DEFINE.BLUETOOTH_OFF_FLG, false)) {
            PrinterSDK.btClose();
        }
    }

    /**
     * 印刷を実行<br />
     * PrintModeの値により画像印刷か文字列印刷か切り替えて印刷する<br />
     * 画像印刷の場合：PrintBase.PRINT_MODE_IMAGE<br />
     * 文字列印刷の場合：PrintBase.PRINT_MODE_CHAR<br />
     * をそれぞれ引数で渡す。<br />
     * 空の場合は画像印刷が優先される.
     * 
     * @param wkPrintMode   [in] int    印刷モード
     * @throws MException 印刷時にエラーがあった場合に発生
     */
    protected void printExecute(int wkPrintMode) throws MException {
        int wkPrintSt; // プリンタステータス用
        int wkPrintType; // プリンタタイプ用

        // // プリンターに接続されていない時のみ再接続
        wkPrintSt = PrinterSDK.getStatus();
        if (wkPrintSt == -1) {
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_PRINT_FAILED), null); // 13.02.04
        }

        // プリンタ情報取得
        wkPrintType = PrinterSDK.getPrinterType();
        if (wkPrintType != PRINTER_TYPE_BLM80) {
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_OTHER_CONNECT), null);
        }

        // プリンタ初期化
        if (!PrinterSDK.initPrint()) {
            // 初期化失敗
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_SET_INFO), null);
        }

        // プリンタの状態を取得
        wkPrintSt = PrinterSDK.getStatus();
        if ((wkPrintSt & 0x01) != 0) {
            // 紙なし
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_COVER_OPEN), null);
        }
        if ((wkPrintSt & 0x02) != 0) {
            // 温度エラー
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_TEMP_ERROR), null);
        }
        if ((wkPrintSt & 0x04) != 0) {
            // ローバッテリー
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_LOW_BATTERY), null);
        }

        // 濃度を120%にする
        PrinterSDK.sendData(prn_120, prn_120.length);

        MLog.DEBUG(mContext, TAG, "##########モバイルプリンタ：印刷データ転送開始");
        if (wkPrintMode == PRINT_MODE_CHAR) {
            // キャラクタ送信時はここを実行
            sendCharactorData();
        }
        else if (wkPrintMode == PRINT_MODE_IMAGE) {
            // 画像送信時は以下を実行
            sendImageData();
        }
        MLog.DEBUG(mContext, TAG, "##########モバイルプリンタ：印刷データ転送完了");

        // 紙送り=4行
        if (!PrinterSDK.sendData(prn_feed_4lines, prn_feed_4lines.length)) {
            // 入出力ストリーム、及びbluetoothを閉じる
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_SET_DATA), null);
        }

        // 印字コード終了コード送信
        if (!PrinterSDK.sendData(prn_demo_last, prn_demo_last.length)) {
            // 入出力ストリーム、及びbluetoothを閉じる
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_SET_DATA), null);
        }

        // ステータス取得命令送信
        PrinterSDK.getStatus();
        // 入出力ストリーム、及びbluetoothを閉じる
        SharedPreferences wkSharedPreferences = mContext.getSharedPreferences("Printer", Context.MODE_PRIVATE);
        if (wkSharedPreferences.getBoolean(DEFINE.BLUETOOTH_OFF_FLG, false)) PrinterSDK.btClose();
    }

    /**
     * 印刷を実行<br />
     * 画像モードで印刷を実行する。<br />
     * 詳細は{@link #printExecute(int)}を参照してください。
     * 
     * @throws MException 印刷実行時にエラーがあった場合に発生
     */
    public void printExecute() throws MException {
        printExecute(PRINT_MODE_IMAGE);
    }

    /**
     * 文字列データをbyte配列に格納してモバイルプリンタに送信する
     */
    protected void sendCharactorData() {
        byte[] wkSendByte = getPrintExecData();
        PrinterSDK.sendData(wkSendByte, wkSendByte.length);
        mPrintData.clear();
    }

    /**
     * Bitmapデータを初期化.
     */
    protected void clearBitmapDat(){
        mPrintDataBitmap.clear();
    }

    /**
     * イメージデータをbyte配列に格納してモバイルプリンタに送信する.
     *
     * @throws MException データ送信時にエラーがあった場合に発生
     */
    protected void sendImageData() throws MException {
        if(getBitmapHeight() == 0){
            return;
        }
        // 統合bitmapデータ作成
        if (!createBitmapData()) {
            // 作成失敗
            throw new MException(mContext.getString(R.string.ERROR_PRINT_PRINT_FAILED), null);
//            return;
        }
        //// BMPファイルを印字 -TOP- ////
        // データバッファ
        byte[] readBytes = bmp2byte();

        //// BMP情報と印字に必要な変数 -TOP- ////
        // 読み込んだBMPデータの横方向、縦方向のピクセル数とバイト数を取得
        int iSzPxX = mBitmap.getWidth();
        int iSzImgX = iSzPxX / 8;
        int iSzPxY = mBitmap.getHeight();
        byte[] cmd_width = new byte[1];

        // BMPデータの横方向のLONG数とバイト数
        int iSzLngX = (int) (Math.ceil((double) (iSzPxX) / 32.0));
        int iSzLngXByt = iSzLngX * 4;

        // BMPデータに含まれるパディングのバイト数
        int iSzPad = 0;

        if ((iSzImgX % 2) != 0) {
            iSzPad = 2 - (iSzImgX % 2);
        }

        // イメージの縦方向のライン数、最後の分割画像の画像部分のライン数を算出。
        // 分割画像のサイズ = 24ライン * （印字可能な最大の横方向のバイト数、または画像の横幅のバイト数）
        // 分割画像の単位で送信データを構成する。
        int iImgLine, iImgLastLine;
        iImgLine = Math.min(iSzPxY, 24);
        if ((iSzPxY % 24) == 0) {
            iImgLastLine = 24;
        }
        else {
            iImgLastLine = 24 - (iSzPxY % 24);
        }
        //// BMP情報と印字に必要な変数 -END- ////

        //// 印字用バッファ領域の生成 -TOP- ////
        // ページモード開始コマンド送信
        if(!PrinterSDK.sendData(PrintCommand.commandSetPageMode(), PrintCommand.commandSetPageMode().length)){
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_SET_DATA), null);
        }

        // 伝票印字領域設定
        int height = mBitmap.getHeight();
        byte[] wkBytes = PrintCommand.commandSetPrintArea(getLowLevel(0), getHighLevel(0), getLowLevel(0), getHighLevel(0), getLowLevel(576),
                getHighLevel(576), getLowLevel(height), getHighLevel(height));
        if(!PrinterSDK.sendData(wkBytes, wkBytes.length)){
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_SET_DATA), null);
        }
        
        // 宣言
        byte[] bImgBuf;

        // Y方向の画面分割数
        int iImgDiv = (int) (Math.ceil((double) (iSzPxY) / 24.0));

        // 実体の生成
        int iMaxWidth;

        if (iSzPxX > 576) {
            iMaxWidth = 72; // BLM-80
        }
        else {
            iMaxWidth = iSzImgX;
        }

        cmd_width[0] = (byte) iMaxWidth;

        bImgBuf = new byte[iImgLine * iMaxWidth];
        //// 印字用バッファ領域の生成 -END- ////

        //// 印字用バッファにBMPファイルから読み込んだデータを格納する -TOP- ////
        // ループカウンタ、その他
        int j, k, m, line, offset;
        int iCntD, iCntL, iCntW, iArrNum;
        k = iSzLngXByt - iSzPad;

        // 縦方向ループ（画像全体）
        for (iCntD = 0; iCntD < iImgDiv; iCntD++) {
            // 縦方向ループ（24ライン単位）
            for (iCntL = 0; iCntL < iImgLine; iCntL++) {
                line = (iSzPxY - ((iCntD * 24) + iCntL) - 1);
                offset = line * iSzLngXByt;

                // 横方向ループ（印字可能な最大の横方向のバイト数、または画像の横幅のバイト数）
                for (iCntW = 0; iCntW < iMaxWidth; iCntW++) {
                    j = offset + iCntW;
                    m = iCntL * iMaxWidth + iCntW;
                    // BMPに含まれる0x00パディングをスキップして、送信データを作る。
                    if (iCntW < k) {
                        // 最終ブロック？
                        if (iCntD == (iImgDiv - 1)) {
                            // 実データが無いライン
                            // 例えば、画像のサイズが768px*545pxの場合、必要な分割画面の数は23となり
                            // (545÷24=22余17)、最後の分割画面は17ライン分ほど画像データが不足する。
                            // 不足分は0x00(白)で埋めておく。
                            if (iCntL >= (24 - iImgLastLine)) {
                                bImgBuf[m] = 0x00;
                            }
                            else {
                                // 実データがあるライン
                                // １バイト毎にMSBとLSBを逆転する(コマンドリファレンスp22を参照)
                                // 例)
                                // 元データが"0xC5(= 11000101b)"であれば、"0xA3(= 10100011b)"に変換。
                                if (Objects.requireNonNull(readBytes)[j] < 0) {
                                    iArrNum = readBytes[j] + 256;
                                }
                                else {
                                    iArrNum = readBytes[j];
                                }
                                bImgBuf[m] = (byte) PrintBaseImage.bit_reverse[iArrNum];
                            }
                        }
                        else {
                            // 通常ライン
                            if (Objects.requireNonNull(readBytes)[j] < 0) {
                                iArrNum = readBytes[j] + 256;
                            }
                            else {
                                iArrNum = readBytes[j];
                            }
                            bImgBuf[m] = (byte) PrintBaseImage.bit_reverse[iArrNum];
                        }
                    }
                }
            }
            // BMPイメージの印字
            // イメージコマンド送信
            if (!PrinterSDK.sendData(prn_bmp_c10, prn_bmp_c10.length)) {
                // 入出力ストリーム、及びbluetoothを閉じる
                PrinterSDK.btClose();
                throw new MException(mContext.getString(R.string.ERROR_PRINT_SET_DATA), null);
            }

            // イメージコマンドの横バイト数を送信
            if (!PrinterSDK.sendData(cmd_width, cmd_width.length)) {
                // 入出力ストリーム、及びbluetoothを閉じる
                PrinterSDK.btClose();
                throw new MException(mContext.getString(R.string.ERROR_PRINT_SET_DATA), null);
            }

            // イメージデータ送信
            if (!PrinterSDK.sendData(bImgBuf, bImgBuf.length)) {
                // 入出力ストリーム、及びbluetoothを閉じる
                PrinterSDK.btClose();
                throw new MException(mContext.getString(R.string.ERROR_PRINT_SET_DATA), null);
            }

            // 印字ポインタを24ライン分送る
            if (!PrinterSDK.sendData(prn_feed_24dot, prn_feed_24dot.length)) {
                // 入出力ストリーム、及びbluetoothを閉じる
                throw new MException(mContext.getString(R.string.ERROR_PRINT_SET_DATA), null);
            }
        }
        //// 印字用バッファにBMPファイルから読み込んだデータを格納する -END- ////
        //// BMPファイルを印字 -END- ////
        // ページモードの印字とスタンダードモードへの復帰
        if(!PrinterSDK.sendData(PrintCommand.commandSwichStdMode(), PrintCommand.commandSwichStdMode().length)){
            PrinterSDK.btClose();
            throw new MException(mContext.getString(R.string.ERROR_PRINT_SET_DATA), null);
        }
    }

    /**
     * Bitmapデータ高さの取得.
     *
     * @return  int Bitmapデータの高さ
     */
    protected int getBitmapHeight(){
        int nHeight = 0;
        for(Bitmap bitmap : mPrintDataBitmap){
            nHeight += bitmap.getHeight();
        }
        return nHeight;
    }

    /**
     * 複数のビットマップデータを作成し、ファイルに保存する。
     * 
     * @return boolean 格納成否(true: 成功, false: 失敗)
     */
    private boolean createBitmapData() {
        Canvas wkCanvas = new Canvas();
        mBitmap = null;
        float wkYPos = 0;
        int wkHeight = 0;

        // 結合後のbitmapの高さを求める
        for (int i = 0; i < mPrintDataBitmap.size(); i++) {
            wkHeight += mPrintDataBitmap.get(i).getHeight();
        }
        MLog.INFO(mContext, TAG, "結合後のbitmapの高さ: " + wkHeight);
        if (wkHeight == 0) {
            return false;
        }
        // 結合後のbitmapを生成する
        mBitmap = Bitmap.createBitmap(PrintBaseImage.DEFAULT_X_SIZE, wkHeight, Bitmap.Config.RGB_565);
        wkCanvas.setBitmap(mBitmap);

        // Bitmap画像をCanvasに貼り付けていく
        for (int i = 0; i < mPrintDataBitmap.size(); i++) {
            wkCanvas.drawBitmap(mPrintDataBitmap.get(i), 0, wkYPos, null);
            wkYPos += mPrintDataBitmap.get(i).getHeight();
        }

        FileOutputStream fos = null;
        File wkFile = new File(DEFINE.getDataPath(mContext), TEMP_FILE_NAME);
        try {
            fos = new FileOutputStream(wkFile);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        }
        catch (IOException e) {
            MLog.ERROR(mContext, TAG, e);
            return false;
        }
        finally {
            if (fos != null) {
                try {
                    fos.close();
                }
                catch (Exception ioe){
                    MLog.WARN(mContext, TAG, ioe);
                }
            }
        }
        return true;
    }

    /**
     * ビットマップデータを白黒バイトデータに変換する
     * 
     * @return byte[] 白黒データに変換した画像データ
     */
    private byte[] bmp2byte() {
        if (mBitmap == null) {
            return null;
        }
        byte[] wkRawBytes;

        // 水平方向バイト数
        int wkCountX = mBitmap.getWidth() / 8;
        // 垂直方向バイト数
        int wkCountY = mBitmap.getHeight();

        wkRawBytes = new byte[wkCountX * wkCountY];
        int count = 0;

        for (int i = mBitmap.getHeight() - 1; i >= 0; i--) {
            byte wkByte = 0;
            for (int j = 0; j < mBitmap.getWidth(); j++) {
                int pixcel = mBitmap.getPixel(j, i);
                int sift = 7 - j % 8;

                int gray = (Color.red(pixcel) + Color.green(pixcel) + Color.blue(pixcel));

                wkByte |= (byte) (((gray != 0 ? 0 : 1) << sift) & 0xFF);
                if (sift == 0) {
                    wkRawBytes[count++] = wkByte;
                    wkByte = 0;
                }
            }
        }
        return wkRawBytes;
    }

    /**
     * ページモードを開始する
     */
    public void startPagemode() {
        addCommand(PrintCommand.commandSetPageMode());
    }

    /**
     * モードをページモードに変更して、印字エリアを設定する<br />
     * ページエリア外の時は無効となる。
     */
    protected void setPageArea() {
        // ページモードコマンド
        addCommandHead(PrintCommand.commandSetPageMode());
        // コマンドリストの頭に入れる
        // エリア指定
        byte[] wkBytes = PrintCommand.commandSetPrintArea(getLowLevel(0), getHighLevel(0), getLowLevel(0), getHighLevel(0), getLowLevel(576),
                getHighLevel(576), getLowLevel(mHeight), getHighLevel(mHeight));
        addCommandHead(wkBytes);
    }

    /**
     * 対象文字列に必要な分だけ全角スペースを追加する
     * 
     * @param wkStr         [in] String 印刷する文字列
     * @return String 全体の長さ分全角スペースを追加した文字列
     */
    protected String addSpaceToString(String wkStr) {
        StringBuilder wkStrBuilder = new StringBuilder();
        wkStrBuilder.append(wkStr);
        for (int i = 0; i < 20 - wkStr.length(); i++) {
            wkStrBuilder.append("　");
        }
        return wkStrBuilder.toString();
    }
    //Hieu
    /**
     * 売上明細の存在チェック.
     *
     * @param hmefDats          [in] HmefDat[]          明細一覧
     * @param sysfDat           [in] {@link SysfDat}    システムデータ
     * @param isIncludeNyuCho   [in] boolean            入金・調整を含めるか(true:含める, false:含めない)
     * @return  boolean 対象明細の存在有無(true:存在する, false:存在しない)
     */
    protected boolean isUriage(HmefDat[] hmefDats,
                               SysfDat sysfDat,
                               boolean isIncludeNyuCho){
        if(hmefDats == null){
            return false;
        }
        short sSnvalue = sysfDat.getSnvalue();
        for(HmefDat hmefDat : hmefDats){
            if(hmefDat.isUsef() && (hmefDat.getHmCode() >= sSnvalue || isIncludeNyuCho)){
                return true;
            }
        }
        return false;
    }



    //Hieu
    /**
     * 売上明細の存在チェックを実施
     * @param   hmefDat0    [in]    HmefDat[]   締前明細一覧
     * @param   hmefDat1    [in]    HmefDat[]   締後明細一覧
     * @param   hmefDat2    [in]    HmefDat[]   ハンディ明細一覧
     * @param   sysfDat     [in]    SysfDat     システム設定データ
     * @return  boolean true: 売上明細有り, false: 売上明細無し
     */
    protected boolean isUriage(HmefDat[] hmefDat0,
                               HmefDat[] hmefDat1,
                               HmefDat[] hmefDat2,
                               SysfDat sysfDat) {
        return isUriage(hmefDat0, sysfDat, false) || isUriage(hmefDat1, sysfDat, false) || isUriage(hmefDat2, sysfDat, false);
    }

//    /**
//     * 内税の計算を実施.
//     *
//     * @param wkKensinData  [in] {@link KensinData} 印刷データ
//     * @param isHybSeikyu   [in] boolean            ハイブリッド請求フラグ(true:有り, false:無し)
//     * @return  {@link TaxDat}  内税計算後消費税データ
//     */
//    protected TaxDat Calc_UchiZei(KensinData wkKensinData, boolean isHybSeikyu) {
//        //初期化
//        TaxDat wTaxdat = new TaxDat();
//        double flo;
//        int i;
//        int wk_taxr;
//
//        wTaxdat.mGUchiZei = 0;
//        wTaxdat.mUchiZei = 0;
//
//        SysfDat wkSysf = mUserData.getSysfDat();
//        Sy2fDat wkSys2f = mUserData.getSy2fDat();
//        KokfDat wkKokf = mUserData.getKokfDat();
//        Ko2fDat wkKo2f = wkKensinData.getKo2fDat();
//        HybfDat wkHybf = wkKensinData.getHybfDat();
//        GasfDat wkGasf = wkKensinData.getGasfDat();
//
//        //ガスの内税
//        if( wkGasf != null && wkGasf.getTaxDiv() == 2 )	{	//内税
//            wk_taxr = GasRaterCom.getKenTaxr( wkKokf, wkSysf, wkSysf.getTax_yy(),wkSysf.getTax_mm(),wkSysf.getTax_dd()
//                    , wkSysf.getConsumTax(), wkSysf.getTaxr_old(), wkSysf.getTaxr_new());
//            //2014.04.09 ハイブリッドでの内税額の対応
//            long wk_kin;
//            wk_kin = wkKokf.getFee();
//            if (isHybSeikyu && wkKo2f.getGashyb() > 0) {
//                if ( wkKo2f.getChoKin() != 0 || wkKo2f.getChoTax() != 0) {//値引きが発生しないときには、通常料金とする。
//                    //ハイブリッド料金
//                    wk_kin = wkKo2f.getNorKin();
//                    for ( int j = 0 ; j < Ko2fDat.getHyb_MAX() ; j++ ) {
//                        if ( wkHybf.mCusef[j] == 1 && wkKo2f.getFee()[j] != 0 ) {
//                            wk_kin += wkKo2f.getFee()[j];
//                        }
//                    }
//                }
//            }
//
//            flo = ((double)wk_kin * ( (double)wk_taxr ) ) / ( 1000. + (double)wk_taxr );
//            wTaxdat.mGUchiZei += (OtherUtil.hasCom( flo*1000., wkGasf.getTaxAdd(), wkGasf.getTaxMult() , 1000. )/1000.) ;
//        }
//
//        if(!wkSysf.isLtas()) {
//            // 販売データ
//            HmefDat[] wkHmefList = mUserData.getGetHmef0().toArray(new HmefDat[0]);
//            HmefDat[] wkHmefList1 = mUserData.getGetHmef1().toArray(new HmefDat[0]);
//            HmefDat[] wkHmefList2 = mUserData.getGetHmef2().toArray(new HmefDat[0]);
//            boolean isUriage;
//            try {
//                isUriage = isUriage(wkHmefList, wkHmefList1, wkHmefList2, wkSysf);
//
//                //販売明細の内税
//                if ((wkKokf.mUriSumi && isUriage) ||//売上済区分 0:未, 1:済み	かつ明細あり
//                        (wkKokf.mSimeF == 0 && wkKokf.mHme01Ken != 0L) ||    //フラグ締日処理(0:未 1:済)//販売明細(締前) ・件数
//                        (wkKokf.mSimeF == 1 && wkKokf.mHme00Ken != 0L)) {    //フラグ締日処理(0:未 1:済)//販売明細(締後) ・件数
//                    if (wkHmefList.length > 0) {
//                        wTaxdat.mUchiZei += calcUtaxHm(wkHmefList, wkSysf, wkSys2f);
//                    }
//                    if (wkHmefList1.length > 0) {
//                        wTaxdat.mUchiZei += calcUtaxHm(wkHmefList1, wkSysf, wkSys2f);
//                    }
//                    if (wkHmefList2.length > 0) {
//                        wTaxdat.mUchiZei += calcUtaxHm(wkHmefList2, wkSysf, wkSys2f);
//                    }
//                }
//            } catch (MException e) {
//                MLog.WARN(mContext, TAG, e);
//            }
//        }
//
//        return wTaxdat;	//正常終了
//    }

//    /**
//     * 販売明細情報の内税を作成する。
//     *
//     * @param wkHmef    [in] HmefDat[]      販売明細データ
//     * @param sysf      [in] SysfDat        システムデータ
//     * @throws MException 印刷データ作成時にエラーがあった場合に発生
//     */
//    private long calcUtaxHm(HmefDat[] wkHmef, SysfDat sysf, Sy2fDat sysf2) throws MException {
//        double flo = 0;
//        long wkUtax = 0;
//
//        if (wkHmef == null) {
//            return 0;
//        }
//        for (HmefDat wkHmefDat : wkHmef) {
//            if(!wkHmefDat.mUsef || wkHmefDat.mHmCode < sysf.mSnvalue ){
//                continue;
//            }
//            // 金額
//            if(wkHmefDat.mLeasKind == 1){
//                if (!sysf.mIfAlarm){
//                    continue;
//                }
//            } // リースsys_fhmcd05
//            else if (wkHmefDat.mHmCode == sysf.mHinCd9){
//                if (!sysf.mIfLampoil){
//                    continue;
//                }
//            } // 灯油
//            else if (wkHmefDat.mHmCode == sysf2.mSysfHbcd04){
//                if (!sysf.mIfDiv){
//                    continue;
//                }
//            } // 分割金
//            else if (wkHmefDat.mHmCode == sysf2.mSysfHmcd02){
//                if (!sysf.mIfProceeds){
//                    continue;
//                }
//            } // ガスｺｰﾄﾞ
//            else {
//                if (!sysf.mIfProceeds && wkHmefDat.mHmCode != sysf2.mSysfHmcd14 ){
//                    continue;
//                }
//            } // その他金額(遅収料金は必ず印字)
//            wkUtax += calcUtax(sysf, wkHmefDat);
//        }
//
//        return wkUtax;
//    }



    //Hieu
    /**
     * 内税料金の取得.
     *
     * @param sysfDat   [in] {@link SysfDat}    システムデータ
     * @param hmefDat   [in] {@link HmefDat}    販売明細データ
     * @return  int 内税金額
     * @throws MException   計算失敗時に発生
     */
    protected int calcUtax(SysfDat sysfDat,
                           HmefDat hmefDat) throws MException {
        int nUtax = 0;
        if ( hmefDat.getTaxKu() == 2 ) {	//内税
            double flo = ( (double)(hmefDat.getKin()) * ( (double)(hmefDat.getTaxR()) ) ) / (double)(hmefDat.getTaxR()+1000);
            //  商品マスタに端数処理が登録されていない時のみ、システムの端数処理を使用する。2012.09.26

            if (hmefDat.getShofDat().getFracAddTax() ==0 && hmefDat.getShofDat().getFracAddMult() ==0) {
                //　消費税：端数処理　システムをしようする。 Ver2.4.13 以前
                nUtax = (int)(OtherUtil.hasCom( flo*1000., (int)sysfDat.getFracAddTax(), (int)sysfDat.getFracMulTax(), 1000. )/1000. );
            } else {
                //　消費税：端数処理　　商品の端数処理を使用する。V2.4.14 以降
                nUtax = (int)(OtherUtil.hasCom( flo*1000., hmefDat.getShofDat().getFracAddTax(), hmefDat.getShofDat().getFracAddMult(), 1000. )/1000. );
            }
        }
        return nUtax;
    }

    /**
     * 1500ラインを超える印字エリアは印刷不可な為、1500ラインを超えた場合は<br>
     * 一旦データを送信する
     */
    protected void checkPageModeLength() throws MException{
        if(getBitmapHeight() > 1500){
            sendImageData();
            clearBitmapDat();
        }
    }

    //Hieu
    protected void createHmInfoLocal(jp.co.MarutouCompack.commonClass.PrinterDat.UserData userData) throws MException {
        // 販売データ
        jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[] hmefList = null;
        jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[] hmefList1 = null;
        jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[] hmefList2 = null;
        if (userData.getGetHmef0() != null) {
            hmefList = userData.getGetHmef0().toArray(new jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[0]);
        }
        if (userData.getGetHmef1() != null) {
            hmefList1 = userData.getGetHmef1().toArray(new jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[0]);
        }
        if (userData.getGetHmef2() != null) {
            hmefList2 = userData.getGetHmef2().toArray(new jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[0]);
        }
        jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat sysfDat = userData.getSysfDat();
        boolean isTanka = userData.getSy2fDat().getSysOption()[SysOption.PRINT_TANKA.getIdx()] == 1;
        if (isUriage(hmefList, hmefList1, hmefList2, sysfDat)) { // 販売実績がある場合のみ印刷する
            PrintImageList t_lst = new PrintImageList(mContext);
            createHmInfoHeader("<<当月お買上げ明細>>", t_lst, isTanka);
            int nTax = 0;
            Map<Integer, jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat> mapHmefDat = new TreeMap<>();
            calcKeigen(mapHmefDat, hmefList, userData);
            calcKeigen(mapHmefDat, hmefList1, userData);
            calcKeigen(mapHmefDat, hmefList2, userData);
            if (hmefList != null && hmefList.length > 0) {
                nTax = createHmInfo(hmefList, t_lst, sysfDat, mapHmefDat, isTanka);
            }
            if (hmefList1 != null && hmefList1.length > 0) {
                nTax += createHmInfo(hmefList1, t_lst, sysfDat, mapHmefDat, isTanka);
            }
            if (hmefList2 != null && hmefList2.length > 0) {
                createHmInfo(hmefList2, t_lst, sysfDat, mapHmefDat, isTanka);
            }
            createHmInfoTax(t_lst, mapHmefDat, userData, userData.getKokfDat().getUriTax() + nTax, isTanka);
            addPrintData(t_lst.getBitmap());
            t_lst.clear();
        }
        checkPageModeLength();
    }

//    /**
//     * 明細が印字対象か確認.
//     *
//     * @param hmefDat   [in] {@link HmefDat}    売上明細
//     * @param sysfDat   [in] {@link SysfDat}    システムデータ
//     * @param sy2fDat   [in] {@link Sy2fDat}    システム2データ
//     * @return  boolean true:無効明細, false:有効明細
//     */
//    private boolean isValidMeisai(@NonNull HmefDat hmefDat, SysfDat sysfDat, Sy2fDat sy2fDat){
//        return !(!hmefDat.mUsef ||                                                       // 使用フラグ:未使用
//                (hmefDat.mDenKind == 1 && !sysfDat.mIfAdjust) ||                    // 伝票区分:入金, 入金調整印字フラグ:false
//                (hmefDat.mDenKind == 3 && !sysfDat.mIfAdjust) ||                    // 伝票区分:調整, 入金調整印字フラグ:false
//                (hmefDat.mLeasKind == 1 && !sysfDat.mIfAlarm) ||                    // リース明細フラグ:対象, 警報器リース印字フラグ:false
//                (hmefDat.mHmCode == sysfDat.mHinCd9 && !sysfDat.mIfLampoil) ||      // 灯油, 灯油印字フラグ:false
//                (hmefDat.mHmCode == sy2fDat.mSysfHmcd04 && !sysfDat.mIfDiv) ||      // 分割金, 分割金印字フラグ:false
//                (hmefDat.mHmCode == sy2fDat.mSysfHmcd02 && !sysfDat.mIfProceeds) || // ガス, その他売上印字フラグ:false
//                !sysfDat.mIfProceeds ||                                             // その他売上印字フラグ:false
//                hmefDat.mLnkZan == 0);                                              // 入金済み
//    }

    //Hieu
    /**
     * 未入金明細の印字.
     *
     * @param userData  [in] {@link UserData}   ユーザーデータ
     * @throws MException   エラー時に発生
     */
    protected void createHmInfoLocalLtas(UserData userData) throws MException {
//        // 本日お買上明細
//        HmefDat[] hmefList2 = mUserData.getHmef(InputDat.ENUM_HMEF_TYPE.HT);
//        SysfDat sysfDat = mUserData.getSysfDat();
//        boolean isTanka = mUserData.getSy2fDat().mSysOption[SysOption.PRINT_TANKA.getIdx()] == 1;
//        Map<Integer, HmefDat> mapHmefDat = new TreeMap<>();
//        if (isUriage(null, null, hmefList2, sysfDat)) { // 販売実績がある場合のみ印刷する
//            PrintImageList t_lst = new PrintImageList(mContext);
//            createHmInfoHeader("<<本日お買上げ明細>>", t_lst, isTanka);
//            calcKeigen(mapHmefDat, hmefList2);
//            if (hmefList2.length > 0) {
//                createHmInfo(hmefList2, t_lst, sysfDat, mapHmefDat, isTanka);
//            }
////            createHmInfoTax(t_lst, mapHmefDat, mUserData.getKokfDat().mUriTax, isTanka);
//            //comment lại vì đã xóa hàm
//            addPrintData(t_lst.getBitmap());
//            t_lst.clear();
//        }
//        checkPageModeLength();
//
//        // 未入金明細
//        Sy2fDat sy2fDat = mUserData.getSy2fDat();
//        // 依頼中の前残非表示時は請求月の明細のみ印字
//        boolean isFuriDemand = GasRaterCom.isFuriDemand(mUserData.getSysfDat(), sy2fDat, mUserData.getKokfDat());
//        List<HmefDat> lstHmefDat = new ArrayList<>();
//        HmefDat[] lstHmefZan = mUserData.getHmef(InputDat.ENUM_HMEF_TYPE.ZAN);
//        if(!isFuriDemand && isUriage(lstHmefZan, sysfDat, false)) {
//            // 過去明細の件数を数える
//            int nCnt = 0;
//            HmefDat hmefDatOver = new HmefDat();
//            hmefDatOver.mUsef = false;
//            hmefDatOver.mHmName = "＊以前の残高＊";
//            hmefDatOver.mHmCode = 9999;
//            HmefDat[] lstHmefDatZan = mUserData.getHmef(InputDat.ENUM_HMEF_TYPE.ZAN);
//            for(int i = lstHmefDatZan.length - 1; i >= 0; i--){
//                HmefDat hmefDat = lstHmefDatZan[i];
//                if(isValidMeisai(hmefDat, sysfDat, sy2fDat)){
//                    nCnt++;
//                    if(nCnt <= 10){
//                        // 10明細以下は印字
//                        lstHmefDat.add(hmefDat);
//                    }
//                    else {
//                        // 11明細以上はまとめる
//                        hmefDatOver.mUsef = true;
//                        hmefDatOver.mKin += hmefDat.mLnkZan;
//                        continue;
//                    }
//                    checkUchikin(hmefDat);
//                }
//            }
//            if(hmefDatOver.mUsef) {
//                lstHmefDat.add(hmefDatOver);
//            }
//        }
//        Collections.reverse(lstHmefDat);
//        lstHmefZan = lstHmefDat.toArray(new HmefDat[0]);
//        HmefDat[] lstHmefDatBefore = getCheckMeisai(mUserData.getHmef(InputDat.ENUM_HMEF_TYPE.BEFORE), sysfDat, sy2fDat);
//        HmefDat[] lstHmefDatAfter = getCheckMeisai(mUserData.getHmef(InputDat.ENUM_HMEF_TYPE.AFTER), sysfDat, sy2fDat);
//
//        if(isUriage(lstHmefZan, lstHmefDatBefore, lstHmefDatAfter, sysfDat)) {
//            PrintImageList t_lst = new PrintImageList(mContext);
//            createHmInfoHeader("<<残高明細>>", t_lst, isTanka);
//            // 税率毎明細データ
//            mapHmefDat = new TreeMap<>();
//            calcKeigen(mapHmefDat, lstHmefZan);
//            calcKeigen(mapHmefDat, lstHmefDatBefore);
//            calcKeigen(mapHmefDat, lstHmefDatAfter);
//
//            if (lstHmefZan.length > 0) {
//                createHmInfo(lstHmefZan, t_lst, sysfDat, mapHmefDat, isTanka);
//            }
//            if(lstHmefDatBefore.length > 0){
//                createHmInfo(lstHmefDatBefore, t_lst, sysfDat, mapHmefDat, isTanka);
//            }
//            if(lstHmefDatAfter.length > 0){
//                createHmInfo(lstHmefDatAfter, t_lst, sysfDat, mapHmefDat, isTanka);
//            }
////            createHmInfoTax(t_lst, mapHmefDat, 0, isTanka);
//            //comment lại vì đã xóa hàm
//            addPrintData(t_lst.getBitmap());
//            t_lst.clear();
//        }
//        checkPageModeLength();
    }

//    private void checkUchikin(HmefDat hmefDat){
//        if(hmefDat.mLnkZan != hmefDat.mKin + hmefDat.mTax){
//            // 内金あり・・・残金を金額に設定、内税とする
//            hmefDat.mKin =hmefDat.mLnkZan;
//            hmefDat.mTax = 0;
//            if(hmefDat.mTaxKu == 3){
//                hmefDat.mTaxKu = 2;
//            }
//        }
//    }
//
//    /**
//     * 有効明細一覧の取得.
//     *
//     * @param lstHmefDatSrc [in] HmefDat[]          対象明細一覧
//     * @param sysfDat       [in] {@link SysfDat}    システムデータ
//     * @param sy2fDat       [in] {@link Sy2fDat}    システム2データ
//     * @return  HmefDat[]   有効明細一覧
//     */
//    private HmefDat[] getCheckMeisai(HmefDat[] lstHmefDatSrc, SysfDat sysfDat, Sy2fDat sy2fDat){
//        List<HmefDat> lstHmefDatDst = new ArrayList<>();
//        if(isUriage(lstHmefDatSrc, sysfDat, false)){
//            for(HmefDat hmefDat : lstHmefDatSrc){
//                if(isValidMeisai(hmefDat, sysfDat, sy2fDat)){
//                    // 印字有効明細
//                    checkUchikin(hmefDat);
//                    lstHmefDatDst.add(hmefDat);
//                }
//            }
//        }
//        return lstHmefDatDst.toArray(new HmefDat[0]);
//    }

    /**
     * 売上明細ヘッダー印字処理.
     *
     * @param strTitle          [in] String                 タイトル
     * @param printImageList    [in] {@link PrintImageList} 印字データ格納先(画像モード時に使用)
     * @param isTanka           [in] boolean                単価印字フラグ(true:印字する, false:印字しない)
     */
    protected abstract void createHmInfoHeader(String strTitle, PrintImageList printImageList, boolean isTanka);

    //Hieu
    /**
     * 売上明細印字処理.
     *
     * @param lstHmefDat        [in] HmefDat[]                      印字明細一覧
     * @param printImageList    [in] {@link PrintImageList}         印字データ格納先(画像モード時に使用)
     * @param sysfDat           [in] {@link SysfDat}                システムデータ
     * @param mapHmefDat        [in] {@code Map<Integer, HmefDat>}  軽減税率データ
     * @param isTanka           [in] boolean                        単価印字フラグ(true:印字する, false:印字しない)
     * @return  int 消費税額
     */
    protected abstract int createHmInfo(jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[] lstHmefDat,
                                        PrintImageList printImageList,
                                        jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat sysfDat, Map<Integer, jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat> mapHmefDat,
                                        boolean isTanka);


    //Hieu
    /**
     * 消費税明細印字処理.
     *
     * @param printImageList    [in] {@link PrintImageList}         印字データ格納先(画像モード時に使用)
     * @param mapHmefDat        [in] {@code Map<Integer, HmefDat>}  税率ごと消費税額データ
     * @param nTax              [in] int                            消費税額
     * @param isTanka           [in] boolean                        単価印字フラグ(true:印字する, false:印字しない)
     */
    protected abstract void createHmInfoTax(PrintImageList printImageList,
                                            Map<Integer, jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat> mapHmefDat,
                                            jp.co.MarutouCompack.commonClass.PrinterDat.UserData userData,
                                            int nTax,
                                            boolean isTanka);


    //Hieu
    /**
     * 軽減税率の計算処理.
     *
     * @param mapHmefDat    [in] {@code Map<Integer, HmefDat>}  軽減税率マップ
     * @param lstHmefDat    [in] HmefDat[]                      販売明細一覧
     * @throws MException   計算エラー時に発生
     */
    protected void calcKeigen(Map<Integer, jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat> mapHmefDat,
                              jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[] lstHmefDat,
                              jp.co.MarutouCompack.commonClass.PrinterDat.UserData userData) throws MException{
        if(lstHmefDat == null){
            return;
        }
        if(mapHmefDat == null) {
            mapHmefDat = new TreeMap<>();
        }
        if(userData.getSy2fDat().getSyskeigen() == 1){
            // ハンディ明細
            addKeigenTax(userData.getSysfDat(), lstHmefDat, mapHmefDat);
        }
    }


    //Hieu
    /**
     * 売上明細を税率毎に税率、金額のmapに格納.
     *
     * @param sysfDat       [in] {@link SysfDat}                    システムデータ
     * @param hmefDats      [in] HmefDta[]                          売上明細一覧
     * @param mapHmefDat    [in/out] {@code Map<Integer, Hmefdat>}  軽減税率区分、税率毎の消費税金額
     * @throws MException   エラー時に発生
     */
    private void addKeigenTax(jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat sysfDat,
                              jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[] hmefDats,
                              Map<Integer, jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat> mapHmefDat) throws MException{
        int nIdx = 1;
        for (jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat hmefDat : hmefDats) {
            if(!hmefDat.isUsef() || hmefDat.getHmCode() < sysfDat.getSnvalue() || hmefDat.getTaxKu() < 2 || hmefDat.getTaxR() == 0){
                continue;
            }
            setKeigenKubun(hmefDat, sysfDat);
            int nKubun = hmefDat.getKeigenKubun();
            if(nKubun == 0){
                nKubun = 3;
            }
            int nKey = nKubun * 1000 + hmefDat.getTaxR();
            jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat hmefDatKeigen = mapHmefDat.get(nKey);
            if (hmefDatKeigen == null) {
                hmefDatKeigen = new jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat();
                if(hmefDat.getKeigenKubun() != 0) {
                    hmefDatKeigen.setCusRec(nIdx++);
                }
                mapHmefDat.put(nKey, hmefDatKeigen);
            }
            hmefDatKeigen.setKeigenKubun(hmefDat.getKeigenKubun());
            hmefDatKeigen.setKin(hmefDatKeigen.getKin() + hmefDat.getKin() + hmefDat.getTax());
            if(hmefDat.getTaxKu() == 2){
                // 内税
                hmefDatKeigen.setTax(hmefDatKeigen.getTax() + calcUtax(sysfDat, hmefDat));
            }
            else {
                hmefDatKeigen.setTax(hmefDatKeigen.getTax() + hmefDat.getTax());
            }
            hmefDatKeigen.setTaxR(hmefDat.getTaxR());
        }

    }



    //Hieu
    /**
     * 売上明細データに軽減区分を設定.
     *
     * @param hmefDat   [in/out] {@link HmefDat}    売上データ
     * @param sysfDat   [in] {@link SysfDat}        システムデータ
     */
    private void setKeigenKubun(jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat hmefDat,
                                jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat sysfDat){
        if(hmefDat.getKeigenKubun() != 0 || hmefDat.getTaxKu() < 2){
            // 軽減税率区分設定済み or 税区分が未設定、非課税
            // 何もしない
            return;
        }
        hmefDat.setKeigenKubun(getKeigenKubun(sysfDat, hmefDat.getTaxR()));
    }


    //Hieu
    /**
     * 軽減区分の取得.<br>
     * 消費税率から軽減税率区分の取得.
     *
     * @param sysfDat   [in] {@link SysfDat}    システムデータ
     * @param sTaxr     [in] short              消費税率
     * @return byte 軽減税率区分
     */
    protected byte getKeigenKubun(jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat sysfDat, short sTaxr){
        int nSysTaxr = OtherUtil.getUriTaxr(sysfDat.getTax_yy(), sysfDat.getTax_mm(), sysfDat.getTax_dd(),
                sysfDat.getSysYear(), sysfDat.getSysMonth(), (byte)1,
                sysfDat.getTaxr_new(), sysfDat.getTaxr_old(), sysfDat.getTaxr_new());
        if(sTaxr != nSysTaxr){
            // 軽減税率対象
            return 2;
        }
        return 0;
    }


    //Hieu
    /**
     * 軽減税率印字データの生成.
     *
     * @param hmefDat   [in] {@link HmefDat}    売上明細データ
     * @return String   軽減税率印字データ
     */
    protected String getHmefTaxKeigenTotal(jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat hmefDat){
        StringBuilder strTax = new StringBuilder();
        strTax.append("(");
        if(hmefDat.getTaxR() % 10 == 0){
            strTax.append(String.format(Locale.JAPAN, "%2d", hmefDat.getTaxR() / 10));
        }
        else {
            strTax.append(String.format(Locale.JAPAN, "%2d.%d", hmefDat.getTaxR() / 10, hmefDat.getTaxR() % 10));
        }
        strTax.append("%対象");
        switch (hmefDat.getKeigenKubun()){
            case 1: // 経過措置
                strTax.append("　経過措置");
                break;
            case 2: // 軽減税率
                strTax.append("　軽減税率");
                break;
        }
        if(hmefDat.getKeigenKubun() != 0) {
            strTax.append(String.format(Locale.JAPAN, "*%d", hmefDat.getCusRec()));
        }
        return strTax.toString();
    }

    //Hieu
    /**
     * 軽減税率消費税印字データの生成.
     *
     * @param hmefDat   [in] {@link HmefDat}    売上明細データ
     * @return String   軽減税率消費税額印字データ
     */
    protected String getHmefTaxkeigenTax(jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat hmefDat){
        StringBuilder strTax = new StringBuilder();
        strTax.append("(");
        if(hmefDat.getTaxR() % 10 == 0){
            strTax.append(String.format(Locale.JAPAN, "%2d", hmefDat.getTaxR() / 10));
        }
        else {
            strTax.append(String.format(Locale.JAPAN, "%2d.%d", hmefDat.getTaxR() / 10, hmefDat.getTaxR() % 10));
        }
        strTax.append("%消費税");
        return strTax.toString();
    }


//    /**
//     * 売上明細の印字処理.
//     *
//     * @param lstHmefDat    [in] {@code List<HmefDat>}  印字対象売上明細
//     * @throws MException   エラー時に発生
//     */
//    protected void createMeisaiInfoLocal(List<HmefDat> lstHmefDat) throws MException {
//        // 販売データ
//        SysfDat sysfDat = mUserData.getSysfDat();
//        boolean isUriage = isUriage(new HmefDat[0], new HmefDat[0], lstHmefDat.toArray(new HmefDat[0]), sysfDat);
//        if (isUriage) { // 販売実績がある場合のみ印刷する
//            boolean isTanka = mUserData.getSy2fDat().mSysOption[SysOption.PRINT_TANKA.getIdx()] == 1;
//            // 軽減税率データ
//            Map<Integer, HmefDat> mapHmefDat = new LinkedHashMap<>();
//            calcKeigen(mapHmefDat, lstHmefDat.toArray(new HmefDat[0]));
//            PrintImageList printImageList = new PrintImageList(mContext);
//            createHmInfoHeader("", printImageList, isTanka);
//            long lKin = 0L;
//            int nTax = 0;
//            for(HmefDat hmefDat : lstHmefDat){
//                if(hmefDat.mUsef && hmefDat.mHmCode >= sysfDat.mSnvalue){
//                    lKin += hmefDat.mKin;
//                    nTax += hmefDat.mTax;
//                }
//            }
////            createHmInfo(lstHmefDat.toArray(new HmefDat[0]), printImageList, sysfDat, mapHmefDat, isTanka);
//
////            createHmInfoTax(printImageList, mapHmefDat, nTax, isTanka);
//            //comment lại vì đã xóa hàm
//            createHmInfoFooter(printImageList, lKin + nTax);
//            addPrintData(printImageList.getBitmap());
//            printImageList.clear();
//        }
//        checkPageModeLength();
//    }

    /**
     * 売上明細フッター部分の印字内容生成.
     *
     * @param printImageList    [in] {@link PrintImageList} 画像モード時の印字データ格納先
     * @param lKin              [in] long                   金額
     */
    protected abstract void createHmInfoFooter(PrintImageList printImageList, long lKin);


    //Hieu
    /**
     * 集金時の売上明細印字処理.
     *
     * @param wkUserData    [in] {@link UserData}   アプリ共通データ
     * @throws MException   エラー時に発生
     */
    protected void createHmInfoShukin(jp.co.MarutouCompack.commonClass.PrinterDat.UserData wkUserData) throws MException {
        jp.co.MarutouCompack.commonClass.PrinterDat.KokfDat kokfDat = wkUserData.getKokfDat();
        jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat sysfDat = wkUserData.getSysfDat();
        jp.co.MarutouCompack.commonClass.PrinterDat.Sy2fDat sy2fDat = wkUserData.getSy2fDat();
        boolean isTanka = sy2fDat.getSysOption()[SysOption.PRINT_TANKA.getIdx()] == 1;
        // 販売データ
        jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[] hmefDatsZan = null;
        boolean isZanMeisai = false;
        if(sysfDat.isLtas()){
            //Hieu note khúc này chưa làm ra
//            hmefDatsZan = wkUserData.getHmef(InputDat.ENUM_HMEF_TYPE.ZAN);
            isZanMeisai = hmefDatsZan != null && isUriage(hmefDatsZan, sysfDat, true);
        }
        jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[] wkHmefList = null;
        if (wkUserData.getGetHmef0() != null || wkUserData.getGetHmef1() != null) {
           wkHmefList = (kokfDat.getSimeF() == 1 ? wkUserData.getGetHmef0() : wkUserData.getGetHmef1()).toArray(new jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[0]);
        }
        jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[] wkHmefList2 = null;
        if (wkUserData.getGetHmef2() != null) {
            wkHmefList2 = wkUserData.getGetHmef2().toArray(new jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[0]);
        }

        boolean isUriage = isUriage(wkHmefList, sysfDat, true) || isUriage(wkHmefList2, sysfDat, true) || kokfDat.getFee() > 0 || isZanMeisai;
        if (isUriage) { // 販売実績がある場合のみ印刷する
            PrintImageList printImageList = new PrintImageList(mContext);
            createHmInfoHeader("<<売上明細>>", printImageList, isTanka);
            Map<Integer, jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat> mapHmefDat = new TreeMap<>();
            calcKeigen(mapHmefDat, wkHmefList, wkUserData);
            calcKeigen(mapHmefDat, wkHmefList2, wkUserData);
            calcKeigen(mapHmefDat, hmefDatsZan, wkUserData);
            List<jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat> lstHmefDat = null;
            if(kokfDat.getFee() > 0){
                lstHmefDat = new ArrayList<>();
                // ガス料金有り⇒軽減税率チェック実施
                jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat hmefDatGas = new jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat();
                hmefDatGas.setUsef(true);
                hmefDatGas.setHmName("ガス使用量");
                hmefDatGas.setHmCode((short) 9999);
                hmefDatGas.setDeny(Short.valueOf(wkUserData.getKensinDate().substring(0,4)));
                hmefDatGas.setDenm(kokfDat.getKMonth());
                hmefDatGas.setDend(kokfDat.getKDate());
                hmefDatGas.setKin(kokfDat.getFee());
                hmefDatGas.setTax(kokfDat.getConTax());
                if (wkUserData.getGasfDat() != null) {
                    hmefDatGas.setTaxKu((byte)wkUserData.getGasfDat().getTaxDiv());
                } else {
                    hmefDatGas.setTaxKu((byte)0);
                }
                if(hmefDatGas.getTaxKu() > 1){
                    hmefDatGas.setTaxR((short) GasRaterCom.getKenTaxr(kokfDat, sysfDat, sysfDat.getTax_yy(), sysfDat.getTax_mm(), sysfDat.getTax_dd(), sysfDat.getConsumTax(), sysfDat.getTaxr_old(), sysfDat.getTaxr_new()));
                    if(getKeigenKubun(sysfDat, hmefDatGas.getTaxR()) > 0){
                        hmefDatGas.setKeigenKubun((byte) 1);
                    }
                }
                lstHmefDat.add(hmefDatGas);
                if(kokfDat.getReduce() > 0){
                    // 還元額有り
                    jp.co.MarutouCompack.commonClass.PrinterDat.ShofDat shofDat;
                    try {
                        shofDat = wkUserData.getShofDatKangen();
                    }
                    catch (Exception ex) {
                        MLog.ERROR(mContext, TAG, "取引区分ファイルの読込みに失敗: " + ex.getLocalizedMessage());
                        throw new MException(mContext.getResources().getString(R.string.ERROR_GASRATE_GET_SH2FILE), null);
                    }
                    jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat hmefDatKng = new jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat();
                    hmefDatKng.setUsef(true);
                    hmefDatKng.setHmCode(shofDat.getHinno());
                    hmefDatKng.setHmName(BasePrintActivity.mWebData.getTantname());
                    hmefDatKng.setDeny(Short.valueOf(wkUserData.getKensinDate().substring(0,4)));
                    hmefDatKng.setDenm(kokfDat.getKMonth());
                    hmefDatKng.setDend(kokfDat.getKDate());
                    hmefDatKng.setKin(kokfDat.getReduce());
                    hmefDatKng.setTax(kokfDat.getReduceTax());
                    hmefDatKng.setTaxKu(shofDat.getTaxKu());
                    if(hmefDatKng.getTaxKu() > 1){
                        hmefDatKng.setTaxR((short)GasRaterCom.getKenTaxr(kokfDat, sysfDat, shofDat.getTax_yy(), shofDat.getTax_mm(), shofDat.getTax_dd(), shofDat.getTaxR(), shofDat.getTaxr_old(), sysfDat.getTaxr_new()));
                        if(getKeigenKubun(sysfDat, hmefDatKng.getTaxR()) > 0){
                            hmefDatKng.setKeigenKubun((byte) 1);
                        }
                    }
                    lstHmefDat.add(hmefDatKng);
                }
                if(sysfDat.getKnebFlg() == 1) {
                    // 漢の値引き有り
                    for (jp.co.MarutouCompack.commonClass.PrinterDat.KnebDat knebDat : wkUserData.getLstKnebDat()) {
                        if (knebDat.getCode() > 0 &&  // 割引コード設定有
                                knebDat.getUmu() == 1 &&  // 割引フラグ有
                                knebDat.getRes() == 1 &&  // 割引実績有
                                knebDat.getKin() != 0) {  // 割引金額有
                            jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat hmefDatNeb = new jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat();
//                            WarifDat warifDat = InputDat.getWarifDat(mContext, knebDat.getCode());
//                            hmefDatNeb.setUsef(true);
//                            hmefDatNeb.setHmCode(warifDat.m_nHinHinno);
//                            hmefDatNeb.setHmName(warifDat.m_strHinName);
//                            hmefDatNeb.setDeny(Short.valueOf(wkUserData.getKensinDate().substring(0,4)));
//                            hmefDatNeb.setDenm(kokfDat.getKMonth());
//                            hmefDatNeb.setDend(kokfDat.getKDate());
//                            hmefDatNeb.setKin(knebDat.getKin());
//                            hmefDatNeb.setTax(knebDat.getTax());
//                            hmefDatNeb.setTaxKu(warifDat.m_nShoTaxku);
//                            if(hmefDatNeb.getTaxKu() > 1){
//                                hmefDatNeb.setTaxR((short)GasRaterCom.getKenTaxr(kokfDat, sysfDat, warifDat.m_sShoTax_yy, warifDat.m_bShoTax_mm, warifDat.m_bShoTax_dd, warifDat.m_nShoTaxr, warifDat.m_sShoTaxr_old, warifDat.m_sShoTaxr_new));
//                                if(getKeigenKubun(sysfDat, hmefDatNeb.getTaxR()) > 0){
//                                    hmefDatNeb.setKeigenKubun((byte) 1);
//                                }
//                            }
//                            lstHmefDat.add(hmefDatNeb);
                        }
                    }
                }
                calcKeigen(mapHmefDat, lstHmefDat.toArray(new jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[0]), wkUserData);
            }

            int nTax = 0;
            if(sysfDat.isLtas()){
                // 残高明細
                if(isZanMeisai){
                    nTax = createHmInfo(hmefDatsZan, printImageList, sysfDat, mapHmefDat, isTanka);
                }
            }
            else {
                // 前残
                int nPreBalance = GasRaterCom.readPrebalance(mContext, sysfDat, kokfDat, sy2fDat);
                if (nPreBalance != 0) {
                    jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat hmefDatZan = new jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat();
                    hmefDatZan.setUsef(true);
                    hmefDatZan.setHmCode((short) 9999);
                    if (kokfDat.getSimeF() == 0) {
                        hmefDatZan.setHmName("前月残高");
                        hmefDatZan.setKin(kokfDat.getPreBalance());
                    } else {
                        hmefDatZan.setHmName("前月繰越額");
                        hmefDatZan.setKin(GasRaterCom.calcSeikyu(mContext, sysfDat, kokfDat, sy2fDat, false));
                        hmefDatZan.setKin(hmefDatZan.getKin() - (kokfDat.getGUri2() + kokfDat.getUri2() + kokfDat.getTax2() - kokfDat.getNyu2() + kokfDat.getCho2()));
                    }

                    createHmInfo(new jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[]{hmefDatZan}, printImageList, sysfDat, mapHmefDat, isTanka);
                }
            }

            if (wkHmefList != null && wkHmefList.length > 0) nTax = createHmInfo(wkHmefList, printImageList, sysfDat, mapHmefDat, isTanka);
            if (lstHmefDat != null && !lstHmefDat.isEmpty()) nTax = createHmInfo(lstHmefDat.toArray(new jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat[0]), printImageList, sysfDat, mapHmefDat, isTanka);
            if (wkHmefList2 != null && wkHmefList2.length > 0) createHmInfo(wkHmefList2, printImageList, sysfDat, mapHmefDat, isTanka);

            //消費税
            createHmInfoTax(printImageList, mapHmefDat,  wkUserData,wkUserData.getKokfDat().getUriTax() + nTax, isTanka);
            addPrintData(printImageList.getBitmap());
            printImageList.clear();
            checkPageModeLength();
        }
        checkPageModeLength();
    }

    /**
     * 調整額名称の取得.
     *
     * @return String   調整額名称
     * @throws MException   エラー時に発生
     */
    protected String getChoTitle(UserData userData) throws MException {
//        Hieunote
//        String strChoTitle = "調整額";
//        SysfDat sysfDat = userData.getSysfDat();
//        Sy2fDat sy2fDat = userData.getSy2fDat();
//        if (sy2fDat != null && sy2fDat.getSysfHmcd13() != 0) {
//            if (userData.geth) {
//
//            }
//            strChoTitle = OtherUtil.cutStringSpace(mBusfDat_hmcd13.mName).trim();
//        }
//        return strChoTitle;
        return "";
    }
}
