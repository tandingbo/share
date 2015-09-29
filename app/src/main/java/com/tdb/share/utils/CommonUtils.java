package com.tdb.share.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by Administrator on 2015/9/29.
 */
public class CommonUtils {

    public static String getMyUniqueId(Activity mActivity) {
        TelephonyManager tm = (TelephonyManager) mActivity.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice = "" + tm.getDeviceId();
        String tmSerial = "" + tm.getSimSerialNumber();
        String androidId = "" + android.provider.Settings.Secure.getString(mActivity.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    public static boolean isExitsSdcard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static void Vibrate(Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo.State wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            if (wifi == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 图片质量压缩
     */
    public static boolean compressBmpToFile(Activity activity, Uri imageUri, File file, int limitSize, int limitWidth, int limitHeight) {
        try {
            InputStream inputStream = activity.getContentResolver().openInputStream(imageUri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();

            options.inJustDecodeBounds = false;
            float wRate = ((float) options.outWidth) / limitWidth;
            float hRate = ((float) options.outHeight) / limitHeight;

            int scalerate = 1;

            if (Math.max(wRate, hRate) > 1) {
                scalerate = Math.round(Math.max(wRate, hRate));
            }

            options.inSampleSize = scalerate;
            inputStream = activity.getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            boolean ret = compressImageSize(bitmap, file, limitSize);

            return ret;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean compressImageSize(Bitmap bmp, File file, int limitSize) {
        int options[] = {90, 70, 50, 30, 10, 1};

        boolean ret = false;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for (int i = 0; i < options.length; i++) {
            baos.reset();
            bmp.compress(Bitmap.CompressFormat.JPEG, options[i], baos);
            if (baos.toByteArray().length <= limitSize) {
                break;
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            bmp.recycle();
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 根据id获取一个图片
     */
    public static Bitmap getResIcon(Resources res, int resId) {
        Drawable icon = res.getDrawable(resId);
        if (icon instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) icon;
            return bd.getBitmap();
        } else {
            return null;
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getHeight();
    }

    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Activity activity) {
        WindowManager manage = activity.getWindowManager();
        Display display = manage.getDefaultDisplay();
        int screenWidth = display.getWidth();
        return screenWidth;
    }

    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    private static double EARTH_RADIUS = 6378.137;// 一般的认为，地球的赤道半径是6378137米

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    // 单位为米
    public static int getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return (int) s;
    }

    public static String generateSignature(String arg1, String arg2, String arg3) {
        String[] strings = new String[3];
        strings[0] = arg1;
        strings[1] = arg2;
        strings[2] = arg3;
        Arrays.sort(strings);
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string);
        }
        return encrypt(sb.toString(), "SHA-1");
    }

    public static String encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "MD5";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    @SuppressLint("DefaultLocale")
    public static String bytes2Hex(byte[] bts) {
        StringBuilder strDes = new StringBuilder();
        String tmpStr = null;
        for (int i = 0; i < bts.length; i++) {
            tmpStr = (Integer.toHexString(bts[i] & 0xFF));
            if (tmpStr.length() == 1) {
                strDes.append("0" + tmpStr);
            } else {
                strDes.append(tmpStr);
            }
        }
        return strDes.toString().toLowerCase();
    }
}
