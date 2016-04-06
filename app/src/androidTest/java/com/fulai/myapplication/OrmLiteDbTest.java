package com.fulai.myapplication;

import android.test.AndroidTestCase;
import android.util.Log;

import com.fulai.myapplication.bean.User;
import com.fulai.myapplication.db.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import junit.framework.Assert;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by fulai on 2016/4/5.
 */
public class OrmLiteDbTest extends AndroidTestCase {
    private static final String TAG = "OrmLiteDbTest";

    public void testAddUser() {
        User u1 = new User("xiao1", "3b");
        DatabaseHelper helper = DatabaseHelper.getInstance(getContext());
        try {
            helper.getUserDao().create(u1);
            u1 = new User("xiao2", "4b");
            helper.getUserDao().create(u1);
            u1 = new User("xiao3", "5b");
            helper.getUserDao().create(u1);
            u1 = new User("xiao4", "6b");
            helper.getUserDao().create(u1);
            u1 = new User("xiao5", "7b");
            helper.getUserDao().create(u1);
            testList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testAddDo(){
        DatabaseHelper helper = DatabaseHelper.getInstance(getContext());
        Dao dao = helper.getDao(User.class);

    }

    public void testDeleteUser() throws SQLException {
        DatabaseHelper helper = DatabaseHelper.getInstance(getContext());
        Dao<User, Integer> userDao = helper.getUserDao();
        userDao.deleteById(2);
    }

    public void testUpdateUser() throws SQLException {
        DatabaseHelper helper = DatabaseHelper.getInstance(getContext());
        Dao<User, Integer> userDao = helper.getUserDao();
        testList();
        User u1 = new User("zhy-android", "2B青年");
        u1.setId(1);
        helper.getUserDao().update(u1);
        testList();
    }


    public void testList() throws SQLException {
        DatabaseHelper helper = DatabaseHelper.getInstance(getContext());
        User u1 = new User("zhy-android", "2B青年");
        u1.setId(2);
        List<User> users = helper.getUserDao().queryForAll();
        Log.e("TAG", users.toString());

    }

    public void testadds() {
        System.out.println("goodasdasdasd");
        Log.i(TAG,"goodasdasdasd");
        //Assert.assertEquals(5, 6);
    }
}
