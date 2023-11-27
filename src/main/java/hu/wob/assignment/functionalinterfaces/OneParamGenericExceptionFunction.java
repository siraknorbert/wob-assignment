package hu.wob.assignment.functionalinterfaces;

@FunctionalInterface
public interface OneParamGenericExceptionFunction<T, R> {

    R apply(T t) throws Exception;
}
