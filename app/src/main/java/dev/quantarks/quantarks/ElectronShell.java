package dev.quantarks.quantarks;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class ElectronShell extends View {
    private static final int NUCLEUS_RADIUS = 60;

    //region Private Variables

    private int _orbitals;
    private ArrayList<ArrayList<ShapeDrawable>> _electrons;
    private Paint mTextPaint, nucleusPaint, orbitalPaint;
    private float _textWidth;
    private Rect _textBounds;

    private int atomicNumber;
    private String _elementName;
    private String _elementSymbol;
    private String _electronConf;

    private int _nucleusColor;
    private int _electronColor;
    private int _orbitalColor;

    private int viewHeight;
    private int viewWidth;

    //endregion

    public ElectronShell(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme()
                .obtainStyledAttributes(
                        attrs,
                        R.styleable.ElectronShell,
                        0, 0
                );

        try {
            atomicNumber = a.getInteger(R.styleable.ElectronShell_atomicNumber, 1);
            _elementName = a.getString(R.styleable.ElectronShell_elementName);
            _elementSymbol = a.getString(R.styleable.ElectronShell_elementSymbol);
            _electronConf = a.getString(R.styleable.ElectronShell_electronConf);

            _nucleusColor = a.getColor(R.styleable.ElectronShell_nucleusColor, Color.BLACK);
            _electronColor = a.getColor(R.styleable.ElectronShell_electronColor, Color.CYAN);
            _orbitalColor = a.getColor(R.styleable.ElectronShell_orbitalColor, Color.GRAY);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        nucleusPaint = new Paint();
        nucleusPaint.setColor(_nucleusColor);
        nucleusPaint.setAntiAlias(true);

        orbitalPaint = new Paint();
        orbitalPaint.setColor(_orbitalColor);
        orbitalPaint.setStyle(Paint.Style.STROKE);
        orbitalPaint.setAntiAlias(true);
        orbitalPaint.setStrokeWidth(3);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(0xFFCBCBCB);
        mTextPaint.setTextSize(NUCLEUS_RADIUS);
        mTextPaint.setTypeface(
                Typeface.createFromAsset(
                        this.getContext().getAssets(),
                        "fonts/OpenSans-Bold.ttf"));
        _textWidth = mTextPaint.measureText(_elementSymbol);
        _textBounds = new Rect();
        mTextPaint.getTextBounds(_elementSymbol, 0, _elementSymbol.length(), _textBounds);


        _electrons = new ArrayList<>();

        initializeElectrons();
        initializeOrbitals();
    }

    //region Utilities

    private ArrayList parseElectronConf(String json) {
        JsonArray ja = new JsonParser().parse(json).getAsJsonArray();
        ArrayList al = new ArrayList();
        int size = ja.size();

        for (int i = 0; i < size; i++) {
            al.add(ja.get(i).getAsInt());
        }

        return al;
    }

    private void initializeElectrons() {
        ArrayList<Integer> elecConf = this.getElectronConf();
        int orbitalCount = elecConf.size();

        for (int i = 0; i < orbitalCount; i++) {
            int electrons = elecConf.get(i);

            ArrayList<ShapeDrawable> al = new ArrayList();

            for (int j = 0; j < electrons; j++) {
                ShapeDrawable sd = new ShapeDrawable(new OvalShape());
                sd.getPaint().setColor(_electronColor);
                al.add(sd);
            }

            _electrons.add(al);
        }


    }

    private void initializeOrbitals() {
        int orbitalCount = this.getElectronConf().size();
        _orbitals = orbitalCount;
    }

    //endregion

    //region Getters

    public int getAtomicNumber()       { return atomicNumber; }
    public String getElementName()     { return _elementName;  }
    public ArrayList getElectronConf() { return parseElectronConf(_electronConf); }

    public int get_nucleusColor()  { return _nucleusColor;  }
    public int get_electronColor() { return _electronColor; }
    public int get_orbitalColor()  { return _orbitalColor;  }

    //endregion

    //region Setters

    public void setAtomicNumber(int newAtomicNumber) {
        atomicNumber = newAtomicNumber;
        invalidate(); // let the system know what needs to be redrawn
        requestLayout();
    }

    public void setElementName(String newElementName) {
        _elementName = newElementName;
        invalidate();
        requestLayout();
    }

    public void setElectronConf(String newElectronConf) {
        _electronConf = newElectronConf;
        invalidate();
        requestLayout();
    }

    public void set_electronColor(int _electronColor) {
        this._electronColor = _electronColor;
    }

    public void set_nucleusColor(int _nucleusColor) {
        this._nucleusColor = _nucleusColor;
    }

    public void set_orbitalColor(int _orbitalColor) {
        this._orbitalColor = _orbitalColor;
    }

    //endregion

    //region Event Handlers

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw nucleus
        canvas.drawCircle(getWidth()/2, getHeight()/2, NUCLEUS_RADIUS, nucleusPaint);

        // draw orbitals
        for (int i = 1; i <= _orbitals; i++) {
            canvas.drawCircle(getWidth()/2, getHeight()/2, (i * 20) + NUCLEUS_RADIUS, orbitalPaint);
        }

        //draw label
        canvas.drawText("Ar",
                (getWidth()/2) - (_textBounds.width()/2),
                (getHeight()/2) + (_textBounds.height()/2),
                mTextPaint);

        // draw electrons
        int i = _electrons.size();
        for (int j = 0; j < i; j++) {
            int orbitSize = ((j+1) * 20) + NUCLEUS_RADIUS;
            int electronsInThisOrbit = _electrons.get(j).size();
            ArrayList<ShapeDrawable> orbital = _electrons.get(j);

            for (int k = 0; k < electronsInThisOrbit; k++) {
                ShapeDrawable currentElectron = orbital.get(k);
                int y = ((int) getCircumferentialPoint(getHeight() / 2, orbitSize, 360 / electronsInThisOrbit)) - 5;
                int x = ((int) getCircumferentialPoint(getWidth()/2, orbitSize, 360 / electronsInThisOrbit)) - 5;
               currentElectron.setBounds(x, y, x + 10, y + 10);

                currentElectron.draw(canvas);
            }
        }
    }

    public double getCircumferentialPoint(int cy, int radius, float angle) {
        double y = (cy + radius * Math.cos(angle * Math.PI/180));

        return y;
    }


    //endregion
}
