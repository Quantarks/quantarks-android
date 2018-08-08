package dev.quantarks.quantarks;

import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dev.quantarks.util.ChemicalElements;

public class MainActivity extends AppCompatActivity {

    TextView txtElectronConf, txtElementName, txtGeneral;
    ImageView electronShell;
    PreviousElementPager prevElemBtn;
    NextElementPager nextElemBtn;

    private ChemicalElements ce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.hide();

        init();

        getAtomDrawable(1);
    }

    private Drawable getAtomDrawable(int atomicNumber) {
        int resId = getResources().getIdentifier("atom_" + atomicNumber, "drawable", getPackageName());

        return getResources().getDrawable(resId, null);
    }

    private void init() {
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Bold.ttf");
        txtGeneral = findViewById(R.id.txtGeneral);
        txtElementName = findViewById(R.id.element_name);
        txtElectronConf = findViewById(R.id.electron_conf);
        txtGeneral.setTypeface(type);
        txtElementName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf"));
        txtElectronConf.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf"));
        electronShell = findViewById(R.id.electronShell);

        prevElemBtn = findViewById(R.id.button_prev_element);
        nextElemBtn = findViewById(R.id.button_next_element);

        prevElemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousElement();
            }
        });

        nextElemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextElement();
            }
        });

        ce = ChemicalElements.findByNumber(1, getApplicationContext());

        updateEverything();
    }

    private void updateElectronShellAnimation() {
        Drawable d = getAtomDrawable(ce.getAtomicNumber());

        electronShell.setImageDrawable(d);

        Drawable mDrawable = electronShell.getDrawable();
        if(mDrawable instanceof Animatable) {
            ((Animatable) mDrawable).start();
        }
    }

    private void updateEverything() {
        ChemicalElements prev = this.getPreviousElement(ce);
        ChemicalElements next = this.getNextElement(ce);

        txtElementName.setText(ce.getName());

        updateElectronShellAnimation();

        //update prev element button
        prevElemBtn.setTextTop(prev.getAtomicNumber() + "");
        prevElemBtn.setTextBottom(prev.getName());
        prevElemBtn.setTextMiddle(prev.getSymbol());

        //update next element button
        nextElemBtn.setTextTop(next.getAtomicNumber() + "");
        nextElemBtn.setTextMiddle(next.getSymbol());
        nextElemBtn.setTextBottom(next.getName());
    }

    private void nextElement() {
        this.ce = this.getNextElement(this.ce);
        updateEverything();
    }

    private void previousElement() {
        this.ce = this.getPreviousElement(this.ce);
        updateEverything();
    }

    private ChemicalElements getNextElement(ChemicalElements ce1) {
        int thisElementNumber = ce1.getAtomicNumber();
        int nextElementNumber = thisElementNumber + 1;
        ChemicalElements c;

        if (nextElementNumber <= ChemicalElements.MAX_ELEMENT_NUMBER)
            c = ChemicalElements.findByNumber(nextElementNumber, getApplicationContext());
        else
            c = ChemicalElements.findByNumber(1, getApplicationContext());

        return c;
    }

    private ChemicalElements getPreviousElement(ChemicalElements ce1) {
        int thisElementNumber = ce1.getAtomicNumber();
        int previousElementNumber = thisElementNumber - 1;
        ChemicalElements c;


        if (previousElementNumber >= 1)
            c = ChemicalElements.findByNumber(previousElementNumber, getApplicationContext());

        else
            c = ChemicalElements.findByNumber(ChemicalElements.MAX_ELEMENT_NUMBER, getApplicationContext());

        return c;
    }
}
