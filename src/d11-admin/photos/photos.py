from premier_league import get_premier_league_teams, get_premier_league_players, download_player_photo
import os

download_directory = "../download/photo/temp"
photo_file_name = "{id}.png"

def update_player_photos():
  premier_league_teams = get_premier_league_teams()

  if not os.path.exists(download_directory):
     os.makedirs(download_directory)

  for premier_league_team in premier_league_teams:
     print(f"{premier_league_team.name}")
     premier_league_players = get_premier_league_players(premier_league_team)

     for premier_league_player in premier_league_players:
        image = download_player_photo(premier_league_player.photo_id)

        if not image == None:
            image_file = open(download_directory + "/" + photo_file_name.format(id = premier_league_player.photo_id), "wb")
            image_file.write(image)
            image_file.close()
        else:
            print(f"    Photo not Found for {premier_league_player.name} ({premier_league_team.name})")

if __name__ == "__main__":
   update_player_photos()
