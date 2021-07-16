package me.coffee.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 带清除按钮的EditText
 *
 * @author kongfei
 */
public class CleanableEditText extends android.support.v7.widget.AppCompatEditText {

    private Drawable cleanButtonDrawable;

    public CleanableEditText(Context context) {
        super(context);
        init(context, null);
    }

    public CleanableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CleanableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int cleanButtonResId = -1;
        int cleanButtonTintColor = -1;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CleanableEditText);
            cleanButtonResId = typedArray.getResourceId(R.styleable.CleanableEditText_cleanButtonDrawable, -1);
            cleanButtonTintColor = typedArray.getColor(R.styleable.CleanableEditText_cleanButtonTint, -1);
            typedArray.recycle();
        }
        if (cleanButtonResId == -1) cleanButtonResId = R.drawable.ic_cancel_black_24dp;
        if (cleanButtonTintColor == -1) cleanButtonTintColor = getCurrentHintTextColor();

        final Drawable drawable = ContextCompat.getDrawable(context, cleanButtonResId);
        cleanButtonDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(cleanButtonDrawable, cleanButtonTintColor);
        cleanButtonDrawable.setBounds(0, 0, cleanButtonDrawable.getIntrinsicHeight(), cleanButtonDrawable.getIntrinsicHeight());
        setCleanButtonVisible(false);

        setOnTouchListener(null);
        setOnFocusChangeListener(null);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setCleanButtonVisible(hasFocus() && s.length() > 0);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener((v, event) -> {
            final int x = (int) event.getX();
            if (cleanButtonDrawable.isVisible() && x > getWidth() - getPaddingRight() - cleanButtonDrawable.getIntrinsicWidth()) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    setError(null);
                    setText("");
                }
                return true;
            }
            return l != null && l.onTouch(v, event);
        });
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        super.setOnFocusChangeListener((v, hasFocus) -> {
            setCleanButtonVisible(hasFocus && getText().length() > 0);
            if (l != null) l.onFocusChange(v, hasFocus);
        });
    }

    public void setCleanButtonVisible(boolean visible) {
        cleanButtonDrawable.setVisible(visible, false);
        final Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(
                compoundDrawables[0],
                compoundDrawables[1],
                visible ? cleanButtonDrawable : null,
                compoundDrawables[3]);
    }
}
