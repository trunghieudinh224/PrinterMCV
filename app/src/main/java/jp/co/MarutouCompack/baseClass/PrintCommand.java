
package jp.co.MarutouCompack.baseClass;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
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
@SuppressWarnings("unused")
public class PrintCommand {

    // ------------------------------------------------------------------
    // 定数
    // ------------------------------------------------------------------
    // Debug
    /** ログ出力用タグ */
    private static final String TAG = "PrintCommand";

    // 国際文字
    /** 　国際文字:アメリカ */
    public static final byte COUNTRY_CHARSET_USA = 0x00;
    /** 国際文字:フランス */
    public static final byte COUNTRY_CHARSET_FRANCE = 0x01;
    /** 国際文字:ドイツ */
    public static final byte COUNTRY_CHARSET_GERMANY = 0x02;
    /** 国際文字:イギリス */
    public static final byte COUNTRY_CHARSET_ENGLAND = 0x03;
    /** 国際文字:デンマーク1 */
    public static final byte COUNTRY_CHARSET_DENMARK_1 = 0x04;
    /** 国際文字:スウェーデン */
    public static final byte COUNTRY_CHARSET_SWEDEN = 0x05;
    /** 国際文字:イタリア */
    public static final byte COUNTRY_CHARSET_ITALY = 0x06;
    /** 国際文字:スペイン */
    public static final byte COUNTRY_CHARSET_SPAIN = 0x07;
    /** 国際文字:日本 */
    public static final byte COUNTRY_CHARSET_JAPAN = 0x08;
    /** 国際文字:ノルウェー */
    public static final byte COUNTRY_CHARSET_NORWAY = 0x09;
    /** 国際文字:デンマーク2 */
    public static final byte COUNTRY_CHARSET_DENMARK_2 = 0x0A;

    // 文字コードテーブル
    /** 文字コードテーブル: PC437 */
    public static final byte CHARSET_TABLE_USA_EUROPE = 0x00;
    /** 文字コードテーブル:　カナ */
    public static final byte CHARSET_TABLE_KANA = 0x01;
    /** 文字コードテーブル: PC850 */
    public static final byte CHARSET_TABLE_MULTI = 0x2;
    /** 文字コードテーブル: PC860 */
    public static final byte CHARSET_TABLE_PORT = 0x3;
    /** 文字コードテーブル: PC863 */
    public static final byte CHARSET_TABLE_CANADA = 0x4;
    /** 文字コードテーブル: PC865 */
    public static final byte CHARSET_TABLE_NORDIC = 0x5;

    // 文字フォント
    /** 文字フォントA */
    public static final byte SELECT_FONT_A = 0x00;
    /** 文字フォントB */
    public static final byte SELECT_FONT_B = 0x01;
    /** 文字フォントC */
    public static final byte SELECT_FONT_C = 0x02;

    // 拡大倍率
    /** 縦倍拡大率: 1倍 */
    public static final byte CHARACTOR_HEIGHT_SIZE_1 = 0x00;
    /** 縦倍拡大率: 2倍 */
    public static final byte CHARACTOR_HEIGHT_SIZE_2 = 0x01;
    /** 縦倍拡大率: 3倍 */
    public static final byte CHARACTOR_HEIGHT_SIZE_3 = 0x02;
    /** 縦倍拡大率: 4倍 */
    public static final byte CHARACTOR_HEIGHT_SIZE_4 = 0x03;
    /** 縦倍拡大率: 5倍 */
    public static final byte CHARACTOR_HEIGHT_SIZE_5 = 0x04;
    /** 縦倍拡大率: 6倍 */
    public static final byte CHARACTOR_HEIGHT_SIZE_6 = 0x05;
    /** 縦倍拡大率: 7倍 */
    public static final byte CHARACTOR_HEIGHT_SIZE_7 = 0x06;
    /** 縦倍拡大率: 8倍 */
    public static final byte CHARACTOR_HEIGHT_SIZE_8 = 0x07;
    /** 横倍拡大率: 1倍 */
    public static final byte CHARACTOR_WIDTH_SIZE_1 = 0x00;
    /** 横倍拡大率: 2倍 */
    public static final byte CHARACTOR_WIDTH_SIZE_2 = 0x10;
    /** 横倍拡大率: 3倍 */
    public static final byte CHARACTOR_WIDTH_SIZE_3 = 0x20;
    /** 横倍拡大率: 4倍 */
    public static final byte CHARACTOR_WIDTH_SIZE_4 = 0x30;
    /** 横倍拡大率: 5倍 */
    public static final byte CHARACTOR_WIDTH_SIZE_5 = 0x40;
    /** 横倍拡大率: 6倍 */
    public static final byte CHARACTOR_WIDTH_SIZE_6 = 0x50;
    /** 横倍拡大率: 7倍 */
    public static final byte CHARACTOR_WIDTH_SIZE_7 = 0x60;
    /** 横倍拡大率: 8倍 */
    public static final byte CHARACTOR_WIDTH_SIZE_8 = 0x70;

    // 位置揃え
    /** 位置揃え:　左揃え */
    public static final byte ALIGN_LEFT = 0x00;
    /** 位置揃え: 中央寄せ */
    public static final byte ALIGN_CENTER = 0x01;
    /** 位置揃え: 右揃え */
    public static final byte ALIGN_RIGHT = 0x02;

    // 印字方向
    /** 印字方向: 左から右 */
    public static final byte DIRECTION_LEFT_TO_RIGHT = 0x00;
    /** 印字方向: 下から上 */
    public static final byte DIRECTION_BOTTOM_TO_TOP = 0x01;
    /** 印字方向: 右から左 */
    public static final byte DIRECTION_RIGHT_TO_LEFT = 0x02;
    /** 印字方向: 上から下 */
    public static final byte DIRECTION_TOP_TO_BOTTOM = 0x03;

