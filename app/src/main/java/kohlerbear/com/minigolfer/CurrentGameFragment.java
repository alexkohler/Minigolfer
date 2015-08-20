package kohlerbear.com.minigolfer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonRectangle;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import kohlerbear.com.minigolfer.db.DBHelper;
import kohlerbear.com.minigolfer.db.Player;
import kohlerbear.com.minigolfer.db.PlayerDAO;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private TableLayout m_table;
    private View m_fragmentView;

    private ArrayList<TableRow> m_holeRows = new ArrayList<>();

    private ArrayList<EditText> m_playerEditTexts = new ArrayList<>();

    SQLiteDatabase m_db; //TODO add (reliable) means to close database
    DBHelper m_helper;
    PlayerDAO m_playerDAO; //TODO add (reliable) means to close database (DAO opens a connection too)


    public static Fragment newInstance(/*String param1, String param2*/) {
        return new CurrentGameFragment();
    }

    public CurrentGameFragment() {
        // Required empty public constructor
    }

    /* To solve your edit texts getting changed on orientation change.. You will need to do this with a list of edittexts that gets
    updated/initialized as you input your number of players/add additional players
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myFragmentView = inflater.inflate(R.layout.fragment_current_game, container, false);
        m_fragmentView = myFragmentView;
        m_table = (TableLayout) myFragmentView.findViewById(R.id.table);

        //Initialization of tableRows
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


        FloatingActionButton addPlayerButton = (FloatingActionButton) myFragmentView.findViewById(R.id.AddPlayerButton);
        addPlayerButton.setOnClickListener(AddPlayerPressed());

        com.gc.materialdesign.views.ButtonRectangle finishGameButton = (com.gc.materialdesign.views.ButtonRectangle) myFragmentView.findViewById(R.id.finishGameButton);
        finishGameButton.setOnClickListener(finishButtonPressed());

        //Let view know we have an options menu we wish to add
        setHasOptionsMenu(true);

//        SharedPreferences.Editor.clear().commit();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());//getActivity().getSharedPreferences("defaultUser", Context.MODE_PRIVATE);
        final String defaultUserKey = "defaultUser";
        String defaultUser = prefs.getString(defaultUserKey, null);


        m_helper = new DBHelper(getActivity());
        m_db = m_helper.getWritableDatabase();
        m_playerDAO = new PlayerDAO(getActivity());
        String count = "SELECT count(*) FROM " + DBHelper.TABLE_CURRENT_GAME;
        Cursor cursor = m_db.rawQuery(count, null);
        cursor.moveToFirst();
        int numberOfEntries = cursor.getInt(0);

        if (defaultUser != null && defaultUser.trim().length() != 0 )
        {
            // if we have zero database entries, then we can add our default user to the card
            if (numberOfEntries == 0) {
                showToast("We need to add our default user " + defaultUser + " (DB empty)");
                addRow(defaultUser);
                m_playerDAO.insertPlayer(defaultUser);
            }
            // otherwise we have more than zero entries, and we should use this info to populate our card
            else {
                List<Player> players = m_playerDAO.getAllPlayers();
                for (Player player : players) {
                    addRow(player.getPlayerName());

                    // loop through our 18 holes and fill in any fields that our database has track of
                    int holeScore = 0;
                    Method currentHoleMethod;
                    for (int i = 0; i < 18; i++) {
                        try {
                        currentHoleMethod = player.getClass().getMethod("getHole" + (i +1) + "score");
                            holeScore = (int) currentHoleMethod.invoke(player);
                        } catch (SecurityException e) {
                        } catch (NoSuchMethodException e) {
                        } catch (IllegalArgumentException e) {
                        } catch (IllegalAccessException e) {
                        } catch (InvocationTargetException e) {
                        }
                        if (holeScore != 0) {
                            //since we just added a row, we know this added row will be at the end of titleTableRow
                            TableRow titleTableRow = (TableRow) m_table.findViewById(R.id.titleTableRow);

                            EditText currentHoleEntry = (EditText) m_holeRows.get(i).getChildAt(titleTableRow.getChildCount() - 1); // taking care of hole 1, grabbing child at (current) end
                            currentHoleEntry.setText(String.valueOf(holeScore));
                        }
                    }
                }
            }
        }
        else { //if we don't have a user stored in the sharedpreferences, hop on doing that
            new MaterialDialog.Builder(getActivity())
                    .title("Please enter your name")
//                    .content(R.string.input_content)
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input("Name", "Name", new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            String defaultUserInput;
                            if (input.toString().trim().length() == 0) {
                                defaultUserInput = "Player 1";
                            } else {
                                defaultUserInput = input.toString();
                            }

                            //commit our change
                            prefs.edit().putString(defaultUserKey, defaultUserInput).apply();
                        }
                    }).show();
        }
        return myFragmentView;
    }

    public void removeRow(int childNumber) {

        // handling UI
        TableRow titleTableRow = (TableRow) m_table.findViewById(R.id.titleTableRow);
        String playerName = ((EditText) titleTableRow.getChildAt(childNumber)).getText().toString();
        titleTableRow.removeViewAt(childNumber);

        for (TableRow row : m_holeRows) {
            row.removeViewAt(childNumber);
        }

        TableRow subTotalRow = (TableRow) m_table.findViewById(R.id.subtotalRow);
        subTotalRow.removeViewAt(childNumber);//removing name 2 --> removing child at 2 (bada boom)

        TableRow totalRow = (TableRow) m_table.findViewById(R.id.totalRow);
        totalRow.removeViewAt(childNumber);//removing name 2 --> removing child at 2 (bada boom)

        // handling db
        m_playerDAO.deletePlayer(playerName);
    }

    public void addRow(String playerName) {
        //******Managing UI******

        //Add name to TitleTableRow (and our floating dummy row)
        TableRow titleTableRow = (TableRow) m_table.findViewById(R.id.titleTableRow);
        TableRow floatingTitleRow = (TableRow) m_fragmentView.findViewById(R.id.floatingTitleRow);

        EditText newEditText = new EditText(getActivity());//new EditText(getActivity(), null, R.attr.NameEntryStyle);
        newEditText.setText(playerName);
        newEditText.setPadding(5, 10, 5, 10);
        newEditText.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_border_green));
        newEditText.setEllipsize(TextUtils.TruncateAt.START);
        newEditText.setMaxLines(1);
        newEditText.setGravity(Gravity.CENTER);
        newEditText.setTypeface(newEditText.getTypeface(), Typeface.BOLD); // preserve fond
        newEditText.setTextColor(Color.WHITE);
        newEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        newEditText.setLayoutParams(m_table.findViewById(R.id.holeTitleText).getLayoutParams());
        newEditText.setMinimumWidth(50);
//        newEditText.setOnFocusChangeListener(NameEditTextChanged(newEditText));
        titleTableRow.addView(newEditText);


        EditText floatingEditText = new EditText(getActivity());//new EditText(getActivity(), null, R.attr.NameEntryStyle);
        floatingEditText.setText(playerName);
        floatingEditText.setPadding(5, 10, 5, 10);
        floatingEditText.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_border_green));
        floatingEditText.setEllipsize(TextUtils.TruncateAt.START);
        floatingEditText.setMaxLines(1);
        floatingEditText.setGravity(Gravity.CENTER);
        floatingEditText.setTypeface(floatingEditText.getTypeface(), Typeface.BOLD); // preserve fond
        floatingEditText.setTextColor(Color.WHITE);
        floatingEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        floatingEditText.setLayoutParams(m_fragmentView.findViewById(R.id.dummyHoleTitleText).getLayoutParams());
        floatingEditText.setMinimumWidth(50);


        floatingTitleRow.addView(floatingEditText);

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
            golfEntry.addTextChangedListener(HoleEditTextChanged(golfEntry, titleTableRow.getChildCount() - 1, i + 1)); //will always be added to end

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.current_game_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_remove_player:
                removePlayerPressed();
                return true;
            case R.id.action_discard_game:
                discardGamePressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                                if (newPlayer.length() == 0) {
                                    newPlayer = "New player";
                                }
                                // ensure we don't already have a player with this name
                                if (!m_playerDAO.playerExists(newPlayer)) {
                                    addRow(newPlayer);
                                    m_playerDAO.insertPlayer(newPlayer);
                                } else {
                                    showToast("Player with name " + newPlayer + " already exists!");
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
            }
        };
    }

    /*removePlayerPressed prompts the user with a spinner populated with the current players on the card, with
    the option to remove a selected player (this WILL allow user to remove all players
    */
    void removePlayerPressed() {
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

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
    }

    void discardGamePressed()
    {
        new MaterialDialog.Builder(getActivity())
                .title("Are you sure you wish to discard this game?")
                .positiveText("Yes")
                .negativeText("No")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        //remove all current entries
                        TableRow titleTableRow = (TableRow) m_table.findViewById(R.id.titleTableRow);
                        int childCount = titleTableRow.getChildCount();
                        for (int i = 1; i < childCount; i++) {
                            removeRow(1); // we will simply to continue to remove at 1, analogous to "pressing the delete key"
                        }

                        //despite deleting our players in the removeRow method, we still will clear our database
                        m_db.execSQL("delete from " + DBHelper.TABLE_CURRENT_GAME);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.cancel();
                    }
                }).show();
    }

    View.OnClickListener finishButtonPressed() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .title("Are you sure you're finished?")
                        .positiveText("Yes")
                        .negativeText("No")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                //time to add our game to PREVIOUS_GAMES11

                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
            }
        };
    }

    TextWatcher HoleEditTextChanged(final EditText currentEditText, final int childNum, final int holeNum) {
        return new TextWatcher() {

            public void afterTextChanged(Editable s) {
                // if we aren't dealing with a deletion (that is, an empty string)
                if (!s.toString().equals("")) {
                    // you can call or do what you want with your EditText here
                    TableRow subTotalRow = (TableRow) m_table.findViewById(R.id.subtotalRow);
                    TableRow totalRow = (TableRow) m_table.findViewById(R.id.totalRow);
//                    m_holeRows
                    int subTotal = 0;
                    int total = 0;
                    //TODO an optimization would be to not iterate (you have the modified hole number)
                    for (int i = 0; i < 18; i++) {
                        TableRow row = m_holeRows.get(i);
                        String currentFieldVal = ((EditText) row.getChildAt(childNum)).getText().toString();
                        if (currentFieldVal.length() > 0) {
                            // manage total + subtotal
                            int integerFieldVal = Integer.valueOf(currentFieldVal);
                            if (i < 9) {
                                subTotal += integerFieldVal;
                            }
                            total += integerFieldVal;
                        }
                    }
                    // manage db
                    TableRow titleTableRow = (TableRow) m_table.findViewById(R.id.titleTableRow);
                    String player = ((EditText) titleTableRow.getChildAt(childNum)).getText().toString();
//                    showToast(player + " modified his shit at hole " + holeNum + " to " + s.toString());
                    m_playerDAO.updateHoleValue(player, holeNum, Integer.valueOf(s.toString()));

                    ((TextView) subTotalRow.getChildAt(childNum)).setText(String.valueOf(subTotal));
                    ((TextView) totalRow.getChildAt(childNum)).setText(String.valueOf(total));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();

        // grab our players
        List<Player> players = m_playerDAO.getAllPlayers();

        // ensure no names have changed (comparing against titleTableRow)
        TableRow titleTableRow = (TableRow) m_fragmentView.findViewById(R.id.floatingTitleRow);
        for (int i = 1; i < titleTableRow.getChildCount(); i++) {
            //title table row is our source of truth
            String currentPlayerName = ((EditText) titleTableRow.getChildAt(i)).getText().toString();
            Player currentPlayer = players.get(i - 1);
            if (!currentPlayerName.equals(currentPlayer.getPlayerName())) {
                m_playerDAO.updatePlayerName(currentPlayer.getPlayerId(), currentPlayerName);
            }
        }
    }

    private Toast mToast;

    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        mToast.show();


    }
}