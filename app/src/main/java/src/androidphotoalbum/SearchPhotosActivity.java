package src.androidphotoalbum;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import src.androidphotoalbum.adapters.ImageViewGridAdapter;
import src.androidphotoalbum.models.Album;
import src.androidphotoalbum.models.AlbumListWrapper;
import src.androidphotoalbum.models.Photo;
import src.androidphotoalbum.models.TagValuePair;
import src.androidphotoalbum.state.ApplicationInstance;

public class SearchPhotosActivity extends AppCompatActivity {

    private static final String logCode = "androidPhotoAlbumLog";

    private List<Photo> searchResults;
    private GridView searchResultsGrid;
    private ImageViewGridAdapter searchResultsAdapter;

    private Spinner spnType;
    private TextView txtValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_photos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spnType = (Spinner)findViewById(R.id.spnSearchType);
        txtValue = (TextView)findViewById(R.id.txtSearchValue);

        searchResultsGrid = (GridView)findViewById(R.id.gridViewSearchResults);
        searchResults = new ArrayList<Photo>();
        searchResultsAdapter = new ImageViewGridAdapter(this, searchResults);
        searchResultsGrid.setAdapter(searchResultsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_photos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnRunSearch:
                if (spnType.getSelectedItem().toString().isEmpty() || txtValue.getText().toString().isEmpty()){
                    Toast.makeText(this, "Tag type and value fields are required", Toast.LENGTH_LONG).show();
                } else {
                    Log.i(logCode, "Running search... tag type = " + spnType.getSelectedItem().toString());
                    runSearch(spnType.getSelectedItem().toString(), txtValue.getText().toString());
                    searchResultsAdapter.notifyDataSetChanged();
                }
                return true;
            default:
                Log.i(logCode, "Shouldn't go here");
                return super.onOptionsItemSelected(item);
        }
    }

    private void runSearch(String type, String value){
        AlbumListWrapper albumListWrapper = ApplicationInstance.getInstance().getAlbumListWrapper();

        searchResults.clear();
        for (Album a : albumListWrapper.getAlbumList()){
            for (Photo p : a.getPhotoList()){
                for (TagValuePair t : p.getTags())
                {
                    Log.i(logCode, "Discovered type: " + t.getKey());
                    Log.i(logCode, "Discovered value: " + t.getValue());
                    if (t.getKey().contains(type) && t.getValue().contains(value)){
                        searchResults.add(p);
                        Log.i(logCode, "Found photo... " + p.toString());
                    }
                }
            }
        }
    }
}
