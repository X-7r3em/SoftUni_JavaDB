package softuni.workshop.util;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidatorUtilImpl implements ValidatorUtil {
    private final Validator validator;

    public ValidatorUtilImpl() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public <T> boolean isValid(T element) {
        return this.validator.validate(element).size() == 0;
    }

    public <T> String validationErrors(T element) {
        StringBuilder output = new StringBuilder();
        this.validator.validate(element)
                .forEach(v -> output.append(v.getMessage()).append(System.lineSeparator()));

        return output.toString().trim();
    }
}
