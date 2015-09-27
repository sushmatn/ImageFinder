package com.instagramclient.sushmanayak.gridimagesearch.fragments;

import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.instagramclient.sushmanayak.gridimagesearch.R;
import com.instagramclient.sushmanayak.gridimagesearch.helpers.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterSettingsFragment extends DialogFragment {

    Spinner spSize;
    Spinner spType;
    Spinner spColor;
    EditText etSite;
    Button btnOK;

    public interface SettingsChangedListener{
        public void onSettingsChanged();
    }

    public FilterSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter_settings, container, false);
        getDialog().setTitle(getResources().getString(R.string.filterSettings));

        setupViews(view);
        return view;
    }

    private void setupViews(View view) {
        spSize = (Spinner) view.findViewById(R.id.spSize);
        setSpinnerToValue(spSize, Helper.IMG_SIZE);

        spType = (Spinner) view.findViewById(R.id.spType);
        setSpinnerToValue(spType, Helper.IMG_TYPE);

        spColor = (Spinner) view.findViewById(R.id.spColor);
        setSpinnerToValue(spColor, Helper.IMG_COLOR);

        etSite = (EditText) view.findViewById(R.id.etSite);
        String prefValue = Helper.GetPreference(getActivity(), Helper.IMG_SITE, null);
        if (prefValue != null)
            etSite.setText(prefValue);

        btnOK = (Button) view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSettings();
                ((SettingsChangedListener)getActivity()).onSettingsChanged();
                dismiss();
            }
        });
    }

    private void SaveSettings() {
        Helper.SetPreference(getActivity(), Helper.IMG_SIZE, spSize.getSelectedItem().toString());
        Helper.SetPreference(getActivity(), Helper.IMG_TYPE, spType.getSelectedItem().toString());
        Helper.SetPreference(getActivity(), Helper.IMG_COLOR, spColor.getSelectedItem().toString());
        Helper.SetPreference(getActivity(), Helper.IMG_SITE, etSite.getText().toString());
    }

    public void setSpinnerToValue(Spinner spinner, String preferenceKey) {
        String prefValue = Helper.GetPreference(getActivity(), preferenceKey, null);
        if (prefValue != null) {
            int index = 0;
            SpinnerAdapter adapter = spinner.getAdapter();
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).toString().equals(prefValue)) {
                    index = i;
                    break; // terminate loop
                }
            }
            spinner.setSelection(index);
        }
    }
}
