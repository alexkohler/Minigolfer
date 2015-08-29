package kohlerbear.com.minigolfer;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import kohlerbear.com.minigolfer.db.Game;
import kohlerbear.com.minigolfer.db.GameDAO;
import kohlerbear.com.minigolfer.db.Player;


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
    private List<Game> mGameList;
    private PreviousGameAdapter mAdapter;
    private GameDAO mGameDAO;
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

        SwipeMenuListView listView = (SwipeMenuListView) myFragmentView.findViewById(R.id.listView);

        mGameList = new ArrayList<Game>();
        mGameDAO = new GameDAO(getActivity());

        mGameList = mGameDAO.getAllGames();

        //bootstrapped data until we can throw in our game object
        /*Game testGame = new Game();
        long id = 10039484;
        testGame.setGameID(id);
        testGame.setLocation("Hindlersburg");
        //test player object
        Player playerOne = new Player("jeff", 1); //hole in one son
        ArrayList<Player> dummyPlayerList = new ArrayList<>();
        dummyPlayerList.add(playerOne);
        testGame.setPlayers(dummyPlayerList);

        mGameList.add(testGame);*/


        mAdapter = new PreviousGameAdapter();
        listView.setAdapter(mAdapter);

        // create menu creaor
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

    // set creator
        listView.setMenuCreator(creator);


        // listener
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        showToast("open");
                        break;
                    case 1:
                        //
                        //TODO database stuff here
                        showToast("delete");
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Game clickedGame = (Game) adapterView.getItemAtPosition(position);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, ViewIndividualGameFragment.newInstance(clickedGame), "NewFragmentTag");
                ft.addToBackStack(null);
                ft.commit();

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


    //TODO move this into separate file?
    class PreviousGameAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mGameList.size();
        }

        @Override
        public Game getItem(int position) {
            return mGameList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getActivity(),
                        R.layout.games_list, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            Game game = getItem(position);
            holder.tv_name.setText(game.getLocation());
//            ApplicationInfo item = getItem(position);
//            holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
//            holder.tv_name.setText(item.loadLabel(getPackageManager()));
            return convertView;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(this);
            }
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
