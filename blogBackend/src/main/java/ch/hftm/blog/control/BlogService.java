package ch.hftm.blog.control;

import java.util.List;
import java.util.Optional;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.BlogStatus;
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

    public Blog getBlog(long id) {
        return Blog.findById(id);
    }

    public Blog updateBlog(long id, Blog updated) {
        Blog blog = Blog.findById(id);
        if (blog == null)
            return null;

        blog.title = updated.title;
        blog.content = updated.content;
        blog.status = BlogStatus.PENDING; // optional: revalidate

        return blog;
    }

    public boolean deleteBlogById(Long id) {
        return Blog.deleteById(id);
    }

    public void deleteBlogs() {
        Blog.deleteAll();
    }

    public List<Blog> getBlogs(Optional<String> search, Optional<Integer> page, Optional<Integer> size) {

        var query = Blog.find("lower(title) like ?1", "%" + search.orElse("").toLowerCase() + "%");

        if (page.isPresent() && size.isPresent()) {
            query.page(page.get(), size.get());
        }

        return query.list();
    }

}