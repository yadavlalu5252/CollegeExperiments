from __future__ import print_function
from simpleai.search import CspProblem, backtrack, min_conflicts
from simpleai.search.csp import MOST_CONSTRAINED_VARIABLE, HIGHEST_DEGREE_VARIABLE, LEAST_CONSTRAINING_VALUE

variables = ('WA', 'NT', 'SA', 'Q', 'NSW', 'V', 'T')
domains = dict((v, ['red', 'green', 'blue']) for v in variables)

def const_different(variables, values):
    return values[0] != values[1]  # Expect the value of the neighbors to be different

constraints = [
    (('WA', 'NT'), const_different),
    (('WA', 'SA'), const_different),
    (('SA', 'NT'), const_different),
    (('SA', 'Q'), const_different),
    (('NT', 'Q'), const_different),
    (('SA', 'NSW'), const_different),
    (('Q', 'NSW'), const_different),
    (('SA', 'V'), const_different),
    (('NSW', 'V'), const_different),
]

my_problem = CspProblem(variables, domains, constraints)

print(backtrack(my_problem))  # Using default backtrack
print(backtrack(my_problem, variable_heuristic=MOST_CONSTRAINED_VARIABLE))  # Using most constrained variable heuristic
print(backtrack(my_problem, variable_heuristic=HIGHEST_DEGREE_VARIABLE))  # Using highest degree variable heuristic
print(backtrack(my_problem, value_heuristic=LEAST_CONSTRAINING_VALUE))  # Using least constraining value heuristic
print(backtrack(my_problem, variable_heuristic=MOST_CONSTRAINED_VARIABLE, value_heuristic=LEAST_CONSTRAINING_VALUE))  # Both heuristics
print(backtrack(my_problem, variable_heuristic=HIGHEST_DEGREE_VARIABLE, value_heuristic=LEAST_CONSTRAINING_VALUE))  # Both heuristics
print(min_conflicts(my_problem))  # Using the min conflicts heuristic
