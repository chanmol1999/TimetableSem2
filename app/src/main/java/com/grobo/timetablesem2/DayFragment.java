package com.grobo.timetablesem2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DayFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<String>{

    private String mDay;
    private TimeTableAdapter mAdapter;
    private ListView listView;
    public String branchPreference;
    private static final String TIMETABLE_URL = "https://timetable-grobo.firebaseio.com/.json";
    private String mJsonResponse;

    public static DayFragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt("dayNumber", page);
        DayFragment fragment = new DayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (getArguments().getInt("dayNumber")){
            case 1:
                mDay = "monday";
                break;
            case 2:
                mDay = "tuesday";
                break;
            case 3:
                mDay = "wednesday";
                break;
            case 4:
                mDay = "thursday";
                break;
            case 5:
                mDay = "friday";
                break;
            default:

        }

        branchPreference = getContext().getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("branchPreference", "").toLowerCase();

        LoaderManager loaderManager = android.support.v4.app.LoaderManager.getInstance(this);
        loaderManager.initLoader(1, null, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.timetable_list, container, false);

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {

        return new TimetableLoader(getContext(), TIMETABLE_URL);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String jsonResponse) {
        mJsonResponse = jsonResponse;
        extractData(jsonResponse);
    }

    private void extractData(String jsonResponse){
        listView = getView().findViewById(R.id.timetable_list_view);

        List<SingleDay> singleDayList;
        singleDayList = QueryUtils.extractTimetable(jsonResponse, branchPreference, mDay);

        mAdapter = new TimeTableAdapter(getActivity(), R.layout.single_day_item, singleDayList);

        listView.setAdapter(mAdapter);

    }

}