    // テキストサイズ
    /** Font size Small */
    public static final float FONT_SIZE_SMALL = 12;
    /** Font size Medium */
    public static final float FONT_SIZE_MEDIUM = 16;
    /** Font size Large */
    public static final float FONT_SIZE_LARGE = 24;

    // ------------------------------------------------------------------
    // 印字コマンド
    // ------------------------------------------------------------------
    /**
     * 印字及び改行<br />
     * プリントバッファー内のデータを印字し、設定されている改行量に基づき1行紙送りをする<br />
     * 実行後は、行の先頭を次の印字開始位置とする。
     * 
     * @return  byte    コマンド
     */
    public static byte commandLf() {
        return 0x0A;
    }

    /**
     * 印字及び紙送り(ドット単位)<br />
     * プリントバッファ内のデータを印字し、nドット分(1/8mm)の紙送りをする。
     * 
     * @param wkDot [in] byte   紙送りドット数( 0 ≦ wkDot ≦ 255 )
     * @return  byte[]  コマンド
     */
    public static byte[] commandFeedDot(byte wkDot) {
        byte[] wkByte = new byte[] {
                0x1B, 0x4A, 0x00
        };
        if (wkDot >= 0) {
            wkByte[2] = wkDot;
        }
        return wkByte;
    }

    /**
     * 印字及びn行の紙送り<br />
     * プリントバッファ内のデータを印字し、n行の紙送りをする。<br />
     * このコマンドは初期改行量及び改行量の設定によって設定されている改行量×n行の紙送りを行う。
     * 
     * @param wkLine    [in] byte   紙送り行数
     * @return  byte[]  コマンド
     */
    public static byte[] commandFeedLine(byte wkLine) {
        byte[] wkByte = new byte[] {
                0x1B, 0x64, 0x00
        };
        if (wkLine >= 0) {
            wkByte[2] = wkLine;
        }
        return wkByte;
    }

    /**
     * ページモードの印字とスタンダードモードへの復帰<br />
     * プリントバッファ内のデータを印字し、スタンダードモードへ復帰する。<br />
     * 展開したデータは印字後すべて消去される。<br />
     * ページモードにおける印字領域の設定により設定した印字領域は初期化される。<br />
     * 実行後は、行の先頭を次の印字開始位置とする。<br />
     * ページモード選択時のみ有効である。
     * 
     * @return  byte[]  コマンド
     */
    public static byte[] commandSwichStdMode() {
        return new byte[] {
                0x1D, 0x0C
        };
    }

    /**
     * ページモードのデータ印字<br />
     * ページモードにおいて全印字領域に展開したデータの一括印字をする。<br />
     * ページモード選択時のみ有効となる。<br />
     * 印字後、下記情報は保持される。<br />
     * a. 展開したデータ<br />
     * b. ページモードにおける文字の印字方向の選択<br />
     * c. ページモードにおける印字領域の設定 d. 文字展開位置
     * 
     * @return  byte[]  コマンド
     */
    public static byte[] commandPagePrint() {
        return new byte[] {
                0x1B, 0x0C
        };
    }

    // ------------------------------------------------------------------
    // 改行量のコマンド
    // ------------------------------------------------------------------
    /**
     * 初期改行量の設定<br />
     * 1行あたりの改行量を34ドット(約3.75mm)にする。<br />
     * スタンダードモードとページモードに、それぞれ独立した改行量が設定可能。
     * 
     * @return  byte[]  コマンド
     */
    public static byte[] commandIndentionDefault() {
        return new byte[] {
                0x1B, 0x32
        };
    }

    /**
     * 改行量の設定<br />
     * 1行あたりの改行量をnドットに設定する。<br />
     * スタンダードモードとページモードに、それぞれ独立した改行量が設定可能。
     * 
     * @param wkByte    [in] byte   改行量(ドット)( 0 ≦ n ≦ 255 )
     * @return  byte[]  コマンド
     */
    public static byte[] commandIndention(byte wkByte) {
        byte[] wkBytes = new byte[] {
                0x1B, 0x33, 0x00
        };
        wkBytes[2] = wkByte;
        return wkBytes;
    }

    // ------------------------------------------------------------------
    // 文字コマンド
    // ------------------------------------------------------------------
    /**
     * 文字の右スペース量を設定<br />
     * ANK文字の右スペース量をnドットに設定する。<br />
     * 1) 倍幅拡大文字指定時の文字の右スペース量は、通常字の2倍となる。<br />
     * 文字の横方向倍率が2倍以上のときは、右スペース量も倍率に応じて大きくなる。<br />
     * 2) スタンダードモードとページモードに、それぞれ独立した右スペース量の設定が可能。<br />
     * 3) この設定はANK文字にのみ有効。
     * 
     * @param wkMargin  [in] byte   右スペース量(ドット)( 0 ≦ n ≦ 255 )
     * @return  byte[]  コマンド
     */
    public static byte[] commandCharMarginRight(byte wkMargin) {
        byte[] wkByte = new byte[] {
                0x1B, 0x20, 0x00
        };
        wkByte[2] = wkMargin;
        return wkByte;
    }

    /**
     * 国際文字の選択<br />
     * 各国の文字セットを選択する。<br />
     * 0: アメリカ、1: フランス、 2: ドイツ、3: イギリス、4: デンマーク１、 5: スウェーデン、6: イタリア、7: スペイン、8:
     * 日本、9: ノルウェー、10: デンマーク2
     * 
     * @param wkCharset [in] byte   国際文字セット( 0 ≦ wkCharset ≦ 10 )
     * @return  byte[]  コマンド
     */
    public static byte[] commandSelectInternationalCharset(byte wkCharset) {
        byte[] wkByte = new byte[] {
                0x1B, 0x52, 0x00
        };
        wkByte[2] = wkCharset;
        return wkByte;
    }

