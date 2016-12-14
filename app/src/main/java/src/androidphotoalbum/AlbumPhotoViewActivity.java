package src.androidphotoalbum;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import android.util.Log;

import src.androidphotoalbum.models.Album;

public class AlbumPhotoViewActivity extends AppCompatActivity {

    private static final String logCode = "androidPhotoAlbumLog";
    private Album activeAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activeAlbum = (Album) getIntent().getExtras().get("ALBUM");
        Log.i(logCode, "Launching Album Photo View Activity...");
        Log.i(logCode, String.format("Opening album: %s", activeAlbum.getName()));

        setContentView(R.layout.activity_album_photo_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton btnAddPhoto = (FloatingActionButton) findViewById(R.id.btnAddPhoto);
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhoto();
            }
        });
    }

    private void addPhoto(){




    }
}
