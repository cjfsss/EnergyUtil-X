package hos.utilx.fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * <p>Title: TabPageFragmentHelper </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/4/11 23:28
 */
public class TabPageFragmentHelper {

    private TabLayoutMediator mTabLayoutMediator;

    public TabPageFragmentHelper bindActivity(@NonNull AppCompatActivity fragment, @NonNull TabLayout tabLayout,
                                              @NonNull ViewPager2 viewPager2, @NonNull TabPageFragment tabPageFragment,
                                              @NonNull TabLayoutMediator.TabConfigurationStrategy strategy
    ) {
        return bindManager(fragment.getSupportFragmentManager(), fragment.getLifecycle(), tabLayout, viewPager2, tabPageFragment, strategy);
    }

    private TabPageFragmentHelper bindActivity(@NonNull AppCompatActivity fragment, @NonNull TabLayout tabLayout,
                                               @NonNull ViewPager2 viewPager2, @NonNull TabPageFragment tabPageFragment
    ) {
        return bindManager(fragment.getSupportFragmentManager(), fragment.getLifecycle(), tabLayout, viewPager2, tabPageFragment);
    }

    public TabPageFragmentHelper bindManager(@NonNull Fragment fragment, @NonNull TabLayout tabLayout,
                                             @NonNull ViewPager2 viewPager2, @NonNull TabPageFragment tabPageFragment,
                                             @NonNull TabLayoutMediator.TabConfigurationStrategy strategy
    ) {
        return bindManager(fragment.getChildFragmentManager(), fragment.getLifecycle(), tabLayout, viewPager2, tabPageFragment, strategy);
    }

    private TabPageFragmentHelper bindFragment(@NonNull Fragment fragment, @NonNull TabLayout tabLayout,
                                               @NonNull ViewPager2 viewPager2, @NonNull TabPageFragment tabPageFragment
    ) {
        return bindManager(fragment.getChildFragmentManager(), fragment.getLifecycle(), tabLayout, viewPager2, tabPageFragment);
    }


    public TabPageFragmentHelper bindManager(@NonNull FragmentManager fragmentManager,
                                             @NonNull Lifecycle lifecycle, @NonNull TabLayout tabLayout,
                                             @NonNull ViewPager2 viewPager2, @NonNull TabPageFragment tabPageFragment,
                                             @NonNull TabLayoutMediator.TabConfigurationStrategy strategy
    ) {
        return bind(fragmentManager, lifecycle, tabLayout, viewPager2, tabPageFragment, tabPageFragment, null, strategy);
    }


    public TabPageFragmentHelper bindManager(@NonNull FragmentManager fragmentManager,
                                             @NonNull Lifecycle lifecycle, @NonNull TabLayout tabLayout,
                                             @NonNull ViewPager2 viewPager2, @NonNull TabPageFragment tabPageFragment
    ) {
        return bind(fragmentManager, lifecycle, tabLayout, viewPager2, tabPageFragment, tabPageFragment, null, null);
    }

    public TabPageFragmentHelper bind(@NonNull TabLayout tabLayout, @NonNull ViewPager2 viewPager2, @NonNull TabPageFragment tabPageFragment,
                                      @NonNull FragmentStateAdapter adapter
    ) {
        return bind(null, null, tabLayout, viewPager2, tabPageFragment, tabPageFragment, adapter, null);
    }

    public TabPageFragmentHelper bind(@NonNull TabLayout tabLayout, @NonNull ViewPager2 viewPager2, @NonNull TabPageFragment tabPageFragment,
                                      @NonNull FragmentStateAdapter adapter, @NonNull TabLayoutMediator.TabConfigurationStrategy strategy
    ) {
        return bind(null, null, tabLayout, viewPager2, tabPageFragment, tabPageFragment, adapter, strategy);
    }

    private TabPageFragmentHelper bind(FragmentManager fragmentManager,
                                       Lifecycle lifecycle, @NonNull TabLayout tabLayout,
                                       @NonNull ViewPager2 viewPager2, @NonNull TabPageFragment tabPageFragment,
                                       FragmentStateAdapterCreateFragment fragmentStateAdapterCreateFragment,
                                       FragmentStateAdapter adapter, TabLayoutMediator.TabConfigurationStrategy strategy
    ) {
        // Adapter
        String[] tabs = tabPageFragment.getTabs();
        int selectTab = tabPageFragment.selectTab();
        if (tabs.length < 5) {
            viewPager2.setOffscreenPageLimit(tabs.length);
        } else {
            viewPager2.setOffscreenPageLimit(1);
        }
        if (strategy == null) {
            strategy = new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    tab.setText(tabs[position]);
                }
            };
        }
        if (adapter == null && fragmentManager != null && lifecycle != null) {
            adapter = new FragmentStateAdapter(fragmentManager, lifecycle) {
                @NonNull
                @Override
                public Fragment createFragment(int position) {
                    return fragmentStateAdapterCreateFragment.createFragment(tabs[position], position);
                }

                @Override
                public int getItemCount() {
                    return tabs.length;
                }
            };
            lifecycle.addObserver(new LifecycleEventObserver() {
                @Override
                public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        detach();
                        tabLayout.clearOnTabSelectedListeners();
                        source.getLifecycle().removeObserver(this);
                    }
                }
            });
        }
        viewPager2.setAdapter(adapter);
        mTabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, strategy);
        //要执行这一句才是真正将两者绑定起来
        mTabLayoutMediator.attach();
        // 设置默认选中
        viewPager2.setCurrentItem(selectTab, false);
        tabLayout.selectTab(tabLayout.getTabAt(selectTab));
        return this;
    }


    public void detach() {
        if (mTabLayoutMediator != null) {
            mTabLayoutMediator.detach();
        }
        mTabLayoutMediator = null;
    }

}
