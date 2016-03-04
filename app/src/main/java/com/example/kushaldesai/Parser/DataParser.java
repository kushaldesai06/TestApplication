package com.example.kushaldesai.Parser;

import org.json.JSONArray;
import java.util.ArrayList;

/**
 * Created by kushal.desai on 3/3/2016.
 */
public class DataParser {

    private ArrayList<DataObject> dataObjects;

    public Object parse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            dataObjects = new ArrayList<DataObject>();
            for (int i = 0; i < jsonArray.length(); i++) {
                DataObject dataObject = new DataObject();
                dataObject.id = jsonArray.getJSONObject(i).getInt("id");
                dataObject.lat = jsonArray.getJSONObject(i).getJSONObject("location").getDouble("latitude");
                dataObject.lng = jsonArray.getJSONObject(i).getJSONObject("location").getDouble("longitude");
                dataObject.name = jsonArray.getJSONObject(i).getString("name");
                dataObject.modeOfTransport = jsonArray.getJSONObject(i).getJSONObject("fromcentral").optString("car");
                dataObject.modeOfTransportTrain = jsonArray.getJSONObject(i).getJSONObject("fromcentral").optString("train");

                dataObjects.add(dataObject);
            }
            return dataObjects;
        } catch (Exception e) {
            e.printStackTrace();
            return 110;
        }
    }
}
