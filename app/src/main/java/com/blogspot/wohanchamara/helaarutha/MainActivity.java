package com.blogspot.wohanchamara.helaarutha;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DictionaryFragment.SendDataInterface {

    FragmentManager fragmentManager = getSupportFragmentManager();
    private DrawerLayout drawer;
    DictionaryFragment dictionaryFragment;
    //BookmarkFragment bookmarkFragment;
    AboutFragment aboutFragment;
    AddNewFragment addNewFragment;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        dictionaryFragment = new DictionaryFragment();
        //bookmarkFragment = new BookmarkFragment();
        aboutFragment = new AboutFragment();
        addNewFragment = new AddNewFragment();
        goToFragment(dictionaryFragment, true);

        dictionaryFragment.setOnFragmentListener(new FragmentListner() {
            @Override
            public void onItemClick(String value) {
                goToFragment(DetailFragment.getNewInstance(value), false);
            }
        });
        /*bookmarkFragment.setOnFragmentListener(new FragmentListner() {
            @Override
            public void onItemClick(String value) {
                goToFragment(DetailFragment.getNewInstance(value), false);
            }
        });*/

        EditText edit_search = findViewById(R.id.edit_search);
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //dictionaryFragment.filterValue(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            /*case R.id.nav_bookmark:
                String activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
                if (!activeFragment.equals(BookmarkFragment.class.getSimpleName())) {
                    goToFragment(bookmarkFragment, false);
                }
                break;*/
            case R.id.nav_about_us:
                String activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
                if (!activeFragment.equals(AboutFragment.class.getSimpleName())) {
                    goToFragment(aboutFragment, false);
                }
                break;
            case R.id.nav_new:
                activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
                if (!activeFragment.equals(AddNewFragment.class.getSimpleName())) {
                    goToFragment(addNewFragment, false);
                }
                break;
            case R.id.nav_ad:
                Toast.makeText(this, "Thank you for trying to help us! ❤ \nBut still we have not implement this feature", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_rate:
                Toast.makeText(this, "Thank you for trying to rate this App! ❤ \nBut still we have not implement this feature", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Thank you for trying to share this App! ❤ \nBut still we have not implement this feature", Toast.LENGTH_LONG).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        String activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } /*else if(!activeFragment.equals(DictionaryFragment.class.getSimpleName())) {
            goToFragment(dictionaryFragment, false);
        }*/ else {
            super.onBackPressed();
        }
    }

    //This method created by me for future development
    public boolean OnNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        /*if (id == R.id.nav_bookmark){
            goToFragment(bookmarkFragment, false);
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void goToFragment (Fragment fragment, boolean isTop){

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (!isTop)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
        /*if (activeFragment.equals(BookmarkFragment.class.getSimpleName())){
            toolbar.findViewById(R.id.edit_search).setVisibility(View.GONE);
            toolbar.setTitle("Bookmark");
        } else*/ if (activeFragment.equals(DetailFragment.class.getSimpleName())){
            toolbar.findViewById(R.id.edit_search).setVisibility(View.GONE);
            toolbar.setTitle("Description");
        } else if (activeFragment.equals(AboutFragment.class.getSimpleName())){
            toolbar.findViewById(R.id.edit_search).setVisibility(View.GONE);
            toolbar.setTitle("About Us");
        } else if (activeFragment.equals(AddNewFragment.class.getSimpleName())){
            toolbar.findViewById(R.id.edit_search).setVisibility(View.GONE);
            toolbar.setTitle("Add new Word");
        } else if (activeFragment.equals(EditWordFragment.class.getSimpleName())){
            toolbar.findViewById(R.id.edit_search).setVisibility(View.GONE);
            toolbar.setTitle("Edit Word");
        }
        else {
            toolbar.findViewById(R.id.edit_search).setVisibility(View.VISIBLE);
            toolbar.setTitle("");
        }
        return true;
    }

    @Override
    public void sendData(String word, String mean) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("WORD", word);
        bundle.putString("MEAN", mean);
        detailFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container,detailFragment)
        .addToBackStack(null).commit();
    }
}