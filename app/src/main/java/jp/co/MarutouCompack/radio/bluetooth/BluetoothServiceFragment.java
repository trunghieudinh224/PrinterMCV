/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.co.MarutouCompack.radio.bluetooth;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

/**
 * Bluetoothサービス.
 */
public class BluetoothServiceFragment extends Fragment {
    // --------------------------------------------------
    // callback
    // --------------------------------------------------
    public interface OnBluetoothListener {
        /**
         * Bluetooth機能の有無を通知する
         * 
         * @param available [in] Bluetooth機能有無
         */
        void onCheckAvailability(boolean available);

        /**
         * Bluetoothの有効・無効が切り替わった場合に通知される
         * 
         * @param enable    [in] Bluetooth機能のON/OFF
         */
        void onChangeBluetoothState(boolean enable);

        /**
         * デバイスに対して接続した事を通知する
         */
        void onConnected();

        /**
         * デバイスに対して接続することを通知する
         */
        void onConnecting();

        /**
         * デバイスからの受入可能状態であることを通知する
         */
        void onListen();

        /**
         * デバイスに対してデータを書き込んだ事を通知する
         * 
         * @param data  [in] String 書き込むデータ
         */
        void onWrite(String data);

        /**
         * デバイスからデータを受信したことを通知する
         * 
         * @param data  [in] byte[] 受信したデータ
         */
        void onRead(byte[] data);

        /**
         * 接続するデバイス名を通知する
         * 
         * @param device    [in] String デバイス名称
         */
        void onDeviceName(String device);

        /**
         * 接続テストの結果を返す
         * 
         * @param res   [in] boolean    接続結果
         */
        void onConnectTestResult(boolean res);
        
        /**
         * Bluetoothデバイスへの接続エラー
         */
        void onConnectFail();
        
        /**
         * Bluetoothの接続が切れた
         */
        void onConnectLost();
    }

    // --------------------------------------------------
    // Debugging
    // --------------------------------------------------
    /** クラス識別用タグ */
    private static final String TAG = BluetoothServiceFragment.class.getSimpleName();

    // --------------------------------------------------
    // Name for the SDP record when creating server socket
    // --------------------------------------------------
    /** セキュアモード */
    private static final String NAME_SECURE = "BluetoothChatSecure";
    /** アンセキュアモード */
    private static final String NAME_INSECURE = "BluetoothChatInsecure";

    // --------------------------------------------------
    // Unique UUID for this application
    // --------------------------------------------------
    /** セキュアモードUUID */
    private static final UUID MY_UUID_SECURE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    /** アンセキュアモードUUID */
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // --------------------------------------------------
    // Member fields
    // --------------------------------------------------
    /** BTアダプター */
    private BluetoothAdapter mAdapter;
    /** 待受けスレッド(セキュア用) */
    private AcceptThread mSecureAcceptThread;
    /** 待受けスレッド(アンセキュア用) */
    private AcceptThread mInsecureAcceptThread;
    /** 接続スレッド */
    private ConnectThread mConnectThread;
    /** データ転送スレッド */
    private ConnectedThread mConnectedThread;
    /** ステータス */
    private int mState;
    /** 状態通知用リスナー */
    private OnBluetoothListener mCallback;
    /** このフラグメント */
    private static BluetoothServiceFragment mInstance;
    /** BT機能有無 */
    private boolean mIsAvailable;
    /** BT機能有効・無効状態 */
    private boolean mIsEnable;
    /** テストモード */
    private boolean mIsTestMode;
    /** BTデバイスアダプター */
    private BluetoothDeviceAdapter mDiscoveringAdapter;
    /** 接続状態フラグ */
    private boolean mIsConnected;

    // --------------------------------------------------
    // Constants that indicate the current connection state
    // --------------------------------------------------
    /** ステータス：何もしていない */
    public static final int STATE_NONE = 0; // we're doing nothing
    /** ステータス：待受け中 */
    public static final int STATE_LISTEN = 1; // now listening for incoming connections
    /** ステータス：接続中 */
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    /** ステータス：接続完了 */
    public static final int STATE_CONNECTED = 3; // now connected to a remote device

