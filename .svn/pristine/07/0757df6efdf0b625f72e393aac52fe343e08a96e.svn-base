package jp.co.MarutouCompack.Printer.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import jp.co.MarutouCompack.baseClass.DEFINE;
import jp.co.MarutouCompack.baseClass.EnumStatus;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;

abstract class DataTransferLoader extends AsyncTaskLoader<EnumStatus> {

    // --------------------------------------------------
    // 定数
    // --------------------------------------------------
    private static final String TAG = DataTransferLoader.class.getSimpleName();

    // --------------------------------------------------
    // 変数
    // --------------------------------------------------
    /** 入力用ストリーム */
    InputStream mIs = null;
    /** 出力用ストリーム */
    OutputStream mOs = null;
    /** 終了フラグ */
    boolean m_bFinished = false;
    /** テンプレートファイルサイズ */
    private int mTemplateFileSize = 0;
    /** テンプレートファイル名 */
    private String mTemplateFileName = null;
    /** テンプレートファイル */
    private File mTemplateFile = null;
    /** データ転送モード */
    DEFINE.ENUM_TRANSFER_MODE mEnumTransferMode;
    /** キャンセルフラグ */
    private boolean m_bCanceled = false;
    /** バックアップ完了フラグ */
    private boolean m_isBackupComp = false;
    /** データ転送開始済みフラグ */
    private boolean m_isLoaded = false;
    /** 更新ファイル有り */
    private boolean m_isUpdate = false;
    /** 送信済みフラグ */
    private boolean m_isSend = false;
    /** エラー発生フラグ */
    private boolean m_isError = false;
    /** ファイル送信フラグ */
    private boolean m_isSendFile = false;
    /** 転送開始フラグ */
    private boolean m_isStart = false;

    /**
     * 後方側に対して接続を開始
     *
     * @return {@link EnumStatus}   接続ステータス
     */
    public abstract EnumStatus connect();

    /**
     * 後方側との接続を切断
     *
     * @return {@link EnumStatus}   接続ステータス
     */
    public abstract EnumStatus disconnect();

    /**
     * コンストラクター
     *
     * @param context [in] Context 呼び出し元コンテキスト
     */
    DataTransferLoader(Context context, DEFINE.ENUM_TRANSFER_MODE enumTransferMode) {
        super(context);
        mEnumTransferMode = enumTransferMode;
    }

    @Override
    public EnumStatus loadInBackground() {
        EnumStatus res = EnumStatus.STATUS_OK;

        if(!m_isLoaded){
            m_isLoaded = true;

            // only run if it's the first time
            if (OtherUtil.dataBackup(getContext(), mEnumTransferMode)) {
                try {
                    // 後方側との接続を開始
                    OtherUtil.sendBroadCastMessage(getContext(), "接続を開始");
                    res = connect();
                    if (res != EnumStatus.STATUS_OK) {
                        MLog.ERROR(getContext(), TAG, res.getStatus());
                        OtherUtil.sendBroadCastMessage(getContext(), "接続失敗");
                        OtherUtil.sendBroadCastMessage(getContext(), "転送方法、接続先を確認してください");
                        return res;
                    }
                    mIs = new BufferedInputStream(mIs, 512000);
                    mOs = new BufferedOutputStream(mOs, 512000);
                    switch (mEnumTransferMode) {
                        case RECIEVE:   // データ受信
                            if (!recieveData()) {
                                res = EnumStatus.STATUS_ERROR_RECEIVE;
                            }
                            if (m_isUpdate) {
                                res = EnumStatus.STATUS_UPDATE;
                            }
                            break;
                        case SEND:  // データ送信
                            if (!sendData()) {
                                res = EnumStatus.STATUS_ERROR_SEND;
                            }
                            break;
                    }
                } catch (IOException ioe) {
                    MLog.ERROR(getContext(), TAG, ioe);
                    res = EnumStatus.STATUS_UNKNOWN;
                } finally {
                    disconnect();
                    OtherUtil.closeInputStream(getContext(), mIs);
                    OtherUtil.closeOutputStream(getContext(), mOs);
                    OtherUtil.sendBroadCastMessage(getContext(), "切断しました");
                }
            }
            else {
                res = EnumStatus.STATUS_ERROR_BACKUP;
            }
        }else{
            m_isLoaded = false;
        }
        return res;
    }

