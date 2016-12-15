package src.androidphotoalbum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import src.androidphotoalbum.models.AlbumListWrapper;
import src.androidphotoalbum.state.ApplicationInstance;

public class AddEditAlbumActivity extends AppCompatActivity {

    private EditText txtAlbumName;

    private AlbumListWrapper albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_album);

        albumList = ApplicationInstance.getInstance().getAlbumListWrapper();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtAlbumName = (EditText) findViewById(R.id.txtAlbumName);

        setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_album_add_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.mnuSave:
                if (!txtAlbumName.getText().toString().isEmpty())
                {
                    if (albumList.checkDuplicate(txtAlbumName.getText().toString()))
                    {
                        Toast.makeText(this,"This album name already exists.",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        String albumName = txtAlbumName.getText().toString();
                        getIntent().putExtra("ALBUM_NAME", albumName);
                        setResult(RESULT_OK, getIntent());
                        finish();
                    }
                }
                else
                {
                    Toast.makeText(this,"Album field name is required.",Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.mnuCancel:
                setResult(RESULT_CANCELED);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
