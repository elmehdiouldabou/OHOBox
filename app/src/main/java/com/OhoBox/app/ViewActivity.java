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

public class ViewActivity extends AppCompatActivity {
	
	private ViewBinding binding;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = ViewBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
	}
	
	private void initializeLogic() {
		// ════════════════════════════════════════════════════════════════
		// ViewActivity onCreate — Silent Manga & Archive Reader
		// ════════════════════════════════════════════════════════════════
		
		final android.content.Context _ctx = ViewActivity.this;
		final float _dp = getResources().getDisplayMetrics().density;
		
		// ── Full screen & Immersive UI ──
		getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().getDecorView().setSystemUiVisibility(
		android.view.View.SYSTEM_UI_FLAG_FULLSCREEN |
		android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
		android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		// Sleeker dark background
		getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(0xFF090910)); 
		
		final String _bookUrl = getIntent().getStringExtra("book_url");
		final String _bookTitle = getIntent().getStringExtra("book_title");
		
		// ── Root ──
		final android.widget.FrameLayout root = new android.widget.FrameLayout(_ctx);
		root.setBackgroundColor(0xFF090910);
		setContentView(root);
		
		// ── WebView ──
		final android.webkit.WebView wv = new android.webkit.WebView(_ctx);
		wv.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1, -1));
		wv.setBackgroundColor(0xFF090910);
		
		android.webkit.WebSettings s = wv.getSettings();
		s.setJavaScriptEnabled(true);
		s.setDomStorageEnabled(true);
		s.setLoadWithOverviewMode(true);
		s.setUseWideViewPort(true);
		s.setBuiltInZoomControls(true);
		s.setDisplayZoomControls(false);
		s.setSupportZoom(true);
		s.setUserAgentString("Mozilla/5.0 (Linux; Android 11) AppleWebKit/537.36 Chrome/112.0.0.0 Mobile Safari/537.36");
		root.addView(wv);
		
		// ── Modern Loading Overlay ──
		final android.widget.FrameLayout load = new android.widget.FrameLayout(_ctx);
		load.setBackgroundColor(0xFA090910); // Translucent dark background
		load.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1, -1));
		
		final android.widget.LinearLayout loadContent = new android.widget.LinearLayout(_ctx);
		loadContent.setOrientation(android.widget.LinearLayout.VERTICAL);
		loadContent.setGravity(android.view.Gravity.CENTER);
		android.widget.FrameLayout.LayoutParams lp = new android.widget.FrameLayout.LayoutParams(-2, -2);
		lp.gravity = android.view.Gravity.CENTER;
		loadContent.setLayoutParams(lp);
		
		// Animated Loading Text
		final android.widget.TextView loadingText = new android.widget.TextView(_ctx);
		loadingText.setText("READING");
		loadingText.setTextColor(0xFFE91E8C);
		loadingText.setTextSize(18);
		loadingText.setTypeface(android.graphics.Typeface.create("sans-serif-black", android.graphics.Typeface.NORMAL));
		if (android.os.Build.VERSION.SDK_INT >= 21) loadingText.setLetterSpacing(0.3f);
		loadContent.addView(loadingText);
		
		// Custom Slim Progress Bar
		android.widget.ProgressBar spin = new android.widget.ProgressBar(_ctx, null, android.R.attr.progressBarStyleHorizontal);
		spin.setIndeterminate(true);
		android.widget.LinearLayout.LayoutParams sp = new android.widget.LinearLayout.LayoutParams((int)(100*_dp), (int)(4*_dp));
		sp.topMargin = (int)(12*_dp);
		spin.setLayoutParams(sp);
		spin.setIndeterminateTintList(android.content.res.ColorStateList.valueOf(0xFFE91E8C));
		loadContent.addView(spin);
		
		load.addView(loadContent);
		root.addView(load);
		
		// Loading Animation Pulse
		final android.animation.ObjectAnimator pulse = android.animation.ObjectAnimator.ofFloat(loadingText, "alpha", 0.4f, 1f);
		pulse.setDuration(700);
		pulse.setRepeatMode(android.animation.ValueAnimator.REVERSE);
		pulse.setRepeatCount(android.animation.ValueAnimator.INFINITE);
		pulse.start();
		
		// ── Floating Back Button ──
		final android.widget.TextView back = new android.widget.TextView(_ctx);
		back.setText("❮"); // Sleek Unicode arrow
		back.setTextSize(22);
		back.setTextColor(0xFFFFFFFF);
		back.setGravity(android.view.Gravity.CENTER);
		back.setAlpha(0f);
		if (android.os.Build.VERSION.SDK_INT >= 21) back.setElevation(8 * _dp);
		
		android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();
		bg.setColor(0xEE1E1E2C);
		bg.setShape(android.graphics.drawable.GradientDrawable.OVAL);
		bg.setStroke((int)(1*_dp), 0x44FFFFFF);
		back.setBackground(bg);
		
		// Rounded Floating Action Button properties
		android.widget.FrameLayout.LayoutParams bp = new android.widget.FrameLayout.LayoutParams((int)(56*_dp), (int)(56*_dp));
		bp.gravity = android.view.Gravity.BOTTOM | android.view.Gravity.START;
		bp.setMargins((int)(20*_dp), 0, 0, (int)(32*_dp));
		back.setLayoutParams(bp);
		root.addView(back);
		
		// Auto-hide animation
		final Runnable hideBack = new Runnable(){
			public void run(){
				back.animate().alpha(0f).translationY(15*_dp).setDuration(300)
				.setInterpolator(new android.view.animation.AccelerateInterpolator()).start();
			}
		};
		
		// Initial show then auto-hide
		back.postDelayed(new Runnable(){
			public void run(){
				back.setTranslationY(15*_dp);
				back.animate().alpha(0.85f).translationY(0f).setDuration(400)
				.setInterpolator(new android.view.animation.DecelerateInterpolator()).start();
			}
		}, 500);
		back.postDelayed(hideBack, 3500);
		
		// ── WebView Client ──
		wv.setWebViewClient(new android.webkit.WebViewClient(){
			
			@Override
			public void onPageFinished(android.webkit.WebView view, String url){
				// Inject global CSS via template literals to force Full Screen & hide UI
				String js = "var css = `" +
				// MangaFire UI blocks
				"header, nav, footer, .logo, .component, #nav-menu, #user, .gotop, " +
				".abs-footer, .wrap, #ctrl-menu, .sub-panel, .modal, #report, #sign, #request, " +
				".dropdown, .favourite, .viewing, .number-toggler, .page-toggler, .tooltipz, " +
				".progress-bar, #progress-bar, [class*='loading'], .loading, #loading, " +
				".spinner, .pace, [class*='share'], [class*='social'], #disqus, " +
				// Internet Archive Topbars & Buttons
				"#app-topnav, .ia-topnav, #includes-desktop-ia-topnav, .ia-book-actions, " +
				".BRtoolbar, .BRfooter, #IABookReaderMessageWrapper, .hamburger-menu, .navbar, " +
				"#topBanners { display: none !important; } " +
				// Strip margins
				"body { margin: 0 !important; padding: 0 !important; overflow-x: hidden !important; background: #090910 !important; } " +
				// Force Full Document Canvas for Internet Archive
				"#theatre-ia, #BRcontainer, .bookreader { position: fixed !important; top: 0 !important; left: 0 !important; width: 100vw !important; height: 100vh !important; z-index: 9999 !important; padding: 0 !important; margin: 0 !important; } " +
				".BRpage { max-height: 100vh !important; }`;" +
				// Append Style to Head
				"var style = document.createElement('style');" +
				"style.type = 'text/css';" +
				"style.appendChild(document.createTextNode(css));" +
				"document.head.appendChild(style);" +
				// Title cleanup
				"if(document.title.toLowerCase().indexOf('mangafire')>=0 || document.title.toLowerCase().indexOf('internet archive')>=0){" +
				"document.title='" + (_bookTitle!=null ? _bookTitle.replace("'","\\'") : "Reader") + "';" +
				"}";
				
				// evaluateJavascript is fully supported in SDK 19+ 
				view.evaluateJavascript(js, null);
				
				// Smoothly hide loading view via scaling and fading
				load.animate().alpha(0f).scaleX(1.1f).scaleY(1.1f).setDuration(400)
				.setInterpolator(new android.view.animation.AccelerateInterpolator())
				.withEndAction(new Runnable(){
					public void run(){
						load.setVisibility(android.view.View.GONE);
						pulse.cancel(); // Stop animation 
					}
				}).start();
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url){
				view.loadUrl(url);
				return true;
			}
		});
		
		wv.setWebChromeClient(new android.webkit.WebChromeClient(){
			@Override
			public void onProgressChanged(android.webkit.WebView view, int p){
				if(p > 85 && load.getVisibility() == android.view.View.VISIBLE) {
					load.animate().alpha(0f).scaleX(1.05f).scaleY(1.05f).setDuration(300)
					.withEndAction(new Runnable(){
						public void run(){
							load.setVisibility(android.view.View.GONE);
							pulse.cancel();
						}
					}).start();
				}
			}
		});
		
		// ── Load ──
		String startUrl = (_bookUrl != null && !_bookUrl.isEmpty()) ? _bookUrl : "https://mangafire.to/updated";
		wv.loadUrl(startUrl);
		
		// ── Back Handler ──
		back.setOnClickListener(new android.view.View.OnClickListener(){
			public void onClick(android.view.View v){
				if(wv.canGoBack()) wv.goBack(); else finish();
			}
		});
		
		// Tap to show/hide back button
		wv.setOnTouchListener(new android.view.View.OnTouchListener(){
			public boolean onTouch(android.view.View v, android.view.MotionEvent e){
				if(e.getAction() == android.view.MotionEvent.ACTION_UP){
					back.removeCallbacks(hideBack);
					// Slide up and fade in if hidden
					if (back.getAlpha() < 0.1f) {
						back.setTranslationY(15*_dp);
						back.animate().alpha(0.85f).translationY(0f).setDuration(300)
						.setInterpolator(new android.view.animation.DecelerateInterpolator()).start();
					}
					// Retrigger auto-hide
					back.postDelayed(hideBack, 3000);
				}
				return false;
			}
		});
	}
	
}