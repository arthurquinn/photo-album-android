package src.androidphotoalbum;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import src.androidphotoalbum.models.Album;
import src.androidphotoalbum.models.AlbumListWrapper;

public class MovePhotoActivity extends AppCompatActivity {

    private ListView lstAlbums;
    private AlbumListWrapper albumList;
    private ArrayAdapter<Album> albumListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
}
