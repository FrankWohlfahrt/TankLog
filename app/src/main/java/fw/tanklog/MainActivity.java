package fw.tanklog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button m_BtnAdd = null;
    private TankLogDB m_DB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_BtnAdd = (Button)findViewById(R.id.buttonAdd);
        m_BtnAdd.setOnClickListener(this);
        m_DB = new TankLogDB(this);
        updateGridFromDatabase();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateGridFromDatabase() {
        // get Data from SQLite
        ArrayList<String> list = m_DB.getAllTableEntries();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        final GridView grid = (GridView)findViewById(R.id.gridView);
        grid.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        inputMileage();
    }

    private void inputMileage() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Kilometerstand");
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String mileage = input.getText().toString();
                inputFuel(mileage);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    private void inputFuel(String mileage) {
        final String mileageKM = mileage;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Liter");
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String fuel = input.getText().toString();
                Format formatter = new SimpleDateFormat("dd.MM.yyyy");
                String date = formatter.format(GregorianCalendar.getInstance().getTime());
                m_DB.insertData(new TankLogTableEntry(date, mileageKM, fuel));
                updateGridFromDatabase();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }
}
