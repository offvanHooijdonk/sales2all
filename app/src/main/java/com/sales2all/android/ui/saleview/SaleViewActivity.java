package com.sales2all.android.ui.saleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.sales2all.android.R;

import butterknife.Bind;

public class SaleViewActivity extends AppCompatActivity {
    public static final String EXTRA_SALE_ID = "extra_sale_id";

    @Bind(R.id.imagePhotoMain)
    private ImageView imageMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_view);


    }
}
