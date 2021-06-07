package ru.mirea.tourist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.mirea.tourist.Model.Users;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link admin_panel#newInstance} factory method to
 * create an instance of this fragment.
 */
public class admin_panel extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private DatabaseReference mDataBase;
    private String USER_KEY = "Users";
    private EditText loginInputAdd,passInputAdd;
    private String login_dell;

    public admin_panel() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment admin_panel.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_panel newInstance(String param1, String param2) {
        admin_panel fragment = new admin_panel();
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
        View view = inflater.inflate(R.layout.fragment_admin_panel, container, false);

        listView = view.findViewById(R.id.list_account);
        listData = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(adapter);

        mDataBase = FirebaseDatabase.getInstance("https://tourist-3be36-default-rtdb.europe-west1.firebasedatabase.app/").getReference(USER_KEY);
        getDataFromDB();


        Button add_users = (Button) view.findViewById(R.id.admin_add_btn);
        Button dell_users = (Button) view.findViewById(R.id.admin_dell_btn);
        loginInputAdd = (EditText) view.findViewById(R.id.admin_login_add_input);
        passInputAdd = (EditText) view.findViewById(R.id.admin_pass_add_input);
        EditText loginInputDell = (EditText) view.findViewById(R.id.admin_dell_login);

        add_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
        dell_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_dell = loginInputDell.getText().toString();
                if(!login_dell.isEmpty()){
                    DatabaseReference dUsers = FirebaseDatabase.getInstance("https://tourist-3be36-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
                    dUsers.child(login_dell).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                loginInputDell.setText("");
                            }
                        }
                    });
                }else{
                    Toast.makeText(getActivity(), "Введите логин", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void CreateAccount(){
        String login = loginInputAdd.getText().toString();
        String pass = passInputAdd.getText().toString();

        if(TextUtils.isEmpty(login)) {
            Toast.makeText(getActivity(), "Введите логин", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pass)){
            Toast.makeText(getActivity(), "Введите пароль", Toast.LENGTH_SHORT).show();
        }else if(login.length()<5 || login.length()>20){
            Toast.makeText(getActivity(), "Длинна логина от 5 до 20 символов", Toast.LENGTH_SHORT).show();
        }else if(pass.length()<10 || pass.length()>30){
            Toast.makeText(getActivity(), "Длинна пароля от 10 до 30 символов", Toast.LENGTH_SHORT).show();
        }
        else{
            ValidateLogin(login,pass);
        }
    }

    private void ValidateLogin(final String login,final String pass){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance("https://tourist-3be36-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(login).exists()))
                {
                    HashMap<String,Object> userDataMap = new HashMap<>();
                    userDataMap.put("login",login);
                    userDataMap.put("pass",pass);

                    RootRef.child("Users").child(login).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getActivity(), "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                                        loginInputAdd.setText("");
                                        passInputAdd.setText("");
                                    }
                                    else{
                                        Toast.makeText(getActivity(), "Ошибка", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(getActivity(), "Логин "+ login + " уже зарегистрирован", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(listData.size() > 0) listData.clear();

                for(DataSnapshot ds : snapshot.getChildren()){
                    Users user = ds.getValue(Users.class);
                    assert user != null;
                    listData.add(user.login);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }

}