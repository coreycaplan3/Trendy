package caplan.innovations.trendy.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Corey Caplan on 1/21/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To layout the boilerplate code necessary to create Java object from JSON
 */
abstract class AbstractJsonDeserializer<T> {

    private JSONObject mJsonObject;

    AbstractJsonDeserializer(JSONObject jsonObject) {
        this.mJsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return mJsonObject;
    }

    /**
     * @return An object to which this generic class conforms
     */
    public abstract T getObjectFromJson();

    /**
     * @param array The JSON array from which the objects will be extracted
     * @param extractor The extractor for the given Java object.
     * @param <T> The type of object to be returned as an array list
     * @return An array of objects of type T
     */
    static <T> ArrayList<T> getArrayFromJson(JSONArray array, AbstractJsonDeserializer<T> extractor) {
        ArrayList<T> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            list.add(extractor.getObjectFromJson());
        }
        return list;
    }

}
