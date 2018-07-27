package dev.quantarks.quantarks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

public class SampleCustomShape extends View {
    private ShapeDrawable mDrawable;
    private int _height, _width, _x, _y;

    public SampleCustomShape(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.getPaint()
                .setColor(0xff74AC23);

    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        _width = xNew;
        _height = yNew;

        _x = 0;
        _y = 0;
    }

    protected void onDraw(Canvas canvas){
        mDrawable.setBounds(_x, _y, _x + _width, _y + _height);
        mDrawable.draw(canvas);
    }
}
