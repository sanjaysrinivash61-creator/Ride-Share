package com.rideshare.controllers;
import com.rideshare.dto.UserRequest; import com.rideshare.models.User; import com.rideshare.services.UserService;
import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService s) {
        service = s;
    }

    @PostMapping
    public User create(@Valid @RequestBody UserRequest r) {
        return service.create(r);
    }

    @GetMapping
    public List<User> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return service.get(id);
    }

}
