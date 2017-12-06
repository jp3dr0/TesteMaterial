package com.jp3dr0.testematerial;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwitchTesteActivity extends AppCompatActivity {

    int[] photos = new int[]{R.drawable.gallardo, R.drawable.vyron, R.drawable.corvette, R.drawable.paganni_zonda, R.drawable.porsche_911, R.drawable.bmw_720, R.drawable.db77, R.drawable.mustang, R.drawable.camaro, R.drawable.ct6};
    String imageUrl[] = new String[]{"http://goo.gl/gEgYUd","http://oconciergeonline.com.br/wp-content/uploads/2017/10/leo-santana-1.jpg",};
    String names[] = new String[]{"Gallardo", "Vyron", "Corvette", "Pagani Zonda", "Porsche 911 Carrera", "BMW 720i", "DB77", "Mustang", "Camaro", "CT6"};

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_teste);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        List list = getList();
        mAdapter = new RecyclerAdapter(this, list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));

        //Glide.with(context).load(url).centerCrop().placeholder(R.drawable.loading_spinner).into(myImageView);
    }

    private List getList() {
        List list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            ItemModel model = new ItemModel();
            model.setName(names[i]);
            //Log.i("LOG", "getList() fora do if");

            if (photos.length == names.length) {
                model.setPhoto(photos[i]);
                try {
                    model.setImagePath(imageUrl[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (imageUrl.length == names.length){
                model.setImagePath(imageUrl[i]);
                try {
                    model.setPhoto(photos[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            list.add(model);
        }
        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        //menu.findItem(R.id.action_search).setIcon(R.drawable.ic_menu_camera);

        if (!isSwitched) {
            menu.findItem(R.id.action_change_recycler).setIcon(R.drawable.ic_view_quilt_black_24dp);
        }
        else {
            menu.findItem(R.id.action_change_recycler).setIcon(R.drawable.ic_view_stream_black_24dp);
        }

        return true;
    }

    private boolean isSwitched = true;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_change_recycler:
                supportInvalidateOptionsMenu();
                isSwitched = mAdapter.toggleItemViewType();
                mRecyclerView.setLayoutManager(isSwitched ? new GridLayoutManager(this, 2) : new LinearLayoutManager(this));
                //mRecyclerView.setLayoutManager(isSwitched ? new LinearLayoutManager(this) : new GridLayoutManager(this, 2));
                mAdapter.notifyDataSetChanged();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
