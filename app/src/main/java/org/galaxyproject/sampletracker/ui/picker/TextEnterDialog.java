package org.galaxyproject.sampletracker.ui.picker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.util.DeviceUtils;

/**
 * Dialog for getting text inputs of defined format.
 *
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class TextEnterDialog extends DialogFragment implements DialogInterface.OnClickListener, TextView.OnEditorActionListener {

    public interface OnTextEnteredListener {
        public void onTextEntered(int requestCode, CharSequence enteredText);
    }

    public static final String TAG = "TextEnterDialog";

    private static final String ARG_TITLE = "TextEnterDialog_title";
    private static final String ARG_INPUT_TYPE = "TextEnterDialog_inputType";
    private static final String ARG_DEFAULT_VALUE = "TextEnterDialog_defaultValue";

    public static TextEnterDialog create(Fragment targetFragment, int requestCode, String title, int inputType, String defaultValue) {
        Preconditions.checkArgument(targetFragment instanceof OnTextEnteredListener);
        TextEnterDialog dialog = new TextEnterDialog();
        dialog.setTargetFragment(targetFragment, requestCode);
        Bundle args = new Bundle(3);
        args.putString(ARG_TITLE, title);
        args.putInt(ARG_INPUT_TYPE, inputType);
        args.putString(ARG_DEFAULT_VALUE, defaultValue);
        dialog.setArguments(args);
        return dialog;
    }

    private EditText mField;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString(ARG_TITLE));
        builder.setPositiveButton(R.string.glb_set, this);

        mField = new EditText(getActivity());
        mField.setInputType(getArguments().getInt(ARG_INPUT_TYPE));
        mField.setText(getArguments().getString(ARG_DEFAULT_VALUE));
        mField.setSelection(mField.length());
        mField.selectAll();
        mField.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mField.setOnEditorActionListener(this);
        builder.setView(mField);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        returnFieldDataAsResult();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (DeviceUtils.actionsEquals(EditorInfo.IME_ACTION_DONE, actionId)) {
            returnFieldDataAsResult();
            dismiss();
            return true;
        } else {
            return false;
        }
    }

    private void returnFieldDataAsResult() {
        ((OnTextEnteredListener) getTargetFragment()).onTextEntered(getTargetRequestCode(), mField.getText());
    }
}
