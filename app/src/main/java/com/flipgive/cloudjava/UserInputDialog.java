package com.flipgive.cloudjava;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class UserInputDialog {

    private Context context;
    private PromptCallback callback;

    public UserInputDialog(Context context, PromptCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void promptUserForValues() {
        // Create a LayoutInflater to inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_prompt, null);

        // Find the EditText fields in the custom layout
        final EditText tokenInput = dialogView.findViewById(R.id.token_input);
        final EditText baseUrlInput = dialogView.findViewById(R.id.base_url_input);

        // Create the AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    public interface PromptCallback {
        void onValuesEntered(String[] values);
    }
}
