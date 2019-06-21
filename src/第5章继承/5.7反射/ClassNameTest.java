import java.util.*;

public class ClassNameTest {
    public static void main(String[] args) {
        Class cl1 = Random.class;
        Class cl2 = int.class;
        Class cl3 = Double[].class;
        Class cl4 = Employee.class;
        Class cl5 = Employee[].class;
        System.out.println("Random.class : " + cl1.getName());
        System.out.println("int.class : " + cl2.getName());
        System.out.println("Double[].class : " + cl3.getName());
        System.out.println("Employee.class : " + cl4.getName());
        System.out.println("Employee[].class : " + cl5.getName());

        System.out.println("\n========================\n");

        System.out.println("short.class : " + short.class.getName());
        System.out.println("byte.class : " + byte.class.getName());
        System.out.println("int.class : " + int.class.getName());
        System.out.println("long.class : " + long.class.getName());
        System.out.println("float.class : " + float.class.getName());
        System.out.println("double.class : " + double.class.getName());
        System.out.println("char.class : " + char.class.getName());
        System.out.println("boolean.class : " + boolean.class.getName());
        System.out.println("void.class : " + void.class.getName());

        System.out.println("\n========================\n");

        System.out.println("short[].class : " + short[].class.getName());
        System.out.println("byte[].class : " + byte[].class.getName());
        System.out.println("int[].class : " + int[].class.getName());
        System.out.println("long[].class : " + long[].class.getName());
        System.out.println("float[].class : " + float[].class.getName());
        System.out.println("double[].class : " + double[].class.getName());
        System.out.println("char[].class : " + char[].class.getName());
        System.out.println("boolean[].class : " + boolean[].class.getName());
        //System.out.println("void[].class : " + void[].class.getName());

        System.out.println("\n========================\n");

        System.out.println("Short.class : " + Short.class.getName());
        System.out.println("Byte.class : " + Byte.class.getName());
        System.out.println("Integer.class : " + Integer.class.getName());
        System.out.println("Long.class : " + Long.class.getName());
        System.out.println("Float.class : " + Float.class.getName());
        System.out.println("Double.class : " + Double.class.getName());
        System.out.println("Character.class : " + Character.class.getName());
        System.out.println("Boolean.class : " + Boolean.class.getName());
        System.out.println("Void.class : " + Void.class.getName());

        System.out.println("\n========================\n");

        System.out.println("Short[].class : " + Short[].class.getName());
        System.out.println("Byte[].class : " + Byte[].class.getName());
        System.out.println("Int[].class : " + Integer[].class.getName());
        System.out.println("Long[].class : " + Long[].class.getName());
        System.out.println("Float[].class : " + Float[].class.getName());
        System.out.println("Double[].class : " + Double[].class.getName());
        System.out.println("Char[].class : " + Character[].class.getName());
        System.out.println("Boolean[].class : " + Boolean[].class.getName());
        System.out.println("Void[].class : " + Void[].class.getName());
    }
}

class Employee {

}