package com.sujan.code.UserSignIn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private Button back;
    private Button createAccount;
    private EditText email;
    private EditText password;
    private FirebaseAuth auth;
    private String TAG = "TAG";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_form, container, false);
        back = view.findViewById(R.id.buttonBack);
        createAccount = view.findViewById(R.id.buttonCreateAC);
        email = view.findViewById(R.id.eTextUserName);
        password = view.findViewById(R.id.eTextPassword);
        back.setOnClickListener(this);
        createAccount.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
    }

    /**
     * A onClick listener, that handles when user clicks back button.
     * Pop the fragment out of stack, and
     * Fires an EventBus Event, acknowledge by onBackButtonClicked(Boolean isBackClicked) method in SignInFragment.
     *
     * @param v view on which action took place
     */
    @Override
    public void onClick(View v) {
        // Clear the current fragment when back button is pressed.
        if (v.getId() == R.id.buttonBack) {
            getActivity().getSupportFragmentManager().popBackStack();
            EventBus.getDefault().post(true);
            // Create a new user when user clicks create new account.
        } else if (v.getId() == R.id.buttonCreateAC) {
            try {
                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = auth.getCurrentUser();
                                    Toast.makeText(getActivity(),"User created.",Toast.LENGTH_LONG).show();
                                    back.performClick();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Invalid Inputs", Toast.LENGTH_LONG).show();
            }

        }

    }

}
