package com.hongniu.moduleorder.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.moduleorder.R;
import com.sang.thirdlibrary.map.ui.MapLoactionFragment;

/**
 * 定位，选择当前所在点的Activity
 */
@Route(path = ArouterParamOrder.activity_map_loaction)
public class OrderMapLocationActivity extends AppCompatActivity {

    private MapLoactionFragment mapLoactionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_map_location_ativity);
        mapLoactionFragment = new MapLoactionFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, mapLoactionFragment).commit();
    }











}
