package softuni.workshop.util;

public interface ValidatorUtil {
    <T> boolean isValid(T element);

    <T> String validationErrors(T element);
}
