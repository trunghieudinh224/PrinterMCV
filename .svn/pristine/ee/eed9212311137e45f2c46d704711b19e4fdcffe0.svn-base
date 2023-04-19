
package jp.co.MarutouCompack.Printer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.saneielec.libs.PrinterSDK;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.baseClass.PrintBase;
import jp.co.MarutouCompack.baseClass.PrintKensinInterface;
import jp.co.MarutouCompack.baseClass.PrintKensinListInterface;
import jp.co.MarutouCompack.baseClass.PrintShukeiInterface;
import jp.co.MarutouCompack.baseClass.PrintShukinInterface;
import jp.co.MarutouCompack.baseClass.PrintShukinListInterface;
import jp.co.MarutouCompack.baseClass.PrintUriageInterface;
import jp.co.MarutouCompack.baseClass.PrintUriageListInterface;
import jp.co.MarutouCompack.commonClass.PrintKensinCharactor;
import jp.co.MarutouCompack.commonClass.PrintShukeiImage;
import jp.co.MarutouCompack.commonClass.PrintShukinCharactor;
import jp.co.MarutouCompack.commonClass.PrintUriageCharactor;
import jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KensinData;
import jp.co.MarutouCompack.commonClass.PrinterDat.PrintGenuriInfo;
import jp.co.MarutouCompack.commonClass.PrinterDat.PrintStatus;
import jp.co.MarutouCompack.commonClass.PrinterDat.Sy2fDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysOption;
import jp.co.MarutouCompack.commonClass.PrintKensinImage;
import jp.co.MarutouCompack.commonClass.PrintKensinListCharactor;
import jp.co.MarutouCompack.commonClass.PrintKensinListImage;
import jp.co.MarutouCompack.commonClass.PrintShukinImage;
import jp.co.MarutouCompack.commonClass.PrintShukinListCharactor;
import jp.co.MarutouCompack.commonClass.PrintShukinListImage;
import jp.co.MarutouCompack.commonClass.PrintUriageImage;
import jp.co.MarutouCompack.commonClass.PrintUriageListCharactor;
import jp.co.MarutouCompack.commonClass.PrintUriageListImage;
import jp.co.MarutouCompack.commonClass.PrinterDat.ShukeiKensinData;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UriageItemDat;
import jp.co.MarutouCompack.dialog.GifDialog;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

public class BasePrintActivity extends BaseActivity {
    /** クラス識別用タグ */
    private static final String TAG = BasePrintActivity.class.getSimpleName();

