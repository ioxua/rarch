package br.edu.ioxua.rarch.core.employee.businessrule;


import br.edu.ioxua.rarch.core.businessrule.BusinessRule;
import br.edu.ioxua.rarch.core.employee.Employee;
import br.edu.ioxua.rarch.core.result.Message;
import br.edu.ioxua.rarch.core.result.OperationResult;
import br.edu.ioxua.rarch.di.annotation.Group;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Named;

import java.util.regex.Pattern;

@Group("SAVE")
@Named(clazz = Employee.class)
@Injectable
public class ValidateUser implements BusinessRule<Employee> {

    // From https://emailregex.com/
    private static final Pattern EMAIL_PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    private static final Pattern PASSWD_PATTERN = Pattern.compile("(?=.*?[0-9])(?=.*?[A-Z])(?=.*?[a-z])(?=.*[^0-9A-Za-z]).{5,}");

    @Override
    public void process(OperationResult.OperationResultBuilder builder, Employee entity) {
        if (null == entity.getUser()) {
            builder.message(
                    Message.error("When saving a new Employee, a user is required")
            );
            return;
        }

        if (null == entity.getUser().getLogin()) {
            builder.message(
                    Message.error("The user login is required")
            );
            return;
        }

        if ( !EMAIL_PATTERN.matcher(entity.getUser().getLogin()).matches() ) {
            builder.message(
                    Message.error("The user login must be a valid email")
            );
        }

        if (null == entity.getUser().getPassword()) {
            builder.message(
                    Message.error("The user password is required")
            );
            return;
        }

        if ( !PASSWD_PATTERN.matcher(entity.getUser().getPassword()).matches() ) {
            builder.message(
                    Message.error("The user password must have at least one uppercase letter, one lowercase letter, a number and a special character")
            );
        }

    }
}
