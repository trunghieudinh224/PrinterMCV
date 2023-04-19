package jp.co.MarutouCompack.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.co.MarutouCompack.Printer.R;
import pl.droidsonroids.gif.GifImageView;

public class GifDialog extends Dialog {

    private static final String TAG = GifDialog.class.getSimpleName();

    /** View holder */
    private static ViewHolder viewHolder;
    /** activty */
    private Activity mActivity;

    private int imageId = 0;

    private String message;

    public GifDialog(Activity activity, int imageId, String mess) {
        super(activity);
        this.mActivity = activity;
        this.imageId = imageId;
        this.message = mess;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_gif);
        initViewHolder();
        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        viewHolder.image.setImageResource(imageId);
        viewHolder.message.setText(message);
    }


    @Override
    public void onBackPressed() {

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
    private class ViewHolder {
        private LinearLayout background;
        private GifImageView image;
        private TextView message;

        public ViewHolder(View view) {
            background = view.findViewById(R.id.background);
            image = view.findViewById(R.id.image);
            message = view.findViewById(R.id.message);
        }
    }
}
