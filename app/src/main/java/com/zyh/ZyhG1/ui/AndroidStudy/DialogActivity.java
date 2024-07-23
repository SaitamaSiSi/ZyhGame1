package com.zyh.ZyhG1.ui.AndroidStudy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.model.Fruit;
import com.zyh.ZyhG1.adapter.FruitAdapter2;
import com.zyh.ZyhG1.ui.BaseActivity;

import java.util.ArrayList;
import java.util.Comparator;

public class DialogActivity extends BaseActivity {
    String msg = "Android DialogActivity: ";
    private final ArrayList<Fruit> fruitList = new ArrayList<>();

    /** 当活动第一次被创建时调用 */
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        Log.d(msg, "The onCreate() event");
        setContentView(R.layout.dialog);

        setTitle("Dialog");
        initFruits();

        /* FruitAdapter调用
        ListView listView = findViewById(R.id.dialog_listView);
        if (listView != null) {
            ListAdapter adapter = new FruitAdapter(this, R.layout.fruit_item, fruitList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(this, fruit._name, Toast.LENGTH_SHORT).show();
            });
        }
        **/

        // FruitAdapter2调用
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView recyclerView = findViewById(R.id.dialog_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter2 adapter2 = new FruitAdapter2(fruitList);
        recyclerView.setAdapter(adapter2);
    }

    public void initFruits() {
        for (int i = 0; i < 2; i++) {
            fruitList.add(new Fruit("Apple_00x00", R.drawable.img_pic1_00_00));
            fruitList.add(new Fruit("Apple_00x01", R.drawable.img_pic1_00_01));
            fruitList.add(new Fruit("Apple_00x02", R.drawable.img_pic1_00_02));
            fruitList.add(new Fruit("Apple_01x00", R.drawable.img_pic1_01_00));
            fruitList.add(new Fruit("Apple_01x01", R.drawable.img_pic1_01_01));
            fruitList.add(new Fruit("Apple_01x02", R.drawable.img_pic1_01_02));
            fruitList.add(new Fruit("Apple_02x00", R.drawable.img_pic1_02_00));
            fruitList.add(new Fruit("Apple_02x01", R.drawable.img_pic1_02_01));
            fruitList.add(new Fruit("Apple_02x02", R.drawable.img_pic1_02_02));
        }
        fruitList.sort(Comparator.comparingInt((Fruit a) -> a._imgId));
    }

    public void quit(View view) {
        DialogActivity.this.finish();
    }
}
