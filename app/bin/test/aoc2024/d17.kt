package aoc2024

class D17Test: SolutionTest<String>(
    solution = D17(),
    answers = Answers(
        "4,6,3,5,6,3,5,2,1,0", 
        "7,3,1,3,6,3,6,0,2",
        "117440",
        "105843716614554",
    ),
    resources = Resources(
        part2Example = "example2.txt",
    ), 
) {}