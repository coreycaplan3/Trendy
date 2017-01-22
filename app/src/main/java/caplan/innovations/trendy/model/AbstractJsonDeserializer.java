package caplan.innovations.trendy.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import caplan.innovations.trendy.utilities.JsonExtractor;

/**
 * Created by Corey Caplan on 1/21/17.
 * Project: Trendy
 * <p></p>
 * Purpose of Class: To layout the boilerplate code necessary to create Java object from JSON
 */
abstract class AbstractJsonDeserializer<T> {

    private static final String TAG = AbstractJsonDeserializer.class.getSimpleName();

    AbstractJsonDeserializer() {
    }

    /**
     * @param jsonObject The JSON object that should be converted to its equivalent Java object.
     * @return An object to which this generic class conforms
     */
    public abstract T getObjectFromJson(JSONObject jsonObject);

    /**
     * @param array     The JSON array from which the objects will be extracted
     * @param deserializer The extractor for the given Java object.
     * @param <T>       The type of object to be returned as an array list
     * @return An array of objects of type T
     */
    public static <T> ArrayList<T> getArrayFromJson(JSONArray array,
                                                    AbstractJsonDeserializer<T> deserializer) {
        ArrayList<T> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                T object = deserializer.getObjectFromJson(array.getJSONObject(i));
                if (object != null) {
                    list.add(object);
                }
            } catch (JSONException e) {
                Log.d(TAG, "getArrayFromJson: ", e);
            }
        }
        return list;
    }

}
