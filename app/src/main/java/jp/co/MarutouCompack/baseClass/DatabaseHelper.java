
package jp.co.MarutouCompack.baseClass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * <p>
 * タイトル: MGS
 * </p>
 * <p>
 * 説明:
 * </p>
 * <p>
 * 著作権: Copyright (c) 2011 Marutou Compack Co.,Ltd
 * </p>
 * <p>
 * 会社名: Marutou Compack Co.,Ltd
 * </p>
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /** DB用ファイル名 */
    private static final String DBNAME = "Printer.db";

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, (Integer.parseInt(Build.VERSION.RELEASE.substring(0, 1)) >= 4 ? 2 : 1));
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // SQLiteにテーブルを追加する
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE REGISTORY ");
        sql.append("(_id INTEGER PRIMARY KEY, NAME TEXT, VALUE TEXT)");

        arg0.execSQL(sql.toString());

        arg0.execSQL("INSERT INTO REGISTORY VALUES(1, 'FTPADDR', '255.255.255.255');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(2, 'FTPUSER', '123456');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(3, 'FTPPASS', '123456');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(4, 'CARDPATH', '/mnt/sdcard/MarutouCompack/');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(5, 'DATAPATH', '/data/data/jp.co.MarutouCompack.Printer/files/');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(6, 'COPYPATH', '/data/data/jp.co.MarutouCompack.Printer/files/BackUp/');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(7, 'SHOWKENSUMI', '0');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(8, 'PDANO', '0');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(9, 'NOWKENSINCNT', '0');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(10, 'SHOWINDEX', '0');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(11, 'PDABUFFER', '0');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(12, 'PRINTADDR', '255.255.255.255');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(13, 'DATESETMODE', '0');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(14, 'TDATAPATH', 'tdatapath');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(15, 'TCOPYPATH', 'tbackpath');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(16, 'TCARDPATH', 'tcardpath');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(17, 'TSHOWINDEX', '0');");
        arg0.execSQL("INSERT INTO REGISTORY VALUES(18, 'BTADDRESS', '00:00:00:00:00:00');");

        sql = new StringBuilder();
        sql.append("CREATE TABLE INPUTHIS ");
        sql.append("(CUSCODE TEXT PRIMARY KEY, RECEIPT)");

        arg0.execSQL(sql.toString());

        arg0.execSQL("INSERT INTO INPUTHIS VALUES( '0', 10000 );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }

}
