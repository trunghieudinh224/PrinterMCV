package jp.co.MarutouCompack.baseClass;

import java.util.List;

import jp.co.MarutouCompack.commonClass.PrinterDat.HanfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.HmefDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UserData;

public interface PrintUriageInterface {

    /**
     * 印刷実行.
     *
     * @throws MException   印刷実行時にエラーがあった場合に発生
     */
    void printExecute() throws MException;

    /**
     * ヘッダー印刷データの作成.
     *
     * @param isHikae   [in] boolean    控えフラグ(true: 控え, false: 通常)
     * @param isGenuri  [in] boolean    現金売りフラグ(true: 現金売り, false: 掛売)
     * @throws MException   印刷データの作成時にエラーがあった場合に発生
     */
    void createHeader(boolean isHikae, boolean isGenuri, String date) throws MException;

    /**
     * 顧客印刷データの作成.
     *
     * @param strSname_0    [in] String 伝票用氏名1
     * @param strSname_1    [in] String 伝票用氏名2
     * @throws MException   印刷データの作成時にエラーがあった場合に発生
     */
    void createCusInfo(UserData userData, String strSname_0, String strSname_1) throws MException;

    /**
     * 販売明細印刷データの作成.
     *
     * @param lstHmefDat    [in] {@code List<HmefDat>}  販売明細一覧
     * @throws MException   印刷データの作成時にエラーがあった場合に発生
     */
    void createMeisaiInfo(UserData userData, List<HmefDat> lstHmefDat) throws MException;

    /**
     * 領収書印刷データの作成.
     *
     * @param nChokin   [in] int    調整額
     * @param nNyukin   [in] int    入金額
     * @param nRecept   [in] int    領収額
     * @param lSeikyu   [in] int    請求金額
     * @throws MException   印刷データの作成時にエラーがあった場合に発生
     */
    void createRyoshu(UserData userData, int nChokin, int nNyukin, int nRecept, long lSeikyu) throws MException;

    /**
     * コメント印刷データの作成.
     *
     * @param commentData [in] {@link String[]}    コメントデータ
     * @throws MException   印刷データの作成時にエラーがあった場合に発生
     */
    void createComment(String[] commentData) throws MException;

    /**
     * 販売店情報の印字.
     *
     * @param hanfDat           [in] {@link HanfDat}    販売店データ
     * @param strTantname       [in] String             担当者名
     * @param sysfDat           [in] {@link SysfDat}    システムデータ
     * @param isTnumberPrint    [in] boolean            Tナンバー・インボイスコメント印字有無(true:印字する, false:印字しない)
     * @throws MException   印刷データの作成時にエラーがあった場合に発生
     */
    void createUserInfo(HanfDat hanfDat, String strTantname, SysfDat sysfDat, boolean isTnumberPrint) throws MException;
}
