package com.haydende.heymusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Activity will be the first to start once application has started.
 * <p>This is where the user will be prompted to accept Read/Write permissions.</p>
 */
public class EntryPointActivity extends AppCompatActivity {

    /**
     * Used to capture user's permission response for READ_EXTERNAL_STORAGE_PERMISSION.
     */
    private final static int READ_EXTERNAL_STORAGE_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_point);
        checkReadExternalStoragePermission();
    }

    /**
     * Checks if the device is using Android Marshmallow or above.
     * <p>If so, the application needs to ensure that the user has given permission to access storage</p>
     */
    private void checkReadExternalStoragePermission() {
        // check if device is using Marshmallow or above
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // if the permission has been granted...
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                // start cursor loader
            // if permission has not been granted
            } else {
                // determine if the application should show a custom permission request
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // display toast notification to request for permission
                    Toast.makeText(this,
                                    "App needs access to external storage to work",
                                    Toast.LENGTH_SHORT).show();
                }
                // prompt for permission
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                                   READ_EXTERNAL_STORAGE_PERMISSION);
            }
        } else {
            // start cursor loader
        }
    }

    /**
     * Method is called once the permission request has been acknowledged.
     * @param requestCode Passed result code to identify which permission is being checked
     * @param permissions <code>String</code> array of permissions that are request by application
     * @param grantResults Array of results for permissions - used to determine if accepted or denied
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String [] permissions,
                                           @NonNull int[] grantResults) {
        // Using switch/case makes it easier to add more if necessary
        switch (requestCode) {
            // in the case that the permission to check is READ_EXTERNAL_STORAGE_PERMISSION
            case READ_EXTERNAL_STORAGE_PERMISSION:
                // if the permission was granted...
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // call cursor loader
                // if the permission was not granted
                } else {
                    // TODO: Warns user about permission requirements
                }
            // do this if no other case is accepted
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
