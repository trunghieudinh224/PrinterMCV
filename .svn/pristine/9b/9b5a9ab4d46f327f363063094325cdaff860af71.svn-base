
package jp.co.MarutouCompack.baseClass;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.provider.DocumentFile;
import android.util.Log;
import android.util.SparseArray;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import jp.co.MarutouCompack.commonClass.PrinterDat.Sy2fDat;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.FileUtils;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * アプリ共通処理.
 */
@SuppressWarnings("unused")
public class OtherUtil {

    /** ログ出力用タグ */
    private static final String TAG = OtherUtil.class.getSimpleName();
    /** バックアップの履歴数 */
    private static final int BACKUP_LIMIT_NUM = 50;

    /**
     * モードで指定された形で日付を取得。<br />
     * mode = 0: yyyy/mm/dd<br />
     * mode = 1: mm/dd
     * 
     * @param date [in] {@link GregorianCalendar}   Date型の日付
     * @param mode [in] byte                        モード
     * @return 指定されたモードで整形された日付文字列
     */
    public static String DataFormat(GregorianCalendar date, byte mode) {
        String year = Integer.toString(date.get(GregorianCalendar.YEAR));
        String month = Integer.toString(date.get(GregorianCalendar.MONTH) + 1);
        String day = Integer.toString(date.get(GregorianCalendar.DAY_OF_MONTH));
        String ymd;

        if (date.get(GregorianCalendar.MONTH) + 1 < 10) {
            month = " " + month;
        }
        if (date.get(GregorianCalendar.DAY_OF_MONTH) < 10) {
            day = " " + day;
        }

        switch (mode) {
            case 0:
                ymd = year + "/" + month + "/" + day;
                break;
            case 1:
                ymd = month + "/" + day;
                break;
            default:
                ymd = "";
        }

        return ymd;
    }

    /**
     * 月と日から0無しでMM/dd形式で作成
     * 
     * @param month [in] long       月
     * @param day   [in] long       日
     * @param mask  [in] booelan    true: mm/dd, false: mm月dd日
     * @return String   整形された日付文字列
     */
    public static String DateFormat(long month, long day, boolean mask) {
        String _month = Long.toString(month);
        String _day = Long.toString(day);
        String ymd;
        if (month < 10) {
            _month = " " + _month;
        }
        if (day < 10) {
            _day = " " + _day;
        }
        if (mask) {
            ymd = _month + "/" + _day;
        }
        else {
            ymd = _month + "月" + _day + "日";
        }
        return ymd;
    }

    /**
     * 年と月と日から0無しでyyyy/mm/dd形式で作成
     * 
     * @param year  [in] int        年
     * @param month [in] int        月
     * @param day   [in] int        日
     * @param mask  [in] boolean    true: yyyy/mm/dd, false: yyyy年mm月dd日
     * @return  String  整形された日付文字列
     */
    public static String DateFormat(int year, int month, int day, boolean mask) {
        String _year = Integer.toString(year);
        String _month = Integer.toString(month);
        String _day = Integer.toString(day);
        String ymd;

        if (month < 10) {
            _month = " " + _month;
        }
        if (day < 10) {
            _day = " " + _day;
        }

        if (mask) {
            ymd = _year + "/" + _month + "/" + _day;
        }
        else {
            ymd = _year + "年" + _month + "月" + _day + "日";
        }

        return ymd;
    }

    /**
     * 年、月、日からDate型を作成します。
     * 
     * @param year  [in] int    年
     * @param month [in] int    月
     * @param day   [in] int    日
     * @return String   年月日から作成されたDate型
     */
    public static GregorianCalendar getDate(int year, int month, int day) {
        return new GregorianCalendar(year, month, day);
    }

    /**
     * 数値を指定したフォーマットで変換します。
     * 
     * @param value     [in] long   値
     * @param keta      [in] byte   値の桁数補正値
     * @return  String  フォーマット後の文字列
     */
    public static String Format(long value, byte keta) {
        double temp;
        temp = (double) value / Math.pow(10, keta);
        return Double.toString(temp);
    }

    /**
     * 金額のフォーマットで変換.
     *
     * @param value [in] long   値
     * @return  String  フォーマット後の文字列
     */
    public static String KingakuFormat(long value) {
        return KingakuFormatLocal("###,##0", value);
    }

    /**
     * 引数で与えられた値を指定フォーマットで変換.
     *
     * @param wkFormat  [in] String フォーマット
     * @param value     [in] long   値
     * @return  String  フォーマット後の文字列
     */
    public static String KingakuFormat(String wkFormat, long value) {
        return KingakuFormatLocal(wkFormat, value);
    }

    /**
     * 金額用フォーマット<br />
     * 正数の場合： ￥xxx,xxx<br />
     * 負数の場合： △xxx,xxx
     *
     * @param wkFormat  [in] String フォーマット
     * @param value     [in] long   金額
     * @return String   金額用に整形された金額
     */
    private static String KingakuFormatLocal(String wkFormat, long value) {
        String kingaku;
        DecimalFormat format;
        long temp = value;
        if (temp < 0) {
            temp = Math.abs(temp);
            format = new DecimalFormat("-" + wkFormat);
        }
        else {
            format = new DecimalFormat(wkFormat);
        }
        kingaku = format.format(temp);
        return kingaku;
    }

    /**
     * 指定フォーマットと倍数計算後の値を用いて、数値用文字列に変換します。
     * 
     * @param wkFormat  [in] String 指定フォーマット（小数点以下の文字列数を固定化したい場合は、0.## のように指定してください。）
     * @param wkValue   [in] double 倍数計算後の値
     * @param wkMulti   [in] byte   倍数値（wkValueの値を１倍＝０、１０倍＝－１、１０分の１倍＝１）
     * @return String   変換後文字列
     */
    private static synchronized String printformatLocal(String wkFormat, double wkValue, byte wkMulti) {
        if (wkFormat == null) return null;

        StringBuilder retBui = new StringBuilder();
        StringBuilder wkStr = new StringBuilder();

        byte wkIdx;
        byte wkLen;
        int wkCalcValue;
        byte wkCnt;
        String strTail;
        byte tailCnt = 0;

        try {
            wkStr.append(format(wkFormat, wkValue, wkMulti));
            wkLen = (byte) wkFormat.length();

            wkIdx = (byte) wkFormat.indexOf(".");
            // 極力インスタンスを生成しないようにする。// 2011.02.10
            if (wkFormat.contains("#")) {
                strTail = wkFormat.substring(wkIdx + 1).replaceAll("#", "");
            }
            else {
                strTail = wkFormat.substring(wkIdx + 1);
            }
            if (strTail.length() == 0) {
                // 小数点以下が全て # となっている。
                // 末尾に付与する空白文字列数を計算します。
                wkCalcValue = (int) (wkValue % calcMulti(wkMulti));
                if (wkCalcValue != 0) {
                    wkCnt = (byte) String.valueOf(wkCalcValue).length();
                    if (wkMulti <= wkCnt) {
                        if (wkCalcValue % 10 == 0) {
                            wkCnt--;
                        }
                        while (wkCalcValue % 10 != 0) {
                            wkCalcValue /= 10;
                        }
                        if (wkCalcValue < 0){
                            wkCnt--;
                        }
                        tailCnt = (byte) ((wkMulti > wkCnt) ? (wkMulti - wkCnt) : 0);
                    }
                }
                else {
                    tailCnt = (byte) (wkMulti + 1);
                }
                wkStr.append(createMultiString(" ", tailCnt));
            }
            for (short i = (short) wkStr.length(); i < wkLen; i++) {
                retBui.append(" "); // 先頭から半角空白を追加する。
            }
            retBui.append(wkStr); // 最後にフォーマットされた文字列を追加する。

        }
        catch (Exception e) {
            retBui = new StringBuilder();
        }

        return retBui.toString();
    }

    /**
     * 指定文字列を、指定回数分連続した文字列を作成します。
     * 
     * @param wkStr     [in] String 指定文字列
     * @param wkMulti   [in] int    指定回数
     * @return String   作成された文字列
     */
    public static synchronized String createMultiString(String wkStr, int wkMulti) {
        if (wkStr == null || wkMulti <= 0) return ""; // 余計にインスタンスを生成させないようにする。

        StringBuilder retBui = new StringBuilder();

        for (short i = 0; i < wkMulti; i++) {
            retBui.append(wkStr);
        }

        return retBui.toString();
    }

    /**
     * 指定値を１０倍数化します。
     * 
     * @param wkValue [in] double   倍計算したい値
     * @param wkMulti [in] byte     倍数値(wkValueの100倍値を取得したい場合は、2を設定します。)
     * @return double   指定値を倍数化された値
     */
    public static synchronized double calcMultiValue(double wkValue, byte wkMulti) {
        return wkValue * calcMulti(wkMulti);
    }

    /**
     * 引数の値分１０倍して返します。
     * 
     * @param wkMulti [in] byte 倍数値(100倍値を取得したい場合は、2を設定します。)
     * @return double   １０×倍数値(倍数値０の場合は１が返ります。)
     */
    public static synchronized double calcMulti(byte wkMulti) {
        return Math.pow(10.0d, wkMulti);
    }

