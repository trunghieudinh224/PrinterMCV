package jp.co.MarutouCompack.Printer.loader;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import java.io.IOException;
import java.util.UUID;

import jp.co.MarutouCompack.baseClass.DEFINE;
import jp.co.MarutouCompack.baseClass.EnumStatus;
import jp.co.MarutouCompack.baseClass.MException;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.commonClass.PrinterDat.RegEdit;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * データ転送処理(Bluetooth).
 * <br>接続処理を実装.
 */
public class BTDataTransferLoader extends DataTransferLoader {

    // --------------------------------------------------
    // 定数
    // --------------------------------------------------
    /** クラス識別用タグ */
    private static final String TAG = BTDataTransferLoader.class.getSimpleName();
    /** SPP UUID */
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // --------------------------------------------------
    // 変数
    // --------------------------------------------------
    /** BLuetooth通信ソケット */
    private BluetoothSocket mBtSocket = null;

    /**
     * コンストラクタ.
     *
     * @param context           {@link Context}                     呼び出し元コンテキスト
     * @param enumTransferMode  {@link DEFINE.ENUM_TRANSFER_MODE}   データ転送モード
     */
    public BTDataTransferLoader(Context context, DEFINE.ENUM_TRANSFER_MODE enumTransferMode){
        super(context, enumTransferMode);
    }

    @Override
    public EnumStatus connect() {
        EnumStatus res = EnumStatus.STATUS_OK;

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if(btAdapter.isDiscovering()){
            // BTデバイス検索中の場合はキャンセル
            btAdapter.cancelDiscovery();
        }
        BluetoothDevice btDevice;
        try {
            btDevice = btAdapter.getRemoteDevice(RegEdit.getBTAddress(getContext()));
        }
        catch (MException e){
            MLog.ERROR(getContext(), TAG, e);
            return EnumStatus.STATUS_ERROR_CONNECT;
        }

        if(btDevice == null){
            // 接続先BTデバイスが存在しない
            return EnumStatus.STATUS_ERROR_CONNECT;
        }
        try{
            mBtSocket = btDevice.createRfcommSocketToServiceRecord(MY_UUID);
        }
        catch(IOException ioe){
            // 接続先BTデバイスのソケット取得失敗
            MLog.ERROR(getContext(), TAG, ioe);
            return EnumStatus.STATUS_ERROR_CONNECT;
        }

        if(mBtSocket == null ){
            // ソケットが存在しない
            return EnumStatus.STATUS_ERROR_CONNECT;
        }
        try{
            mBtSocket.connect();
        }
        catch (IOException ioe){
            MLog.ERROR(getContext(), TAG, ioe);
            return EnumStatus.STATUS_ERROR_CONNECT;
        }

        try {
            mIs = mBtSocket.getInputStream();
        }
        catch (IOException ioe){
            MLog.ERROR(getContext(), TAG, ioe);
            res = EnumStatus.STATUS_ERROR_GET_INPUTSTREAM;
        }
        try {
            mOs = mBtSocket.getOutputStream();
        }
        catch (IOException ioe){
            MLog.ERROR(getContext(), TAG, ioe);
            res = EnumStatus.STATUS_ERROR_GET_OUTPUTSTREAM;
        }
        return res;
    }

    @Override
    public EnumStatus disconnect(){
        OtherUtil.closeOutputStream(getContext(), mOs);
        OtherUtil.closeInputStream(getContext(), mIs);
        try {
            if (mBtSocket != null) {
                mBtSocket.close();
            }
        }
        catch (IOException ioe){
            MLog.WARN(getContext(), TAG, ioe);
        }
        finally {
            mBtSocket = null;
        }
        return EnumStatus.STATUS_OK;
    }
}
