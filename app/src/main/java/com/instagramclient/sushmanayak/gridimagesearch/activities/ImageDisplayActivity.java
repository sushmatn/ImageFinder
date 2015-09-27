package com.instagramclient.sushmanayak.gridimagesearch.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.instagramclient.sushmanayak.gridimagesearch.R;
import com.instagramclient.sushmanayak.gridimagesearch.helpers.Helper;
import com.instagramclient.sushmanayak.gridimagesearch.helpers.TouchImageView;
import com.instagramclient.sushmanayak.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends AppCompatActivity {

    TouchImageView ivImageResult;
    ImageResult mImageResult;
    private ShareActionProvider miShareAction;
    private Uri mLocalImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        ivImageResult = (TouchImageView) findViewById(R.id.ivImageResult);
        mImageResult = getIntent().getParcelableExtra(SearchActivity.CURRENT_IMAGE);
        if (mImageResult.getFullURL() != null)
            Picasso.with(this).load(mImageResult.getFullURL()).placeholder(R.drawable.loading)
                    .error(R.drawable.image_unavailable).into(ivImageResult, new Callback() {
                @Override
                public void onSuccess() {
                    setupShareIntent();
                }

                @Override
                public void onError() {
                }
            });

        getSupportActionBar().setTitle(mImageResult.getContent());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_display, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        // Fetch reference to the share action provider
        miShareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        if (mLocalImageUri != null)
            setupShareIntent();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.action_view_site:
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mImageResult.getPageURL()));
                startActivity(myIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    public void setupShareIntent() {
        // Get access to the URI for the bitmap
        mLocalImageUri = Helper.getLocalBitmapUri(this,ivImageResult);
        if (mLocalImageUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, mImageResult.getContent());
            shareIntent.putExtra(Intent.EXTRA_STREAM, mLocalImageUri);
            shareIntent.setType("image/*");

            if (miShareAction != null)
                miShareAction.setShareIntent(shareIntent);
        }
    }
}
