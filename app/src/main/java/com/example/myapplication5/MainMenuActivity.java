package com.example.myapplication5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class MainMenuActivity extends AppCompatActivity implements ItemListFragment.FragmentListener, ItemFragmentInterface {

    String username;

    public static final String USERNAME_EXTRA = "username";

    ItemManager itemManager = new ItemManager(this);

    ItemListFragment allListFragment;
    ElectronicListFragment electronicListFragment;
    CarListFragment carListFragment;
    ClothesListFragment clothesListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        Toolbar toolbar = findViewById(R.id.main_menu_toolbar);
        setSupportActionBar(toolbar);
        username = getIntent().getStringExtra(USERNAME_EXTRA);
        if(username == null){
            UserManager userManager = new UserManager(this);
            username = userManager.getLastRequestedUsername();
        }
        allListFragment = new ItemListFragment();
        allListFragment.setFragmentListener(this);
        electronicListFragment = new ElectronicListFragment();
        electronicListFragment.setListener(this);
        carListFragment = new CarListFragment();
        carListFragment.setListener(this);
        clothesListFragment = new ClothesListFragment();
        clothesListFragment.setListener(this);
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the app bar.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_offers:
                Intent intent = new Intent(getApplicationContext(), UserOffersActivity.class);
                intent.putExtra(UserOffersActivity.USERNAME_EXTRA,username);
                startActivity(intent);
                return true;
            case R.id.add_item:
                Intent intent2 = new Intent(getApplicationContext(),ItemAddActivity.class);
                intent2.putExtra(ItemAddActivity.USERNAME_EXTRA,username);
                startActivity(intent2);
                return true;
            case R.id.my_inventory:
                Intent intent4 = new Intent(getApplicationContext(),UserInventoryActivity.class);
                intent4.putExtra(UserInventoryActivity.USERNAME_EXTRA,username);
                startActivity(intent4);
                return true;
            case R.id.my_profile:
                Intent intent3 = new Intent(getApplicationContext(),MyProfileActivity.class);
                intent3.putExtra(MyProfileActivity.USERNAME_EXTRA,username);
                startActivity(intent3);
                return true;
            case R.id.menu_exit:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to return to login page?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                username = "";
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void handleItemClickedInMainActivity(int position) {
        Intent mainMenuIntent = new Intent(getApplicationContext(), ItemDetailActivity.class);
        int itemId = itemManager.getItem(position).getItemId();
        mainMenuIntent.putExtra(ItemDetailActivity.ITEM_NO_EXTRA, itemId);
        mainMenuIntent.putExtra(ItemDetailActivity.USERNAME_EXTRA,username);
        startActivity(mainMenuIntent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to return to login page?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        username = "";
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    @Override
    public void spesificItemClicked(int position, String itemType) {
        int itemId = itemManager.getItemListByType(itemType).get(position).getItemId();
        Intent intent = new Intent(getApplicationContext(), ItemDetailActivity.class);
        intent.putExtra(ItemDetailActivity.ITEM_NO_EXTRA, itemId);
        intent.putExtra(ItemDetailActivity.USERNAME_EXTRA,username);
        startActivity(intent);
    }

    public void fabClicked(View view) {
        Intent intent = new Intent(this,ProfileUserModification.class);
        intent.putExtra(ProfileUserModification.EXTRA_MESSAGE,"BALANCE");
        intent.putExtra(ProfileUserModification.EXTRA_USERNAME,username);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("username",username);
    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter{

        public SectionsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return allListFragment;
                case 1:
                    return electronicListFragment;
                case 2:
                    return carListFragment;
                case 3:
                    return clothesListFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return getResources().getText(R.string.all_items);
                case 1:
                    return getResources().getText(R.string.electronic);
                case 2:
                    return getResources().getText(R.string.car);
                case 3:
                    return getResources().getText(R.string.clothes);
            }
            return super.getPageTitle(position);
        }
    }
}
