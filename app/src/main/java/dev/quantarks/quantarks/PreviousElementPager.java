package dev.quantarks.quantarks;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * A View that represents either a "Next" or "Previous" element button.
 */
public class PreviousElementPager extends ConstraintLayout {

    private TextView textTop;
    private TextView textMiddle;
    private TextView textBottom;

    private String strTextTop;
    private String strTextMiddle;
    private String strTextBottom;

    private int pagerType;
    private int textColor;

    private Typeface font;
    private int _font;

    public PreviousElementPager(Context context) {
        this(context, null);
    }

    public PreviousElementPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_previous_element_pager, this);

        this.textTop = view.findViewById(R.id.text_top);
        this.textMiddle = view.findViewById(R.id.text_middle);
        this.textBottom = view.findViewById(R.id.text_bottom);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PreviousElementPager, 0, 0);
        try {
            strTextTop = array.getString(R.styleable.PreviousElementPager_textTop);
            strTextMiddle = array.getString(R.styleable.PreviousElementPager_textMiddle);
            strTextBottom= array.getString(R.styleable.PreviousElementPager_textBottom);
            pagerType = array.getInt(R.styleable.PreviousElementPager_pagerType, 0);
            textColor = array.getColor(R.styleable.PreviousElementPager_textColor, 0xFF333333);
            _font = array.getColor(R.styleable.PreviousElementPager_textFontFamily, 2);
        } finally {
            array.recycle();
        }

        textTop.setText(strTextTop);
        textMiddle.setText(strTextMiddle);
        textBottom.setText(strTextBottom);

        invalidateFont();
        textTop.setTypeface(font);
        textMiddle.setTypeface(font);
        textBottom.setTypeface(font);
    }

    public String getTextTop()    { return strTextTop;    }
    public String getTextMiddle() { return strTextMiddle; }
    public String getTextBottom() { return strTextBottom; }
    public int getPagerType()     { return pagerType;     }
    public int getTextColor()     { return textColor;     }


    public void setTextTop(String text) {
        this.strTextTop = text;
        textTop.setText(strTextTop);
        invalidate();
        requestLayout();
    }

    public void setTextMiddle(String text) {
        this.strTextMiddle = text;
        textMiddle.setText(strTextMiddle);
        invalidate();
        requestLayout();
    }

    public void setTextBottom(String text) {
        this.strTextBottom = text;
        textBottom.setText(strTextBottom);
        invalidate();
        requestLayout();
    }

    public void setPagerType(int type) {
        this.pagerType = type;
        invalidate();
        requestLayout();
    }

    public void setTextColor(int color) {
        this.textColor = color;
        textTop.setTextColor(color);
        textMiddle.setTextColor(color);
        textBottom.setTextColor(color);
        invalidate();
        requestLayout();
    }

    public void setTextFontFamily(int _textFontFamily) {
        _font = _textFontFamily;
        invalidateFont();
        invalidate();
        requestLayout();
    }


    private void refreshView() {
        ConstraintSet cs = new ConstraintSet();

        cs.clone(this);

        if(pagerType == 0) {
            cs.connect(textTop.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        } else {
            cs.connect(textTop.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        }
    }

    private void invalidateFont() {
        switch(_font) {
            case 0:
                font = Typeface.createFromAsset(this.getContext().getAssets(), "Lato-Hairline.ttf");
            case 1:
                font = Typeface.createFromAsset(this.getContext().getAssets(), "Lato-Light.ttf");
            case 2:
                font = Typeface.createFromAsset(this.getContext().getAssets(), "Lato-Regular.ttf");
            case 3:
                font = Typeface.createFromAsset(this.getContext().getAssets(), "Lato-Bold.ttf");
            case 4:
                font = Typeface.createFromAsset(this.getContext().getAssets(), "Lato-Black.ttf");
            default:
                font = Typeface.createFromAsset(this.getContext().getAssets(), "Lato-Regular.ttf");}
    }

    public static final int LATO_HAIRLINE = 0;
    public static final int LATO_LIGHT = 1;
    public static final int LATO_REGULAR = 2;
    public static final int LATO_BOLD = 3;
    public static final int LATO_BLACK = 4;
}
