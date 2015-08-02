package kohlerbear.com.minigolfer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

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

    private FloatingActionsMenu m_rightLabels;


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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("x", 3);
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
        if (savedInstanceState != null ) {
            if (savedInstanceState.containsKey("x")) {
                int x = savedInstanceState.getInt("x");
                Toast.makeText(getActivity(), x, Toast.LENGTH_SHORT).show();
            }
        }

        m_rightLabels = (FloatingActionsMenu) myFragmentView.findViewById(R.id.right_labels);

//        FloatingActionButton addedOnce = new FloatingActionButton(getActivity());
//        addedOnce.setTitle("Added once");
//        rightLabels.addButton(addedOnce);
        FloatingActionButton addPlayerButton = (FloatingActionButton) myFragmentView.findViewById(R.id.AddPlayerButton);
        addPlayerButton.setOnClickListener(AddPlayerPressed());

        FloatingActionButton removePlayerButton = (FloatingActionButton) myFragmentView.findViewById(R.id.RemovePlayerButton);
        removePlayerButton.setOnClickListener(RemovePlayerPressed());


        //Let view know we have an options menu we wish to add
        //setHasOptionsMenu(true);

        return myFragmentView;
    }

    public void removeRow(int childNumber) {

        //EditText row1 = (EditText) m_table.findViewById(R.id.name2EditText); //this will be configurable via the name popup
        //Instead of passing in the row itself, you can probably get away with passing in the child number
        TableRow titleTableRow = (TableRow) m_table.findViewById(R.id.titleTableRow);
        titleTableRow.removeViewAt(childNumber);
        for (TableRow row : m_holeRows) {
                row.removeViewAt(childNumber);
        }


        TableRow subTotalRow = (TableRow) m_table.findViewById(R.id.subtotalRow);
        subTotalRow.removeViewAt(childNumber);//removing name 2 --> removing child at 2 (bada boom)

        TableRow totalRow = (TableRow) m_table.findViewById(R.id.totalRow);
        totalRow.removeViewAt(childNumber);//removing name 2 --> removing child at 2 (bada boom)

    }

    public void addRow(String playerName) {

        //Add name to TitleTableRow
        TableRow titleTableRow = (TableRow) m_table.findViewById(R.id.titleTableRow);
        EditText newEditText = new EditText(getActivity(), null, R.attr.NameEntryStyle);
        newEditText.setText(playerName);
        titleTableRow.addView(newEditText);




        //Add EditTexts to all of our TableRows
        final TableRow.LayoutParams lparams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.FILL_PARENT); // Width , height
        for (int i = 0; i < 18; i++) {
            TableRow row = m_holeRows.get(i);
            //Add our golf entry to  each of the 18 holes
            final EditText golfEntry = new EditText(getActivity(), null, R.attr.GolfEntryStyle);
            golfEntry.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_border_two));
            //Layout parameters not respected, so we need to take care of them ourselves!
            golfEntry.setLayoutParams(lparams);

            //Add our listener to update our total
            golfEntry.addTextChangedListener(HoleEditTextChanged(golfEntry, titleTableRow.getChildCount() - 1)); //will always be added to end


            row.addView(golfEntry);
        }


        //Add a textview for subtotal
        TableRow subTotalRow = (TableRow) m_table.findViewById(R.id.subtotalRow);
        final TextView subtotalText = new EditText(getActivity(), null, R.attr.TotalTextStyle);
        subtotalText.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_border_two));
        //Layout parameters not respected, so we need to take care of them ourselves!
        subtotalText.setLayoutParams(lparams);
        subTotalRow.addView(subtotalText);


        //Add a textview for total
        TableRow totalRow = (TableRow) m_table.findViewById(R.id.totalRow);
        final TextView totalText = new EditText(getActivity(), null, R.attr.TotalTextStyle);
        totalText.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_border_two));
        //Layout parameters not respected, so we need to take care of them ourselves!
        totalText.setLayoutParams(lparams);
        totalRow.addView(totalText);


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



    //On Click Listeners
    //AddPlayerPressed - Prompts user with dialog to add a new player to the card.
    View.OnClickListener AddPlayerPressed() {
        return new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                //Create alert Dialog to get Name
                final EditText newPlayerEditText = new EditText(getActivity());
                newPlayerEditText.setHint("New Player Name");
                newPlayerEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)}); //Set max length to 20 characters
                newPlayerEditText.setMaxLines(1);//only a single lined name
                new AlertDialog.Builder(getActivity())
                        .setTitle("Add Player")
                        .setView(newPlayerEditText)
                        .setMessage("Enter new player name")
                        .setPositiveButton("Add Player", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String newPlayer = newPlayerEditText.getText().toString();
                                addRow(newPlayer);
                                m_rightLabels.collapse();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                m_rightLabels.collapse();
                            }
                        })
                        .show();
            }
        };
    }

    /*RemovePlayerPressed prompts the user with a spinner populated with the current players on the card, with
    the option to remove a selected player (this WILL allow user to remove all players
    */
    View.OnClickListener RemovePlayerPressed() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Populate our spinner
                //Create our spinner
                final Spinner spinner = new Spinner(getActivity());
                //Get the names we wish to be on our spinner

                ArrayList<String> spinnerArray = new ArrayList<>();
                TableRow titleTableRow = (TableRow) m_table.findViewById(R.id.titleTableRow);
                for (int i = 1; i < titleTableRow.getChildCount(); i++) {
                    EditText v = (EditText) titleTableRow.getChildAt(i);
                    spinnerArray.add(v.getText().toString());
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerArrayAdapter);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Remove Player")
                        .setMessage("Please select a player to remove")
                        .setView(spinner)
                        .setPositiveButton("Remove Player", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                removeRow(spinner.getSelectedItemPosition() + 1); //We don't want to be able to remove our holes column
                                m_rightLabels.collapse();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                m_rightLabels.collapse();
                            }
                        })
                        .show();
            }
        };
    }

        TextWatcher HoleEditTextChanged(final EditText currentEditText, final int childNum) {
            return new TextWatcher() {

                public void afterTextChanged(Editable s) {

                    // you can call or do what you want with your EditText here
                    TableRow subTotalRow = (TableRow) m_table.findViewById(R.id.subtotalRow);
                    TableRow totalRow = (TableRow) m_table.findViewById(R.id.totalRow);
//                    m_holeRows
                    int subTotal = 0; int total = 0;
                    for (int i = 0; i < 18; i++) {
                        TableRow row = m_holeRows.get(i);
                        String currentFieldVal = ((EditText) row.getChildAt(childNum)).getText().toString();
                        if (currentFieldVal.length() > 0) {
                            int integerFieldVal = Integer.valueOf(currentFieldVal);
                            if (i < 9) {
                                subTotal += integerFieldVal;
                            }
                            total += integerFieldVal;

                        }
                    }
                    ((TextView) subTotalRow.getChildAt(childNum)).setText(String.valueOf(subTotal));
                    ((TextView) totalRow.getChildAt(childNum)).setText(String.valueOf(total));

                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            };



        }


}
