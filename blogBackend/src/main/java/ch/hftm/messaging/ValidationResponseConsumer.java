package ch.hftm.messaging;

import ch.hftm.control.BlogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import io.smallrye.common.annotation.Blocking;

@ApplicationScoped
public class ValidationResponseConsumer {

    @Inject
    BlogService blogService;

    private final Jsonb jsonb = JsonbBuilder.create();

    @Incoming("validation-responses")
    @Blocking // wichtig: JPA/DB ist blocking
    public void onMessage(String payload) {
        ValidationResponse response = jsonb.fromJson(payload, ValidationResponse.class);
        blogService.applyValidationResult(response.blogId, response.approved, response.reason);
    }
}