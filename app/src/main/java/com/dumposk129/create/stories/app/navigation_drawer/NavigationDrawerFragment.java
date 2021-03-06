package com.dumposk129.create.stories.app.navigation_drawer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.create_stories.StoryName;
import com.dumposk129.create.stories.app.quizzes.Quizzes;
import com.dumposk129.create.stories.app.watch.ShowStories;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class NavigationDrawerFragment extends Fragment implements MyAdapter.ClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String PRE_FILE_NAME = "testpre";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private MyAdapter myAdapter;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;


    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    // ListView with recyclerView and set click listener
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        myAdapter = new MyAdapter(getActivity(), getData());
        myAdapter.setClickListener(this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    // Add Data to Navigation Drawer
    public List<Information> getData() {
        List<Information> data = new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.drawer_item_array);
        for (int i = 0; i < titles.length; i++) {
            Information current = new Information();
            current.title = titles[i];
            data.add(current);
        }
        return data;
    }

    // Do Drawer open and Close when users click arrow
    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PRE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PRE_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    // Click go to page
    @Override
    public void itemClicked(View view, int position) {
        switch (position){
            case 0: startActivity(new Intent(getActivity(), ShowStories.class));
                break;
            case 1: startActivity(new Intent(getActivity(), StoryName.class));
                break;
            case 2: startActivity(new Intent(getActivity(), Quizzes.class));
                break;
        }
    }
}