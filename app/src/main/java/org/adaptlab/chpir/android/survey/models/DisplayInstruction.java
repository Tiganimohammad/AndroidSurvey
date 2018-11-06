package org.adaptlab.chpir.android.survey.models;

import android.util.Log;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.adaptlab.chpir.android.activerecordcloudsync.ReceiveModel;
import org.adaptlab.chpir.android.survey.BuildConfig;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "DisplayInstructions")
public class DisplayInstruction extends ReceiveModel {
    private static final String TAG = "DisplayInstruction";
    @Column(name = "RemoteId")
    private Long mRemoteId;
    @Column(name = "Position")
    private int mPosition;
    @Column(name = "RemoteDisplayId")
    private Long mRemoteDisplayId;
    @Column(name = "InstructionId")
    private Long mInstructionId;
    @Column(name = "Deleted")
    private boolean mDeleted;

    public DisplayInstruction() {
        super();
    }

    @Override
    public void createObjectFromJSON(JSONObject jsonObject) {
        if (BuildConfig.DEBUG) Log.i(TAG, "Creating DisplayInstruction: " + jsonObject);
        try {
            Long remoteId = jsonObject.getLong("id");
            DisplayInstruction displayInstruction = DisplayInstruction.findByRemoteId(remoteId);
            if (displayInstruction == null) {
                displayInstruction = new DisplayInstruction();
            }
            displayInstruction.setRemoteId(remoteId);
            displayInstruction.setPosition(jsonObject.optInt("position"));
            displayInstruction.setDisplayId(jsonObject.optLong("display_id"));
            displayInstruction.setInstructionId(jsonObject.optLong("instruction_id"));
            if (jsonObject.isNull("deleted_at")) {
                displayInstruction.setDeleted(false);
            } else {
                displayInstruction.setDeleted(true);
            }
            displayInstruction.save();
        } catch (JSONException je) {
            if (BuildConfig.DEBUG) Log.e(TAG, "Error parsing object json", je);
        }
    }

    public static DisplayInstruction findByRemoteId(Long id) {
        return new Select().from(DisplayInstruction.class).where("RemoteId = ?", id).executeSingle();
    }

    public int getPosition() {
        return mPosition;
    }

    public Long getRemoteId() {
        return mRemoteId;
    }

    public String getInstructions() {
        Instruction instruction = Instruction.findByRemoteId(mInstructionId);
        if (instruction == null) return null;
        return instruction.getText(getInstrument());
    }

    private Instrument getInstrument() {
        Display display = Display.findByRemoteId(mRemoteDisplayId);
        if (display == null) return null;
        return display.getInstrument();
    }

    private void setRemoteId(Long id) {
       mRemoteId = id;
    }

    private void setDeleted(boolean status) {
        mDeleted = status;
    }

    private void setPosition(int position) {
        mPosition = position;
    }

    private void setDisplayId(Long id) {
        mRemoteDisplayId = id;
    }

    private void setInstructionId(Long instructionId) {
        mInstructionId = instructionId;
    }

}
