
package jp.co.MarutouCompack.baseClass;

import java.util.List;
import java.util.Map;

public interface PrintKensinListInterface {
    /**
     * 検針一覧印刷データの作成.
     *
     * @param mapKensinData [in] {@code Map<String, List<ShukeiKensinData>>}    日付毎検針一覧データ
     * @param isPrintToyu   [in] boolean                                        灯油検針フラグ
     */
    void createPrintData(Map<String, List<jp.co.MarutouCompack.commonClass.PrinterDat.ShukeiKensinData>> mapKensinData, boolean isPrintToyu, String tantname);

    /**
     * 印刷実行.
     *
     * @throws MException   印刷実行時にエラーがあった場合に発生
     */
    void printExecute() throws MException;
}
