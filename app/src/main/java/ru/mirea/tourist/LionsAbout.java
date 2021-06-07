package ru.mirea.tourist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LionsAbout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LionsAbout extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView text_about,text_metro,text_name;

    public LionsAbout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LionsAbout.
     */
    // TODO: Rename and change types and number of parameters
    public static LionsAbout newInstance(String param1, String param2) {
        LionsAbout fragment = new LionsAbout();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lions_about, container, false);

        text_about = view.findViewById(R.id.lions_about);
        text_metro = view.findViewById(R.id.lions_metro);
        text_name = view.findViewById(R.id.lions_name);

        String metro = getArguments().getString("metro");
        text_metro.setText(metro);

        String about = getArguments().getString("about");
        text_about.setText(about);

        String name = getArguments().getString("name");
        text_name.setText(name);

        return view;
    }
}