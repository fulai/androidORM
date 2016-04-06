package com.fulai.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.fulai.myapplication.realm.RealmActivity;
import com.fulai.myapplication.services.MainService;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private String urlGet = "http://api.jisuapi.com/shouji/query?appkey=66bf3db9ac22e4fb&shouji=13456755448";
    private String urlPost = "http://api.jisuapi.com/shouji/query";
    private String urlImage = "http://www.baidu.com/img/bdlogo.png";
    private ImageView ivImg;
    private NetworkImageView networkImageView;
    private ImageView iv, img;
    private Button btnJump;
    private final static boolean create_flag = true;
    private final static boolean destory_flag = false;
    private MyServiceAIDL myServiceAIDL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initService();
        initView();
        initPoint();
        //testThread();
        initDeOrEncode();
        //cacheImage();
        //NetworkImageViewDo();
        int memoryCache = (int) (Runtime.getRuntime().maxMemory() / 8);
    }

    public void JumpRealm(View view) {
        startActivity(new Intent(this, RealmActivity.class));
    }

    private ServiceConnection sc = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myServiceAIDL = com.fulai.myapplication.MyServiceAIDL.Stub.asInterface(service);
            try {
                String string = myServiceAIDL.getString("hello");
                System.out.println("MainActivity " + string);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void initService() {
        Intent intent = new Intent(this, MainService.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", create_flag);
        intent.putExtras(bundle);
        bindService(intent, sc, BIND_AUTO_CREATE);
    }

    private void initPoint() {
        img = (ImageView) findViewById(R.id.img);
        Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(40);
        canvas.drawPoint(120, 20, mPaint);

        mPaint.setColor(Color.GREEN);
        float[] mfloat = new float[]{10, 10, 50, 50, 50, 100, 50, 150};
        canvas.drawPoints(mfloat, mPaint);


        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.BLUE);
        int offsetDy = 50;
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(10, offsetDy * i, 300, offsetDy * i, mPaint);
        }
        img.setImageBitmap(bitmap);

    }

    private void NetworkImageViewDo() {
        ImageLoader loader = new ImageLoader(MyApplication.getHttpQueue(), new BitmapCache());
        networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        networkImageView.setErrorImageResId(R.mipmap.ic_launcher);
        networkImageView.setImageUrl(urlImage, loader);
    }

    private void cacheImage() {
        ImageLoader loader = new ImageLoader(MyApplication.getHttpQueue(), new BitmapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(ivImg, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        loader.get(urlImage, listener);
    }

    /**
     * 编码和解码
     */
    private void initDeOrEncode() {
        String string = "你好我是中国人，不是English";
        String str1 = "你好";
        String str2 = "你好";
        try {
//            byte[] bytes = string.getBytes("UTF-8");
//            String get = new String(bytes, "UTF-8");
            byte[] bytes = str2.getBytes("GB2312");
            Toast.makeText(MainActivity.this, String.valueOf(bytes.length), Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        ivImg = (ImageView) findViewById(R.id.iv_img);
        networkImageView = (NetworkImageView) findViewById(R.id.iv_networkimageview);
        btnJump = (Button) findViewById(R.id.btn_jump);
        btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });

        iv = (ImageView) findViewById(R.id.iv);
        Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        canvas.drawBitmap(bmp, 0, 0, null);
        Rect src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        Rect drc = new Rect(0, bmp.getHeight(), bmp.getWidth() * 3, bmp.getHeight() * 4);
        canvas.drawBitmap(bmp, src, drc, null);
        iv.setImageBitmap(bitmap);
    }

    private void loadImage() {
        ImageRequest imageRequest = new ImageRequest(urlImage, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                ivImg.setImageBitmap(bitmap);
            }
        }, 1000, 1000, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        imageRequest.setTag("imageTag");
        MyApplication.getHttpQueue().add(imageRequest);
    }

    /**
     * Toast不能再子线程中运行
     */
    private void testThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "ThreadThread", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    private void volleyGet() {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                StringRequest request = new StringRequest(Method.GET, urlGet, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String s) {
//                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(final VolleyError volleyError) {
//                        Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//                request.setTag("testGet");
//                MyApplication.getHttpQueue().add(request);
//            }
//        }).start();

//        StringRequest request = new StringRequest(Method.POST, urlPost, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(final VolleyError volleyError) {
//                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<String, String>();
//                //appkey=66bf3db9ac22e4fb&shouji=13456755448
//                map.put("appkey", "66bf3db9ac22e4fb");
//                map.put("shouji", "13456755448");
//                return map;
//            }
//        };
//        request.setTag("testGet");
//        MyApplication.getHttpQueue().add(request);
        MyVolleyInterface<String> myVolleyInterface = new MyVolleyInterface<String>(this);
        String tag = "stringKeyGet";
        VolleyStringRequest.requestGet(this, urlGet, tag, myVolleyInterface);

    }

    private void volleyPost() {
        MyVolleyInterface<String> myVolleyInterface = new MyVolleyInterface<String>(this);
        String tag = "stringKeyPost";
        Map<String, String> map = new HashMap<String, String>();
        map.put("appkey", "66bf3db9ac22e4fb");
        map.put("shouji", "13482053750");
        VolleyStringRequest.requestPost(this, urlPost, tag, map, myVolleyInterface);
    }

    private void volleyJsonObjectGet() {
        MyVolleyInterface<JSONObject> myVolleyInterface = new MyVolleyInterface<JSONObject>(this);
        String tag = "stringKeyPost";
        VolleyJsonObjectRequest.requestGet(this, urlGet, tag, myVolleyInterface);
    }

    private void volleyJsonObjectPost() {
        MyVolleyInterface<JSONObject> myVolleyInterface = new MyVolleyInterface<JSONObject>(this);
        String tag = "stringKeyPost";
        Map<String, String> map = new HashMap<String, String>();
        map.put("appkey", "66bf3db9ac22e4fb");
        map.put("shouji", "13482053750");
        VolleyJsonObjectRequest.requestPost(this, urlPost, tag, map, myVolleyInterface);
    }


    private class MyVolleyInterface<T> extends VolleyInterface<T> {

        public MyVolleyInterface(Context context) {
            super(context);
        }

        @Override
        public void onMySuccess(T result) {
            Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMyError(VolleyError volleyError) {
            Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            Log.i(TAG, volleyError.toString());
        }
    }

    public void jsonObjectRequest() {
//        JsonObjectRequest request = new JsonObjectRequest(Method.GET, urlGet, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
        HashMap<String, String> map = new HashMap<String, String>();
        //appkey=66bf3db9ac22e4fb&shouji=13456755448
        map.put("appkey", "66bf3db9ac22e4fb");
        map.put("shouji", "13456755448");
        final String mRequestBody = appendParameter(urlPost, map);
        Log.i(TAG, mRequestBody);
        JsonObjectRequest request = new JsonObjectRequest(Method.POST, urlPost, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
            }

            @Override
            public byte[] getBody() {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            mRequestBody, "utf-8");
                    return null;
                }
            }
        };
        request.setTag("testGet");
        MyApplication.getHttpQueue().add(request);
    }

    /**
     * 将Map数据转为key1=values1&key2=values2
     *
     * @param url
     * @param params
     * @return
     */
    public String appendParameter(String url, Map<String, String> params) {
        Uri uriTemp = Uri.parse(url);
        Uri.Builder builder = uriTemp.buildUpon();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        Log.i(TAG, builder.build().getQuery());
        return builder.build().getQuery();
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQueue().cancelAll("testGet");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //volleyGet();
        //volleyPost();
        //jsonObjectRequest();
        //volleyJsonObjectGet();
        //volleyJsonObjectPost();
        //loadImage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(sc);
    }
}
