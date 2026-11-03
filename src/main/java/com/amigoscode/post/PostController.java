package com.amigoscode.post;

import com.amigoscode.jsonplaceholder.JsonPlaceholderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;
    private final JsonPlaceholderService jsonPlaceholderService;

    public PostController(PostService postService, @Qualifier("web-client") JsonPlaceholderService jsonPlaceholderService) {
        this.postService = postService;
        this.jsonPlaceholderService = jsonPlaceholderService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        //return postService.getAllPosts();
        return jsonPlaceholderService.getAllPosts();
    }

    @GetMapping("{id}")
    public Post getPostById(@PathVariable("id") Integer id) {
        //return postService.getPostById(id);
        return jsonPlaceholderService.getPostById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody Post post){
        //postService.createPost(post);
        jsonPlaceholderService.createPost(post);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") Integer id){
        //postService.deletePost(id);
        jsonPlaceholderService.deletePost(id);
    }

    @PutMapping("{id}")
    public void updatePost(
            @PathVariable("id") Integer id,
            @RequestBody Post post){
        //postService.updatePost(id, post);
        jsonPlaceholderService.updatePost(id, post);
    }

}