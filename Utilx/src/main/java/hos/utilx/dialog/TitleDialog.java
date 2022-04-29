package hos.utilx.dialog;

import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import hos.thread.hander.MH;
import hos.util.dialog.TitleDialogImpl;
import hos.util.dialog.TitleDialogInterface;
import hos.util.utils.WindowUtils;

/**
 * <p>Title: AppCompatAlertDialogImpl </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/4/23 22:15
 */
public class TitleDialog extends TitleDialogImpl<TitleDialog> implements TitleDialogInterface<TitleDialog> {
    private AppCompatActivity activity;
    protected androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder;
    protected androidx.appcompat.app.AlertDialog dialogReal;

    public TitleDialog(@NonNull AppCompatActivity activity) {
        this.activity = activity;
        activity.getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    // 销毁的时候执行
                    dismiss();
                    source.getLifecycle().removeObserver(this);
                    TitleDialog.this.activity = null;
                    TitleDialog.this.alertDialogBuilder = null;
                    TitleDialog.this.dialogReal = null;
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
    public TitleDialog create() {
        if (dialogReal != null) {
            return this;
        }
        androidx.appcompat.app.AlertDialog.Builder builder = getDialogBuilder();
        if (builder != null) {
            if (title != null) {
                builder.setTitle(title);
            }
            if (content != null) {
                builder.setMessage(content);
            }
            if (cancelListener != null) {
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        cancelListener.onTarget(TitleDialog.this);
                    }
                });
            }
            if (dismissListener != null) {
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        dismissListener.onTarget(TitleDialog.this);
                    }
                });
            }
            if (confirm != null) {
                if (confirmFunction == null) {
                    builder.setNegativeButton(confirm, null);
                } else {
                    builder.setNegativeButton(confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            confirmFunction.onTarget(TitleDialog.this);
                        }
                    });
                }
            }
            if (cancel != null) {
                if (cancelFunction == null) {
                    builder.setPositiveButton(cancel, null);
                } else {
                    builder.setPositiveButton(cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cancelFunction.onTarget(TitleDialog.this);
                        }
                    });
                }
            }
            if (left != null) {
                if (cancelFunction == null) {
                    builder.setNeutralButton(left, null);
                } else {
                    builder.setNeutralButton(left, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            leftFunction.onTarget(TitleDialog.this);
                        }
                    });
                }
            }
            builder.setCancelable(cancelable);
            dialogReal = builder.create();
            if (applyBottom) {
                WindowUtils.applyBottom(dialogReal.getWindow());
            }
        }
        return this;
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
        if (dialogReal != null) {
            dialogReal.dismiss();
        }
    }

    @Override
    public TitleDialog setContent(CharSequence content) {
        if (dialogReal != null) {
            dialogReal.setMessage(content);
        }
        return super.setContent(content);
    }

}
