package service;

import bean.Login;

public interface LoginService {
    public Login getUser(int ID, int password);
}