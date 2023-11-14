import logging
import requests
from bs4 import BeautifulSoup
import re
from premier_league import PremierLeagueTeam, PremierLeaguePlayer

premier_league_table_url = "https://www.premierleague.com/tables"
premier_league_squad_url = "https://www.premierleague.com/clubs/{id}/{name}/squad"
premier_league_player_photo_url = "https://resources.premierleague.com/premierleague/photos/players/250x250/{id}.png"

def get_premier_league_teams():
  """
  Gets a list of Premier League teams from the league table page at PremierLeague.com
  """
  request = requests.get(premier_league_table_url)

  if request.status_code == 200:
      document = BeautifulSoup(request.text, features="lxml")
      team_tds = document.find_all("td", class_="team")

      premier_league_teams = set()

      for team_td in team_tds[:20]:
          match = re.search("\/clubs\/(\d{1,3})\/(.*)\/overview", team_td.a.get("href"))
          if match:
              premier_league_team = PremierLeagueTeam(match.group(1), match.group(2))
              premier_league_teams.add(premier_league_team)

      return premier_league_teams
  else:
      logging.error(f"{request.status_code} response when requesting Premier League table page.")
      return None




def get_premier_league_players(premier_league_team):
    """
    Gets a list of Premier League players from a team page at PremierLeague.com
    """
    request = requests.get(premier_league_squad_url.format(id = premier_league_team.id, name = premier_league_team.name))

    if (request.status_code == 200):
        document = BeautifulSoup(request.text, features="lxml")
        stats_cards = document.find_all("li", class_="stats-card")

        premier_league_players = set()

        for stats_card in stats_cards:
          match = re.search("\/players\/(\d*)\/.*/overview", stats_card.a.get("href"))
          if match:
            id = match.group(1)
            name = stats_card.find(class_="stats-card__player-name").getText().replace("\n", "").strip()
            position = stats_card.find(class_="stats-card__player-position").getText().strip()
            number = stats_card.find(class_="stats-card__squad-number u-show-mob-l").getText().strip()
            country = stats_card.find(class_="stats-card__player-country").getText().strip()
            photo_id = stats_card.find("img", class_="statCardImg statCardPlayer").get("data-player")

            premier_league_player = PremierLeaguePlayer(id, name, position, number, country, photo_id)

            premier_league_players.add(premier_league_player)

        return premier_league_players

    else:
        logging.error(f"{request.status_code} response when requesting Premier League squad page for {premier_league_team.name}")
        return None




def download_player_photo(image_id):
    """
    Downloads a player photo from PremierLeague.com
    """
    request = requests.get(premier_league_player_photo_url.format(id = image_id))

    if (request.status_code == 200):
        return request.content
    else:
        return None
