package br.edu.ioxua.rarch.web.helper;

import br.edu.ioxua.rarch.core.user.User;
import br.edu.ioxua.rarch.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserViewHelper implements ViewHelper<User> {
    @Override
    public User createInstance(HttpServletRequest request, HttpServletResponse response, String paramPrefix) throws NumberFormatException {
        User user = new User();

        user.setId(Util.safeLongFromString(request.getParameter(paramPrefix + "id")));
        user.setLogin(request.getParameter(paramPrefix + "login"));
        user.setPassword(request.getParameter(paramPrefix + "password"));

        return user;
    }
}
