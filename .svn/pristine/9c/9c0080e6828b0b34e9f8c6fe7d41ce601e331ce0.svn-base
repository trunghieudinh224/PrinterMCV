
package jp.co.MarutouCompack.baseClass;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import jp.co.MarutouCompack.baseClass.PrintBaseImage.Align;
import jp.co.MarutouCompack.baseClass.PrintBaseImage.TextSize;

/**
 * 行の上から順番にセットして下さい。また、同じ行は同じフォント及び太さでないと印字が崩れます。
 *
 */
@SuppressWarnings("unused")
public class PrintImageList {

    /** 行印字用のリスト */
    private ArrayList<PrintInfo> m_list;
    /** 矩形印字用のリスト */
    private ArrayList<PrintRectInfo> m_rect;
    /** 矩形パッディング */
    public final int RECT_PADDING = 5;
    /** 呼び出し元コンテキスト */
    private final Context mContext;

    /**
     * コンストラクタ.
     *
     * @param ctx   [in] {@link Context}    呼び出し元コンテキス
     */
    public PrintImageList(Context ctx) {
        mContext = ctx;
        m_list = new ArrayList<>();
        m_rect = new ArrayList<>();
    }

    /**
     * 初期化.
     */
    public void clear() {
        m_list = null;
        m_rect = null;
    }

    /**
     * 高さの取得.
     *
     * @return  int 高さ
     */
    private int getHeight() {
        if (m_list == null || m_list.size() == 0) return 0;
        PrintInfo t_printinfo = m_list.get(m_list.size() - 1);
        return t_printinfo.m_nYpos + PrintBaseImage.getHeight(mContext, t_printinfo.m_enumTextSize.getSize(), t_printinfo.m_fBold);
    }

    /**
     * 印字領域の高さの取得.
     *
     * @return  int 印字領域の高さ
     */
    public int getHeightPrintLine() {
        if (m_list == null || m_list.size() == 0) return 0;
        return getHeight() + (m_list.get(m_list.size() - 1)).m_enumTextSize.getSpace();
    }

    /**
     * 印刷データ(Bitmap)の取得.
     *
     * @return  {@link Bitmap}  印字データ
     */
    public Bitmap getBitmap() {
        if (m_list == null || m_list.size() == 0) return null;
        PrintBaseImage t_printbitmap = new PrintBaseImage(PrintBaseImage.DEFAULT_X_SIZE, getHeight() + 20, mContext);
        for (PrintInfo t_printinfo : m_list) {
            t_printbitmap.setText(t_printinfo.m_strLine, t_printinfo.m_nXpos, t_printinfo.m_nYpos, t_printinfo.m_enumTextSize.getSize(),
                    t_printinfo.m_enumAlign, t_printinfo.m_fBold);
        }
        if (m_rect != null && m_rect.size() != 0) {
            for (PrintRectInfo t_printrectinfo : m_rect) {
                t_printbitmap.createRect(t_printrectinfo.m_nXpos, t_printrectinfo.m_nYpos, t_printrectinfo.m_nWidth, t_printrectinfo.m_nHeight,
                        t_printrectinfo.m_fBold);

            }
        }
        return t_printbitmap.getBitmap();
    }
    /**
     * 印字行リストに追加.
     *
     * @param printInfo   {@link PrintInfo}   追加する印字データ
     * @return int[]    印字位置配列 0：開始位置、1：終了位置(スペースを加味した位置を返す)
     */
    private int[] addBase(PrintInfo printInfo) {
        int[] ret = new int[2];
        ret[0] = printInfo.m_nXpos - printInfo.m_enumTextSize.getSpace();
        m_list.add(printInfo);
        ret[1] = getHeight() + printInfo.m_enumTextSize.getSpace();
        return ret;
    }

    /**
     * 文字列を追加.
     *
     * @param strLine       [in] String             印字文字列
     * @param nXpos         [in] int                X軸位置
     * @param nYpos         [in] int                Y軸位置
     * @param enumTextSize  [in] {@link TextSize}   文字サイズ
     * @param enumAlign     [in] {@link Align}      印字位置
     * @param fBold         [in] float              文字列の太さ
     * @return  int[]   印字後の高さ
     */
    public int[] add(String strLine, int nXpos, int nYpos, TextSize enumTextSize, Align enumAlign, float fBold) {
        return addBase(new PrintInfo(strLine, nXpos, nYpos, enumTextSize, enumAlign, fBold));
    }

