package com.scorptech.turtleremote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.scorptech.turtleremote.addCarScreen.AddCarView;
import com.scorptech.turtleremote.carManagementScreen.CarManagementView;
import com.scorptech.turtleremote.carsScreen.CarsView;
import com.scorptech.turtleremote.settingsScreen.SettingsView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{

    private static final String TAG = "MainActivity";
    private static Pages CURRENT_PAGE;

    @BindView(R.id.toolbar_left_button)
    Button toolbar_left;
    @BindView(R.id.toolbar_right_button)
    Button toolbar_right;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbar_subtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().addOnBackStackChangedListener(this);
            CarsView cView = new CarsView();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment, cView);
            ft.commit();
            CURRENT_PAGE = Pages.CARS;
            setupToolbar(CURRENT_PAGE);
        } else {
            goTo(getPageEnum(savedInstanceState.getString("CURRENT_PAGE")));
        }
    }


    public void goTo(Pages page) {
        if (CURRENT_PAGE != page && page != null) {

            CURRENT_PAGE = page;
            switch (page) {
                case CARS:
                    CarsView cView = new CarsView();
                    addFragmentToStack(cView);
                    break;
                case ADD_CAR:
                    AddCarView acView = new AddCarView();
                    addFragmentToStack(acView);
                    break;
                case CAR_MANAGEMENT:
                    setFullscreen(true);
                    CarManagementView cmView = new CarManagementView();
                    addFragmentToStack(cmView);
                    break;
                case SETTINGS:
                    SettingsView sView = new SettingsView();
                    addFragmentToStack(sView);
                    break;
                case POP_BACK:
                    getSupportFragmentManager().popBackStack();
                    break;
            }
        }
    }

    private void addFragmentToStack(Fragment newFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                               android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.fragment, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void setupToolbar(Pages page) {
        hideToolbarButtons();
        toolbar.setVisibility(View.VISIBLE);
        switch (page) {
            case CARS:
                setFullscreen(false);
                showToolbarButton(toolbar_right, R.mipmap.toolbar_settings);
                toolbar_right.setTag(Pages.SETTINGS);
                showToolbarButton(toolbar_left, R.mipmap.toolbar_add);
                toolbar_left.setTag(Pages.ADD_CAR);
                break;
            case CAR_MANAGEMENT:
                setFullscreen(true);
                toolbar.setVisibility(View.GONE);
                break;
            case SETTINGS:
            case ADD_CAR:
                setFullscreen(false);
                showToolbarButton(toolbar_left, R.mipmap.toolbar_back);
                toolbar_left.setTag(Pages.POP_BACK);
                break;
        }
        setToolbarTextContent(page);
    }

    private void hideToolbarButtons() {
        toolbar_left.setVisibility(View.GONE);
        toolbar_left.setText("");
        toolbar_right.setVisibility(View.GONE);
        toolbar_right.setText("");
    }

    private void setToolbarTextContent(Pages page) {
        switch (page) {
            case CARS:
                toolbar_title.setText(R.string.cars_title);
                toolbar_subtitle.setText(R.string.cars_subtitle);
                break;
            case SETTINGS:
                toolbar_title.setText(R.string.settings_title);
                toolbar_subtitle.setText(R.string.settings_subtitle);
                toolbar_left.setText(R.string.cars_title);
                break;
            case ADD_CAR:
                toolbar_title.setText(R.string.add_car_title);
                toolbar_subtitle.setText(R.string.add_car_subtitle);
                toolbar_left.setText(R.string.cars_title);
                break;
            // TODO: other pages will be added
        }
    }

    private void showToolbarButton(Button button, int resourceId) {
        if(button.getId() == toolbar_left.getId()) {
            toolbar_left.setVisibility(View.VISIBLE);
            button.setCompoundDrawablesWithIntrinsicBounds(resourceId, 0, 0, 0);
        } else if(button.getId() == toolbar_right.getId()) {
            toolbar_right.setVisibility(View.VISIBLE);
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, resourceId, 0);
        }
    }

    @OnClick({ R.id.toolbar_left_button, R.id.toolbar_right_button })
    public void tButtonClicked(View v) {
        Pages page = (Pages) v.getTag();
        goTo(page);
    }

    private Pages getPageEnum(String page) {
        if (page.equals(Pages.CARS.toString())) {
            return Pages.CARS;
        } else if (page.equals(Pages.ADD_CAR.toString())) {
            return Pages.ADD_CAR;
        } else if (page.equals(Pages.CAR_MANAGEMENT.toString())) {
            return Pages.CAR_MANAGEMENT;
        } else if (page.equals(Pages.SETTINGS.toString())) {
            return Pages.SETTINGS;
        } else if (page.equals(Pages.POP_BACK.toString())) {
            return Pages.POP_BACK;
        } else {
            return Pages.CARS;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CURRENT_PAGE", CURRENT_PAGE.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackStackChanged() {
        Fragment someFragment = (Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment);

        if (someFragment == null) {
            // this fragment was removed from back stack
        } else {

            Toast.makeText(this, someFragment.toString(), Toast.LENGTH_LONG).show();
            if (someFragment.getClass().toString().equals(SettingsView.class.toString())) {
                CURRENT_PAGE = Pages.SETTINGS;
            } else if (someFragment.getClass().toString().equals(CarsView.class.toString())) {
                CURRENT_PAGE = Pages.CARS;
            } else if (someFragment.getClass().toString().equals(AddCarView.class.toString())) {
                CURRENT_PAGE = Pages.ADD_CAR;
            } else if(someFragment.getClass().toString().equals(CarManagementView.class.toString())) {
                CURRENT_PAGE = Pages.CAR_MANAGEMENT;
            }
        }
        Toast.makeText(this, "CURRENT PAGE: " + CURRENT_PAGE, Toast.LENGTH_LONG).show();

        setupToolbar(CURRENT_PAGE);
    }

    private void setFullscreen(boolean fullscreen)
    {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        if (fullscreen)
        {
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }
        else
        {
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }
        getWindow().setAttributes(attrs);
    }
}
