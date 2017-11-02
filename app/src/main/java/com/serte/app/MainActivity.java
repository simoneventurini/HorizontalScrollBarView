package com.serte.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.serte.horizontalscrollbar.HorizontalScrollBarView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Adapter mAdapter;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new Adapter(this);
        recyclerView.setAdapter(mAdapter);
        HorizontalScrollBarView horizontalScrollBarView = findViewById(R.id.horizontalScrollView);
        HorizontalScrollBarView horizontalScrollBarViewNoAnim = findViewById(R.id.horizontalScrollViewNoAnim);
        horizontalScrollBarView.setRecyclerView(recyclerView);
        horizontalScrollBarViewNoAnim.setRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dx > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            //Do pagination.. i.e. fetch new data
                            loading = true;
                            //addData();
                            Log.d("TEST", "Endddd");
                            loading = false;
                        }
                    }
                }
            }
        });
        addData();
    }

    private void addData() {
        Integer[] arraysint = new Integer[5];
        Arrays.fill(arraysint, 0);
        mAdapter.updateData(new ArrayList<>(Arrays.asList(arraysint)));
    }
}
