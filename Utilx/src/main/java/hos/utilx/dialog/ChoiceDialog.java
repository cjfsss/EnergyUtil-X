package hos.utilx.dialog;

import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import hos.thread.hander.MH;
import hos.util.dialog.ChoiceDialogImpl;
import hos.util.dialog.ChoiceDialogInterface;
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
public class ChoiceDialog extends ChoiceDialogImpl<ChoiceDialog> implements ChoiceDialogInterface<ChoiceDialog> {
    private AppCompatActivity activity;
    protected AlertDialog.Builder alertDialogBuilder;
    protected AlertDialog dialogReal;

    private int showType;

    public ChoiceDialog(@NonNull AppCompatActivity activity) {
        this.activity = activity;
        activity.getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    // 销毁的时候执行
                    dismiss();
                    source.getLifecycle().removeObserver(this);
                    ChoiceDialog.this.activity = null;
                    ChoiceDialog.this.alertDialogBuilder = null;
                    ChoiceDialog.this.dialogReal = null;
                    clear();
                }
            }
        });
    }

    @Nullable
    public AlertDialog.Builder getDialogBuilder() {
        if (activity == null) {
            return null;
        }
        if (style == 0) {
            return this.alertDialogBuilder == null ? (this.alertDialogBuilder = new AlertDialog.Builder(activity)) : this.alertDialogBuilder;
        }
        return this.alertDialogBuilder == null ? (this.alertDialogBuilder = new AlertDialog.Builder(activity, style)) : this.alertDialogBuilder;
    }

    @Override
    public ChoiceDialog create() {
        if (dialogReal != null) {
            return this;
        }
        androidx.appcompat.app.AlertDialog.Builder builder = getDialogBuilder();
        if (builder != null) {
            builder.setCancelable(cancelable);
            if (itemId != 0) {
                items = ResUtils.getStringArray(itemId);
            }
            if (title != null) {
                builder.setTitle(title);
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
                                    listener.onItemSelected(ChoiceDialog.this, items[which], which, true);
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
                                    listener.onItemSelected(ChoiceDialog.this, adapter.getItem(which), which, true);
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
                                    listener.onItemSelected(ChoiceDialog.this, items[which], which, isChecked);
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
                                    listener.onItemSelected(ChoiceDialog.this, items[which], which, true);
                                }
                            }
                        });
                    }
                }
            }
            if (confirm != null) {
                if (confirmFunction == null) {
                    builder.setPositiveButton(confirm, null);
                } else {
                    builder.setPositiveButton(confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            confirmFunction.onTarget(ChoiceDialog.this);
                        }
                    });
                }
            }
            if (cancel != null) {
                if (cancelFunction == null) {
                    builder.setNegativeButton(cancel, null);
                } else {
                    builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cancelFunction.onTarget(ChoiceDialog.this);
                        }
                    });
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
    public void showItem() {
        showType = 0;
        show();
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
}
