package demo.testdemo;

import android.Manifest;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

/**
 *  Collect device information
 *  @author qipu
 */
public class DataCollector {

    private static final String BIN_SU_PATH = "/system/bin/su";
    private static final String XBIN_SU_PATH = "/system/xbin/su";
    private static final String SUPER_USER_APK_PATH = "/system/app/Superuser.apk";

    /** Map to collect all the info*/
    private Map<String, String> dataMap = new HashMap<>();

    private Context context;

    public DataCollector(Context context) {
        this.context = context;
    }

    public Map collectDeviceInfo() {

        dataMap.put("mg", getCpuInfoDigest());
        dataMap.put("py", getCpuAbi());
        dataMap.put("fd", "22efbd0d6ffdb658fe7a7e7879350e64");
        dataMap.put("zv", getBluetoothAddress());
        dataMap.put("zc", "863684038661135");
        dataMap.put("xv", "460029513558782");
        dataMap.put("hr", getWifiList());

        dataMap.put("la", getBaseStation());

        dataMap.put("zs", getNeighborBaseStation());
        dataMap.put("ul", getBoard());
        dataMap.put("fv", getBrand());
        dataMap.put("tz", getHardware());
        dataMap.put("wh", getManufacturer());
        dataMap.put("ju", getDisplayRom());
        dataMap.put("ds", getProduct());
        dataMap.put("wl", "OPPO A37m");
        dataMap.put("yt", getResolution());
        dataMap.put("mw", "5.1");
        dataMap.put("fu", "com.qiyi.video");
        dataMap.put("go", "V8.2");
        dataMap.put("ot", getTotalMemory() + "");
        dataMap.put("ps", getTotalSystem() + "");
        dataMap.put("wj",  "386334720");
        dataMap.put("ks", getFreeMemory() + "");
        dataMap.put("se", getFreeSystem() + "");
        dataMap.put("sp", "386334720");
        dataMap.put("wa", getTimeZone() + "");
        dataMap.put("yy", getStartTime() + "");
        dataMap.put("kp", getActiveTime() + "");
        dataMap.put("sy", getBattery() + "");
        dataMap.put("jd", getBrightness() + "");

        dataMap.put("eh", getFreeSystem() + "");
        dataMap.put("ce", hasSu() || isSuperuserExist() ? "true" : "false");
        dataMap.put("mr", getFreeSystem() + "");

        dataMap.put("no", getMusicHash());
        dataMap.put("xm", getPhotoHash());
        dataMap.put("hl", getSensors());
        dataMap.put("qf", "2.0");
        dataMap.put("qd", getWifi());
        dataMap.put("kv", getNetworkType());
        dataMap.put("lw", getCellular());

        dataMap.put("kl", "76faebc5-a7f9-4c75-888d-6c3bcc87c997");

        dataMap.put("kd", "f45d93433b315a4a");
        dataMap.put("ae", "ANDROID");
        dataMap.put("ed", "");
        return truncateCollectedData();
    }

