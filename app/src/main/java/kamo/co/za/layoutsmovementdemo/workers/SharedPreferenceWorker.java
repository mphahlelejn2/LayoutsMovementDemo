package kamo.co.za.layoutsmovementdemo.workers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import java.util.ArrayList;
import kamo.co.za.layoutsmovementdemo.main.Info;



public class SharedPreferenceWorker {

    public static Info getData(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, "");

        Info info =gson.fromJson(json, Info.class);
        if (info ==null){
            info =new Info();
            addData(context, info,key);
        }
        return info;
    }


    public static void deleteLayoutInfo(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key);
        editor.apply();
    }

    public static void removeLastImage(Context context, String key) {
        Info info = getData(context,key);
        if(!info.getImagesList().isEmpty()){
        info.getImagesList().remove(info.getImagesList().size()-1);
        addData(context, info,key);
        }
    }

   public static void addData(Context context, Info info, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(info);
        editor.putString(key, json);
        editor.apply();
    }
    public static void addImageName(Context context, String imageName, String key) {
        Info info =getData(context,key);
        info.getImagesList().add(imageName);
        addData(context, info,key);
    }

    public static ArrayList<String> getImageList(Context context,String key) {
        Info info =getData(context,key);
        if(info !=null) {
            return info.getImagesList();
        }else{
        return null;
        }
    }


}
