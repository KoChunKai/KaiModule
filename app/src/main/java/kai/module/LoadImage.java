package kai.module;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by kevin on 15/9/18.
 */
public class LoadImage {

    private static LoadImage instance = null;

    ImageLoader imageLoader = ImageLoader.getInstance();

    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .resetViewBeforeLoading(true)
            .showImageOnLoading(null)
            .showImageForEmptyUri(null)
            .showImageOnFail(null)
            .cacheInMemory(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .build();

    public static LoadImage getInstance(Context context){
        if(instance == null){
            instance = new LoadImage(context);
        }
        return instance;
    }


    public LoadImage(Context context){
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }
}
