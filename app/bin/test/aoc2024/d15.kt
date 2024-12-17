package aoc2024

class D15Test: SolutionTest<Long>(
    solution = D15(),
    answers = Answers(2028, null, null, null),
) {}

class D15Test2: SolutionTest<Long>(
    solution = D15(),
    answers = Answers(10092, 1509074, 9021, null),
    resources = Resources(
        part1Example = "example2.txt",
        part2Example = "example2.txt",
    ),    
) {}

class D15Test3: SolutionTest<Long>(
    solution = D15(),
    answers = Answers(null, null, 618, 1521453),
    resources = Resources(
        part2Example = "example3.txt",
    ),    
)
