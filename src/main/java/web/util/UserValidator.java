package web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import web.models.User;
import web.services.UserService;

import java.util.List;

@Component
public class UserValidator implements Validator {

    private final UserService userServiceImplementation;

    @Autowired
    public UserValidator(UserService userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (!userServiceImplementation.getUserByFullName(user.getName(), user.getSurname()).isEmpty()) {
            errors.rejectValue("name", "", "Пользователь с таким именем и фамилией уже существует");
            errors.rejectValue("surname", "", "Пользователь с таким именем и фамилией уже существует");
        }
    }

    public void validateToUpdate(Object o, Errors errors) {
        User user = (User) o;
        List<User> foundUsers = userServiceImplementation.getUserByFullName(user.getName(), user.getSurname());
        if (!foundUsers.isEmpty() && foundUsers.get(0).getId() != user.getId()) {
            errors.rejectValue("name", "", "Пользователь с таким именем и фамилией уже существует");
            errors.rejectValue("surname", "", "Пользователь с таким именем и фамилией уже существует");
        }
    }
}
