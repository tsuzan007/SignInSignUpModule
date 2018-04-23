# SignInSignUpModule
This module contains Sign In and Sign Up features. It also includes Gmail and Facebook login. Underlining libraries used are Firebase Authentication, EventBus, Facebook and Gmail Authentication

Here are the steps to use this in your project:
  1. Import it as a module in your application.
  
            Import the library module to your project (the library source becomes part of your project):
            Click File > New > Import Module.
            Enter the location of the library module directory then click Finish.
            
        a. Go to your project build.gradle file and add classpath 'com.google.gms.google-services:3.2.0', as we have gmail              log in button in the view
        
          buildscript {
            repositories {
              ...
            }
            dependencies {
            ...
            //include this line.
            classpath 'com.google.gms:google-services:3.2.0'
            } }
            
        b. In build.setting file, add 
        
                include ':app', ':my-library-module'
              
        c.  Open the app module's build.gradle file and add a new line to the dependencies block as shown in the following                snippet:
        
                  dependencies {
                        .......
                          compile project(path: ':SignIn')
                          }
                          
  2. Add a FrameLayout with id= "R.id.frameLayout" in your activity where you are adding the signIn and signUp features. Framelayout lets you control Fragment with ease.
  3. In your activity (where you want to add the signIn and signUp features), use FragmentManager to add a fragment. 
     Below is the snippet:
     
        SignInFragment signInFragment=new SignInFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        //R.id.frameLayout is the id of the parent view that holds the fragment
        fragmentTransaction.add(R.id.frameLayout,signInFragment);
        fragmentTransaction.commit();
  
  4. In order to get Firebase Authentication working, register your app in Firebase Console (https://console.firebase.google.com).
     Go to AppManifest.xml of this module, and use package name to register it.
  5. Copy google-services.json and paste in inside the module (inside SignIn).
