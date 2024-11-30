package services;

import models.Role;

public interface AuthInterface {
    boolean login(String email, String password, Role role);
    void logout();
}
