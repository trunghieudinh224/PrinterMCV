
package jp.co.MarutouCompack.baseClass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;

@SuppressWarnings("unused")
public class PrintBaseImage {

    // *************************************************************
    // 定数
    // *************************************************************
    /** デフォルト横サイズ */
    public static final int DEFAULT_X_SIZE = 576;
    /** デフォルト縦サイズ */
    public static final int DEFAULT_Y_SIZE = 256;
    /** デフォルト改行量 */
    public static final int DEFAULT_LINE_HEIGHT = 35;
    /** 文字サイズ */
    public enum TextSize {
        HMSMALL(16, 1),
        XXSMALL(14, 1),
        XSMALL(22, 1),
        SMALL(28, 1),
        NORMAL(30, 2),
        LARGE(48, 5);
        int size; // 文字サイズ
        int space; // 文字間隔

        TextSize(int t_size, int t_space) {
            this.size = t_size;
            this.space = t_space;
        }

        public int getSize() {
            return size;
        }

        public int getSpace() {
            return space;
        }

    }
    /** デフォルトのx文字位置 */
    public static final int DEFAULT_X_POSITION = 0;
    /** デフォルトのx文字位置 */
    public static final int DEFAULT_Y_POSITION = 0;
    /** 印字位置 */
    public enum Align {
        NONE(0),
        LEFT(1),
        CENTER(2),
        RIGHT(3);
        int value;

        Align(int t_value) {
            this.value = t_value;
        }

        public int getValue() {
            return value;
        }
    }

    /** 文字の太さ: ノーマル */
    public static final float STROKE_WIDTH_NORMAL = 0;
    /** 文字の太さ: 太い */
    public static final float STROKE_WIDTH_BOLD = (float) 0.8;

    /** 線の太さ: ノーマル */
    public static final float RECT_STROKE_NORMAL = (float) 2;
    /** 線の太さ: 太い */
    public static final float RECT_STROKE_BOLD = (float) 5;

    /** ログ出力用タグ */
    private static final String TAG = "PrintBitmap";

    /** ビット反転テーブル */
    public static final int[] bit_reverse = {
            0x00, 0x80, 0x40, 0xc0, 0x20, 0xa0, 0x60, 0xe0, 0x10, 0x90, 0x50, 0xd0, 0x30, 0xb0, 0x70, 0xf0, 0x08, 0x88, 0x48, 0xc8, 0x28, 0xa8, 0x68,
            0xe8, 0x18, 0x98, 0x58, 0xd8, 0x38, 0xb8, 0x70, 0xf8, 0x04, 0x84, 0x44, 0xc4, 0x24, 0xa4, 0x64, 0xe4, 0x14, 0x94, 0x54, 0xd4, 0x34, 0xb4,
            0x74, 0xf4, 0x0c, 0x8c, 0x4c, 0xcc, 0x2c, 0xac, 0x6c, 0xec, 0x1c, 0x9c, 0x5c, 0xdc, 0x3c, 0xbc, 0x7c, 0xfc, 0x02, 0x82, 0x42, 0xc2, 0x22,
            0xa2, 0x62, 0xe2, 0x12, 0x92, 0x52, 0xd2, 0x32, 0xb2, 0x72, 0xf2, 0x0a, 0x8a, 0x4a, 0xca, 0x2a, 0xaa, 0x6a, 0xea, 0x1a, 0x9a, 0x5a, 0xda,
            0x3a, 0xba, 0x7a, 0xfa, 0x06, 0x86, 0x46, 0xc6, 0x26, 0xa6, 0x66, 0xe6, 0x16, 0x96, 0x56, 0xd6, 0x36, 0xb6, 0x76, 0xf6, 0x0e, 0x8e, 0x4e,
            0xce, 0x2e, 0xae, 0x6e, 0xee, 0x1e, 0x9e, 0x5e, 0xde, 0x3e, 0xbe, 0x7e, 0xfe, 0x01, 0x81, 0x41, 0xc1, 0x21, 0xa1, 0x61, 0xe1, 0x11, 0x91,
            0x51, 0xd1, 0x31, 0xb1, 0x71, 0xf1, 0x09, 0x89, 0x49, 0xc9, 0x29, 0xa9, 0x69, 0xe9, 0x19, 0x99, 0x59, 0xd9, 0x39, 0xb9, 0x79, 0xf9, 0x05,
            0x85, 0x45, 0xc5, 0x25, 0xa5, 0x65, 0xe5, 0x15, 0x95, 0x55, 0xd5, 0x35, 0xb5, 0x75, 0xf5, 0x0d, 0x8d, 0x4d, 0xcd, 0x2d, 0xad, 0x6d, 0xed,
            0x1d, 0x9d, 0x5d, 0xdd, 0x3d, 0xbd, 0x7d, 0xfd, 0x03, 0x83, 0x43, 0xc3, 0x23, 0xa3, 0x63, 0xe3, 0x13, 0x93, 0x53, 0xd3, 0x33, 0xb3, 0x73,
            0xf3, 0x0b, 0x8b, 0x4b, 0xcb, 0x2b, 0xab, 0x6b, 0xeb, 0x1b, 0x9b, 0x5b, 0xdb, 0x3b, 0xbb, 0x7b, 0xfb, 0x07, 0x87, 0x47, 0xc7, 0x27, 0xa7,
            0x67, 0xe7, 0x17, 0x97, 0x57, 0xd7, 0x37, 0xb7, 0x77, 0xf7, 0x0f, 0x8f, 0x4f, 0xcf, 0x2f, 0xaf, 0x6f, 0xef, 0x1f, 0x9f, 0x5f, 0xdf, 0x3f,
            0xbf, 0x7f, 0xff,
    };

