package hong.cantonese.cache;

import android.os.Environment;

import com.umeng.commonsdk.debug.E;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by hongenit on 2017/1/16.
 */

public class CacheManager {
    private final String fileName = "sentences.dat";
    private final String rootDir = "cantonese";

    private CacheManager() {
    }

    private static CacheManager cacheManager;

    public static CacheManager getInstance() {
        if (cacheManager == null) {
            cacheManager = new CacheManager();
        }
        return cacheManager;
    }


    public void saveSentenceListToFile(ArrayList<String> arrayList) {
        String string = listToString(arrayList);
        saveByteToFile(string.getBytes());
    }

    public ArrayList<String> getSentenceListFromFile() {
        byte[] bytes = null;
        File cacheFile = null;
        File cacheDir = null;
        // 如果存储在SD卡上去没有装载SD卡 则直接返回
        if (Environment.getExternalStorageState() != null
                && !Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return null;
        }

        cacheDir = new File(Environment.getExternalStorageDirectory(),
                rootDir);
        if (!cacheDir.isDirectory() || !cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        if (cacheDir.exists()) {
            cacheFile = new File(cacheDir, fileName);
        } else {
            return null;
        }
        try {
            bytes = readByteFromFoile(cacheFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bytes == null) {
            return null;
        }
        String string = new String(bytes);
        return stringToList(string);
    }

    private String listToString(ArrayList<String> arrayList) {

        StringBuilder sb = new StringBuilder();
        for (String s : arrayList) {
            sb.append(s);
            sb.append(',');
        }
        int lastIndexOf = sb.lastIndexOf(",");
        if (lastIndexOf == -1) {
            return "";
        }
        try {

            sb.delete(lastIndexOf, lastIndexOf + 1);
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
        return sb.toString();
    }

    private ArrayList<String> stringToList(String string) {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] strs = string.split(",");
        for (String str : strs) {
            arrayList.add(str);
        }
        return arrayList;
    }

    private void saveByteToFile(byte[] content) {
        // 缓存任务为空直接返回
        if (content == null)
            return;
        // 如果存储在SD卡上去没有装载SD卡 则直接返回
        if (Environment.getExternalStorageState() != null
                && !Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return;
        }

        // 如果无法生成缓存文件名直接返回

        File cacheFile = null;
        File cacheDir = null;

        cacheDir = new File(Environment.getExternalStorageDirectory(),
                rootDir);
        if (!cacheDir.isDirectory() || !cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        if (cacheDir != null && cacheDir.exists()) {
            cacheFile = new File(cacheDir, fileName);
        } else {
            return;
        }
        try {
            writeFile(cacheFile, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }


    private void writeFile(File file, byte[] data) throws Exception {
        if (file != null && data != null) {
            OutputStream fos = new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.write(data);
            if (dos != null)
                dos.close();
        }
    }


    private byte[] readByteFromFoile(File file) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(
                (int) file.length());
        InputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        try {
            int i = -1;
            byte[] buffer = new byte[1024];
            while ((i = bis.read(buffer, 0, 1024)) != -1) {
                bos.write(buffer, 0, i);
            }
            return bos.toByteArray();
        } finally {
            try {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
