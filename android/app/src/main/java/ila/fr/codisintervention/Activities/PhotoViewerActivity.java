package ila.fr.codisintervention.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.net.URL;
import java.util.ArrayList;

import ila.fr.codisintervention.Activities.PhotoUtils.PhotoAdapter;
import ila.fr.codisintervention.R;

public class PhotoViewerActivity extends AppCompatActivity {

    private GridView imageGrid;
    private ArrayList<Bitmap> bitmapList;
    private ArrayList<URL> urlList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);

        this.imageGrid = (GridView) findViewById(R.id.gridview);
        this.bitmapList = new ArrayList<Bitmap>();
        this.urlList = new ArrayList<URL>();

        try {
            for(int i = 0; i < 10; i++) {
                //TODO A changer par l'url du serveur
                this.bitmapList.add(urlImageToBitmap("http://placehold.it/150x150"));
                URL url = new URL("http://placehold.it/150x150");
                this.urlList.add(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.imageGrid.setAdapter(new PhotoAdapter(this, this.bitmapList));

        imageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent( PhotoViewerActivity.this, FullPhotoViewer.class);
                intent.putExtra("urlPhoto", urlList.get(position-1));
                startActivity(intent);

            }
        });
    }

    public static Bitmap urlImageToBitmap(String imageUrl) throws Exception {
        Bitmap result = null;
        URL url = new URL(imageUrl);
        if(url != null) {
            result = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        return result;
    }

}