    /**
     * 文字列の追加.
     *
     * @param strLine   [in] String 印字文字列
     * @param nXpos     [in] int    X軸位置
     * @param nYpos     [in] int    Y軸位置
     * @return  int[]   印字後の高さ
     */
    public int[] add(String strLine, int nXpos, int nYpos) {
        return addBase(new PrintInfo(strLine, nXpos, nYpos));
    }

    /**
     * 大きい文字列の追加.
     *
     * @param strLine   [in] String 印字文字列
     * @param nXpos     [in] int    X軸位置
     * @param nYpos     [in] int    Y軸位置
     * @return  int[]   印字後の高さ
     */
    public int[] addLarge(String strLine, int nXpos, int nYpos) {
        return addBase(new PrintInfo(strLine, nXpos, nYpos, TextSize.LARGE));
    }

    /**
     * 小さい文字列の追加.
     *
     * @param strLine   [in] String 印字文字列
     * @param nXpos     [in] int    X軸位置
     * @param nYpos     [in] int    Y軸位置
     * @return  int[]   印字後の高さ
     */
    public int[] addSmall(String strLine, int nXpos, int nYpos) {
        return addBase(new PrintInfo(strLine, nXpos, nYpos, TextSize.SMALL));
    }

    /**
     * より小さい文字列の追加.
     *
     * @param strLine   [in] String 印字文字列
     * @param nXpos     [in] int    X軸位置
     * @param nYpos     [in] int    Y軸位置
     * @return  int[]   印字後の高さ
     */
    public int[] addXsmall(String strLine, int nXpos, int nYpos) {
        return addBase(new PrintInfo(strLine, nXpos, nYpos, TextSize.XSMALL));
    }

    /**
     * 文字列の追加.
     *
     * @param strLine       [in] String             印字文字列
     * @param nXpos         [in] int                X軸位置
     * @param nYpos         [in] int                Y軸位置
     * @param enumTextSize  [in] {@link TextSize}   文字サイズ
     * @return  int[]   印字後の高さ
     */
    public int[] add(String strLine, int nXpos, int nYpos, TextSize enumTextSize) {
        return addBase(new PrintInfo(strLine, nXpos, nYpos, enumTextSize));
    }

    /**
     * 文字列の追加.
     *
     * @param strLine       [in] String             印字文字列
     * @param nXpos         [in] int                X軸位置
     * @param nYpos         [in] int                Y軸位置
     * @param enumTextSize  [in] {@link TextSize}   文字サイズ
     * @param enumAlign     [in] {@link Align}      印字位置
     * @return  int[]   印字後高さ
     */
    public int[] add(String strLine, int nXpos, int nYpos, TextSize enumTextSize, Align enumAlign) {
        return addBase(new PrintInfo(strLine, nXpos, nYpos, enumTextSize, enumAlign));
    }

    /**
     * 文字列の追加.
     *
     * @param strLine   [in] String 印字文字列
     * @param nXpos     [in] int    X軸位置
     * @param nYpos     [in] int    Y軸位置
     * @param fBold     [in] float  太さ
     * @return  int[]   印字後高さ
     */
    public int[] add(String strLine, int nXpos, int nYpos, float fBold) {
        return addBase(new PrintInfo(strLine, nXpos, nYpos, fBold));
    }

    /**
     * 文字列の追加.
     *
     * @param strLine       [in] String             印字文字列
     * @param nXpos         [in] int                X軸位置
     * @param nYpos         [in] int                Y軸位置
     * @param enumTextSize  [in] {@link TextSize}   印字サイズ
     * @param fBold         [in] float              太さ
     * @return  int[]   印字後高さ
     */
    public int[] add(String strLine, int nXpos, int nYpos, TextSize enumTextSize, float fBold) {
        return addBase(new PrintInfo(strLine, nXpos, nYpos, enumTextSize, fBold));
    }

    /**
     * 矩形の追加.
     *
     * @param printRectInfo [in] {@link PrintRectInfo}  矩形データ
     */
    private void addRectBase(PrintRectInfo printRectInfo) {
        m_rect.add(printRectInfo);
    }

    /**
     * 矩形の追加.
     *
     * @param nXpos     [in] int    X軸位置
     * @param nYpos     [in] int    Y軸位置
     * @param nWidth    [in] int    横幅
     * @param nHeight   [in] int    高さ
     * @param fBold     [in] float  太さ
     */
    public void addRect(int nXpos, int nYpos, int nWidth, int nHeight, float fBold) {
        addRectBase(new PrintRectInfo(nXpos, nYpos, nWidth, nHeight, fBold));
    }

