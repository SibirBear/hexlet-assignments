package exercise.controller;

import exercise.ResourceNotFoundException;
import exercise.model.Comment;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import exercise.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    // BEGIN
    //GET /posts/{postId}/comments - вывод всех комментариев для конкретного поста. Должны выводится только комментарии, принадлежащие посту.
    @GetMapping(path = "/{postId}/comments")
    public Iterable<Comment> getAllComments(@PathVariable Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    //GET /posts/{postId}/comments/{commentId} - вывод конкретного комментария для поста. Должны выводится только комментарий,
    // принадлежащие посту. Если такой комментарий не существует, должен вернуться ответ с кодом 404.
    @GetMapping(path = "/{postId}/comments/{commentId}")
    public Comment getComment(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId
    ) {
        return commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with ID "
                        + commentId + " in Post ID " + postId + " not found."));
    }

    //POST /posts/{postId}/comments - создание нового комментария для поста. Должны выводится только комментарий, принадлежащие посту.
    @PostMapping(path = "/{postId}/comments")
    public void createComment(
            @PathVariable long postId,
            @RequestBody Comment comment
    ) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post wit ID " + postId + " not found"));
        comment.setPost(post);

        commentRepository.save(comment);

    }

    //PATCH /posts/{postId}/comments/{commentId} - редактирование конкретного комментария поста. Если такой комментарий не существует, должен вернуться ответ с кодом 404.
    @PatchMapping(path = "/{postId}/comments/{commentId}")
    public void updateComment(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId,
            @RequestBody Comment comment
    ) {
        Comment commentRepo = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with ID "
                        + commentId + " in Post ID " + postId + " not found."));
        commentRepo.setContent(comment.getContent());

        commentRepository.save(commentRepo);
    }

    //DELETE /posts/{postId}/comments/{commentId} - удаление конкретного комментария у поста. Если такой комментарий не существует, должен вернуться ответ с кодом 404
    @DeleteMapping(path = "/{postId}/comments/{commentId}")
    public void deleteComment(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId
    ) {
        Comment commentRepo = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with ID "
                        + commentId + " in Post ID " + postId + " not found."));
        commentRepository.delete(commentRepo);

    }
    // END
}
