package com.example.projetisn;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navView;
    private VocabFrag vocabFrag;
    private FriendsFrag friendsFrag;
    private ProfilFrag profilFrag;
    private ArrayList<Fragment> HomeMadeBackStack = new ArrayList<>();
    boolean programaticallySelected;


    private void setFragment(Fragment fragment){
       Fragment currentFrag = getCurrentFragment();
       final FragmentManager manager = getSupportFragmentManager();
       FragmentTransaction ft = manager.beginTransaction();
       ft.replace(R.id.main_frame, fragment);
       if (currentFrag != null) {
           HomeMadeBackStack.add(currentFrag);
       }
       Toast toast = Toast.makeText(getBaseContext(), "fragmentToBeSet = " + currentFrag, Toast.LENGTH_LONG);
       toast.show();
       ft.commit();
        int i;
        for (i = 1; i < HomeMadeBackStack.size(); i++) {
            Fragment fragFromBackStack = HomeMadeBackStack.get(i);
            if (fragment == fragFromBackStack) {
                HomeMadeBackStack.remove(i);
            }
            if (HomeMadeBackStack.size() > 1) {
                if (HomeMadeBackStack.get(1) == vocabFrag) {
                    HomeMadeBackStack.remove(1);
                }
            }
        }
   }

   private void setFragmentReturn(Fragment fragment){
       final FragmentManager manager = getSupportFragmentManager();
       FragmentTransaction ft = manager.beginTransaction();
       ft.replace(R.id.main_frame, fragment);
       ft.commit();
   }

   private Fragment getCurrentFragment(){
       if (vocabFrag.isVisible()){
           return vocabFrag;
       }
       else if (friendsFrag.isVisible()){
           return friendsFrag;
       }
       else if (profilFrag.isVisible()){
           return profilFrag;
       }
       else{
           return null;
       }
   }

    @Override
    public void onBackPressed() {
        int HomeMadeBackStackSize = HomeMadeBackStack.size();
        if (HomeMadeBackStackSize < 1) {
            finish();
        } else {

            Fragment fragmentToBeSet = HomeMadeBackStack.get(HomeMadeBackStackSize - 1);
            if (fragmentToBeSet != null) {
                setFragmentReturn(fragmentToBeSet);
            }
            HomeMadeBackStack.remove(HomeMadeBackStackSize - 1);
            if (fragmentToBeSet == vocabFrag){
                programaticallySelected = true;
                navView.setSelectedItemId(R.id.navigation_vocab);
            }
            else if (fragmentToBeSet == friendsFrag){
                programaticallySelected = true;
                navView.setSelectedItemId(R.id.navigation_friends);
            }
            else if (fragmentToBeSet == profilFrag){
                programaticallySelected = true;
                navView.setSelectedItemId(R.id.navigation_profil);
            }

        }
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        vocabFrag = new VocabFrag();
        friendsFrag = new FriendsFrag();
        profilFrag = new ProfilFrag();



        setFragment(vocabFrag);
        HomeMadeBackStack.add(vocabFrag);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_vocab, R.id.naviagtion_friends, R.id.navigation_profil)
                .build();*/
       navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
               switch (menuItem.getItemId()){
                   case R.id.navigation_vocab:
                       if (!programaticallySelected) {
                           setFragment(vocabFrag);
                       }
                       else{
                           programaticallySelected = false;
                       }
                       return true;
                   case R.id.navigation_friends:
                       if (!programaticallySelected) {
                           setFragment(friendsFrag);
                       }
                       else{
                           programaticallySelected = false;
                       }
                       return true;
                   case R.id.navigation_profil:
                       if (!programaticallySelected) {
                           setFragment(profilFrag);
                       }
                       else{
                           programaticallySelected = false;
                       }
                       return true;
                   default:
                       return false;
               }

           }
       }

       );
    }

}
