package org.adaptlab.chpir.android.survey.questionfragments;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import org.adaptlab.chpir.android.survey.models.Option;
import org.adaptlab.chpir.android.survey.QuestionFragment;

import static org.adaptlab.chpir.android.survey.FormatUtils.removeNonNumericCharacters;

public class SelectOneQuestionFragment extends QuestionFragment {
    private RadioGroup mRadioGroup;
    private int mResponseIndex;

    // This is used to add additional UI components in subclasses.
    protected void beforeAddViewHook(ViewGroup questionComponent) {
    }

    protected RadioGroup getRadioGroup() {
        return mRadioGroup;
    }

    @Override
    protected void createQuestionComponent(ViewGroup questionComponent) {
        mRadioGroup = new RadioGroup(getActivity());
        for (Option option : getOptions()) {
            int optionId = getOptions().indexOf(option);
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setText(option.getText());
            radioButton.setId(optionId);
            radioButton.setTypeface(getInstrument().getTypeFace(getActivity().getApplicationContext()));
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
            mRadioGroup.addView(radioButton, optionId);
        }
        
        getRadioGroup().setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setResponseIndex(checkedId);
            }
        });
        questionComponent.addView(mRadioGroup);
        beforeAddViewHook(questionComponent);
    }

    @Override
    protected String serialize() {
        return String.valueOf(mResponseIndex);
    }

    @Override
    protected void deserialize(String responseText) {
        if (responseText.equals("")) {
        	int checked = getRadioGroup().getCheckedRadioButtonId();
        	if (checked > -1)
        		((RadioButton) getRadioGroup().getChildAt(checked)).setChecked(false);
        } else {
            ((RadioButton) getRadioGroup().getChildAt(Integer.parseInt(removeNonNumericCharacters(responseText)))).setChecked(true);
        }
    }
    
    protected void setResponseIndex(int index) {
        mResponseIndex = index;
        setResponseText();
    }
  
}
