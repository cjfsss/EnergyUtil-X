package hos.utilx.bus;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Title: LiveBus </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/3/31 20:43
 */
public class LiveBus {
    static final ConcurrentHashMap<String, StickyLiveData<?>> eventMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> StickyLiveData<T> with(String eventName) {
        //基于事件名称 订阅 分发消息
        // 由于一个livedata 只能发送一种数据类型，所以 不同的event 事件，需要使用不同的livedata实例 分发
        StickyLiveData<?> stickyLiveData = eventMap.get(eventName);
        if (stickyLiveData == null) {
            stickyLiveData = new StickyLiveData<T>(eventName);
            eventMap.put(eventName, stickyLiveData);
        }
        return (StickyLiveData<T>) stickyLiveData;
    }

}
