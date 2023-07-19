# demo-cloud-app-java


## Android Token App
This is a simple app that demonstrates how to expose a token and pass it through a WebView. The app allows users to input a token and displays a WebView that loads a webpage using the provided token for authentication.

### Prerequisites
1. Android Studio
2. minSdk 24

### Installation
1. Clone or download the repository to your local machine.
2. Open the project in Android Studio.

### Usage
1. Open the project in Android Studio.
2. Build and run the app on the Android simulator/emulator or a physical device.
3. The app will launch and display a prompt.
4. Enter your initial token and baseURL (stage as default).
5. The WebView will load a webpage using the provided token for authentication.
6. Make sure your token has opt in flow, go to wallet view to start "authentication" flow.
7. Tap on the continue button, a window will be displayed intercepting the javascript.
8. Use the prompt to send a new token to complete the authentication.


### Code Structure
The project follows a simple structure:

* MainActivity.java: Contains the main view of the app, including the prompt and WebView.
* UserInputDialog.java: reusable prompt dialog
* WebViewMessages.java: message handler for js flipgive interface
