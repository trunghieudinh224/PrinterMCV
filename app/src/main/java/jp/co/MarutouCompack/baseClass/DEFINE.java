
package jp.co.MarutouCompack.baseClass;

import android.content.Context;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

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
public class DEFINE {

    private static final String TAG = DEFINE.class.getSimpleName();
    /** デバッグフラグ */
    public static final boolean isDebug = true;
    /** 簡ガス以外で日割り計算をするかどうか? */
    public static final boolean isExecuteHiwari = false;
    /** バックアップパス */
    public static final String BACKUP_PATH = "Backup/";
    /** データ受け渡しキー：UserData */
    public static final String PARCEL_USER_DATA = DEFINE.class.getPackage() + ".UserData";
    /** データ受け渡しキー：サーチ結果から呼ばれたかどうか？ */
    public static final String PARCEL_CALL_FROM_SEARCHRESULT = "CALL_FROM_SEARCHRESULT";
    /** データ受け渡しキー：顧客並び順 */
    public static final String PARCEL_CALL_FROM_SEARCHORDER = "CALL_FROM_SEARCHORDER";
    /** 初回起動時パスワード */
    public static final String PASSWORD = "MARUTOU8301";
    /** プリファレンス設定項目名:Bluetooth接続設定 */
    public static final String BLUETOOTH_OFF_FLG = "BluetoothOffFlg";
    /** データ受け渡しキー：初回起動フラグ */
    public static final String PARCEL_KEY_FIRSTSET = "PARCEL_KEY_FIRSTSET";
    /** データ保存キー：データバックアップ用URI */
    public static final String PREF_URI_BACKUP_DATA_SAVE = "PREF_URI_BACKUP_DATA_SAVE";
    /** データ転送：端末ストレージ時のディレクトリパス格納先 */
    public static final String PREF_LOCAL_DATA_DIRECTORY = "PREF_LOCAL_DATA_DIRECTORY";
    /** SD保存用パス */
    public static final String SD_DATA_DIRECTORY = "MarutouCompack/";
    /** 更新用アプリ格納先パス */
    public static final String SD_APK_DIRECTORY = SD_DATA_DIRECTORY + "Printer/apk/";
    /** データ受け渡しキー：グループID */
    public static final String KEY_GROUP_ID = "KEY_GROUP_ID";
    /** データ受け渡しキー：グループ名 */
    public static final String KEY_GROUP_NAME = "KEY_GROUP_NAME";

    /**
     * データ格納パスを取得する。
     * 
     * @return データ格納先パス
     */
    public static String getDataPath(Context ctx) {
        File wkDir = ctx.getExternalFilesDirs(null)[0];
        if (!wkDir.exists()) {
            if(!wkDir.mkdir()){
                jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog.WARN(ctx, DEFINE.class.getSimpleName(), "ディレクトリ作成失敗");
            }
        }
        // 応答なし時にm_DATA_PATHが空白になる点への対応
        return wkDir.getPath() + "/";
    }

    /**
     * 受信データ格納パスを取得する。
     * 
     * @return 受信データ格納パス
     */
    public static String getDataCopyPath(Context ctx) {
        File wkDir = new File(ctx.getExternalFilesDirs(null)[0], BACKUP_PATH);
        if (!wkDir.exists()) {
            if(!wkDir.mkdir()){
                MLog.WARN(ctx, TAG, "バックアップパスの生成に失敗.");
            }
        }
        // 応答なし時にm_DATA_PATHが空白になる点への対応
        return wkDir.getPath() + "/";
    }

    /**
     * データ転送モード
     */
    public enum ENUM_TRANSFER_MODE{
        /** 送信 */
        SEND,
        /** 受信 */
        RECIEVE,
    }

    public static final String FILE_SYSF = "Sysf.D";
    public static final String FILE_SY2F = "Sy2f.D";
    public static final String FILE_HTCMT = "Htcmt.D";
    public static final String FILE_IDXF = "Idxf.D";
    public static final String FILE_TNTF = "Tntf.D";
    public static final String FILE_BUSF = "Busf.D";
    public static final String FILE_HINF = "Hinf.D";
    public static final String FILE_SHOF = "Shof.D";
    public static final String FILE_HANF = "Hanf.D";
    public static final String FILE_HM00 = "Hm00.D";
    public static final String FILE_HM01 = "Hm01.D";
    public static final String FILE_HMHT = "Hmht.D";
    public static final String FILE_HMH2 = "Hmh2.D";
    public static final String FILE_LESF = "Lesf.D";
    public static final String FILE_KOKF = "Kokf.D";
    public static final String FILE_KOBF = "Kobf.D";
    public static final String FILE_KO2F = "Ko2f.D";
    public static final String FILE_HYBF = "HYBF.D";
    public static final String FILE_HYNM = "HYNM.D";
    public static final String FILE_SHUK = "Shuk.D";
    public static final String FILE_CNPCUS = "CnpCus.D";
    public static final String FILE_GDTL = "Gdtl.D";
    public static final String FILE_GCDF = "Gcdf.D";
    public static final String FILE_GSTP = "Gstp.D";
    public static final String FILE_KOUSER = "Kouser.D";
    public static final String FILE_FUNOUCOMMENT = "FunouCommentf.D";
    public static final String FILE_GEXT = "Gext.D";
    public static final String FILE_GTPC = "Gtpc.D";
    public static final String FILE_ZYKS = "Zyks.D";
    public static final String FILE_KTPC = "Ktpc.D";
    public static final String FILE_KOKFWARI = "Kokfwari.D";
    public static final String FILE_WARIF = "WariF.D";
    public static final String FILE_KOST = "Kost.D";
    public static final String FILE_GIDX = "Gidx.D";
    public static final String FILE_GNMF = "Gnmf.D";
    public static final String FILE_KENK = "Kenk.D";
    public static final String FILE_HANK = "Hank.D";
    public static final String FILE_KOTF = "Kotf.D";
    // --------------------
    // 以下ltas仕様
    // --------------------
    public static final String FILE_SYSREC = "SysRec.D";
    public static final String FILE_NYUHT = "NyuHT.D";
    public static final String FILE_LNKHT = "LNKHT.D";
    public static final String FILE_HM02 = "Hm02.D";

