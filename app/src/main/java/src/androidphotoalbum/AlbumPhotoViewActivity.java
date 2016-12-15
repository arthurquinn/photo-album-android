package src.androidphotoalbum;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.ApplicationTestCase;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import src.androidphotoalbum.adapters.ImageViewGridAdapter;
import src.androidphotoalbum.models.Album;
import src.androidphotoalbum.models.AlbumListWrapper;
import src.androidphotoalbum.models.Photo;
import src.androidphotoalbum.state.ApplicationInstance;

public class AlbumPhotoViewActivity extends AppCompatActivity {

    private final int MOVE_PHOTO_CODE = 2;
    private static final int PHOTO_PICKER_CODE = 1;
    private static final String logCode = "androidPhotoAlbumLog";

    private Album activeAlbum;
    private AlbumListWrapper albumListWrapper;

    private ImageViewGridAdapter photoGridAdapter;
    private GridView gridPhotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activeAlbum = ApplicationInstance.getInstance().getActiveAlbum();
        albumListWrapper = ApplicationInstance.getInstance().getAlbumListWrapper();

        setTitle(String.format("%s Photos", activeAlbum.getName()));

        setContentView(R.layout.activity_album_photo_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        photoGridAdapter = new ImageViewGridAdapter(this, activeAlbum.getPhotoList());
        photoGridAdapter.notifyDataSetChanged();

        gridPhotoView = (GridView) findViewById(R.id.gridPhotoView);
        gridPhotoView.setAdapter(photoGridAdapter);
        gridPhotoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayPhoto((Photo)photoGridAdapter.getItem(position));
            }
        });

        registerForContextMenu(gridPhotoView);

        FloatingActionButton btnAddPhoto = (FloatingActionButton) findViewById(R.id.btnAddPhoto);
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhoto();
            }
        });

        Log.i(logCode, "Album photo view loading " + activeAlbum.getPhotoList().size() + " photos");
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.gridPhotoView){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

            getMenuInflater().inflate(R.menu.context_menu_album_photo_view, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Photo p;
        switch (item.getItemId()){
            case R.id.mnuRemovePhoto:
                p = (Photo) photoGridAdapter.getItem(info.position);
                activeAlbum.removePhoto(p);
                photoGridAdapter.notifyDataSetChanged();
                return true;
            case R.id.mnuMovePhoto:
                p = (Photo) photoGridAdapter.getItem(info.position);
                Intent movePhotoIntent = new Intent(this, MovePhotoActivity.class);
                movePhotoIntent.putExtra("EXCLUDE_ALBUM", activeAlbum);
                ApplicationInstance.getInstance().setActivePhoto(p);
                startActivityForResult(movePhotoIntent, MOVE_PHOTO_CODE);
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == PHOTO_PICKER_CODE){
                Uri imageUri = data.getData();
                this.grantUriPermission(this.getPackageName(), imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                activeAlbum.addPhoto(new Photo(imageUri.toString()));
                photoGridAdapter.notifyDataSetChanged();
            }
            else if (requestCode == MOVE_PHOTO_CODE){
                Photo p = ApplicationInstance.getInstance().getActivePhoto();
                Album moveToAlbum = (Album)data.getExtras().get("MOVE_TO_ALBUM");
                albumListWrapper.movePhotoToAlbum(p, activeAlbum, moveToAlbum);
                photoGridAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addPhoto(){
        // Create intent to pick photo form device
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        // Get picture directory of file system
        File pictureDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Uri picData = Uri.parse(pictureDir.getPath());

        // Start the activity
        photoPickerIntent.setDataAndType(picData, "image/*");
        startActivityForResult(photoPickerIntent, PHOTO_PICKER_CODE);

    }

    private void displayPhoto(Photo photo) {
        Intent displayPhotoIntent = new Intent(getBaseContext(), PhotoDisplayActivity.class);
        ApplicationInstance.getInstance().setActiveAlbum(activeAlbum);
        ApplicationInstance.getInstance().setActivePhoto(photo);
        startActivity(displayPhotoIntent);
    }
}
