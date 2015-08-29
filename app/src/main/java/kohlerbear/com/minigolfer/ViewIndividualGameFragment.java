package kohlerbear.com.minigolfer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import kohlerbear.com.minigolfer.db.Game;
import kohlerbear.com.minigolfer.db.Player;


public class ViewIndividualGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View m_fragmentView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static Game m_currentGame;
    private TableLayout m_table;
    private ArrayList<TableRow> m_holeRows = new ArrayList<>();

    // TODO: Rename and change types and number of parameters
    public static ViewIndividualGameFragment newInstance(Game game) {
        ViewIndividualGameFragment fragment = new ViewIndividualGameFragment();
        m_currentGame = game;
        return fragment;
    }

    public ViewIndividualGameFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        m_fragmentView = inflater.inflate(R.layout.fragment_view_individual_game, container, false);

        m_table = (TableLayout) m_fragmentView.findViewById(R.id.table);

        //Initialization of hole rows
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole1Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole2Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole3Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole4Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole5Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole6Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole7Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole8Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole9Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole10Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole11Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole12Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole13Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole14Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole15Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole16Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole17Row));
        m_holeRows.add((TableRow) m_fragmentView.findViewById(R.id.hole18Row));

        for (Player player : m_currentGame.getPlayers())
        {
            addRow(player);

        }

        return m_fragmentView;
    }


    //TODO add row logic will be very similar to current_game except when adding rows there will be no edit texts, only textviews.

    public void addRow(Player player) {
        //******Managing UI******

        //Add name to TitleTableRow (and our floating dummy row)
        TableRow titleTableRow = (TableRow) m_table.findViewById(R.id.titleTableRow);
        TableRow floatingTitleRow = (TableRow) m_fragmentView.findViewById(R.id.floatingTitleRow);

        TextView newEditText = new TextView(getActivity());//new EditText(getActivity(), null, R.attr.NameEntryStyle);
        newEditText.setText(player.getPlayerName());
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


        TextView floatingEditText = new TextView(getActivity());//new EditText(getActivity(), null, R.attr.NameEntryStyle);
        floatingEditText.setText(player.getPlayerName());
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
        int holeScore = 0;
        int subtotal = 0;
        int total = 0;
        Method currentHoleMethod;
        for (int i = 0; i < 18; i++) {
            TableRow row = m_holeRows.get(i);
            //Add our golf entry to  each of the 18 holes
            final TextView golfEntry = new TextView(getActivity(), null, R.attr.GolfEntryStyle);

            //set hole score with a little bit of reflection
            try {
                currentHoleMethod = player.getClass().getMethod("getHole" + (i +1) + "score");
                holeScore = (int) currentHoleMethod.invoke(player);
            } catch (SecurityException e) {
            } catch (NoSuchMethodException e) {
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }

            golfEntry.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_border_two));
            //Layout parameters not respected, so we need to take care of them ourselves!
            golfEntry.setLayoutParams(lparams);
            if (holeScore != 0) {
                golfEntry.setText(String.valueOf(holeScore));
            }
            if (i < 9)
                subtotal += holeScore;
            total += holeScore;


            row.addView(golfEntry);
        }
        showToast("First nine " + subtotal + "... tot" + total);

        //Add a textview for subtotal
        TableRow subTotalRow = (TableRow) m_table.findViewById(R.id.subtotalRow);
        final TextView subtotalText = new EditText(getActivity(), null, R.attr.TotalTextStyle);
        subtotalText.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_border_two));
        //Layout parameters not respected, so we need to take care of them ourselves!
        subtotalText.setLayoutParams(lparams);
        subtotalText.setText(String.valueOf(subtotal));
        subTotalRow.addView(subtotalText);

        //Add a textview for total
        TableRow totalRow = (TableRow) m_table.findViewById(R.id.totalRow);
        final TextView totalText = new EditText(getActivity(), null, R.attr.TotalTextStyle);
        totalText.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_border_two));
        //Layout parameters not respected, so we need to take care of them ourselves!
        totalText.setLayoutParams(lparams);
        totalText.setText(String.valueOf(total));
        totalRow.addView(totalText);

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
