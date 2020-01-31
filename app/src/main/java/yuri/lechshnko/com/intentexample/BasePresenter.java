package yuri.lechshnko.com.intentexample;

interface BasePresenter<T> {
    void startView(T view);

    void stopView();
}
