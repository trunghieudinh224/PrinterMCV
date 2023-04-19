//
//package jp.co.MarutouCompack.commonClass.PrinterDat;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.support.v4.provider.DocumentFile;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Objects;
//
//import jp.co.MarutouCompack.Printer.R;
//import jp.co.MarutouCompack.baseClass.DEFINE;
//import jp.co.MarutouCompack.baseClass.MException;
//import jp.co.MarutouCompack.baseClass.OtherUtil;
//import jp.co.MarutouCompack.commonClass.ErrorDialog;
//import jp.co.MarutouCompack.commonClass.HTDat.KobfDat.YOUKI;
//import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;
//
///**
// * データ書き込みクラス.
// */
//public class OutputDat {
//
//    /** ログ出力用タグ */
//    private static final String TAG = OutputDat.class.getSimpleName();
//
//    /**
//     * 書込み用データの生成.
//     *
//     * @param ctx           [in] {@link Context}    呼び出し元コンテキス
//     * @param baseDat       [in] T                  書込み対象クラス
//     * @param size          [in] int                1レコードのサイズ
//     * @param lstExclusioin [in] String[]           無視するメンバ変数名一覧
//     * @param <T>                                   {@link BaseDat}を継承したクラス
//     * @return  byte[]  書込み用データ
//     * @throws Exception    書込み用データ生成処理でエラーがあった場合に発生.
//     */
//    private static <T extends BaseDat> byte[] createData(Context ctx, T baseDat,int size, String[] lstExclusioin) throws Exception{
//        return createData(ctx, baseDat, size, lstExclusioin, false);
//    }
//
//    /**
//     * 各データを指定したbyte配列に変換する
//     *
//     * @param ctx           [in] Context    書き込むデータ
//     * @param baseDat       [in] T          BaseDatを継承したデータ
//     * @param size          [in] int        書込みデータサイズ
//     * @param lstExclusion  [in] String[]   除外する変数名のリスト
//     * @throws Exception データ作成時にエラーがあった場合に発生
//     */
//    private static <T extends BaseDat> byte[] createData(Context ctx, T baseDat, int size, String[] lstExclusion, boolean isToyukensinFlg) throws Exception {
//        // データ読み出し用に各メンバ変数の読出し開始位置とサイズの一覧を取得。
//        HashMap<String, int[]> wkMember = baseDat.getMemberInfo(isToyukensinFlg);
//        // 各データのメンバ変数を取得する。
//        Field[] wkField = baseDat.getClass().getFields();
//
//        // メンバ変数をインデックス順に並び替える
//        Field[] wkIdxField = new Field[wkMember.size()];
//        for(Field field : wkField){
//            if (field.getName().equals("CREATOR") ||
//                    field.getName().equals("CONTENTS_FILE_DESCRIPTOR") ||
//                    field.getName().equals("PARCELABLE_WRITE_RETURN_VALUE") ||
//                    field.getName().equals("PARCELABLE_ELIDE_DUPLICATES") ||
//                    field.getName().equals("PARCELABLE_STABILITY_LOCAL") ||
//                    field.getName().equals("PARCELABLE_STABILITY_VINTF")) {
//                continue;
//            }
//            if(lstExclusion != null){
//                boolean isExclusion = false;
//                for(String strExclusion : lstExclusion){
//                    if(field.getName().equals(strExclusion)){
//                        isExclusion = true;
//                        break;
//                    }
//                }
//                if(isExclusion){
//                    continue;
//                }
//            }
//            int wkInt = Objects.requireNonNull(wkMember.get(field.getName()))[2];
//            wkIdxField[wkInt] = field;
//        }
//
//        // 書き込むデータを準備
//        byte[] wkBytedat = new byte[size];
//        int wkIdx;
//        for (int i = 0; i < wkIdxField.length; i++) {
//            if(wkIdxField[i] == null){
//                continue;
//            }
//            int[] wkInt = wkMember.get(wkIdxField[i].getName());
//            // 書き込む長さ
//            int length = Objects.requireNonNull(wkInt)[1];
//            // 書き込む値
//            Object wkValue = wkIdxField[i].get(baseDat);
//            // 書き込む場所
//            wkIdx = wkInt[0];
//
//            if (wkIdxField[i].getType() == byte.class) {
//                // byte型の値を書き込む
//                wkBytedat[wkIdx] = (Byte) wkValue;
//            }
//            else if (wkIdxField[i].getType() == short.class) {
//                // short型の値を書き込む
//                OtherUtil.bytedatSetShort(wkBytedat, wkIdx, (Short) wkValue);
//            }
//            else if (wkIdxField[i].getType() == int.class) {
//                // int型の値を書き込む
//                OtherUtil.bytedatSetInt(wkBytedat, wkIdx, (Integer) wkValue);
//            }
//            else if (wkIdxField[i].getType() == long.class) {
//                // long型の値を書き込む
//                OtherUtil.bytedatSetLong(wkBytedat, wkIdx, (Long) wkValue);
//            }
//            else if (wkIdxField[i].getType() == double.class) {
//                // dobule型の値を書き込む
//                OtherUtil.bytedatSetDouble(wkBytedat, wkIdx, (Double) wkValue);
//            }
//            else if (wkIdxField[i].getType() == String.class) {
//                // String型の値を書き込む
//                OtherUtil.bytedatSetBytes(wkBytedat, wkIdx, ((String) wkValue).getBytes("SJIS"));
//            }
//            else if (wkIdxField[i].getType() == boolean.class) {
//                // boolean型の値を書き込む
//                byte wkByte = wkIdxField[i].getBoolean(baseDat) ? (byte) 1 : (byte) 0;
//                wkBytedat[wkIdx] = wkByte;
//            }
//            else if (wkIdxField[i].getType() == int[].class) {
//                // int型配列の値を書き込む
//                int wkSize = length / 4;
//                int[] wkArrayInt = (int[]) wkValue;
//                for (int j = 0; j < wkSize; j++) {
//                    OtherUtil.bytedatSetInt(wkBytedat, wkIdx, wkArrayInt[j]);
//                    wkIdx += 4;
//                }
//            }
//            else if (wkIdxField[i].getType() == long[].class) {
//                // long型配列の値を書き込む
//                int wkSize = length / 4;
//                long[] wkArrayLong = (long[]) wkValue;
//                for (int j = 0; j < wkSize; j++) {
//                    OtherUtil.bytedatSetLong(wkBytedat, wkIdx, wkArrayLong[j]);
//                    wkIdx += 4;
//                }
//            }
//            else if (wkIdxField[i].getType() == short[].class) {
//                // short型配列の値を書き込む
//                int wkSize = length / 2;
//                short[] wkArrayShort = (short[]) wkValue;
//                for (int j = 0; j < wkSize; j++) {
//                    OtherUtil.bytedatSetShort(wkBytedat, wkIdx, wkArrayShort[j]);
//                    wkIdx += 2;
//                }
//            }
//            else if (wkIdxField[i].getType() == byte[].class) {
//                // byte型配列の値を書き込む
//                OtherUtil.bytedatSetBytes(wkBytedat, wkIdx, (byte[]) wkValue);
//            }
//            else if (wkIdxField[i].getType() == YOUKI[].class) {
//                // YOUKI型配列の値を書き込む
//                YOUKI[] wkArrayYouki = (YOUKI[]) wkValue;
//                for(YOUKI youki : wkArrayYouki){
//                    OtherUtil.bytedatSetShort(wkBytedat, wkIdx, youki.mUmu);
//                    wkIdx += 2;
//                    OtherUtil.bytedatSetBytes(wkBytedat, wkIdx, youki.mYouki.getBytes("SJIS"));
//                    wkIdx += 11;
//                    wkIdx += 3; // 予備分インデックスを進める
//                }
//            }
//            else if(wkField[i].getType() == java.util.Date.class){
//                // java.util.Date型
//                GregorianCalendar cal = new GregorianCalendar();
//                cal.setTime((java.util.Date)wkValue);
//                OtherUtil.bytedatSetShort(wkBytedat, wkIdx, (short)cal.get(Calendar.YEAR));
//                wkBytedat[wkIdx++] = (byte)cal.get(Calendar.MONTH);
//                wkBytedat[wkIdx] = (byte)cal.get(Calendar.DAY_OF_MONTH);
//            }
//            else {
//                MLog.WARN(ctx, TAG, "Unknown field type: " + wkField[i].getType().getName());
//            }
//        }
//        return wkBytedat;
//    }
//
//    /**
//     * 指定されたファイルにデータを書き込む
//     *
//     * @param ctx       [in] Context    呼び出し元アクティビティ
//     * @param writeDat  [in] byte[]     書き込みデータ
//     * @param fileName  [in] String     書込み先ファイル名
//     * @param idx       [in] int        書込みインデックス
//     * @throws Exception データ書込み時にエラーがあった場合に発生
//     */
//    private static void writeDataFile(Context ctx, byte[] writeDat, String fileName, int idx) throws Exception {
//
//        // 書き込み先ファイル名がnullの場合
//        if (fileName.equals("")) {
//            MLog.ERROR(ctx, TAG, "書き込み先ファイル名が不明です。");
//            throw new Exception("書き込み先ファイル名が不明です。");
//        }
//        // 書込みインデックスが負数の場合
//        if (idx < 0) {
//            MLog.ERROR(ctx, TAG, "書き込みインデックスが負数です。");
//            throw new Exception("書込みインデックスが負数です。");
//        }
//
//        // ファイル出力
//        // オフセット値
//        int wkOffset = idx * writeDat.length;
//
//        File wkDir = new File(DEFINE.getDataPath(ctx));
//        if(!wkDir.exists()){
//            if(!wkDir.mkdirs()){
//                MLog.WARN(ctx, TAG, "ディレクトリの作成に失敗:" + wkDir.getAbsolutePath());
//            }
//        }
//        File wkFile = new File(wkDir, fileName);
//        if(wkFile.exists() && wkFile.isDirectory()){
//            if(!wkFile.delete()){
//                MLog.INFO(ctx, TAG, "ディレクトリの削除に失敗");
//            }
//        }
//        RandomAccessFile wkRandomFile = null;
//
//        try {
//            wkRandomFile = new RandomAccessFile(wkFile, "rw");
//            wkRandomFile.seek(wkOffset);
//            wkRandomFile.write(writeDat);
//        }
//        catch (FileNotFoundException fnfe) {
//            MLog.ERROR(ctx, TAG, "ファイルが見つかりませんでした" + DEFINE.getDataPath(ctx) + fileName);
//            throw fnfe;
//        }
//        catch (IllegalArgumentException iae) {
//            MLog.ERROR(ctx, TAG, "ファイル書き込み中にエラーが発生しました。");
//            throw iae;
//        }
//        catch (Exception ex) {
//            MLog.ERROR(ctx, TAG, "ファイル書き込み中に不明なエラーが発生しました: " + ex.getLocalizedMessage());
//            throw ex;
//        }
//        finally {
//            try {
//                if (wkRandomFile != null) {
//                    wkRandomFile.close();
//                }
//            }
//            catch (IOException ioe) {
//                MLog.WARN(ctx, TAG, ioe);
//            }
//        }
//    }
//
//    /**
//     * SDカードにバックアップデータとして保存する。
//     *
//     * @param ctx   [in] Context 呼び出し元コンテキスト
//     */
//    public static void copyToSDCard(Context ctx) throws MException {
//        DocumentFile sdRoot = OtherUtil.getSdRootDirectory(ctx);
//        if(sdRoot == null){
//            throw new MException("SDカードが端末に挿入されていません。");
//        }
//        // sysfを試し読み。読み込みできなければバックアップとらない
//        try {
//            InputDat.getSysfDat(ctx);
//        }
//        catch (MException ex) {
//            throw new MException("システムデータの読込みに失敗しました。");
//        }
//        try {
//            SharedPreferences pref = ctx.getSharedPreferences("Printer", Activity.MODE_PRIVATE);
//            if(pref.getBoolean("KEY_ACTION_SETTING_BACKUP", true)){
//                // 検針毎バックアップ設定の場合は全データをＳＤのバックアップ領域にバックアップ
//                OtherUtil.dataBackup(ctx, null);
//            }
//            OtherUtil.copyData(ctx, DEFINE.getDataPath(ctx), DEFINE.SD_DATA_DIRECTORY);
//        }
//        catch (IOException e) {
//            throw new MException("バックアップの作成が正常に行われませんでした。 \r\n" + e.getMessage());
//        }
//    }
//
//    /**
//    * 検針時リース売上計上(入力確定時に呼び出し)
//    *
//    * @param ctx    [in] Context    呼び出し元コンテキスト
//    * @throws MException データ書き込み時にエラーがあった場合に発生
//    */
//    public static void writeLesDat(Context ctx, LesfDat lesfDat) throws MException {
//        String fileName = "Lesf.D";
//        try {
//            byte[] wkByte = createData(ctx, lesfDat, 128, new String[]{"m_nRec"});
//            writeDataFile(ctx, wkByte, fileName, lesfDat.m_nRec - 1);
//        }
//        catch (MException mex) {
//            throw mex;
//        }
//        catch (Exception ex) {
//            throw new MException(ex, ctx.getResources().getString(R.string.ERROR_GASRATE_CALC_LEAS), ErrorDialog.ERROR_GASRATE_CALC_LEAS);
//        }
//    }
//}
