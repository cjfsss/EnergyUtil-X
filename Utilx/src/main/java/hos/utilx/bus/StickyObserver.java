package hos.utilx.bus;

import androidx.lifecycle.Observer;

/**
 * <p>Title: StickyObserver </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/3/31 10:36
 */
public class StickyObserver<T> implements Observer<T> {
    private final StickyLiveData<T> stickyLiveData;
    private final boolean sticky;
    private final Observer<? super T> observer;

    private int lastVersion;

    public StickyObserver(StickyLiveData<T> stickyLiveData, boolean sticky, Observer<? super T> observer) {
        this.stickyLiveData = stickyLiveData;
        this.sticky = sticky;
        this.observer = observer;
        lastVersion = stickyLiveData.version;
    }

    @Override
    public void onChanged(T t) {
        if (lastVersion >= stickyLiveData.version) {
            if (sticky && stickyLiveData.stickyData != null) {
                observer.onChanged(stickyLiveData.stickyData);
            }
            return;
        }
        lastVersion = stickyLiveData.version;
        observer.onChanged(t);
    }
}
