
package jp.co.MarutouCompack.baseClass;

import jp.co.MarutouCompack.commonClass.PrinterDat.CusData;
import jp.co.MarutouCompack.commonClass.PrinterDat.HanfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.KensinData;
import jp.co.MarutouCompack.commonClass.PrinterDat.KensinInfoBaseDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.SysfDat;
import jp.co.MarutouCompack.commonClass.PrinterDat.UserData;

public interface PrintKensinInterface {

    /** 日常点検項目 */
    String[] HOAN_ITEMS = new String[] {
            "①容器設置場所", "②容器設置状況", "③火気禁止２ｍ", "④調整器", "⑤配管状況", "⑥ガス栓", "⑦危険標識", "⑧マイコンメーター"
    };

    /**
     * 伝票の印字を実行する
     *
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void printExecute() throws MException;

    /**
     * 伝票印字データの作成
     *
     * @param isNyukin  [in] boolean    入金有無
     * @param isHikae   [in] boolean    控え有無
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createPrintData(UserData userData, boolean isNyukin, boolean isHikae) throws MException;

    /**
     * 伝票の顧客データ印字部分の作成
     *
     * @param cusData [in] {@link CusData}    顧客データ
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createCusInfo(CusData cusData) throws MException;

    //Hieu
    /**
     * 伝票の検針データ印字部分の作成
     *
     * @param kensinData  [in] {@link KensinData} 検針データ
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createKensinInfo(UserData userData, KensinData kensinData) throws MException;

    /**
     * 伝票の領収印字部分の作成
     *
     * @param strInpReceipt   [in] String 領収金額
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createRyoshu(String strInpReceipt) throws MException;

    //Hieu
    /**
     * 伝票のコメント部分の作成
     *
     * @param commentData [in] {@link String[]}    コメントデータ
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createComment(String[] commentData) throws MException;

    /**
     * 伝票の明細部分の作成
     *
     * @param userData    [in] {@link UserData}   共通データ
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createHmInfo(UserData userData) throws MException;

    /**
     * 伝票の保安部分の作成
     *
     * @param strHoan    [in] String 日常点検データ
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createHoanInfo(boolean isNyukinMode, String strHoan) throws MException;

    /**
     * 伝票の店舗データ印字部分の作成
     *
     * @param hanfDat           [in] {@link HanfDat}    店舗データ
     * @param strTantname       [in] String             担当者名
     * @param sysfDat           [in] {@link SysfDat}    システムデータ
     * @param isTnumberPrint    [in] boolean            Tナンバー・インボイスコメント印字フラグ(true:印字する, false:印字しない)
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createUserInfo(HanfDat hanfDat, String strTantname, SysfDat sysfDat, boolean isTnumberPrint) throws MException;


    /**
     * 伝票の自振関連データ印字部分の作成
     *
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createBank(UserData userData) throws MException;


    /**
     * 伝票のCNポイント用コメント印字部分の作成
     *
     * @param kensinData  [in] {@link KensinData}   検針データ
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createCnComment(KensinData kensinData) throws MException;

    /**
     * 通常ポイント印字部分の作成.
     *
     * @param userData    [in] {@link UserData} 検針データ
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createPoint(UserData userData) throws MException;

    /**
     * 伝票：宮野式ポイント印字部分の作成
     *
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createMiyaPoint(UserData userData) throws MException;

    //Hieu
    /**
     * 伝票にガス料金総額を印字
     *
     * @param printImageList    [in] PrintImageList         印刷データ格納先データ
     * @param mKSIB             [in] KensinInfoBaseDat      印刷用検針データ
     * @param nYpos             [in] int                    現在の伝票高さ
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createGasryokinTotal(PrintImageList printImageList, KensinInfoBaseDat mKSIB, int nYpos) throws MException;

    /**
     * 伝票に日割りコメントを印字
     *
     * @param printImageList    [in] PrintImageList 印刷データ格納先データ
     * @param kensinData        [in] Kensindata     印刷用検針データ
     * @param nYpos             [in] int            現在の伝票高さ
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    void createHiwariComment(PrintImageList printImageList, KensinData kensinData, int nYpos) throws MException;


    //Hieu
    /**
     * 前年同月使用量を印字
     *
     * @param printImageList    [in] PrintImageList 印字データ格納先(画像モードのみ使用)
     * @param mKSIB             [in] KensinInfoBaseDat     検針印字データ
     * @param nYpos             [in] int            現在の伝票高さ
     * @return int 印字後の伝票高さ
     * @throws MException 印字データ作成時にエラーがあった場合に発生
     */
    int createZenYearkenSr(PrintImageList printImageList, KensinInfoBaseDat mKSIB, int nYpos) throws MException;

    /**
     * ハイブリッドコメントの印字.
     *
     * @param kensinData    [in] {@link KensinData} 検針印字データ
     * @throws MException   印字データ作成時にエラーがあった場合に発生
     */
    void createHybComment(KensinData kensinData) throws MException;

    //Hieu
    /**
     * ハイブリッド料金式の印字.
     *
     * @param kensinData    [in] {@link KensinData} 検針印字データ
     * @throws MException   印字データ作成時にエラーがあった場合に発生
     */
    void createHybTblPrint(KensinData kensinData) throws MException;

    /**
     * 銀行不能コメントの印字.
     *
     * @throws MException   印刷データ作成時にエラーがあった場合に発生
     */
    void createFunouComment(UserData userData) throws MException;
}
