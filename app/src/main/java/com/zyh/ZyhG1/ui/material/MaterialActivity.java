package com.zyh.ZyhG1.ui.material;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.ui.BaseActivity;

import java.util.Objects;

public class MaterialActivity extends BaseActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.material_activity);

        init();
    }

    public void init(){
        Toolbar toolbar = findViewById(R.id.material_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.material_drawLayout);

        androidx.appcompat.app.ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);

        NavigationView navView = findViewById(R.id.material_navView);
        navView.setNavigationItemSelectedListener((v) -> {
            drawerLayout.closeDrawers();
            return true;
        });

        FloatingActionButton fab = findViewById(R.id.material_fab);
        fab.setOnClickListener((v) -> {
            Snackbar.make(v, "Date deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo", view ->
                    {
                        Toast.makeText(MaterialActivity.this, "Data restored", Toast.LENGTH_SHORT).show();
                    })
                .show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
        int itemId = item.getItemId();
        if (itemId == R.id.menu_backup) {
            Toast.makeText(this, "You clicked backup", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.menu_delete) {
            Toast.makeText(this, "You clicked delete", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.menu_settings) {
            Toast.makeText(this, "You clicked settings", Toast.LENGTH_SHORT).show();
        } else if (itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }
}
