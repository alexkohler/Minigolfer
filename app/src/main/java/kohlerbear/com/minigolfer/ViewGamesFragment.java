package kohlerbear.com.minigolfer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import kohlerbear.com.minigolfer.listview.SwipeDismissListViewTouchListener;
import kohlerbear.com.minigolfer.listview.SwipeDismissTouchListener;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewGamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewGamesFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private View myFragmentView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ViewGamesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewGamesFragment newInstance(/*String param1, String param2*/) {
        ViewGamesFragment fragment = new ViewGamesFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewGamesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = getListView();
        //TODO custom adapter that takes Game objects (this will ultimately save you time and frustation)
        ArrayList<String> items = new ArrayList<String>();
        items.add("something");
        final ArrayAdapter mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(mAdapter);

        // add on click listener
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //TODO change to individual game fragment... constructor should take game object
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, new ViewIndividualGameFragment(), "ViewGameTag").addToBackStack("ViewGameTag");
                ft.commit();
            }
        });

        // Create a ListView-specific touch listener. ListViews are given special treatment because
        // by default they handle touches for their list items... i.e. they're in charge of drawing
        // the pressed state (the list selector), handling list item clicks, etc.
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    mAdapter.remove(mAdapter.getItem(position));
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        });
        listView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listView.setOnScrollListener(touchListener.makeScrollListener());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //TODO custom array adapter
    //TODO add analytics
    //TODO option to delete old games
        //decide if you want to add are you sure dialog with the swipable deletes or edit and
        //may want to look for swipe to delete
    //TODO prompt user for confirmation/location when finish button is pressed

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_view_games, container, false);



        // Inflate the layout for this fragment
        return myFragmentView;
    }


    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Toast.makeText(getActivity(),
                "Clicked " + getListAdapter().getItem(position).toString(),
                Toast.LENGTH_SHORT).show();
    }





}
