package src.androidphotoalbum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateTagActivity extends AppCompatActivity {

    private EditText txtType;
    private EditText txtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tag);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtType = (EditText)findViewById(R.id.txtType);
        txtValue = (EditText)findViewById(R.id.txtValue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_tag, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.mnuCreateTagSave:
                // TODO: Add check to make sure no duplicate tags
                if (txtType.getText().toString().isEmpty() || txtValue.getText().toString().isEmpty()){
                    Toast.makeText(this, "Tag type and value are required", Toast.LENGTH_LONG).show();
                } else {
                    Intent createTagIntent = new Intent(this, ManageTagsActivity.class);
                    createTagIntent.putExtra("TYPE", txtType.getText().toString());
                    createTagIntent.putExtra("VALUE", txtValue.getText().toString());
                    setResult(RESULT_OK, createTagIntent);
                    finish();
                }
                return true;
            case R.id.mnuCreateTagCancel:
                setResult(RESULT_CANCELED);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
