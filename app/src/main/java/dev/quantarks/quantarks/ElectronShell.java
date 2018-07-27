package dev.quantarks.quantarks;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class ElectronShell extends View {

    //region Private Variables

    private ArrayList<GradientDrawable> _orbitals;
    private ArrayList<ShapeDrawable> _electrons;
    private ShapeDrawable _nucleus;
    private  Paint mTextPaint;

    private int atomicNumber;
    private String _elementName;
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
        JsonArray elec = this.getElectronConf();
        int atomicNumber = this.getAtomicNumber();
        int widthCenter = getCenterWidth(30);
        int heightCenter = getCenterHeight(30);

        _electrons = new ArrayList<>();
        _orbitals = new ArrayList<>();

        initializeElectrons();
        initializeOrbitals();

        _nucleus = new ShapeDrawable(new OvalShape());
        _nucleus.getPaint().setColor(_nucleusColor);
        _nucleus.setBounds(heightCenter, widthCenter, 30, 30);

        this.getClipBounds();

        //TODO: Fix nuclei and orbitals to this View's center

        Log.wtf("QUANTARKS", "width: " + viewWidth);
        Log.wtf("QUANTARKS", "height: " + viewHeight);
        Log.wtf("QUANTARKS", "sWidth: " + 30);

    }

    //region Utilities

    private JsonArray parseElectronConf(String json) {
        return new JsonParser().parse(json).getAsJsonArray();
    }

    private void initializeElectrons() {
        for (int i = 0; i < atomicNumber; i++) {
            ShapeDrawable sd = new ShapeDrawable(new OvalShape());
            sd.getPaint().setColor(_electronColor);
            sd.setBounds(0, 0, 20, 20);

            _electrons.add(sd);
        }
    }

    private void initializeOrbitals() {
        int orbitalCount = this.getElectronConf().size();
        int orbitalRadiusGrowingFactor = 30;

        for (int i = 0; i < orbitalCount; i++) {
            GradientDrawable gd = new GradientDrawable();
            int orbitalRadius = (i + 1) * orbitalRadiusGrowingFactor;
            int centerX = getCenterWidth(orbitalRadius);
            int centerY = getCenterHeight(orbitalRadius);

            gd.setColor(0x00FFFFFF); // set fill to transparent
            gd.setCornerRadius(orbitalRadius);
            gd.setStroke(3, _orbitalColor);
            gd.setBounds(centerX, centerY,  orbitalRadius, orbitalRadius);

            _orbitals.add(gd);
        }
    }

    private int getCenterWidth(int shapeWidth) {
        //return (viewWidth) - (shapeWidth / 2);
        return 0;
    }

    private int getCenterHeight(int shapeHeight) {
        //return (viewHeight) - (shapeHeight / 2);
        return 0;
    }

    //endregion

    //region Getters

    public int getAtomicNumber()       { return atomicNumber; }
    public String getElementName()     { return _elementName;  }
    public JsonArray getElectronConf() { return parseElectronConf(_electronConf); }

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

        int len = _electrons.size();
        for (int i = 0; i < len; i++) {
            _electrons.get(i).draw(canvas);
        }

        len = _orbitals.size();
        for (int i = 0; i < len; i++) {
            _orbitals.get(i).draw(canvas);
        }

        _nucleus.draw(canvas);
    }


    //endregion
}
