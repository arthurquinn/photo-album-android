package src.androidphotoalbum;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

import src.androidphotoalbum.models.Album;
import src.androidphotoalbum.models.Photo;

public class PhotoDisplayActivity extends AppCompatActivity {
private final String logCode = "androidPhotoAlbumLog";

    private Album activeAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_display);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the active album/photo
        activeAlbum = (Album) getIntent().getExtras().get("ACTIVE_ALBUM");
        Photo photo = (Photo) getIntent().getExtras().get("ACTIVE_PHOTO");

        try {
            // Set up image view
            ImageView imgView = (ImageView) findViewById(R.id.imgViewPhotoDisplay);
            InputStream inputStream = getContentResolver().openInputStream(photo.getUri());
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            imgView.setImageBitmap(image);
            imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgView.setPadding(8, 8, 8, 8);

        } catch (FileNotFoundException e) {
            Log.i(logCode, e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_display, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
