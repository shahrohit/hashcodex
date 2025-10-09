package com.shahrohit.hashcodex.events;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ServerSentEventHub {
    private final Map<String, CopyOnWriteArrayList<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter register(String correlationId, long timeoutMs) {
        SseEmitter em = new SseEmitter(timeoutMs);
        emitters.computeIfAbsent(correlationId, k -> new CopyOnWriteArrayList<>()).add(em);
        em.onCompletion(() -> remove(correlationId, em));
        em.onTimeout(() -> remove(correlationId, em));
        em.onError(e -> remove(correlationId, em));
        return em;
    }

    public void send(String correlationId, String event, Object data) {
        var list = emitters.get(correlationId);
        if (list == null) return;
        for (var em : list) {
            try { em.send(SseEmitter.event().name(event).data(data)); }
            catch (IOException e) { em.complete(); remove(correlationId, em); }
        }
    }

    private void remove(String correlationId, SseEmitter em) {
        var list = emitters.get(correlationId);
        if (list == null) return;
        list.remove(em);
        if (list.isEmpty()) emitters.remove(correlationId);
    }

}
