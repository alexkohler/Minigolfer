package kohlerbear.com.minigolfer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;

import kohlerbear.com.minigolfer.listview.ListViewAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewGamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewGamesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private View myFragmentView;
    private ListView mListView;
    private ListViewAdapter mAdapter;
    private Context mContext = getActivity();
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

        mListView = (ListView) myFragmentView.findViewById(R.id.listview);

        /**
         * The following comment is the sample usage of ArraySwipeAdapter.
         */
//        String[] adapterData = new String[]{"Activity", "Service", "Content Provider", "Intent", "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient",
//                "DDMS", "Android Studio", "Fragment", "Loader", "Activity", "Service", "Content Provider", "Intent",
//                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent",
//                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient"};
//        mListView.setAdapter(new ArraySwipeAdapterSample<String>(this, R.layout.listview_item, R.id.position, adapterData));

        mAdapter = new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mAdapter.setMode(Attributes.Mode.Single);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ((SwipeLayout)(mListView.getChildAt(position - mListView.getFirstVisiblePosition()))).open(true);
                showToast("Clicked");
            }
        });
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                ((SwipeLayout)(mListView.getChildAt(position - mListView.getFirstVisiblePosition()))).open(true);
                return true;
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ListView", "onNothingSelected:");
            }
        });


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
