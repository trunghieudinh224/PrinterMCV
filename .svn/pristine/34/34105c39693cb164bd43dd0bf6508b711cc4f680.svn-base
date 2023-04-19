
package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * 顧客料金透明化データクラス.
 */
public class NippouDat {
    @SerializedName("m_isToyukensinFlg")
    private boolean m_isToyukensinFlg = false;
    @SerializedName("mapKensinData")
    private Map<String, List<ShukeiKensinData>> mapKensinData;
    @SerializedName("mapUriageData")
    private Map<String, List<UriageItemDat>> mapUriageData;

    public NippouDat(boolean m_isToyukensinFlg, Map<String, List<ShukeiKensinData>> mapKensinData, Map<String, List<UriageItemDat>> mapUriageData) {
        this.m_isToyukensinFlg = m_isToyukensinFlg;
        this.mapKensinData = mapKensinData;
        this.mapUriageData = mapUriageData;
    }

    public boolean isToyukensinFlg() {
        return m_isToyukensinFlg;
    }

    public void setIsToyukensinFlg(boolean m_isToyukensinFlg) {
        this.m_isToyukensinFlg = m_isToyukensinFlg;
    }

    public Map<String, List<ShukeiKensinData>> getMapKensinData() {
        return mapKensinData;
    }

    public void setMapKensinData(Map<String, List<ShukeiKensinData>> mapKensinData) {
        this.mapKensinData = mapKensinData;
    }

    public Map<String, List<UriageItemDat>> getMapUriageData() {
        return mapUriageData;
    }

    public void setMapUriageData(Map<String, List<UriageItemDat>> mapUriageData) {
        this.mapUriageData = mapUriageData;
    }
}