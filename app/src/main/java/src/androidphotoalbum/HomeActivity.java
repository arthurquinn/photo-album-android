package src.androidphotoalbum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import src.androidphotoalbum.models.AlbumListWrapper;
import src.androidphotoalbum.models.Album;
import src.androidphotoalbum.models.Photo;
import src.androidphotoalbum.state.ApplicationInstance;

import android.util.Log;
import android.widget.Toast;

import java.io.File;


public class HomeActivity extends AppCompatActivity {

    private static final String logCode = "androidPhotoAlbumLog";

    private static final int ADD_ALBUM_CODE = 1;
    private static final int EDIT_ALBUM_CODE = 2;

    private ListView lstAlbums;
    private AlbumListWrapper albumList;
    private ArrayAdapter<Album> albumListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstAlbums = (ListView) findViewById(R.id.lstAlbums);

        File saveFile = new File(getFilesDir(), "usr.dat");
        if (!saveFile.exists()){
            Log.i(logCode, "Instantiating file...");
            ApplicationInstance.instantiate(this);
        } else {
            Log.i(logCode, "User data found...");
        }
        ApplicationInstance.getInstance().load(this);


        albumList = ApplicationInstance.getInstance().getAlbumListWrapper();

        for (Album a : albumList.getAlbumList()){
            Log.i(logCode, "Home Screen received: " + a.getName());
        }

        albumListAdapter = new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, albumList.getAlbumList());

        lstAlbums.setAdapter(albumListAdapter);

        // Register album list view for context menu
        registerForContextMenu(lstAlbums);


        FloatingActionButton btnCreate = (FloatingActionButton) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlbum();
            }
        });



        lstAlbums.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Album album = albumListAdapter.getItem(position);
                Intent albumPhotoViewIntent = new Intent(getBaseContext(), AlbumPhotoViewActivity.class);
                Log.i(logCode, "Sending " + album.getName() + "...");
                ApplicationInstance.getInstance().setActiveAlbum(album);
                startActivity(albumPhotoViewIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.btnRunSearch :
                Intent searchPhotosIntent = new Intent(getBaseContext(), SearchPhotosActivity.class);
                startActivity(searchPhotosIntent);
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // If view is album list view then inflate the album context menu
        if (v.getId() == R.id.lstAlbums) {
            AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
            // menu.setHeaderTitle(albumListAdapter.getItem(info.position).toString());
            getMenuInflater().inflate(R.menu.context_menu_album_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

        // Actions on context menu item selection
        switch(item.getItemId()) {
            case R.id.mnuRename:
                Album editAlbum = albumListAdapter.getItem(info.position);
                Intent renameIntent = new Intent(this, AddEditAlbumActivity.class);
                ApplicationInstance.getInstance().setActiveAlbum(editAlbum);
                startActivityForResult(renameIntent, EDIT_ALBUM_CODE);
                return true;
            case R.id.mnuDelete:
                albumList.removeAlbum(info.position);
                albumListAdapter.notifyDataSetChanged();
                ApplicationInstance.getInstance().save(this);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == EDIT_ALBUM_CODE) {
                String newName = data.getStringExtra("ALBUM_NAME");
                String oldName = ((Album)data.getExtras().get("ALBUM")).getName();

                albumList.editAlbum(oldName, newName);
                albumListAdapter.notifyDataSetChanged();
                ApplicationInstance.getInstance().save(this);
            }
            else if (requestCode == ADD_ALBUM_CODE) {
                String newName = data.getStringExtra("ALBUM_NAME");

                albumList.addAlbum(new Album(newName));
                albumListAdapter.notifyDataSetChanged();
                ApplicationInstance.getInstance().save(this);
            }
        }
    }

    private void createAlbum() {
        Intent intent = new Intent(this, AddEditAlbumActivity.class);
        intent.putExtra("ALBUM_LIST", albumList);
        startActivityForResult(intent, ADD_ALBUM_CODE);
    }


}
