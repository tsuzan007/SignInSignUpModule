# SignInSignUpModule
This module contains Sign In and Sign Up features. It also includes Gmail and Facebook login. Underlining libraries used are Firebase Authentication, EventBus, Facebook and Gmail Authentication

Here are the steps to use this in your project:
  1. Import it as a module in your application.
  2. Add a frameLayout in your activity where you are adding the signIn and signUp features. Framelayout lets you control Fragment with ease.
  3. In your activity (where you want to add the signIn and signUp features), use FragmentManager to add a fragment. 
     Below is the snippet:
     
        SignInFragment signInFragment=new SignInFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout,signInFragment);
        fragmentTransaction.commit();
  
  4. In order to get Firebase Authentication working, register your app in Firebase Console (https://console.firebase.google.com).
     Go to AppManifest.xml of this module, and use package name to register it.
  5. Copy google-services.json and paste in inside the module (inside SignIn).
