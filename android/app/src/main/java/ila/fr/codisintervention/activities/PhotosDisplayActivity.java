package ila.fr.codisintervention.activities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.R;

import static ila.fr.codisintervention.R.drawable.area;


/**
 * The type Photos display activity.
 */
public class PhotosDisplayActivity extends AppCompatActivity {
    protected static final String TAG = "PhotosDisplayActivity";

    private List<String> urlList = new ArrayList<>();
    private int indexImage = 0;
    /**
     * The Progress.
     */
    ProgressDialog progress;
    /**
     * The Img.
     */
    ImageView img;
    /**
     * The Url.
     */
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_display);
        setTitle(R.string.title_photos_display);

        loadList();
        addArrowsListeners();

        imageDrone();
        comments();
    }


    private void loadList() {
        urlList.add("https://zonevideo.telequebec.tv/visuels_containers/1280x720/sam_le_pompier.jpg");
        urlList.add("http://aws.vdkimg.com/tv_show/6/1/2/6/61265_backdrop_scale_1280xauto.jpg");
        urlList.add("http://i.f1g.fr/media/ext/1900x1900/madame.lefigaro.fr/sites/default/files/img/2017/09/pourquoi-le-fantasme-du-pompier-est-aussi-intemporel-.jpg");
        urlList.add("http://www.le-deguisement.fr/image-deguisement/2014/01/deguisement-pompier-sexy-femme.jpg");
        urlList.add("https://www.aurorecinema.fr/evenement/03_sam-le-pompier-2018.jpg");
    }

    private void addArrowsListeners() {

        ImageView previousArrow = (ImageView) findViewById(R.id.previous);
        previousArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexImage == 0) {
                    indexImage = urlList.size() - 1;
                } else {
                    indexImage--;
                }

                imageDrone();

            }
        });

        ImageView nextArrow = (ImageView) findViewById(R.id.next);
        nextArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexImage == urlList.size() - 1) {
                    indexImage = 0;
                } else {
                    indexImage++;
                }
                imageDrone();

            }
        });

    }

    /**
     *
     */
    private void imageDrone() {
        url = urlList.get(indexImage);
        new LoadImage().execute();
    }

    private void comments(){
        EditText azerty = (EditText) findViewById(R.id.text);
        azerty.setText(url);
    }



    private class LoadImage extends AsyncTask <Void,Void,Bitmap> {
        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap=null;

            try {
                bitmap= BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
            } catch (Exception e) {
                Log.w(TAG, "Impossible to load the image file");
            }

            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            //show progress dialog while image is loading
            progress=new ProgressDialog(PhotosDisplayActivity.this);
            progress.setMessage("Loading Image....");
            progress.show();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            img = (ImageView) findViewById(R.id.imageDrone);
            if(bitmap!=null) {
                img.setImageBitmap(bitmap);
                progress.dismiss();
            } else {
                progress.dismiss();
                Toast.makeText(PhotosDisplayActivity.this,"Some error occurred!",Toast.LENGTH_LONG).show();
            }
        }
    }
}

