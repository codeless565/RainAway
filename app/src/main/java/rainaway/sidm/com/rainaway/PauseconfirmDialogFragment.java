package rainaway.sidm.com.rainaway;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class PauseconfirmDialogFragment extends DialogFragment
{
    public static boolean isShown=false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // This is our duplicate guard
        isShown=true;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Confirm Pause?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //todo -trigger pause
                        // GameSystem.Instance.SetIsPaused
                        Game_Normal.Instance.setIsPaused(!Game_Normal.Instance.getIsPaused());
                        isShown=false;
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isShown=false;
                    }
                });
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        isShown=false;
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        isShown=false;
    }

}