    /**
     * 矩形の追加.
     *
     * @param nXpos     [in] int    X軸位置
     * @param nYpos     [in] int    Y軸位置
     * @param nYposEnd  [in] int    終了Y軸位置
     */
    public void addRect(int nXpos, int nYpos, int nYposEnd) {
        addRectBase(new PrintRectInfo(nXpos, nYpos, PrintBase.RECT_WIDTH, nYposEnd - nYpos));
    }

    /**
     * 横幅固定の矩形の追加.
     *
     * @param nYpos     [in] int    Y軸位置
     * @param nYposEnd  [in] int    終了Y軸位置
     */
    public void addRect(int nYpos, int nYposEnd) {
        addRectBase(new PrintRectInfo(PrintBase.START_TAG, nYpos, PrintBase.RECT_WIDTH, nYposEnd - nYpos));
    }

    /**
     * 横幅固定の太い矩形の追加.
     *
     * @param nYpos     [in] int    Y軸位置
     * @param nYposEnd  [in] int    終了Y軸位置
     */
    public void addRectBold(int nYpos, int nYposEnd) {
        addRectBase(new PrintRectInfo(PrintBase.START_TAG, nYpos, PrintBase.RECT_WIDTH, nYposEnd - nYpos, PrintBaseImage.RECT_STROKE_BOLD));
    }

    /**
     * 太い矩形の追加.
     *
     * @param nXpos     [in] int    X軸位置
     * @param nYpos     [in] int    Y軸位置
     * @param nYposEnd  [in] int    終了Y軸位置
     */
    public void addRectBold(int nXpos, int nYpos, int nYposEnd) {
        addRectBase(new PrintRectInfo(nXpos, nYpos, PrintBase.RECT_WIDTH, nYposEnd - nYpos, PrintBaseImage.RECT_STROKE_BOLD));
    }

    /**
     * 複数の矩形を追加.
     *
     * @param nXpos     [in] int[]  X軸位置
     * @param nYpos     [in] int    Y軸位置
     * @param nYposEnd  [in] int    終了Y軸位置
     */
    public void addRectMulti(int[] nXpos, int nYpos, int nYposEnd) {
        addRectMultiBase(nXpos, nYpos, nYposEnd, PrintBaseImage.RECT_STROKE_NORMAL);
    }

    /**
     * 複数の太い矩形を追加.
     *
     * @param nXpos     [in] int[]  X軸位置
     * @param nYpos     [in] int    Y軸位置
     * @param nYposEnd  [in] int    終了Y軸位置
     */
    public void addRectMultiBold(int[] nXpos, int nYpos, int nYposEnd) {
        addRectMultiBase(nXpos, nYpos, nYposEnd, PrintBaseImage.RECT_STROKE_BOLD);
    }

    /**
     * 複数の枠を追加.
     *
     * @param nXpos     [in] int[]  枠の開始X座標(枠数分指定)
     * @param nYpos     [in] int    枠の開始Y座標
     * @param nYposEnd  [in] int    枠の終了Y座標
     */
    private void addRectMultiBase(int[] nXpos, int nYpos, int nYposEnd, float t_bold) {
        int nXposEnd;
        for (int i = 0; i < nXpos.length; i++) {
            if (i != nXpos.length - 1)
                nXposEnd = nXpos[i + 1];
            else
                nXposEnd = PrintBase.RECT_WIDTH + PrintBase.START_TAG;
            addRectBase(new PrintRectInfo(nXpos[i], nYpos, nXposEnd - nXpos[i], nYposEnd - nYpos, t_bold));
        }

    }

    /**
     * 印字情報
     */
    private static class PrintInfo {
        /** 印字文字列 */
        private final String m_strLine;
        /** X軸位置 */
        private int m_nXpos;
        /** y軸位置 */
        private final int m_nYpos;
        /** 文字サイズ */
        private TextSize m_enumTextSize = TextSize.NORMAL;
        /** 印字位置 */
        private Align m_enumAlign = Align.NONE;
        /**　太さ */
        private float m_fBold;

        /**
         * コンストラクタ.
         *
         * @param strLine       [in] String             印字文字列
         * @param nXpos         [in] int                X軸位置
         * @param nYpos         [in] int                Y軸位置
         * @param enumTextSize  [in] {@link TextSize}   印字サイズ
         * @param enumAlign     [in] {@link Align}      印字位置
         * @param fBold         [in] float              文字サイズ
         */
        private PrintInfo(String strLine, int nXpos, int nYpos, TextSize enumTextSize, Align enumAlign, float fBold) {
            m_strLine = strLine;
            m_nXpos = nXpos;
            m_nYpos = nYpos;
            m_enumTextSize = enumTextSize;
            m_enumAlign = enumAlign;
            m_fBold = fBold;
        }

