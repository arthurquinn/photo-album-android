package src.androidphotoalbum;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddEditAlbumActivity extends AppCompatActivity {

    private EditText txtAlbumName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_album);

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
                if (!txtAlbumName.getText().toString().isEmpty()) {
                    // TODO: Check to make sure text box isn't empty
                    String albumName = txtAlbumName.getText().toString();
                    getIntent().putExtra("ALBUM_NAME", albumName);
                    setResult(RESULT_OK, getIntent());
                    finish();
                }
                else {
                    AlertDialog.Builder missingNameAlert = new AlertDialog.Builder(this);
                    missingNameAlert.setMessage("Album name field is required")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setTitle("Error")
                            .create();
                    missingNameAlert.show();
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