    // --------------------------------------------------
    // Message types sent from the BluetoothChatService Handler
    // --------------------------------------------------
    /** ステータス変更 */
    public static final int MESSAGE_STATE_CHANGE = 1; // change the bluetooth status
    /** データ読み込み */
    public static final int MESSAGE_READ = 2; // read data from device
    /** データ書き込み */
    public static final int MESSAGE_WRITE = 3; // write data to device
    /** デバイス名称 */
    public static final int MESSAGE_DEVICE_NAME = 4; // device name
    /** トースト表示 */
    public static final int MESSAGE_TOAST = 5; // show the Toast
    /** テスト結果 */
    public static final int MESSAGE_TEST = 6; // result to connect test.
    /** 接続失敗 */
    public static final int MESSAGE_CONNECT_FAIL = 7; // failed to connect device.
    /** 切断 */
    public static final int MESSAGE_CONNECT_LOST = 8;

    // --------------------------------------------------
    // Key names received from the BluetoothChatService Handler
    // --------------------------------------------------
    /** キー：デバイス名称 */
    public static final String DEVICE_NAME = "device_name";
    /** キー：トースト */
    public static final String TOAST = "toast";

    // --------------------------------------------------
    // key the argument
    // --------------------------------------------------
    /** キー：テストモード */
    public static final String KEY_TEST_MODE = "KEY_TEST_MODE";

    // --------------------------------------------------
    // intentのリクエストコード
    // --------------------------------------------------
    /** BTリクエストキー */
    private static final int REQUEST_ENABLE_BT = 11;

    // --------------------------------------------------
    // BluetoothChatServiceFragmentのイベント処理用のハンドラー
    // --------------------------------------------------
    private static class BTHandler extends Handler{
        /** このフラグメント(弱い参照) */
        private final WeakReference<BluetoothServiceFragment> mWeakReference;

