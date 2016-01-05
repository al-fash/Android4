package com.example.al_ameenfashola.fashola_hw4;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new RecyclerViewFragment())
                    .commit();
        }
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class RecyclerViewFragment extends Fragment {

    public static final String ARG_OPTION = "option";
        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        private MyRecyclerViewAdapter mRecyclerViewAdapter ;
        private MovieData movieData;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
            movieData = new MovieData();

        }




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            final View rootView = inflater.inflate(R.layout.recycleview, container, false);

            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);


            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity());

            mRecyclerView.setLayoutManager(mLayoutManager);

            mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), movieData.getMoviesList());

            mRecyclerViewAdapter.SetOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    HashMap<String, ?> movie = (HashMap<String, ?> ) movieData.getItem(position) ;
                    String name=(String)movie.get("name");
                    Toast.makeText(getActivity(), "Movie: " + name, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onItemLongClick(View v, int position) {
                    movieData.moviesList.add(position+1, (HashMap) movieData.getItem(position).clone());
                    mRecyclerViewAdapter.notifyItemInserted(position);
                }
            });

     mRecyclerView.setAdapter(mRecyclerViewAdapter) ;

        Button delete =(Button) rootView.findViewById(R.id.delete) ;
            delete.setOnClickListener (new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                   // ArrayList<HashMap<String, ?>> movies = new ArrayList<HashMap<String, ?>>();
                    for (int i =mRecyclerViewAdapter.getItemCount()-1; i>=0;i--) {
                        Map<String, ?> temp = (Map<String, ?>) movieData.getItem(i);
                        if ((Boolean) temp.get("selection")) {

                            movieData.moviesList.remove(i);

                            mRecyclerViewAdapter.notifyItemRemoved(i);

                        }

                    }

                    mRecyclerViewAdapter.notifyDataSetChanged();
                }
                });

            Button selectAll = (Button) rootView.findViewById(R.id.selectall) ;
            selectAll.setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick (View view) {
                    for (int i =mRecyclerViewAdapter.getItemCount()-1; i>=0;i--) {
                        HashMap<String, Boolean> item =
                                (HashMap<String, Boolean>) movieData.getItem(i);
                        item.put("selection", true);
                    }
                    mRecyclerViewAdapter.notifyDataSetChanged();
                }
            }) ;

            Button clearAll = (Button) rootView.findViewById(R.id.clearall) ;
            clearAll.setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick (View view) {
                    for (int i =mRecyclerViewAdapter.getItemCount()-1; i>=0;i--) {
                        HashMap<String, Boolean> item =
                                (HashMap<String, Boolean>) movieData.getItem(i);
                        item.put("selection", false);
                    }
                    mRecyclerViewAdapter.notifyDataSetChanged();
                }
            }) ;

            return rootView;
        }



}
}
