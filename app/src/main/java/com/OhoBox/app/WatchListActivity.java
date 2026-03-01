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

public class WatchListActivity extends AppCompatActivity {
	
	private WatchListBinding binding;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = WatchListBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
	}
	
	private void initializeLogic() {
		final android.content.Context _ctx = WatchListActivity.this;
		final float _dp = getResources().getDisplayMetrics().density;
		final android.os.Handler _uiH = new android.os.Handler(android.os.Looper.getMainLooper());
		
		final android.view.View _btnBack = findViewById(R.id.btn_back_watchlist);
		final android.widget.TextView _tvEmpty = (android.widget.TextView) findViewById(R.id.tv_empty_state);
		final android.widget.LinearLayout _gridContainer = (android.widget.LinearLayout) findViewById(R.id.grid_watchlist);
		
		_btnBack.setOnClickListener(new android.view.View.OnClickListener() {
			@Override public void onClick(android.view.View v) { finish(); }
		});
		
		android.content.SharedPreferences prefs = getSharedPreferences("WATCHLIST_DATA", android.content.Context.MODE_PRIVATE);
		String savedData = prefs.getString("items", "[]");
		
		try {
			org.json.JSONArray savedArray = new org.json.JSONArray(savedData);
			
			if (savedArray.length() == 0) {
				_tvEmpty.setVisibility(android.view.View.VISIBLE);
			} else {
				_tvEmpty.setVisibility(android.view.View.GONE);
				
				android.widget.LinearLayout currentRow = null;
				for (int i = savedArray.length() - 1; i >= 0; i--) {
					int position = (savedArray.length() - 1) - i;
					
					if (position % 3 == 0) {
						currentRow = new android.widget.LinearLayout(_ctx);
						currentRow.setOrientation(android.widget.LinearLayout.HORIZONTAL);
						currentRow.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
						android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
						currentRow.setPadding(0, 0, 0, (int)(12*_dp));
						_gridContainer.addView(currentRow);
					}
					
					org.json.JSONObject doc = savedArray.getJSONObject(i);
					final String _title = doc.optString("title", "Unknown");
					final String _year = doc.optString("year", "");
					final String _imdbId = doc.optString("imdb", "");
					final String _tmdbId = doc.optString("show_id", "");
					final String _subject = doc.optString("subject", "");
					final String _thumb = doc.optString("thumb", "");
					final String _desc = doc.optString("desc", "");
					
					android.widget.LinearLayout card = new android.widget.LinearLayout(_ctx);
					card.setOrientation(android.widget.LinearLayout.VERTICAL);
					card.setBackgroundColor(0xFF111118);
					android.widget.LinearLayout.LayoutParams cardLp = new android.widget.LinearLayout.LayoutParams(0, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 1);
					cardLp.setMargins((int)(4*_dp), 0, (int)(4*_dp), 0);
					card.setLayoutParams(cardLp);
					
					final android.widget.ImageView iv = new android.widget.ImageView(_ctx);
					iv.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, (int)(160*_dp)));
					iv.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
					iv.setBackgroundColor(0xFF1A1A2E);
					card.addView(iv);
					
					if (!_thumb.isEmpty()) {
						new Thread(new Runnable() {
							@Override public void run() {
								try {
									java.net.HttpURLConnection ic = (java.net.HttpURLConnection) new java.net.URL(_thumb).openConnection();
									ic.setConnectTimeout(6000); ic.setReadTimeout(6000);
									final android.graphics.Bitmap bmp = android.graphics.BitmapFactory.decodeStream(ic.getInputStream());
									_uiH.post(new Runnable() { @Override public void run() { if (bmp != null) iv.setImageBitmap(bmp); }});
								} catch (Exception ignored) {}
							}
						}).start();
					}
					
					android.widget.LinearLayout info = new android.widget.LinearLayout(_ctx);
					info.setOrientation(android.widget.LinearLayout.VERTICAL);
					info.setPadding((int)(4*_dp), (int)(6*_dp), (int)(4*_dp), (int)(6*_dp));
					card.addView(info);
					
					android.widget.TextView tvT = new android.widget.TextView(_ctx);
					tvT.setText(_title); tvT.setTextColor(0xFFF0F0FF); tvT.setTextSize(10); tvT.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
					tvT.setMaxLines(2); tvT.setEllipsize(android.text.TextUtils.TruncateAt.END);
					info.addView(tvT);
					
					card.setClickable(true);
					card.setOnClickListener(new android.view.View.OnClickListener() {
						@Override public void onClick(android.view.View v) {
							android.content.Intent intent = new android.content.Intent(_ctx, DetailActivity.class);
							intent.putExtra("title", _title); 
							intent.putExtra("year", _year); 
							intent.putExtra("desc", _desc);
							intent.putExtra("thumb", _thumb); 
							intent.putExtra("imdb", _imdbId); 
							intent.putExtra("show_id", _tmdbId);
							intent.putExtra("subject", _subject);
							_ctx.startActivity(intent);
						}
					});
					
					if (currentRow != null) currentRow.addView(card);
				}
				
				if (currentRow != null && currentRow.getChildCount() < 3) {
					int missing = 3 - currentRow.getChildCount();
					for (int p = 0; p < missing; p++) {
						android.view.View dummy = new android.view.View(_ctx);
						android.widget.LinearLayout.LayoutParams dLp = new android.widget.LinearLayout.LayoutParams(0, 1, 1);
						dLp.setMargins((int)(4*_dp), 0, (int)(4*_dp), 0);
						dummy.setLayoutParams(dLp);
						currentRow.addView(dummy);
					}
				}
			}
		} catch (Exception e) {}
	}
	
}