    /**
     * 文字コードテーブルの選択<br />
     * 文字コード表を選択する。<br />
     * 英数字(20H(10進数 32)から7FH(10進数 127))は、各ページ同じ<br />
     * 拡張文字(80H(10進数 128)からFFH(10進数 255)は、各ページ異なる<br />
     * 0: Page 0[PC437 (USA, Standard Europe)]<br />
     * 1: Page 1[カタカナ]<br />
     * 2: Page 2[Multilingual PC850] 3: Page 3[Portuguese PC860] 4: Page
     * 4[Canadian-French PC863] 5: Page 5[Nordic PC865]
     * 
     * @param wkTable   [in] byte   文字コードテーブル
     * @return  byte[]  コマンド
     */
    public static byte[] commandSelectCharsetTable(byte wkTable) {
        byte[] wkByte = new byte[] {
                0x1B, 0x75, 0x00
        };
        if (0 <= wkTable && wkTable <= 5) {
            wkByte[2] = wkTable;
        }
        return wkByte;
    }

    /**
     * 印字モードの一括指定<br />
     * 印字モードを以下のように設定する。<br />
     * 1) 縦倍拡大と横倍拡大が指定されているときは、4倍角で印字。<br />
     * 2) アンダーラインはESC SP(文字の右スペース)、FS S(漢字のスペース量を設定)を含む文字幅全てに付加。<br />
     * ただし、HT(水平タブ)等によりスキップした部分には付加されない。<br />
     * 3) アンダーラインの太さは文字に関係なく、ESC -(アンダーラインの指定・解除)で設定された太さ<br />
     * 4) アンダーラインは、ESC -(アンダーラインの指定・解除)でも実行できるが、最後に処理したコマンドが有効となる。<br />
     * 5) 文字サイズはGS !(文字サイズの指定)でも実行できるが、最後に処理したコマンドが有効となる。
     * 
     * @param wkFont    [in] byte       フォントを設定(0: 文字フォントA、1: 文字フォントB、2: 文字フォントC)
     * @param wkBold    [in] boolean    強調印字の設定(true: 強調印字する、false: 強調印字しない)
     * @param wkHeight  [in] boolean    縦倍拡大の設定(true: 縦倍拡大する、false: 縦倍拡大しない)
     * @param wkWidth   [in] boolean    横倍拡大の設定(true: 横倍拡大する、false: 横倍拡大しない)
     * @param wkUnder   [in] boolean    アンダーラインの設定(true: アンダーライン指定する、false: アンダーライン指定しない)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetFontMode(byte wkFont, boolean wkBold, boolean wkHeight, boolean wkWidth, boolean wkUnder) {
        byte[] wkBytes = new byte[] {
                0x1B, 0x21, 0x00
        };
        byte wkByte = 0x00;

        // フォント選択
        wkByte |= wkFont;

        // 強調印字
        wkByte |= wkBold ? 0x08 : 0x00;

        // 縦倍拡大
        wkByte |= wkHeight ? 0x10 : 0x00;

        // 横倍拡大
        wkByte |= wkWidth ? 0x20 : 0x00;

        // アンダーライン
        wkByte |= wkUnder ? 0x80 : 0x00;

        wkBytes[2] = wkByte;
        return wkBytes;
    }

    /**
     * 印字モードの一括指定<br />
     *
     * @param isMulti  [in] boolean    縦倍設定
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetFontMode(boolean isMulti) {
        return commandSetFontMode((byte) 0, false, isMulti, isMulti, false);
    }

    /**
     * シフトJIS時の印字モードの一括指定<br />
     * 印字モードを以下のように設定する。<br />
     * 1) 縦倍拡大と横倍拡大が指定されているときは、4倍角で印字。<br />
     * 2) アンダーラインはESC SP(文字の右スペース)、FS S(漢字のスペース量を設定)を含む文字幅全てに付加。<br />
     * ただし、HT(水平タブ)等によりスキップした部分には付加されない。<br />
     * 3) アンダーラインの太さは文字に関係なく、ESC -(アンダーラインの指定・解除)で設定された太さ<br />
     * 4) アンダーラインは、ESC -(アンダーラインの指定・解除)でも実行できるが、最後に処理したコマンドが有効となる。<br />
     * 5) 文字サイズはGS !(文字サイズの指定)でも実行できるが、最後に処理したコマンドが有効となる。
     * 
     * @param wkFont    [in] byte       フォントを設定(0: 文字フォントA、1: 文字フォントB、2: 文字フォントC)
     * @param wkHeight  [in] boolean    縦倍拡大の設定(true: 縦倍拡大する、false: 縦倍拡大しない)
     * @param wkWidth   [in] boolean    横倍拡大の設定(true: 横倍拡大する、false: 横倍拡大しない)
     * @param wkUnder   [in] boolean    アンダーラインの設定(true: アンダーライン指定する、false: アンダーライン指定しない)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetKanjiMode(byte wkFont, boolean wkHeight, boolean wkWidth, boolean wkUnder) {
        byte[] wkBytes = new byte[] {
                0x1B, 0x21, 0x00
        };
        byte wkByte = 0x00;

        // フォント選択
        wkByte |= wkFont;

        // 縦倍拡大
        wkByte |= wkHeight ? 0x10 : 0x00;

        // 横倍拡大
        wkByte |= wkWidth ? 0x20 : 0x00;

        // アンダーライン
        wkByte |= wkUnder ? 0x80 : 0x00;

        wkBytes[2] = wkByte;
        return wkBytes;
    }

    /**
     * 全角文字指定時の文字サイズ4倍指定の設定・解除を行う
     * 
     * @param wkSetting [in] boolean    拡大指定(true: 拡大する, false: 拡大しない)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetKanjiSize(boolean wkSetting) {
        return new byte[] {
                0x1C, 0x57, (byte) (wkSetting ? 0x01 : 0x00)
        };
    }

    /**
     * アンダーラインの指定・解除<br />
     * 1) アンダーラインはESC SP(文字の右スペース)、FS S(漢字のスペース量の設定)を含む文字幅全てに付加される。<br />
     * ただし、HT(水平タブ)等によりスキップした部分には付加されない。<br />
     * 2) アンダーラインはGS B(白黒反転m文字)には付加されない。<br />
     * 3) wkLine =
     * 0によりアンダーライン解除を実行した場合、以降のデータにアンダーラインは付加されないが、アンダーライン幅は直前の設定が保持される。<br />
     * また、初期状態では1ドット幅アンダーラインが設定されている。<br />
     * 4) 文字サイズに関係なくアンダーライン幅は、設定された太さで一定となる。<br />
     * 5) アンダーラインはESC !(印字モード一括指定)でも実行できるが、最後に処理したコマンドが有効となる。
     * 
     * @param wkLine    [in] byte   アンダーライン指定(0 ≦ wkLIne ≦ 2)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetUnderLine(byte wkLine) {
        byte[] wkByte = new byte[] {
                0x1B, 0x55, 0x00
        };
        if (0 <= wkLine && wkLine <= 2) {
            wkByte[2] = wkLine;
        }
        return wkByte;
    }

    /**
     * 強調印字の指定・解除<br />
     * 強調印字の指定または解除を行う。<br />
     * 1) 強調印字はESC !(印字モードの一括指定)でも実行できるが、最後に処理したコマンドが有効となる。
     * 
     * @param wkBold    [in] boolean    強調有無(true: 指定、false: 解除)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetBold(boolean wkBold) {
        return new byte[] {
                0x1B, 0x45, (byte) (wkBold ? 0x01 : 0x00)
        };
    }

    /**
     * 倒立印字の指定・解除を行う<br />
     * 倒立印字の指定または解除を行う。<br />
     * 1) スタンダードモード選択時、このコマンドは行の先頭で入力される場合のみ有効である。<br />
     * 2) ページモード選択時、このコマンドは設定のみ有効である。<br />
     * 3) このコマンドはページモードには影響しない。<br />
     * 4) 倒立印字とは、その行のデータを180度回転させて印字することである。
     * 
     * @param wkInvert  boolean 倒立指定(true: 倒立印字する、false: 倒立印字しない)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetInvert(boolean wkInvert) {
        return new byte[] {
                0x1B, 0x7B, (byte) (wkInvert ? 0x01 : 0x00)
        };
    }

    /**
     * 白黒反転印字の指定・解除<br />
     * 白黒反転印字の指定または解除を行う。<br />
     * 1) 全ての文字が白黒反転印字の対象となる。<br />
     * 2) ESC SP(文字の右スペース量の設定)、FS S(漢字のスペース量の設定)により設定された文字の右スペース部分も白黒反転印字の対象となる。<br />
     * 3) このコマンドは行間のスペース部分には影響しない。<br />
     * 4) 白黒反転印字の指定はアンダーライン指定より優先される。<br />
     * アンダーライン指定時でも、白黒反転文字にはアンダーラインは付加されない。<br />
     * ただし、アンダーラインの指定は取り消されない。
     * 
     * @param wkInversion   [in] boolean    反転有無(true: 白黒反転印字を指定する、false: 白黒反転印字を解除する)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetInversion(boolean wkInversion) {
        return new byte[] {
                0x1D, 0x42, (byte) (wkInversion ? 0x01 : 0x00)
        };
    }

    // ------------------------------------------------------------------
    // 印字位置コマンド
    // ------------------------------------------------------------------
    /**
     * 相対位置の指定<br />
     * 次の印字開始位置を、現在位置を基準とした相対位置で指定する。<br />
     * 1) 次の印字開始位置を現在位置から( wkLow + wkHigh * 256 )ドットの位置に移動させる。<br />
     * 2) 印字領域を超える指定は無視する。<br />
     * 3) 文字方向に対し現在位置より右方向を指定する場合は正数を、左方向を指定する場合は負数を指定する。<br />
     * 負数は65536の補数で表す。<br />
     * 左方向にNドット移動する場合は次のようになる。<br />
     * wkLow + wkHigh * 256 = 65536 - N
     * 
     * @param wkLow     [in] byte   下位ビット(0 <= wkLow <= 255)
     * @param wkHigh    [in] byte   上位ビット(0 <= wkHigh <= 255)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetRelativePosition(byte wkLow, byte wkHigh) {
        byte[] wkByte = new byte[] {
                0x1B, 0x5C, 0x00, 0x00
        };
        if (0 <= wkLow) {
            wkByte[2] = wkLow;
        }
        if (0 <= wkHigh) {
            wkByte[3] = wkHigh;
        }
        return wkByte;
    }

    /**
     * 位置揃え<br />
     * 1行の全ての印字データを指定位置に揃える<br />
     * 1) スタンダードモード選択時、行の先頭で入力される場合のみ有効である。<br />
     * 2) ページモード選択時、このコマンドは設定のみ有効である。<br />
     * 3) このコマンドの設定はページモードには影響しない。<br />
     * 4) 設定されている印字領域幅内で位置揃えを実行する。<br />
     * 5) このコマンドの設定はテキストデータにのみ有効である。<br />
     * 6) このコマンドの使用中は、HT(水平タブ)、ESC $(絶対位置移動)及びESC \(相対位置移動)は使用できない。<br />
     * 7) 中央寄せ、右揃えを実行したい場合、行の先頭は文字データでなければならない。
     * 
     * @param wkAlign   [in] byte   位置揃え(ALIGN_LEFT: 左揃え、ALIGN_CENTER: 中央寄せ、ALIGN_RIGHT: 右揃え)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetAlign(byte wkAlign) {
        return new byte[] {
                0x1B, 0x61, wkAlign
        };
    }

    /**
     * 水平タブ<br />
     * 印字位置を次の水平タブ位置まで移動する。<br />
     * 1) 次の水平タブ位置が設定されていない場合は、このコマンドは無視する。<br />
     * 2) 次の水平タブ位置が印字領域を越える場合は、現在行のバッファフル印字と次行先頭からの水平タブ処理を実行する。<br />
     * 3) 水平タブ位置はESC D(水平タブ位置の設定)により設定する。<br />
     * 4) 水平タブの初期値は設定無し。
     * 
     * @return  byte[]  生成コマンド
     */
    public static byte commandSetHt() {
        return 0x09;
    }

