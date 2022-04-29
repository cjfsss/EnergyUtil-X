package hos.utilx.dialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * <p>Title: ProgressDialogDefalult </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/4/14 22:59
 */
public class ProgressHorizontalDialog extends ProgressDialog {

    public ProgressHorizontalDialog(@NonNull AppCompatActivity activity) {
        super(activity);
    }

    @Nullable
    protected android.app.ProgressDialog getProgressDialog() {
        android.app.ProgressDialog dialog = super.getProgressDialog();
        //设置样式
        if (dialog == null) {
            return null;
        }
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(android.app.ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        return dialog;
    }

    public ProgressHorizontalDialog showIndeterminate() {
        return showIndeterminate(true);
    }

    public ProgressHorizontalDialog showIndeterminate(boolean indeterminate) {
        android.app.ProgressDialog progressDialog = getProgressDialog();
        if (progressDialog != null) {
            progressDialog.setIndeterminate(false);
        }
        return this;
    }

    @Override
    public ProgressDialog setTitle(CharSequence title) {
        android.app.ProgressDialog progressDialog = getProgressDialog();
        if (progressDialog != null) {
            progressDialog.setTitle(title);
        }
        return super.setTitle(title);
    }
}
