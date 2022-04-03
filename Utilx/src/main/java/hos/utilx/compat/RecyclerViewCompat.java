package hos.utilx.compat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>Title: RecyclerViewUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/3/18 20:41
 */
public class RecyclerViewCompat {

    public static void notifyItemRangeChangedChecked(
            @NonNull final RecyclerView recyclerView,
            int adapterPosition, int size
    ) {
        if (recyclerView.isComputingLayout()) {
            // 延时递归处理。
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyItemRangeChangedChecked(recyclerView, adapterPosition, size);
                }
            }, 100);
        } else {
            @SuppressWarnings("rawtypes") RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null) {
                adapter.notifyItemRangeChanged(adapterPosition, size);
            }
        }
    }


    /**
     * 连级更新选中项
     */
    public static void notifyChangedChecked(@NonNull RecyclerView recyclerView) {
        if (recyclerView.isComputingLayout()) {
            // 延时递归处理。
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyChangedChecked(recyclerView);
                }
            }, 100);
        } else {
            @SuppressWarnings("rawtypes") RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 移除所有分割线
     */
    public static void removeAllItemDecoration(@NonNull RecyclerView recyclerView) {
        // 移除所有的分割线，这里要实现时间轴方式的展示效果
        int itemDecorationCount = recyclerView.getItemDecorationCount();
        for (int i = 0; i < itemDecorationCount; i++) {
            recyclerView.removeItemDecorationAt(i);
        }
    }

    /**
     * 获取View的截图, 支持获取整个RecyclerView列表的长截图
     * 注意：调用该方法时，请确保View已经测量完毕，如果宽高为0，则将抛出异常
     */
    public static Bitmap toBitmap(@NonNull View view) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        if (measuredWidth == 0 || measuredHeight == 0) {
            throw new RuntimeException("调用该方法时，请确保View已经测量完毕，如果宽高为0，则抛出异常以提醒！");
        }
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.scrollToPosition(0);
            int width = recyclerView.getWidth();
            recyclerView.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            Bitmap bmp = Bitmap.createBitmap(width, measuredHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            //draw default bg, otherwise will be black
            Drawable background = recyclerView.getBackground();
            if (background != null) {
                background.setBounds(0, 0, width, measuredHeight);
                background.draw(canvas);
            } else {
                canvas.drawColor(Color.WHITE);
            }
            recyclerView.draw(canvas);
            //恢复高度
            recyclerView.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), View.MeasureSpec.AT_MOST));
            return bmp;
        } else {
            Bitmap screenshot = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(screenshot);
            Drawable background = view.getBackground();
            if (background != null) {
                background.setBounds(0, 0, view.getWidth(), measuredHeight);
                background.draw(canvas);
            } else {
                canvas.drawColor(Color.WHITE);
            }
            view.draw(canvas);// 将 view 画到画布上
            return screenshot;
        }
    }
}
