package com.instagramclient.sushmanayak.gridimagesearch.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.instagramclient.sushmanayak.gridimagesearch.fragments.FilterSettingsFragment;
import com.instagramclient.sushmanayak.gridimagesearch.adapters.ImageResultsAdapter;
import com.instagramclient.sushmanayak.gridimagesearch.helpers.EndlessScrollListener;
import com.instagramclient.sushmanayak.gridimagesearch.helpers.Helper;
import com.instagramclient.sushmanayak.gridimagesearch.models.ImageResult;
import com.instagramclient.sushmanayak.gridimagesearch.R;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements FilterSettingsFragment.SettingsChangedListener {

    private static final String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&imgc=color";
    private static final String QUERY_PARAM = "q";
    private static final String START_PARAM = "start";
    private static final String SIZE_PARAM = "imgsz";
    private static final String TYPE_PARAM = "imgtype";
    private static final String COLOR_PARAM = "imgcolor";
    private static final String SITE_PARAM = "as_sitesearch";
    private static final String IMAGE_LIST = "ImageResults";
    private static final String SCROLL_POSITION = "ScrollPosition";
    public static final String CURRENT_IMAGE = "CurrentImage";
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter imageResultsAdapter;
    private static final int RESULT_SIZE = 8;
    private static final int MAX_NO_PAGES = 8;
    MenuItem miActionProgressItem;
    private static String mQuery;
    private StaggeredGridView gvResults;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();

        imageResults = new ArrayList<>();
        imageResultsAdapter = new ImageResultsAdapter(this, imageResults);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setShowHideAnimationEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Attach the listener to the AdapterView onCreate
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (page <= MAX_NO_PAGES && imageResults.size() <= MAX_NO_PAGES * RESULT_SIZE) {
                    fetchImageData();
                }
            }
        });
        gvResults.setAdapter(imageResultsAdapter);

        // Create and set the EmptyView
        TextView emptyView = (TextView)findViewById(R.id.emptyElement);
        gvResults.setEmptyView(emptyView);
    }

    private void setupViews() {

        gvResults = (StaggeredGridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SearchActivity.this, view, "transition");
                Intent intent = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                ImageResult result = imageResults.get(position);
                intent.putExtra(CURRENT_IMAGE, result);
                startActivity(intent, optionsCompat.toBundle());

            }
        });
    }

    private void fetchImageData() {

        AsyncHttpClient client = new AsyncHttpClient();

        if (!Helper.isNetworkAvailable(this)) {
            Toast.makeText(this, getResources().getString(R.string.noNetwork), Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressBar();
        String sizeParam = Helper.GetPreference(this, Helper.IMG_SIZE, "");
        String typeParam = Helper.GetPreference(this, Helper.IMG_TYPE, "");
        String colorParam = Helper.GetPreference(this, Helper.IMG_COLOR, "");
        String siteParam = Helper.GetPreference(this, Helper.IMG_SITE, "");

        String uriString = Uri.parse(BASE_URL)
                .buildUpon()
                .appendQueryParameter(QUERY_PARAM, mQuery)
                .appendQueryParameter(START_PARAM, Integer.toString(imageResults.size()))
                .appendQueryParameter(SIZE_PARAM, sizeParam)
                .appendQueryParameter(TYPE_PARAM, typeParam)
                .appendQueryParameter(COLOR_PARAM, colorParam)
                .appendQueryParameter(SITE_PARAM, siteParam)
                .build().toString();
        client.get(uriString, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray imageResultsJSON = response.getJSONObject("responseData").getJSONArray("results");
                    imageResultsAdapter.addAll(ImageResult.fromJSONArray(imageResultsJSON));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.fetchError), Toast.LENGTH_SHORT).show();
                super.onFailure(statusCode, headers, throwable, object);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                mQuery = query;
                searchView.clearFocus();
                imageResultsAdapter.clear();
                fetchImageData();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v = (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FilterSettingsFragment taskDetails = new FilterSettingsFragment();
            taskDetails.show(fragmentManager, "Dialog_Settings");
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(IMAGE_LIST, imageResults);
        outState.putString(QUERY_PARAM, mQuery);
        outState.putInt(SCROLL_POSITION, gvResults.getFirstVisiblePosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mQuery = savedInstanceState.getString(QUERY_PARAM);
            ArrayList<ImageResult> savedResults = savedInstanceState.getParcelableArrayList(IMAGE_LIST);
            imageResultsAdapter.clear();
            imageResultsAdapter.addAll(savedResults);
            gvResults.setSelection(savedInstanceState.getInt(SCROLL_POSITION, 0));
        }
    }

    @Override
    public void onSettingsChanged() {
        imageResultsAdapter.clear();
        fetchImageData();
    }
}
