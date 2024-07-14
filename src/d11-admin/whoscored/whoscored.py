import logging
import os
import re
import sys
from bs4 import BeautifulSoup
from tkinter.filedialog import askdirectory
from datetime import datetime, timedelta

def parse_fixtures():
    """
    Parses WhoScored html files with Premier League fixtures in a directory. Get these files by going to WhoScored, 
    looking at the fixtures page and for each month doing inspect, copy the top level html element and saving to a file.
    """

    sql = """
insert into $schema.match (home_team_id, away_team_id, match_week_id, stadium_id, whoscored_id, datetime, home_team_goals, away_team_goals, previous_home_team_goals, previous_away_team_goals, elapsed, status, created_at, updated_at)
values ((select (id) from $schema.team where whoscored_id = {}),
        (select (id) from $schema.team where whoscored_id = {}),
        (select (id) from $schema.match_week where match_week_number = {} and season_id = (select max(id) from $schema.season)),
        (select (stadium_id) from $schema.team where whoscored_id = {}),
        {}, '{}', 0, 0, 0, 0, 'N/A', 0, now()::timestamp, now()::timestamp);"""
    
    schema = '${flyway:defaultSchema}'

    directory = askdirectory(initialdir = '.')

    if directory == "":
        sys.exit();
 
    # Date container element is <div class="Accordion-module_accordion__*****"> 
    dateContainerRe = re.compile('Accordion-module_accordion__.*')
    # Date header element is <div class="Accordion-module_header__*****"> 
    dateHeaderRe = re.compile('Accordion-module_header__.*')
    # Match elements are <div class="Match-module_row__*****">
    matchRe = re.compile('Match-module_row__.*')
    # Start time element is <span class="Match-module_startTime__*****">
    startTimeRe = re.compile('Match-module_startTime__.*')
    # Scores element is <div class="Match-module_scores__*****">
    scoresRe = re.compile('Match-module_scores__.*')
    # Scores element is <div class="Match-module_teamName__*****">
    teamNameRe = re.compile('Match-module_teamName__.*')
    # Team link is /Teams/{id}/Show/England-{teamName}
    teamLinkRe = re.compile('/Teams/(\d*)/Show/.*')

    matchCount = 0
    matchWeekNumber = 0
    matchWeekDates = []
    output = ''

    for filename in sorted(os.scandir(directory), key=lambda e: e.name):
        with open(os.path.join(directory, filename)) as file:
            logging.info(f'Parsing {file.name}')
            document = BeautifulSoup(file, features="lxml")    

            dateContainers = document.findAll('div',{'class' : dateContainerRe})

            for dateContainer in dateContainers:                
                date = dateContainer.find('div',{'class' : dateHeaderRe})
                logging.info(f'    Date {date.getText()}')

                for match in dateContainer.findAll('div',{'class' : matchRe}):
                    if matchCount % 10 == 0:
                        matchWeekDates.append(date.getText())
                        matchWeekNumber += 1

                    matchCount += 1                    
                    startTime = match.find('span',{'class' : startTimeRe}).getText()
                    matchDateTime = datetime.strptime(date.getText() + ' ' + startTime, '%A, %b %d %Y %H:%M') + timedelta(hours = 2)

                    whoScoredId = match.find('div',{'class' : scoresRe}).find('a').get('id').replace('scoresBtn-','')

                    teamNames = match.findAll('div',{'class': teamNameRe})

                    homeTeamId = re.search(teamLinkRe, teamNames[0].find('a').get('href')).group(1)
                    awayTeamId = re.search(teamLinkRe, teamNames[1].find('a').get('href')).group(1)

                    formattedDateTime = matchDateTime.strftime('%Y-%m-%d %H:%M:00')

                    logging.info(f'        {matchWeekNumber} {whoScoredId} {formattedDateTime} {homeTeamId} {awayTeamId}')
                    output += sql.format(homeTeamId, awayTeamId, matchWeekNumber, homeTeamId, whoScoredId, formattedDateTime).replace('$schema', schema)                    

    logging.info(f'Parsed {matchCount} matches')        

    with open('output.sql', 'w') as file:
        file.write(output)

    logging.info('Match week dates')
    for matchWeekDate in matchWeekDates:        
        logging.info(datetime.strptime(matchWeekDate, '%A, %b %d %Y').strftime('%Y-%m-%d'))
