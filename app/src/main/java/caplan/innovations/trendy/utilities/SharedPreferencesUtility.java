package caplan.innovations.trendy.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import leavitt.innovations.thebuzz.application.BuzzApplication;
import leavitt.innovations.thebuzz.models.Account;
import leavitt.innovations.thebuzz.models.Message;

/**
 * Created by Corey Caplan on 10/13/16.
 * Project: The Buzz
 * <p></p>
 * Purpose of Class:
 */

public class SharedPreferencesUtility {

    private static final String KEY_MESSAGES = "MESSAGES";
    private static final String KEY_ACCOUNT = "ACCOUNT";

    /**
     * Attempts to get the global account instance from shared preferences.
     */
    public static void silentLogin() {
        String json = BuzzApplication.context()
                .getSharedPreferences(KEY_ACCOUNT, Context.MODE_PRIVATE)
                .getString(KEY_ACCOUNT, null);
        if (json != null) {
            try {
                Account.login(new JSONObject(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param jsonObject Saves the given JSON containing the account's information to shared
     *                   preferences or null if the user is now logged out
     */
    public static void saveAccount(@Nullable JSONObject jsonObject) {
        BuzzApplication.getContext()
                .getSharedPreferences(KEY_ACCOUNT, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_ACCOUNT, jsonObject == null ? null : jsonObject.toString())
                .apply();
    }

    /**
     * @return The list of {@link Message} objects that were saved to JSON
     */
    @NonNull
    public static ArrayList<Message> getMessages() {
        String json = BuzzApplication.context()
                .getSharedPreferences(KEY_MESSAGES, Context.MODE_PRIVATE)
                .getString(KEY_MESSAGES, null);

        if (json == null) {
            return new ArrayList<>();
        } else {
            try {
                JSONArray jsonArray = new JSONArray(json);
                ArrayList<Message> messages = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    messages.add(Message.getDeserializer().deserializeObjectFromJson(jsonObject));
                }
                return messages;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Saves the given message's content to shared preferences (persisted storage).
     *
     * @param message The message whose content changed and should be saved.
     */
    public static void saveMessageContentChanges(Message message) {
        ArrayList<Message> messages = getMessages();
        int index = Collections.binarySearch(messages, message);
        if (index >= 0) {
            messages.remove(index);
            messages.add(index, message);
            saveMessages(messages);
        }
    }

    /**
     * Saves the messages to shared preferences.
     *
     * @param messages The messages to be saved.
     */
    public static void saveMessages(@NonNull ArrayList<Message> messages) {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < messages.size(); i++) {
            jsonArray.put(Message.getSerializer().serializeToJson(messages.get(i)));
        }

        // Convert the json array to a string
        String json = jsonArray.toString();

        // write the string to shared preferences, BE SURE TO CALL APPLY OR ELSE IT WON'T SAVE!!!
        BuzzApplication.context()
                .getSharedPreferences(KEY_MESSAGES, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_MESSAGES, json)
                .apply();
    }

}
