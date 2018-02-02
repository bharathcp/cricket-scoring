# Basic Scoring

As a Cricket Scorer, I want an application to help me add the scores so the score is always up to date.

* I can enter the score for each ball.
* I can view the score card for all balls to date. 
* I can view the total runs scored to date.
* I can amend mistakes to the score for the previous ball.

### Extra Detail

A "dot ball" scores 0 runs. "Dot ball"s are entered as a full stop.

The batsman can also score 1 to 6 runs for each ball, entered numerically.

### [Example - Scoring](-)

[Given a new innings](- "startInnings()")<br/>
When I enter the following scores in order<br/>
Then the Score Card and Total Runs Scored are accumulated as shown.

| [enter][][Entry][entry] | [Score Card][card] | [Total Runs Scored][score] |
| ---------------         | -------------      | ---------------            |
| . | .      | 0  |
| 1 | .1     | 1  |
| 3 | .13    | 4  |
| . | .13.   | 4  |
| 6 | .13.6  | 10 |

[enter]: - "#result = enterScore(#entry)"
[entry]: - "#entry"
[card]:  - "?=#result.card"
[score]: - "?=#result.score"


### [Example - Amending a mistake to the previous ball](- "amend-previous")
Given I entered '[2](- "#entry")' for the [first](- "startInnings(#entry)") ball of an innings and I should have entered '3'<br/>
When I [erase](- "eraseLastEntry()") the '2' and enter '[3](- "#result = enterScore(#TEXT)")'<br/>
Then the Score Card should show '[3](- "?=#result.card")' and the Total Runs Scored should show '[3](- "?=#result.score")'.

### [Example - Amending a mistake to multiple balls](- "amend-multiple")
Given I entered '[2..14](- "#entry")' at the [start](- "startInnings(#entry)") of an innings and I should have entered '2..34'<br/>
When I erase the last [2](- "eraseLastnEntries(#TEXT)") entries and enter '[34](- "#result = enterScore(#TEXT)")'<br/>
Then the Score Card should show '[2..34](- "?=#result.card")' and the Total Runs Scored should show '[9](- "?=#result.score")'.