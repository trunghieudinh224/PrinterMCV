
package jp.co.MarutouCompack.commonClass.PrinterDat;


import com.google.gson.annotations.SerializedName;

/**
 * CNポイントデータクラス.
 */
public class CnpCusDat {
    /** 仮会員フラグ（0: 未会員, 1: 仮会員） */
    @SerializedName("mCnpTemp")
    private byte mCnpTemp = 0;
    /** 本会員フラグ（0: 未会員, 1: 本会員） */
    @SerializedName("mCnpMembers")
    private byte mCnpMembers = 0;
    /** CNポイント */
    @SerializedName("mCnpPoint")
    private int mCnpPoint = 0;
    /** 前月獲得CNポイント */
    @SerializedName("mCnpZpoint")
    private int mCnpZpoint = 0;
    /** 会員ＩＤ */
    @SerializedName("mCnpMembersId")
    private String mCnpMembersId = null;
    /** 残高取得日：年 */
    @SerializedName("mCnpZanymd_y")
    private short mCnpZanymd_y = 0;
    /** 残高取得日：月 */
    @SerializedName("mCnpZanymd_m")
    private byte mCnpZanymd_m = 0;
    /** 残高取得日：日 */
    @SerializedName("mCnpZanymd_d")
    private byte mCnpZanymd_d = 0;

    /**
     * コンストラクタ<br />
     * 値の初期化を行う。
     */
    @SuppressWarnings("unused")
    public CnpCusDat() {
    }

    public CnpCusDat(byte mCnpTemp, byte mCnpMembers, int mCnpPoint, int mCnpZpoint, String mCnpMembersId, short mCnpZanymd_y, byte mCnpZanymd_m, byte mCnpZanymd_d) {
        this.mCnpTemp = mCnpTemp;
        this.mCnpMembers = mCnpMembers;
        this.mCnpPoint = mCnpPoint;
        this.mCnpZpoint = mCnpZpoint;
        this.mCnpMembersId = mCnpMembersId;
        this.mCnpZanymd_y = mCnpZanymd_y;
        this.mCnpZanymd_m = mCnpZanymd_m;
        this.mCnpZanymd_d = mCnpZanymd_d;
    }

    public byte getCnpTemp() {
        return mCnpTemp;
    }

    public void setCnpTemp(byte mCnpTemp) {
        this.mCnpTemp = mCnpTemp;
    }

    public byte getCnpMembers() {
        return mCnpMembers;
    }

    public void setCnpMembers(byte mCnpMembers) {
        this.mCnpMembers = mCnpMembers;
    }

    public int getCnpPoint() {
        return mCnpPoint;
    }

    public void setCnpPoint(int mCnpPoint) {
        this.mCnpPoint = mCnpPoint;
    }

    public int getCnpZpoint() {
        return mCnpZpoint;
    }

    public void setCnpZpoint(int mCnpZpoint) {
        this.mCnpZpoint = mCnpZpoint;
    }

    public String getCnpMembersId() {
        return mCnpMembersId;
    }

    public void setCnpMembersId(String mCnpMembersId) {
        this.mCnpMembersId = mCnpMembersId;
    }

    public short getCnpZanymd_y() {
        return mCnpZanymd_y;
    }

    public void setCnpZanymd_y(short mCnpZanymd_y) {
        this.mCnpZanymd_y = mCnpZanymd_y;
    }

    public byte getCnpZanymd_m() {
        return mCnpZanymd_m;
    }

    public void setCnpZanymd_m(byte mCnpZanymd_m) {
        this.mCnpZanymd_m = mCnpZanymd_m;
    }

    public byte getCnpZanymd_d() {
        return mCnpZanymd_d;
    }

    public void setCnpZanymd_d(byte mCnpZanymd_d) {
        this.mCnpZanymd_d = mCnpZanymd_d;
    }
}
