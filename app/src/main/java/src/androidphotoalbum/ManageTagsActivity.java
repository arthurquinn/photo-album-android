package src.androidphotoalbum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import src.androidphotoalbum.R;
import src.androidphotoalbum.models.Photo;
import src.androidphotoalbum.models.TagValuePair;
import src.androidphotoalbum.state.ApplicationInstance;

public class ManageTagsActivity extends AppCompatActivity {

    private static final int CREATE_TAG_CODE = 1;
    private static final String logCode = "androidPhotoAlbumLog";
    private Photo activePhoto;

    private ImageView imgViewManageTags;
    private ArrayAdapter<TagValuePair> lstTagAdapter;
    private ListView lstTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_tags);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up tag list view
        activePhoto = ApplicationInstance.getInstance().getActivePhoto();
        lstTags = (ListView)findViewById(R.id.lstTags);

        // Set up tag adapter
        lstTagAdapter = new ArrayAdapter<TagValuePair>(this, android.R.layout.simple_list_item_1, activePhoto.getTags());
        lstTags.setAdapter(lstTagAdapter);

        // Register list view for context menu
        registerForContextMenu(lstTags);

        imgViewManageTags = (ImageView)findViewById(R.id.imgViewManageTags);
        try {
            InputStream inputStream = getContentResolver().openInputStream(activePhoto.getUri());
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            imgViewManageTags.setImageBitmap(image);
        } catch (Exception e){
            Log.i(logCode, "Exception message: " + e.getMessage());
        }

        FloatingActionButton btnAddTag = (FloatingActionButton) findViewById(R.id.btnAddTag);
        btnAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTag();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manage_tags, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuDone:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == lstTags.getId()){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            getMenuInflater().inflate(R.menu.context_menu_manage_tags, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.mnuRemoveTag:
                activePhoto.removeTag(lstTagAdapter.getItem(info.position));
                lstTagAdapter.notifyDataSetChanged();
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == CREATE_TAG_CODE){
                String type = data.getStringExtra("TYPE");
                String value = data.getStringExtra("VALUE");

                activePhoto.addTag(new TagValuePair(type, value));
                lstTagAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addTag(){
        Intent createTagIntent = new Intent(this, CreateTagActivity.class);
        startActivityForResult(createTagIntent, CREATE_TAG_CODE);
    }
}
