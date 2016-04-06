package com.example;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExanpleDooGenerator {
    public static void main(String[] args) throws Exception {
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
        Schema schema = new Schema(1, "com.fulai.myapplication");
        schema.enableActiveEntitiesByDefault();
        schema.enableKeepSectionsByDefault();
        addNote(schema);
        new DaoGenerator().generateAll(schema, "E:\\project\\MyApplication3\\app\\src\\main\\java-gen");
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.setTableName("NOTE");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }
}
