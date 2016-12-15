package src.androidphotoalbum;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import src.androidphotoalbum.models.Photo;
import src.androidphotoalbum.state.ApplicationInstance;

public class SlideshowActivity extends AppCompatActivity {

    private static final String logCode = "androidPhotoAlbumLog";

    private TextView lblSlideshowNum;
    private ImageView imgViewSlideshow;

    private List<Photo> photoList;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lblSlideshowNum = (TextView)findViewById(R.id.lblSlideshowNum);
        imgViewSlideshow = (ImageView)findViewById(R.id.imgViewSlideshow);

        setTitle(String.format("%s - Slideshow", ApplicationInstance.getInstance().getActiveAlbum().getName()));

        photoList = ApplicationInstance.getInstance().getActiveAlbum().getPhotoList();
        index = photoList.indexOf(ApplicationInstance.getInstance().getActivePhoto());

        Button btnNext = (Button)findViewById(R.id.btnNext);
        Button btnPrevious = (Button)findViewById(R.id.btnPrevious);

        setSlideshowImageView(photoList.get(index));
        setSlideshowLabel(index);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < photoList.size() - 1){
                    setSlideshowImageView(photoList.get(++index));
                    setSlideshowLabel(index);
                }
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index > 0){
                    setSlideshowImageView(photoList.get(--index));
                    setSlideshowLabel(index);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_slideshow, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuCloseSlideshow:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setSlideshowImageView(Photo p){
        Bitmap image = p.loadBitmap(this);
        imgViewSlideshow.setImageBitmap(image);
    }

    private void setSlideshowLabel(int index){
        lblSlideshowNum.setText(String.format("Photo %d of %d", index + 1, photoList.size()));
    }
}
