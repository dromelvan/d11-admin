# To update to newly generated client do this in the directory that contains setup.py: pip install .
import d11_api_v1

api_client = d11_api_v1.ApiClient()
api_client.configuration.host = "http://192.168.1.3:8080"

player_api = d11_api_v1.PlayerApi(api_client)

def find_player_by_premier_league_id(premier_league_id):
    try:
        return player_api.find_player_by_premier_league_id(premier_league_id)
    except d11_api_v1.ApiException:
        return None
