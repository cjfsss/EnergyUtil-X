package hos.utilx.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import hos.util.compat.ResContext;
import hos.util.listener.OnCancelListener;
import hos.util.listener.OnConfirmListener;


/**
 * <p>Title: AlertDialogBuilder </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/2/22 20:33
 */
public final class AlertDialogBuilder {

    @NonNull
    public static AlertDialog.Builder asConfirmRes(@NonNull AlertDialog.Builder builder,
                                                   @StringRes int titleId, @StringRes int contentId,
                                                   @StringRes int confirmId, @Nullable OnConfirmListener confirmListener,
                                                   @StringRes int cancelId, @Nullable OnCancelListener cancelListener,
                                                   boolean isHideCancel, @LayoutRes int bindLayoutId) {
        Context context = builder.getContext();
        CharSequence title = null;
        if (titleId != 0) {
            title = ResContext.getString(context, titleId);
        }
        CharSequence content = null;
        if (contentId != 0) {
            content = ResContext.getString(context, contentId);
        }
        CharSequence cancelBtnText = null;
        if (cancelId != 0) {
            cancelBtnText = ResContext.getString(context, cancelId);
        }
        CharSequence confirmBtnText = null;
        if (confirmId != 0) {
            confirmBtnText = ResContext.getString(context, confirmId);
        }
        return asConfirm(builder,
                title, content, confirmBtnText, confirmListener,
                cancelBtnText, cancelListener, isHideCancel, bindLayoutId
        );
    }

