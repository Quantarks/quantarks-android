package dev.quantarks.quantarks;

import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import dev.quantarks.util.ChemicalElements;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.hide();

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Bold.ttf");
        TextView txtGeneral = findViewById(R.id.txtGeneral);
        txtGeneral.setTypeface(type);

        ImageView electronShell = findViewById(R.id.electronShell);
        Drawable mDrawable = electronShell.getDrawable();
        if(mDrawable instanceof Animatable) {
            ((Animatable) mDrawable).start();
        }

        ChemicalElements ce = ChemicalElements.findByName("Hett", this.getApplicationContext());
        Log.d("CHEMICALS", "onCreate: " + ce);
    }
}
