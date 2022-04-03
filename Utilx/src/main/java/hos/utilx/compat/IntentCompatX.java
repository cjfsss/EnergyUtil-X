package hos.utilx.compat;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;

import hos.util.compat.IntentCompat;

/**
 * <p>Title: IntentUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/7/12 20:45
 */
public class IntentCompatX {
    @Nullable
    public static Intent getLaunchTargetOpenFile(@NonNull Context context, @NonNull final String pkgName,
                                                 @Nullable final String filePath) {
        return IntentCompat.getLaunchTargetOpenFile(context, pkgName, filePath, new DefaultUriCallbackFile());
    }

    @Nullable
    public static Intent getLaunchTargetOpenFile(@NonNull Context context, @NonNull final String pkgName,
                                                 @Nullable final File file) {
        return IntentCompat.getLaunchTargetOpenFile(context, pkgName, file, new DefaultUriCallbackFile());
    }

    @Nullable
    public static Intent getLaunchOpenFile(@NonNull Context context, @Nullable String filePath) {
        return IntentCompat.getLaunchOpenFile(context, filePath, new DefaultUriCallbackFile());
    }

    @NonNull
    public static Intent getLaunchOpenFile(@NonNull Context context, @NonNull File file) {
        return IntentCompat.getLaunchOpenFile(context, file, new DefaultUriCallbackFile());
    }

    @Nullable
    public static Intent getLaunchOpenWPS(@NonNull Context context, @Nullable String filePath) {
        return IntentCompat.getLaunchOpenWPS(context, filePath, new DefaultUriCallbackFile());
    }

    @Nullable
    public static Intent getLaunchOpenWPS(@NonNull Context context, @NonNull File file) {
        return IntentCompat.getLaunchOpenWPS(context, file, new DefaultUriCallbackFile());
    }
}
