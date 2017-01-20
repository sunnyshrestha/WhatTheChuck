package dev.suncha.whatthechuck;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

public class MainActivity extends AppCompatActivity {

    private String[] navigationDrawerItemTitles;
    private DrawerLayout drawerLayout;
    private ListView categoryList;
    private RelativeLayout mainParent;


    private static final String ENDPOINT = "https://api.chucknorris.io/jokes/random";
    private RequestQueue requestQueue;
    TextView factView;
    private Gson gson;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainParent = (RelativeLayout)findViewById(R.id.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        //Add drawer
        new DrawerBuilder().withActivity(this).build();
        SecondaryDrawerItem random = new SecondaryDrawerItem().withIdentifier(1).withName(R.string.random);
        SecondaryDrawerItem dev = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.dev);
        SecondaryDrawerItem movie = new SecondaryDrawerItem().withIdentifier(3).withName(R.string.movie);
        SecondaryDrawerItem food = new SecondaryDrawerItem().withIdentifier(4).withName(R.string.food);
        SecondaryDrawerItem celebrity= new SecondaryDrawerItem().withIdentifier(5).withName(R.string.celebrity);
        SecondaryDrawerItem science= new SecondaryDrawerItem().withIdentifier(6).withName(R.string.science);
        SecondaryDrawerItem political= new SecondaryDrawerItem().withIdentifier(7).withName(R.string.political);
        SecondaryDrawerItem sports= new SecondaryDrawerItem().withIdentifier(8).withName(R.string.sports);
        SecondaryDrawerItem music= new SecondaryDrawerItem().withIdentifier(9).withName(R.string.music);
        SecondaryDrawerItem history= new SecondaryDrawerItem().withIdentifier(10).withName(R.string.history);
        SecondaryDrawerItem travel= new SecondaryDrawerItem().withIdentifier(11).withName(R.string.travel);
        SecondaryDrawerItem career= new SecondaryDrawerItem().withIdentifier(12).withName(R.string.career);
        SecondaryDrawerItem money= new SecondaryDrawerItem().withIdentifier(13).withName(R.string.money);
        SecondaryDrawerItem fashion= new SecondaryDrawerItem().withIdentifier(14).withName(R.string.fashion);


        //create the drawer and remember the Drawer result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        random,
                        dev,
                        movie,
                        food,
                        celebrity,
                        science,
                        political,
                        sports,
                        music,
                        history,
                        travel,
                        career,
                        money,
                        fashion
                ).build();

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
            Fact fact;
            fact = gson.fromJson(response,Fact.class);
            factView.setText(fact.getValue());

        }
    };

    private final Response.ErrorListener onFactsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("Fact error",error.toString());
            Snackbar.make(mainParent,"NO INTERNET",Snackbar.LENGTH_SHORT).show();
        }
    };
}
