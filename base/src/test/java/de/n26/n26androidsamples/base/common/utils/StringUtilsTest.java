package de.n26.n26androidsamples.base.common.utils;

import org.junit.Before;
import org.junit.Test;

import android.support.v4.util.Pair;

import de.n26.n26androidsamples.base.test_common.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
public class StringUtilsTest extends BaseTest {

    private StringUtils stringUtils;

    @Before
    public void setUp() {
        stringUtils = new StringUtils();
    }

    @Test
    public void parametersAreReplacedCorrectly() {
        String param1Name = "firstName";
        String param1Value = "Georgy";
        String param2Name = "lastName";
        String param2Value = "Georgiev";
        String string = "He is {{firstName}} {{lastName}}";

        String formattedString = stringUtils.applySubstitutionsToString(string,
                                                                        Pair.create(param1Name, param1Value),
                                                                        Pair.create(param2Name, param2Value));

        assertThat(formattedString).isEqualTo("He is Georgy Georgiev");
    }

    @Test
    public void notFoundParametersAreIgnored() {
        String param1Name = "wrongParamName";
        String param1Value = "Somebody";
        String string = "He is {{firstName}}";

        String formattedString = stringUtils.applySubstitutionsToString(string, Pair.create(param1Name, param1Value));

        assertThat(formattedString).isEqualTo(string);
    }

}