    /**
     * 水平タブ位置の設定<br />
     * 水平タブ位置を設定する。<br />
     * 1) 既に設定されている水平タブ位置は解除する。<br />
     * 2) 水平タブ位置に8を設定した場合、HT(水平タブ)の実行により次の印字位置は9桁目に移動する。<br />
     * 3) 設定可能な水平タブ位置は最大32ヶ所であり、これを越えた場合次データからは通常データとして処理する。<br />
     * 4) ESC D NULコマンドにより全ての水平タブ位置を解除する。<br />
     * 水平タブ位置の初期値は設定無し。
     * 
     * @param bs    [in] byte[] 水平タブ位置のリスト
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetHtPosition(byte... bs) {
        int wkInt = bs.length;
        byte[] wkByte = new byte[3 + wkInt];
        wkByte[0] = 0x1B;
        wkByte[1] = 0x44;
        for (int i = 0; i < wkInt; i++) {
            if (0 <= bs[i]) {
                wkByte[i + 2] = bs[i];
            }
            else {
                wkByte[i + 2] = 0x00;
            }
        }
        wkByte[2 + wkInt] = 0x00;
        return wkByte;
    }

    /**
     * 左マージンの設定<br />
     * wkLow,wkHighで指定された左マージンを設定する。<br />
     * 1) 左マージンは( wkLow + wkHigh * 256 )ドットとなる。<br />
     * 2) ページモード選択時、このコマンドは設定にのみ有効である。<br />
     * 3) このコマンドの設定はページモードには影響しない。<br />
     * 4) 設定可能な最大の左マージンは、横方向の印字可能領域と同じ大きさである。<br />
     * 最大値を越えた場合はこのコマンドは無視される。<br />
     * 5) プリントバッファ内にデータが存在する場合、データを印字してからコマンドを実行する。
     * 
     * @param wkLow     [in] byte   下位ビット(0 ≦ wkLow ≦ 255)
     * @param wkHigh    [in] byte   上位ビット(0 ≦ wkHigh ≦ 255)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetLeftMargin(byte wkLow, byte wkHigh) {
        byte[] wkByte = new byte[] {
                0x1D, 0x4C, 0x00, 0x00
        };
        if (0 <= wkLow) {
            wkByte[2] = wkLow;
        }
        if (0 <= wkHigh) {
            wkByte[3] = wkHigh;
        }
        return wkByte;
    }

    /**
     * 印字領域幅の設定<br />
     * wkLow、wkHighで指定された印字領域幅を設定する。<br />
     * 1) 印字領域幅は( wkLow + wkHigh * 256 )ドットとなる。<br />
     * 2) ページモード選択時、このコマンドは設定にのみ有効である。<br />
     * 3) このコマンドの設定はページモードには影響しない。<br />
     * 4) 【左マージン＋印字領域幅】が印字領域を越えた場合はこのコマンドは無視される。<br />
     * 5) プリントバッファ内にデータが存在する場合、データを印字してからコマンドを実行する。
     * 
     * @param wkLow     [in] byte   下位ビット(0 ≦ wkLow ≦ 255 初期値: 64)
     * @param wkHigh    [in] byte   上位ビット(0 ≦ wkHigh ≦ 255 初期値: 2)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetPrintWidth(byte wkLow, byte wkHigh) {
        byte[] wkByte = new byte[] {
                0x1D, 0x57, 0x00, 0x00
        };
        wkByte[2] = wkLow;
        wkByte[3] = wkHigh;
        return wkByte;
    }

    /**
     * ページモードにおける印字領域の設定<br />
     * ページモードにおける印字領域の位置及び大きさを設定する。<br />
     * 横方向始点(x) = ( wkXlow + wkXhigh * 256 ) 縦方向始点(y) = ( wkYlow + wkYhigh * 256
     * ) 横方向長さ(dx) = ( wkWidthlow + wkWidthHigh * 256 ) 縦方向長さ(dy) = (
     * wkHeightlow + wkHeightHigh * 256 ) 1)
     * スタンダードモード選択時、このコマンドを入力するとプリンタの内部フラグ操作のみ実行し、印字には影響しない。 2)
     * 横方向始点または縦方向始点が印字可能領域外の場合、このコマンドは無視される。<br />
     * 横方向長さまたは縦方向長さが0の場合、このコマンドは無視される。<br />
     * 3) (横方向始点＋横方向長さ)が横方向の印字可能領域を越える場合、<br />
     * (横方向の印字可能領域―横方向始点)を横方向長さとする。<br />
     * 4) （縦方向始点＋縦方向長さ）が縦方向の印字可能領域を越える場合、<br />
     * （縦方向の印字可能領域－縦方向始点）を縦方向長さとする。<br />
     * 初期値: wkXlow, wkXhigh, wkYlow, wkYhith = 0<br />
     * wkHeightLow = 96, wkHeightHigh = 9<br />
     * wkWidthLow = 64, wkWidthHigh = 2
     * 
     * @param wkXlow        [in] byte   X軸下位ビット(0 ≦ wkXlow ≦ 255)
     * @param wkXhigh       [in] byte   X軸上位ビット(0 ≦ wkXhigh ≦ 255)
     * @param wkYlow        [in] byte   Y軸下位ビット(0 ≦ wkYlow ≦ 255)
     * @param wkYhigh       [in] byte   Y軸上位ビット(0 ≦ wkYhigh ≦ 255)
     * @param wkWidthLow    [in] byte   横幅下位ビット(0 < wkWidthLow <= 255)
     * @param wkWidthHigh   [in] byte   横幅上位ビット(0 < wkWidthHigh <= 255)
     * @param wkHeightLow   [in] byte   高さ下位ビット(0 < wkHeightHigh <= 255)
     * @param wkHeightHigh  [in] byte   高さ上位ビット(0 < wkHeightHigh <= 255)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetPrintArea(byte wkXlow, byte wkXhigh, byte wkYlow, byte wkYhigh, byte wkWidthLow, byte wkWidthHigh,
            byte wkHeightLow, byte wkHeightHigh) {
        byte[] wkByte = new byte[] {
                0x1B, 0x57, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };
        wkByte[2] = wkXlow;
        wkByte[3] = wkXhigh;
        wkByte[4] = wkYlow;
        wkByte[5] = wkYhigh;
        wkByte[6] = wkWidthLow;
        wkByte[7] = wkWidthHigh;
        wkByte[8] = wkHeightLow;
        wkByte[9] = wkHeightHigh;
        return wkByte;
    }

    /**
     * ページモードにおける文字の印字方向の選択<br />
     * ページモードにおける文字の印字方向および始点を選択する。<br />
     * 1) スタンダードモード選択時、このコマンドを入力するとプリンタの内部フラグ操作のみ実行し、印字には影響しない。<br />
     * 2) このコマンドはESC W(ページモードにおける印字領域の設定)により設定された印字領域内の始点となる。<br />
     * 3) 始点が左上または右下の場合、紙送り方向と垂直に文字を展開する。<br />
     * 4) 始点が右上または左下の場合、紙送り方向に文字を展開する。
     * 
     * @param wkDirection   [in] byte   印字方向
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandPrintDirection(byte wkDirection) {
        return new byte[] {
                0x1D, 0x54, wkDirection
        };
    }

    /**
     * ページモードにおける縦方向絶対位置の指定<br />
     * ページモードにおけるデータ展開開始位置の文字縦方向の位置を、始点を基準とした絶対位置で指定する。<br />
     * 1) 開始位置の文字縦方向の位置は、始点から（ wkLow + wkHigh * 256 ）ドットとなる。<br />
     * 2) ページモード選択時以外はこのコマンドを無視する。<br />
     * 3) 指定されている印字領域を越える絶対位置指定は無視する。<br />
     * 4) ESC T（ページモードにおける文字の印字方向の選択）で設定された始点により、下記の動作となる。<br />
     * 始点が左上または右下に指定されている場合は紙送り方向（文字の縦方向）の絶対位置を指定する。<br />
     * 始点が右上または左下に指定されている場合は紙送りに垂直な方向（文字の縦方向）の絶対位置を指定する。
     * 
     * @param wkLow     [in] byte   下位ビット(0 ≦ wkLow ≦ 255)
     * @param wkHigh    [in] byte   上位ビット(0 ≦ wkHigh ≦ 255)
     * @return  byte[]  生成コマンド
     */
     public static byte[] commandSetAbsolutePositionHigh( byte wkLow, byte wkHigh ) {
         byte[] wkByte = new byte[] { 0x1D, 0x24, 0x00, 0x00 };
             wkByte[2] = wkLow;
             wkByte[3] = wkHigh;
         return wkByte;
     }

