package aoc2024



class D12Test1: SolutionTest<Long>(
    solution = D12(),
    answers = Answers(null, null, 80, null),
    resources = Resources(
        part2Example = "example2.txt",
    ),
) {}

class D12Test2: SolutionTest<Long>(
    solution = D12(),
    answers = Answers(null, null, 236, null),
    resources = Resources(
        part2Example = "example3.txt",
    ),
) {}

class D12Test3: SolutionTest<Long>(
    solution = D12(),
    answers = Answers(null, null, 368, null),
    resources = Resources(
        part2Example = "example4.txt",
    ),
) {}

class D12Test: SolutionTest<Long>(
    solution = D12(),
    answers = Answers(1930, 1431440, 1206, 869070),
) {}