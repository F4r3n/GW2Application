package com.example.guillaume2.gw2applicaton.Builder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.guillaume2.gw2applicaton.CallerBack;
import com.example.guillaume2.gw2applicaton.Collection;
import com.example.guillaume2.gw2applicaton.Professions;
import com.example.guillaume2.gw2applicaton.R;
import com.example.guillaume2.gw2applicaton.SpecializationInterface;
import com.example.guillaume2.gw2applicaton.Tool.FileManagerTool;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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

    private SpecializationInterface specializationInterface1;
    private SpecializationInterface specializationInterface2;
    private SpecializationInterface specializationInterface3;

    private android.support.v7.app.ActionBar actionBar;
    private String pathFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GW2App/builder/save/";
    private String nameSave;
    private BuilderSave builderSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);

        dialogSpeChoice = new DialogSpeChoice();
        Collection c = (Collection) getApplication();
        specializationManager = c.specializationManager;
        professions = professions.WARRIOR;
        HashMap<String, Specialization> spe = specializationManager.getSpe().get(professions);
        specializations = new ArrayList<>(spe.values());

        setContentView(R.layout.activity_builder);
        specialization1 = (FrameLayout) findViewById(R.id.spe1);
        specialization2 = (FrameLayout) findViewById(R.id.spe2);
        specialization3 = (FrameLayout) findViewById(R.id.spe3);
        changeVisibility(View.INVISIBLE);
        builderSave = new BuilderSave();

        specializationInterface1 = new SpecializationInterface(this, specialization1, builderSave, 0);
        specializationInterface2 = new SpecializationInterface(this, specialization2, builderSave, 1);
        specializationInterface3 = new SpecializationInterface(this, specialization3, builderSave, 2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            actionBar = getSupportActionBar();
            actionBar.setIcon(getIdIcon(professions));
            setTitle(professions.toString().toLowerCase());

        }
        builderSave.professions = professions;

    }

    public void readData(String name) {
        Gson gson = new Gson();

        File file = new File(pathFolder + name);
        if (!file.exists()) {
            System.out.println("File does not exist");

            return;
        } else {
            System.out.println("File does exist");
        }


        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            builderSave = gson.fromJson(br, BuilderSave.class);
            br.close();
        } catch (IOException e) {
        }

    }

    private void saveFile() {
        File dir = new File(pathFolder);
        dir.mkdirs();
        File file = new File(dir, professions.toString() + "_" + nameSave);

        try {

            FileOutputStream outputStream = new FileOutputStream(file);

            Gson gson = new Gson();
            String json = gson.toJson(builderSave);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<String> getFiles() {
        List<String> s = new ArrayList<>();
        File folder = new File(pathFolder);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                s.add(listOfFiles[i].getName());
            }
        }
        return s;
    }

    private void loadData() {
        professions = builderSave.professions;
        HashMap<String, Specialization> spe = specializationManager.getSpe().get(builderSave.professions);
        specializations = new ArrayList<>(spe.values());
        loadDataFrameLayout(specialization1, builderSave.spe[0], specializations);
        specializationInterface1.specialization = specializations.get(builderSave.spe[0]);
        specializationInterface1.init(builderSave.posTrait[0],builderSave.posTrait[1],builderSave.posTrait[2]);

        loadDataFrameLayout(specialization2, builderSave.spe[1], specializations);
        specializationInterface2.specialization = specializations.get(builderSave.spe[1]);
        specializationInterface2.init(builderSave.posTrait[3],builderSave.posTrait[4],builderSave.posTrait[5]);

        loadDataFrameLayout(specialization3, builderSave.spe[2], specializations);
        specializationInterface3.specialization = specializations.get(builderSave.spe[2]);
        specializationInterface3.init(builderSave.posTrait[6],builderSave.posTrait[7],builderSave.posTrait[8]);
    }

    private void loadDataFrameLayout(FrameLayout frameLayout, int position, List<Specialization> specializations) {
        ImageView imageView = (ImageView) frameLayout.findViewById(R.id.background);
        frameLayout.setVisibility(View.VISIBLE);
        Bitmap b = FileManagerTool.getImage(
                specializations.get(position).specializationData.backgroundImage.iconPath);
        if (!specializations.get(position).specializationData.elite) {

            System.out.println(FileManagerTool.convertDpToPixels(this, 1500));
            b = Bitmap.createBitmap(b, 0, 128, 800, 128);
            FileManagerTool.scaleImage(this, imageView, b, 1800, 900);
        } else {
            b = Bitmap.createBitmap(b, 0, 40, 948, 216);
            FileManagerTool.scaleImage(this, imageView, b, 1900, 256);
        }


        List<ImageButton> buttonsMinor = new ArrayList<>();
        buttonsMinor.add((ImageButton) frameLayout.findViewById(R.id.t10));
        buttonsMinor.add((ImageButton) frameLayout.findViewById(R.id.t20));
        buttonsMinor.add((ImageButton) frameLayout.findViewById(R.id.t30));

        for (int i = 0; i < buttonsMinor.size(); ++i) {
            Bitmap bb = FileManagerTool.getImage(
                    specializations.get(position).specializationData.minorTraits.get(i).iconImage.iconPath);
            buttonsMinor.get(i).setImageBitmap(FileManagerTool.makeTransparent(bb, 255));

        }

        List<ImageButton> buttonsMajor = new ArrayList<>();
        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t11));
        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t12));
        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t13));

        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t21));
        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t22));
        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t23));

        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t31));
        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t32));
        buttonsMajor.add((ImageButton) frameLayout.findViewById(R.id.t33));

        for (int i = 0; i < buttonsMajor.size(); ++i) {
            Bitmap bb = FileManagerTool.getImage(
                    specializations.get(position).specializationData.majorTraits.get(i).iconImage.iconPath);
            buttonsMajor.get(i).setImageBitmap(FileManagerTool.makeTransparent(bb, 255));

        }
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
            case R.id.action_save:
                saveDialog();
                break;
            case R.id.action_load_folder:
                DialogLoadSpe dialogLoadSpe = new DialogLoadSpe();
                dialogLoadSpe.init(this, getFiles(), this);
                dialogLoadSpe.show(getFragmentManager(), "dialogLoadFile");
                break;
            default:
                in = false;
                break;

        }
        if (in) {
            builderSave.professions = professions;
            setTitle(professions.toString().toLowerCase());
            HashMap<String, Specialization> spe = specializationManager.getSpe().get(professions);
            specializations = new ArrayList<>(spe.values());

            changeVisibility(View.INVISIBLE);
            if (actionBar != null) {
                actionBar.setIcon(getIdIcon(professions));
            } else System.out.println("Action bar null");
            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    private void saveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name of the save");

// Set up the input
        final EditText input = new EditText(this);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameSave = input.getText().toString();
                saveFile();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private int getIdIcon(Professions professions) {
        switch (professions) {
            case ELEMENTALIST:
                return R.drawable.ic_elementalist;
            case ENGINEER:
                return R.drawable.ic_engineer;
            case GUARDIAN:
                return R.drawable.ic_guardian;
            case MESMER:
                return R.drawable.ic_mesmer;
            case NECROMANCER:
                return R.drawable.ic_necro;
            case RANGER:
                return R.drawable.ic_ranger;
            case REVENANT:
                return R.drawable.ic_revenant;
            case THIEF:
                return R.drawable.ic_thief;
            case WARRIOR:
                return R.drawable.ic_warrior;
            default:
                return 0;
        }
    }

    public void changeSpe(View view) {
        switch (view.getId()) {
            case R.id.speChange1:
                dialogSpeChoice.init(this, specializations, this, specialization1, 1);
                dialogSpeChoice.show(getFragmentManager(), "dialogSpeChoice");
                break;
            case R.id.speChange2:
                dialogSpeChoice.init(this, specializations, this, specialization2, 2);
                dialogSpeChoice.show(getFragmentManager(), "dialogSpeChoice");
                break;
            case R.id.speChange3:
                dialogSpeChoice.init(this, specializations, this, specialization3, 3);
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

    public void notifySpeChoice(int id, Specialization specialization, int position) {
        builderSave.spe[id - 1] = position;
        if (id == 1) {
            specializationInterface1.specialization = specialization;
            specializationInterface1.init();
        }
        if (id == 2) {
            specializationInterface2.specialization = specialization;
            specializationInterface2.init();

        }
        if (id == 3) {
            specializationInterface3.specialization = specialization;
            specializationInterface3.init();
        }
    }

    public void fileToLoad(String name) {
        System.out.println(name);
        readData(name);
        loadData();
    }

    @Override
    public void cancel() {
    }


}
