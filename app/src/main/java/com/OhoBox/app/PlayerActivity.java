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

public class PlayerActivity extends AppCompatActivity {
	
	private PlayerBinding binding;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = PlayerBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		binding.wv.setWebViewClient(new WebViewClient() {
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
		// PlayerActivity onCreate (With AdBlock & Doodstream Isolation)
		// ════════════════════════════════════════════════════════════════
		
		// Force fullscreen and landscape
		getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		// Hide navigation bar for true immersive video experience
		getWindow().getDecorView().setSystemUiVisibility(
		android.view.View.SYSTEM_UI_FLAG_FULLSCREEN |
		android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
		android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		
		setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		// Assuming you call setContentView(R.layout.activity_player) here
		
		final android.webkit.WebView _wv = (android.webkit.WebView) findViewById(R.id.wv);
		final android.widget.ProgressBar _pb = (android.widget.ProgressBar) findViewById(R.id.pb);
		final android.widget.TextView _tvTitle = (android.widget.TextView) findViewById(R.id.tv_title);
		final android.widget.TextView _btnBack = (android.widget.TextView) findViewById(R.id.btn_back);
		final android.widget.LinearLayout _topbar = (android.widget.LinearLayout) findViewById(R.id.topbar);
		
		// Intent data
		final String _imdbId = getIntent().getStringExtra("imdb");
		final String _season = getIntent().getStringExtra("season");
		final String _episode = getIntent().getStringExtra("episode");
		final String _movieTitle = getIntent().getStringExtra("title");
		final String _videoUrl = getIntent().getStringExtra("video_url"); // NEW: For direct URLs
		
		if (_movieTitle != null && !_movieTitle.isEmpty()) {
			_tvTitle.setText(_movieTitle);
		} else {
			_tvTitle.setText("Now Playing");
		}
		
		// ── Configure WebView for HTML5 Video ──
		android.webkit.WebSettings ws = _wv.getSettings();
		ws.setJavaScriptEnabled(true);
		ws.setDomStorageEnabled(true);
		ws.setMediaPlaybackRequiresUserGesture(false); // Allows video to play without user clicking
		
		// Disable opening multiple windows (Crucial: Prevents Doodstream popup ads from opening new tabs)
		ws.setSupportMultipleWindows(false);
		ws.setJavaScriptCanOpenWindowsAutomatically(false);
		
		_wv.setBackgroundColor(0xFF000000); // Black background
		
		// Required for fullscreen video support
		_wv.setWebChromeClient(new android.webkit.WebChromeClient());
		
		// ── CUSTOM AD BLOCKING & ISOLATION WEBVIEW CLIENT ──
		_wv.setWebViewClient(new android.webkit.WebViewClient() {
			
			// 1. Block Navigation to Popup Ads
			@Override
			public boolean shouldOverrideUrlLoading(android.webkit.WebView view, android.webkit.WebResourceRequest request) {
				return handleUrl(request.getUrl().toString());
			}
			
			@SuppressWarnings("deprecation")
			@Override
			public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
				return handleUrl(url);
			}
			
			private boolean handleUrl(String url) {
				String lowerUrl = url.toLowerCase();
				// Whitelist domains we actually want to load. Block EVERYTHING else to stop popups.
				if (lowerUrl.contains("vidsrc") || lowerUrl.contains("downloads-anymovies") || lowerUrl.contains("dood")) {
					return false; // Allow the page to load
				}
				return true; // Block the popup ad
			}
			
			// 2. Block Background Ad Scripts & Trackers
			@Override
			public android.webkit.WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, android.webkit.WebResourceRequest request) {
				String url = request.getUrl().toString().toLowerCase();
				
				// List of common ad networks and tracker domains
				String[] adDomains = {
					"doubleclick.net", "googleadservices.com", "googlesyndication.com",
					"adsystem", "adserver", "popads.net", "popcash.net", 
					"propellerads", "onclickads", "exoclick", "tracking", "analytics"
				};
				
				for (String adDomain : adDomains) {
					if (url.contains(adDomain)) {
						// Return empty response to kill the ad script
						java.io.InputStream emptyInput = new java.io.ByteArrayInputStream("".getBytes());
						return new android.webkit.WebResourceResponse("text/plain", "UTF-8", emptyInput);
					}
				}
				return super.shouldInterceptRequest(view, request);
			}
			
			// 3. Inject JavaScript when page loads
			@Override
			public void onPageFinished(android.webkit.WebView view, String url) {
				super.onPageFinished(view, url);
				_pb.setVisibility(android.view.View.GONE);
				
				// Hide standard ads for vidsrc
				view.evaluateJavascript(
				"javascript:(function() { " +
				"var elements = document.querySelectorAll('.ad-container, .adsbygoogle, [id^=ad_], [class^=ad_]');" +
				"for(var i=0; i<elements.length; i++) { elements[i].style.display = 'none'; }" +
				"})()", null);
				
				// ── DOODSTREAM EXACT IFRAME ISOLATION ──
				if (url != null && url.contains("downloads-anymovies.co")) {
					String isolateJs = "javascript:(function() {" +
					"var isolate = function() {" +
					"var v = document.getElementById('playerFrame');" +
					"if(v && v.parentElement !== document.body) {" +
					"document.body.innerHTML = '';" + // Delete website
					"document.body.appendChild(v);" + // Inject only the Doodstream playerFrame
					"document.body.style.background = '#000';" + 
					"document.body.style.margin = '0';" +
					"document.body.style.padding = '0';" +
					"document.body.style.overflow = 'hidden';" +
					"v.style.position = 'fixed';" +
					"v.style.top = '0';" +
					"v.style.left = '0';" +
					"v.style.width = '100vw';" +
					"v.style.height = '100vh';" +
					"v.style.zIndex = '999999';" +
					"v.style.border = 'none';" +
					"}" +
					"};" +
					"isolate();" + 
					"setTimeout(isolate, 1000);" + 
					"setTimeout(isolate, 2500);" +
					"})();";
					view.evaluateJavascript(isolateJs, null);
				}
			}
		});
		
		// ── Construct URL dynamically ──
		String startUrl = "";
		if (_videoUrl != null && !_videoUrl.isEmpty()) {
			// It's an Adult Movie from downloads-anymovies
			startUrl = _videoUrl;
		} else if (_imdbId != null && !_imdbId.isEmpty()) {
			// It's a vidsrc movie/tv show
			if (_season != null && _episode != null && !_season.isEmpty() && !_episode.isEmpty()) {
				startUrl = "https://vidsrc.to/embed/tv/" + _imdbId + "/" + _season + "/" + _episode;
			} else {
				startUrl = "https://vidsrc.to/embed/movie/" + _imdbId;
			}
		} else {
			startUrl = "about:blank";
		}
		
		// Load the player
		_wv.loadUrl(startUrl);
		
		// ── Back button logic ──
		_btnBack.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(android.view.View v) {
				_wv.loadUrl("about:blank"); // Force stop any playing audio immediately
				finish();
			}
		});
		
		// ── Auto-hide Topbar on touch (Optional logic to make it full screen) ──
		_wv.setOnTouchListener(new android.view.View.OnTouchListener() {
			private Runnable hideTopbar = new Runnable() {
				@Override public void run() {
					_topbar.animate().translationY(-_topbar.getHeight()).alpha(0f).setDuration(300).start();
				}
			};
			@Override
			public boolean onTouch(android.view.View v, android.view.MotionEvent event) {
				if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
					_topbar.removeCallbacks(hideTopbar);
					if (_topbar.getAlpha() < 0.5f) {
						// Show topbar
						_topbar.animate().translationY(0).alpha(1f).setDuration(300).start();
						_topbar.postDelayed(hideTopbar, 3500);
					} else {
						// Hide immediately
						hideTopbar.run();
					}
				}
				return false;
			}
		});
		
		// Auto-hide topbar initially after 3.5 seconds
		_topbar.postDelayed(new Runnable() {
			@Override public void run() {
				_topbar.animate().translationY(-_topbar.getHeight()).alpha(0f).setDuration(300).start();
			}
		}, 3500);
	}
	
}