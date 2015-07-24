package kohlerbear.com.minigolfer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TableLayout table;

    public static Fragment newInstance(/*String param1, String param2*/) {
        CurrentGameFragment fragment = new CurrentGameFragment();
        Bundle args = new Bundle();


        fragment.setArguments(args);
        return fragment;
    }

    public CurrentGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    //http://stackoverflow.com/questions/12420396/how-to-retain-edittext-data-on-orientation-change
    /* To solve your edit texts getting changed on orientation change.. You will need to do this with a list of edittexts that gets
    updated/initialized as you input your number of players/add additional players
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View myFragmentView = inflater.inflate(R.layout.fragment_current_game, container, false);
        table  = (TableLayout) myFragmentView.findViewById(R.id.table);
        removeRow();
//        FloatingActionsMenu rightLabels = (FloatingActionsMenu) myFragmentView.findViewById(R.id.right_labels);
//        FloatingActionButton addedOnce = new FloatingActionButton(getActivity());
//        addedOnce.setTitle("Added once");
//        rightLabels.addButton(addedOnce);

        //Let view know we have an options menu we wish to add
        //setHasOptionsMenu(true);

        return myFragmentView;
    }


    public void tableCraziness() {

        for(int i = 0, j = table.getChildCount(); i < j; i++) {
            View view = table.getChildAt(i);
            if (view instanceof TableRow) {
                // then, you can remove the the row you want...
                TableRow currentRow = (TableRow) view;
                View v = currentRow.getVirtualChildAt(2);
                    if (v instanceof EditText) {
                       EditText columnChunk = ((EditText) v);
                       table.removeView(columnChunk);

                    }

                }
            }
        }

    //Basis (research) for remove row function, pretty much what dictates whether or not a row shows is its existence in titletablerow
    //TODO figure out an elegant way to add in EditTexts to each of the new columns - looks like it just needs to be added to the end of the row along with a vertical divider

    public void removeRow() {
        TableRow titleTableRow = (TableRow) table.findViewById(R.id.titleTableRow);
        EditText row1 = (EditText) table.findViewById(R.id.name2EditText);
        titleTableRow.removeView(row1);




    }

    public void addRow() {
        TableRow titleTableRow = (TableRow) table.findViewById(R.id.titleTableRow);
        EditText newEditText = new EditText(getActivity());
        //titleTableRow.addView()//TODO figure out how to add custom components




    }


   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.current_game_menu, menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getActivity().getApplicationContext(), "stub", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
