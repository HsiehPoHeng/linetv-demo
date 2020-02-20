package com.linetv.demo.ui.dramas;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linetv.demo.DramasSampleApplication;
import com.linetv.demo.R;
import com.linetv.demo.data.DataCRUD;
import com.linetv.demo.data.db.DramasTable;
import com.linetv.demo.ui.BaseActivity;
import com.linetv.demo.util.Util;

import static com.linetv.demo.Constants.KEY_EXTRA;

public class  DramasDetailsActivity extends BaseActivity {

    private DramasDetailsActivity pthis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dramas_details);
        pthis = this;
        Long Drama_id = null;
        if (this.getIntent().hasExtra(KEY_EXTRA)) {
            Drama_id = this.getIntent().getLongExtra(KEY_EXTRA,0);
        }
        init(Drama_id);
    }

    private void init(Long drama_id) {
        DramasSampleApplication App = ((DramasSampleApplication) pthis.getApplicationContext());
        DataCRUD crud = new DataCRUD(App);
        DramasTable dramasRecord = crud.selectRecord(drama_id);
        if (dramasRecord != null){
            TextView txtRating = findViewById(R.id.txtRating);
            txtRating.setText(String.valueOf(dramasRecord.getRating()));
            TextView txtTotalViews = findViewById(R.id.txtTotalViews);
            txtTotalViews.setText(String.valueOf(dramasRecord.getTotal_views()));
            TextView txtCreated_at = findViewById(R.id.txtCreated_at);
            txtCreated_at.setText(dramasRecord.getCreated_at());
            TextView txtName = findViewById(R.id.txtName);
            txtName.setText(dramasRecord.getName());
            ImageView imageView = findViewById(R.id.imageView);
            if(!Util.isNetworkAvailable(pthis)){
                Bitmap tmpbitmap = Util.getPic(pthis, drama_id + ".jpg");
                imageView.setImageBitmap(tmpbitmap);
            }else{
                Glide.with(pthis)
                        .asBitmap()
                        .load(dramasRecord.getThumb_url())
                        .into(imageView);
            }
        }
    }

}
