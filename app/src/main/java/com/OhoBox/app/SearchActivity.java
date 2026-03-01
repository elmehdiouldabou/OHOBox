package com.OhoBox.app;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.OhoBox.app.databinding.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;

public class SearchActivity extends AppCompatActivity {
	
	private SearchBinding binding;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = SearchBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
	}
	
	private void initializeLogic() {
		// ════════════════════════════════════════════════════════════════
		// SearchActivity onCreate (FINAL: DUAL MODE - SEARCH & SEE ALL)
		// ════════════════════════════════════════════════════════════════
		
		final android.content.Context _ctx = SearchActivity.this;
		final float _dp = getResources().getDisplayMetrics().density;
		final android.os.Handler _uiH = new android.os.Handler(android.os.Looper.getMainLooper());
		
		final android.view.View _btnBack = findViewById(R.id.btn_back_search);
		final android.widget.EditText _editSearch = (android.widget.EditText) findViewById(R.id.edit_search_input);
		final android.widget.TextView _tvStatus = (android.widget.TextView) findViewById(R.id.tv_search_status);
		final android.widget.LinearLayout _gridContainer = (android.widget.LinearLayout) findViewById(R.id.grid_container);
		final android.widget.TextView _btnLoadMore = (android.widget.TextView) findViewById(R.id.btn_load_more);
		
		final org.json.JSONArray[] _allResults = new org.json.JSONArray[1];
		final int[] _displayCount = {0};
		final int BATCH_SIZE = 18; 
		
		final String TMDB_API_KEY = "94bf6ec3141eb7b8e37a2f2285da9ead";
		final String TMDB_BASE_URL = "https://api.themoviedb.org/3";
		final String TMDB_IMAGE_BASE = "https://image.tmdb.org/t/p/w300";
		
		// ── CUSTOM "SEE ALL" INTENT LOGIC ──
		final String customUrl = getIntent().getStringExtra("custom_url");
		final String customTitle = getIntent().getStringExtra("custom_title");
		final boolean isCustomList = (customUrl != null && !customUrl.isEmpty());
		
		if (isCustomList) {
			// Hide search bar, transform into full grid view
			((android.view.View)_editSearch.getParent()).setVisibility(android.view.View.GONE);
			_tvStatus.setText(customTitle);
			_tvStatus.setTextSize(18);
			_tvStatus.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
			getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		}
		
		// ── UI HELPERS ──
		class UIHelper {
			public void applyRoundedBackground(android.view.View v, int color, float radiusDp) {
				android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
				gd.setColor(color); gd.setCornerRadius(radiusDp * _dp); v.setBackground(gd); v.setClipToOutline(true); 
			}
			public void addBounceClick(final android.view.View view, final Runnable action) {
				view.setOnTouchListener(new android.view.View.OnTouchListener() {
					@Override public boolean onTouch(android.view.View v, android.view.MotionEvent event) {
						switch (event.getAction()) {
							case android.view.MotionEvent.ACTION_DOWN: v.animate().scaleX(0.92f).scaleY(0.92f).setDuration(100).start(); break;
							case android.view.MotionEvent.ACTION_UP: case android.view.MotionEvent.ACTION_CANCEL:
							v.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
							if (event.getAction() == android.view.MotionEvent.ACTION_UP && action != null) action.run(); break;
						} return true;
					}
				});
			}
		}
		final UIHelper ui = new UIHelper();
		
		ui.addBounceClick(_btnBack, new Runnable() { @Override public void run() { finish(); } });
		android.view.View searchContainer = (android.view.View) _editSearch.getParent();
		ui.applyRoundedBackground(searchContainer, 0xFF1E1E2E, 12);
		ui.applyRoundedBackground(_btnLoadMore, 0xFF1A1A2E, 12);
		
		class SearchManager {
			public void showGridSkeletons() {
				_gridContainer.removeAllViews(); _btnLoadMore.setVisibility(android.view.View.GONE);
				_tvStatus.setText(isCustomList ? customTitle : "Searching...");
				
				for (int r = 0; r < 4; r++) {
					android.widget.LinearLayout rowLayout = new android.widget.LinearLayout(_ctx); rowLayout.setOrientation(0);
					rowLayout.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-1, -2)); rowLayout.setPadding(0, 0, 0, (int)(10*_dp));
					for (int c = 0; c < 3; c++) {
						android.view.View skel = new android.view.View(_ctx); android.widget.LinearLayout.LayoutParams lp = new android.widget.LinearLayout.LayoutParams(0, (int)(170*_dp), 1); lp.setMargins((int)(4*_dp), 0, (int)(4*_dp), 0);
						skel.setLayoutParams(lp); ui.applyRoundedBackground(skel, 0xFF2A2A3E, 8); 
						android.animation.ObjectAnimator anim = android.animation.ObjectAnimator.ofFloat(skel, "alpha", 1f, 0.3f); anim.setDuration(700); anim.setRepeatMode(2); anim.setRepeatCount(-1); anim.setStartDelay((r * 3 + c) * 50); anim.start();
						rowLayout.addView(skel);
					}
					_gridContainer.addView(rowLayout);
				}
			}
			
			public void renderBatch() {
				if (_allResults[0] == null) return;
				int totalFound = _allResults[0].length(); int startIndex = _displayCount[0]; int endIndex = Math.min(startIndex + BATCH_SIZE, totalFound);
				
				if (startIndex == 0) {
					_gridContainer.removeAllViews(); 
					if (!isCustomList) _tvStatus.setText("Found " + totalFound + " results.");
				}
				
				android.widget.LinearLayout currentRow = null;
				for (int i = startIndex; i < endIndex; i++) {
					if ((i - startIndex) % 3 == 0) {
						currentRow = new android.widget.LinearLayout(_ctx); currentRow.setOrientation(0);
						currentRow.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-1, -2)); currentRow.setPadding(0, 0, 0, (int)(12*_dp)); _gridContainer.addView(currentRow);
					}
					try {
						org.json.JSONObject doc = _allResults[0].getJSONObject(i);
						final String mediaType = doc.optString("media_type", ""); if (mediaType.equals("person")) continue; 
						
						final String _tmdbId = doc.optString("id", ""); final String _title = doc.has("title") ? doc.optString("title") : doc.optString("name", "Unknown");
						String rawDate = doc.has("release_date") ? doc.optString("release_date") : doc.optString("first_air_date", "");
						final String _year = rawDate.length() >= 4 ? rawDate.substring(0, 4) : "N/A"; final String _desc = doc.optString("overview", "No description.");
						final String _thumb = doc.optString("poster_path", "").isEmpty() ? "" : TMDB_IMAGE_BASE + doc.optString("poster_path", "");
						
						boolean isTvFallback = doc.has("first_air_date") || doc.has("name");
						final String _displayType = mediaType.equals("tv") || (isCustomList && isTvFallback) ? "TV Show" : "Movie";
						
						android.widget.RelativeLayout card = new android.widget.RelativeLayout(_ctx); android.widget.LinearLayout.LayoutParams cardLp = new android.widget.LinearLayout.LayoutParams(0, (int)(170*_dp), 1); cardLp.setMargins((int)(4*_dp), 0, (int)(4*_dp), 0); card.setLayoutParams(cardLp); ui.applyRoundedBackground(card, 0xFF1A1A2E, 8); 
						
						final android.widget.ImageView iv = new android.widget.ImageView(_ctx); iv.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(-1, -1)); iv.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP); card.addView(iv);
						if (!_thumb.isEmpty()) {
							iv.setAlpha(0f); new Thread(new Runnable() { @Override public void run() { try { final android.graphics.Bitmap bmp = android.graphics.BitmapFactory.decodeStream(new java.net.URL(_thumb).openConnection().getInputStream()); _uiH.post(new Runnable() { @Override public void run() { if (bmp != null) { iv.setImageBitmap(bmp); iv.animate().alpha(1f).setDuration(400).start(); } }}); } catch (Exception ignored) {} } }).start();
						}
						
						android.view.View gradient = new android.view.View(_ctx); gradient.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(-1, -1)); gradient.setBackground(new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0xEE000000, 0x00000000})); card.addView(gradient);
						android.widget.TextView tvT = new android.widget.TextView(_ctx); tvT.setText(_title); tvT.setTextColor(0xFFFFFFFF); tvT.setTextSize(10); tvT.setTypeface(android.graphics.Typeface.DEFAULT_BOLD); tvT.setMaxLines(2); tvT.setEllipsize(android.text.TextUtils.TruncateAt.END); android.widget.RelativeLayout.LayoutParams txtLp = new android.widget.RelativeLayout.LayoutParams(-1, -2); txtLp.addRule(12); txtLp.setMargins((int)(6*_dp), 0, (int)(6*_dp), (int)(18*_dp)); tvT.setLayoutParams(txtLp); tvT.setGravity(17); card.addView(tvT);
						android.widget.TextView tvY = new android.widget.TextView(_ctx); tvY.setText((_year.isEmpty() ? "" : _year + " · ") + _displayType); tvY.setTextColor(0xFF00E676); tvY.setTextSize(8); tvY.setMaxLines(1); android.widget.RelativeLayout.LayoutParams subLp = new android.widget.RelativeLayout.LayoutParams(-2, -2); subLp.addRule(12); subLp.addRule(14); subLp.setMargins(0, 0, 0, (int)(6*_dp)); tvY.setLayoutParams(subLp); card.addView(tvY);
						
						ui.addBounceClick(card, new Runnable() {
							@Override public void run() {
								android.content.Intent intent = new android.content.Intent(_ctx, DetailActivity.class);
								intent.putExtra("title", _title); intent.putExtra("year", _year); intent.putExtra("desc", _desc); intent.putExtra("thumb", _thumb); intent.putExtra("imdb", _tmdbId); 
								if (_displayType.equals("TV Show")) intent.putExtra("show_id", _tmdbId); else intent.putExtra("show_id", "");
								intent.putExtra("subject", _displayType); _ctx.startActivity(intent);
							}
						});
						
						card.setAlpha(0f); card.setTranslationY(30f); card.animate().alpha(1f).translationY(0f).setDuration(400).setStartDelay((i - startIndex) * 40).start();
						if (currentRow != null) currentRow.addView(card);
					} catch (Exception e) {}
				}
				
				if (currentRow != null && currentRow.getChildCount() < 3) {
					int missing = 3 - currentRow.getChildCount();
					for (int p = 0; p < missing; p++) {
						android.view.View dummy = new android.view.View(_ctx); android.widget.LinearLayout.LayoutParams dLp = new android.widget.LinearLayout.LayoutParams(0, 1, 1); dLp.setMargins((int)(4*_dp), 0, (int)(4*_dp), 0); dummy.setLayoutParams(dLp); currentRow.addView(dummy);
					}
				}
				
				_displayCount[0] = endIndex;
				if (_displayCount[0] < totalFound) {
					_btnLoadMore.setVisibility(android.view.View.VISIBLE); _btnLoadMore.setText("Load More (" + (totalFound - _displayCount[0]) + " left)");
					ui.addBounceClick(_btnLoadMore, new Runnable() { @Override public void run() { renderBatch(); }});
				} else { _btnLoadMore.setVisibility(android.view.View.GONE); }
			}
			
			public void doSearch(final String query) {
				showGridSkeletons();
				new Thread(new Runnable() {
					@Override public void run() {
						try {
							java.net.HttpURLConnection c = (java.net.HttpURLConnection) new java.net.URL(TMDB_BASE_URL + "/search/multi?api_key=" + TMDB_API_KEY + "&query=" + java.net.URLEncoder.encode(query, "UTF-8")).openConnection();
							java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
							StringBuilder sb = new StringBuilder(); String ln; while ((ln = br.readLine()) != null) sb.append(ln); br.close();
							_allResults[0] = new org.json.JSONObject(sb.toString()).getJSONArray("results"); _displayCount[0] = 0; 
							_uiH.post(new Runnable() { @Override public void run() { if (_allResults[0].length() == 0) { _gridContainer.removeAllViews(); _tvStatus.setText("No results found."); } else { renderBatch(); } }});
						} catch(Exception e) { _uiH.post(new Runnable() { @Override public void run() { _tvStatus.setText("Network error."); }}); }
					}
				}).start();
			}
		}
		final SearchManager _searchManager = new SearchManager();
		
		// Regular Typing Search Logic
		_editSearch.addTextChangedListener(new android.text.TextWatcher() {
			private Runnable searchRunnable;
			@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {} @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override public void afterTextChanged(final android.text.Editable s) {
				if (searchRunnable != null) _uiH.removeCallbacks(searchRunnable);
				final String query = s.toString().trim();
				searchRunnable = new Runnable() {
					@Override public void run() {
						if (!query.isEmpty() && query.length() >= 3) { _searchManager.doSearch(query); } 
						else if (query.isEmpty()) { _gridContainer.removeAllViews(); _tvStatus.setText("Type 3 or more letters..."); }
					}
				}; _uiH.postDelayed(searchRunnable, 500); 
			}
		});
		
		// ── TRIGGER CUSTOM FETCH IF COMING FROM "SEE ALL" ──
		if (isCustomList) {
			_searchManager.showGridSkeletons();
			new Thread(new Runnable() {
				@Override public void run() {
					try {
						java.net.HttpURLConnection c = (java.net.HttpURLConnection) new java.net.URL(customUrl).openConnection();
						java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
						StringBuilder sb = new StringBuilder(); String ln; while ((ln = br.readLine()) != null) sb.append(ln); br.close();
						_allResults[0] = new org.json.JSONObject(sb.toString()).getJSONArray("results"); _displayCount[0] = 0; 
						_uiH.post(new Runnable() { @Override public void run() { _searchManager.renderBatch(); } });
					} catch(Exception e) { _uiH.post(new Runnable() { @Override public void run() { _tvStatus.setText("Network error."); }}); }
				}
			}).start();
		}
	}
	
}