    /**
     * ページモードにおける縦方向相対位置の指定<br />
     * ページモードにおけるデータ展開開始位置の縦方向の位置を、現在位置を基準とした相対位置で指定する。<br />
     * 1) 開始位置の文字縦方向の位置は、始点から（ wkLow + wkHigh * 256 ）ドットとなる。<br />
     * 2) ページモード選択時以外はこのコマンドを無視する。<br />
     * 3) 文字に対して現在位置より下方向に指定する場合は正数（プラス）となり、上方向に指定する場合は負数（マイナス）となる。<br />
     * 負数は、65536の補数で表す。上方向にNピッチ移動する場合は次のようになる。<br />
     * wkLow + wkHigh * 256 = 65536 － N<br />
     * 4) 指定されている印字領域を越える相対位置指定は無視する。<br />
     * 5) ESC T（ページモードにおける文字の印字方向の選択）で設定された始点により、下記の動作となる。<br />
     * 始点が左上または右下に指定されている場合は紙送り方向の相対位置を指定する。<br />
     * 始点が右上または左下に指定されている場合は紙送りに垂直な方向の相対位置を指定する。<br />
     * 
     * @param wkLow     [in] byte   下位ビット(0 ≦ wkLow ≦ 255)
     * @param wkHigh    [in] byte   上位ビット(0 ≦ wkHigh ≦ 255)
     * @return  byte[]  生成コマンド
     */
     public static byte[] commandSetRelativePositionHigh( byte wkLow, byte wkHigh ) {
         byte[] wkByte = new byte[] { 0x1D, 0x5C, 0x00, 0x00 };
         wkByte[2] = wkLow;
         wkByte[3] = wkHigh;
     return wkByte;
     }

