package com.chad.ctsdemo.view;

import com.chad.ctsdemo.DownloadVideoActivity;
import com.chad.ctsdemo.R;
import com.chad.ctsdemo.Utils.SharedPrefsStore;
import com.chad.ctsdemo.bean.VideoInfo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class AddPopWindow extends PopupWindow {
    private View contentView;
    private DownloadVideoActivity activity;
    private DisplayMetrics dm = new DisplayMetrics();
    private EditText etTitle;
    private EditText etUrl;
    private TextView tvPaste;

    public AddPopWindow(final DownloadVideoActivity context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.pop_window_add, null);
        activity = context;
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.WRAP_CONTENT);
        // // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(android.R.style.Animation_Dialog);
        etTitle = (EditText) contentView.findViewById(R.id.et_pop_add_title);
        etUrl = (EditText) contentView.findViewById(R.id.et_pop_add_url);
        tvPaste = (TextView) contentView.findViewById(R.id.tv_remind_clip);
        tryShowPaste();
        contentView.findViewById(R.id.tv_pop_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dismiss();
            }
        });
        contentView.findViewById(R.id.btn_confirm).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // save the config
                if (etTitle.getText().toString().equals("") || etUrl.getText().toString().equals("")) {
                    Toast.makeText(contentView.getContext(), "两项输入项均不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    VideoInfo info = new VideoInfo(etTitle.getText().toString(), etUrl.getText().toString());
                    SharedPrefsStore.addToMainVideo(etTitle.getContext(), info);
                    activity.refreshData();
                    dismiss();
                }
            }
        });
    }

    /**
     * 因为PopWindow至今不支持EditText的选择、粘贴等操作，因此在此自制粘贴选项
     * 如果不想使用该方法，建议使用半透明的Activity替换PopWindow。
     */
    private void tryShowPaste() {
        final ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        // if (!(clipboard.hasPrimaryClip())) {
        // tvPaste.setVisibility(View.GONE);
        // }else {
        // }
        tvPaste.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (tvPaste.getText().equals("粘贴")) {
                    ClipData clipData = clipboard.getPrimaryClip();
                    if (clipData != null && clipData.getItemCount() > 0) {
                        ClipData.Item item = clipData.getItemAt(0);
                        String pasteData = item.getText() == null ? null : item.getText().toString();
                        Log.d("AddPopWindow", "item.getText() is " + pasteData);
                        if (pasteData == null) {
                            pasteData = item.getUri() == null ? null : item.getUri().toString();
                            Log.d("AddPopWindow", "item.getUri() is " + pasteData);
                        }
                        if (pasteData != null && pasteData.length() > 0) {
                            etUrl.setText(pasteData);
                            tvPaste.setText("清空");
                            return;
                        }
                    }
                    Toast.makeText(activity, "剪贴板为空", Toast.LENGTH_SHORT).show();
                } else {
                    // clean
                    etUrl.setText("");
                    tvPaste.setText("粘贴");
                }
                
            }
        });

    }

    @Override
    public void dismiss() {
        super.dismiss();
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 1f;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 显示popupWindow
     * 
     */
    public void showPopupWindow() {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(activity.findViewById(R.id.rl_main), Gravity.CENTER, 0, 0);
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 0.3f;
            activity.getWindow().setAttributes(lp);
        } else {
            this.dismiss();
        }
    }
}
