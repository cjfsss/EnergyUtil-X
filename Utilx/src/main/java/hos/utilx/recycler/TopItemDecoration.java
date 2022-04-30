package hos.utilx.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import hos.util.func.Function1;
import hos.utilx.R;

/**
 * <p>Title: TopItemDecoration </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/4/6 20:42
 */
public class TopItemDecoration extends RecyclerView.ItemDecoration {

    public TopItemDecoration(Context context, Function1<Integer, String> tagListener) {
        super();
        mContext = context.getApplicationContext();
        this.tagListener = tagListener;
    }

    //间隔高度
    private final int mHeight = 100;

    //矩形画笔
    private final Paint mPaint = new Paint();

    //标签画笔
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Context mContext;
    private final Rect mRound = new Rect();

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        mPaint.setColor(Color.parseColor("#20FFFFFF"));
        textPaint.setColor(Color.parseColor("#ECECEC"));
        textPaint.setTextSize(40f);
        float left = parent.getPaddingLeft();
        float right = (parent.getWidth() - parent.getPaddingRight());
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            float bottom = childView.getTop();
            float top = bottom - mHeight;
            //绘制灰底矩形间隔
            c.drawRect(left, top, right, bottom, mPaint);
            //根据位置获取当前item的标签
            String tag = tagListener.invoke(parent.getChildAdapterPosition(childView));
            //绘制标签文本内容
            textPaint.getTextBounds(tag, 0, tag.length(), mRound);
            c.drawText(
                    tag,
                    left + textPaint.getTextSize(),
                    bottom - mHeight / 2 + mRound.height() / 2,
                    textPaint
            );
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //设置间隔高度
        outRect.top = mHeight;
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        float left = parent.getPaddingLeft();
        float right = (parent.getWidth() - parent.getPaddingRight());
        LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
        //第一个可见item位置
        int index = Objects.requireNonNull(manager).findFirstVisibleItemPosition();
        if (index != -1) {
            //获取指定位置item的View信息
            View childView = Objects.requireNonNull(parent.findViewHolderForLayoutPosition(index)).itemView;
            float top = parent.getPaddingTop();
            String tag = tagListener.invoke(index);
            float bottom = parent.getPaddingTop() + mHeight;
            //悬浮置顶判断，其实也就是一直在绘制一个矩形加文本内容(上滑时取值bottom，下滑时取值childView.bottom.toFloat())

            bottom = coerceAtMost(childView.getBottom(), bottom);
            c.drawRect(0f, top, right, bottom, mPaint);
            textPaint.getTextBounds(tag, 0, tag.length(), mRound);
            c.drawText(
                    tag,
                    left + textPaint.getTextSize(),
                    bottom - mHeight / 2 + mRound.height() / 2,
                    textPaint
            );
        }
    }

    private float coerceAtMost(float value, float maximumValue) {
        if (value > maximumValue) {
            return maximumValue;
        } else {
            return value;
        }
    }

    /**
     * 获取悬停标签
     */
    private final Function1<Integer, String> tagListener;


}
