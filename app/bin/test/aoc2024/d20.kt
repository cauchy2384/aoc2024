package aoc2024

class D20Test: SolutionTest<Long>(
    solution = D20(1),
    answers = Answers(44, null, null, null),
) {}

class D20Test2: SolutionTest<Long>(
    solution = D20(100),
    answers = Answers(null, 1307, null, 986545),
) {}

class D20Test3: SolutionTest<Long>(
    solution = D20(50),
    answers = Answers(null, null, 285, null),
) {}