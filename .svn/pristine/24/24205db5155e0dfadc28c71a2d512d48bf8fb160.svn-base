
package jp.co.MarutouCompack.commonClass.PrinterDat;

/**
 * システムオプション.
 */
@SuppressWarnings("unused")
public enum SysOption {
    /** [00]:検針時入金 */
    NYUKIN(0, true),
    /** [01]:検針時点検 */
    TENKEN(1, true),
    /** [02]:検針時配送 */
    HAISOU(2, false),
    /** [05]:警報器リース出力 */
    PRINT_LEAS_KEI(5, true),
    /** [06]:分割金出力 */
    PRINT_BUNKATU(6, true),
    /** [07]:その他売上出力 */
    PRINT_OTHER_URI(7, true),
    /** [08]:前月請求額出力 */
    PRINT_ZSEI(8, true),
    /** [09]:入金・調整出力 */
    PRINT_NYUCHO(9, true),
    /** [10]:灯油出力 */
    PRINT_TOYU(10, true),
    /** [11]:自動引落出力 */
    PRINT_JIFURI(11, true),
    /** [12]:当月検針済み作成 */
    OUT_KENSIN_ZUMI(12, true),
    /** [13]:依頼中の前残の非出力 */
    PRINT_ZENZAN_IRAI(13, true),
    /** [14]:販売明細の非出力 */
    PRINT_HANMEISAI(14, false),
    /** [15]:内税コメント非出力 */
    NOT_PRINT_UTIZEI(15, false),
    /** [16]:自動検針区分非対応 */
    AUTO_KENSIN(16, true),
    /** [17]:リース割賦残回数出力 */
    PRINT_ZANKAI_LEASKAPPU(17, false),
    /** [19]:当月検針済み含まない */
    RADIO_NO_ADD(19, false),
    /** [20]:使用伝票種類　１：専用伝票 */
    PAPER_TYPE(20, true),
    /** [21]:社名・伝票出力 */
    PRINT_HANINFO(21, true),
    /** [22]:銀行用コメント出力 */
    PRINT_COMMENT_BANK(22, false),
    /** [23]:検針伝票ガス料金出力方法 */
    GASMEISAI_TYPE(23, true),
    /** [24]:保安点検結果出力 */
    PRINT_HOAN(24, true),
    /** [25]:検針伝票コメント出力 */
    PRINT_COMMENT(25, true),
    /** [26]:配送伝票コメント出力 */
    PRINT_COMMENT_HAISOU(26, false),
    /** [27]:納品書コメント出力 */
    PRINT_COMMENT_NOUHIN(27, true),
    /** [28]:領収書コメント出力 */
    PRINT_COMMENT_RYOSHU(28, true),
    /** [32]:控え出力 */
    PRINT_HIKAE(32, true),
    /** [31]:顧客の滞納月表示フラグ */
    DISP_TAINOU(31, true),
    /** [33]:単価印字フラグ */
    PRINT_TANKA(33, true),
    /** [34]:販売店の振込先銀行情報印字フラグ */
    PRINT_HANBANK(34, true),
    /** [35]:転送顧客最大数(*100件) */
    SEND_CUS_NUM(35, true),
    /** [36]:宮野式(獲得ポイント)印字フラグ */
    PRINT_MIYANO_GET(36, true),
    /** [37]:宮野式(使用ポイント)印字フラグ */
    PRINT_MIYANO_USE(37, true),
    /** [38]:宮野式(累計ポイント)印字フラグ */
    PRINT_MIYANO_RUI(38, true),
    /** [39]:ハイブリッド拡張用（ko2f)読込み用 */
    HYBRID_READ(39, true);

    /** インデックス */
    private final int m_nIdx;
    /** 仕様フラグ */
    private final boolean m_isUse;

    SysOption(int nIdx, boolean isUse) {
        m_nIdx = nIdx;
        m_isUse = isUse;
    }

    /**
     * インデックスの取得
     *
     * @return int インデックス
     */
    public int getIdx(){
        return m_nIdx;
    }

    /**
     * 使用フラグの取得
     *
     * @return boolean 使用フラグ
     */
    public boolean getIsUse(){
        return m_isUse;
    } 

}
