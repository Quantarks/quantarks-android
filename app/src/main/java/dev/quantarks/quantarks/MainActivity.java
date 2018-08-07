package dev.quantarks.quantarks;

import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView txtGeneral = (TextView)findViewById(R.id.txtGeneral);
        txtGeneral.setTypeface(type);

        ImageView electronShell = (ImageView)findViewById(R.id.electronShell);
        Drawable mDrawable = electronShell.getDrawable();
        if(mDrawable instanceof Animatable) {
            ((Animatable) mDrawable).start();
        }
    }
}
