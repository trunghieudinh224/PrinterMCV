
package jp.co.MarutouCompack.baseClass;

import java.util.List;

import jp.co.MarutouCompack.commonClass.PrinterDat.UserData;
import jp.co.MarutouCompack.commonClass.PrinterDat.ShukeiDat;

public interface PrintShukeiInterface {

    /**
     * 集計データの設定.
     *
     * @throws MException   印刷データ設定時にエラーがあった場合に発生
     */
    void setShukeiData(ShukeiDat mData) throws MException;

    /**
     * 印刷データ生成.
     *
     * @throws MException   印刷データ作成時にエラーがあった場合に発生
     */
    void createPrintData(String date, String tantname) throws MException;

    /**
     * 印刷実行.
     *
     * @throws MException   印刷実行時にエラーがあった場合に発生
     */
    void printExecute() throws MException;
}
