package ch.hftm.boundary;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import ch.hftm.entity.ValidationRequest;

@ApplicationScoped
public class ValidationProducer {

    @Channel("validation-request-out")
    Emitter<ValidationRequest> emitter;

    public void sendValidation(Long id, String text) {
        ValidationRequest request = new ValidationRequest();
        request.id = id;
        request.text = text;
        request.sourceType = "BLOG_POST";

        emitter.send(request);
    }
}