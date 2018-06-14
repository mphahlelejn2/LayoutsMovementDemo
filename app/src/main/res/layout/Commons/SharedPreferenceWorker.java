package bme.co.za.stopeImages.Commons;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * This class is Shared preference worker for stope images
 *
 * Created by Jeffrey.Mphahlele on 6/1/2017.
 */

public class SharedPreferenceWorker {

    /**
     * Save StopeInfo Wrapper reference into sharePreference
     *
     * @param context
     * @param stopeInfo
     * @param key
     */
    public static void saveStopeWrapperReference(Context context, bme.co.za.stopeImages.Wrapper.StopeInfo stopeInfo, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(stopeInfo);
        editor.putString(key, json);

        editor.commit();
    }

    /**
     * This method save image name into stope wrapper reference
     *
     * @param context
     * @param stopekey
     * @param imageUrl
     */
    public static void saveImageNameReferenceToStopeWrapper(Context context, String stopekey, String imageUrl) {

        bme.co.za.stopeImages.Wrapper.StopeInfo stopeInfo = getStopeInfoReference(context,stopekey);
        if(stopeInfo !=null)
        {
            stopeInfo.getImagesList().add(imageUrl);
        }else
        {
            stopeInfo =new bme.co.za.stopeImages.Wrapper.StopeInfo();
            ArrayList<String> list=new ArrayList<String>();
            list.add(imageUrl);
            String savedStopeImage="";
            stopeInfo.setSavedStopeImageName(savedStopeImage);
            stopeInfo.setImagesList(list);

        }
        saveStopeWrapperReference(context, stopeInfo,stopekey);
    }

    /**
     * Remove reference to the last image of the stope
     *
     * @param context
     * @param stopekey
     * @return
     */
    public static String removeLastStopeImageNameReference(Context context, String stopekey) {

        bme.co.za.stopeImages.Wrapper.StopeInfo stopeInfo = getStopeInfoReference(context,stopekey);
        String url=null;
        if(stopeInfo !=null)
        {
            url= stopeInfo.getImagesList().remove(stopeInfo.getImagesList().size()-1);
        }
        saveStopeWrapperReference(context, stopeInfo,stopekey);
        return url;
    }

    /**
     * Save cropped stope image reference
     *
     * @param context
     * @param stopekey
     * @param url
     */
    public static void saveCroppedStopeImageNameReference(Context context, String stopekey, String url) {

        bme.co.za.stopeImages.Wrapper.StopeInfo stopeInfo = getStopeInfoReference(context,stopekey);
        if(stopeInfo !=null)
        {
            stopeInfo.setSavedStopeImageName(url);
        }
        saveStopeWrapperReference(context, stopeInfo,stopekey);

    }

    /**
     * This method delete cropped stope image reference
     *
     * @param context
     * @param stopekey
     */
    public static void deleteCroppedStopeImageNameReference(Context context, String stopekey) {

        bme.co.za.stopeImages.Wrapper.StopeInfo stopeInfo = getStopeInfoReference(context,stopekey);
        if(stopeInfo !=null)
        {
            stopeInfo.setSavedStopeImageName("");
        }
        saveStopeWrapperReference(context, stopeInfo,stopekey);

    }

    /**
     * return a stope wrapper reference
     *
     * @param context
     * @param key
     * @return stope wrapper reference
     */
    public static bme.co.za.stopeImages.Wrapper.StopeInfo getStopeInfoReference(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, "");
        bme.co.za.stopeImages.Wrapper.StopeInfo obj = gson.fromJson(json, bme.co.za.stopeImages.Wrapper.StopeInfo.class);
        return obj;
    }

    /**
     * Delete stope wrapper reference from shared preference
     *
     * @param context
     * @param key
     */
    public static void deleteStopeWrapperReference(Context context, String key)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * Get all sorted key reference
     *
     * @param context
     * @return key list
     */
    public static List<String> getAllStopesKeysReference(Context context) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            Map<String, ?> allEntries = (Map<String, ?>) prefs.getAll();
            List<String> result = new ArrayList(allEntries.keySet());
            Collections.sort(result);
            //sort a list
            Collections.reverse(result);
            return result;
    }

}
