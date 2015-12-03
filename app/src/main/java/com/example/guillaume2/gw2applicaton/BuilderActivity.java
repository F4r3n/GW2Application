package com.example.guillaume2.gw2applicaton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    }

    public void changeSpe(View view) {
        switch (view.getId()) {
            case R.id.speChange1:
                dialogSpeChoice.init(this, specializations, this, specialization1);
                dialogSpeChoice.show(getFragmentManager(), "dialogSpeChoice");
                System.out.println("Choice 1");
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

    private void sortSpe() {

    }

    @Override
    public void notifyUpdate(Object... o) {

    }

    @Override
    public void cancel() {

    }
}
