
package jp.co.MarutouCompack.baseClass;

import jp.co.MarutouCompack.commonClass.PrinterDat.HanfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.WebData;

public interface PrintShukinInterface {

    /**
     * 伝票の印字を実行する
     *
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void printExecute() throws MException;

    /**
     * ヘッダーデータの作成
     *
     * @param isHikae   [in] boolean    控えフラグ(true: 控え, false: 通常)
     */
    void createHeaderData(boolean isHikae, String kensinDate);

    /**
     * 伝票の顧客データ印字部分の作成
     *
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createCusInfo(jp.co.MarutouCompack.commonClass.PrinterDat.UserData userData) throws MException;

    /**
     * 伝票の検針データ印字部分の作成
     *
     * @param mWebdata      [in]    WebData  検針データ
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createRyosyuInfo(WebData mWebdata) throws MException;

    /**
     * 伝票の領収印字部分の作成
     *
     * @param wkInput   [in]    String  領収金額
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createRyoshu(String wkInput) throws MException;

    /**
     * 伝票のコメント部分の作成
     *
     * @param commentData [in]    CommentData コメントデータ
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createComment(String[] commentData) throws MException;

    /**
     * 伝票の明細部分の作成
     *
     * @param wkUserData    [in]    UserData    共通データ
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createHmInfo(jp.co.MarutouCompack.commonClass.PrinterDat.UserData wkUserData) throws MException;

    /**
     * 伝票の店舗データ印字部分の作成
     *
     * @param hanfDat         [in]    {@link HanfDat} 店舗データ
     * @param strTantname         [in]    String          担当者名
     * @param sysfDat           [in]    {@link SysfDat} システムデータ
     * @param isTnumberPrint    [in]    Tナンバー・インボイスコメント印字有無(true:印字する, false:印字しない)
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createUserInfo(HanfDat hanfDat, String strTantname, SysfDat sysfDat, boolean isTnumberPrint) throws MException;
}
