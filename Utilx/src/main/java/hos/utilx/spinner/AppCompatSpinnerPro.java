package hos.utilx.spinner;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import java.util.List;

import hos.util.adapter.ArrayAdapterPro;

/**
 * <p>Title: AppCompatSpinnerPro </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2022/4/28 13:24
 */
public class AppCompatSpinnerPro extends AppCompatSpinner {


    public AppCompatSpinnerPro(@NonNull Context context) {
        super(context);
    }

    public AppCompatSpinnerPro(@NonNull Context context, int mode) {
        super(context, mode);
    }

    public AppCompatSpinnerPro(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AppCompatSpinnerPro(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AppCompatSpinnerPro(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public AppCompatSpinnerPro(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    public <T> AppCompatSpinnerPro bind(@NonNull List<T> objects, int resource) {
        return bind(objects, resource, 0);
    }

    public <T> AppCompatSpinnerPro bind(@NonNull T[] objects, int resource) {
        return bind(objects, resource, 0);
    }

    public <T> AppCompatSpinnerPro bind(@NonNull List<T> objects, int resource, @ColorInt int color) {
        ArrayAdapterPro<T> adapterPro = new ArrayAdapterPro<>(getContext(), resource, objects);
        adapterPro.setTextColor(color);
        setAdapter(adapterPro);
        return this;
    }

    public <T> AppCompatSpinnerPro bind(@NonNull T[] objects, int resource, @ColorInt int color) {
        ArrayAdapterPro<T> adapterPro = new ArrayAdapterPro<>(getContext(), resource, objects);
        adapterPro.setTextColor(color);
        setAdapter(adapterPro);
        return this;
    }

    public <T> void setList(@NonNull List<T> objects) {
        SpinnerAdapter spinnerAdapter = getAdapter();
        if (spinnerAdapter instanceof ArrayAdapterPro) {
            ArrayAdapterPro<T> adapter = (ArrayAdapterPro<T>) getAdapter();
            adapter.setList(objects);
        }
    }

    public <T> void setList(@NonNull T[] objects) {
        SpinnerAdapter spinnerAdapter = getAdapter();
        if (spinnerAdapter instanceof ArrayAdapterPro) {
            ArrayAdapterPro<T> adapter = (ArrayAdapterPro<T>) getAdapter();
            adapter.setList(objects);
        }
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        if (listener == null) {
            setOnItemSelectedListener(null);
            return;
        }
        setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listener.onItemClick(AppCompatSpinnerPro.this, getSelectedItem(), position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(AppCompatSpinnerPro spinner, Object item, int position);
    }
}
