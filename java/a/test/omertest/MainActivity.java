package a.test.omertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import a.test.omertest.fragments.ItemsListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ItemsListFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() >= 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
