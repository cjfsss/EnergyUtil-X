package hos.utilx.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import hos.thread.hander.MH;
import hos.util.dialog.TitleDialogInterface;
import hos.util.dialog.ViewDialogInterface;
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
public class ViewDialogFragment extends DialogFragment implements ViewDialogInterface<ViewDialogFragment> {
    private androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder;
    private androidx.appcompat.app.AlertDialog dialogReal;
    protected CharSequence title;
    protected int style;
    protected boolean cancelable = true;
    protected boolean applyBottom = false;

    private boolean mIsShowing;
    protected View view;
    protected int layoutId;

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
    public ViewDialogFragment create() {
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
    public ViewDialogFragment setStyle(int style) {
        this.style = style;
        return this;
    }

    @Override
    public ViewDialogFragment setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    @Override
    @Deprecated
    public ViewDialogFragment setContent(CharSequence charSequence) {
        return this;
    }


    @Override
    public ViewDialogFragment applyBottom() {
        applyBottom = true;
        return this;
    }

    @Override
    public ViewDialogFragment setView(View view) {
        this.view = view;
        return this;
    }

    @Override
    public ViewDialogFragment setView(int i) {
        this.layoutId = i;
        return this;
    }
}
