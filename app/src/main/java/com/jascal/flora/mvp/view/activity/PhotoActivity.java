package com.jascal.flora.mvp.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jascal.flora.R;
import com.jascal.flora.base.BaseActivity;
import com.jascal.flora.mvp.PhotoContract;
import com.jascal.flora.mvp.binding.DaggerPhotoComponent;
import com.jascal.flora.mvp.binding.PhotoModule;
import com.jascal.flora.mvp.presenter.PhotoPresenter;
import com.jascal.flora.net.Config;
import com.jascal.flora.net.bean.Feed;
import com.jascal.ophelia_annotation.BindView;
import com.jascal.ophelia_annotation.OnClick;
import com.jascal.ophelia_api.Ophelia;

public class PhotoActivity extends BaseActivity implements PhotoContract.View {
    private PhotoContract.Presenter presenter;

    @BindView(R.id.photo)
    SimpleDraweeView photo;

    @BindView(R.id.excerpt)
    TextView excerpt;

    @OnClick(R.id.back)
    void back(View view) {
        this.finish();
    }

    @OnClick(R.id.share)
    void share(View view) {
        // TODO
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Ophelia.bind(this);
        new PhotoPresenter(this);

        initToolbar();
        initData();

        presenter = DaggerPhotoComponent.builder().photoModule(new PhotoModule(this)).build().getPresenter();
    }

    private void initData() {
        Intent intent = getIntent();
        Feed feed = intent.getParcelableExtra("feed");

        Uri uri = Uri.parse(Config.BASE_IMAGE_PATH + feed.getImages().get(0).getUser_id() +
                Config.BASE_IMAGE_TAIL + feed.getImages().get(0).getImg_id() + ".jpg");

        photo.setImageURI(uri);
        excerpt.setText(feed.getExcerpt());
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void setPresenter(PhotoContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
