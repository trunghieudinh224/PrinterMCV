package jp.co.MarutouCompack.commonClass.PrinterDat;

import com.google.gson.annotations.SerializedName;

public class GasryokinSiki {
    @SerializedName("gasfDat")
    private GasfDat gasfDat;
    @SerializedName("bIsVisibleGas")
    private boolean bIsVisibleGas;
    @SerializedName("bIsPrintGasRyokinSiki")
    private boolean bIsPrintGasRyokinSiki;
    @SerializedName("nPrintGasRyokinSikiPtn")
    private int nPrintGasRyokinSikiPtn;
    @SerializedName("nGasBaseKin")
    private int nGasBaseKin;
    @SerializedName("bPrintGasFacilityKin")
    private boolean bPrintGasFacilityKin;
    @SerializedName("nFacilityKin")
    private int nFacilityKin;
    @SerializedName("nGasTotalKinWithoutTax")
    private int nGasTotalKinWithoutTax;

    public GasryokinSiki(GasfDat gasfDat, boolean bIsVisibleGas, boolean bIsPrintGasRyokinSiki, int nPrintGasRyokinSikiPtn, int nGasBaseKin, boolean bPrintGasFacilityKin, int nFacilityKin, int nGasTotalKinWithoutTax) {
        this.gasfDat = gasfDat;
        this.bIsVisibleGas = bIsVisibleGas;
        this.bIsPrintGasRyokinSiki = bIsPrintGasRyokinSiki;
        this.nPrintGasRyokinSikiPtn = nPrintGasRyokinSikiPtn;
        this.nGasBaseKin = nGasBaseKin;
        this.bPrintGasFacilityKin = bPrintGasFacilityKin;
        this.nFacilityKin = nFacilityKin;
        this.nGasTotalKinWithoutTax = nGasTotalKinWithoutTax;
    }

    public GasfDat getGasfDat() {
        return gasfDat;
    }

    public void setGasfDat(GasfDat gasfDat) {
        this.gasfDat = gasfDat;
    }

    public boolean isVisibleGas() {
        return bIsVisibleGas;
    }

    public void setIsVisibleGas(boolean bIsVisibleGas) {
        this.bIsVisibleGas = bIsVisibleGas;
    }

    public boolean isPrintGasRyokinSiki() {
        return bIsPrintGasRyokinSiki;
    }

    public void setIsPrintGasRyokinSiki(boolean bIsPrintGasRyokinSiki) {
        this.bIsPrintGasRyokinSiki = bIsPrintGasRyokinSiki;
    }

    public int getPrintGasRyokinSikiPtn() {
        return nPrintGasRyokinSikiPtn;
    }

    public void setPrintGasRyokinSikiPtn(int nPrintGasRyokinSikiPtn) {
        this.nPrintGasRyokinSikiPtn = nPrintGasRyokinSikiPtn;
    }

    public int getGasBaseKin() {
        return nGasBaseKin;
    }

    public void setGasBaseKin(int nGasBaseKin) {
        this.nGasBaseKin = nGasBaseKin;
    }

    public boolean isPrintGasFacilityKin() {
        return bPrintGasFacilityKin;
    }

    public void setPrintGasFacilityKin(boolean bPrintGasFacilityKin) {
        this.bPrintGasFacilityKin = bPrintGasFacilityKin;
    }

    public int getFacilityKin() {
        return nFacilityKin;
    }

    public void setFacilityKin(int nFacilityKin) {
        this.nFacilityKin = nFacilityKin;
    }

    public int getGasTotalKinWithoutTax() {
        return nGasTotalKinWithoutTax;
    }

    public void setGasTotalKinWithoutTax(int nGasTotalKinWithoutTax) {
        this.nGasTotalKinWithoutTax = nGasTotalKinWithoutTax;
    }
}
