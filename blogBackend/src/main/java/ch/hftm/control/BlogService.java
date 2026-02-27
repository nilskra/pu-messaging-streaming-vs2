package ch.hftm.control;


import java.util.List;
import java.util.Optional;
import ch.hftm.entity.Blog;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class BlogService {
    
    @Inject
    BlogRepository blogRepository;

   public List<Blog> getBlogs(Optional<String> searchString, Optional<Integer> page, Optional<Integer> size) {
        Log.info("Getting blogs with searchString: " + searchString.orElse("none") + ", page: " + page.orElse(0));
        
        PanacheQuery<Blog> query;

        if (searchString.isPresent()) {
            query = blogRepository.find("title like ?1 or content like ?1", "%" + searchString.get() + "%");
        } else {
            query = blogRepository.findAll();
        }

        if (page.isPresent() && size.isPresent()) {
            query = query.page(Page.of(page.get(), size.get()));
        }

        List<Blog> blogs = query.list();
        Log.info("Returning " + blogs.size() + " blogs");
        return blogs;
    }

    public Blog getBlog(long id) {
        Blog foundBlog = blogRepository.findById(id);
        if (foundBlog == null) {
            throw new IllegalArgumentException("Blog mit ID " + id + " nicht gefunden.");
        }
        return foundBlog;
    }

    @Transactional
    public void addBlog(Blog blog) {
        Log.info("Adding blog " + blog.getTitle());
        blogRepository.persist(blog);
    }

    @Transactional
    public void updateBlog(long id, Blog updatedBlog) {
        Blog existingBlog = blogRepository.findById(id);
        if (existingBlog == null) {
            throw new IllegalArgumentException("Blog mit ID " + id + " nicht gefunden.");
        }
        existingBlog.setTitle(updatedBlog.getTitle());
        existingBlog.setContent(updatedBlog.getContent());
    }

    @Transactional
    public void deleteBlogById(Long id) {
        Log.info("Deleting Blog with id " + id);
        Blog blog = blogRepository.findById(id);
        
        if (blog.getId() == id) {
            blogRepository.deleteById(id);
        }
    }

    @Transactional
    public void deleteBlogs() {
        Log.info("Deleting all Blogs");
        blogRepository.deleteAll();
    }
}