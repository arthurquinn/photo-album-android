package src.androidphotoalbum;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import src.androidphotoalbum.models.Album;
import src.androidphotoalbum.models.AlbumListWrapper;
import src.androidphotoalbum.models.Photo;
import src.androidphotoalbum.models.TagValuePair;
import src.androidphotoalbum.state.ApplicationInstance;

public class SearchPhotosActivity extends AppCompatActivity {

    private TextView txtType;
    private TextView txtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_photos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtType = (TextView)findViewById(R.id.txtSearchType);
        txtValue = (TextView)findViewById(R.id.txtSearchValue);
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
                if (txtType.getText().toString().isEmpty() || txtValue.getText().toString().isEmpty()){
                    Toast.makeText(this, "Tag type and value fields are required", Toast.LENGTH_LONG).show();
                } else {
                    runSearch(txtType.getText().toString(), txtValue.getText().toString());
                }
                return true;
            case R.id.btnCancelSearch:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<Photo> runSearch(String type, String value){
        AlbumListWrapper albumListWrapper = ApplicationInstance.getInstance().getAlbumListWrapper();

        List<Photo> searchList = new ArrayList<Photo>();

        for (Album a : albumListWrapper.getAlbumList()){
            for (Photo p : a.getPhotoList()){
                for (TagValuePair t : p.getTags())
                {
                    if (t.getKey().contains(type) || t.getValue().contains(value))
                        searchList.add(p);
                }
            }
        }
        return searchList;
    }
}