    /**
     * 指定フォーマットと倍数計算後の値を用いて、数値用文字列に変換します。
     * 
     * @param wkFormat  [in] String 指定フォーマット
     * @param wkValue   [in] double 変換したい値
     * @param wkMulti   [in] byte   倍数値（wkValueの値を１倍＝０、１０倍＝－１、１０分の１倍＝１）
     * @return String   変換後文字列
     */
    private static synchronized String formatLocal(String wkFormat, double wkValue, byte wkMulti) {
        if (wkFormat == null) return null;

        String retStr = null;

        try {
            wkValue = calcMultiValue(wkValue, (byte) (wkMulti * -1)); // 倍数を逆算する。
            retStr = new DecimalFormat(wkFormat).format(wkValue);
        }
        catch (Exception e) {
            MLog.WARN(null, TAG, e);
        }

        return retStr;
    }

    /**
     * 数値(double型)を倍数変換して指定フォーマットに合わせて文字列に変換します。
     * 
     * @param wkFormat  [in] String 指定フォーマット
     * @param wkValue   [in] double 変換したい数値(double型)
     * @param wkMulti   [in] byte   倍数値（wkValueの値を１倍＝０、１０倍＝－１、１０分の１倍＝１）
     * @return String   変換後文字列
     */
    public static synchronized String format(String wkFormat, double wkValue, byte wkMulti) {
        return formatLocal(wkFormat, wkValue, wkMulti);
    }

    /**
     * 数値(int型)を指定フォーマットに合わせた文字列を作成後、足りない桁数分先頭から半角空白を追加した文字列を取得します。
     * 
     * @param wkFormat  [in] String 指定フォーマット
     * @param wkValue   [in] int    変換したい数値(int型)
     * @return String   変換後文字列
     */
    public static synchronized String printformat(String wkFormat, int wkValue) {
        return printformatLocal(wkFormat, wkValue, (byte) 0);
    }
    
    /**
     * long型の数値を指定フォーマットに合わせた文字列を作成します。<br />
     * 作成後、足りない桁数分先頭から半角空白を追加した文字列を作成します。
     * @param wkFormat  [in]    String  フォーマット
     * @param wkValue   [in]    long    数値
     * @return  String  変換後文字列
     */
    public static synchronized String printformat(String wkFormat, long wkValue){
        return printformatLocal(wkFormat, (double)wkValue, (byte)0);
    }

    /**
     * 数値(long型)を指定フォーマットに合わせた文字列を作成後、足りない桁数分先頭から半角空白を追加した文字列を取得します。
     * 
     * @param wkValue   [in] long   数値
     * @param wkFormat  [in] String フォーマット
     * @param wkMulti   [in] byte   倍数値（wkValueの値を１倍＝０、１０倍＝－１、１０分の１倍＝１）
     * @return  String  変換後文字列
     */
    public static synchronized String printformat(long wkValue, String wkFormat, byte wkMulti) {
        return printformatLocal(wkFormat, (double) wkValue, wkMulti);
    }

    /**
     * 数値(long型)を指定フォーマットに合わせた文字列に変換します。
     * 
     * @param wkFormat  [in] String 指定フォーマット
     * @param wkValue   [in] long   変換したい数値(long型)
     * @return String   変換後文字列
     */
    public static synchronized String format(String wkFormat, long wkValue) {
        return formatLocal(wkFormat, (double) wkValue, (byte) 0);
    }

    /**
     * 日付型を指定フォーマットに合わせた文字列に変換します。
     * 
     * @param wkFormat <p>
     *            指定フォーマット<br>
     *            指定文字列 : 変換結果 (以下の例は、[2000/01/02 14:25:36.789]をもとにしています。)<br>
     *            yyyy(年) : "2000"<br>
     *            yy (年2桁): "00"<br>
     *            YY (年平成): "12"<br>
     *            #Y (年平成): "#2"<br>
     *            MM (月) : "01"<br>
     *            #M (月) : " 1"<br>
     *            dd (日) : "02"<br>
     *            #d (日) : " 2"<br>
     *            hh (時12) : "02"<br>
     *            HH (時24) : "14"<br>
     *            mm (分) : "25"<br>
     *            ss (秒) : "36"<br>
     *            SSS (ﾐﾘ秒) : "789"
     *            </p>
     * @param wkDate    [in] {@link java.util.Date} 変換したい日付
     * @return String   日付の変換後文字列（変換処理でエラーが発生した場合は、""(空白文字列)を返します。）
     */
    public static synchronized String format(String wkFormat, java.util.Date wkDate) {
        String retStr;
        String wkStr_M;
        String wkStr_d;
        StringBuilder wkBui;
        String wkFormat2;

        try {
            if (wkFormat == null || wkDate == null) return "";
            // 極力インスタンスを生成しないように、毎回チェックする。// 2011.02.10
            wkFormat2 = wkFormat;
            if (wkFormat.contains("#Y")) wkFormat2 = wkFormat2.replaceAll("#Y", "yy");
            if (wkFormat.contains("YY")) wkFormat2 = wkFormat2.replaceAll("YY", "yy");
            if (wkFormat.contains("#M")) wkFormat2 = wkFormat2.replaceAll("#M", "MM");
            if (wkFormat.contains("#d")) wkFormat2 = wkFormat2.replaceAll("#d", "dd");
            if (wkFormat.contains("#h")) wkFormat2 = wkFormat2.replaceAll("#h", "hh");
            if (wkFormat.contains("#H")) wkFormat2 = wkFormat2.replaceAll("#H", "HH");
            if (wkFormat.contains("#m")) wkFormat2 = wkFormat2.replaceAll("#m", "mm");
            if (wkFormat.contains("#s")) wkFormat2 = wkFormat2.replaceAll("#s", "ss");
            retStr = new SimpleDateFormat(wkFormat2, Locale.JAPANESE).format(wkDate);

            // #M, #d の場合は、空白補正する。
            if (wkFormat.contains("#M") || wkFormat.contains("#d")) {
                int wkIdx_M = wkFormat.indexOf("#M"); // 月
                int wkIdx_d = wkFormat.indexOf("#d"); // 日
                wkStr_M = null;
                wkStr_d = null;

                if (wkIdx_M >= 0) { // 月変換
                    wkStr_M = retStr.substring(wkIdx_M, wkIdx_M + 2);
                    wkStr_M = printformat("#0", Integer.parseInt(wkStr_M));
                }

                if (wkIdx_d >= 0) { // 日変換
                    wkStr_d = retStr.substring(wkIdx_d, wkIdx_d + 2);
                    wkStr_d = printformat("#0", Integer.parseInt(wkStr_d));
                }

                wkBui = new StringBuilder();
                if (wkIdx_M >= 0 && wkIdx_d >= 0) {
                    // 月・日ともに変換
                    wkBui.append(retStr.substring(0, wkIdx_M));
                    wkBui.append(wkStr_M);
                    wkBui.append(retStr.substring(wkIdx_M + 2, wkIdx_d));
                    wkBui.append(wkStr_d);
                    wkBui.append(retStr.substring(wkIdx_d + 2));
                }
                else if (wkIdx_M >= 0) {
                    // 月のみ変換
                    wkBui.append(retStr.substring(0, wkIdx_M));
                    wkBui.append(wkStr_M);
                    wkBui.append(retStr.substring(wkIdx_M + 2));
                }
                else if (wkIdx_d >= 0) {
                    // 日のみ変換
                    wkBui.append(retStr.substring(0, wkIdx_d));
                    wkBui.append(wkStr_d);
                    wkBui.append(retStr.substring(wkIdx_d + 2));
                }
                retStr = wkBui.toString();
            }
            if (!wkFormat.contains("YYYY") && (wkFormat.contains("YY") || wkFormat.contains("#Y"))) {
                // 西暦年下２桁から平成変換する。
                int wkIdx_Y;
                if (wkFormat.contains("YY")) {
                    wkIdx_Y = wkFormat.indexOf("YY"); // 年
                }
                else {
                    wkIdx_Y = wkFormat.indexOf("#Y"); // 年
                }
                short wkY = Short.parseShort(retStr.substring(wkIdx_Y, wkIdx_Y + 2));
                if (wkY >= 70)
                    wkY += 1900;
                else
                    wkY += 2000;
                wkY -= 1988;
                String wkStr_Y;
                if (wkFormat.contains("YY")) {
                    wkStr_Y = printformat("00", wkY); // ０詰め
                }
                else {
                    wkStr_Y = printformat("#0", wkY); // 頭空白
                }
                wkBui = new StringBuilder();
                wkBui.append(retStr.substring(0, wkIdx_Y));
                wkBui.append(wkStr_Y);
                wkBui.append(retStr.substring(wkIdx_Y + 2));
                retStr = wkBui.toString();
            }
        }
        catch (Exception e) {
            retStr = null;
        }

        return retStr;
    }

    /**
     * バイト配列の指定位置からshort型の値を取得する。
     * 
     * @param data  [in] byte[] バイト配列
     * @param idx   [in] int    位置
     * @return short    値
     */
    public static short byteToShort(byte[] data, int idx) {
        int value;
        value = data[idx] & 0xff;
        value |= (data[idx + 1] & 0xff) << 8;
        return (short) value;
    }

