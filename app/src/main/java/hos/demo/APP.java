package hos.demo;

import android.app.Application;

import com.google.gson.Gson;

import hos.core.ActivityManager;
import hos.core.AppCompat;
import hos.util.carash.CrashHandler;
import hos.util.log.HiConsolePrinter;
import hos.util.log.HiLogConfig;
import hos.util.log.HiLogManager;

/**
 * <p>Title: APP </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/4/3 18:02
 */
class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompat.init(this);
        ActivityManager.init(this);
        CrashHandler.init();
        HiLogManager.init(new HiLogConfig() {
            @Override
            public JsonParser injectJsonParser() {
                return new JsonParser() {
                    @Override
                    public String toJson(Object src) {
                        return new Gson().toJson(src);
                    }
                };
            }
        },new HiConsolePrinter());
    }
}
