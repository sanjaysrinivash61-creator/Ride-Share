package com.rideshare.services;
import com.rideshare.dto.UserRequest;
import com.rideshare.models.User;
import java.util.List;
public interface UserService { User create(UserRequest r); List<User> all(); User get(Long id); }
