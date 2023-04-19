
package jp.co.MarutouCompack.baseClass;

import android.util.Log;

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
public class MException extends Exception {

    /** ログ出力用タグ */
    private static final String TAG = "MException";

    /** エラーコード */
    private int mErrorCode = -1;

    /**
     * コンストラクタ.
     */
    public MException() {
    }

    /**
     * コンストラクタ.
     *
     * @param detailMessage [in] String メッセージ
     */
    public MException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * コンストラクタ.
     *
     * @param throwable [in] {@link Throwable}  例外
     */
    public MException(Throwable throwable) {
        super(throwable);
    }

    /**
     * コンストラクタ.
     *
     * @param detailMessage [in] String             メッセージ
     * @param throwable     [in] {@link Throwable}  例外
     */
    public MException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * コンストラクタ.
     *
     * @param detailMessage [in] String メッセージ
     * @param errorCode     [in] int    エラーコード
     */
    public MException(String detailMessage, int errorCode) {
        super(detailMessage);
        mErrorCode = errorCode;
    }

    /**
     * コンストラクタ.
     *
     * @param ex            [in] {@link Exception}  例外
     * @param detailMessage [in] String             メッセージ
     * @param errorCode     [in] int                エラーコード
     */
    public MException(Exception ex, String detailMessage, int errorCode) {
        super(detailMessage);
        mErrorCode = errorCode;

        StackTraceElement[] wkStackTrace = ex.getStackTrace();
        for (StackTraceElement wkElement : wkStackTrace) {
            Log.e(TAG, wkElement.toString());
        }
    }

    /**
     * エラーコードの取得.
     * 
     * @return  int エラーコード
     */
    public int getErrorCode() {
        return mErrorCode;
    }
}
