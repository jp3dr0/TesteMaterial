package com.jp3dr0.testematerial;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.jp3dr0.testematerial.adapters.CarAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public boolean isProductViewAsList = true;

    public TabLayout tablayout;
    public Toolbar toolbar;
    public Toolbar toolbar2;
    public Toolbar toolbar3;
    public ConstraintLayout myBackground;

    public List<Car> getSetCarList(int qtd){
        String[] models = new String[]{"Gallardo", "Vyron", "Corvette", "Pagani Zonda", "Porsche 911 Carrera", "BMW 720i", "DB77", "Mustang", "Camaro", "CT6"};
        String[] brands = new String[]{"Lamborghini", " bugatti", "Chevrolet", "Pagani", "Porsche", "BMW", "Aston Martin", "Ford", "Chevrolet", "Cadillac"};
        int[] photos = new int[]{R.drawable.gallardo, R.drawable.vyron, R.drawable.corvette, R.drawable.paganni_zonda, R.drawable.porsche_911, R.drawable.bmw_720, R.drawable.db77, R.drawable.mustang, R.drawable.camaro, R.drawable.ct6};
        List<Car> listAux = new ArrayList<>();

        for(int i = 0; i < qtd; i++){
            Car c = new Car( models[i % models.length], brands[ i % brands.length ], photos[i % models.length] );
            listAux.add(c);
        }
        return(listAux);
    }

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myBackground = (ConstraintLayout) findViewById(R.id.relativeLayout3);
        tablayout = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        //toolbar2.setTitle("china cds");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //toolbar3.setTitle("Main Activity");
        //mToolbar.setSubtitle("just a subtitle");
        //mToolbar.setLogo(R.drawable.ic_launcher);

        toolbar3 = (Toolbar) findViewById(R.id.toolbar3);
        //toolbar3 = (AppBarLayout) findViewById(R.id.inc_tb_bottom);
        toolbar3.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return true;
            }
        });
        //toolbar3.inflateMenu(R.menu.menu_bottom);

        toolbar3.findViewById(R.id.iv_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Settings pressed", Toast.LENGTH_SHORT).show();
            }
        });

        mList = getSetCarList(10);
        adapter = new CarAdapter(this,this, mList);

        // FRAGMENT

        /*

        String msg;
        msg = isSwitched ? "true" : "false";

        Bundle bundleObject = new Bundle();
        bundleObject.putString("data", msg);

        CarFragment frag = (CarFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");

        if(frag == null) {
            Toast.makeText(this, "frag é null", Toast.LENGTH_SHORT).show();
            frag = new CarFragment();

            frag.setArguments(bundleObject);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, frag, "mainFrag");
            ft.commit();
        }

        */

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart()", Toast.LENGTH_SHORT).show();
    }

    private boolean long_press_item_on_fragment = false;

    public void setLong_press_item_on_fragment(boolean long_press_item_on_fragment) {
        this.long_press_item_on_fragment = long_press_item_on_fragment;
    }

    private boolean doubleBackToExitPressedOnce = false;

    private List<Car> itens_selecionados_no_momento = new ArrayList<Car>();

    public List<Car> getItens_selecionados_no_momento() {
        return itens_selecionados_no_momento;
    }

    public void setItens_selecionados_no_momento(List<Car> itens_selecionados_no_momento) {
        this.itens_selecionados_no_momento = itens_selecionados_no_momento;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "long_press_item_on_fragment: " + String.valueOf(long_press_item_on_fragment), Toast.LENGTH_SHORT).show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if(long_press_item_on_fragment){
            //Toast.makeText(this, "coracao)", Toast.LENGTH_SHORT).show();
            itens_selecionados_no_momento.clear();
            toolbar2.setVisibility(Toolbar.GONE);
            toolbar.setVisibility(Toolbar.VISIBLE);
            setSupportActionBar(toolbar);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                Window window = this.getWindow();

                // clear FLAG_TRANSLUCENT_STATUS flag:
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                }

                // finally change the color
                window.setStatusBarColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));
            }

            long_press_item_on_fragment = !long_press_item_on_fragment;
        }

        else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }


                this.doubleBackToExitPressedOnce = true;

                Toast.makeText(this, "Realmente deseja sair? Clique 'voltar' novamente", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 2000);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(toolbar2.getVisibility() == Toolbar.GONE && toolbar.getVisibility() == Toolbar.VISIBLE){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main, menu);

            //menu.findItem(R.id.action_search).setIcon(R.drawable.ic_menu_camera);

            if (!isSwitched) {
                menu.findItem(R.id.action_change_recycler).setIcon(R.drawable.ic_view_quilt_black_24dp);
            }
            else {
                menu.findItem(R.id.action_change_recycler).setIcon(R.drawable.ic_view_stream_black_24dp);
            }
        }
        else if(toolbar.getVisibility() == Toolbar.GONE && toolbar2.getVisibility() == Toolbar.VISIBLE){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_clicked_item, menu);

            //menu.findItem(R.id.action_devolucao).getIcon().set

            //menu.findItem(R.id.action_devolucao).getIcon().setColorFilter(R.color.selected_item_icon_color);
        }

        return true;
    }

    private boolean isSwitched = true;
    List<Car> mList;
    CarAdapter adapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                itens_selecionados_no_momento.clear();
                tablayout.setVisibility(View.VISIBLE);
                toolbar2.setVisibility(Toolbar.GONE);
                toolbar.setVisibility(Toolbar.VISIBLE);
                setSupportActionBar(toolbar);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                    Window window = this.getWindow();

                    // clear FLAG_TRANSLUCENT_STATUS flag:
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    }

                    // finally change the color
                    window.setStatusBarColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));
                }

                //finish();
                break;
            case R.id.action_search:
                //finish();
                break;
            case R.id.action_change_recycler:

                Toast.makeText(this, "fui reconhecido pela MainActivity", Toast.LENGTH_SHORT).show();

                supportInvalidateOptionsMenu();

                isSwitched = adapter.toggleItemViewType();

                String msg;
                msg = isSwitched ? "true" : "false";

                CarFragment frag = new CarFragment();
                Bundle bundleObject = new Bundle();
                bundleObject.putString("data", msg);
                frag.setArguments(bundleObject);

                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
				/*
				 * IMPORTANT: We use the "root frame" defined in
				 * "root_fragment.xml" as the reference to replace fragment
				 */
                trans.replace(R.id.root_frame, frag);

				/*
				 * IMPORTANT: The following lines allow us to add the fragment
				 * to the stack and return to it later, by pressing back
				 */
                //trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                //trans.addToBackStack(null);

                trans.commit();

                /*
                String msg;
                msg = isSwitched ? "true" : "false";

                CarFragment frag = (CarFragment) getSupportFragmentManager().findFragmentById(R.id.container);

                frag.Teste("vou fazer promessa");

                mSectionsPagerAdapter.notifyDataSetChanged();

                CarFragment frag = new CarFragment();

                Bundle bundleObject = new Bundle();
                bundleObject.putString("data", msg);
                frag.setArguments(bundleObject);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, frag);
                ft.commit();

                */

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_favoritos) {
            // Handle the camera action
        } else if (id == R.id.nav_recentes) {

        } else if (id == R.id.nav_adicionar) {

        } else if (id == R.id.nav_remover) {

        } else if (id == R.id.nav_retiradas) {

        } else if (id == R.id.nav_devolucao) {

        } else if (id == R.id.nav_vidrarias) {

        } else if (id == R.id.nav_reagentes) {

        } else if (id == R.id.nav_sair) {
            Intent secondActivity = new Intent(this, SwitchTesteActivity.class);
            startActivity(secondActivity);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("dorgival dantas");
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        private boolean isSwitched2 = false;

        public boolean atualizar(boolean isSwitched){
            isSwitched2 = !isSwitched2;
            return true;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            if (position == 0)
                return new RootFragment();
            else
                //return new StaticFragment();
                return PlaceholderFragment.newInstance(position + 1);

            /*
            switch (position) {
                case 0:
                    String msg;
                    msg = isSwitched ? "true" : "false";

                    CarFragment frag = new CarFragment();
                    Bundle bundleObject = new Bundle();
                    bundleObject.putString("data", msg);
                    frag.setArguments(bundleObject);
                    return frag;
                case 1:
                    return PlaceholderFragment.newInstance(position + 1);
                case 2:
                    return PlaceholderFragment.newInstance(position + 1);
            }
            return PlaceholderFragment.newInstance(position + 1);
            */
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