    /**
     * データ送信
     *
     * @return  boolean 送信成否 true: 成功, false: 失敗
     * @throws IOException  データ入出力でエラーがあった場合に発生
     */
    protected boolean sendData() throws IOException {
        m_isBackupComp = false;
        sendStartSend();
        // 転送開始通知を待つ
        m_bCanceled = false;
        m_isStart = false;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!m_isStart) {
                    m_bCanceled = true;
                }
            }
        }, TimeUnit.SECONDS.toMillis(30));
        while (!m_isBackupComp) {
            MLog.DEBUG(getContext(), TAG, "Start Receive受信待ち");
            if (m_bCanceled) {
                // キャンセルフラグ有り
                sendCancel();
                return false;
            }
            readData();
        }
        timer.cancel();
        m_isStart = true;
        return fileSendStart();
    }

    /**
     * データ送信.
     *
     * @param data      [in] byte[]     送信対象データ
     * @param isRetry   [in] boolean    リトライによる送信かどうかのフラグ
     * @throws IOException データ送信エラー
     */
    protected void send(byte[] data, boolean isRetry) throws IOException {
        mOs.write(data);
        if(this instanceof BTDataTransferLoader && isRetry) {
            // Bluetooth通信でリトライの場合
            try{
                Thread.sleep( 500 );
            }
            catch (InterruptedException ie){
                MLog.WARN(getContext(), TAG, ie);
            }
            mOs.flush();
            try{
                Thread.sleep( 500 );
            }
            catch (InterruptedException ie){
                MLog.WARN(getContext(), TAG, ie);
            }
        }
    }

    /**
     * ファイル送信開始
     *
     * @return boolean
     */
    private boolean fileSendStart() throws IOException {
        try {
            m_isSend = false;
            m_isSendFile = false;
            m_isError = false;

            StringBuilder sbf;
            File i_fl;
            File fromFile = new File(DEFINE.getDataPath(getContext()));
            String[] lstFiles = fromFile.list();
            boolean isRetry = false;
            for (int i = 0; i < lstFiles.length; i++) {
                if(!DEFINE.MAP_FILE_NAME.containsKey(lstFiles[i])){
                    // 送信対象ファイルでない場合は送信しない
                    continue;
                }
                if (m_bCanceled) {
                    // 送信処理を中断
                    sendCancel();
                    return false;
                }
                i_fl = new File(fromFile, lstFiles[i]); //コピー元
                if(i_fl.isDirectory()){
                    // ディレクトリの場合は送信しない
                    continue;
                }
                String strFileName = DEFINE.MAP_FILE_NAME.get(lstFiles[i]);
                if(strFileName == null){
                    strFileName = lstFiles[i];
                }
                StringBuilder strMsg = new StringBuilder(strFileName);
                if(isRetry){
                    strMsg.append("再");
                }
                strMsg.append("送信中");
                OtherUtil.sendBroadCastMessage(getContext(), strMsg.toString());
                sbf = new StringBuilder();
                sbf.append(lstFiles[i]);
                sbf.append(":");
                sbf.append(i_fl.length());
                sbf.append("\t");

                sendStart(sbf.toString());  // 送信要求

                //反応があるまで待機
                while(!m_isSendFile && !m_isSend){
                    MLog.DEBUG(getContext(), TAG, "ファイル受信開始フラグ待ち");
                    readData();
//                    if (m_bCanceled) {
//                        // 送信処理を中断
//                        sendCancel();
//                        return false;
//                    }

                    // 中断する or Error
                    if (m_isError) {
                        sendError();
                        return false;
                    }
                }
                m_isSendFile = false;
                if (m_isSend) { //もう1回
                    m_isSend = false;
                    i--;
                    isRetry = true;
                    continue;
                }

                MLog.INFO(getContext(), TAG, "Start:" + lstFiles[i]);

                if (!SendFile(i_fl, isRetry)) {
                    if (m_isSend) {
                        // リトライ要求
                        m_isSend = false;
                        i--;
                        isRetry = true;
                        continue;
                    }
                }
                isRetry = false;
            }
            sendAllFinish();

            while (true) {
                readData();
                if (m_bCanceled) {
                    // 送信処理を中断
                    sendCancel();
                    return false;
                }
                if (m_isSendFile) {
                    m_isSendFile = false;
                    break;
                }
                // 中断する or Error
                if (m_isError) {
                    sendError();
                    return false;
                }
            }

        } catch (Exception ex) {
            MLog.ERROR(getContext(), TAG, ex);
            return false;
        } finally {
            if (mOs != null) {
                mOs.close();
            }
        }
        return true;
    }

    /**
     * 対象のファイルを端末へ送信する
     *
     * @param fileSend [in]	{@link File}    対象ファイル名
     * @param isRetry  [in] booelan         リトライ送信フラグ
     * @return boolean    ファイル送信成否(true: 成功, false:失敗)
     */
    private boolean SendFile(File fileSend, boolean isRetry) {
        BufferedInputStream bis = null;
        boolean res;

        try {
            if(!fileSend.exists()){
                if(!fileSend.createNewFile()){
                    MLog.WARN(getContext(), TAG, "ファイルの作成に失敗");
                }
            }
            bis = new BufferedInputStream(new FileInputStream(fileSend));

            long lSize = fileSend.length();
            byte[] binary;

            m_isSend = false;

            int nPos;
            int nSeparate = 4096;
            if( this instanceof BTDataTransferLoader ){
                // Bluetooth通信の場合、1回に送信するデータサイズを倍にする
                nSeparate = 16384;
            }
            while (lSize > 0) {
                if (m_isError) {
                    break;
                }
                if (lSize > nSeparate) {
                    nPos = nSeparate;
                    // 複数回に分けて送信する場合は、リトライ扱いで送信
                    isRetry = true;
                } else {
                    nPos = (int) lSize;
                }

                binary = new byte[nPos];

                int len = bis.read(binary, 0, nPos);
                MLog.DEBUG(getContext(), TAG, "読込みデータサイズ:" + len);
                send(binary, isRetry);   // 出力ストリームにバイト列を書き込む
                lSize -= nPos;
                MLog.DEBUG(getContext(), TAG, "送信ファイルサイズ:" + nPos + ", 残りファイルサイズ:" + lSize);
                if (nPos < nSeparate) {
                    break;
                }
            }
            Thread.sleep(1000);

            // emit data and refresh memory
            // to separate between transfering data and [finish] signal
            mOs.flush();
            Thread.sleep(100);

            sendFinish();
            res = true;
            // OKがくるまで待機
            m_isSendFile = false;
            while (!m_isSendFile) {
                MLog.DEBUG(getContext(), TAG, "Finish通知受信待ち");
                readData();
                if (m_isError) {
                    sendError();
                    res = false;
                    break;
                }

                if (m_isSend) {
                    // リトライ要求受信
                    res = false;
                    break;
                }
            }
            m_isSendFile = false;
        } catch (Exception ex) {
            MLog.ERROR(getContext(), TAG, ex);
            return false;
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception ex) {
                    MLog.WARN(getContext(), TAG, ex);
                }
            }
        }
        return res;
    }

    /**
     * データ受信処理
     *
     * @return boolean データ受信結果フラグ true: 成功, false: 失敗
     * @throws IOException データ入出力関連のエラーがあった場合に発生
     */
    private boolean recieveData() throws IOException {
        boolean res = false;
        // 受信開始準備完了通知
        sendStartReceive();
        m_bCanceled = false;
        m_isStart = false;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!m_isStart) {
                    m_bCanceled = true;
                }
            }
        }, TimeUnit.SECONDS.toMillis(30));
        while (!m_bFinished) {
            if(m_bCanceled){
                sendCancel();
                return false;
            }
            res = readData();
        }
        timer.cancel();
        return res;
    }

    /**
     * データ読み込み処理.
     *
     * @return  boolean 読み込み成否
     * @throws IOException  読み込み時にエラーがあった場合に発生
     */
    protected boolean readData() throws IOException {
        boolean res = false;
        int nLen = mIs.available();
        if (nLen == 0) {
            // データが届いていない
            return false;
        }
        byte[] data = new byte[nLen];
        if (mIs.read(data) != -1) {
            // データを解析
            res = analyze(data);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
            MLog.WARN(getContext(), TAG, ie);
        }
        return res;
    }

    @Override
    public void deliverResult(EnumStatus data) {
        if (isReset()) {
            return;
        }

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public void reset() {
        super.reset();
        onStopLoading();
    }

    /**
     * 受信したデータの解析を行う<br />
     * 受信コマンドを文字列に変換し、コマンド解析を行う<br />
     * <ul type="square">
     * <li>文字列の先頭が「Start:」：受信ファイル情報</li>
     * <li>文字列の後方が「Cancel」：データ受信キャンセル</li>
     * <li>文字列の先頭が「AllFinish」：全データ受信完了</li>
     * <li>文字列の後方が「Finish」：データ受信完了</li>
     * <li>上記以外：現在のテンプレートファイルに追記</li>
     * </ul>
     * <h1>コマンド詳細</h1>
     * <h2>Start</h2>
     * Start:コマンドの後ろにファイル名とファイルサイズが続く<br />
     * 例)<br />
     * Start:ファイル名:ファイルサイズ\t<br />
     * このコマンドを受信後、テンプレート用のファイルを生成
     * テンプレートファイル作成後、後方側に「OK」コマンドを発行
     * <h2>Cancel</h2>
     * このコマンドを受信した場合は受信処理を中断
     * <h2>AllFinish</h2>
     * 全てのファイルの受信が完了<br />
     * 受信ファイルをデータ領域にコピー
     * <h2>Finish</h2>
     * 現在受信中のファイルの受信が完了<br />
     * Startコマンドのファイルサイズと実際のファイルサイズの確認を実施し、<br />
     * 同じであれば「OK」コマンドし、テンプレートファイルの名称を実際のファイル名にリネームする<br />
     * サイズに違いがある場合は「Retry」コマンドを発行<br />
     *
     * @param recieveData [in] byte[] 受信データ
     * @return boolean true: 処理が正常に完了, false: キャンセルコマンド受信、テンプレートファイル作成失敗
     * @throws IOException データ入出力でエラーがあった場合に発生
     */
    boolean analyze(byte[] recieveData) throws IOException {
        String strCommand = new String(recieveData, 0, recieveData.length, StandardCharsets.US_ASCII);
        MLog.DEBUG(getContext(), TAG, strCommand);
        if(strCommand.startsWith("StartReceive")){
            return true;
        }
        if (strCommand.startsWith("Start:")) {
            // ファイル受信開始
            m_isStart = true;
            MLog.INFO(getContext(), TAG, "受信コマンド：" + strCommand);
            return createTemplateFile(strCommand);
        }
        if (strCommand.endsWith("Cancel")) {
            // キャンセル
            MLog.INFO(getContext(), TAG, "受信コマンド：" + "Cancel");
            OtherUtil.sendBroadCastMessage(getContext(), "転送処理を中断");
            m_bFinished = true;
            return false;
        }
        if (strCommand.startsWith("AllFinish")) {
            // 全てのファイルの転送が完了
            OtherUtil.sendBroadCastMessage(getContext(), "全てのファイルの受信完了");
            sendFinish();

            // データディレクトリからSDカードへコピー
            if(!m_isUpdate) {
                OtherUtil.sendBroadCastMessage(getContext(), "SDカードへデータをコピー中");
                try {
                    OtherUtil.copyDataAll(getContext(), DEFINE.getDataPath(getContext()), DEFINE.SD_DATA_DIRECTORY);
                } catch (Exception e) {
                    MLog.ERROR(getContext(), TAG, e);
                }
            }
            else {
                try {
                    OtherUtil.copyData(getContext(), Objects.requireNonNull(getContext().getExternalCacheDir()).getPath(), DEFINE.SD_APK_DIRECTORY);
                }
                catch (Exception e){
                    MLog.ERROR(getContext(), TAG, e);
                }
            }
            m_bFinished = true;

            return true;
        }
        int nIndexOf = strCommand.indexOf("Finish");
        if (strCommand.endsWith("Finish")) {
            if (nIndexOf != 0) {
                recieveData = Arrays.copyOf(recieveData, nIndexOf);
                writeData(recieveData);
            }

            // refresh memory
            int tmp_Len;
            while((tmp_Len = mIs.available()) > 0){
                // Finishをもらってから、残ってるデータは要らない
                // また、前のデータを覚えて、次のデータがくると、間違ったSizeを覚えて、データを取れないことです
                byte[] tmp_data = new byte[tmp_Len];
                if(mIs.read(tmp_data) > 0) {
                    String tmp_command = new String(tmp_data, 0, tmp_data.length, StandardCharsets.US_ASCII);
                    MLog.INFO(getContext(), TAG, "data remain:" + tmp_command);
                }
            }

            // ファイルの受信が完了
            MLog.INFO(getContext(), TAG, "受信コマンド：" + "Finish");
            if (mTemplateFile.length() == mTemplateFileSize) {
                // 受信したデータのサイズと、あらかじめ通知されたデータサイズが同じならOKコマンド送信
                String wkFileName = DEFINE.MAP_FILE_NAME.get(mTemplateFileName);
                if (mTemplateFileName.contains(".apk")) {
                    wkFileName = "更新用apkファイル";
                }
                else {
                    if (wkFileName == null) {
                        wkFileName = mTemplateFileName;
                    }
                }

                OtherUtil.sendBroadCastMessage(getContext(), wkFileName + "受信完了");
                if(m_isUpdate){
                    // アプリ更新の場合・・・
                    if(mTemplateFile.renameTo(new File(mTemplateFile.getParentFile(), "KENSIN_UPDATE.apk"))){
                        sendOk();
                    }
                    else {
                        sendRetry();
                    }
                }
                else {
                    MLog.INFO(getContext(), TAG, "データ領域へリネームコピー: temp.D -> " + mTemplateFileName);
                    File file = new File(DEFINE.getDataPath(getContext()) + mTemplateFileName);
                    try {
                        if (file.exists() && !file.delete()) {
                            throw new IOException("既存ファイルの削除に失敗しました。ファイル名:" + file.getName());
                        }
                        OtherUtil.fileCopy(mTemplateFile, file);
                        sendOk();
                    } catch (IOException ioe) {
                        sendRetry();
                    }
                }
            } else {
                // 取得したファイルサイズと、Startコマンドで取得したファイルサイズが違う場合はリトライ要求
                MLog.INFO(getContext(), TAG, "テンプレートファイルサイズ: " + mTemplateFile.length() + ", 取得ファイルサイズ: " + mTemplateFileSize);
                sendRetry();
            }
            return true;
        }
        if(strCommand.startsWith("OK")){
            m_isSendFile = true;
            m_isSend = false;
            return true;
        }
        if(strCommand.startsWith("BackupComp")){
            m_isBackupComp = true;
            return true;
        }
        if(strCommand.startsWith("Retry")){
            // リトライ
            MLog.INFO(getContext(), TAG, "リトライコマンド受信");
            m_isSend = true;
            return true;
        }
        // データ追記
        writeData(recieveData);

        return true;
    }

    /**
     * テンプレート用ファイルを作成<br />
     * テンプレート用ファイル作成時に前回受信したテンプレートファイルのファイルサイズと、Start:コマンド受信時のファイルサイズを<br />
     * 比較して同じであれば受信成功、同じでなければ受信失敗としてリトライコマンドを発行する。
     */
    private boolean createTemplateFile(String command) throws IOException {
        String[] wkComArray = command.split(":");
        int wkIdx = wkComArray[2].indexOf('\t');
        wkComArray[2] = wkComArray[2].substring(0, wkIdx);

        if (wkComArray.length != 3) {
            // コマンド誤り
            // リトライ要求
            MLog.ERROR(getContext(), TAG, "コマンド解析に失敗");
            sendRetry();
            return false;
        }

        mTemplateFile = new File(getContext().getExternalCacheDir(), "temp.D");
        if (mTemplateFile.exists()) {
            if (!mTemplateFile.delete()) {
                // ファイルの削除に失敗
                throw new IOException("テンプレートファイルの削除に失敗");
            }
        }
        if (!mTemplateFile.createNewFile()) {
            // ファイルの作成に失敗
            throw new IOException("テンプレートファイルの作成に失敗");
        }

        mTemplateFileName = wkComArray[1];
        mTemplateFileSize = Integer.parseInt(wkComArray[2]);

        String strFileName;
        if(mTemplateFileName.contains(".apk")){
            strFileName = "更新用apkファイル";
            m_isUpdate = true;
        }
        else {
            strFileName = DEFINE.MAP_FILE_NAME.get(mTemplateFileName);
            if(strFileName == null){
                strFileName = mTemplateFileName;
            }
        }

        OtherUtil.sendBroadCastMessage(getContext(), strFileName + "受信開始");
        sendOk();
        return true;
    }

    /**
     * テンプレートデータファイルに受信したデータを格納する
     *
     * @param writeByte [in] byte[] 書き込むデータ
     */
    private void writeData(byte[] writeByte) throws IOException {
        RandomAccessFile wkRaf = null;
        try {
            int wkSize = writeByte.length;
            MLog.INFO(getContext(), TAG, "書込みデータサイズ: " + writeByte.length);
            wkRaf = new RandomAccessFile(mTemplateFile, "rw");
            long wkIdx = wkRaf.length();
            MLog.INFO(getContext(), TAG, " RandomAccessFile.length(): " + wkIdx);

            if (wkIdx != 0) {
                wkRaf.seek(wkRaf.length());
            }
            wkRaf.write(writeByte, 0, wkSize);
        } catch (Exception e) {
            // データ取り込みでエラー発生
            MLog.WARN(getContext(), TAG, "データ取込みでエラーが発生しました。:" + e.getMessage());
            sendRetry();
        } finally {
            if (wkRaf != null) {
                try {
                    wkRaf.close();
                } catch (IOException ioe) {
                    MLog.WARN(getContext(), TAG, ioe);
                }
            }
        }
    }

    /**
     * データ受信通知の送信
     *
     * @throws IOException 送信に失敗した場合に発生
     */
    protected void sendStartReceive() throws IOException {
        MLog.DEBUG(getContext(), TAG, "受信準備完了通知");
        mOs.write("StartReceive".getBytes(StandardCharsets.US_ASCII));
        mOs.flush();
    }

    /**
     * データ送信通知の送信.
     *
     * @throws IOException  送信に失敗した場合に発生
     */
    protected void sendStartSend() throws IOException {
        MLog.DEBUG(getContext(), TAG, "送信準備完了通知");
        mOs.write("StartSend".getBytes(StandardCharsets.US_ASCII));
        mOs.flush();
    }

    /**
     * 送信開始を通知する
     *
     * @param file [in]	String	ファイル名
     */
    protected void sendStart(String file) throws IOException {
        byte[] startb = ("Start:" + file).getBytes(StandardCharsets.US_ASCII);
        mOs.write(startb, 0, startb.length);
        mOs.flush();
    }

    /**
     * エラーコマンドを送信
     *
     * @throws IOException 送信に失敗した場合に発生
     */
    protected void sendError() throws IOException {
        MLog.DEBUG(getContext(), TAG, "エラー通知");
        mOs.write("Error".getBytes(StandardCharsets.US_ASCII));
        mOs.flush();
    }

    /**
     * OKコマンドを送信
     *
     * @throws IOException 送信に失敗した場合に発生
     */
    protected void sendOk() throws IOException {
        MLog.DEBUG(getContext(), TAG, "肯定通知");
        mOs.write("OK".getBytes(StandardCharsets.US_ASCII));
        mOs.flush();
    }

    /**
     * リトライコマンドを送信
     *
     * @throws IOException 送信に失敗した場合に発生
     */
    protected void sendRetry() throws IOException {
        MLog.DEBUG(getContext(), TAG, "リトライ通知");
        mOs.write("Retry".getBytes(StandardCharsets.US_ASCII));
        mOs.flush();
    }

    /**
     * 全ファイル送信完了を通知
     */
    protected void sendAllFinish() throws IOException {
        byte[] cmd = "AllFinish".getBytes(StandardCharsets.US_ASCII);
        mOs.write(cmd, 0, cmd.length);
        mOs.flush();
    }

    /**
     * 終了コマンドを送信
     *
     * @throws IOException 送信に失敗した場合に発生
     */
    protected void sendFinish() throws IOException {
        MLog.DEBUG(getContext(), TAG, "終了通知");
        mOs.write("Finish".getBytes(StandardCharsets.US_ASCII));
        mOs.flush();
    }

    /**
     * キャンセルを通知
     */
    protected void sendCancel() throws IOException {
        MLog.DEBUG(getContext(), TAG, "キャンセル通知");
        byte[] cmd = "Cancel".getBytes(StandardCharsets.US_ASCII);
        mOs.write(cmd, 0, cmd.length);
    }
}
