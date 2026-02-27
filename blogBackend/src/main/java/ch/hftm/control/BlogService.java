package ch.hftm.control;

import ch.hftm.entity.Blog;
import ch.hftm.entity.BlogStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BlogService {

    @Inject
    BlogRepository blogRepository;

    @Transactional
    public Blog create(Blog blog) {
        blog.status = BlogStatus.PENDING;
        blog.persist();
        return blog;
    }

    @Transactional
    public void applyValidationResult(Long blogId, boolean approved, String reason) {
        Blog blog = blogRepository.findById(blogId);
        if (blog == null)
            return;

        blog.status = approved ? BlogStatus.APPROVED : BlogStatus.REJECTED;
        // optional: reason speichern (z.B. blog.validationReason)
    }
}