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

public class ProfileActivity extends AppCompatActivity {
	
	private ProfileBinding binding;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = ProfileBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
	}
	
	private void initializeLogic() {
		// =========================================================================
		// ProfileActivity onCreate - PREMIUM ANIMATED UI v2 (FIXED)
		// =========================================================================
		
		final android.content.Context _ctx = ProfileActivity.this;
		final float _dp = getResources().getDisplayMetrics().density;
		
		// =========================================================================
		// UIAnimationHelper — Extended Premium Edition
		// =========================================================================
		class UIAnimationHelper {
			
			// ── GRADIENT BACKGROUND ──
			public void setGradientBg(android.view.View v, int colorStart, int colorEnd, float radiusDp) {
				android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable(
				android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM,
				new int[]{colorStart, colorEnd}
				);
				gd.setCornerRadius(radiusDp * _dp);
				v.setBackground(gd);
				v.setClipToOutline(true);
				v.setElevation(6 * _dp);
			}
			
			// ── SOLID ROUNDED BG ──
			public void setRoundedBg(android.view.View v, int color, float radiusDp) {
				android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
				gd.setColor(color);
				gd.setCornerRadius(radiusDp * _dp);
				v.setBackground(gd);
				v.setClipToOutline(true);
				v.setElevation(4 * _dp);
			}
			
			// ── STROKE BORDER + BG ──
			public void setStrokeBg(android.view.View v, int fillColor, int strokeColor, float radiusDp, int strokeWidthDp) {
				android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
				gd.setColor(fillColor);
				gd.setCornerRadius(radiusDp * _dp);
				gd.setStroke((int)(strokeWidthDp * _dp), strokeColor);
				v.setBackground(gd);
				v.setClipToOutline(true);
				v.setElevation(4 * _dp);
			}
			
			// ── PREMIUM BOUNCE TOUCH ──
			public void addBounceClick(final android.view.View view, final Runnable action) {
				view.setOnTouchListener(new android.view.View.OnTouchListener() {
					@Override
					public boolean onTouch(android.view.View v, android.view.MotionEvent event) {
						switch (event.getAction()) {
							case android.view.MotionEvent.ACTION_DOWN:
							v.animate()
							.scaleX(0.93f).scaleY(0.93f)
							.alpha(0.75f)
							.translationZ(2 * _dp)
							.setDuration(110)
							.setInterpolator(new android.view.animation.DecelerateInterpolator())
							.start();
							v.performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY);
							break;
							case android.view.MotionEvent.ACTION_UP:
							case android.view.MotionEvent.ACTION_CANCEL:
							v.animate()
							.scaleX(1f).scaleY(1f)
							.alpha(1f)
							.translationZ(6 * _dp)
							.setDuration(320)
							.setInterpolator(new android.view.animation.OvershootInterpolator(2.2f))
							.start();
							if (event.getAction() == android.view.MotionEvent.ACTION_UP && action != null) {
								view.postDelayed(action, 120);
							}
							break;
						}
						return true;
					}
				});
			}
			
			// ── STAGGERED SLIDE-UP ENTRANCE ──
			public void staggerEntrance(android.view.View[] views, int baseDelayMs) {
				for (int i = 0; i < views.length; i++) {
					final android.view.View v = views[i];
					v.setAlpha(0f);
					v.setTranslationY(60f);
					v.setScaleX(0.95f);
					v.setScaleY(0.95f);
					v.animate()
					.alpha(1f)
					.translationY(0f)
					.scaleX(1f).scaleY(1f)
					.setStartDelay(baseDelayMs + i * 70L)
					.setDuration(480)
					.setInterpolator(new android.view.animation.OvershootInterpolator(1.1f))
					.start();
				}
			}
			
			// ── INFINITE PULSE (FIX 1: pulse declared final) ──
			public void startPulse(final android.view.View view) {
				final android.animation.ObjectAnimator scaleX = android.animation.ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.045f, 1f);
				final android.animation.ObjectAnimator scaleY = android.animation.ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.045f, 1f);
				final android.animation.AnimatorSet pulse = new android.animation.AnimatorSet(); // ✅ FIXED: final
				pulse.playTogether(scaleX, scaleY);
				pulse.setDuration(1400);
				pulse.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());
				pulse.addListener(new android.animation.AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(android.animation.Animator animation) {
						if (view.isAttachedToWindow()) pulse.start(); // ✅ FIXED
					}
				});
				pulse.setStartDelay(1200);
				pulse.start();
			}
			
			// ── GLOW RING PULSE ──
			public void startGlowPulse(final android.view.View view, final int glowColor, final float radiusDp) {
				final android.animation.ValueAnimator anim = android.animation.ValueAnimator.ofFloat(0.25f, 0.85f, 0.25f);
				anim.setDuration(2000);
				anim.setRepeatCount(android.animation.ValueAnimator.INFINITE);
				anim.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());
				anim.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(android.animation.ValueAnimator va) {
						float alpha = (float) va.getAnimatedValue();
						int a = (int)(alpha * 255);
						android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
						gd.setColor(0xFF1A1A2E);
						gd.setCornerRadius(radiusDp * _dp);
						int strokeColor = (a << 24) | (glowColor & 0x00FFFFFF);
						gd.setStroke((int)(2 * _dp), strokeColor);
						view.setBackground(gd);
					}
				});
				anim.start();
			}
			
			// ── ROTATING ICON SPIN ──
			public void spinOnce(android.view.View v) {
				v.animate()
				.rotationBy(360f)
				.setDuration(600)
				.setInterpolator(new android.view.animation.DecelerateInterpolator())
				.start();
			}
			
			// ── OPEN URL SAFELY ──
			public void openUrl(String url) {
				try {
					android.content.Intent i = new android.content.Intent(
					android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url));
					_ctx.startActivity(i);
				} catch (Exception e) {
					android.widget.Toast.makeText(_ctx, "Could not open link.", android.widget.Toast.LENGTH_SHORT).show();
				}
			}
			
			// ── SLIDE-OUT FINISH (FIX 2 & 3: act captured as final _act) ──
			public void finishWithSlide(android.app.Activity act) {
				final android.app.Activity _act = act; // ✅ FIXED: final alias
				android.view.ViewGroup root = _act.findViewById(android.R.id.content);
				root.animate()
				.alpha(0f)
				.translationY(40f)
				.scaleX(0.97f).scaleY(0.97f)
				.setDuration(260)
				.setInterpolator(new android.view.animation.AccelerateInterpolator())
				.withEndAction(new Runnable() {
					@Override public void run() {
						_act.finish();                        // ✅ FIXED
						_act.overridePendingTransition(0, 0); // ✅ FIXED
					}
				}).start();
			}
		}
		
		final UIAnimationHelper animHelper = new UIAnimationHelper();
		
		// =========================================================================
		// ── 1. ROOT ENTRANCE: Zoom + Fade In ──
		// =========================================================================
		android.view.ViewGroup rootView = findViewById(android.R.id.content);
		rootView.setAlpha(0f);
		rootView.setScaleX(0.97f);
		rootView.setScaleY(0.97f);
		rootView.setTranslationY(30f);
		rootView.animate()
		.alpha(1f)
		.scaleX(1f).scaleY(1f)
		.translationY(0f)
		.setDuration(500)
		.setInterpolator(new android.view.animation.DecelerateInterpolator(1.5f))
		.start();
		
		// =========================================================================
		// ── 2. FIND VIEWS ──
		// =========================================================================
		final android.widget.TextView btnBack       = findViewById(R.id.btn_back);
		final android.widget.TextView btnPatreon    = findViewById(R.id.btn_patreon);
		final android.widget.TextView btnWatchlist  = findViewById(R.id.btn_watchlist);
		final android.widget.TextView btnSettings   = findViewById(R.id.btn_settings);
		final android.widget.TextView btnFb         = findViewById(R.id.btn_fb);
		final android.widget.TextView btnInsta      = findViewById(R.id.btn_insta);
		final android.widget.TextView btnWtsp       = findViewById(R.id.btn_wtsp);
		final android.widget.TextView btnGithub     = findViewById(R.id.btn_github);
		final android.widget.TextView btnPrivacy    = findViewById(R.id.btn_privacy);
		final android.widget.TextView btnOpenSource = findViewById(R.id.btn_opensource);
		
		// =========================================================================
		// ── 3. APPLY PREMIUM STYLED BACKGROUNDS ──
		// =========================================================================
		animHelper.setGradientBg(btnPatreon,    0xFFFF424D, 0xFFCC1A26, 14);
		animHelper.setStrokeBg(btnWatchlist,    0xFF1E1E38, 0xFF5C6BC0, 14, 1);
		animHelper.setStrokeBg(btnSettings,     0xFF1E1E38, 0xFF26C6DA, 14, 1);
		animHelper.setStrokeBg(btnFb,           0xFF1A1A2E, 0xFF1877F2, 14, 1);
		animHelper.setStrokeBg(btnInsta,        0xFF1A1A2E, 0xFFE1306C, 14, 1);
		animHelper.setStrokeBg(btnWtsp,         0xFF1A1A2E, 0xFF25D366, 14, 1);
		animHelper.setStrokeBg(btnGithub,       0xFF1A1A2E, 0xFFCCCCCC, 14, 1);
		animHelper.setStrokeBg(btnPrivacy,      0xFF1A1A2E, 0xFF546E7A, 14, 1);
		animHelper.setStrokeBg(btnOpenSource,   0xFF1A1A2E, 0xFF546E7A, 14, 1);
		
		// =========================================================================
		// ── 4. STAGGERED ENTRANCE FOR ALL BUTTONS ──
		// =========================================================================
		android.view.View[] allButtons = new android.view.View[]{
			btnPatreon, btnWatchlist, btnSettings,
			btnFb, btnInsta, btnWtsp, btnGithub,
			btnPrivacy, btnOpenSource
		};
		animHelper.staggerEntrance(allButtons, 200);
		
		// Back button slides in from left independently
		btnBack.setAlpha(0f);
		btnBack.setTranslationX(-30f);
		btnBack.animate()
		.alpha(1f).translationX(0f)
		.setDuration(350).setStartDelay(100)
		.setInterpolator(new android.view.animation.OvershootInterpolator(2f))
		.start();
		
		// =========================================================================
		// ── 5. SPECIAL EFFECTS ──
		// =========================================================================
		animHelper.startPulse(btnPatreon);
		animHelper.startGlowPulse(btnWatchlist, 0xFF5C6BC0, 14);
		animHelper.startGlowPulse(btnSettings,  0xFF26C6DA, 14);
		
		// =========================================================================
		// ── 6. CLICK ACTIONS ──
		// =========================================================================
		
		// Back
		animHelper.addBounceClick(btnBack, new Runnable() {
			@Override public void run() {
				animHelper.finishWithSlide((android.app.Activity) _ctx);
			}
		});
		
		// Patreon (FIX 4 & 5: btnPatreon captured as final _btnPatreon)
		final android.widget.TextView _btnPatreon = btnPatreon; // ✅ FIXED: final alias
		animHelper.addBounceClick(_btnPatreon, new Runnable() {
			@Override public void run() {
				animHelper.spinOnce(_btnPatreon);        // ✅ FIXED
				_btnPatreon.postDelayed(new Runnable() { // ✅ FIXED
					@Override public void run() {
						animHelper.openUrl("https://www.patreon.com/c/Mahdinsfwart"); // REPLACE WITH YOUR LINK
					}
				}, 300);
			}
		});
		
		// Watchlist
		animHelper.addBounceClick(btnWatchlist, new Runnable() {
			@Override public void run() {
				android.content.Intent intent = new android.content.Intent(_ctx, WatchListActivity.class);
				_ctx.startActivity(intent);
			}
		});
		
		// Settings
		animHelper.addBounceClick(btnSettings, new Runnable() {
			@Override public void run() {
				android.widget.Toast.makeText(_ctx, "⚙️ Settings coming soon...", android.widget.Toast.LENGTH_SHORT).show();
			}
		});
		
		// Socials
		animHelper.addBounceClick(btnFb, new Runnable() {
			@Override public void run() { animHelper.openUrl("https://www.facebook.com/kayeiden"); }
		});
		animHelper.addBounceClick(btnInsta, new Runnable() {
			@Override public void run() { animHelper.openUrl("https://www.instagram.com/OHOBoxApp"); }
		});
		animHelper.addBounceClick(btnWtsp, new Runnable() {
			@Override public void run() { animHelper.openUrl("https://wa.me/00703558031"); } // REPLACE
		});
		animHelper.addBounceClick(btnGithub, new Runnable() {
			@Override public void run() { animHelper.openUrl("https://github.com/elmehdiouldabou/"); }
		});
		
		// About
		animHelper.addBounceClick(btnPrivacy, new Runnable() {
			@Override public void run() { animHelper.openUrl("https://policies.google.com/privacy"); }
		});
		animHelper.addBounceClick(btnOpenSource, new Runnable() {
			@Override public void run() {
				android.widget.Toast.makeText(_ctx, "🎬 Movie data provided by public APIs.", android.widget.Toast.LENGTH_LONG).show();
			}
		});
	}
	
}