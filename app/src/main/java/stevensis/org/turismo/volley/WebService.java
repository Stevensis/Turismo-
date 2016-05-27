package stevensis.org.turismo.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Steven on 25/05/2016.
 */
public class WebService {
    public static String server="http://172.16.16.200:3000/api/v1";
    public static String autenticar=server+"usuario/login";
    private static WebService mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private WebService(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache(){
                    private final LruCache<String, Bitmap>
                    cache = new LruCache<String, Bitmap>(20);

                    public Bitmap getBitmap(String url){return cache.get(url);}

                    public void putBitmap(String url, Bitmap bitmap){ cache.put(url, bitmap);}
                });
    }

    public static synchronized WebService getmInstance(Context context){
        if (mInstance == null){
            mInstance = new WebService(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req){getRequestQueue().add(req);}
    public ImageLoader getmImageLoader(){return mImageLoader;}
}
