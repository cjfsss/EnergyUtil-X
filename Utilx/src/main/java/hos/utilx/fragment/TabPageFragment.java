package hos.utilx.fragment;

import androidx.annotation.NonNull;

/**
 * <p>Title: TabPageFragment </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/4/14 22:14
 */
public interface TabPageFragment extends FragmentStateAdapterCreateFragment {

    @NonNull
    String[] getTabs();

    default int selectTab() {
        return 0;
    }
}
