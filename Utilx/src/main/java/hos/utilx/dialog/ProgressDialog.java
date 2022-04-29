package hos.utilx.dialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import hos.thread.hander.MH;
import hos.util.dialog.DialogImpl;
import hos.util.utils.WindowUtils;

/**
 * <p>Title: ProgressDialogDefalult </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/4/14 22:59
 */
public class ProgressDialog extends DialogImpl<ProgressDialog> {
    protected AppCompatActivity activity;
    @Nullable
    protected android.app.ProgressDialog progressNumberDialog;

    public ProgressDialog(@NonNull AppCompatActivity activity) {
        this.activity = activity;
        activity.getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    // 销毁的时候执行
                    dismiss();
                    source.getLifecycle().removeObserver(this);
                    ProgressDialog.this.activity = null;
                    ProgressDialog.this.progressNumberDialog = null;
                }
            }
        });
    }

    @Nullable
    protected android.app.ProgressDialog getProgressDialog() {
        if (activity == null) {
            return null;
        }
        if (style == 0) {
            return this.progressNumberDialog == null ? (this.progressNumberDialog = new android.app.ProgressDialog(activity)) : this.progressNumberDialog;
        }
        return this.progressNumberDialog == null ? (this.progressNumberDialog = new android.app.ProgressDialog(activity, style)) : this.progressNumberDialog;
    }

    @Override
    @Deprecated
    public ProgressDialog create() {
        return this;
    }

    @Override
    public boolean isShowing() {
        android.app.ProgressDialog progressDialog = getProgressDialog();
        if (progressDialog != null) {
            return progressDialog.isShowing();
        }
        return false;
    }

    @Override
    public void show() {
        MH.postOnMain(new Runnable() {
            @Override
            public void run() {
                if (isShowing()) {
                    return;
                }
                android.app.ProgressDialog progressDialog = getProgressDialog();
                if (progressDialog != null) {
                    progressDialog.setCancelable(cancelable);
                    progressDialog.setTitle(title);
                    progressDialog.setMessage(content);
                    if (applyBottom) {
                        WindowUtils.applyBottom(progressDialog.getWindow());
                    }
                    progressDialog.show();
                }
            }
        });
    }

    public void showTip() {
        showTip(1000);
    }

    public void showTip(final long delayMillis) {
        show();
        MH.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, delayMillis);
    }

    @Override
    public void dismiss() {
        if (!isShowing()) {
            return;
        }
        android.app.ProgressDialog progressDialog = getProgressDialog();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void setProgress(int value) {
        MH.postOnMain(new Runnable() {
            @Override
            public void run() {
                android.app.ProgressDialog progressDialog = getProgressDialog();
                if (progressDialog != null) {
                    progressDialog.setProgress(value);
                }
            }
        });
    }


    @Override
    public ProgressDialog setContent(CharSequence content) {
        android.app.ProgressDialog progressDialog = getProgressDialog();
        if (progressDialog != null) {
            progressDialog.setMessage(content);
        }
        return super.setContent(content);
    }
}
