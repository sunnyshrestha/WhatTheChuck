package dev.suncha.whatthechuck;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class MainActivity extends AppCompatActivity {

    private String[] navigationDrawerItemTitles;
    private DrawerLayout drawerLayout;
    private ListView categoryList;
    private RelativeLayout mainParent;


    private static String ENDPOINT;
    private RequestQueue requestQueue;
    TextView factView;
    private Gson gson;
    Toolbar toolbar;
    Drawer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainParent = (RelativeLayout) findViewById(R.id.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Add drawer
        new DrawerBuilder().withActivity(this).build();
        //PrimaryDrawerItem categories = new PrimaryDrawerItem().withIdentifier(0).withName(R.string.categories).withSelectable(false).withEnabled(false);
        final SecondaryDrawerItem random = new SecondaryDrawerItem().withIdentifier(1).withName(R.string.random).withIcon(FontAwesome.Icon.faw_random);
        SecondaryDrawerItem dev = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.dev).withIcon(FontAwesome.Icon.faw_terminal);
        SecondaryDrawerItem movie = new SecondaryDrawerItem().withIdentifier(3).withName(R.string.movie).withIcon(FontAwesome.Icon.faw_video_camera);
        SecondaryDrawerItem food = new SecondaryDrawerItem().withIdentifier(4).withName(R.string.food).withIcon(FontAwesome.Icon.faw_cutlery);
        SecondaryDrawerItem celebrity = new SecondaryDrawerItem().withIdentifier(5).withName(R.string.celebrity).withIcon(FontAwesome.Icon.faw_star);
        SecondaryDrawerItem science = new SecondaryDrawerItem().withIdentifier(6).withName(R.string.science).withIcon(FontAwesome.Icon.faw_flask);
        SecondaryDrawerItem political = new SecondaryDrawerItem().withIdentifier(7).withName(R.string.political).withIcon(FontAwesome.Icon.faw_flag);
        SecondaryDrawerItem sports = new SecondaryDrawerItem().withIdentifier(8).withName(R.string.sports).withIcon(FontAwesome.Icon.faw_futbol_o);
        SecondaryDrawerItem music = new SecondaryDrawerItem().withIdentifier(9).withName(R.string.music).withIcon(FontAwesome.Icon.faw_music);
        SecondaryDrawerItem history = new SecondaryDrawerItem().withIdentifier(10).withName(R.string.history).withIcon(FontAwesome.Icon.faw_header);
        SecondaryDrawerItem travel = new SecondaryDrawerItem().withIdentifier(11).withName(R.string.travel).withIcon(FontAwesome.Icon.faw_suitcase);
        SecondaryDrawerItem career = new SecondaryDrawerItem().withIdentifier(12).withName(R.string.career).withIcon(FontAwesome.Icon.faw_briefcase);
        SecondaryDrawerItem money = new SecondaryDrawerItem().withIdentifier(13).withName(R.string.money).withIcon(FontAwesome.Icon.faw_money);
        SecondaryDrawerItem fashion = new SecondaryDrawerItem().withIdentifier(14).withName(R.string.fashion).withIcon(FontAwesome.Icon.faw_black_tie);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.chuck_header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Chuck Norris")
                                .withEmail("gmail@chucknorris.com").withIcon(R.drawable.chuck_norris_profile)
                                .withEnabled(false)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .build();

        //create the drawer and remember the Drawer result object
        result = new DrawerBuilder()
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
                )
                .withAccountHeader(headerResult)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 1:
                                loadRandomFact();
                                break;
                            case 2:
                                loadDevFact();
                                break;
                            case 3:
                                loadMovieFact();
                                break;
                            case 4:
                                loadFoodFact();
                                break;
                            case 5:
                                loadCelebrityFact();
                                break;
                            case 6:
                                loadScienceFact();
                                break;
                            case 7:
                                loadPoliticalFact();
                                break;
                            case 8:
                                loadSportsFact();
                                break;
                            case 9:
                                loadMusicFact();
                                break;
                            case 10:
                                loadHistoryFact();
                                break;
                            case 11:
                                loadTravelFact();
                                break;
                            case 12:
                                loadCareerFact();
                                break;
                            case 13:
                                loadMoneyFact();
                                break;
                            case 14:
                                loadFashionFact();
                                break;
                            default:
                                break;

                        }
                        return true;
                    }
                })
                .build();

        //result.addStickyFooterItem(categories);


        factView = (TextView) findViewById(R.id.textView);

        requestQueue = Volley.newRequestQueue(this);
        gson = new Gson();
        fetchFacts(ENDPOINT);
    }

    private void loadFashionFact() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        toolbar.setTitle(R.string.fashion);
        ENDPOINT = "https://api.chucknorris.io/jokes/random?category=fashion";
        fetchFacts(ENDPOINT);
    }

    private void loadMoneyFact() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        toolbar.setTitle(R.string.money);
        ENDPOINT = "https://api.chucknorris.io/jokes/random?category=money";
        fetchFacts(ENDPOINT);
    }

    private void loadCareerFact() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        toolbar.setTitle(R.string.career);
        ENDPOINT = "https://api.chucknorris.io/jokes/random?category=career";
        fetchFacts(ENDPOINT);
    }

    private void loadTravelFact() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        toolbar.setTitle(R.string.travel);
        ENDPOINT = "https://api.chucknorris.io/jokes/random?category=travel";
        fetchFacts(ENDPOINT);
    }

    private void loadHistoryFact() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        toolbar.setTitle(R.string.history);
        ENDPOINT = "https://api.chucknorris.io/jokes/random?category=history";
        fetchFacts(ENDPOINT);
    }

    private void loadMusicFact() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        toolbar.setTitle(R.string.music);
        ENDPOINT = "https://api.chucknorris.io/jokes/random?category=music";
        fetchFacts(ENDPOINT);
    }

    private void loadSportsFact() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        toolbar.setTitle(R.string.sports);
        ENDPOINT = "https://api.chucknorris.io/jokes/random?category=sport";
        fetchFacts(ENDPOINT);
    }

    private void loadPoliticalFact() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        toolbar.setTitle(R.string.political);
        ENDPOINT = "https://api.chucknorris.io/jokes/random?category=political";
        fetchFacts(ENDPOINT);
    }

    private void loadScienceFact() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        toolbar.setTitle(R.string.science);
        ENDPOINT = "https://api.chucknorris.io/jokes/random?category=science";
        fetchFacts(ENDPOINT);
    }

    private void loadCelebrityFact() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        toolbar.setTitle(R.string.celebrity);
        ENDPOINT = "https://api.chucknorris.io/jokes/random?category=celebrity";
        fetchFacts(ENDPOINT);
    }

    private void loadFoodFact() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        toolbar.setTitle(R.string.food);
        ENDPOINT = "https://api.chucknorris.io/jokes/random?category=food";
        fetchFacts(ENDPOINT);
    }

    private void loadMovieFact() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        toolbar.setTitle(R.string.movie);
        ENDPOINT = "https://api.chucknorris.io/jokes/random?category=movie";
        fetchFacts(ENDPOINT);
    }

    private void loadRandomFact() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        toolbar.setTitle(R.string.random);
        ENDPOINT = "https://api.chucknorris.io/jokes/random";
        fetchFacts(ENDPOINT);

    }

    private void loadDevFact() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        }
        toolbar.setTitle(R.string.dev);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        ENDPOINT = "https://api.chucknorris.io/jokes/random?category=dev";
        fetchFacts(ENDPOINT);
    }

    private void fetchFacts(String endpoint) {
        StringRequest request = new StringRequest(Request.Method.GET, endpoint, onFactsLoaded, onFactsError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onFactsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("Fact received", response);
            Fact fact;
            fact = gson.fromJson(response, Fact.class);
            factView.setText(fact.getValue());

        }
    };

    private final Response.ErrorListener onFactsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("Fact error", error.toString());
            Snackbar.make(mainParent, "NO INTERNET", Snackbar.LENGTH_SHORT).show();
        }
    };
}
