package org.galaxyproject.sampletracker.ui.picker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.common.collect.Lists;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;

import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

/**
 * @author Pavel Sveda <pavel.sveda@cleverlance.com>
 */
@ContentView(R.layout.act_picker_state)
public final class StatePickerActivity extends BaseActivity implements OnItemClickListener {

    private static final String EXTRA_CURRENT_STATE = "current_state";

    public static Intent showIntent(String currentState) {
        Intent intent = new Intent(GalaxyApplication.get(), StatePickerActivity.class);
        if (!TextUtils.isEmpty(currentState)) {
            intent.putExtra(EXTRA_CURRENT_STATE, currentState);
        }
        return intent;
    }

    @InjectView(android.R.id.list) private ListView mList;
    @InjectExtra(value = EXTRA_CURRENT_STATE, optional = true) private String mCurrentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();

        // Initialize list view
        int valuesResource = R.array.specimen_state_values;
        mList.setAdapter(ArrayAdapter.createFromResource(this, valuesResource, R.layout.list_item));
        mList.setOnItemClickListener(this);

        // Select current state if set
        int currentIndex = Lists.newArrayList(getResources().getStringArray(valuesResource)).indexOf(mCurrentState);
        if (currentIndex >= 0) {
            mList.setItemChecked(currentIndex, true);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
