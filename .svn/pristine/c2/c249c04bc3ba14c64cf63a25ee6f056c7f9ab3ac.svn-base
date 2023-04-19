
package jp.co.MarutouCompack.Printer;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Objects;
import java.util.Set;

import jp.co.MarutouCompack.baseClass.DEFINE;
import jp.co.MarutouCompack.baseClass.OtherUtil;
import jp.co.MarutouCompack.baseClass.PrintBase;
import jp.co.MarutouCompack.commonClass.PrinterDat.WebData;
import jp.co.MarutouCompack.marutoucommonlibrary.utils.MLog;


/**
 * メニューアクティビティ
 */
public class MainMenuActivity extends BasePrintActivity {

    /** クラス識別用タグ */
    private static final String TAG = MainMenuActivity.class.getSimpleName();

    /** View holder */
    public static ViewHolder viewHolder;
    /** リクエストコード：バックアップ */
    public static final int REQEST_CODE_BACKUP_DIR = 0x11;
    /** リクエストコード：書き込み権限 */
    private static final int REQUEST_WRITE_STORAGE_PERMISSION = 1;

    /**
     * コンストラクタ
     */
    public MainMenuActivity() {
        super(R.layout.mainmenu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewHolder();;
        SharedPreferences pref = getSharedPreferences("Printer", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Intent intent = getIntent();
        Uri data = intent.getData();
        String json ="";
        if (data != null) {
            json = data.getQuery().replace("param=","");
            mWebData = new Gson().fromJson(json, WebData.class);
            type = mWebData.getType();
            editor.putInt("PrintMode", mWebData.getPrintMode() == 0 ? PrintBase.PRINT_MODE_IMAGE : PrintBase.PRINT_MODE_CHAR);
            editor.apply();
        }
        editor.putBoolean("IsPrinting", false);
        editor.apply();


        //check permission before choosing URI
        checkExternalStrageAccessPermission();
        viewHolder.editButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // ペアリング済みデバイス一覧を取得する
                final SparseArray<String[]> lstBluetoothDevices = getBluetoothDevices();

                // デバイス名：デバイスMACアドレスの文字列配列を作成
                String[] lstMacaddr = new String[lstBluetoothDevices.size()];
                String[] lstName = new String[lstBluetoothDevices.size()];
                String[] lstAdd = new String[lstBluetoothDevices.size()];
                for (int i = 0; i < lstMacaddr.length; i++) {
                    String[] lstDeviceInfo = lstBluetoothDevices.valueAt(i);
                    lstMacaddr[i] = lstDeviceInfo[0] + "\r\n" + lstDeviceInfo[1];
                    lstName[i] = lstDeviceInfo[0];
                    lstAdd[i] = lstDeviceInfo[1];
                }

                // リストダイアログ表示
                new AlertDialog.Builder(MainMenuActivity.this).setTitle("Bluetoothデバイス一覧").setItems(lstMacaddr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 選択した項目のMACアドレスを設定する
                        if (lstName.length > 0) {
                            viewHolder.printerNameTxt.setText(lstName[which]);

                            SharedPreferences pref = getSharedPreferences("Printer", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("PrinterName", lstName[which]);
                            editor.putString("PrinterMAC", lstAdd[which]);
                            editor.apply();
                            CheckPrint();
                        }
                    }
                }).show();
            }
        });

        switch (type) {
            case "kensin":
            case "nyukin":
            case "uriage":
            case "toyu":
                CheckPrint();
                break;
            case "shukei":
            case "kensin_nippou":
            case "shukin_nippou":
            case "uriage_nippou":
                startShukei();
                break;
            default:
                SharedPreferences sharedPreferences = getSharedPreferences("Printer", Context.MODE_PRIVATE);
                viewHolder.printerNameTxt.setText(sharedPreferences.getString("PrinterName", ""));

                if (!OtherUtil.nullToString(sharedPreferences.getString("PrinterMAC", "")).equals("")) {
                    viewHolder.printerNameTxt.setText(sharedPreferences.getString("PrinterName", ""));
                    CheckPrint();
                }
                break;
        }
    }


    /**
     * ペアリング済みデバイス一覧の取得.
     *
     * @return {@code SparseArray<String[]>}    デバイス名をキーとして、MACアドレスを値としたSparseArrayを返す
     */
    public SparseArray<String[]> getBluetoothDevices() {
        SparseArray<String[]> lstBluetoothDevices = new SparseArray<>();

        // BluetoothAdapterのインスタンスを取得する
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // ペアリング済みデバイス一覧を取得する
        Set<BluetoothDevice> setPairedDevices = bluetoothAdapter.getBondedDevices();
        int nIdx = 0;
        for (BluetoothDevice wkDevice : setPairedDevices) {
            lstBluetoothDevices.put(nIdx++, new String[] {
                    wkDevice.getName(), wkDevice.getAddress()
            });
        }
        return lstBluetoothDevices;
    }



    /**
     * 権限付与後のディレクトリ設定
     */
    public void chooseFolderAfterGetPermission(){
        // データ保存先URIの設定
        SharedPreferences prefs = getSharedPreferences("Printer", MODE_PRIVATE);
        String strUri = prefs.getString(DEFINE.PREF_URI_BACKUP_DATA_SAVE, "");
        if(Objects.requireNonNull(strUri).equals("")){
            Intent intent;
            // データ保存先URIの設定
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, REQEST_CODE_BACKUP_DIR);
        }
    }


    /**
     * 外部ストレージのアクセス権限確認.
     */
    void checkExternalStrageAccessPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            chooseFolderAfterGetPermission();
            return;
        }
        // REQUEST_WRITE_PERMISSIONは、8bit以下の整数定数
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 以前に許諾して、今後表示しないとしていた場合は、ここにはこない
            // startActivityForResult()みたいな感じで許諾を要求
            String[] permissions = new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            requestPermissions(permissions, REQUEST_WRITE_STORAGE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_WRITE_STORAGE_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                MLog.INFO(this, TAG, "ストレージへのアクセスを許可");

                // choose backup folder
                chooseFolderAfterGetPermission();
            }
            else {
                Toast.makeText(this, "検針くんαを利用するためにはストレージへのアクセス許可が必要です。", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onPause() {
        super.onPause();
        checkApp = !checkApp;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("Printer", Context.MODE_PRIVATE);
            viewHolder.printerNameTxt.setText(sharedPreferences.getString("PrinterName", ""));
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            viewHolder.versionTxt.setText("Ver " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void startShukei() {
        startPrintOnline(true);
    }


    /**
     * Get ViewHolder Function
     */
    private void initViewHolder() {
        View view = getWindow().getDecorView();
        viewHolder = (ViewHolder) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
    }


    /**
     * Init view component
     */
    public static class ViewHolder {
        public TextView printerNameTxt;
        public TextView statusTxt;
        public TextView versionTxt;
        public ImageView editButton;

        public ViewHolder(View view) {
            printerNameTxt = view.findViewById(R.id.printerName);
            statusTxt = view.findViewById(R.id.status);
            versionTxt = view.findViewById(R.id.version);
            editButton = view.findViewById(R.id.editButton);
        }
    }
}
