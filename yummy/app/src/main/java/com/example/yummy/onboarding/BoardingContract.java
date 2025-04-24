package com.example.yummy.onboarding;

public interface BoardingContract {

    public interface View
    {
        public void guestMode();
        public void signedMode();

    }
    public interface Presenter
    {
        public void fireBaseinit();

        public void signInListnr();
        public void signUpListnr();
        public void googleListnr();
        public void guestListnr();

    }
}
