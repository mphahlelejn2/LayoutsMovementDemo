package bme.co.za.stopeImages.Wrapper;

import java.util.ArrayList;

/**
 * This class contains individual stope data
 *
 * Created by Jeffrey.Mphahlele on 7/25/2017.
 */

public class StopeInfo {

    String savedStopeImageName;
    ArrayList<String> imagesList;

    public String getSavedStopeImageName() {
        return savedStopeImageName;
    }

    public void setSavedStopeImageName(String savedStopeImageName) {
        this.savedStopeImageName = savedStopeImageName;
    }

    public ArrayList<String> getImagesList() {
        return imagesList;
    }

    public void setImagesList(ArrayList<String> imagesList) {
        this.imagesList = imagesList;
    }
}
