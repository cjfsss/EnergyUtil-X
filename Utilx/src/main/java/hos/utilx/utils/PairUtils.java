package hos.utilx.utils;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Title: PairUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/7/26 20:27
 */
public final class PairUtils {

    @Nullable
    public static List<Pair<String, Object>> toPair(@Nullable Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        Set<String> keySet = map.keySet();
        List<Pair<String, Object>> list = new ArrayList<>();
        for (String key : keySet) {
            list.add(new Pair<String, Object>(key, map.get(key)));
        }
        return list;
    }

    @SafeVarargs
    @Nullable
    public static Map<String, Object> toMap(@Nullable Pair<String, Object>... pairs) {
        if (pairs == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        for (Pair<String, Object> pair : pairs) {
            map.put(pair.first, pair.second);
        }
        return map;
    }
}
