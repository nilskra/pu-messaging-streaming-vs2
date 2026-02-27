package ch.hftm.messaging;

public class ValidationRequest {
    public Long blogId;
    public String text;

    public ValidationRequest() {}

    public ValidationRequest(Long blogId, String text) {
        this.blogId = blogId;
        this.text = text;
    }
}