package hos.utilx.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * <p>Title: FragmentStateAdapterCreateFragment </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/4/11 23:36
 */
public interface FragmentStateAdapterCreateFragment {
    @NonNull
    Fragment createFragment(String tab, int position);
}
