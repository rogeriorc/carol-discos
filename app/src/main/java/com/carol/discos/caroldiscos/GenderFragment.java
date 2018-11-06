package com.carol.discos.caroldiscos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carol.discos.caroldiscos.db.GenderDbHelper;
import com.carol.discos.caroldiscos.db.GenderDbHelper.GenderEntry;
import com.carol.discos.caroldiscos.ui.GenderRecyclerViewAdapter;

import java.util.ArrayList;

public class GenderFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    public static GenderFragment instance;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GenderFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GenderFragment newInstance(int columnCount) {
        GenderFragment fragment = new GenderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GenderFragment.instance = this;

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gender_list, container, false);
        GenderDbHelper db = new GenderDbHelper(this.getActivity());

        ArrayList<GenderEntry> items = db.select();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new GenderRecyclerViewAdapter(items, this.getActivity()));
        }
        return view;
    }


    public static void addGender() {


    }

}
