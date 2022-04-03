package hos.utilx.bus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

/**
 * <p>Title: StickyLiveData </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/3/31 20:34
 */
public class StickyLiveData<T> extends LiveData<T> {

    private final String eventName;

    int version = 0;

    @Nullable
    T stickyData = null;

    public StickyLiveData(String eventName) {
        this.eventName = eventName;
    }

    public void setStickData(T stickyData) {
        this.stickyData = stickyData;
        setValue(stickyData);
    }

    public void postStickData(T stickyData) {
        this.stickyData = stickyData;
        postValue(stickyData);
    }

    @Override
    public void setValue(T value) {
        version++;
        super.setValue(value);
    }

    @Override
    public void postValue(T value) {
        version++;
        super.postValue(value);
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        observeSticky(owner, false, observer);
    }

    public void observeSticky(LifecycleOwner owner, boolean sticky, Observer<? super T> observer) {
        owner.getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    LiveBus.eventMap.remove(eventName);
                }
            }
        });
        super.observe(owner, new StickyObserver<T>(this, sticky, observer));
    }
}
