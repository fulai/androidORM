package com.fulai.myapplication.realm;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fulai.myapplication.MyApplication;
import com.fulai.myapplication.R;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etContent;
    private Button btnAdd, btnSearch, btnUpdate;
    private ListView lv;
    private MyRealmBaseAdapter mMyRealmBaseAdapter;
    private RealmResults<Country> mRealmResults;
    private Realm realm;
    private PopupMenu popupMenu;
    private Menu menu;


    /**
     * 一个Realm相当于一个SQLite数据库
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm);
        initViews();
        initRealm();
    }

    @Override
    protected void onStart() {
        super.onStart();
        search(null);
    }

    private void initViews() {

        mMyRealmBaseAdapter = new MyRealmBaseAdapter(this);
        etContent = (EditText) findViewById(R.id.et_content);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        lv = (ListView) findViewById(R.id.lv_content);
        lv.setAdapter(mMyRealmBaseAdapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                popupMenu = new PopupMenu(RealmActivity.this, view);
                menu = popupMenu.getMenu();
                menu.add(Menu.NONE, Menu.FIRST + 0, 0, "删除");
                menu.add(Menu.NONE, Menu.FIRST + 1, 1, "编辑");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Country country = mRealmResults.get(position);
                        switch (item.getItemId()) {
                            case Menu.FIRST + 0:
                                Toast.makeText(RealmActivity.this, "删除",
                                        Toast.LENGTH_LONG).show();

                                realm.beginTransaction();
                                RealmResults<Country> mRealm = realm.where(Country.class).equalTo("name", country.getName()).findAll();
                                mRealm.remove(0);
                                realm.commitTransaction();
                                search(null);
                                break;
                            case Menu.FIRST + 1:
                                Toast.makeText(RealmActivity.this, "编辑",
                                        Toast.LENGTH_LONG).show();
                                realm.beginTransaction();
                                RealmResults<Country> mRealms = realm.where(Country.class).equalTo("name", country.getName()).findAll();
                                Country country1 = mRealms.get(0);
                                country1.setCode("123123");
                                country1.setPopulation(123123);
                                country1.setName("American");
                                realm.commitTransaction();
                                search(null);

                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    private void initRealm() {
        realm = MyApplication.getRealm();

//        Country country2 = new Country();
//        country2.setName("American");
//        country2.setPopulation(5166810);
//        country2.setCode("No");
//        realm.beginTransaction();
//        realm.copyToRealm(country2);
//        realm.commitTransaction();

        //向app中添加另一个Realm
        //Realm.getInstance(new RealmConfiguration.Builder(this).name("myOtherRealm.realm").build());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                add();
                break;
            case R.id.btn_search:
                String strContent = etContent.getText() == null ? "" : etContent.getText().toString();
                if (TextUtils.isEmpty(strContent)) {
                    search(null);
                } else {
                    search(strContent);
                }

                break;
            case R.id.btn_update:
                update();
                break;
        }
    }

    private void update() {

    }

    public void delete() {

    }

    private void search(String strContent) {

        if (!TextUtils.isEmpty(strContent)) {
            //根据条件查询
            RealmQuery<Country> getCounts = realm.where(Country.class).equalTo("name", strContent);
            mRealmResults = getCounts.findAll();
            mMyRealmBaseAdapter.reloadData(mRealmResults);

        } else {
            //查询全部
            mRealmResults = realm.where(Country.class).findAll();
            mMyRealmBaseAdapter.reloadData(mRealmResults);
        }
    }

    private void add() {
        String strContent = etContent.getText() == null ? "" : etContent.getText().toString();
        if (TextUtils.isEmpty(strContent)) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        etContent.setText("");
        //创建一个叫做 default.realm的Realm文件
        realm.beginTransaction();
        //mCountry并不是用Country类的构造器创建的。对于一个Realm来说，管理一个RealmObject的实例，这个实例必须用createObject方法创建
        Country mCountry = realm.createObject(Country.class);
        mCountry.setName("China:" + strContent);
        mCountry.setPopulation(5166800);
        mCountry.setCode("Yes:" + strContent);
        realm.commitTransaction();
        search(null);
    }

    private class MyRealmBaseAdapter extends RealmBaseAdapter<Country> {


        public MyRealmBaseAdapter(Context context) {
            super(context, null, false);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.listitem, null);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.code = (TextView) convertView.findViewById(R.id.tv_code);
                holder.population = (TextView) convertView.findViewById(R.id.tv_population);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Country country = getItem(position);
            holder.name.setText(country.getName());
            holder.code.setText(country.getCode());
            holder.population.setText(String.valueOf(country.getPopulation()));
            return convertView;
        }

        public void reloadData(RealmResults<Country> country) {
            this.realmResults = country;
            notifyDataSetChanged();
        }

    }

    private class ViewHolder {
        private TextView name;
        private TextView code;
        private TextView population;
    }
}
