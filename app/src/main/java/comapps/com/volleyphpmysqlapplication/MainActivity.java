package comapps.com.volleyphpmysqlapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.backendless.Backendless;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    final String TAG = this.getClass().getSimpleName();

    TextView results;
    // URL of object to be parsed
    String JsonURL = "https://raw.githubusercontent.com/ianbar20/JSON-Volley-Tutorial/master/Example-JSON-Files/Example-Array.JSON";
    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        results = (TextView) findViewById(R.id.tvresults);

        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this, Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION);


     /*   BackendlessUser user = new BackendlessUser();
        user.setEmail("pjcoman@gmail.com");
        user.setPassword("password");

        Backendless.UserService.register(user, new BackendlessCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser backendlessUser) {
                Log.i("Registration", backendlessUser.getEmail() + " successfully registered");
            }
        });
*/

      /*  String url = "https://raw.githubusercontent.com/ianbar20/JSON-Volley-Tutorial/master/Example-JSON-Files/Example-Array.JSON";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error while reading data", Toast.LENGTH_SHORT).show();

            }
        });*/

        JsonArrayRequest arrayreq = new JsonArrayRequest(JsonURL,
                // The second parameter Listener overrides the method onResponse() and passes
                //JSONArray as a parameter
                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Retrieves first JSON object in outer array
                            JSONObject colorObj = response.getJSONObject(0);
                            // Retrieves "colorArray" from the JSON object
                            JSONArray colorArry = colorObj.getJSONArray("colorArray");
                            // Iterates through the JSON Array getting objects and adding them
                            //to the list view until there are no more objects in colorArray
                            for (int i = 0; i < colorArry.length(); i++) {
                                //gets each JSON object within the JSON array
                                JSONObject jsonObject = colorArry.getJSONObject(i);

                                // Retrieves the string labeled "colorName" and "hexValue",
                                // and converts them into javascript objects
                                String color = jsonObject.getString("colorName");
                                String hex = jsonObject.getString("hexValue");

                                // Adds strings from the current object to the data string
                                //spacing is included at the end to separate the results from
                                //one another
                                data += "Color Number " + (i + 1) + "\nColor Name: " + color +
                                        "\nHex Value : " + hex + "\n\n\n";
                            }
                            // Adds the data string to the TextView "results"
                            results.setText(data);
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        // Adds the JSON array request "arrayreq" to the request queue

        MySingleton.getInstance(this).addToRequestQueue(arrayreq);
   //     MySingleton.getInstance(this).addToRequestQueue(stringRequest);
     //   MySingleton.getInstance(this).addToRequestQueue(stringRequest2);
    }










}
