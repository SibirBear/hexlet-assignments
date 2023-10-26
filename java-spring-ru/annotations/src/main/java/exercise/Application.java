package exercise;

import exercise.model.Address;
import exercise.annotation.Inspect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);

        // BEGIN
        for (Method method : Address.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Inspect.class)) {
                String name = method.getName();
                String type = method.getReturnType().getSimpleName();
                System.out.printf("Method %s returns a value of type %s\n", name, type);
            }
        }
        // END
    }
}
