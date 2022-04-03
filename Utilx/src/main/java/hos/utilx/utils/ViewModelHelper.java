package hos.utilx.utils;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * <p>Title: ViewModelHelper </p>
 * <p>Description: ViewModel 帮助类 </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/10/18 21:23
 */
public class ViewModelHelper {

    /**
     * 获取ViewModel
     *
     * @param activity 上下文
     * @param clazz    目标对象
     * @param <T>      返回值
     */
    public static <T extends ViewModel> T viewModel(@NonNull ComponentActivity activity, @NonNull Class<T> clazz) {
        return viewModel(activity, clazz, null);
    }

    /**
     * 获取ViewModel
     *
     * @param activity        上下文
     * @param clazz           目标对象
     * @param factoryProducer 工厂
     * @param <T>             返回值
     */
    public static <T extends ViewModel> T viewModel(@NonNull ComponentActivity activity, @NonNull Class<T> clazz, @Nullable ViewModelProvider.Factory factoryProducer) {
        if (factoryProducer == null) {
            factoryProducer = activity.getDefaultViewModelProviderFactory();
        }
        return new ViewModelProvider(activity.getViewModelStore(), factoryProducer).get(clazz);
    }

    /**
     * 获取ViewModel
     *
     * @param fragment fragment
     * @param clazz    目标对象
     * @param <T>      返回值
     */
    public static <T extends ViewModel> T viewModel(@NonNull Fragment fragment, @NonNull Class<T> clazz) {
        return viewModel(fragment, clazz, null, null);
    }

    /**
     * 获取ViewModel
     *
     * @param fragment        fragment
     * @param clazz           目标对象
     * @param factoryProducer 工厂
     * @param <T>             返回值
     */
    public static <T extends ViewModel> T viewModel(@NonNull Fragment fragment, @NonNull Class<T> clazz, @Nullable ViewModelStoreOwner ownerProducer, @Nullable ViewModelProvider.Factory factoryProducer) {
        if (factoryProducer == null) {
            factoryProducer = fragment.getDefaultViewModelProviderFactory();
        }
        if (ownerProducer == null) {
            ownerProducer = fragment;
        }
        return new ViewModelProvider(ownerProducer.getViewModelStore(), factoryProducer).get(clazz);
    }

    /**
     * 获取ViewModel
     *
     * @param fragment fragment
     * @param clazz    目标对象
     * @param <T>      返回值
     */
    public static <T extends ViewModel> T activityViewModels(@NonNull Fragment fragment, @NonNull Class<T> clazz) {
        return activityViewModels(fragment, clazz, null);
    }

    /**
     * 获取ViewModel
     *
     * @param fragment        fragment
     * @param clazz           目标对象
     * @param factoryProducer 工厂
     * @param <T>             返回值
     */
    public static <T extends ViewModel> T activityViewModels(@NonNull Fragment fragment, @NonNull Class<T> clazz, @Nullable ViewModelProvider.Factory factoryProducer) {
        if (factoryProducer == null) {
            factoryProducer = fragment.requireActivity().getDefaultViewModelProviderFactory();
        }
        return new ViewModelProvider(fragment.requireActivity().getViewModelStore(), factoryProducer).get(clazz);
    }
}