    @NonNull
    public static AlertDialog.Builder asConfirm(@NonNull AlertDialog.Builder builder,
                                                @Nullable CharSequence title, @Nullable CharSequence content,
                                                @Nullable CharSequence confirmBtnText, @Nullable OnConfirmListener confirmListener,
                                                @Nullable CharSequence cancelBtnText, @Nullable OnCancelListener cancelListener,
                                                boolean isHideCancel, @LayoutRes int bindLayoutId) {
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(content)) {
            builder.setMessage(content);
        }
        if (!isHideCancel && !TextUtils.isEmpty(cancelBtnText)) {
            builder.setNegativeButton(cancelBtnText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (cancelListener != null) {
                        cancelListener.onCancel();
                    }
                }
            });
        }
        if (!TextUtils.isEmpty(confirmBtnText)) {
            builder.setPositiveButton(confirmBtnText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (confirmListener != null) {
                        confirmListener.onConfirm();
                    }
                }
            });
        }
        if (bindLayoutId != 0) {
            builder.setView(bindLayoutId);
        }
        return builder;
    }

    @NonNull
    public static AlertDialog.Builder asConfirm(@NonNull AlertDialog.Builder builder,
                                                @Nullable CharSequence title, @Nullable CharSequence content,
                                                @Nullable CharSequence confirmBtnText, @Nullable OnConfirmListener confirmListener,
                                                @Nullable CharSequence cancelBtnText, @Nullable OnCancelListener cancelListener,
                                                boolean isHideCancel) {
        return asConfirm(builder, title, content, confirmBtnText, confirmListener,
                cancelBtnText, cancelListener, isHideCancel, 0);
    }

    @NonNull
    public static AlertDialog.Builder asConfirm(@NonNull AlertDialog.Builder builder,
                                                @Nullable CharSequence title, @Nullable CharSequence content,
                                                @Nullable CharSequence confirmBtnText, @Nullable OnConfirmListener confirmListener,
                                                @Nullable CharSequence cancelBtnText, @Nullable OnCancelListener cancelListener) {
        return asConfirm(builder, title, content, confirmBtnText, confirmListener,
                cancelBtnText, cancelListener, false, 0);
    }

    @NonNull
    public static AlertDialog.Builder asConfirm(@NonNull AlertDialog.Builder builder,
                                                @Nullable CharSequence title, @Nullable CharSequence content,
                                                @Nullable CharSequence confirmBtnText, @Nullable OnConfirmListener confirmListener) {
        return asConfirm(builder, title, content, confirmBtnText, confirmListener,
                null, null, true, 0);
    }

    @NonNull
    public static AlertDialog.Builder asConfirm(@NonNull AlertDialog.Builder builder,
                                                @Nullable CharSequence title,
                                                @Nullable CharSequence confirmBtnText, @Nullable OnConfirmListener confirmListener) {
        return asConfirm(builder, title, null, confirmBtnText, confirmListener,
                null, null, true, 0);
    }

    @NonNull
    public static AlertDialog.Builder asConfirmButton(@NonNull AlertDialog.Builder builder,
                                                      @Nullable CharSequence title, @Nullable CharSequence content,
                                                      @Nullable OnConfirmListener confirmListener,
                                                      @Nullable OnCancelListener cancelListener) {
        return asConfirm(builder, title, content, "确定", confirmListener,
                "取消", cancelListener, false, 0);
    }

    @NonNull
    public static AlertDialog.Builder asConfirmButton(@NonNull AlertDialog.Builder builder,
                                                      @Nullable CharSequence title, @Nullable CharSequence content,
                                                      @Nullable OnConfirmListener confirmListener) {
        return asConfirm(builder, title, content, "确定", confirmListener,
                null, null, true, 0);
    }

    @NonNull
    public static AlertDialog.Builder asConfirmButton(@NonNull AlertDialog.Builder builder,
                                                      @Nullable CharSequence title,
                                                      @Nullable OnConfirmListener confirmListener) {
        return asConfirm(builder, title, null, "确定", confirmListener,
                null, null, true, 0);
    }

    @NonNull
    public static AlertDialog.Builder asConfirmTitle(@NonNull AlertDialog.Builder builder,
                                                     @Nullable CharSequence content,
                                                     @Nullable CharSequence confirmBtnText, @Nullable OnConfirmListener confirmListener,
                                                     @Nullable CharSequence cancelBtnText, @Nullable OnCancelListener cancelListener) {
        return asConfirm(builder, "温馨提示", content, confirmBtnText, confirmListener,
                cancelBtnText, cancelListener, false, 0);
    }

    @NonNull
    public static AlertDialog.Builder asConfirmTitle(@NonNull AlertDialog.Builder builder,
                                                     @Nullable CharSequence content,
                                                     @Nullable CharSequence confirmBtnText, @Nullable OnConfirmListener confirmListener) {
        return asConfirm(builder, "温馨提示", content, confirmBtnText, confirmListener,
                null, null, true, 0);
    }

    @NonNull
    public static AlertDialog.Builder asConfirmTitle(@NonNull AlertDialog.Builder builder,
                                                     @Nullable CharSequence confirmBtnText, @Nullable OnConfirmListener confirmListener) {
        return asConfirm(builder, "温馨提示", null, confirmBtnText, confirmListener,
                null, null, true, 0);
    }

    @NonNull
    public static AlertDialog.Builder asConfirmTitleButton(@NonNull AlertDialog.Builder builder,
                                                           @Nullable CharSequence content, @Nullable OnConfirmListener confirmListener,
                                                           @Nullable OnCancelListener cancelListener) {
        return asConfirm(builder, "温馨提示", content, "确定", confirmListener,
                "取消", cancelListener, false, 0);
    }

    @NonNull
    public static AlertDialog.Builder asConfirmTitleButton(@NonNull AlertDialog.Builder builder,
                                                           @Nullable CharSequence content, @Nullable OnConfirmListener confirmListener) {
        return asConfirm(builder, "温馨提示", content, "确定", confirmListener,
                null, null, true, 0);
    }

    @NonNull
    public static AlertDialog.Builder asConfirmTitleButton(@NonNull AlertDialog.Builder builder,
                                                           @Nullable OnConfirmListener confirmListener) {
        return asConfirm(builder, "温馨提示", null, "确定", confirmListener,
                null, null, true, 0);
    }
}
