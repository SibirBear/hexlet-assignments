package exercise;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

import static org.springframework.http.ResponseEntity.ok;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
//       * *GET /posts* - Список всех постов. Должен возвращаться статус 200 и заголовок "X-Total-Count" в котором содержиться количество постов
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> listPosts(@RequestParam(defaultValue = "10") Integer limit,
                                                @RequestParam(defaultValue = "1") Integer page) {
        int offset = (page - 1) * limit;
        List<Post> result = posts.stream().skip(offset).limit(limit).toList();

        return ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(result);

    }
//       * *GET /posts/{id}* – Просмотр конкретного поста. Должен возвращаться статус 200, если пост найден. Если пост не найден, то должен возвращаться статус 404
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable String id) {
        Optional<Post> post = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        return ResponseEntity
                .of(post);
    }
//       * *POST /posts* – Создание нового поста. Должен возвращаться статус 201
    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity
                .created(URI.create("/posts"))
                .body(post);
    }
//       * *PUT /posts/{id}* – Обновление поста. Должен возвращаться статус 200. Если пост уже не существует, то должен возвращаться 204
    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id,
                                           @RequestBody Post post) {
        Optional<Post> findPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        int status = 204;

        if (findPost.isPresent()) {
            Post newPost = findPost.get();
            newPost.setId(post.getId());
            newPost.setTitle(post.getTitle());
            newPost.setBody(post.getBody());
            status = 200;
        }

        return ResponseEntity.status(status).body(post);

    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
