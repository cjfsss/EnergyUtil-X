package hos.utilx.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import hos.thread.hander.MH;
import hos.util.dialog.ChoiceDialogInterface;
import hos.util.dialog.OnItemDialogListener;
import hos.util.listener.OnTargetListener;
import hos.util.utils.ResUtils;
import hos.util.utils.WindowUtils;

/**
 * <p>Title: AppCompatAlertChoiceDialogImpl </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/4/24 21:20
 */
public class ChoiceDialogFragment extends DialogFragment implements ChoiceDialogInterface<ChoiceDialogFragment> {
    protected AlertDialog.Builder alertDialogBuilder;
    protected AlertDialog dialogReal;

    protected CharSequence title;
    protected CharSequence content;
    protected int style;
    protected boolean cancelable = true;
    protected boolean applyBottom = false;

    protected int itemId;
    protected CharSequence[] items;
    protected ListAdapter adapter;
    protected int checkedPosition;
    protected int[] checkedPositions;
    protected OnItemDialogListener<ChoiceDialogFragment> listener;

    protected CharSequence confirm;
    protected CharSequence cancel;
    protected OnTargetListener<ChoiceDialogFragment> confirmFunction;
    protected OnTargetListener<ChoiceDialogFragment> cancelFunction;

    private int showType;

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
        super.onDestroy();
    }

    @Nullable
    public AlertDialog.Builder getDialogBuilder() {
        FragmentActivity activity = requireActivity();
        if (style == 0) {
            return this.alertDialogBuilder == null ? (this.alertDialogBuilder = new AlertDialog.Builder(activity)) : this.alertDialogBuilder;
        }
        return this.alertDialogBuilder == null ? (this.alertDialogBuilder = new AlertDialog.Builder(activity, style)) : this.alertDialogBuilder;
    }

    @Override
    public ChoiceDialogFragment create() {
        if (dialogReal != null) {
            return this;
        }
        AlertDialog.Builder builder = getDialogBuilder();
        if (builder != null) {
            builder.setCancelable(cancelable);
            if (itemId != 0) {
                items = ResUtils.getStringArray(itemId);
            }
            if (showType == 1) {
                // showSingle
                if (items != null) {
                    if (listener == null) {
                        builder.setSingleChoiceItems(items, checkedPosition, null);
                    } else {
                        builder.setSingleChoiceItems(items, checkedPosition, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                if (listener != null) {
                                    listener.onItemSelected(ChoiceDialogFragment.this, items[which], which, true);
                                }
                            }
                        });
                    }
                }
                if (adapter != null) {
                    if (listener == null) {
                        builder.setSingleChoiceItems(adapter, checkedPosition, null);
                    } else {
                        builder.setSingleChoiceItems(adapter, checkedPosition, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (listener != null) {
                                    listener.onItemSelected(ChoiceDialogFragment.this, adapter.getItem(which), which, true);
                                }
                            }
                        });
                    }
                }
            } else if (showType == 2) {
                // showMulti
                if (items != null) {
                    int length = items.length;
                    boolean[] checkedItems = new boolean[length];
                    for (int i = 0; i < items.length; i++) {
                        if (checkedPositions != null && checkedPositions.length != 0) {
                            for (int position : checkedPositions) {
                                if (i == position) {
                                    checkedItems[i] = true;
                                    break;
                                }
                                checkedItems[i] = false;
                            }
                        } else {
                            checkedItems[i] = false;
                        }
                    }
                    if (listener == null) {
                        builder.setMultiChoiceItems(items, checkedItems, null);
                    } else {
                        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (listener != null) {
                                    listener.onItemSelected(ChoiceDialogFragment.this, items[which], which, isChecked);
                                }
                            }
                        });
                    }
                }
            } else {
                // showItem
                if (items != null) {
                    if (listener == null) {
                        builder.setItems(items, null);
                    } else {
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (listener != null) {
                                    listener.onItemSelected(ChoiceDialogFragment.this, items[which], which, true);
                                }
                            }
                        });
                    }
                }
            }
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
    public void show() {
        MH.postOnMain(new Runnable() {
            @Override
            public void run() {
                if (isShowing()) {
                    return;
                }
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
    public void showItem() {
        showType = 0;
        show();
    }

    public ChoiceDialogFragment setItem(int itemId) {
        this.itemId = itemId;
        return this;
    }

    public ChoiceDialogFragment setItem(CharSequence[] items) {
        this.items = items;
        return this;
    }

    public ChoiceDialogFragment setAdapter(ListAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public ChoiceDialogFragment setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        return this;
    }

    public ChoiceDialogFragment setCheckedPosition(int[] checkedPositions) {
        this.checkedPositions = checkedPositions;
        return this;
    }

    public ChoiceDialogFragment setOnItemDialogListener(OnItemDialogListener<ChoiceDialogFragment> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void showSingle() {
        showType = 1;
        show();
    }

    @Override
    public void showMulti() {
        showType = 2;
        show();
    }


    @Override
    @Deprecated
    public void setProgress(int value) {
        throw new RuntimeException("not call setProgress");
    }

    @Override
    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    @Override
    public ChoiceDialogFragment setStyle(int style) {
        this.style = style;
        return this;
    }

    @Override
    public ChoiceDialogFragment setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    @Override
    public ChoiceDialogFragment setContent(CharSequence content) {
        this.content = content;
        return this;
    }

    @Override
    public ChoiceDialogFragment applyBottom() {
        applyBottom = true;
        return this;
    }

    //
    @Override
    public ChoiceDialogFragment setConfirm(CharSequence confirm, OnTargetListener<ChoiceDialogFragment> confirmFunction) {
        this.confirm = confirm;
        this.confirmFunction = confirmFunction;
        return this;
    }

    @Override
    public ChoiceDialogFragment setCancel(CharSequence cancel, OnTargetListener<ChoiceDialogFragment> cancelFunction) {
        this.cancel = cancel;
        this.cancelFunction = cancelFunction;
        return this;
    }

}
