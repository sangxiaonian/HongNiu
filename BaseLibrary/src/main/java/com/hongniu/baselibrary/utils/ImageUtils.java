package com.hongniu.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fy.androidlibrary.utils.JLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import static android.os.Environment.DIRECTORY_PICTURES;

/**
 * Created by a on 2016/3/31.
 */
public class ImageUtils {
    /**
     * 从本地path中获取bitmap，压缩后保存小图片到本地 * * @param context * @param path 图片存放的路径 * * @return 返回压缩后图片的存放路径
     */
    public static String saveBitmap(Context context, String path, long size) {
        String compressdPicPath = "";

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;  // 屏幕宽度（像素）
        int height = displayMetrics.heightPixels;  // 屏幕高度（像素）

        Bitmap bitmap = decodeSampledBitmapFromPath(path, width, height);
        int start = path.lastIndexOf("/");
        int end = path.lastIndexOf(".");
        String oldName;
        if (end>start&&start>=0) {
             oldName = path.substring(start,end);
       }else {
            oldName=path.substring(start);
       }

        compressdPicPath = getCompressPath(context,oldName, size,  bitmap);
        return compressdPicPath;
    }

    private static String getCompressPath(Context context,String originalName, long size,   Bitmap bitmap) {
        String name = originalName + "_compress.jpg";//★很奇怪oldName之前不能拼接字符串，只能拼接在后面，否则图片保存失败
        String saveDir = context.getExternalFilesDir(DIRECTORY_PICTURES)
                + "/compress";
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        // 保存入sdCard
        File file = new File(dir, name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /* options表示 如果不压缩是100，表示压缩率为0。如果是70，就表示压缩率是70，表示压缩30%; */
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        while (baos.toByteArray().length > size) {
            // 循环判断如果压缩后图片是否大于size继续压缩
            baos.reset();
            options -= 10;
            if (options < 11) {//为了防止图片大小一直达不到size，options一直在递减，当options<0时，下面的方法会报错
                // 也就是说即使达不到size，也就压缩到10了
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                break;
            }
            // 这里压缩options%，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        String compressdPicPath = "";
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(baos.toByteArray());
            out.flush();
            out.close();
            compressdPicPath = file.getAbsolutePath();
            JLog.i(file.length()+"+++++++++");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return compressdPicPath;
    }

    /**
     * 根据ImageView获取适当的压缩的宽和高 * 尽可能得到ImageView的精确的宽高 * * @param imageView * @return
     */
    public static ImageSize getImageViewSize(ImageView imageView) {

// 得到屏幕的宽度
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();


        ImageSize imageSize = new ImageSize();
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();

// ------------------------------------尽可能得到ImageView的精确的宽高-------------------------------------------------------------
// 得到imageView的实际宽度（为了压缩图片，这里一定要得到imageview的宽高）必须压缩！，否则OOM！！！！！！！！！！
        int width = imageView.getWidth();

        if (width <= 0) {//可能imageView刚new出来还没有添加到容器当中，width可能为0
            width = lp.width;//获取imageView在layout中声明的宽度
        }
        if (width <= 0) {//如果imageView设置的是WRAP_CONTENT=-1或者MATHC_PARENT=-2，得到的width还是0
// width = imageView.getMaxWidth();//检查最大值（此方法在API16以上才可以使用，为了兼容低版本，一会用反射获取）
            width = getImageViewFieldValue(imageView, "mMaxWidth");//检查最大值（此方法在API16以上才可以使用，为了兼容低版本，一会用反射获取）
        }
        if (width <= 0) {//如果还是得不到width，就设置为屏幕的宽度
            width = displayMetrics.widthPixels;
        }

// ----------------------------------------

        // 得到imageView的实际高度（为了压缩图片，这里一定要得到imageview的宽高）必须压缩！，否则OOM！！！！！！！！！！
        int height = imageView.getHeight();

        if (height <= 0) {//可能imageView刚new出来还没有添加到容器当中，width可能为0
            height = lp.height;//获取imageView在layout中声明的高度
        }
        if (height <= 0) {//如果imageView设置的是WRAP_CONTENT=-1或者MATHC_PARENT=-2，得到的width还是0
// height = imageView.getMaxHeight();//检查最大值（此方法在API16以上才可以使用，为了兼容低版本，一会用反射获取）
            height = getImageViewFieldValue(imageView, "mMaxHeight");//检查最大值（此方法在API16以上才可以使用，为了兼容低版本，一会用反射获取）
        }
        if (height <= 0) {//如果还是得不到width，就设置为屏幕的高度
            height = displayMetrics.heightPixels;
        }
// --------------------------------尽可能得到ImageView的精确的宽高------------------------------------------------------------------

        imageSize.width = width;
        imageSize.height = height;
        return imageSize;
    }

    /**
     * 通过反射获取imageview的某个属性值（imageView.getMaxWidth()这个方法在api16以上才可以用，所以使用反射得到这个width属性值） * * @param object * @param fieldName * @return
     */
    private static int getImageViewFieldValue(Object object, String fieldName) {

        int value = 0;

        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return value;
    }

    /**
     * 根据图片要显示的宽和高，对图片进行压缩，避免OOM * * @param path * @param width 要显示的imageview的宽度 * @param height 要显示的imageview的高度 * @return
     */
    private static Bitmap decodeSampledBitmapFromPath(String path, int width, int height) {

// 获取图片的宽和高，并不把他加载到内存当中
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = caculateInSampleSize(options, width, height);
// 使用获取到的inSampleSize再次解析图片(此时options里已经含有压缩比 options.inSampleSize，再次解析会得到压缩后的图片，不会oom了 )
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;

    }

    /**
     * 根据需求的宽和高以及图片实际的宽和高计算SampleSize * * @param options * @param reqWidth 要显示的imageview的宽度 * @param reqHeight 要显示的imageview的高度 * @return * @compressExpand 这个值是为了像预览图片这样的需求，他要比所要显示的imageview高宽要大一点，放大才能清晰
     */
    private static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if (width >= reqWidth || height >= reqHeight) {

            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(width * 1.0f / reqHeight);

            inSampleSize = Math.max(widthRadio, heightRadio);

        }

        return inSampleSize;
    }


    /**
     * This method is used to get real path of file from from uri<br/> * http://stackoverflow.com/questions/11591825/how-to-get-image-path-just- * captured-from-camera * 这个方法是可以使用的（网上搜了一堆好多没用的代码） * @param contentUri * @return String
     */
    public static String getRealPathFromURI(Activity activity, Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            // Do not call Cursor.close() on a cursor obtained using this
            // method,
            // because the activity will do that for you at the appropriate time
            Cursor cursor = activity.managedQuery(contentUri, proj, null, null,
                    null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }

    static class ImageSize {
        public int width;
        public int height;
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;  // 屏幕宽度（像素）
            int height = displayMetrics.heightPixels;  // 屏幕高度（像素）

            InputStream input = context.getContentResolver().openInputStream(uri);
            BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
            onlyBoundsOptions.inJustDecodeBounds = true;
            onlyBoundsOptions.inDither = true;//optional
            onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
            bitmap = BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
            input.close();
            onlyBoundsOptions.inSampleSize = caculateInSampleSize(onlyBoundsOptions, width, height);
            onlyBoundsOptions.inJustDecodeBounds = false;
            //比例压缩
            input = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
            input.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static Uri getCompressUri(Context context, long size, Uri uri){
        String path = uri.getPath();
        int start = path.lastIndexOf("/");
        int end = path.lastIndexOf(".");
        String oldName = "temp";
        if (end>start&&start>=0) {
            oldName = path.substring(start,end);
        }else if (start<path.length()){
            oldName=path.substring(start+1);
        }

        Bitmap bitmap = getBitmapFromUri(context, uri);
        String compressPath = getCompressPath(context,oldName, size, bitmap);
        //获取到压缩之后的路径
       return new Uri.Builder()
                .scheme(UriUtil.LOCAL_FILE_SCHEME)
                .path(compressPath)
                .build();

    }


}