    /**
     * バイト配列の指定位置からint型の値を取得する。
     * 
     * @param data  [in] byte[] バイト配列
     * @param idx   [in] int    位置
     * @return int  値
     */
    public static int byteToInt(byte[] data, int idx) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            value |= (data[idx + i] & 0xff) << 8 * i;
        }
        return value;
    }

    /**
     * バイト配列の指定位置からlong型の値を取得する。
     * 
     * @param data  [in] byte   バイト配列
     * @param idx   [in] int    位置
     * @return long 値
     */
    @SuppressWarnings("unused")
    public static long byteToLong(byte[] data, int idx) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= (long) (data[idx + i] & 0xff) << 8 * i;
        }
        return value;
    }

    /**
     * バイト配列の指定位置からdouble型の値を取得する。
     * 
     * @param data  [in] byte[] バイト配列
     * @param idx   [in] int    位置
     * @return double   値
     */
    public static double byteToDouble(byte[] data, int idx) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= (long) (data[idx + i] & 0xff) << 8 * i;
        }
        return Double.longBitsToDouble(value);
    }

    /**
     * バイト配列の指定位置からString型の文字列を取得する。
     * 
     * @param data  [in] byte[] バイト配列
     * @param idx   [in] int    位置
     * @param len   [in] int    長さ
     * @return  String  文字列
     */
    public static String byteToString(byte[] data, int idx, int len){
        return new String(data, idx, len, Charset.forName("shift_jis"));
    }

    /**
     * バイト配列をバイト配列に格納する。
     * 
     * @param bdat  [in/out] byte[] 格納先バイトデータ
     * @param idx   [in] int        書き込み開始インデックス
     * @param val   [in: byte[]     書き込むbyte配列
     */
    public static void bytedatSetBytes(byte[] bdat, int idx, byte[] val) {
        for (byte b : val) {
            bdat[idx++] = b;
        }
    }

    /**
     * short型の値をバイト配列に格納する。
     *  @param bdat  [in/out] byte[] 格納先バイトデータ
     * @param idx   [in]     int    書込み開始インデックス
     * @param val   [in]     short  書き込むshort値
     */
    public static void bytedatSetShort(byte[] bdat, int idx, short val) {
        for (int i = 0; i < 2; i++) {
            bdat[idx++] = (byte) (0xff & (val >> 8 * i));
        }
    }

    /**
     * int型の値をバイト配列に格納する。
     * 
     * @param bdat  [in/out] byte[] 格納先バイトデータ
     * @param idx   [in] int        書込み開始インデックス
     * @param val   [in] int        書き込むint値
     */
    public static void bytedatSetInt(byte[] bdat, int idx, int val) {
        for (int i = 0; i < 4; i++) {
            bdat[idx++] = (byte) (0xff & (val >> 8 * i));
        }
    }

    /**
     * long型の値をバイト配列に格納する
     * 
     * @param bdat  [in/out] byte[] 格納先バイトデータ
     * @param idx   [in] int        書込み開始インデックス
     * @param val   [in] long       書き込むlong値
     */
    public static void bytedatSetLong(byte[] bdat, int idx, long val) {
        for (int i = 0; i < 8; i++) {
            bdat[idx++] = (byte) (0xff & (val >> 8 * i));
        }
    }

    /**
     * double型の値をバイト配列に格納する
     * 
     * @param bdat  [in/out] byte[] 格納先バイトデータ
     * @param idx   [in] int        書込み開始インデックス
     * @param val   [in] double     書き込むdouble値
     */
    public static void bytedatSetDouble(byte[] bdat, int idx, double val) {
        long val_l = Double.doubleToLongBits(val);
        for (int i = 0; i < 8; i++) {
            bdat[idx++] = (byte) (0xff & (val_l >> 8 * i));
        }
    }

    /**
     * null値を文字数０の空白に変換します。
     * 
     * @param wkStr [in] String 文字列
     * @return String   変換後文字列
     */
    public static synchronized String nullToString(String wkStr) {
        return wkStr == null ? "" : wkStr;
    }

    /**
     * 指定文字列のバイト数を取得します。
     * 
     * @param wkStr [in] String バイト数を調べたい文字列
     * @return int  指定文字列のバイト数
     */
    public static synchronized int getBytesLen(String wkStr) {
        if (wkStr == null) return 0;
        // return wkStr.getBytes().length; // 正しい文字バイト数を取得できない。 // 2011.05.11

        int ret = 0;
        char ch;
        int len = wkStr.length();
        for (int i = 0; i < len; i++) {
            ch = wkStr.charAt(i);
            // Unicodeｶﾅの範囲以外は２バイト
            if ((ch > 0x0019 && ch < 0x0080) || (ch >= 0xFF61 && ch <= 0xFF9F)) {
                ret++;
            }
            else {
                ret += 2;
            }
        }
        return ret;
    }

    /**
     * 指定日付から、年を加減します。
     *
     * @param wkDate    [in] {@link java.util.Date} 指定日付
     * @param wkAdd     [in] int                    加減する年数
     * @return {@link java.util.Date}   年加減後の日付
     */
    @SuppressWarnings("unused")
    public static synchronized java.util.Date addDateY(java.util.Date wkDate, int wkAdd) {
        return addDate(Calendar.YEAR, wkDate, wkAdd);
    }

    /**
     * 指定日付から、月を加減します。
     *
     * @param wkDate    [in] {@link java.util.Date} 指定日付
     * @param wkAdd     [in] int                    加減する月数
     * @return {@link java.util.Date}   月加減後の日付
     */
    @SuppressWarnings("unused")
    public static synchronized java.util.Date addDateM(java.util.Date wkDate, int wkAdd) {
        return addDate(Calendar.MONTH, wkDate, wkAdd);
    }

    /**
     * 指定日付から、日を加減します。
     *
     * @param wkDate    [in] {@link java.util.Date} 指定日付
     * @param wkAdd     [in] int                    加減する日数
     * @return {@link java.util.Date}   日加減後の日付
     */
    @SuppressWarnings("unused")
    public static synchronized java.util.Date addDateD(java.util.Date wkDate, int wkAdd) {
        return addDate(Calendar.DATE, wkDate, wkAdd);
    }

    /**
     * 指定日付から、年月日どれかを指定数値分を加減します。
     * @param strYMD    [in] String                 日付加減をする年、月、日の文字( "Y", "M", "D" ) 複数が指定されていた場合はYMDの順で優先されます。
     * @param wkDate    [in] {@link java.util.Date} 指定日付
     * @param wkAdd     [in] int                    日付を加減する値
     * @return {@link java.util.Date}   加減後の日付(エラーがあった、または年月日文字列で無効文字が使われていた場合はnull)
     */
    @SuppressWarnings("unused")
    public static synchronized java.util.Date addDate(String strYMD, java.util.Date wkDate, int wkAdd) {
        java.util.Date retDate = null;
        String wkYMD;

        if (strYMD == null || wkDate == null) {
            return null;
        }

        // 極力インスタンスを生成しないようにする。// 2011.02.10
        wkYMD = strYMD.toUpperCase();
        if (wkYMD.contains("Y")) {
            retDate = addDate(Calendar.YEAR, wkDate, wkAdd);
        }
        else if (wkYMD.contains("M")) {
            retDate = addDate(Calendar.MONTH, wkDate, wkAdd);
        }
        else if (wkYMD.contains("D")) {
            retDate = addDate(Calendar.DATE, wkDate, wkAdd);
        }

        return retDate;
    }

    /**
     * 日付のうち、指定した項目について加減算を行う.
     *
     * @param wkYMD     [in] int
     * @param wkDate    [in] {@link java.util.Date} 対象日時データ
     * @param wkAdd     [in] int                    日付を加減する値
     * @return {@link java.util.Date}   加減後の日付(エラーがあった、または年月日文字列で無効文字が使われていた場合はnull)
     */
    private static synchronized java.util.Date addDate(int wkYMD, java.util.Date wkDate, int wkAdd) {
        java.util.Date retDate;
        Calendar calendar;

        if (wkDate == null) {
            return null;
        }

        calendar = Calendar.getInstance();
        calendar.setTime(wkDate);
        calendar.add(wkYMD, wkAdd);
        retDate = calendar.getTime();

        return retDate;
    }

    /**
     * ファイルコピーを行う
     * 
     * @param srcPath   [in] {@link File}   コピー元ファイルインスタンス
     * @param dstPath   [in] {@link File}   コピー先ファイルインスタンス
     * @throws IOException  入出力でエラーがあった場合に発生.
     */
    public static void fileCopy(File srcPath, File dstPath) throws IOException {
        FileChannel srcChannel = null;
        FileChannel dstChannel = null;

        try {
            srcChannel = new FileInputStream(srcPath).getChannel();
            dstChannel = new FileOutputStream(dstPath).getChannel();

            srcChannel.transferTo(0, srcChannel.size(), dstChannel);

        }
        catch (IOException ioe) {
            Log.e(TAG, "  " + ioe);
            throw ioe;
        }
        finally {
            if (srcChannel != null) {
                try {
                    srcChannel.close();
                }
                catch (IOException ioe) {
                    Log.w(TAG, "  " + ioe);
                }
            }
            if (dstChannel != null) {
                try {
                    dstChannel.close();
                }
                catch (IOException ioe) {
                    Log.w(TAG, "  " + ioe);
                }
            }
        }
    }

    /**
     * ファイルコピーの実施.
     *
     * @param context   [in] {@link Context}        呼び出し元コンテキスト.
     * @param os        [in] {@link OutputStream}   出力ストリーム
     * @param srcPath   [in] String                 コピー元パス
     * @throws IOException  入出力エラー時に発生
     */
    public static void fileCopy(Context context, OutputStream os, String srcPath) throws IOException {
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(srcPath);
            byte[] buffer = new byte[1024 * 4];
            int n;
            while((n = fis.read(buffer)) != -1){
                os.write(buffer, 0, n);
            }
        }
        finally {
            OtherUtil.closeInputStream(context, fis);
        }
    }

    /**
     * ファイルコピーを行う
     *
     * @param is        [in] {@link InputStream}    入力ストリーム
     * @param dstPath   [in] String                 コピー先ファイルパス
     * @param context   [in] {@link Context}        呼び出し元コンテキスト
     * @throws IOException  ファイアルコピー時エラーで発生
     */
    public static void fileCopy(InputStream is, String dstPath, Context context) throws IOException {
        try {
            File fileDst = new File(dstPath);
            if(fileDst.exists() && !fileDst.isDirectory()){
                if(!fileDst.delete()){
                    MLog.ERROR(context, TAG, "コピー先ファイルの削除に失敗");
                }
            }
            FileOutputStream fos = new FileOutputStream(fileDst);

            byte[] buffer = new byte[1024 * 4];
            int n;
            while ((n = is.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }
            fos.close();
        }
        catch (Exception e) {
            MLog.ERROR(context, TAG, e);
            throw e;
        }
    }

    /**
     * 日付文字列を指定フォーマットの型に合わせて作成し、日付型を取得します。
     * @param wkFormat  [in] String フォーマット
     * <p>
     * 指定フォーマット<br>
     * 指定文字列 : 変換結果 (以下の例は、"2000/01/02 14:25:36.789"をもとにしています。)<br>
     * yyyy(年)   : 2000<br>
     * yy  (年2桁): 00<br>
     * MM  (月)   : 01<br>
     * dd  (日)   : 02<br>
     * hh  (時12) : 02<br>
     * HH  (時24) : 14<br>
     * mm  (分)   : 25<br>
     * ss  (秒)   : 36<br>
     * SSS (ﾐﾘ秒) : 789
     * </p>
     * @param wkStr [in] String 日付文字列(例:"2000/01/02 14:25:36.789")
     * @return {@link java.util.Date}   指定フォーマットの型に合わせて作成された日付型
     */
    public static synchronized java.util.Date parseDate(String wkFormat, String wkStr) {
        if (wkFormat == null) {
            return null;
        }

        java.util.Date retDate = null;
        String wkFormat2;

        try {
            // 日付型を変換する。
            // 極力インスタンスを生成しないように、毎回チェックする。// 2011.02.10
            wkFormat2 = wkFormat;
            if (wkFormat.contains("#Y")) {
                wkFormat2 = wkFormat2.replaceAll("#Y", "yy");
            }
            if (wkFormat.contains("YY")) {
                wkFormat2 = wkFormat2.replaceAll("YY", "yy");
            }
            if (wkFormat.contains("#M")) {
                wkFormat2 = wkFormat2.replaceAll("#M", "MM");
            }
            if (wkFormat.contains("#d")) {
                wkFormat2 = wkFormat2.replaceAll("#d", "dd");
            }
            retDate = new SimpleDateFormat(wkFormat2, Locale.JAPANESE).parse(wkStr);
        }
        catch (Exception e) {
            MLog.WARN(null, TAG, e);
        }

        return retDate;
    }

    /**
     * 基準日付から日付を引いた差分日数を取得します。
     * @param wkDateA   [in] {@link java.util.Date} 基準日付
     * @param wkDateB   [in] {@link java.util.Date} 基準日付から引く日付
     * @return  int
     * <p>
     * 差分日数 = 基準日付 - 日付 = wkDateA - wkDateB<br>
     * 基準日付よりも日付が未来の場合は、差分日数はマイナス値となります。<br>
     * 日付のどちらかがnull値の場合は、差分日数は０となります。<br>
     * COMPACKのgetBetweenとは引数の順番が逆となります。
     * </p>
     */
    public static synchronized int betweenDays(java.util.Date wkDateA, java.util.Date wkDateB) {
        int retValue = 0;
        Calendar calendarA;
        Calendar calendarB;

        try {
            if (wkDateA == null || wkDateB == null) {
                return retValue;
            }

            // 基準日付
            calendarA = Calendar.getInstance();
            calendarA.setTime(wkDateA);
            calendarA.add(Calendar.HOUR, 0);
            calendarA.add(Calendar.MINUTE, 0);
            calendarA.add(Calendar.SECOND, 0);
            calendarA.add(Calendar.MILLISECOND, 0);

            // 基準日付から引く日付
            calendarB = Calendar.getInstance();
            calendarB.setTime(wkDateB);
            calendarB.add(Calendar.HOUR, 0);
            calendarB.add(Calendar.MINUTE, 0);
            calendarB.add(Calendar.SECOND, 0);
            calendarB.add(Calendar.MILLISECOND, 0);

            long wkValue = calendarA.getTime().getTime() - calendarB.getTime().getTime();
            wkValue /= 1000; // MILLISECOND
            wkValue /= 60; // SECOND
            wkValue /= 60; // MINUTE
            wkValue /= 24; // HOUR
            retValue = (int) wkValue;

        }
        catch (Exception e) {
            MLog.WARN(null, TAG, e);
        }

        return retValue;
    }

    /**
     * 文字列から\0と"　"(大文字スペース)を取り除く
     * 
     * @param wkStr [in] String 対象文字列
     * @return  String  取り除いたあとの文字列
     */
    public static String getClearString(String wkStr) {
        return nullToString(wkStr).replace('\0', ' ').replace('　', ' ').trim();
    }

    /**
     * 3桁毎の区切り(,)を取り除く
     * 
     * @param wkStr [in] String 対象文字列
     * @return  String  3桁毎","を取り除いた文字列
     */
    public static String getNumFromString(String wkStr) {
        String[] wkStrArray = getClearString(wkStr).split(",");
        StringBuilder wkStrBuilder = new StringBuilder();
        if (wkStrArray.length == 0) {
            wkStrBuilder.append(wkStr);
        }
        else {
            for (String s : wkStrArray) {
                wkStrBuilder.append(s);
            }
        }
        return wkStrBuilder.toString();
    }

    /**
     * 文字列が全角かなか確認する。<br />
     * 引数で渡された文字列を1文字ずつ分解し、全て全角かなか判定を行い結果を返す。
     * 
     * @param wkStr [in] String 確認を行う文字列
     * @return boolean  全て全角かなの場合はtrueを返し、そうでない場合はfalseを返す。
     */
    public static boolean isAllKana(String wkStr) {
        boolean ret = true;
        for (char wkChar : wkStr.toCharArray()) {
            Log.i(TAG, "wkChar: " + (int) wkChar);
            if (!isKana(wkChar)) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    /**
     * 文字が全角かなかチェックする
     * 
     * @param wkChar    [in] char   入力文字列
     * @return boolean  かなであればtrue, そうでなければfalseを返す。
     */
    private static boolean isKana(char wkChar) {
        return 0x3041 <= (int) wkChar && (int) wkChar <= 0x3093;
    }

    /** 全角かなから半角かな変換用 */
    public static final HashMap<Character, String> mKanaMap = new HashMap<Character, String>() {
        /**
        * 
        */
        private static final long serialVersionUID = -2081174211286493753L;

        {
            put('あ', "ｱ");
            put('い', "ｲ");
            put('う', "ｳ"); // 13.02.14
            put('え', "ｴ");
            put('お', "ｵ");
            put('ぁ', "ｧ");
            put('ぃ', "ｨ");
            put('ぅ', "ｩ");
            put('ぇ', "ｪ");
            put('ぉ', "ｫ");
            put('か', "ｶ");
            put('き', "ｷ");
            put('く', "ｸ");
            put('け', "ｹ");
            put('こ', "ｺ");
            put('が', "ｶﾞ");
            put('ぎ', "ｷﾞ");
            put('ぐ', "ｸﾞ");
            put('げ', "ｹﾞ");
            put('ご', "ｺﾞ");
            put('さ', "ｻ");
            put('し', "ｼ");
            put('す', "ｽ");
            put('せ', "ｾ");
            put('そ', "ｿ");
            put('ざ', "ｻﾞ");
            put('じ', "ｼﾞ");
            put('ず', "ｽﾞ");
            put('ぜ', "ｾﾞ");
            put('ぞ', "ｿﾞ");
            put('た', "ﾀ");
            put('ち', "ﾁ");
            put('つ', "ﾂ");
            put('て', "ﾃ");
            put('と', "ﾄ");
            put('だ', "ﾀﾞ");
            put('ぢ', "ﾁﾞ");
            put('づ', "ﾂﾞ");
            put('で', "ﾃﾞ");
            put('ど', "ﾄﾞ");
            put('な', "ﾅ");
            put('に', "ﾆ");
            put('ぬ', "ﾇ");
            put('ね', "ﾈ");
            put('の', "ﾉ");
            put('は', "ﾊ");
            put('ひ', "ﾋ");
            put('ふ', "ﾌ");
            put('へ', "ﾍ");
            put('ほ', "ﾎ");
            put('ば', "ﾊﾞ");
            put('び', "ﾋﾞ");
            put('ぶ', "ﾌﾞ");
            put('べ', "ﾍﾞ");
            put('ぼ', "ﾎﾞ");
            put('ぱ', "ﾊﾟ");
            put('ぴ', "ﾋﾟ");
            put('ぷ', "ﾌﾟ");
            put('ぺ', "ﾍﾟ");
            put('ぽ', "ﾎﾟ");
            put('ま', "ﾏ");
            put('み', "ﾐ");
            put('む', "ﾑ");
            put('め', "ﾒ");
            put('も', "ﾓ");
            put('や', "ﾔ");
            put('ゆ', "ﾕ");
            put('よ', "ﾖ");
            put('ゃ', "ｬ");
            put('ゅ', "ｭ");
            put('ょ', "ｮ");
            put('ら', "ﾗ");
            put('り', "ﾘ");
            put('る', "ﾙ");
            put('れ', "ﾚ");
            put('ろ', "ﾛ");
            put('わ', "ﾜ");
            put('を', "ｦ");
            put('ん', "ﾝ");
            put('っ', "ｯ");
        }
    };



    /**
     * ペアリング済みデバイス一覧を取得する
     * 
     * @return LinkedHashMap<String, String>
     *         デバイス名をキーとして、MACアドレスを値としたLinkedHashMapを返す
     */
    public static SparseArray<String[]> getBluetoothDevices() {
        SparseArray<String[]> wkBluetoothDevices = new SparseArray<>();

        // BluetoothAdapterのインスタンスを取得する
        BluetoothAdapter wkBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // ペアリング済みデバイス一覧を取得する
        Set<BluetoothDevice> wkPairedDevices = wkBluetoothAdapter.getBondedDevices();
        int wkIdx = 0;
        for (BluetoothDevice wkDevice : wkPairedDevices) {
            wkBluetoothDevices.put(wkIdx++, new String[] {
                    wkDevice.getName(), wkDevice.getAddress()
            });
        }
        return wkBluetoothDevices;
    }

    /**
     * コピー元ディレクトリ内の基本ファイルをコピー先ディレクトリへコピー.
     *
     * @param ctx       [in] {@link Context}    呼び出し元コンテキスト
     * @param wkDirSrc  [in] String             コピー元ディレクトリパス
     * @param wkDirDist [in] String             コピー先ディレクトリパス
     * @throws IOException  コピー失敗時に発生
     */
    public static void copyData(Context ctx, String wkDirSrc, String wkDirDist) throws IOException {
        copyData(ctx, wkDirSrc, wkDirDist, false);
    }

    /**
     * コピー元ディレクトリ内のすべてのファイルをコピー先ディレクトリへコピー.
     *
     * @param ctx       [in] {@link Context}    呼び出し元コンテキスト
     * @param wkDirSrc  [in] String             コピー元ディレクトリ
     * @param wkDirDist [in] String             コピー先ディレクトリ
     * @throws IOException  コピー失敗時に発生
     */
    public static void copyDataAll(Context ctx, String wkDirSrc, String wkDirDist)throws IOException{
        copyData(ctx, wkDirSrc, wkDirDist, true);
    }

    /**
     * 端末内ファイルのコピー.
     *
     * @param wkDirSrc  [in] String     コピー元ディレクトリのパス
     * @param wkDirDist [in] String     コピー先ディレクトリのパス
     * @param isAll     [in] boolean    全ファイルコピーフラグ(true:全ファイルコピー, false:基本ファイルのみコピー)
     * @throws IOException  コピーに失敗した場合に発生
     */
    private static void copyDataLocal(String wkDirSrc, String wkDirDist, boolean isAll) throws IOException {
        File wkDir = new File(wkDirSrc);

        // コピー先の作成
        File wkBDir = new File(wkDirDist);
        if (!wkBDir.exists()) {
            if(!wkBDir.mkdirs()){
                throw new IOException("コピー先ディレクトリの作成に失敗");
            }
        }

        File[] wkFiles = wkDir.listFiles();
        if (wkFiles == null) return;
        File toFile;
        for (File file : wkFiles) {
            // ディレクトリーはパスする
            if (file.isDirectory()) {
                continue;
            }
            if (!isAll && isBaseFile(file.getName())) {
                continue;
            }
            else {
                toFile = new File(wkDirDist + file.getName());
            }
            if (toFile.exists()) {
                if(!toFile.delete()){
                    throw new IOException("コピー先に同一ファイルが存在するが、削除に失敗");
                }
            }
            if(!toFile.createNewFile()){
                throw new IOException("コピー先のファイル作成に失敗");
            }
            OtherUtil.fileCopy(file, toFile);
        }
    }

    /**
     * ファイルのコピーを実施.
     * <br>コピー元、コピー先のパスからSDカードかどうか判断し、それぞのコピー方法でコピーを実施.
     *
     * @param ctx       [in] {@link Context}    呼び出し元コンテキスト
     * @param wkDirSrc  [in] String             コピー元ディレクトリパス
     * @param wkDirDist [in] String             コピー先ディレクトリパス
     * @param isAll     [in] boolean            コピー対象フラグ(true:基本ファイルのみ, false:全ファイル)
     * @throws IOException  コピー失敗時に発生
     */
    public static void copyData(@NonNull Context ctx, String wkDirSrc, String wkDirDist, boolean isAll)throws IOException{
        DocumentFile rootFile = getSdRootDirectory(ctx);
        DocumentFile srcDir = null;
        DocumentFile dstDir = null;
        
        boolean isSrcSd = wkDirSrc.contains(DEFINE.SD_DATA_DIRECTORY);
        boolean isDstSd = wkDirDist.contains(DEFINE.SD_DATA_DIRECTORY);
        
        if(isSrcSd){
            srcDir = getDirectoryUri(rootFile, wkDirSrc, true);
        }
        if(isDstSd){
            dstDir = getDirectoryUri(rootFile, wkDirDist, true);
        }
        

        if((isSrcSd && !srcDir.isDirectory()) ||
                isDstSd && !dstDir.isDirectory()){
            // コピー元、コピー先がディレクトリでない場合はエラー
            throw new IllegalStateException("");
        }
        
        if(isSrcSd && isDstSd){
            // SDカード内同士のコピー
            copySDtoSD(ctx, srcDir, dstDir, isAll);
        }
        else if(!isSrcSd && isDstSd){
            // コピー先のみSDカード
            copyInternaltoSD(ctx, wkDirSrc, dstDir, isAll);
        }
        else if(isSrcSd){
            // コピー元のみSDカード
            copySDtoInternal(ctx, srcDir, wkDirDist, isAll);
        }
        else {
            // 端末内コピー
            copyDataLocal(wkDirSrc, wkDirDist, isAll);
        }
    }

    /**
     * SDカード同士のディレクトリコピーを実施.
     *
     * @param ctx       [in] {@link Context}        呼び出し元コンテキスト
     * @param srcDir    [in] {@link DocumentFile}   コピー元ファイルドキュメント
     * @param dstDir    [in] {@link DocumentFile}   コピー先ファイルドキュメント
     * @param isAll     [in] boolean                コピー対象フラグ(true:基本ファイルのみ, false:全ファイル)
     * @throws IOException  コピー失敗時に発生
     */
    private static void copySDtoSD(Context ctx, DocumentFile srcDir, DocumentFile dstDir, boolean isAll) throws IOException{
        FileInputStream srcIs = null;
        FileOutputStream dstOs = null;
        BufferedInputStream srcBis = null;
        BufferedOutputStream dstBis = null;

        // コピー元ディレクトリからファイル一覧の取得
        for(DocumentFile srcFile : srcDir.listFiles()){
            if(srcFile.isDirectory()){
                // ディレクトリは無視
                continue;
            }
            if(!isAll && isBaseFile(srcFile.getName())){
                // ベースファイルをコピーしない
                continue;
            }
            // コピー先ファイルインスタンスの生成
            DocumentFile dstFile = dstDir.findFile(Objects.requireNonNull(srcFile.getName()));
            
            if(dstFile != null && dstFile.exists()){
                // コピー先ファイルが存在する場合は削除
                dstFile.delete();
            }
            dstFile = dstDir.createFile("application/octet-stream", srcFile.getName());

            try{
                srcIs = (FileInputStream) ctx.getContentResolver().openInputStream(srcFile.getUri());
                dstOs = (FileOutputStream) ctx.getContentResolver().openOutputStream(Objects.requireNonNull(dstFile).getUri());

                srcBis = new BufferedInputStream(srcIs);
                dstBis = new BufferedOutputStream(dstOs);
                byte[] buf = new byte[4096];
                int length;
                while((length = srcBis.read(buf)) != -1){
                    dstBis.write(buf, 0, length);
                }
            }
            catch(FileNotFoundException fnfe){
                MLog.WARN(ctx, TAG, fnfe);
            }
            finally{
                closeStream(ctx, dstBis);
                closeStream(ctx, srcBis);
                closeStream(ctx, dstOs);
                closeStream(ctx, srcIs);
            }
        }
    }

    /**
     * SDカードから端末内へのコピー.
     *
     * @param ctx       [in] {@link Context}        呼び出し元コンテキスト
     * @param srcDir    [in] {@link DocumentFile}   コピー元ドキュメントファイル
     * @param dstDir    [in] String                 コピー先ディレクトリパス
     * @param isAll     [in] boolean                コピー対象フラグ(true:基本ファイルのみ, false:全ファイル)
     * @throws IOException  コピー失敗時に発生
     */
    private static void copySDtoInternal(Context ctx, DocumentFile srcDir, String dstDir, boolean isAll) throws IOException{
        FileInputStream srcIs = null;
        FileOutputStream dstOs = null;
        BufferedInputStream srcBis = null;
        BufferedOutputStream dstBis = null;
        File dstFile;

        // コピー先の作成
        File wkBDir = new File(dstDir);
        if (!wkBDir.exists()) {
            if(!wkBDir.mkdirs()){
                throw new IOException("コピー先ディレクトリの作成に失敗");
            }
        }

        // コピー元ディレクトリからファイル一覧の取得
        for(DocumentFile srcFile : srcDir.listFiles()){
            if(srcFile.isDirectory()){
                // ディレクトリは無視
                continue;
            }
            if(!isAll && isBaseFile(srcFile.getName())){
                // ベースファイルをコピーしない
                continue;
            }
            // コピー先ファイルインスタンスの生成
            dstFile = new File(dstDir + srcFile.getName());
            if(dstFile.exists()){
                if(!dstFile.delete()){
                    throw new IOException("コピー先に同一ファイルが存在するが、削除に失敗");
                }
            }
            if(!dstFile.createNewFile()){
                throw new IOException("コピー先のファイル作成に失敗");
            }
            try{
                srcIs = (FileInputStream) ctx.getContentResolver().openInputStream(srcFile.getUri());
                dstOs = new FileOutputStream(dstFile);

                srcBis = new BufferedInputStream(srcIs);
                dstBis = new BufferedOutputStream(dstOs);
                
                byte[] buf = new byte[4096];
                int length;
                while((length = srcBis.read(buf)) != -1){
                    dstBis.write(buf, 0, length);
                }
            }
            catch(FileNotFoundException fnfe){
                MLog.WARN(ctx, TAG, fnfe);
            }
            finally{
                closeStream(ctx, dstBis);
                closeStream(ctx, srcBis);
                closeStream(ctx, dstOs);
                closeStream(ctx, srcIs);
            }
        }
    }

    /**
     * 端末内からSDカードへコピー.
     *
     * @param ctx       [in] {@link Context}        呼び出し元コンテキスト
     * @param srcDir    [in] String                 コピー元ディレクトリパス
     * @param dstDir    [in] {@link DocumentFile}   コピー先ドキュメントファイル
     * @param isAll     [in] boolean                コピー対象フラグ(true:基本ファイルのみ, false:全ファイル)
     * @throws IOException  コピー失敗時に発生
     */
    private static void copyInternaltoSD(Context ctx, String srcDir, DocumentFile dstDir, boolean isAll) throws IOException{
        FileInputStream srcIs = null;
        FileOutputStream dstOs = null;
        BufferedInputStream srcBis = null;
        BufferedOutputStream dstBis = null;

        File srcDirFile = new File(srcDir);
        // コピー元ディレクトリからファイル一覧の取得
        for(File srcFile : srcDirFile.listFiles()){
            if(srcFile.isDirectory()){
                // ディレクトリは無視
                continue;
            }
            if(!isAll && isBaseFile(srcFile.getName())){
                // ベースファイルをコピーしない
                continue;
            }
            // コピー先ファイルインスタンスの生成
            DocumentFile dstFile = dstDir.findFile(srcFile.getName());
            if(dstFile != null && dstFile.exists()){
                // ファイルが存在する場合は削除
                dstFile.delete();
            }

            jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog.INFO(ctx, TAG, srcFile.getName());
            dstFile = dstDir.createFile("application/octet-stream", srcFile.getName());
            
            try{
                srcIs = new FileInputStream(srcFile);
                dstOs = (FileOutputStream) ctx.getContentResolver().openOutputStream(Objects.requireNonNull(dstFile).getUri());

                srcBis = new BufferedInputStream(srcIs);
                dstBis = new BufferedOutputStream(dstOs);
                byte[] buf = new byte[4096];
                int length;
                while((length = srcBis.read(buf)) != -1){
                    dstBis.write(buf, 0, length);
                }
            }
            catch(FileNotFoundException fnfe){
                MLog.WARN(ctx, TAG, fnfe);
            }
            finally{
                closeStream(ctx, dstBis);
                closeStream(ctx, srcBis);
                closeStream(ctx, dstOs);
                closeStream(ctx, srcIs);
            }
        }
    }
    
    /**
     * 入力ストリームを閉じる.
     *
     * @param ctx   [in] {@link Context}        呼び出し元コンテキスト
     * @param is    [in] {@link InputStream}    入力ストリーム
     */
    private static void closeStream(Context ctx, InputStream is){
        if(is != null){
            try{
                is.close();
            }
            catch(IOException ioe){
                MLog.WARN(ctx, TAG, ioe);
            }
        }
    }
    
    /**
     * 出力ストリームを閉じる.
     *
     * @param ctx   [in] {@link Context}        呼び出し元コンテキスト
     * @param os    [in] {@link OutputStream}   出力ストリーム
     */
    private static void closeStream(Context ctx, OutputStream os){
        if(os != null){
            try{
                os.close();
            }
            catch(IOException ioe){
                MLog.WARN(ctx, TAG, ioe);
            }
        }
    }

    /**
     * マウント済みストレージの取得.
     *
     * @return  String  マウント済みストレージ
     * @throws IllegalStateException    エラー時に発生
     */
    public static String getMountSd() throws IllegalStateException{
        List<String> mountList = new ArrayList<>();
        String strMountSd = null;
    
        Scanner scanner = null;
    
        try{
            // システム設定ファイルにアクセス
            File fstab = new File("/system/etc/vold.fstab");
            scanner = new Scanner(new FileInputStream(fstab));
            // 1行ずつ読み込む
            while(scanner.hasNextLine()){
                String strLine = scanner.nextLine();
                // dev_mountまたはfuse_mountで始まる行
                if(strLine.startsWith("dev_mount") || strLine.startsWith("fuse_mount")){
                    String strPath = strLine.replaceAll("\t", " ").split(" ")[2];
                    if(!mountList.contains(strPath)){
                        mountList.add(strPath);
                    }
                }
            }
        }
        catch(FileNotFoundException fnfe){
            throw new IllegalStateException(fnfe);
        }
        finally{
            if(scanner != null){
                scanner.close();
            }
        }

        if(!Environment.isExternalStorageRemovable()){
            // 内部パスは除外
            mountList.remove(Environment.getExternalStorageDirectory().getPath());
        }

        // マウントされていないパスを除外
        for(String path : mountList){
            if(!isMounted(path)){
                mountList.remove(path);
            }
        }
    
        // 1件以上残っている場合は、先頭のマウント先を設定
        if(mountList.size() > 0){
            strMountSd = mountList.get(0);
        }
    
        return strMountSd;
    }

    /**
     * 対象ストレージがマウント済みか確認.
     *
     * @param path  [in] String 対象ストレージ
     * @return  boolean true:マウント済み, false:未マウント
     * @throws IllegalStateException    エラー時に発生
     */
    private static boolean isMounted(String path) throws IllegalStateException{
        boolean isMounted = false;
        Scanner scanner = null;
        try{
            // マウントポイントを取得
            File mounts = new File("/proc/mounts");
            scanner = new Scanner(new FileInputStream(mounts));
            // マウントポイントに該当するパスがあるかチェック
            while(scanner.hasNextLine()){
                if(scanner.nextLine().contains(path)){
                    // 該当パスが存在するのでマウントされている
                    isMounted = true;
                    break;
                }
            }
        }
        catch(FileNotFoundException fnfe){
            throw new IllegalStateException(fnfe);
        }
        finally{
            if(scanner != null){
                scanner.close();
            }
        }
        return isMounted;
    }

    /**
     * 対象のファイルがガス料金、商品ファイルか確認.
     *
     * @param strFilename   [in] String ファイル名
     * @return  boolean true:対象ファイル, false:非対象ファイル
     */
    public static boolean isBaseFile(String strFilename) {
        if (strFilename == null) {
            return false;
        }
        return strFilename.trim().equalsIgnoreCase("GASF.D") || strFilename.trim().equalsIgnoreCase("SHOF.D");
    }
    
    /**
     * DocumentFileで示すファイルを削除する<br />
     * 対象がディレクトリの場合、ディレクトリ内のファイルを削除してから削除する.
     *
     * @param delFile   [in] {@link DocumentFile}   削除対象ドキュメントファイル
     */
    private static void deleteData(DocumentFile delFile){
        if(delFile == null){
            return;
        }
        if(delFile.isDirectory()){
            for(DocumentFile childeFile : delFile.listFiles()){
                deleteData(childeFile);
            }
        }
        delFile.delete();
    }

    public static class SDCardErrorException extends Exception {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
    }

    /**
    * 端数処理
    * 
    * @param suu    [in] double 対象数値
    * @param add    [in] int    加算分
    * @param multi  [in] int    乗算分
    * @param keta   [in] double 桁数
    * @return   long    端数処理後の値
    */
    public static long hasCom(double suu, int add, int multi, double keta) {
        double kin, kin1;
        double add1, multi1;
        int sgn;

        if (add == 0L && multi == 10L) {
            // 端数処理：未設定
            kin = suu;
        }
        else {
            kin1 = suu;
            if (keta < 0) {
                kin1 = suu * Math.abs(keta);
            }
            else if (keta > 0) {
                kin1 = suu / Math.abs(keta);
            }

            add1 = (0.1 + add);
            multi1 = multi;
            if (kin1 < 0) {
                sgn = -1;
            }
            else {
                sgn = 1;
            }
            kin1 = Math.abs(kin1);

            double temp = (((kin1 * 1000) + add1 + 0.01) / multi1);
            temp = Math.floor(temp);
            kin = ((temp * multi1) / 1000) * sgn;

            if (keta < 0) {
                kin /= Math.abs(keta);
            }
            else if (keta > 0) {
                kin *= Math.abs(keta);
            }
        }
        return (long) kin;
    }

    /**
     * パラメータからURIを作成する<br />
     * ユーザー名、パスワード付のURIを作成する
     * 
     * @param wkIpAddress       [in] String 宛先アドレス
     * @param wkNetworkDirName  [in] String 宛先ディレクトリ名
     * @param wkFileName        [in] String 対象のファイル名
     * @param wkUserName        [in] String ユーザー名
     * @param wkPasswd          [in] String パスワード
     * @return  String  URL
     */
    public static String createURI(String wkIpAddress, String wkNetworkDirName, String wkFileName, String wkUserName, String wkPasswd) {
        StringBuilder wkUri = new StringBuilder();
        wkUri.append("smb://");
        if (wkUserName != null && !wkUserName.equals("")) {
            wkUri.append(wkUserName);
        }
        if (wkPasswd != null && !wkPasswd.equals("")) {
            wkUri.append(":").append(wkPasswd);
        }
        if (wkUserName != null && !wkUserName.equals("")) {
            wkUri.append("@");
        }
        wkUri.append(wkIpAddress);
        wkUri.append("/");
        wkUri.append(wkNetworkDirName);
        wkUri.append("/");
        if (wkFileName != null && !wkFileName.equals("")) {
            wkUri.append(wkFileName);
        }
        MLog.INFO(TAG, "    接続先URI: " + wkUri);
        return wkUri.toString();
    }

    /**
     * 対象パス(SDカード)のドキュメントディレクトリを取得.
     *
     * @param ctx           [in] {@link Context}    呼び出し元コンテキスト
     * @param strPath       [in] String             対象パス
     * @param isDirectory   [in] boolean            ディレクトリかどうか(true:ディレクトリ, false: ファイル)
     * @return  {@link DocumentFile}    対象パスのドキュメントファイル
     */
    public static DocumentFile getDirectoryUri(Context ctx, String strPath, boolean isDirectory){
        return getDirectoryUri(
                getSdRootDirectory(ctx),
                strPath,
                isDirectory
        );
    }

    /**
     * 対象ファイルのドキュメントファイルを取得.
     *
     * @param rootFile      [in] {@link DocumentFile}   ドキュメントファイルのルート
     * @param path          [in] String                 対象パス
     * @param isDirectory   [in] boolean                ディレクトリかどうか(true:ディレクトリ, false:非ディレクトリ)
     * @return  {@link DocumentFile}    対象ファイルのドキュメントファイル
     */
    public static DocumentFile getDirectoryUri(DocumentFile rootFile, String path, boolean isDirectory){
        DocumentFile docFile;
        String[] splitPath = path.split("/", 2);
        docFile = rootFile.findFile(splitPath[0]);
        if((docFile == null || !docFile.exists()) && isDirectory){
            docFile = rootFile.createDirectory(splitPath[0]);
        }
        if(splitPath.length != 1 && splitPath[1].length() != 0){
            docFile = getDirectoryUri(Objects.requireNonNull(docFile), splitPath[1], isDirectory);
        }
        return docFile;
    }
    
    /**
     * 設定に保存してある検針くんのＳＤカードルートディレクトリの取得.
     *
     * @param ctx   [in] {@link Context}    呼び出し元のコンテキスト
     * @return  {@link DocumentFile}    ルートドキュメントファイル
     */
    public static DocumentFile getSdRootDirectory(Context ctx){
        DocumentFile rootFile;
        SharedPreferences prefs = ctx.getSharedPreferences("Printer", Context.MODE_PRIVATE);
        Uri rootUri = Uri.parse(prefs.getString(DEFINE.PREF_URI_BACKUP_DATA_SAVE, ""));
        rootFile = DocumentFile.fromTreeUri(ctx, rootUri);
        return rootFile;
    }

    /**
     * 文字列後方の全半角スペース除去.
     *
     * @param strTarget [in] String 対象文字列
     * @return String   全半角スペース除去済み文字列を返す。
     */
    public static String cutStringSpace( String strTarget ) {

        if ( strTarget == null ) {
            return "";
        }

        int nLen = strTarget.length() - 1;
        if ( nLen < 0 ) {
            return "";
        }

        int nPos = -1;
        String retStr;
        String strMulti = "　";   // 全角スペース
        String strSingle = " ";   // 半角スペース

        for ( int i = nLen; i >= 0; i-- ) {
            if ( !(strTarget.substring(i, i + 1)).equals(strMulti) && !(strTarget.substring(i, i + 1)).equals(strSingle)) {
                nPos = i + 1;
                break;
            }
        }
        if ( nPos == -1 ) {
            retStr = "";
        }
        else {
            retStr = strTarget.substring( 0, nPos );
        }

        return retStr;
    }

    /**
     * String型をbyte型配列の格納開始インデックスから格納します。
     *
     * @param bTbl      [in/out] byte[] 格納先byte配列
     * @param bIdx      [in] int        格納開始インデックス
     * @param wkStr     [in] String     格納する文字列型
     * @param strLen    [in] int        文字列数
     * @return int  次の格納開始インデックス
     */
    public static synchronized int stringToBytes(byte[] bTbl, int bIdx, String wkStr, int strLen) {
        if ( wkStr == null ) {
            return 0;
        }

        byte[] val;
        int sIdx = 0;
        byte CHAR_SPACE = ' ';

        try {
            val = wkStr.getBytes();
            sIdx = bIdx;

            for (byte b : val) {
                bTbl[sIdx++] = b;
            }

            while ( sIdx < bIdx + strLen ) {
                // Byte配列に置き換える時に、余り文字数分に半角空白を代入します。
                bTbl[sIdx++] = CHAR_SPACE;
            }
        }
        catch ( Exception e ) {
            MLog.WARN(null, TAG, e);
        }
        return sIdx;
    }

    /**
     * byte型配列の読込開始インデックスからのデータをString変換します。
     *
     * @param bTbl      [in/out] byte[] バイト配列
     * @param bIdx      [in]            読込開始インデックス
     * @param strLen    [in] int        文字列数
     * @return String   変換後文字列
     */
    public static synchronized String bytesToString( byte[] bTbl, int bIdx, int strLen ) {
        return new String( bTbl, bIdx, strLen );
    }

    /**
     * 全角文字列の最後尾から全角空白を除去.
     *
     * @param wkStr [in] String 全角空白を除去する文字列
     * @return String   全角空白除去後の文字列 (wkStrがnull,または文字列数０の場合は、nullを返します。)
     */
    public static synchronized String cutSpace( String wkStr ) {
        if ( wkStr == null ) {
            return "";
        }

        short wkLen = (short)(wkStr.length() - 1);
        if ( wkLen < 0 ) {
            return "";
        }

        short wkCnt = -1;
        String retStr;
        String strTrim = "　";

        for ( short i = wkLen; i >= 0; i-- ) {
            if (!(wkStr.substring(i, i + 1)).equals(strTrim)) {
                if (wkStr.charAt(i) != 0) {
                    wkCnt = (short) (i + 1);
                    break;
                }
            }
        }
        if ( wkCnt == -1 ) {
            retStr = "";
        }
        else {
            retStr = wkStr.substring(0, wkCnt);
        }
        return retStr;
    }

    /**
     * <p>
     * 文字列から、開始から指定したバイト数分で切った文字列を取得します。<br>
     * ただし、終了バイトインデックス＋１の位置に全角の真ん中となった場合は、その文字は含めません。
     * </p>
     * @param wkStr     [in] String 文字列
     * @param endIdx    [in] int    終了バイトインデックス＋１
     * @return String   処理後の文字列
     */
    public synchronized static String cutString(String wkStr, int endIdx) {
        return cutString( wkStr, 0, endIdx );
    }

    /**
     * <p>
     * 文字列から、指定した開始から終了までのバイト数分で切った文字列を取得します。<br>
     * ただし、終了バイトインデックス＋１の位置に全角の真ん中となった場合は、その文字は含めません。
     * </p>
     * @param wkStr     [in] String 文字列
     * @param startIdx  [in] int    開始バイトインデックス
     * @param endIdx    [in] int    終了バイトインデックス＋１
     * @return String   処理後の文字列
     */
    public synchronized static String cutString(String wkStr, int startIdx, int endIdx) {
        StringBuilder retStr;
        String cutStr;

        try {
            if ( wkStr == null ) {
                return wkStr;
            }
            if ( startIdx < 0 ) {
                return wkStr;
            }
            if ( startIdx >= endIdx ) {
                return wkStr;
            }
            retStr = new StringBuilder();
            for ( int i = startIdx; i < wkStr.length(); i++ ) {
                cutStr = wkStr.substring( i, i + 1 );
                if ( getBytesLen( cutStr ) == 2 ) {
                    // 全角文字
                    if ( (getBytesLen( retStr.toString() ) + 2) > endIdx ) {
                        break;
                    }
                }
                else {
                    // 半角文字
                    if ( (getBytesLen( retStr.toString() ) + 1) > endIdx ) {
                        break;
                    }
                }
                retStr.append( cutStr );
            }
        }
        catch ( Exception e ) {
            MLog.WARN(null, TAG, e);
            return wkStr;
        }
        return retStr.toString();
    }

    /**
     * ブロードキャストに対してメッセージを送信
     *
     * @param context   [in] {@link Context}    呼び出し元のコンテキスト
     * @param msg       [in] String             送信するメッセージ
     */
    public static void sendBroadCastMessage(Context context, String msg) {
        MLog.INFO(context, TAG, msg);
        Intent intent = new Intent(DEFINE.KEY_BROADCAST_RECIEVER_DATA_RECIEVE);
        intent.putExtra(DEFINE.KEY_BROADCAST_RECIEVER_STATUS, msg);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * データ受信前に現在タブレット内にあるデータをバックアップにコピーする
     *
     * @return boolean true: バックアップ成功, false: バックアップ失敗
     */
    public static boolean dataBackup(Context context, DEFINE.ENUM_TRANSFER_MODE enumTransferMode) {
        if (enumTransferMode == DEFINE.ENUM_TRANSFER_MODE.SEND) {
            // データ送信モードの場合はバックアップをとらない
            return true;
        }
        sendBroadCastMessage(context, "バックアップ開始");
        boolean res = true;
        // バックアップパス
        File fileBackup = new File(DEFINE.getDataCopyPath(context));
        if(!fileBackup.exists()){
            if(!fileBackup.mkdirs()){
                MLog.ERROR(context, TAG, "バックアップ用ディレクトリの作成に失敗");
                return false;
            }
        }
        File fromFile = new File(DEFINE.getDataPath(context));
        File[] lstFiles = fromFile.listFiles();
        if(lstFiles == null || lstFiles.length == 0 ){
            MLog.INFO(context, TAG, "バックアップ対象のファイルが有りません。");
            return true;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.JAPANESE);
        // バックアップ用のパスを設定
        String strBackupFileName = sdf.format(new java.util.Date());
        if(enumTransferMode == DEFINE.ENUM_TRANSFER_MODE.RECIEVE){
            strBackupFileName += "_受信前";
        }
        File fileNewBackup = new File(fileBackup, strBackupFileName);
        if (fileNewBackup.mkdirs()) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            for (File file : lstFiles) {
                if(file.isDirectory()){
                    continue;
                }
                File fileBackupFile = new File(fileNewBackup, file.getName());
                try {
                    if (fileBackupFile.createNewFile()) {
                            fis = new FileInputStream(file);
                            fos = new FileOutputStream(fileBackupFile);
                            res = FileUtils.copyFile(context, fis, Objects.requireNonNull(fos));
                    } else {
                        // ファイルの作成に失敗
                        OtherUtil.sendBroadCastMessage(context, "ファイルの作成に失敗：" + file.getName());
                        res = false;
                        break;
                    }
                } catch (IOException ioe) {
                    OtherUtil.sendBroadCastMessage(context, "バックアップに失敗：" + file.getName());
                    res = false;
                    break;
                } finally {
                    closeOutputStream(context, fos);
                    closeInputStream(context, fis);
                }
            }
            if (res) {
                // バックアップディレクトリのロテート処理
                if (fileBackup.listFiles().length >BACKUP_LIMIT_NUM ) {
                    // バックアップファイルが100個を超える場合、最古のバックアップを探し出し、
                    // ディレクトリごと削除する
                    List<String> lstDirName = new ArrayList<>();
                    for (File dfDir : fileBackup.listFiles()) {
                        lstDirName.add(dfDir.getName());
                    }
                    Collections.sort(lstDirName);
                    File fileDelete = new File(fileBackup, lstDirName.get(0));
                    if(rmdir(new File(fileBackup, lstDirName.get(0)))) {
                        MLog.WARN(context, TAG, "バックアップファイルのロテートに成功.");
                    }
                    else {
                        MLog.WARN(context, TAG, "バックアップファイルのロテートに失敗.");
                    }
                }
            }
        } else {
            OtherUtil.sendBroadCastMessage(context, "バックアップフォルダーの作成に失敗");
        }
        OtherUtil.sendBroadCastMessage(context, "バックアップ完了：" + (res ? "成功" : "失敗"));
        return res;
    }

    /**
     * 引数で渡されたディレクトリを削除.
     *
     * @param filePath  [in] {@link File}   削除対象のディレクトリ
     * @return  boolean 削除成否
     */
    public static boolean rmdir(File filePath){
        if(!filePath.exists() || !filePath.isDirectory()){
            return false;
        }
        for(File file : filePath.listFiles()){
            if(file.isDirectory()){
                if(!rmdir(file)){
                    return false;
                }
            }
            if(!file.delete()){
                return false;
            }
        }
        return filePath.delete();
    }
    /**
     * 入力ストリームを閉じる
     */
    public static void closeInputStream(Context context, InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException ioe) {
                MLog.WARN(context, TAG, ioe);
            }
        }
    }

    /**
     * 出力ストリームを閉じる
     */
    public static void closeOutputStream(Context context, OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException ioe) {
                MLog.WARN(context, TAG, ioe);
            }
        }
    }

    /**
     * 文字列を指定したバイト数までバイト単位で切り出し、切り出し後の文字列リストを返す。<br>
     * <br>
     * 先頭から指定バイト数分、文字列を切り出す。<br>
     * 切り出し終了部分が２バイト文字の場合は、直前の文字までを切り出す。<br>
     * 例：１つ文字列を複数行に分割したいときに使用する。<br>
     * cutStringList( "あいうえおやゆよ", 10 )　　　→　{ "あいうえお", "やゆよ" }<br>
     * cutStringList( "あいうえ4かきくけこ5", 10 )　→　{ "あいうえ4", "かきくけこ", "5" }<br>
     *
     * @param str       [in] String 切り出し対象文字列
     * @param nByteLen  [in] int    切り出しバイト数
     * @return {@code List<String>} 切り出し後の文字列リスト
     */
    public static List<String> cutStringList( String str, int nByteLen ) {
        List<String> lstDat = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int cnt = 0;

        if (str == null) {
            return lstDat;
        }

        for (int i = 0; i < str.length(); i++) {
            String tmpStr = str.substring( i, i + 1 );
            try {
                byte[] b = tmpStr.getBytes(Charset.forName("MS932"));
                if ( cnt + b.length > nByteLen ) {
                    lstDat.add( sb.toString() );
                    sb = new StringBuilder();
                    cnt = 0;
                }
                sb.append( tmpStr );
                cnt += b.length;
            }
            catch (Exception ex) {
                MLog.WARN(null, TAG, ex);
            }
        }

        if ( sb.length() > 0 ) {
            lstDat.add( sb.toString() );
        }

        return lstDat;
    }

    /**
     * 売上用の消費税率取得.
     *
     * @param sTaxyy    [in] short  消費税切替:年
     * @param bTaxmm    [in] byte   消費税切替:月
     * @param bTaxdd    [in] byte   消費税切替:日
     * @param sDenyy    [in] short  伝票日付:年
     * @param bDenmm    [in] byte   伝票日付:月
     * @param bDendd    [in] byte   伝票日付:日
     * @param sTaxr     [in] short  消費税率
     * @param sTaxrOld  [in] short  旧消費税率
     * @param sTaxrNew  [in] short  新消費税率
     * @return  short   消費税率
     */
    public static int getUriTaxr(
            short sTaxyy,
            byte bTaxmm,
            byte bTaxdd,
            short sDenyy,
            byte bDenmm,
            byte bDendd,
            short sTaxr,
            short sTaxrOld,
            short sTaxrNew
    ){
        int nResTaxr = sTaxr;
        if(sTaxyy != 0 && bTaxmm != 0 && bTaxdd != 0){
            long lTaxYmd = (long)sTaxyy * 10000L + (long)bTaxmm * 100L + (long)bTaxdd;
            long lDenYmd = (long)sDenyy * 10000L + (long)bDenmm * 100L + (long)bDendd;
            if(lDenYmd >= lTaxYmd){
                nResTaxr = sTaxrNew;
            }
            else {
                nResTaxr = sTaxrOld;
            }
        }
        return nResTaxr;
    }

    //Hieu
    /**
     * 差益還元の名称を取得.
     *
     * @param context   [in] {@link Context}    呼び出し元コンテキスト
     * @param sy2fDat   [in] {@link Sy2fDat}    システム設定２データ
     * @return  String  差益還元名称
     */
    public static String getKangcontname(Context context, Sy2fDat sy2fDat) {
        String strKang = "原料費調整";
        // 差益還元額名称取得
//        if () {
//
//        }
        return OtherUtil.getClearString(OtherUtil.nullToString(strKang));
    }

    /**
     * double型をlong型に変換.
     *
     * @param dvalue    [in] double 変換元double値
     * @return  long    変換後long値
     */
    public static long double2long(double dvalue){
        if(dvalue < 0.){
            return (long)(dvalue - 0.001);
        }
        else {
            return (long)(dvalue + 0.001);
        }
    }
}
