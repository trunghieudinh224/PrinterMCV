
package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

import jp.co.MarutouCompack.commonClass.PrinterDat.ShukeiKensinData;

/**
 * 販売店データクラス.
 */
public class WebData {
    @SerializedName("type")
    private String type;
    @SerializedName("printStatus")
    private PrintStatus printStatus;
    @SerializedName("isHybseikyu")
    private boolean isHybseikyu;
    @SerializedName("isHikae")
    private boolean isHikae;
    @SerializedName("mUserData")
    private UserData mUserData;
    @SerializedName("kensinData")
    private KensinData kensinData;
    @SerializedName("androidKensinDat")
    private AKensinDat aKensinDat;
    @SerializedName("androidNyukinDat")
    private ANyukinDat aNyukinDat;
    @SerializedName("lstComment")
    private String[] lstComment;
    @SerializedName("sTantname")
    private String sTantname;
    @SerializedName("shukeiDat")
    private ShukeiDat shukeiDat;
    @SerializedName("nippouDat")
    private NippouDat nippouDat;
    @SerializedName("printGenuriInfo")
    private PrintGenuriInfo printGenuriInfo;
    @SerializedName("printMode")
    private int printMode;
    @SerializedName("cusData")
    private CusData cusData;

    public WebData(String type, PrintStatus printStatus, boolean isHybseikyu, boolean isHikae, UserData mUserData, KensinData kensinData,
                   AKensinDat aKensinDat, ANyukinDat aNyukinDat, String[] lstComment, String sTantname, ShukeiDat shukeiDat,
                   NippouDat nippouDat, PrintGenuriInfo printGenuriInfo, int printMode, CusData cusData) {
        this.type = type;
        this.printStatus = printStatus;
        this.isHybseikyu = isHybseikyu;
        this.isHikae = isHikae;
        this.mUserData = mUserData;
        this.kensinData = kensinData;
        this.aKensinDat = aKensinDat;
        this.aNyukinDat = aNyukinDat;
        this.lstComment = lstComment;
        this.sTantname = sTantname;
        this.shukeiDat = shukeiDat;
        this.nippouDat = nippouDat;
        this.printGenuriInfo = printGenuriInfo;
        this.printMode = printMode;
        this.cusData = cusData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PrintStatus getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(PrintStatus printStatus) {
        this.printStatus = printStatus;
    }

    public boolean isHybseikyu() {
        return isHybseikyu;
    }

    public void setHybseikyu(boolean hybseikyu) {
        isHybseikyu = hybseikyu;
    }

    public boolean isHikae() {
        return isHikae;
    }

    public void setHikae(boolean hikae) {
        isHikae = hikae;
    }

    public void setUserData(UserData mUserData) {
        this.mUserData = mUserData;
    }

    public UserData getUserData() {
        return mUserData;
    }

    public KensinData getKensinData() {
        return kensinData;
    }

    public void setKensinData(KensinData kensinData) {
        this.kensinData = kensinData;
    }

    public AKensinDat getAKensinDat() {
        return aKensinDat;
    }

    public void setAKensinDat(AKensinDat aKensinDat) {
        this.aKensinDat = aKensinDat;
    }

    public ANyukinDat getANyukinDat() {
        return aNyukinDat;
    }

    public void setANyukinDat(ANyukinDat aNyukinDat) {
        this.aNyukinDat = aNyukinDat;
    }

    public String[] getLstComment() {
        return lstComment;
    }

    public void setLstComment(String[] lstComment) {
        this.lstComment = lstComment;
    }

    public String getTantname() {
        return sTantname;
    }

    public void setTantname(String sTantname) {
        this.sTantname = sTantname;
    }

    public ShukeiDat getShukeiDat() {
        return shukeiDat;
    }

    public void setShukeiDat(ShukeiDat shukeiDat) {
        this.shukeiDat = shukeiDat;
    }

    public NippouDat getNippouDat() {
        return nippouDat;
    }

    public void setNippouDat(NippouDat nippouDat) {
        this.nippouDat = nippouDat;
    }

    public PrintGenuriInfo getPrintGenuriInfo() {
        return printGenuriInfo;
    }

    public void setPrintGenuriInfo(PrintGenuriInfo printGenuriInfo) {
        this.printGenuriInfo = printGenuriInfo;
    }

    public int getPrintMode() {
        return printMode;
    }

    public void setPrintMode(int printMode) {
        this.printMode = printMode;
    }

    public CusData getCusData() {
        return cusData;
    }

    public void setCusData(CusData cusData) {
        this.cusData = cusData;
    }
}