    /**
     * ページモードにおける印字開始位置の指定<br />
     * ページモードにおける印字開始位置を水平方向と垂直方向でそれぞれ指定する。<br />
     * 水平印字開始位置 = wkXlow + wkXhigh * 256<br />
     * 垂直印字開始位置 = wkYlow + wkYhigh * 256<br />
     * ページモード選択時以外はこのコマンドを無視する。
     * 
     * @param wkXlow    [in] byte   X軸下位ビット(0 ≦ wkXlow ≦ 255)
     * @param wkXhigh   [in] byte   X軸上位ビット(0 ≦ wkXhow ≦ 255)
     * @param wkYlow    [in] byte   Y軸下位ビット(0 ≦ wkYlow ≦ 255)
     * @param wkYhigh   [in] byte   Y軸上位ビット(0 ≦ wkYhow ≦ 255)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetStartPosition(byte wkXlow, byte wkXhigh, byte wkYlow, byte wkYhigh) {
        byte[] wkByte = new byte[] {
                0x1B, 0x4F, 0x00, 0x00, 0x00, 0x00
        };
        wkByte[2] = wkXlow;
        wkByte[3] = wkXhigh;
        wkByte[4] = wkYlow;
        wkByte[5] = wkYhigh;
        return wkByte;
    }

    // ------------------------------------------------------------------
    // ビットイメージコマンド
    // ------------------------------------------------------------------

    // ------------------------------------------------------------------
    // ステータスコマンド
    // ------------------------------------------------------------------

    // ------------------------------------------------------------------
    // 補助機能
    // ------------------------------------------------------------------
    /**
     * プリンタの初期化<br />
     * プリントバッファ内のデータをクリアし、各種設定を初期状態にする。<br />
     * 受信バッファ内のデータはクリアされない。
     * 
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandInitPrinter() {
        return new byte[] {
                0x1B, 0x40
        };
    }

    /**
     * ページモードの選択<br />
     * スタンダードモードからページモードへの切替えを行う。<br />
     * 1) スタンダードモードで入力された場合に有効である。<br />
     * 2) FF(ページモードの印字と復帰)またはESC S(スタンダードモードの選択)の実行後、スタンダードモードに戻る。<br />
     * 3) 文字展開位置はESC W（ページモードにおける印字領域の設定）で指定された印字領域内の、<br />
     * ESC T（ページモードにおける文字の印字方向の選択）で指定された始点となる。<br />
     * 4) ページモードとスタンダードモードにそれぞれ独立した値をもつ下記コマンドの設定値をページモードの設定値に切替える。<br />
     * 文字の右スペース量の設定 ： ESC SP, FS S<br />
     * 改行量の設定 ： ESC 2, ESC 3<br />
     * 5) 電源再投入、リセット、およびESC @（プリンタの初期化）実行時にスタンダードモードへ復帰する。
     * 
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetPageMode() {
        return new byte[] {
                0x1B, 0x4C
        };
    }

    /**
     * スタンダードモードの選択<br />
     * ページモードからスタンダードモードへの切替えを行う。<br />
     * 1) ページモードで入力された場合のみ有効である。<br />
     * 2) ページモードで展開したデータは消去される。<br />
     * 3) 実行後は、行の先頭を次の印字開始位置とする。<br />
     * 4) ESC W（ページモードにおける印字領域の設定）により設定した印字領域は初期化される。<br />
     * 5) ページモードとスタンダードモードにそれぞれ独立した値をもつ下記コマンドの設定値をスタンダードモードの設定値に切替える。<br />
     * 文字の右スペース量の設定 ： ESC SP, FS S 改行量の設定 ： ESC 2, ESC 3 6)
     * スタンダードモードにおいては、下記のコマンドは設定のみ有効である。<br />
     * ページモードにおける印字領域の設定 ： ESC W<br />
     * ページモードにおける文字の印字方向の選択 ： ESC T<br />
     * 7) 電源再投入、リセット、およびESC @（プリンタの初期化）実行時にはスタンダードモードが選択される。
     * 
     * @return  byte[]  生成コマンド
     */
     public static byte[] commandSetStandardMode(){
         return new byte[] { 0x1B, 0x53 };
     }

