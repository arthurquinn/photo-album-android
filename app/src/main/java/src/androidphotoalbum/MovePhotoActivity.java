package src.androidphotoalbum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import src.androidphotoalbum.models.Album;
import src.androidphotoalbum.models.Photo;
import src.androidphotoalbum.state.ApplicationInstance;

public class MovePhotoActivity extends AppCompatActivity {

    private ListView lstAlbumsMove;
    private List<Album> availableAlbums;
    private ArrayAdapter<Album> availableAlbumsAdapter;

    private Photo p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_photo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstAlbumsMove = (ListView) findViewById(R.id.lstAlbumsMove);

        List<Album> allAlbums = ApplicationInstance.getInstance().getAlbumListWrapper().getAlbumList();
        Album excludeAlbum = (Album)getIntent().getExtras().get("EXCLUDE_ALBUM");
        availableAlbums = new ArrayList<Album>(allAlbums);
        availableAlbums.remove(excludeAlbum);

        availableAlbumsAdapter = new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, availableAlbums);
        lstAlbumsMove.setAdapter(availableAlbumsAdapter);

        // Set up image view
        p = ApplicationInstance.getInstance().getActivePhoto();
        ImageView imgView = (ImageView)findViewById(R.id.imgViewMovePhoto);
        Bitmap image = p.loadBitmap(this);
        imgView.setImageBitmap(image);
        imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgView.setPadding(8, 8, 8, 8);

        lstAlbumsMove.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Album a = (Album)availableAlbumsAdapter.getItem(position);
                Intent moveToIntent = new Intent(getBaseContext(), AlbumPhotoViewActivity.class);
                moveToIntent.putExtra("MOVE_TO_ALBUM_NAME", a.getName());
                setResult(RESULT_OK, moveToIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_move_photo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.mnuCancel:
                setResult(RESULT_CANCELED);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