    public static final Map<String, String> MAP_FILE_NAME;
    static{
        MAP_FILE_NAME = new LinkedHashMap<>();
        MAP_FILE_NAME.put(FILE_SYSF, "システムファイル");
        MAP_FILE_NAME.put(FILE_SY2F, "システムファイル２");
        MAP_FILE_NAME.put(FILE_BUSF, "取引区分ファイル");
        MAP_FILE_NAME.put(FILE_HANF, "店舗名称ファイル");
        MAP_FILE_NAME.put(FILE_HINF, "品目名称ファイル");
        MAP_FILE_NAME.put(FILE_HM00, "販売明細(締後）ファイル");
        MAP_FILE_NAME.put(FILE_HM01, "販売明細(締前）ファイル");
        MAP_FILE_NAME.put(FILE_HMHT, "販売明細ファイル");
        MAP_FILE_NAME.put(FILE_HMH2, "拡張販売明細ファイル");
        MAP_FILE_NAME.put(FILE_HTCMT, "コメントファイル");
        MAP_FILE_NAME.put(FILE_IDXF, "顧客インデックスファイル");
        MAP_FILE_NAME.put(FILE_KO2F, "顧客データ２ファイル");
        MAP_FILE_NAME.put(FILE_KOBF, "顧客配送容器ファイル");
        MAP_FILE_NAME.put(FILE_KOKF, "顧客データファイル");
        MAP_FILE_NAME.put(FILE_LESF, "リース明細ファイル");
        MAP_FILE_NAME.put(FILE_SHOF, "商品マスタファイル");
        MAP_FILE_NAME.put(FILE_SHUK, "集計ファイル");
        MAP_FILE_NAME.put(FILE_TNTF, "担当者ファイル");
        MAP_FILE_NAME.put(FILE_CNPCUS, "CNポイントファイル");
        MAP_FILE_NAME.put(FILE_GDTL, "ガス料金詳細ファイル");
        MAP_FILE_NAME.put(FILE_GSTP, "ガス料金ステップファイル");
        MAP_FILE_NAME.put(FILE_GCDF, "ガス料金インデックスファイル");
        MAP_FILE_NAME.put(FILE_KOUSER, "顧客特別仕様ファイル");
        MAP_FILE_NAME.put(FILE_WARIF, "値引設定ファイル");
        MAP_FILE_NAME.put(FILE_KOKFWARI, "顧客値引設定ファイル");
        MAP_FILE_NAME.put(FILE_GTPC, "ガス料金透明化設定ファイル");
        MAP_FILE_NAME.put(FILE_GEXT, "ガス料金拡張ファイル");
        MAP_FILE_NAME.put(FILE_ZYKS, "前年同月使用量ファイル");
        MAP_FILE_NAME.put(FILE_KTPC, "ガス料金内訳ファイル");
        MAP_FILE_NAME.put(FILE_HYNM, "ハイブリッド名称ファイル");
        MAP_FILE_NAME.put(FILE_GIDX, "無線検針用インデックスファイル");
        MAP_FILE_NAME.put(FILE_GNMF, "グループ名称ファイル");
        MAP_FILE_NAME.put(FILE_HYBF, "ハイブリッド料金表ファイル");
        MAP_FILE_NAME.put(FILE_FUNOUCOMMENT, "銀行不能コメントファイル");
        MAP_FILE_NAME.put(FILE_KOST, "顧客別商品単価ファイル");
        MAP_FILE_NAME.put(FILE_KENK, "検針区分ファイル");
        MAP_FILE_NAME.put(FILE_HANK, "販売区分ファイル");
        MAP_FILE_NAME.put(FILE_KOTF, "顧客灯油ファイル");
        MAP_FILE_NAME.put(FILE_SYSREC, "入金割当レコードファイル");
        MAP_FILE_NAME.put(FILE_NYUHT, "入金明細ファイル");
        MAP_FILE_NAME.put(FILE_LNKHT, "入金割当ファイル");
        MAP_FILE_NAME.put(FILE_HM02, "未入金明細ファイル");
        MAP_FILE_NAME.put("PrgChg.Dat", "PrgChg.Datファイル");
        MAP_FILE_NAME.put("receipt.png", "receipt.png");
    }

    public static final String KEY_BROADCAST_RECIEVER_DATA_RECIEVE = "KEY_BROADCAST_RECIEVER_DATA_RECIEVE";
    public static final String KEY_BROADCAST_RECIEVER_STATUS = "KEY_BROADCAST_RECIEVER_STATUS";

    /** ZIPデータ作成先 */
    public static final String ZIP_DIR = "MarutouCompack/Printer/Zip";
}
