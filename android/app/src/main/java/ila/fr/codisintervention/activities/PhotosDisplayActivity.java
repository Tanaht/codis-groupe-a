package ila.fr.codisintervention.activities;

import android.app.ProgressDialog;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.models.model.Photo;
import ila.fr.codisintervention.services.ModelServiceAware;
import ila.fr.codisintervention.utils.Config;

/**
 * The type Photos display activity.
 */
public class PhotosDisplayActivity extends AppCompatActivity implements ModelServiceAware {

    protected static final String TAG = "PhotosDisplayActivity";

    private ServiceConnection modelServiceConnection;

    private ModelServiceBinder.IMyServiceMethod modelService;

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
        modelServiceConnection = bindModelService();
        setContentView(R.layout.activity_photos_display);
        setTitle(R.string.title_photos_display);
    }


    private void loadList() {
        Integer currentIntervention = modelService.getCurrentIntervention().getId();
        List<Photo> photos = modelService.getPhotos();
        for (Photo photo : photos) {
            if (photo.getInterventionId() == currentIntervention) {
                String uri = "http://" + Config.get().getHost() + ":" + Config.get().getPort() + photo.getUri();
                urlList.add(uri);
            }
        }
        Collections.sort(urlList);
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
        if (!urlList.isEmpty()) {
            url = urlList.get(indexImage);
            String imageUrl = new String(urlList.get(indexImage)).replace(".png","");
            String[] urlPart = imageUrl.split("/");
            String[] imageNamePart = urlPart[urlPart.length-1].split("_");
            String point = imageNamePart[1];
            Date date = new Date(Long.parseLong(imageNamePart[2])*1000);
            SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
            TextView imageInfo = (TextView) findViewById(R.id.text);
            imageInfo.setText("Point " + point + " at " + ft.format(date));
            new LoadImage().execute();
        }
    }

    @Override
    public void onModelServiceConnected() {
        loadList();
        addArrowsListeners();

        imageDrone();
    }

    @Override
    public void setModelService(ModelServiceBinder.IMyServiceMethod modelService) {
        this.modelService = modelService;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindModelService(modelServiceConnection);

    }
}

