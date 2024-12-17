package aoc2024

class D16Test: SolutionTest<Long>(
    solution = D16(),
    answers = Answers(7036, 85396, 45, 428),
) {}

class D16Test2: SolutionTest<Long>(
    solution = D16(),
    answers = Answers(11048, null, 64, null),
    resources = Resources(
        part1Example = "example2.txt",
        part2Example = "example2.txt",
    ),    
) {}