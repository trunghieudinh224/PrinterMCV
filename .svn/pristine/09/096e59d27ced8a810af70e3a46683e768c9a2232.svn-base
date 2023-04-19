
package jp.co.MarutouCompack.baseClass;

import java.util.List;
import java.util.Map;

import jp.co.MarutouCompack.commonClass.PrinterDat.ShukeiUriageData;
import jp.co.MarutouCompack.commonClass.PrinterDat.UriageItemDat;

public interface PrintUriageListInterface {

    /**
     * 売上日報印刷データの作成.
     *
     * @param mapUriageData [in] {@code Map<String, Map<Integer, ShukeiUriageDat>>}    日付毎の売上データ一覧
     */
    void createPrintData(Map<String, List<UriageItemDat>> mapUriageData, String tantname);

    /**
     * 印刷実行.
     *
     * @throws MException   印刷実行時にエラーがあった場合に発生
     */
    void printExecute() throws MException;
}
