package com.stay4cold.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.stay4cold.okrecyclerview.OkRecyclerView;
import com.stay4cold.okrecyclerview.holder.HeaderView;
import com.stay4cold.okrecyclerview.holder.OnFooterListener;
import com.stay4cold.okrecyclerview.holder.OnLoaderListener;
import com.stay4cold.okrecyclerview.state.FooterState;
import com.stay4cold.okrecyclerview.state.LoaderState;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRv;

    private Ad adapter;

    private OkRecyclerView agent;

    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mRv = (RecyclerView) findViewById(R.id.rv);

        mRv.setLayoutManager(new GridLayoutManager(this, 2));

        mRv.setAdapter(adapter = new Ad());

        agent = new OkRecyclerView.Builder(mRv).addHeader(new HeaderView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(parent.getContext()).inflate(R.layout.example1, null);
            }

            @Override
            public void onBindView(View view) {

            }
        })
            .setLoaderTargetView((ViewGroup) mRv.getParent())
            .setOnFooterListener(new OnFooterListener() {
                @Override
                public void onFooterClickListener(FooterState state) {
                    if (state == FooterState.Error) {
                        agent.setLoaderState(LoaderState.Empty);
                    } else if (state == FooterState.TheEnd) {
                        agent.setFooterState(FooterState.Loading);
                    }
                }
            })
            .setOnLoaderListener(new OnLoaderListener() {
                @Override
                public void onLoaderClick(LoaderState state) {
                    if (state == LoaderState.Empty) {
                        agent.setLoaderState(state.Error);
                    } else if (state == LoaderState.Error) {
                        agent.setLoaderState(LoaderState.Normal);
                    }
                }
            })
            .setOnLoadmoreListener(new OkRecyclerView.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mRv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 50; i++) {
                                data.add("Demo" + data.size());
                                adapter.notifyDataSetChanged();
                            }
                            if (data.size() > 250) {
                                agent.setFooterState(FooterState.Error);
                            } else if (data.size() > 180) {
                                agent.setFooterState(FooterState.TheEnd);
                            } else {
                                agent.setFooterState(FooterState.Normal);
                            }
                        }
                    }, 2000);
                }
            })
            .build();

        for (int i = 0; i < 10; i++) {
            data.add("Demo" + i);
            agent.setFooterState(FooterState.TheEnd);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =
            new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            for (int i = 0; i < 10; i++) {
                data.add("Demo" + i);
                adapter.notifyDataSetChanged();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class Ad extends RecyclerView.Adapter<Ad.Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(
                LayoutInflater.from(MainActivity.this).inflate(R.layout.example, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.tv.setText(data.get(position));
        }

        @Override
        public int getItemCount() {

            return data.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            public TextView tv;

            public Holder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.tv);
            }
        }
    }
}
