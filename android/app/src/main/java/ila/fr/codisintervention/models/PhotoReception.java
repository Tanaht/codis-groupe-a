package ila.fr.codisintervention.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ila.fr.codisintervention.models.model.Photo;

public class PhotoReception {

    HashMap<Integer, List<Photo>> map = new HashMap<Integer, List<Photo>>();

    public boolean isPhotoReceptionEmpty() {
        return map.isEmpty();
    }

    public void addPhotos(Integer id, ArrayList<Photo> mesPhotos) {
        map.put(id, mesPhotos);
    }

    public void getPhotos(Integer id) {
        map.get(id);
    }
}
