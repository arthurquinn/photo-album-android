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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import src.androidphotoalbum.models.Album;

public class AlbumPhotoViewActivity extends AppCompatActivity {

    private static final int PHOTO_PICKER_CODE = 1;
    private static final String logCode = "androidPhotoAlbumLog";
    private Album activeAlbum;

    private GridView gridPhotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activeAlbum = (Album) getIntent().getExtras().get("ALBUM");
        Log.i(logCode, "Launching Album Photo View Activity...");
        Log.i(logCode, String.format("Opening album: %s", activeAlbum.getName()));

        setTitle(String.format("%s Photos", activeAlbum.getName()));

        setContentView(R.layout.activity_album_photo_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridPhotoView = (GridView) findViewById(R.id.gridPhotoView);

        FloatingActionButton btnAddPhoto = (FloatingActionButton) findViewById(R.id.btnAddPhoto);
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhoto();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == PHOTO_PICKER_CODE){
                Uri imageUri = data.getData();

                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    ImageView imgView = new ImageView(this);
                    imgView.setImageBitmap(image);

                    gridPhotoView.addView(imgView);
                } catch (FileNotFoundException e) {
                    // TODO: Print error
                }
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
}
