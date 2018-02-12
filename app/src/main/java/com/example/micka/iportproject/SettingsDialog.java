package com.example.micka.iportproject;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by micka on 2/12/2018.
 */

public class SettingsDialog extends AppCompatDialogFragment {

    private CheckBox mAutoBootFlag;
    private EditText mNewAutoBootDelay;
    private EditText mNewUrl;
    private View view;
    private SharePrefSingleton sPref;
    private PackageManager packageManager;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.settings_fragment,null);
        packageManager = getActivity().getPackageManager();
        sPref = SharePrefSingleton.getInstance(getActivity());

        //initialize UI components and set its default or custom values
        initializeComponents();
        setComponentsValue();


        //check box event handler
        mAutoBootFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAutoBootFlag.isChecked())
                    enableAutoLoad(true);
                else if(!mAutoBootFlag.isChecked())
                    enableAutoLoad(false);
                sPref.saveNewFlag(mAutoBootFlag.isChecked());
            }
        });

        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dismiss settings
            }
        }).setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newUrl = mNewUrl.getText().toString();

                sPref.saveNewDelay(Integer.parseInt(mNewAutoBootDelay.getText().toString()));
                sPref.saveNewURL(newUrl);

                getWebViewFromContext().loadUrl(newUrl);

            }
        });

        return builder.create();
    }

    private void initializeComponents(){
        mAutoBootFlag = view.findViewById(R.id.cb_autoboot_flag);
        mNewAutoBootDelay = view.findViewById(R.id.et_new_autoboot_delay);
        mNewUrl = view.findViewById(R.id.et_new_url);
    }

    private void setComponentsValue(){
        mNewUrl.setText(sPref.getUrl());
        mAutoBootFlag.setChecked(sPref.getAutobootFlag());
        mNewAutoBootDelay.setText(String.valueOf(sPref.getDelay()));
    }

    private final WebView getWebViewFromContext(){
        WebView webView = (WebView) ((Activity) getContext()).findViewById(R.id.wv_broadcast_holder);
        return webView;
    }

    private void enableAutoLoad(boolean bool){
        ComponentName componentName = new ComponentName(getContext(),BootIntentHandler.class);
        if(bool) {
            packageManager.setComponentEnabledSetting(componentName
                    , PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                    , PackageManager.DONT_KILL_APP);
        }else if(!bool){
            packageManager.setComponentEnabledSetting(componentName
                    , PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    , PackageManager.DONT_KILL_APP);
        }
    }

}
