package com.zeta.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class LambdaBasics {
    static Predicate<Integer> predicate = (x) -> x % 2 == 0;
    static Consumer<String> consumerLambda = (message) -> System.out.println(message);
    static Calculator calculator=(x, y)-> {
        return x+y;
    };
    static ArrayIncrement incrementByTwo=(array) -> {
        int[] result= new int[5];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] + 2;
        }
        return result;
    };
    static IncrementEvenByX incrementEvenByX=(x,array) -> {
        int[] result= new int[5];
        for (int i = 0; i < array.length; i++) {
            if (array[i]%2==0) {
                result[i] = array[i] + x;
            }
            else{
                result[i]=array[i];
            }
        }
        return result;
    };
    static SumOfEveryThird sumOfEveryThird = (array) -> {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            if (i % 3 == 0) sum += array[i];
        }
        return sum;
    };

    public static void main(String[] args) {
        consumerLambda.accept("Hi");
        System.out.println(predicate.test(10));
        System.out.println(calculator.add(2,3));
        int[] numbers = {1, 2, 3, 4, 5};
        int[] updated = incrementByTwo.increment(numbers);
        System.out.println("Output of (Increment all numbers by two function");
        System.out.println(Arrays.toString(updated));
        System.out.println("Output of (Increment all Even numbers by two");
        int updated2[]=incrementEvenByX.incrementEvenByX(2,numbers);
        System.out.println(Arrays.toString(updated2));
        System.out.println("Output of Sum of every third value");
        int sum1=sumOfEveryThird.sumOfEveryThird(numbers);
        System.out.println(sum1);

    }
}
