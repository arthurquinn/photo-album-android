package src.androidphotoalbum.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import src.androidphotoalbum.models.Photo;

public class ImageViewGridAdapter extends BaseAdapter {
    private Context ctx;
    private List<Photo> photoList;


    public ImageViewGridAdapter(Context ctx, List<Photo> photoList)
    {
        this.ctx = ctx;
        this.photoList = photoList;

    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgView;
        if (convertView == null)
        {
            // Make the image view
            imgView = new ImageView(ctx);

            try {
                // Get bitmap from photo object
                Photo photo = photoList.get(position);
                Uri imageUri = Uri.parse(photo.getFilePath());
                InputStream inputStream = ctx.getContentResolver().openInputStream(imageUri);
                Bitmap image = BitmapFactory.decodeStream(inputStream);

                // Create the image view
                imgView = new ImageView(ctx);
                imgView.setImageBitmap(image);
                ViewGroup.LayoutParams imageLayout = new ViewGroup.LayoutParams(250, 250);
                imageLayout.width = 250;
                imageLayout.height = 250;
                imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imgView.setPadding(8, 8, 8, 8);
                imgView.setLayoutParams(imageLayout);

            } catch (FileNotFoundException e){

            }
        }
        else
        {
            imgView = (ImageView) convertView;
        }
        return imgView;
    }
}
