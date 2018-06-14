package fifthelement.theelement.presentation.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import fifthelement.theelement.presentation.MainActivity;


public class AddMusicActivity extends AppCompatActivity {

    private static final int CHOOSE_FILE_REQUESTCODE = 8777;
    private static final int PICKFILE_RESULT_CODE = 8778;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("*/*");
        try {
            startActivityForResult(fileintent, PICKFILE_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    String FilePath = data.getData().getPath();
                    System.out.println(FilePath);
                }
        }
        Intent intent = new Intent(AddMusicActivity.this, MainActivity.class);
        AddMusicActivity.this.startActivity(intent);
    }
}
