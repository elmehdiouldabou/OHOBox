package com.OhoBox.app;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
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
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.OhoBox.app.databinding.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;


public class MainActivity extends AppCompatActivity {
	
	private MainBinding binding;
	private String mangaFetchUrl = "";
	
	private Intent i = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = MainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
	}
	
	private void initializeLogic() {
		// ════════════════════════════════════════════════════════════════
		// MainActivity onCreate — ULTRA PREMIUM EDITION (Java 7 / Sketchware)
		// NO lambdas · All anonymous classes · All captured vars final
		// ════════════════════════════════════════════════════════════════
		
		final android.content.Context _ctx = MainActivity.this;
		final float _dp = getResources().getDisplayMetrics().density;
		final android.os.Handler _uiH = new android.os.Handler(android.os.Looper.getMainLooper());
		final android.view.animation.Interpolator _overshoot = new android.view.animation.OvershootInterpolator(1.4f);
		final android.view.animation.Interpolator _spring   = new android.view.animation.DecelerateInterpolator(2.5f);
		
		final android.os.Vibrator _vib = (android.os.Vibrator) getSystemService(android.content.Context.VIBRATOR_SERVICE);
		
		// Views
		final android.widget.LinearLayout _dynamicRows  = (android.widget.LinearLayout) findViewById(R.id.dynamic_rows_container);
		final android.widget.LinearLayout _topSearchBar = (android.widget.LinearLayout) findViewById(R.id.top_search_bar);
		final androidx.viewpager.widget.ViewPager _heroPager = (androidx.viewpager.widget.ViewPager) findViewById(R.id.hero_viewpager);
		final android.widget.LinearLayout _layoutDots   = (android.widget.LinearLayout) findViewById(R.id.layout_dots);
		
		// Whole-page entrance
		final android.widget.LinearLayout _linearContent = (android.widget.LinearLayout) findViewById(R.id.linear_content);
		_linearContent.setAlpha(0f);
		_linearContent.setTranslationY(40f * _dp);
		_linearContent.animate().alpha(1f).translationY(0f).setDuration(700).setInterpolator(_spring).start();
		
		final String TMDB_BASE_URL  = "https://api.themoviedb.org/3";
		final String TMDB_KEY_PARAM = "?api_key=94bf6ec3141eb7b8e37a2f2285da9ead";
		
		// ════════════════════════════════════════════════════════════════
		// UI HELPERS
		// ════════════════════════════════════════════════════════════════
		class UIHelper {
			
			public void applyRoundedBackground(android.view.View v, int color, float radiusDp) {
				android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
				gd.setColor(color);
				gd.setCornerRadius(radiusDp * _dp);
				v.setBackground(gd);
				v.setClipToOutline(true);
			}
			
			public void applyGlass(android.view.View v, float radiusDp) {
				android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
				gd.setColor(0x26FFFFFF);
				gd.setCornerRadius(radiusDp * _dp);
				gd.setStroke((int)(1 * _dp), 0x33FFFFFF);
				v.setBackground(gd);
				v.setClipToOutline(true);
				v.setElevation(6 * _dp);
			}
			
			public void applyNeonCard(android.view.View v, float radiusDp) {
				android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
				gd.setColor(0xFF1A1A2E);
				gd.setCornerRadius(radiusDp * _dp);
				gd.setStroke((int)(1.2f * _dp), 0x3300E676);
				v.setBackground(gd);
				v.setClipToOutline(true);
				v.setElevation(12 * _dp);
			}
			
			public void haptic() {
				if (_vib != null && android.os.Build.VERSION.SDK_INT >= 26) {
					_vib.vibrate(android.os.VibrationEffect.createOneShot(18, 80));
				}
			}
			
			public void addBounceClick(final android.view.View view, final Runnable action) {
				view.setOnTouchListener(new android.view.View.OnTouchListener() {
					@Override
					public boolean onTouch(android.view.View v, android.view.MotionEvent event) {
						if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
							v.animate().scaleX(0.91f).scaleY(0.91f).setDuration(120)
							.setInterpolator(new android.view.animation.DecelerateInterpolator()).start();
							haptic();
						} else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
							final android.view.View vRef = v;
							v.animate().scaleX(1.04f).scaleY(1.04f).setDuration(160)
							.setInterpolator(_overshoot)
							.withEndAction(new Runnable() {
								@Override public void run() {
									vRef.animate().scaleX(1f).scaleY(1f).setDuration(120).start();
								}
							}).start();
							if (action != null) action.run();
						} else if (event.getAction() == android.view.MotionEvent.ACTION_CANCEL) {
							v.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
						}
						return true;
					}
				});
			}
			
			// Shimmer skeleton placeholder
			public android.view.View makeShimmer(int widthDp, int heightDp, float cornerDp) {
				android.widget.FrameLayout host = new android.widget.FrameLayout(_ctx);
				android.widget.LinearLayout.LayoutParams lp =
				new android.widget.LinearLayout.LayoutParams((int)(widthDp * _dp), (int)(heightDp * _dp));
				lp.setMarginEnd((int)(12 * _dp));
				host.setLayoutParams(lp);
				applyRoundedBackground(host, 0xFF1E1E30, cornerDp);
				host.setClipToOutline(true);
				
				final android.view.View sweep = new android.view.View(_ctx);
				sweep.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1, -1));
				android.graphics.drawable.GradientDrawable sweepGd =
				new android.graphics.drawable.GradientDrawable(
				android.graphics.drawable.GradientDrawable.Orientation.LEFT_RIGHT,
				new int[]{0x00FFFFFF, 0x22FFFFFF, 0x00FFFFFF});
				sweep.setBackground(sweepGd);
				host.addView(sweep);
				
				android.animation.ObjectAnimator shimAnim =
				android.animation.ObjectAnimator.ofFloat(sweep, "translationX",
				-(widthDp * _dp), widthDp * _dp * 2f);
				shimAnim.setDuration(1400);
				shimAnim.setRepeatCount(-1);
				shimAnim.setInterpolator(new android.view.animation.LinearInterpolator());
				shimAnim.start();
				
				android.animation.ObjectAnimator pulse =
				android.animation.ObjectAnimator.ofFloat(host, "alpha", 1f, 0.45f);
				pulse.setDuration(900);
				pulse.setRepeatMode(android.animation.ObjectAnimator.REVERSE);
				pulse.setRepeatCount(-1);
				pulse.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());
				pulse.start();
				
				return host;
			}
			
			// Staggered overshoot pop-in
			public void popIn(android.view.View v, int index) {
				v.setAlpha(0f);
				v.setScaleX(0.75f);
				v.setScaleY(0.75f);
				v.setTranslationY(30f);
				v.animate().alpha(1f).scaleX(1f).scaleY(1f).translationY(0f)
				.setDuration(450).setStartDelay(index * 60L).setInterpolator(_overshoot).start();
			}
		}
		final UIHelper ui = new UIHelper();
		
		// ════════════════════════════════════════════════════════════════
		// CHIP HELPER — defined before CategoryBuilder so it's available
		// ════════════════════════════════════════════════════════════════
		class ChipHelper {
			
			public android.widget.TextView makeChip(String label, boolean active) {
				android.widget.TextView tv = new android.widget.TextView(_ctx);
				tv.setText(label);
				tv.setTextSize(13);
				tv.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
				tv.setPadding((int)(16 * _dp), (int)(8 * _dp), (int)(16 * _dp), (int)(8 * _dp));
				android.widget.LinearLayout.LayoutParams lp =
				new android.widget.LinearLayout.LayoutParams(-2, -2);
				lp.setMargins(0, 0, (int)(8 * _dp), 0);
				tv.setLayoutParams(lp);
				if (active) {
					ui.applyRoundedBackground(tv, 0xFF00E676, 22);
					tv.setTextColor(0xFF000000);
					tv.setElevation(6 * _dp);
				} else {
					android.graphics.drawable.GradientDrawable gd =
					new android.graphics.drawable.GradientDrawable();
					gd.setColor(0xFF1E1E2E);
					gd.setCornerRadius(22 * _dp);
					gd.setStroke((int)(_dp), 0x44FFFFFF);
					tv.setBackground(gd);
					tv.setTextColor(0xCCFFFFFF);
				}
				return tv;
			}
			
			public void selectChip(android.widget.LinearLayout container,
			final android.widget.TextView chosen) {
				for (int j = 0; j < container.getChildCount(); j++) {
					android.widget.TextView t = (android.widget.TextView) container.getChildAt(j);
					android.graphics.drawable.GradientDrawable gd =
					new android.graphics.drawable.GradientDrawable();
					gd.setColor(0xFF1E1E2E);
					gd.setCornerRadius(22 * _dp);
					gd.setStroke((int)(_dp), 0x44FFFFFF);
					t.setBackground(gd);
					t.setTextColor(0xCCFFFFFF);
					t.setElevation(0);
				}
				ui.applyRoundedBackground(chosen, 0xFF00E676, 22);
				chosen.setTextColor(0xFF000000);
				chosen.setElevation(6 * _dp);
				chosen.animate().scaleX(1.12f).scaleY(1.12f).setDuration(150).setInterpolator(_overshoot)
				.withEndAction(new Runnable() {
					@Override public void run() {
						chosen.animate().scaleX(1f).scaleY(1f).setDuration(150).start();
					}
				}).start();
			}
		}
		final ChipHelper chip = new ChipHelper();
		
		// ════════════════════════════════════════════════════════════════
		// DEFAULT CATEGORIES
		// ════════════════════════════════════════════════════════════════
		final String[][] defaultCategories = {
			{"🔥 Trending Now",       "trending"},
			{"💥 Action & Adventure", "10759"},
			{"🎌 Animation",          "16"},
			{"🎭 Drama",              "18"},
			{"😂 Comedy",             "35"},
			{"😱 Horror & Thriller",  "27"},
		};
		
		// ════════════════════════════════════════════════════════════════
		// SEARCH BAR: glassmorphism + breathing neon border
		// ════════════════════════════════════════════════════════════════
		ui.applyGlass(_topSearchBar, 14);
		_topSearchBar.setElevation(8 * _dp);
		
		android.animation.ValueAnimator borderPulse = android.animation.ValueAnimator.ofFloat(0f, 1f);
		borderPulse.setDuration(2200);
		borderPulse.setRepeatMode(android.animation.ValueAnimator.REVERSE);
		borderPulse.setRepeatCount(-1);
		borderPulse.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(android.animation.ValueAnimator anim) {
				float v = (float) anim.getAnimatedValue();
				android.graphics.drawable.GradientDrawable gd =
				new android.graphics.drawable.GradientDrawable();
				gd.setColor(0x20FFFFFF);
				gd.setCornerRadius(14 * _dp);
				int alpha = (int)(0x15 + v * 0x30);
				gd.setStroke((int)(1.5f * _dp), (alpha << 24) | 0x00E676);
				_topSearchBar.setBackground(gd);
			}
		});
		borderPulse.start();
		
		ui.addBounceClick(_topSearchBar, new Runnable() {
			@Override public void run() {
				startActivity(new android.content.Intent(_ctx, SearchActivity.class));
			}
		});
		ui.addBounceClick(findViewById(R.id.nav_search), new Runnable() {
			@Override public void run() {
				startActivity(new android.content.Intent(_ctx, SearchActivity.class));
			}
		});
		ui.addBounceClick(findViewById(R.id.nav_watchlist), new Runnable() {
			@Override public void run() {
				startActivity(new android.content.Intent(_ctx, ProfileActivity.class));
			}
		});
		
		// Bottom nav slides up on launch
		final android.widget.LinearLayout _bottomNav =
		(android.widget.LinearLayout) findViewById(R.id.bottom_nav_bar);
		_bottomNav.setTranslationY(80 * _dp);
		_bottomNav.animate().translationY(0f).setDuration(700).setStartDelay(300)
		.setInterpolator(_overshoot).start();
		
		// ════════════════════════════════════════════════════════════════
		// HERO CAROUSEL
		// ════════════════════════════════════════════════════════════════
		class HeroAdapter extends androidx.viewpager.widget.PagerAdapter {
			private final org.json.JSONArray items;
			HeroAdapter(org.json.JSONArray items) { this.items = items; }
			@Override public int getCount() { return items == null ? 0 : items.length(); }
			@Override public boolean isViewFromObject(android.view.View view, Object o) { return view == o; }
			
			@Override
			public Object instantiateItem(android.view.ViewGroup container, int position) {
				android.widget.FrameLayout frame = new android.widget.FrameLayout(_ctx);
				frame.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -1));
				
				final android.widget.ImageView iv = new android.widget.ImageView(_ctx);
				iv.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1, -1));
				iv.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
				iv.setBackgroundColor(0xFF141416);
				iv.setScaleX(1.12f);
				iv.setScaleY(1.12f);
				frame.addView(iv);
				
				android.view.View gradBot = new android.view.View(_ctx);
				gradBot.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1, -1));
				gradBot.setBackground(new android.graphics.drawable.GradientDrawable(
				android.graphics.drawable.GradientDrawable.Orientation.BOTTOM_TOP,
				new int[]{0xFF141416, 0xCC141416, 0x66141416, 0x00141416}));
				frame.addView(gradBot);
				
				android.view.View gradTop = new android.view.View(_ctx);
				android.widget.FrameLayout.LayoutParams tgLp =
				new android.widget.FrameLayout.LayoutParams(-1, (int)(80 * _dp));
				tgLp.gravity = android.view.Gravity.TOP;
				gradTop.setLayoutParams(tgLp);
				gradTop.setBackground(new android.graphics.drawable.GradientDrawable(
				android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM,
				new int[]{0xAA141416, 0x00141416}));
				frame.addView(gradTop);
				
				try {
					org.json.JSONObject doc = items.getJSONObject(position);
					final String _title   = doc.optString("name", doc.optString("title", "Unknown"));
					final String _tmdbId  = doc.optString("id", "");
					final String _desc    = doc.optString("overview", "");
					final String rawDate  = doc.optString("first_air_date", doc.optString("release_date", ""));
					final String _year    = rawDate.length() >= 4 ? rawDate.substring(0, 4) : "N/A";
					final double _voteRaw = doc.optDouble("vote_average", 0);
					final String _rating  = _voteRaw > 0 ? String.format("%.1f", _voteRaw) : "";
					final String _img     = doc.optString("backdrop_path", "").isEmpty() ? ""
					: "https://image.tmdb.org/t/p/w780" + doc.optString("backdrop_path", "");
					final String _media   = doc.optString("media_type", "tv");
					
					if (!_img.isEmpty()) {
						iv.setAlpha(0f);
						new Thread(new Runnable() {
							@Override public void run() {
								try {
									final android.graphics.Bitmap bmp =
									android.graphics.BitmapFactory.decodeStream(
									new java.net.URL(_img).openConnection().getInputStream());
									_uiH.post(new Runnable() {
										@Override public void run() {
											if (bmp != null) {
												iv.setImageBitmap(bmp);
												iv.animate().alpha(1f).setDuration(600).start();
											}
										}
									});
								} catch (Exception ignored) {}
							}
						}).start();
					}
					
					// Info overlay
					android.widget.LinearLayout infoBar = new android.widget.LinearLayout(_ctx);
					infoBar.setOrientation(android.widget.LinearLayout.VERTICAL);
					android.widget.FrameLayout.LayoutParams ibLp =
					new android.widget.FrameLayout.LayoutParams(-1, -2);
					ibLp.gravity = android.view.Gravity.BOTTOM | android.view.Gravity.START;
					ibLp.setMargins((int)(16 * _dp), 0, (int)(16 * _dp), (int)(40 * _dp));
					infoBar.setLayoutParams(ibLp);
					frame.addView(infoBar);
					
					android.widget.TextView tvTitle = new android.widget.TextView(_ctx);
					tvTitle.setText(_title);
					tvTitle.setTextColor(0xFFFFFFFF);
					tvTitle.setTextSize(26);
					tvTitle.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
					tvTitle.setMaxLines(2);
					tvTitle.setEllipsize(android.text.TextUtils.TruncateAt.END);
					tvTitle.setShadowLayer(8, 0, 2, 0xAA000000);
					infoBar.addView(tvTitle);
					
					android.widget.LinearLayout metaRow = new android.widget.LinearLayout(_ctx);
					metaRow.setOrientation(android.widget.LinearLayout.HORIZONTAL);
					metaRow.setGravity(android.view.Gravity.CENTER_VERTICAL);
					android.widget.LinearLayout.LayoutParams mrLp =
					new android.widget.LinearLayout.LayoutParams(-1, -2);
					mrLp.topMargin = (int)(6 * _dp);
					metaRow.setLayoutParams(mrLp);
					
					android.widget.TextView tvYear = new android.widget.TextView(_ctx);
					tvYear.setText(_year);
					tvYear.setTextColor(0xBBFFFFFF);
					tvYear.setTextSize(13);
					metaRow.addView(tvYear);
					
					if (!_rating.isEmpty()) {
						android.widget.TextView tvRate = new android.widget.TextView(_ctx);
						tvRate.setText("  \u2B50 " + _rating);
						tvRate.setTextColor(0xFFFFD700);
						tvRate.setTextSize(13);
						tvRate.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
						metaRow.addView(tvRate);
					}
					
					android.widget.TextView tvType = new android.widget.TextView(_ctx);
					tvType.setText("  \u2022 " + (_media.equals("movie") ? "MOVIE" : "SERIES"));
					tvType.setTextColor(0x9900E676);
					tvType.setTextSize(11);
					tvType.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
					metaRow.addView(tvType);
					infoBar.addView(metaRow);
					
					// Action chips
					android.widget.LinearLayout chipsRow = new android.widget.LinearLayout(_ctx);
					chipsRow.setOrientation(android.widget.LinearLayout.HORIZONTAL);
					android.widget.LinearLayout.LayoutParams crLp =
					new android.widget.LinearLayout.LayoutParams(-2, -2);
					crLp.topMargin = (int)(12 * _dp);
					chipsRow.setLayoutParams(crLp);
					
					android.widget.LinearLayout playChip = new android.widget.LinearLayout(_ctx);
					playChip.setOrientation(android.widget.LinearLayout.HORIZONTAL);
					playChip.setGravity(android.view.Gravity.CENTER);
					playChip.setPadding((int)(18 * _dp), (int)(10 * _dp), (int)(18 * _dp), (int)(10 * _dp));
					ui.applyRoundedBackground(playChip, 0xFF00E676, 24);
					playChip.setElevation(6 * _dp);
					android.widget.TextView tvPlay = new android.widget.TextView(_ctx);
					tvPlay.setText("\u25B6  Play Now");
					tvPlay.setTextColor(0xFF000000);
					tvPlay.setTextSize(13);
					tvPlay.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
					playChip.addView(tvPlay);
					
					android.widget.LinearLayout watchChip = new android.widget.LinearLayout(_ctx);
					watchChip.setOrientation(android.widget.LinearLayout.HORIZONTAL);
					watchChip.setGravity(android.view.Gravity.CENTER);
					watchChip.setPadding((int)(14 * _dp), (int)(10 * _dp), (int)(14 * _dp), (int)(10 * _dp));
					ui.applyGlass(watchChip, 24);
					android.widget.LinearLayout.LayoutParams wcLp =
					new android.widget.LinearLayout.LayoutParams(-2, -2);
					wcLp.setMarginStart((int)(10 * _dp));
					watchChip.setLayoutParams(wcLp);
					android.widget.TextView tvWatch = new android.widget.TextView(_ctx);
					tvWatch.setText("\uFF0B  Watchlist");
					tvWatch.setTextColor(0xFFFFFFFF);
					tvWatch.setTextSize(13);
					tvWatch.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
					watchChip.addView(tvWatch);
					
					chipsRow.addView(playChip);
					chipsRow.addView(watchChip);
					infoBar.addView(chipsRow);
					
					infoBar.setAlpha(0f);
					infoBar.setTranslationY(20f);
					infoBar.animate().alpha(1f).translationY(0f).setDuration(600)
					.setStartDelay(200).setInterpolator(_spring).start();
					
					ui.addBounceClick(playChip, new Runnable() {
						@Override public void run() {
							android.content.Intent i = new android.content.Intent(_ctx, DetailActivity.class);
							i.putExtra("title", _title); i.putExtra("year", _year);
							i.putExtra("desc",  _desc);  i.putExtra("thumb", _img);
							i.putExtra("imdb",  _tmdbId); i.putExtra("show_id", _tmdbId);
							i.putExtra("subject", "Trending");
							_ctx.startActivity(i);
						}
					});
					ui.addBounceClick(watchChip, new Runnable() {
						@Override public void run() { /* TODO watchlist */ }
					});
					ui.addBounceClick(frame, new Runnable() {
						@Override public void run() {
							android.content.Intent i = new android.content.Intent(_ctx, DetailActivity.class);
							i.putExtra("title", _title); i.putExtra("year", _year);
							i.putExtra("desc",  _desc);  i.putExtra("thumb", _img);
							i.putExtra("imdb",  _tmdbId); i.putExtra("show_id", _tmdbId);
							i.putExtra("subject", "Trending");
							_ctx.startActivity(i);
						}
					});
					
				} catch (Exception ignored) {}
				container.addView(frame);
				return frame;
			}
			
			@Override
			public void destroyItem(android.view.ViewGroup container, int position, Object object) {
				container.removeView((android.view.View) object);
			}
		}
		
		// Fetch hero data
		new Thread(new Runnable() {
			@Override public void run() {
				try {
					java.net.HttpURLConnection c = (java.net.HttpURLConnection)
					new java.net.URL(TMDB_BASE_URL + "/trending/all/day" + TMDB_KEY_PARAM).openConnection();
					java.io.BufferedReader br =
					new java.io.BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
					StringBuilder sb = new StringBuilder(); String ln;
					while ((ln = br.readLine()) != null) sb.append(ln); br.close();
					final org.json.JSONArray docs =
					new org.json.JSONObject(sb.toString()).getJSONArray("results");
					final org.json.JSONArray heroItems = new org.json.JSONArray();
					for (int i = 0; i < Math.min(docs.length(), 6); i++) heroItems.put(docs.getJSONObject(i));
					
					_uiH.post(new Runnable() {
						@Override public void run() {
							final HeroAdapter adapter = new HeroAdapter(heroItems);
							_heroPager.setAdapter(adapter);
							
							// Depth + parallax transition
							_heroPager.setPageTransformer(true,
							new androidx.viewpager.widget.ViewPager.PageTransformer() {
								@Override
								public void transformPage(android.view.View page, float position) {
									float abs = Math.abs(position);
									page.setAlpha(1f - abs * 0.35f);
									page.setScaleX(1f - abs * 0.07f);
									page.setScaleY(1f - abs * 0.07f);
									if (page instanceof android.widget.FrameLayout) {
										android.view.View bg =
										((android.widget.FrameLayout) page).getChildAt(0);
										if (bg != null)
										bg.setTranslationX(-position * page.getWidth() * 0.18f);
									}
								}
							});
							_heroPager.setOffscreenPageLimit(2);
							
							// Morphing pill dots
							final android.view.View[] dots = new android.view.View[adapter.getCount()];
							for (int i = 0; i < adapter.getCount(); i++) {
								android.view.View dot = new android.view.View(_ctx);
								boolean active = (i == 0);
								ui.applyRoundedBackground(dot, active ? 0xFF00E676 : 0x44FFFFFF, 4);
								android.widget.LinearLayout.LayoutParams dotLp =
								new android.widget.LinearLayout.LayoutParams(
								(int)((active ? 28 : 8) * _dp), (int)(8 * _dp));
								dotLp.setMargins((int)(3 * _dp), 0, (int)(3 * _dp), 0);
								dot.setLayoutParams(dotLp);
								_layoutDots.addView(dot);
								dots[i] = dot;
							}
							
							_heroPager.addOnPageChangeListener(
							new androidx.viewpager.widget.ViewPager.OnPageChangeListener() {
								@Override public void onPageScrolled(int p, float po, int pop) {}
								@Override public void onPageScrollStateChanged(int s) {}
								@Override public void onPageSelected(int page) {
									for (int i = 0; i < adapter.getCount(); i++) {
										final android.view.View dot = dots[i];
										final boolean sel = (i == page);
										final int targetW = (int)((sel ? 28 : 8) * _dp);
										android.animation.ValueAnimator wa =
										android.animation.ValueAnimator.ofInt(
										dot.getLayoutParams().width, targetW);
										wa.setDuration(280);
										wa.setInterpolator(_spring);
										wa.addUpdateListener(
										new android.animation.ValueAnimator.AnimatorUpdateListener() {
											@Override
											public void onAnimationUpdate(
											android.animation.ValueAnimator a) {
												dot.getLayoutParams().width =
												(int) a.getAnimatedValue();
												dot.requestLayout();
											}
										});
										wa.start();
										ui.applyRoundedBackground(dot,
										sel ? 0xFF00E676 : 0x44FFFFFF, 4);
									}
								}
							});
							
							new java.util.Timer().schedule(new java.util.TimerTask() {
								@Override public void run() {
									_uiH.post(new Runnable() {
										@Override public void run() {
											int next = (_heroPager.getCurrentItem() + 1) % adapter.getCount();
											_heroPager.setCurrentItem(next, true);
										}
									});
								}
							}, 4500, 4500);
						}
					});
				} catch (Exception ignored) {}
			}
		}).start();
		
		// ════════════════════════════════════════════════════════════════
		// CHIP FILTER STRIP
		// ════════════════════════════════════════════════════════════════
		android.widget.HorizontalScrollView hScrollChips = new android.widget.HorizontalScrollView(_ctx);
		hScrollChips.setHorizontalScrollBarEnabled(false);
		hScrollChips.setClipToPadding(false);
		android.widget.LinearLayout.LayoutParams hscLp =
		new android.widget.LinearLayout.LayoutParams(-1, -2);
		hscLp.topMargin = (int)(8 * _dp);
		hScrollChips.setLayoutParams(hscLp);
		
		final android.widget.LinearLayout chipContainer = new android.widget.LinearLayout(_ctx);
		chipContainer.setOrientation(android.widget.LinearLayout.HORIZONTAL);
		chipContainer.setPadding((int)(16 * _dp), (int)(8 * _dp), (int)(16 * _dp), (int)(8 * _dp));
		hScrollChips.addView(chipContainer);
		
		android.widget.LinearLayout mainParent = (android.widget.LinearLayout) _dynamicRows.getParent();
		mainParent.addView(hScrollChips, mainParent.indexOfChild(_dynamicRows));
		hScrollChips.setAlpha(0f);
		hScrollChips.animate().alpha(1f).setDuration(500).setStartDelay(400).start();
		
		// ════════════════════════════════════════════════════════════════
		// CATEGORY BUILDER
		// ════════════════════════════════════════════════════════════════
		class CategoryBuilder {
			
			public void buildRows(final String[][] cats) {
				_dynamicRows.animate().alpha(0f).setDuration(200)
				.withEndAction(new Runnable() {
					@Override public void run() {
						_dynamicRows.removeAllViews();
						for (int i = 0; i < cats.length; i++) {
							final String catTitle = cats[i][0];
							final String catId    = cats[i][1];
							final long   hdrDelay = i * 80L;
							
							android.widget.LinearLayout hdr = new android.widget.LinearLayout(_ctx);
							hdr.setOrientation(android.widget.LinearLayout.HORIZONTAL);
							hdr.setGravity(android.view.Gravity.CENTER_VERTICAL);
							android.widget.LinearLayout.LayoutParams hdrLp =
							new android.widget.LinearLayout.LayoutParams(-1, -2);
							hdrLp.setMargins((int)(16 * _dp), (int)(28 * _dp),
							(int)(16 * _dp), (int)(14 * _dp));
							hdr.setLayoutParams(hdrLp);
							
							android.widget.TextView txtTitle = new android.widget.TextView(_ctx);
							txtTitle.setText(catTitle);
							txtTitle.setTextSize(17);
							txtTitle.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
							txtTitle.setTextColor(0xFFFFFFFF);
							hdr.addView(txtTitle,
							new android.widget.LinearLayout.LayoutParams(0, -2, 1));
							
							android.widget.TextView txtArrow = new android.widget.TextView(_ctx);
							txtArrow.setText("See All  \u203A");
							txtArrow.setTextSize(12);
							txtArrow.setTextColor(0xFF141416);
							txtArrow.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
							txtArrow.setPadding((int)(12 * _dp), (int)(5 * _dp),
							(int)(12 * _dp), (int)(5 * _dp));
							ui.applyRoundedBackground(txtArrow, 0xFF00E676, 20);
							hdr.addView(txtArrow);
							
							ui.addBounceClick(txtArrow, new Runnable() {
								@Override public void run() {
									android.content.Intent intent =
									new android.content.Intent(_ctx, SearchActivity.class);
									String apiUrl = catId.equals("trending")
									? TMDB_BASE_URL + "/trending/tv/day" + TMDB_KEY_PARAM
									: TMDB_BASE_URL + "/discover/tv" + TMDB_KEY_PARAM
									+ "&with_genres=" + catId + "&sort_by=popularity.desc";
									intent.putExtra("custom_url",   apiUrl);
									intent.putExtra("custom_title", catTitle);
									_ctx.startActivity(intent);
								}
							});
							
							hdr.setAlpha(0f);
							hdr.setTranslationX(-20f);
							hdr.animate().alpha(1f).translationX(0f).setDuration(350)
							.setStartDelay(hdrDelay).setInterpolator(_spring).start();
							_dynamicRows.addView(hdr);
							
							final android.widget.LinearLayout rowContainer =
							new android.widget.LinearLayout(_ctx);
							rowContainer.setOrientation(android.widget.LinearLayout.HORIZONTAL);
							rowContainer.setPadding((int)(16 * _dp), 0, (int)(16 * _dp), 0);
							
							android.widget.HorizontalScrollView hScroll =
							new android.widget.HorizontalScrollView(_ctx);
							hScroll.setHorizontalScrollBarEnabled(false);
							hScroll.setClipToPadding(false);
							hScroll.setLayoutParams(
							new android.widget.LinearLayout.LayoutParams(-1, -2));
							hScroll.addView(rowContainer);
							_dynamicRows.addView(hScroll);
							
							for (int s = 0; s < 6; s++)
							rowContainer.addView(ui.makeShimmer(120, 175, 10));
							
							fetchCategoryData(catId, rowContainer);
						}
						_dynamicRows.animate().alpha(1f).setDuration(300).start();
					}
				}).start();
			}
			
			private void fetchCategoryData(final String genreId,
			final android.widget.LinearLayout rowContainer) {
				new Thread(new Runnable() {
					@Override public void run() {
						try {
							String apiUrl = genreId.equals("trending")
							? TMDB_BASE_URL + "/trending/tv/day" + TMDB_KEY_PARAM
							: TMDB_BASE_URL + "/discover/tv" + TMDB_KEY_PARAM
							+ "&with_genres=" + genreId + "&sort_by=popularity.desc";
							java.net.HttpURLConnection c = (java.net.HttpURLConnection)
							new java.net.URL(apiUrl).openConnection();
							java.io.BufferedReader br =
							new java.io.BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
							StringBuilder sb = new StringBuilder(); String ln;
							while ((ln = br.readLine()) != null) sb.append(ln); br.close();
							final org.json.JSONArray docs =
							new org.json.JSONObject(sb.toString()).getJSONArray("results");
							
							_uiH.post(new Runnable() {
								@Override public void run() {
									rowContainer.removeAllViews();
									for (int i = 0; i < Math.min(docs.length(), 20); i++) {
										org.json.JSONObject doc = docs.optJSONObject(i);
										if (doc == null) continue;
										
										final String _tmdbId  = doc.optString("id", "");
										final String _title   = doc.optString("name",
										doc.optString("original_name", "Unknown"));
										final String _year    = doc.optString("first_air_date", "").length() >= 4
										? doc.optString("first_air_date", "").substring(0, 4) : "N/A";
										final String _desc    = doc.optString("overview", "No description.");
										final String _thumb   = doc.optString("poster_path", "").isEmpty() ? ""
										: "https://image.tmdb.org/t/p/w300" + doc.optString("poster_path", "");
										final double _voteRaw = doc.optDouble("vote_average", 0);
										final String _vote    = _voteRaw > 0
										? String.format("%.1f", _voteRaw) : "";
										
										final android.widget.FrameLayout card =
										new android.widget.FrameLayout(_ctx);
										android.widget.LinearLayout.LayoutParams clp =
										new android.widget.LinearLayout.LayoutParams(
										(int)(120 * _dp), (int)(175 * _dp));
										clp.setMarginEnd((int)(12 * _dp));
										card.setLayoutParams(clp);
										ui.applyNeonCard(card, 10);
										card.setClipToOutline(true);
										
										final android.widget.ImageView iv = new android.widget.ImageView(_ctx);
										iv.setLayoutParams(
										new android.widget.FrameLayout.LayoutParams(-1, -1));
										iv.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
										card.addView(iv);
										
										if (!_thumb.isEmpty()) {
											iv.setAlpha(0f);
											new Thread(new Runnable() {
												@Override public void run() {
													try {
														final android.graphics.Bitmap bmp =
														android.graphics.BitmapFactory.decodeStream(
														new java.net.URL(_thumb).openConnection()
														.getInputStream());
														_uiH.post(new Runnable() {
															@Override public void run() {
																if (bmp != null) {
																	iv.setImageBitmap(bmp);
																	iv.animate().alpha(1f)
																	.setDuration(500).start();
																}
															}
														});
													} catch (Exception ignored) {}
												}
											}).start();
										}
										
										android.view.View gradB = new android.view.View(_ctx);
										android.widget.FrameLayout.LayoutParams gbLp =
										new android.widget.FrameLayout.LayoutParams(-1, (int)(90 * _dp));
										gbLp.gravity = android.view.Gravity.BOTTOM;
										gradB.setLayoutParams(gbLp);
										gradB.setBackground(new android.graphics.drawable.GradientDrawable(
										android.graphics.drawable.GradientDrawable.Orientation.BOTTOM_TOP,
										new int[]{0xFF0D0D1A, 0xAA0D0D1A, 0x00000000}));
										card.addView(gradB);
										
										if (!_vote.isEmpty()) {
											android.widget.TextView tvR = new android.widget.TextView(_ctx);
											tvR.setText("\u2B50 " + _vote);
											tvR.setTextSize(9);
											tvR.setTextColor(0xFFFFD700);
											tvR.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
											tvR.setPadding((int)(5 * _dp), (int)(3 * _dp),
											(int)(5 * _dp), (int)(3 * _dp));
											android.widget.FrameLayout.LayoutParams rLp =
											new android.widget.FrameLayout.LayoutParams(-2, -2);
											rLp.gravity =
											android.view.Gravity.TOP | android.view.Gravity.END;
											rLp.setMargins(0, (int)(6 * _dp), (int)(6 * _dp), 0);
											tvR.setLayoutParams(rLp);
											ui.applyRoundedBackground(tvR, 0xDD000000, 6);
											card.addView(tvR);
										}
										
										android.widget.TextView tvT = new android.widget.TextView(_ctx);
										tvT.setText(_title);
										tvT.setTextColor(0xFFFFFFFF);
										tvT.setTextSize(11);
										tvT.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
										tvT.setMaxLines(2);
										tvT.setEllipsize(android.text.TextUtils.TruncateAt.END);
										tvT.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
										android.widget.FrameLayout.LayoutParams tLp =
										new android.widget.FrameLayout.LayoutParams(-1, -2);
										tLp.gravity = android.view.Gravity.BOTTOM
										| android.view.Gravity.CENTER_HORIZONTAL;
										tLp.setMargins((int)(6 * _dp), 0, (int)(6 * _dp), (int)(8 * _dp));
										tvT.setLayoutParams(tLp);
										card.addView(tvT);
										
										final android.view.View pressOverlay = new android.view.View(_ctx);
										pressOverlay.setLayoutParams(
										new android.widget.FrameLayout.LayoutParams(-1, -1));
										pressOverlay.setAlpha(0f);
										ui.applyRoundedBackground(pressOverlay, 0x2200E676, 10);
										card.addView(pressOverlay);
										
										card.setOnTouchListener(new android.view.View.OnTouchListener() {
											@Override
											public boolean onTouch(android.view.View v,
											android.view.MotionEvent ev) {
												if (ev.getAction() == android.view.MotionEvent.ACTION_DOWN) {
													pressOverlay.animate().alpha(1f).setDuration(100).start();
													v.animate().scaleX(0.93f).scaleY(0.93f)
													.setDuration(120).start();
													ui.haptic();
												} else if (ev.getAction() == android.view.MotionEvent.ACTION_UP) {
													pressOverlay.animate().alpha(0f).setDuration(300).start();
													final android.view.View vRef = v;
													v.animate().scaleX(1.04f).scaleY(1.04f)
													.setDuration(160).setInterpolator(_overshoot)
													.withEndAction(new Runnable() {
														@Override public void run() {
															vRef.animate().scaleX(1f).scaleY(1f)
															.setDuration(120).start();
														}
													}).start();
													android.content.Intent intent =
													new android.content.Intent(_ctx, DetailActivity.class);
													intent.putExtra("title",   _title);
													intent.putExtra("year",    _year);
													intent.putExtra("desc",    _desc);
													intent.putExtra("thumb",   _thumb);
													intent.putExtra("imdb",    _tmdbId);
													intent.putExtra("show_id", _tmdbId);
													intent.putExtra("subject", "TV Show");
													_ctx.startActivity(intent);
												} else if (ev.getAction() == android.view.MotionEvent.ACTION_CANCEL) {
													pressOverlay.animate().alpha(0f).setDuration(200).start();
													v.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
												}
												return true;
											}
										});
										
										ui.popIn(card, i);
										rowContainer.addView(card);
									}
								}
							});
						} catch (Exception ignored) {}
					}
				}).start();
			}
		}
		final CategoryBuilder catBuilder = new CategoryBuilder();
		
		// ════════════════════════════════════════════════════════════════
		// FETCH GENRES & BUILD CHIPS
		// ════════════════════════════════════════════════════════════════
		new Thread(new Runnable() {
			@Override public void run() {
				try {
					java.net.HttpURLConnection c = (java.net.HttpURLConnection)
					new java.net.URL(TMDB_BASE_URL + "/genre/tv/list" + TMDB_KEY_PARAM).openConnection();
					java.io.BufferedReader br =
					new java.io.BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
					StringBuilder sb = new StringBuilder(); String ln;
					while ((ln = br.readLine()) != null) sb.append(ln); br.close();
					final org.json.JSONArray genres =
					new org.json.JSONObject(sb.toString()).getJSONArray("genres");
					
					_uiH.post(new Runnable() {
						@Override public void run() {
							final android.widget.TextView btnAll = chip.makeChip("All", true);
							chipContainer.addView(btnAll);
							ui.addBounceClick(btnAll, new Runnable() {
								@Override public void run() {
									chip.selectChip(chipContainer, btnAll);
									catBuilder.buildRows(defaultCategories);
								}
							});
							
							for (int i = 0; i < Math.min(genres.length(), 15); i++) {
								org.json.JSONObject g = genres.optJSONObject(i);
								if (g == null) continue;
								final String gId   = g.optString("id");
								final String gName = g.optString("name");
								
								final android.widget.TextView btnG = chip.makeChip(gName, false);
								btnG.setAlpha(0f);
								btnG.setTranslationY(10f);
								btnG.animate().alpha(1f).translationY(0f).setDuration(300)
								.setStartDelay(i * 40L + 150).start();
								
								ui.addBounceClick(btnG, new Runnable() {
									@Override public void run() {
										chip.selectChip(chipContainer, btnG);
										String[][] filtered = {
											{"Popular " + gName, gId},
											{"New in " + gName,  gId},
										};
										catBuilder.buildRows(filtered);
									}
								});
								chipContainer.addView(btnG);
							}
							
							catBuilder.buildRows(defaultCategories);
						}
					});
				} catch (Exception ignored) {}
			}
		}).start();
		
		// ════════════════════════════════════════════════════════════════
		// 📚 BOOKS SECTION — Internet Archive → opens in ViewActivity
		// ════════════════════════════════════════════════════════════════
		
		// ── Section Header ──
		android.widget.LinearLayout booksHdr = new android.widget.LinearLayout(_ctx);
		booksHdr.setOrientation(android.widget.LinearLayout.HORIZONTAL);
		booksHdr.setGravity(android.view.Gravity.CENTER_VERTICAL);
		android.widget.LinearLayout.LayoutParams bhLp =
		new android.widget.LinearLayout.LayoutParams(-1, -2);
		bhLp.setMargins((int)(16 * _dp), (int)(32 * _dp), (int)(16 * _dp), (int)(6 * _dp));
		booksHdr.setLayoutParams(bhLp);
		
		// Left accent bar
		android.view.View accentBar = new android.view.View(_ctx);
		android.widget.LinearLayout.LayoutParams abLp =
		new android.widget.LinearLayout.LayoutParams((int)(4 * _dp), (int)(22 * _dp));
		abLp.setMarginEnd((int)(10 * _dp));
		accentBar.setLayoutParams(abLp);
		android.graphics.drawable.GradientDrawable accentGd =
		new android.graphics.drawable.GradientDrawable(
		android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM,
		new int[]{0xFF00E676, 0xFF00897B});
		accentGd.setCornerRadius(4 * _dp);
		accentBar.setBackground(accentGd);
		booksHdr.addView(accentBar);
		
		android.widget.TextView booksTitleTv = new android.widget.TextView(_ctx);
		booksTitleTv.setText("📚 Free Books");
		booksTitleTv.setTextSize(17);
		booksTitleTv.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
		booksTitleTv.setTextColor(0xFFFFFFFF);
		booksHdr.addView(booksTitleTv,
		new android.widget.LinearLayout.LayoutParams(0, -2, 1));
		
		android.widget.TextView booksArrow = new android.widget.TextView(_ctx);
		booksArrow.setText("See All  ›");
		booksArrow.setTextSize(12);
		booksArrow.setTextColor(0xFF141416);
		booksArrow.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
		booksArrow.setPadding((int)(12 * _dp), (int)(5 * _dp), (int)(12 * _dp), (int)(5 * _dp));
		ui.applyRoundedBackground(booksArrow, 0xFF00E676, 20);
		booksHdr.addView(booksArrow);
		
		// See All → open ViewActivity with IA search page
		ui.addBounceClick(booksArrow, new Runnable() {
			@Override public void run() {
				android.content.Intent i = new android.content.Intent(_ctx, ViewActivity.class);
				i.putExtra("book_url",    "https://archive.org/search?query=books&mediatype=texts");
				i.putExtra("book_title",  "All Free Books");
				i.putExtra("book_author", "Internet Archive");
				_ctx.startActivity(i);
			}
		});
		
		booksHdr.setAlpha(0f);
		booksHdr.setTranslationX(-20f);
		booksHdr.animate().alpha(1f).translationX(0f)
		.setDuration(350).setStartDelay(200)
		.setInterpolator(new android.view.animation.DecelerateInterpolator(2f))
		.start();
		
		// ── Chip strip ──
		android.widget.HorizontalScrollView booksChipScroll =
		new android.widget.HorizontalScrollView(_ctx);
		booksChipScroll.setHorizontalScrollBarEnabled(false);
		booksChipScroll.setClipToPadding(false);
		android.widget.LinearLayout.LayoutParams bcScrollLp =
		new android.widget.LinearLayout.LayoutParams(-1, -2);
		bcScrollLp.setMargins(0, (int)(4 * _dp), 0, 0);
		booksChipScroll.setLayoutParams(bcScrollLp);
		
		final android.widget.LinearLayout booksChipRow = new android.widget.LinearLayout(_ctx);
		booksChipRow.setOrientation(android.widget.LinearLayout.HORIZONTAL);
		booksChipRow.setPadding((int)(16 * _dp), (int)(6 * _dp), (int)(16 * _dp), (int)(6 * _dp));
		booksChipScroll.addView(booksChipRow);
		
		// ── Book card row ──
		final android.widget.LinearLayout booksRow = new android.widget.LinearLayout(_ctx);
		booksRow.setOrientation(android.widget.LinearLayout.HORIZONTAL);
		booksRow.setPadding((int)(16 * _dp), 0, (int)(16 * _dp), 0);
		
		android.widget.HorizontalScrollView booksHScroll =
		new android.widget.HorizontalScrollView(_ctx);
		booksHScroll.setHorizontalScrollBarEnabled(false);
		booksHScroll.setClipToPadding(false);
		booksHScroll.setLayoutParams(
		new android.widget.LinearLayout.LayoutParams(-1, -2));
		booksHScroll.addView(booksRow);
		
		// Shimmer placeholders
		for (int s = 0; s < 6; s++) booksRow.addView(ui.makeShimmer(110, 165, 10));
		
		// ── Inject into main layout ──
		android.widget.LinearLayout _mainParentBooks =
		(android.widget.LinearLayout) _dynamicRows.getParent();
		_mainParentBooks.addView(booksHdr);
		_mainParentBooks.addView(booksChipScroll);
		_mainParentBooks.addView(booksHScroll);
		
		// ════════════════════════════════════════════════════════════════
		// BOOK FETCHER
		// ════════════════════════════════════════════════════════════════
		class BookFetcher {
			
			public String buildUrl(String query) {
				return "https://archive.org/advancedsearch.php"
				+ "?q=" + query
				+ "+AND+mediatype%3Atexts"
				+ "&fl=identifier,title,creator,year,description"
				+ "&sort=downloads+desc"
				+ "&rows=20"
				+ "&page=1"
				+ "&output=json";
			}
			
			public String thumbUrl(String identifier) {
				return "https://archive.org/services/img/" + identifier;
			}
			
			public String detailUrl(String identifier) {
				return "https://archive.org/details/" + identifier;
			}
			
			public void fetch(final String urlStr,
			final android.widget.LinearLayout container) {
				new Thread(new Runnable() {
					@Override public void run() {
						try {
							java.net.HttpURLConnection c = (java.net.HttpURLConnection)
							new java.net.URL(urlStr).openConnection();
							c.setRequestMethod("GET");
							c.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Linux; Android 11; Mobile) "
							+ "AppleWebKit/537.36 (KHTML, like Gecko) "
							+ "Chrome/112.0.0.0 Mobile Safari/537.36");
							c.setRequestProperty("Accept",
							"application/json, text/plain, */*");
							c.setConnectTimeout(8000);
							c.setReadTimeout(8000);
							
							int code = c.getResponseCode();
							java.io.InputStream stream = (code >= 200 && code < 300)
							? c.getInputStream() : c.getErrorStream();
							
							if (stream == null) {
								showToast("Books: no response (" + code + ")");
								return;
							}
							
							java.io.BufferedReader br =
							new java.io.BufferedReader(
							new java.io.InputStreamReader(stream));
							StringBuilder sb = new StringBuilder();
							String ln;
							while ((ln = br.readLine()) != null) sb.append(ln);
							br.close();
							
							final String raw = sb.toString().trim();
							if (raw.isEmpty()) { showToast("Books: empty response"); return; }
							
							org.json.JSONObject root = new org.json.JSONObject(raw);
							org.json.JSONArray docs = null;
							
							if (root.has("response")) {
								org.json.JSONObject resp = root.getJSONObject("response");
								if (resp.has("docs")) docs = resp.getJSONArray("docs");
							}
							if (docs == null && root.has("docs")) {
								docs = root.getJSONArray("docs");
							}
							if (docs == null || docs.length() == 0) {
								showToast("Books: 0 results");
								return;
							}
							
							final org.json.JSONArray _docs = docs;
							_uiH.post(new Runnable() {
								@Override public void run() {
									container.removeAllViews();
									
									for (int i = 0; i < Math.min(_docs.length(), 20); i++) {
										org.json.JSONObject doc = _docs.optJSONObject(i);
										if (doc == null) continue;
										
										final String _id     = doc.optString("identifier", "");
										if (_id.isEmpty()) continue;
										final String _title  = doc.optString("title",   "Unknown Title");
										final String _author = doc.optString("creator", "Unknown Author");
										final String _year   = doc.optString("year",    "");
										final String _thumb  = thumbUrl(_id);
										final String _url    = detailUrl(_id);
										
										// ── Card ──
										final android.widget.FrameLayout card =
										new android.widget.FrameLayout(_ctx);
										android.widget.LinearLayout.LayoutParams clp =
										new android.widget.LinearLayout.LayoutParams(
										(int)(110 * _dp), (int)(165 * _dp));
										clp.setMarginEnd((int)(12 * _dp));
										card.setLayoutParams(clp);
										
										android.graphics.drawable.GradientDrawable cardGd =
										new android.graphics.drawable.GradientDrawable();
										cardGd.setColor(0xFF1A1A2E);
										cardGd.setCornerRadius(10 * _dp);
										cardGd.setStroke((int)(1.2f * _dp), 0x44FFB300);
										card.setBackground(cardGd);
										card.setClipToOutline(true);
										card.setElevation(10 * _dp);
										
										// Cover
										final android.widget.ImageView iv =
										new android.widget.ImageView(_ctx);
										iv.setLayoutParams(
										new android.widget.FrameLayout.LayoutParams(-1, -1));
										iv.setScaleType(
										android.widget.ImageView.ScaleType.CENTER_CROP);
										iv.setBackgroundColor(0xFF1E1E2E);
										iv.setAlpha(0f);
										card.addView(iv);
										
										// Load cover
										final String _thumbFinal = _thumb;
										new Thread(new Runnable() {
											@Override public void run() {
												try {
													java.net.HttpURLConnection imgC =
													(java.net.HttpURLConnection)
													new java.net.URL(_thumbFinal)
													.openConnection();
													imgC.setRequestProperty("User-Agent",
													"Mozilla/5.0 (Linux; Android 11)");
													imgC.setConnectTimeout(6000);
													imgC.setReadTimeout(6000);
													final android.graphics.Bitmap bmp =
													android.graphics.BitmapFactory
													.decodeStream(imgC.getInputStream());
													_uiH.post(new Runnable() {
														@Override public void run() {
															if (bmp != null) {
																iv.setImageBitmap(bmp);
																iv.animate().alpha(1f)
																.setDuration(500).start();
															}
														}
													});
												} catch (Exception ignored) {}
											}
										}).start();
										
										// Bottom gradient
										android.view.View gradB = new android.view.View(_ctx);
										android.widget.FrameLayout.LayoutParams gbLp =
										new android.widget.FrameLayout.LayoutParams(
										-1, (int)(90 * _dp));
										gbLp.gravity = android.view.Gravity.BOTTOM;
										gradB.setLayoutParams(gbLp);
										gradB.setBackground(
										new android.graphics.drawable.GradientDrawable(
										android.graphics.drawable.GradientDrawable
										.Orientation.BOTTOM_TOP,
										new int[]{0xFF0D0D1A, 0xBB0D0D1A, 0x00000000}));
										card.addView(gradB);
										
										// FREE badge
										android.widget.TextView tvBadge =
										new android.widget.TextView(_ctx);
										tvBadge.setText("FREE");
										tvBadge.setTextSize(8);
										tvBadge.setTextColor(0xFF000000);
										tvBadge.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
										tvBadge.setPadding(
										(int)(5 * _dp), (int)(2 * _dp),
										(int)(5 * _dp), (int)(2 * _dp));
										android.widget.FrameLayout.LayoutParams bdgLp =
										new android.widget.FrameLayout.LayoutParams(-2, -2);
										bdgLp.gravity =
										android.view.Gravity.TOP | android.view.Gravity.START;
										bdgLp.setMargins((int)(6 * _dp), (int)(6 * _dp), 0, 0);
										tvBadge.setLayoutParams(bdgLp);
										ui.applyRoundedBackground(tvBadge, 0xFFFFB300, 4);
										card.addView(tvBadge);
										
										// Year badge
										if (!_year.isEmpty()) {
											android.widget.TextView tvY =
											new android.widget.TextView(_ctx);
											tvY.setText(_year);
											tvY.setTextSize(8);
											tvY.setTextColor(0xFFFFFFFF);
											tvY.setTypeface(
											android.graphics.Typeface.DEFAULT_BOLD);
											tvY.setPadding(
											(int)(4 * _dp), (int)(2 * _dp),
											(int)(4 * _dp), (int)(2 * _dp));
											android.widget.FrameLayout.LayoutParams yLp =
											new android.widget.FrameLayout.LayoutParams(-2, -2);
											yLp.gravity =
											android.view.Gravity.TOP | android.view.Gravity.END;
											yLp.setMargins(
											0, (int)(6 * _dp), (int)(6 * _dp), 0);
											tvY.setLayoutParams(yLp);
											ui.applyRoundedBackground(tvY, 0xDD000000, 4);
											card.addView(tvY);
										}
										
										// Title
										android.widget.TextView tvTitle =
										new android.widget.TextView(_ctx);
										tvTitle.setText(_title);
										tvTitle.setTextColor(0xFFFFFFFF);
										tvTitle.setTextSize(10);
										tvTitle.setTypeface(
										android.graphics.Typeface.DEFAULT_BOLD);
										tvTitle.setMaxLines(2);
										tvTitle.setEllipsize(
										android.text.TextUtils.TruncateAt.END);
										tvTitle.setGravity(
										android.view.Gravity.CENTER_HORIZONTAL);
										android.widget.FrameLayout.LayoutParams tLp =
										new android.widget.FrameLayout.LayoutParams(-1, -2);
										tLp.gravity =
										android.view.Gravity.BOTTOM
										| android.view.Gravity.CENTER_HORIZONTAL;
										tLp.setMargins(
										(int)(5 * _dp), 0,
										(int)(5 * _dp), (int)(18 * _dp));
										tvTitle.setLayoutParams(tLp);
										card.addView(tvTitle);
										
										// Author
										android.widget.TextView tvAuthor =
										new android.widget.TextView(_ctx);
										tvAuthor.setText(_author);
										tvAuthor.setTextColor(0xAAFFB300);
										tvAuthor.setTextSize(9);
										tvAuthor.setMaxLines(1);
										tvAuthor.setEllipsize(
										android.text.TextUtils.TruncateAt.END);
										tvAuthor.setGravity(
										android.view.Gravity.CENTER_HORIZONTAL);
										android.widget.FrameLayout.LayoutParams aLp =
										new android.widget.FrameLayout.LayoutParams(-1, -2);
										aLp.gravity =
										android.view.Gravity.BOTTOM
										| android.view.Gravity.CENTER_HORIZONTAL;
										aLp.setMargins(
										(int)(5 * _dp), 0,
										(int)(5 * _dp), (int)(6 * _dp));
										tvAuthor.setLayoutParams(aLp);
										card.addView(tvAuthor);
										
										// Press overlay
										final android.view.View pressOverlay =
										new android.view.View(_ctx);
										pressOverlay.setLayoutParams(
										new android.widget.FrameLayout.LayoutParams(-1, -1));
										pressOverlay.setAlpha(0f);
										ui.applyRoundedBackground(pressOverlay, 0x22FFB300, 10);
										card.addView(pressOverlay);
										
										// ── TAP → open in ViewActivity ──
										final String _titleFinal  = _title;
										final String _authorFinal = _author;
										final String _urlFinal    = _url;
										
										card.setOnTouchListener(
										new android.view.View.OnTouchListener() {
											@Override
											public boolean onTouch(
											android.view.View v,
											android.view.MotionEvent ev) {
												
												if (ev.getAction() ==
												android.view.MotionEvent.ACTION_DOWN) {
													pressOverlay.animate().alpha(1f)
													.setDuration(100).start();
													v.animate().scaleX(0.93f).scaleY(0.93f)
													.setDuration(120).start();
													ui.haptic();
													
												} else if (ev.getAction() ==
												android.view.MotionEvent.ACTION_UP) {
													pressOverlay.animate().alpha(0f)
													.setDuration(300).start();
													final android.view.View vRef = v;
													v.animate()
													.scaleX(1.04f).scaleY(1.04f)
													.setDuration(160)
													.setInterpolator(_overshoot)
													.withEndAction(new Runnable() {
														@Override public void run() {
															vRef.animate()
															.scaleX(1f).scaleY(1f)
															.setDuration(120).start();
														}
													}).start();
													
													// Launch ViewActivity
													android.content.Intent intent =
													new android.content.Intent(
													_ctx, ViewActivity.class);
													intent.putExtra("book_url",    _urlFinal);
													intent.putExtra("book_title",  _titleFinal);
													intent.putExtra("book_author", _authorFinal);
													_ctx.startActivity(intent);
													
												} else if (ev.getAction() ==
												android.view.MotionEvent.ACTION_CANCEL) {
													pressOverlay.animate().alpha(0f)
													.setDuration(200).start();
													v.animate().scaleX(1f).scaleY(1f)
													.setDuration(200).start();
												}
												return true;
											}
										});
										
										ui.popIn(card, i);
										container.addView(card);
									}
								}
							});
							
						} catch (final Exception e) {
							showToast("Books error: " + e.getMessage());
						}
					}
					
					private void showToast(final String msg) {
						_uiH.post(new Runnable() {
							@Override public void run() {
								android.widget.Toast.makeText(
								_ctx, msg,
								android.widget.Toast.LENGTH_LONG).show();
							}
						});
					}
				}).start();
			}
		}
		final BookFetcher bookFetcher = new BookFetcher();
		
		// ── Genre chips ──
		final String[][] bookGenres = {
			{"🔥 Popular",    "subject%3A(fiction)+AND+language%3Aenglish"},
			{"🔬 Science",    "subject%3A(science)+AND+language%3Aenglish"},
			{"📖 Classic",    "subject%3A(classic+literature)+AND+language%3Aenglish"},
			{"🕵️ Mystery",   "subject%3A(mystery)+AND+language%3Aenglish"},
			{"🧠 Philosophy", "subject%3A(philosophy)+AND+language%3Aenglish"},
			{"💡 Self-Help",  "subject%3A(self-help)+AND+language%3Aenglish"},
			{"🏛️ History",   "subject%3A(history)+AND+language%3Aenglish"},
			{"🧪 Tech",       "subject%3A(computer+science)+AND+language%3Aenglish"},
		};
		
		// Load Popular on start
		bookFetcher.fetch(bookFetcher.buildUrl(bookGenres[0][1]), booksRow);
		
		for (int gi = 0; gi < bookGenres.length; gi++) {
			final String gLabel  = bookGenres[gi][0];
			final String gQuery  = bookGenres[gi][1];
			final boolean isFirst = (gi == 0);
			
			final android.widget.TextView gChip = chip.makeChip(gLabel, isFirst);
			gChip.setAlpha(0f);
			gChip.setTranslationY(10f);
			gChip.animate().alpha(1f).translationY(0f)
			.setDuration(280).setStartDelay(gi * 45L + 250).start();
			
			ui.addBounceClick(gChip, new Runnable() {
				@Override public void run() {
					chip.selectChip(booksChipRow, gChip);
					booksRow.animate().alpha(0f).setDuration(180)
					.withEndAction(new Runnable() {
						@Override public void run() {
							booksRow.removeAllViews();
							for (int s = 0; s < 6; s++)
							booksRow.addView(ui.makeShimmer(110, 165, 10));
							booksRow.animate().alpha(1f).setDuration(200).start();
							bookFetcher.fetch(bookFetcher.buildUrl(gQuery), booksRow);
						}
					}).start();
				}
			});
			booksChipRow.addView(gChip);
		}
		// ════════════════════════════════════════════════════════════════
		// END BOOKS SECTION
		// ════════════════════════════════════════════════════════════════
		// ════════════════════════════════════════════════════════════════
		// MANGA SECTION — mangafire.to integration  
		// ADDED TO linear_content AFTER the Books section
		// ════════════════════════════════════════════════════════════════
		
		// Use existing variables from top of onCreate - DO NOT redeclare
		// _ctx, _dp, _uiH, _overshoot already defined at top
		
		final android.widget.LinearLayout mangaSection = new android.widget.LinearLayout(_ctx);
		mangaSection.setOrientation(android.widget.LinearLayout.VERTICAL);
		mangaSection.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-1, -2));
		
		final android.widget.LinearLayout mangaHdr = new android.widget.LinearLayout(_ctx);
		mangaHdr.setOrientation(android.widget.LinearLayout.HORIZONTAL);
		mangaHdr.setGravity(android.view.Gravity.CENTER_VERTICAL);
		android.widget.LinearLayout.LayoutParams mhLp = new android.widget.LinearLayout.LayoutParams(-1,-2);
		mhLp.setMargins((int)(16*_dp),(int)(32*_dp),(int)(16*_dp),(int)(6*_dp));
		mangaHdr.setLayoutParams(mhLp);
		
		android.view.View mangaAccent = new android.view.View(_ctx);
		android.widget.LinearLayout.LayoutParams maLp = new android.widget.LinearLayout.LayoutParams((int)(4*_dp),(int)(22*_dp));
		maLp.rightMargin=(int)(10*_dp);
		mangaAccent.setLayoutParams(maLp);
		android.graphics.drawable.GradientDrawable maGd = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM,new int[]{0xFFE91E8C,0xFFAD1457});
		maGd.setCornerRadius(4*_dp);
		mangaAccent.setBackground(maGd);
		mangaHdr.addView(mangaAccent);
		
		android.widget.TextView mangaTitleTv = new android.widget.TextView(_ctx);
		mangaTitleTv.setText("Manga");
		mangaTitleTv.setTextSize(17);
		mangaTitleTv.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
		mangaTitleTv.setTextColor(0xFFFFFFFF);
		mangaHdr.addView(mangaTitleTv,new android.widget.LinearLayout.LayoutParams(0,-2,1));
		
		android.widget.TextView mangaArrow = new android.widget.TextView(_ctx);
		mangaArrow.setText("See All");
		mangaArrow.setTextSize(12);
		mangaArrow.setTextColor(0xFF000000);
		mangaArrow.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
		mangaArrow.setPadding((int)(12*_dp),(int)(5*_dp),(int)(12*_dp),(int)(5*_dp));
		android.graphics.drawable.GradientDrawable arrowBg = new android.graphics.drawable.GradientDrawable();
		arrowBg.setColor(0xFFE91E8C);
		arrowBg.setCornerRadius(20*_dp);
		mangaArrow.setBackground(arrowBg);
		mangaArrow.setOnClickListener(new android.view.View.OnClickListener(){
			@Override public void onClick(android.view.View v){
				android.content.Intent i = new android.content.Intent(_ctx,ViewActivity.class);
				i.putExtra("book_url","https://mangafire.to/updated");
				i.putExtra("book_title","All Manga");
				i.putExtra("book_author","MangaFire");
				i.putExtra("book_id","");
				_ctx.startActivity(i);
			}
		});
		mangaHdr.addView(mangaArrow);
		mangaSection.addView(mangaHdr);
		
		android.widget.HorizontalScrollView mangaChipScroll = new android.widget.HorizontalScrollView(_ctx);
		mangaChipScroll.setHorizontalScrollBarEnabled(false);
		mangaChipScroll.setClipToPadding(false);
		android.widget.LinearLayout.LayoutParams csLp = new android.widget.LinearLayout.LayoutParams(-1,-2);
		csLp.topMargin=(int)(4*_dp);
		mangaChipScroll.setLayoutParams(csLp);
		final android.widget.LinearLayout mangaChipRow = new android.widget.LinearLayout(_ctx);
		mangaChipRow.setOrientation(android.widget.LinearLayout.HORIZONTAL);
		mangaChipRow.setPadding((int)(16*_dp),(int)(6*_dp),(int)(16*_dp),(int)(6*_dp));
		mangaChipScroll.addView(mangaChipRow);
		mangaSection.addView(mangaChipScroll);
		
		final android.widget.LinearLayout mangaRow = new android.widget.LinearLayout(_ctx);
		mangaRow.setOrientation(android.widget.LinearLayout.HORIZONTAL);
		mangaRow.setPadding((int)(16*_dp),0,(int)(16*_dp),0);
		android.widget.HorizontalScrollView mangaHScroll = new android.widget.HorizontalScrollView(_ctx);
		mangaHScroll.setHorizontalScrollBarEnabled(false);
		mangaHScroll.setClipToPadding(false);
		mangaHScroll.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-1,-2));
		mangaHScroll.addView(mangaRow);
		mangaSection.addView(mangaHScroll);
		
		// Add shimmer placeholders
		for(int si=0;si<6;si++){
			android.widget.FrameLayout sh = new android.widget.FrameLayout(_ctx);
			android.widget.LinearLayout.LayoutParams slp = new android.widget.LinearLayout.LayoutParams((int)(110*_dp),(int)(165*_dp));
			slp.rightMargin=(int)(12*_dp);
			sh.setLayoutParams(slp);
			android.graphics.drawable.GradientDrawable sgd = new android.graphics.drawable.GradientDrawable();
			sgd.setColor(0xFF1E1E2E);
			sgd.setCornerRadius(10*_dp);
			sh.setBackground(sgd);
			mangaRow.addView(sh);
		}
		
		final String[][] mangaGenres = {
			{"Updated","updated"},
			{"Trending","filter?sort=trending"},
			{"Action","genre/action"},
			{"Romance","genre/romance"},
			{"Fantasy","genre/fantasy"},
			{"Comedy","genre/comedy"},
			{"Horror","genre/horror"},
			{"Isekai","genre/isekai"},
			{"Manhwa","filter?type[]=manhwa"},
			{"Manhua","filter?type[]=manhua"},
		};
		
		for(int gi=0;gi<mangaGenres.length;gi++){
			final String gLabel = mangaGenres[gi][0];
			final String gPath  = mangaGenres[gi][1];
			final boolean gFirst = (gi==0);
			android.widget.TextView gChip = new android.widget.TextView(_ctx);
			gChip.setText(gLabel);
			gChip.setTextSize(12);
			gChip.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
			gChip.setPadding((int)(14*_dp),(int)(6*_dp),(int)(14*_dp),(int)(6*_dp));
			android.widget.LinearLayout.LayoutParams gcLp = new android.widget.LinearLayout.LayoutParams(-2,-2);
			gcLp.rightMargin=(int)(8*_dp);
			gChip.setLayoutParams(gcLp);
			android.graphics.drawable.GradientDrawable gcBg = new android.graphics.drawable.GradientDrawable();
			if(gFirst){ gcBg.setColor(0xFFE91E8C); gChip.setTextColor(0xFF000000); }
			else{ gcBg.setColor(0xFF1E1E2E); gcBg.setStroke((int)(1*_dp),0x55E91E8C); gChip.setTextColor(0xFFCCCCCC); }
			gcBg.setCornerRadius(20*_dp);
			gChip.setBackground(gcBg);
			final android.widget.LinearLayout chipRowRef = mangaChipRow;
			final android.widget.LinearLayout cardRowRef = mangaRow;
			final android.content.Context ctxRef = _ctx;
			final android.os.Handler hRef = _uiH;
			final float dpRef = _dp;
			final android.view.animation.Interpolator ovRef = _overshoot;
			final android.widget.TextView chipRef = gChip;
			gChip.setOnClickListener(new android.view.View.OnClickListener(){
				@Override public void onClick(android.view.View v){
					for(int ci=0;ci<chipRowRef.getChildCount();ci++){
						android.view.View cv=chipRowRef.getChildAt(ci);
						if(cv instanceof android.widget.TextView){
							android.widget.TextView ct=(android.widget.TextView)cv;
							android.graphics.drawable.GradientDrawable cbd=new android.graphics.drawable.GradientDrawable();
							cbd.setColor(0xFF1E1E2E); cbd.setStroke((int)(1*dpRef),0x55E91E8C); cbd.setCornerRadius(20*dpRef);
							ct.setBackground(cbd); ct.setTextColor(0xFFCCCCCC);
						}
					}
					android.graphics.drawable.GradientDrawable selBg=new android.graphics.drawable.GradientDrawable();
					selBg.setColor(0xFFE91E8C); selBg.setCornerRadius(20*dpRef);
					chipRef.setBackground(selBg); chipRef.setTextColor(0xFF000000);
					final String fetchUrl="https://mangafire.to/"+gPath;
					cardRowRef.animate().alpha(0f).setDuration(180).withEndAction(new Runnable(){
						@Override public void run(){
							cardRowRef.removeAllViews();
							for(int si=0;si<6;si++){
								android.widget.FrameLayout sc=new android.widget.FrameLayout(ctxRef);
								android.widget.LinearLayout.LayoutParams slp2=new android.widget.LinearLayout.LayoutParams((int)(110*dpRef),(int)(165*dpRef));
								slp2.rightMargin=(int)(12*dpRef); sc.setLayoutParams(slp2);
								android.graphics.drawable.GradientDrawable sgd2=new android.graphics.drawable.GradientDrawable();
								sgd2.setColor(0xFF1E1E2E); sgd2.setCornerRadius(10*dpRef); sc.setBackground(sgd2);
								cardRowRef.addView(sc);
							}
							cardRowRef.animate().alpha(1f).setDuration(200).start();
							new Thread(new Runnable(){
								@Override public void run(){
									try{
										java.net.HttpURLConnection c=(java.net.HttpURLConnection)new java.net.URL(fetchUrl).openConnection();
										c.setRequestMethod("GET");
										c.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
										c.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
										c.setRequestProperty("Accept-Language","en-US,en;q=0.5");
										c.setRequestProperty("Referer","https://mangafire.to/");
										c.setConnectTimeout(10000); c.setReadTimeout(12000); c.setInstanceFollowRedirects(true);
										int code=c.getResponseCode();
										java.io.InputStream stream=(code>=200&&code<300)?c.getInputStream():c.getErrorStream();
										if(stream==null) return;
										java.io.BufferedReader br=new java.io.BufferedReader(new java.io.InputStreamReader(stream,"UTF-8"));
										StringBuilder sb=new StringBuilder(); String ln;
										while((ln=br.readLine())!=null){ sb.append(ln).append(" "); }
										br.close();
										final String html=sb.toString();
										final java.util.List<String[]> items=new java.util.ArrayList<String[]>();
										int cur=0;
										while(items.size()<20){
											// FIXED: Search for class="unit item- (with plain quotes, not escaped)
											int us=html.indexOf("class=\"unit item-",cur);
											if(us<0) break;
											int ue=html.indexOf("class=\"unit item-",us+20);
											if(ue<0) ue=html.length();
											String blk=html.substring(us,ue);
											String cover="";
											// FIXED: Search for src="https://static.mfcdn.cc/ (plain quotes)
											int csi=blk.indexOf("src=\"https://static.mfcdn.cc/");
											if(csi>=0){ csi+=5; int ei=blk.indexOf("\"",csi); if(ei>csi) cover=blk.substring(csi,ei); }
											String title="Unknown";
											// FIXED: Search for alt=" (plain quotes)
											int ti=blk.indexOf("alt=\"");
											if(ti>=0){ ti+=5; int ei=blk.indexOf("\"",ti); if(ei>ti) title=blk.substring(ti,ei); }
											String type="";
											// FIXED: Search for class="type"> (plain quotes)
											int tyi=blk.indexOf("class=\"type\">");
											if(tyi>=0){ tyi+=13; int ei=blk.indexOf("<",tyi); if(ei>tyi) type=blk.substring(tyi,ei).trim(); }
											String readUrl="https://mangafire.to/updated"; String chap="?";
											// FIXED: Search for href="/read/ (plain quotes)
											int ri=blk.indexOf("href=\"/read/");
											if(ri>=0){ ri+=6; int ei=blk.indexOf("\"",ri); if(ei>ri){ String rp=blk.substring(ri,ei); readUrl="https://mangafire.to"+rp; int ci2=rp.lastIndexOf("/chapter-"); if(ci2>=0) chap=rp.substring(ci2+9); } }
											if(!cover.isEmpty()&&!title.equals("Unknown")) items.add(new String[]{title,cover,readUrl,type,chap});
											cur=ue;
										}
										hRef.post(new Runnable(){ @Override public void run(){
												cardRowRef.removeAllViews();
												if(items.isEmpty()){
													android.widget.TextView empty=new android.widget.TextView(ctxRef);
													empty.setText("No results"); empty.setTextColor(0xFFAAAAAA);
													empty.setPadding((int)(16*dpRef),0,0,0);
													cardRowRef.addView(empty); return;
												}
												for(int idx=0;idx<items.size();idx++){
													final String[] item=items.get(idx);
													final String iTitle=item[0],iCover=item[1],iReadUrl=item[2],iType=item[3],iChap=item[4];
													final int iIdx=idx;
													final android.widget.FrameLayout card=new android.widget.FrameLayout(ctxRef);
													android.widget.LinearLayout.LayoutParams clp=new android.widget.LinearLayout.LayoutParams((int)(110*dpRef),(int)(165*dpRef));
													clp.rightMargin=(int)(12*dpRef); card.setLayoutParams(clp);
													android.graphics.drawable.GradientDrawable cardGd=new android.graphics.drawable.GradientDrawable();
													cardGd.setColor(0xFF1A1A2E); cardGd.setCornerRadius(10*dpRef); cardGd.setStroke((int)(1.2f*dpRef),0x44E91E8C);
													card.setBackground(cardGd); card.setClipToOutline(true); card.setElevation(10*dpRef);
													final android.widget.ImageView iv=new android.widget.ImageView(ctxRef);
													iv.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1,-1));
													iv.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
													iv.setBackgroundColor(0xFF1E1E2E); iv.setAlpha(0f); card.addView(iv);
													final android.os.Handler hRef2=hRef;
													new Thread(new Runnable(){ @Override public void run(){
															try{
																java.net.HttpURLConnection ic=(java.net.HttpURLConnection)new java.net.URL(iCover).openConnection();
																ic.setRequestProperty("User-Agent","Mozilla/5.0"); ic.setRequestProperty("Referer","https://mangafire.to/");
																ic.setConnectTimeout(6000); ic.setReadTimeout(6000);
																final android.graphics.Bitmap bmp=android.graphics.BitmapFactory.decodeStream(ic.getInputStream());
																hRef2.post(new Runnable(){ @Override public void run(){ if(bmp!=null){ iv.setImageBitmap(bmp); iv.animate().alpha(1f).setDuration(400).start(); } }});
															}catch(Exception ignored){}
														}}).start();
													android.view.View grad=new android.view.View(ctxRef);
													android.widget.FrameLayout.LayoutParams glp=new android.widget.FrameLayout.LayoutParams(-1,(int)(80*dpRef));
													glp.gravity=android.view.Gravity.BOTTOM; grad.setLayoutParams(glp);
													grad.setBackground(new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.BOTTOM_TOP,new int[]{0xFF0D0D1A,0xCC0D0D1A,0x00000000}));
													card.addView(grad);
													if(!iType.isEmpty()){
														android.widget.TextView tvType=new android.widget.TextView(ctxRef);
														tvType.setText(iType.toUpperCase()); tvType.setTextSize(7.5f); tvType.setTextColor(0xFF000000);
														tvType.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
														tvType.setPadding((int)(5*dpRef),(int)(2*dpRef),(int)(5*dpRef),(int)(2*dpRef));
														android.widget.FrameLayout.LayoutParams tpLp=new android.widget.FrameLayout.LayoutParams(-2,-2);
														tpLp.gravity=android.view.Gravity.TOP|android.view.Gravity.START;
														tpLp.topMargin=(int)(6*dpRef); tpLp.leftMargin=(int)(6*dpRef); tvType.setLayoutParams(tpLp);
														int bc=iType.equalsIgnoreCase("manhwa")?0xFF00BCD4:iType.equalsIgnoreCase("manhua")?0xFFFF7043:0xFFE91E8C;
														android.graphics.drawable.GradientDrawable bdg=new android.graphics.drawable.GradientDrawable();
														bdg.setColor(bc); bdg.setCornerRadius(4*dpRef); tvType.setBackground(bdg); card.addView(tvType);
													}
													android.widget.TextView tvChap=new android.widget.TextView(ctxRef);
													tvChap.setText("Ch."+iChap); tvChap.setTextSize(8); tvChap.setTextColor(0xFFFFFFFF);
													tvChap.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
													tvChap.setPadding((int)(4*dpRef),(int)(2*dpRef),(int)(4*dpRef),(int)(2*dpRef));
													android.widget.FrameLayout.LayoutParams chLp=new android.widget.FrameLayout.LayoutParams(-2,-2);
													chLp.gravity=android.view.Gravity.TOP|android.view.Gravity.END;
													chLp.topMargin=(int)(6*dpRef); chLp.rightMargin=(int)(6*dpRef); tvChap.setLayoutParams(chLp);
													android.graphics.drawable.GradientDrawable chBg=new android.graphics.drawable.GradientDrawable();
													chBg.setColor(0xDD000000); chBg.setCornerRadius(4*dpRef); tvChap.setBackground(chBg); card.addView(tvChap);
													android.widget.TextView tvTitle=new android.widget.TextView(ctxRef);
													tvTitle.setText(iTitle); tvTitle.setTextColor(0xFFFFFFFF); tvTitle.setTextSize(10);
													tvTitle.setTypeface(android.graphics.Typeface.DEFAULT_BOLD); tvTitle.setMaxLines(2);
													tvTitle.setEllipsize(android.text.TextUtils.TruncateAt.END);
													tvTitle.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
													android.widget.FrameLayout.LayoutParams titLp=new android.widget.FrameLayout.LayoutParams(-1,-2);
													titLp.gravity=android.view.Gravity.BOTTOM|android.view.Gravity.CENTER_HORIZONTAL;
													titLp.bottomMargin=(int)(6*dpRef); titLp.leftMargin=(int)(5*dpRef); titLp.rightMargin=(int)(5*dpRef);
													tvTitle.setLayoutParams(titLp); card.addView(tvTitle);
													card.setOnClickListener(new android.view.View.OnClickListener(){ @Override public void onClick(android.view.View v){
															android.content.Intent intent=new android.content.Intent(ctxRef,ViewActivity.class);
															intent.putExtra("book_url",iReadUrl); intent.putExtra("book_title",iTitle);
															intent.putExtra("book_author","MangaFire"); intent.putExtra("book_id","");
															ctxRef.startActivity(intent);
														}});
													card.setAlpha(0f); card.setScaleX(0.85f); card.setScaleY(0.85f);
													card.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(300).setStartDelay(iIdx*60L).setInterpolator(ovRef).start();
													cardRowRef.addView(card);
												}
											}});
									}catch(final Exception e){
										hRef.post(new Runnable(){ @Override public void run(){
												android.widget.Toast.makeText(ctxRef,"Manga: "+e.getMessage(),android.widget.Toast.LENGTH_SHORT).show();
											}});
									}
								}
							}).start();
						}
					}).start();
				}
			});
			mangaChipRow.addView(gChip);
		}
		
		// Initial data fetch for "Updated" tab
		new Thread(new Runnable(){
			@Override public void run(){
				try{
					java.net.HttpURLConnection c=(java.net.HttpURLConnection)new java.net.URL("https://mangafire.to/updated").openConnection();
					c.setRequestMethod("GET");
					c.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
					c.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
					c.setRequestProperty("Accept-Language","en-US,en;q=0.5");
					c.setRequestProperty("Referer","https://mangafire.to/");
					c.setConnectTimeout(10000); c.setReadTimeout(12000); c.setInstanceFollowRedirects(true);
					int code=c.getResponseCode();
					java.io.InputStream stream=(code>=200&&code<300)?c.getInputStream():c.getErrorStream();
					if(stream==null) return;
					java.io.BufferedReader br=new java.io.BufferedReader(new java.io.InputStreamReader(stream,"UTF-8"));
					StringBuilder sb=new StringBuilder(); String ln;
					while((ln=br.readLine())!=null){ sb.append(ln).append(" "); }
					br.close();
					final String html=sb.toString();
					final java.util.List<String[]> items=new java.util.ArrayList<String[]>();
					int cur=0;
					while(items.size()<20){
						// FIXED: Search for class="unit item- (with plain quotes, not escaped)
						int us=html.indexOf("class=\"unit item-",cur);
						if(us<0) break;
						int ue=html.indexOf("class=\"unit item-",us+20);
						if(ue<0) ue=html.length();
						String blk=html.substring(us,ue);
						String cover="";
						// FIXED: Search for src="https://static.mfcdn.cc/ (plain quotes)
						int csi=blk.indexOf("src=\"https://static.mfcdn.cc/");
						if(csi>=0){ csi+=5; int ei=blk.indexOf("\"",csi); if(ei>csi) cover=blk.substring(csi,ei); }
						String title="Unknown";
						// FIXED: Search for alt=" (plain quotes)
						int ti=blk.indexOf("alt=\"");
						if(ti>=0){ ti+=5; int ei=blk.indexOf("\"",ti); if(ei>ti) title=blk.substring(ti,ei); }
						String type="";
						// FIXED: Search for class="type"> (plain quotes)
						int tyi=blk.indexOf("class=\"type\">");
						if(tyi>=0){ tyi+=13; int ei=blk.indexOf("<",tyi); if(ei>tyi) type=blk.substring(tyi,ei).trim(); }
						String readUrl="https://mangafire.to/updated"; String chap="?";
						// FIXED: Search for href="/read/ (plain quotes)
						int ri=blk.indexOf("href=\"/read/");
						if(ri>=0){ ri+=6; int ei=blk.indexOf("\"",ri); if(ei>ri){ String rp=blk.substring(ri,ei); readUrl="https://mangafire.to"+rp; int ci2=rp.lastIndexOf("/chapter-"); if(ci2>=0) chap=rp.substring(ci2+9); } }
						if(!cover.isEmpty()&&!title.equals("Unknown")) items.add(new String[]{title,cover,readUrl,type,chap});
						cur=ue;
					}
					_uiH.post(new Runnable(){ @Override public void run(){
							mangaRow.removeAllViews();
							if(items.isEmpty()){
								android.widget.TextView empty=new android.widget.TextView(_ctx);
								empty.setText("No results"); empty.setTextColor(0xFFAAAAAA);
								empty.setPadding((int)(16*_dp),0,0,0);
								mangaRow.addView(empty); return;
							}
							for(int idx=0;idx<items.size();idx++){
								final String[] item=items.get(idx);
								final String iTitle=item[0],iCover=item[1],iReadUrl=item[2],iType=item[3],iChap=item[4];
								final int iIdx=idx;
								final android.widget.FrameLayout card=new android.widget.FrameLayout(_ctx);
								android.widget.LinearLayout.LayoutParams clp=new android.widget.LinearLayout.LayoutParams((int)(110*_dp),(int)(165*_dp));
								clp.rightMargin=(int)(12*_dp); card.setLayoutParams(clp);
								android.graphics.drawable.GradientDrawable cardGd=new android.graphics.drawable.GradientDrawable();
								cardGd.setColor(0xFF1A1A2E); cardGd.setCornerRadius(10*_dp); cardGd.setStroke((int)(1.2f*_dp),0x44E91E8C);
								card.setBackground(cardGd); card.setClipToOutline(true); card.setElevation(10*_dp);
								final android.widget.ImageView iv=new android.widget.ImageView(_ctx);
								iv.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1,-1));
								iv.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
								iv.setBackgroundColor(0xFF1E1E2E); iv.setAlpha(0f); card.addView(iv);
								new Thread(new Runnable(){ @Override public void run(){
										try{
											java.net.HttpURLConnection ic=(java.net.HttpURLConnection)new java.net.URL(iCover).openConnection();
											ic.setRequestProperty("User-Agent","Mozilla/5.0"); ic.setRequestProperty("Referer","https://mangafire.to/");
											ic.setConnectTimeout(6000); ic.setReadTimeout(6000);
											final android.graphics.Bitmap bmp=android.graphics.BitmapFactory.decodeStream(ic.getInputStream());
											_uiH.post(new Runnable(){ @Override public void run(){ if(bmp!=null){ iv.setImageBitmap(bmp); iv.animate().alpha(1f).setDuration(400).start(); } }});
										}catch(Exception ignored){}
									}}).start();
								android.view.View grad=new android.view.View(_ctx);
								android.widget.FrameLayout.LayoutParams glp=new android.widget.FrameLayout.LayoutParams(-1,(int)(80*_dp));
								glp.gravity=android.view.Gravity.BOTTOM; grad.setLayoutParams(glp);
								grad.setBackground(new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.BOTTOM_TOP,new int[]{0xFF0D0D1A,0xCC0D0D1A,0x00000000}));
								card.addView(grad);
								if(!iType.isEmpty()){
									android.widget.TextView tvType=new android.widget.TextView(_ctx);
									tvType.setText(iType.toUpperCase()); tvType.setTextSize(7.5f); tvType.setTextColor(0xFF000000);
									tvType.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
									tvType.setPadding((int)(5*_dp),(int)(2*_dp),(int)(5*_dp),(int)(2*_dp));
									android.widget.FrameLayout.LayoutParams tpLp=new android.widget.FrameLayout.LayoutParams(-2,-2);
									tpLp.gravity=android.view.Gravity.TOP|android.view.Gravity.START;
									tpLp.topMargin=(int)(6*_dp); tpLp.leftMargin=(int)(6*_dp); tvType.setLayoutParams(tpLp);
									int bc=iType.equalsIgnoreCase("manhwa")?0xFF00BCD4:iType.equalsIgnoreCase("manhua")?0xFFFF7043:0xFFE91E8C;
									android.graphics.drawable.GradientDrawable bdg=new android.graphics.drawable.GradientDrawable();
									bdg.setColor(bc); bdg.setCornerRadius(4*_dp); tvType.setBackground(bdg); card.addView(tvType);
								}
								android.widget.TextView tvChap=new android.widget.TextView(_ctx);
								tvChap.setText("Ch."+iChap); tvChap.setTextSize(8); tvChap.setTextColor(0xFFFFFFFF);
								tvChap.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
								tvChap.setPadding((int)(4*_dp),(int)(2*_dp),(int)(4*_dp),(int)(2*_dp));
								android.widget.FrameLayout.LayoutParams chLp=new android.widget.FrameLayout.LayoutParams(-2,-2);
								chLp.gravity=android.view.Gravity.TOP|android.view.Gravity.END;
								chLp.topMargin=(int)(6*_dp); chLp.rightMargin=(int)(6*_dp); tvChap.setLayoutParams(chLp);
								android.graphics.drawable.GradientDrawable chBg=new android.graphics.drawable.GradientDrawable();
								chBg.setColor(0xDD000000); chBg.setCornerRadius(4*_dp); tvChap.setBackground(chBg); card.addView(tvChap);
								android.widget.TextView tvTitle=new android.widget.TextView(_ctx);
								tvTitle.setText(iTitle); tvTitle.setTextColor(0xFFFFFFFF); tvTitle.setTextSize(10);
								tvTitle.setTypeface(android.graphics.Typeface.DEFAULT_BOLD); tvTitle.setMaxLines(2);
								tvTitle.setEllipsize(android.text.TextUtils.TruncateAt.END);
								tvTitle.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
								android.widget.FrameLayout.LayoutParams titLp=new android.widget.FrameLayout.LayoutParams(-1,-2);
								titLp.gravity=android.view.Gravity.BOTTOM|android.view.Gravity.CENTER_HORIZONTAL;
								titLp.bottomMargin=(int)(6*_dp); titLp.leftMargin=(int)(5*_dp); titLp.rightMargin=(int)(5*_dp);
								tvTitle.setLayoutParams(titLp); card.addView(tvTitle);
								card.setOnClickListener(new android.view.View.OnClickListener(){ @Override public void onClick(android.view.View v){
										android.content.Intent intent=new android.content.Intent(_ctx,ViewActivity.class);
										intent.putExtra("book_url",iReadUrl); intent.putExtra("book_title",iTitle);
										intent.putExtra("book_author","MangaFire"); intent.putExtra("book_id","");
										_ctx.startActivity(intent);
									}});
								card.setAlpha(0f); card.setScaleX(0.85f); card.setScaleY(0.85f);
								card.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(300).setStartDelay(iIdx*60L).setInterpolator(_overshoot).start();
								mangaRow.addView(card);
							}
						}});
				}catch(final Exception e){
					_uiH.post(new Runnable(){ @Override public void run(){
							android.widget.Toast.makeText(_ctx,"Manga: "+e.getMessage(),android.widget.Toast.LENGTH_SHORT).show();
						}});
				}
			}
		}).start();
		
		// ════════════════════════════════════════════════════════════════
		// ADD MANGA SECTION TO LAYOUT - THIS IS THE CRITICAL PART
		// ════════════════════════════════════════════════════════════════
		
		// Get reference to the LinearLayout that contains _dynamicRows
		//final android.widget.LinearLayout _linearContent = (android.widget.LinearLayout) findViewById(R.id.linear_content);
		
		// Add manga section to linear_content AFTER _dynamicRows
		_linearContent.addView(mangaSection);
	}
	
}