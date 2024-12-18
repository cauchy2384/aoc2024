package aoc2024

class D18Test: SolutionTest<Long>(
    solution = D18(6, 12),
    answers = Answers(22, null, 20, null),
) {}

class D18Test2: SolutionTest<Long>(
    solution = D18(70, 1024),
    answers = Answers(null, 270, null, 2871),
) {}