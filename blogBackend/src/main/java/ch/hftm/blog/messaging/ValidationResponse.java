package ch.hftm.blog.messaging;

public class ValidationResponse {
    public Long blogId;
    public boolean approved;
    public String reason;

    public ValidationResponse() {}

    public ValidationResponse(Long blogId, boolean approved, String reason) {
        this.blogId = blogId;
        this.approved = approved;
        this.reason = reason;
    }
}