package hos.utilx.dialog;

import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import hos.thread.hander.MH;
import hos.util.dialog.ViewDialogImpl;
import hos.util.dialog.ViewDialogInterface;
import hos.util.utils.WindowUtils;

/**
 * <p>Title: ViewDialog </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/4/29 20:54
 */
public class ViewDialog extends ViewDialogImpl<ViewDialog> implements ViewDialogInterface<ViewDialog> {
    private AppCompatActivity activity;
    protected androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder;
    protected androidx.appcompat.app.AlertDialog dialogReal;

    public ViewDialog(@NonNull AppCompatActivity activity) {
        this.activity = activity;
        activity.getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    // 销毁的时候执行
                    dismiss();
                    source.getLifecycle().removeObserver(this);
                    ViewDialog.this.activity = null;
                    ViewDialog.this.alertDialogBuilder = null;
                    ViewDialog.this.dialogReal = null;
                    clear();
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
    @Deprecated
    public ViewDialog setContent(CharSequence content) {
        return super.setContent(content);
    }

    @Override
    @Deprecated
    public ViewDialog setContent(int contentId) {
        return this;
    }

    @Override
    public ViewDialog create() {
        if (dialogReal != null) {
            return this;
        }
        androidx.appcompat.app.AlertDialog.Builder builder = getDialogBuilder();
        if (builder != null) {
            if (title != null) {
                builder.setTitle(title);
            }
            if (view != null) {
                builder.setView(view);
            }
            if (layoutId != 0) {
                builder.setView(layoutId);
            }
            builder.setCancelable(cancelable);
            dialogReal = builder.create();
            if (applyBottom) {
                WindowUtils.applyBottom(dialogReal.getWindow());
            }
        }
        return this;
    }
}
