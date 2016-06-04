package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class StrongBoxDaoGenerator {
    public static void main(String[] args) throws Exception {
        // 一个用于添加实体（Entity）的模式（Schema）对象。
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
        Schema schema = new Schema(1, "me.luckyaf.greendao");
        addCodeBook(schema);
        addDiary(schema);
        addContact(schema);
        addProgram(schema);
        addDocument(schema);
        new DaoGenerator().generateAll(schema, "/Users/xiangzhongfei/StudioProjects/StrongBox/app/src/main/java-gen");
    }


    /**
     * 添加 密码本表
     **/
    public static void addCodeBook(Schema schema) {
        Entity codeBook = schema.addEntity("CodeBook");
        codeBook.addIdProperty();
        codeBook.addStringProperty("title");
        codeBook.addStringProperty("username");
        codeBook.addStringProperty("password");
        codeBook.addStringProperty("describe");
        codeBook.addStringProperty("updateTime");
        codeBook.addStringProperty("createTime");
        codeBook.addBooleanProperty("canSee");
    }

    /**
    *  添加 日记本表
    */
    public static void addDiary(Schema schema){
        Entity diary = schema.addEntity("Diary");
        diary.addIdProperty();
        diary.addStringProperty("title");
        diary.addStringProperty("weather");
        diary.addStringProperty("content");
        diary.addStringProperty("createTime");
        diary.addStringProperty("updateTime");
    }

    /**
    * 添加 通讯录
    * */
    public static void addContact(Schema schema){
        Entity contact = schema.addEntity("MyContact");
        contact.addIdProperty();
        contact.addStringProperty("name");
        contact.addStringProperty("phone");
        contact.addStringProperty("email");
        contact.addStringProperty("address");
        contact.addStringProperty("remark");
    }

    /**
     * 添加图片表
     *
    * */
    public static void addImage(Schema schema){
        Entity myImage = schema.addEntity("MyImage");
        myImage.addIdProperty();
        myImage.addStringProperty("oldFileName");
        myImage.addStringProperty("newFilename");
        myImage.addStringProperty("key");
        myImage.addStringProperty("describe");
        myImage.addStringProperty("encodeTime");// 加密时间
    }

    /**
     * 添加 文件表
     **/
    public static void addDocument(Schema schema){
        Entity document = schema.addEntity("document");
        document.addIdProperty();
        document.addStringProperty("oldFileName");
        document.addStringProperty("newFilename");
        document.addStringProperty("key");
        document.addStringProperty("encodeTime");
    }

    /**
    *   添加 程序表
    * */
    public static void addProgram(Schema schema){
        Entity program = schema.addEntity("MyApps");
        program.addIdProperty();
        program.addStringProperty("packageName");
        program.addStringProperty("className");
        program.addBooleanProperty("lock");
    }
}
