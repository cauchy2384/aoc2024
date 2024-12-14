package aoc2024

class D14TestExample: SolutionTest<Long>(
    solution = D14(11L, 7L),
    answers = Answers(12, null, null, null),
) {}

class D14TestInput: SolutionTest<Long>(
    solution = D14(101L, 103L),
    answers = Answers(null, 208437768L, null, null),
) {}

class D14Test2Input: SolutionTest<Long>(
    solution = D14(101L, 103L),
    answers = Answers(null, null, null, 7492L),
) {}