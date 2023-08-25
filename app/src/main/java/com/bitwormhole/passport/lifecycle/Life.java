package com.bitwormhole.passport.lifecycle;

public interface Life {

    class Registration {
        public OnCreateHandler onCreate;
        public OnStartHandler onStart;
        public OnResumeHandler onResume;
        public OnPauseHandler onPause;
        public OnRestartHandler onRestart;
        public OnStopHandler onStop;
        public OnDestroyHandler onDestroy;
    }

    interface Registry {
        Registration[] listLifeRegistrations();
    }

}
