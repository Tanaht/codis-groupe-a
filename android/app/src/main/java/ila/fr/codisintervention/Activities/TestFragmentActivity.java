package ila.fr.codisintervention.Activities;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import ila.fr.codisintervention.Entities.SymboleDispo;
import ila.fr.codisintervention.Fragments.DessinFragment;
import ila.fr.codisintervention.Fragments.ListeSymbolesFragment;
import ila.fr.codisintervention.R;

public class TestFragmentActivity  extends FragmentActivity implements ListeSymbolesFragment.OnFragmentInteractionListener,
                                                                        DessinFragment.OnFragmentInteractionListener ,
                                                                        View.OnTouchListener   {

    private SymboleDispo symboleDispo = new SymboleDispo();

    String couleur = "";
    ImageView ressourceEau;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_test_fragment);

    }

    public void imageClick(View view){
        ressourceEau = (ImageView) view.findViewById(R.id.ressource_eau);
        ressourceEau.setImageResource(R.drawable.ressource_eau_glow);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        int x = (int)event.getX();
//        int y = (int)event.getY();
//        Toast.makeText(this, "x"+x+"y"+y, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int)event.getX();
        int y = (int)event.getY();
        Toast.makeText(this, "x"+x+"y"+y, Toast.LENGTH_SHORT).show();
        return true;
    }

    public void clicRouge(View view){
        couleur = "rouge";
        Toast.makeText(this, "rouge", Toast.LENGTH_SHORT).show();
    }

    public void clicVert(View view){
        couleur = "vert";
        Toast.makeText(this, "vert", Toast.LENGTH_SHORT).show();
    }

    public void clicBleu(View view){
        couleur = "bleu";
        Toast.makeText(this, "bleu", Toast.LENGTH_SHORT).show();
    }

    public void clicOrange(View view){
        couleur = "orange";
        Toast.makeText(this, "orange", Toast.LENGTH_SHORT).show();
    }

    public void clicViolet(View view){
        couleur = "violet";
        Toast.makeText(this, "violet", Toast.LENGTH_SHORT).show();
    }

}
