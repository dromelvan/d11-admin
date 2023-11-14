import sys
import os
import shutil
import hashlib
from tkinter.filedialog import askdirectory
from premier_league import get_premier_league_teams, get_premier_league_players, download_player_photo
from d11_api import find_player_by_premier_league_id

download_directory = "../download/photo/"
temp_directory = download_directory + "temp"
updated_directory = download_directory + "updated"
new_directory = download_directory + "new"
unknown_directory = download_directory + "unknown"

photo_file_name_format = "{id}.png"

def update_player_photos():

    photo_directory = askdirectory(initialdir = '.')

    if photo_directory == "":
        sys.exit();

    if os.path.exists(download_directory):
        shutil.rmtree(download_directory)

    os.makedirs(temp_directory)
    os.makedirs(updated_directory)
    os.makedirs(new_directory)
    os.makedirs(unknown_directory)

    premier_league_teams = get_premier_league_teams()

    for premier_league_team in premier_league_teams:
        print(f"{premier_league_team.name}")
        premier_league_players = get_premier_league_players(premier_league_team)

        for premier_league_player in premier_league_players:
            image = download_player_photo(premier_league_player.photo_id)

            if image is None:
                # No photo was found on PremierLeague.com
                print(f"    {premier_league_player.name}: Photo not found")
            else:
                # Download the file to the temp directory and name it with the Premier League player id
                temp_file_name = photo_file_name_format.format(id = premier_league_player.photo_id)

                with open(temp_directory + "/" + temp_file_name, "wb") as image_file:
                    image_file.write(image)

                # Find a player with the Premier League id in the D11 API
                player = find_player_by_premier_league_id(premier_league_player.id)

                if player is None:
                    # No player with the Premier League id was found in the D11 API
                    print(f"    {premier_league_player.name}: Unknown")
                    os.rename(temp_directory + "/" + temp_file_name,
                              unknown_directory + "/" + temp_file_name)
                else:
                    # A player with the Premier League id was found in the D11 API. Name the final result file
                    # with the D11 API player id
                    file_name = photo_file_name_format.format(id = player.id)

                    if os.path.exists(photo_directory + "/" + file_name):
                        # A photo of the player already exists in the D11 application. Get the md5 of both
                        # the new and the existing file
                        temp_md5 = get_md5(temp_directory + "/" + temp_file_name)
                        existing_md5 = get_md5(photo_directory + "/" + file_name)

                        if temp_md5 == existing_md5:
                            # The new photo is the same as the already existing photo. Delete the temp file
                            os.remove(temp_directory + "/" + temp_file_name)
                            print(f"    {premier_league_player.name}: Delete")
                        else:
                            # The new photo is not the same as the already existing photo.
                            # Move the file to the updated directory
                            os.rename(temp_directory + "/" + temp_file_name,
                                      updated_directory + "/" + file_name)
                            print(f"    {premier_league_player.name}: Update")
                    else:
                        # A photo of the player does not already exist in the D11 application.
                        # Move the file to the unknown directory
                        os.rename(temp_directory + "/" + temp_file_name,
                                  new_directory + "/" + file_name)
                        print(f"    {premier_league_player.name}: New")


def get_md5(filename):
    with open(filename, "rb") as file:
        data = file.read()
        md5 = hashlib.md5(data).hexdigest()
        return md5

if __name__ == "__main__":
    update_player_photos()
