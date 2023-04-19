
package jp.co.MarutouCompack.baseClass;

import java.util.List;
import java.util.Map;

import jp.co.MarutouCompack.commonClass.PrinterDat.ShukeiKensinData;

public interface PrintShukinListInterface {

    /**
     * 集金日報用印刷データの作成.
     *
     * @param mapKensinData [in] {@code Map<String, List<ShukeiKensinData>>}    日付毎集金データ
     */
    void createPrintData(Map<String, List<ShukeiKensinData>> mapKensinData, String tantname);

    /**
     * 印刷実行.
     *
     * @throws MException   印刷実行時にエラーがあった場合に発生
     */
    void printExecute() throws MException;
}
