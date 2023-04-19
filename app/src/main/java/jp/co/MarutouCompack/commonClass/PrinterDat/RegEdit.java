
package jp.co.MarutouCompack.commonClass.PrinterDat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Objects;

import jp.co.MarutouCompack.Printer.R;
import jp.co.MarutouCompack.baseClass.DEFINE;
import jp.co.MarutouCompack.baseClass.DatabaseHelper;
import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * <p>
 * タイトル: MGS
 * </p>
 * <p>
 * 説明:
 * </p>
 * <p>
 * 著作権: Copyright (c) 2011 Marutou Compack Co.,Ltd
 * </p>
 * <p>
 * 会社名: Marutou Compack Co.,Ltd
 * </p>
 */
public class RegEdit {
    /** ログ用タグ */
    private static final String TAG = RegEdit.class.getSimpleName();
    /** テーブル名 */
    private static final String TABLE = "REGISTORY";

    /**
     * 検針済み顧客の表示有無
     * 
     * @param ctx   [in] {@link Context}    呼び出し元コンテキスト
     * @return String   検針済み顧客の表示有無
     * @throws MException   検針済み顧客の表示有無取得時にエラーがあった場合に発生
     */
    public static boolean getShowKenSumi(Context ctx) throws MException {
        String wkStr;
        try {
            wkStr = getData(ctx, "SHOWKENSUMI");
        }
        catch (Exception e) {
            throw new MException(e, ctx.getResources().getString(R.string.ERROR_REG_SHOWKENSIN_GET), 0);
        }

        return wkStr != null && wkStr.equals("0");
    }

    /**
     * 検針済み顧客の表示有無を設定
     * 
     * @param ctx       [in] {@link Context}    呼び出し元コンテキスト
     * @param isSumi    [in] boolean            検針済み顧客の表示有無
     * @throws MException　検針済み顧客の表示有無設定失敗時に発生
     */
    public static void setShowKenSumi(Context ctx, boolean isSumi) throws MException {
        try {
            setData(ctx, "SHOWKENSUMI", (isSumi ? "0" : "1"));
        }
        catch (Exception e) {
            throw new MException(e, ctx.getResources().getString(R.string.ERROR_REG_KENSUMI_SET), 0);
        }
    }

    /**
     * 前回終了時の表示顧客番号を取得する。
     * 
     * @param ctx   [in] {@link Context}    呼び出し元コンテキスト
     * @return int  前回終了時の表示顧客番号
     * @throws MException 前回終了時の表示顧客番号時に発生する。
     */
    public static int getShowIndex(Context ctx) throws MException {
        try {
            return Integer.parseInt(Objects.requireNonNull(getData(ctx, "SHOWINDEX")));
        }
        catch (Exception ex) {
            throw new MException(ex, ctx.getResources().getString(R.string.ERROR_REG_SHOWKENSIN_GET), 0);
        }
    }

    /**
     * 前回終了時の表示顧客番号を設定する。
     * 
     * @param ctx   [in] {@link Context}    呼び出し元コンテキスト
     * @param nIdx  [in] int                前回終了時の表示顧客番号
     * @throws MException　前回終了時表示顧客設定失敗時に発生する。
     */
    public static void setShowIndex(Context ctx, int nIdx) throws MException {
        try {
            setData(ctx, "SHOWINDEX", String.valueOf(nIdx));
        }
        catch (Exception ex) {
            throw new MException(ex, ctx.getResources().getString(R.string.ERROR_REG_SHOWKENSIN_SET), 0);
        }
    }

    /**
     * 日付設定モードを取得する。<br />
     * 0: システムファイル<br />
     * 1: 本体日付設定
     * 
     * @param ctx   [in] {@link Context}    呼び出し元コンテキスト
     * @return byte 日付設定モード
     * @throws MException 日付設定取得失敗時に発生する。
     */
    public static byte getDateSetMode(Context ctx) throws MException {
        try {
            return Byte.parseByte(Objects.requireNonNull(getData(ctx, "DATESETMODE")));
        }
        catch (Exception ex) {
            throw new MException(ex, ctx.getResources().getString(R.string.ERROR_REG_DATE_GET), 0);
        }
    }

    /**
     * 日付設定モードを設定する.<br />
     * 0: システムファイル<br />
     * 1: 本体日付設定
     * 
     * @param ctx   [in] {@link Context}    呼び出し元コンテキスト
     * @param bMode [in] byte               日付設定モード
     * @throws MException　日付設定モード設定失敗時に発生
     */
    public static void setDateSetMode(Context ctx, byte bMode) throws MException {
        try {
            setData(ctx, "DATESETMODE", String.valueOf(bMode));
        }
        catch (Exception ex) {
            throw new MException(ex, ctx.getResources().getString(R.string.ERROR_REG_DATE_SET), 0);
        }
    }

