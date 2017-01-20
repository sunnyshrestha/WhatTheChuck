package dev.suncha.whatthechuck;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private String[] navigationDrawerItemTitles;
    private DrawerLayout drawerLayout;
    private ListView categoryList;


    private static final String ENDPOINT = "https://api.chucknorris.io/jokes/random";
    private RequestQueue requestQueue;
    TextView factView;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationDrawerItemTitles = getResources().getStringArray(R.array.categories);
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
        categoryList = (ListView)findViewById(R.id.navigation_list);

        CategoryModel[] categories = new CategoryModel[13];
        categories[0]=new CategoryModel("Dev");
        categories[1]=new CategoryModel("Movie");
        categories[2]=new CategoryModel("Food");

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this,R.layout.list_view_item,categories);
        categoryList.setAdapter(adapter);
//        categoryList.setOnItemClickListener(new DrawerItemClickListener());

        factView = (TextView)findViewById(R.id.textView);

        requestQueue = Volley.newRequestQueue(this);
        gson = new Gson();
        fetchFacts();
    }

    private void fetchFacts() {
        StringRequest request = new StringRequest(Request.Method.GET,ENDPOINT,onFactsLoaded,onFactsError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onFactsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("Fact received",response);
            Fact fact = new Fact();
            fact = gson.fromJson(response,Fact.class);
            factView.setText(fact.getValue());


        }
    };

    private final Response.ErrorListener onFactsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("Fact error",error.toString());
        }
    };
}
