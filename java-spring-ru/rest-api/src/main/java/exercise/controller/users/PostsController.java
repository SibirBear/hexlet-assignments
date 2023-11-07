package exercise.controller.users;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {

    List<Post> posts = Data.getPosts();
    //GET /api/users/{id}/posts* - Список всех постов, написанных пользователем с таким же `userId`, как `id` в маршруте
    @GetMapping("/users/{id}/posts")
    public ResponseEntity<List<Post>> listPosts(@PathVariable int id) {
        List<Post> result = posts.stream().filter(p -> p.getUserId() == id).toList();

        return ResponseEntity.ok().body(result);

    }

    //POST /api/users/{id}/posts* – Создание нового поста, привязанного к пользователю по `id`. Должен возвращаться
    //статус 201. Тело запроса должно содержать `slug`, `title`, `body`. А `userId` берется из маршрута
    @PostMapping("/users/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
        Post newPost = new Post();
        newPost.setUserId(id);
        newPost.setTitle(post.getTitle());
        newPost.setBody(post.getBody());
        newPost.setSlug(post.getSlug());
        posts.add(newPost);
        return ResponseEntity.created(URI.create("/users/"+ id + "/posts")).body(newPost);
    }


}

// END
