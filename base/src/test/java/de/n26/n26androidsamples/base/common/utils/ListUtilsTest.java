package de.n26.n26androidsamples.base.common.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public class ListUtilsTest {

    @Mock
    private Object mockObject1;

    @Mock
    private Object mockObject2;

    @Mock
    private Object mockObject3;

    @Mock
    private Object mockObject4;

    @Mock
    private Object mockObject5;

    @Mock
    private Object mockObject6;

    @Test
    public void unionResultsInSecondListAppendedToFirstListRespectingTheOrder() {
        final List<Object> list1 = new ArrayList<Object>() {{
            add(mockObject1);
            add(mockObject2);
            add(mockObject3);
            add(mockObject4);
        }};
        final List<Object> list2 = new ArrayList<Object>() {{
            add(mockObject5);
            add(mockObject6);
        }};

        final List<Object> result = ListUtils.union(list1, list2);

        Assertions.assertThat(result).containsExactly(mockObject1, mockObject2, mockObject3, mockObject4, mockObject5, mockObject6);
    }

    @Test
    public void unionDoesNotRemoveDuplicates() {
        final List<Object> list1 = new ArrayList<Object>() {{
            add(mockObject5);
            add(mockObject6);
        }};
        final List<Object> list2 = new ArrayList<Object>() {{
            add(mockObject5);
            add(mockObject6);
        }};

        final List<Object> result = ListUtils.union(list1, list2);

        Assertions.assertThat(result).containsExactly(mockObject5, mockObject6, mockObject5, mockObject6);

    }
}
