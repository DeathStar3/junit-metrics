package org.junit.experimental.categories;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Suite.SuiteClasses;

public class CategoriesAndParameterizedTest {
    @Test
    public void doesNotRunTestsWithoutCategory() {
        Result result = new JUnitCore()
                .run(SuiteWithParameterizedTestWithoutCategory.class);
        assertEquals(1, result.getRunCount());
        assertEquals(0, result.getFailureCount());
    }

    @Test
    public void runsTestsWithoutCategory() {
        Result result = new JUnitCore()
                .run(SuiteWithParameterizedTestWithCategory.class);
        assertEquals(2, result.getRunCount());
        assertEquals(0, result.getFailureCount());
    }

    @Test
    public void runsTestMethodWithCategory() {
        Result result = new JUnitCore()
                .run(SuiteWithParameterizedTestWithMethodWithCategory.class);
        assertEquals(2, result.getRunCount());
        assertEquals(0, result.getFailureCount());
    }

    public static class Token {

    }

    @RunWith(Parameterized.class)
    public static class ParameterizedTestWithoutCategory {
        @Parameterized.Parameter
        public String value;

        @Parameters
        public static Iterable<String> getParameters() {
            return Arrays.asList("first", "second");
        }

        @Test
        public void testSomething() {
            Assert.assertTrue(true);
        }
    }

    @Category(Token.class)
    public static class TestThatAvoidsNoTestRemainsException {
        @Test
        public void testSomething() {
            Assert.assertTrue(true);
        }
    }

    @RunWith(Categories.class)
    @IncludeCategory(Token.class)
    @SuiteClasses({TestThatAvoidsNoTestRemainsException.class,
            ParameterizedTestWithoutCategory.class})
    public static class SuiteWithParameterizedTestWithoutCategory {
    }

    @RunWith(Parameterized.class)
    @Category(Token.class)
    public static class ParameterizedTestWithCategory {
        @Parameterized.Parameter
        public String value;

        @Parameters
        public static Iterable<String> getParameters() {
            return Arrays.asList("first", "second");
        }

        @Test
        public void testSomething() {
            Assert.assertTrue(true);
        }
    }

    @RunWith(Categories.class)
    @IncludeCategory(Token.class)
    @SuiteClasses({ParameterizedTestWithCategory.class})
    public static class SuiteWithParameterizedTestWithCategory {
    }

    @RunWith(Parameterized.class)
    public static class ParameterizedTestWithMethodWithCategory {
        @Parameterized.Parameter
        public String value;

        @Parameters
        public static Iterable<String> getParameters() {
            return Arrays.asList("first", "second");
        }

        @Test
        @Category(Token.class)
        public void testSomething() {
            Assert.assertTrue(true);
        }

        @Test
        public void testThatIsNotExecuted() {
            Assert.assertTrue(true);
        }
    }

    @RunWith(Categories.class)
    @IncludeCategory(Token.class)
    @SuiteClasses({ParameterizedTestWithMethodWithCategory.class})
    public static class SuiteWithParameterizedTestWithMethodWithCategory {
    }
}