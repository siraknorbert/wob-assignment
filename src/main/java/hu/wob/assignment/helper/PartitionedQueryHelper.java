package hu.wob.assignment.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.functionalinterfaces.OneParamGenericExceptionFunction;

/**
 * Helper class for partitioning database queries.
 */
@Component
public class PartitionedQueryHelper {

    private static final int PARTITION_SIZE = 1000;

    public <T, E> List<T> partitionedQuery(Collection<E> params, OneParamGenericExceptionFunction<List<E>, List<T>> function) throws Exception {
        if (params == null) {
            throw new InvalidInputException("params parameter is null");
        }

        List<E> copy = new ArrayList<>(params);
        List<List<E>> lists = Lists.partition(copy, PARTITION_SIZE);
        List<T> results = new ArrayList<>();
        for (List<E> sub : lists) {
            results.addAll(function.apply(sub));
        }
        return results;
    }
}
