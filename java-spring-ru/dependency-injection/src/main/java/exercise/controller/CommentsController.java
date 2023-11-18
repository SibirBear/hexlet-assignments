package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

    //GET /comments* - Список всех комментариев
    @GetMapping(path = "")
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    //GET /comments/{id}* – Просмотр конкретного комментария
    @GetMapping(path = "/{id}")
    public Comment getComment(@PathVariable Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found."));
    }

    //POST /comments* – Создание нового комментария. При успешном создании возвращается статус 201
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createCommment(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

    //PUT /comments/{id}* – Обновление комментария
    @PutMapping(path = "/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment newComment) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found."));
        comment.setPostId(newComment.getPostId());
        comment.setBody(newComment.getBody());

        return commentRepository.save(comment);
    }

    //DELETE /comments/{id}* – Удаление комментария
    @DeleteMapping(path = "/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }

}
// END
