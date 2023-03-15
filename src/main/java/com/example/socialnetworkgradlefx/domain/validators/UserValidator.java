package com.example.socialnetworkgradlefx.domain.validators;


import com.example.socialnetworkgradlefx.domain.User;

import java.util.regex.Pattern;

/**
 * Class for user validation
 */
public class UserValidator implements Validator<User> {
    /**
     * Validates the given User object
     * @param entity User - the user that has to be validated
     * @throws ValidationException exception is thrown if user is not valid, meaning
     * its first/last name has an inappropriate length,
     * email is null or its structure does not respect the specified regex
     */
    @Override
    public void validate(User entity) throws ValidationException {
        String firstName = entity.getFirstName();
        String lastName = entity.getLastName();
        String email = entity.getEmail();
        String messages = "";
        if(firstName.length() < 3 || firstName.length() > 30)
            messages = messages + "First name length should be between 3 and 30 characters!\n";
        if(lastName.length() < 3 || lastName.length() > 30)
            messages = messages + "Last name length should be between 3 and 30 characters!\n";

        if (email.equals(""))
            messages = messages + "Email cannot be null!\n";
        else{
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
            Pattern pat = Pattern.compile(emailRegex);
            if(!pat.matcher(email).matches()) {
                messages = messages + "Invalid email structure!\n";
            }
        }
        if(!messages.equals("")){
            throw new ValidationException(messages);
        }
    }
}
