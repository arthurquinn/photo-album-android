package src.androidphotoalbum.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageViewGridAdapter extends BaseAdapter {
    private Context ctx;
    private List<Bitmap> images;


    public ImageViewGridAdapter(Context ctx, List<Bitmap> images)
    {
        this.ctx = ctx;
        this.images = images;

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
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
            imgView = new ImageView(ctx);
            imgView.setImageBitmap(images.get(position));

            ViewGroup.LayoutParams imageLayout = new ViewGroup.LayoutParams(250, 250);
            imageLayout.width = 250;
            imageLayout.height = 250;
            imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgView.setPadding(8, 8, 8, 8);
            imgView.setLayoutParams(imageLayout);
        }
        else
        {
            imgView = (ImageView) convertView;
        }


        return imgView;
    }
}
