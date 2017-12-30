package data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.Menu;
import android.widget.EditText;

import com.example.lincoln.weatheapp.MainActivity;

/**
 * Created by Lincoln on 12/28/2017.
 */

public class CityPreference {
    SharedPreferences prefs;
    public CityPreference(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }
    public String getCity(){
        return prefs.getString("city","Spokane,US");
    }
    public void setCity (String city){
        prefs.edit().putString("city",city).commit();
    }
}


