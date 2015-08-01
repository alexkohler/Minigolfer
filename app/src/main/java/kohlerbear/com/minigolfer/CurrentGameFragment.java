package kohlerbear.com.minigolfer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private TableLayout m_table;

    private ArrayList<TableRow> m_holeRows = new ArrayList<>();



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
        m_table = (TableLayout) myFragmentView.findViewById(R.id.table);

        //Initialization of tableRows - holes 1 - 4
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole1Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole2Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole3Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole4Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole5Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole6Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole7Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole8Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole9Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole10Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole11Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole12Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole13Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole14Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole15Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole16Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole17Row));
        m_holeRows.add((TableRow) myFragmentView.findViewById(R.id.hole18Row));


        addRow();
        //removeRow(1);
//        FloatingActionsMenu rightLabels = (FloatingActionsMenu) myFragmentView.findViewById(R.id.right_labels);
//        FloatingActionButton addedOnce = new FloatingActionButton(getActivity());
//        addedOnce.setTitle("Added once");
//        rightLabels.addButton(addedOnce);

        //Let view know we have an options menu we wish to add
        //setHasOptionsMenu(true);

        return myFragmentView;
    }


    public void tableCraziness() {

        for(int i = 0, j = m_table.getChildCount(); i < j; i++) {
            View view = m_table.getChildAt(i);
            if (view instanceof TableRow) {
                // then, you can remove the the row you want...
                TableRow currentRow = (TableRow) view;
                View v = currentRow.getVirtualChildAt(2);
                    if (v instanceof EditText) {
                       EditText columnChunk = ((EditText) v);
                       m_table.removeView(columnChunk);

                    }

                }
            }
        }

    //Basis (research) for remove row function, pretty much what dictates whether or not a row shows is its existence in titletablerow
    //TODO figure out an elegant way to add in EditTexts to each of the new columns - looks like it just needs to be added to the end of the row along with a vertical divider
/*
        List<String> rows = new ArrayList<String>();
        for (int i = 0; i < titleTableRow.getChildCount(); i++) {
            View currentView = titleTableRow.getChildAt(i);
            if (currentView instanceof EditText) {
                String possibleDeletionCandidate = ((EditText) currentView).getText().toString();
                rows.add(possibleDeletionCandidate);
            }

        }
 */
    public void removeRow(int childNumber) {

        //EditText row1 = (EditText) m_table.findViewById(R.id.name2EditText); //this will be configurable via the name popup
        //Instead of passing in the row itself, you can probably get away with passing in the child number
        TableRow titleTableRow = (TableRow) m_table.findViewById(R.id.titleTableRow);
        titleTableRow.removeViewAt(childNumber);
        //TODO edittext holes 1-9
        for (TableRow row : m_holeRows) {
                row.removeViewAt(childNumber);
        }


        TableRow subTotalRow = (TableRow) m_table.findViewById(R.id.subtotalRow);
        subTotalRow.removeViewAt(childNumber);//removing name 2 --> removing child at 2 (bada boom)
        //TODO edittext holes 10-18

       // TableRow totalRow = (TableRow) m_table.findViewById(R.id.totalRow);
       // subTotalRow.removeViewAt(2);//removing name 2 --> removing child at 2 (bada boom)



        //TODO will also have to know to remove its subtotal/total row
        //TODO use
        //titleTableRow.removeView(row1);
        for (int i = 0; i < titleTableRow.getChildCount(); i++) {
            View currentView = titleTableRow.getChildAt(i);
                if (currentView instanceof EditText) {
                    Toast.makeText(getActivity(), "Node " + i, Toast.LENGTH_SHORT).show();
                }

        }


    }

    public void addRow() {
        TableRow titleTableRow = (TableRow) m_table.findViewById(R.id.titleTableRow);
        EditText newEditText = new EditText(getActivity(), null, R.attr.NameEntryStyle);
        newEditText.setText("Name Added");
       titleTableRow.addView(newEditText);

        final EditText golfEntry = new EditText(getActivity(), null, R.attr.GolfEntryStyle);
        golfEntry.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_border_two));
        //Layout parameters not respected, so we need to take care of them ourselves!
        final TableRow.LayoutParams lparams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.FILL_PARENT); // Width , height
        golfEntry.setLayoutParams(lparams);



        TableRow hole1Row = (TableRow) m_table.findViewById(R.id.hole1Row);//TODO this isn't quite right..
        //experiment - I don't think layout options are being respected... this is TRUE!
        //EditText layoutOptionsSource = (EditText) hole1Row.findViewById(R.id.player1Hole1EditText);
        //golfEntry.setLayoutParams(layoutOptionsSource.getLayoutParams());



        hole1Row.addView(golfEntry);


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
            //case R.id.action_settings:
            //    Toast.makeText(getActivity().getApplicationContext(), "stub", Toast.LENGTH_SHORT).show();
            //    return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
