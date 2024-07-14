import random
import logging

def flip(match_pair):
    # Flip home and away teams
    home, away = match_pair.split(':')
    return f"{away}:{home}"

def generate_fixtures():
    """
    Generate D11 fixtures.
    """

    sql = """
insert into $schema.d11_match (home_d11_team_id, away_d11_team_id, match_week_id, datetime, home_team_goals, away_team_goals, home_team_points, away_team_points,
                                               previous_home_team_goals, previous_away_team_goals, previous_home_team_points, previous_away_team_points, elapsed, status, created_at, updated_at)
values((select d11_team_id from $schema.d11_team_season_stat where ranking = {} and season_id = (select max(id) from $schema.season)),
       (select d11_team_id from $schema.d11_team_season_stat where ranking = {} and season_id = (select max(id) from $schema.season)),
       (select id from $schema.match_week where match_week_number = {} and season_id = (select max(id) from $schema.season)),
       (select((select date from $schema.match_week where match_week_number = {} and season_id = (select max(id) from $schema.season))::timestamp)),
       0, 0, 0, 0, 0, 0, 0, 0, 'N/A', 0, now()::timestamp, now()::timestamp);"""

    schema = '${flyway:defaultSchema}'

    rankings = list(range(1, 21))
    random.shuffle(rankings)

    matchDayCount = len(rankings) - 1
    matchDayMatchCount = len(rankings) // 2
    matchDays = [["" for _ in range(matchDayMatchCount)] for _ in range(matchDayCount)]

    for matchDay in range(matchDayCount):
        for match in range(matchDayMatchCount):
            home = (matchDay + match) % (len(rankings) - 1)
            away = (len(rankings) - 1 - match + matchDay) % (len(rankings) - 1)
            # The last team will stay in place while the others rotate around it.
            if match == 0:
                away = len(rankings) - 1
            matchDays[matchDay][match] = f"{rankings[home]}:{rankings[away]}"

    # Mix it up to evenly distribute home and away games
    shuffled = [["" for _ in range(matchDayMatchCount)] for _ in range(matchDayCount)]
    even = 0
    odd = matchDayCount // 2

    for i in range(matchDayCount):
        if i % 2 == 0:
            shuffled[i] = matchDays[even]
            even += 1
        else:
            shuffled[i] = matchDays[odd]
            odd += 1

    # Assign shuffled back to matchDays
    matchDays = shuffled

    # The last team can't play away all the time so switch them to the home team for odd numbered match days.
    for matchDay in range(len(matchDays)):
        if matchDay % 2 == 1:
            matchDays[matchDay][0] = flip(matchDays[matchDay][0])

    # Create allMatchDays with double the match days
    allMatchDays = [["" for _ in range(matchDayMatchCount)] for _ in range(matchDayCount * 2)]

    # Copy matchDays into the first half of allMatchDays
    for i in range(matchDayCount):
        allMatchDays[i] = matchDays[i][:]

    # Flip match pairs and populate the second half of allMatchDays
    for i in range(matchDayCount):
        for j in range(matchDayMatchCount):
            allMatchDays[i + matchDayCount][j] = flip(matchDays[i][j])

    matchCount = 0
    matchWeekNumber = 0
    output = ''

    # Print the matchDays array (for demonstration)
    for row in allMatchDays:
        matchWeekNumber += 1
        random.shuffle(row)
        for match in row:
            matchRankings = match.split(':')
            output += sql.format(matchRankings[0], matchRankings[1], matchWeekNumber, matchWeekNumber).replace('$schema', schema)                    

            matchCount += 1
            
    with open('output.sql', 'w') as file:
        file.write(output)

    logging.info(f'Generated {matchCount} D11 matches')