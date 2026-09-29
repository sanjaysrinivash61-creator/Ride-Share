package com.rideshare.services;
import com.rideshare.dto.UserRequest; import com.rideshare.models.User; import com.rideshare.repositories.UserRepository;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.List;
@Service @Transactional
public class UserServiceImpl implements UserService {
 private final UserRepository repo;
 public UserServiceImpl(UserRepository repo){this.repo=repo;}
 public User create(UserRequest r){ if(repo.findByEmail(r.email()).isPresent()) throw new IllegalArgumentException("Email already registered"); return repo.save(new User(r.name(),r.email(),r.phone()));}
 public List<User> all(){return repo.findAll();} public User get(Long id){return repo.findById(id).orElseThrow(()->new IllegalArgumentException("User not found"));}
}