    /**
     * ページモードにおける印字データのキャンセル<br />
     * ページモードにおいて現在設定されている印字領域の全データを消去する。<br />
     * ページモード選択時のみ有効である。
     * 
     * @return  byte[]  生成コマンド
     */
    public static byte commandPrintCancel() {
        return 0x18;
    }

    // ------------------------------------------------------------------
    // ライン＆ボックスの印字
    // ------------------------------------------------------------------
    /**
     * ページモードにおけるライン＆ボックスの印字<br />
     * ページモードにおいて線＆四角形を印字する。<br />
     * 横（水平方向）の位置 ： wkXlow + wkXhigh * 256（dots）<br />
     * 縦（垂直方向）の位置 ： wkYlow + wkYhigh * 256（dots）<br />
     * 横（水平方向）の長さ : wkDxlow + wkDxhigh * 256(dots)<br />
     * 縦（垂直方向）の長さ : wkDylow + wkDyhigh * 256(dots)<br />
     * 印字モード： 0: 箱内を白色、1: 箱内を黒色、 2: 箱内を反転 線の太さ ： wkBold（ドット単位）<br />
     * 
     * @param wkXlow    [in] byte   X軸下位ビット
     * @param wkXhigh   [in] byte   X軸上位ビット
     * @param wkYlow    [in] byte   Y軸下位ビット
     * @param wkYhigh   [in] byte   Y軸上位ビット
     * @param wkDxlow   [in] byte   横幅下位ビット
     * @param wkDxhigh  [in] byte   横幅上位ビット
     * @param wkDylow   [in] byte   高さ下位ビット
     * @param wkDyhigh  [in] byte   高さ上位ビット
     * @param wkMode    [in] byte   印字モード
     * @param wkBold    [in] byte   太さ
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetLineBox(byte wkXlow, byte wkXhigh, byte wkYlow, byte wkYhigh, byte wkDxlow, byte wkDxhigh, byte wkDylow,
            byte wkDyhigh, byte wkMode, byte wkBold) {
        byte[] wkByte = new byte[] {
                0x1D, 0x58, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };
        wkByte[2] = wkXlow;
        wkByte[3] = wkXhigh;
        wkByte[4] = wkYlow;
        wkByte[5] = wkYhigh;
        wkByte[6] = wkDxlow;
        wkByte[7] = wkDxhigh;
        wkByte[8] = wkDylow;
        wkByte[9] = wkDyhigh;
        wkByte[10] = wkMode;
        wkByte[11] = wkBold;
        return wkByte;
    }

    // ------------------------------------------------------------------
    // 漢字制御コマンド
    // ------------------------------------------------------------------
    /**
     * シフトJISモードの指定<br />
     * シフトJISモードを指定する。<br />
     * 
     * @param wkMode    [in] boolean    true: シフトJISモードを指定、false: シフトJISモードを解除
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetKanjiMode(boolean wkMode) {
        return new byte[] {
                0x1C, 0x43, (byte) (wkMode ? 0x01 : 0x00)
        };
    }

    /**
     * 漢字のスペース量の設定<br />
     * 漢字の左スペース量および右スペース量を設定する。<br />
     * 1) 設定するスペース量は、文字サイズが標準サイズの時のスペース量である。<br />
     * 文字の横方向倍率が2以上の場合は、スペース量も倍率に応じて大きくなる。<br />
     * 2) スタンダードモードとページモードに、それぞれ独立したスペース量の設定が可能である。<br />
     * 3) この設定は漢字にのみ有効である。
     * 
     * @param wkLeft    [in] byte   左スペース量(0 <= wkLeft <= 255 wkLeft = 0)
     * @param wkRight   [in: byte   右スペース量(0 <= wkRight <= 255 wkRight = 0)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetKanjiSpace(byte wkLeft, byte wkRight) {
        byte[] wkByte = new byte[] {
                0x1C, 0x53, 0x00, 0x00
        };
        if (0 <= wkLeft) {
            wkByte[2] = wkLeft;
        }
        if (0 <= wkRight) {
            wkByte[3] = wkRight;
        }
        return wkByte;
    }

    // ------------------------------------------------------------------
    // ブラックマーク検知コマンド
    // ------------------------------------------------------------------
    /**
     * ブラックマーク検出後の紙送り量の設定<br />
     * ブラックマーク検出後の紙送り量を設定する。
     * 
     * @param wkLow     [in] byte   下位ビット(0 <= wkLow <= 255)
     * @param wkHigh    [in] byte   上位ビット(0 <= wkHigh <= 255)
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetBlackMarkerFeedPaper(byte wkLow, byte wkHigh) {
        byte[] wkByte = new byte[] {
                0x1B, 0x50, 0x00, 0x00
        };
        wkByte[2] = wkLow;
        wkByte[3] = wkHigh;
        return wkByte;
    }

    /**
     * ブラックマーク検出後の紙送りの実行<br />
     * 設定されたブラックマーク検出後の紙送り量分だけ、紙送りを実行する。
     * 
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandBlackMarkerFeePaperExecute() {
        return new byte[] {
                0x1B, 0x7A, 0x1B, 0x79
        };
    }

    // ------------------------------------------------------------------
    // ロゴコマンド
    // ------------------------------------------------------------------
    /**
     * ロゴの印字<br />
     * <br />
     * プリンタのメモリに格納されているロゴを印字する。<br />
     * 1) ロゴデータは、1ビットのPCXファイルフォーマットでその幅(ピクセル)は8の倍数でなくてはならない。<br />
     * 2) Star Micronics Utilityを使用してプリンタにロゴを登録することができる。<br />
     * 4) 登録されるロゴの数(総メモリーサイズ)はプリンタの機種によって異なる。<br />
     * ロゴデータのサイズが4K(4096バイト)以下ならば、最大60のロゴ(最大243Kバイト)をプリンタnい登録できる。<br />
     * ロゴは2400ピクセルの高さを越えてはならない。<br />
     * <br />
     * *** プリンタの最大ロゴサイズ ***<br />
     * 2インチ幅プリンタ:384 x 2400ピクセル<br />
     * 3インチ幅プリンタ:576 x 2400ピクセル
     * 
     * @param logoNum   [in] byte   ロゴ番号
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandLogoPrint(byte logoNum) {
        byte[] wkByte = new byte[] {
                0x1B, 0x66, 0x00, 0x0C
        };
        if (0 <= logoNum) {
            wkByte[2] = logoNum;
        }
        return wkByte;
    }

    /**
     * 引数で与えられた文字列、サイズ、太字指定からビットマップを生成する。
     * 
     * @param text      [in] String     生成する文字列
     * @param size      [in] float      テキストサイズ
     * @param wkBold    [in] boolean    太さ
     * @return {@link Bitmap}   生成されたビットマップデータを返します。
     */
    public static Bitmap commandAddBitmapText(String text, float size, boolean wkBold) {
        Typeface wkFont = Typeface.DEFAULT;
        int wkStyle = wkBold ? Typeface.BOLD : Typeface.NORMAL;

        Paint wkPaint = new Paint();
        wkPaint.setStyle(Paint.Style.FILL);
        wkPaint.setColor(Color.BLACK);
        wkPaint.setAntiAlias(true);
        Typeface wkTypeface = Typeface.create(wkFont, wkStyle);
        wkPaint.setTypeface(wkTypeface);
        wkPaint.setTextSize(size * 2);
        TextPaint wkTextPaint = new TextPaint(wkPaint);

        android.text.StaticLayout wkStaticLayout = new StaticLayout(text, wkTextPaint, 562, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        int wkHeight = wkStaticLayout.getHeight();
        Log.i(TAG, " Height: " + wkHeight);

        Bitmap wkBitMap = Bitmap.createBitmap(wkStaticLayout.getWidth(), wkHeight, Bitmap.Config.RGB_565);

        Canvas wkCanvas = new Canvas(wkBitMap);
        wkCanvas.drawColor(Color.WHITE);
        wkCanvas.translate(0, 0);
        wkStaticLayout.draw(wkCanvas);

        return wkBitMap;
    }

    /**
     * 印刷濃度を変更する。
     * 
     * @param t_density [in] int    濃度( 0:60%, 1:75% 2:90%, 3:100%, 4:120%, 5:140%, 6:160% )
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetPrintDensity(int t_density) {
        if (t_density > 6) t_density = 6;
        if (t_density < 0) t_density = 0;
        return new byte[] {
                0x1B, 0x59, (byte) t_density
        };
    }

    /**
     * フラッシュメモリ領域に保存.
     *
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSaveFlash() {
        return new byte[] {
                0x1B, 0x5E,
        };
    }

    /**
     * 印刷濃度をデフォルト値(100%)にする。
     * 
     * @return  byte[]  生成コマンド
     */
    public static byte[] commandSetPrintDensityNormal() {
        return commandSetPrintDensity(3);
    }

}
