package forum.service;

import forum.domain.User;

public interface UserService {
    int register(User user);

    User login(String phone, String pwd);
}
