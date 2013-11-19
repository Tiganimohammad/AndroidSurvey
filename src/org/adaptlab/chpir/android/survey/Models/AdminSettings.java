package org.adaptlab.chpir.android.survey.Models;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "AdminSettings")
public class AdminSettings extends Model {
    private static final String TAG = "AdminSettings";
    @Column(name = "DeviceIdentifier")
    private String mDeviceIdentifier;
    @Column(name = "SyncInterval")
    private int mSyncInterval;
    @Column(name = "ApiUrl")
    private String mApiUrl;
    
    private static AdminSettings adminSettings;
    
    /**
     * This maintains a single row in the database for the admin settings, and
     * effectively is a Singleton.  This is done to piggy-back on the
     * ReceiveModel functionality.
     * 
     */
    public static AdminSettings getInstance() {
        adminSettings = new Select().from(AdminSettings.class).orderBy("Id asc").executeSingle();
        if (adminSettings == null) {
            Log.i(TAG, "Creating new admin settings instance");
            adminSettings = new AdminSettings();
            adminSettings.save();
        }
        return adminSettings;
    }
    
    /**
     * Typically a Singleton constructor is private, but in this case the constructor
     * must be public for ActiveAndroid to function properly.  Do not use this
     * constructor, use getInstance() instead.
     * 
     */
    public AdminSettings() {
        super();
    }
    
    public void setDeviceIdentifier(String id) {
        Log.i(TAG, "Setting device identifier: " + id);
        mDeviceIdentifier = id;
        save();
    }
    
    public String getDeviceIdentifier() {
        return mDeviceIdentifier;
    }
    
    /**
     * Millisecond sync interval
     */
    public int getSyncInterval() {
        return mSyncInterval;
    }

    /**
     * Second sync interval
     */
    public int getSyncIntervalInMinutes() {
        return mSyncInterval / (60 * 1000);
    }
    
    /**
     * Set the interval in minutes, it is converted to milliseconds
     */
    public void setSyncInterval(int interval) {
        Log.i(TAG, "Setting set interval: " + (interval * 1000 * 60));
        mSyncInterval = interval * 1000 * 60;
        save();
    }
    
    public void setApiUrl(String apiUrl) {
        Log.i(TAG, "Setting api endpoint: " + apiUrl);
        mApiUrl = apiUrl;
        save();
    }
    
    public String getApiUrl() {
        return mApiUrl;
    }

}
