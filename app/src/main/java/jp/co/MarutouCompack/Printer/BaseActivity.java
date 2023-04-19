
package jp.co.MarutouCompack.Printer;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.commonClass.PrinterDat.WebData;
import jp.co.MarutouCompack.dialog.GifDialog;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * 検針くんαの基底アクティビティ
 */
public class BaseActivity extends AppCompatActivity {
    /** ログ出力用タグ */
    private static final String TAG = BaseActivity.class.getSimpleName();
    /** レイアウト情報 */
    private final int mLayoutInfo;
    protected String type = "";
    public static WebData mWebData;

    protected static GifDialog gifDialog;

    protected static boolean checkApp = true;

    /**
     * コンストラクタ
     * 
     * @param layoutInfo    [in] int        レイアウト情報
     */
    protected BaseActivity(int layoutInfo) {
        mLayoutInfo = layoutInfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ソフトキーボードを常に非表示にする
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // レイアウト情報を取得
        setContentView(mLayoutInfo);
    }
    
    /**
     * アプリケーションバージョンを取得する.
     *
     * @return String アプリバージョン
     */
    public String getVersion() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo;
        StringBuilder ret = new StringBuilder();
        try {
            packageInfo = packageManager.getPackageInfo("jp.co.MarutouCompack.Printer", PackageManager.GET_INSTRUMENTATION);
            ret.append(" Ver.");
            ret.append(packageInfo.versionName);
            ret.append(" rev");
            ret.append(String.format(Locale.JAPANESE, "%03d", packageInfo.versionCode));
            if( getString(R.string.r_num).length() != 0 ){
                ret.append(" (");
                ret.append(getString(R.string.r_num));
                ret.append(")");
            }
        }
        catch (NameNotFoundException e) {
            MLog.WARN(this, TAG, e);
        }
        return ret.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    /**
     * 設定されているモバイルプリンタ印刷有無を取得.
     * 
     * @return  boolean 印刷有無.
     */
    protected boolean getPrintExecute() {
        return getSharedPreferences("Printer", Context.MODE_PRIVATE).getBoolean("PrintExecute", true);
    }


    /**
     * BitmapデータをPNGファイルとて保存.
     *
     * @param view [in] {@link View}
     */
    protected void saveBitmapToSd(View view) {
        try {
            view.getRootView().setDrawingCacheEnabled(true);
            Bitmap cache = view.getRootView().getDrawingCache();
            if(cache == null){
                MLog.ERROR(this, TAG, "スクリーンキャプチャ失敗");
                return;
            }
            Bitmap bitmap = Bitmap.createBitmap(cache);
            view.setDrawingCacheEnabled(false);
            // sdcardフォルダを指定
            File root = Environment.getExternalStorageDirectory();

            // 日付でファイル名を作成　
            Date mDate = new Date();
            SimpleDateFormat fileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.JAPANESE);

            // 保存処理開始
            FileOutputStream fos = new FileOutputStream(new File(root, fileName.format(mDate) + ".png"));
            // PNGで保存
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            // 保存処理終了
            fos.close();
        } catch (Exception e) {
            MLog.ERROR(this, TAG, e);
        }
    }
}
