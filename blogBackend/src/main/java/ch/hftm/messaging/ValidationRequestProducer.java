package ch.hftm.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class ValidationRequestProducer {

    @Inject
    @Channel("validation-requests")
    Emitter<ValidationRequest> emitter;

    public void send(ValidationRequest request) {
        emitter.send(request);
    }
}