        /**
         * コンストラクタ.
         *
         * @param strLine   [in] String 印字文字列
         * @param nYpos     [in] int    Y軸位置
         */
        private PrintInfo(String strLine, int nYpos) {
            m_strLine = strLine;
            m_nYpos = nYpos;
        }

        /**
         * コンストラクタ.
         *
         * @param strLine   [in] String 印字文字列
         * @param nXpos     [in] int    X軸位置
         * @param nYpos     [in] int    y軸位置
         */
        private PrintInfo(String strLine, int nXpos, int nYpos) {
            m_strLine = strLine;
            m_nXpos = nXpos;
            m_nYpos = nYpos;
        }

        /**
         * コンストラクタ.
         *
         * @param strLine       [in] String             印字文字列
         * @param nXpos         [in] int                X軸位置
         * @param nYpos         [in] int                Y軸位置
         * @param enumTextSize  [in] {@link TextSize}   文字サイズ
         */
        private PrintInfo(String strLine, int nXpos, int nYpos, TextSize enumTextSize) {
            m_strLine = strLine;
            m_nXpos = nXpos;
            m_nYpos = nYpos;
            m_enumTextSize = enumTextSize;
        }

        /**
         * コンストラクタ.
         *
         * @param strLine   [in] String 印字文字列
         * @param nXpos     [in] int    X軸位置
         * @param nYpos     [in] int    Y軸位置
         * @param fBold     [in] float  文字列太さ
         */
        private PrintInfo(String strLine, int nXpos, int nYpos, float fBold) {
            m_strLine = strLine;
            m_nXpos = nXpos;
            m_nYpos = nYpos;
            m_fBold = fBold;
        }

        /**
         * コンストラクタ.
         *
         * @param strLine       [in] String             印字文字列
         * @param nXpos         [in] int                X軸位置
         * @param nYpos         [in] int                Y軸位置
         * @param enumTextSize  [in] {@link TextSize}   文字列サイズ
         * @param fBold         [in] float              文字列太さ
         */
        private PrintInfo(String strLine, int nXpos, int nYpos, TextSize enumTextSize, float fBold) {
            m_strLine = strLine;
            m_nXpos = nXpos;
            m_nYpos = nYpos;
            m_enumTextSize = enumTextSize;
            m_fBold = fBold;
        }

        /**
         * コンストラクタ.
         *
         * @param strLine       [in] String             印字文字列
         * @param xPos          [in] int                X軸位置
         * @param yPos          [in] int                Y軸位置
         * @param enumTextSize  [in] {@link TextSize}   文字サイズ
         * @param enumAlign     [in] {@link Align}      印字位置
         */
        private PrintInfo(String strLine, int xPos, int yPos, TextSize enumTextSize, Align enumAlign) {
            m_strLine = strLine;
            m_nXpos = xPos;
            m_nYpos = yPos;
            m_enumTextSize = enumTextSize;
            m_enumAlign = enumAlign;
        }
    }

    /**
     * 矩形情報クラス
     */
    private static class PrintRectInfo {
        /**　X軸位置 */
        private final int m_nXpos;
        /** Y軸位置 */
        private final int m_nYpos;
        /** 横幅 */
        private final int m_nWidth;
        /** 高さ */
        private final int m_nHeight;
        /** 太さ */
        private float m_fBold;

        /**
         * コンストラクタ.
         *
         * @param nXpos     [in] int    X軸位置
         * @param nYpos     [in] int    Y軸位置
         * @param nWidth    [in] int    横幅
         * @param nHeight   [in] int    高さ
         * @param fBold     [in] float  太さ
         */
        public PrintRectInfo(int nXpos, int nYpos, int nWidth, int nHeight, float fBold) {
            m_nXpos = nXpos;
            m_nYpos = nYpos;
            m_nWidth = nWidth;
            m_nHeight = nHeight;
            m_fBold = fBold;
        }

        /**
         * コンストラクタ.
         *
         * @param nXpos     [in] int    X軸位置
         * @param nYpos     [in] int    Y軸位置
         * @param nWidth    [in] int    横幅
         * @param nHeight   [in] int    高さ
         */
        public PrintRectInfo(int nXpos, int nYpos, int nWidth, int nHeight) {
            this(nXpos, nYpos, nWidth, nHeight, PrintBaseImage.RECT_STROKE_NORMAL);
        }

    }
}
