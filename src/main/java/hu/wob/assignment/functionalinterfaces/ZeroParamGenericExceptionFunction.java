package hu.wob.assignment.functionalinterfaces;

@FunctionalInterface
public interface ZeroParamGenericExceptionFunction<T> {

    T apply() throws Exception;
}
