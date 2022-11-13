package kz.lakida.javacourse.rest;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        if (user.getName() == null || user.getName().length() < 2) throw new RuntimeException("User name is invalid");
        userRepository.save(user);
    }


    public List<User> findAll() {
        var list = new ArrayList<User>();
        for (User user : userRepository.findAll()) {
            list.add(user);
        }
        return list;
    }

    public void createAccount() {

    }
}
