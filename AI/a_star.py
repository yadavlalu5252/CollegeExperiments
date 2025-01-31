from simpleai.search import SearchProblem, astar # type: ignore

GOAL = 'HELLO WORLD'

class HelloProblem(SearchProblem):
    def actions(self, state):
        if len(state) < len(GOAL):
            return list(' ABCDEFGHIJKLMNOPQRSTUVWXYZ')  # Possible actions (characters)
        else:
            return []  # No more actions once the goal length is reached

    def result(self, state, action):
        return state + action  # Append the action (single character) to the state

    def is_goal(self, state):
        return state == GOAL  # Goal is reached when state matches the GOAL string

    def heuristic(self, state):
        # Calculate the heuristic: number of wrong characters + missing characters
        wrong = sum([1 if state[i] != GOAL[i] else 0
                     for i in range(len(state))])  # Count wrong characters
        missing = len(GOAL) - len(state)  # Count missing characters (if any)
        return wrong + missing  # Total wrong + missing letters

problem = HelloProblem(initial_state='')

result = astar(problem)

print(result.state)
print(result.path())
