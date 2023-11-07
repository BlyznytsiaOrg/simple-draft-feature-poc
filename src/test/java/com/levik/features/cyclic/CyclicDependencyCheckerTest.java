package com.levik.features.cyclic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CyclicDependencyCheckerTest {

    private CyclicDependencyChecker cyclicChecker;

    @BeforeEach
    public void setUp() {
        cyclicChecker = new CyclicDependencyChecker();
    }

    @Test
    void testNoCyclicDependency() {
        assertThat(cyclicChecker.addDependency("A", "B")).isFalse();
        assertThat(cyclicChecker.addDependency("B", "C")).isFalse();
        assertThat(cyclicChecker.addDependency("C", "D")).isFalse();
    }

    @Test
    void testCyclicDependency() {
        assertThat(cyclicChecker.addDependency("A", "B")).isFalse();
        assertThat(cyclicChecker.addDependency("B", "C")).isFalse();
        assertThat(cyclicChecker.addDependency("C", "A")).isTrue();
    }

    @Test
    void testMultipleDependencies() {
        assertThat(cyclicChecker.addDependency("A", "B")).isFalse();
        assertThat(cyclicChecker.addDependency("B", "C")).isFalse();
        assertThat(cyclicChecker.addDependency("B", "D")).isFalse();
        assertThat(cyclicChecker.addDependency("C", "E")).isFalse();
        assertThat(cyclicChecker.addDependency("E", "F")).isFalse();
        assertThat(cyclicChecker.addDependency("F", "G")).isFalse();
        assertThat(cyclicChecker.addDependency("G", "H")).isFalse();
        assertThat(cyclicChecker.addDependency("H", "I")).isFalse();
    }

    @Test
    void testSelfDependency() {
        assertThat(cyclicChecker.addDependency("A", "A")).isFalse();
    }

}