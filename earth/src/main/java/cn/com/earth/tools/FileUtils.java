package cn.com.earth.tools;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/1/4 下午2:55
 */

public class FileUtils {

    public static String readAssets(String fileName) {
        InputStream is = null;
        String content = null;
        try {
            is = FileUtils.class.getResourceAsStream("/assets/" + fileName);
            if (is != null) {

                byte[] buffer = new byte[1024];
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    int readLength = is.read(buffer);
                    if (readLength == -1) break;
                    arrayOutputStream.write(buffer, 0, readLength);
                }
                is.close();
                arrayOutputStream.close();
                content = new String(arrayOutputStream.toByteArray());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            content = null;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return content;
    }

    /**
     * SD卡是否存在
     *
     * @return
     */
    public static boolean isSdcardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /*
     * 返回SD卡可用容量
     */
    public static long getAvailableStorage() {
        String sDcString = Environment.getExternalStorageState();

        if (sDcString.equals(Environment.MEDIA_MOUNTED)) {
            File pathFile = Environment.getExternalStorageDirectory();
            StatFs statfs = new StatFs(pathFile.getPath());
            // 获取可供程序使用的Block的数量
            long nAvailaBlock = statfs.getAvailableBlocks();
            long nBlocSize = statfs.getBlockSize();
            // 计算 SDCard 剩余大小MB
            return nAvailaBlock * nBlocSize / 1024 / 1024;
        } else {
            return -1;
        }

    }


    /**
     * 获取assets中指定后缀名的文件
     *
     * @param context
     * @param path
     * @param Extension
     * @return
     */
    public static List<String> getfileFromAssets(Context context, String path, String Extension) {
        List<String> lstFile = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        try {
            String[] files = assetManager.list(path);
            if (files == null) {
                return lstFile;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].substring(files[i].length() - Extension.length()).equals(Extension)) //判断扩展名
                    lstFile.add(files[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lstFile;

    }


    /**
     * 获取不带后缀名的文件名
     *
     * @param filename
     * @return
     */
    public static String getRealName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.indexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    //从文件读取文本内容
    public static String readTextFile(File file) throws IOException {
        String text = null;
        InputStream is = null;
        if (null != file) {
            try {
                is = new FileInputStream(file);
                text = readTextInputStream(is);
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
        return text;
    }


    //从输入流读取文本内容
    public static String readTextInputStream(InputStream is) throws IOException {
        if (null == is) return null;
        StringBuffer strBuffer = new StringBuffer();
        String line;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                strBuffer.append(line).append("\r\n");
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return strBuffer.toString();
    }

    public static void unzip(InputStream is, String dir) throws IOException {
        File dest = new File(dir);
        if (!dest.exists()) {
            dest.mkdirs();
        }

        if (!dest.isDirectory())
            throw new IOException("Invalid Unzip destination " + dest);
        if (null == is) {
            throw new IOException("InputStream is null");
        }

        ZipInputStream zip = new ZipInputStream(is);

        ZipEntry ze;
        while ((ze = zip.getNextEntry()) != null) {
            final String path = dest.getAbsolutePath()
                    + File.separator + ze.getName();

            String zeName = ze.getName();
            char cTail = zeName.charAt(zeName.length() - 1);
            if (cTail == File.separatorChar) {
                File file = new File(path);
                if (!file.exists()) {
                    if (!file.mkdirs()) {
                        throw new IOException("Unable to create folder " + file);
                    }
                }
                continue;
            }

            FileOutputStream fout = new FileOutputStream(path);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = zip.read(bytes)) != -1) {
                fout.write(bytes, 0, c);
            }
            zip.closeEntry();
            fout.close();
        }
    }

    public static boolean deleteFile(File f) {
        return f != null && f.exists() && !f.isDirectory()&& f.delete();
    }

    public static boolean deleteFile(String f) {
        if(f != null && f.length() > 0) {
            return deleteFile(new File(f));
        } else {
            return false;
        }
    }

    public static void createDirectory(String path){
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }
        file = null;
    }
}