    /**
     * Bluetoothアドレスを設定する。
     * 
     * @param ctx       [in] {@link Context}    呼び出し元コンテキスト
     * @param strBtAddr [in] String             Bluetoothアドレス
     * @throws MException 設定失敗時に発生。
     */
    public static void setBTAddress(Context ctx, String strBtAddr) throws MException {
        try {
            setData(ctx, "BTADDRESS", strBtAddr);
        }
        catch (Exception ex) {
            throw new MException(ex, ctx.getResources().getString(R.string.ERROR_REG_BTADDRESS_SET), 0);
        }
    }

    /**
     * 接続先のBluetoothアドレスを取得する。
     * 
     * @param ctx   [in] {@link Context}    呼び出し元コンテキスト
     * @return  String  Bluetoothアドレス
     * @throws MException   Bluetoothアドレスの取得に失敗した場合に発生
     */
    public static String getBTAddress(Context ctx) throws MException {
        try {
            return getData(ctx, "BTADDRESS");
        }
        catch (Exception ex) {
            throw new MException(ex, ctx.getResources().getString(R.string.ERROR_REG_BTADDRESS_GET), 0);
        }
    }

    /**
     * SQLiteデータベースから指定した端末設定情報を取得する。
     *
     * @param ctx           [in] {@link Context}    呼び出し元コンテキスト
     * @param strColumnName [in] String             項目名
     * @return String   指定した項目の設定値
     * @throws Exception 端末設定の異常時に発生する。
     */
    private static String getData(Context ctx, String strColumnName) throws Exception {
        if (ctx == null) {
            MLog.ERROR(null, TAG, "Contextがnullです。");
            return null;
        }

        // データベースヘルパーの設定
        DatabaseHelper wkHelper = new DatabaseHelper(ctx);
        // データベースの宣言
        SQLiteDatabase wkDb = wkHelper.getReadableDatabase();
        if (wkDb == null) {
            MLog.ERROR(ctx, TAG, "データベースの宣言に失敗しました。");
            wkHelper.close();
            throw new Exception("端末設定に異常があります。");
        }

        String wkResult;
        String[] wkColumn = {
            "VALUE"
        };
        String wkWhere = "NAME = ?";
        String[] wkWhereArgs = {
            strColumnName
        };

        // データベースを検索
        Cursor wkCursor = wkDb.query(TABLE, wkColumn, wkWhere, wkWhereArgs, null, null, null);
        if (wkCursor.getCount() != 1) {
            Log.e(TAG, "複数設定存在します。");
            Log.d(TAG, "結果個数: " + wkCursor.getCount());
            wkCursor.close();
            wkHelper.close();
            throw new Exception("端末設定に異常があります。");
        }

        // 結果を取得
        wkCursor.moveToFirst();
        wkResult = wkCursor.getString(wkCursor.getColumnIndex("VALUE"));

        // クローズ処理
        wkCursor.close();
        wkHelper.close();

        if (DEFINE.isDebug) Log.d(TAG, "--getData()");
        return wkResult;
    }

    /**
     * 設定を端末に保存する。
     *
     * @param ctx           [in] {@link Context}    呼び出し元コンテキスト
     * @param strColumnName [in] String             項目名
     * @param strValue      [in] String             設定値
     * @throws Exception 端末設定の異常時に発生する。
     */
    private static void setData(Context ctx, String strColumnName, String strValue) throws Exception {
        MLog.INFO(ctx, TAG, "項目名:" + strColumnName + ", 設定値:" + strValue);
        // データベースヘルパーの設定
        DatabaseHelper wkHelper = new DatabaseHelper(ctx);
        // データベースの宣言
        SQLiteDatabase wkDb = wkHelper.getWritableDatabase();
        if (wkDb == null) {
            MLog.ERROR(ctx, TAG, "データベースの宣言に失敗しました。");
            wkHelper.close();
            throw new Exception("端末設定に異常があります。");
        }

        // SQL文の種作成
        String wkWhere = "NAME = ?";
        String[] wkWhereArgs = {
            strColumnName
        };
        ContentValues wkValues = new ContentValues();
        wkValues.put("VALUE", strValue);

        // DBの更新を実行
        wkDb.update(TABLE, wkValues, wkWhere, wkWhereArgs);

        // データベースクローズ処理
        wkHelper.close();
    }

}
