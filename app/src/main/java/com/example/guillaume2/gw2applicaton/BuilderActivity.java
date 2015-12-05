package com.example.guillaume2.gw2applicaton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.guillaume2.gw2applicaton.Builder.DialogSpeChoice;
import com.example.guillaume2.gw2applicaton.Builder.Specialization;
import com.example.guillaume2.gw2applicaton.Builder.SpecializationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by guillaume2 on 02/12/15.
 */
public class BuilderActivity extends AppCompatActivity implements CallerBack {
    private FrameLayout specialization1;
    private FrameLayout specialization2;
    private FrameLayout specialization3;
    private SpecializationManager specializationManager;
    private List<Specialization> specializations;
    private DialogSpeChoice dialogSpeChoice;
    private Professions professions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        dialogSpeChoice = new DialogSpeChoice();
        Collection c = (Collection) getApplication();
        specializationManager = c.specializationManager;
        HashMap<String, Specialization> spe = specializationManager.getSpe().get(Professions.WARRIOR);
        specializations = new ArrayList<>(spe.values());

        setContentView(R.layout.activity_builder);
        specialization1 = (FrameLayout) findViewById(R.id.spe1);
        //ImageView view  = (ImageView) specialization1.findViewById(R.id.background);

        specialization2 = (FrameLayout) findViewById(R.id.spe2);
        specialization3 = (FrameLayout) findViewById(R.id.spe3);
        changeVisibility(View.INVISIBLE);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    private void changeVisibility(int visibility) {
        specialization1.setVisibility(visibility);
        specialization2.setVisibility(visibility);
        specialization3.setVisibility(visibility);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean in = false;
        switch (id) {
            case R.id.action_warrior:
                professions = Professions.WARRIOR;
                in = true;
                break;
            case R.id.action_elementalist:
                professions = Professions.ELEMENTALIST;
                in = true;
                break;
            case R.id.action_guardian:
                professions = Professions.GUARDIAN;
                in = true;
                break;
            case R.id.action_mesmer:
                professions = Professions.MESMER;
                in = true;
                break;
            case R.id.action_necro:
                professions = Professions.NECROMANCER;
                in = true;
                break;
            case R.id.action_revenant:
                professions = Professions.REVENANT;
                in = true;
                break;
            case R.id.action_thief:
                professions = Professions.THIEF;
                in = true;
                break;
            case R.id.action_ranger:
                professions = Professions.RANGER;
                in = true;
                break;

        }

        setTitle(professions.toString().toLowerCase());
        HashMap<String, Specialization> spe = specializationManager.getSpe().get(professions);
        specializations = new ArrayList<>(spe.values());
        if (in) {
            changeVisibility(View.INVISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);


    }

    public void changeSpe(View view) {
        switch (view.getId()) {
            case R.id.speChange1:
                dialogSpeChoice.init(this, specializations, this, specialization1);
                dialogSpeChoice.show(getFragmentManager(), "dialogSpeChoice");
                break;
            case R.id.speChange2:
                dialogSpeChoice.init(this, specializations, this, specialization2);
                dialogSpeChoice.show(getFragmentManager(), "dialogSpeChoice");
                break;
            case R.id.speChange3:
                dialogSpeChoice.init(this, specializations, this, specialization3);
                dialogSpeChoice.show(getFragmentManager(), "dialogSpeChoice");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.builder_activity_menu, menu);
        return true;
    }


    @Override
    public void notifyUpdate(Object... o) {
    }

    @Override
    public void cancel() {
    }


}
