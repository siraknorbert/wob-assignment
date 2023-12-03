package hu.wob.assignment.helper;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.repository.MarketplaceRepository;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PartitionedQueryHelperTest {

    @Mock
    private MarketplaceRepository marketplaceRepository;

    @Autowired
    private PartitionedQueryHelper partitionedQueryHelper;

    @Test
    void testPartitionedQueryWith3000ElementsRepositoryInvoked3Times() throws Exception {
        // given
        List<Integer> inputList = create3000Elements();

        // when
        partitionedQueryHelper.partitionedQuery(inputList, marketplaceRepository::findIdsThatAreInGivenCollection);

        // then
        Mockito.verify(marketplaceRepository, Mockito.times(3)).findIdsThatAreInGivenCollection(Mockito.any());
    }

    @Test
    void testPartitionedQueryThrowsInvalidInputExceptionForNullInput() {
        // given when then
        Assertions.assertThrows(InvalidInputException.class, () -> partitionedQueryHelper.partitionedQuery(null, null));
    }

    private List<Integer> create3000Elements() {
        List<Integer> elements = new ArrayList<>();
        for (int i = 1; i < 3001; i++) {
            elements.add(i);
        }
        return elements;
    }
}