        /**
         * コンストラクタ.
         *
         * @param fragment  [in] {@link BluetoothServiceFragment}   このフラグメント
         */
        private BTHandler(BluetoothServiceFragment fragment){
            mWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            BluetoothServiceFragment fragment = mWeakReference.get();
            if(fragment == null){
                return;
            }
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE: // Bluetoothのステータスが変更された
                    MLog.DEBUG(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case STATE_CONNECTED:
                            // デバイスへの接続通知
                            fragment.mCallback.onConnected();
                            break;
                        case STATE_CONNECTING:
                            // デバイスへの接続開始通知
                            fragment.mCallback.onConnecting();
                            break;
                        case STATE_LISTEN:
                        case STATE_NONE:
                            // デバイスからの待ち受け通知
                            fragment.mCallback.onListen();
                            break;
                    }
                    break;
                case MESSAGE_WRITE: // 接続先デバイスにデータを書き込んだ
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    fragment.mCallback.onWrite(writeMessage);
                    break;
                case MESSAGE_READ: // 接続先デバイスからデータが書き込まれた
                    byte[] readBuf = (byte[]) msg.obj;
                    fragment.mCallback.onRead(readBuf);
                    break;
                case MESSAGE_DEVICE_NAME: // 接続デバイス名を通知
                    // save the connected device's name
                    fragment.mCallback.onDeviceName(msg.getData().getString(DEVICE_NAME));
                    break;
                case MESSAGE_CONNECT_FAIL: // デバイスへの接続に失敗
                    fragment.mCallback.onConnectFail();
                    break;
                case MESSAGE_CONNECT_LOST: // デバイスへの接続が切れた
                    fragment.mCallback.onConnectLost();
                    break;
                case MESSAGE_TOAST: // Toast通知
                    break;
                case MESSAGE_TEST: // 接続テスト
                    fragment.mCallback.onConnectTestResult(msg.getData().getBoolean(KEY_TEST_MODE));
                    break;
            }
        }
    }
    private final Handler mHandler = new BTHandler(this);

    /**
     * BluetoothChatServiceFragmentのインスタンスを取得する
     * 
     * @return  {@link BluetoothServiceFragment}    このフラグメント
     */
    public static BluetoothServiceFragment getInstance() {
        mInstance = new BluetoothServiceFragment();
        mInstance.stop();
        return mInstance;
    }

    /**
     * テストモードを設定する<br />
     * テストモードを設定した場合、接続テストのみまで実施する
     * 
     * @param mode  [in] boolean    テストモードフラグ
     * @return  {@link BluetoothServiceFragment}    このフラグメント
     */
    public BluetoothServiceFragment setTestMode(boolean mode) {
        if (mInstance == null) {
            throw new IllegalArgumentException("BluetoothServiceFragmentインスタンスが生成されていません。");
        }
        Bundle args = getArguments();
        if (args == null) {
            args = new Bundle();
        }
        args.putBoolean(KEY_TEST_MODE, mode);
        if(!mInstance.isAdded()){
            mInstance.setArguments(args);
        }
        return mInstance;
    }

    /**
     * 空のコンストラクタを呼び出す
     */
    public BluetoothServiceFragment() {
    }

    /**
     * Bluetoot機能の存在有無を取得する<br />
     * true: 機能有り<br />
     * false: 機能無し
     * 
     * @return  boolean BT機能有無
     */
    @SuppressWarnings("unused")
    public boolean isAvailable() {
        return mIsAvailable;
    }

    /**
     * Bluetooth機能の有効・無効状態を取得する<br />
     * true: 有効<br />
     * false: 無効
     * 
     * @return  boolean BT有効・無効
     */
    public boolean isEnable() {
        return mIsEnable;
    }

    /**
     * Bluetoothデバイス接続状態を取得する<br />
     * true: 接続済み<br />
     * false: 未接続
     * 
     * @return  boolean 接続有無
     */
    public boolean isConnected() {
        return mIsConnected;
    }

    // --------------------------------------------------
    // Fragmentからのcallbackメソッド
    // --------------------------------------------------
    @Override
    public void onAttach(Context context) {
        if (context instanceof OnBluetoothListener) {
            mCallback = (OnBluetoothListener) context;
        }
        else {
            // Activityが実装していない場合はFragmentを確認する
            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof OnBluetoothListener) {
                mCallback = (OnBluetoothListener) parentFragment;
            }
            else {
                // callbackが実装されていない
                throw new ClassCastException("BluetoothServiceFragmentはOnBluetoothListenerを実装する必要があります。");
            }
        }
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mState = STATE_NONE;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            savedInstanceState = getArguments();
        }
        if(savedInstanceState == null){
            savedInstanceState = new Bundle();
        }
        mIsTestMode = savedInstanceState.getBoolean(KEY_TEST_MODE, false);
        if (mAdapter == null) {
            mAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        mIsAvailable = mAdapter != null; // Bluetooth機能の有無
        // 利用元にBluetoothの存在有無を通知
        mCallback.onCheckAvailability(mIsAvailable);

        if (!mIsAvailable) {
            return;
        }

        // Bluetooth機能が無効化されている場合は有効化する
        if (mAdapter.isEnabled()) {
            mIsEnable = true;
        }
        else {
            // Bluetooth機能が無効化されている場合は有効化してみる
            mIsEnable = false;
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        // Bluetoothデバイスの現在の状態を利用元に通知する
        mCallback.onChangeBluetoothState(mIsEnable);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_TEST_MODE, mIsTestMode);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            mIsEnable = resultCode == Activity.RESULT_OK;
            mCallback.onChangeBluetoothState(mIsEnable);
        }
    }

    /**
     * 現在の状態を設定.
     * 
     * @param state [in] int    現在の状態
     */
    private synchronized void setState(int state) {
        MLog.DEBUG(TAG, "BT状態変更:" + mState + " -> " + state);
        mState = state;
        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }

    /**
     * 現在の状態を取得.
     *
     * @return int  現在の状態
     */
    public synchronized int getState() {
        return mState;
    }

    /**
     * Start the Bluetoothservice. Specifically start AcceptThread to begin a session in listening (server) mode. Called by the Activity onResume()
     */
    public synchronized void start() {
        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        setState(STATE_LISTEN);

        // Start the thread to listen on a BluetoothServerSocket
        if (mSecureAcceptThread == null) {
            mSecureAcceptThread = new AcceptThread(true);
            mSecureAcceptThread.start();
        }
        if (mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread(false);
            mInsecureAcceptThread.start();
        }
    }

    /**
     * 指定されたBluetoothデバイスに対して接続を開始する
     * 
     * @param deviceName    [in] String     BTデバイス
     * @param secure        [in] boolean    セキュアモードフラグ
     */
    public synchronized void connect(String deviceName, boolean secure) {
        MLog.DEBUG(TAG, "connect to: " + deviceName);
        BluetoothDevice device = mAdapter.getRemoteDevice(deviceName);
        if (device == null) {
            return;
        }
        connect(device, secure);
    }

    /**
     * Start the ConnectThread to initiate a connection to a remote device.
     *
     * @param device    [in] {@link BluetoothDevice}    BTデバイス
     * @param secure    [in] boolean                    セキュアモードフラグ
     */
    public synchronized void connect(BluetoothDevice device, boolean secure) {
        MLog.DEBUG(TAG, "BT接続開始: " + device);
        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(device, secure);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     * 
     * @param socket The BluetoothSocket on which the connection was made
     * @param device The BluetoothDevice that has been connected
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device, final String socketType) {
        MLog.DEBUG(TAG, "connected, Socket Type:" + socketType);

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Cancel the accept thread because we only want to connect to one device
        if (mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }
        if (mInsecureAcceptThread != null) {
            mInsecureAcceptThread.cancel();
            mInsecureAcceptThread = null;
        }

        // テストモードの場合は接続後スレッドを起動しない
        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket, socketType);
        mConnectedThread.start();
        setState(STATE_CONNECTED);
        // Send the name of the connected device back to the UI Activity
        Message msg = mHandler.obtainMessage(MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(DEVICE_NAME, device.getAddress());
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        if (mIsTestMode) {
            stop();
        }
    }

    /**
     * Stop all threads
     */
    public synchronized void stop() {
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        if (mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }

        if (mInsecureAcceptThread != null) {
            mInsecureAcceptThread.cancel();
            mInsecureAcceptThread = null;
        }

        setState(STATE_NONE);
        mIsConnected = false;
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     * 
     * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out) {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED) {
                return;
            }
            r = mConnectedThread;
        }
        // Perform the write unsynchronized
        r.write(out);
    }

    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void connectionFailed() {
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(MESSAGE_CONNECT_FAIL);
        Bundle bundle = new Bundle();
        bundle.putString(TOAST, "Unable to connect device");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        mIsConnected = false;
        // Start the service over to restart listening mode
        BluetoothServiceFragment.this.start();
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private void connectionLost() {
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(MESSAGE_CONNECT_LOST);
        Bundle bundle = new Bundle();
        bundle.putString(TOAST, "Device connection was lost");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        mIsConnected = false;
        // Start the service over to restart listening mode
        BluetoothServiceFragment.this.start();
    }

    /**
     * This thread runs while listening for incoming connections. It behaves like a server-side client. It runs until a connection is accepted (or
     * until cancelled).
     */
    private class AcceptThread extends Thread {
        // The local server socket
        private final BluetoothServerSocket mmServerSocket;
        private final String mSocketType;

        public AcceptThread(boolean secure) {
            BluetoothServerSocket tmp = null;
            mSocketType = secure ? "Secure" : "Insecure";

            // Create a new listening server socket
            try {
                if (secure) {
                    tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, MY_UUID_SECURE);
                }
                else {
                    tmp = mAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME_INSECURE, MY_UUID_INSECURE);
                }
            }
            catch (IOException e) {
                MLog.ERROR(getContext(), TAG, e);
            }
            mmServerSocket = tmp;
        }

        public void run() {
            MLog.DEBUG(TAG, "Socket Type: " + mSocketType + "BEGIN mAcceptThread" + this);
            setName("AcceptThread" + mSocketType);

            BluetoothSocket socket;
            if(mmServerSocket == null){
                return;
            }

            // Listen to the server socket if we're not connected
            while (mState != STATE_CONNECTED) {
                try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                    socket = mmServerSocket.accept();
                }
                catch (IOException e) {
                    MLog.ERROR(getContext(), TAG, e);
                    break;
                }

                // If a connection was accepted
                if (socket != null) {
                    synchronized (BluetoothServiceFragment.this) {
                        switch (mState) {
                            case STATE_LISTEN:
                            case STATE_CONNECTING:
                                // Situation normal. Start the connected thread.
                                connected(socket, socket.getRemoteDevice(), mSocketType);
                                break;
                            case STATE_NONE:
                            case STATE_CONNECTED:
                                // Either not ready or already connected. Terminate new socket.
                                try {
                                    socket.close();
                                }
                                catch (IOException e) {
                                    MLog.ERROR(getContext(), TAG, e);
                                }
                                break;
                        }
                    }
                }
            }
            MLog.DEBUG(TAG, "END mAcceptThread, socket Type: " + mSocketType);
        }

        public void cancel() {
            MLog.DEBUG(TAG, "Socket Type" + mSocketType + "cancel " + this);
            if(mmServerSocket == null){
                MLog.INFO(getContext(), TAG, "mmServerSocket is null.");
                return;
            }
            try {
                mmServerSocket.close();
            }
            catch (IOException e) {
                MLog.ERROR(getContext(), TAG, e);
            }
        }
    }

    /**
     * This thread runs while attempting to make an outgoing connection with a device. It runs straight through; the connection either succeeds or
     * fails.
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private final String mSocketType;

        public ConnectThread(BluetoothDevice device, boolean secure) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            mSocketType = secure ? "Secure" : "Insecure";

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                if (secure) {
                    tmp = device.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
                }
                else {
                    tmp = device.createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE);
                }
            }
            catch (IOException e) {
                MLog.ERROR(getContext(), TAG, e);
            }
            mmSocket = tmp;
        }

        public void run() {
            MLog.INFO(TAG, "BEGIN mConnectThread SocketType:" + mSocketType);
            setName("ConnectThread" + mSocketType);

            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            }
            catch (IOException e) {
                // Close the socket
                MLog.WARN(getContext(), TAG, e);
                try {
                    mmSocket.close();
                }
                catch (IOException e2) {
                    MLog.ERROR(getContext(), TAG, e2);
                }
                connectionFailed();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (BluetoothServiceFragment.this) {
                mConnectThread = null;
            }

            // Start the connected thread
            connected(mmSocket, mmDevice, mSocketType);
        }

        public void cancel() {
            try {
                mmSocket.close();
            }
            catch (IOException e) {
                MLog.ERROR(getContext(), TAG, e);
            }
        }
    }

    /**
     * This thread runs during a connection with a remote device. It handles all incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket, String socketType) {
            MLog.DEBUG(TAG, "create ConnectedThread: " + socketType);
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            }
            catch (IOException e) {
                MLog.ERROR(getContext(), TAG, e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
            mIsConnected = true;
        }

        public void run() {
            MLog.INFO(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;
            int idx = 0;
            byte[] recvBytes = new byte[40];
            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                    for (int i = 0; i < bytes; i++) {
                        recvBytes[idx++] = buffer[i];
                    }
                    if (idx >= 40) {
                        // Send the obtained bytes to the UI Activity
                        mHandler.obtainMessage(MESSAGE_READ, 40, -1, recvBytes).sendToTarget();
                        recvBytes = new byte[40];
                        idx = 0;
                    }
                }
                catch (IOException e) {
                    MLog.ERROR(getContext(), TAG, e);
                    connectionLost();
                    // Start the service over to restart listening mode
                    BluetoothServiceFragment.this.start();
                    break;
                }
            }
        }

        /**
         * Write to the connected OutStream.
         * 
         * @param buffer The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);

                // Share the sent message back to the UI Activity
                mHandler.obtainMessage(MESSAGE_WRITE, -1, -1, buffer).sendToTarget();
            }
            catch (IOException e) {
                MLog.ERROR(getContext(), TAG, e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            }
            catch (IOException e) {
                MLog.ERROR(getContext(), TAG, e);
            }
        }
    }

    // --------------------------------------------------
    // Bluetooth探索制御
    // --------------------------------------------------
    /**
     * ペアリング済みデバイスを選択する
     */
    public void selectPairedDevice() {
        // 3. ペアリング済みデバイス一覧を表示
        Set<BluetoothDevice> pairedDevices = mAdapter.getBondedDevices();
        // ペアリング済みデバイス一覧が存在する場合
        // 一覧に追加する
        ArrayList<BluetoothDevice> deviceList = new ArrayList<>(pairedDevices);
        final BluetoothDeviceAdapter adapter = new BluetoothDeviceAdapter(getActivity(), deviceList);

        // デバイスを選択するためのダイアログを設定
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Dialog);
        builder.setTitle("ペアリング済みデバイス");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 選択したデバイスに対して接続を開始する
                BluetoothDevice selectedDevice = adapter.getItem(which);
                mCallback.onDeviceName(Objects.requireNonNull(selectedDevice).getAddress());
                connect(selectedDevice.getAddress(), true);
            }
        });
        // いいえボタン設定
        builder.setNegativeButton(android.R.string.cancel, null);
        Context context = getActivity();
        PackageManager manager = Objects.requireNonNull(context).getPackageManager();
        int permission = manager.checkPermission(Manifest.permission.BLUETOOTH_ADMIN, context.getPackageName());
        if (permission == PackageManager.PERMISSION_GRANTED) {
            builder.setPositiveButton("デバイスを探索する", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // requuest permission for using bluetooth
                    checkPermissionForBluetooth();
                }
            });
        }
        builder.show();
    }

    /**
     * デバイス探索を実施する
     */
    private void discoveringDevices() {
        // 4. デバイスを探索する
        ArrayList<BluetoothDevice> deviceList = new ArrayList<>();
        // アダプターを設定
        mDiscoveringAdapter = new BluetoothDeviceAdapter(getActivity(), deviceList);

        // デバイス一覧を表示するための設定
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Dialog);
        builder.setTitle("デバイス探索中...");
        builder.setAdapter(mDiscoveringAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // デバイスが選択されたので探索を終了する
                stopDiscovery();
                BluetoothDevice selectedDevice = mDiscoveringAdapter.getItem(which);
                mCallback.onDeviceName(Objects.requireNonNull(selectedDevice).getAddress());
                connect(selectedDevice.getAddress(), true);
            }
        });
        // いいえボタン設定
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // デバイスの探索を終了する
                stopDiscovery();
            }
        });
        builder.show();
        // デバイスの探索を開始する
        startDiscovery();
    }

    /**
     * Bluetooth権限の確認.
     */
    public void checkPermissionForBluetooth(){
        // Beginning in Android 6.0 MarshMallow(API level 23), users grant permissions to apps
        // while the app is running, not when they install the app.
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // Quick permission check
            int permissionCheck = ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), "Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += ActivityCompat.checkSelfPermission(getActivity(), "Manifest.permission.ACCESS_COARSE_LOCATION");

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
            }else {
                // デバイスの探索を開始する
                discoveringDevices();
            }
        }else{
            // デバイスの探索を開始する
            discoveringDevices();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1001) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay!

                // デバイスの探索を開始する
                discoveringDevices();

            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                System.out.print("permission denied, boo!");
                // デバイス一覧を表示するための設定
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Dialog);
                builder.setTitle("Permission denied");
                builder.setMessage("無線検針を行うには置情報について許可する必要があります。");
                // いいえボタン設定
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.show();
            }
        }
    }

    /**
     * Bluetoothデバイスの探索を開始する
     */
    private void startDiscovery() {
        // Reciverに登録する
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        Objects.requireNonNull(getActivity()).registerReceiver(mDiscoveringReceiver, filter);
        // デバイス探索開始
        mAdapter.startDiscovery();
    }

    /**
     * Bluetoothデバイスの探索を終了する
     */
    private void stopDiscovery() {
        // デバイス探索終了
        mAdapter.cancelDiscovery();
        // Reciverへの登録を解除する
        Objects.requireNonNull(getActivity()).unregisterReceiver(mDiscoveringReceiver);
    }

    /**
     * デバイス探索用のブロードキャストレシーバーを設定する
     */
    final BroadcastReceiver mDiscoveringReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Bluetoothデバイスデータを取得して設定する
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            mDiscoveringAdapter.add(device);
        }
    };

    /**
     * 内部クラス<br />
     * ペアリング済みのBluetoothデバイス一覧をアダプターに登録する
     * 
     * @author 株式会社マルトウコンパック
     */
    public static class BluetoothDeviceAdapter extends ArrayAdapter<BluetoothDevice> {
        /**
         * コンストラクター
         * 
         * @param context   [in] {@link Context}                呼び出し元コンテキス
         * @param objects   [in] {@code List<BluetoothDevice>}  ペアリング済みBluetoothデバイス一覧
         */
        public BluetoothDeviceAdapter(Context context, List<BluetoothDevice> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            }

            // BluetoothDeviceを取得する
            BluetoothDevice device = getItem(position);

            // BluetoothDevice名を設定する
            TextView nameView = convertView.findViewById(android.R.id.text1);
            nameView.setText(Objects.requireNonNull(device).getName());

            // BluetoothDevieのアドレスを設定する
            TextView addressView = convertView.findViewById(android.R.id.text2);
            addressView.setText(device.getAddress());
            return convertView;
        }
    }
}
