package com.flipgive.cloudjava;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize WebView
        webView = findViewById(R.id.webview);

        // Enable JavaScript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true); // @important: Allows the use of the localStorage API.

        // Register the JavaScript interface
        WebViewMessages webViewAppInterface = new WebViewMessages(this, webView);
        webView.addJavascriptInterface(webViewAppInterface, "flipgiveAppInterface");

        // Set WebViewClient
        // @important: Allows reloading the page by injecting JavaScript.
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // Load the URL within the WebView
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

        // Prompt the user for input and load the webpage
        promptUserForValues(new PromptCallback() {
            @Override
            public void onValuesEntered(String[] values) {
                if (values != null) {
                    String token = values[0];
                    String baseUrl = values[1];
                    String url = baseUrl + "?token=" + token;
                    webView.loadUrl(url);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
//        else {
//            super.onBackPressed();
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void promptUserForValues(final PromptCallback callback) {
        // Create a LayoutInflater to inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_prompt, null);

        // Find the EditText fields in the custom layout
        final EditText tokenInput = dialogView.findViewById(R.id.token_input);
        final EditText baseUrlInput = dialogView.findViewById(R.id.base_url_input);

        // Create the AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Details")
                .setView(dialogView)
                .setPositiveButton("Load Webpage", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the values entered by the user
                        String token = tokenInput.getText().toString();
                        String baseUrl = baseUrlInput.getText().toString();

                        // Return the values as an array
                        String[] values = new String[]{token, baseUrl};

                        // Return the entered values
                        callback.onValuesEntered(values);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        // Return null when the dialog is canceled
                        callback.onValuesEntered(null);
                    }
                });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    interface PromptCallback {
        void onValuesEntered(String[] values);
    }

}
