package hos.utilx.dialog;

import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import hos.thread.hander.MH;
import hos.util.dialog.WindowDialogImpl;
import hos.util.dialog.WindowDialogInterface;

/**
 * <p>Title: ViewDialog </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/4/29 20:54
 */
public class WindowDialog extends WindowDialogImpl<WindowDialog> implements WindowDialogInterface<WindowDialog> {
    private AppCompatActivity activity;
    protected androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder;
    protected androidx.appcompat.app.AlertDialog dialogReal;

    public WindowDialog(@NonNull AppCompatActivity activity) {
        this.activity = activity;
        activity.getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    // 销毁的时候执行
                    dismiss();
                    source.getLifecycle().removeObserver(this);
                    WindowDialog.this.activity = null;
                    WindowDialog.this.alertDialogBuilder = null;
                    WindowDialog.this.dialogReal = null;
                }
            }
        });
    }

    @Nullable
    public androidx.appcompat.app.AlertDialog.Builder getDialogBuilder() {
        if (activity == null) {
            return null;
        }
        if (style == 0) {
            return this.alertDialogBuilder == null ?
                    (this.alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(activity)) : this.alertDialogBuilder;
        }
        return this.alertDialogBuilder == null ?
                (this.alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(activity, style)) : this.alertDialogBuilder;
    }

    @Override
    public boolean isShowing() {
        if (dialogReal != null) {
            return dialogReal.isShowing();
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
                if (dialogReal != null) {
                    dialogReal.show();
                    return;
                }
                create();
                if (dialogReal != null) {
                    dialogReal.show();
                }
            }
        });
    }

    @Override
    public void dismiss() {
        if (!isShowing()) {
            return;
        }
        if (dialogReal != null) {
            dialogReal.dismiss();
        }
    }

    @Override
    public WindowDialog create() {
        if (dialogReal != null) {
            return this;
        }
        androidx.appcompat.app.AlertDialog.Builder builder = getDialogBuilder();
        if (builder != null) {
            builder.setCancelable(cancelable);
            dialogReal = builder.create();
            if (view != null && params != null) {
                dialogReal.setContentView(view, params);
            } else if (view != null) {
                dialogReal.setContentView(view);
            } else if (layoutId != 0) {
                dialogReal.setContentView(layoutId);
            }
            dialogReal.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        return this;
    }
}
