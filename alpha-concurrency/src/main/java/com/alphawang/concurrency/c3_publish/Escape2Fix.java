package com.alphawang.concurrency.c3_publish;


public class Escape2Fix {
    private final EventListener listener;

    private Escape2Fix() {
        listener = new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        };
    }

    public static Escape2Fix newInstance(EventSource source) {
        Escape2Fix safe = new Escape2Fix();
        source.registerListener(safe.listener);
        return safe;
    }

    void doSomething(Event e) {
    }

    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }
  }