    /**
     * Get the digest of current device's CPU information
     * @return A md5 of CPU info
     */
    private String getCpuInfoDigest() {

        StringBuilder cpu_info = new StringBuilder();

        InputStreamReader inputStreamReader = null;
        try {
            Process localProcess = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            inputStreamReader = new InputStreamReader(localProcess.getInputStream());
            LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);

            String line;

            while ((line = lineNumberReader.readLine()) != null) {
                cpu_info.append(line.trim());
            }

            return Utils.md5(cpu_info.toString().trim());
        }
        catch (IOException e) {
            e.printStackTrace();
            return cpu_info.toString();
        } finally {
            if (null != inputStreamReader) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get the name of the instruction set of native code
     * @return
     */
    private String getCpuAbi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Arrays.toString(Build.SUPPORTED_ABIS);
        } else {
            return Arrays.toString(new String[]{Build.CPU_ABI, Build.CPU_ABI2});
        }
    }


    /**
     * 获取蓝牙MAC地址 对6.0以后的系统 特别处理
     * 相关的权限申请在 QYVideoClient中{@see QYVideoClient/AndroidManifest.xml}
     * @return 蓝牙MAC地址
     */
    private String getBluetoothAddress() {

        String dress = "";
        String defaultDress = "02:00:00:00:00:00";//Google 6.0以后默认通过BluetoothAdapter方式的返回值 add v8.3

        try {

            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

                if ((adapter != null) && (adapter.isEnabled())) {
                    dress = Utils.encode(adapter.getAddress());
                }
            }else{
                String inDress = android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
                if(!TextUtils.isEmpty(inDress)){

                    dress = Utils.encode(inDress);
                }else {
                    dress = Utils.encode(defaultDress);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            dress = Utils.encode(defaultDress);
        }

        return dress;
    }


    /**
     * 获取Wifi列表
     * 相关的权限申请在 QYVideoClient中{@see QYVideoClient/AndroidManifest.xml}
     * @return Wifi列表
     */
    private String getWifiList() {

        if (context == null) {
            return "";
        }

        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            /** 权限的定义在"QYVideoClient\AndroidManifest.xml"中*/
            wifiManager.startScan();
            List<ScanResult> wifi_list = wifiManager.getScanResults();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");

            for (ScanResult result : wifi_list) {
                stringBuilder.append(Utils.encode(result.SSID) + ","
                        + Utils.encode(result.BSSID) + ","
                        + Utils.encode(result.capabilities.replace("[", "").replace("]", ""))
                        + "#");
            }

            return Utils.md5(stringBuilder.substring(0, stringBuilder.length() - 1) + "]");

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 这个方法用于混淆，直接返回“”
     * @return “”
     */
    private String getBaseStation() {

        if (context == null) {
            return "";
        }

        try {

            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            int operator_type = telephonyManager.getNetworkType();

            if (operator_type == TelephonyManager.NETWORK_TYPE_CDMA ||
                    operator_type == TelephonyManager.NETWORK_TYPE_EVDO_0 ||
                    operator_type == TelephonyManager.NETWORK_TYPE_EVDO_A ||
                    operator_type == TelephonyManager.NETWORK_TYPE_1xRTT) {

            }

            return "";

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取周围基站的信息，包括基站信号强度，基站区域码
     * @return 周围基站的信息
     */
    private String getNeighborBaseStation() {

        if (context == null) {
            return "";
        }

        try {
            boolean hasPermission = PackageManager.PERMISSION_GRANTED ==
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (hasPermission) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.getNetworkOperator();
                List<NeighboringCellInfo> wifi_list = telephonyManager.getNeighboringCellInfo();

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[");

                for (NeighboringCellInfo result : wifi_list) {
                    stringBuilder.append(result.getLac() + ","
                            + result.getCid() + ","
                            + result.getRssi()
                            + "#");
                }

                return stringBuilder.substring(0, stringBuilder.length() == 1 ? 1 : stringBuilder.length() - 1) + "]";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    /**
     * 获取主板信息
     * @return 主板信息
     */
    private String getBoard() {
        return Build.BOARD;
    }

    /**
     * 获取手机品牌名称
     * @return 手机品牌
     */
    private String getBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备硬件名称
     * @return 设备硬件名称
     */
    private String getHardware() {
        return Build.HARDWARE;
    }

    /**
     * 获取制造商
     * @return 制造商
     */
    private String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取ROM显示版本号
     * @return ROM显示版本号
     */
    private String getDisplayRom() {
        return Build.DISPLAY;
    }

    /**
     * 设备产品名称
     * @return 产品名称
     */
    private String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取屏幕密度相关参数
     * @return 屏幕密度相关参数
     */
    private String getResolution() {

        if (context == null) {
            return "";
        }

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        return "[" + metrics.density + ","
                + Math.max(metrics.widthPixels, metrics.heightPixels) + ","
                + Math.min(metrics.heightPixels, metrics.widthPixels) + ","
                + metrics.xdpi + ","
                + metrics.ydpi + "]";
    }



    /**
     * RAM总大小
     * @return RAM总大小
     */
    private long getTotalMemory() {

        FileReader reader = null;
        try {
            reader = new FileReader("/proc/meminfo");
            BufferedReader bufferedReader = new BufferedReader(reader, 8192);
            String line = bufferedReader.readLine();
            if (!TextUtils.isEmpty(line)) {
                String[] key_value = line.split("\\s+");
                return Long.valueOf(key_value[1]).intValue();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return -1;
    }

    /**
     * 获取System分区总大小
     * @return System分区总大小
     */
    private long getTotalSystem() {
        try {
            if (context != null) {
                File root = Environment.getRootDirectory();
                StatFs statFs = new StatFs(root.getPath());
                return statFs.getBlockSize() * statFs.getBlockCount() / 1024L;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }


    /**
     * 可用内存大小
     * @return 可用内存大小
     */
    private long getFreeMemory() {

        if (context == null) {
            return -1;
        }

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(memoryInfo);

        return memoryInfo.availMem;
    }

    /**
     * 获取System分区的可用空间大小
     * @return System分区的可用空间大小
     */
    private long getFreeSystem() {
        try {
            File root = Environment.getRootDirectory();
            StatFs statFs = new StatFs(root.getPath());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong() / 1024L;
            } else {
                return statFs.getBlockSize() * statFs.getAvailableBlocks() / 1024L;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * 获取时区
     * @return 时区
     */
    private String getTimeZone() {
        TimeZone timeZone = TimeZone.getDefault();
        if (null == timeZone) {
            return "";
        }
        return "[" + timeZone.getDisplayName(false, TimeZone.SHORT) + "," + timeZone.getID() + "]";
    }

    /**
     * 获取系统运行时间
     * @return 系统运行时间
     */
    private long getStartTime() {
        return System.currentTimeMillis() - SystemClock.elapsedRealtime();
    }

    /**
     * 系统运行时间，不计算系统深度睡眠时间
     * @return 系统运行时间
     */
    private long getActiveTime() {
        return SystemClock.uptimeMillis();
    }

    /**
     * 获取电池状态
     * @return 电池状态
     */
    private String getBattery() {

        if (context == null) {
            return "";
        }

        IntentFilter filter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
        Intent intent = context.registerReceiver(null, filter);

        if (intent != null) {
            return "[" + intent.getIntExtra("status", 0) + "," + intent.getIntExtra("level", 0) + "]";
        }

        return "";
    }

    /**
     * 设备背光亮度
     * @return 背光亮度
     */
    private int getBrightness() {

        if (context == null) {
            return -1;
        }

        ContentResolver resolver = context.getContentResolver();

        try {
            return Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取部分音频文件的Hash，现在只取5个
     * @return 部分音频文件的Hash
     */
    private String getMusicHash() {

        if (context == null) {
            return "";
        }

        try {
            boolean hasPermission = false;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                hasPermission = PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (hasPermission) {
                ContentResolver resolver = context.getContentResolver();
                String[] projection = {"_id", "_display_name", "_data", "album", "artist", "duration", "_size", "date_added"};
                Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, "date_added");

                if (cursor != null) {

                    StringBuilder stringBuilder = new StringBuilder();

                    int count = 0;

                    while (cursor.moveToNext()) {

                        if (count >= 5) {
                            cursor.close();
                            break;
                        }

                        stringBuilder.append(cursor.getString(cursor.getColumnIndex("_display_name")))
                                .append(",").append(cursor.getString(cursor.getColumnIndex("album")))
                                .append(",").append(cursor.getString(cursor.getColumnIndex("_id")))
                                .append(",").append(cursor.getString(cursor.getColumnIndex("duration")))
                                .append(",").append(cursor.getString(cursor.getColumnIndex("_size")))
                                .append(",").append(cursor.getString(cursor.getColumnIndex("artist")))
                                .append(",").append(cursor.getString(cursor.getColumnIndex("_data")))
                                .append(",").append(cursor.getString(cursor.getColumnIndex("date_added")))
                                .append(",");

                        count++;
                    }

                    cursor.close();

                    return Utils.md5(stringBuilder.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取部分照片信息的Hash，只取5个
     * @return 部分照片信息的Hash
     */
    private String getPhotoHash() {

        if (context == null) {
            return "";
        }

        try {
            boolean hasPermission = false;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                hasPermission = PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (hasPermission) {
                ContentResolver resolver = context.getContentResolver();
                String[] projection = {"_data", "_size", "orientation", "width",
                        "height", "date_added", "latitude", "longitude"};
                Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, null, null, "date_added");

                if (cursor != null) {

                    StringBuilder stringBuilder = new StringBuilder();

                    int count = 0;

                    while (cursor.moveToNext()) {

                        if (count >= 5) {
                            cursor.close();
                            break;
                        }

                        stringBuilder.append(cursor.getString(cursor.getColumnIndex("_data"))).append(",")
                                .append(cursor.getString(cursor.getColumnIndex("_size"))).append(",")
                                .append(cursor.getString(cursor.getColumnIndex("orientation"))).append(",")
                                .append(cursor.getString(cursor.getColumnIndex("width"))).append(",")
                                .append(cursor.getString(cursor.getColumnIndex("height"))).append(",")
                                .append(cursor.getString(cursor.getColumnIndex("date_added"))).append(",")
                                .append(cursor.getString(cursor.getColumnIndex("latitude"))).append(",")
                                .append(cursor.getString(cursor.getColumnIndex("longitude"))).append(",");

                        count++;
                    }

                    cursor.close();

                    return Utils.md5(stringBuilder.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return "";
    }

    /**
     * 获取设备Sensor信息Hash，只取5个
     * @return 设备Sensor信息Hash
     */
    private String getSensors() {

        if (context == null) {
            return "";
        }

        try {

            SensorManager sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

            if (sensorManager == null) {
                return "";
            }

            List list = sensorManager.getSensorList(-1);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");

            if ((list != null) && (list.size() > 0)) {

                int count = 0;
                Iterator localIterator = list.iterator();

                while (localIterator.hasNext()) {

                    Sensor sensor = (Sensor)localIterator.next();

                    if (count > 5) {
                        break;
                    }

                    stringBuilder.append(Utils.encode(sensor.getType() + ","))
                            .append(Utils.encode(sensor.getName())).append(",")
                            .append(Utils.encode(sensor.getVersion() + ","))
                            .append(Utils.encode(sensor.getVendor())).append(",")
                            .append(Utils.encode(sensor.getMaximumRange() + ","))
                            .append(Utils.encode(sensor.getMinDelay() + ","))
                            .append(Utils.encode(sensor.getPower() + ","))
                            .append(Utils.encode(sensor.getResolution() + ""))
                            .append("#");
                    count++;
                }
                return Utils.md5(stringBuilder.toString().substring(0,
                        stringBuilder.length() == 1 ? 1 : stringBuilder.length() - 1) + "]");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return "";
    }

    /**
     * 获取连接的Wifi信息
     * @return 连接的Wifi信息
     */
    private String getWifi() {

        if (context == null) {
            return "";
        }

        try {

            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            if (wifiManager == null) {
                return "";
            }

            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            return "[" + wifiInfo.getBSSID() + "," + wifiInfo.getSSID() + "]";

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取连接的网络类型
     * @return 连接的网络类型
     */
    private String getNetworkType() {

        if (context == null) {
            return "";
        }

        try {

            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager == null) {
                return "";
            }

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if ((networkInfo != null) && (networkInfo.isConnected())) {

                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return "WiFi";
                }

                return "Mobile Network";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return "";
    }

    /**
     * 获取网络接口地址
     * @return 网络接口地址
     */
    private String getCellular() {

        try {

            Enumeration interface_enumeration = NetworkInterface.getNetworkInterfaces();

            while (interface_enumeration.hasMoreElements()) {

                NetworkInterface networkInterface = (NetworkInterface)interface_enumeration.nextElement();

                Enumeration address_enumeration = networkInterface.getInetAddresses();

                while (address_enumeration.hasMoreElements()) {

                    InetAddress inetAddress = (InetAddress)address_enumeration.nextElement();

                    if ((!inetAddress.isLoopbackAddress()) && ((inetAddress instanceof Inet4Address))) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return "";
    }

    public String getJsonString() {

        if (null == dataMap) {
            return "";
        }

        JSONObject jsonObject = new JSONObject();

        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            try {
                jsonObject.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
                return "";
            }
        }

        return jsonObject.toString();
    }

    /**
     * 系统是否有su
     * @return 系统是否有su
     */
    private boolean hasSu() {
        return (new File(BIN_SU_PATH).exists()) && (checkSuProperty(BIN_SU_PATH))
                || (new File(XBIN_SU_PATH).exists()) && (checkSuProperty(XBIN_SU_PATH));
    }

    /**
     * 检查su权限
     * @param path su可执行文件路径
     * @return 是否有su权限
     */
    private boolean checkSuProperty(String path) {

        Process process = null;
        InputStreamReader inputStreamReader = null;
        try {
            process = Runtime.getRuntime().exec("ls -l " + path);
            inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String str = reader.readLine();
            if ((str != null) && (str.length() >= 4)) {
                char c = str.charAt(3);
                if ((c == 'x') || (c == 's')) {
                    return true;
                }
            }
        } catch (Exception localException) {
            localException.printStackTrace();
            return false;
        } finally {
            if (process != null) {
                process.destroy();
            }
            if (null != inputStreamReader) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * Superuser.apk是否存在
     */
    private boolean isSuperuserExist() {
        return new File(SUPER_USER_APK_PATH).exists();
    }



    /**
     * 截断String，最大50个字符
     * @param str 待处理字符串
     */
    private String truncateString(String str) {
        if (null == str) {
            return "";
        }

        if (str.length() > 50) {
            return str.substring(0,50);
        } else {
            return str;
        }
    }

    /**
     * 对采集到的数据每一个都做截断处理
     */
    private Map truncateCollectedData() {
        if (null != dataMap) {
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                if (!"ed".equals(entry.getKey())) {
                    dataMap.put(entry.getKey(), truncateString(entry.getValue()));
                }
            }
        }
        return dataMap;
    }
}
