package hos.utilx.compat;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.File;

import hos.util.listener.UriCallback;

/**
 * <p>Title: DefaultUriCallbackFile </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/3/25 20:30
 */
public class DefaultUriCallbackFile implements UriCallback.UriCallbackFile {
    @Override
    public Uri getUriForFile(@NonNull  Context context, @NonNull String authority, @NonNull File file) {
        return FileProvider.getUriForFile(context, authority, file);
    }
}
