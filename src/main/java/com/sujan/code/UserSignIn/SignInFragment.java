package com.sujan.code.UserSignIn;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;




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
    private GoogleSignInOptions gso;
    private CallbackManager callbackManager;

    private static final String EMAIL = "email";

    public static List<String> permission=new ArrayList<String>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        cardView = view.findViewById(R.id.myCardView);
        signIn = view.findViewById(R.id.buttonSignIn);
        signUp = view.findViewById(R.id.buttonSignUp);
        facebookSignIn =view.findViewById(R.id.facebookSignIn);
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
        String key = getHashKeyForFacebook(getActivity());
        Log.e("..", key);
    }

    public String getHashKeyForFacebook(Context context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
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
                Toast.makeText(getActivity(), "Invalid inputs", Toast.LENGTH_LONG).show();
            }


        } else if (v.getId() == R.id.facebookSignIn) {
            Toast.makeText(getActivity(), "Facebook log in is in developing mode.", Toast.LENGTH_SHORT).show();


        } else if (v.getId() == R.id.gmailSignIn) {
            Toast.makeText(getActivity(), "Gmail log in is in developing mode.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Gmail log in Clicked");
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