    // *************************************************************
    // 変数
    // *************************************************************
    /** 描画を行うビットマップデータ */
    private final Bitmap mBitmap;

    /** 詳細な描画処理を行うキャンバス */
    private final Canvas mCanvas;

    /** 呼び出し元コンテキスト */
    private final Context mContext;

    // *************************************************************
    // メソッド
    // *************************************************************
    /**
     * コンストラクタ.
     *
     * @param ctx   [in] {@link Context}    呼び出し元コンテキスト
     */
    public PrintBaseImage(Context ctx) {
        this(DEFAULT_X_SIZE, DEFAULT_Y_SIZE, ctx);
    }

    /**
     * コンストラクタ<br />
     * 指定したサイズのBitmapを作成し、canvasに登録する.
     * 
     * @param x [in] int    bitmapの横サイズ
     * @param y [in] int    bitmapの縦サイズ
     */
    public PrintBaseImage(int x, int y, Context ctx) {
        // Bitmapデータ作成
        mBitmap = Bitmap.createBitmap(x, y, Bitmap.Config.RGB_565);
        mContext = ctx;

        // CanvasにBitmapを登録
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.WHITE);
    }

    /**
     * 作成したビットマップデータを取得する
     * 
     * @return {@link Bitmap} 印字データ
     */
    public Bitmap getBitmap() {
        return mBitmap;
    }

    /**
     * テキストの描画を行う.<br />
     * 
     * @param text  [in] String         描画する文字列
     * @param x     [in] int            描画位置:x
     * @param y     [in] int            描画位置:y
     * @param size  [in] int            文字サイズ
     * @param align [in] {@link Align}  文字の配置
     * @param bold  [in] float          文字の太さ
     * @return float 文字列の高さ
     */
    public float setText(String text, int x, int y, int size, Align align, float bold) {
        // 描画位置がbitmapの大きさより大きい場合
        if (mBitmap.getWidth() < x || mBitmap.getHeight() < y) {
            // 描画位置をデフォルトに設定し、テキストにエラーを追記する
            text += " Position error!";
            x = DEFAULT_X_POSITION;
            y = DEFAULT_Y_POSITION;
        }

        // テキストサイズを設定
        Paint wkPaint = new Paint();
        // 太さを設定
        // bold == 0 ならば太字設定を実施しない
        if (bold == 0) {
            wkPaint.setStyle(Paint.Style.FILL);
        }
        else {
            wkPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            wkPaint.setStrokeWidth(bold);
        }

        // 色を黒に固定
        wkPaint.setColor(Color.BLACK);
        // アンチエイリアス有効
        wkPaint.setAntiAlias(false);
        // テキストサイズを設定
        wkPaint.setTextSize(size);
        // フォントを設定
        Typeface wkType = Typeface.createFromAsset(mContext.getAssets(), "fonts/lucon.ttf");
        wkPaint.setTypeface(Typeface.create(wkType, Typeface.NORMAL));

        // 文字列の描画位置が指定されている場合は横位置の設定を無視する
        switch (align) {
            case CENTER: // センター
                x = (int) (mBitmap.getWidth() / 2 - wkPaint.measureText(text) / 2);
                break;
            case LEFT: // 左より
                x = 0;
                break;
            case RIGHT: // 右より
                x = (int) (mBitmap.getWidth() - 20 - wkPaint.measureText(text));
                break;
            default: // 何もしない
                break;
        }

        // キャンバスに描画
        mCanvas.drawText(text, x, y - wkPaint.getFontMetrics().ascent, wkPaint);
        return getHeight(wkPaint);
    }

    /**
     * 印字領域の高さの取得.
     *
     * @param wkPaint   [in] {@link Paint}  ペイント
     * @return  int 高さ
     */
    private static int getHeight(Paint wkPaint) {
        return (int) (wkPaint.getFontMetrics().bottom - wkPaint.getFontMetrics().top + 5);
    }

    /**
     * 高さの取得.
     *
     * @param ctx   [in] {@link Context}    呼び出し元コンテキスト
     * @param size  [in] int                文字サイズ
     * @param bold  [in] float              文字の太さ
     * @return  int 印字領域の高さ
     */
    public static int getHeight(Context ctx, int size, float bold) {
        // テキストサイズを設定
        Paint wkPaint = new Paint();
        // 太さを設定       
        if (bold == 0) {// bold == 0 ならば太字設定を実施しない
            wkPaint.setStyle(Paint.Style.FILL);
        }
        else {
            wkPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            wkPaint.setStrokeWidth(bold);
        }
        // テキストサイズを設定
        wkPaint.setTextSize(size);
        // フォントを設定
        Typeface wkType = Typeface.createFromAsset(ctx.getAssets(), "fonts/lucon.ttf");
        wkPaint.setTypeface(Typeface.create(wkType, Typeface.NORMAL));
        return getHeight(wkPaint);
    }

    /**
     * 矩形を描画する
     *
     * @param x_pos     [in] int    X位置
     * @param y_pos     [in] int    Y位置
     * @param width     [in] int    横幅
     * @param height    [in] int    高さ
     * @param bold      [in] float  矩形の太さ
     */
    public void createRect(int x_pos, int y_pos, int width, int height, float bold) {
        float wkX;
        float wkY;
        float wkWidth;
        float wkHeight;

        // 矩形の4点を設定
        wkX = x_pos;
        wkY = y_pos;
        wkWidth = x_pos + width;
        wkHeight = y_pos + height;

        // RectFインスタンスを作成
        RectF wkRect = new RectF(wkX, wkY, wkWidth, wkHeight);

        // 描画設定
        Paint wkPaint = new Paint();
        // 枠線中塗り無し
        wkPaint.setStyle(Paint.Style.STROKE);
        // 線の太さ設定
        wkPaint.setStrokeWidth(bold);
        // 線の色設定
        wkPaint.setColor(Color.BLACK);

        // キャンバスに矩形描画
        mCanvas.drawRect(wkRect, wkPaint);
    }

    /**
     * テキストの横幅を返す.
     *
     * @param ctx           [in] Context    呼び出し元コンテキスト
     * @param wkText        [in] String     対象の文字列
     * @param wkTextsize    [in] TextSize   文字サイズ
     * @param wkBold        [in] float      文字の太さ
     * @return float 文字列の横幅
     */
    public static float getValuePosition(Context ctx, String wkText, TextSize wkTextsize, float wkBold) {
        Paint wkPaint = new Paint();
        if (wkBold == STROKE_WIDTH_NORMAL) {
            wkPaint.setStyle(Paint.Style.FILL);
        }
        else {
            wkPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            wkPaint.setStrokeWidth(wkBold);
        }
        // アンチエイリアス有効
        wkPaint.setAntiAlias(true);
        // テキストサイズを設定
        wkPaint.setTextSize(wkTextsize.getSize());
        // フォントを設定
        Typeface wkType = Typeface.createFromAsset(ctx.getAssets(), "fonts/lucon.ttf");
        wkPaint.setTypeface(Typeface.create(wkType, Typeface.NORMAL));
        return wkPaint.measureText(wkText);
    }
}
