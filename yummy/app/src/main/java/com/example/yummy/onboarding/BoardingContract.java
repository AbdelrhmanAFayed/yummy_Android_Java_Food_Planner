package com.example.yummy.onboarding;

public interface BoardingContract {

    interface View {
        String getEmail();
        String getPassword();
        void showSignInSuccess();
        void showSignUpSuccess();
        void showSignInError(String errorMessage);
        void showSignUpError(String errorMessage);
        void startGoogleSignInFlow();
        void showGoogleSignInError(String errorMessage);
        void onGoogleSignInSuccess();
        void onGoogleSignInFailure(String errorMessage);
        void showAnonymousSignInSuccess();
        void showAnonymousSignInError(String errorMessage);
        void navigateToHomeScreen();
        void showLoadingIndicator(boolean show);

        void promptForUsername();
        void onUsernameSaved();
        void onUsernameSaveError(String err);
        void saveUsernameLocally(String username);
    }

    interface Presenter {
        void handleSignInButtonClick();
        void handleSignUpButtonClick();
        void handleGoogleSignInButtonClick();
        void handleGoogleSignInResult(String idToken);
        void handleAnonymousSignInButtonClick();
        void handleUsernameChosen(String username);
    }
}
