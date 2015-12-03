package com.example.guillaume2.gw2applicaton.Builder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.guillaume2.gw2applicaton.R;

/**
 * Created by guillaume2 on 25/11/15.
 */
public class DialogSpeMean extends DialogFragment implements View.OnClickListener {

    private CHOICE_DL_SPECIALIZATION choice = CHOICE_DL_SPECIALIZATION.NO;

    public enum CHOICE_DL_SPECIALIZATION {
        YES,
        NO,
        NODATA
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dlRadioButton:
                choice = CHOICE_DL_SPECIALIZATION.YES;
                break;
            case R.id.readRadioButton:
                choice = CHOICE_DL_SPECIALIZATION.NO;
                break;
            default:
                break;
        }
    }


    public interface DialogListener {
        void onChoiceSpeMean(DialogFragment dialog, CHOICE_DL_SPECIALIZATION choice);
    }


    private DialogListener dialogListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            dialogListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DialogListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        String title = args.getString("title");
        String message = args.getString("message");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_fragment_choice_specialization, null))
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        dialogListener.onChoiceSpeMean(DialogSpeMean.this, CHOICE_DL_SPECIALIZATION.NODATA);
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        dialogListener.onChoiceSpeMean(DialogSpeMean.this, choice);

                    }
                });

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                ((AlertDialog) dialog).findViewById(R.id.dlRadioButton).setOnClickListener(DialogSpeMean.this);
                ((AlertDialog) dialog).findViewById(R.id.readRadioButton).setOnClickListener(DialogSpeMean.this);

            }
        });
        return dialog;

    }


}
