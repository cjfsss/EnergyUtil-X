package hos.utilx.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import hos.thread.hander.MH;
import hos.util.dialog.TitleDialogInterface;
import hos.util.listener.OnTargetListener;
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
public class TitleDialogFragment extends DialogFragment implements TitleDialogInterface<TitleDialogFragment> {
    private androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder;
    private androidx.appcompat.app.AlertDialog dialogReal;
    protected CharSequence title;
    protected CharSequence content;
    protected int style;
    protected boolean cancelable = true;
    protected boolean applyBottom = false;

    protected OnTargetListener<TitleDialogFragment> cancelListener;
    protected OnTargetListener<TitleDialogFragment> dismissListener;
    protected CharSequence confirm;
    protected CharSequence cancel;
    protected CharSequence left;
    protected OnTargetListener<TitleDialogFragment> confirmFunction;
    protected OnTargetListener<TitleDialogFragment> cancelFunction;
    protected OnTargetListener<TitleDialogFragment> leftFunction;
    private boolean mIsShowing;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (dialogReal != null) {
            return dialogReal;
        }
        androidx.appcompat.app.AlertDialog.Builder builder = getDialogBuilder();
        if (builder == null) {
            throw new RuntimeException("please new TitleDialogFragment()");
        }
        create();
        if (dialogReal == null) {
            dialogReal = builder.create();
        }
        return builder.create();
    }

    @Override
    public void onDestroy() {
        // 销毁的时候执行
        alertDialogBuilder = null;
        dialogReal = null;
        cancelListener = null;
        dismissListener = null;
        confirmFunction = null;
        cancelFunction = null;
        leftFunction = null;
        super.onDestroy();
    }

    @Nullable
    public androidx.appcompat.app.AlertDialog.Builder getDialogBuilder() {
        FragmentActivity activity = requireActivity();
        if (style == 0) {
            return this.alertDialogBuilder == null ?
                    (this.alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(activity)) : this.alertDialogBuilder;
        }
        return this.alertDialogBuilder == null ?
                (this.alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(activity, style)) : this.alertDialogBuilder;
    }

    @Override
    public TitleDialogFragment create() {
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
                        cancelListener.onTarget(TitleDialogFragment.this);
                    }
                });
            }
            if (dismissListener != null) {
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        dismissListener.onTarget(TitleDialogFragment.this);
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
                            confirmFunction.onTarget(TitleDialogFragment.this);
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
                            cancelFunction.onTarget(TitleDialogFragment.this);
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
                            leftFunction.onTarget(TitleDialogFragment.this);
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
        Dialog dialog = getDialog();
        if (dialog != null) {
            return dialog.isShowing();
        }
        return mIsShowing;
    }

    @Override
    @Deprecated
    public void show() {
        MH.postOnMain(new Runnable() {
            @Override
            public void run() {
                if (isShowing()) {
                    return;
                }
                show(getParentFragmentManager(), "title");
                mIsShowing = true;
            }
        });
    }

    public void show(@NonNull FragmentManager manager) {
        if (isShowing()) {
            return;
        }
        show(manager, "title");
        mIsShowing = true;
    }

    public void showTip(@NonNull FragmentManager manager) {
        showTip(manager, 1000);
    }

    public void showTip(@NonNull FragmentManager manager, final long delayMillis) {
        show(manager);
        MH.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, delayMillis);
    }

    @Override
    public void dismiss() {
        mIsShowing = false;
        super.dismiss();
    }

    @Override
    public void setProgress(int value) {
        throw new RuntimeException("not call setProgress");
    }

    @Override
    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    @Override
    public TitleDialogFragment setStyle(int style) {
        this.style = style;
        return this;
    }

    @Override
    public TitleDialogFragment setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    @Override
    public TitleDialogFragment setContent(CharSequence content) {
        this.content = content;
        return this;
    }

    @Override
    public TitleDialogFragment applyBottom() {
        applyBottom = true;
        return this;
    }

    @Override
    public TitleDialogFragment setOnCancelListener(@Nullable OnTargetListener<TitleDialogFragment> cancelListener) {
        this.cancelListener = cancelListener;
        return this;
    }

    @Override
    public TitleDialogFragment setOnDismissListener(@Nullable OnTargetListener<TitleDialogFragment> dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    @Override
    public TitleDialogFragment setConfirm(CharSequence confirm, OnTargetListener<TitleDialogFragment> confirmFunction) {
        this.confirm = confirm;
        this.confirmFunction = confirmFunction;
        return this;
    }

    @Override
    public TitleDialogFragment setCancel(CharSequence cancel, OnTargetListener<TitleDialogFragment> cancelFunction) {
        this.cancel = cancel;
        this.cancelFunction = cancelFunction;
        return this;
    }

    @Override
    public TitleDialogFragment setLeftButton(CharSequence left, OnTargetListener<TitleDialogFragment> leftFunction) {
        this.left = left;
        this.leftFunction = leftFunction;
        return this;
    }


}
