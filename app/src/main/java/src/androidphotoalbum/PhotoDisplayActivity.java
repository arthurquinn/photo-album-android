package src.androidphotoalbum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import src.androidphotoalbum.models.Album;
import src.androidphotoalbum.models.Photo;
import src.androidphotoalbum.models.TagValuePair;
import src.androidphotoalbum.state.ApplicationInstance;

public class PhotoDisplayActivity extends AppCompatActivity {

    private final String logCode = "androidPhotoAlbumLog";

    private static final int MANAGE_TAGS_CODE = 1;
    private Album activeAlbum;

    private LinearLayout tagLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_display);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tagLayout = (LinearLayout)findViewById(R.id.tagsLinearLayout);

        // Get the active album/photo
        activeAlbum = ApplicationInstance.getInstance().getActiveAlbum();
        Photo photo = ApplicationInstance.getInstance().getActivePhoto();

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

        loadTags();
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
            case R.id.mnuManageTags:
                Intent manageTagsIntent = new Intent(getBaseContext(), ManageTagsActivity.class);
                startActivityForResult(manageTagsIntent, MANAGE_TAGS_CODE);
                return true;
            case R.id.mnuSlideshow:
                Intent slideshowIntent = new Intent(getBaseContext(), SlideshowActivity.class);
                startActivity(slideshowIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MANAGE_TAGS_CODE){
            // Refresh after any result code
            loadTags();
        }
    }

    private void loadTags(){
        // Load tags for photo
        tagLayout.removeAllViews();
        for (TagValuePair tag : ApplicationInstance.getInstance().getActivePhoto().getTags()){
            TextView tagView = new TextView(getBaseContext());
            tagView.setText(tag.toString());
            tagLayout.addView(tagView);
        }
    }
}
