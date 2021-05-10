package njit.oa.nosnooze.alarm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class QuestionDialog extends AppCompatDialogFragment {
    private QuestionDialogListener listener;
    private String myText = " ";
    private String difficulty;

    public QuestionDialog(String diffy){
        difficulty = diffy;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
        String[] questionArr;
        if(difficulty.equals("medium")){
            questionArr = generateMediumQuestion();
        }
        else if(difficulty.equals("easy")){
            questionArr = generateEasyQuestion();
        }
        else{
            questionArr = generateHardQuestion();
        }
        mydialog.setTitle(questionArr[2] + questionArr[1]);
        final EditText answer = new EditText(getActivity());
        answer.setInputType(InputType.TYPE_CLASS_NUMBER);
        mydialog.setView(answer);
        mydialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myText=answer.getText().toString();
                if(myText.equals(questionArr[0])){
                    Toast.makeText(getContext(), "Correct!", Toast.LENGTH_LONG).show();
                    upcomingAlarm.numCorrect += 1;
                    listener.onOkClicked();
                }
                else{
                    Toast.makeText(getContext(), "Incorrect! The right answer is " + questionArr[0], Toast.LENGTH_LONG).show();
                    listener.onOkClicked();
                }

            }
        });
        return mydialog.create();
    }

    public interface QuestionDialogListener{
        void onOkClicked();

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        listener = (QuestionDialogListener) context;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public String[] generateEasyQuestion(){
        String[] returnArr = new String[3];
        returnArr[2] = "Easy Question ";
        int answer = -1;
        int A = getRandomNumber(1, 100);
        int B = getRandomNumber(1, 100);
        int addOrSubtract = getRandomNumber(1, 3);
        if(addOrSubtract==1){
            answer = A + B;
            returnArr[0] = Integer.toString(answer);
            returnArr[1] = A + " + " + B;
            return returnArr;
        }
        else{
            if(A>B){
                answer = A - B;
                returnArr[0] = Integer.toString(answer);
                returnArr[1] = A + " - " + B;
                return returnArr;
            }
            else{
                answer = B - A;
                returnArr[0] = Integer.toString(answer);
                returnArr[1] = B + " - " + A;
                return returnArr;
            }
        }
    }

    public String[] generateMediumQuestion(){
        String[] returnArr = new String[3];
        returnArr[2] = "Medium Question ";
        int answer = -1;
        int A = getRandomNumber(5, 15);
        int B = getRandomNumber(5, 15);
        answer = A * B;
        returnArr[0] = Integer.toString(answer);
        returnArr[1] = A + " * " + B;
        return returnArr;
    }

    public String[] generateHardQuestion(){
        String[] returnArr = new String[3];
        returnArr[2] = "Hard Question ";
        int answer = -1;
        int A = getRandomNumber(35, 100);
        int B = getRandomNumber(5, 15);
        answer = A % B;
        returnArr[0] = Integer.toString(answer);
        returnArr[1] = "What is the remainder of " + A + " divided by " + B;
        return returnArr;
    }
}
