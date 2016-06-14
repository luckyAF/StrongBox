package com.luckyaf.strongbox.util;

import android.os.Environment;

import com.luckyaf.strongbox.MyApplication;
import com.luckyaf.strongbox.bean.FileModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.luckyaf.greendao.MyDocument;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/6/4
 */
public class FileUtils {


    // 获取指定目录下的文件与子目录

    public static List<FileModel> getFilesByDir(String dirString) {
        File parentFile;
        List<FileModel> fileModels = new ArrayList<>();
        if (dirString == null || dirString.isEmpty()) {
            parentFile = Environment.getRootDirectory();
        } else {
            parentFile = new File(dirString);
        }

        File fileList[] = parentFile.listFiles();
        if (fileList == null || fileList.length == 0) {
            return null;
        }
        for (File file : fileList) {
            FileModel fileModel = new FileModel();
            if (file.isDirectory()) {
                fileModel.setFileType(fileModel.FILE_DIR);
            } else {
                fileModel.setFileType(fileModel.FILE_FILE);
            }
            fileModel.setName(file.getName());
            fileModel.setPath(file.getPath());
            fileModels.add(fileModel);
        }
        return fileModels;
    }

    // 隐藏文件
    public static boolean hideFile(FileModel fileModel) {
        File fromFile = new File(fileModel.getPath());
        if (!fromFile.exists()) {
            return false;
        }
        String toPathString = getHidePath(fileModel.getPath());
        if (toPathString.isEmpty()) {
            return false;
        }
        File toFile = new File(toPathString + DateUtils.currentTime2String() + getSuffix());
        // 复制
        if (fromFile.renameTo(toFile)) {
            // 插入数据库
            MyDocument myDocument = new MyDocument();
            myDocument.setNewFilename(toFile.getPath());
            myDocument.setOldFileName(fromFile.getPath());
            myDocument.setContentType(fileModel.getContentType());
            myDocument.setKey(DateUtils.currentTime2String());
            myDocument.setEncodeTime(DateUtils.currentTime2String());
            MyApplication.daoMaster.newSession().getMyDocumentDao().insert(myDocument);
        }

        return false;
    }

    // 取消文件隐藏
    public static boolean unHideFile(MyDocument myDocument) {
        if (myDocument != null) {
            File fromFile = new File(myDocument.getNewFilename());
            File toFile = new File(myDocument.getOldFileName());
            MyApplication.daoMaster.newSession().getMyDocumentDao().delete(myDocument);

            // 复制
            if (fromFile.renameTo(toFile)) {
                return true;
            }
        }
        return false;
    }

    // 删除指定文件
    public static boolean deleteAudioByPath(MyDocument myDocument) {
        if (myDocument != null) {
            String pathString = myDocument.getNewFilename();
            MyApplication.daoMaster.newSession().getMyDocumentDao().delete(myDocument);
            File fileFile = new File(pathString);
            if (fileFile != null) {
                return fileFile.delete();
            }
        }
        return false;
    }
    // 删除指定文件
    public static boolean deleteAudioByPath(String pathString) {
        if (pathString == null || pathString.isEmpty()) {
            return false;
        }
        File fileFile = new File(pathString);
        if (fileFile != null) {
            return fileFile.delete();
        }
        return false;
    }

    // 获取所有加密文件列表
    public static List<MyDocument> getHideFiles() {
        List myDocuments = MyApplication.daoMaster.newSession().getMyDocumentDao().loadAll();
        if(myDocuments == null || myDocuments.size() == 0){
            return myDocuments;
        }
        final List<MyDocument> list = checkHideFile(myDocuments);
        //将已消失的隐藏文件删除
/*        if (list.size() > 0) {
            new Thread() {
                @Override
                public void run() {
                    for (MyDocument hideFile : list) {
                        unHideFile(hideFile);
                        deleteAudioByPath(hideFile.getOldFileName());
                    }
                }
            }.start();
        }*/
        return myDocuments;
    }


    /**
     * 转换数据，并屏蔽隐藏文件（以.开始的文件）
     *
     * @param list
     * @return
     */
    public static List<FileModel> transList(List<FileModel> list) {
        List<FileModel> listExt = new ArrayList();
        char dian = '.';
        if (list != null)
            for (FileModel fileModel : list) {
                if (fileModel.getName().charAt(0) != dian)
                    listExt.add(fileModel);
            }
        return listExt;
    }

    /**
     * 检测隐藏文件列表，如果有已经删除的文件，返回提交，更新数据库
     *
     * @param list
     * @return
     */
    public static List<MyDocument> checkHideFile(List<MyDocument> list) {
        List<MyDocument> list1 = new ArrayList<>();
        for (int i = list.size() - 1; i > -1; i--) {
            MyDocument myDocument = list.get(i);
            if (!new File(myDocument.getNewFilename()).exists()) {
                list1.add(myDocument);
                list.remove(i);
            }
        }
        return list1;
    }


    /**
     * 在/sdcard/或/data/data/pkg/files/创建文件夹
     */
    public static File mkDir(String dir) {
        String sdPath = getSDPath();
        if (sdPath == null) {
            sdPath = MyApplication.getInstance().getFilesDir().getPath();
        }
        String path = sdPath + File.separator + dir;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDPath() {
        //判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            //获取跟目录
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }


    /**
     * 获取文件隐藏名
     */
    public static String getHidePath(String pathString) {
      /*  File pathFile = new File(pathString);
        if (pathFile != null) {
            return pathFile.getPath().substring(0, pathFile.getPath().lastIndexOf('/')) + "/.";
        }*/
        return AppSettings.getMyDir() + "/.";
    }

    public static String getSuffix() {
        return ".lock";
    }
}