    /**
     * コンストラクタ.
     * 
     * @param nLayoutInfo    [in] int        レイアウト情報
     */
    protected BasePrintActivity(int nLayoutInfo) {
        super(nLayoutInfo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 領収書発行.
     */
    public static class StartKensinPrint extends AsyncTask<PrintStatus, Void, Boolean>{
        /** クラス識別用タグ */
        private static final String TAG = StartShukinPrint.class.getSimpleName();
        /** 進捗ダイアログ */
        private GifDialog dialog = null;
        /** 呼び出し元アクティビティ */
        private final WeakReference<Activity> mReferenceActivity;

        private PrintStatus mPrintStatus;
        private boolean bIsHikae;

        /**
         * コンストラクタ.
         *
         * @param activity  [in] {@link Activity}
         * @param printStatus  [in] PrintStatus
         * @param isHikae  [in] boolean
         */
        public StartKensinPrint(Activity activity, PrintStatus printStatus, boolean isHikae){
            mReferenceActivity = new WeakReference<>(activity);
            mPrintStatus = printStatus;
            bIsHikae = isHikae;
        }

        @Override
        protected void onPreExecute() {
            MLog.DEBUG(mReferenceActivity.get(), TAG, "##########モバイルプリンタ：領収書印刷開始");
            dialog = new GifDialog(mReferenceActivity.get(), R.drawable.gif_printing, "印刷しています。。。");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(PrintStatus... printStatus) {
            // 印刷モード確認
            SharedPreferences preferences = mReferenceActivity.get().getSharedPreferences("Printer", MODE_PRIVATE);
            int nPrintMode = preferences.getInt("PrintMode", PrintBase.PRINT_MODE_IMAGE);


            // 印刷モードにより生成するインスタンスを変更する
            PrintKensinInterface ifPrintKensin;

            try {
                if (nPrintMode == PrintBase.PRINT_MODE_IMAGE) {
                    // 画像イメージの印刷
                    ifPrintKensin = new PrintKensinImage(mReferenceActivity.get());
                }
                else{
                    // 文字列データの印刷
                    ifPrintKensin = new PrintKensinCharactor(mReferenceActivity.get());
                }

                // ヘッダー
                ifPrintKensin.createPrintData(mWebData.getUserData(),mPrintStatus.m_lReceipt != 0, bIsHikae);
                ifPrintKensin.createCusInfo(mWebData.getCusData());
                KensinData wkKensinData = mWebData.getKensinData();

                ifPrintKensin.createKensinInfo(mWebData.getUserData(), wkKensinData);

                if(mPrintStatus.m_isPrintNyukin) {
                    if (mPrintStatus.m_lReceipt > 0) {
                        ifPrintKensin.createRyoshu(OtherUtil.KingakuFormat(mPrintStatus.m_lReceipt));
                    }
                }

                if(!mWebData.getUserData().isNyukinMode()) {
                    if (mWebData.getUserData().getKokfDat().getBankCode() != 0) {
                        ifPrintKensin.createBank(mWebData.getUserData());
                    }

                    // 振替不能コメント
                    ifPrintKensin.createFunouComment(mWebData.getUserData());
                }

                if(!mWebData.getUserData().isNyukinMode() || !mWebData.getUserData().isNyukinOnly()) {
                    if (mWebData.getUserData().getSy2fDat().getSysOption()[SysOption.PRINT_HANMEISAI.getIdx()] == 0) {
                        ifPrintKensin.createHmInfo(mWebData.getUserData());
                    }
                }
                if (mWebData.getUserData().getSy2fDat().getSysOption()[SysOption.PRINT_COMMENT.getIdx()] == 1) {
                    ifPrintKensin.createComment(mWebData.getLstComment());
                }

                if(!mWebData.getUserData().isNyukinMode()) {
                    if(mPrintStatus.m_isPrintKensin) {
                        ifPrintKensin.createHybComment(mWebData.getKensinData());

                        //HieuNote chưa làm tới hybrid
                        ifPrintKensin.createHybTblPrint(mWebData.getKensinData());

                        if (mPrintStatus.m_isPrintHoan) {
                            if (mWebData.getUserData().getKokfDat().getNoKensin() == 0) { // 13.01.16
                                // 保安点検
                                if (mWebData.getUserData().getSysfDat().isCheckHoan() && mWebData.getUserData().getSy2fDat().getSysOption()[SysOption.PRINT_HOAN.getIdx()] == 1) { // 13.07.10
                                    ifPrintKensin.createHoanInfo(mWebData.getUserData().isNyukinMode(), mWebData.getUserData().getKokfDat().getHoan());
                                }
                            }
                        }
                    }

                    // 標準ポイント
                    ifPrintKensin.createPoint(mWebData.getUserData());
                    // 宮野式ポイント
                    ifPrintKensin.createMiyaPoint(mWebData.getUserData());

                    ifPrintKensin.createCnComment(mWebData.getKensinData());
                }

                // 店舗データ
                if (mWebData.getUserData().getSysfDat().ifChitUser()) {
                    ifPrintKensin.createUserInfo(mWebData.getUserData().getHanfDat(), mWebData.getTantname(), mWebData.getUserData().getSysfDat(), true);
                }

                MLog.DEBUG(mReferenceActivity.get(), TAG, "##########モバイルプリンタ：印刷データ生成完了");
                ifPrintKensin.printExecute();
            }
            catch (MException e) {
                MLog.ERROR(mReferenceActivity.get(), TAG, e);
            }
            return bIsHikae;
        }

        @Override
        protected void onPostExecute(Boolean isHikae) {
            if (dialog != null) {
                dialog.dismiss();
            }

            SharedPreferences pref = mReferenceActivity.get().getSharedPreferences("Printer", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("IsPrinting", false);
            editor.apply();
            mWebData = null;
            mReferenceActivity.get().finish();
        }
    }


    /**
     * モバイルプリンタ生存確認クラス
     * 
     * @author 株式会社マルトウコンパック
     */
    public static class PrintOnline extends AsyncTask<Void, Void, Boolean> {
        /** クラス識別用タグ */
        private static final String TAG = PrintOnline.class.getSimpleName();
        /** 呼び出し元アクティビティ(弱い参照) */
        private WeakReference<Activity> mReferenceActivity;
        /** ハイブリッド請求フラグ */
        private boolean m_isHybseikyu;
        /** 進捗ダイアログ */
        private GifDialog dialog = null;
        /** 現金売り情報 */
        private PrintGenuriInfo mPrintGenuriInfo;



        /**
         * モバイルプリンター疎通確認.
         *
         * @param activity          [in] {@link Activity}           呼び出し元アクティビティ
         * @param isHybseikyu       [in] boolean                    ハイブリッド請求フラグ
         * @param printGenuriInfo   [in] {@link PrintGenuriInfo}    納品書情報
         */
        public PrintOnline(Activity activity, boolean isHybseikyu, PrintGenuriInfo printGenuriInfo) {
            mReferenceActivity = new WeakReference<>(activity);
            m_isHybseikyu = isHybseikyu;
            mPrintGenuriInfo = printGenuriInfo;
        }

        public PrintOnline(Activity activity, PrintGenuriInfo printGenuriInfo) {
            mReferenceActivity = new WeakReference<>(activity);
            mPrintGenuriInfo = printGenuriInfo;
        }

        public PrintOnline(Activity activity, boolean isHybseikyu) {
            mReferenceActivity = new WeakReference<>(activity);
            m_isHybseikyu = isHybseikyu;
        }

        @Override
        protected void onPreExecute() {
            MLog.DEBUG(mReferenceActivity.get(), TAG, "##########モバイルプリンタ：検索開始");
            if (mWebData != null) {
                dialog = new GifDialog(mReferenceActivity.get(), R.drawable.gif_searching, "プリンタ確認中。。。");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();

                // ビープ音を鳴らす
                ToneGenerator wkToneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM, 50);
                wkToneGenerator.startTone(ToneGenerator.TONE_PROP_NACK);
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // プリンタ生存確認
            SharedPreferences wkPref = mReferenceActivity.get().getSharedPreferences("Printer", MODE_PRIVATE);
            return ((BasePrintActivity)mReferenceActivity.get()).isPrinterOnline(wkPref.getString("PrinterMAC", ""), this);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (dialog != null) {
                dialog.dismiss();
            }

            SharedPreferences pref = mReferenceActivity.get().getSharedPreferences("Printer", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (!mBluetoothAdapter.isEnabled()) {
                MainMenuActivity.viewHolder.statusTxt.setText("BluetoothをONにしてください");
                MainMenuActivity.viewHolder.statusTxt.setTextColor(mReferenceActivity.get().getResources().getColor(R.color.textColor));
                MainMenuActivity.viewHolder.printerNameTxt.setTextColor(mReferenceActivity.get().getResources().getColor(R.color.textColor));
            } else {
                if (result) {
                    MainMenuActivity.viewHolder.statusTxt.setText("オンライン");
                    MainMenuActivity.viewHolder.statusTxt.setTextColor(mReferenceActivity.get().getResources().getColor(R.color.onlineStatus));
                    MainMenuActivity.viewHolder.printerNameTxt.setTextColor(mReferenceActivity.get().getResources().getColor(R.color.onlineStatus));
                    if (mWebData != null  && pref.getBoolean("IsPrinting", false) == false) {
                        // 見つかったらビープ音を2回鳴らす
                        ToneGenerator wkToneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM, 50);
                        wkToneGenerator.startTone(ToneGenerator.TONE_PROP_NACK);
                        if (mWebData.getType().equals("kensin") || mWebData.getType().equals("toyu")) {
                            new StartKensinPrint(mReferenceActivity.get(), mWebData.getPrintStatus(), false).execute(mWebData.getPrintStatus());
                        } else if (mWebData.getType().equals("nyukin")) {
                            new StartShukinPrint(mReferenceActivity.get()).execute(false);
                        } else if (mWebData.getType().equals("shukei") ||
                                mWebData.getType().equals("kensin_nippou") ||
                                mWebData.getType().equals("shukin_nippou") ||
                                mWebData.getType().equals("uriage_nippou")) {
                            new StartPrintData(mReferenceActivity.get(), m_isHybseikyu, false).execute(false);
                        } else if (mWebData.getType().equals("uriage")) {
                            new StartUriagePrint(mReferenceActivity.get(), mPrintGenuriInfo).execute(false);
                        }
                        editor.putBoolean("IsPrinting", true);
                        editor.apply();
                        return;
                    }
                }
                else {
                    MainMenuActivity.viewHolder.statusTxt.setText("オフライン");
                    MainMenuActivity.viewHolder.statusTxt.setTextColor(mReferenceActivity.get().getResources().getColor(R.color.textColor));
                    MainMenuActivity.viewHolder.printerNameTxt.setTextColor(mReferenceActivity.get().getResources().getColor(R.color.textColor));
                    editor.putBoolean("IsPrinting", false);
                    editor.apply();
                }
            }

            if (checkApp == true) {
                ((BasePrintActivity)mReferenceActivity.get()).searchingPrinter();
            }
            MLog.DEBUG(mReferenceActivity.get(), TAG, "##########モバイルプリンタ：検索完了");
        }

        @Override
        protected void onCancelled() {
            dialog.dismiss();
        }
    }

    /**
     * 接続先のモバイルプリンターがオンライン状態か確認する。
     * 
     * @param portName      [in] String                 プリンターポート名
     * @param printOnline   [in] {@link PrintOnline}    モバイルプリンター接続確認クラス
     * @return boolean  接続先のモバイルプリンターがオンラインの場合はtrue, オフラインや取得に失敗した場合はfalseを返す。
     */
    private boolean isPrinterOnline(String portName, PrintOnline printOnline) {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        SharedPreferences pref = getSharedPreferences("Printer", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (mBluetoothAdapter.isEnabled()) {
            editor.putBoolean("showMess", false);
            editor.apply();
        } else {
            return false;
        }

        int nPrintStatus;
        boolean isResult = true;

        try {
            MLog.DEBUG(BasePrintActivity.this, TAG, "##########モバイルプリンタ：接続開始");
            // プリンターに接続されていない時のみ再接続
            nPrintStatus = PrinterSDK.getStatus();
            if (nPrintStatus == -1) {
                // モバイルプリンターに接続
                while (!PrinterSDK.btConnect(portName)) {
                    if (printOnline.isCancelled()) {
                        PrinterSDK.btClose();
                        return false;
                    }

                    nPrintStatus = PrinterSDK.getStatus();
                    if (nPrintStatus != 0) { // 正常以外
                        return false;
                    }
                }
            }
            MLog.DEBUG(BasePrintActivity.this, TAG, "##########モバイルプリンタ：接続完了");

            // プリンタ状態を取得
            nPrintStatus = PrinterSDK.getStatus();
            if (nPrintStatus != 0) { // 正常以外
                isResult = false;
            }

            try {
                Thread.sleep(500);
            }
            catch (InterruptedException ie) {
                MLog.WARN(this, TAG, ie);
            }
        }
        catch (IllegalArgumentException | NullPointerException iae) {
            isResult = false;
            MLog.ERROR(this, TAG, iae);
        }
        return isResult;
    }


    /**
     * 納品書発行クラス.
     */
    private static class StartUriagePrint extends AsyncTask<Boolean, Void, Boolean>{
        /** クラス識別用タグ */
        private static final String TAG = StartUriagePrint.class.getSimpleName();
        /** 進捗ダイアログ */
        private GifDialog dialog = null;
        private final WeakReference<Activity> mReferenceActivity;
        private final PrintGenuriInfo m_prntGenuriInfo;

        /**
         * コンストラクタ.
         *
         * @param activity          [in] {@link Activity}           呼び出し元
         * @param printGenuriInfo   [in] {@link PrintGenuriInfo}    納品書情報
         */
        private StartUriagePrint(Activity activity, PrintGenuriInfo printGenuriInfo){
            mReferenceActivity = new WeakReference<>(activity);
            m_prntGenuriInfo = printGenuriInfo;
        }

        @Override
        protected void onPreExecute() {
            MLog.DEBUG(mReferenceActivity.get(), TAG, "##########モバイルプリンタ：納品書印刷開始");
            dialog = new GifDialog(mReferenceActivity.get(), R.drawable.gif_printing, "印刷しています。。。");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Boolean... isHikae) {
            // 印刷モード確認
            SharedPreferences preferences = mReferenceActivity.get().getSharedPreferences("Printer", Context.MODE_PRIVATE);
            int wkPrintMode = preferences.getInt("PrintMode", PrintBase.PRINT_MODE_IMAGE);

            // 印刷モードにより生成するインスタンスを変更する
            PrintUriageInterface ifPrintUriage;

            try {
                if (wkPrintMode == PrintBase.PRINT_MODE_IMAGE) {
                    // 画像イメージの印刷
                    ifPrintUriage = new PrintUriageImage(mReferenceActivity.get());
                }
                else {
                    // 文字列データの印刷
                    ifPrintUriage = new PrintUriageCharactor(mReferenceActivity.get());
                }

                SysfDat wkSysfDat = mWebData.getUserData().getSysfDat();
                ifPrintUriage.createHeader(mWebData.isHikae(), m_prntGenuriInfo.isGenuri(), mWebData.getUserData().getKensinDate());

                // 顧客情報
                ifPrintUriage.createCusInfo(mWebData.getUserData(), m_prntGenuriInfo.getSname_0(), m_prntGenuriInfo.getSname_1());
                Sy2fDat sy2fDat = mWebData.getUserData().getSy2fDat();

                // 売上明細
                ifPrintUriage.createMeisaiInfo(mWebData.getUserData(), m_prntGenuriInfo.getLstHmefDat());

                if(m_prntGenuriInfo.isGenuri()) {
                    // 領収
                    long lSeikyu = 0L;
                    for (HmefDat hmefDat : m_prntGenuriInfo.getLstHmefDat()) {
                        if (hmefDat.isUsef() && hmefDat.getHmCode() >= wkSysfDat.getSnvalue()) {
                            lSeikyu += hmefDat.getKin() + hmefDat.getTax();
                        }
                    }
                    ifPrintUriage.createRyoshu(mWebData.getUserData(), m_prntGenuriInfo.getChokin(), m_prntGenuriInfo.getNyukin(), m_prntGenuriInfo.getReceipt(), lSeikyu);
                }

                // コメント
                if (sy2fDat.getSysOption()[SysOption.PRINT_COMMENT_NOUHIN.getIdx()] == 1) {
                    ifPrintUriage.createComment(mWebData.getLstComment());
                }

                // 店舗データ
                if (wkSysfDat.ifChitUser()) {
                    ifPrintUriage.createUserInfo(mWebData.getUserData().getHanfDat(), mWebData.getTantname(), mWebData.getUserData().getSysfDat(), true);
                }

                MLog.DEBUG(mReferenceActivity.get(), TAG, "##########モバイルプリンタ：納品書印刷データ生成完了");
                ifPrintUriage.printExecute();
            }
            catch (MException e) {
                MLog.ERROR(mReferenceActivity.get(), TAG, e);
            }
            return mWebData.isHikae();
        }

        @Override
        protected void onPostExecute(Boolean isHikae) {
            if (dialog != null) {
                dialog.dismiss();
            }

            SharedPreferences pref = mReferenceActivity.get().getSharedPreferences("Printer", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("IsPrinting", false);
            editor.apply();
            mWebData = null;
            mReferenceActivity.get().finish();
        }
    }

    /**
     * 領収書発行.
     */
    private static class StartShukinPrint extends AsyncTask<Boolean, Void, Boolean>{
        /** クラス識別用タグ */
        private static final String TAG = StartShukinPrint.class.getSimpleName();
        /** 進捗ダイアログ */
        private GifDialog dialog = null;
        /** 呼び出し元アクティビティ */
        private final WeakReference<Activity> mReferenceActivity;

        /**
         * コンストラクタ.
         *
         * @param activity  [in] {@link Activity}
         */
        private StartShukinPrint(Activity activity){
            mReferenceActivity = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            MLog.DEBUG(mReferenceActivity.get(), TAG, "##########モバイルプリンタ：領収書印刷開始");
            dialog = new GifDialog(mReferenceActivity.get(), R.drawable.gif_printing, "印刷しています。。。");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Boolean... isHikae) {
            // 印刷モード確認
            SharedPreferences preferences = mReferenceActivity.get().getSharedPreferences("Printer", Context.MODE_PRIVATE);
            int wkPrintMode = preferences.getInt("PrintMode", PrintBase.PRINT_MODE_IMAGE);

            // 印刷モードにより生成するインスタンスを変更する
            PrintShukinInterface ifPrintShukin;

            try {
                if (wkPrintMode == PrintBase.PRINT_MODE_IMAGE) {
                    // 画像イメージの印刷
                    ifPrintShukin = new PrintShukinImage(mReferenceActivity.get());
                }
                else {
                    // 文字列データの印刷
                    ifPrintShukin = new PrintShukinCharactor(mReferenceActivity.get());
                }

                // ヘッダー
                ifPrintShukin.createHeaderData(isHikae[0], mWebData.getUserData().getKensinDate());
                // 顧客情報
                ifPrintShukin.createCusInfo(mWebData.getUserData());
                // 金額
                ifPrintShukin.createRyosyuInfo(mWebData);
                if(!mWebData.getUserData().isNyukinOnly()) {
                    // 明細
                    ifPrintShukin.createHmInfo(mWebData.getUserData());
                }
                // 領収印
                ifPrintShukin.createRyoshu(OtherUtil.KingakuFormat(mWebData.getPrintStatus().getReceipt()));

                // コメント
                if (mWebData.getUserData().getSy2fDat().getSysOption()[SysOption.PRINT_COMMENT_RYOSHU.getIdx()] == 1) {
                    ifPrintShukin.createComment(mWebData.getLstComment());
                }

                // 販売店
                if (mWebData.getUserData().getSysfDat().ifChitUser()) {
                    ifPrintShukin.createUserInfo(mWebData.getUserData().getHanfDat(), mWebData.getTantname(), mWebData.getUserData().getSysfDat(), true);
                }

                MLog.DEBUG(mReferenceActivity.get(), TAG, "##########モバイルプリンタ：領収書印刷データ生成完了");
                ifPrintShukin.printExecute();
            }
            catch (MException e) {
                MLog.ERROR(mReferenceActivity.get(), TAG, e);
            }
            return isHikae[0];
        }

        @Override
        protected void onPostExecute(Boolean isHikae) {
            if (dialog != null) {
                dialog.dismiss();
            }

            SharedPreferences pref = mReferenceActivity.get().getSharedPreferences("Printer", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("IsPrinting", false);
            editor.apply();
            mWebData = null;
            mReferenceActivity.get().finish();
        }
    }

    /**
     * 検針伝票印刷クラス
     */
    private static class StartPrintData extends AsyncTask<Boolean, Void, Void> {
        /** クラス識別用タグ */
        private static final String TAG = StartPrintData.class.getSimpleName();
        /** 呼び出し元アクティビティ */
        final WeakReference<Activity> mReferenceActivity;
        /** 進捗ダイアログ */
        private GifDialog dialog = null;
        /** ハイブリッド請求フラグ */
        final boolean m_isHybseikyu;
        /** 控えflag */
        final boolean m_isHikae;

        /**
         * コンストラクタ.
         *
         * @param activity      [in] {@link Activity}   呼び出し元アクティビティ
         * @param isHybseikyu   [in] boolean            ハイブリッド請求フラグ
         * @param isHikae       [in] boolean            控えflag
         */
        public StartPrintData(Activity activity, boolean isHybseikyu, boolean isHikae) {
            mReferenceActivity = new WeakReference<>(activity);
            m_isHybseikyu = isHybseikyu;
            m_isHikae = isHikae;
        }

        @Override
        protected void onPreExecute() {
            MLog.DEBUG(mReferenceActivity.get(), TAG, "##########モバイルプリンタ：検針伝票印刷開始");
            dialog = new GifDialog(mReferenceActivity.get(), R.drawable.gif_printing, "印刷しています。。。");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Boolean... params) {
            SharedPreferences preferences = mReferenceActivity.get().getSharedPreferences("Printer", Context.MODE_PRIVATE);
            int nPrintMode = preferences.getInt("PrintMode", PrintBase.PRINT_MODE_IMAGE);

            // 印刷モードにより生成するインスタンスを変更する
            try {
                switch (((BasePrintActivity)mReferenceActivity.get()).type) {
                    case "shukei":
                        PrintShukeiInterface ifPrintShukei = new PrintShukeiImage(mReferenceActivity.get());
                        ifPrintShukei.setShukeiData(mWebData.getShukeiDat());
                        ifPrintShukei.createPrintData(mWebData.getShukeiDat().getShukeiDate(), mWebData.getTantname());
                        ifPrintShukei.printExecute();
                        break;
                    case "kensin_nippou":
                        PrintKensinListInterface ifPrintKensinList = null;
                        switch (nPrintMode) {
                            case PrintBase.PRINT_MODE_CHAR:
                                ifPrintKensinList = new PrintKensinListCharactor(mReferenceActivity.get());
                                break;
                            case PrintBase.PRINT_MODE_IMAGE:
                                ifPrintKensinList = new PrintKensinListImage(mReferenceActivity.get());
                                break;
                        }

                        if (ifPrintKensinList != null) {
                            Map<String, List<ShukeiKensinData>> mapKensinData = mWebData.getNippouDat().getMapKensinData();
                            ifPrintKensinList.createPrintData(mapKensinData, false, mWebData.getTantname());    //mWebData.getUserData().getSysfDat().isToyukensinFlg()
                            ifPrintKensinList.printExecute();
                        }
                        break;
                    case "shukin_nippou":
                        PrintShukinListInterface ifPrintShukinList = null;
                        switch (nPrintMode) {
                            case PrintBase.PRINT_MODE_CHAR:
                                ifPrintShukinList = new PrintShukinListCharactor(mReferenceActivity.get());
                                break;
                            case PrintBase.PRINT_MODE_IMAGE:
                                ifPrintShukinList = new PrintShukinListImage(mReferenceActivity.get());
                                break;
                        }
                        if (ifPrintShukinList != null) {
                            Map<String, List<ShukeiKensinData>> mapKensinData = mWebData.getNippouDat().getMapKensinData();
                            ifPrintShukinList.createPrintData(mapKensinData, mWebData.getTantname());
                            ifPrintShukinList.printExecute();
                        }
                        break;
                    case "uriage_nippou":
                        PrintUriageListInterface ifPrintUriageList = null;
                        switch (nPrintMode) {
                            case PrintBase.PRINT_MODE_CHAR:
                                ifPrintUriageList = new PrintUriageListCharactor(mReferenceActivity.get());
                                break;
                            case PrintBase.PRINT_MODE_IMAGE:
                                ifPrintUriageList = new PrintUriageListImage(mReferenceActivity.get());
                                break;
                        }
                        if (ifPrintUriageList != null) {
                            Map<String, List<UriageItemDat>> mapUriageData = mWebData.getNippouDat().getMapUriageData();
                            ifPrintUriageList.createPrintData(mapUriageData, mWebData.getTantname());
                            ifPrintUriageList.printExecute();
                        }
                        break;
                    default:
                        break;
                }
            }
            catch (MException e) {
                e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            MLog.DEBUG(mReferenceActivity.get(), TAG, "##########モバイルプリンタ：検針伝票印刷完了");
            if (dialog != null) {
                dialog.dismiss();
            }
            mWebData = null;
            mReferenceActivity.get().finish();
        }

        @Override
        protected void onCancelled() {
            dialog.dismiss();
        }
    }


    protected void startPrintOnline(boolean isHybseikyu) {
        new PrintOnline(this, isHybseikyu).execute();
    }

    protected void startPrintOnline(PrintGenuriInfo printGenuriInfo) {
        new PrintOnline(this, printGenuriInfo).execute();
    }

    protected void searchingPrinter() {
        new PrintOnline(this, true).execute();
    }


    protected void CheckPrint(){
        if (mWebData == null) {
            searchingPrinter();
        } else {
            if (mWebData.getType().equals("kensin")) {
                mWebData.getUserData().setNyukinOnly(false);
                startPrintOnline(mWebData.isHybseikyu());
            } else if (mWebData.getType().equals("toyu")) {
                startPrintOnline(mWebData.isHybseikyu());
            } else if (mWebData.getType().equals("nyukin")) {
                startPrintOnline(mWebData.isHybseikyu());
            } else if (mWebData.getType().equals("uriage")) {
                startPrintOnline(mWebData.getPrintGenuriInfo());
            }
        }
    }
}
