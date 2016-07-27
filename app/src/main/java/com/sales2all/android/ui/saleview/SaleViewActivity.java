package com.sales2all.android.ui.saleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sales2all.android.R;
import com.sales2all.android.helper.FSHelper;
import com.sales2all.android.model.SaleBean;
import com.sales2all.android.ui.views.AspectRatioImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SaleViewActivity extends AppCompatActivity {
    public static final String EXTRA_SALE_ID = "extra_sale_id";

    private Context ctx;
    private SaleBean saleBean;

    @Bind(R.id.imagePhotoMain)
    AspectRatioImageView imageMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_view);
        ctx = SaleViewActivity.this;

        ButterKnife.bind(this);

        Long saleId = getIntent().getExtras().getLong(EXTRA_SALE_ID);

        saleBean = SaleBean.findById(SaleBean.class, saleId);

        if (!saleBean.getImages().isEmpty()) {
            // TODO if the file is still loading - must handle this and show progress, then show image when the file is loaded
            String pathToImage = FSHelper.getPicturesPath(ctx) + saleBean.getImages().get(0).getFileName();
            Bitmap imageBitmap = BitmapFactory.decodeFile(pathToImage);
            imageMain.setImageBitmap(imageBitmap);

            imageMain.setAspectRatio(((float) imageBitmap.getHeight()) / imageBitmap.getWidth());
        } else {
            // TODO should be replaced with a special ImageView with an icon
            imageMain.setImageDrawable(ctx.getDrawable(R.drawable.ic_photo_camera_black_48dp));
        }
    }
}
