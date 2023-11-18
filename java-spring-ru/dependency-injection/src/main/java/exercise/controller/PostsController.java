package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    //GET /posts* - Список всех постов
    @GetMapping(path = "")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    //GET /posts/{id}* – Просмотр конкретного поста
    @GetMapping(path = "/{id}")
    public Post getPost(@PathVariable Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found."));
    }

    //POST /posts* – Создание нового поста. При успешном создании возвращается статус 201
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    //PUT /posts/{id}* – Обновление поста
    @PutMapping(path = "/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post newPost) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found."));
        post.setTitle(newPost.getTitle());
        post.setBody(newPost.getBody());

        return postRepository.save(post);
    }
    //DELETE /posts/{id}* – Удаление поста. При удалении поста удаляются все комментарии этого поста
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        commentRepository.deleteByPostId(id);
        postRepository.deleteById(id);
    }

}
// END
