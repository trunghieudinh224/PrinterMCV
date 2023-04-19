package jp.co.MarutouCompack.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Dialog extends android.app.Dialog {

    public boolean isClosed = false;


    public Dialog(@NonNull Context context) {
        super(context);
    }


    public Dialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    protected Dialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    public boolean isClosed() {
        return isClosed;
    }


    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
