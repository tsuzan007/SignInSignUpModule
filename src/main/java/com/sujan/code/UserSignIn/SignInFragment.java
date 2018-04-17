package com.sujan.code.UserSignIn;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class SignInFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TAG";
    private CardView cardView;
    private Button signIn;
    private Button signUp;
    private ImageView facebookSignIn;
    private ImageView gmailSignIn;
    private EditText username;
    private EditText password;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private SignUpFragment signUpFragment;
    private FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        cardView = view.findViewById(R.id.myCardView);
        signIn = view.findViewById(R.id.buttonSignIn);
        signUp = view.findViewById(R.id.buttonSignUp);
        facebookSignIn = view.findViewById(R.id.facebookSignIn);
        gmailSignIn = view.findViewById(R.id.gmailSignIn);
        username = view.findViewById(R.id.editTextUserName);
        password = view.findViewById(R.id.editTextPassword);
        signUp.setOnClickListener(this);
        signIn.setOnClickListener(this);
        facebookSignIn.setOnClickListener(this);
        gmailSignIn.setOnClickListener(this);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();
        signUpFragment = new SignUpFragment();
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void updateUI(FirebaseUser currentUser) {
        Log.e("Current User", currentUser.getEmail());
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSignUp) {
            fragmentTransaction = fragmentManager.beginTransaction();
            cardView.setVisibility(View.INVISIBLE);
            fragmentTransaction.add(R.id.frameL, signUpFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (v.getId() == R.id.buttonSignIn) {
            try {
                mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "sign in:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getActivity(), "Welcome" + username.getText().toString(), Toast.LENGTH_LONG).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            } catch (Exception e) {
                Log.e("...", e.toString());
                Toast.makeText(getActivity(),"Invalid inputs",Toast.LENGTH_LONG).show();
            }


        }else if(v.getId() == R.id.facebookSignIn){
            Log.e(TAG,"Facebook login clicked");

        }else if(v.getId()==R.id.gmailSignIn){
            Log.e(TAG,"Gmail log in Clicked");
        }

    }


    @Subscribe
    public void onBackButtonClicked(Boolean isBackClicked) {
        cardView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}
