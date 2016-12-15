package src.androidphotoalbum.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import src.androidphotoalbum.R;
import src.androidphotoalbum.models.Photo;
import src.androidphotoalbum.state.ApplicationInstance;

public class ImageViewGridAdapter extends BaseAdapter {
    Context ctx;
    List<Photo> photoList;

    String logCode = "androidPhotoAlbumLog";


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
        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.photo_thumbnail, parent, false);
        }

        ImageView imgView = (ImageView)convertView.findViewById(R.id.imgThumbnailView);
        TextView txtView = (TextView)convertView.findViewById(R.id.imgThumbnailText);

        Photo photo = photoList.get(position);
        Bitmap image = photo.loadBitmap(ctx);
        imgView.setImageBitmap(image);
        LinearLayout.LayoutParams imageLayout = new LinearLayout.LayoutParams(250, 250);
        imageLayout.width = 225;
        imageLayout.height = 225;
        imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgView.setPadding(12, 12, 12, 12);
        imgView.setLayoutParams(imageLayout);

        txtView.setText(photo.getFilename());

        return convertView;
    }
}
