package dev.suncha.whatthechuck;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bhargavms.dotloader.DotLoader;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.like.LikeButton;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity {

    Fact fact;

    private RelativeLayout mainParent;

    private static String ENDPOINT;
    private RequestQueue requestQueue;
    TextView factView;
    private Gson gson;
    Toolbar toolbar;
    Drawer result;
    private FancyButton nextButton, copyButton;
    DotLoader dotloader;
    LikeButton starButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        setContentView(R.layout.activity_main);

        mainParent = (RelativeLayout) findViewById(R.id.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nextButton = (FancyButton) findViewById(R.id.nextButton);
        copyButton = (FancyButton) findViewById(R.id.copyButton);
        dotloader = (DotLoader) findViewById(R.id.text_dot_loader);
        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/PoiretOne-Regular.ttf");
        factView = (TextView) findViewById(R.id.textView);
        starButton = (LikeButton) findViewById(R.id.starButton);


        //font for factview
        factView.setTypeface(font);
        factView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.md_white_1000));

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
                                starButton.setVisibility(View.INVISIBLE);
                                dotloader.setVisibility(View.VISIBLE);
                                loadRandomFact();
                                starButton.setVisibility(View.VISIBLE);
                                break;
                            case 2:
                                starButton.setVisibility(View.INVISIBLE);
                                dotloader.setVisibility(View.VISIBLE);
                                loadDevFact();
                                starButton.setVisibility(View.VISIBLE);
                                break;
                            case 3:
                                starButton.setVisibility(View.INVISIBLE);
                                dotloader.setVisibility(View.VISIBLE);
                                loadMovieFact();
                                starButton.setVisibility(View.VISIBLE);
                                break;
                            case 4:
                                starButton.setVisibility(View.INVISIBLE);
                                dotloader.setVisibility(View.VISIBLE);
                                loadFoodFact();
                                starButton.setVisibility(View.VISIBLE);
                                break;
                            case 5:
                                starButton.setVisibility(View.INVISIBLE);
                                dotloader.setVisibility(View.VISIBLE);
                                loadCelebrityFact();
                                starButton.setVisibility(View.VISIBLE);
                                break;
                            case 6:
                                starButton.setVisibility(View.INVISIBLE);
                                dotloader.setVisibility(View.VISIBLE);
                                loadScienceFact();
                                starButton.setVisibility(View.VISIBLE);
                                break;
                            case 7:
                                starButton.setVisibility(View.INVISIBLE);
                                dotloader.setVisibility(View.VISIBLE);
                                loadPoliticalFact();
                                starButton.setVisibility(View.VISIBLE);
                                break;
                            case 8:
                                starButton.setVisibility(View.INVISIBLE);
                                dotloader.setVisibility(View.VISIBLE);
                                loadSportsFact();
                                starButton.setVisibility(View.VISIBLE);
                                break;
                            case 9:
                                starButton.setVisibility(View.INVISIBLE);
                                dotloader.setVisibility(View.VISIBLE);
                                loadMusicFact();
                                starButton.setVisibility(View.VISIBLE);
                                break;
                            case 10:
                                starButton.setVisibility(View.INVISIBLE);
                                dotloader.setVisibility(View.VISIBLE);
                                loadHistoryFact();
                                starButton.setVisibility(View.VISIBLE);
                                break;
                            case 11:
                                starButton.setVisibility(View.INVISIBLE);
                                dotloader.setVisibility(View.VISIBLE);
                                loadTravelFact();
                                starButton.setVisibility(View.VISIBLE);
                                break;
                            case 12:
                                starButton.setVisibility(View.INVISIBLE);
                                dotloader.setVisibility(View.VISIBLE);
                                loadCareerFact();
                                starButton.setVisibility(View.VISIBLE);
                                break;
                            case 13:
                                starButton.setVisibility(View.INVISIBLE);
                                dotloader.setVisibility(View.VISIBLE);
                                loadMoneyFact();
                                starButton.setVisibility(View.VISIBLE);
                                break;
                            case 14:
                                starButton.setVisibility(View.INVISIBLE);
                                dotloader.setVisibility(View.VISIBLE);
                                loadFashionFact();
                                starButton.setVisibility(View.VISIBLE);
                                break;
                            default:
                                break;

                        }
                        return true;
                    }
                })
                .build();

        requestQueue = Volley.newRequestQueue(this);
        gson = new Gson();
        loadRandomFact();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dotloader.setVisibility(View.VISIBLE);
                loadNextFact(result.getCurrentSelectedPosition());
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipboard();
            }
        });
    }

    private void copyToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("clip data", factView.getText().toString());
        clipboard.setPrimaryClip(clip);
        Snackbar.make(mainParent, R.string.copied, Snackbar.LENGTH_SHORT).show();
    }


    private void loadNextFact(int position) {
        switch (position) {
            case 1:
                starButton.setVisibility(View.INVISIBLE);
                loadRandomFact();
                starButton.setVisibility(View.VISIBLE);
                break;
            case 2:
                starButton.setVisibility(View.INVISIBLE);
                loadDevFact();
                starButton.setVisibility(View.VISIBLE);
                break;
            case 3:
                starButton.setVisibility(View.INVISIBLE);
                loadMovieFact();
                starButton.setVisibility(View.VISIBLE);
                break;
            case 4:
                starButton.setVisibility(View.INVISIBLE);
                loadFoodFact();
                starButton.setVisibility(View.VISIBLE);
                break;
            case 5:
                starButton.setVisibility(View.INVISIBLE);
                loadCelebrityFact();
                starButton.setVisibility(View.VISIBLE);
                break;
            case 6:
                starButton.setVisibility(View.INVISIBLE);
                loadScienceFact();
                starButton.setVisibility(View.VISIBLE);
                break;
            case 7:
                starButton.setVisibility(View.INVISIBLE);
                loadPoliticalFact();
                starButton.setVisibility(View.VISIBLE);
                break;
            case 8:
                starButton.setVisibility(View.INVISIBLE);
                loadSportsFact();
                starButton.setVisibility(View.VISIBLE);
                break;
            case 9:
                starButton.setVisibility(View.INVISIBLE);
                loadMusicFact();
                starButton.setVisibility(View.VISIBLE);
                break;
            case 10:
                starButton.setVisibility(View.INVISIBLE);
                loadHistoryFact();
                starButton.setVisibility(View.VISIBLE);
                break;
            case 11:
                starButton.setVisibility(View.INVISIBLE);
                loadTravelFact();
                starButton.setVisibility(View.VISIBLE);
                break;
            case 12:
                starButton.setVisibility(View.INVISIBLE);
                loadCareerFact();
                starButton.setVisibility(View.VISIBLE);
                break;
            case 13:
                starButton.setVisibility(View.INVISIBLE);
                loadMoneyFact();
                starButton.setVisibility(View.VISIBLE);
                break;
            case 14:
                starButton.setVisibility(View.INVISIBLE);
                loadFashionFact();
                starButton.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
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
//            Fact fact;
            fact = gson.fromJson(response, Fact.class);
            factView.setText(fact.getValue());
            dotloader.setVisibility(View.INVISIBLE);
        }
    };

    private final Response.ErrorListener onFactsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("Fact error", error.toString());
            Snackbar.make(mainParent, "NO INTERNET", Snackbar.LENGTH_SHORT).show();
            dotloader.setVisibility(View.INVISIBLE);
        }
    };
}
