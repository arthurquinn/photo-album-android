package src.androidphotoalbum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import src.androidphotoalbum.models.TagValuePair;
import src.androidphotoalbum.state.ApplicationInstance;

public class CreateTagActivity extends AppCompatActivity {

    private Spinner spnType;
    private EditText txtValue;

    private String selectedType;

    private List<TagValuePair> tagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tag);

        tagList = ApplicationInstance.getInstance().getActivePhoto().getTags();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spnType = (Spinner)findViewById(R.id.spnType);
        txtValue = (EditText) findViewById(R.id.txtValue);
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
                if (spnType.getSelectedItem().toString().isEmpty() || txtValue.getText().toString().isEmpty()){
                    Toast.makeText(this, "Tag type and value are required", Toast.LENGTH_LONG).show();
                }
                else
                {
                    boolean exists = false;
                    for (TagValuePair tag : tagList)
                    {
                        if (tag.getKey().equals(spnType.getSelectedItem().toString()) && tag.getValue().equals(txtValue.getText().toString()))
                        {
                            Toast.makeText(this, "This tag already exists.", Toast.LENGTH_LONG).show();
                            exists = true;
                        }
                    }
                    if (!exists)
                    {
                        Intent createTagIntent = new Intent(this, ManageTagsActivity.class);
                        createTagIntent.putExtra("TYPE", spnType.getSelectedItem().toString());
                        createTagIntent.putExtra("VALUE", txtValue.getText().toString());
                        setResult(RESULT_OK, createTagIntent);
                        finish();
                    }
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
