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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.*;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

public class DetailActivity extends AppCompatActivity {
	
	private DetailBinding binding;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = DetailBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		binding.wvPlayer.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				
				super.onPageFinished(_param1, _param2);
			}
		});
	}
	
	private void initializeLogic() {
		// ════════════════════════════════════════════════════════════════
		// DetailActivity onCreate — ULTRA PREMIUM EDITION (Java 7 / Sketchware)
		// Cinematic entrance · Glassmorphism · Neon accents · Rich animations
		// NO lambdas · All anonymous classes · All captured vars final
		// ════════════════════════════════════════════════════════════════
		
		final android.content.Context _ctx = DetailActivity.this;
		final float _dp = getResources().getDisplayMetrics().density;
		final android.os.Handler _uiH = new android.os.Handler(android.os.Looper.getMainLooper());
		final android.view.animation.Interpolator _overshoot = new android.view.animation.OvershootInterpolator(1.4f);
		final android.view.animation.Interpolator _spring    = new android.view.animation.DecelerateInterpolator(2.5f);
		final android.os.Vibrator _vib = (android.os.Vibrator) getSystemService(android.content.Context.VIBRATOR_SERVICE);
		
		// ── Views ──
		final android.webkit.WebView      _wvPlayer         = (android.webkit.WebView)      findViewById(R.id.wv_player);
		final android.widget.ProgressBar  _pbPlayer         = (android.widget.ProgressBar)  findViewById(R.id.pb_player);
		final android.view.View           _btnBack          = findViewById(R.id.btn_detail_back);
		final android.widget.TextView     _tvTitle          = (android.widget.TextView)     findViewById(R.id.tv_detail_title);
		final android.widget.TextView     _tvMeta           = (android.widget.TextView)     findViewById(R.id.tv_detail_meta);
		final android.widget.TextView     _btnDownload      = (android.widget.TextView)     findViewById(R.id.btn_detail_download);
		final android.widget.TextView     _btnShare         = (android.widget.TextView)     findViewById(R.id.btn_detail_share);
		final android.widget.TextView     _btnAdd           = (android.widget.TextView)     findViewById(R.id.btn_detail_add);
		final android.widget.LinearLayout _episodesSection  = (android.widget.LinearLayout) findViewById(R.id.linear_episodes_section);
		final android.widget.Spinner      _spinnerSeasons   = (android.widget.Spinner)      findViewById(R.id.spinner_seasons);
		final android.widget.LinearLayout _episodesRow      = (android.widget.LinearLayout) findViewById(R.id.linear_episodes_row);
		
		final android.widget.FrameLayout _customViewContainer = new android.widget.FrameLayout(_ctx);
		_customViewContainer.setBackgroundColor(0xFF000000);
		final android.view.ViewGroup _decorView = (android.view.ViewGroup) getWindow().getDecorView();
		
		// ════════════════════════════════════════════════════════════════
		// UI HELPERS
		// ════════════════════════════════════════════════════════════════
		class UIHelper {
			
			public void haptic() {
				try {
					if (_vib != null && android.os.Build.VERSION.SDK_INT >= 26) {
						_vib.vibrate(android.os.VibrationEffect.createOneShot(18, 80));
					}
				} catch (Exception ignored) {}
			}
			
			public void applyRoundedBackground(android.view.View v, int color, float radiusDp) {
				android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
				gd.setColor(color);
				gd.setCornerRadius(radiusDp * _dp);
				v.setBackground(gd);
				v.setClipToOutline(true);
			}
			
			public void applyGlass(android.view.View v, float radiusDp) {
				android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
				gd.setColor(0x22FFFFFF);
				gd.setCornerRadius(radiusDp * _dp);
				gd.setStroke((int)(_dp), 0x33FFFFFF);
				v.setBackground(gd);
				v.setClipToOutline(true);
				v.setElevation(4 * _dp);
			}
			
			public void applyNeonCard(android.view.View v, float radiusDp) {
				android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
				gd.setColor(0xFF111120);
				gd.setCornerRadius(radiusDp * _dp);
				gd.setStroke((int)(1.2f * _dp), 0x3300E676);
				v.setBackground(gd);
				v.setClipToOutline(true);
				v.setElevation(10 * _dp);
			}
			
			public void applyAccent(android.view.View v, float radiusDp) {
				android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
				gd.setColor(0xFF00E676);
				gd.setCornerRadius(radiusDp * _dp);
				v.setBackground(gd);
				v.setClipToOutline(true);
				v.setElevation(6 * _dp);
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
							v.animate().scaleX(1.05f).scaleY(1.05f).setDuration(180)
							.setInterpolator(_overshoot)
							.withEndAction(new Runnable() {
								@Override public void run() {
									vRef.animate().scaleX(1f).scaleY(1f).setDuration(130).start();
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
			
			public void popIn(android.view.View v, int index) {
				v.setAlpha(0f);
				v.setTranslationY(24f);
				v.animate().alpha(1f).translationY(0f)
				.setDuration(400).setStartDelay(index * 55L)
				.setInterpolator(_overshoot).start();
			}
			
			public android.view.View makeShimmer(int wDp, int hDp, float cornerDp) {
				android.widget.FrameLayout host = new android.widget.FrameLayout(_ctx);
				android.widget.LinearLayout.LayoutParams lp =
				new android.widget.LinearLayout.LayoutParams((int)(wDp * _dp), (int)(hDp * _dp));
				lp.setMarginEnd((int)(12 * _dp));
				host.setLayoutParams(lp);
				applyRoundedBackground(host, 0xFF1E1E30, cornerDp);
				host.setClipToOutline(true);
				
				final android.view.View sweep = new android.view.View(_ctx);
				sweep.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1, -1));
				android.graphics.drawable.GradientDrawable sweepGd =
				new android.graphics.drawable.GradientDrawable(
				android.graphics.drawable.GradientDrawable.Orientation.LEFT_RIGHT,
				new int[]{0x00FFFFFF, 0x1AFFFFFF, 0x00FFFFFF});
				sweep.setBackground(sweepGd);
				host.addView(sweep);
				
				android.animation.ObjectAnimator shimAnim =
				android.animation.ObjectAnimator.ofFloat(sweep, "translationX",
				-(wDp * _dp), wDp * _dp * 2f);
				shimAnim.setDuration(1300);
				shimAnim.setRepeatCount(-1);
				shimAnim.setInterpolator(new android.view.animation.LinearInterpolator());
				shimAnim.start();
				
				android.animation.ObjectAnimator pulse =
				android.animation.ObjectAnimator.ofFloat(host, "alpha", 1f, 0.4f);
				pulse.setDuration(850);
				pulse.setRepeatMode(android.animation.ObjectAnimator.REVERSE);
				pulse.setRepeatCount(-1);
				pulse.start();
				return host;
			}
		}
		final UIHelper ui = new UIHelper();
		
		// ════════════════════════════════════════════════════════════════
		// INTENT DATA
		// ════════════════════════════════════════════════════════════════
		final String _title   = getIntent().getStringExtra("title");
		final String _year    = getIntent().getStringExtra("year");
		final String _imdbId  = getIntent().getStringExtra("imdb");
		final String _tmdbId  = getIntent().getStringExtra("show_id");
		final String _subject = getIntent().getStringExtra("subject");
		final String _thumb   = getIntent().getStringExtra("thumb");
		final String _desc    = getIntent().getStringExtra("desc");
		final String TARGET_DB_ID = (_tmdbId != null && !_tmdbId.isEmpty()) ? _tmdbId : _imdbId;
		final boolean isTV = (_tmdbId != null && !_tmdbId.trim().isEmpty()
		&& _subject != null && !_subject.equals("Movie"));
		
		// ════════════════════════════════════════════════════════════════
		// STYLE ACTION BUTTONS + ANIMATED BACK BUTTON
		// ════════════════════════════════════════════════════════════════
		ui.applyGlass(_btnBack, 20);
		_btnBack.setElevation(8 * _dp);
		
		// Breathing neon border on back button
		android.animation.ValueAnimator backPulse = android.animation.ValueAnimator.ofFloat(0f, 1f);
		backPulse.setDuration(2000);
		backPulse.setRepeatMode(android.animation.ValueAnimator.REVERSE);
		backPulse.setRepeatCount(-1);
		backPulse.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
			@Override public void onAnimationUpdate(android.animation.ValueAnimator anim) {
				float v = (float) anim.getAnimatedValue();
				android.graphics.drawable.GradientDrawable gd =
				new android.graphics.drawable.GradientDrawable();
				gd.setColor(0x22FFFFFF);
				gd.setCornerRadius(20 * _dp);
				gd.setStroke((int)(1.5f * _dp), ((int)(0x20 + v * 0x35) << 24) | 0x00E676);
				_btnBack.setBackground(gd);
			}
		});
		backPulse.start();
		
		ui.applyNeonCard(_btnAdd,      12);
		ui.applyNeonCard(_btnShare,    12);
		ui.applyNeonCard(_btnDownload, 12);
		
		// Title & Meta cinematic slide-in
		_tvTitle.setText(_title != null ? _title : "Unknown Title");
		_tvTitle.setShadowLayer(10, 0, 3, 0xAA000000);
		_tvTitle.setAlpha(0f);
		_tvTitle.setTranslationY(20f);
		_tvTitle.animate().alpha(1f).translationY(0f).setDuration(600)
		.setStartDelay(100).setInterpolator(_spring).start();
		
		_tvMeta.setText("\u2B50 " + (_year != null ? _year : "\u2014")
		+ "  \u2022  " + (_subject != null ? _subject : "Video"));
		_tvMeta.setAlpha(0f);
		_tvMeta.animate().alpha(1f).setDuration(500).setStartDelay(250).start();
		
		// ════════════════════════════════════════════════════════════════
		// DESCRIPTION CARD — expandable with read more
		// ════════════════════════════════════════════════════════════════
		android.widget.LinearLayout _mainContent =
		(android.widget.LinearLayout) _tvTitle.getParent();
		
		final android.widget.LinearLayout descCard = new android.widget.LinearLayout(_ctx);
		descCard.setOrientation(android.widget.LinearLayout.VERTICAL);
		android.widget.LinearLayout.LayoutParams dcLp =
		new android.widget.LinearLayout.LayoutParams(-1, -2);
		dcLp.setMargins(0, (int)(16 * _dp), 0, (int)(8 * _dp));
		descCard.setLayoutParams(dcLp);
		ui.applyNeonCard(descCard, 12);
		descCard.setPadding((int)(14 * _dp), (int)(14 * _dp), (int)(14 * _dp), (int)(14 * _dp));
		
		final android.widget.TextView tvDesc = new android.widget.TextView(_ctx);
		final String descText = (_desc != null && !_desc.isEmpty()) ? _desc : "No description available.";
		tvDesc.setText(descText);
		tvDesc.setTextColor(0xFFBBBBCC);
		tvDesc.setTextSize(13);
		tvDesc.setLineSpacing(4, 1f);
		tvDesc.setMaxLines(3);
		tvDesc.setEllipsize(android.text.TextUtils.TruncateAt.END);
		descCard.addView(tvDesc);
		
		final android.widget.TextView tvReadMore = new android.widget.TextView(_ctx);
		tvReadMore.setText("Read more \u25BE");
		tvReadMore.setTextColor(0xFF00E676);
		tvReadMore.setTextSize(12);
		tvReadMore.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
		android.widget.LinearLayout.LayoutParams rmLp =
		new android.widget.LinearLayout.LayoutParams(-2, -2);
		rmLp.topMargin = (int)(8 * _dp);
		tvReadMore.setLayoutParams(rmLp);
		descCard.addView(tvReadMore);
		
		final boolean[] descExpanded = {false};
		ui.addBounceClick(tvReadMore, new Runnable() {
			@Override public void run() {
				descExpanded[0] = !descExpanded[0];
				if (descExpanded[0]) {
					tvDesc.setMaxLines(Integer.MAX_VALUE);
					tvDesc.setEllipsize(null);
					tvReadMore.setText("Show less \u25B4");
				} else {
					tvDesc.setMaxLines(3);
					tvDesc.setEllipsize(android.text.TextUtils.TruncateAt.END);
					tvReadMore.setText("Read more \u25BE");
				}
			}
		});
		
		descCard.setAlpha(0f);
		descCard.setTranslationY(16f);
		descCard.animate().alpha(1f).translationY(0f).setDuration(500)
		.setStartDelay(200).setInterpolator(_spring).start();
		_mainContent.addView(descCard);
		
		// Bottom container for all sections
		final android.widget.LinearLayout bottomContainer = new android.widget.LinearLayout(_ctx);
		bottomContainer.setOrientation(android.widget.LinearLayout.VERTICAL);
		bottomContainer.setPadding((int)(16 * _dp), 0, (int)(16 * _dp), (int)(40 * _dp));
		((android.widget.LinearLayout) _episodesSection.getParent()).addView(bottomContainer);
		
		// ════════════════════════════════════════════════════════════════
		// GRADIENT DIVIDER HELPER (reused below)
		// ════════════════════════════════════════════════════════════════
		// Used inline: each section gets a fading neon divider
		
		// ════════════════════════════════════════════════════════════════
		// COMMUNITY (LIKES + COMMENTS)
		// ════════════════════════════════════════════════════════════════
		android.view.View div1 = new android.view.View(_ctx);
		android.widget.LinearLayout.LayoutParams dv1Lp =
		new android.widget.LinearLayout.LayoutParams(-1, (int)(1.5f * _dp));
		dv1Lp.setMargins(0, (int)(28 * _dp), 0, (int)(24 * _dp));
		div1.setLayoutParams(dv1Lp);
		div1.setBackground(new android.graphics.drawable.GradientDrawable(
		android.graphics.drawable.GradientDrawable.Orientation.LEFT_RIGHT,
		new int[]{0x00141416, 0xFF00E676, 0x00141416}));
		bottomContainer.addView(div1);
		
		// Community section header
		android.widget.LinearLayout socialHeader = new android.widget.LinearLayout(_ctx);
		socialHeader.setOrientation(android.widget.LinearLayout.HORIZONTAL);
		socialHeader.setGravity(android.view.Gravity.CENTER_VERTICAL);
		android.widget.LinearLayout.LayoutParams shLp =
		new android.widget.LinearLayout.LayoutParams(-1, -2);
		shLp.setMargins(0, 0, 0, (int)(16 * _dp));
		socialHeader.setLayoutParams(shLp);
		
		android.widget.LinearLayout communityLeft = new android.widget.LinearLayout(_ctx);
		communityLeft.setOrientation(android.widget.LinearLayout.VERTICAL);
		android.widget.TextView tvSocialTitle = new android.widget.TextView(_ctx);
		tvSocialTitle.setText("Community");
		tvSocialTitle.setTextColor(0xFFFFFFFF);
		tvSocialTitle.setTextSize(19);
		tvSocialTitle.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
		tvSocialTitle.setShadowLayer(6, 0, 2, 0x8800E676);
		communityLeft.addView(tvSocialTitle);
		android.widget.TextView tvSocialSub = new android.widget.TextView(_ctx);
		tvSocialSub.setText("Reactions & Discussions");
		tvSocialSub.setTextColor(0xFF666688);
		tvSocialSub.setTextSize(11);
		communityLeft.addView(tvSocialSub);
		socialHeader.addView(communityLeft,
		new android.widget.LinearLayout.LayoutParams(0, -2, 1));
		
		// Like button pill
		final android.widget.LinearLayout btnLikeWrap = new android.widget.LinearLayout(_ctx);
		btnLikeWrap.setOrientation(android.widget.LinearLayout.HORIZONTAL);
		btnLikeWrap.setGravity(android.view.Gravity.CENTER);
		btnLikeWrap.setPadding((int)(14 * _dp), (int)(10 * _dp), (int)(14 * _dp), (int)(10 * _dp));
		ui.applyNeonCard(btnLikeWrap, 24);
		
		final android.widget.TextView tvHeart = new android.widget.TextView(_ctx);
		tvHeart.setText("\u2665");
		tvHeart.setTextColor(0xFFFF4466);
		tvHeart.setTextSize(16);
		btnLikeWrap.addView(tvHeart);
		
		final android.widget.TextView tvLikeCount = new android.widget.TextView(_ctx);
		tvLikeCount.setText("  \u2014");
		tvLikeCount.setTextColor(0xFFFFFFFF);
		tvLikeCount.setTextSize(14);
		tvLikeCount.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
		btnLikeWrap.addView(tvLikeCount);
		
		socialHeader.addView(btnLikeWrap);
		bottomContainer.addView(socialHeader);
		
		socialHeader.setAlpha(0f);
		socialHeader.setTranslationX(20f);
		socialHeader.animate().alpha(1f).translationX(0f).setDuration(450)
		.setInterpolator(_spring).start();
		
		// Fetch like count
		final String likeUrl = "https://api.counterapi.dev/v1/moviebox/like_" + TARGET_DB_ID;
		new Thread(new Runnable() {
			@Override public void run() {
				try {
					java.net.HttpURLConnection c = (java.net.HttpURLConnection)
					new java.net.URL(likeUrl).openConnection();
					c.setRequestProperty("Accept", "application/json");
					java.io.BufferedReader br =
					new java.io.BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
					String res = br.readLine(); br.close();
					final int count = (res != null && res.contains("count"))
					? new org.json.JSONObject(res).optInt("count", 0) : 0;
					_uiH.post(new Runnable() {
						@Override public void run() { tvLikeCount.setText("  " + count); }
					});
				} catch (Exception ignored) {
					_uiH.post(new Runnable() {
						@Override public void run() { tvLikeCount.setText("  0"); }
					});
				}
			}
		}).start();
		
		final boolean[] liked = {false};
		ui.addBounceClick(btnLikeWrap, new Runnable() {
			@Override public void run() {
				if (liked[0]) return;
				liked[0] = true;
				// Heart burst animation
				tvHeart.animate().scaleX(1.6f).scaleY(1.6f).setDuration(150)
				.setInterpolator(_overshoot)
				.withEndAction(new Runnable() {
					@Override public void run() {
						tvHeart.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
					}
				}).start();
				ui.applyAccent(btnLikeWrap, 24);
				tvHeart.setTextColor(0xFF000000);
				tvLikeCount.setTextColor(0xFF000000);
				new Thread(new Runnable() {
					@Override public void run() {
						try {
							java.net.HttpURLConnection c = (java.net.HttpURLConnection)
							new java.net.URL(likeUrl + "/up").openConnection();
							java.io.BufferedReader br =
							new java.io.BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
							String res = br.readLine(); br.close();
							final int newCount = (res != null && res.contains("count"))
							? new org.json.JSONObject(res).optInt("count", 0) : 0;
							_uiH.post(new Runnable() {
								@Override public void run() { tvLikeCount.setText("  " + newCount); }
							});
						} catch (Exception ignored) {}
					}
				}).start();
			}
		});
		
		// ── Comment Input Card ──
		android.widget.LinearLayout inputCard = new android.widget.LinearLayout(_ctx);
		inputCard.setOrientation(android.widget.LinearLayout.HORIZONTAL);
		inputCard.setGravity(android.view.Gravity.CENTER_VERTICAL);
		android.widget.LinearLayout.LayoutParams icLp =
		new android.widget.LinearLayout.LayoutParams(-1, -2);
		icLp.setMargins(0, (int)(12 * _dp), 0, (int)(16 * _dp));
		inputCard.setLayoutParams(icLp);
		ui.applyNeonCard(inputCard, 14);
		inputCard.setPadding((int)(14 * _dp), (int)(4 * _dp), (int)(4 * _dp), (int)(4 * _dp));
		
		final android.widget.EditText editComment = new android.widget.EditText(_ctx);
		editComment.setHint("Share your thoughts...");
		editComment.setHintTextColor(0xFF44445A);
		editComment.setTextColor(0xFFDDDDFF);
		editComment.setTextSize(13);
		editComment.setBackground(null);
		editComment.setPadding(0, (int)(10 * _dp), 0, (int)(10 * _dp));
		inputCard.addView(editComment,
		new android.widget.LinearLayout.LayoutParams(0, -2, 1));
		
		final android.widget.TextView btnPost = new android.widget.TextView(_ctx);
		btnPost.setText("Post");
		btnPost.setTextColor(0xFF000000);
		btnPost.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
		btnPost.setTextSize(13);
		btnPost.setPadding((int)(16 * _dp), (int)(10 * _dp), (int)(16 * _dp), (int)(10 * _dp));
		android.widget.LinearLayout.LayoutParams bpLp =
		new android.widget.LinearLayout.LayoutParams(-2, -2);
		bpLp.setMargins((int)(8 * _dp), (int)(4 * _dp), (int)(4 * _dp), (int)(4 * _dp));
		btnPost.setLayoutParams(bpLp);
		ui.applyAccent(btnPost, 10);
		inputCard.addView(btnPost);
		
		inputCard.setAlpha(0f);
		inputCard.setTranslationY(12f);
		inputCard.animate().alpha(1f).translationY(0f).setDuration(400)
		.setStartDelay(100).setInterpolator(_spring).start();
		bottomContainer.addView(inputCard);
		
		final android.widget.LinearLayout commentsList = new android.widget.LinearLayout(_ctx);
		commentsList.setOrientation(android.widget.LinearLayout.VERTICAL);
		bottomContainer.addView(commentsList);
		
		final String BUCKET = "https://kvdb.io/2qK2hH3p1y6M/comments_" + TARGET_DB_ID;
		
		class CommentManager {
			public void loadComments() {
				new Thread(new Runnable() {
					@Override public void run() {
						try {
							java.net.HttpURLConnection c = (java.net.HttpURLConnection)
							new java.net.URL(BUCKET).openConnection();
							java.io.BufferedReader br =
							new java.io.BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
							StringBuilder sb = new StringBuilder(); String ln;
							while ((ln = br.readLine()) != null) sb.append(ln); br.close();
							final org.json.JSONArray arr = new org.json.JSONArray(sb.toString());
							_uiH.post(new Runnable() {
								@Override public void run() {
									commentsList.removeAllViews();
									if (arr.length() == 0) {
										android.widget.TextView empty = new android.widget.TextView(_ctx);
										empty.setText("No comments yet \u2014 be the first!");
										empty.setTextColor(0xFF444466);
										empty.setTextSize(13);
										empty.setGravity(android.view.Gravity.CENTER);
										android.widget.LinearLayout.LayoutParams eLp =
										new android.widget.LinearLayout.LayoutParams(-1, -2);
										eLp.setMargins(0, (int)(8 * _dp), 0, (int)(8 * _dp));
										empty.setLayoutParams(eLp);
										commentsList.addView(empty);
										return;
									}
									for (int i = 0; i < arr.length(); i++) {
										final String text = arr.optString(i, "");
										if (text.isEmpty()) continue;
										
										android.widget.LinearLayout commentCard =
										new android.widget.LinearLayout(_ctx);
										commentCard.setOrientation(android.widget.LinearLayout.VERTICAL);
										android.widget.LinearLayout.LayoutParams cclp =
										new android.widget.LinearLayout.LayoutParams(-1, -2);
										cclp.setMargins(0, 0, 0, (int)(8 * _dp));
										commentCard.setLayoutParams(cclp);
										commentCard.setPadding((int)(14 * _dp), (int)(12 * _dp),
										(int)(14 * _dp), (int)(12 * _dp));
										ui.applyNeonCard(commentCard, 10);
										
										android.widget.LinearLayout nameRow =
										new android.widget.LinearLayout(_ctx);
										nameRow.setOrientation(android.widget.LinearLayout.HORIZONTAL);
										nameRow.setGravity(android.view.Gravity.CENTER_VERTICAL);
										
										android.widget.TextView tvAvatar = new android.widget.TextView(_ctx);
										tvAvatar.setText("\uD83D\uDC64");
										tvAvatar.setTextSize(14);
										tvAvatar.setPadding(0, 0, (int)(8 * _dp), 0);
										nameRow.addView(tvAvatar);
										
										android.widget.TextView tvUser = new android.widget.TextView(_ctx);
										tvUser.setText("Viewer");
										tvUser.setTextColor(0xFF00E676);
										tvUser.setTextSize(11);
										tvUser.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
										nameRow.addView(tvUser);
										commentCard.addView(nameRow);
										
										android.widget.TextView tc = new android.widget.TextView(_ctx);
										tc.setText(text);
										tc.setTextColor(0xFFCCCCDD);
										tc.setTextSize(13);
										tc.setLineSpacing(3, 1f);
										android.widget.LinearLayout.LayoutParams tclp =
										new android.widget.LinearLayout.LayoutParams(-1, -2);
										tclp.topMargin = (int)(6 * _dp);
										tc.setLayoutParams(tclp);
										commentCard.addView(tc);
										
										ui.popIn(commentCard, i);
										commentsList.addView(commentCard);
									}
								}
							});
						} catch (Exception ignored) {}
					}
				}).start();
			}
		}
		final CommentManager cm = new CommentManager();
		cm.loadComments();
		
		ui.addBounceClick(btnPost, new Runnable() {
			@Override public void run() {
				final String newComment = editComment.getText().toString().trim();
				if (newComment.isEmpty()) {
					// Shake animation for empty input
					editComment.animate().translationX(-10f).setDuration(60)
					.withEndAction(new Runnable() {
						@Override public void run() {
							editComment.animate().translationX(10f).setDuration(60)
							.withEndAction(new Runnable() {
								@Override public void run() {
									editComment.animate().translationX(0f).setDuration(60).start();
								}
							}).start();
						}
					}).start();
					return;
				}
				editComment.setText("");
				btnPost.setText("...");
				new Thread(new Runnable() {
					@Override public void run() {
						try {
							java.net.HttpURLConnection c = (java.net.HttpURLConnection)
							new java.net.URL(BUCKET).openConnection();
							org.json.JSONArray arr = new org.json.JSONArray();
							if (c.getResponseCode() == 200) {
								java.io.BufferedReader br =
								new java.io.BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
								StringBuilder sb = new StringBuilder(); String ln;
								while ((ln = br.readLine()) != null) sb.append(ln); br.close();
								try { arr = new org.json.JSONArray(sb.toString()); } catch (Exception ignored) {}
							}
							org.json.JSONArray updated = new org.json.JSONArray();
							updated.put(newComment);
							for (int i = 0; i < Math.min(arr.length(), 20); i++)
							updated.put(arr.getString(i));
							
							java.net.HttpURLConnection out = (java.net.HttpURLConnection)
							new java.net.URL(BUCKET).openConnection();
							out.setRequestMethod("POST");
							out.setDoOutput(true);
							out.setRequestProperty("Content-Type", "application/json");
							out.getOutputStream().write(updated.toString().getBytes());
							out.getInputStream();
							
							_uiH.post(new Runnable() {
								@Override public void run() {
									btnPost.setText("Post");
									cm.loadComments();
								}
							});
						} catch (Exception ignored) {
							_uiH.post(new Runnable() {
								@Override public void run() { btnPost.setText("Post"); }
							});
						}
					}
				}).start();
			}
		});
		
		// ════════════════════════════════════════════════════════════════
		// CAST SECTION
		// ════════════════════════════════════════════════════════════════
		android.view.View div2 = new android.view.View(_ctx);
		android.widget.LinearLayout.LayoutParams dv2Lp =
		new android.widget.LinearLayout.LayoutParams(-1, (int)(1.5f * _dp));
		dv2Lp.setMargins(0, (int)(28 * _dp), 0, (int)(24 * _dp));
		div2.setLayoutParams(dv2Lp);
		div2.setBackground(new android.graphics.drawable.GradientDrawable(
		android.graphics.drawable.GradientDrawable.Orientation.LEFT_RIGHT,
		new int[]{0x00141416, 0xFF00E676, 0x00141416}));
		bottomContainer.addView(div2);
		
		android.widget.LinearLayout castHeader = new android.widget.LinearLayout(_ctx);
		castHeader.setOrientation(android.widget.LinearLayout.VERTICAL);
		android.widget.LinearLayout.LayoutParams chLp =
		new android.widget.LinearLayout.LayoutParams(-2, -2);
		chLp.setMargins(0, 0, 0, (int)(14 * _dp));
		castHeader.setLayoutParams(chLp);
		android.widget.TextView tvCastTitle = new android.widget.TextView(_ctx);
		tvCastTitle.setText("Cast & Crew");
		tvCastTitle.setTextColor(0xFFFFFFFF);
		tvCastTitle.setTextSize(19);
		tvCastTitle.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
		tvCastTitle.setShadowLayer(6, 0, 2, 0x8800E676);
		castHeader.addView(tvCastTitle);
		android.widget.TextView tvCastSub = new android.widget.TextView(_ctx);
		tvCastSub.setText("Featured performers");
		tvCastSub.setTextColor(0xFF666688);
		tvCastSub.setTextSize(11);
		castHeader.addView(tvCastSub);
		bottomContainer.addView(castHeader);
		
		android.widget.HorizontalScrollView hScrollCast = new android.widget.HorizontalScrollView(_ctx);
		hScrollCast.setHorizontalScrollBarEnabled(false);
		hScrollCast.setClipToPadding(false);
		hScrollCast.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-1, -2));
		final android.widget.LinearLayout castRow = new android.widget.LinearLayout(_ctx);
		castRow.setOrientation(android.widget.LinearLayout.HORIZONTAL);
		castRow.setPadding(0, 0, (int)(16 * _dp), 0);
		hScrollCast.addView(castRow);
		// Cast shimmer placeholders (circles)
		for (int s = 0; s < 5; s++) {
			android.widget.LinearLayout skelWrap = new android.widget.LinearLayout(_ctx);
			skelWrap.setOrientation(android.widget.LinearLayout.VERTICAL);
			skelWrap.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
			android.widget.LinearLayout.LayoutParams swLp =
			new android.widget.LinearLayout.LayoutParams((int)(80 * _dp), -2);
			swLp.setMarginEnd((int)(14 * _dp));
			skelWrap.setLayoutParams(swLp);
			android.view.View circle = new android.view.View(_ctx);
			android.widget.LinearLayout.LayoutParams cirLp =
			new android.widget.LinearLayout.LayoutParams((int)(72 * _dp), (int)(72 * _dp));
			circle.setLayoutParams(cirLp);
			android.graphics.drawable.GradientDrawable cirGd =
			new android.graphics.drawable.GradientDrawable();
			cirGd.setColor(0xFF1E1E30);
			cirGd.setShape(android.graphics.drawable.GradientDrawable.OVAL);
			circle.setBackground(cirGd);
			android.animation.ObjectAnimator cp =
			android.animation.ObjectAnimator.ofFloat(circle, "alpha", 1f, 0.35f);
			cp.setDuration(800 + s * 80);
			cp.setRepeatMode(android.animation.ObjectAnimator.REVERSE);
			cp.setRepeatCount(-1); cp.start();
			skelWrap.addView(circle);
			android.view.View nameSkel = new android.view.View(_ctx);
			android.widget.LinearLayout.LayoutParams nsLp =
			new android.widget.LinearLayout.LayoutParams((int)(55 * _dp), (int)(10 * _dp));
			nsLp.topMargin = (int)(6 * _dp);
			nameSkel.setLayoutParams(nsLp);
			ui.applyRoundedBackground(nameSkel, 0xFF1E1E30, 5);
			skelWrap.addView(nameSkel);
			castRow.addView(skelWrap);
		}
		bottomContainer.addView(hScrollCast);
		
		// ════════════════════════════════════════════════════════════════
		// YOU MIGHT ALSO LIKE
		// ════════════════════════════════════════════════════════════════
		android.view.View div3 = new android.view.View(_ctx);
		android.widget.LinearLayout.LayoutParams dv3Lp =
		new android.widget.LinearLayout.LayoutParams(-1, (int)(1.5f * _dp));
		dv3Lp.setMargins(0, (int)(28 * _dp), 0, (int)(24 * _dp));
		div3.setLayoutParams(dv3Lp);
		div3.setBackground(new android.graphics.drawable.GradientDrawable(
		android.graphics.drawable.GradientDrawable.Orientation.LEFT_RIGHT,
		new int[]{0x00141416, 0xFF00E676, 0x00141416}));
		bottomContainer.addView(div3);
		
		android.widget.LinearLayout recHeader = new android.widget.LinearLayout(_ctx);
		recHeader.setOrientation(android.widget.LinearLayout.VERTICAL);
		android.widget.LinearLayout.LayoutParams rhLp =
		new android.widget.LinearLayout.LayoutParams(-2, -2);
		rhLp.setMargins(0, 0, 0, (int)(14 * _dp));
		recHeader.setLayoutParams(rhLp);
		android.widget.TextView tvRecTitle = new android.widget.TextView(_ctx);
		tvRecTitle.setText("You Might Also Like");
		tvRecTitle.setTextColor(0xFFFFFFFF);
		tvRecTitle.setTextSize(19);
		tvRecTitle.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
		tvRecTitle.setShadowLayer(6, 0, 2, 0x8800E676);
		recHeader.addView(tvRecTitle);
		android.widget.TextView tvRecSub = new android.widget.TextView(_ctx);
		tvRecSub.setText("Curated picks for you");
		tvRecSub.setTextColor(0xFF666688);
		tvRecSub.setTextSize(11);
		recHeader.addView(tvRecSub);
		bottomContainer.addView(recHeader);
		
		android.widget.HorizontalScrollView hScrollRec = new android.widget.HorizontalScrollView(_ctx);
		hScrollRec.setHorizontalScrollBarEnabled(false);
		hScrollRec.setClipToPadding(false);
		hScrollRec.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-1, -2));
		final android.widget.LinearLayout recRow = new android.widget.LinearLayout(_ctx);
		recRow.setOrientation(android.widget.LinearLayout.HORIZONTAL);
		recRow.setPadding(0, 0, (int)(16 * _dp), 0);
		hScrollRec.addView(recRow);
		for (int s = 0; s < 5; s++) recRow.addView(ui.makeShimmer(110, 165, 10));
		bottomContainer.addView(hScrollRec);
		
		// ════════════════════════════════════════════════════════════════
		// WEBVIEW SETUP
		// ════════════════════════════════════════════════════════════════
		android.webkit.WebSettings ws = _wvPlayer.getSettings();
		ws.setJavaScriptEnabled(true);
		ws.setDomStorageEnabled(true);
		ws.setMediaPlaybackRequiresUserGesture(false);
		
		_wvPlayer.setWebChromeClient(new android.webkit.WebChromeClient() {
			private android.view.View mCustomView;
			private android.webkit.WebChromeClient.CustomViewCallback mCustomViewCallback;
			
			@Override
			public void onShowCustomView(android.view.View view,
			android.webkit.WebChromeClient.CustomViewCallback callback) {
				if (mCustomView != null) { callback.onCustomViewHidden(); return; }
				mCustomView = view; mCustomViewCallback = callback;
				_customViewContainer.addView(view);
				_decorView.addView(_customViewContainer,
				new android.widget.FrameLayout.LayoutParams(-1, -1));
				getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
				setRequestedOrientation(0);
			}
			
			@Override
			public void onHideCustomView() {
				if (mCustomView == null) return;
				_decorView.removeView(_customViewContainer);
				_customViewContainer.removeAllViews();
				mCustomView = null; mCustomViewCallback.onCustomViewHidden();
				getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
				setRequestedOrientation(1);
			}
		});
		
		_wvPlayer.setWebViewClient(new android.webkit.WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
				return !url.contains("vidsrc.to");
			}
			
			@Override
			public android.webkit.WebResourceResponse shouldInterceptRequest(
			android.webkit.WebView view, android.webkit.WebResourceRequest request) {
				String url = request.getUrl().toString().toLowerCase();
				String[] ads = {"doubleclick.net", "googleadservices", "popads", "popcash", "exoclick"};
				for (String ad : ads) {
					if (url.contains(ad))
					return new android.webkit.WebResourceResponse("text/plain", "UTF-8",
					new java.io.ByteArrayInputStream("".getBytes()));
				}
				return super.shouldInterceptRequest(view, request);
			}
			
			@Override
			public void onPageStarted(android.webkit.WebView view, String url,
			android.graphics.Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				_pbPlayer.setVisibility(android.view.View.VISIBLE);
				_wvPlayer.setAlpha(0.5f);
			}
			
			@Override
			public void onPageFinished(android.webkit.WebView view, String url) {
				super.onPageFinished(view, url);
				_pbPlayer.setVisibility(android.view.View.GONE);
				_wvPlayer.animate().alpha(1f).setDuration(500).start();
				view.evaluateJavascript(
				"javascript:(function(){" +
				"var els=document.querySelectorAll('.ad-container,[id^=ad_],[class*=popup]');" +
				"for(var i=0;i<els.length;i++)els[i].style.display='none';" +
				"})()", null);
			}
		});
		
		// ════════════════════════════════════════════════════════════════
		// BACK BUTTON
		// ════════════════════════════════════════════════════════════════
		ui.addBounceClick(_btnBack, new Runnable() {
			@Override public void run() {
				_wvPlayer.loadUrl("about:blank");
				finish();
			}
		});
		
		// ════════════════════════════════════════════════════════════════
		// WATCHLIST + SHARE
		// ════════════════════════════════════════════════════════════════
		final android.content.SharedPreferences _prefs =
		getSharedPreferences("WATCHLIST_DATA", 0);
		
		class WatchlistManager {
			public boolean isSaved() {
				try {
					org.json.JSONArray s =
					new org.json.JSONArray(_prefs.getString("items", "[]"));
					for (int i = 0; i < s.length(); i++) {
						if (s.getJSONObject(i).getString("imdb").equals(_imdbId)) return true;
					}
				} catch (Exception ignored) {}
				return false;
			}
		}
		final WatchlistManager wlManager = new WatchlistManager();
		
		if (wlManager.isSaved()) {
			ui.applyAccent(_btnAdd, 12);
			_btnAdd.setText("\u2713 Saved");
			_btnAdd.setTextColor(0xFF000000);
		}
		
		ui.addBounceClick(_btnAdd, new Runnable() {
			@Override public void run() {
				try {
					org.json.JSONArray arr =
					new org.json.JSONArray(_prefs.getString("items", "[]"));
					if (wlManager.isSaved()) return;
					org.json.JSONObject itm = new org.json.JSONObject();
					itm.put("title",   _title);   itm.put("year",    _year);
					itm.put("imdb",    _imdbId);  itm.put("show_id", _tmdbId);
					itm.put("subject", _subject); itm.put("thumb",   _thumb);
					itm.put("desc",    _desc);
					arr.put(itm);
					_prefs.edit().putString("items", arr.toString()).apply();
					ui.applyAccent(_btnAdd, 12);
					_btnAdd.setText("\u2713 Saved");
					_btnAdd.setTextColor(0xFF000000);
					final android.view.View ref = _btnAdd;
					_btnAdd.animate().scaleX(1.15f).scaleY(1.15f).setDuration(200)
					.setInterpolator(_overshoot)
					.withEndAction(new Runnable() {
						@Override public void run() {
							ref.animate().scaleX(1f).scaleY(1f).setDuration(150).start();
						}
					}).start();
				} catch (Exception ignored) {}
			}
		});
		
		ui.addBounceClick(_btnShare, new Runnable() {
			@Override public void run() {
				String currentUrl = _wvPlayer.getUrl();
				android.content.Intent share = new android.content.Intent(
				android.content.Intent.ACTION_SEND);
				share.setType("text/plain");
				String shareText = (currentUrl != null && !currentUrl.isEmpty())
				? "Watch " + _title + ": " + currentUrl
				: "Check out " + _title + " on OhoBox!";
				share.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
				_ctx.startActivity(android.content.Intent.createChooser(share, "Share via..."));
			}
		});
		
		// ════════════════════════════════════════════════════════════════
		// TMDB: EPISODES / CAST / RECOMMENDATIONS
		// ════════════════════════════════════════════════════════════════
		final String TMDB_API_KEY = "?api_key=94bf6ec3141eb7b8e37a2f2285da9ead";
		final String TMDB_BASE    = "https://api.themoviedb.org/3";
		final android.content.SharedPreferences _hist =
		getSharedPreferences("WATCH_HISTORY", 0);
		
		// ── Episode Fetcher ──
		class EpisodeFetcher {
			public void loadEpisodesForSeason(final String tvId, final int seasonNumber) {
				_episodesRow.removeAllViews();
				// Shimmer placeholders while loading
				for (int s = 0; s < 6; s++) {
					android.view.View skel = new android.view.View(_ctx);
					android.widget.LinearLayout.LayoutParams slp =
					new android.widget.LinearLayout.LayoutParams(
					(int)(58 * _dp), (int)(52 * _dp));
					slp.setMargins(0, 0, (int)(8 * _dp), 0);
					skel.setLayoutParams(slp);
					ui.applyRoundedBackground(skel, 0xFF1E1E30, 8);
					android.animation.ObjectAnimator sp =
					android.animation.ObjectAnimator.ofFloat(skel, "alpha", 1f, 0.35f);
					sp.setDuration(700 + s * 70);
					sp.setRepeatMode(android.animation.ObjectAnimator.REVERSE);
					sp.setRepeatCount(-1); sp.start();
					_episodesRow.addView(skel);
				}
				new Thread(new Runnable() {
					@Override public void run() {
						try {
							String epUrl = TMDB_BASE + "/tv/" + tvId + "/season/"
							+ seasonNumber + TMDB_API_KEY;
							java.net.HttpURLConnection c = (java.net.HttpURLConnection)
							new java.net.URL(epUrl).openConnection();
							java.io.BufferedReader br =
							new java.io.BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
							StringBuilder sb = new StringBuilder(); String ln;
							while ((ln = br.readLine()) != null) sb.append(ln); br.close();
							final org.json.JSONArray episodes =
							new org.json.JSONObject(sb.toString()).optJSONArray("episodes");
							if (episodes == null) return;
							
							_uiH.post(new Runnable() {
								@Override public void run() {
									_episodesRow.removeAllViews();
									for (int i = 0; i < episodes.length(); i++) {
										org.json.JSONObject ep = episodes.optJSONObject(i);
										if (ep == null) continue;
										final int epNum = ep.optInt("episode_number", i + 1);
										final boolean isWatched = _hist.contains(
										"ep_" + _tmdbId + "_" + seasonNumber + "_" + epNum);
										
										final android.widget.LinearLayout btnEpWrap =
										new android.widget.LinearLayout(_ctx);
										btnEpWrap.setOrientation(android.widget.LinearLayout.VERTICAL);
										btnEpWrap.setGravity(android.view.Gravity.CENTER);
										android.widget.LinearLayout.LayoutParams lp =
										new android.widget.LinearLayout.LayoutParams(
										(int)(58 * _dp), (int)(52 * _dp));
										lp.setMargins(0, 0, (int)(8 * _dp), 0);
										btnEpWrap.setLayoutParams(lp);
										
										if (isWatched) {
											android.graphics.drawable.GradientDrawable wGd =
											new android.graphics.drawable.GradientDrawable();
											wGd.setColor(0xFF003322);
											wGd.setCornerRadius(8 * _dp);
											wGd.setStroke((int)(_dp), 0x8800E676);
											btnEpWrap.setBackground(wGd);
											btnEpWrap.setClipToOutline(true);
										} else {
											ui.applyNeonCard(btnEpWrap, 8);
										}
										
										android.widget.TextView btnEpNum = new android.widget.TextView(_ctx);
										btnEpNum.setText(String.format("%02d", epNum));
										btnEpNum.setTextColor(isWatched ? 0xFF00E676 : 0xFFFFFFFF);
										btnEpNum.setTextSize(14);
										btnEpNum.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
										btnEpNum.setGravity(android.view.Gravity.CENTER);
										btnEpWrap.addView(btnEpNum);
										
										if (isWatched) {
											android.widget.TextView tvCheck =
											new android.widget.TextView(_ctx);
											tvCheck.setText("\u2713");
											tvCheck.setTextColor(0xFF00E676);
											tvCheck.setTextSize(9);
											tvCheck.setGravity(android.view.Gravity.CENTER);
											btnEpWrap.addView(tvCheck);
										}
										
										ui.addBounceClick(btnEpWrap, new Runnable() {
											@Override public void run() {
												for (int j = 0; j < _episodesRow.getChildCount(); j++) {
													android.view.View child = _episodesRow.getChildAt(j);
													if (child != btnEpWrap)
													ui.applyNeonCard(child, 8);
												}
												ui.applyAccent(btnEpWrap, 8);
												
												_hist.edit()
												.putInt("season_"  + _tmdbId, seasonNumber)
												.putInt("episode_" + _tmdbId, epNum)
												.putBoolean("ep_" + _tmdbId + "_" + seasonNumber
												+ "_" + epNum, true)
												.apply();
												_pbPlayer.setVisibility(android.view.View.VISIBLE);
												_wvPlayer.loadUrl("https://vidsrc.to/embed/tv/"
												+ _tmdbId + "/" + seasonNumber + "/" + epNum);
											}
										});
										
										ui.popIn(btnEpWrap, i);
										_episodesRow.addView(btnEpWrap);
									}
								}
							});
						} catch (Exception ignored) {}
					}
				}).start();
			}
		}
		final EpisodeFetcher fetcher = new EpisodeFetcher();
		
		if (!isTV) {
			_episodesSection.setVisibility(android.view.View.GONE);
			_wvPlayer.loadUrl("https://vidsrc.to/embed/movie/" + _imdbId);
		} else {
			_episodesSection.setVisibility(android.view.View.VISIBLE);
			final int sSeason = _hist.getInt("season_"  + _tmdbId, 1);
			final int sEp     = _hist.getInt("episode_" + _tmdbId, 1);
			_wvPlayer.loadUrl("https://vidsrc.to/embed/tv/" + _tmdbId + "/" + sSeason + "/" + sEp);
			
			new Thread(new Runnable() {
				@Override public void run() {
					try {
						String detailsUrl = TMDB_BASE + "/tv/" + _tmdbId + TMDB_API_KEY;
						java.net.HttpURLConnection c = (java.net.HttpURLConnection)
						new java.net.URL(detailsUrl).openConnection();
						java.io.BufferedReader br =
						new java.io.BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
						StringBuilder sb = new StringBuilder(); String ln;
						while ((ln = br.readLine()) != null) sb.append(ln); br.close();
						final org.json.JSONArray seasons =
						new org.json.JSONObject(sb.toString()).optJSONArray("seasons");
						if (seasons == null) return;
						
						final java.util.ArrayList<String>  seasonList    = new java.util.ArrayList<>();
						final java.util.ArrayList<Integer> seasonNumbers = new java.util.ArrayList<>();
						int savedIndex = 0;
						for (int i = 0; i < seasons.length(); i++) {
							org.json.JSONObject s = seasons.getJSONObject(i);
							int sNum = s.optInt("season_number", 0);
							if (sNum > 0) {
								seasonList.add("Season " + String.format("%02d", sNum));
								seasonNumbers.add(sNum);
								if (sNum == sSeason) savedIndex = seasonNumbers.size() - 1;
							}
						}
						final int finalIndex = savedIndex;
						
						_uiH.post(new Runnable() {
							@Override public void run() {
								android.widget.ArrayAdapter<String> adapter =
								new android.widget.ArrayAdapter<>(_ctx,
								android.R.layout.simple_spinner_dropdown_item, seasonList);
								_spinnerSeasons.setAdapter(adapter);
								_spinnerSeasons.setSelection(finalIndex);
								ui.applyNeonCard(_spinnerSeasons, 8);
								_spinnerSeasons.setOnItemSelectedListener(
								new android.widget.AdapterView.OnItemSelectedListener() {
									@Override
									public void onItemSelected(android.widget.AdapterView<?> parent,
									android.view.View view, int pos, long id) {
										fetcher.loadEpisodesForSeason(_tmdbId, seasonNumbers.get(pos));
									}
									@Override
									public void onNothingSelected(android.widget.AdapterView<?> p) {}
								});
							}
						});
					} catch (Exception ignored) {}
				}
			}).start();
		}
		
		// ── Fetch Cast ──
		new Thread(new Runnable() {
			@Override public void run() {
				try {
					String cUrl = TMDB_BASE + "/" + (isTV ? "tv" : "movie") + "/"
					+ TARGET_DB_ID + "/credits" + TMDB_API_KEY;
					java.net.HttpURLConnection c = (java.net.HttpURLConnection)
					new java.net.URL(cUrl).openConnection();
					java.io.BufferedReader br =
					new java.io.BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
					StringBuilder sb = new StringBuilder(); String ln;
					while ((ln = br.readLine()) != null) sb.append(ln); br.close();
					final org.json.JSONArray cArr =
					new org.json.JSONObject(sb.toString()).optJSONArray("cast");
					if (cArr == null) return;
					
					_uiH.post(new Runnable() {
						@Override public void run() {
							castRow.removeAllViews();
							for (int i = 0; i < Math.min(cArr.length(), 12); i++) {
								org.json.JSONObject act = cArr.optJSONObject(i);
								if (act == null) continue;
								final String pP      = act.optString("profile_path", "");
								final String iU      = pP.isEmpty() ? ""
								: "https://image.tmdb.org/t/p/w185" + pP;
								final String actName = act.optString("name", "");
								final String actChar = act.optString("character", "");
								
								android.widget.LinearLayout aC = new android.widget.LinearLayout(_ctx);
								aC.setOrientation(android.widget.LinearLayout.VERTICAL);
								aC.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
								android.widget.LinearLayout.LayoutParams lp =
								new android.widget.LinearLayout.LayoutParams((int)(80 * _dp), -2);
								lp.setMarginEnd((int)(14 * _dp));
								aC.setLayoutParams(lp);
								
								// Avatar with neon ring
								android.widget.FrameLayout avatarFrame = new android.widget.FrameLayout(_ctx);
								android.widget.LinearLayout.LayoutParams afLp =
								new android.widget.LinearLayout.LayoutParams(
								(int)(76 * _dp), (int)(76 * _dp));
								avatarFrame.setLayoutParams(afLp);
								
								android.view.View ring = new android.view.View(_ctx);
								ring.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1, -1));
								android.graphics.drawable.GradientDrawable ringGd =
								new android.graphics.drawable.GradientDrawable();
								ringGd.setColor(0x00000000);
								ringGd.setShape(android.graphics.drawable.GradientDrawable.OVAL);
								ringGd.setStroke((int)(2 * _dp), 0x5500E676);
								ring.setBackground(ringGd);
								avatarFrame.addView(ring);
								
								final android.widget.ImageView iv = new android.widget.ImageView(_ctx);
								android.widget.FrameLayout.LayoutParams ivLp =
								new android.widget.FrameLayout.LayoutParams(
								(int)(70 * _dp), (int)(70 * _dp));
								ivLp.gravity = android.view.Gravity.CENTER;
								iv.setLayoutParams(ivLp);
								iv.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
								android.graphics.drawable.GradientDrawable ivBg =
								new android.graphics.drawable.GradientDrawable();
								ivBg.setColor(0xFF1E1E30);
								ivBg.setShape(android.graphics.drawable.GradientDrawable.OVAL);
								iv.setBackground(ivBg);
								iv.setClipToOutline(true);
								avatarFrame.addView(iv);
								aC.addView(avatarFrame);
								
								if (!iU.isEmpty()) {
									iv.setAlpha(0f);
									new Thread(new Runnable() {
										@Override public void run() {
											try {
												final android.graphics.Bitmap bmp =
												android.graphics.BitmapFactory.decodeStream(
												new java.net.URL(iU).openConnection().getInputStream());
												_uiH.post(new Runnable() {
													@Override public void run() {
														if (bmp != null) {
															iv.setImageBitmap(bmp);
															iv.animate().alpha(1f).setDuration(400).start();
														}
													}
												});
											} catch (Exception ignored) {}
										}
									}).start();
								}
								
								android.widget.TextView tn = new android.widget.TextView(_ctx);
								tn.setText(actName);
								tn.setTextColor(0xFFFFFFFF);
								tn.setTextSize(10);
								tn.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
								tn.setMaxLines(2);
								tn.setGravity(android.view.Gravity.CENTER);
								android.widget.LinearLayout.LayoutParams tnLp =
								new android.widget.LinearLayout.LayoutParams(-1, -2);
								tnLp.topMargin = (int)(6 * _dp);
								tn.setLayoutParams(tnLp);
								aC.addView(tn);
								
								android.widget.TextView tr = new android.widget.TextView(_ctx);
								tr.setText(actChar);
								tr.setTextColor(0xFF666688);
								tr.setTextSize(9);
								tr.setMaxLines(1);
								tr.setEllipsize(android.text.TextUtils.TruncateAt.END);
								tr.setGravity(android.view.Gravity.CENTER);
								android.widget.LinearLayout.LayoutParams trLp =
								new android.widget.LinearLayout.LayoutParams(-1, -2);
								trLp.topMargin = (int)(2 * _dp);
								tr.setLayoutParams(trLp);
								aC.addView(tr);
								
								ui.popIn(aC, i);
								castRow.addView(aC);
							}
						}
					});
				} catch (Exception ignored) {}
			}
		}).start();
		
		// ── Fetch Recommendations ──
		new Thread(new Runnable() {
			@Override public void run() {
				try {
					String rUrl = TMDB_BASE + "/" + (isTV ? "tv" : "movie") + "/"
					+ TARGET_DB_ID + "/recommendations" + TMDB_API_KEY;
					java.net.HttpURLConnection c = (java.net.HttpURLConnection)
					new java.net.URL(rUrl).openConnection();
					java.io.BufferedReader br =
					new java.io.BufferedReader(new java.io.InputStreamReader(c.getInputStream()));
					StringBuilder sb = new StringBuilder(); String ln;
					while ((ln = br.readLine()) != null) sb.append(ln); br.close();
					final org.json.JSONArray rArr =
					new org.json.JSONObject(sb.toString()).optJSONArray("results");
					if (rArr == null) return;
					
					_uiH.post(new Runnable() {
						@Override public void run() {
							recRow.removeAllViews();
							for (int i = 0; i < Math.min(rArr.length(), 12); i++) {
								org.json.JSONObject rec = rArr.optJSONObject(i);
								if (rec == null) continue;
								final String rId     = rec.optString("id", "");
								final String rTitle  = rec.optString("name", rec.optString("title", ""));
								final String rYear   = rec.optString("first_air_date",
								rec.optString("release_date", ""));
								final String rThumb  = rec.optString("poster_path", "").isEmpty() ? ""
								: "https://image.tmdb.org/t/p/w300" + rec.optString("poster_path", "");
								final double rVoteRaw = rec.optDouble("vote_average", 0);
								final String rVote   = rVoteRaw > 0
								? String.format("%.1f", rVoteRaw) : "";
								
								final android.widget.FrameLayout card = new android.widget.FrameLayout(_ctx);
								android.widget.LinearLayout.LayoutParams clp =
								new android.widget.LinearLayout.LayoutParams(
								(int)(110 * _dp), (int)(165 * _dp));
								clp.setMarginEnd((int)(12 * _dp));
								card.setLayoutParams(clp);
								ui.applyNeonCard(card, 10);
								card.setClipToOutline(true);
								
								final android.widget.ImageView iv = new android.widget.ImageView(_ctx);
								iv.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1, -1));
								iv.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
								card.addView(iv);
								
								if (!rThumb.isEmpty()) {
									iv.setAlpha(0f);
									new Thread(new Runnable() {
										@Override public void run() {
											try {
												final android.graphics.Bitmap bmp =
												android.graphics.BitmapFactory.decodeStream(
												new java.net.URL(rThumb).openConnection()
												.getInputStream());
												_uiH.post(new Runnable() {
													@Override public void run() {
														if (bmp != null) {
															iv.setImageBitmap(bmp);
															iv.animate().alpha(1f).setDuration(400).start();
														}
													}
												});
											} catch (Exception ignored) {}
										}
									}).start();
								}
								
								android.view.View grad = new android.view.View(_ctx);
								android.widget.FrameLayout.LayoutParams gLp =
								new android.widget.FrameLayout.LayoutParams(-1, (int)(80 * _dp));
								gLp.gravity = android.view.Gravity.BOTTOM;
								grad.setLayoutParams(gLp);
								grad.setBackground(new android.graphics.drawable.GradientDrawable(
								android.graphics.drawable.GradientDrawable.Orientation.BOTTOM_TOP,
								new int[]{0xFF0D0D1A, 0xAA0D0D1A, 0x00000000}));
								card.addView(grad);
								
								if (!rVote.isEmpty()) {
									android.widget.TextView tvR = new android.widget.TextView(_ctx);
									tvR.setText("\u2B50 " + rVote);
									tvR.setTextSize(9);
									tvR.setTextColor(0xFFFFD700);
									tvR.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
									tvR.setPadding((int)(5 * _dp), (int)(3 * _dp),
									(int)(5 * _dp), (int)(3 * _dp));
									android.widget.FrameLayout.LayoutParams rLp =
									new android.widget.FrameLayout.LayoutParams(-2, -2);
									rLp.gravity = android.view.Gravity.TOP | android.view.Gravity.END;
									rLp.setMargins(0, (int)(6 * _dp), (int)(6 * _dp), 0);
									tvR.setLayoutParams(rLp);
									ui.applyRoundedBackground(tvR, 0xDD000000, 6);
									card.addView(tvR);
								}
								
								android.widget.TextView tvT = new android.widget.TextView(_ctx);
								tvT.setText(rTitle);
								tvT.setTextColor(0xFFFFFFFF);
								tvT.setTextSize(10);
								tvT.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
								tvT.setMaxLines(2);
								tvT.setEllipsize(android.text.TextUtils.TruncateAt.END);
								tvT.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
								android.widget.FrameLayout.LayoutParams tLp =
								new android.widget.FrameLayout.LayoutParams(-1, -2);
								tLp.gravity =
								android.view.Gravity.BOTTOM | android.view.Gravity.CENTER_HORIZONTAL;
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
											pressOverlay.animate().alpha(1f).setDuration(80).start();
											v.animate().scaleX(0.94f).scaleY(0.94f).setDuration(110).start();
											ui.haptic();
										} else if (ev.getAction() == android.view.MotionEvent.ACTION_UP) {
											pressOverlay.animate().alpha(0f).setDuration(250).start();
											final android.view.View vRef = v;
											v.animate().scaleX(1.04f).scaleY(1.04f).setDuration(160)
											.setInterpolator(_overshoot)
											.withEndAction(new Runnable() {
												@Override public void run() {
													vRef.animate().scaleX(1f).scaleY(1f)
													.setDuration(120).start();
												}
											}).start();
											android.content.Intent intent =
											new android.content.Intent(_ctx, DetailActivity.class);
											intent.putExtra("title",   rTitle);
											intent.putExtra("year",    rYear);
											intent.putExtra("thumb",   rThumb);
											intent.putExtra("imdb",    rId);
											intent.putExtra("show_id", isTV ? rId : "");
											intent.putExtra("subject", isTV ? "TV Show" : "Movie");
											_ctx.startActivity(intent);
											finish();
										} else if (ev.getAction() == android.view.MotionEvent.ACTION_CANCEL) {
											pressOverlay.animate().alpha(0f).setDuration(200).start();
											v.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
										}
										return true;
									}
								});
								
								ui.popIn(card, i);
								recRow.addView(card);
							}
						}
					});
				} catch (Exception ignored) {}
			}
		}).start();
		
	}
	
}