package jp.co.MarutouCompack.Printer.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * バックアップデータコピーサービス
 */
public class CopyToSDService extends IntentService {

    // --------------------------------------------------
    // 定数
    // --------------------------------------------------
    /** クラス識別用タグ */
    private static final String TAG = CopyToSDService.class.getSimpleName();
    /** ブロードキャスト通知用アクション */
    public static final String ACTION_BROADCAST_MESSAGE_COPY_DONE = "ACTION_BROADCAST_MESSAGE_COPY_DONE";
    /** ブロードキャストメッセージキー：結果 */
    public static final String KEY_BROADCAST_MESSAGE_COPY_RET = "KEY_BROADCAST_MESSAGE_COPY_RET";
    /** ブロードキャストメッセージキー：メッセージ */
    public static final String KEY_BROADCAST_MESSAGE_COPY_MSG = "KEY_BROADCAST_MESSAGE_COPY_MSG";

    // --------------------------------------------------
    // 変数
    // --------------------------------------------------

    /**
     * コンストラクタ<br />
     * サービス名称の設定
     */
    public CopyToSDService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        boolean ret;
//        String msg = "";
//        try {
//            Log.i(TAG, "SDカードへバックアップデータの保存を開始");
//            // ここでデータのバックアップを実施する
//            OutputDat.copyToSDCard(getBaseContext());
//            Log.i(TAG, "SDカードへバックアップデータの保存が完了");
//            ret = true;
//        }
//        catch (MException mex){
//            Log.e(TAG, "データバックアップ失敗", mex);
//            msg = mex.getLocalizedMessage();
//            ret = false;
//        }
//
//        // ブロードキャストに結果と失敗した場合のメッセージを送信する
//        Intent broadcastIntent = new Intent();
//        broadcastIntent.putExtra(KEY_BROADCAST_MESSAGE_COPY_RET, ret);
//        broadcastIntent.putExtra(KEY_BROADCAST_MESSAGE_COPY_MSG, msg);
//        broadcastIntent.setAction(ACTION_BROADCAST_MESSAGE_COPY_DONE);
//        getBaseContext().sendBroadcast(broadcastIntent);
    }
}

