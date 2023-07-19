package com.flipgive.cloudjava;

import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;
import android.content.Context;

import java.net.MalformedURLException;
import java.net.URL;


public class WebViewMessages {
    private Context context;
    private WebView webView;
    private UserInputDialog userInputDialog;

    public WebViewMessages(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;
        this.userInputDialog = new UserInputDialog(context, new UserInputDialog.PromptCallback() {
            @Override
            public void onValuesEntered(String[] values) {
                if (values != null && values[0] != null) {
                    String token = values[0];

                    // Construct the JavaScript code with newToken
                    final String javascriptCode = "(() => { if (window && window.updateToken) { window.updateToken(\""+token+"\"); } })()";

                    webView.post(new Runnable() {
                        @Override
                        public void run() {
                            // Inject the JavaScript code
                            webView.evaluateJavascript(javascriptCode, null);
                        }
                    });
                }
            }
        });
    }

    @JavascriptInterface
    public void postMessage(String message) {
        // Handle the received message
        Toast.makeText(webView.getContext(), "Received message: " + message, Toast.LENGTH_SHORT).show();

        // Check if the message body is "USER_DATA_REQUIRED"
        if (message.equals("USER_DATA_REQUIRED")) {
            // Prompt the user for input and add newToken
            userInputDialog.promptUserForValues();
        }

        if (message.contains("OPEN_IN_BROWSER::")) {
            String urlPayload = message.replace("OPEN_IN_BROWSER::", "");
            try {
                URL url = new URL(urlPayload);
                if (url != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

}
